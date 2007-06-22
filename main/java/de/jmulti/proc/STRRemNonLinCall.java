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
import com.jstatcom.util.FArg;
import com.jstatcom.util.UMatrix;

/**
 * Handles test on no remaining nonlinearity in an STR model. 
 * The result is stored under STR_REMNONLIN_RESULT.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public final class STRRemNonLinCall extends RPCall {
	public static final JSCTypeDef STR_REMNONLIN_RESULT =
		new JSCTypeDef(
			"STR_REMNONLIN_RESULT",
			JSCTypes.NARRAY,
			"A test of the functional form that has power against remaining nonlinearity in the conditional mean.\n"
				+ "sumc(trans)x4 [F-value~nom-df~den-df~p-value] for each selected transition variable");
	private JSCNArray result;
	private JSCNArray RTZ;
	private JSCNArray X;
	private JSCNArray D;
	private JSCSArray transNames;
	private JSCNArray selTrans;
	private JSCNArray selConst;
	private JSCNArray selPhi0;
	private JSCNArray allRes;
	/**
	 * <code>STRRemNonLinCall</code> constructor takes the arguments for the procedure call.
	 */
	public STRRemNonLinCall(
		JSCNArray X,
		JSCNArray D,
		JSCNArray RTZ,
		JSCSArray transNames,
		JSCNArray selTrans,
		JSCNArray selConst,
		JSCNArray selPhi0,
		JSCSArray constNames,
		JSCNArray allRes) {

		super();

		setName("STR test on no remaining nonlinearity");

		this.RTZ = RTZ;
		this.X = X;
		this.D = D;
		this.transNames = transNames;
		this.selTrans = new JSCNArray(selTrans);
		this.selConst = selConst;
		this.selPhi0 = selPhi0;
		this.allRes = allRes;

	}

	/* 
	 * @see ProcCall#runCode()
	 */
	protected void runCode() {
		if (getSymbolTable() != null)
			getSymbolTable().get(STR_REMNONLIN_RESULT).clear();

		result = (JSCNArray) STR_REMNONLIN_RESULT.getInstance();

		engine().load("str", GaussLoadTypes.LIB);

		selTrans.appendRows(new JSCNArray("append", 0));

		engine().call(
			"remnonlin_strtests",
			new JSCData[] {
				D,
				X,
				allRes,
				selTrans,
				selConst,
				selPhi0,
				new JSCNArray("resids", RTZ.getCol(0)),
				new JSCNArray("gradient", RTZ.getCols(2, RTZ.cols() - 1))},
			new JSCData[] { result });

		if (getSymbolTable() != null)
			getSymbolTable().set(result);

	}
	/* 
	 * @see ProcCall#finalCode()
	 */
	protected void finalCode() {
		double[][] remnonlin_result = result.doubleArray();

		int sel = UMatrix.getNonzeroDoubleCount(selTrans.vec());
		String[] selTransNames = new String[sel];
		for (int i = 0, index = 0; i < transNames.rows(); i++) {
			if (selTrans.intAt(i, 0) > 0)
				selTransNames[index++] = transNames.stringAt(i, 0);
		}

		StringBuffer buffer = output;
		buffer.append(
			"Test of No Remaining Nonlinearity (NaN - matrix inversion problem):\n\n");

		buffer.append(
			FArg.sprintf(
				"%-24s%-13s%-13s%-13s%-13s\n",
				new FArg("transition variable").add(" F").add(" F4").add(
					" F3").add(
					" F2")));

		for (int i = 0; i < remnonlin_result.length; i++) {
			buffer.append(
				FArg.sprintf(
					"%-24s%- 13.4e%- 13.4e%- 13.4e%- 13.4e\n",
					new FArg(selTransNames[i])
						.add(remnonlin_result[i][0])
						.add(remnonlin_result[i][1])
						.add(remnonlin_result[i][2])
						.add(remnonlin_result[i][3])));
		}

		buffer.append("\n");

	}
}
