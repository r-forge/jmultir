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
import com.jstatcom.model.JSCTypeDef;
import com.jstatcom.model.JSCTypes;
import com.jstatcom.util.FArg;

import de.jmulti.tools.ModelTypes;

/**
 * This GAUSS command object computes and plots the Forecast Error Variance
 * Decomposition for different models according to the parametrization. The
 * result of this call is stored to the symbol table with
 * <code>fevd_result</code> if one has been set.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
public final class FEVDCall extends RPCall {
    public static final JSCTypeDef FEVD_RESULT = new JSCTypeDef("fevd_vd",
            JSCTypes.NARRAY,
            "(Horizon-1,K*K) series of decomposed forecast errors");

    private int horizon = 20;

    private JSCSArray variableNames = null;

    private JSCNArray selection = null;

    private JSCNArray coeff = null;

    private JSCNArray covar = null;

    private JSCNArray A0 = null;

    private JSCNArray varianceDecomp = null;

    private ModelTypes modelType = null;

    private JSCNArray barTypes = null;

    private boolean doPlot = true;

    /**
     * <code>FEVDCall</code> constructor takes the arguments for the procedure
     * call.
     */
    public FEVDCall(int horizon, JSCSArray variableNames, JSCNArray selection,
            JSCNArray coeff, JSCNArray covar, JSCNArray A0,
            ModelTypes modelType, JSCNArray barTypes, boolean doPlot) {

        super();
        if (modelType == null)
            throw new IllegalArgumentException("Modeltype was null.");
        setName("Forecast Error Variance Decomposition for " + modelType);

        this.horizon = horizon;
        this.variableNames = variableNames;
        this.selection = selection;
        this.coeff = coeff;
        this.covar = covar;
        this.A0 = A0;
        this.modelType = modelType;
        this.barTypes = barTypes;
        this.doPlot = doPlot;

    }

    /**
     * Writes the text output to a string that can be referenced via
     * <code>getOutput</code>.
     */
    protected void finalCode() {

        output.append(modelType + " FORECAST ERROR VARIANCE DECOMPOSITION\n\n");
        int k = variableNames.rows();
        //
        //
        for (int i = 0; i < k; i++) {
            if (selection.intAt(i, 0) == 1) {
                // print header
                output.append("Proportions of forecast error in \"");
                output.append(FArg.sprintf("%s\"\n", new FArg(variableNames
                        .stringAt(i, 0))));
                output
                        .append("                    accounted for by:\n forecast horizon   ");
                for (int j = 0; j < k; j++)
                    output.append(FArg.sprintf("%10s  ", new FArg(variableNames
                            .stringAt(j, 0))));
                output.append("\n");
                for (int h = 0; h < horizon; h++) {
                    output.append(FArg.sprintf("      %3.0d             ",
                            new FArg(new Integer(h + 1))));
                    for (int j = 0; j < k; j++)
                        output.append(FArg.sprintf("%8.2f    ", new FArg(
                                varianceDecomp.doubleAt(h, i * k + j))));
                    output.append("\n");
                }
                output.append("\n\n");
            }
        }

    }

    /**
     * @see RPCall
     */
    protected void runCode() {

        if (getSymbolTable() != null)
            getSymbolTable().get(FEVD_RESULT).clear();

        engine().load("pgraph", GaussLoadTypes.LIB);
        engine().load("tools", GaussLoadTypes.LIB);
        engine().load("plot", GaussLoadTypes.LIB);
        engine().load("var", GaussLoadTypes.LIB);
        engine().load("vec", GaussLoadTypes.LIB);

        varianceDecomp = (JSCNArray) FEVD_RESULT.getInstance();
        JSCNArray irOrth = new JSCNArray("irOrth");

        // VAR
        if (modelType == ModelTypes.VAR) {

            engine()
                    .call(
                            "var_impulseResponses",
                            new JSCData[] { new JSCNArray("A0_empty"), coeff,
                                    covar, new JSCInt("horizon", horizon),
                                    new JSCInt("outFmt", 1) },
                            new JSCData[] { new JSCNArray("ir_ferr"),
                                    new JSCNArray("tmp"), irOrth,
                                    new JSCNArray("tmp") });

            engine().call("vec_fevd", new JSCData[] { irOrth },
                    new JSCData[] { varianceDecomp });

        }
        // SVAR / SVEC
        if (modelType == ModelTypes.SVAR || modelType == ModelTypes.SVEC) {

            engine()
                    .call(
                            "ComputeIRF_svar_var",
                            new JSCData[] { coeff,
                                    new JSCInt("horizon", horizon), A0 },
                            new JSCData[] { irOrth, new JSCNArray("tmp") });

            engine().call("vec_fevd", new JSCData[] { irOrth },
                    new JSCData[] { varianceDecomp });

        }

        // VECM
        if (modelType == ModelTypes.VECM) {

            engine()
                    .call(
                            "var_impulseResponses",
                            new JSCData[] { A0, coeff, covar,
                                    new JSCInt("horizon", horizon),
                                    new JSCInt("outFmt", 1) },
                            new JSCData[] { new JSCNArray("ir_ferr"),
                                    new JSCNArray("tmp"), irOrth,
                                    new JSCNArray("tmp") });

            engine().call("vec_fevd", new JSCData[] { irOrth },
                    new JSCData[] { varianceDecomp });

        }
        // only when plot is set
        if (doPlot) {
            setGlobalPgraphSettings();
            if (barTypes == null)
                barTypes = new JSCNArray("barTypes");
            // PLOT
            engine().call(
                    "vec_fevd_plot",
                    new JSCData[] { varianceDecomp, variableNames, selection,
                            new JSCInt("horizon", horizon), barTypes }, null);
        }

        if (getSymbolTable() != null)
            getSymbolTable().set(varianceDecomp);
    }
}