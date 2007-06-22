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
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.SymbolTable;
import com.jstatcom.util.UData;
import com.jstatcom.util.UMatrix;

import de.jmulti.var.VARConstants;


/**
 * This GAUSS command object carries out a search routine for subset
 * restrictions for the coefficients of different models according to the
 * parametrization. The results of this call are stored to the symbol table if
 * one has been set. The variable names defined in <code>VARConstants</code>
 * are used for the arguments of the call as well as for storing the results.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
public final class VARSubsetSearchCall extends RPCall {
    private int strategy = 1;

    private int criterion = 1;

    private double threshold = 2;

    private JSCNArray cy = null;

    private JSCNArray cx = null;

    private JSCNArray cd = null;

    private JSCNArray y_notrunc = null;

    private JSCNArray x_notrunc = null;

    private JSCNArray d_notrunc = null;

    private int py = 0;

    private int px = 0;

    private JSCNArray result = null;

    /**
     * Constructor.
     * 
     * @param symbolTable
     *            must hold all model variables
     * @param strategy
     *            1 = System SER, 2 = TestingProcedure /SER, 3 = TopDown, 4 =
     *            System Testing
     * @param criterion
     *            1 = AIC, 2 = HQ, 3 = SC
     * @param threshold
     *            only used for System Testing
     */
    public VARSubsetSearchCall(SymbolTable symbolTable, int strategy,
            int criterion, double threshold) {
        super();

        setSymbolTable(symbolTable);

        setName("Subset search for VAR");

        this.strategy = strategy;
        this.criterion = criterion;
        this.threshold = threshold;

        // Get the additional parameters from the symbol table
        // according to the names defined in VARConstants.
        cy = symbolTable.get(VARConstants.Cy_Def).getJSCNArray();
        cx = symbolTable.get(VARConstants.Cx_Def).getJSCNArray();
        cd = symbolTable.get(VARConstants.Cd_Def).getJSCNArray();

        y_notrunc = symbolTable.get(VARConstants.y_raw_Def).getJSCNArray();
        x_notrunc = symbolTable.get(VARConstants.x_raw_Def).getJSCNArray();
        d_notrunc = symbolTable.get(VARConstants.d_all_Def).getJSCNArray();

        py = symbolTable.get(VARConstants.py_Def).getJSCInt().intVal();
        px = symbolTable.get(VARConstants.px_Def).getJSCInt().intVal();
    }

    protected void finalCode() {
        SymbolTable gst = getSymbolTable();

        double[][] raw = result.doubleArray();

        int d = cd.cols();
        int k = y_notrunc.cols();
        int y = cy.cols();
        int x = cx.cols();

        double[][] restrictions = UMatrix.getDoubleRows(raw, 0, k - 1);

        // first set cx,cy,cd, because of listeners to them reset other data

        if (d > 0)
            gst.get(VARConstants.Cd_Def).setJSCData(
                    new JSCNArray("Cd", UMatrix.getDoubleCols(restrictions, 0,
                            d - 1)));
        if (y > 0)
            gst.get(VARConstants.Cy_Def).setJSCData(
                    new JSCNArray("Cy", UMatrix.getDoubleCols(restrictions, d,
                            d + y - 1)));
        if (x > 0)
            gst.get(VARConstants.Cx_Def).setJSCData(
                    new JSCNArray("Cx", UMatrix.getDoubleCols(restrictions, d
                            + y, d + y + x - 1)));

    }

    /**
     * @see RPCall
     */
    protected void runCode() {

        JSCNArray yz[] = UData.createYZMat(d_notrunc, y_notrunc, x_notrunc, py,
                px, "yMatrix", "zMatrix");

        engine().load("vec", GaussLoadTypes.LIB);
        engine().load("var", GaussLoadTypes.LIB);

        JSCNArray userRes = new JSCNArray("userRes");
        userRes.appendCols(cd);
        userRes.appendCols(cy);
        userRes.appendCols(cx);

        JSCNArray mode = new JSCNArray("mode", new double[] { strategy,
                criterion, threshold });

        result = new JSCNArray("result");

        engine().call(
                "ComputeSubsetJM_subset",
                new JSCData[] {
                        new JSCNArray("yMatrix", yz[0].getTransposed()), yz[1],
                        userRes, mode },
                new JSCData[] { result, new JSCNArray("uhat"),
                        new JSCNArray("cov") });

    }
}