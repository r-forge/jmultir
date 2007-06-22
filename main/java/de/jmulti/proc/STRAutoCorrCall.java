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
import com.jstatcom.model.JSCInt;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.JSCTypeDef;
import com.jstatcom.model.JSCTypes;
import com.jstatcom.util.FArg;

/**
 * Handles test on no serial correlation in an STR model. 
 * The result is stored under STR_AUTOCORR_RESULT.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public final class STRAutoCorrCall extends RPCall {
	public static final JSCTypeDef STR_AUTOCORR_RESULT =
		new JSCTypeDef(
			"STR_AUTOCORR_RESULT",
			JSCTypes.NARRAY,
			"A test of the functional form that has power against remaining serial dependence in the conditional mean.\n"
				+ "[Lag~F-value~nom-df~den-df~p-value] is a delay x 5 matrix");
	private JSCNArray result;
	private JSCNArray RTZ;
	private int lags = 0;

	/**
	 * <code>STRAutoCorrCall</code> constructor takes the arguments for the procedure call.
	 */
	public STRAutoCorrCall(JSCNArray RTZ, int lags) {

		super();

		setName("STR test on no autocorrelation");

		this.RTZ = RTZ;
		this.lags = lags;

	}

	/* 
	 * @see ProcCall#runCode()
	 */
	protected void runCode() {

		if (getSymbolTable() != null)
			getSymbolTable().get(STR_AUTOCORR_RESULT).clear();

		engine().load("str", GaussLoadTypes.LIB);

		result = (JSCNArray) STR_AUTOCORR_RESULT.getInstance();

		engine().call(
			"serind_strtests",
			new JSCData[] {
				new JSCNArray("resids", RTZ.getCol(0)),
				new JSCNArray("gradient", RTZ.getCols(2, RTZ.cols() - 1)),
				new JSCInt("lags", lags)},
			new JSCData[] { result });

		if (getSymbolTable() != null)
			getSymbolTable().set(result);

	}
	/* 
	 * @see gauss.ProcCall#finalCode()
	 */
	protected void finalCode() {
		double[][] serind_result = result.doubleArray();
		StringBuffer buffer = output;
		buffer.append(
			"Test of No Error Autocorrelation (NaN - matrix inversion problem):\n\n");
		buffer.append(
			FArg.sprintf(
				"%-10s%-13s%-9s%-9s%-13s\n",
				new FArg("lag").add("F-value").add("df1").add("df2").add(
					"p-value")));
		for (int i = 0; i < serind_result.length; i++)
			buffer.append(
				FArg.sprintf(
					"%-9i%- 13.4f%- 9i%- 9i%- 13.4f\n",
					new FArg(serind_result[i][0])
						.add(serind_result[i][1])
						.add(serind_result[i][2])
						.add(serind_result[i][3])
						.add(serind_result[i][4])));

		buffer.append("\n");

	}
}
