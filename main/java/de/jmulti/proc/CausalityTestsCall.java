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
import com.jstatcom.model.JSCTypeDef;
import com.jstatcom.model.JSCTypes;
import com.jstatcom.util.FArg;
import com.jstatcom.util.UMatrix;

import de.jmulti.tools.ModelTypes;

/**
 * This GAUSS command object computes the Granger and instantaneous causality
 * tests for different models according to the parametrization. The result of
 * this call is stored to the symbol table if one has been set.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
public final class CausalityTestsCall extends RPCall {

    public static final JSCTypeDef GRANGER_STAT = new JSCTypeDef(
            "GRANGER_STAT", JSCTypes.NUMBER, "Granger causality test statistic");

    public static final JSCTypeDef GRANGER_DF1 = new JSCTypeDef("GRANGER_DF1",
            JSCTypes.NUMBER, "Granger causality degrees of freedom 1");

    public static final JSCTypeDef GRANGER_DF2 = new JSCTypeDef("GRANGER_DF2",
            JSCTypes.NUMBER, "Granger causality degrees of freedom 2");

    public static final JSCTypeDef GRANGER_PVAL = new JSCTypeDef(
            "GRANGER_PVAL", JSCTypes.NUMBER, "Granger causality test F p-value");

    public static final JSCTypeDef INSTANT_STAT = new JSCTypeDef(
            "INSTANT_STAT", JSCTypes.NUMBER,
            "instantaneous causality test statistic");

    public static final JSCTypeDef INSTANT_DF = new JSCTypeDef("INSTANT_DF",
            JSCTypes.NUMBER, "instantaneous causality degrees of freedom");

    public static final JSCTypeDef INSTANT_PVAL = new JSCTypeDef(
            "INSTANT_PVAL", JSCTypes.NUMBER,
            "instantaneous causality Chi2 p-value");

    private ModelTypes modelType = null;

    private JSCNArray y = null;

    private JSCNArray x = null;

    private JSCNArray d = null;

    private int px = 0;

    private int py = 0;

    private JSCNArray cause_sel = null;

    private JSCNArray cx = null;

    private JSCNArray cd = null;

    private String caus = null;

    private String eff = null;

    private JSCNArray selEC = null;

    // results
    private JSCNumber g_t = null;

    private JSCNumber g_df1 = null;

    private JSCNumber g_df2 = null;

    private JSCNumber g_f = null;

    private JSCNumber i_t = null;

    private JSCNumber i_df = null;

    private JSCNumber i_c = null;

    /**
     * <code>CausalityTestsCall</code> constructor takes the arguments for the
     * procedure call.
     */
    public CausalityTestsCall(ModelTypes modelType, JSCNArray y, JSCNArray x,
            JSCNArray d, int px, int py, JSCNArray cause_sel, JSCNArray cx,
            JSCNArray cd, String caus, String eff, JSCNArray selEC) {

        super();
        if (modelType == null)
            throw new IllegalArgumentException("Modeltype was null.");

        setName("Causality Tests " + modelType);

        this.modelType = modelType;
        this.y = y;
        this.x = x;
        this.d = d;
        this.py = py;
        this.px = px;
        this.cause_sel = cause_sel;
        this.cx = cx;
        this.cd = cd;
        this.caus = caus;
        this.eff = eff;
        this.selEC = selEC;

    }

    /**
     * Writes the text output to a string that can be referenced via
     * <code>getOutput</code>.
     */
    protected void finalCode() {

        StringBuffer buffer = output;

        if (!g_t.isEmpty()) {
            buffer.append("TEST FOR GRANGER-CAUSALITY:\n");
            buffer.append(FArg.sprintf(
                    "H0: \"%s\" do not Granger-cause \"%s\"\n\n",
                    new FArg(caus).add(eff)));
            buffer.append(FArg.sprintf("Test statistic l = %1.4f\n", new FArg(
                    g_t.doubleVal())));
            buffer.append(FArg.sprintf("pval-F( l; %1.0f, %1.0f) =", new FArg(
                    g_df1.doubleVal()).add(g_df2.doubleVal())));
            buffer.append(FArg.sprintf(" %1.4f ", new FArg(g_f.doubleVal())));
            buffer.append("\n\n");
        } else
            buffer
                    .append("Test for Granger-causality was not performed (p = 0).\n");

        if (!i_t.isEmpty()) {
            buffer.append("TEST FOR INSTANTANEOUS CAUSALITY:\n");
            buffer
                    .append(FArg
                            .sprintf(
                                    "H0: No instantaneous causality between \"%s\" and \"%s\"\n\n",
                                    new FArg(caus).add(eff)));
            buffer.append(FArg.sprintf("Test statistic: c = %1.4f\n", new FArg(
                    i_t.doubleVal())));
            buffer.append(FArg.sprintf("pval-Chi( c; %1.0f) = ", new FArg(i_df
                    .doubleVal())));
            buffer.append(FArg.sprintf("%1.4f ", new FArg(i_c.doubleVal())));
            buffer.append("\n\n");
        }

    }

    /**
     * @see RPCall
     */
    protected void runCode() {

        int isVecm = 0;
        if (modelType == ModelTypes.VECM)
            isVecm = 1;

        if (getSymbolTable() != null) {
            getSymbolTable().get(GRANGER_DF1).clear();
            getSymbolTable().get(GRANGER_DF2).clear();
            getSymbolTable().get(GRANGER_STAT).clear();
            getSymbolTable().get(GRANGER_PVAL).clear();
            getSymbolTable().get(INSTANT_STAT).clear();
            getSymbolTable().get(INSTANT_DF).clear();
            getSymbolTable().get(INSTANT_PVAL).clear();
        }

        engine().load("var", GaussLoadTypes.LIB);
        engine().load("vec", GaussLoadTypes.LIB);

        g_t = (JSCNumber) GRANGER_STAT.getInstance();
        g_df1 = (JSCNumber) GRANGER_DF1.getInstance();
        g_df2 = (JSCNumber) GRANGER_DF2.getInstance();
        g_f = (JSCNumber) GRANGER_PVAL.getInstance();
        i_t = (JSCNumber) INSTANT_STAT.getInstance();
        i_df = (JSCNumber) INSTANT_DF.getInstance();
        i_c = (JSCNumber) INSTANT_PVAL.getInstance();

        JSCNArray cd_new = cd; // for VAR/VEC compatibility

        // handle case where deterministics are restricted to EC term
        // restriction matrix must be build
        if (isVecm == 1) {
            cd_new = new JSCNArray("cd", UMatrix.ones(y.cols(), d.cols()));

            for (int i = 0, k = 0; i < selEC.rows(); i++) {
                if (selEC.intAt(i, 0) == 0) {
                    for (int j = 0; j < cd_new.rows(); j++) {
                        cd_new.setValAt(cd.doubleAt(j, k), j, i);
                    }
                    k++; // count col in cd (without restricted)
                }
            }
        }
        JSCData[] retArgs = new JSCData[] { g_t, g_df1, g_df2, g_f, i_t, i_df,
                i_c };
        engine().call(
                "vec_causality",
                new JSCData[] { y, new JSCInt("py", py), x,
                        new JSCInt("px", px), d, cause_sel, cx, cd_new,
                        new JSCInt("isVecm", isVecm) }, retArgs);

        if (getSymbolTable() != null)
            getSymbolTable().set(retArgs);
    }
}