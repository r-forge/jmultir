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

import com.jstatcom.engine.rstat.RArgProps;
import com.jstatcom.engine.rstat.RStatLoadTypes;
import com.jstatcom.model.JSCData;
import com.jstatcom.model.JSCInt;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.JSCString;
import com.jstatcom.model.JSCTypeDef;
import com.jstatcom.model.JSCTypes;
import com.jstatcom.ts.TSDateRange;
import com.jstatcom.util.FArg;

/**
 * Computes the ADF test and stores the results in a symbol table if one has
 * been set. The symbol names in that table are given by the type definitions.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
public final class ADFTestCall extends RPCall {
    /**
     * ADF test result: coef~t-stat.
     */
    public static final JSCTypeDef ADF_RESULT = new JSCTypeDef("ADF_RESULT",
            JSCTypes.NARRAY, "ADF test result: coef~t-stat");

    /**
     * ADF residuals from test regression.
     */
    public static final JSCTypeDef ADF_RESIDS = new JSCTypeDef("ADF_RESIDS",
            JSCTypes.NARRAY, "ADF residuals from test regression");

    private int lags = 0;

    private JSCNArray y = null;

    private JSCString yName = null;

    private TSDateRange range = null;

    private int testState = 0;

    private boolean isSeasDum = false;

    private JSCNArray adf_result = null;

    /**
     * <code>ADFTestCall</code> constructor takes the arguments for the
     * procedure call.
     * 
     * @param y
     *            data to be tested
     * @param yName
     *            name of series to test, can be <code>null</code> or empty
     * @param range
     *            sample range, used for display and seasonal dummy generation
     * @param lags
     *            lags in differences to include
     * @param testState
     *            <ul>
     *            <li>1 -- no intercept,no seasonal dummies, no timetrend
     *            <li>2 -- intercept,no seasonal dummies, no timetrend
     *            <li>3 -- intercept, no seasonal dummies, time trend
     *            <li>4 -- intercept, seasonal dummies, timetrend
     *            <li>5 -- intercept, seasonal dummies, no timetrend
     *            </ul>
     * @param isSeasDum
     *            <code>true</code> if test should be done with seasonal
     *            dummies
     * @throws IllegalArgumentException
     *             if <code>null</code> argument, <br>
     *             or <code>if (lags < 0)</code>, <br>
     *             or <code>if (testState < 1 || testState > 5)</code>, <br>
     *             or <code>if (y.isEmpty() || y.cols() > 1)</code>
     */
    public ADFTestCall(JSCNArray y, JSCString yName, TSDateRange range,
            int lags, int testState, boolean isSeasDum) {

        super();

        if (yName == null || yName.isEmpty())
            yName = new JSCString("yName", "data");

        if (y == null || range == null)
            throw new IllegalArgumentException("Argument was null.");

        if (y.isEmpty() || y.cols() > 1)
            throw new IllegalArgumentException(
                    "Data was not a univariate series: " + y);

        if (lags < 0)
            throw new IllegalArgumentException("Lags < 0.");

        if (testState < 1 || testState > 5)
            throw new IllegalArgumentException("Invalid teststate " + testState
                    + ".");

        setName("ADF Test");

        this.lags = lags;
        this.range = range;
        this.y = y;
        this.yName = yName;
        this.testState = testState;
        this.isSeasDum = isSeasDum;

    }

    /**
     * Gets string with critical values.
     */
    private void addCriticalValues(int testState) {
        StringBuffer buffer = output;
        String ref = "asymptotic critical values\nreference: Davidson, R. and MacKinnon, J. (1993),\n\"Estimation and Inference in Econometrics\" p 708, table 20.1,\nOxford University Press, London\n";
        if (testState == 1) {
            buffer.append("no intercept, no time trend\n");
            buffer.append(ref);
            buffer.append(FArg.sprintf(
                    "%-10s %-10s %-10s \n%-10s %-10s %-10s\n", new FArg(" 1%")
                            .add(" 5%").add(" 10%").add("-2.56").add("-1.94")
                            .add("-1.62")));
        }
        if (testState == 2) {
            buffer.append("intercept, no time trend\n");
            buffer.append(ref);
            buffer.append(FArg.sprintf(
                    "%-10s %-10s %-10s \n%-10s %-10s %-10s\n", new FArg(" 1%")
                            .add(" 5%").add(" 10%").add("-3.43").add("-2.86")
                            .add("-2.57")));
        }
        if (testState == 3) {
            buffer.append("intercept, time trend\n");
            buffer.append(ref);
            buffer.append(FArg.sprintf(
                    "%-10s %-10s %-10s \n%-10s %-10s %-10s\n", new FArg(" 1%")
                            .add(" 5%").add(" 10%").add("-3.96").add("-3.41")
                            .add("-3.13")));
        }
        if (testState == 4) {
            buffer.append("intercept, time trend, seasonal dummies\n");
            buffer.append(ref);
            buffer.append(FArg.sprintf(
                    "%-10s %-10s %-10s \n%-10s %-10s %-10s\n", new FArg(" 1%")
                            .add(" 5%").add(" 10%").add("-3.96").add("-3.41")
                            .add("-3.13")));
        }
        if (testState == 5) {
            buffer.append("intercept, seasonal dummies, no time trend\n");
            buffer.append(ref);
            buffer.append(FArg.sprintf(
                    "%-10s %-10s %-10s \n%-10s %-10s %-10s\n", new FArg(" 1%")
                            .add(" 5%").add(" 10%").add("-3.43").add("-2.86")
                            .add("-2.57")));
        }

    }

    /**
     * Writes the text output to a string that can be referenced via
     * <code>getOutput</code>.
     */
    protected void finalCode() {

        StringBuffer buffer = output;
        double result[][] = adf_result.doubleArray();

        buffer.append(FArg.sprintf("%-25s %s \n", new FArg(
                "ADF Test for series:").add(yName.string())));
        buffer.append(range.addPeriodsToStart(lags + 1).format("sample range:",
                26)
                + "\n");
        buffer.append(FArg.sprintf("%-25s %i \n", new FArg(
                "lagged differences:").add(lags)));

        // Critical Values.
        addCriticalValues(testState);

        buffer.append(FArg.sprintf("value of test statistic: %.4f\n", new FArg(
                result[0][1])));
        buffer.append("regression results:\n");
        buffer.append("---------------------------------------\n");
        buffer.append(FArg.sprintf("%-13s %-13s %-13s\n", new FArg("variable")
                .add("coefficient").add("t-statistic")));
        buffer.append("---------------------------------------\n");

        buffer.append(FArg.sprintf("%-13s %- 13.4f %- 13.4f\n", new FArg(
                " x(-1)").add(result[0][0]).add(result[0][1])));
        for (int a = 1; a < lags + 1; a++)
            buffer.append(FArg.sprintf("%-13s %- 13.4f %- 13.4f\n", new FArg(
                    "dx(-" + a + ")").add(result[a][0]).add(result[a][1])));
        if (testState > 1)
            buffer.append(FArg.sprintf("%-13s %- 13.4f %- 13.4f\n", new FArg(
                    "constant").add(result[lags + 1][0]).add(
                    result[lags + 1][1])));
        if (testState == 3 || testState == 4)
            buffer
                    .append(FArg.sprintf("%-13s %- 13.4f %- 13.4f\n", new FArg(
                            "trend").add(result[lags + 2][0]).add(
                            result[lags + 2][1])));
        for (int a = 1; a < range.lowerBound().subPeriodicity(); a++) {
            if (testState == 4)
                buffer.append(FArg.sprintf("%-13s %- 13.4f %- 13.4f\n",
                        new FArg("sdummy(" + (a + 1) + ")").add(
                                result[lags + 2 + a][0]).add(
                                result[lags + 2 + a][1])));
            if (testState == 5)
                buffer.append(FArg.sprintf("%-13s %- 13.4f %- 13.4f\n",
                        new FArg("sdummy(" + (a + 1) + ")").add(
                                result[lags + 1 + a][0]).add(
                                result[lags + 1 + a][1])));
        }
        buffer.append(FArg.sprintf("%-13s %- 13.4f\n", new FArg("RSS")
                .add(result[result.length - 1][0])));
        buffer.append("\n");
    }

    /**
     * @see RPCall
     */
    protected void runCode() {

        if (getSymbolTable() != null) {
            getSymbolTable().get(ADF_RESIDS).clear();
            getSymbolTable().get(ADF_RESULT).clear();
        }

        if (y.rows() < 2 * (1 + lags))
            throw new RuntimeException("Not enough observations in sample "
                    + range + ".");

        JSCNArray seasDum = new JSCNArray("seasDum");
        if (isSeasDum)
            seasDum = new JSCNArray("seasDum", range
                    .createSeasDum(true, false));

        adf_result = (JSCNArray) ADF_RESULT.getInstance();
        adf_result.setJSCProperty(RArgProps.RLIST_KEY, "est");
        JSCNArray adf_resids = (JSCNArray) ADF_RESIDS.getInstance();
        adf_resids.setJSCProperty(RArgProps.RLIST_KEY, "resid");

        engine().load("jm_adf.R", RStatLoadTypes.USERCODE);
        engine().load("lagn.R", RStatLoadTypes.USERCODE);

        engine().call(
                "jm_adf",
                new JSCData[] { y, new JSCInt("lags", lags), seasDum,
                        new JSCInt("teststate", testState) },
                new JSCData[] { adf_result, adf_resids });

        if (getSymbolTable() != null) {
            getSymbolTable().set(adf_result);
            getSymbolTable().set(adf_resids);
        }

    }
}