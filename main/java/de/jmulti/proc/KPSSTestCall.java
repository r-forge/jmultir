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
import com.jstatcom.model.JSCNumber;
import com.jstatcom.model.JSCString;
import com.jstatcom.model.JSCTypeDef;
import com.jstatcom.model.JSCTypes;
import com.jstatcom.ts.TSDateRange;
import com.jstatcom.util.FArg;

/**
 * This GAUSS command object computes the KPSS test and stores the result in a
 * symbol table if one is set.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public final class KPSSTestCall extends RPCall {
    public static final JSCTypeDef KPSS_RESULT = new JSCTypeDef("KPSS_RESULT",
            JSCTypes.NUMBER, "KPSS test statistic");

    private int lags = 0;

    private JSCNArray y = null;

    private JSCString yName = null;

    private TSDateRange range = null;

    private int testState = 0;

    private JSCNumber result = null;

    /**
     * <code>KPSSTestCall</code> constructor takes the arguments for the
     * procedure call.
     * 
     * @param testState
     *            <ul>
     *            <li>1 -- level stationarity
     *            <li>2 -- trend stationarity
     *            </ul>
     */
    public KPSSTestCall(JSCNArray y, JSCString yName, TSDateRange range,
            int lags, int testState) {

        super();

        setName("KPSS Test");

        this.lags = lags;
        this.range = range;
        this.y = y;
        this.yName = yName;
        this.testState = testState;

    }

    /**
     * Writes the text output to a string that can be referenced via
     * <code>getOutput</code>.
     */
    protected void finalCode() {

        StringBuffer buffer = output;
        buffer.append(FArg.sprintf("%-22s %s \n", new FArg(
                "KPSS test for series:").add(yName.string())));
        buffer.append(range.format("sample range:", 23) + "\n");

        buffer.append(FArg.sprintf("%-22s %i \n", new FArg("number of lags:")
                .add(lags)));
        if (testState == 1) {
            buffer
                    .append("KPSS test based on y(t)=a+e(t) (level stationarity)\n");
            buffer.append("asymptotic critical values:\n");
            buffer.append(FArg.sprintf(
                    "%-10s %-10s %-10s \n%-10s %-10s %-10s\n", new FArg(" 10%")
                            .add(" 5%").add(" 1%").add("0.347").add("0.463")
                            .add("0.739")));
        }
        if (testState == 2) {
            buffer
                    .append("KPSS test based on y(t)=a+bt+e(t) (trend stationarity)\n");
            buffer.append("asymptotic critical values:\n");
            buffer.append(FArg.sprintf(
                    "%-10s %-10s %-10s \n%-10s %-10s %-10s\n", new FArg(" 10%")
                            .add(" 5%").add(" 1%").add("0.119").add("0.146")
                            .add("0.216")));
        }
        buffer.append(FArg.sprintf("value of test statistic: %.4f\n", new FArg(
                result.doubleVal())));
        buffer
                .append("reference: reprinted from JOURNAL OF ECONOMETRICS,\nVol 54, No 1, 1992, pp 159-178, Kwiatkowski et al:\n\"Testing the null hypothesis of stationarity ...\",\nwith permission from Elsevier Science\n");

        buffer.append("\n");
    }

    /**
     * @see RPCall
     */
    protected void runCode() {

        if (getSymbolTable() != null) {
            getSymbolTable().get(KPSS_RESULT).clear();
        }
        if (y.rows() < 2)
            throw new RuntimeException("Not enough observations in sample "
                    + range + ".");

        result = (JSCNumber) KPSS_RESULT.getInstance();
        if (testState == 1)
            result.setJSCProperty(RArgProps.RLIST_KEY, "levelst");
        else
            result.setJSCProperty(RArgProps.RLIST_KEY, "trendst");

        engine().load("jm_kpss.R", RStatLoadTypes.USERCODE);
        engine().call("jm_kpss", new JSCData[] { y, new JSCInt("lags", lags) },
                new JSCData[] { result });

        if (getSymbolTable() != null)
            getSymbolTable().set(result);

    }
}