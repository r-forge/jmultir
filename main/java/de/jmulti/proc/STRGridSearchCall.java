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
import com.jstatcom.model.JSCTypeDef;
import com.jstatcom.model.JSCTypes;
import com.jstatcom.ts.TSDateRange;
import com.jstatcom.util.FArg;

/**
 * Handles pre estimation gridsearch for starting values of
 * c1/c2 and gamma in the STR modelling cycle.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public final class STRGridSearchCall extends RPCall {
	public static final JSCTypeDef STR_GRIDSEARCH_RESULT =
		new JSCTypeDef(
			"STR_GRIDSEARCH_RESULT",
			JSCTypes.NARRAY,
			"results of STR grid search: SSR~gamma~c1~(c2)");
	private JSCNArray optGrid = null;
	private JSCNArray Y;
	private JSCNArray X;
	private JSCNArray D;
	private int py;
	private int pz;
	private TSDateRange sampleRange;
	private int selTransIndex;
	private JSCSArray xNames;
	private JSCNArray selConst;
	private JSCSArray constNames;
	private JSCNArray allRes;
	private JSCNArray cInfo;
	private JSCNArray gInfo;
	private boolean isPlot;
	private int graphNumber;
	private int K = 1;

	/**
	 * <code>STRGridSearchCall</code> constructor takes the arguments for the procedure call.
	 */
	public STRGridSearchCall(
		JSCNArray Y,
		JSCNArray X,
		JSCNArray D,
		int py,
		int pz,
		TSDateRange sampleRange,
		int selTransIndex,
		JSCSArray xNames,
		JSCNArray selConst,
		JSCSArray constNames,
		JSCNArray allRes,
		JSCNArray cInfo,
		JSCNArray gInfo,
		boolean isPlot,
		int graphNumber,
		int K) {

		super();

		setName("STR Gridsearch");

		this.Y = Y;
		this.X = X;
		this.D = D;
		this.py = py;
		this.pz = pz;
		this.sampleRange = sampleRange;
		this.selTransIndex = selTransIndex;
		this.xNames = xNames;
		this.selConst = selConst;
		this.constNames = constNames;
		this.allRes = allRes;
		this.cInfo = cInfo;
		this.gInfo = gInfo;
		this.isPlot = isPlot;
		this.graphNumber = graphNumber;
		this.K = K;

	}

	/* 
	 * @see ProcCall#runCode()
	 */
	protected void runCode() {

		if (getSymbolTable() != null)
			getSymbolTable().get(STR_GRIDSEARCH_RESULT).clear();

		engine().load("pgraph", GaussLoadTypes.LIB);
		engine().load("tools", GaussLoadTypes.LIB);
		engine().load("plot", GaussLoadTypes.LIB);
		engine().load("str", GaussLoadTypes.LIB);

		optGrid = (JSCNArray) STR_GRIDSEARCH_RESULT.getInstance();
		JSCNArray allGrid = new JSCNArray("allGrid");

		engine().call(
			"grid_strgrid",
			new JSCData[] {
				Y,
				D,
				X,
				allRes,
				selConst,
				new JSCInt("selTransIndex", selTransIndex + 1),
				cInfo,
				gInfo,
				new JSCNumber("check", 0.02),
				new JSCInt("K", K)},
			new JSCData[] { allGrid, optGrid });

		if (isPlot)
			setGlobalPgraphSettings();

		if (K == 1 && isPlot)
			engine().call(
				"plotm1_strgrid",
				new JSCData[] { allGrid, gInfo, cInfo },
				null);

		if (K == 2 && isPlot)
			engine().call(
				"plotm2_strgrid",
				new JSCData[] {
					allGrid,
					gInfo,
					cInfo,
					new JSCInt("graphNumber", graphNumber)},
				null);

		if (getSymbolTable() != null)
			getSymbolTable().set(optGrid);

	}
	/* 
	 * @see ProcCall#finalCode()
	 */
	protected void finalCode() {
		StringBuffer buffer = output;
		String transName = xNames.stringAt(selTransIndex, 0);

		// AR part
		String variableNames = "CONST";
		for (int i = 0; i < xNames.rows(); i++) {
			if (allRes.doubleAt(0, i) > 0) {
				variableNames = variableNames + " " + xNames.stringAt(i, 0);
			}
		}
		// part with theta = 0, excluded from nonlinear part
		String constNameString = "";
		for (int i = 0; i < selConst.rows(); i++) {
			if (selConst.intAt(i, 0) > 0)
				constNameString =
					constNameString + " " + constNames.stringAt(i, 0);
		}

		double[][] res = optGrid.doubleArray();
		buffer.append("STR GRID SEARCH\n\n");
		buffer.append(
			FArg.sprintf(
				"%-25s %s \n",
				new FArg("variables in AR part:").add(variableNames)));
		buffer.append(
			FArg.sprintf(
				"%-25s %s \n",
				new FArg("restriction theta=0:").add(constNameString.trim())));
		buffer.append(
			FArg.sprintf(
				"%-25s %s \n",
				new FArg("transition variable:").add(transName)));
		buffer.append(
			sampleRange.addPeriodsToStart(Math.max(py, pz)).format(
				"sample range:",
				26)
				+ "\n");

		if (K == 1)
			buffer.append(
				FArg.sprintf(
					"%-25s %s \n",
					new FArg("transition function:").add("LSTR1")));
		else
			buffer.append(
				FArg.sprintf(
					"%-25s %s \n",
					new FArg("transition function:").add("LSTR2")));
		buffer.append(
			FArg.sprintf(
				"%-25s {%- 5.2f,%- 5.2f,%- 3i}\n",
				new FArg("grid c").add(cInfo.doubleAt(0, 0)).add(
					cInfo.doubleAt(1, 0)).add(
					cInfo.doubleAt(2, 0))));
		buffer.append(
			FArg.sprintf(
				"%-25s {%- 5.2f,%- 5.2f,%- 3i}\n\n",
				new FArg("grid gamma").add(gInfo.doubleAt(0, 0)).add(
					gInfo.doubleAt(1, 0)).add(
					gInfo.doubleAt(2, 0))));
		if (K == 1) {
			buffer.append(
				FArg.sprintf(
					"%-13s%-13s%-13s\n",
					new FArg("SSR").add(" gamma").add(" c1")));
			buffer.append(
				FArg.sprintf(
					"%-13.4f%- 13.4f%- 13.4f\n",
					new FArg(res[0][0]).add(res[0][1]).add(res[0][2])));
		} else {
			buffer.append(
				FArg.sprintf(
					"%-13s%-13s%-13s%-13s\n",
					new FArg("SSR").add(" gamma").add(" c1").add(" c2")));
			buffer.append(
				FArg.sprintf(
					"%-13.4f%- 13.4f%- 13.4f%- 13.4f\n",
					new FArg(res[0][0]).add(res[0][1]).add(res[0][2]).add(
						res[0][3])));
		}
		buffer.append("\n");

	}
}
