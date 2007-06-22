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

import com.jstatcom.engine.gauss.GaussLoadTypes;
import com.jstatcom.model.JSCData;
import com.jstatcom.model.JSCInt;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.JSCSArray;
import com.jstatcom.model.JSCString;
import com.jstatcom.util.FArg;

import de.jmulti.tools.IRACIManager;
import de.jmulti.tools.ModelTypes;
import de.jmulti.tools.PlotControlModel;

/**
 * This GAUSS command plots the selected impulse response function together with
 * the respective confidence intervals that are given. It also assembles text
 * output that is printed to the result field if one was set. The computed
 * confidence intervals that should be displayed with the IR function are
 * retrieved from the <code>IRACIManager</code> for the respective model. The
 * selected CIs are stored to the symbol table if one is set.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
public final class IRADisplayCall extends RPCall {

	private PlotControlModel plotModel = null;
	private JSCNArray irFunct = null;
	private String[] selCINames = null;
	private ModelTypes modelType = ModelTypes.VAR;
	private boolean isOrth = false;
	private int periods = 1;
	private JSCNArray selImpulseIndex = null;
	private JSCNArray selResponseIndex = null;
	private JSCNArray responseAccumIndex = null;
	private JSCSArray varNames = null;
	private String title = "IR Plot";

	/**
	 * IRADisplayCall constructor comment.
	 */
	public IRADisplayCall(JSCNArray irFunct, String[] selCINames,
			boolean isOrth, int periods, JSCNArray selImpulseIndex,
			JSCNArray selResponseIndex, JSCNArray responseAccumIndex,
			JSCSArray varNames, String title, PlotControlModel plotModel,
			ModelTypes modelType) {

		super();

		setName(title);

		this.irFunct = irFunct;
		this.selCINames = selCINames;
		this.isOrth = isOrth;
		this.periods = periods;
		this.selImpulseIndex = selImpulseIndex;
		this.selResponseIndex = selResponseIndex;
		this.responseAccumIndex = responseAccumIndex;
		this.varNames = varNames;
		this.title = title;
		this.plotModel = plotModel;
		this.modelType = modelType;
	}

	/**
	 * Assembles the output text for IR function and all CIs.
	 *  
	 */
	public void finalCode() {

		output.append(title + "\n\n");

		// compute number of selected impulse reponse combinations
		int n_impulses = 0;
		for (int i = 0; i < selImpulseIndex.rows(); i++) {
			if (selImpulseIndex.intAt(i, 0) == 1)
				n_impulses++;
		}
		int n_responses = 0;
		for (int i = 0; i < selResponseIndex.rows(); i++) {
			if (selResponseIndex.intAt(i, 0) == 1)
				n_responses++;
		}
		int n_ir = n_impulses * n_responses;

		// indices of selected impulse reponses
		int[] indices = new int[n_ir];
		String[] headerImpulse = new String[n_ir];
		String[] headerResponse = new String[n_ir];
		boolean[] isAccum = new boolean[n_ir];

		int K = varNames.rows();
		int idx = 0;
		for (int i = 0; i < K; i++) {
			for (int j = 0; j < K; j++) {
				if (selResponseIndex.intAt(i, 0) == 1
						&& selImpulseIndex.intAt(j, 0) == 1) {
					indices[idx] = i * K + j;
					if (responseAccumIndex.intAt(i, 0) == 1) {
						headerImpulse[idx] = "accumulated "
								+ varNames.stringAt(j, 0);
						isAccum[idx] = true;
					} else {
						headerImpulse[idx] = " " + varNames.stringAt(j, 0);
						isAccum[idx] = false;
					}
					headerResponse[idx++] = " ->" + varNames.stringAt(i, 0);
				}
			}
		}

		// number of selected confidence intervals
		int n_ci = selCINames.length;

		JSCNArray[][] ciDataArray = new JSCNArray[n_ci][2];

		for (int i = 0; i < n_ci; i++)
			ciDataArray[i] = IRACIManager.getInstance().getCI(selCINames[i],
					modelType, isOrth);

		// store CI's to symbol table
		if (getSymbolTable() != null)
			for (int i = 0; i < n_ci; i++) {
				getSymbolTable().set(ciDataArray[i]);
				getSymbolTable()
						.getSymbol(ciDataArray[i][0].name())
						.setDescription(
								selCINames[i]
										+ " lower bound,\nK*K normal ~ K*K accumulated");
				getSymbolTable()
						.getSymbol(ciDataArray[i][1].name())
						.setDescription(
								selCINames[i]
										+ " upper bound,\nK*K normal ~ K*K accumulated");
			}

		if (n_ci > 0) {
			output.append("Selected Confidence Interval (CI):\n");
			for (int i = 0; i < n_ci; i++)
				output.append("" + (char) (97 + i) + ") " + selCINames[i]
						+ "\n");

			output.append("\n\n");
		}
		// print header
		output
				.append("                   Selected  Impulse  Responses: \"impulse variable -> response variable\"\n\n");
		output.append(FArg.sprintf("%4s              ", new FArg("time")));
		for (int i = 0; i < headerImpulse.length; i++)
			output.append(FArg.sprintf("%16s    ", new FArg(headerImpulse[i])));

		output.append("\n                  ");
		for (int i = 0; i < headerResponse.length; i++)
			output.append(FArg.sprintf("%16s    ",
					new FArg((headerResponse[i]))));

		output.append("\n\n");

		// print point estimates and ci's
		int cols = irFunct.cols() / 2;
		for (int i = 0; i < irFunct.rows(); i++) {
			// print point estimates
			output.append(FArg.sprintf("%3.0d point estimate", new FArg(i)));

			for (int j = 0; j < indices.length; j++) {
				output.append(FArg.sprintf("%16.4f    ", new FArg(irFunct
						.doubleAt(i, indices[j] + (isAccum[j] ? cols : 0)))));
			}
			output.append("\n");

			// print ci's
			if (n_ci > 0) {
				for (int ci_i = 0; ci_i < n_ci; ci_i++) {
					output.append(FArg.sprintf("    CI %1c)          ",
							new FArg((char) (97 + ci_i))));
					if (i < ciDataArray[ci_i][0].rows()) {
						for (int j = 0; j < indices.length; j++) {
							output.append(FArg.sprintf(" [%8.4f,%8.4f]",
									new FArg(ciDataArray[ci_i][0].doubleAt(i,
											indices[j]
													+ (isAccum[j] ? cols : 0)))
											.add(ciDataArray[ci_i][1].doubleAt(
													i, indices[j]
															+ (isAccum[j]
																	? cols
																	: 0)))));

						}
					} else
						output.append(" not bootstrapped up to this horizon");

					output.append("\n");
				}
				output.append("\n");
			}
		}
	}

	/**
	 * @see ProcCall
	 */
	protected void runCode() {

		// Get CIs from manager and add names to commandline,
		// at most 4 CI arguments are possible, the others have to be
		// filled with argNULL.
		JSCNArray argEmtpy = new JSCNArray("argEmpty");

		JSCData[] arg = new JSCData[15]; // 1 + 2 * 4 + 6
		arg[0] = irFunct;
		for (int i = 0; i < 4; i++) { // We need 4*2 CI arguments.
			if (i < selCINames.length) {
				JSCNArray[] gda = IRACIManager.getInstance().getCI(
						selCINames[i], modelType, isOrth);

				arg[1 + 2 * i] = gda[0];
				arg[1 + 2 * i + 1] = gda[1];
			} else {
				arg[1 + 2 * i] = argEmtpy;
				arg[1 + 2 * i + 1] = argEmtpy;
			}
		}

		arg[9] = new JSCInt("periods", periods);
		arg[10] = selImpulseIndex;
		arg[11] = selResponseIndex;
		arg[12] = responseAccumIndex;
		arg[13] = varNames;
		arg[14] = new JSCString("tit", title);

		engine().load("pgraph", GaussLoadTypes.LIB);
		engine().load("tools", GaussLoadTypes.LIB);
		engine().load("plot", GaussLoadTypes.LIB);
		engine().load("var", GaussLoadTypes.LIB);
		engine().load("vec", GaussLoadTypes.LIB);

		setGlobalPgraphSettings(plotModel);

		engine().call("vec_ir_plot", arg, null);

	}
}