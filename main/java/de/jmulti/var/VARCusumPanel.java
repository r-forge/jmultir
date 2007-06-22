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

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import org.apache.log4j.Logger;

import com.jstatcom.component.ResultField;
import com.jstatcom.component.StdMessages;
import com.jstatcom.engine.PCall;
import com.jstatcom.model.ModelPanel;

import de.jmulti.proc.VARCusumCall;

/**
 * Panel to execute CUSUM test for VAR models.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public final class VARCusumPanel extends ModelPanel {
    private static final Logger log = Logger.getLogger(VARCusumPanel.class);

    private JPanel ivjJPanel1 = null;

    private ResultField ivjResultField = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private JButton ivjExecute = null;

    private JComboBox ivjSignificanceCombo = null;

    private JLabel ivjJLabel = null;

    private JComboBox ivjMethodCombo = null;

    class IvjEventHandler implements java.awt.event.ActionListener {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == VARCusumPanel.this.getExecute())
                connEtoC1();
        };
    };

    /**
     * CusumPanel constructor comment.
     */
    public VARCusumPanel() {
        super();
        initialize();
    }

    /**
     * connEtoC1: (Execute.action. --> CusumPanel.execute_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC1() {
        try {
            // user code begin {1}
            // user code end
            this.execute_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * Execute CUSUM test.
     */
    private void execute_ActionEvents() {

        double sign = 0.95;
        String item = getSignificanceCombo().getSelectedItem().toString();
        if (item.equals("10%"))
            sign = 0.9;
        else if (item.equals("1%"))
            sign = 0.99;

        if (global().get(VARConstants.Ad_Def).isEmpty()
                && global().get(VARConstants.Ax_Def).isEmpty()
                && global().get(VARConstants.Ad_Def).isEmpty()) {
            StdMessages.errorSpecification("Not possible for empty model.");
            return;
        }

        PCall job = new VARCusumCall(global(), getMethodCombo()
                .getSelectedIndex(), sign);
        job.execute();

    }

    /**
     * Return the Execute property value.
     * 
     * @return javax.swing.JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JButton getExecute() {
        if (ivjExecute == null) {
            try {
                ivjExecute = new javax.swing.JButton();
                ivjExecute.setName("Execute");
                ivjExecute.setPreferredSize(new java.awt.Dimension(105, 25));
                ivjExecute.setText("Execute");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjExecute;
    }

    /**
     * Return the JLabel property value.
     * 
     * @return javax.swing.JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JLabel getJLabel() {
        if (ivjJLabel == null) {
            try {
                ivjJLabel = new javax.swing.JLabel();
                ivjJLabel.setName("JLabel");
                ivjJLabel.setText("Significance level of the test");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJLabel;
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
                ivjJPanel1.setPreferredSize(new java.awt.Dimension(0, 150));
                ivjJPanel1.setBorder(new BevelBorder(BevelBorder.LOWERED));
                ivjJPanel1.setLayout(new java.awt.GridBagLayout());

                java.awt.GridBagConstraints constraintsExecute = new java.awt.GridBagConstraints();
                constraintsExecute.gridx = 3;
                constraintsExecute.gridy = 2;
                constraintsExecute.gridwidth = 3;
                constraintsExecute.anchor = java.awt.GridBagConstraints.SOUTHEAST;
                constraintsExecute.weightx = 1.0;
                constraintsExecute.weighty = 1.0;
                constraintsExecute.insets = new java.awt.Insets(0, 10, 10, 10);
                getJPanel1().add(getExecute(), constraintsExecute);

                java.awt.GridBagConstraints constraintsSignificanceCombo = new java.awt.GridBagConstraints();
                constraintsSignificanceCombo.gridx = 0;
                constraintsSignificanceCombo.gridy = 0;
                constraintsSignificanceCombo.gridwidth = 2;
                constraintsSignificanceCombo.anchor = java.awt.GridBagConstraints.WEST;
                constraintsSignificanceCombo.insets = new java.awt.Insets(10,
                        10, 0, 0);
                getJPanel1().add(getSignificanceCombo(),
                        constraintsSignificanceCombo);

                java.awt.GridBagConstraints constraintsJLabel = new java.awt.GridBagConstraints();
                constraintsJLabel.gridx = 3;
                constraintsJLabel.gridy = 0;
                constraintsJLabel.anchor = java.awt.GridBagConstraints.WEST;
                constraintsJLabel.insets = new java.awt.Insets(10, 5, 0, 0);
                getJPanel1().add(getJLabel(), constraintsJLabel);

                java.awt.GridBagConstraints constraintsMethodCombo = new java.awt.GridBagConstraints();
                constraintsMethodCombo.gridx = 1;
                constraintsMethodCombo.gridy = 1;
                constraintsMethodCombo.gridwidth = 3;
                constraintsMethodCombo.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsMethodCombo.anchor = java.awt.GridBagConstraints.WEST;
                constraintsMethodCombo.insets = new java.awt.Insets(10, 10, 0,
                        0);
                getJPanel1().add(getMethodCombo(), constraintsMethodCombo);
                // user code begin {1}
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
     * Return the MethodCombo property value.
     * 
     * @return javax.swing.JComboBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JComboBox getMethodCombo() {
        if (ivjMethodCombo == null) {
            try {
                ivjMethodCombo = new javax.swing.JComboBox();
                ivjMethodCombo.setName("MethodCombo");
                ivjMethodCombo
                        .setPreferredSize(new java.awt.Dimension(100, 23));
                ivjMethodCombo.setMinimumSize(new java.awt.Dimension(100, 23));
                // user code begin {1}
                ivjMethodCombo.addItem("CUSUM of Squares");
                ivjMethodCombo.addItem("CUSUM Test");
                ivjMethodCombo.setSelectedIndex(1);
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjMethodCombo;
    }

    /**
     * Return the ResultField property value.
     * 
     * @return ResultField
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private com.jstatcom.component.ResultField getResultField() {
        if (ivjResultField == null) {
            try {
                ivjResultField = new com.jstatcom.component.ResultField();
                ivjResultField.setName("ResultField");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjResultField;
    }

    /**
     * Return the SignificanceCombo property value.
     * 
     * @return javax.swing.JComboBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JComboBox getSignificanceCombo() {
        if (ivjSignificanceCombo == null) {
            try {
                ivjSignificanceCombo = new javax.swing.JComboBox();
                ivjSignificanceCombo.setName("SignificanceCombo");
                ivjSignificanceCombo.setPreferredSize(new java.awt.Dimension(
                        100, 23));
                ivjSignificanceCombo.setMinimumSize(new java.awt.Dimension(100,
                        23));
                // user code begin {1}
                ivjSignificanceCombo.addItem("1%");
                ivjSignificanceCombo.addItem("5%");
                ivjSignificanceCombo.addItem("10%");
                ivjSignificanceCombo.setSelectedIndex(0);
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSignificanceCombo;
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
        getExecute().addActionListener(ivjEventHandler);
    }

    /**
     * Initialize the class.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initialize() {
        try {
            // user code begin {1}
            // user code end
            setName("CusumPanel");
            setLayout(new java.awt.BorderLayout());
            setSize(500, 400);
            add(getResultField(), "Center");
            add(getJPanel1(), "North");
            initConnections();
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}

        // user code end
    }
}