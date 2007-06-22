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
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.JSCString;
import com.jstatcom.model.SymbolTable;

import de.jmulti.vecm.VECMConstants;


/**
 * This GAUSS command object carries out a subset search for VECM coefficients. 
 * The results of this call are stored to the symbol table if one has been set.
 * The variable names defined in <code>VECMConstants</code> are used for the
 * arguments of the call as well as for storing the results.
 *
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public final class VECMSubsetSearchCall extends RPCall {
	private int strategy = 1;
	private int criterion = 1;
	private double threshold = 2;

	/**
	 * VECMSubsetSearchCall.
	 *
	 */
	public VECMSubsetSearchCall(
		SymbolTable symbolTable,
		int strategy,
		int criterion,
		double threshold) {

		super();

		setSymbolTable(symbolTable);

		setName("Subset search for VECM");

		this.strategy = strategy;
		this.criterion = criterion;
		this.threshold = threshold;

	}

	/**
	 * @see RPCall
	 */
	protected void runCode() {

		SymbolTable gst = getSymbolTable();

		/*if (gst != null) {
			gst.get(VECMConstants.S_alpha_Def).clear();
			gst.get(VECMConstants.S_G_Def).clear();
			gst.get(VECMConstants.S_B_Def).clear();
			gst.get(VECMConstants.S_C_VEC_Def).clear();
		}*/

		engine().load("vec", GaussLoadTypes.LIB);
		engine().load("var", GaussLoadTypes.LIB);

		JSCNArray vecmBuffer = new JSCNArray("vecmBuffer");
		VECMEstimationCall.setVecBuffer(
			engine(),
			gst,
			vecmBuffer,
			VECMConstants.TWO_STAGE);

		JSCNArray mode =
			new JSCNArray(
				"mode",
				new double[] { strategy, criterion, threshold });
		engine().call(
			"var_FindSubsetConstraints1",
			new JSCData[] { vecmBuffer, mode },
			new JSCData[] { vecmBuffer });
		
		JSCData S_alpha_Def = VECMConstants.S_alpha_Def.getInstance();
		JSCData S_G_Def = VECMConstants.S_G_Def.getInstance();
		JSCData S_B_Def = VECMConstants.S_B_Def.getInstance();
		JSCData S_C_VEC_Def = VECMConstants.S_C_VEC_Def.getInstance();

		engine().call(
			"vml_vread",
			new JSCData[] { vecmBuffer, new JSCString("infoKey", "S_alpha")},
			new JSCData[] { S_alpha_Def });
		engine().call(
			"vml_vread",
			new JSCData[] { vecmBuffer, new JSCString("infoKey", "S_G")},
			new JSCData[] { S_G_Def });
		engine().call(
			"vml_vread",
			new JSCData[] { vecmBuffer, new JSCString("infoKey", "S_B")},
			new JSCData[] { S_B_Def });
		engine().call(
			"vml_vread",
			new JSCData[] { vecmBuffer, new JSCString("infoKey", "S_C_VEC")},
			new JSCData[] { S_C_VEC_Def });

		if (gst != null) {
			gst.get(VECMConstants.S_alpha_Def).setJSCData(S_alpha_Def);
			gst.get(VECMConstants.S_G_Def).setJSCData(S_G_Def);
			gst.get(VECMConstants.S_B_Def).setJSCData(S_B_Def);
			gst.get(VECMConstants.S_C_VEC_Def).setJSCData(S_C_VEC_Def);
		}

	}
}