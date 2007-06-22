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

package de.jmulti.vecm;

import com.jstatcom.model.JSCTypeDef;
import com.jstatcom.model.JSCTypes;

/**
 * This class defines all variables used for VEC modeling that are stored in the
 * global symbol table.
 * 
 * @author A. Benkwitz, <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
public final class VECMConstants {
    /**
     * Identifier for intercept.
     */
    public static final String CONST = "CONST";

    /**
     * Identifier for trend.
     */
    public static final String TREND = "TREND";

    /**
     * Johansen strategy.
     */
    public static final int JOHANSEN = 1;

    /**
     * S2S (Lütkepohl) strategy.
     */
    public static final int S2S = 2;

    /**
     * Two stage strategy.
     */
    public static final int TWO_STAGE = 3;

    /**
     * 3x1 matrix with test of restricted model against full model.\nLR
     * statistic | p-value of Chi2(resnum) | resnum".
     */
    public static final JSCTypeDef lrtest_Def = new JSCTypeDef(
            "LR_Test",
            JSCTypes.NARRAY,
            "3x1 matrix with test of restricted model against full model,\nLR statistic | p-value of Chi2(resnum) | resnum");

    /**
     * Endogenous coeff selection matrix for recursive coefficients computation.
     */
    public static final JSCTypeDef cy_recCoeff_Def = new JSCTypeDef(
            "cy_recCoeff", JSCTypes.NARRAY,
            "Endogenous coeff selection matrix for recursive coefficients computation.");

    /**
     * Selection matrix for recursive coefficients computation.
     */
    public static final JSCTypeDef cx_recCoeff_Def = new JSCTypeDef(
            "cx_recCoeff", JSCTypes.NARRAY,
            "Exogenous coeff selection matrix for recursive coefficients computation.");

    /**
     * Structural coeff selection matrix for recursive coefficients computation.
     */
    public static final JSCTypeDef cy0_recCoeff_Def = new JSCTypeDef(
            "cy0_recCoeff", JSCTypes.NARRAY,
            "Structural coeff selection matrix for recursive coefficients computation.");

    /**
     * Deterministic coeff selection matrix for recursive coefficients
     * computation.
     */
    public static final JSCTypeDef cd_recCoeff_Def = new JSCTypeDef(
            "cd_recCoeff", JSCTypes.NARRAY,
            "Deterministic coeff selection matrix for recursive coefficients computation.");

    /**
     * Alpha coeff selection matrix for recursive coefficients computation.
     */
    public static final JSCTypeDef alpha_recCoeff_Def = new JSCTypeDef(
            "alpha_recCoeff", JSCTypes.NARRAY,
            "Alpha coeff selection matrix for recursive coefficients computation.");

    /**
     * Matrix H of external represenation of restrictions on beta in
     * vec(beta_k-r) = H*eta+h.
     */
    public static final JSCTypeDef resHmat_Def = new JSCTypeDef("resHmat",
            JSCTypes.NARRAY,
            "Matrix H of external represenation of restrictions on beta\r\n"
                    + "in vec(beta_k-r) = H*eta+h.");

    /**
     * Index of free parameters marking elements of vec(beta_k-r) to be
     * estimated. This defines the elements of matrix eta in vec(beta_k-r) =
     * H*eta+h.
     */
    public static final JSCTypeDef resFreeParamIndex_Def = new JSCTypeDef(
            "resFreeParamIndex",
            JSCTypes.NARRAY,
            "Index of free parameters marking elements of vec(beta_k-r)\r\n"
                    + "to be estimated. This defines the elements of matrix eta in vec(beta_k-r) = H*eta+h.");

    /**
     * Matrix h of external represenation of restrictions on beta in
     * vec(beta_k-r) = H*eta+h.
     */
    public static final JSCTypeDef resH_Def = new JSCTypeDef(
            "resH",
            JSCTypes.NARRAY,
            "Matrix h of external represenation of restrictions on beta in vec(beta_k-r) = H*eta+h.");

    /**
     * The range of the sample period as a string for display.
     */
    public static final JSCTypeDef estRange_Def = new JSCTypeDef(
            "estimation_Range", JSCTypes.STRING,
            "The range of the sample period as string for display.");

    /**
     * Deterministic data without lag truncation and extra assembled standard
     * CONST, TREND, Si.
     */
    public static final JSCTypeDef d_raw_Def = new JSCTypeDef(
            "d_raw",
            JSCTypes.NARRAY,
            "Deterministic data without lag truncation and extra assembled standard CONST, TREND, Si.");

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
     * Names of deterministic variables without extra assembled parts.
     */
    public static final JSCTypeDef Nd_raw_Def = new JSCTypeDef("Nd_raw",
            JSCTypes.SARRAY,
            "Names of deterministic variables without extra assembled parts.");

    /**
     * Names of deterministic variables in VEC model.
     */
    public static final JSCTypeDef Nd_vec_Def = new JSCTypeDef("Nd_vec",
            JSCTypes.SARRAY, "Names of deterministic variables in VEC model.");

    /**
     * Names of endogenous variables.
     */
    public static final JSCTypeDef Ny_Def = new JSCTypeDef("Ny",
            JSCTypes.SARRAY, "Names of endogenous variables.");

    /**
     * Names of exogenous variables.
     */
    public static final JSCTypeDef Nx_Def = new JSCTypeDef("Nx",
            JSCTypes.SARRAY, " Names of exogenous variables.");

    /**
     * Names of deterministic variables with all parts.
     */
    public static final JSCTypeDef Nd_Def = new JSCTypeDef("Nd",
            JSCTypes.SARRAY, "Names of deterministic variables with all parts.");

    /**
     * Date range without lag truncation.
     */
    public static final JSCTypeDef T1_raw_Def = new JSCTypeDef("T1_raw",
            JSCTypes.DRANGE, "Date range without lag truncation.");

    /**
     * A 4 x 1 vector indicating the selection of additional deterministics
     * {CONST, SEAS, TREND, STD(1)-CENTERED(0)}. A zero indicates no selection
     * of the respective deterministic variable, a one indicates selection.
     */
    public static final JSCTypeDef det_sel_Def = new JSCTypeDef(
            "det_sel",
            JSCTypes.NARRAY,
            "A 4 x 1 vector indicating the selection of additional deterministics\r\n"
                    + "{CONST, SEAS, TREND, STD(1)-CENTERED(0)}. A zero indicates no selection of the respective\r\n"
                    + "deterministic variable, a one indicates selection.");

    /**
     * Indicates the current estimation strategy, 1: Johanson, 2: S2S, 3: Two
     * Stage.
     */
    public static final JSCTypeDef estimationStrategy_Def = new JSCTypeDef(
            "estimationStrategy", JSCTypes.INT,
            "Indicates the current estimation strategy:\r\n"
                    + "1: Johanson\r\n" + "2: S2S\r\n" + "3: Two Stage");

    /**
     * The cointegration rank for the VEC model 1 &le; r &lt; K.
     */
    public static final JSCTypeDef cointRank_Def = new JSCTypeDef("r",
            JSCTypes.INT,
            "The cointegration rank for the VEC model 1 <= r < K. ");

    /**
     * Eigenvalues of the reverse characterisic polynomial.
     */
    public static final JSCTypeDef eigv_modulus_Def = new JSCTypeDef(
            "eigv_modulus", JSCTypes.NARRAY,
            "Eigenvalues of the reverse characterisic polynomial.");

    /**
     * Names of lagged endogenous variables.
     */
    public static final JSCTypeDef Ndy_Def = new JSCTypeDef("Ndy",
            JSCTypes.SARRAY, "Names of lagged endogenous variables. ");

    /**
     * Estimated structural coefficients in VEC model.
     */
    public static final JSCTypeDef G0_Def = new JSCTypeDef("G0",
            JSCTypes.NARRAY, "Estimated structural coefficients in VEC model.");

    /**
     * T-values of structural coefficients in VEC model.
     */
    public static final JSCTypeDef TV_G0_Def = new JSCTypeDef("TV_G0",
            JSCTypes.NARRAY,
            "T-values of structural coefficients in VEC model.");

    /**
     * Standard deviations of structural coefficients in VEC model.
     */
    public static final JSCTypeDef SD_G0_Def = new JSCTypeDef("SD_G0",
            JSCTypes.NARRAY,
            "Standard deviations of structural coefficients in VEC model.");

    /**
     * Matrix of structural parameters in VAR representation of VEC model.
     */
    public static final JSCTypeDef A0_Def = new JSCTypeDef("A0",
            JSCTypes.NARRAY,
            "Matrix of structural parameters in VAR representation of VEC model.");

    /**
     * T-values of the matrix of structural parameters in VAR representation of
     * VEC model.
     */
    public static final JSCTypeDef TV_A0_Def = new JSCTypeDef(
            "TV_A0",
            JSCTypes.NARRAY,
            "T-values of the matrix of structural parameters in VAR representation of VEC model.");

    /**
     * Standard deviations of the matrix of structural parameters in VAR
     * representation of VEC model.
     */
    public static final JSCTypeDef SD_A0_Def = new JSCTypeDef(
            "SD_A0",
            JSCTypes.NARRAY,
            "Standard deviations of the matrix of structural parameters in VAR representation of VEC model.");

    /**
     * Estimated endogenous coefficients of VAR representation of VEC model.
     */
    public static final JSCTypeDef A_Def = new JSCTypeDef("A", JSCTypes.NARRAY,
            "Estimated endogenous coefficients of VAR representation of VEC model.");

    /**
     * T-values of endogenous coefficients of VAR representation of VEC model.
     */
    public static final JSCTypeDef TV_A_Def = new JSCTypeDef("TV_A",
            JSCTypes.NARRAY,
            "T-values of endogenous coefficients of VAR representation of VEC model.");

    /**
     * Standard deviation of endogenous coefficients of VAR representation of
     * VEC model.
     */
    public static final JSCTypeDef SD_A_Def = new JSCTypeDef(
            "SD_A",
            JSCTypes.NARRAY,
            "Standard deviation of endogenous coefficients of VAR representation of VEC model.");

    /**
     * Estimated coefficients of lagged endogenous variables.
     */
    public static final JSCTypeDef G_Def = new JSCTypeDef("G", JSCTypes.NARRAY,
            "Estimated coefficients of lagged endogenous variables.");

    /**
     * T-values of coefficients of lagged endogenous variables.
     */
    public static final JSCTypeDef TV_G_Def = new JSCTypeDef("TV_G",
            JSCTypes.NARRAY,
            " T-values of coefficients of lagged endogenous variables.");

    /**
     * Standard deviations of coefficients of lagged endogenous variables.
     */
    public static final JSCTypeDef SD_G_Def = new JSCTypeDef("SD_G",
            JSCTypes.NARRAY,
            "Standard deviations of coefficients of lagged endogenous variables.");

    /**
     * Number of endogenous lags in VEC representation.
     */
    public static final JSCTypeDef pdy_Def = new JSCTypeDef("pdy",
            JSCTypes.INT, "Number of endogenous lags in VEC representation.");

    /**
     * Number of endogenous lags in VAR representation.
     */
    public static final JSCTypeDef py_Def = new JSCTypeDef("py", JSCTypes.INT,
            "Number of endogenous lags in VAR representation.");

    /**
     * Number of endogenous lags in VAR representation for Granger causality
     * test.
     */
    public static final JSCTypeDef py_granger_Def = new JSCTypeDef(
            "py_granger", JSCTypes.INT,
            "Number of endogenous lags in VAR representation for Granger causality test.");

    /**
     * Estimated loading parameters.
     */
    public static final JSCTypeDef alpha_Def = new JSCTypeDef("alpha",
            JSCTypes.NARRAY, "Estimated loading parameters.");

    /**
     * T-values of estimated loading parameters.
     */
    public static final JSCTypeDef TV_alpha_Def = new JSCTypeDef("TV_alpha",
            JSCTypes.NARRAY, "T-values of estimated loading parameters.");

    /**
     * Standard deviation of estimated loading parameters.
     */
    public static final JSCTypeDef SD_alpha_Def = new JSCTypeDef("SD_alpha",
            JSCTypes.NARRAY,
            "Standard deviation of estimated loading parameters.");

    /**
     * Names of equations in EC term.
     */
    public static final JSCTypeDef Nec_Def = new JSCTypeDef("Nec",
            JSCTypes.SARRAY, "Names of equations in EC term.");

    /**
     * Estimated coefficients of exogenous variables.
     */
    public static final JSCTypeDef B_Def = new JSCTypeDef("B", JSCTypes.NARRAY,
            "Estimated coefficients of exogenous variables.");

    /**
     * T-values of coefficients of exogenous variables.
     */
    public static final JSCTypeDef TV_B_Def = new JSCTypeDef("TV_B",
            JSCTypes.NARRAY, "T-values of coefficients of exogenous variables.");

    /**
     * Standard deviations of coefficients of exogenous variables.
     */
    public static final JSCTypeDef SD_B_Def = new JSCTypeDef("SD_B",
            JSCTypes.NARRAY,
            "Standard deviations of coefficients of exogenous variables.");

    /**
     * Number of exogenous lags.
     */
    public static final JSCTypeDef px_Def = new JSCTypeDef("px", JSCTypes.INT,
            "Number of exogenous lags.");

    /**
     * Estimated deterministic coefficients of VAR representation of VEC model.
     */
    public static final JSCTypeDef C_var_Def = new JSCTypeDef("C_var",
            JSCTypes.NARRAY,
            "Estimated deterministic coefficients of VAR representation of VEC model.");

    /**
     * T-values of deterministic coefficients of VAR representation of VEC
     * model.
     */
    public static final JSCTypeDef TV_C_var_Def = new JSCTypeDef("TV_C_var",
            JSCTypes.NARRAY,
            "T-values of deterministic coefficients of VAR representation of VEC model.");

    /**
     * Standard deviation of deterministic coefficients of VAR representation of
     * VEC model.
     */
    public static final JSCTypeDef SD_C_var_Def = new JSCTypeDef(
            "SD_C_var",
            JSCTypes.NARRAY,
            "Standard deviation of deterministic coefficients of VAR representation of VEC model.");

    /**
     * Estimated deterministic coefficients of VEC model.
     */
    public static final JSCTypeDef C_vec_Def = new JSCTypeDef("C_vec",
            JSCTypes.NARRAY,
            "Estimated deterministic coefficients of VEC model.");

    /**
     * T-values of deterministic coefficients of VEC model.
     */
    public static final JSCTypeDef TV_C_vec_Def = new JSCTypeDef("TV_C_vec",
            JSCTypes.NARRAY,
            "T-values of deterministic coefficients of VEC model.");

    /**
     * Standard deviation of deterministic coefficients of VEC model.
     */
    public static final JSCTypeDef SD_C_vec_Def = new JSCTypeDef("SD_C_vec",
            JSCTypes.NARRAY,
            "Standard deviation of deterministic coefficients of VEC model.");

    /**
     * Names of the residuals of the single equations.
     */
    public static final JSCTypeDef Nu_Def = new JSCTypeDef("Nu",
            JSCTypes.SARRAY, "Names of the residuals of the single equations.");

    /**
     * Names of the deterministic variables restricted to the EC term.
     */
    public static final JSCTypeDef Nd_ec_Def = new JSCTypeDef("Nd_ec",
            JSCTypes.SARRAY,
            "Names of the deterministic variables restricted to the EC term.");

    /**
     * Predefined cointegration vectors that are not estimated.
     */
    public static final JSCTypeDef beta_x_Def = new JSCTypeDef("beta_x",
            JSCTypes.NARRAY,
            "Predefined cointegration vectors that are not estimated.");

    /**
     * Predefined cointegration vectors for deterministic part that are not
     * estimated.
     */
    public static final JSCTypeDef beta_d_x_Def = new JSCTypeDef(
            "beta_d_x",
            JSCTypes.NARRAY,
            "Predefined cointegration vectors for deterministic part that are not estimated.");

    /**
     * Estimated cointegration vector beta for the endogenous levels.
     */
    public static final JSCTypeDef beta_Def = new JSCTypeDef("beta",
            JSCTypes.NARRAY,
            "Estimated cointegration vector beta for the endogenous levels.");

    /**
     * Estimated cointegration vector beta for the deterministic variables
     * restricted to the EC term.
     */
    public static final JSCTypeDef beta_d_Def = new JSCTypeDef(
            "beta_d",
            JSCTypes.NARRAY,
            "Estimated cointegration vector beta for the deterministic variables restricted to the EC term.");

    /**
     * Standard deviations of the estimated cointegration vector for the
     * endogenous levels.
     */
    public static final JSCTypeDef SD_beta_Def = new JSCTypeDef(
            "SD_beta",
            JSCTypes.NARRAY,
            "Standard deviations of the estimated cointegration vector for the endogenous levels.");

    /**
     * Standard deviations of the estimated cointegration vector for the
     * deterministic variables restricted to the EC term.
     */
    public static final JSCTypeDef SD_beta_d_Def = new JSCTypeDef(
            "SD_beta_d",
            JSCTypes.NARRAY,
            "Standard deviations of the estimated cointegration vector for the deterministic variables restricted to the EC term.");

    /**
     * T-values of the estimated cointegration vector for the endogenous levels.
     */
    public static final JSCTypeDef TV_beta_Def = new JSCTypeDef("TV_beta",
            JSCTypes.NARRAY,
            "T-values of the estimated cointegration vector for the endogenous levels.");

    /**
     * T-values of the estimated cointegration vector for the deterministic
     * variables restricted to the EC term.
     */
    public static final JSCTypeDef TV_beta_d_Def = new JSCTypeDef(
            "TV_beta_d",
            JSCTypes.NARRAY,
            "T-values of the estimated cointegration vector for the deterministic variables restricted to the EC term.");

    /**
     * Names of all deterministic variables used in the VAR representation of
     * the VEC model.
     */
    public static final JSCTypeDef Nd_var_Def = new JSCTypeDef(
            "Nd_var",
            JSCTypes.SARRAY,
            "Names of all deterministic variables used in the VAR representation of the VEC model.");

    /**
     * Keeps detailed information about estimation method applied for estimating
     * the VECM. It is assembled by the GAUSS code and read back after
     * estimation.
     */
    public static final JSCTypeDef estimationMethod_Def = new JSCTypeDef(
            "estimationMethod",
            JSCTypes.STRING,
            "Keeps detailed information about estimation method applied for estimating the VECM.\r\n"
                    + "It is assembled by the GAUSS code and read back after estimation.");

    /**
     * Observations for endogenous variables.
     */
    public static final JSCTypeDef y_Def = new JSCTypeDef("y", JSCTypes.NARRAY,
            "Observations for endogenous variables.");

    /**
     * Observations for exogenous variables.
     */
    public static final JSCTypeDef x_Def = new JSCTypeDef("x", JSCTypes.NARRAY,
            "Observations for exogenous variables.");

    /**
     * Observations for deterministic variables (not splitted!)
     */
    public static final JSCTypeDef d_Def = new JSCTypeDef("d", JSCTypes.NARRAY,
            "Observations for deterministic variables (not splitted!)");

    /**
     * Indices of deterministic variables that are restricted to the
     * cointegration relation. (unlagged)
     */
    public static final JSCTypeDef idx_cd2ci_Def = new JSCTypeDef(
            "idx_cd2ci",
            JSCTypes.NARRAY,
            "Indices of deterministic variables that are restricted to the cointegration relation (unlagged).");

    /**
     * Number of cointegration relations estimated by Johanson's method in the
     * first stage.
     */
    public static final JSCTypeDef firstStage_crJoh_Def = new JSCTypeDef(
            "r_estJOH",
            JSCTypes.INT,
            "Number of cointegration relations estimated by Johanson's method in the first stage.");

    /**
     * 1 if first stage is estimated by Johansen approach, otherwise 0.
     */
    public static final JSCTypeDef firstStage_byJoh_Def = new JSCTypeDef(
            "firstStage_byJoh", JSCTypes.INT,
            "1 if first stage is estimated by Johansen approach, otherwise 0.");

    /**
     * 1 if first stage is estimated from single equation, otherwise 0.
     */
    public static final JSCTypeDef firstStage_bySingleEquation_Def = new JSCTypeDef(
            "firstStage_bySingleEquation", JSCTypes.INT,
            "1 if first stage is estimated from single equation, otherwise 0.");

    /**
     * 1 if first stage is estimated by the S2S (Lütkepohl) method.
     */
    public static final JSCTypeDef firstStage_byS2S_Def = new JSCTypeDef(
            "firstStage_byS2S", JSCTypes.INT,
            "1 if first stage is estimated by the S2S (Lütkepohl) method.");

    /**
     * Index of the equation in the system that the cointegration relation is
     * estimated from in the first stage.
     */
    public static final JSCTypeDef firstStage_equaIdx_Def = new JSCTypeDef(
            "idx_equa",
            JSCTypes.INT,
            "Index of the equation in the system that the cointegration relation is estimated from in the first stage.");

    /**
     * The method that is used for second stage estimation.
     */
    public static final JSCTypeDef secondStage_method_Def = new JSCTypeDef(
            "secondStage_method", JSCTypes.STRING,
            "The method that is used for second stage estimation.");

    /**
     * The restriction matrix for structural coefficients.
     */
    public static final JSCTypeDef S_G0_Def = new JSCTypeDef("S_G0",
            JSCTypes.NARRAY,
            "The restriction matrix for structural coefficients.");

    /**
     * Subset restrictions for lagged endogenous coefficients.
     */
    public static final JSCTypeDef S_G_Def = new JSCTypeDef("S_G",
            JSCTypes.NARRAY,
            "Subset restrictions for lagged endogenous coefficients. ");

    /**
     * Subset restriction for loading coefficients.
     */
    public static final JSCTypeDef S_alpha_Def = new JSCTypeDef("S_alpha",
            JSCTypes.NARRAY, "Subset restriction for loading coefficients.");

    /**
     * Subset restrictions on the cointegration vector for the endogenous
     * levels.
     */
    public static final JSCTypeDef S_beta_Def = new JSCTypeDef("S_beta",
            JSCTypes.NARRAY,
            "Subset restrictions on the cointegration vector for the endogenous levels.");

    /**
     * Subset restrictions on the cointegration vector for the deterministic
     * variables restricted to the EC term.
     */
    public static final JSCTypeDef S_beta_d_Def = new JSCTypeDef(
            "S_beta_d",
            JSCTypes.NARRAY,
            "Subset restrictions on the cointegration vector for the deterministic variables restricted to the EC term.");

    /**
     * Subset restrictions for exogenous coefficients.
     */
    public static final JSCTypeDef S_B_Def = new JSCTypeDef("S_B",
            JSCTypes.NARRAY, "Subset restrictions for exogenous coefficients. ");

    /**
     * Subset restrictions for deterministic coefficients.
     */
    public static final JSCTypeDef S_C_VEC_Def = new JSCTypeDef("S_C_VEC",
            JSCTypes.NARRAY,
            "Subset restrictions for deterministic coefficients. ");

    /**
     * The date range of the sample (lag truncated).
     */
    public static final JSCTypeDef T1_Def = new JSCTypeDef("T1",
            JSCTypes.DRANGE, "The date range of the sample (lag truncated).");

    /**
     * Estimated residuals of VEC model.
     */
    public static final JSCTypeDef u_hat_Def = new JSCTypeDef("u_hat",
            JSCTypes.NARRAY, "Estimated residuals of VEC model.");

    /**
     * Estimated residual covariance matrix of VEC model
     */
    public static final JSCTypeDef cv_u_hat_Def = new JSCTypeDef("cv_u_hat",
            JSCTypes.NARRAY,
            "Estimated residual covariance matrix of VEC model");

    /**
     * Not to be invoked.
     * 
     */
    private VECMConstants() {
    }
}