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
import com.jstatcom.model.JSCString;
import com.jstatcom.model.JSCTypeDef;
import com.jstatcom.model.JSCTypes;
import com.jstatcom.util.FArg;

import de.jmulti.tools.ModelTypes;

/**
 * This GAUSS command object computes ARCH/GARCH/T-GARCH estimation and
 * stores the results in a symbol table if one is set. 
 *
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public final class GARCHCall extends RPCall {
	public final static JSCTypeDef GARCH_IVAR =
		new JSCTypeDef(
			"GARCH_IVAR",
			JSCTypes.NARRAY,
			"estimated variance process");
	public final static JSCTypeDef GARCH_RES =
		new JSCTypeDef(
			"GARCH_RES",
			JSCTypes.NARRAY,
			"residuals from garch estimation");
	public final static JSCTypeDef GARCH_PARAM =
		new JSCTypeDef(
			"GARCH_PARAM",
			JSCTypes.NARRAY,
			"parameter estimates from univariate arch estimation: intercept | q parameters | p parameters | likelihood");
	public static final JSCTypeDef GARCH_COV =
		new JSCTypeDef(
			"GARCH_COV",
			JSCTypes.NARRAY,
			"variance-covariance matrix");
	public static final JSCTypeDef GARCH_TVAL =
		new JSCTypeDef(
			"GARCH_TVAL",
			JSCTypes.NARRAY,
			"t-values of estimated GARCH parameters: intercept | q parameters | p parameters");

	private JSCNArray data = null;
	private JSCString name = null;
	private ModelTypes modelType = null;
	private int qLags;
	private int pLags;
	private int distType;

	private JSCNArray param;
	private JSCNArray std;
	private JSCNArray tval;

	/**
	 * <code>UnivarJBeraCall</code> constructor takes the arguments for the procedure call.
	 *
	 * @param distType type of error distribution
	 *        <ul>
	 *        <li> 0 - Gaussian errors
	 *        <li> 1 - GED errors
	 *        <li> 2 - t-distributed errors
	 *        </ul> 
	 */
	public GARCHCall(
		JSCNArray data,
		JSCString name,
		ModelTypes modelType,
		int qLags,
		int pLags,
		int distType) {

		super();

		setName("GARCH Estimation");

		this.data = data;
		this.modelType = modelType;
		this.qLags = qLags;
		this.pLags = pLags;
		this.distType = distType;
		this.name = name;

	}

	/**
	 * Writes the text output to a string that can be
	 * referenced via <code>getOutput</code>.
	 */
	protected void finalCode() {

		StringBuffer buffer = output;

		String dist = "normal distributed errors";
		// GED errors
		if (distType == 1)
			dist = "GED errors";
		else
			// t-distributed errors
			if (distType == 2)
				dist = "t-distributed errors";

		buffer.append(
			modelType
				+ " estimation with "
				+ dist
				+ " for \""
				+ name.string()
				+ "\"\n");
		buffer.append("ARCH lags = " + qLags + "\n");
		if (modelType != ModelTypes.ARCH)
			buffer.append("lags in variances = " + pLags + "\n");
		double[][] g_arch = param.doubleArray();
		double[][] tvalues = tval.doubleArray();
		double[][] s_arch = std.doubleArray();
		buffer.append(
			FArg.sprintf(
				"\n %-14s%-14s%-14s\n",
				new FArg("variable").add("coefficient").add("t-statistic")));

		// intercept
		buffer.append(
			FArg.sprintf(
				"%-14s%- 14.5e%- 14.5f\n",
				new FArg(" intercept").add(g_arch[0][0]).add(tvalues[0][0])));
		// qLags parameters
		for (int i = 0; i < qLags; i++)
			buffer.append(
				FArg.sprintf(
					"%-14s%- 14.5e%- 14.5f\n",
					new FArg(" gamma(" + (i + 1) + ")").add(
						g_arch[i + 1][0]).add(
						tvalues[i + 1][0])));
		// qLags parameters for TGARCH
		if (modelType == ModelTypes.TGARCH) {
			for (int i = 0; i < qLags; i++)
				buffer.append(
					FArg.sprintf(
						"%-14s%- 14.5e%- 14.5f\n",
						new FArg(" gamma_(" + (i + 1) + ")").add(
							g_arch[i + 1 + qLags][0]).add(
							tvalues[i + 1 + qLags][0])));
			qLags = qLags + qLags;
		}

		// p parameters in case garch || tgarch models (p > 0)
		for (int i = 0; i < pLags; i++)
			buffer.append(
				FArg.sprintf(
					"%-14s%- 14.5e%- 14.5f\n",
					new FArg(" beta(" + (i + 1) + ")").add(
						g_arch[i + 1 + qLags][0]).add(
						tvalues[i + 1 + qLags][0])));
		// the degrees of freedom
		if (g_arch.length > 1 + pLags + qLags + 1)
			buffer.append(
				FArg.sprintf(
					"%-14s%- 14.5e%- 14.5f\n",
					new FArg(" degfree").add(
						g_arch[1 + pLags + qLags + 1][0]).add(
						tvalues[1 + pLags + qLags][0])));

		buffer.append("\n covariance matrix\n");
		for (int i = 0; i < s_arch.length; i++) {
			for (int j = 0; j < s_arch[0].length; j++)
				buffer.append(FArg.sprintf("%- 14.5e", new FArg(s_arch[i][j])));
			buffer.append("\n");
		}

		buffer.append(
			FArg.sprintf(
				"\nlog likelihood = %- 14.5e",
				new FArg(-g_arch[1 + pLags + qLags][0])));

		buffer.append("\n");
	}

	/**
	 * @see GaussCall
	 */
	protected void runCode() {
		if (getSymbolTable() != null) {
			getSymbolTable().get(GARCH_PARAM).clear();
			getSymbolTable().get(GARCH_COV).clear();
			getSymbolTable().get(GARCH_TVAL).clear();
			getSymbolTable().get(GARCH_RES).clear();
			getSymbolTable().get(GARCH_IVAR).clear();
		}

		if (data.rows() < 2 * qLags)
			throw new RuntimeException("Not enough observations.");

		StringBuffer methodCall = new StringBuffer();
		// ARCH
		if (modelType == ModelTypes.ARCH) {
			methodCall.append("arch");
		} else
			// GARCH
			if (modelType == ModelTypes.GARCH) {
				methodCall.append("garch");
			} else
				// TGARCH
				if (modelType == ModelTypes.TGARCH) {
					methodCall.append("tgarch");
				} else
					// Should not happen
					throw new RuntimeException("Invalid ARCH Model selection.");

		// Gaussian errors
		if (distType == 0)
			methodCall.append("_wrapper_arch");
		else
			// GED errors
			if (distType == 1)
				methodCall.append("ged_wrapper_arch");
			else
				// t-distributed errors
				if (distType == 2)
					methodCall.append("tdi_wrapper_arch");
				else
					// Should not happen
					throw new RuntimeException("Invalid error distribution selection.");

		engine().load("arch", GaussLoadTypes.LIB);

		JSCNArray[] retArg =
			new JSCNArray[] {
				(JSCNArray) GARCH_PARAM.getInstance(),
				(JSCNArray) GARCH_COV.getInstance(),
				(JSCNArray) GARCH_TVAL.getInstance(),
				(JSCNArray) GARCH_RES.getInstance(),
				(JSCNArray) GARCH_IVAR.getInstance()};
		
     	// GARCH || TGARCH append p parameter
		if (modelType == ModelTypes.GARCH || modelType == ModelTypes.TGARCH)
			engine().call(
				methodCall.toString(),
				new JSCData[] {
					data,
					new JSCInt("qLags", qLags),
					new JSCInt("pLags", pLags)},
				retArg);
		else
			engine().call(
				methodCall.toString(),
				new JSCData[] { data, new JSCInt("qLags", qLags)},
				retArg);

		if (getSymbolTable() != null)
			getSymbolTable().set(retArg);
		param = retArg[0];
		std = retArg[1];
		tval = retArg[2];
	}
}