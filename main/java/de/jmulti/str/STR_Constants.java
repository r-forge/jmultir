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

package de.jmulti.str;

import com.jstatcom.model.JSCTypeDef;
import com.jstatcom.model.JSCTypes;

/**
 * Global constants for the STR analysis.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public final class STR_Constants {
	/**
	 * Intercept identifier.
	 */
	public static final String CONST = "CONST";
	/**
	 * Trend identifier.
	 */
	public static final String TREND = "TREND";

	/**
	 * Intercept name.
	 */
	public static final JSCTypeDef STR_CONSTNAME =
		new JSCTypeDef("STR_CONSTNAME", JSCTypes.SARRAY, "Intercept name.");
	/**
	 * Index of variables in X restricted with theta=0 in estimation.
	 */
	public static final JSCTypeDef STR_CONST_EST =
		new JSCTypeDef(
			"STR_CONST_EST",
			JSCTypes.NARRAY,
			"Index of variables in X restricted with theta=0 in estimation.");
	/**
	 * Index of variables in X restricted with phi=0 in estimation.
	 */
	public static final JSCTypeDef STR_PHIRES_EST =
		new JSCTypeDef(
			"STR_PHIRES_EST",
			JSCTypes.NARRAY,
			"Index of variables in X restricted with theta=0 in estimation.");
	/**
	 * Diagnostics for estimated STR model.
	 * <ul>
	 * <li>number of iterations    
	 * <li>AIC   
	 * <li>SBIC 
	 * <li>HQ
	 * <li>R2
	 * <li>Adjusted R2
	 * <li>Variance of the delay vector 
	 * <li>Standard deviation of the delay vector
	 * <li>Variance of the residual vector  
	 * <li>Standard deviation of the residual vector      
	 * </ul>
	 */
	public static final JSCTypeDef STR_EST_CHECKS =
		new JSCTypeDef(
			"STR_EST_CHECKS",
			JSCTypes.NARRAY,
			"Diagnostics for estimated STR model:\r\n"
				+ "number of iterations\r\n"
				+ "AIC\r\n"
				+ "SBIC\r\n"
				+ "HQ\r\n"
				+ "R2\r\n"
				+ "Adjusted R2\r\n"
				+ "Variance of the delay vector\r\n"
				+ "Standard deviation of the delay vecto\r\n"
				+ "Variance of the residual vector\r\n"
				+ "Standard deviation of the residual vector\r\n");
	/**
	 * Estimated coefficients and t-values of STR model
	 * (start~estimate~SD~t-test~p-value).
	 */
	public static final JSCTypeDef STR_EST =
		new JSCTypeDef(
			"STR_EST",
			JSCTypes.NARRAY,
			"Estimated coefficients and t-values of STR model:\r\n"
				+ "start~estimate~SD~t-test~p-value");
	/**
	 * Estimated linear part of STR model.
	 */
	public static final JSCTypeDef STR_LINPART =
		new JSCTypeDef(
			"STR_LINPART",
			JSCTypes.NARRAY,
			"Estimated linear part of STR model.");
	/**
	 * Estimated nonlinear part of STR model.
	 */
	public static final JSCTypeDef STR_NONLINPART =
		new JSCTypeDef(
			"STR_NONLINPART",
			JSCTypes.NARRAY,
			"Estimated nonlinear part of STR model.");
	/**
	 * Fitted values of STR model (linear part + nonlinear part).
	 */
	public static final JSCTypeDef STR_FITTED =
		new JSCTypeDef(
			"STR_FITTED",
			JSCTypes.NARRAY,
			"Fitted values of STR model (linear part + nonlinear part).");
	/**
	 * Zero based index of transition variable in data matrix X.
	 */
	public static final JSCTypeDef STR_TRANSINDEX =
		new JSCTypeDef(
			"STR_TRANSINDEX",
			JSCTypes.INT,
			"Zero based index of transition variable in data matrix X.");
	/**
	 * Name of transition variable used in estimation.
	 */
	public static final JSCTypeDef STR_TRANSNAME =
		new JSCTypeDef(
			"STR_TRANSNAME",
			JSCTypes.STRING,
			"Name of transition variable used in estimation.");
	/**
	 * The GAMMA startvalue.
	 */
	public static final JSCTypeDef STR_GAMMA =
		new JSCTypeDef(
			"STR_GAMMA_START",
			JSCTypes.NUMBER,
			"The GAMMA startvalue.");
	/**
	 * The C1 startvalue.
	 */
	public static final JSCTypeDef STR_C1 =
		new JSCTypeDef("STR_C1_START", JSCTypes.NUMBER, "The C1 startvalue.");
	/**
	 * The C2 startvalue.
	 */
	public static final JSCTypeDef STR_C2 =
		new JSCTypeDef("STR_C2_START", JSCTypes.NUMBER, "The C2 startvalue.");
	/**
	 * All potential regressors plus trend.
	 * Contains all possible transition variables.
	 */
	public static final JSCTypeDef STR_X =
		new JSCTypeDef(
			"STR_X",
			JSCTypes.NARRAY,
			"All potential regressors plus trend.\r\nContains all possible transition variables.");
	/**
	 * Lag truncated endogenous data.
	 */
	public static final JSCTypeDef STR_Y =
		new JSCTypeDef(
			"STR_Y",
			JSCTypes.NARRAY,
			"Lag truncated endogenous data.");
	/**
	 * Lag truncated deterministic data
	 */
	public static final JSCTypeDef STR_D =
		new JSCTypeDef(
			"STR_D",
			JSCTypes.NARRAY,
			"Lag truncated deterministic data.");

	/**
	 * Selected endogenous data.
	 */
	public static final JSCTypeDef STR_endData =
		new JSCTypeDef(
			"STR_endData",
			JSCTypes.NARRAY,
			"Selected endogenous data.");
	/**
	* Selected exogenous data.
	*/
	public static final JSCTypeDef STR_exData =
		new JSCTypeDef(
			"STR_exData",
			JSCTypes.NARRAY,
			"Selected exogenous data.");
	/**
	 * Selected only deterministic data.
	 */
	public static final JSCTypeDef STR_detData_raw =
		new JSCTypeDef(
			"STR_detData_raw",
			JSCTypes.NARRAY,
			"Selected only deterministic data.");
	/**
	 * Selected and generated deterministic data.
	 */
	public static final JSCTypeDef STR_detData =
		new JSCTypeDef(
			"STR_detData",
			JSCTypes.NARRAY,
			"Selected and generated deterministic data.");
	/**
	 * Selected date range before lag truncation.
	 */
	public static final JSCTypeDef STR_drange =
		new JSCTypeDef(
			"STR_drange",
			JSCTypes.DRANGE,
			"Selected date range before lag truncation.");
	/**
	* Start date range after lag truncation.
	*/
	public static final JSCTypeDef STR_T1 =
		new JSCTypeDef(
			"STR_T1",
			JSCTypes.DRANGE,
			"Start date range after lag truncation.");
	/**
	 * Name of selected endogenous series.
	 */
	public static final JSCTypeDef STR_Ny =
		new JSCTypeDef(
			"STR_Ny",
			JSCTypes.SARRAY,
			"Name of selected endogenous series.");
	/**
	 * Names of selected exogenous series.
	 */
	public static final JSCTypeDef STR_Nx =
		new JSCTypeDef(
			"STR_Nx",
			JSCTypes.SARRAY,
			"Names of selected exogenous series.");
	/**
	 * Names of generated and selected deterministic series.
	 */
	public static final JSCTypeDef STR_Nd =
		new JSCTypeDef(
			"STR_Nd",
			JSCTypes.SARRAY,
			"Names of generated and selected deterministic series.");
	/**
	 * Names of selected deterministic series only.
	 */
	public static final JSCTypeDef STR_Nd_raw =
		new JSCTypeDef(
			"STR_Nd_raw",
			JSCTypes.SARRAY,
			"Names of selected deterministic series only.");
	/**
	 * Lags of endogenous variables.
	 */
	public static final JSCTypeDef STR_py =
		new JSCTypeDef("STR_py", JSCTypes.INT, "Lags of endogenous variables.");
	/**
	 * Lags of exogenous variables.
	 */
	public static final JSCTypeDef STR_pz =
		new JSCTypeDef("STR_pz", JSCTypes.INT, "Lags of exogenous variables.");
	/**
	 * Residuals~Transition Function~Z from estimation.
	 * Z is derivative with respect to g,c1 (,c2).
	 */
	public static final JSCTypeDef STR_RTZ =
		new JSCTypeDef(
			"STR_RTZ",
			JSCTypes.NARRAY,
			"Residuals~Transition Function~Z from estimation.\r\nZ is derivative with respect to g,c1 (,c2).");
	/**
	 * Restrictions for endogenous variables.
	 */
	public static final JSCTypeDef STR_Cy =
		new JSCTypeDef(
			"STR_Cy",
			JSCTypes.NARRAY,
			"Restrictions for endogenous variables.");
	/**
	 * Restrictions for exogenous variables.
	 */
	public static final JSCTypeDef STR_Cx =
		new JSCTypeDef(
			"STR_Cx",
			JSCTypes.NARRAY,
			"Restrictions for exogenous variables.");
	/**
	 * Merged restrictions for all variables.
	 */
	public static final JSCTypeDef STR_allRes =
		new JSCTypeDef(
			"STR_allRes",
			JSCTypes.NARRAY,
			"Merged restrictions for all variables.");
	/**
	 * Names of all possible transition variables.
	 * This is needed for testing nonlinearity.
	 */
	public static final JSCTypeDef STR_transNames =
		new JSCTypeDef(
			"STR_transNames",
			JSCTypes.SARRAY,
			"Names of all possible transition variables.\r\nThis is needed for testing nonlinearity.");
	/**
	 * Names of all possible transition variables without 
	 * the TREND. This is needed for diagnostic testing.
	 */
	public static final JSCTypeDef STR_transNames_noTrend =
		new JSCTypeDef(
			"STR_transNames_noTrend",
			JSCTypes.SARRAY,
			"Names of all possible transition variables without the TREND.\r\nThis is needed for diagnostic testing.");
	/**
	 * Names of all possible constant variables for which theta == 0.
	 */
	public static final JSCTypeDef STR_thet0Names =
		new JSCTypeDef(
			"STR_thet0Names",
			JSCTypes.SARRAY,
			"Names of all possible constant variables for which theta == 0.");
	/**
	 * Names of all possibly constant variables for which theta == 0
	 * excluding CONST.
	 */
	public static final JSCTypeDef STR_thet0Names_noConst =
		new JSCTypeDef(
			"STR_thet0Names_noConst",
			JSCTypes.SARRAY,
			"Names of all possibly constant variables for which theta == 0 excluding CONST.");

	/**
	 * STR_Constants not to be invoked.
	 */
	private STR_Constants() {
		super();
	}
}