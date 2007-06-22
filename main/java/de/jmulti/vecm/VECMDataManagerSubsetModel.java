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

package de.jmulti.vecm;

import static de.jmulti.vecm.VECMConstants.*;

import com.jstatcom.model.JSCInt;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.SymbolEvent;
import com.jstatcom.model.SymbolListener;
import com.jstatcom.model.SymbolTable;
import com.jstatcom.util.UMatrix;

/**
 * Singleton data manager for VECM subset restrictions that can be accessed via
 * <code>getInstance</code>. It listens to changes in <code>
 * <ul>
 * <li>VECMConstants.string_Ndy
 * <li>VECMConstants.string_pdy
 * <li>VECMConstants.string_cointRank
 * <li>VECMConstants.string_Nx
 * <li>VECMConstants.string_px
 * <li>VECMConstants.string_Nd_vec
 * </ul>
 * </code> It takes care of adjusting the restriction matrizes for the subset
 * restrictions on coefficients.
 * 
 * @author A. Benkwitz, <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
final class VECMDataManagerSubsetModel implements SymbolListener {
    private static VECMDataManagerSubsetModel instance = null;

    private final SymbolTable sTable;

    /**
     * FirstStageDataManager constructor comment.
     */
    private VECMDataManagerSubsetModel(SymbolTable table) {
        super();
        if (table == null)
            throw new IllegalArgumentException("SymbolTable was null.");

        sTable = table;
        initialize();
    }

    /**
     * Gets the Singleton instance of this data manager
     * 
     * @return the unique instance of this class
     */
    public final static VECMDataManagerSubsetModel getInstance() {
        if (instance == null)
            instance = new VECMDataManagerSubsetModel(VECM.getInstance()
                    .getSymbolTable());
        return instance;
    }

    /**
     * Insert the method's description here.
     */
    private void initialize() {

        // Install model listeners.
        sTable.get(Ny_Def).addSymbolListener(this);
        sTable.get(pdy_Def).addSymbolListener(this);
        sTable.get(cointRank_Def).addSymbolListener(this);
        sTable.get(Nx_Def).addSymbolListener(this);
        sTable.get(px_Def).addSymbolListener(this);
        sTable.get(Nd_vec_Def).addSymbolListener(this);

        // Set initial values.
        resetSubsetRestrictionsDeterministics();
        resetSubsetRestrictionsEndogenous();
        resetSubsetRestrictionsExogenous();
        resetSubsetRestrictionsErrorCorrection();
    }

    /**
     * Insert the method's description here. Creation date: (14.12.01 13:27:14)
     */
    public void resetAll() {
        resetSubsetRestrictionsDeterministics();
        resetSubsetRestrictionsEndogenous();
        resetSubsetRestrictionsErrorCorrection();
        resetSubsetRestrictionsExogenous();
    }

    /**
     * Insert the method's description here.
     */
    private void resetSubsetRestrictionsDeterministics() {
        //
        int c = sTable.get(Nd_vec_Def).getJSCSArray().rows();
        int r = sTable.get(Ny_Def).getJSCSArray().rows();
        //
        sTable.get(S_C_VEC_Def).setJSCData(
                new JSCNArray("S_C_VEC", UMatrix.ones(r, c)));
    }

    /**
     * Insert the method's description here.
     */
    private void resetSubsetRestrictionsEndogenous() {
        //
        int k = sTable.get(Ny_Def).getJSCSArray().rows();
        int p = 0;
        JSCInt pdy = sTable.get(pdy_Def).getJSCInt();
        if (!pdy.isEmpty())
            p = pdy.intVal();

        sTable.get(S_G_Def).setJSCData(
                new JSCNArray("S_G", UMatrix.ones(k, k * p)));
    }

    /**
     * Insert the method's description here.
     */
    private void resetSubsetRestrictionsErrorCorrection() {
        //
        int k = sTable.get(Ny_Def).getJSCSArray().rows();

        int r = 1;
        JSCInt rr = sTable.get(cointRank_Def).getJSCInt();
        if (!rr.isEmpty())
            r = rr.intVal();

        //
        sTable.get(S_alpha_Def).setJSCData(
                new JSCNArray("S_alpha", UMatrix.ones(k, r)));
    }

    /**
     * Insert the method's description here.
     */
    private void resetSubsetRestrictionsExogenous() {
        //
        int k = sTable.get(Ny_Def).getJSCSArray().rows();
        int r = sTable.get(Nx_Def).getJSCSArray().rows();

        int p = 0;
        JSCInt px = sTable.get(px_Def).getJSCInt();
        if (!px.isEmpty())
            p = px.intVal();

        //
        sTable.get(S_B_Def).setJSCData(
                new JSCNArray("S_B", UMatrix.ones(k, r * (p + 1))));
    }

    public void valueChanged(SymbolEvent evt) {

        String symbolName = evt.getSource().name;

        if (symbolName.equalsIgnoreCase(Ny_Def.name)
                || symbolName.equalsIgnoreCase(pdy_Def.name)) {
            resetSubsetRestrictionsEndogenous();
        }

        if (symbolName.equalsIgnoreCase(Ny_Def.name)
                || symbolName.equalsIgnoreCase(cointRank_Def.name)) {
            resetSubsetRestrictionsErrorCorrection();
        }

        if (symbolName.equalsIgnoreCase(Nx_Def.name)
                || symbolName.equalsIgnoreCase(px_Def.name)) {
            resetSubsetRestrictionsExogenous();
        }
        if (symbolName.equalsIgnoreCase(Nd_vec_Def.name)
                || symbolName.equalsIgnoreCase(Ny_Def.name)) {
            resetSubsetRestrictionsDeterministics();
        }

    }
}