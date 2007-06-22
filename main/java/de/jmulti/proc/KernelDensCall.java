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
import com.jstatcom.model.JSCString;
import com.jstatcom.model.JSCTypeDef;
import com.jstatcom.model.JSCTypes;
import com.jstatcom.ts.TSDateRange;
import com.jstatcom.util.FArg;
import com.jstatcom.util.UData;
import com.jstatcom.util.UMatrix;

/**
 * This GAUSS command object computes the kernel density function 
 * for each series in the columns of the data object and stores the result in a 
 * symbol table if one is set. 
 *
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public final class KernelDensCall extends RPCall {
	public static final JSCTypeDef KERNEL_DENSITY =
		new JSCTypeDef(
			"KERNEL_DENSITY",
			JSCTypes.NARRAY,
			"estimated kernel density function");
	public static final JSCTypeDef KERNEL_DERIVATIVE =
		new JSCTypeDef(
			"KERNEL_DERIVATIVE",
			JSCTypes.NARRAY,
			"estimated 1st derivative of kernel density function");
	public static final JSCTypeDef KERNEL_X =
		new JSCTypeDef(
			"KERNEL_X",
			JSCTypes.NARRAY,
			"grid on which kernel density function is estimated");
	public static final JSCTypeDef KERNEL_BW =
		new JSCTypeDef(
			"KERNEL_BW",
			JSCTypes.NARRAY,
			"used bandwidth for kernel estimation");

	private JSCNArray data = null;
	private JSCSArray names = null;
	private TSDateRange range = null;
	private boolean isTextOut = false;
	private boolean isMultWindow = false;
	private int kernel = 0;
	private int bound = 0;
	private boolean isStd = false;
	private boolean isPlotNormal = false;
	private boolean isDerivative = false;
	private boolean isDefaultBw = false;
	private double bandWidth = 0;
	private String kernelName = "";
	JSCNArray whereToEst = null;
	JSCNArray density = null;
	JSCNArray derivative = null;
	JSCNArray bw = null;

	/**
	 * <code>KernelDensCall</code> constructor takes the arguments for the procedure call.
	 *
	 * @param names the data object with the names, a copy is used
	 * @param kernel
	 * <ul>
	 * <li>0 - Gaussian kernel
	 * <li>1 - biweight kernel
	 * <li>2 - rectangular kernel
	 * <li>3 - triangular kernel
	 * <li>4 - Epanechnikov kernel
	 * </ul>
	 */
	public KernelDensCall(
		JSCNArray data,
		JSCSArray names,
		TSDateRange range,
		int kernel,
		int bound,
		boolean isDefaultBw,
		double bandWidth,
		boolean isStd,
		boolean isPlotNormal,
		boolean isTextOut,
		boolean isMultWindow,
		boolean isDerivative) {

		super();

		setName("Kernel Density Estimation");

		this.range = range;
		this.data = data;
		this.names = new JSCSArray(names);
		this.isTextOut = isTextOut;
		this.isMultWindow = isMultWindow;
		this.kernel = kernel;
		this.bound = bound;
		this.isStd = isStd;
		this.isPlotNormal = isPlotNormal;
		this.isDefaultBw = isDefaultBw;
		this.bandWidth = bandWidth;
		this.isDerivative = isDerivative;

	}

	/**
	 * Writes the text output to a string that can be
	 * referenced via <code>getOutput</code>.
	 */
	protected void finalCode() {

		if (!isTextOut)
			return;

		StringBuffer buffer = output;

		double[][] res_density = density.doubleArray();
		double[][] res_derivative = derivative.doubleArray();
		double[][] res_where = whereToEst.doubleArray();
		double res_bw[][] = bw.doubleArray();

		buffer.append("KERNEL DENSITY ESTIMATION\n");
		buffer.append(range.format("sample range:", 21) + "\n");
		buffer.append(
			FArg.sprintf(
				"%-20s %s\n",
				new FArg("used kernel:").add(kernelName)));
		if (isDefaultBw)
			buffer.append(
				"for default bandwidth selection see Silverman (1986), eq. 3.31\n");
		// names
		buffer.append("\n");
		buffer.append(FArg.sprintf("%-12s", new FArg("")));
		for (int i = 0; i < names.rows(); i++)
			buffer.append(
				FArg.sprintf("%-20s", new FArg(names.stringAt(i, 0))));
		buffer.append("\n");

		// bandwidth
		buffer.append(FArg.sprintf("%-12s", new FArg("")));
		for (int i = 0; i < names.rows(); i++)
			buffer.append(FArg.sprintf("h=%-18.3f", new FArg(res_bw[i][0])));
		buffer.append("\n");

		// table headers
		buffer.append(FArg.sprintf("%-12s", new FArg(" x")));
		for (int i = 0; i < names.rows(); i++)
			buffer.append(
				FArg.sprintf("%-10s%-10s", new FArg("dens.").add("deriv.")));

		for (int i = 0; i < res_density.length; i++) {
			buffer.append("\n");
			buffer.append(FArg.sprintf("%- 11.4f", new FArg(res_where[i][0])));
			for (int j = 0; j < res_density[0].length; j++) {
				buffer.append(
					FArg.sprintf(
						"%- 10.4f%- 10.4f",
						new FArg(res_density[i][j]).add(res_derivative[i][j])));
			}
		}

		buffer.append("\n");
	}

	/**
	 * @see gauss.GaussProcCall
	 */
	protected void runCode() {

		if (getSymbolTable() != null) {
			getSymbolTable().get(KERNEL_BW).clear();
			getSymbolTable().get(KERNEL_DENSITY).clear();
			getSymbolTable().get(KERNEL_DERIVATIVE).clear();
			getSymbolTable().get(KERNEL_X).clear();
		}

		if (data.rows() < 2)
			throw new RuntimeException("Not enough observations");

		//String kernMethod = "k_gauss_kernel";
		kernelName = "Gaussian kernel";
		if (kernel == 1) {
			//kernMethod = "k_bw_kernel";
			kernelName = "biweight kernel";
		}
		if (kernel == 2) {
			//kernMethod = "k_rect_kernel";
			kernelName = "rectangular kernel";
		}
		if (kernel == 3) {
			//kernMethod = "k_trian_kernel";
			kernelName = "triangular kernel";
		}
		if (kernel == 4) {
			//kernMethod = "k_epan_kernel";
			kernelName = "Epanechnikov kernel";
		}
		if (isStd)
			data = UData.standardize(data, "data");

		engine().load("pgraph", GaussLoadTypes.LIB);
		engine().load("tools", GaussLoadTypes.LIB);
		engine().load("plot", GaussLoadTypes.LIB);
		engine().load("fil", GaussLoadTypes.LIB);

		whereToEst = (JSCNArray) KERNEL_X.getInstance();
		whereToEst.setVal(UMatrix.seqa(-bound, 0.1, (20 * bound + 1)));

		density = (JSCNArray) KERNEL_DENSITY.getInstance();
		derivative = (JSCNArray) KERNEL_DERIVATIVE.getInstance();
		bw = (JSCNArray) KERNEL_BW.getInstance();

		engine().call(
			"ukernel_wrapper_kernel",
			new JSCData[] {
				whereToEst,
				data,
				new JSCNumber("isDefaultBw", (isDefaultBw ? 0 : bandWidth)),
				new JSCInt("kernmethod", kernel),
				},
			new JSCData[] { density, derivative, bw });

		// compute kernel density for std normal as well
		if (isPlotNormal) {

			JSCNArray density_norm = (JSCNArray) KERNEL_DENSITY.getInstance();
			JSCNArray derivative_norm =
				(JSCNArray) KERNEL_DERIVATIVE.getInstance();
			JSCNArray bandwidth_norm = (JSCNArray) KERNEL_BW.getInstance();

			names.appendRows(
				new JSCSArray("std", new String[] { "stdnormal" }));

			JSCNArray normData = new JSCNArray("stdData");
			engine().call(
				"rndn_tools",
				new JSCData[] { new JSCInt("r", 3000), new JSCInt("c", 1)},
				new JSCData[] { normData });

			engine().call(
				"ukernel_wrapper_kernel",
				new JSCData[] {
					whereToEst,
					normData,
					new JSCNumber("isDefaultBw", (isDefaultBw ? 0 : bandWidth)),
					new JSCInt("kernmethod", kernel),
					},
				new JSCData[] {
					density_norm,
					derivative_norm,
					bandwidth_norm });

			density.appendCols(density_norm);
			derivative.appendCols(derivative_norm);
			bw.appendRows(bandwidth_norm);

		}

		// plot density or 1st derivative
		if (!isTextOut) {
			// set config data
			setGlobalPgraphSettings();

			names = new JSCSArray(names);
			for (int i = 0; i < bw.rows(); i++) {
				String nam_i = names.stringAt(i, 0);
				nam_i =
					FArg.sprintf(
						"%s, h=%-18.3f",
						new FArg(nam_i).add(bw.doubleAt(i, 0)));
				names.setValAt(nam_i, i, 0);
			}

			if (!isDerivative)
				engine().call(
					"plotxy_plot",
					new JSCData[] {
						whereToEst,
						density,
						new JSCInt("isMult", isMultWindow),
						names,
						new JSCString(
							"tit",
							"Kernel Density Estimation\n(" + kernelName + ")")},
					null);

			else
				engine().call(
					"plotxy_plot",
					new JSCData[] {
						whereToEst,
						derivative,
						new JSCInt("isMult", isMultWindow),
						names,
						new JSCString(
							"tit",
							"1st Derivative of Kernel Density\n("
								+ kernelName
								+ ")")},
					null);

		}

		if (getSymbolTable() != null) {
			getSymbolTable().set(density);
			getSymbolTable().set(derivative);
			getSymbolTable().set(whereToEst);
			getSymbolTable().set(bw);
		}

	}
}