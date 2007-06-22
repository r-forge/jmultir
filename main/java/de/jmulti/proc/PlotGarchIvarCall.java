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
import com.jstatcom.ts.TSDate;

/**
 * This GAUSS command object plots the variance process for
 * a GARCH model.
 *
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public final class PlotGarchIvarCall extends RPCall {
	private JSCNArray ivar = null;
	private JSCNArray origData = null;
	private TSDate startDate = null;
	private boolean isUnivar = false;

	/**
	 * <code>PlotGarchIvarCall</code> constructor takes the arguments for the procedure call.
	 */
	public PlotGarchIvarCall(
		JSCNArray ivar,
		JSCNArray origData,
		TSDate startDate,
		boolean isUnivar) {

		super();

		setName("Plotting GARCH Variance Process");

		this.ivar = ivar;
		this.startDate = startDate;
		this.origData = origData;
		this.isUnivar = isUnivar;

	}

	/**
	 * Writes the text output to a string that can be
	 * referenced via <code>getOutput</code>.
	 */
	protected void finalCode() {

	}

	/**
	 * @see RPCall
	 */
	protected void runCode() {

		if (ivar.rows() < 2)
			throw new RuntimeException("Not enough observations");

		engine().load("pgraph", GaussLoadTypes.LIB);
		engine().load("tools", GaussLoadTypes.LIB);
		engine().load("plot", GaussLoadTypes.LIB);
		engine().load("arch", GaussLoadTypes.LIB);

		setGlobalPgraphSettings();

		engine().call(
			"plot_istddev_arch",
			new JSCData[] {
				ivar,
				origData,
				new JSCInt("isPlotUnivar", isUnivar),
				new JSCNumber("startDate", startDate.doubleValue()),
				new JSCInt("period", startDate.subPeriodicity())},
			null);

	}
}