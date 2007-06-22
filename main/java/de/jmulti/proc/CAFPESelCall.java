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

import java.io.File;

import com.jstatcom.engine.gauss.GaussLoadTypes;
import com.jstatcom.io.FileSupport;
import com.jstatcom.model.JSCData;
import com.jstatcom.model.JSCInt;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.JSCString;
import com.jstatcom.ts.TSDate;
import com.jstatcom.util.UString;

import de.jmulti.cafpe.CAFPE_constants;


/**
 * Call to nonlinear model selection via CAFPE. The results are stored in the
 * symbol table if one has been set with the names defined
 * <code>CAFPE_Constants</code>.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public final class CAFPESelCall extends RPCall {

    private JSCNArray y;

    private JSCNArray res;

    private String name;

    private TSDate start;

    private int maxLag;

    private int maxNum;

    private File outFile;

    private String model;

    private String searchStrat;

    private String selCrit;

    private String startStrat;

    private boolean verb;

    private boolean stand;

    private boolean volatSel;

    /**
     * 
     */
    public CAFPESelCall(JSCNArray y, JSCNArray res, String name, TSDate start,
            int maxLag, int maxNum, File outFile, String model,
            String searchStrat, String selCrit, String startStrat,
            boolean verb, boolean stand, boolean volatSel) {

        super();
        setName("Nonparametric Model Selection");

        this.y = y;
        this.res = res;
        this.name = name;
        this.start = start;
        this.maxLag = maxLag;
        this.maxNum = maxNum;
        this.outFile = outFile;
        this.model = model;
        this.searchStrat = searchStrat;
        this.selCrit = selCrit;
        this.startStrat = startStrat;
        this.verb = verb;
        this.stand = stand;
        this.volatSel = volatSel;

    }

    public void runCode() {

        if (getSymbolTable() != null) {
            if (volatSel) {
                getSymbolTable().get(CAFPE_constants.OPT_LAGS_VOLAT).clear();
                getSymbolTable().get(CAFPE_constants.CRMIN_VOLAT).clear();
            } else {
                getSymbolTable().get(CAFPE_constants.OPT_LAGS).clear();
                getSymbolTable().get(CAFPE_constants.CRMIN).clear();

            }
        }

        // not for GRTE but GAUSS comm, Win32 only
        engine().load("dlib/cafpe", GaussLoadTypes.SYS);

        engine().load("pgraph", GaussLoadTypes.LIB);
        engine().load("tools", GaussLoadTypes.LIB);
        engine().load("plot", GaussLoadTypes.LIB);
        engine().load("cafp", GaussLoadTypes.LIB);

        JSCData optLags = null;
        JSCData crMin = null;
        if (volatSel) {
            optLags = CAFPE_constants.OPT_LAGS_VOLAT.getInstance();
            crMin = CAFPE_constants.CRMIN_VOLAT.getInstance();
        } else {
            optLags = CAFPE_constants.OPT_LAGS.getInstance();
            crMin = CAFPE_constants.CRMIN.getInstance();
        }

        engine().call(
                "cafpe_sel_nonparam",
                new JSCData[] {
                        y,
                        res,
                        new JSCInt("maxLag", maxLag),
                        new JSCInt("maxNum", maxNum),
                        new JSCString("fName", UString.replaceAllSubStrings(
                                outFile.toString(), "\\", "/")),
                        new JSCString("model", model),
                        new JSCString("searchStrat", searchStrat),
                        new JSCString("selCrit", selCrit),
                        new JSCString("startStrat", startStrat),
                        new JSCInt("verb", verb),
                        new JSCString("stand", (stand ? "yes" : "no")),
                        new JSCInt("startperiod", start.subPeriod()) },
                new JSCData[] { optLags, crMin });

        if (getSymbolTable() != null) {
            // crmin has to be set before, because listener to optLags needs it
            getSymbolTable().set(crMin);
            getSymbolTable().set(optLags);
        }
    }

    public void finalCode() {

        StringBuffer buffer = output;
        if (volatSel)
            buffer.append("selected series for volatility model selection: \""
                    + name + "\"\n");
        else
            buffer.append("selected series for model selection: \"" + name
                    + "\"\n");

        buffer.append(FileSupport.getInstance().readTextFile(
                outFile.getAbsolutePath()));
        buffer.append("\n\n");

    }
}
