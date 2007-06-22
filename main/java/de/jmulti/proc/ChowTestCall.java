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
import com.jstatcom.model.JSCNumber;
import com.jstatcom.model.JSCTypeDef;
import com.jstatcom.model.JSCTypes;
import com.jstatcom.model.SymbolTable;
import com.jstatcom.ts.TSDate;
import com.jstatcom.ts.TSDateRange;
import com.jstatcom.util.FArg;
import com.jstatcom.util.UMatrix;

import de.jmulti.tools.ModelTypes;
import de.jmulti.var.VARConstants;
import de.jmulti.vecm.VECMConstants;

/**
 * Makes the GAUSS call to the bootstrapped Chow test and displays the results
 * in the output buffer. The results of this call are read back to the local
 * symbol table set with <code>toStore</code>.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
public final class ChowTestCall extends RPCall {
    private static final String Referenz = "On the reliability of Chow-type tests..., B. Candelon, H. Lütkepohl, Economic Letters 73 (2001), 155-160";

    public static final JSCTypeDef CHOW_SS_ALL = new JSCTypeDef("CHOW_SS_ALL",
            JSCTypes.NARRAY, "sample split chow test for range");

    public static final JSCTypeDef CHOW_BP_ALL = new JSCTypeDef("CHOW_BP_ALL",
            JSCTypes.NARRAY, "break point chow test for range");

    public static final JSCTypeDef CHOW_FC_ALL = new JSCTypeDef("CHOW_FC_ALL",
            JSCTypes.NARRAY, "forecast chow test for range");

    public static final JSCTypeDef CHOW_SS = new JSCTypeDef("CHOW_SS",
            JSCTypes.NARRAY, "sample split chow test for single point");

    public static final JSCTypeDef CHOW_BP = new JSCTypeDef("CHOW_BP",
            JSCTypes.NARRAY, "break point chow test for single point");

    public static final JSCTypeDef CHOW_FC = new JSCTypeDef("CHOW_FC",
            JSCTypes.NARRAY, "forecast chow test for single point");

    public static final JSCTypeDef CHOW_EDF = new JSCTypeDef("CHOW_EDF",
            JSCTypes.NARRAY, "degrees of freedom for chow tests");

    public static final JSCTypeDef CHOW_INDEX = new JSCTypeDef("CHOW_INDEX",
            JSCTypes.NARRAY, "indizes where chow test was computed");

    private SymbolTable toStore = null;

    private TSDateRange range = null;

    private ModelTypes modelType;

    private int bootRep;

    private int startBreak;

    private int endBreak;

    private int grid;

    private boolean showGraph;

    private JSCNArray chow_ss_all = null;

    private JSCNArray chow_bp_all = null;

    private JSCNArray chow_fc_all = null;

    private JSCNArray index_all = null;

    private JSCNArray chow_bp = null;

    private JSCNArray chow_ss = null;

    private JSCNArray chow_fc = null;

    private JSCNArray edf = null;

    private boolean isSubset = false;

    /**
     * ChowTestCall constructor comment.
     * 
     * @param gst
     *            symbol table to fetch input data from
     * @param toStore
     *            symbol table to store results to
     */
    public ChowTestCall(SymbolTable gst, SymbolTable toStore,
            TSDateRange range, ModelTypes modelType, int bootRep,
            int startBreak, int endBreak, int grid, boolean showGraph) {

        super();

        setSymbolTable(gst);
        setName("Bootstrapped Chow test");
        this.modelType = modelType;
        this.bootRep = bootRep;
        this.startBreak = startBreak;
        this.endBreak = endBreak;
        this.grid = grid;
        this.range = range;
        this.showGraph = showGraph;
        this.toStore = toStore;

    }

    /**
     * Prints out results for search.
     */
    private void displayAllResults() {
        double chow_ss_all_Result[][] = chow_ss_all.doubleArray();
        double chow_bp_all_Result[][] = chow_bp_all.doubleArray();
        double chow_fc_all_Result[][] = chow_fc_all.doubleArray();
        double index_all_Result[][] = index_all.doubleArray();

        StringBuffer buffer = output;

        // transform the index back into a date
        if (isSubset)
            buffer.append("\n(F p-values not valid with subset restrictions)");

        buffer
                .append(FArg
                        .sprintf(
                                "\n%-15s%-10s%-13s%-13s%-6s%-10s%-13s%-13s%-6s%-10s%-13s%-10s%-6s%-6s\n",
                                new FArg("break date").add("chow_bp").add(
                                        "boot p-val").add("chi^2 p-val").add(
                                        "df").add("chow_ss").add("boot p-val")
                                        .add("chi^2 p-val").add("df").add(
                                                "chow_fc").add("boot p-val")
                                        .add("F p-val").add("df1").add("df2")));
        for (int i = 0; i < index_all_Result.length; i++) {
            TSDate dat = range.lowerBound().addPeriods(
                    new Double(index_all_Result[i][0]).intValue() - 1);
            String ind = dat.toString();
            if (!new Double(chow_ss_all_Result[0][i]).equals(JSCConstants.NaN)
                    || !new Double(chow_bp_all_Result[0][i])
                            .equals(JSCConstants.NaN))
                buffer
                        .append(FArg
                                .sprintf(
                                        "%-14s%- 10.4f%- 13.4f%- 13.4f%- 6.0f%- 10.4f%- 13.4f%- 13.4f%- 6.0f%- 10.4f%- 13.4f%- 10.4f%- 6.0f%- 6.0f\n",
                                        new FArg(ind).add(
                                                chow_bp_all_Result[0][i]).add(
                                                chow_bp_all_Result[1][i]).add(
                                                chow_bp_all_Result[2][i]).add(
                                                chow_bp_all_Result[3][i]).add(
                                                chow_ss_all_Result[0][i]).add(
                                                chow_ss_all_Result[1][i]).add(
                                                chow_ss_all_Result[2][i]).add(
                                                chow_ss_all_Result[3][i]).add(
                                                chow_fc_all_Result[0][i]).add(
                                                chow_fc_all_Result[1][i]).add(
                                                chow_fc_all_Result[2][i]).add(
                                                chow_fc_all_Result[3][i]).add(
                                                chow_fc_all_Result[4][i])));
            else
                buffer
                        .append(FArg
                                .sprintf(
                                        "%-14s%-10s%-13s%-13s%-6s%-10s%-13s%-13s%-6s%- 10.4f%- 13.4f%- 10.4f%- 6.0f%- 6.0f\n",
                                        new FArg(ind).add(" NaN").add(" NaN")
                                                .add(" NaN").add(" NaN").add(
                                                        " NaN").add(" NaN")
                                                .add(" NaN").add(" NaN")
                                                .add(chow_fc_all_Result[0][i])
                                                .add(chow_fc_all_Result[1][i])
                                                .add(chow_fc_all_Result[2][i])
                                                .add(chow_fc_all_Result[3][i])
                                                .add(chow_fc_all_Result[4][i])));

        }
        buffer.append("\n");

    }

    /**
     * Prints out results for single break.
     */
    private void displaySingleResult() {

        double chow_bp_Result[][] = chow_bp.doubleArray();
        double chow_ss_Result[][] = chow_ss.doubleArray();
        double chow_fc_Result[][] = chow_fc.doubleArray();

        StringBuffer buffer = output;

        buffer.append(FArg.sprintf(
                "%-28s %s (%.0i observations before break)\n\n", new FArg(
                        "tested break date:").add(
                        range.lowerBound().addPeriods(startBreak).toString())
                        .add(startBreak)));

        //chow_bp|Chow_BP_p_value|Chi_2_p_value|BP_k
        if (!new Double(chow_bp_Result[0][0]).equals(JSCConstants.NaN)) {
            buffer.append(FArg.sprintf("%-27s %- 8.4f \n", new FArg(
                    "break point Chow test:").add(chow_bp_Result[0][0])));
            buffer.append(FArg.sprintf("%-27s %- 8.4f \n", new FArg(
                    " bootstrapped p-value:").add(chow_bp_Result[1][0])));
            buffer.append(FArg.sprintf("%-27s %- 8.4f \n", new FArg(
                    " asymptotic chi^2 p-value:").add(chow_bp_Result[2][0])));
            buffer.append(FArg.sprintf("%-27s  %.0f \n\n", new FArg(
                    " degrees of freedom:").add(chow_bp_Result[3][0])));
        } else
            buffer
                    .append("break point Chow test not possible for given break date\n");

        //chow_ss|Chow_SS_p_value|Chi_2_p_value|SS_k
        if (!new Double(chow_ss_Result[0][0]).equals(JSCConstants.NaN)) {
            buffer.append(FArg.sprintf("%-27s %- 8.4f \n", new FArg(
                    "sample split Chow test:").add(chow_ss_Result[0][0])));
            buffer.append(FArg.sprintf("%-27s %- 8.4f \n", new FArg(
                    " bootstrapped p-value:").add(chow_ss_Result[1][0])));
            buffer.append(FArg.sprintf("%-27s %- 8.4f \n", new FArg(
                    " asymptotic chi^2 p-value:").add(chow_ss_Result[2][0])));
            buffer.append(FArg.sprintf("%-27s  %.0f \n\n", new FArg(
                    " degrees of freedom:").add(chow_ss_Result[3][0])));
        } else
            buffer
                    .append("sample split Chow test not possible for given break date\n");

        //chow_FC|Chow_FC_p_value|F_dist_p_value|FC_n1|FC_n2

        buffer.append(FArg.sprintf("%-27s %- 8.4f \n", new FArg(
                "Chow forecast test:").add(chow_fc_Result[0][0])));
        buffer.append(FArg.sprintf("%-27s %- 8.4f \n", new FArg(
                " bootstrapped p-value:").add(chow_fc_Result[1][0])));
        if (isSubset)
            buffer.append(" (F p-value not valid with subset restrictions)\n");
        buffer.append(FArg.sprintf("%-27s %- 8.4f \n", new FArg(
                " asymptotic F p-value:").add(chow_fc_Result[2][0])));
        buffer.append(FArg.sprintf("%-27s  %.0f, %.0f \n\n", new FArg(
                " degrees of freedom:").add(chow_fc_Result[3][0]).add(
                chow_fc_Result[4][0])));

    }

    protected void finalCode() {

        StringBuffer buffer = output;

        buffer.append(FArg.sprintf("%s\n", new FArg(
                "CHOW TEST FOR STRUCTURAL BREAK\n" + Referenz + "\n")));

        buffer.append(range.format("sample range:", 29) + "\n");

        if (startBreak == endBreak)
            displaySingleResult();
        else
            displayAllResults();

    }

    /**
     * @see ProcCall
     */
    protected void runCode() {

        SymbolTable sTable = getSymbolTable();

        engine().load("pgraph", GaussLoadTypes.LIB);
        engine().load("tools", GaussLoadTypes.LIB);
        engine().load("plot", GaussLoadTypes.LIB);

        engine().load("var", GaussLoadTypes.LIB);
        engine().load("vec", GaussLoadTypes.LIB);
        engine().load("stab", GaussLoadTypes.LIB);

        if (toStore != null) {
            toStore.get(CHOW_BP).clear();
            toStore.get(CHOW_BP_ALL).clear();
            toStore.get(CHOW_FC).clear();
            toStore.get(CHOW_FC_ALL).clear();
            toStore.get(CHOW_SS).clear();
            toStore.get(CHOW_SS_ALL).clear();
            toStore.get(CHOW_EDF).clear();
            toStore.get(CHOW_INDEX).clear();
        }

        JSCNArray varBuffer = new JSCNArray("varBuffer");

        if (modelType == ModelTypes.VAR) {

            JSCNArray restrictions = new JSCNArray("restrictions");
            restrictions.appendCols(sTable.get(VARConstants.Cy_Def)
                    .getJSCNArray());
            restrictions.appendCols(sTable.get(VARConstants.Cx_Def)
                    .getJSCNArray());
            restrictions.appendCols(sTable.get(VARConstants.Cd_Def)
                    .getJSCNArray());

            double[] vecRes = restrictions.vec();
            if (UMatrix.getNonzeroDoubleCount(vecRes) < vecRes.length)
                isSubset = true;

            VAREstimationCall.setVarBuffer(engine(), sTable, varBuffer);

        } else if (modelType == ModelTypes.VECM) {

            JSCNArray restrictions = new JSCNArray("restrictions");
            restrictions.appendCols(sTable.get(VECMConstants.S_G_Def)
                    .getJSCNArray());
            restrictions.appendCols(sTable.get(VECMConstants.S_B_Def)
                    .getJSCNArray());
            restrictions.appendCols(sTable.get(VECMConstants.S_C_VEC_Def)
                    .getJSCNArray());
            restrictions.appendCols(sTable.get(VECMConstants.S_alpha_Def)
                    .getJSCNArray());

            double[] vecRes = restrictions.vec();
            if (UMatrix.getNonzeroDoubleCount(vecRes) < vecRes.length)
                isSubset = true;

            int strategy = sTable.get(VECMConstants.estimationStrategy_Def)
                    .getJSCInt().intVal();

            VECMEstimationCall.setVecBuffer(engine(), sTable, varBuffer,
                    strategy);

        } else
            throw new IllegalStateException("Unknown modeltype " + modelType
                    + ".");

        if (startBreak < endBreak) {
            if (showGraph)
                setGlobalPgraphSettings();

            chow_bp_all = (JSCNArray) CHOW_BP_ALL.getInstance();
            chow_ss_all = (JSCNArray) CHOW_SS_ALL.getInstance();
            chow_fc_all = (JSCNArray) CHOW_FC_ALL.getInstance();
            edf = (JSCNArray) CHOW_EDF.getInstance();
            index_all = (JSCNArray) CHOW_INDEX.getInstance();

            engine().call(
                    "stab_call_wrapper_all",
                    new JSCData[] {
                            varBuffer,
                            new JSCInt("grid", grid),
                            new JSCInt("bootRep", bootRep),
                            new JSCInt("seed", 0),
                            new JSCInt("showGraph", showGraph),
                            new JSCNumber("startPlot", range.lowerBound()
                                    .doubleValue()),
                            new JSCInt("subPeriodicity", range.lowerBound()
                                    .subPeriodicity()),
                            new JSCInt("startIndex", (startBreak + 1)),
                            new JSCInt("endIndex", (endBreak + 1)) },
                    new JSCData[] { chow_bp_all, chow_ss_all, chow_fc_all, edf,
                            index_all });

            if (toStore != null) {
                toStore.set(chow_bp_all);
                toStore.set(chow_ss_all);
                toStore.set(chow_fc_all);
                toStore.set(edf);
                toStore.set(index_all);
            }
        } else {
            chow_bp = (JSCNArray) CHOW_BP.getInstance();
            chow_ss = (JSCNArray) CHOW_SS.getInstance();
            chow_fc = (JSCNArray) CHOW_FC.getInstance();
            edf = (JSCNArray) CHOW_EDF.getInstance();

            engine().call(
                    "stab_call_wrapper",
                    new JSCData[] {
                            varBuffer,
                            new JSCInt("breakIndex", (startBreak + 1)),
                            new JSCInt("bootRep", bootRep),
                            new JSCInt("seed", 0),
                            new JSCInt("isGraph", 0),
                            new JSCNumber("startPlot", range.lowerBound()
                                    .doubleValue()),
                            new JSCInt("subPeriodicity", range.lowerBound()
                                    .subPeriodicity()), },
                    new JSCData[] { chow_bp, chow_ss, chow_fc, edf });

            if (toStore != null) {
                toStore.set(chow_bp);
                toStore.set(chow_ss);
                toStore.set(chow_fc);
                toStore.set(edf);
            }
        }
    }
}