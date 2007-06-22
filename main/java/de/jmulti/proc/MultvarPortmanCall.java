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
import com.jstatcom.model.JSCTypeDef;
import com.jstatcom.model.JSCTypes;
import com.jstatcom.model.SymbolTable;
import com.jstatcom.util.FArg;
import com.jstatcom.util.UMatrix;

import de.jmulti.arch.MultARCHAnalysisPanel;
import de.jmulti.tools.ModelTypes;
import de.jmulti.var.VARConstants;
import de.jmulti.vecm.VECMConstants;

/**
 * This GAUSS command object computes the multivariate Portmanteau test and
 * stores the result in a symbol table if one is set.
 *
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public final class MultvarPortmanCall extends RPCall {
	public static final JSCTypeDef MPORT_RESULT =
		new JSCTypeDef(
			"MPORT_RESULT",
			JSCTypes.NARRAY,
			"results of multivariate portmanteau test: stat|p-value|adjstat|p-value|df");

	private ModelTypes modelType = null;
	private int lags = 0;
	private JSCNArray portRes = null;
	private boolean exog = false;
	private SymbolTable toStore = null;

	/**
	 * <code>MultvarPortmanCall</code> constructor takes the arguments for the procedure call.
	 */
	public MultvarPortmanCall(
		SymbolTable gst,
		SymbolTable toStore,
		ModelTypes modelType,
		int lags) {

		super();

		setSymbolTable(gst);
		setName("Multivariate Portmanteau Test");

		this.lags = lags;
		this.modelType = modelType;
		this.toStore = toStore;
	}

	/**
	 * Writes the text output to a string that can be
	 * referenced via <code>getOutput</code>.
	 */
	protected void finalCode() {

		StringBuffer buffer = output;
		if (exog) {
			buffer.append(
				"PORTMANTEAU TEST is not implemented if exogenous variables are in the model.\n\n");
			return;
		}
		buffer.append("PORTMANTEAU TEST (H0:Rh=(r1,...,rh)=0)\n");
		if (modelType == ModelTypes.VAR)
			buffer.append(
				"Reference: Lütkepohl (1993), Introduction to Multiple Time Series Analysis, 2ed, p. 150.");
		buffer.append("\n");
		double portResult[][] = portRes.doubleArray();

		buffer.append(
			FArg.sprintf("%-25s %i \n", new FArg("tested order:").add(lags)));
		buffer.append(
			FArg.sprintf(
				"%-24s %- 8.4f \n",
				new FArg("test statistic:").add(portResult[0][0])));
		buffer.append(
			FArg.sprintf(
				"%-24s %- 8.4f \n",
				new FArg(" p-value:").add(portResult[1][0])));
		buffer.append(
			FArg.sprintf(
				"%-24s %- 8.4f \n",
				new FArg("adjusted test statistic:").add(portResult[2][0])));
		buffer.append(
			FArg.sprintf(
				"%-24s %- 8.4f \n",
				new FArg(" p-value:").add(portResult[3][0])));
		buffer.append(
			FArg.sprintf(
				"%-24s %- 8.4f \n",
				new FArg("degrees of freedom:").add(portResult[4][0])));
		buffer.append("\n");
	}

	/**
	 * @see RPCall
	 */
	protected void runCode() {

		int py = 0;
		int restrict = 0;
		JSCNArray data = null;
		exog = false;
		if (toStore != null)
			toStore.get(MPORT_RESULT).clear();

		engine().load("var", GaussLoadTypes.LIB);
		engine().load("vec", GaussLoadTypes.LIB);
		engine().load("fil", GaussLoadTypes.LIB);
		engine().load("diag", GaussLoadTypes.LIB);

		portRes = (JSCNArray) MPORT_RESULT.getInstance();

		if (modelType == ModelTypes.VAR) {
			data = getSymbolTable().get(VARConstants.u_hat_Def).getJSCNArray();
			if (exog = !getSymbolTable().get(VARConstants.Ax_Def).isEmpty())
				return;

			py = getSymbolTable().get(VARConstants.py_Def).getJSCInt().intVal();

			if (py > 0) {
				double[] coeff =
					getSymbolTable()
						.get(VARConstants.Ay_Def)
						.getJSCNArray()
						.vec();

				restrict = coeff.length - UMatrix.getNonzeroDoubleCount(coeff);
			}

			engine().call(
				"portman_resanal",
				new JSCData[] {
					data,
					new JSCInt("lags", lags),
					new JSCInt("py", py),
					new JSCInt("restrict", restrict)},
				new JSCData[] { portRes });

		} else if (modelType == ModelTypes.VECM) {
			data = getSymbolTable().get(VECMConstants.u_hat_Def).getJSCNArray();
			if (exog = !getSymbolTable().get(VECMConstants.B_Def).isEmpty())
				return;

			py =
				getSymbolTable().get(VECMConstants.py_Def).getJSCInt().intVal();
			if (py > 1) {
				double[] coeff =
					getSymbolTable()
						.get(VECMConstants.G_Def)
						.getJSCNArray()
						.vec();

				restrict = UMatrix.getNonzeroDoubleCount(coeff);
			}
			JSCNArray alpha =
				getSymbolTable().get(VECMConstants.alpha_Def).getJSCNArray();
			int rank = alpha.cols();

			double[] alph = alpha.vec();
			restrict += UMatrix.getNonzeroDoubleCount(alph);

			engine().call(
				"GetPortmanteauVec_diag",
				new JSCData[] {
					data,
					new JSCInt("py", py),
					new JSCInt("rk", rank),
					new JSCInt("restrict", restrict),
					new JSCInt("lags", lags)},
				new JSCData[] { portRes });

		} else if (modelType == ModelTypes.MGARCH) {
			data =
				getSymbolTable()
					.get(MultARCHAnalysisPanel.resName)
					.getJSCNArray();

			engine().call(
				"portman_resanal",
				new JSCData[] {
					data,
					new JSCInt("lags", lags),
					new JSCInt("py", 1),
					new JSCInt("restrict", 0)},
				new JSCData[] { portRes });

		} else
			throw new IllegalArgumentException(
				"Invalid model type " + modelType + ".");

		if (toStore != null)
			toStore.set(portRes);

	}
}