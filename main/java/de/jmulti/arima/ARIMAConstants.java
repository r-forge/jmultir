/*
 * This file is part of the econometric software package jmulti.
 * 
 * Creation date 07.02.2006, Copyright (C) 2006 Markus Kraetzig
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package de.jmulti.arima;

import com.jstatcom.model.JSCTypeDef;
import com.jstatcom.model.JSCTypes;

/**
 * Global symbol table constants for the ARIMA analysis.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
public final class ARIMAConstants {
    public final static JSCTypeDef EST_PARAMS = new JSCTypeDef(
            "EST_PARAMS",
            JSCTypes.NARRAY,
            "column vector of final estimates equal to the number of AR, MA, and Constant (or fixed regressor) terms");

    public final static JSCTypeDef COV_EST_PARAMS = new JSCTypeDef(
            "COV_EST_PARAMS", JSCTypes.NARRAY,
            "covariance matrix of all estimated parameters");

    public final static JSCTypeDef EST_RESIDS = new JSCTypeDef("EST_RESIDS",
            JSCTypes.NARRAY, "estimated residuals");

    public final static JSCTypeDef EST_RESIDS_NAME = new JSCTypeDef(
            "EST_RESIDS_NAME", JSCTypes.SARRAY, "name of estimated residuals");

    public final static JSCTypeDef P = new JSCTypeDef("P", JSCTypes.INT,
            "number of AR parameters to be estimated");

    public final static JSCTypeDef Q = new JSCTypeDef("Q", JSCTypes.INT,
            "number of MA parameters to be estimated");

    public final static JSCTypeDef D = new JSCTypeDef("D", JSCTypes.INT,
            "order of differencing");

    public final static JSCTypeDef START_VALS = new JSCTypeDef("START_VALS",
            JSCTypes.NARRAY, "starting values for ARIMA estimation");

    /**
     * date range for ARIMA analysis
     */
    public final static JSCTypeDef DRANGE = new JSCTypeDef("DRANGE",
            JSCTypes.DRANGE, "date range for ARIMA analysis");

    public final static JSCTypeDef DRANGE_ADJUSTED = new JSCTypeDef(
            "DRANGE_ADJUSTED", JSCTypes.DRANGE,
            "date range for ARIMA analysis after adjusting for lags");

    public final static JSCTypeDef DET_DATA_SEL = new JSCTypeDef("DET_DATA_SEL",
            JSCTypes.NARRAY, "selected fixed regressor data for ARIMA analysis");

    public final static JSCTypeDef DET_NAMES_SEL = new JSCTypeDef(
            "DET_NAMES_SEL", JSCTypes.SARRAY, "selected fixed regressor names");

    public final static JSCTypeDef DET_DATA_ALL = new JSCTypeDef("DET_DATA_ALL",
            JSCTypes.NARRAY, "fixed regressor data with const/trend/seasdum");

    public final static JSCTypeDef DET_NAMES_ALL = new JSCTypeDef(
            "DET_NAMES_ALL", JSCTypes.SARRAY,
            "fixed regressor names with const/trend/seasdum");

    public final static JSCTypeDef DET_SEL = new JSCTypeDef("DET_SEL",
            JSCTypes.NARRAY,
            "3x1 array of 0 and 1: is const| is seasonal dummies | is trend ");

    public final static JSCTypeDef END_DATA = new JSCTypeDef("END_DATA",
            JSCTypes.NARRAY, "data for ARIMA analysis");

    public final static JSCTypeDef END_NAME = new JSCTypeDef("END_NAME",
            JSCTypes.SARRAY, "endogenous data name for ARIMA analysis");

}
