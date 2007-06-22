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
 * This class defines all variables used for SVAR modeling that are stored in
 * the global symbol table and that might be reused by different components.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
public final class SVARConstants {
    /**
     * String to identify SVAR AB model.
     */
    public static final String AB_Model = "AB";

    /**
     * String to identify SVAR Blanchard-Quah model.
     */
    public static final String BQ_Model = "BLANQUAH";

    /**
     * String to identify SVAR B model.
     */
    public static final String B_Model = "B";

    /**
     * String to identify last SVAR model estimated.
     */
    public static final JSCTypeDef lastModel_Def = new JSCTypeDef(
            "SVAR_lastModel", JSCTypes.STRING,
            "String to identify last SVAR model estimated.");

    /**
     * SVAR estimated std. deviation of A matrix.
     */
    public static final JSCTypeDef svar_A_STD_Def = new JSCTypeDef(
            "SVAR_A_STD", JSCTypes.NARRAY,
            "SVAR estimated std. deviation of A matrix.");

    /**
     * SVAR estimated std. deviation of B matrix.
     */
    public static final JSCTypeDef svar_B_STD_Def = new JSCTypeDef(
            "SVAR_B_STD", JSCTypes.NARRAY,
            "SVAR estimated std. deviation of B matrix.");

    /**
     * SVAR estimated A0 matrix.
     */
    public static final JSCTypeDef svar_A0_Def = new JSCTypeDef("SVAR_A0",
            JSCTypes.NARRAY, "SVAR estimated A0 matrix.");

    /**
     * Relative Change in parameters stop condition.
     */
    public static final JSCTypeDef relParamCh_Def = new JSCTypeDef(
            "SVAR_relParamCh", JSCTypes.NUMBER,
            "Relative Change in parameters stop condition.");

    /**
     * Relative Change in likelihood stop condition.
     */
    public static final JSCTypeDef relLikCh_Def = new JSCTypeDef(
            "SVAR_relLikCh", JSCTypes.NUMBER,
            "Relative Change in likelihood stop condition.");

    /**
     * Maximum iterations until convergence is reached.
     */
    public static final JSCTypeDef maxIter_Def = new JSCTypeDef("SVAR_maxIter",
            JSCTypes.INT, "Maximum iterations until convergence is reached.");

    /**
     * Vector of normalizing constants.
     */
    public static final JSCTypeDef normConst_Def = new JSCTypeDef(
            "SVAR_normConst", JSCTypes.NARRAY,
            "Vector of normalizing constants.");

    /**
     * Restriction matrix for AB model.
     */
    public static final JSCTypeDef resMatrixAB_Def = new JSCTypeDef(
            "SVAR_resMatrixAB", JSCTypes.NARRAY,
            "Restriction matrix for AB model.");

    /**
     * Vector of start values for bootstrap, contains estimated values which
     * should make good start values for the bootstrap iterations.
     */
    public static final JSCTypeDef startValVector_Def = new JSCTypeDef(
            "SVAR_startValVector",
            JSCTypes.NARRAY,
            "Vector of start values for bootstrap, contains estimated values\n"
                    + "which should make good start values for the bootstrap iterations.");

    /**
     * SVAR AB model type.
     */
    public static final JSCTypeDef svarABModelType_Def = new JSCTypeDef(
            "SVAR_svarABModelType", JSCTypes.INT, "SVAR AB model type:\n"
                    + "1 = AB-model\n" + "2 = A-model\n" + "3 = B-model");

    /**
     * SVAR estimated A matrix in AB.
     */
    public static final JSCTypeDef aMatAB_Def = new JSCTypeDef("SVAR_aMatAB",
            JSCTypes.NARRAY, "SVAR estimated A matrix in AB.");

    /**
     * SVAR estimated B matrix in AB model.
     */
    public static final JSCTypeDef bMatAB_Def = new JSCTypeDef("SVAR_bMatAB",
            JSCTypes.NARRAY, "SVAR estimated B matrix in AB model.");

    /**
     * SVAR Blanchard-Quah identified long run effects.
     */
    public static final JSCTypeDef bqLongREffects_Def = new JSCTypeDef(
            "SVAR_bqLongREffects", JSCTypes.NARRAY,
            "SVAR Blanchard-Quah identified long run effects.");

    /**
     * SVAR Blanchard-Quah bootstrapped std. errors of contemporaneus impact
     * matrix.
     */
    public static final JSCTypeDef bqStdImpact_Def = new JSCTypeDef(
            "SVAR_bqStdImpact", JSCTypes.NARRAY,
            "SVAR Blanchard-Quah bootstrapped std. errors of contemporaneus impact matrix.");

    /**
     * SVAR Blanchard-Quah bootstrapped t-values of contemporaneus impact
     * matrix.
     */
    public static final JSCTypeDef bqTVImpact_Def = new JSCTypeDef(
            "SVAR_bqTVImpact", JSCTypes.NARRAY,
            "SVAR Blanchard-Quah bootstrapped t-values of contemporaneus impact matrix.");

    /**
     * SVAR Blanchard-Quah bootstrapped std. errors of long run impact matrix.
     */
    public static final JSCTypeDef bqStdLongREffects_Def = new JSCTypeDef(
            "SVAR_bqStdLongREffects", JSCTypes.NARRAY,
            "SVAR Blanchard-Quah bootstrapped std. errors of long run impact matrix.");

    /**
     * SVAR Blanchard-Quah bootstrapped t-values of long run impact matrix.
     */
    public static final JSCTypeDef bqTVLongREffects_Def = new JSCTypeDef(
            "SVAR_bqTVLongREffects", JSCTypes.NARRAY,
            "SVAR Blanchard-Quah bootstrapped t-values of long run impact matrix.");

    /**
     * Not to be invoked.
     *  
     */
    private SVARConstants() {
    }
}