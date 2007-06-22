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

/**
 * Handles test on parameter constancy in an STR model. 
 * The result is stored under STR_PARACONS_RESULT.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public final class STRParaConstCall extends RPCall {
	public static final JSCTypeDef STR_PARACONS_RESULT =
		new JSCTypeDef(
			"STR_PARACONS_RESULT",
			JSCTypes.NARRAY,
			"A test of the functional form that has power against nonconstant parameter in the conditional mean.\n"
				+ "3x4 [F-value~nom-df~den-df~p-value] for functions H1,H2,H3");
	private JSCNArray result;
	private JSCNArray RTZ;
	private JSCNArray X;
	private JSCNArray D;
	private JSCNArray selConst;
	private JSCNArray selPhi0;
	private JSCSArray constNames;
	private JSCNArray allRes;
	private JSCNArray selEx;

	/**
	 * <code>STRParaConstCall</code> constructor takes the arguments for the procedure call.
	 */
	public STRParaConstCall(
		JSCNArray X,
		JSCNArray D,
		JSCNArray RTZ,
		JSCNArray selConst,
		JSCNArray selPhi0,
		JSCSArray constNames,
		JSCNArray allRes,
		JSCNArray selEx) {

		super();

		setName("STR test on parameter constancy");

		this.RTZ = RTZ;
		this.X = X;
		this.D = D;
		this.selEx = selEx;
		this.selConst = selConst;
		this.selPhi0 = selPhi0;
		this.constNames = constNames;
		this.allRes = allRes;

	}

	/* 
	 * @see ProcCall#runCode()
	 */
	protected void runCode() {
		if (getSymbolTable() != null)
			getSymbolTable().get(STR_PARACONS_RESULT).clear();

		result = (JSCNArray) STR_PARACONS_RESULT.getInstance();

		engine().load("str", GaussLoadTypes.LIB);

		engine().call(
			"paracons_strtests",
			new JSCData[] {
				D,
				X,
				allRes,
				selConst,
				selPhi0,
				new JSCNArray("resids", RTZ.getCol(0)),
				new JSCNArray("gradient", RTZ.getCols(2, RTZ.cols() - 1)),
				new JSCNArray("transfkt", RTZ.getCol(1)),
				selEx },
			new JSCData[] { result });

		if (getSymbolTable() != null)
			getSymbolTable().set(result);

	}
	/* 
	 * @see ProcCall#finalCode()
	 */
	protected void finalCode() {
		double[][] paraconst_result = result.doubleArray();

		StringBuffer buffer = output;
		buffer.append(
			"Parameter Constancy Test (NaN - matrix inversion problem):\n\n");

		String cn = "";
		for (int i = 0; i < selEx.rows(); i++)
			if (selEx.doubleAt(i, 0) == 1)
				cn = cn + " " + constNames.stringAt(i, 0);

		buffer.append("variables not under test:  " + cn.trim() + "\n\n");
		buffer.append(
			FArg.sprintf(
				"%-24s%-13s%-13s%-13s%-13s\n",
				new FArg("transition function").add(" F-value").add(
					" df1").add(
					" df2").add(
					" p-value")));

		for (int i = 0; i < paraconst_result.length; i++) {
			buffer.append(
				FArg.sprintf(
					"%-24s%- 13.4f%- 13.4f%- 13.4f%- 13.4f\n",
					new FArg("H" + (i + 1))
						.add(paraconst_result[i][0])
						.add(paraconst_result[i][1])
						.add(paraconst_result[i][2])
						.add(paraconst_result[i][3])));
		}
		buffer.append("\n");
	}
}
