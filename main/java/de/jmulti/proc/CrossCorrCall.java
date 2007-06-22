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

import com.jstatcom.model.JSCData;
import com.jstatcom.model.JSCInt;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.JSCSArray;
import com.jstatcom.model.JSCTypeDef;
import com.jstatcom.model.JSCTypes;
import com.jstatcom.model.SymbolTable;
import com.jstatcom.util.FArg;
import com.jstatcom.util.UData;
import com.jstatcom.util.UMatrix;

import de.jmulti.tools.ModelTypes;
import de.jmulti.var.VARConstants;
import de.jmulti.vecm.VECMConstants;

/**
 * Makes the GAUSS call to compute the crosscorrelation functions for
 * mulivariate residuals. The results of this call are read back 
 * to the local symbol table set with <code>toStore</code>.
 *
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public final class CrossCorrCall extends RPCall {
	/**
	 * The computed crosscorrelations.
	 */
	public static final JSCTypeDef CROSSCORR_RESULT =
		new JSCTypeDef(
			"CROSSCORR_RESULT",
			JSCTypes.NARRAY,
			"computed crosscorrelations of residuals");

	private SymbolTable toStore = null;
	private ModelTypes modelType;
	private boolean isTextOutput = true;
	private int maxLags;
	private boolean isAsym;
	private boolean isSquared;
	private int[] index0;
	private int[] index1;
	private JSCNArray cc_result = null;

	/**
	 * CrossCorrCall constructor comment.
	 *
	 * @param gst     symbol table to fetch input data from
	 * @param toStore symbol table to store results to
	 */
	public CrossCorrCall(
		SymbolTable gst,
		SymbolTable toStore,
		ModelTypes modelType,
		int maxLags,
		boolean isAym,
		boolean isSquared,
		int[] index0,
		int[] index1,
		boolean isTextOutput) {

		super();

		setSymbolTable(gst);
		setName("Crosscorrelations for Multivariate Residuals");
		this.toStore = toStore;
		this.modelType = modelType;
		this.maxLags = maxLags;
		this.isAsym = isAym;
		this.isSquared = isSquared;
		this.index0 = index0;
		this.index1 = index1;
		this.isTextOutput = isTextOutput;

	}

	protected void finalCode() {

		if (!isTextOutput)
			return;

		StringBuffer buffer = output;

		double ccResult[][] = cc_result.doubleArray();
		if (!isAsym) {
			if (isSquared)
				buffer.append(
					FArg.sprintf(
						"%s\n",
						new FArg("CROSSCORRELATIONS FOR SQUARED RESIDUALS")));
			else
				buffer.append(
					FArg.sprintf("%s\n", new FArg("CROSSCORRELATIONS")));

			buffer.append(
				FArg.sprintf(
					"%s %- 10.4f",
					new FArg("standard error 1/sqrt(T):").add(ccResult[1][2])));
		} else {
			buffer.append(
				"CROSSCORRELATIONS (exact asymptotic standard errors)\n");
			buffer.append(
				"Reference: Lütkepohl (1993), Introduction to Multiple Time Series Analysis, 2ed, p. 147 and 187\n");
		}
		buffer.append(FArg.sprintf("\n%s", new FArg("lags  ")));

		if (isAsym) {
			for (int i = 0; i < ccResult.length; i += 2)
				buffer.append(
					FArg.sprintf(
						"%s%.0f%-8.0f%-11s",
						new FArg("r").add(ccResult[i][0]).add(
							ccResult[i][1]).add(
							"asym std.")));
			buffer.append("\n");
			for (int i = 2; i < maxLags + 2; i++) {
				buffer.append(FArg.sprintf("%-5i", new FArg(i - 1)));
				for (int j = 0; j < ccResult.length; j += 2) {
					buffer.append(
						FArg.sprintf(
							"%- 10.4f %-10.4f",
							new FArg(ccResult[j][i]).add(ccResult[j + 1][i])));
				}
				buffer.append("\n");
			}
		} else {
			for (int i = 0; i < ccResult.length; i += 2)
				buffer.append(
					FArg.sprintf(
						"%s%i%-8i",
						new FArg("r").add(ccResult[i][0]).add(ccResult[i][1])));
			buffer.append("\n");
			for (int i = 2; i < maxLags + 2; i++) {
				buffer.append(FArg.sprintf("%-5i", new FArg(i - 1)));
				for (int j = 0; j < ccResult.length; j += 2) {
					buffer.append(
						FArg.sprintf("%- 10.4f", new FArg(ccResult[j][i])));
				}
				buffer.append("\n");
			}
		}
		buffer.append("\n");
	}

	/**
	 * @see ProcCall
	 */
	protected void runCode() {

		SymbolTable sTable = getSymbolTable();

		if (toStore != null)
			toStore.get(CROSSCORR_RESULT).clear();

		JSCNArray cy = null;
		JSCNArray cd = null;

		JSCNArray Ad = null;
		JSCNArray Ay = null;
		JSCInt pyLags = null;
		JSCInt pxLags = null;
		JSCNArray y = null;
		JSCNArray x = null;
		JSCNArray d = null;
		JSCNArray res = null;
		JSCSArray resNames = null;
		JSCNArray resMat = new JSCNArray("Restrictions");
		JSCNArray ind0 = new JSCNArray("index0", index0);
		JSCNArray ind1 = new JSCNArray("index1", index1);

		if (modelType == ModelTypes.VAR) {
			cy = sTable.get(VARConstants.Cy_Def).getJSCNArray();
			cd = sTable.get(VARConstants.Cd_Def).getJSCNArray();
			Ad = sTable.get(VARConstants.Ad_Def).getJSCNArray();
			Ay = sTable.get(VARConstants.Ay_Def).getJSCNArray();
			pyLags = sTable.get(VARConstants.py_Def).getJSCInt();
			pxLags = sTable.get(VARConstants.px_Def).getJSCInt();
			y = sTable.get(VARConstants.y_raw_Def).getJSCNArray();
			x = sTable.get(VARConstants.x_raw_Def).getJSCNArray();
			d = sTable.get(VARConstants.d_all_Def).getJSCNArray();
			res = sTable.get(VARConstants.u_hat_Def).getJSCNArray();
			resNames = sTable.get(VARConstants.Nu_Def).getJSCSArray();
		} else if (modelType == ModelTypes.VECM) {
			isAsym = false; // Not available for VECMs.
			Ad = sTable.get(VECMConstants.C_var_Def).getJSCNArray();
			Ay = sTable.get(VECMConstants.A_Def).getJSCNArray();
			pyLags = sTable.get(VECMConstants.py_Def).getJSCInt();
			pxLags = sTable.get(VECMConstants.px_Def).getJSCInt();
			y = sTable.get(VECMConstants.y_raw_Def).getJSCNArray();
			x = sTable.get(VECMConstants.x_raw_Def).getJSCNArray();
			d = sTable.get(VECMConstants.d_Def).getJSCNArray();
			res = sTable.get(VECMConstants.u_hat_Def).getJSCNArray();
			resNames = sTable.get(VECMConstants.Nu_Def).getJSCSArray();

		} else
			throw new IllegalArgumentException(
				"Invalid model type " + modelType + ".");
		int py = pyLags.intVal();
		int px = pxLags.intVal();

		if (res.rows() < 2 * maxLags)
			throw new RuntimeException("Not enough observations.");

		boolean subset = false;

		if (isAsym) {
			isSquared = false;
			JSCNArray restr = new JSCNArray("restr");
			restr.appendCols(cy);
			restr.appendCols(cd);
			if (UMatrix.getNonzeroDoubleCount(restr.vec()) > 0)
				subset = true;
			if (subset)
				resMat =
					UData.createResMat(
						cd,
						cy,
						new JSCNArray("empty"),
						resMat.name());

		}
		JSCSArray nam = resNames;
		JSCNArray yz[] =
			UData.createYZMat(d, y, x, py, px, "yMatrix", "zMatrix");
		if (isSquared) {
			res = new JSCNArray(res.name() + "_squared", res.getPow(2));
			nam = UData.appendSuffix(resNames, "^2", resNames.name());
		}

		if (!isTextOutput)
			setGlobalPgraphSettings();

		JSCNArray asymCi = new JSCNArray("AsymCi");
		if (isAsym)
			engine().call(
				"ci_descript",
				new JSCData[] {
					res,
					new JSCInt("maxLags", maxLags),
					yz[1],
					resMat,
					Ay,
					pyLags,
					new JSCInt("dnum", Ad.cols())},
				new JSCData[] { asymCi });

		cc_result = (JSCNArray) CROSSCORR_RESULT.getInstance();

		engine().call(
			"crosscorr_descript",
			new JSCData[] {
				res,
				new JSCInt("maxLags", maxLags),
				new JSCInt("isGraph", !isTextOutput),
				nam,
				ind0,
				ind1,
				asymCi },
			new JSCData[] { cc_result });

		if (toStore != null)
			toStore.set(cc_result);

	}
}