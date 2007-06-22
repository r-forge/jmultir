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

package de.jmulti.tools;

import com.jstatcom.engine.PCall;
import com.jstatcom.engine.gauss.GaussLoadTypes;
import com.jstatcom.model.JSCDRange;
import com.jstatcom.model.JSCData;
import com.jstatcom.model.JSCInt;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.JSCNumber;
import com.jstatcom.model.JSCSArray;
import com.jstatcom.ts.TS;
import com.jstatcom.ts.TSActionTypes;
import com.jstatcom.ts.TSDate;
import com.jstatcom.ts.TSList;
import com.jstatcom.util.UData;

import de.jmulti.proc.RPCall;

/**
 * This class provides plots carried out by the GAUSS engine
 * for selected time series in a <code>TSList</code>. Due to its
 * dependance of a specific engine implementation, it is not part of
 * the <i>JStatCom</i> framework. The class is designed as a
 * SINGLETON and should can therefore only be references by its
 * <code>TSPLOT_GAUSS</code> instance variable.
 *
 * @see com.jstatcom.ts.TSList
 *
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public final class TSPlotAction extends TSActionTypes {
	/**
	 * SINGLETON instance variable of this action.
	 */
	public static final TSPlotAction TSPLOT_GAUSS = new TSPlotAction("Plot");

	/**
	 * TSPlotAction constructor comment.
	 * @param name String
	 */
	private TSPlotAction(String name) {
		super(name);
	}

	
	/**
	 * Creates time series plot with GAUSS engine.
	 *
	 * @param tsList currently referenced TSList to fetch series from
	 */
	public void doAction(TSList tsList) {

		TS[] sel = tsList.getSelectedTS();

		final int period = sel[0].start().subPeriodicity();

		String[] names = new String[sel.length];
		for (int i = 0; i < names.length; i++)
			names[i] = sel[i].name();

		final JSCSArray namesOfData = new JSCSArray("Names", names);
		JSCData[] all =
			UData.mergeTS(namesOfData, "PlotPanel_AllDat", "earliestStart");
		final JSCNArray allData = (JSCNArray) all[0];
		TSDate start = ((JSCDRange) all[1]).getTSDateRange().lowerBound();
		final double earliestStart = start.doubleValue();

		PCall job = new RPCall() {
			{
				setName("Plot All Selected TS");
			}
			public void runCode() {

				engine().load("pgraph", GaussLoadTypes.LIB);
				engine().load("tools", GaussLoadTypes.LIB);
				engine().load("plot", GaussLoadTypes.LIB);

				// set config data
				setGlobalPgraphSettings();

				engine().call(
					"plotTs_plot",
					new JSCData[] {
						allData,
						new JSCNumber("startDate", earliestStart),
						new JSCInt("period", period),
						namesOfData },
					null);
			}
		};
		job.execute();
	}

}