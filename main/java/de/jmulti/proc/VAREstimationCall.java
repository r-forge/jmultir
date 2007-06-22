/*
 This file is part of the econometric software package JMulTi.
 Copyright (C) 2000-2005  Alexander Bankwitz, Markus Kraetzig

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

import com.jstatcom.engine.Engine;
import com.jstatcom.engine.gauss.GaussLoadTypes;
import com.jstatcom.io.FileSupport;
import com.jstatcom.model.JSCData;
import com.jstatcom.model.JSCInt;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.JSCSArray;
import com.jstatcom.model.JSCString;
import com.jstatcom.model.SymbolTable;
import com.jstatcom.ts.TSDateRange;
import com.jstatcom.util.FArg;
import com.jstatcom.util.UData;
import com.jstatcom.util.UString;

import de.jmulti.var.VARConstants;


/**
 * This GAUSS command object carries out a VAR estimation according to the
 * parametrization. The results of this call are stored to the symbol table if
 * one has been set. The variable names defined in <code>VARConstants</code>
 * are used for the arguments of the call as well as for storing the results.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
public final class VAREstimationCall extends RPCall {

	// Input: "y", "x", "d", "py", "px", "Cy", "Cx", "Cd", "Ny", "Nx", "Nd"
	// Output: Ay, Ax, Ad, SD_Ay,SD_Ax,SD_Ad, TV_Ay,TV_Ax,TV_Ad,
	// estimationMethod, u_hat,cv_u_hat

	private JSCNArray cy = null;
	private JSCNArray cx = null;
	private JSCNArray cd = null;

	private JSCNArray y = null;
	private JSCNArray x = null;
	private JSCNArray d = null;

	private JSCSArray Ny = null;
	private JSCSArray Nx = null;
	private JSCSArray Nd = null;

	private int py = 0;
	private int px = 0;

	private JSCNArray Ay = null;
	private JSCNArray SD_Ay = null;
	private JSCNArray TV_Ay = null;

	private JSCNArray Ax = null;
	private JSCNArray SD_Ax = null;
	private JSCNArray TV_Ax = null;

	private JSCNArray Ad = null;
	private JSCNArray SD_Ad = null;
	private JSCNArray TV_Ad = null;

	private JSCString estMethod = null;

	private JSCNArray covar = null;
	private JSCNArray eigv_mod = null;
	private JSCNArray cv_par = null;
	private JSCNArray lr = null;

	private String fileName = "";

	/**
	 * VAREstimationCall constructor comment.
	 */
	public VAREstimationCall(SymbolTable symbolTable, String fileName) {
		super();

		setSymbolTable(symbolTable);

		setName("VAR Estimation");

		// Get the additional parameters from the symbol table
		// according to the names defined in VARConstants.
		cy = symbolTable.get(VARConstants.Cy_Def).getJSCNArray();
		cx = symbolTable.get(VARConstants.Cx_Def).getJSCNArray();
		cd = symbolTable.get(VARConstants.Cd_Def).getJSCNArray();

		y = symbolTable.get(VARConstants.y_Def).getJSCNArray();
		x = symbolTable.get(VARConstants.x_Def).getJSCNArray();
		d = symbolTable.get(VARConstants.d_all_Def).getJSCNArray();

		Ny = symbolTable.get(VARConstants.Ny_Def).getJSCSArray();
		Nx = symbolTable.get(VARConstants.Nx_Def).getJSCSArray();
		Nd = symbolTable.get(VARConstants.Nd_Def).getJSCSArray();

		py = symbolTable.get(VARConstants.py_Def).getJSCInt().intVal();
		px = symbolTable.get(VARConstants.px_Def).getJSCInt().intVal();

		this.fileName = fileName;
	}

	/**
	 * Gets the name of the GAUSS procedure to create a buffer for VAR
	 * estimation.
	 * 
	 * @return procedure call
	 * 
	 * public static String getVARBufferProc() { return "vec_CreateVAR"; }
	 */

	/**
	 * Sets the Gauss Buffer to <code>bufferToSet</code>.
	 * 
	 * @param engine
	 *            the engine to use
	 * @param sTable
	 *            table to get input data from <code>VARConstants</code>
	 * @param bufferToSet
	 *            initialized data object that is set to GAUSS buffer contents
	 */
	public static void setVarBuffer(Engine engine, SymbolTable sTable,
			JSCNArray bufferToSet) {

		int py = sTable.get(VARConstants.py_Def).getJSCInt().intVal();
		int px = sTable.get(VARConstants.px_Def).getJSCInt().intVal();
		engine.call("vec_CreateVAR", VAREstimationCall.getVARBufferArgs(sTable,
				py, px), new JSCData[]{bufferToSet});

	}

	/**
	 * Helper function that creates the GAUSS command to create a VAR estimation
	 * data buffer as defined in the <code>var / vec</code> libraries of
	 * <i>JMulTi </i>. It uses <code>VARConstants</code>.
	 * <p>
	 * <code>
	 * vec_CreateVAR(y,x,d,py,px,Ny,Nx,Nd,Cy,Cx,Cd);
	 * </code>.
	 * 
	 * @param stable
	 *            symbol table with data defined in <code>VARConstants</code>
	 * @param py
	 *            endogenous VAR lags
	 * @param px
	 *            exogenous lags
	 * @return the GAUSS argument array
	 */
	private static JSCData[] getVARBufferArgs(SymbolTable stable, int py, int px) {

		return new JSCData[]{stable.getJSCData(VARConstants.y_Def),
				stable.getJSCData(VARConstants.x_Def),
				stable.getJSCData(VARConstants.d_all_Def),
				new JSCInt("py_arg", py), new JSCInt("px_arg", px),
				stable.getJSCData(VARConstants.Ny_Def),
				stable.getJSCData(VARConstants.Nx_Def),
				stable.getJSCData(VARConstants.Nd_Def),
				stable.getJSCData(VARConstants.Cy_Def),
				stable.getJSCData(VARConstants.Cx_Def),
				stable.getJSCData(VARConstants.Cd_Def),};
	}

	/**
	 * @see RPCall
	 */
	protected void runCode() {

		if (y.rank() < y.cols())
			throw new RuntimeException(
					"Endogenous data matrix has not full column rank.");
		if (x.rows() > 0 && x.rank() < x.cols())
			throw new RuntimeException(
					"Exogenous data matrix has not full column rank.");
		if (d.rows() > 0 && d.rank() < d.cols())
			throw new RuntimeException(
					"Deterministic data matrix has not full column rank.");

		if (getSymbolTable() != null) {
			getSymbolTable().get(VARConstants.Ay_Def).clear();
			getSymbolTable().get(VARConstants.TV_Ay_Def).clear();
			getSymbolTable().get(VARConstants.SD_Ay_Def).clear();
			getSymbolTable().get(VARConstants.Ad_Def).clear();
			getSymbolTable().get(VARConstants.TV_Ad_Def).clear();
			getSymbolTable().get(VARConstants.SD_Ad_Def).clear();
			getSymbolTable().get(VARConstants.Ax_Def).clear();
			getSymbolTable().get(VARConstants.TV_Ax_Def).clear();
			getSymbolTable().get(VARConstants.SD_Ax_Def).clear();
			getSymbolTable().get(VARConstants.est_method_Def).clear();
			getSymbolTable().get(VARConstants.u_hat_Def).clear();
			getSymbolTable().get(VARConstants.cv_u_hat_Def).clear();
			getSymbolTable().get(VARConstants.cv_par_Def).clear();
			getSymbolTable().get(VARConstants.lrtest_Def).clear();
		}

		engine().load("var", GaussLoadTypes.LIB);
		engine().load("vec", GaussLoadTypes.LIB);

		Ay = (JSCNArray) VARConstants.Ay_Def.getInstance();
		TV_Ay = (JSCNArray) VARConstants.TV_Ay_Def.getInstance();
		SD_Ay = (JSCNArray) VARConstants.SD_Ay_Def.getInstance();
		Ad = (JSCNArray) VARConstants.Ad_Def.getInstance();
		TV_Ad = (JSCNArray) VARConstants.TV_Ad_Def.getInstance();
		SD_Ad = (JSCNArray) VARConstants.SD_Ad_Def.getInstance();
		Ax = (JSCNArray) VARConstants.Ax_Def.getInstance();
		TV_Ax = (JSCNArray) VARConstants.TV_Ax_Def.getInstance();
		SD_Ax = (JSCNArray) VARConstants.SD_Ax_Def.getInstance();
		estMethod = (JSCString) VARConstants.est_method_Def.getInstance();
		JSCNArray uHat = (JSCNArray) VARConstants.u_hat_Def.getInstance();
		covar = (JSCNArray) VARConstants.cv_u_hat_Def.getInstance();
		cv_par = (JSCNArray) VARConstants.cv_par_Def.getInstance();
		lr = (JSCNArray) VARConstants.lrtest_Def.getInstance();

		eigv_mod = (JSCNArray) VARConstants.eigv_modulus_Def.getInstance();

		JSCData[] retArgs = new JSCData[]{Ay, Ax, Ad, SD_Ay, SD_Ax, SD_Ad,
				TV_Ay, TV_Ax, TV_Ad, estMethod, uHat, covar, cv_par, lr};

		engine().call(
				"vec_estimate",
				new JSCData[]{
						y,
						x,
						d,
						new JSCInt("py", py),
						new JSCInt("px", px),
						cy,
						cx,
						cd,
						Ny,
						Nx,
						Nd,
						new JSCString("fileout", UString.replaceAllSubStrings(
								fileName.toString(), "\\", "/"))}, retArgs);

		engine().call("var_eigenvalues", new JSCData[]{Ay},
				new JSCData[]{eigv_mod});
    	estMethod.setVal(estMethod.string().trim());

		if (getSymbolTable() != null) {
			getSymbolTable().set(retArgs);
			getSymbolTable().set(eigv_mod);
		}

	}

	protected void finalCode() {
		SymbolTable gst = getSymbolTable();

		StringBuffer buffer = output;

		String end = UData.stringForArray(Ny);
		String ex = UData.stringForArray(Nx);
		String det = UData.stringForArray(Nd);

		buffer.append("VAR ESTIMATION RESULTS\n");

		buffer.append(FArg.sprintf("%-25s %s \n", new FArg(
				"endogenous variables:").add(end)));
		buffer.append(FArg.sprintf("%-25s %s \n", new FArg(
				"exogenous variables:").add(ex)));
		buffer.append(FArg.sprintf("%-25s %s \n", new FArg(
				"deterministic variables:").add(det)));
		buffer.append(FArg.sprintf("%-25s %i \n", new FArg("endogenous lags:")
				.add(py)));
		buffer.append(FArg.sprintf("%-25s %i \n", new FArg("exogenous lags:")
				.add(px)));
		TSDateRange range = gst.get(VARConstants.T1_Def).getJSCDRange()
				.getTSDateRange();

		buffer.append(range.format("sample range:", 26) + "\n");

		if (!eigv_mod.isEmpty()) {
			buffer
					.append("\nmodulus of the eigenvalues of the reverse characteristic polynomial :\n");
			buffer.append("|z| = ( ");
			for (int i = 0; i < eigv_mod.rows(); i++) {
				buffer.append(FArg.sprintf("%-10.4f ", new FArg(eigv_mod
						.doubleAt(i, 0))));

			}
			buffer.append(")\n");
		}
		buffer.append(FileSupport.getInstance().readTextFile(fileName));
		new File(fileName).delete();
	}
}