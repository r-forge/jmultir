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
import com.jstatcom.model.JSCTypeDef;
import com.jstatcom.model.JSCTypes;
import com.jstatcom.model.SymbolTable;
import com.jstatcom.ts.TSDateRange;

import de.jmulti.var.VARConstants;

/**
 * Makes the GAUSS call to display the recursive residuals for a VAR model
 * starting from the first possible date.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public final class VARRecursResidCall extends RPCall {
    public static final JSCTypeDef RECURS_RESIDS = new JSCTypeDef(
            "RECURS_RESIDS", JSCTypes.NARRAY, "recursively estimated residuals");

    public static final JSCTypeDef RECURS_CI_WIDTH = new JSCTypeDef(
            "RECURS_CI_WIDTH", JSCTypes.NARRAY,
            "CI width (one side) for recursive residuals");

    private double signLevel = 0.95;

    private boolean isStand = false;

    private SymbolTable toStore;

    /**
     * VARRecursResidCall constructor comment.
     * 
     * @param gst
     *            symbol table to fetch input data from
     * @param signLevel
     *            significance level
     * @param isStand
     *            <code>true</code> if standardized
     */
    public VARRecursResidCall(SymbolTable gst, SymbolTable toStore,
            double signLevel, boolean isStand) {

        super();

        setSymbolTable(gst);
        setName("VAR Recursive Residuals");
        this.signLevel = signLevel;
        this.isStand = isStand;
        this.toStore = toStore;

    }

    /**
     * @see ProcCall
     */
    protected void runCode() {

        SymbolTable gst = getSymbolTable();
        if (toStore != null) {
            toStore.get(RECURS_RESIDS).clear();
            toStore.get(RECURS_CI_WIDTH).clear();
        }
        engine().load("pgraph", GaussLoadTypes.LIB);
        engine().load("tools", GaussLoadTypes.LIB);
        engine().load("plot", GaussLoadTypes.LIB);
        engine().load("vec", GaussLoadTypes.LIB);
        engine().load("var", GaussLoadTypes.LIB);
        engine().load("stab", GaussLoadTypes.LIB);

        JSCNArray y = gst.get(VARConstants.y_Def).getJSCNArray();
        int startPoint = gst.get(VARConstants.py_Def).getJSCInt().intVal()
                * y.cols()
                + (gst.get(VARConstants.px_Def).getJSCInt().intVal() + 1)
                * gst.get(VARConstants.x_Def).getJSCNArray().cols()
                + gst.get(VARConstants.d_all_Def).getJSCNArray().cols()
                + y.cols() + 1;

        TSDateRange range = gst.get(VARConstants.T1_Def).getJSCDRange()
                .getTSDateRange();

        JSCNArray varBuffer = new JSCNArray("varBuffer");
        VAREstimationCall.setVarBuffer(engine(), gst, varBuffer);

        setGlobalPgraphSettings();
        JSCData[] returns = new JSCData[] { RECURS_RESIDS.getInstance(),
                RECURS_CI_WIDTH.getInstance() };
        engine().call(
                "stab_VARrecres_wrapper",
                new JSCData[] {
                        varBuffer,
                        new JSCInt("startPoint", startPoint),
                        new JSCNumber("startDate", range.lowerBound()
                                .doubleValue()),
                        new JSCInt("period", range.subPeriodicity()),
                        new JSCNumber("signLevel", signLevel),
                        new JSCInt("isStand", !isStand) }, returns);

        if (toStore != null)
            toStore.set(returns);

    }
}