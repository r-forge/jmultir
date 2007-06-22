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
import com.jstatcom.model.JSCNumber;
import com.jstatcom.model.JSCSArray;
import com.jstatcom.model.JSCString;
import com.jstatcom.model.JSCTypeDef;
import com.jstatcom.model.JSCTypes;
import com.jstatcom.ts.TSDateRange;
import com.jstatcom.util.FArg;

/**
 * This GAUSS command object computes the spectrum for each series
 * in the columns of the data object and stores the result in a 
 * symbol table if one is set. 
 *
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public final class SpectrumCall extends RPCall {
	public static final JSCTypeDef SPECTRUM_RESULT =
		new JSCTypeDef(
			"SPECTRUM_RESULT",
			JSCTypes.NARRAY,
			"spectrum frequency~spectrum");

	private JSCNArray data = null;
	private JSCSArray names = null;
	private TSDateRange range = null;
	private int bartWindow = 0;
	private boolean isLogScale = false;
	private boolean isTextOut = false;
	private boolean isMultWindow = false;

	private JSCNArray spectrumRes = null;

	/**
	 * <code>SpectrumCall</code> constructor takes the arguments for the procedure call.
	 *
	 */
	public SpectrumCall(
		JSCNArray data,
		JSCSArray names,
		TSDateRange range,
		int bartWindow,
		boolean isLogScale,
		boolean isTextOut,
		boolean isMultWindow) {

		super();

		setName("Spectrum");

		this.range = range;
		this.data = data;
		this.names = names;
		this.bartWindow = bartWindow;
		this.isLogScale = isLogScale;
		this.isTextOut = isTextOut;
		this.isMultWindow = isMultWindow;

	}

	/**
	 * Writes the text output to a string that can be
	 * referenced via <code>getOutput</code>.
	 */
	protected void finalCode() {

		if (!isTextOut)
			return;

		StringBuffer buffer = output;

		buffer.append("SPECTRUM\n");
		buffer.append(range.format("sample range:", 21) + "\n");
		buffer.append(
			FArg.sprintf(
				"%-20s %i\n",
				new FArg("Bartlett size:").add(bartWindow)));
		buffer.append(
			FArg.sprintf(
				"%-20s %s\n\n",
				new FArg("log scale:").add(isLogScale + "")));
		for (int i = -1; i < names.rows(); i++) {
			if (i == -1)
				buffer.append(FArg.sprintf("%-13s", new FArg("frequence")));
			else
				buffer.append(
					FArg.sprintf("%-12s", new FArg(names.stringAt(i, 0))));
		}
		for (int i = 0; i < spectrumRes.rows(); i++) {
			buffer.append("\n");
			for (int j = 0; j < spectrumRes.cols(); j++) {
				if (j == 0)
					buffer.append(
						FArg.sprintf(
							"%-12.4f",
							new FArg(spectrumRes.doubleAt(i, j))));
				else
					buffer.append(
						FArg.sprintf(
							"%- 12.4f",
							new FArg(spectrumRes.doubleAt(i, j))));
			}
		}
		buffer.append("\n");
	}

	/**
	 * @see gauss.GaussProcCall
	 */
	protected void runCode() {
		if (getSymbolTable() != null) {
			getSymbolTable().get(SPECTRUM_RESULT).clear();
		}

		if (data.rows() < 2)
			throw new RuntimeException("Not enough observations.");

		engine().load("pgraph", GaussLoadTypes.LIB);
		engine().load("tools", GaussLoadTypes.LIB);
		engine().load("plot", GaussLoadTypes.LIB);
		engine().load("fil", GaussLoadTypes.LIB);

		spectrumRes = (JSCNArray) SPECTRUM_RESULT.getInstance();

		engine().call(
			"spectrum_filter",
			new JSCData[] {
				data,
				new JSCNumber("bartWindow", bartWindow),
				new JSCInt("isLog", isLogScale),
				},
			new JSCData[] { spectrumRes });

		if (!isTextOut) {
			// set config data
			setGlobalPgraphSettings();

			engine().call(
				"plotxy_plot",
				new JSCData[] {
					new JSCNArray("x", spectrumRes.getCol(0)),
					new JSCNArray(
						"y",
						spectrumRes.getCols(1, spectrumRes.cols() - 1)),
					new JSCInt("isMultWindow", isMultWindow),
					names,
					new JSCString("tit", "Spectrum")},
				null);

		}

		if (getSymbolTable() != null)
			getSymbolTable().set(spectrumRes);

	}
}