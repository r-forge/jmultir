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
import com.jstatcom.model.SymbolTable;
import com.jstatcom.ts.TSDateRange;

import de.jmulti.var.VARConstants;


/**
 * Makes the GAUSS call to display the CUSUM test for a VAR model
 * starting from the first possible date.
 *
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public final class VARCusumCall extends RPCall {
	private double signLevel = 0.95;
	private int method = 0;
	private int startPoint;

	/**
	 * VARCusumCall constructor comment.
	 *
	 * @param gst     symbol table to fetch input data from
	 * @param method  0  "CUSUM of Squares"; 1 "CUSUM Test"
	 * @param signLevel    significance level
	 */
	public VARCusumCall(SymbolTable gst, int method, double signLevel) {

		super();

		setSymbolTable(gst);
		setName("VAR CUSUM");

		this.method = method;
		this.signLevel = signLevel;

	}

	/**
	 * @see ProcCall
	 */
	protected void runCode() {

		SymbolTable gst = getSymbolTable();

		engine().load("pgraph", GaussLoadTypes.LIB);
		engine().load("tools", GaussLoadTypes.LIB);
		engine().load("plot", GaussLoadTypes.LIB);
		engine().load("vec", GaussLoadTypes.LIB);
		engine().load("var", GaussLoadTypes.LIB);
		engine().load("stab", GaussLoadTypes.LIB);

		JSCNArray y = gst.get(VARConstants.y_Def).getJSCNArray();
		startPoint =
			gst.get(VARConstants.py_Def).getJSCInt().intVal() * y.cols()
				+ (gst.get(VARConstants.px_Def).getJSCInt().intVal() + 1)
					* gst.get(VARConstants.x_Def).getJSCNArray().cols()
				+ gst.get(VARConstants.d_all_Def).getJSCNArray().cols()
				+ y.cols()
				+ 1;

		TSDateRange estrange =
			gst.get(VARConstants.T1_Def).getJSCDRange().getTSDateRange();

		JSCNArray varBuffer = new JSCNArray("varBuffer");
		VAREstimationCall.setVarBuffer(engine(), gst, varBuffer);

		setGlobalPgraphSettings();

		engine().call(
			"stab_VARcusum_wrapper",
			new JSCData[] {
				varBuffer,
				new JSCInt("startPoint", startPoint),
				new JSCNumber("startDate", estrange.lowerBound().doubleValue()),
				new JSCInt("period", estrange.subPeriodicity()),
				new JSCNumber("signLevel", signLevel),
				new JSCInt("isGraph", true),
				new JSCInt("method", method),
				},
			null);

	}

	protected void finalCode() {
	
		TSDateRange range =
			getSymbolTable()
				.get(VARConstants.T1_raw_Def)
				.getJSCDRange()
				.getTSDateRange();

		int test = range.numOfObs() - startPoint + 1;
		if (test > 202) {
			output.append(
				"Using sample size of 202 for critical values of CUSUM square test instead of (T-start+1)="
					+ test
					+ ".\nCritical values are not availabe for larger samples.");
		}
	}
}