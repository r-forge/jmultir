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
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import com.jstatcom.model.JSCTypeDef;
import com.jstatcom.model.JSCTypes;
import com.jstatcom.ts.TSSel;

/**
 * Panel that binds cointegration tests together with a <code>TSSelector</code>.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
final class CointPanel extends JPanel {
    private static final Logger log = Logger.getLogger(CointPanel.class);

    static final JSCTypeDef COINT_DRANGE = new JSCTypeDef("COINT_DRANGE",
            JSCTypes.DRANGE, "date range for cointegration tests");

    static final JSCTypeDef COINT_ENDNAMES = new JSCTypeDef("COINT_ENDNAMES",
            JSCTypes.SARRAY, "names of endogenous series for coint tests");

    static final JSCTypeDef COINT_ENDDATA = new JSCTypeDef("COINT_ENDDATA",
            JSCTypes.NARRAY, "endogenous series for coint tests");

    static final JSCTypeDef COINT_DET = new JSCTypeDef("COINT_DET",
            JSCTypes.NARRAY, "deterministic series for coint tests");

    static final JSCTypeDef COINT_DETNAMES = new JSCTypeDef("COINT_DETNAMES",
            JSCTypes.SARRAY, "names of deterministic series for coint tests");

    private CardLayout card = null;

    private TSSel ivjCointPanelSelect = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private CointTestContainer ivjCointTestContainer = null;

    private JPanel ivjJPanel1 = null;

    private JComboBox ivjTestSelectCombo = null;

    class IvjEventHandler implements java.awt.event.ActionListener {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == CointPanel.this.getTestSelectCombo())
                connEtoC2();
        };
    };

    /**
     * CointPanel constructor comment.
     */
    public CointPanel() {
        super();
        initialize();
    }

    /**
     * connEtoC2: (TestSelectCombo.action. -->
     * CointPanel.testSelectCombo_ActionEvents()V)
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
     * Return the CointPanelSelect property value.
     * 
     * @return TSSelector
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private TSSel getCointPanelSelect() {
        if (ivjCointPanelSelect == null) {
            try {
                ivjCointPanelSelect = new TSSel();
                ivjCointPanelSelect.setName("CointPanelSelect");
                ivjCointPanelSelect.setExogenousEnabled(false);
                // user code begin {1}
                ivjCointPanelSelect
                        .setEndogenousStringsName(COINT_ENDNAMES.name);
                ivjCointPanelSelect
                        .setDeterministicStringsName(COINT_DETNAMES.name);
                ivjCointPanelSelect.setDeterministicDataName(COINT_DET.name);
                ivjCointPanelSelect.setDateRangeName(COINT_DRANGE.name);
                ivjCointPanelSelect.setEndogenousDataName(COINT_ENDDATA.name);

                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjCointPanelSelect;
    }

    /**
     * Return the CointTestContainer property value.
     * 
     * @return program.frame.prelim.CointTestContainer
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private CointTestContainer getCointTestContainer() {
        if (ivjCointTestContainer == null) {
            try {
                ivjCointTestContainer = new de.jmulti.initanal.CointTestContainer();
                ivjCointTestContainer.setName("CointTestContainer");
                ivjCointTestContainer.setPreferredSize(new java.awt.Dimension(
                        485, 350));
                ivjCointTestContainer.setMinimumSize(new java.awt.Dimension(
                        485, 350));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjCointTestContainer;
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
                ivjTestSelectCombo.setPreferredSize(new java.awt.Dimension(130,
                        27));
                ivjTestSelectCombo.setMinimumSize(new java.awt.Dimension(126,
                        27));
                ivjTestSelectCombo.setMaximumSize(new java.awt.Dimension(32767,
                        27));
                // user code begin {1}
                ivjTestSelectCombo.addItem("Johansen Trace Test");
                ivjTestSelectCombo.addItem("Saikkonen & Lütkepohl Test");
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
    }

    /**
     * Initialize the class.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initialize() {
        try {
            // user code begin {1}
            // user code end
            setName("CointPanel");
            setPreferredSize(new java.awt.Dimension(700, 400));
            setLayout(new java.awt.GridBagLayout());
            setSize(700, 400);
            setMinimumSize(new java.awt.Dimension(700, 400));

            java.awt.GridBagConstraints constraintsCointPanelSelect = new java.awt.GridBagConstraints();
            constraintsCointPanelSelect.gridx = 1;
            constraintsCointPanelSelect.gridy = 0;
            constraintsCointPanelSelect.gridheight = 2;
            constraintsCointPanelSelect.fill = java.awt.GridBagConstraints.VERTICAL;
            constraintsCointPanelSelect.weighty = 1.0;
            add(getCointPanelSelect(), constraintsCointPanelSelect);

            java.awt.GridBagConstraints constraintsCointTestContainer = new java.awt.GridBagConstraints();
            constraintsCointTestContainer.gridx = 0;
            constraintsCointTestContainer.gridy = 1;
            constraintsCointTestContainer.fill = java.awt.GridBagConstraints.BOTH;
            constraintsCointTestContainer.weightx = 1.0;
            constraintsCointTestContainer.weighty = 1.0;
            add(getCointTestContainer(), constraintsCointTestContainer);

            java.awt.GridBagConstraints constraintsJPanel1 = new java.awt.GridBagConstraints();
            constraintsJPanel1.gridx = 0;
            constraintsJPanel1.gridy = 0;
            constraintsJPanel1.fill = java.awt.GridBagConstraints.HORIZONTAL;
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
            card = (CardLayout) getCointTestContainer().getLayout();
        if (getTestSelectCombo().getSelectedItem() == "Johansen Trace Test") {
            card.show(getCointTestContainer(), "JohansenPanel");
        }
        if (getTestSelectCombo().getSelectedItem() == "Saikkonen & Lütkepohl Test") {
            card.show(getCointTestContainer(), "SLCointPanel");
        }

    }
}