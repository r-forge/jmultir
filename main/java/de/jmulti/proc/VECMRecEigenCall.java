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
import com.jstatcom.model.JSCNumber;
import com.jstatcom.model.JSCTypeDef;
import com.jstatcom.model.JSCTypes;
import com.jstatcom.model.SymbolTable;
import com.jstatcom.ts.TSDateRange;

import de.jmulti.vecm.VECMConstants;


/**
 * Makes the GAUSS call to display the recursive eigenvalues for a VECM model.
 * The results are stored to the given symboltable <code>toStore</code>.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public final class VECMRecEigenCall extends RPCall {
    // eig_val, eig_CI, eig_Psi, eig_psi_CI, tau_t, crval,tau_sum, crval_sum
    public static final JSCTypeDef RECEIGEN_EIGVAL = new JSCTypeDef(
            "RECEIGEN_EIGVAL", JSCTypes.NARRAY, "recursive eigenvalues");

    public static final JSCTypeDef RECEIGEN_CI = new JSCTypeDef("RECEIGEN_CI",
            JSCTypes.NARRAY,
            "CI for eigenvalues, 1:r - lower bounds, r+1:2r - upper bounds");

    public static final JSCTypeDef RECEIGEN_PSI = new JSCTypeDef(
            "RECEIGEN_PSI", JSCTypes.NARRAY,
            "Psi statistics (SUM [ln(lambda/(1-lambda))], where lambda - eigenvalues");

    public static final JSCTypeDef RECEIGEN_PSICI = new JSCTypeDef(
            "RECEIGEN_PSICI", JSCTypes.NARRAY, "CI for psi statistics");

    public static final JSCTypeDef RECEIGEN_TAU_T = new JSCTypeDef(
            "RECEIGEN_TAU_T", JSCTypes.NARRAY,
            "tau statistics - distributed as standard Brownian Bridge (t/T)");

    public static final JSCTypeDef RECEIGEN_CRVAL = new JSCTypeDef(
            "RECEIGEN_CRVAL", JSCTypes.NARRAY,
            "90%, 95% and 99% quantiles of standard Brownian Bridge");

    public static final JSCTypeDef RECEIGEN_TAU_SUM = new JSCTypeDef(
            "RECEIGEN_TAU_SUM", JSCTypes.NARRAY,
            "tau statistics - distributed as r-dimensional Brownian Bridge (t/T)");

    public static final JSCTypeDef RECEIGEN_CRVAL_SUM = new JSCTypeDef(
            "RECEIGEN_CRVAL_SUM", JSCTypes.NARRAY,
            "90%, 95% and 99% quantiles of r-dimensial Brownian Bridge ");

    private double signLevel = 0.95;

    private SymbolTable toStore = null;

    private int startIndex = 0;

    private int estMethod = 0;

    private int ciMethod = 0;

    /**
     * VECMRecEigenCall constructor comment.
     * 
     * @param gst
     *            symbol table to fetch input data from
     * @param toStore
     *            symbol table to store results to
     * @param startIndex
     *            starting index
     * @param estMethod
     *            0: "using concentrated likelihood func." 1: "recursively
     *            estimate all parameters"
     * @param ciMethod
     *            0: "full sample estimates of standard errors" 1: "recursive
     *            sample estimates of standard errors"
     * @param signLevel
     *            significance level
     */
    public VECMRecEigenCall(SymbolTable gst, SymbolTable toStore,
            int startIndex, int estMethod, int ciMethod, double signLevel) {

        super();

        setSymbolTable(gst);
        setName("Recursive Eigenvalues");

        this.toStore = toStore;
        this.signLevel = signLevel;
        this.startIndex = startIndex;
        this.estMethod = estMethod;
        this.ciMethod = ciMethod;

    }

    protected void finalCode() {

    }

    /**
     * @see ProcCall
     */
    protected void runCode() {

        SymbolTable gst = getSymbolTable();

        engine().load("pgraph", GaussLoadTypes.LIB);
        engine().load("tools", GaussLoadTypes.LIB);
        engine().load("plot", GaussLoadTypes.LIB);
        engine().load("vec", GaussLoadTypes.LIB);
        engine().load("var", GaussLoadTypes.LIB);
        engine().load("stab", GaussLoadTypes.LIB);

        setGlobalPgraphSettings();

        if (toStore != null) {
            toStore.get(RECEIGEN_CI).clear();
            toStore.get(RECEIGEN_EIGVAL).clear();
            toStore.get(RECEIGEN_PSI).clear();
            toStore.get(RECEIGEN_PSICI).clear();
            toStore.get(RECEIGEN_CRVAL).clear();
            toStore.get(RECEIGEN_CRVAL_SUM).clear();
            toStore.get(RECEIGEN_TAU_T).clear();
            toStore.get(RECEIGEN_TAU_SUM).clear();
        }

        JSCData[] retArg = new JSCData[] { RECEIGEN_EIGVAL.getInstance(),
                RECEIGEN_CI.getInstance(), RECEIGEN_PSI.getInstance(),
                RECEIGEN_PSICI.getInstance(), RECEIGEN_TAU_T.getInstance(),
                RECEIGEN_CRVAL.getInstance(), RECEIGEN_TAU_SUM.getInstance(),
                RECEIGEN_CRVAL_SUM.getInstance() };

        TSDateRange range = gst.get(VECMConstants.T1_Def).getJSCDRange()
                .getTSDateRange();

        engine().call(
                "stab_recurs_eigen_wrapper",
                new JSCData[] {
                        gst.getJSCData(VECMConstants.y_Def),
                        gst.getJSCData(VECMConstants.py_Def),
                        gst.getJSCData(VECMConstants.idx_cd2ci_Def),
                        gst.getJSCData(VECMConstants.d_Def),
                        gst.getJSCData(VECMConstants.cointRank_Def),
                        new JSCInt("startIndex", startIndex + 1),
                        new JSCInt("estMethod", estMethod),
                        new JSCInt("ciMethod", ciMethod),
                        new JSCNumber("signLevel", signLevel),
                        new JSCInt("isGraph", true),
                        new JSCInt("period", range.subPeriodicity()),
                        new JSCNumber("startDate", range.lowerBound()
                                .doubleValue()) }, retArg);

        if (toStore != null)
            toStore.set(retArg);

    }
}