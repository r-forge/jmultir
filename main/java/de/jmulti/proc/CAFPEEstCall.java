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

import de.jmulti.cafpe.CAFPE_constants;


/**
 * Call to nonlinear model estimation via CAFPE. The results are stored in the
 * symbol table if one has been set with the names defined
 * <code>CAFPE_Constants</code>.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
public final class CAFPEEstCall extends RPCall {
    private JSCNArray y;

    private JSCNArray res;

    private String name;

    private TSDate start;

    private JSCNArray lagsGrid;

    private JSCNArray gridVar;

    private File outFile;

    private String model;

    private String searchStrat;

    private String selCrit;

    private String startStrat;

    private boolean verb;

    private boolean stand;

    private JSCNArray xConst;

    private JSCNArray xConstAll;

    private double perGridRemove;

    private int gridNum;

    private boolean isCI;

    private double ciLevel;

    private boolean isBonfCI;

    private double bwFactor;

    private boolean showGraph;

    private int rotateAngle;

    private boolean volatEst;

    private boolean isYZero;

    /**
     *  
     */
    public CAFPEEstCall(JSCNArray y, JSCNArray res, String name, TSDate start,
            JSCNArray lagsGrid, JSCNArray gridVar, File outFile, String model,
            String searchStrat, String selCrit, String startStrat,
            JSCNArray xConst, JSCNArray xConstAll, boolean verb, boolean stand,
            double perGridRemove, int gridNum, boolean isCI, double ciLevel,
            boolean isBonfCI, double bwFactor, boolean showGraph,
            int rotateAngle, boolean volatEst, boolean isYZero) {

        super();
        setName("Mean Estimation with CAFPE");

        this.y = y;
        this.res = res;
        this.name = name;
        this.start = start;
        this.gridVar = gridVar;
        this.lagsGrid = lagsGrid;
        this.outFile = outFile;
        this.model = model;
        this.searchStrat = searchStrat;
        this.selCrit = selCrit;
        this.startStrat = startStrat;
        this.verb = verb;
        this.stand = stand;
        this.xConst = xConst;
        this.xConstAll = xConstAll;
        this.perGridRemove = perGridRemove;
        this.gridNum = gridNum;
        this.isCI = isCI;
        this.ciLevel = ciLevel;
        this.isBonfCI = isBonfCI;
        this.bwFactor = bwFactor;
        this.showGraph = showGraph;
        this.rotateAngle = rotateAngle;
        this.volatEst = volatEst;
        this.isYZero = isYZero;

    }

    public void runCode() {

        // not for GRTE but GAUSS comm
        engine().load("dlib/cafpe", GaussLoadTypes.SYS);

        engine().load("pgraph", GaussLoadTypes.LIB);
        engine().load("tools", GaussLoadTypes.LIB);
        engine().load("plot", GaussLoadTypes.LIB);
        engine().load("cafp", GaussLoadTypes.LIB);

        if (getSymbolTable() != null) {
            if (volatEst) {
                getSymbolTable().get(CAFPE_constants.GRID_VALUES_VOLAT).clear();
                getSymbolTable().get(CAFPE_constants.RESID_EST_VOLAT).clear();
                getSymbolTable().get(CAFPE_constants.XSADJ_VOLAT).clear();
                getSymbolTable().get(CAFPE_constants.G_HAT_XCONSTALL_VOLAT)
                        .clear();
                getSymbolTable().get(CAFPE_constants.CI_G_HAT_XCONSTALL_VOLAT)
                        .clear();
                getSymbolTable().get(CAFPE_constants.CI_G_HATPLOT_VOLAT)
                        .clear();
            } else {
                getSymbolTable().get(CAFPE_constants.GRID_VALUES).clear();
                getSymbolTable().get(CAFPE_constants.RESID_EST).clear();
                getSymbolTable().get(CAFPE_constants.XSADJ).clear();
                getSymbolTable().get(CAFPE_constants.G_HAT_XCONSTALL).clear();
                getSymbolTable().get(CAFPE_constants.CI_G_HAT_XCONSTALL)
                        .clear();
                getSymbolTable().get(CAFPE_constants.CI_G_HATPLOT).clear();
            }
        }

        JSCData results[] = null;
        if (volatEst)
            results = new JSCData[] {
                    CAFPE_constants.GRID_VALUES_VOLAT.getInstance(),
                    CAFPE_constants.RESID_EST_VOLAT.getInstance(),
                    CAFPE_constants.XSADJ_VOLAT.getInstance(),
                    CAFPE_constants.G_HAT_XCONSTALL_VOLAT.getInstance(),
                    CAFPE_constants.CI_G_HAT_XCONSTALL_VOLAT.getInstance(),
                    CAFPE_constants.CI_G_HATPLOT_VOLAT.getInstance() };
        else
            results = new JSCData[] {
                    CAFPE_constants.GRID_VALUES.getInstance(),
                    CAFPE_constants.RESID_EST.getInstance(),
                    CAFPE_constants.XSADJ.getInstance(),
                    CAFPE_constants.G_HAT_XCONSTALL.getInstance(),
                    CAFPE_constants.CI_G_HAT_XCONSTALL.getInstance(),
                    CAFPE_constants.CI_G_HATPLOT.getInstance() };

        engine().call(
                "cafpe_est_nonparam",
                new JSCData[] {
                        y,
                        res,
                        new JSCString("fName", UString.replaceAllSubStrings(
                                outFile.toString(), "\\", "/")),
                        new JSCString("model", model),
                        new JSCString("searchStrat", searchStrat),
                        new JSCString("selCrit", selCrit),
                        new JSCString("startStrat", startStrat),
                        new JSCInt("verb", verb),
                        new JSCString("isstand", (stand ? "yes" : "no")),
                        new JSCInt("seastart", start.subPeriod()), lagsGrid,
                        gridVar, xConst,
                        new JSCNumber("perGridRemove", perGridRemove),
                        new JSCInt("gridNum", gridNum), xConstAll,
                        new JSCInt("isCI", isCI),
                        new JSCNumber("ciLevel", ciLevel),
                        new JSCInt("showGraph", showGraph),
                        new JSCInt("isBonfCI", isBonfCI),
                        new JSCNumber("bwFactor", bwFactor),
                        new JSCInt("rotateAngle", rotateAngle) }, results);

        if (getSymbolTable() != null)
            getSymbolTable().set(results);

    }

    public void finalCode() {

        StringBuffer buffer = output;
        if (volatEst)
            buffer.append("selected series for volatility estimation: \""
                    + name + "\"\n");
        else
            buffer.append("selected series: \"" + name + "\"\n");
        if (isYZero)
            buffer
                    .append("conditional mean set to zero (f(y) = 0; resids = y\n");

        buffer.append(FileSupport.getInstance().readTextFile(outFile.getAbsolutePath()));
        buffer.append("\n\n");

    }
}
