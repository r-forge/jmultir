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

package de.jmulti.cafpe;

import com.jstatcom.model.JSCTypeDef;
import com.jstatcom.model.JSCTypes;

/**
 * Class that holds all variables that are used in CAFPE analysis.
 *
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public final class CAFPE_constants {
	// the file names for estimation output
	public static final String OUT_FILE_SEL = "cafpe_sel.pro";
	public static final String OUT_FILE_VOLATSEL = "cafpe_volatsel.pro";
	public static final String OUT_FILE_EST = "cafpe_est.pro";
	public static final String OUT_FILE_VOLATEST = "cafpe_volatest.pro";
	public static final String OUT_FILE_FORECAST = "cafpe_forecast.pro";

	public static final JSCTypeDef Y_NAME =
		new JSCTypeDef(
			"CAFPE_name",
			JSCTypes.SARRAY,
			"name of selected series");
	public static final JSCTypeDef DRANGE =
		new JSCTypeDef("CAFPE_drange", JSCTypes.DRANGE, "selected date range");
	public static final JSCTypeDef T1 =
		new JSCTypeDef(
			"CAFPE_T1",
			JSCTypes.DRANGE,
			"date range after lag truncation for model estimation");
	public static final JSCTypeDef T1_volat =
		new JSCTypeDef(
			"CAFPE_T1_volat",
			JSCTypes.DRANGE,
			"date range after lag truncation for volatility estimation");
	public static final JSCTypeDef Y =
		new JSCTypeDef("CAFPE_data", JSCTypes.NARRAY, "selected data");
	public static final JSCTypeDef MOD_METH =
		new JSCTypeDef(
			"mod_meth",
			JSCTypes.STRING,
			"selected model: NAR SNAR etc.");
	public static final JSCTypeDef SEARCH_METH =
		new JSCTypeDef(
			"search_meth",
			JSCTypes.STRING,
			"selected search method: full or directed");
	public static final JSCTypeDef SELCRIT =
		new JSCTypeDef(
			"selcrit",
			JSCTypes.STRING,
			"selected criterium: lqafpe araic etc.");
	public static final JSCTypeDef STARTSTRAT =
		new JSCTypeDef(
			"startstrat",
			JSCTypes.STRING,
			"selected start strategy: same or different");
	public static final JSCTypeDef IS_STAND =
		new JSCTypeDef(
			"is_stand",
			JSCTypes.INT,
			"whether data is standardized for estimation: 0 or 1");
	public static final JSCTypeDef OPT_LAGS =
		new JSCTypeDef(
			"opt_lags",
			JSCTypes.NARRAY,
			"selected optimal lags for mean estimation");
	public static final JSCTypeDef RESID_EST =
		new JSCTypeDef(
			"resids_est",
			JSCTypes.NARRAY,
			"residuals from mean estimation");
	public static final JSCTypeDef GRID_VALUES =
		new JSCTypeDef(
			"grid_values",
			JSCTypes.NARRAY,
			"grid from mean estimation: x~estimated function");
	public static final JSCTypeDef MAX_LAG =
		new JSCTypeDef(
			"max_lag",
			JSCTypes.INT,
			"maximum lag for mean model selection");
	public static final JSCTypeDef D_MAX =
		new JSCTypeDef(
			"d_max",
			JSCTypes.INT,
			"maximum number of variables for mean model selection");
	public static final JSCTypeDef XCONST_FAC =
		new JSCTypeDef(
			"xconstfac",
			JSCTypes.NARRAY,
			"vector of constant values for conditional plotting");
	public static final JSCTypeDef XCONST_ALL =
		new JSCTypeDef(
			"xconst_all",
			JSCTypes.NARRAY,
			"vector of constant values for function evaluation at a point");
	public static final JSCTypeDef NAME_RESIDS =
		new JSCTypeDef(
			"NAME_RESIDS",
			JSCTypes.SARRAY,
			"name of residual series");
	public static final JSCTypeDef LAG_MAX =
		new JSCTypeDef(
			"lagmax",
			JSCTypes.INT,
			"maximum lag that can be used for estimation");
	public static final JSCTypeDef XSADJ =
		new JSCTypeDef(
			"X_s_j_adj",
			JSCTypes.NARRAY,
			"matrix of explanatory adjusted for seasonal components (SHNAR model)");
	public static final JSCTypeDef CRMIN =
		new JSCTypeDef(
			"crmin",
			JSCTypes.NARRAY,
			"array that stores for all considered lag combinations for the mean model:\r\n"
				+ "in the first d_max cols the selected lag vector,\r\n"
				+ "in the d_max+1 cols the estimated criterion,\r\n"
				+ "in the d_max+2 cols the computed A_hat if (C)AFPE is used,\r\n"
				+ "in the d_max+3 cols the estimated variance of the white noise process");
	public static final JSCTypeDef MEAN_Y =
		new JSCTypeDef("mean_y", JSCTypes.NUMBER, "mean of selected data");
	public static final JSCTypeDef G_HAT_XCONSTALL =
		new JSCTypeDef(
			"g_hat_xconstall",
			JSCTypes.NARRAY,
			"evaluated function");
	public static final JSCTypeDef CI_G_HAT_XCONSTALL =
		new JSCTypeDef(
			"ci_g_hat_xconstall",
			JSCTypes.NARRAY,
			"confidence interval for evaluated function at a single point");
	public static final JSCTypeDef CI_G_HATPLOT =
		new JSCTypeDef(
			"ci_g_hatplot",
			JSCTypes.NARRAY,
			"confidence interval for evaluated function");

	// all the variables for the volatility analysis
	public static final JSCTypeDef NAME_RESIDS_VOLAT =
		new JSCTypeDef(
			"NAME_RESIDS_VOLAT",
			JSCTypes.SARRAY,
			"name of residuals from volatility estimation");
	public static final JSCTypeDef RESID_EST_VOLAT =
		new JSCTypeDef(
			"resids_est_volat",
			JSCTypes.NARRAY,
			"residuals from volatility estimation");
	public static final JSCTypeDef LAG_MAX_VOLAT =
		new JSCTypeDef(
			"lagmax_volat",
			JSCTypes.INT,
			"maximum lag that can be used for volatility estimation");
	public static final JSCTypeDef OPT_LAGS_VOLAT =
		new JSCTypeDef(
			"opt_lags_volat",
			JSCTypes.NARRAY,
			"selected optimal lags for volatility estimation");
	public static final JSCTypeDef GRID_VALUES_VOLAT =
		new JSCTypeDef(
			"grid_values_volat",
			JSCTypes.NARRAY,
			"grid from volatility estimation: x~estimated function");
	public static final JSCTypeDef MOD_METH_VOLAT =
		new JSCTypeDef(
			"mod_meth_volat",
			JSCTypes.STRING,
			"selected model for volatility: NAR SNAR etc.");
	public static final JSCTypeDef SEARCH_METH_VOLAT =
		new JSCTypeDef(
			"search_meth_volat",
			JSCTypes.STRING,
			"selected search method for volatility: full or directed");
	public static final JSCTypeDef SELCRIT_VOLAT =
		new JSCTypeDef(
			"selcrit_volat",
			JSCTypes.STRING,
			"selected criterium for volatility: lqafpe araic etc.");
	public static final JSCTypeDef STARTSTRAT_VOLAT =
		new JSCTypeDef(
			"startstrat_volat",
			JSCTypes.STRING,
			"selected start strategy for volatility: same or different");
	public static final JSCTypeDef IS_STAND_VOLAT =
		new JSCTypeDef(
			"is_stand_volat",
			JSCTypes.INT,
			"whether data is standardized for volatility estimation: 0 or 1");
	public static final JSCTypeDef CRMIN_VOLAT =
		new JSCTypeDef(
			"crmin_volat",
			JSCTypes.NARRAY,
			"array that stores for all considered lag combinations for the volatility model:\r\n"
				+ "in the first d_max cols the selected lag vector,\r\n"
				+ "in the d_max+1 cols the estimated criterion,\r\n"
				+ "in the d_max+2 cols the computed A_hat if (C)AFPE is used,\r\n"
				+ "in the d_max+3 cols the estimated variance of the white noise process");
	public static final JSCTypeDef XSADJ_VOLAT =
		new JSCTypeDef(
			"X_s_j_adj_volat",
			JSCTypes.NARRAY,
			"matrix of explanatory adjusted for seasonal components (SHNAR model)");
	public static final JSCTypeDef MAX_LAG_VOLAT =
		new JSCTypeDef(
			"max_lag_volat",
			JSCTypes.INT,
			"maximum lag for volatility model selection");
	public static final JSCTypeDef D_MAX_VOLAT =
		new JSCTypeDef(
			"d_max_volat",
			JSCTypes.INT,
			"maximum number of variables for volatility model selection");
	public static final JSCTypeDef XCONST_FAC_VOLAT =
		new JSCTypeDef(
			"xconstfac_volat",
			JSCTypes.NARRAY,
			"vector of constant values for conditional plotting");
	public static final JSCTypeDef G_HAT_XCONSTALL_VOLAT =
		new JSCTypeDef(
			"g_hat_xconstall_volat",
			JSCTypes.NUMBER,
			"evaluated volatility function");
	public static final JSCTypeDef CI_G_HAT_XCONSTALL_VOLAT =
		new JSCTypeDef(
			"ci_g_hat_xconstall_volat",
			JSCTypes.NARRAY,
			"confidence interval for evaluated volatility function at a single point");
	public static final JSCTypeDef CI_G_HATPLOT_VOLAT =
		new JSCTypeDef(
			"ci_g_hatplot_volat",
			JSCTypes.NARRAY,
			"confidence interval for evaluated volatility function");

	/**
	 * Constructor should not be invoked.
	 */
	private CAFPE_constants() {
	}
}