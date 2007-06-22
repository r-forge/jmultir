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
import com.jstatcom.model.JSCString;
import com.jstatcom.model.JSCTypeDef;
import com.jstatcom.model.JSCTypes;
import com.jstatcom.ts.TSDateRange;
import com.jstatcom.util.FArg;

/**
 * This GAUSS command object computes the unit root test allowing for a
 * structural break and stores the result in a symbol table if one is set under
 * the typedefs defined statically.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public final class StructBreakURCall extends RPCall {
    public static final JSCTypeDef SBREAK_RESULT = new JSCTypeDef(
            "SBREAK_RESULTS", JSCTypes.NARRAY,
            "Results of UR test with structural break.");

    public static final JSCTypeDef SBREAK_RESIDS = new JSCTypeDef(
            "SBREAK_RESIDS", JSCTypes.NARRAY,
            "Residuals from UR test with structural break.");

    public static final JSCTypeDef SBREAK_SHIFTFKT = new JSCTypeDef(
            "SBREAK_SHIFTFKT", JSCTypes.NARRAY,
            "Estimated shiftfunction from UR test with structural break.");

    public static final JSCTypeDef SBREAK_DETRES = new JSCTypeDef(
            "SBREAK_DETRES", JSCTypes.NARRAY,
            "Original series - deterministic part");

    public static final JSCTypeDef SBREAK_THETASEARCHED = new JSCTypeDef(
            "SBREAK_THETASEARCHED", JSCTypes.NARRAY, "Grid of searched thetas");

    public static final JSCTypeDef SBREAK_EST = new JSCTypeDef("SBREAK_EST",
            JSCTypes.NARRAY, "Estimated break date");

    public static final JSCTypeDef SBREAK_OBJECTIVETHETA = new JSCTypeDef(
            "SBREAK_OBJECTIVETHETA", JSCTypes.NARRAY,
            "Values of objective function to minimize for optimal theta.");

    private int lags = 0;

    private JSCNArray y = null;

    private JSCString yName = null;

    private TSDateRange range = null;

    private TSDateRange breakRange = null;

    private int shiftFunction = 0;

    private int maxIter = 1000;

    private boolean isSeasDum = false;

    private boolean isTrend = false;

    private boolean isGraph = false;

    private JSCNArray sb_result = null;

    private JSCNArray sb_estimates = null;

    /**
     * <code>StructBreakURCall</code> constructor takes the arguments for the
     * procedure call.
     * 
     * @param shiftFunction
     *            <ul>
     *            <li>0 -- impulse dummy
     *            <li>1 -- dummy shift
     *            <li>2 -- exponential shift
     *            <li>3 -- rational shift
     *            </ul>
     */
    public StructBreakURCall(JSCNArray y, JSCString yName, TSDateRange range,
            TSDateRange breakRange, int lags, int shiftFunction,
            boolean isTrend, boolean isSeasDum, boolean isGraph, int maxIter) {

        super();

        setName("UR Test with structural break");

        this.lags = lags;
        this.range = range;
        this.breakRange = breakRange;
        this.y = y;
        this.yName = yName;
        this.shiftFunction = shiftFunction;
        this.isTrend = isTrend;
        this.isSeasDum = isSeasDum;
        this.isGraph = isGraph;
        this.maxIter = maxIter;

    }

    /**
     * Insert the method's description here. Creation date: (07.02.01 19:49:45)
     */
    private void addCriticalValues(boolean trend, boolean sdum) {
        StringBuffer buffer = output;
        if (!trend && !sdum) {
            buffer.append("critical values (Lanne et al. 2002):\n");
            buffer.append("---------------------------------------\n");
            buffer.append(FArg.sprintf("%-10s %-10s %-10s %-10s\n", new FArg(
                    " T").add(" 1%").add(" 5%").add(" 10%")));
            buffer.append("---------------------------------------\n");
            // buffer.append(FArg.sprintf("%-10s %-10s %-10s %-10s\n", new
            // FArg(100).add(-3.58).add(-2.94).add(-2.62)));
            // buffer.append(FArg.sprintf("%-10s %-10s %-10s %-10s\n", new
            // FArg(500).add(-3.47).add(-2.90).add(-2.62)));
            buffer.append(FArg.sprintf("%-10s %-10s %-10s %-10s\n", new FArg(
                    1000).add(-3.48).add(-2.88).add(-2.58)));
        }
        if (!trend && sdum) {
            buffer.append("seasonal dummies included\n");
            buffer.append("critical values (Lanne et al. 2002):\n");
            buffer.append("---------------------------------------\n");
            buffer.append(FArg.sprintf("%-10s %-10s %-10s %-10s\n", new FArg(
                    " T").add(" 1%").add(" 5%").add(" 10%")));
            buffer.append("---------------------------------------\n");
            // buffer.append(FArg.sprintf("%-10s %-10s %-10s %-10s\n", new
            // FArg(100).add(-3.58).add(-2.94).add(-2.62)));
            // buffer.append(FArg.sprintf("%-10s %-10s %-10s %-10s\n", new
            // FArg(500).add(-3.47).add(-2.90).add(-2.62)));
            buffer.append(FArg.sprintf("%-10s %-10s %-10s %-10s\n", new FArg(
                    1000).add(-3.48).add(-2.88).add(-2.58)));
        }
        if (trend && !sdum) {
            buffer.append("time trend included\n");
            buffer.append("critical values (Lanne et al. 2002):\n");
            buffer.append("---------------------------------------\n");
            buffer.append(FArg.sprintf("%-10s %-10s %-10s %-10s\n", new FArg(
                    " T").add(" 1%").add(" 5%").add(" 10%")));
            buffer.append("---------------------------------------\n");
            // buffer.append(FArg.sprintf("%-10s %-10s %-10s %-10s\n", new
            // FArg(100).add(-3.73).add(-3.11).add(-2.80)));
            // buffer.append(FArg.sprintf("%-10s %-10s %-10s %-10s\n", new
            // FArg(500).add(-3.62).add(-3.08).add(-2.79)));
            buffer.append(FArg.sprintf("%-10s %-10s %-10s %-10s\n", new FArg(
                    1000).add(-3.55).add(-3.03).add(-2.76)));
        }
        if (trend && sdum) {
            buffer.append("time trend and seasonal dummies included\n");
            buffer.append("critical values (Lanne et al. 2002):\n");
            buffer.append("---------------------------------------\n");
            buffer.append(FArg.sprintf("%-10s %-10s %-10s %-10s\n", new FArg(
                    " T").add(" 1%").add(" 5%").add(" 10%")));
            buffer.append("---------------------------------------\n");
            // buffer.append(FArg.sprintf("%-10s %-10s %-10s %-10s\n", new
            // FArg(100).add(-3.73).add(-3.11).add(-2.80)));
            // buffer.append(FArg.sprintf("%-10s %-10s %-10s %-10s\n", new
            // FArg(500).add(-3.62).add(-3.08).add(-2.79)));
            buffer.append(FArg.sprintf("%-10s %-10s %-10s %-10s\n", new FArg(
                    1000).add(-3.55).add(-3.03).add(-2.76)));
        }
        buffer.append("---------------------------------------\n");

    }

    /**
     * Writes the text output to a string that can be referenced via
     * <code>getOutput</code>.
     */
    protected void finalCode() {

        if (breakRange.numOfObs() == 1)
            printTestResult();
        else
            printBreakSearchResult();
    }

    /**
     * 
     */
    protected void printBreakSearchResult() {

        StringBuffer buffer = output;
        buffer.append(FArg.sprintf("%-30s %s \n", new FArg(
                "Break date search for series:").add(yName.string())));
        buffer.append(range.addPeriodsToStart(lags + 1).format("sample range:",
                31)
                + "\n");
        buffer.append(breakRange.format("searched range:", 31) + "\n");

        buffer.append(FArg.sprintf("%-30s %i \n", new FArg(
                "number of lags (1st diff):").add(lags)));
        if (isTrend && !isSeasDum)
            buffer.append("trend included\n");
        if (isTrend && isSeasDum)
            buffer.append("trend and seasonal dummies included\n");
        if (!isTrend && isSeasDum)
            buffer.append("seasonal dummies included\n");
        buffer.append(FArg.sprintf("%-29s  "
                + range.lowerBound().addPeriods(sb_result.intAt(0, 1) - 1)
                + "\n", new FArg("suggested break date:")));

        buffer.append("\n");
    }

    /**
     * 
     */
    protected void printTestResult() {
        double[][] estimates = sb_estimates.doubleArray();
        StringBuffer buffer = output;

        buffer.append(FArg.sprintf("%-30s %s \n", new FArg(
                "UR Test with structural break for series:")
                .add(yName.string())));
        buffer.append(range.addPeriodsToStart(lags + 1).format("sample range:",
                31)
                + "\n");
        buffer.append(FArg.sprintf("%-30s %i \n", new FArg(
                "number of lags (1st diff):").add(lags)));
        buffer.append(FArg.sprintf("%-29s %- 10.4f\n", new FArg(
                "value of test statistic:").add(sb_result.doubleAt(0, 0))));
        buffer.append(FArg.sprintf("%-29s  "
                + range.lowerBound().addPeriods(sb_result.intAt(0, 1) - 1)
                + "\n", new FArg("used break date:")));
        if (shiftFunction > 1)
            buffer.append(FArg.sprintf("%-29s %- 10.4f\n", new FArg(
                    "estimated theta:").add(sb_result.doubleAt(0, 2))));
        if (shiftFunction == 0)
            buffer.append(FArg.sprintf("%-30s %s \n",
                    new FArg("shiftfunction:").add(("impulse dummy"))));
        if (shiftFunction == 1)
            buffer.append(FArg.sprintf("%-30s %s \n",
                    new FArg("shiftfunction:").add(("shift dummy"))));
        if (shiftFunction == 2)
            buffer.append(FArg.sprintf("%-30s %s \n",
                    new FArg("shiftfunction:").add(("exponential shift"))));
        if (shiftFunction == 3)
            buffer.append(FArg.sprintf("%-30s %s \n",
                    new FArg("shiftfunction:").add(("rational shift"))));
        addCriticalValues(isTrend, isSeasDum);
        buffer.append("regression results:\n");
        buffer.append("---------------------------------------\n");
        buffer.append(FArg.sprintf("%-13s %-13s %-13s\n", new FArg("variable")
                .add("coefficient").add("t-statistic")));
        buffer.append("---------------------------------------\n");
        int index = 0;
        if (isSeasDum)
            for (int a = 0; a < range.subPeriodicity() - 1; a++) {
                buffer.append(FArg.sprintf("%-13s %- 13.4f %- 13.4f\n",
                        new FArg("d(SD" + a + ")").add(estimates[a][0]).add(
                                estimates[a][1])));
                index = a + 1;
            }
        if (isTrend) {
            buffer.append(FArg.sprintf("%-13s %- 13.4f %- 13.4f\n", new FArg(
                    "d(trend)").add(estimates[index][0]).add(
                    estimates[index][1])));
            ++index;
        }
        buffer.append(FArg.sprintf("%-13s %- 13.4f %- 13.4f\n", new FArg(
                "d(const)").add(estimates[index][0]).add(estimates[index][1])));
        ++index;
        if (shiftFunction == 3) {
            buffer.append(FArg.sprintf("%-13s %- 13.4f %- 13.4f\n", new FArg(
                    "d(shiftfkt1)").add(estimates[index][0]).add(
                    estimates[index][1])));
            ++index;
            buffer.append(FArg.sprintf("%-13s %- 13.4f %- 13.4f\n", new FArg(
                    "d(shiftfkt2)").add(estimates[index][0]).add(
                    estimates[index][1])));
        } else
            buffer.append(FArg.sprintf("%-13s %- 13.4f %- 13.4f\n", new FArg(
                    "d(shiftfkt)").add(estimates[index][0]).add(
                    estimates[index][1])));
        ++index;
        int start = index;
        for (int a = index; a < start + lags; a++) {
            buffer.append(FArg.sprintf("%-13s %- 13.4f %- 13.4f\n", new FArg(
                    "dx(-" + (a - start + 1) + ")").add(estimates[a][0]).add(
                    estimates[a][1])));
        }
        buffer.append("\n");
    }

    /**
     * @see gauss.GaussProcCall
     */
    protected void runCode() {

        sb_result = null;
        sb_estimates = null;

        if (getSymbolTable() != null) {
            getSymbolTable().get(SBREAK_RESIDS).clear();
            getSymbolTable().get(SBREAK_DETRES).clear();
            getSymbolTable().get(SBREAK_EST).clear();
            getSymbolTable().get(SBREAK_OBJECTIVETHETA).clear();
            getSymbolTable().get(SBREAK_RESULT).clear();
            getSymbolTable().get(SBREAK_SHIFTFKT).clear();
            getSymbolTable().get(SBREAK_THETASEARCHED).clear();
        }

        if (y.rows() < 2 * (1 + lags))
            throw new RuntimeException("Not enough observations in sample "
                    + range + ".");

        JSCNArray seasDum = new JSCNArray("seasDum");
        if (isSeasDum)
            seasDum = new JSCNArray("seasDum", range
                    .createSeasDum(false, false));
        int[] index = range.indicesForRange(breakRange);
        int breakDateStart = index[0];
        int breakDateEnd = index[1];

        engine().load("pgraph", GaussLoadTypes.LIB);
        engine().load("tools", GaussLoadTypes.LIB);
        engine().load("plot", GaussLoadTypes.LIB);
        engine().load("ur", GaussLoadTypes.LIB);

        JSCData[] ret = new JSCData[] { SBREAK_RESULT.getInstance(),
                SBREAK_RESIDS.getInstance(), SBREAK_DETRES.getInstance(),
                SBREAK_EST.getInstance(), SBREAK_THETASEARCHED.getInstance(),
                SBREAK_OBJECTIVETHETA.getInstance(),
                SBREAK_SHIFTFKT.getInstance() };

        engine().call(
                "tauint_structbr",
                new JSCData[] { y,
                        new JSCNumber("is_Trend", (isTrend ? 1 : 0)),
                        new JSCInt("lags", (lags + 1)),
                        new JSCInt("start_break", (breakDateStart + 1)),
                        new JSCInt("end_break", (breakDateEnd + 1)),
                        new JSCInt("shift_fkt", shiftFunction), seasDum,
                        new JSCInt("max_iterat", maxIter) }, ret);

        if (isGraph && breakRange.numOfObs() == 1) {
            String titel = "UR Test with structural break: " + yName.string();
            if (shiftFunction == 0)
                titel = titel + " (impulse dummy)\n ";
            if (shiftFunction == 1)
                titel = titel + " (dummy shift)\n ";
            if (shiftFunction == 2)
                titel = titel + " (exponential shift)\n ";
            if (shiftFunction == 3)
                titel = titel + " (rational shift)\n ";

            // set config data
            setGlobalPgraphSettings();

            JSCNumber theta = new JSCNumber("theta", ((JSCNArray) ret[0])
                    .doubleAt(0, 2));
            if (shiftFunction == 1)
                theta.setVal(-1);

            engine().call(
                    "graphics_structbr",
                    new JSCData[] {
                            new JSCString("tit", titel),
                            y,
                            new JSCNumber("start", range.lowerBound()
                                    .doubleValue()),
                            new JSCInt("period", range.subPeriodicity()),
                            ret[6], ret[2], ret[4], ret[5], theta }, null);
        }
        if (getSymbolTable() != null)
            getSymbolTable().set(ret);
        sb_result = (JSCNArray) ret[0];
        sb_estimates = (JSCNArray) ret[3];
    }
}