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
import com.jstatcom.model.JSCSArray;
import com.jstatcom.model.JSCTypeDef;
import com.jstatcom.model.JSCTypes;
import com.jstatcom.ts.TSDateRange;
import com.jstatcom.util.FArg;
import com.jstatcom.util.UData;

/**
 * This GAUSS command object computes the Saikkonen & Lütkepohl Test for
 * cointegration. The name of the result object is "sl_out".
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
public final class SLCointTestCall extends RPCall {
    public static final JSCTypeDef SL_RESULT = new JSCTypeDef("SL_RESULT",
            JSCTypes.NARRAY,
            "S&L cointegration test: LR statistic~critical values (1%,5%,10%)~p-values");

    private int testState = 1;

    private int lagLength = 0;

    private TSDateRange range = null;

    private JSCNArray dummies = null;

    private JSCNArray endog = null;

    private boolean isSeasDum = false;

    private JSCSArray dummyNames = null;

    private JSCSArray endogNames = null;

    private JSCNArray sl_Result = null;

    /**
     * <code>SLCointTestCall</code> constructor takes the arguments for the
     * procedure call.
     */
    public SLCointTestCall(JSCNArray endog, JSCSArray endogNames,
            JSCNArray dummies, JSCSArray dummyNames, boolean isSeasDum,
            int lagLength, int testState, TSDateRange range) {

        super();
        setName("S&L Test for Cointegration");

        this.testState = testState;
        this.lagLength = lagLength;
        this.range = range;
        this.dummies = dummies;
        this.endog = endog;
        this.dummyNames = dummyNames;
        this.endogNames = endogNames;
        if (range.lowerBound().subPeriodicity() != 1)
            this.isSeasDum = isSeasDum;

    }

    /**
     * Writes the text output to a string that can be referenced via
     * <code>getOutput</code>.
     */
    protected void finalCode() {

        TSDateRange newRange = range.addPeriodsToStart(lagLength);

        output.append(FArg.sprintf("%-25s %s \n", new FArg("S&L Test for:")
                .add(UData.stringForArray(endogNames))));

        if (dummies.cols() > 0)
            output.append(FArg.sprintf("%-25s %s \n", new FArg(
                    "included dummy variables:").add(UData
                    .stringForArray(dummyNames))));

        output.append(newRange.format("sample range:", 26) + "\n");

        output.append(FArg.sprintf("%-25s %i \n", new FArg(
                "included lags (levels):").add(lagLength)));
        output.append(FArg.sprintf("%-25s %i \n", new FArg(
                "dimension of the process:").add(endogNames.rows())));
        if (testState == 1) {
            output.append("intercept included\n");
            //output.append(
            //	"for critical values see: Johansen, S.
            // (1995),\n\"Likelihood-based Inference in Cointegrated VAR
            // models\",\nOxford University Press, Oxford, Table 15.1\n");
        }
        if (testState == 2) {
            output.append("trend and intercept included\n");
            //output.append(
            //	"reference: Luetkepohl, H. & Saikkonen, P. (2000),\n\"Testing for
            // the cointegration rank of a VAR process with a time
            // trend\",\nJournal of Econometrics, vol. 95, 177-198, Table 1\n");
        }
        if (testState == 3) {
            output.append("trend orthogonal to cointegration relation\n");
            //output.append(
            //"reference: Luetkepohl, H. & Saikkonen, P. (2000),\n\"Testing for
            // the cointegration rank of a VAR process with an
            // intercept\",\nEconometric Theory, vol. 16, 373-406, Table 1\n");
        }
        if (isSeasDum)
            output.append("seasonal dummies included\n");
        output.append("response surface computed:\n");
        output.append("-----------------------------------------------\n");
        output.append(FArg.sprintf(" %-3s %-8s %-8s %-8s %-8s %-8s\n",
                new FArg("r0").add("LR").add("pval").add("90%").add("95%").add(
                        "99%")));
        output.append("-----------------------------------------------\n");
        int max = (testState == 3) ? sl_Result.rows() - 1 : sl_Result.rows();

        for (int i = 0; i < max; i++) {
            output.append(FArg.sprintf(
                    "%- 3i %- 8.2f %- 8.4f %- 8.2f %- 8.2f %- 8.2f",
                    new FArg(i).add(sl_Result.doubleAt(i, 0)).add(
                            sl_Result.doubleAt(i, 4)).add(
                            sl_Result.doubleAt(i, 1)).add(
                            sl_Result.doubleAt(i, 2)).add(
                            sl_Result.doubleAt(i, 3))));
            output.append("\n");

        }

        output.append("\n");
    }

    /**
     * @see RPCall
     */
    protected void runCode() {

        if (getSymbolTable() != null)
            getSymbolTable().get(SL_RESULT).clear();

        JSCNArray seasDum = new JSCNArray("SeasonalDummies");
        if (isSeasDum)
            seasDum = new JSCNArray("SeasonalDummies", range.createSeasDum(
                    true, false));

        JSCNArray[] splitDeterm = UData.splitDeterministics(dummies, false);
        if (!splitDeterm[0].isEmpty() || !splitDeterm[1].isEmpty()
                || !splitDeterm[2].isEmpty() || !splitDeterm[3].isEmpty())
            output.append("user specified dummies [break dates]\n");
        if (!splitDeterm[0].isEmpty()) {
            output.append("impulse(s): ");
            for (int i = 0; i < splitDeterm[0].rows(); i++) {
                output.append("["
                        + range.dateForIndex(splitDeterm[0].intAt(i, 0) - 1)
                        + "] ");
            }
            output.append("\n");
        }

        if (!splitDeterm[1].isEmpty()) {
            output.append("extended impulse(s): ");
            for (int i = 0; i < splitDeterm[1].rows(); i++) {
                output.append("["
                        + range.dateForIndex(splitDeterm[1].intAt(i, 0) - 1)
                        + "-"
                        + range.dateForIndex(splitDeterm[1].intAt(i, 1) - 1)
                        + "] ");
            }
            output.append("\n");
        }

        if (!splitDeterm[2].isEmpty()) {
            output.append("shift(s): ");
            for (int i = 0; i < splitDeterm[2].rows(); i++) {
                output.append("["
                        + range.dateForIndex(splitDeterm[2].intAt(i, 0) - 1)
                        + "] ");
            }
            output.append("\n");
        }

        if (!splitDeterm[3].isEmpty())
            output.append("Trend breaks are not used for S&L test!\n");
        output.append("\n");

        sl_Result = (JSCNArray) SL_RESULT.getInstance();

        engine().load("coi", GaussLoadTypes.LIB);
        engine().load("fil", GaussLoadTypes.LIB);
        engine().load("diag", GaussLoadTypes.LIB);

        engine().call(
                "sltest_cointsl",
                new JSCData[] { endog, splitDeterm[0], splitDeterm[1],
                        splitDeterm[2], seasDum, new JSCInt("lags", lagLength),
                        new JSCInt("testState", testState) },
                new JSCData[] { sl_Result });

        if (getSymbolTable() != null)
            getSymbolTable().set(sl_Result);
    }
}