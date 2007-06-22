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
 * This class defines all variables used for SVEC modeling that
 * are stored in the global symbol table and that might be reused
 * by different components.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public final class SVECConstants {
	/**
	 * User set restrictions for B.
	 */
	public static final JSCTypeDef resB_Def =
		new JSCTypeDef(
			"SVEC_resB",
			JSCTypes.NARRAY,
			"User set restrictions for B.");
	/**
	 * User set restrictions for C.
	 */
	public static final JSCTypeDef resC_Def =
		new JSCTypeDef(
			"SVEC_resC",
			JSCTypes.NARRAY,
			"User set restrictions for C.");

	/**
	 * Relative Change in parameters stop condition.
	 */
	public static final JSCTypeDef relParamCh_Def =
		new JSCTypeDef(
			"SVEC_relParamCh",
			JSCTypes.NUMBER,
			"Relative Change in parameters stop condition.");
	/**
	 * Relative Change in likelihood stop condition.
	 */
	public static final JSCTypeDef relLikCh_Def =
		new JSCTypeDef(
			"SVEC_relLikCh",
			JSCTypes.NUMBER,
			"Relative Change in likelihood stop condition.");
	/**
	 * Maximum iterations until convergence is reached.
	 */
	public static final JSCTypeDef maxIter_Def =
		new JSCTypeDef(
			"SVEC_maxIter",
			JSCTypes.INT,
			"Maximum iterations until convergence is reached.");
	/**
	 * Vector of normalizing constants.
	 */
	public static final JSCTypeDef normConst_Def =
		new JSCTypeDef(
			"SVEC_normConst",
			JSCTypes.NARRAY,
			"Vector of normalizing constants.");
	/**
	 * Vector of start values for bootstrap, contains estimated values
	 * which should make good start values for the bootstrap iterations.
	 */
	public static final JSCTypeDef startValVector_Def =
		new JSCTypeDef(
			"SVEC_startValVector",
			JSCTypes.NARRAY,
			"Vector of start values for bootstrap, contains estimated values\n"
				+ "which should make good start values for the bootstrap iterations.");
	/**
	 * SVEC estimated B matrix.
	 */
	public static final JSCTypeDef matB_Def =
		new JSCTypeDef(
			"SVEC_matB",
			JSCTypes.NARRAY,
			"SVEC estimated B matrix.");
	/**
	 * SVEC estimated XI_B matrix.
	 */
	public static final JSCTypeDef matXi_B_Def =
		new JSCTypeDef(
			"SVEC_matXI_B",
			JSCTypes.NARRAY,
			"SVEC estimated XI_B matrix.");
	/**
	 * SVEC restriction matrix.
	 */
	public static final JSCTypeDef resBC_Def =
		new JSCTypeDef(
			"SVEC_resBC",
			JSCTypes.NARRAY,
			"SVEC restriction matrix.");
	/**
	 * SVEC model type is always 3: C-model.
	 */
	public static final JSCTypeDef modelType_Def =
		new JSCTypeDef(
			"SVEC_modelType",
			JSCTypes.INT,
			"SVEC model type is always 3: C-model.");
	/**
	 * SVEC std. dev. of B.
	 */
	public static final JSCTypeDef std_B_Def =
		new JSCTypeDef("SVEC_std_B", JSCTypes.NARRAY, "SVEC std. dev. of B.");
	/**
	 * SVEC t-values of B.
	 */
	public static final JSCTypeDef tv_B_Def =
		new JSCTypeDef("SVEC_tv_B", JSCTypes.NARRAY, "SVEC t-values of B.");
	/**
	 * SVEC std. dev. of CB.
	 */
	public static final JSCTypeDef std_CB_Def =
		new JSCTypeDef("SVEC_std_CB", JSCTypes.NARRAY, "SVEC std. dev. of CB.");
	/**
	 * SVEC t-values of CB.
	 */
	public static final JSCTypeDef string_tv_CB =
		new JSCTypeDef("SVEC_tv_CB", JSCTypes.NARRAY, "SVEC t-values of CB.");

	/**
	 * Not to be invoked.
	 * 
	 */
	private SVECConstants() {
	}
}