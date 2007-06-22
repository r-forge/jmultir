/*
 This file is part of the econometric software package JMulTi.
 Copyright (C) 2000-2005  Markus Kraetzig

 This program is free software; you can redistribute it and/or
 modify it under the terms of the GNU General Public License
 as published by the Free Software Foundation; either version 2
 of the License, or (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program; if not, write to the Free Software
 Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package de.jmulti.proc;

import com.jstatcom.engine.gauss.GaussLoadTypes;
import com.jstatcom.model.JSCData;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.JSCSArray;
import com.jstatcom.model.JSCTypeDef;
import com.jstatcom.model.JSCTypes;
import com.jstatcom.ts.TSDateRange;
import com.jstatcom.util.FArg;
import com.jstatcom.util.UMatrix;

/**
 * Handles tests for nonlinearities in the STR modelling cycle.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
public final class STRNonLinTestCall extends RPCall {

	public static final JSCTypeDef STR_LINTEST_RESULT = new JSCTypeDef(
			"STR_LINTEST_RESULT",
			JSCTypes.NARRAY,
			"results of test for nonlinearity for each transition variable in a row (p-values):\n"
					+ "b1=b2=b3=0 | b3 = 0 | b2 = 0 (given b3=0) | b1 = 0 (given b2=b3=0)");
	private JSCNArray result = null;
	private JSCNArray Y;
	private JSCNArray X;
	private JSCNArray D;
	private int py;
	private int pz;
	private TSDateRange sampleRange;
	private JSCNArray selTrans;
	private JSCSArray transNames;
	private JSCNArray selConst_noConst;
	private JSCNArray selPhi0_noConst;
	private JSCSArray constNames_noConst;
	private JSCNArray allRes;

	/**
	 * <code>STRNonLinTestCall</code> constructor takes the arguments for the
	 * procedure call.
	 */
	public STRNonLinTestCall(JSCNArray Y, JSCNArray X, JSCNArray D, int py,
			int pz, TSDateRange sampleRange, JSCNArray selTrans,
			JSCSArray transNames, JSCNArray selConst_noConst,
			JSCNArray selPhi0_noConst, JSCSArray constNames_noConst,
			JSCNArray allRes) {

		super();

		setName("STR Nonlinearity Tests");

		this.Y = Y;
		this.X = X;
		this.D = D;
		this.py = py;
		this.pz = pz;
		this.sampleRange = sampleRange;
		this.selTrans = selTrans;
		this.transNames = transNames;
		this.selConst_noConst = selConst_noConst;
		this.selPhi0_noConst = selPhi0_noConst;
		this.constNames_noConst = constNames_noConst;
		this.allRes = allRes;

	}

	/*
	 * @see ProcCall#runCode()
	 */
	protected void runCode() {

		if (getSymbolTable() != null)
			getSymbolTable().get(STR_LINTEST_RESULT).clear();

		engine().load("str", GaussLoadTypes.LIB);

		result = (JSCNArray) STR_LINTEST_RESULT.getInstance();

		engine().call(
				"lintest_lintest",
				new JSCData[]{Y, D, X, allRes, selTrans, selConst_noConst,
						selPhi0_noConst}, new JSCData[]{result});

		if (getSymbolTable() != null)
			getSymbolTable().set(result);

	}

	/*
	 * @see ProcCall#finalCode()
	 */
	protected void finalCode() {
		StringBuffer buffer = output;
		int sel = UMatrix.getNonzeroDoubleCount(selTrans.vec());
		String[] selTransNames = new String[sel];
		for (int i = 0, index = 0; i < transNames.rows(); i++) {
			if (selTrans.intAt(i, 0) > 0)
				selTransNames[index++] = transNames.stringAt(i, 0);
		}

		// AR part (allRes is without const but with trend)
		String variableNames = "CONST";
		for (int i = 0, j = 0; i < transNames.rows(); i++) {
			if (allRes.doubleAt(0, i) > 0
					&& selPhi0_noConst.doubleAt(j++, 0) == 0) {
				variableNames = variableNames + " " + transNames.stringAt(i, 0);
			}
		}
		// part with theta = 0, excluded from nonlinear part
		String constNames = "";
		for (int i = 0; i < selConst_noConst.rows(); i++) {
			if (selConst_noConst.intAt(i, 0) > 0)
				constNames = constNames + " "
						+ constNames_noConst.stringAt(i, 0);
		}

		double[][] res = result.doubleArray();
		String[] mTypes = new String[res.length];
		for (int i = 0; i < mTypes.length; i++) {
			mTypes[i] = "Linear";
			if (res[i][0] < 0.05) {
				mTypes[i] = "LSTR1"; // default
				double H04 = res[i][1];
				double H03 = res[i][2];
				double H02 = res[i][3];

				if (H03 < H04 && H03 < H02)
					mTypes[i] = "LSTR2";
			}
		}
		buffer.append("TESTING LINEARITY AGAINST STR\n\n");
		buffer.append(FArg.sprintf("%-25s %s \n", new FArg(
				"variables in AR part:").add(variableNames)));
		buffer.append(FArg.sprintf("%-25s %s \n", new FArg(
				"param. not under test:").add(constNames.trim())));
		buffer.append(sampleRange.addPeriodsToStart(Math.max(py, pz)).format(
				"sample range:", 26)
				+ "\n");
		buffer
				.append("\np-values of F-tests (NaN - matrix inversion problem):\n\n");
		buffer.append(FArg.sprintf("%-24s%-13s%-13s%-13s%-13s%-14s\n",
				new FArg("transition variable").add(" F").add(" F4").add(" F3")
						.add(" F2").add("suggested model")));
		int lowest_p = -1;
		double check = 10;
		for (int i = 0; i < res.length; i++) {
			if (res[i][0] < check && res[i][0] > 0) {
				lowest_p = i;
				check = res[i][0];
			}
		}
		for (int i = 0; i < res.length; i++) {
			String toAdd = "";
			if (i == lowest_p)
				toAdd = "*";
			buffer.append(FArg.sprintf(
					"%-24s%- 13.4e%- 13.4e%- 13.4e%- 13.4e%-14s\n", new FArg(
							selTransNames[i] + toAdd).add(res[i][0]).add(
							res[i][1]).add(res[i][2]).add(res[i][3]).add(
							mTypes[i])));

		}
		buffer.append("\n");

	}
}