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

import Jama.Matrix;

import com.jstatcom.engine.gauss.GaussLoadTypes;
import com.jstatcom.model.JSCDRange;
import com.jstatcom.model.JSCData;
import com.jstatcom.model.JSCInt;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.JSCNumber;
import com.jstatcom.model.JSCSArray;
import com.jstatcom.model.JSCString;
import com.jstatcom.model.JSCTypeDef;
import com.jstatcom.model.JSCTypes;
import com.jstatcom.model.SymbolTable;
import com.jstatcom.ts.TSDate;
import com.jstatcom.ts.TSDateRange;
import com.jstatcom.util.FArg;
import com.jstatcom.util.UData;

import de.jmulti.tools.ModelTypes;
import de.jmulti.var.VARConstants;
import de.jmulti.vecm.VECMConstants;

/**
 * This GAUSS command object computes the VAR forecasts for different models
 * according to the parametrization. The result of this call is stored to the
 * symbol table, if one has been set.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public final class ForecastCall extends RPCall {

    public static final JSCTypeDef FOREC_LEVELS = new JSCTypeDef(
            "FOREC_LEVELS", JSCTypes.NARRAY,
            "the level forecasts (h x k*3 points~lowerCIs~upperCIs)");

    public static final JSCTypeDef FOREC_LEVELS_UNDIFF = new JSCTypeDef(
            "FOREC_LEVELS_UNDIFF", JSCTypes.NARRAY,
            "the 1st undifferenced forecasts (h x k*3 points~lowerCIs~upperCIs)");

    public static final JSCTypeDef FOREC_EYH = new JSCTypeDef("FOREC_EYH",
            JSCTypes.NARRAY, "the complete Forecast MSE matrices (h*k x k)");

    public static final JSCTypeDef FOREC_PHI = new JSCTypeDef("FOREC_PHI",
            JSCTypes.NARRAY,
            "the matrizes of the MA respresentation (k x k*(h+1))");

    private int h = 1;

    private JSCNArray yt = null;

    private JSCNArray yt_levels = null;

    private JSCNArray y_orig_toPlot = null;

    private JSCNArray d_forec = null;

    private JSCNArray x_forec = null;

    private JSCNArray sel_forec = null;

    private boolean isUndiff = false;

    private boolean isAsym = false;

    private double coverage = 0.95;

    private TSDate startPlot = null;

    private JSCNArray yraw = null;

    private JSCNArray xraw = null;

    private JSCNArray dall = null;

    private JSCSArray ny = null;

    private JSCSArray nx = null;

    private JSCNArray cy = null;

    private JSCNArray cx = null;

    private JSCNArray cd = null;

    private JSCNArray ay = null;

    private JSCNArray ax = null;

    private JSCNArray ad = null;

    private JSCNArray covar = null;

    private TSDate endDate = null;

    private int py = 0;

    private int px = 0;

    JSCNArray estForecasts = null;

    /**
     * <code>ForecastCall</code> constructor takes the arguments for the
     * procedure call.
     */
    public ForecastCall(SymbolTable inputSymbolTable, int h,
            JSCNArray sel_forc, JSCNArray yt, JSCNArray yt_levels,
            JSCNArray y_orig_toPlot, JSCNArray d_forec, JSCNArray x_forec,
            ModelTypes modelType, boolean isUndiff, boolean isAsym,
            double coverage, TSDate startPlot, JSCNArray adUserSupplied) {

        super();

        if (inputSymbolTable == null)
            throw new IllegalArgumentException("Input symbol table was null.");

        setName("Computation of Forecast");

        this.h = h;
        this.yt = yt;
        this.yt_levels = yt_levels;
        this.y_orig_toPlot = y_orig_toPlot;

        this.d_forec = d_forec;
        this.x_forec = x_forec;
        this.sel_forec = sel_forc;

        this.isUndiff = isUndiff;
        this.isAsym = isAsym;
        this.coverage = coverage;
        this.startPlot = startPlot;

        // Set the data according to the model.
        if (modelType == ModelTypes.VAR) {
            yraw = inputSymbolTable.get(VARConstants.y_raw_Def).getJSCNArray();
            xraw = inputSymbolTable.get(VARConstants.x_raw_Def).getJSCNArray();
            dall = inputSymbolTable.get(VARConstants.d_all_Def).getJSCNArray();
            ny = inputSymbolTable.get(VARConstants.Ny_Def).getJSCSArray();
            nx = inputSymbolTable.get(VARConstants.Nx_Def).getJSCSArray();
            cy = inputSymbolTable.get(VARConstants.Cy_Def).getJSCNArray();
            cx = inputSymbolTable.get(VARConstants.Cx_Def).getJSCNArray();
            cd = inputSymbolTable.get(VARConstants.Cd_Def).getJSCNArray();
            ay = inputSymbolTable.get(VARConstants.Ay_Def).getJSCNArray();
            ax = inputSymbolTable.get(VARConstants.Ax_Def).getJSCNArray();
            if (adUserSupplied == null)
                ad = inputSymbolTable.get(VARConstants.Ad_Def).getJSCNArray();
            else
                ad = adUserSupplied;
            covar = inputSymbolTable.get(VARConstants.cv_u_hat_Def)
                    .getJSCNArray();

            TSDateRange range = inputSymbolTable.get(VARConstants.T1_Def)
                    .getJSCDRange().getTSDateRange();

            double end = range.upperBound().doubleValue();
            int period = startPlot.subPeriodicity();
            endDate = new TSDate(end, period);
            py = inputSymbolTable.get(VARConstants.py_Def).getJSCInt().intVal();
            px = inputSymbolTable.get(VARConstants.px_Def).getJSCInt().intVal();

            return;
        }
        if (modelType == ModelTypes.VECM) {
            yraw = inputSymbolTable.get(VECMConstants.y_raw_Def).getJSCNArray();
            xraw = inputSymbolTable.get(VECMConstants.x_raw_Def).getJSCNArray();
            dall = inputSymbolTable.get(VECMConstants.d_Def).getJSCNArray();
            ny = inputSymbolTable.get(VECMConstants.Ny_Def).getJSCSArray();
            nx = inputSymbolTable.get(VECMConstants.Nx_Def).getJSCSArray();
            ay = inputSymbolTable.get(VECMConstants.A_Def).getJSCNArray();
            ax = inputSymbolTable.get(VECMConstants.B_Def).getJSCNArray();
            if (adUserSupplied == null)
                ad = inputSymbolTable.get(VECMConstants.C_var_Def)
                        .getJSCNArray();
            else
                ad = adUserSupplied;
            // A_0
            JSCNArray a0 = inputSymbolTable.get(VECMConstants.A0_Def)
                    .getJSCNArray();
            // multiply with A_0^-1 to get reduced form
            if (a0 != null && !a0.isEmpty()) {
                Matrix a0Mat = a0.jamaMatrix();
                if (ay != null && !ay.isEmpty())
                    ay = new JSCNArray(ay.name(), a0Mat.solve(ay.jamaMatrix())
                            .getArray());
                if (ax != null && !ax.isEmpty())
                    ax = new JSCNArray(ax.name(), a0Mat.solve(ax.jamaMatrix())
                            .getArray());
                if (ad != null && !ad.isEmpty())
                    ad = new JSCNArray(ad.name(), a0Mat.solve(ad.jamaMatrix())
                            .getArray());
            }
            covar = inputSymbolTable.get(VECMConstants.cv_u_hat_Def)
                    .getJSCNArray();

            TSDateRange range = inputSymbolTable.get(VECMConstants.T1_Def)
                    .getJSCDRange().getTSDateRange();

            double end = range.upperBound().doubleValue();
            int period = startPlot.subPeriodicity();
            endDate = new TSDate(end, period);
            py = inputSymbolTable.get(VECMConstants.py_Def).getJSCInt()
                    .intVal();
            px = inputSymbolTable.get(VECMConstants.px_Def).getJSCInt()
                    .intVal();
            return;
        }
        throw new IllegalArgumentException("Unknown model type " + modelType
                + ".");

    }

    /**
     * Writes the text output to a string that can be referenced via
     * <code>getOutput</code>.
     */
    protected void finalCode() {
        StringBuffer buffer = output;

        double[][] result = estForecasts.getTransposed();

        if (!isUndiff)
            buffer.append("FORECASTING\n");
        else
            buffer.append("FORECASTING 1st UNDIFFERENCED SERIES\n");

        buffer
                .append("Reference: Lütkepohl (1993), IMTSA, 2ed, ch. 5.2.6, ch. 10.5\n");
        buffer.append(FArg.sprintf("%-25s %-8.2f \n", new FArg("CI coverage:")
                .add(coverage)));
        buffer.append(FArg.sprintf("%-25s %-i periods \n", new FArg(
                "forecast horizon:").add(h)));

        if (isAsym && !isUndiff)
            buffer.append("using asymptotic confidence intervals\n");
        else
            buffer.append("using standard confidence intervals\n");
        if (isUndiff) {
            buffer.append("\ny(t) in levels used in the forecast\n");
            buffer.append(FArg.sprintf("%-12s", new FArg("time")));

            for (int i = 0; i < ny.rows(); i++) {
                buffer.append(FArg.sprintf("%-20s", new FArg(ny.stringAt(i, 0)
                        + "-level(t)")));

            }
            buffer.append("\n");
            buffer.append(FArg.sprintf("%-11s", new FArg(endDate.toString())));
            for (int j = 0; j < yt_levels.cols(); j++) {
                buffer.append(FArg.sprintf("%- 20.4f", new FArg(yt_levels
                        .doubleAt(0, j))));

            }
            buffer.append("\n");

        }
        for (int k = 0; k < sel_forec.rows(); k++)
            if (sel_forec.intAt(k, 0) > 0) {
                buffer.append(FArg.sprintf("\n%-12s", new FArg("")));

                buffer.append(FArg
                        .sprintf("%-26s", new FArg(ny.stringAt(k, 0))));

                buffer.append("\n");
                buffer.append(FArg.sprintf("%-12s", new FArg("time")));

                buffer
                        .append(FArg.sprintf("%-13s%-13s%-13s%-13s\n",
                                new FArg("forecast").add("lower CI").add(
                                        "upper CI").add("+/-")));

                for (int j = 0; j < result.length; j++) {
                    buffer.append(FArg.sprintf("%-11s", new FArg(endDate
                            .addPeriods(j + 1).toString())));
                    buffer
                            .append(FArg
                                    .sprintf(
                                            "%- 13.4f%- 13.4f%- 13.4f%- 13.4f\n",
                                            new FArg(result[j][k])
                                                    .add(
                                                            result[j][k
                                                                    + sel_forec
                                                                            .rows()])
                                                    .add(
                                                            result[j][k
                                                                    + 2
                                                                    * sel_forec
                                                                            .rows()])
                                                    .add(
                                                            -(result[j][k
                                                                    + sel_forec
                                                                            .rows()] - result[j][k]))));

                }
            }

        if (ax.rows() > 0) {
            buffer.append("\nexogenous variables used in the forecast\n");
            buffer.append(FArg.sprintf("%-12s", new FArg("time")));

            for (int i = 0; i < nx.rows(); i++) {
                buffer.append(FArg
                        .sprintf("%-13s", new FArg(nx.stringAt(i, 0))));

            }

            buffer.append("\n");
            for (int i = 0; i < x_forec.rows(); i++) {
                buffer.append(FArg.sprintf("%-11s", new FArg(endDate
                        .addPeriods(i + 1).toString())));
                for (int j = 0; j < x_forec.cols(); j++) {
                    buffer.append(FArg.sprintf("%- 13.4f", new FArg(x_forec
                            .doubleAt(i, j))));

                }
                buffer.append("\n");

            }
        }

        buffer.append("\n");

        return;
    }

    /**
     * Sets up the restriction matrizes in the special form needed for the
     * procedure.
     */
    private JSCNArray prepareRestrictions() {

        JSCNArray res = new JSCNArray("Restrictions_forecast");

        if (!isAsym)
            return res;

        /* the ordering of the restrictions has to be changed */
        JSCNArray cxt = null;
        JSCNArray cxx = null;

        if (!cd.isEmpty())
            cxt = new JSCNArray(cd);

        /* several exogenous lags */
        if (px > 0 && !cx.isEmpty()) {
            cxx = new JSCNArray("cxx", cx.getCols(cx.rows(), cx.cols() - 1));

            JSCNArray xt = new JSCNArray("cxt", cx.getCols(0, cx.rows() - 1));

            if (cxt != null)
                cxt.appendCols(xt);
            else
                cxt = xt;

            res = UData.createResMat(cy, cxx, cxt, "Restrictions_forecast");
        }
        /* exogenous variables, but no lags */
        if (px == 0 && !cx.isEmpty()) {

            if (cxt != null)
                cxt.appendCols(cx);
            else
                cxt = cx;

            res = UData.createResMat(cy, cxt, null, "Restrictions_forecast");
        }
        /* no exogenous variables */
        if (cx.isEmpty())
            res = UData.createResMat(cy, cxt, null, "Restrictions_forecast");
        if (res.cols() == res.rows())
            return new JSCNArray("Restrictions_forecast");

        return res;
    }

    /**
     * 
     */
    protected void runCode() {

        JSCNArray yz[] = UData.createYZMat(dall, yraw, xraw, py, px, "yMatrix",
                "zMatrix");

        // only used for level forecasts
        TSDate start = null;
        if (!isUndiff) {
            JSCData[] all = UData
                    .mergeTS(ny, "forecast_origY", "earliestStart");
            start = ((JSCDRange) all[1]).getTSDateRange().lowerBound();
        } else
            // here startPlot is in sync with the beggining of the selected
            // series for levels
            start = startPlot;

        int T2 = start.compareTo(endDate) + 1;

        // y should be shown from t=0, which is the beginning of the modeling
        // period
        int T1 = start.compareTo(startPlot) + 1;
        double beg = start.addPeriods(T1 - 1).doubleValue();
        JSCNArray restrictions_forecast = prepareRestrictions();
        int asymCI = isAsym ? 1 : 0;

        if (getSymbolTable() != null) {
            getSymbolTable().get(FOREC_LEVELS).clear();
            getSymbolTable().get(FOREC_LEVELS_UNDIFF).clear();
            getSymbolTable().get(FOREC_EYH).clear();
            getSymbolTable().get(FOREC_PHI).clear();
        }

        engine().load("pgraph", GaussLoadTypes.LIB);
        engine().load("tools", GaussLoadTypes.LIB);
        engine().load("plot", GaussLoadTypes.LIB);
        engine().load("var", GaussLoadTypes.LIB);

        JSCNArray endForecLevel = (JSCNArray) FOREC_LEVELS.getInstance();
        JSCNArray endForecLevelUnDiff = (JSCNArray) FOREC_LEVELS_UNDIFF
                .getInstance();
        JSCNArray ey_h = (JSCNArray) FOREC_EYH.getInstance();
        JSCNArray phi = (JSCNArray) FOREC_PHI.getInstance();
        estForecasts = endForecLevel;

        engine().call(
                "endogenous_forecast",
                new JSCData[] { yt, x_forec, new JSCInt("px", px),
                        new JSCInt("py", py), d_forec, yz[1], ay, ax, ad,
                        new JSCInt("horizon", h),
                        new JSCNumber("coverage", coverage), covar,
                        new JSCInt("asymCI", asymCI), restrictions_forecast },
                new JSCData[] { endForecLevel, ey_h, phi });

        String title = "Time Series Forecasts ";

        if (isUndiff) {
            engine().call(
                    "endogenous_forecast_undiff",
                    new JSCData[] { yt_levels, endForecLevel, covar, phi,
                            new JSCNumber("coverage", coverage) },
                    new JSCData[] { endForecLevelUnDiff });
            estForecasts = endForecLevelUnDiff;

            title = "1st Undifferenced Forecasts ";

        }
        title = title + "(CI " + coverage * 100 + "%)";
        setGlobalPgraphSettings();

        engine().call(
                "plot_forecast",
                new JSCData[] { y_orig_toPlot, estForecasts,
                        new JSCNumber("T1", T1), new JSCNumber("T2", T2),
                        sel_forec, ny, new JSCString("tit", title),
                        new JSCNumber("beg", beg),
                        new JSCInt("period", startPlot.subPeriodicity()) },
                null);

        if (getSymbolTable() != null) {
            getSymbolTable().set(endForecLevel);
            getSymbolTable().set(endForecLevelUnDiff);
            getSymbolTable().set(ey_h);
            getSymbolTable().set(phi);
        }

    }
}