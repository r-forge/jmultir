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

package de.jmulti.var;

import com.jstatcom.model.JSCTypeDef;
import com.jstatcom.model.JSCTypes;

/**
 * This class defines all variables used for VAR modeling that are stored in the
 * global symbol table and that might be reused by other components as well.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
public final class VARConstants {

    /**
     * Identifier for intercept.
     */
    public static final String CONST = "CONST";

    /**
     * Identifier for trend.
     */
    public static final String TREND = "TREND";

    /**
     * 3x1 matrix with test of restricted model against full model.\nLR
     * statistic | p-value of Chi2(resnum) | resnum".
     */
    public static final JSCTypeDef lrtest_Def = new JSCTypeDef(
            "LR_Test",
            JSCTypes.NARRAY,
            "3x1 matrix with test of restricted model against full model,\nLR statistic | p-value of Chi2(resnum) | resnum");

    /**
     * Covariance matrix of estimated coefficients no divided by T.
     */
    public static final JSCTypeDef cv_par_Def = new JSCTypeDef(
            "cv_Coeff",
            JSCTypes.NARRAY,
            "Covariance matrix of estimated coefficients (not divided by T yet!).\r\nordering: endogenous - exogenous - deterministic");

    /**
     * Endogenous coeff selection matrix for recursive coefficients computation.
     */
    public static final JSCTypeDef cy_recCoeff_Def = new JSCTypeDef(
            "cy_recCoeff", JSCTypes.NARRAY,
            "Endogenous coeff selection matrix for recursive coefficients computation.");

    /**
     * Exogenous coeff selection matrix for recursive coefficients computation.
     */
    public static final JSCTypeDef cx_recCoeff_Def = new JSCTypeDef(
            "cx_recCoeff", JSCTypes.NARRAY,
            "Exogenous coeff selection matrix for recursive coefficients computation.");

    /**
     * Deterministic coeff selection matrix for recursive coefficients
     * computation.
     */
    public static final JSCTypeDef cd_recCoeff_Def = new JSCTypeDef(
            "cd_recCoeff", JSCTypes.NARRAY,
            "Deterministic coeff selection matrix for recursive coefficients computation.");

    /**
     * The range of the sample period as string for display.
     */
    public static final JSCTypeDef estRange_Def = new JSCTypeDef(
            "estimation_Range", JSCTypes.STRING,
            "The range of the sample period as string for display.");

    /**
     * Names of endogenous variables.
     */
    public static final JSCTypeDef Ny_Def = new JSCTypeDef("Ny",
            JSCTypes.SARRAY, "Names of endogenous variables.");

    /**
     * Names of exogenous variables.
     */
    public static final JSCTypeDef Nx_Def = new JSCTypeDef("Nx",
            JSCTypes.SARRAY, "Names of exogenous variables.");

    /**
     * Names of deterministic variables with assembled CONST, TREND, Si.
     */
    public static final JSCTypeDef Nd_Def = new JSCTypeDef("Nd",
            JSCTypes.SARRAY,
            "Names of deterministic variables with assembled CONST, TREND, Si.");

    /**
     * Names of deterministic variables selected in <code>TSSel</code>.
     */
    public static final JSCTypeDef Nd_raw_Def = new JSCTypeDef("Nd_raw",
            JSCTypes.SARRAY,
            "Names of deterministic variables selected in TSSel.");

    /**
     * Names of residuals.
     */
    public static final JSCTypeDef Nu_Def = new JSCTypeDef("Nu",
            JSCTypes.SARRAY, "Names of residuals.");

    /**
     * Lag truncated endogenous data.
     */
    public static final JSCTypeDef y_Def = new JSCTypeDef("y", JSCTypes.NARRAY,
            "Lag truncated endogenous data.");

    /**
     * Lag truncated exogenous data.
     */
    public static final JSCTypeDef x_Def = new JSCTypeDef("x", JSCTypes.NARRAY,
            "Lag truncated exogenous data.");

    /**
     * Endogenous data without lag truncation.
     */
    public static final JSCTypeDef y_raw_Def = new JSCTypeDef("y_raw",
            JSCTypes.NARRAY, "Endogenous data without lag truncation.");

    /**
     * Exogenous data without lag truncation.
     */
    public static final JSCTypeDef x_raw_Def = new JSCTypeDef("x_raw",
            JSCTypes.NARRAY, "Exogenous data without lag truncation.");

    /**
     * Deterministic data without lag truncation and extra assembled standard
     * CONST, TREND, Si.
     */
    public static final JSCTypeDef d_raw_Def = new JSCTypeDef(
            "d_raw",
            JSCTypes.NARRAY,
            "Deterministic data without lag truncation and extra assembled standard CONST, TREND, Si.");

    /**
     * Deterministic data without lag truncation with extra assembled standard
     * CONST, TREND, Si.
     */
    public static final JSCTypeDef d_all_Def = new JSCTypeDef(
            "d_all",
            JSCTypes.NARRAY,
            "Deterministic data without lag truncation with extra assembled standard CONST, TREND, Si.");

    /**
     * Start date range with lag truncation.
     */
    public static final JSCTypeDef T1_Def = new JSCTypeDef("T1",
            JSCTypes.DRANGE, "Start date range with lag truncation.");

    /**
     * Start date range without lag truncation.
     */
    public static final JSCTypeDef T1_raw_Def = new JSCTypeDef("T1_raw",
            JSCTypes.DRANGE, "Start date range without lag truncation.");

    /**
     * The modulus of the eigenvalues of the reverse characteristic polynomial.
     */
    public static final JSCTypeDef eigv_modulus_Def = new JSCTypeDef(
            "eigv_modulus", JSCTypes.NARRAY,
            "The modulus of the eigenvalues of the reverse characteristic polynomial.");

    /**
     * The estimated covariance matrix of residuals.
     */
    public static final JSCTypeDef cv_u_hat_Def = new JSCTypeDef("cv_u_hat",
            JSCTypes.NARRAY, "The estimated covariance matrix of residuals.");

    /**
     * The estimated residual series.
     */
    public static final JSCTypeDef u_hat_Def = new JSCTypeDef("u_hat",
            JSCTypes.NARRAY, "The estimated residual series.");

    /**
     * The number of endogenous lags.
     */
    public static final JSCTypeDef py_Def = new JSCTypeDef("py", JSCTypes.INT,
            "The number of endogenous lags.");

    /**
     * The number of exogenous lags.
     */
    public static final JSCTypeDef px_Def = new JSCTypeDef("px", JSCTypes.INT,
            "The number of exogenous lags.");

    /**
     * The estimated endogenous coefficients.
     */
    public static final JSCTypeDef Ay_Def = new JSCTypeDef("Ay",
            JSCTypes.NARRAY, "The estimated endogenous coefficients.");

    /**
     * The standard deviation of the estimated endogenous coefficients.
     */
    public static final JSCTypeDef SD_Ay_Def = new JSCTypeDef("SD_Ay",
            JSCTypes.NARRAY,
            "The standard deviation of the estimated endogenous coefficients.");

    /**
     * The t-values of the estimated endogenous coefficients.
     */
    public static final JSCTypeDef TV_Ay_Def = new JSCTypeDef("TV_Ay",
            JSCTypes.NARRAY,
            "The t-values of the estimated endogenous coefficients.");

    /**
     * The estimated exogenous coefficients.
     */
    public static final JSCTypeDef Ax_Def = new JSCTypeDef("Ax",
            JSCTypes.NARRAY, "The estimated exogenous coefficients.");

    /**
     * The standard deviation of the estimated exogenous coefficients.
     */
    public static final JSCTypeDef SD_Ax_Def = new JSCTypeDef("SD_Ax",
            JSCTypes.NARRAY,
            " The standard deviation of the estimated exogenous coefficients.");

    /**
     * The t-values of the estimated exogenous coefficients.
     */
    public static final JSCTypeDef TV_Ax_Def = new JSCTypeDef("TV_Ax",
            JSCTypes.NARRAY,
            "The t-values of the estimated exogenous coefficients.");

    /**
     * The estimated deterministic coefficients.
     */
    public static final JSCTypeDef Ad_Def = new JSCTypeDef("Ad",
            JSCTypes.NARRAY, "The estimated deterministic coefficients.");

    /**
     * The standard deviation of the estimated deterministic coefficients.
     */
    public static final JSCTypeDef SD_Ad_Def = new JSCTypeDef("SD_Ad",
            JSCTypes.NARRAY,
            "The standard deviation of the estimated deterministic coefficients.");

    /**
     * The t-values of the estimated deterministic coefficients.
     */
    public static final JSCTypeDef TV_Ad_Def = new JSCTypeDef("TV_Ad",
            JSCTypes.NARRAY,
            "The t-values of the estimated deterministic coefficients.");

    /**
     * The subset restrictions on the endogenous coefficients.
     */
    public static final JSCTypeDef Cy_Def = new JSCTypeDef("Cy",
            JSCTypes.NARRAY,
            "The subset restrictions on the endogenous coefficients.");

    /**
     * The subset restrictions on the exogenous coefficients.
     */
    public static final JSCTypeDef Cx_Def = new JSCTypeDef("Cx",
            JSCTypes.NARRAY,
            "The subset restrictions on the exogenous coefficients.");

    /**
     * The subset restrictions on the deterministic coefficients.
     */
    public static final JSCTypeDef Cd_Def = new JSCTypeDef("Cd",
            JSCTypes.NARRAY,
            "The subset restrictions on the deterministic coefficients.");

    /**
     * A 3 x 1 vector indicating the selection of additional deterministics
     * {CONST, SEAS, TREND}. A zero indicates no selection of the respective
     * deterministic variable, a one indicates selection.
     */
    public static final JSCTypeDef det_sel_Def = new JSCTypeDef(
            "det_sel",
            JSCTypes.NARRAY,
            "A 3 x 1 vector indicating the selection of additional deterministics\r\n"
                    + "{CONST, SEAS, TREND}. A zero indicates no selection of the respective\r\n"
                    + "deterministic variable, a one indicates selection.");

    /**
     * The string identifying the estimation method.
     */
    public static final JSCTypeDef est_method_Def = new JSCTypeDef(
            "estimationMethod", JSCTypes.STRING,
            " The string identifying the estimation method.");

    /**
     * Not to be invoked.
     *  
     */
    private VARConstants() {
    }
}