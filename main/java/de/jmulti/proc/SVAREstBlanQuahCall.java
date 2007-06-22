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
import com.jstatcom.model.JSCString;
import com.jstatcom.model.SymbolTable;
import com.jstatcom.util.UString;

import de.jmulti.var.SVARConstants;

/**
 * This GAUSS command object estimates an SVAR Blanchard-Quah model according to the 
 * parametrization. The result of this call is stored to the symbol table
 * with the names defined in <code>SVARConstants</code> if one has been set.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public final class SVAREstBlanQuahCall extends RPCall {
	private JSCNArray Ay = null;
	private JSCNArray mSigmaU = null;
	private String fName = "result.txt";

	/**
	 * SVAREstBlanQuahCall constructor with parameters needed for call.
	 */
	public SVAREstBlanQuahCall(JSCNArray Ay, JSCNArray mSigmaU, String fName) {
		super();

		setName("SVAR Blanchard-Quah Estimation");

		this.Ay = Ay;
		this.mSigmaU = mSigmaU;
		this.fName = fName;

	}

	/**
	 * Writes the text output to a string that can be
	 * referenced via <code>getOutput</code>.
	 */
	protected void finalCode() {
		output.append(FileSupport.getInstance().readTextFile(fName));
		new File(fName).delete();

	}

	/**
	 * This method executes the main task of the 
	 * Gauss computation thread.
	 */
	protected void runCode() {

		// {minvAB,mA,mB,mS,vs,vg_bs,model}
		SymbolTable sTable = getSymbolTable();

		if (sTable != null) {
			sTable.get(SVARConstants.svar_A0_Def).clear();
			sTable.get(SVARConstants.bqLongREffects_Def).clear();
		}

		engine().load("var", GaussLoadTypes.LIB);

		JSCData svar_A0_Def = SVARConstants.svar_A0_Def.getInstance();
		JSCData bqLongREffects_Def =
			SVARConstants.bqLongREffects_Def.getInstance();

		JSCData[] retArgs = new JSCData[] { svar_A0_Def, bqLongREffects_Def, };

		engine().call(
			"GetmA0_svar_var",
			new JSCData[] { Ay, mSigmaU },
			retArgs);

		//proc(0) = OutputBQresults_svar_var(mA0, mPhiOne, mSE_mA0, mSE_mPhiOne, mt_mA0, mt_mPhiOne, BS_ind, fname);
		engine().call(
			"OutputBQresults_svar_var",
			new JSCData[] {
				svar_A0_Def,
				bqLongREffects_Def,
				new JSCInt("empty_se"),
				new JSCInt("empty_se"),
				new JSCInt("empty_se"),
				new JSCInt("empty_se"),
				new JSCInt("isBootstrapSe", false),
				new JSCString(
					"outFileName",
					UString.replaceAllSubStrings(fName, "\\", "/"))},
			null);

		if (sTable != null) {
			sTable.get(SVARConstants.lastModel_Def).setJSCData(
				new JSCString("toSet", SVARConstants.BQ_Model));
			sTable.set(retArgs);
		}
	}
}