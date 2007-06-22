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
import com.jstatcom.ts.TSDateRange;
import com.jstatcom.util.FArg;

import de.jmulti.vecm.VECMConstants;


/**
 * Computes and displays several representations of the EC term in a VEC model.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
public final class ComputeECTermCall extends RPCall {
    private int selRepEc = 0;

    int rank = 0;

    /**
     * Names of the different EC term representations
     */
    public static final String[] repNames = new String[] { "beta*Y(t-1)",
            "beta*Y(t-1)+beta_d*D(t-1)", "beta*Y(t-1)*M",
            "beta*Y(t-1)*M+beta_d*D(t-1)*M" };

    private JSCNArray result = null;

    private SymbolTable gst = null;

    public static final JSCTypeDef EC_TERM = new JSCTypeDef(
            "EC_TERM",
            JSCTypes.NARRAY,
            "The computed EC term in different representations:\nbeta'*y(t-1) ~ beta'*y(t-1) + beta_d'*d(t-1) ~ beta'*y(t-1)*M ~ beta'*y(t-1)*M  + beta_d'*d(t-1)*M");

    /**
     * PlotECTermCall constructor.
     * 
     * @param gst
     *            symbol table to fetch input data from
     * @param selRepEc
     *            choosen ec term representation
     */
    public ComputeECTermCall(SymbolTable gst, int selRepEc) {

        super();

        this.gst = gst;
        setName("Display EC Term");
        this.selRepEc = selRepEc;

    }

    /**
     * @see ProcCall
     */
    protected void runCode() {
        if (getSymbolTable() != null)
            getSymbolTable().get(EC_TERM).clear();

        engine().load("pgraph", GaussLoadTypes.LIB);
        engine().load("tools", GaussLoadTypes.LIB);
        engine().load("plot", GaussLoadTypes.LIB);
        engine().load("vec", GaussLoadTypes.LIB);
        engine().load("var", GaussLoadTypes.LIB);

        JSCNArray y = gst.get(VECMConstants.y_Def).getJSCNArray();
        JSCNArray d = gst.get(VECMConstants.d_Def).getJSCNArray();
        int estMeth = gst.get(VECMConstants.estimationStrategy_Def).getJSCInt()
                .intVal();
        rank = gst.get(VECMConstants.cointRank_Def).getJSCInt().intVal();
        JSCNArray x = new JSCNArray("x");
        // take exogenous into account only if estimation
        // method supported it
        if (estMeth == VECMConstants.TWO_STAGE)
            x = gst.get(VECMConstants.x_Def).getJSCNArray();

        JSCNArray idx = gst.get(VECMConstants.idx_cd2ci_Def).getJSCNArray();
        JSCInt py = gst.get(VECMConstants.py_Def).getJSCInt();
        JSCInt px = gst.get(VECMConstants.px_Def).getJSCInt();

        JSCNArray beta = gst.get(VECMConstants.beta_Def).getJSCNArray();
        JSCNArray beta_d = gst.get(VECMConstants.beta_d_Def).getJSCNArray();

   
        result = (JSCNArray) EC_TERM.getInstance();
        engine().call("vec_getECTerm",
                new JSCData[] { y, py, x, px, d, idx, beta, beta_d },
                new JSCData[] { result });

        if (getSymbolTable() != null)
            getSymbolTable().set(result);

    }

    protected void finalCode() {

        TSDateRange range = gst.get(VECMConstants.T1_Def).getJSCDRange()
                .getTSDateRange();
        output.append("\nEC TERM REPRESENTATION: " + repNames[selRepEc]
                + "\n\n");
        output.append(FArg.sprintf("%-15s", new FArg("date")));
        for (int i = 0; i < rank; i++)
            output
                    .append(FArg.sprintf("%-20s",
                            new FArg("EC term " + (i + 1))));

        output.append("\n\n");
        for (int i = 0; i < result.rows(); i++) {
            output.append(FArg
                    .sprintf("%-15s", new FArg(range.dateForIndex(i))));
            for (int j = 0; j < rank; j++) {
                output.append(FArg.sprintf("%- 20.4f", new FArg(result
                        .doubleAt(i, selRepEc * rank + j))));
            }
            output.append("\n");
        }

    }
}