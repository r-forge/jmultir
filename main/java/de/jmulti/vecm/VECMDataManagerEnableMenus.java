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

import com.jstatcom.model.SymbolEvent;
import com.jstatcom.model.SymbolListener;
import com.jstatcom.model.SymbolTable;


/**
 * Singleton data manager that can be accessed via <code>getInstance</code>.
 * It takes care of enabling the menus in the VECM frame according to the state
 * of the model.
 * 
 * @author A. Benkwitz, <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
final class VECMDataManagerEnableMenus implements SymbolListener {

    private static VECMDataManagerEnableMenus instance = null;

    private final SymbolTable sTable;

    private final VECM vecm = VECM.getInstance();

    /**
     * DataManagerEnableMenus constructor comment.
     */
    private VECMDataManagerEnableMenus(SymbolTable table) {
        super();
        if (table == null)
            throw new IllegalArgumentException("SymbolTable was null.");

        sTable = table;
        installSymbolListeners();
    }

    /**
     * Gets the Singleton instance of this data manager
     * 
     * @return the unique instance of this class
     */
    public final static VECMDataManagerEnableMenus getInstance() {
        if (instance == null)
            instance = new VECMDataManagerEnableMenus(VECM.getInstance()
                    .getSymbolTable());
        return instance;
    }

    /**
     * 
     */
    private void installSymbolListeners() {
        sTable.get(Ny_Def).addSymbolListener(this);
        sTable.get(SVECConstants.matB_Def).addSymbolListener(this);
        sTable.get(cv_u_hat_Def).addSymbolListener(this);
        sTable.get(S_G0_Def).addSymbolListener(this);

    }

    /**
     * Notifies listeners that symbol changed and sets menus enabled/disabled
     * accordingly. It listens to changes in <code>Ny </code>,
     * <code>cv_u_hat</code> and <code>SVECConstants.string_matB</code>.
     */
    public void valueChanged(SymbolEvent e) {

        String name = e.getSource().name;

        if (name.equalsIgnoreCase(Ny_Def.name)) {

            int k = e.getSource().getJSCSArray().rows();
            vecm.action_estimatedCoefficients.setEnabled(k > 1);
            vecm.action_causalityTests.setEnabled(k > 1);
            vecm.action_subsetRestrictionsSecondStage.setEnabled(k > 1);
            vecm.action_waldTestBeta.setEnabled(k > 1);

        } else if (name.equalsIgnoreCase(cv_u_hat_Def.name)) {
            boolean cv_initialized = !e.getSource().isEmpty();

            vecm.action_impulseResponseAnalysis.setEnabled(cv_initialized);
            vecm.action_forecastErrorVarianceDecomposition
                    .setEnabled(cv_initialized);
            vecm.action_svec.setEnabled(cv_initialized);

            vecm.action_impulseResponseAnalysis.setEnabled(cv_initialized);
            vecm.action_forecastErrorVarianceDecomposition
                    .setEnabled(cv_initialized);
            vecm.action_Forecasting.setEnabled(cv_initialized);
            vecm.action_stabAnal.setEnabled(cv_initialized);
            vecm.action_residualAnalysis.setEnabled(cv_initialized);
            vecm.action_ecPlot.setEnabled(cv_initialized);
            vecm.action_multArchAnal.setEnabled(cv_initialized);

        } else if (name.equalsIgnoreCase(SVECConstants.matB_Def.name)) {
            boolean b_initialized = !e.getSource().isEmpty();
            vecm.action_svecFEVD.setEnabled(b_initialized);
            vecm.action_svecIRA.setEnabled(b_initialized);

        } else if (name.equalsIgnoreCase(S_G0_Def.name)) {
            boolean cv_initialized = !sTable.get(cv_u_hat_Def).isEmpty();
            boolean g0_initialized = !e.getSource().isEmpty();
            vecm.action_svec.setEnabled(cv_initialized && !g0_initialized);

        }

    }
}