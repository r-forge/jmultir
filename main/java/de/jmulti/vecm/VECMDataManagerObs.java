/*
 This file is part of the econometric software package JMulTi.
 Copyright (C) 2000-2005  Alexander Bankwitz, Markus Kraetzig

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

package de.jmulti.vecm;

import java.util.ArrayList;

import static de.jmulti.vecm.VECMConstants.*;

import com.jstatcom.model.JSCDRange;
import com.jstatcom.model.JSCInt;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.JSCSArray;
import com.jstatcom.model.JSCString;
import com.jstatcom.model.SymbolTable;
import com.jstatcom.ts.TSDateRange;
import com.jstatcom.util.UMatrix;
import com.jstatcom.util.UStringArray;

import de.jmulti.proc.VECMEstimationCall;

/**
 * Singleton data manager that can be accessed via <code>getInstance</code>.
 * It takes care of computing the data objects needed for the estimation.
 * 
 * @author A. Benkwitz, <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
final class VECMDataManagerObs {
    private static VECMDataManagerObs instance = null;

    private final SymbolTable sTable;

    private boolean interceptSelected = false;

    private boolean seasDumSelected = false;

    private boolean trendSelected = false;

    private boolean centered = true;

    /**
     * FirstStageDataManager constructor comment.
     */
    private VECMDataManagerObs(SymbolTable table) {
        super();

        if (table == null)
            throw new IllegalArgumentException("SymbolTable was null.");

        this.sTable = table;
    }

    /**
     * 
     */
    public void computeData() {

        removeData();
        computeSampleSizeAndBeginningOfSample();
        computeEndogenous();
        computeExogenous();
        computeDeterministics();
        computeNames();

    }

    /**
     * Adds the selected deterministics to the names vector (CONST, TREND, Si).
     */
    private void computeDeterministicNames() {
        JSCSArray newNames = sTable.get(Nd_raw_Def).getJSCSArray().copy();
        // clone(Nd_Def);

        if (interceptSelected)
            newNames.appendRows(new JSCSArray("const", CONST));

        JSCDRange jscdrange = sTable.get(T1_raw_Def).getJSCDRange();

        int k = 0;
        if (!jscdrange.isEmpty())
            k = jscdrange.getTSDateRange().subPeriodicity() - 1;
        if (seasDumSelected && k > 0) {
            String[] sa = UStringArray.createNamedIndex("S", UMatrix.seqa(1, 1,
                    k));
            newNames.appendRows(new JSCSArray("seasDumNames", sa));
        }

        if (trendSelected)
            newNames.appendRows(new JSCSArray("trend", TREND));

        sTable.get(Nd_Def).setJSCData(newNames);
        computeEC_VAR_VECNames();

    }

    /**
     * Computes the deterministic data array.
     */
    private void computeDeterministics() {
        JSCNArray d_raw = (JSCNArray) sTable.get(d_raw_Def).getJSCData().copy();

        TSDateRange range = null;
        JSCDRange jscDRange = sTable.get(T1_raw_Def).getJSCDRange();
        if (!jscDRange.isEmpty()) {
            range = jscDRange.getTSDateRange();
        }

        if (range != null) {
            int sampleSize = range.numOfObs();

            if (interceptSelected)
                d_raw.appendCols(new JSCNArray("const", UMatrix.ones(
                        sampleSize, 1)));

            if (seasDumSelected) {
                JSCNArray dummies = new JSCNArray("seasDum", range
                        .createSeasDum(centered, false));

                d_raw.appendCols(dummies);
            }

            if (trendSelected)
                d_raw.appendCols(new JSCNArray("trend", UMatrix
                        .toDoubleMatrix(UMatrix.seqa(1, 1, sampleSize))));

            sTable.get(d_Def).setJSCData(d_raw);

        } else
            sTable.get(d_Def).clear();

        double[] sel = new double[] { 0, 0, 0, 0 };
        if (interceptSelected)
            sel[0] = 1;
        if (seasDumSelected)
            sel[1] = 1;
        if (trendSelected)
            sel[2] = 1;
        if (!centered)
            sel[3] = 1;

        sTable.get(det_sel_Def).setJSCData(new JSCNArray("sel", sel));

    }

    /**
     * Computes the names that are used for the deterministics in the equation
     * display.
     */
    private void computeEC_VAR_VECNames() {

        JSCSArray names = sTable.get(Nd_Def).getJSCSArray();
        String[] namArray = new String[names.rows()];
        for (int i = 0; i < namArray.length; i++)
            namArray[i] = names.stringAt(i, 0);

        JSCNArray index = sTable.get(idx_cd2ci_Def).getJSCNArray();
        int[] ecIndex = new int[index.rows()];
        for (int i = 0; i < ecIndex.length; i++)
            ecIndex[i] = index.intAt(i, 0);

        String[] ecNames = UStringArray.selRowsIf(namArray, ecIndex);
        String[] vecNames = UStringArray.getStringDiff(namArray, ecNames);
        sTable.get(Nd_vec_Def).setJSCData(new JSCSArray("vecnames", vecNames));

        // The det names of restricted to EC term.
        ArrayList<String> ecNamesList = new ArrayList<String>();
        if (ecNames.length > 0) {
            for (int i = 0; i < ecNames.length; i++) {
                ecNamesList.add(ecNames[i]);
                if (!ecNames[i].equals(CONST))
                    ecNames[i] = ecNames[i] + "(t-1)";
            }
            sTable.get(Nd_ec_Def).setJSCData(new JSCSArray("ecnames", ecNames));
        } else
            sTable.get(Nd_ec_Def).clear();

        // Build the VAR representation det names with correct time index.
        String[][] varNames = names.stringArray();
        int[] notECIndex = new int[ecIndex.length];
        for (int i = 0; i < notECIndex.length; i++)
            notECIndex[i] = (ecIndex[i] == 0) ? 1 : 0;

        if (varNames != null) {
            String[][] notECNames = names.selRowsIf(notECIndex);
            for (int i = 0; i < notECNames.length; i++) {
                if (!notECNames[i][0].equals(CONST))
                    varNames[i][0] = notECNames[i][0] + "(t)";
                else
                    varNames[i][0] = notECNames[i][0];
            }
            for (int i = notECNames.length; i < varNames.length; i++) {
                // Variable was restricted to EC term, set index to (t-1).
                String name = ecNamesList.get(i - notECNames.length);
                if (!name.equals(CONST))
                    varNames[i][0] = name + "(t-1)";
                else
                    varNames[i][0] = name;
            }

        }

        sTable.get(Nd_var_Def).setJSCData(new JSCSArray("varnames", varNames));

    }

    /**
     * Endogenous must have py + T rows, where T is the effective number of
     * observations, which is rows(y_orig)-max(py,px).
     */
    private void computeEndogenous() {
        int py = sTable.get(pdy_Def).getJSCInt().intVal() + 1;
        int px = sTable.get(px_Def).getJSCInt().intVal();
        int p = Math.max(py, px);
        //
        double[][] y = sTable.get(y_raw_Def).getJSCNArray().doubleArray();
        // Py is the smaller, therefore x must be cutted to fulfill py + T
        // condition.
        if (py < p && y.length > 0)
            y = UMatrix.getDoubleRows(y, p - py, y.length - 1);

        sTable.get(py_granger_Def).setJSCData(new JSCInt("granger", py + 1));
        sTable.get(y_Def).setJSCData(new JSCNArray("y", y));

    }

    /**
     * Insert the method's description here. Creation date: (08.10.01 10:48:35)
     */
    private void computeEndogenousNames() {

        JSCSArray x = sTable.get(Ny_Def).getJSCSArray();
        int k = x.rows();
        if (k > 0) {
            sTable.get(Nu_Def).setJSCData(
                    new JSCSArray("nu", UStringArray.createNamedIndex("u",
                            UMatrix.seqa(1, 1, k))));
            int r = 1;
            r = sTable.get(cointRank_Def).getJSCInt().intVal();
            sTable.get(Nec_Def).setJSCData(
                    new JSCSArray("ec", UStringArray.createNamedIndex("ec",
                            UMatrix.seqa(1, 1, r))));
            String[][] sA = x.stringArray();
            for (int i = 0; i < sA.length; i++)
                sA[i][0] = "d(" + sA[i][0] + ")";

            sTable.get(Ndy_Def).setJSCData(new JSCSArray("ndnames", sA));
        } else {
            sTable.get(Nu_Def).clear();
            sTable.get(Nec_Def).clear();
            sTable.get(Ndy_Def).clear();
        }
    }

    /**
     * Exogenous must have px + T rows, where T is the effective number of
     * observations, which is rows(x_orig)-max(py,px).
     */
    private void computeExogenous() {
        int py = sTable.get(pdy_Def).getJSCInt().intVal() + 1;
        int px = sTable.get(px_Def).getJSCInt().intVal();
        int p = Math.max(py, px);
        //
        double[][] x = sTable.get(x_raw_Def).getJSCNArray().doubleArray();
        double[][] x1 = x;
        // Px is the smaller, therefore x must be cutted to fulfill px + T
        // condition.
        if (px < p && x != null && x.length > 0)
            x1 = UMatrix.getDoubleRows(x, p - px, x.length - 1);

        sTable.get(x_Def).setJSCData(new JSCNArray("x", x1));

    }

    /**
     * Insert the method's description here. Creation date: (08.10.01 10:48:35)
     */
    private void computeExogenousNames() {
        // empty

    }

    /**
     * Insert the method's description here. Creation date: (08.10.01 10:48:35)
     */
    private void computeNames() {
        computeExogenousNames();
        computeEndogenousNames();
        computeDeterministicNames();
    }

    /**
     * Computes the sample length and the start date. Sets py as well.
     */
    public void computeSampleSizeAndBeginningOfSample() {

        boolean endSel = !sTable.get(Ny_Def).isEmpty();
        boolean exoSel = !sTable.get(Nx_Def).isEmpty();
        boolean detSel = !sTable.get(Nd_raw_Def).isEmpty();
        if (endSel || exoSel || detSel) {
            TSDateRange range = sTable.get(T1_raw_Def).getJSCDRange()
                    .getTSDateRange();

            int py = sTable.get(pdy_Def).getJSCInt().intVal() + 1;
            sTable.get(py_Def).setJSCData(new JSCInt("py", py));
            int px = sTable.get(px_Def).getJSCInt().intVal() + 1;
            int max_p = Math.max(py, px);

            TSDateRange estRange = range.addPeriodsToStart(max_p);
            sTable.get(T1_Def).setJSCData(new JSCDRange("estRange", estRange));
            sTable.get(estRange_Def).setJSCData(
                    new JSCString("estRange", estRange.format("", 0)));

        } else {
            if (sTable.get(T1_Def) != null)
                sTable.get(T1_Def).clear();
            if (sTable.get(estRange_Def) != null)
                sTable.get(estRange_Def).clear();
        }

    }

    /**
     * Gets the Singleton instance of this data manager
     * 
     * @return the unique instance of this class
     */
    public final static VECMDataManagerObs getInstance() {
        if (instance == null)
            instance = new VECMDataManagerObs(VECM.getInstance()
                    .getSymbolTable());
        return instance;
    }

    public void removeBetaRestrictions() {
        sTable.get(resFreeParamIndex_Def).clear();
        sTable.get(resH_Def).clear();
        sTable.get(resHmat_Def).clear();

    }

    public void removeData() {

        sTable.get(cv_u_hat_Def).clear();
        sTable.get(u_hat_Def).clear();
        sTable.get(SVECConstants.matB_Def).clear();

        for (int i = 0; i < VECMEstimationCall.outputs.length; i++)
            sTable.get(VECMEstimationCall.outputs[i]).clear();

    }

    public void setDetSel(boolean isIntercept, boolean isTrend,
            boolean isSeasDum, boolean isCentered) {

        interceptSelected = isIntercept;
        trendSelected = isTrend;
        seasDumSelected = isSeasDum;
        centered = isCentered;
        computeDeterministicNames();
        removeData();

    }
}