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
 * This GAUSS command object computes the multivariate tests for nonnormality and
 * stores the result in a symbol table if one is set. 
 *
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public final class MultvarNonNormCall extends RPCall {
	public static final JSCTypeDef MNONNORM_RESULT =
		new JSCTypeDef(
			"MNONNORM_RESULT",
			JSCTypes.NARRAY,
			"results of multivariate nonnormality tests:\n7x2 vector [Hendry (jordan)~Luetkepohl (cholesky)]\n"
				+ "value|p-value|df|skew|pskew|kurt|pkurt");

	private JSCNArray data = null;
	private int varLags = 0;
	private JSCNArray nonRes = null;

	/**
	 * <code>MultvarNonNormCall</code> constructor takes the arguments for the procedure call.
	 */
	public MultvarNonNormCall(JSCNArray data, int varLags) {

		super();

		setName("Multivariate Tests for Nonnormality");

		this.data = data;
		this.varLags = varLags;
	}

	/**
	 * Writes the text output to a string that can be
	 * referenced via <code>getOutput</code>.
	 */
	protected void finalCode() {

		StringBuffer buffer = output;

		double multResult[][] = nonRes.doubleArray();
		buffer.append(FArg.sprintf("%s\n", new FArg("TESTS FOR NONNORMALITY")));
		buffer.append("\nReference: Doornik & Hansen (1994)\n");
		buffer.append(
			FArg.sprintf(
				"%-24s %- 8.4f \n",
				new FArg("joint test statistic:").add(multResult[0][0])));
		buffer.append(
			FArg.sprintf(
				"%-24s %- 8.4f \n",
				new FArg(" p-value:").add(multResult[1][0])));
		buffer.append(
			FArg.sprintf(
				"%-24s %- 8.4f \n",
				new FArg("degrees of freedom:").add(multResult[2][0])));
		buffer.append(
			FArg.sprintf(
				"%-24s %- 8.4f \n",
				new FArg("skewness only:").add(multResult[3][0])));
		buffer.append(
			FArg.sprintf(
				"%-24s %- 8.4f \n",
				new FArg(" p-value:").add(multResult[4][0])));
		buffer.append(
			FArg.sprintf(
				"%-24s %- 8.4f \n",
				new FArg("kurtosis only:").add(multResult[5][0])));
		buffer.append(
			FArg.sprintf(
				"%-24s %- 8.4f \n",
				new FArg(" p-value:").add(multResult[6][0])));
		buffer.append(
			"\nReference: Lütkepohl (1993), Introduction to Multiple Time Series Analysis, 2ed, p. 153\n");
		buffer.append(
			FArg.sprintf(
				"%-24s %- 8.4f \n",
				new FArg("joint test statistic:").add(multResult[0][1])));
		buffer.append(
			FArg.sprintf(
				"%-24s %- 8.4f \n",
				new FArg(" p-value:").add(multResult[1][1])));
		buffer.append(
			FArg.sprintf(
				"%-24s %- 8.4f \n",
				new FArg("degrees of freedom:").add(multResult[2][1])));
		buffer.append(
			FArg.sprintf(
				"%-24s %- 8.4f \n",
				new FArg("skewness only:").add(multResult[3][1])));
		buffer.append(
			FArg.sprintf(
				"%-24s %- 8.4f \n",
				new FArg(" p-value:").add(multResult[4][1])));
		buffer.append(
			FArg.sprintf(
				"%-24s %- 8.4f \n",
				new FArg("kurtosis only:").add(multResult[5][1])));
		buffer.append(
			FArg.sprintf(
				"%-24s %- 8.4f \n",
				new FArg(" p-value:").add(multResult[6][1])));

		buffer.append("\n");
	}

	/**
	 * @see RPCall
	 */
	protected void runCode() {

		if (getSymbolTable() != null)
			getSymbolTable().get(MNONNORM_RESULT).clear();

		if (data.rows() < 2)
			throw new RuntimeException("Not enough observations");

		engine().load("diag", GaussLoadTypes.LIB);
		engine().load("fil", GaussLoadTypes.LIB);

		nonRes = (JSCNArray) MNONNORM_RESULT.getInstance();

		engine().call(
			"multnorm_resanal",
			new JSCData[] { data, new JSCInt("varlags", varLags)},
			new JSCData[] { nonRes });

		if (getSymbolTable() != null)
			getSymbolTable().set(nonRes);

	}
}