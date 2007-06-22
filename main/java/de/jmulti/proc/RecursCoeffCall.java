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
import com.jstatcom.model.JSCTypeDef;
import com.jstatcom.model.JSCTypes;
import com.jstatcom.model.SymbolTable;
import com.jstatcom.ts.TSDateRange;

import de.jmulti.tools.ModelTypes;
import de.jmulti.var.VARConstants;
import de.jmulti.vecm.VECMConstants;
/**
 * Makes the GAUSS call to compute the recursive coefficients for either
 * VAR or VEC models. The results of this call are read back to the 
 * local symbol table set with <code>toStore</code>.
 *
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public final class RecursCoeffCall extends RPCall {
	public static final JSCTypeDef RECURS_COEFF_G0 =
		new JSCTypeDef(
			"RECURS_COEFF_G0",
			JSCTypes.NARRAY,
			"recursively estimated structural coefficients (VEC)");
	public static final JSCTypeDef RECURS_COEFF_G0_SE =
		new JSCTypeDef(
			"RECURS_COEFF_G0_SE",
			JSCTypes.NARRAY,
			"recursively estimated std errors of structural coefficients (VEC)");
	public static final JSCTypeDef RECURS_COEFF_G =
		new JSCTypeDef(
			"RECURS_COEFF_G",
			JSCTypes.NARRAY,
			"recursively estimated endogenous coefficients");
	public static final JSCTypeDef RECURS_COEFF_G_SE =
		new JSCTypeDef(
			"RECURS_COEFF_G_SE",
			JSCTypes.NARRAY,
			"recursively estimated std errors of endogenous coefficients");
	public static final JSCTypeDef RECURS_COEFF_B =
		new JSCTypeDef(
			"RECURS_COEFF_B",
			JSCTypes.NARRAY,
			"recursively estimated exogenous coefficients");
	public static final JSCTypeDef RECURS_COEFF_B_SE =
		new JSCTypeDef(
			"RECURS_COEFF_B_SE",
			JSCTypes.NARRAY,
			"recursively estimated std errors of exogenous coefficients");
	public static final JSCTypeDef RECURS_COEFF_C =
		new JSCTypeDef(
			"RECURS_COEFF_C",
			JSCTypes.NARRAY,
			"recursively estimated deterministic coefficients");
	public static final JSCTypeDef RECURS_COEFF_C_SE =
		new JSCTypeDef(
			"RECURS_COEFF_C_SE",
			JSCTypes.NARRAY,
			"recursively estimated std errors of deterministic coefficients");
	public static final JSCTypeDef RECURS_COEFF_ALPHA =
		new JSCTypeDef(
			"RECURS_COEFF_ALPHA",
			JSCTypes.NARRAY,
			"recursively estimated alpha coefficients (VEC)");
	public static final JSCTypeDef RECURS_COEFF_ALPHA_SE =
		new JSCTypeDef(
			"RECURS_COEFF_ALPHA_SE",
			JSCTypes.NARRAY,
			"recursively estimated std errors of alpha coefficients (VEC)");

	private ModelTypes modelType;
	private int startIndex;
	private boolean splitWindow;
	private SymbolTable toStore;
	/**
	 * ChowTestCall constructor comment.
	 *
	 * @param gst     symbol table to fetch input data from
	 * @param toStore symbol table to store results to
	 */
	public RecursCoeffCall(
		SymbolTable gst,
		SymbolTable toStore,
		ModelTypes modelType,
		int startIndex,
		boolean splitWindow) {

		super();

		setSymbolTable(gst);
		setName("Recursive Coefficients for " + modelType);
		this.modelType = modelType;
		this.startIndex = startIndex;
		this.splitWindow = splitWindow;
		this.toStore = toStore;

	}

	/**
	 * @see ProcCall
	 */
	protected void runCode() {

		SymbolTable gst = getSymbolTable();

		engine().load("var", GaussLoadTypes.LIB);
		engine().load("vec", GaussLoadTypes.LIB);
		engine().load("stab", GaussLoadTypes.LIB);
		engine().load("pgraph", GaussLoadTypes.LIB);
		engine().load("tools", GaussLoadTypes.LIB);
		engine().load("plot", GaussLoadTypes.LIB);

		setGlobalPgraphSettings();

		JSCNArray varBuffer = new JSCNArray("varBuffer");

		if (modelType == ModelTypes.VAR) {

			if (toStore != null) {
				toStore.get(RECURS_COEFF_C_SE).clear();
				toStore.get(RECURS_COEFF_C).clear();
				toStore.get(RECURS_COEFF_B_SE).clear();
				toStore.get(RECURS_COEFF_B).clear();
				toStore.get(RECURS_COEFF_G_SE).clear();
				toStore.get(RECURS_COEFF_G).clear();
			}

			VAREstimationCall.setVarBuffer(engine(), gst, varBuffer);

			JSCData[] retArgs =
				new JSCData[] {
					RECURS_COEFF_G.getInstance(),
					RECURS_COEFF_G_SE.getInstance(),
					RECURS_COEFF_B.getInstance(),
					RECURS_COEFF_B_SE.getInstance(),
					RECURS_COEFF_C.getInstance(),
					RECURS_COEFF_C_SE.getInstance()};

			TSDateRange range =
				gst.get(VARConstants.T1_Def).getJSCDRange().getTSDateRange();

			engine().call(
				"stab_VARrecurs_wrapper",
				new JSCData[] {
					varBuffer,
					gst.getJSCData(VARConstants.cy_recCoeff_Def),
					gst.getJSCData(VARConstants.cx_recCoeff_Def),
					gst.getJSCData(VARConstants.cd_recCoeff_Def),
					new JSCInt("startIndex", (startIndex + 1)),
					new JSCInt("period", range.subPeriodicity()),
					new JSCNumber(
						"startDate",
						range.lowerBound().doubleValue()),
					new JSCInt("splitWindow", splitWindow),
					new JSCInt("isGraph", true)},
				retArgs);

			if (toStore != null)
				toStore.set(retArgs);

		} else if (modelType == ModelTypes.VECM) {
			if (toStore != null) {
				toStore.get(RECURS_COEFF_ALPHA).clear();
				toStore.get(RECURS_COEFF_ALPHA_SE).clear();
				toStore.get(RECURS_COEFF_C_SE).clear();
				toStore.get(RECURS_COEFF_C).clear();
				toStore.get(RECURS_COEFF_B_SE).clear();
				toStore.get(RECURS_COEFF_B).clear();
				toStore.get(RECURS_COEFF_G_SE).clear();
				toStore.get(RECURS_COEFF_G).clear();
				toStore.get(RECURS_COEFF_G0_SE).clear();
				toStore.get(RECURS_COEFF_G0).clear();
			}
			int estStrat =
				gst
					.get(VECMConstants.estimationStrategy_Def)
					.getJSCInt()
					.intVal();

			VECMEstimationCall.setVecBuffer(engine(), gst, varBuffer, estStrat);
			
			JSCData[] retArgs =
				new JSCData[] {
					RECURS_COEFF_G0.getInstance(),
					RECURS_COEFF_G0_SE.getInstance(),
					RECURS_COEFF_G.getInstance(),
					RECURS_COEFF_G_SE.getInstance(),
					RECURS_COEFF_B.getInstance(),
					RECURS_COEFF_B_SE.getInstance(),
					RECURS_COEFF_C.getInstance(),
					RECURS_COEFF_C_SE.getInstance(),
					RECURS_COEFF_ALPHA.getInstance(),
					RECURS_COEFF_ALPHA_SE.getInstance()};

			TSDateRange range =
				gst.get(VECMConstants.T1_Def).getJSCDRange().getTSDateRange();

			engine().call(
				"stab_VECMrecurs_wrapper",
				new JSCData[] {
					varBuffer,
					gst.getJSCData(VECMConstants.cy0_recCoeff_Def),
					gst.getJSCData(VECMConstants.cy_recCoeff_Def),
					gst.getJSCData(VECMConstants.cx_recCoeff_Def),
					gst.getJSCData(VECMConstants.cd_recCoeff_Def),
					gst.getJSCData(VECMConstants.alpha_recCoeff_Def),
					new JSCInt("startIndex", (startIndex + 1)),
					new JSCInt("period", range.subPeriodicity()),
					new JSCNumber(
						"startDate",
						range.lowerBound().doubleValue()),
					new JSCInt("splitWindow", splitWindow),
					new JSCInt("isGraph", true)},
				retArgs);

			if (toStore != null)
				toStore.set(retArgs);

		} else
			throw new IllegalStateException(
				"Unknown modeltype " + modelType + ".");

	}
}