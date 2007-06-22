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

import org.apache.log4j.Logger;

import com.jstatcom.model.ModelPanel;
import com.jstatcom.ts.TSSel;


/**
 * This panel binds the specification components for a VEC model together.
 * 
 * @author A. Benkwitz, <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
public final class VECMSpecifyModelPanel extends ModelPanel {
    private static final Logger log = Logger
            .getLogger(VECMSpecifyModelPanel.class);

    private VECMLagSpecPanel ivjLagsVECMPanel = null;

    private TSSel ivjSelectPanel = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    class IvjEventHandler implements java.beans.PropertyChangeListener {
        public void propertyChange(java.beans.PropertyChangeEvent evt) {
            if (evt.getSource() == VECMSpecifyModelPanel.this.getSelectPanel()
                    && (evt.getPropertyName().equals("selectionChanged")))
                connEtoM1(evt);
        };
    };

    /**
     * SpecifyModelPanel constructor comment.
     */
    public VECMSpecifyModelPanel() {
        super();
        initialize();
    }

    /**
     * connEtoM1: (SelectPanel.selectionChanged -->
     * LagsVECMPanel.selectionChanged()V)
     * 
     * @param arg1
     *            java.beans.PropertyChangeEvent
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoM1(java.beans.PropertyChangeEvent arg1) {
        try {
            // user code begin {1}
            // user code end
            getLagsVECMPanel().selectionChanged();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * Return the LagsVECMPanel property value.
     * 
     * @return de.jmulti.vecm.VECMLagSpecPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    VECMLagSpecPanel getLagsVECMPanel() {
        if (ivjLagsVECMPanel == null) {
            try {
                ivjLagsVECMPanel = new de.jmulti.vecm.VECMLagSpecPanel();
                ivjLagsVECMPanel.setName("LagsVECMPanel");
                ivjLagsVECMPanel
                        .setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
                ivjLagsVECMPanel
                        .setAlignmentY(java.awt.Component.TOP_ALIGNMENT);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjLagsVECMPanel;
    }

    TSSel getSelectPanel() {
        if (ivjSelectPanel == null) {
            try {
                ivjSelectPanel = new TSSel();
                ivjSelectPanel.setName("SelectPanel");
                // user code begin {1}
                ivjSelectPanel
                        .setDeterministicDataName(VECMConstants.d_raw_Def.name);
                ivjSelectPanel
                        .setEndogenousDataName(VECMConstants.y_raw_Def.name);
                ivjSelectPanel
                        .setExogenousDataName(VECMConstants.x_raw_Def.name);
                ivjSelectPanel
                        .setEndogenousStringsName(VECMConstants.Ny_Def.name);
                ivjSelectPanel
                        .setDeterministicStringsName(VECMConstants.Nd_raw_Def.name);
                ivjSelectPanel
                        .setExogenousStringsName(VECMConstants.Nx_Def.name);
                ivjSelectPanel.setDateRangeName(VECMConstants.T1_raw_Def.name);
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSelectPanel;
    }

    /**
     * Called whenever the part throws an exception.
     * 
     * @param exception
     *            Exception
     */
    private void handleException(Throwable exception) {

        log.error("Unhandled Exception", exception);
    }

    /**
     * Initializes connections
     * 
     * @exception Exception
     *                The exception description.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initConnections() throws Exception {
        // user code begin {1}
        // user code end
        getSelectPanel().addPropertyChangeListener(ivjEventHandler);
    }

    /**
     * Initialize the class.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initialize() {
        try {
            // user code begin {1}
            // user code end
            setName("SpecifyModelPanel");
            setLayout(new java.awt.BorderLayout());
            setSize(699, 375);
            add(getSelectPanel(), "East");
            add(getLagsVECMPanel(), "Center");
            initConnections();
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}

        // user code end
    }

    public void shown(boolean isShown) {
        getLagsVECMPanel().shown(isShown);
    }
}