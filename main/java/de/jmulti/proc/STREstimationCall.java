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
import com.jstatcom.model.JSCSArray;
import com.jstatcom.ts.TSDateRange;
import com.jstatcom.util.FArg;

import de.jmulti.str.STR_Constants;


/**
 * Handles estimation of an STR model. The results are stored under
 * the respective definitions in STR_CONSTANTS.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public final class STREstimationCall extends RPCall {
	private JSCNArray[] result;
	private JSCNArray Y;
	private JSCNArray X;
	private JSCNArray D;
	private int py;
	private int pz;
	private TSDateRange sampleRange;
	private int selTransIndex;
	private JSCSArray xNames;
	private JSCNArray selConst;
	private JSCNArray selPhi0;
	private JSCNArray selThetMinPhi;
	private JSCSArray constNames;
	private JSCNArray allRes;
	private JSCNArray startGammC1C2;
	private int K = 1;
	private int loops = 0;

	/**
	 * <code>STREstimationCall</code> constructor takes the arguments for the procedure call.
	 */
	public STREstimationCall(
		JSCNArray Y,
		JSCNArray X,
		JSCNArray D,
		int py,
		int pz,
		TSDateRange sampleRange,
		int selTransIndex,
		JSCSArray xNames,
		JSCNArray selConst,
		JSCNArray selPhi0,
		JSCNArray selThetMinPhi,
		JSCSArray constNames,
		JSCNArray allRes,
		JSCNArray startGammC1C2,
		int loops,
		int K) {

		super();

		setName("STR Estimation");

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
		this.K = K;
		this.selPhi0 = selPhi0;
		this.selThetMinPhi = selThetMinPhi;
		this.startGammC1C2 = startGammC1C2;
		this.loops = loops;

	}

	/* 
	 * @see ProcCall#runCode()
	 */
	protected void runCode() {
		if (getSymbolTable() != null) {
			getSymbolTable().get(STR_Constants.STR_EST_CHECKS).clear();
			getSymbolTable().get(STR_Constants.STR_EST).clear();
			getSymbolTable().get(STR_Constants.STR_RTZ).clear();
			getSymbolTable().get(STR_Constants.STR_LINPART).clear();
			getSymbolTable().get(STR_Constants.STR_NONLINPART).clear();
			getSymbolTable().get(STR_Constants.STR_FITTED).clear();
		}

		engine().load("dlib/str", GaussLoadTypes.SYS);

		engine().load("pgraph", GaussLoadTypes.LIB);
		engine().load("tools", GaussLoadTypes.LIB);
		engine().load("plot", GaussLoadTypes.LIB);
		engine().load("str", GaussLoadTypes.LIB);

		result =
			new JSCNArray[] {
				(JSCNArray) STR_Constants.STR_EST_CHECKS.getInstance(),
				(JSCNArray) STR_Constants.STR_EST.getInstance(),
				(JSCNArray) STR_Constants.STR_RTZ.getInstance(),
				(JSCNArray) STR_Constants.STR_LINPART.getInstance(),
				(JSCNArray) STR_Constants.STR_NONLINPART.getInstance(),
				(JSCNArray) STR_Constants.STR_FITTED.getInstance()};

		engine().call(
			"strest_strest",
			new JSCData[] {
				Y,
				D,
				X,
				allRes,
				new JSCInt("selTransIndex", selTransIndex + 1),
				selConst,
				startGammC1C2,
				selThetMinPhi,
				selPhi0,
				new JSCInt("K", K),
				new JSCInt("maxloops", loops)},
			result);

		if (getSymbolTable() != null)
			getSymbolTable().set(result);

	}
	/* 
	 * @see ProcCall#finalCode()
	 */
	protected void finalCode() {
		double[][] checks = result[0].doubleArray();
		double[][] estimation = result[1].doubleArray();

		StringBuffer buffer = output;
		String transName = xNames.stringAt(selTransIndex, 0);

		// AR part
		String variableNames = "CONST";
		for (int i = 0; i < xNames.rows(); i++) {
			if (allRes.doubleAt(0, i) > 0) {
				variableNames = variableNames + " " + xNames.stringAt(i, 0);
			}
		}

		JSCSArray allNames = new JSCSArray("nonlinNames");

		int linNumber = 0;
		// all names go in linear part, except phi == 0
		String phiNames = "";
		for (int i = 0; i < selPhi0.rows(); i++) {
			if (selPhi0.intAt(i, 0) == 1)
				phiNames = phiNames + " " + constNames.stringAt(i, 0);
			else {
				linNumber++;
				allNames.appendRows(
					new JSCSArray("append", constNames.stringAt(i, 0)));
			}
		}

		// all names go in nonlinear part, except theta == 0
		String constNameString = "";
		for (int i = 0; i < selConst.rows(); i++) {
			if (selConst.intAt(i, 0) > 0)
				constNameString =
					constNameString + " " + constNames.stringAt(i, 0);
			else
				allNames.appendRows(
					new JSCSArray("append", constNames.stringAt(i, 0)));

		}

		allNames.appendRows(new JSCSArray("append", "Gamma"));
		allNames.appendRows(new JSCSArray("append", "C1"));
		allNames.appendRows(new JSCSArray("append", "C2"));

		String phiThetNames = "";
		for (int i = 0; i < selThetMinPhi.rows(); i++) {
			if (selThetMinPhi.intAt(i, 0) == 1)
				phiThetNames = phiThetNames + " " + constNames.stringAt(i, 0);
		}

		buffer.append("STR ESTIMATION\n\n");
		buffer.append(
			FArg.sprintf(
				"%-25s %s \n",
				new FArg("variables in AR part:").add(variableNames.trim())));
		buffer.append(
			FArg.sprintf(
				"%-25s %s \n",
				new FArg("restriction theta=0:").add(constNameString.trim())));
		buffer.append(
			FArg.sprintf(
				"%-25s %s \n",
				new FArg("restriction phi=0:").add(phiNames.trim())));
		buffer.append(
			FArg.sprintf(
				"%-25s %s \n",
				new FArg("restriction phi=-theta:").add(phiThetNames.trim())));
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
				"%-25s%- i\n\n",
				new FArg("number of iterations:").add(checks[0][0])));
		buffer.append(
			FArg.sprintf(
				"%-25s%-13s%-13s%-13s%-13s%-13s\n",
				new FArg("variable")
					.add(" start")
					.add(" estimate")
					.add(" SD")
					.add(" t-stat")
					.add(" p-value")));

		for (int i = 0; i < estimation.length; i++) {
			if (i == 0)
				buffer.append("----- linear part ------\n");
			if (i == linNumber)
				buffer.append("---- nonlinear part ----\n");
			
			// set t-stats and p-vals for trans funktion params to NaN
			for (int j = 0; j < K + 1; j++) {
			    estimation[estimation.length -1 - j][3] = Double.NaN;
			    estimation[estimation.length -1 - j][4] = Double.NaN;
			}
			   
            
			
			buffer.append(
				FArg.sprintf(
					"%-25s%- 13.5f%- 13.5f%- 13.4f%- 13.4f%- 13.4f\n",
					new FArg(allNames.stringAt(i, 0))
						.add(estimation[i][0])
						.add(estimation[i][1])
						.add(estimation[i][2])
						.add(estimation[i][3])
						.add(estimation[i][4])));
		}
		buffer.append("\n");
		buffer.append(
			FArg.sprintf("%-33s%- .4e\n", new FArg("AIC:").add(checks[0][1])));
		buffer.append(
			FArg.sprintf("%-33s%- .4e\n", new FArg("SC:").add(checks[0][2])));
		buffer.append(
			FArg.sprintf("%-33s%- .4e\n", new FArg("HQ:").add(checks[0][3])));
		buffer.append(
			FArg.sprintf("%-33s%- .4e\n", new FArg("R2:").add(checks[0][4])));
		buffer.append(
			FArg.sprintf(
				"%-33s%- .4f\n",
				new FArg("adjusted R2:").add(checks[0][5])));
		buffer.append(
			FArg.sprintf(
				"%-33s%- .4f\n",
				new FArg("variance of transition variable:").add(
					checks[0][6])));
		buffer.append(
			FArg.sprintf(
				"%-33s%- .4f\n",
				new FArg("SD of transition variable:").add(checks[0][7])));
		buffer.append(
			FArg.sprintf(
				"%-33s%- .4f\n",
				new FArg("variance of residuals:").add(checks[0][8])));
		buffer.append(
			FArg.sprintf(
				"%-33s%- .4f\n",
				new FArg("SD of residuals:").add(checks[0][9])));

		buffer.append("\n");

	}
}
