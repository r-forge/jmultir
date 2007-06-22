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
import com.jstatcom.model.JSCConstants;
import com.jstatcom.model.JSCData;
import com.jstatcom.model.JSCInt;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.JSCTypeDef;
import com.jstatcom.model.JSCTypes;
import com.jstatcom.model.SymbolTable;
import com.jstatcom.util.FArg;
import com.jstatcom.util.UData;

import de.jmulti.tools.ModelTypes;
import de.jmulti.var.VARConstants;
import de.jmulti.vecm.VECMConstants;

/**
 * This GAUSS command object computes the Doornik LM test and
 * stores the result in a symbol table if one is set.
 *
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public final class LMTestCall extends RPCall {
	public static final JSCTypeDef LMTEST_RESULT =
		new JSCTypeDef(
			"LMTEST_RESULT",
			JSCTypes.NARRAY,
			"results of LM test:\n"
				+ "VEC: [LM~cdfchic(LM,df)~df~0]|[LMF~cdffc(LMF,df1,df2)~df1~df2]\n"
				+ "VAR: [LM~cdfchic(LM,df)~df]");

	private ModelTypes modelType = null;
	private int lags = 0;
	private JSCNArray lmRes = null;
	private SymbolTable toStore = null;

	/**
	 * <code>LMTestCall</code> constructor takes the arguments for the procedure call.
	 */
	public LMTestCall(
		SymbolTable gst,
		SymbolTable toStore,
		ModelTypes modelType,
		int lags) {

		super();

		setSymbolTable(gst);
		setName("LM/LMF Test");

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
		if (modelType == ModelTypes.VECM) {
			double lm[][] = lmRes.doubleArray();
			buffer.append(
				"LM-TYPE TEST FOR AUTOCORRELATION with "
					+ lags
					+ " lags\n\n");
			if (lm[0][0] > 0) {
				buffer.append(
					FArg.sprintf(
						"%-24s %- 8.4f \n",
						new FArg("LM statistic:").add(lm[0][0])));
				buffer.append(
					FArg.sprintf(
						"%-24s %- 8.4f \n",
						new FArg(" p-value:").add(lm[1][0])));
				buffer.append(
					FArg.sprintf(
						"%-24s %- 8.4f \n",
						new FArg(" df:").add(lm[2][0])));
			} else if (lm[0][0] == -1)
				buffer.append(
					"Too many parameters in auxiliary model. Please choose fewer lags.\n");
			else if (lm[0][0] == -2)
				buffer.append(
					"Auxiliary regression failed due to matrix inversion problem.\n");

		}
		if (modelType == ModelTypes.VAR) {
			double lm[][] = lmRes.doubleArray();
			buffer.append(
				"LM-TYPE TEST FOR AUTOCORRELATION with " + lags + " lags\n");
			buffer.append(
				"Reference: Doornik (1996), LM test and LMF test (with F-approximation)\n");
			buffer.append(
				FArg.sprintf(
					"%-24s %- 8.4f \n",
					new FArg("LM statistic:").add(lm[0][0])));
			buffer.append(
				FArg.sprintf(
					"%-24s %- 8.4f \n",
					new FArg(" p-value:").add(lm[0][1])));
			buffer.append(
				FArg.sprintf(
					"%-24s %- 8.4f \n",
					new FArg(" df:").add(lm[0][2])));
			if (lm[1][0] > 0) {
				buffer.append(
					FArg.sprintf(
						"%-24s %- 8.4f \n",
						new FArg("LMF statistic:").add(lm[1][0])));
				buffer.append(
					FArg.sprintf(
						"%-24s %- 8.4f \n",
						new FArg(" p-value:").add(lm[1][1])));
				buffer.append(
					FArg.sprintf(
						"%-24s %- 8.4f \n",
						new FArg(" df1:").add(lm[1][2])));
				buffer.append(
					FArg.sprintf(
						"%-24s %- 8.4f \n",
						new FArg(" df2:").add(lm[1][3])));
			} else if (
				Double.doubleToLongBits(lm[1][0]) == JSCConstants.NaNLongBits)
				buffer.append("LMF statistic not computed for subset model.");
			else
				buffer.append(
					"Insufficient degrees of freedom for the F-correction.\n");
		}
		buffer.append("\n");
	}

	/**
	 * @see RPCall
	 */
	protected void runCode() {

		SymbolTable gst = getSymbolTable();
		if (toStore != null)
			toStore.get(LMTEST_RESULT).clear();

		lmRes = (JSCNArray) LMTEST_RESULT.getInstance();

		engine().load("fil", GaussLoadTypes.LIB);
		engine().load("diag", GaussLoadTypes.LIB);

		if (modelType == ModelTypes.VAR) {
			JSCNArray varBuffer = new JSCNArray("varBuffer");
			VAREstimationCall.setVarBuffer(engine(), gst, varBuffer);

			engine().call(
				"LMautocorr_resanal",
				new JSCData[] { varBuffer, new JSCInt("lags", lags)},
				new JSCData[] { lmRes });

		} else if (modelType == ModelTypes.VECM) {

			// GetARTest_BGNoCIterms_diag(mU, mX, mR_mod,h);
			JSCNArray resids = gst.get(VECMConstants.u_hat_Def).getJSCNArray();
			int dpy = gst.get(VARConstants.py_Def).getJSCInt().intVal() - 1;
			int px = gst.get(VARConstants.px_Def).getJSCInt().intVal();

			JSCNArray end = gst.get(VECMConstants.y_raw_Def).getJSCNArray();
			JSCNArray ex = gst.get(VECMConstants.x_raw_Def).getJSCNArray();
			JSCNArray det = gst.get(VECMConstants.d_Def).getJSCNArray();
			JSCNArray idx = gst.get(VECMConstants.idx_cd2ci_Def).getJSCNArray();

			JSCNArray tmpRes = new JSCNArray("tmpRes");
			tmpRes.appendCols(gst.get(VECMConstants.S_G_Def).getJSCNArray());
			tmpRes.appendCols(gst.get(VECMConstants.S_B_Def).getJSCNArray());

			JSCNArray beta = gst.get(VECMConstants.beta_Def).getJSCNArray();
			JSCNArray beta_d = gst.get(VECMConstants.beta_d_Def).getJSCNArray();

			JSCNArray resVec =
				UData.createResMat(
					gst.get(VECMConstants.S_C_VEC_Def).getJSCNArray(),
					gst.get(VECMConstants.S_alpha_Def).getJSCNArray(),
					tmpRes,
					"ResVec");

			JSCNArray mX = new JSCNArray("mX");
			engine().call(
				"createMX_diag",
				new JSCData[] {
					end,
					ex,
					det,
					idx,
					new JSCInt("dpy", dpy),
					new JSCInt("px", px),
					beta,
					beta_d },
				new JSCData[] { mX });

			engine().call(
				"GetARTest_BGNoCIterms_diag",
				new JSCData[] { resids, mX, resVec, new JSCInt("lags", lags)},
				new JSCData[] { lmRes });

		} else
			throw new IllegalArgumentException(
				"Invalid model type " + modelType + ".");

		if (toStore != null)
			toStore.set(lmRes);

	}
}