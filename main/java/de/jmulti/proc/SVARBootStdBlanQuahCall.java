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
import com.jstatcom.io.FileSupport;
import com.jstatcom.model.JSCData;
import com.jstatcom.model.JSCInt;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.JSCString;
import com.jstatcom.model.SymbolTable;
import com.jstatcom.util.UString;

import de.jmulti.var.SVARConstants;

/**
 * This GAUSS command object estimates and bootstrappes the standard errors of an 
 * SVAR Blanchard-Quah model according to the parametrization. 
 * The result of this call is stored to the symbol table
 * with the names defined in <code>SVARConstants</code> if one has been set.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public final class SVARBootStdBlanQuahCall extends RPCall {
	private int numBootRep = 100;
	private int seed = 0;
	private boolean isSeed = false;
	private String fName;

	/**
	 * SVAREstBlanQuahCall constructor with parameters needed for call.
	 */
	public SVARBootStdBlanQuahCall(
		SymbolTable symbolTable,
		int numBootRep,
		int seed,
		boolean isSeed,
		String fName) {
		super();

		setSymbolTable(symbolTable);

		setName("SVAR Blanchard-Quah Bootstrapped Estimation");

		this.numBootRep = numBootRep;
		this.seed = seed;
		this.isSeed = isSeed;
		this.fName = fName;

	}

	/**
	 * Writes the text output to a string that can be
	 * referenced via <code>getOutput</code>.
	 */
	protected void finalCode() {
		output.append(FileSupport.getInstance().readTextFile(fName));
	}

	/**
	 * This method executes the main task of the 
	 * Gauss computation thread.
	 */
	protected void runCode() {

		seed = isSeed ? seed : 0;
		SymbolTable gst = getSymbolTable();

		if (gst != null) {
			gst.get(SVARConstants.svar_A0_Def).clear();
			gst.get(SVARConstants.bqLongREffects_Def).clear();
			gst.get(SVARConstants.bqStdImpact_Def).clear();
			gst.get(SVARConstants.bqStdLongREffects_Def).clear();
			gst.get(SVARConstants.bqTVImpact_Def).clear();
			gst.get(SVARConstants.bqTVLongREffects_Def).clear();
		}

		JSCNArray varBuffer = new JSCNArray("varBuffer");
		VAREstimationCall.setVarBuffer(engine(), gst, varBuffer);

		// {mA0, mPhiOne, m_se_mA0,m_se_mPhiOne,m_tv_mA0,m_tv_mPhiOne}= 
		// GetBootStdErr_svar_var(var,bootRep,seed);

		engine().load("var", GaussLoadTypes.LIB);

		JSCData svar_A0_Def = SVARConstants.svar_A0_Def.getInstance();
		JSCData bqLongREffects_Def =
			SVARConstants.bqLongREffects_Def.getInstance();
		JSCData bqStdImpact_Def = SVARConstants.bqStdImpact_Def.getInstance();
		JSCData bqStdLongREffects_Def =
			SVARConstants.bqStdLongREffects_Def.getInstance();
		JSCData bqTVImpact_Def = SVARConstants.bqTVImpact_Def.getInstance();
		JSCData bqTVLongREffects_Def =
			SVARConstants.bqTVLongREffects_Def.getInstance();

		JSCData[] retArgs =
			new JSCData[] {
				svar_A0_Def,
				bqLongREffects_Def,
				bqStdImpact_Def,
				bqStdLongREffects_Def,
				bqTVImpact_Def,
				bqTVLongREffects_Def };

		engine().call(
			"GetBootStdErr_svar_var",
			new JSCData[] {
				varBuffer,
				new JSCInt("numBootRep", numBootRep),
				new JSCInt("seed", seed)},
			retArgs);

		//proc(0) = OutputBQresults_svar_var(mA0, mPhiOne, mSE_mA0, mSE_mPhiOne, mt_mA0, mt_mPhiOne, BS_ind, fname);
		engine().call(
			"OutputBQresults_svar_var",
			new JSCData[] {
				svar_A0_Def,
				bqLongREffects_Def,
				bqStdImpact_Def,
				bqStdLongREffects_Def,
				bqTVImpact_Def,
				bqTVLongREffects_Def,
				new JSCInt("isBootResults", true),
				new JSCString(
					"outFileName",
					UString.replaceAllSubStrings(fName, "\\", "/"))},
			null);

		if (gst != null) {
			gst.get(SVARConstants.lastModel_Def).setJSCData(
				new JSCString("toSet", SVARConstants.BQ_Model));
			getSymbolTable().set(retArgs);
		}
	}
}