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

import com.jstatcom.engine.PCall;
import com.jstatcom.engine.rstat.RArgProps;
import com.jstatcom.engine.rstat.RStatLoadTypes;
import com.jstatcom.model.JSCData;
import com.jstatcom.model.JSCInt;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.JSCSArray;
import com.jstatcom.model.JSCTypeDef;
import com.jstatcom.model.JSCTypes;
import com.jstatcom.ts.TSDate;
import com.jstatcom.ts.TSDateRange;
import com.jstatcom.util.FArg;
import com.jstatcom.util.UData;

/**
 * This GAUSS command object computes the information criteria AIC, HC, FPE and
 * SC for different models according to the parametrization. The result of this
 * call is stored to the symbol table if one has been set.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
public final class InfoCritCall extends RPCall {
    public static final JSCTypeDef INFO_OPT = new JSCTypeDef("info_opt",
            JSCTypes.NARRAY,
            "Optimal lags by inforiteria in the order: AIC FPE HC SC");

    public static final JSCTypeDef INFO_VALS = new JSCTypeDef("infocritvals",
            JSCTypes.NARRAY,
            "Values of infocriteria in the order: AIC FPE HC SC");

    private JSCNArray infoOpt = null;

    private boolean maxLagAdjusted = false;

    private JSCNArray infoCrit = null;

    private JSCSArray endNames = null;

    private JSCSArray exNames = null;

    private JSCSArray detNames = null;

    private JSCNArray endData = null;

    private JSCNArray exData = null;

    private JSCNArray detData = null;

    private int px = 0;

    private TSDate startDate = null;

    private int maxEndLag = 0;

    private int diff = 0;

    /**
     * <code>InfoCritCall</code> constructor takes the arguments for the
     * procedure call.
     * 
     * @param diff
     *            sets whether model is specified in differences, 0 means
     *            levels, 1 means 1st differences, etc., the suggested lag
     *            length is printed in differences as well
     */
    public InfoCritCall(int maxEndLag, int px, JSCSArray endNames,
            JSCSArray exNames, JSCSArray detNames, JSCNArray endData,
            JSCNArray exData, JSCNArray detData, TSDate startDate, int diff) {

        // INPUT CHECKING SHOULD BE DONE!

        super();
        setName("Computation of Infocriteria");

        this.maxEndLag = maxEndLag;
        this.px = px;
        this.endNames = endNames;
        this.exNames = exNames;
        this.detNames = detNames;
        this.endData = endData;
        this.exData = exData;
        this.detData = detData;
        this.startDate = startDate;
        this.diff = diff;

    }

    /**
     * <code>InfoCritCall</code> constructor takes the arguments for the
     * procedure call. This constructor is for models without exogenous data.
     * 
     * @param diff
     *            sets whether model is specified in differences, 0 means
     *            levels, 1 means 1st differences, etc., the suggested lag
     *            length is printed in differences as well
     * 
     */
    public InfoCritCall(int maxEndLag, JSCSArray endNames, JSCSArray detNames,
            JSCNArray endData, JSCNArray detData, TSDate startDate, int diff) {

        this(maxEndLag, 0, endNames, new JSCSArray("exnames"), detNames,
                endData, new JSCNArray("exdata"), detData, startDate, diff);

    }

    /**
     * Writes the text output to a string that can be referenced via
     * <code>getOutput</code>.
     */
    protected void finalCode() {

        output.append("OPTIMAL ENDOGENOUS LAGS FROM INFORMATION CRITERIA\n\n");

        if (endNames != null && endNames.rows() > 0) {
            output.append(FArg.sprintf("%-25s %s \n", new FArg(
                    "endogenous variables:")
                    .add(UData.stringForArray(endNames))));
        }
        if (exNames != null && exNames.rows() > 0) {
            output
                    .append(FArg.sprintf("%-25s %s \n", new FArg(
                            "exogenous variables:").add(UData
                            .stringForArray(exNames))));
            output.append(FArg.sprintf("%-25s %i \n", new FArg(
                    "exogenous lags (fixed):").add(px)));
        }
        if (detNames != null && detNames.rows() > 0) {
            output.append(FArg.sprintf("%-25s %s \n", new FArg(
                    "deterministic variables:").add(UData
                    .stringForArray(detNames))));
        }
        TSDateRange range = new TSDateRange(startDate.addPeriods(maxEndLag
                + diff), startDate.addPeriods(endData.rows() - 1));

        output.append(range.format("sample range:", 26) + "\n\n");

        if (diff > 0)
            output.append("optimal number of lags (searched up to " + maxEndLag
                    + " lags of " + diff + ". differences"
                    + (maxLagAdjusted ? ", max lag adjusted" : "") + "):\n");
        else
            output.append("optimal number of lags (searched up to " + maxEndLag
                    + " lags of levels"
                    + (maxLagAdjusted ? ", max lag adjusted" : "") + "):\n");
        output.append(FArg.sprintf("%-25s %-13i\n", new FArg(
                "Akaike Info Criterion:").add(infoOpt.intAt(0, 0) - diff)));
        output.append(FArg.sprintf("%-25s %-13i\n", new FArg(
                "Final Prediction Error:").add(infoOpt.intAt(1, 0) - diff)));
        output.append(FArg.sprintf("%-25s %-13i\n", new FArg(
                "Hannan-Quinn Criterion:").add(infoOpt.intAt(2, 0) - diff)));
        output.append(FArg.sprintf("%-25s %-13i\n\n", new FArg(
                "Schwarz Criterion:").add(infoOpt.intAt(3, 0) - diff)));
        if (getSymbolTable() != null) {
            getSymbolTable().set(infoCrit);
            getSymbolTable().set(infoOpt);
        }
    }

    /**
     * @see PCall
     */
    protected void runCode() {

        if (getSymbolTable() != null) {
            getSymbolTable().get(INFO_OPT).clear();
            getSymbolTable().get(INFO_VALS).clear();
        }

        // Check rank of lag truncated deterministics, because dummies might
        // have
        // a break in the lag range, causing a singular regressor.
        if (!detData.isEmpty()) {
            for (int i = maxEndLag + diff; i > 0; i--) {
                if (UData.hasFullColumnRank(detData, i)) {
                    if (i < maxEndLag + diff)
                        maxLagAdjusted = true;
                    maxEndLag = i - diff;
                    break;
                }
                if (i == 1)
                    throw new RuntimeException(
                            "Deterministic data does not have full column rank.");
            }
        }

        engine().load("jm_infocrit.R", RStatLoadTypes.USERCODE);
        engine().load("lagn.R", RStatLoadTypes.USERCODE);

        infoCrit = (JSCNArray) INFO_VALS.getInstance();
        infoCrit.setJSCProperty(RArgProps.RLIST_KEY, "critMat");
        
        infoOpt = (JSCNArray) INFO_OPT.getInstance();
        infoOpt.setJSCProperty(RArgProps.RLIST_KEY, "optLags");
        
        engine().call(
                "jm_infocrit",
                new JSCData[] { endData, exData, detData,
                        new JSCInt("diff", diff),
                        new JSCInt("maxLags", (maxEndLag + diff)),
                        new JSCInt("px", px) },
                new JSCData[] { infoCrit, infoOpt });

        if (getSymbolTable() != null) {
            getSymbolTable().set(infoCrit);
            getSymbolTable().set(infoOpt);
        }
    }
}