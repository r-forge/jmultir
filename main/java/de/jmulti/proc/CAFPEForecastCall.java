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
import com.jstatcom.model.JSCNumber;
import com.jstatcom.model.JSCString;
import com.jstatcom.ts.TSDate;
import com.jstatcom.util.UString;

/**
 * Call to one-step ahead forecast or rolling over forecast.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
public final class CAFPEForecastCall extends RPCall {
    private JSCNArray y;

    private String name;

    private TSDate start;

    private JSCNArray lagsGrid;

    private File outFile;

    private String model;

    private String searchStrat;

    private String selCrit;

    private String startStrat;

    private boolean stand;

    private int modelLagSel;

    private double percRollEnd;

    private boolean isRollOver;

    private double bwFactor;

    private double ciLevel;

    private int maxLag;

    private int dMax;

    /**
     *  
     */
    public CAFPEForecastCall(JSCNArray y, String name, int maxLag, int dMax,
            TSDate start, JSCNArray lagsGrid, File outFile, String model,
            String searchStrat, String selCrit, String startStrat,
            boolean stand, int modelLagSel, double percRollEnd, double ciLevel,
            boolean isRollOver, double bwFactor) {

        super();
        setName("Forecast with CAFPE");

        this.y = y;
        this.name = name;
        this.maxLag = maxLag;
        this.dMax = dMax;
        this.start = start;
        this.lagsGrid = lagsGrid;
        this.outFile = outFile;
        this.model = model;
        this.searchStrat = searchStrat;
        this.selCrit = selCrit;
        this.startStrat = startStrat;
        this.stand = stand;
        this.modelLagSel = modelLagSel;
        this.percRollEnd = percRollEnd;
        this.isRollOver = isRollOver;
        this.bwFactor = bwFactor;
        this.ciLevel = ciLevel;

    }

    public void runCode() {

        // not for GRTE but GAUSS comm, Win32 only
        engine().load("dlib/cafpe", GaussLoadTypes.SYS);

        engine().load("pgraph", GaussLoadTypes.LIB);
        engine().load("tools", GaussLoadTypes.LIB);
        engine().load("plot", GaussLoadTypes.LIB);
        engine().load("cafp", GaussLoadTypes.LIB);

        int nRollEnd = (int) (y.rows() * percRollEnd);

        engine().call(
                "cafpe_forc_nonparam",
                new JSCData[] {
                        y,
                        new JSCInt("maxlag", maxLag),
                        new JSCInt("dMax", dMax),
                        new JSCString("fName", UString.replaceAllSubStrings(
                                outFile.toString(), "\\", "/")),
                        new JSCString("model", model),
                        new JSCString("searchStrat", searchStrat),
                        new JSCString("selCrit", selCrit),
                        new JSCString("startStrat", startStrat),
                        new JSCInt("verb", false),
                        new JSCString("isstand", (stand ? "yes" : "no")),
                        new JSCInt("seastart", start.subPeriod()), lagsGrid,
                        new JSCNumber("ciLevel", ciLevel),
                        new JSCInt("modelLagSel", modelLagSel),
                        new JSCInt("nRollEnd", nRollEnd),
                        new JSCInt("isRollOver", isRollOver),
                        new JSCNumber("bwFactor", bwFactor) }, null);

    }

    public void finalCode() {

        StringBuffer buffer = output;
        buffer.append("selected series: \"" + name + "\"\n");
        buffer.append(FileSupport.getInstance().readTextFile(outFile.getAbsolutePath()));
        buffer.append("\n\n");

    }
}
