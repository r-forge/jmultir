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

package de.jmulti.var;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.apache.log4j.Logger;

import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.ModelPanel;
import com.jstatcom.ts.TSSel;


/**
 * This component binds a <code>TSSelector</code> together with a
 * <code>VARLagSpecPanel</code> to provide a VAR model selection.
 * 
 * @author A. Benkwitz, <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
public final class VARSpecifyModelPanel extends ModelPanel {
    private static final Logger log = Logger
            .getLogger(VARSpecifyModelPanel.class);

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private TSSel ivjSelectPanel = null;

    private VARLagSpecPanel ivjLagSpecPanel = null;

    class IvjEventHandler implements java.beans.PropertyChangeListener {
        public void propertyChange(java.beans.PropertyChangeEvent evt) {
            if (evt.getSource() == VARSpecifyModelPanel.this.getSelectPanel()
                    && (evt.getPropertyName().equals("selectionChanged")))
                connEtoM2(evt);
        };
    };

    /**
     * SpecifyModelPanel constructor comment.
     */
    public VARSpecifyModelPanel() {
        super();
        initialize();
    }

    /**
     * connEtoM2: (selectPanel.selectionChanged -->
     * SamplePeriodAndLagsPanel1.selectionChanged()V)
     * 
     * @param arg1
     *            java.beans.PropertyChangeEvent
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoM2(java.beans.PropertyChangeEvent arg1) {
        try {
            // user code begin {1}
            // user code end
            getLagSpecPanel().selectionChanged();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * Return the LagSpecPanel property value.
     * 
     * @return de.jmulti.var.VARLagSpecPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    VARLagSpecPanel getLagSpecPanel() {
        if (ivjLagSpecPanel == null) {
            try {
                ivjLagSpecPanel = new de.jmulti.var.VARLagSpecPanel();
                ivjLagSpecPanel.setName("LagSpecPanel");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjLagSpecPanel;
    }

    TSSel getSelectPanel() {
        if (ivjSelectPanel == null) {
            try {
                ivjSelectPanel = new TSSel();
                ivjSelectPanel.setName("SelectPanel");
                // user code begin {1}
                ivjSelectPanel
                        .setEndogenousStringsName(VARConstants.Ny_Def.name);
                ivjSelectPanel
                        .setExogenousDataName(VARConstants.x_raw_Def.name);
                ivjSelectPanel
                        .setDeterministicStringsName(VARConstants.Nd_raw_Def.name);
                ivjSelectPanel
                        .setDeterministicDataName(VARConstants.d_raw_Def.name);
                ivjSelectPanel.setDateRangeName(VARConstants.T1_raw_Def.name);
                ivjSelectPanel
                        .setEndogenousDataName(VARConstants.y_raw_Def.name);
                ivjSelectPanel
                        .setExogenousStringsName(VARConstants.Nx_Def.name);
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
            setAlignmentY(java.awt.Component.TOP_ALIGNMENT);
            setLayout(new java.awt.BorderLayout());
            setSize(665, 400);
            add(getSelectPanel(), "East");
            add(getLagSpecPanel(), "Center");
            initConnections();
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}
        getSelectPanel().addPropertyChangeListener(
                new PropertyChangeListener() {
                    public void propertyChange(PropertyChangeEvent evt) {

                        if (evt.getPropertyName().equalsIgnoreCase(
                                "selectionChanged")) {
                            VARDataManager.getInstance().removeData();

                            // CausalityTestsPanel
                            boolean __enabled = global().get(
                                    VARConstants.y_raw_Def).getJSCNArray()
                                    .cols() > 1;
                            VAR.getInstance().action_causalityTests
                                    .setEnabled(__enabled);

                        }
                    }
                });

        // user code end
    }

    public void shown(boolean isShown) {
        getLagSpecPanel().shown(isShown);
    }

    /**
     * Sets the selection of predefined deterministic variables.
     * 
     * @param detSel
     *            3x1 array indicating const-trend-seasdum (1:selected 0:not
     *            selected)
     */
    void setDetSelection(JSCNArray detSel) {
        getLagSpecPanel().setDetSelection(detSel);
    }

    /**
     * Gets the current selection of predefined deterministic variables.
     * 
     * @return 3x1 array indicating const-trend-seasdum (1:selected 0:not
     *         selected)
     */
    JSCNArray getDetSelection() {
        return getLagSpecPanel().getDetSelection();
    }
}