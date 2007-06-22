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

package de.jmulti.var;

import static de.jmulti.var.VARConstants.*;

import com.jstatcom.model.JSCInt;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.JSCSArray;
import com.jstatcom.model.JSCString;
import com.jstatcom.model.SymbolEvent;
import com.jstatcom.model.SymbolEventTypes;
import com.jstatcom.model.SymbolListener;
import com.jstatcom.model.SymbolTable;
import com.jstatcom.ts.TSDateRange;
import com.jstatcom.util.UMatrix;

/**
 * Singleton data manager that can be accessed via <code>getInstance</code>.
 * It computes the data objects necessary for estimation.
 * 
 * @author A. Benkwitz, <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
final class VARDataManager {
    private static VARDataManager instance = null;

    private final VAR var = VAR.getInstance();

    private boolean interceptSelected = false;

    private boolean trendSelected = false;

    private boolean seasDumSelected = false;

    private final SymbolTable sTable;

    /**
     * DataManagerEnableMenus constructor comment.
     */
    private VARDataManager(SymbolTable table) {
        super();
        if (table == null)
            throw new IllegalArgumentException("SymbolTable was null.");

        sTable = table;
        installDataListeners(sTable);
    }

    /**
     * Computes the data objects needed for VAR estimation.
     */
    public void computeData() {
        // reset old matrices
        removeData();

        double[] sel = new double[] { 0, 0, 0 };
        if (interceptSelected)
            sel[0] = 1;
        if (seasDumSelected)
            sel[1] = 1;
        if (trendSelected)
            sel[2] = 1;

        sTable.get(det_sel_Def).setJSCData(new JSCNArray("sel", sel));

        JSCNArray gaussData_y_raw = sTable.get(y_raw_Def).getJSCNArray();
        // raw endogenous data
        int k = sTable.get(Ny_Def).getJSCSArray().rows();
        // number of equations
        if (gaussData_y_raw.isEmpty() && k == 0) {
            return;
        }
        TSDateRange range = sTable.get(T1_raw_Def).getJSCDRange()
                .getTSDateRange();

        // intercept, seasonal dummies, and trend
        // the CheckBox deterministic values are added to the d_raw data first!
        JSCNArray gaussData_d_raw = sTable.get(d_raw_Def).getJSCNArray().copy();

        JSCSArray gaussData_Nd = sTable.get(Nd_raw_Def).getJSCSArray().copy();

        int sampleSize = gaussData_y_raw.rows();
        if (interceptSelected) {
            gaussData_d_raw.appendCols(new JSCNArray("const", UMatrix.ones(
                    sampleSize, 1)));
            gaussData_Nd.appendRows(new JSCSArray("const", CONST));
        }
        if (seasDumSelected) {
            JSCNArray dummies = new JSCNArray("temp", range.createSeasDum(
                    false, false));
            gaussData_d_raw.appendCols(dummies);
            String[] seasDumNames = new String[dummies.cols()];
            for (int i = 0; i < dummies.cols(); i++)
                seasDumNames[i] = "S" + (i + 1);

            gaussData_Nd.appendRows(new JSCSArray("seasDum", seasDumNames));

        }
        if (trendSelected) {
            double[][] trend = new double[sampleSize][1];
            for (int i = 0; i < trend.length; i++) {
                trend[i][0] = (i + 1);
            }
            gaussData_d_raw.appendCols(new JSCNArray("trend", trend));
            gaussData_Nd.appendRows(new JSCSArray("trend", TREND));
        }

        // create names for residuals
        String[][] residualNames = new String[k][1];
        for (int i = 0; i < k; i++)
            residualNames[i][0] = "u" + (i + 1);
        sTable.get(Nu_Def).setJSCData(new JSCSArray("resNames", residualNames));

        // number of observations including all presample values
        int t_raw = gaussData_y_raw.rows();

        //
        JSCInt data_Py = sTable.get(py_Def).getJSCInt();
        JSCInt data_Px = sTable.get(px_Def).getJSCInt();
        int py = !data_Py.isEmpty() ? data_Py.intVal() : 0;
        int px = !data_Px.isEmpty() ? data_Px.intVal() : 0;

        int lags = Math.max(py, px);
        // observation matrix for endogenous variables
        sTable.get(y_Def).setJSCData(
                new JSCNArray("y", gaussData_y_raw.getRows(lags - py,
                        t_raw - 1)));

        if (py > 0) {
            // subset restrictions for endogenous variables
            JSCNArray cy = sTable.get(Cy_Def).getJSCNArray();
            if (cy.rows() != k || cy.cols() != k * py)
                sTable.get(Cy_Def).setJSCData(
                        new JSCNArray("cy", UMatrix.ones(k, k * py)));

    
        } else
            sTable.get(Cy_Def).clear();

        int q = sTable.get(Nx_Def).getJSCSArray().rows();
        if (px >= 0 && q > 0) {
            JSCNArray cx = sTable.get(Cx_Def).getJSCNArray();
            if (cx.rows() != k || cx.cols() != ((px + 1) * q))
                sTable.get(Cx_Def).setJSCData(
                        new JSCNArray("cx", UMatrix.ones(k, (px + 1) * q)));

            // observation matrix for exogenous variables
            JSCNArray gaussData_x_raw = sTable.get(x_raw_Def).getJSCNArray();
            sTable.get(x_Def).setJSCData(
                    new JSCNArray("x", gaussData_x_raw.getRows(lags - px,
                            t_raw - 1)));
        } else
            sTable.get(Cx_Def).clear();

        //
        int n = gaussData_Nd.rows();
        if (n > 0) {
            sTable.get(Nd_Def).setJSCData(gaussData_Nd);
            JSCNArray cd = sTable.get(Cd_Def).getJSCNArray();
            if (cd.rows() != k || cd.cols() != n)
                sTable.get(Cd_Def).setJSCData(
                        new JSCNArray("cd", UMatrix.ones(k, n)));
            sTable.get(d_all_Def).setJSCData(gaussData_d_raw);
        } else
            sTable.get(Cd_Def).clear();

    }

    /**
     * Gets the Singleton instance of this data manager
     * 
     * @return the unique instance of this class
     */
    public final static VARDataManager getInstance() {
        if (instance == null)
            instance = new VARDataManager(VAR.getInstance().getSymbolTable());
        return instance;
    }

    /**
     * Data listeners to disable/enable menus in VAR.
     */
    private void installDataListeners(SymbolTable t) {
        // enable Estimation menu only if endogenous data is provided
        t.get(Ny_Def).addSymbolListener(new SymbolListener() {
            public void valueChanged(SymbolEvent evt) {
                boolean __enabled = !evt.isSourceEmpty();
                var.action_setRestrictions.setEnabled(__enabled);
                var.action_estimatedCoefficients.setEnabled(__enabled);
            }
        }, SymbolEventTypes.EMPTY_STATE);

        // residual analysis only if 'estimated' residuals exists
        t.get(u_hat_Def).addSymbolListener(new SymbolListener() {
            public void valueChanged(SymbolEvent evt) {
                boolean __enabled = !evt.isSourceEmpty();
                var.action_residualAnalysis.setEnabled(__enabled);
                var.action_forecasting.setEnabled(__enabled);
                var.action_stabAnal.setEnabled(__enabled);
                // Enable/Disable multivariate/univariate ARCH Analysis.
                boolean isUnivariate = false;
                if (__enabled)
                    isUnivariate = evt.getSource().getJSCNArray().cols() == 1;
                var.action_archAnal.setEnabled(isUnivariate && __enabled);
                var.action_multArchAnal.setEnabled(!isUnivariate && __enabled);
                var.action_svarEstimation
                        .setEnabled(!isUnivariate && __enabled);

            }
        }, SymbolEventTypes.EMPTY_STATE);

        // SVAR IRA and FEVD Decomp depend on SVAR_A0.
        t.get(SVARConstants.svar_A0_Def).addSymbolListener(
                new SymbolListener() {
                    public void valueChanged(SymbolEvent evt) {
                        boolean __enabled = !evt.isSourceEmpty();
                        var.action_svarIRA.setEnabled(__enabled);
                        var.action_svarFEV.setEnabled(__enabled);
                    }
                }, SymbolEventTypes.EMPTY_STATE);

        //
        // if changes in specification were made: reset current results.
        SymbolListener resetResults = new SymbolListener() {
            public void valueChanged(SymbolEvent evt) {
                sTable.get(cv_u_hat_Def).clear();
                sTable.get(u_hat_Def).clear();
                sTable.get(SVARConstants.svar_A0_Def).clear();
                sTable.get(Ay_Def).clear();
                sTable.get(Ax_Def).clear();
                sTable.get(Ad_Def).clear();
                sTable.get(SD_Ay_Def).clear();
                sTable.get(SD_Ax_Def).clear();
                sTable.get(SD_Ad_Def).clear();
                sTable.get(TV_Ay_Def).clear();
                sTable.get(TV_Ax_Def).clear();
                sTable.get(TV_Ad_Def).clear();
                sTable.get(est_method_Def).setJSCData(
                        new JSCString("estMeth", ""));

            }
        };
        t.get(px_Def).addSymbolListener(resetResults);
        t.get(py_Def).addSymbolListener(resetResults);
        t.get(y_Def).addSymbolListener(resetResults);
        t.get(x_Def).addSymbolListener(resetResults);
        t.get(d_all_Def).addSymbolListener(resetResults);
        t.get(Cy_Def).addSymbolListener(resetResults);
        t.get(Cx_Def).addSymbolListener(resetResults);
        t.get(Cd_Def).addSymbolListener(resetResults);

        // ImpulseResponsePanel
        t.get(Ay_Def).addSymbolListener(new SymbolListener() {
            public void valueChanged(SymbolEvent evt) {
                JSCNArray x = evt.getSource().getJSCNArray();
                boolean __enabled = !x.isEmpty();
                var.action_impulseResponseAnalysis.setEnabled(__enabled);
                var.action_forecastErrorVarianceDecomposition
                        .setEnabled(__enabled && x.rows() > 1);

            }
        });

    }

    /**
     * description
     * 
     */
    public void removeData() {
        sTable.get(y_Def).clear();
        sTable.get(x_Def).clear();
        sTable.get(d_all_Def).clear();
        sTable.get(Nu_Def).clear();
        // sTable.get(Cy_Def).clear();
        // sTable.get(Cx_Def).clear();
        // sTable.get(Cd_Def).clear();
        sTable.get(Nd_Def).clear();
        sTable.get(SVARConstants.svar_A0_Def).clear();
        sTable.get(cv_u_hat_Def).clear();
        sTable.get(u_hat_Def).clear();
        sTable.get(Ay_Def).clear();
        sTable.get(Ax_Def).clear();
        sTable.get(Ad_Def).clear();
        sTable.get(SD_Ay_Def).clear();
        sTable.get(SD_Ax_Def).clear();
        sTable.get(SD_Ad_Def).clear();
        sTable.get(TV_Ay_Def).clear();
        sTable.get(TV_Ax_Def).clear();
        sTable.get(TV_Ad_Def).clear();
        sTable.get(lrtest_Def).clear();

        sTable.get(est_method_Def).setJSCData(new JSCString("estMeth", ""));
        var.action_impulseResponseAnalysis.setEnabled(false);

    }

    /**
     * Sets the selected deterministic variables that are automatically created.
     * If the state is different from what has been selected before, nothing
     * happens, otherwise the new names are created immediately.
     * 
     * @param isIntercept
     *            <code>true</code> if a constant should be added
     * @param isTrend
     *            <code>true</code> if a trend should be added
     * @param isSeasDum
     *            <code>true</code> if seasonal dummies should be added
     */
    public void setDetSel(boolean isIntercept, boolean isTrend,
            boolean isSeasDum) {

        // Nothing changed, return.
        if (interceptSelected == isIntercept && trendSelected == isTrend
                && seasDumSelected == isSeasDum)
            return;

        interceptSelected = isIntercept;
        trendSelected = isTrend;
        seasDumSelected = isSeasDum;
        removeData();

    }
}