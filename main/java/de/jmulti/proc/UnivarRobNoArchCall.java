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
 * This GAUSS command object computes the robustified test for no remaining
 * ARCH and stores the result in a symbol table if one is set.
 *
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public final class UnivarRobNoArchCall extends RPCall {
	public static final JSCTypeDef ROBNOARCH_RESULT =
		new JSCTypeDef(
			"ROBNOARCH_RESULT",
			JSCTypes.NARRAY,
			"results of robustified test for no remaining ARCH: Fstat|p_value|df1|df2");

	private int lags = 0;
	private JSCNArray data = null;
	private JSCNArray ivar = null;
	private JSCNArray param = null;
	private int p = 0;
	private int q = 0;

	private JSCNArray noRes = null;

	/**
	 * <code>UnivarRobNoArchCall</code> constructor takes the arguments for the procedure call.
	 */
	public UnivarRobNoArchCall(
		JSCNArray data,
		JSCNArray ivar,
		JSCNArray param,
		int p,
		int q,
		int lags) {

		super();

		setName("Robustified test for no remaining ARCH");

		this.lags = lags;
		this.data = data;
		this.ivar = ivar;
		this.param = param;
		this.p = p;
		this.q = q;

	}

	/**
	 * Writes the text output to a string that can be
	 * referenced via <code>getOutput</code>.
	 */
	protected void finalCode() {

		StringBuffer buffer = output;

		double[][] port = noRes.doubleArray();
		buffer.append("TEST OF NO REMAINING ARCH with " + lags + " lags\n\n");
		if (Double.doubleToLongBits(port[0][0])
			== Double.doubleToLongBits(Double.NaN)) {
			buffer.append(
				"Test could not be computed due to matrix inversion problem.\n\n");
			return;
		}
		buffer.append(
			FArg.sprintf(
				"%-20s%- 16.4f\n",
				new FArg("F-test stat.").add(port[0][0])));
		buffer.append(
			FArg.sprintf(
				"%-20s%- 16.4f\n",
				new FArg(" p-value").add(port[1][0])));
		buffer.append(
			FArg.sprintf(
				"%-20s%- 16.4f\n",
				new FArg(" degfree1").add(port[2][0])));
		buffer.append(
			FArg.sprintf(
				"%-20s%- 16.4f\n\n",
				new FArg(" degfree2").add(port[3][0])));

		buffer.append("\n");
	}

	/**
	 * @see RPCall
	 */
	protected void runCode() {

		if (getSymbolTable() != null)
			getSymbolTable().get(ROBNOARCH_RESULT).clear();

		if (data.rows() < 2 * lags)
			throw new RuntimeException("Not enough observations.");

		engine().load("fil", GaussLoadTypes.LIB);
		engine().load("diag", GaussLoadTypes.LIB);

		noRes = (JSCNArray) ROBNOARCH_RESULT.getInstance();

		engine().call(
			"Robnoarch_arch",
			new JSCData[] {
				data,
				ivar,
				param,
				new JSCInt("qLags", q),
				new JSCInt("pLags", p),
				new JSCInt("lags", lags)},
			new JSCData[] { noRes });

		if (getSymbolTable() != null)
			getSymbolTable().set(noRes);

	}
}