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

import java.io.File;

import com.jstatcom.engine.gauss.GaussLoadTypes;
import com.jstatcom.io.FileSupport;
import com.jstatcom.model.JSCData;
import com.jstatcom.model.JSCInt;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.JSCNumber;
import com.jstatcom.model.JSCString;
import com.jstatcom.model.SymbolTable;
import com.jstatcom.util.UString;

import de.jmulti.vecm.SVECConstants;
import de.jmulti.vecm.VECMConstants;


/**
 * This GAUSS command object estimates an SVEC model according to the
 * parametrization. The result of this call is stored to the symbol table with
 * the names defined in <code>SVECConstants</code> if one has been set.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
public final class SVECEstCall extends RPCall {

	private JSCNArray alpha = null;
	private JSCNArray beta = null;
	private JSCNArray beta_d = null;
	private JSCNArray shortRunCoeff = null;
	private JSCNArray mSigmaU = null;

	private int T = 0;
	private JSCNArray b_mat = null;
	private JSCNArray xi_b_mat = null;
	private double eps1_tol = 10E-6;
	private double eps2_tol = 10E-10;
	private double fixStart = 0.1;
	private int maxIterations = 500;
	private int startValueMethod = 1;
	private boolean useDecomp = true;
	private int maxRetries = 10;
	private String fileName = "result.txt";
	private boolean bootstrapStd = false;
	private int bootrep = 100;
	private int seed = 0;

	/**
	 * <code>SVECEstCall</code> constructor takes the arguments for the
	 * procedure call.
	 */
	public SVECEstCall(SymbolTable gst, JSCNArray alpha, JSCNArray beta,
			JSCNArray beta_d, JSCNArray shortRunCoeff, JSCNArray mSigmaU,
			int T, JSCNArray b_mat, JSCNArray xi_b_mat, double eps1_tol,
			double eps2_tol, double fixStart, int maxIterations,
			int startValueMethod, boolean useDecomp, int maxRetries,
			String fileName, boolean bootstrapStd, int bootrep, int seed) {

		// INPUT CHECKING SHOULD BE DONE!
		setName("SVEC Model Estimation");
		setSymbolTable(gst);

		this.alpha = alpha;
		this.beta = beta;
		this.beta_d = beta_d;
		this.shortRunCoeff = shortRunCoeff;
		this.mSigmaU = mSigmaU;
		this.T = T;
		this.b_mat = b_mat;
		this.xi_b_mat = xi_b_mat;
		this.eps1_tol = eps1_tol;
		this.eps2_tol = eps2_tol;
		this.fixStart = fixStart;
		this.maxIterations = maxIterations;
		this.startValueMethod = startValueMethod;
		this.useDecomp = useDecomp;
		this.maxRetries = maxRetries;
		this.fileName = fileName;
		this.bootstrapStd = bootstrapStd;
		this.bootrep = bootrep;
		this.seed = seed;
	}

	/**
	 * Writes the text output to a string that can be referenced via
	 * <code>getOutput</code>.
	 */
	protected void finalCode() {
		output.append(FileSupport.getInstance().readTextFile(fileName));
		new File(fileName).delete();

	}

	/**
	 * @see RPCall
	 */
	protected void runCode() {

		SymbolTable sTable = getSymbolTable();

		if (sTable != null) {
			sTable.get(SVECConstants.matXi_B_Def).clear();
			sTable.get(SVECConstants.matB_Def).clear();
			sTable.get(SVECConstants.resBC_Def).clear();
			sTable.get(SVECConstants.normConst_Def).clear();
			sTable.get(SVECConstants.startValVector_Def).clear();
			sTable.get(SVECConstants.modelType_Def).clear();
			sTable.get(SVECConstants.std_B_Def).clear();
			sTable.get(SVECConstants.std_CB_Def).clear();
			sTable.get(SVECConstants.tv_B_Def).clear();
			sTable.get(SVECConstants.string_tv_CB).clear();
		}

		engine().load("var", GaussLoadTypes.LIB);
		engine().load("vec", GaussLoadTypes.LIB);

		JSCNArray vecBuffer = new JSCNArray("vecmBuffer");

		// prepare buffer for bootstrap

		if (bootstrapStd) {
			int estStrat = sTable.get(VECMConstants.estimationStrategy_Def)
					.getJSCInt().intVal();

			VECMEstimationCall.setVecBuffer(engine(), sTable, vecBuffer,
					estStrat);

		}
		JSCData matXi_B_Def = SVECConstants.matXi_B_Def.getInstance();
		JSCData matB_Def = SVECConstants.matB_Def.getInstance();
		JSCData resBC_Def = SVECConstants.resBC_Def.getInstance();
		JSCData normConst_Def = SVECConstants.normConst_Def.getInstance();
		JSCData startValVector_Def = SVECConstants.startValVector_Def
				.getInstance();
		JSCData modelType_Def = SVECConstants.modelType_Def.getInstance();
		JSCData std_B_Def = SVECConstants.std_B_Def.getInstance();
		JSCData std_CB_Def = SVECConstants.std_CB_Def.getInstance();
		JSCData tv_B_Def = SVECConstants.tv_B_Def.getInstance();
		JSCData string_tv_CB = SVECConstants.string_tv_CB.getInstance();

		JSCData[] retArgs = new JSCData[]{matXi_B_Def, matB_Def, resBC_Def,
				normConst_Def, startValVector_Def, modelType_Def, std_B_Def,
				std_CB_Def, tv_B_Def, string_tv_CB};

		engine().call(
				"svec_wrapper_var",
				new JSCData[]{
						alpha,
						beta,
						beta_d,
						shortRunCoeff,
						mSigmaU,
						new JSCInt("T", T),
						b_mat,
						xi_b_mat,
						new JSCNumber("relParamCh", eps1_tol),
						new JSCNumber("relLikCh", eps2_tol),
						new JSCInt("maxIterations", maxIterations),
						new JSCInt("startValueMethod", startValueMethod),
						new JSCNumber("fixStart", fixStart),
						new JSCNumber("vStartUser", 0),
						new JSCInt("useDecomp", useDecomp),
						new JSCInt("maxRetries", maxRetries),
						new JSCString("outFileName", UString
								.replaceAllSubStrings(fileName, "\\", "/")),
						new JSCInt("bootstrapStd", bootstrapStd),
						new JSCInt("bootrep", bootrep),
						new JSCInt("seed", seed), vecBuffer}, retArgs);

		if (sTable != null) {
			sTable.set(retArgs);

			sTable.get(SVECConstants.relParamCh_Def).setJSCData(
					new JSCNumber("toSet", eps1_tol));
			sTable.get(SVECConstants.relLikCh_Def).setJSCData(
					new JSCNumber("toSet", eps2_tol));
			sTable.get(SVECConstants.maxIter_Def).setJSCData(
					new JSCInt("toSet", maxIterations));

		}

	}
}