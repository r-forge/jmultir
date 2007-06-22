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
 * This GAUSS command object computes the univariate Jarque-Bera test and
 * stores the result in a symbol table if one is set. If the data contains
 * more than one column, the result is presented for each column in a table.
 *
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public final class UnivarJBeraCall extends RPCall {
	public static final JSCTypeDef JBERA_RESULT =
		new JSCTypeDef(
			"JBERA_RESULT",
			JSCTypes.NARRAY,
			"Jarque-Bera test result: jb~prob_JB~skew~kurt");

	private JSCNArray data = null;
	private JSCSArray names = null;

	private JSCNArray jbRes = null;

	/**
	 * <code>UnivarJBeraCall</code> constructor takes the arguments for the procedure call.
	 */
	public UnivarJBeraCall(JSCNArray data, JSCSArray names) {

		super();

		setName("Univariate Jarque-Bera Test");

		this.data = data;
		this.names = names;
	}

	/**
	 * Writes the text output to a string that can be
	 * referenced via <code>getOutput</code>.
	 */
	protected void finalCode() {

		StringBuffer buffer = output;

		if (data.cols() == 1) {
			if (names != null)
				buffer.append(
					"JARQUE-BERA TEST for \""
						+ names.stringAt(0, 0)
						+ "\"\n\n");
			else
				buffer.append("JARQUE-BERA TEST:\n\n");
			buffer.append(
				FArg.sprintf(
					"%-25s %- 13.4f\n",
					new FArg("test statistic:").add(jbRes.doubleAt(0, 0))));
			buffer.append(
				FArg.sprintf(
					"%-25s %- 13.4f\n",
					new FArg(" p-Value(Chi^2):").add(jbRes.doubleAt(0, 1))));
			buffer.append(
				FArg.sprintf(
					"%-25s %- 13.4f\n",
					new FArg("skewness:").add(jbRes.doubleAt(0, 2))));
			buffer.append(
				FArg.sprintf(
					"%-25s %- 13.4f\n",
					new FArg("kurtosis:").add(jbRes.doubleAt(0, 3))));

		}
		if (data.cols() > 1) { // Table if more series.

			buffer.append("JARQUE-BERA TEST\n");
			buffer.append("\n");
			buffer.append(
				FArg.sprintf(
					"%-15s %-10s %-15s %-10s %-10s\n",
					new FArg("variable").add("teststat").add(
						"p-Value(Chi^2)").add(
						"skewness").add(
						"kurtosis")));
			for (int i = 0; i < names.rows(); i++) {
				buffer.append(
					FArg.sprintf(
						"%- 14s %- 10.4f %- 15.4f %- 10.4f %- 10.4f",
						new FArg(names.stringAt(i, 0))
							.add(jbRes.doubleAt(i, 0))
							.add(jbRes.doubleAt(i, 1))
							.add(jbRes.doubleAt(i, 2))
							.add(jbRes.doubleAt(i, 3))));
				buffer.append("\n");
			}

		}

		buffer.append("\n");
	}

	/**
	 * @see PCall
	 */
	protected void runCode() {
		if (getSymbolTable() != null) {
			getSymbolTable().get(JBERA_RESULT).clear();
		}

		if (data.rows() < 2)
			throw new RuntimeException("Not enough observations.");

		engine().load("fil", GaussLoadTypes.LIB);
		engine().load("diag", GaussLoadTypes.LIB);

		jbRes = (JSCNArray) JBERA_RESULT.getInstance();

		for (int i = 0; i < data.cols(); i++) {
			JSCNArray result = new JSCNArray("result");

			engine().call(
				"jarber_diagnos",
				new JSCData[] { new JSCNArray("data", data.getCol(i))},
				new JSCData[] { result });

			jbRes.appendRows(result);
		}

		if (getSymbolTable() != null)
			getSymbolTable().set(jbRes);

	}
}