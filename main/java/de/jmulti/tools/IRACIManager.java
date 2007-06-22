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

package de.jmulti.tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import com.jstatcom.model.JSCNArray;

/**
 * This class manages the various bootstrapped confidence intervals for the
 * impulse response analysis of different models. It stores the CIs for a
 * certain model in hash tables with a unique CI key. This class is designed to
 * be a Singleton and can be accessed via <code>getInstance</code>. Access to
 * the underlying data is synchronized.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
public final class IRACIManager {
    private final Map<String, JSCNArray[]> varFECI = Collections
            .synchronizedMap(new LinkedHashMap<String, JSCNArray[]>());

    private final Map<String, JSCNArray[]> varOrthCI = Collections
            .synchronizedMap(new LinkedHashMap<String, JSCNArray[]>());

    private final Map<String, JSCNArray[]> vecFECI = Collections
            .synchronizedMap(new LinkedHashMap<String, JSCNArray[]>());

    private final Map<String, JSCNArray[]> vecOrthCI = Collections
            .synchronizedMap(new LinkedHashMap<String, JSCNArray[]>());

    private final Map<String, JSCNArray[]> svarCI = Collections
            .synchronizedMap(new LinkedHashMap<String, JSCNArray[]>());

    private final Map<String, JSCNArray[]> svecCI = Collections
            .synchronizedMap(new LinkedHashMap<String, JSCNArray[]>());

    private static final IRACIManager instance = new IRACIManager();

    /**
     * Not to be invoked.
     */
    private IRACIManager() {
        super();
    }

    /**
     * Adds bootstrapped confidence intervals according to the model type.
     * 
     * @param name
     *            the identifier for the bootstrap CI
     * @param modelType
     *            the type of the model
     * @param ciData
     *            the data with FERR_LOWER|FERR_UPPER|ORTH_LOWER|ORTH_UPPER for
     *            VAR and VEC or LOWER|UPPER for SVAR
     * @throws IllegalArgumentException
     *             if <code>ciData</code> has invalid length or if
     *             <code>modelType</code> is unknown
     */
    public void addCI(String name, ModelTypes modelType, JSCNArray[] ciData) {

        if (modelType == ModelTypes.VAR) {

            if (ciData.length != 4)
                throw new IllegalArgumentException("CI data is invalid for "
                        + modelType + ".");

            varFECI.put(name, new JSCNArray[] { ciData[0], ciData[1] });
            varOrthCI.put(name, new JSCNArray[] { ciData[2], ciData[3] });
            return;
        }

        if (modelType == ModelTypes.VECM) {

            if (ciData.length != 4)
                throw new IllegalArgumentException("CI data is invalid for "
                        + modelType + ".");

            vecFECI.put(name, new JSCNArray[] { ciData[0], ciData[1] });
            vecOrthCI.put(name, new JSCNArray[] { ciData[2], ciData[3] });
            return;

        }

        if (modelType == ModelTypes.SVAR) {

            if (ciData.length != 2)
                throw new IllegalArgumentException("CI data is invalid for "
                        + modelType + ".");

            svarCI.put(name, new JSCNArray[] { ciData[0], ciData[1] });
            return;

        }

        if (modelType == ModelTypes.SVEC) {

            if (ciData.length != 2)
                throw new IllegalArgumentException("CI data is invalid for "
                        + modelType + ".");

            svecCI.put(name, new JSCNArray[] { ciData[0], ciData[1] });
            return;

        }

        throw new IllegalArgumentException("Model " + modelType + " not known.");
    }

    /**
     * Clears the stored CIs for <code>modelType</code>. This should be
     * called if the model is recomputed or the period was increased.
     * 
     * @param modelType
     *            the type of the model
     * @throws IllegalArgumentException
     *             of <code>modelType</code> is unknown
     */
    public void clear(ModelTypes modelType) {

        if (modelType == ModelTypes.VAR) {
            varFECI.clear();
            varOrthCI.clear();
            return;
        }

        if (modelType == ModelTypes.VECM) {
            vecFECI.clear();
            vecOrthCI.clear();
            return;
        }

        if (modelType == ModelTypes.SVAR) {
            svarCI.clear();
            return;
        }

        if (modelType == ModelTypes.SVEC) {
            svecCI.clear();
            return;
        }

        throw new IllegalArgumentException("Model " + modelType + " not known.");
    }

    /**
     * Gets a 2 x 1 (LOWER|UPPER) array with the data of the bootstrapped CIs.
     * 
     * @param name
     *            the key for the CI
     * @param modelType
     *            the type of the model
     * @param isOrth
     *            if <code>true</code> orthogonal CIs are selected instead of
     *            FER, ignore for SVAR models
     * @return data array with lowerbound|upperbound data
     * @throws IllegalArgumentException
     *             of <code>modelType</code> is unknown
     */
    public JSCNArray[] getCI(String name, ModelTypes modelType, boolean isOrth) {

        if (modelType == ModelTypes.VAR) {
            if (isOrth)
                return varOrthCI.get(name);
            return varFECI.get(name);

        }

        if (modelType == ModelTypes.VECM) {
            if (isOrth)
                return vecOrthCI.get(name);
            return vecFECI.get(name);
        }

        if (modelType == ModelTypes.SVAR) {
            return svarCI.get(name);
        }

        if (modelType == ModelTypes.SVEC) {
            return svecCI.get(name);
        }

        throw new IllegalArgumentException("Model " + modelType + " not known.");
    }

    /**
     * Gets all CI names that are stored for a certain model.
     * 
     * @param modelType
     *            the type of the model
     * @return string array with all keys
     * @throws IllegalArgumentException
     *             of <code>modelType</code> is unknown
     */
    public String[] getCIKeys(ModelTypes modelType) {
        if (modelType == ModelTypes.VAR) {
            ArrayList<String> ret = new ArrayList<String>();
            // Keys for orth and ferr are the same.
            ret.addAll(varFECI.keySet());
            String[] retArray = new String[ret.size()];
            for (int i = 0; i < retArray.length; i++)
                retArray[i] = ret.get(i).toString();
            return retArray;
        }
        if (modelType == ModelTypes.VECM) {
            ArrayList<String> ret = new ArrayList<String>();
            // Keys for orth and ferr are the same.
            ret.addAll(vecFECI.keySet());
            String[] retArray = new String[ret.size()];
            for (int i = 0; i < retArray.length; i++)
                retArray[i] = ret.get(i).toString();
            return retArray;

        }
        if (modelType == ModelTypes.SVAR) {
            ArrayList<String> ret = new ArrayList<String>();
            ret.addAll(svarCI.keySet());
            String[] retArray = new String[ret.size()];
            for (int i = 0; i < retArray.length; i++)
                retArray[i] = ret.get(i).toString();
            return retArray;
        }
        if (modelType == ModelTypes.SVEC) {
            ArrayList<String> ret = new ArrayList<String>();
            ret.addAll(svecCI.keySet());
            String[] retArray = new String[ret.size()];
            for (int i = 0; i < retArray.length; i++)
                retArray[i] = ret.get(i).toString();
            return retArray;
        }
        throw new IllegalArgumentException("Model " + modelType + " not known.");
    }

    /**
     * Gets the singleton instance of this class.
     * 
     * @return the unique instance
     */
    public final static IRACIManager getInstance() {
        return instance;
    }

    /**
     * A string representation of this.
     * 
     * @return descriptive string
     */
    public String toString() {
        return getClass() + "\nVAR ferr: " + varFECI + "\nVAR orth: "
                + varOrthCI + "\nVEC ferr: " + vecFECI + "\nVEC orth: "
                + vecOrthCI + "\nSVAR: " + svarCI + "\nSVEC: " + svecCI;
    }
}