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
import com.jstatcom.ts.TSDate;
import com.jstatcom.util.UData;

/**
 * This GAUSS command object plots the given series according to
 * the parameters.
 *
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public final class PlotTSCall extends RPCall {
	private JSCNArray data = null;
	private TSDate startDate = null;
	private JSCSArray names = null;
	private boolean isMultWindow = false;
	private boolean isStd = false;

	/**
	 * <code>PlotTSCall</code> constructor takes the arguments for the procedure call.
	 */
	public PlotTSCall(
		JSCNArray data,
		TSDate startDate,
		JSCSArray names,
		boolean isMultWindow,
		boolean isStd) {

		super();

		setName("Plotting Series");

		this.data = data;
		this.startDate = startDate;
		this.names = names;
		this.isMultWindow = isMultWindow;
		this.isStd = isStd;
	}

	/**
	 * Writes the text output to a string that can be
	 * referenced via <code>getOutput</code>.
	 */
	protected void finalCode() {

	}

	/**
	 * @see PCall
	 */
	protected void runCode() {

		if (data.rows() < 2)
			throw new RuntimeException("Not enough observations.");

		if (isStd)
			data = UData.standardize(data, "stdPlotData");

		engine().load("pgraph", GaussLoadTypes.LIB);
		engine().load("tools", GaussLoadTypes.LIB);
		engine().load("plot", GaussLoadTypes.LIB);

		// set config data
		setGlobalPgraphSettings();

		String procName = "PlotTS_plot";
		if (isMultWindow && data.cols() > 1)
			procName = "plotWindows_plot";

		engine().call(
			procName,
			new JSCData[] {
				data,
				new JSCNumber("start", startDate.doubleValue()),
				new JSCInt("period", startDate.subPeriodicity()),
				names },
			null);

	}
}