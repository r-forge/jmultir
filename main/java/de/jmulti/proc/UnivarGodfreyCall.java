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
 * This GAUSS command object computes the univariate Godfrey 
 * LM-type test for serial correlation and
 * stores the result in a symbol table if one is set.
 *
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public final class UnivarGodfreyCall extends RPCall {
	public static final JSCTypeDef GODFREY_RESULT =
		new JSCTypeDef(
			"GODFREY_RESULT",
			JSCTypes.NARRAY,
			"result of univariate Breusch-Godfrey test: teststat~prob_chi~F-stat~prob_F");
	public static final JSCTypeDef GODFREY_EST =
		new JSCTypeDef(
			"GODFREY_EST",
			JSCTypes.NARRAY,
			"estimation results from univariate Breusch-Godfrey test: b~stderr");

	private int lags = 0;
	private JSCNArray res = null;
	private JSCNArray orig = null;

	private JSCNArray godfreyResult = null;

	/**
	 * <code>UnivarGodfreyCall</code> constructor takes the arguments for the procedure call.
	 */
	public UnivarGodfreyCall(JSCNArray res, JSCNArray orig, int lags) {

		super();

		setName("Univariate Godfrey Test");

		this.lags = lags;
		this.orig = orig;
		this.res = res;

	}

	/**
	 * Writes the text output to a string that can be
	 * referenced via <code>getOutput</code>.
	 */
	protected void finalCode() {

		StringBuffer buffer = output;

		double[][] godfrey_Result = godfreyResult.doubleArray();
		buffer.append("GODFREY TEST with " + lags + " lags\n\n");
		buffer.append(
			FArg.sprintf(
				"%-20s%- 16.4f\n",
				new FArg("test stat").add(godfrey_Result[0][0])));
		buffer.append(
			FArg.sprintf(
				"%-20s%- 16.4f\n",
				new FArg(" p-Value(Chi^2)").add(godfrey_Result[0][1])));
		buffer.append(
			FArg.sprintf(
				"%-20s%- 16.4f\n",
				new FArg("F-test").add(godfrey_Result[0][2])));
		buffer.append(
			FArg.sprintf(
				"%-20s%- 16.4f\n\n",
				new FArg(" p-Value(F)").add(godfrey_Result[0][3])));

		buffer.append("\n");
	}

	/**
	 * @see RPCall
	 */
	protected void runCode() {

		if (getSymbolTable() != null) {
			getSymbolTable().get(GODFREY_EST).clear();
			getSymbolTable().get(GODFREY_RESULT).clear();
		}

		if (res.rows() < 2 * lags || orig.rows() < 2 * lags)
			throw new RuntimeException("Not enough observations.");

		godfreyResult = (JSCNArray) GODFREY_RESULT.getInstance();
		JSCData godfreyEst = GODFREY_EST.getInstance();

		engine().load("fil", GaussLoadTypes.LIB);
		engine().load("diag", GaussLoadTypes.LIB);

		engine().call(
			"godfrey_diagnos",
			new JSCData[] { res, orig, new JSCInt("lags", lags)},
			new JSCData[] { godfreyResult, godfreyEst });

		if (getSymbolTable() != null) {
			getSymbolTable().set(godfreyResult);
			getSymbolTable().set(godfreyEst);

		}

	}
}