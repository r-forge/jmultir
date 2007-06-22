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
import com.jstatcom.model.JSCString;
import com.jstatcom.model.JSCTypeDef;
import com.jstatcom.model.JSCTypes;
import com.jstatcom.ts.TSDateRange;
import com.jstatcom.util.FArg;

/**
 * This GAUSS command object computes the Schmidt-Phillips test and
 * stores the result in a symbol table if one is set under
 * the name <code>SP_RESULT</code>.
 *
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public final class SPTestCall extends RPCall {
	public static final JSCTypeDef SP_RESULT =
		new JSCTypeDef(
			"SP_RESULT",
			JSCTypes.NARRAY,
			"Schmidt-Phillips test result: Z(rho) or Z(tau)");

	private int lags = 0;
	private JSCNArray y = null;
	private JSCString yName = null;
	private TSDateRange range = null;
	private int testState = 0;
	private JSCNArray result = null;

	/**
	 * <code>SPTestCall</code> constructor takes the arguments for the procedure call.
	 *
	 * @param testState
	 * <ul>
	 * <li>1 -- Z(rho) statistic
	 * <li>2 -- Z(tau) statistic
	 * </ul>
	 */
	public SPTestCall(
		JSCNArray y,
		JSCString yName,
		TSDateRange range,
		int lags,
		int testState) {

		super();

		setName("SP Test");

		this.lags = lags;
		this.range = range;
		this.y = y;
		this.yName = yName;
		this.testState = testState;

	}

	/**
	 * Writes the text output to a string that can be
	 * referenced via <code>getOutput</code>.
	 */
	protected void finalCode() {

		StringBuffer buffer = output;
		buffer.append(
			FArg.sprintf(
				"%-25s %s \n",
				new FArg("Schmidt-Phillips test for series:").add(
					yName.string())));
		buffer.append(range.format("sample range:", 19) + "\n");

		buffer.append(
			FArg.sprintf("%-18s %i \n", new FArg("number of lags:").add(lags)));
		buffer.append(
			"reference: Schmidt, P. and Phillips, P. C. B. (1992),\n\"LM tests for a unit root in the presence of deterministic trends\",\nOxford Bulletin of Economics and Statistics, vol. 54, p. 257-287, table 1A\n");
		if (testState == 1) {
			buffer.append("critical values (T=2000, Z(rho) statistic):\n");
			buffer.append(
				FArg.sprintf(
					"%-10s %-10s %-10s \n%-10s %-10s %-10s\n",
					new FArg(" 1%").add(" 5%").add(" 10%").add("-25.2").add(
						"-18.1").add(
						"-15.0")));
		}
		if (testState == 2) {
			buffer.append("critical values (T=2000, Z(tau) statistic):\n");
			buffer.append(
				FArg.sprintf(
					"%-10s %-10s %-10s \n%-10s %-10s %-10s\n",
					new FArg(" 1%").add(" 5%").add(" 10%").add("-3.56").add(
						"-3.02").add(
						"-2.75")));
		}
		buffer.append(
			FArg.sprintf(
				"value of test statistic: %.4f\n",
				new FArg(result.doubleAt(0, 0))));
		buffer.append("\n");
	}

	/**
	 * @see gauss.GaussProcCall
	 */
	protected void runCode() {
		if (getSymbolTable() != null) {
			getSymbolTable().get(SP_RESULT).clear();
		}

		if (y.rows() < 2)
			throw new RuntimeException(
				"Not enough observations in sample " + range + ".");

		engine().load("ur", GaussLoadTypes.LIB);

		result = (JSCNArray) SP_RESULT.getInstance();

		engine().call(
			"sptest_urtests",
			new JSCData[] {
				y,
				new JSCInt("lags", lags),
				new JSCInt("teststate", testState)},
			new JSCData[] { result });

		if (getSymbolTable() != null)
			getSymbolTable().set(result);

	}
}