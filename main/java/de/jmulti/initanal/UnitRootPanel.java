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

package de.jmulti.initanal;

import java.awt.CardLayout;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import com.jstatcom.model.JSCTypeDef;
import com.jstatcom.model.JSCTypes;
import com.jstatcom.ts.TSSel;

/**
 * Panel that binds unit root tests together with a TSSelector.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
final class UnitRootPanel extends JPanel {
    private static final Logger log = Logger.getLogger(UnitRootPanel.class);

    static final String NO_VAR_MSG = "Please select one endogenous series.";

    static final String NO_SEAS_MSG = "Seasonal dummies are not possible for annual data.";

    static final JSCTypeDef UR_ENDNAMES = new JSCTypeDef("UR_ENDNAMES",
            JSCTypes.SARRAY, "name of series for unit root tests");

    static final JSCTypeDef UR_ENDDATA = new JSCTypeDef("UR_ENDDATA",
            JSCTypes.NARRAY, "series for unit root tests");

    static final JSCTypeDef UR_DRANGE = new JSCTypeDef("UR_DRANGE",
            JSCTypes.DRANGE, "date range for unit root tests");

    private CardLayout card = null;

    private UnitRootTestContainer ivjUnitRootTestContainer = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private TSSel ivjSelectPanel = null;

    private JPanel ivjJPanel1 = null;

    private JComboBox ivjTestSelectCombo = null;

    class IvjEventHandler implements java.awt.event.ActionListener,
            java.beans.PropertyChangeListener {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == UnitRootPanel.this.getTestSelectCombo())
                connEtoC2();
        };

        public void propertyChange(java.beans.PropertyChangeEvent evt) {
            if (evt.getSource() == UnitRootPanel.this.getSelectPanel()
                    && (evt.getPropertyName().equals("selectionChanged")))
                connEtoM1(evt);
        };
    };

    /**
     * TestPanel constructor comment.
     */
    public UnitRootPanel() {
        super();
        initialize();
    }

    /**
     * connEtoC2: (TestSelectCombo.action. -->
     * URTestPanel.testSelectCombo_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC2() {
        try {
            // user code begin {1}
            // user code end
            this.testSelectCombo_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoM1: (SelectPanel.selectionChanged -->
     * UnitRootTestContainer.selectionChanged()V)
     * 
     * @param arg1
     *            java.beans.PropertyChangeEvent
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoM1(java.beans.PropertyChangeEvent arg1) {
        try {
            // user code begin {1}
            // user code end
            getUnitRootTestContainer().selectionChanged();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * Return the JPanel1 property value.
     * 
     * @return javax.swing.JPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JPanel getJPanel1() {
        if (ivjJPanel1 == null) {
            try {
                ivjJPanel1 = new javax.swing.JPanel();
                ivjJPanel1.setName("JPanel1");
                ivjJPanel1.setPreferredSize(new java.awt.Dimension(485, 50));
                ivjJPanel1.setLayout(new java.awt.GridBagLayout());
                ivjJPanel1.setMinimumSize(new java.awt.Dimension(485, 50));
                ivjJPanel1.setMaximumSize(new java.awt.Dimension(1000000, 50));

                java.awt.GridBagConstraints constraintsTestSelectCombo = new java.awt.GridBagConstraints();
                constraintsTestSelectCombo.gridx = 1;
                constraintsTestSelectCombo.gridy = 1;
                constraintsTestSelectCombo.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsTestSelectCombo.weightx = 1.0;
                constraintsTestSelectCombo.insets = new java.awt.Insets(0, 0,
                        5, 0);
                getJPanel1().add(getTestSelectCombo(),
                        constraintsTestSelectCombo);
                // user code begin {1}
                TitledBorder title = new TitledBorder(new BevelBorder(
                        BevelBorder.LOWERED), "Select Testing Procedure",
                        TitledBorder.RIGHT, TitledBorder.TOP);
                ivjJPanel1.setBorder(title);

                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJPanel1;
    }

    /**
     * Return the SelectPanel property value.
     * 
     * @return TSSelector
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private TSSel getSelectPanel() {
        if (ivjSelectPanel == null) {
            try {
                ivjSelectPanel = new TSSel();
                ivjSelectPanel.setName("SelectPanel");
                ivjSelectPanel.setDeterministicEnabled(false);
                ivjSelectPanel.setExogenousEnabled(false);
                // user code begin {1}
                ivjSelectPanel
                        .setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                ivjSelectPanel.setEndogenousStringsName(UR_ENDNAMES.name);
                ivjSelectPanel.setDateRangeName(UR_DRANGE.name);
                ivjSelectPanel.setEndogenousDataName(UR_ENDDATA.name);

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
     * Return the JComboBox1 property value.
     * 
     * @return javax.swing.JComboBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JComboBox getTestSelectCombo() {
        if (ivjTestSelectCombo == null) {
            try {
                ivjTestSelectCombo = new javax.swing.JComboBox();
                ivjTestSelectCombo.setName("TestSelectCombo");
                ivjTestSelectCombo.setToolTipText("Select Testing Procedure");
                ivjTestSelectCombo.setPreferredSize(new java.awt.Dimension(140,
                        27));
                // user code begin {1}
                ivjTestSelectCombo.addItem("ADF Test");
                ivjTestSelectCombo.addItem("KPSS Test");
                ivjTestSelectCombo.addItem("Schmidt Phillips Test");
                ivjTestSelectCombo.addItem("HEGY Test");
                ivjTestSelectCombo.addItem("UR with Structural Break");
                ivjTestSelectCombo.setSelectedIndex(0);

                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjTestSelectCombo;
    }

    /**
     * Return the UnitRootTestContainer property value.
     * 
     * @return program.frame.prelim.UnitRootTestContainer
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private UnitRootTestContainer getUnitRootTestContainer() {
        if (ivjUnitRootTestContainer == null) {
            try {
                ivjUnitRootTestContainer = new de.jmulti.initanal.UnitRootTestContainer();
                ivjUnitRootTestContainer.setName("UnitRootTestContainer");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjUnitRootTestContainer;
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
        getTestSelectCombo().addActionListener(ivjEventHandler);
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
            setName("TestPanel");
            setPreferredSize(new java.awt.Dimension(740, 420));
            setLayout(new java.awt.GridBagLayout());
            setDoubleBuffered(true);
            setSize(700, 400);
            setMinimumSize(new java.awt.Dimension(740, 420));

            java.awt.GridBagConstraints constraintsSelectPanel = new java.awt.GridBagConstraints();
            constraintsSelectPanel.gridx = 1;
            constraintsSelectPanel.gridy = 0;
            constraintsSelectPanel.gridheight = 2;
            constraintsSelectPanel.fill = java.awt.GridBagConstraints.BOTH;
            constraintsSelectPanel.weighty = 1.0;
            add(getSelectPanel(), constraintsSelectPanel);

            java.awt.GridBagConstraints constraintsUnitRootTestContainer = new java.awt.GridBagConstraints();
            constraintsUnitRootTestContainer.gridx = 0;
            constraintsUnitRootTestContainer.gridy = 1;
            constraintsUnitRootTestContainer.fill = java.awt.GridBagConstraints.BOTH;
            constraintsUnitRootTestContainer.weightx = 1.0;
            constraintsUnitRootTestContainer.weighty = 1.0;
            constraintsUnitRootTestContainer.ipadx = 485;
            constraintsUnitRootTestContainer.ipady = 350;
            add(getUnitRootTestContainer(), constraintsUnitRootTestContainer);

            java.awt.GridBagConstraints constraintsJPanel1 = new java.awt.GridBagConstraints();
            constraintsJPanel1.gridx = 0;
            constraintsJPanel1.gridy = 0;
            constraintsJPanel1.fill = java.awt.GridBagConstraints.BOTH;
            constraintsJPanel1.weightx = 1.0;
            add(getJPanel1(), constraintsJPanel1);
            initConnections();
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}
        // user code end
    }

    /**
     * Comment
     */
    private void testSelectCombo_ActionEvents() {
        if (card == null)
            card = (CardLayout) getUnitRootTestContainer().getLayout();
        if (getTestSelectCombo().getSelectedItem() == "ADF Test") {
            card.show(getUnitRootTestContainer(), "ADFPanel");
        }
        if (getTestSelectCombo().getSelectedItem() == "KPSS Test") {
            card.show(getUnitRootTestContainer(), "KPSSPanel");
        }
        if (getTestSelectCombo().getSelectedItem() == "Schmidt Phillips Test") {
            card.show(getUnitRootTestContainer(), "SPPanel");
        }
        if (getTestSelectCombo().getSelectedItem() == "HEGY Test") {
            card.show(getUnitRootTestContainer(), "HegyPanel");
        }
        if (getTestSelectCombo().getSelectedItem() == "UR with Structural Break") {
            card.show(getUnitRootTestContainer(), "StructBreakPanel");
        }
    }
}