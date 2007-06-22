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

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import com.jstatcom.component.NumSelector;
import com.jstatcom.component.ResultField;
import com.jstatcom.component.StdMessages;
import com.jstatcom.engine.PCall;
import com.jstatcom.model.ModelPanel;
import com.jstatcom.ts.TSDateRange;

import de.jmulti.proc.SPTestCall;

/**
 * Panel to perform the Schmidt-Phillips unit root test.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
final class SPPanel extends ModelPanel {
    private static final Logger log = Logger.getLogger(SPPanel.class);

    private JLabel ivjJLabel2 = null;

    private NumSelector ivjLagLength = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private ResultField ivjResultField = null;

    private JRadioButton ivjTauStatistic = null;

    private JRadioButton ivjRhoStatistic = null;

    private JButton ivjExecute = null;

    class IvjEventHandler implements java.awt.event.ActionListener {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == SPPanel.this.getExecute())
                connEtoC1();
        };
    };

    /**
     * ADFPanel constructor comment.
     */
    public SPPanel() {
        super();
        initialize();
    }

    /**
     * connEtoC1: (Execute.action. --> SPPanel.execute_ActionEvents()V)
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
     * Makes test call.
     */
    private void execute_ActionEvents() {
        if (global().get(UnitRootPanel.UR_ENDDATA).isEmpty()) {
            StdMessages.infoNothingSelected(UnitRootPanel.NO_VAR_MSG);
            return;
        }
        int lagLength = getLagLength().getIntNumber();

        int testState = 1; // Z(rho) selected
        if (getTauStatistic().isSelected())
            testState = 2;

        TSDateRange range = global().get(UnitRootPanel.UR_DRANGE)
                .getJSCDRange().getTSDateRange();

        // Call KPSS test.
        PCall job = new SPTestCall(global().get(UnitRootPanel.UR_ENDDATA)
                .getJSCNArray(), global().get(UnitRootPanel.UR_ENDNAMES)
                .getJSCString(), range, lagLength, testState);
        job.setOutHolder(getResultField());
        job.setSymbolTable(local());
        job.execute();
    }

    /**
     * Return the JButton1 property value.
     * 
     * @return javax.swing.JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JButton getExecute() {
        if (ivjExecute == null) {
            try {
                ivjExecute = new javax.swing.JButton();
                ivjExecute.setName("Execute");
                ivjExecute.setText("Execute Test");
                ivjExecute.setMaximumSize(new java.awt.Dimension(135, 27));
                ivjExecute.setPreferredSize(new java.awt.Dimension(135, 27));
                ivjExecute.setMinimumSize(new java.awt.Dimension(135, 27));
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
     * Return the JLabel2 property value.
     * 
     * @return javax.swing.JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JLabel getJLabel2() {
        if (ivjJLabel2 == null) {
            try {
                ivjJLabel2 = new javax.swing.JLabel();
                ivjJLabel2.setName("JLabel2");
                ivjJLabel2.setPreferredSize(new java.awt.Dimension(85, 20));
                ivjJLabel2.setText("Number of lags");
                ivjJLabel2.setMinimumSize(new java.awt.Dimension(85, 20));
                ivjJLabel2.setMaximumSize(new java.awt.Dimension(85, 20));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJLabel2;
    }

    /**
     * Return the LagLength property value.
     * 
     * @return com.jstatcom.model.NumberSelector
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private NumSelector getLagLength() {
        if (ivjLagLength == null) {
            try {
                ivjLagLength = new NumSelector();
                ivjLagLength.setName("LagLength");
                ivjLagLength.setPreferredSize(new java.awt.Dimension(25, 20));
                ivjLagLength.setNumber(2.0);
                ivjLagLength.setMinimumSize(new java.awt.Dimension(25, 20));
                ivjLagLength.setRangeExpr("[0,100]");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjLagLength;
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
                ivjResultField
                        .setPreferredSize(new java.awt.Dimension(300, 350));
                ivjResultField.setMinimumSize(new java.awt.Dimension(300, 350));
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
     * Return the RhoStatisic property value.
     * 
     * @return javax.swing.JRadioButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JRadioButton getRhoStatistic() {
        if (ivjRhoStatistic == null) {
            try {
                ivjRhoStatistic = new javax.swing.JRadioButton();
                ivjRhoStatistic.setName("RhoStatistic");
                ivjRhoStatistic.setText("Z(Rho) statistic");
                ivjRhoStatistic.setMaximumSize(new java.awt.Dimension(120, 25));
                ivjRhoStatistic.setSelected(true);
                ivjRhoStatistic
                        .setPreferredSize(new java.awt.Dimension(120, 25));
                ivjRhoStatistic.setMinimumSize(new java.awt.Dimension(120, 25));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjRhoStatistic;
    }

    /**
     * Return the TauStatistic property value.
     * 
     * @return javax.swing.JRadioButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JRadioButton getTauStatistic() {
        if (ivjTauStatistic == null) {
            try {
                ivjTauStatistic = new javax.swing.JRadioButton();
                ivjTauStatistic.setName("TauStatistic");
                ivjTauStatistic
                        .setPreferredSize(new java.awt.Dimension(120, 25));
                ivjTauStatistic.setText("Z(Tau) statistic");
                ivjTauStatistic.setMinimumSize(new java.awt.Dimension(120, 25));
                ivjTauStatistic.setMaximumSize(new java.awt.Dimension(120, 25));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjTauStatistic;
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
            TitledBorder title = new TitledBorder(new BevelBorder(
                    BevelBorder.LOWERED), "Schmidt Phillips Test",
                    TitledBorder.RIGHT, TitledBorder.TOP);
            this.setBorder(title);
            ButtonGroup selectGroup = new javax.swing.ButtonGroup();
            selectGroup.add(getRhoStatistic());
            selectGroup.add(getTauStatistic());

            // user code end
            setName("SPPanel");
            setPreferredSize(new java.awt.Dimension(485, 350));
            setLayout(new java.awt.GridBagLayout());
            setSize(485, 350);
            setMinimumSize(new java.awt.Dimension(485, 350));

            java.awt.GridBagConstraints constraintsLagLength = new java.awt.GridBagConstraints();
            constraintsLagLength.gridx = 1;
            constraintsLagLength.gridy = 2;
            constraintsLagLength.anchor = java.awt.GridBagConstraints.WEST;
            constraintsLagLength.insets = new java.awt.Insets(10, 0, 0, 5);
            add(getLagLength(), constraintsLagLength);

            java.awt.GridBagConstraints constraintsJLabel2 = new java.awt.GridBagConstraints();
            constraintsJLabel2.gridx = 2;
            constraintsJLabel2.gridy = 2;
            constraintsJLabel2.gridwidth = 2;
            constraintsJLabel2.fill = java.awt.GridBagConstraints.HORIZONTAL;
            constraintsJLabel2.anchor = java.awt.GridBagConstraints.WEST;
            constraintsJLabel2.insets = new java.awt.Insets(10, 0, 0, 0);
            add(getJLabel2(), constraintsJLabel2);

            java.awt.GridBagConstraints constraintsExecute = new java.awt.GridBagConstraints();
            constraintsExecute.gridx = 1;
            constraintsExecute.gridy = 6;
            constraintsExecute.gridwidth = 3;
            constraintsExecute.fill = java.awt.GridBagConstraints.HORIZONTAL;
            constraintsExecute.anchor = java.awt.GridBagConstraints.SOUTHEAST;
            constraintsExecute.insets = new java.awt.Insets(0, 0, 5, 5);
            add(getExecute(), constraintsExecute);

            java.awt.GridBagConstraints constraintsRhoStatistic = new java.awt.GridBagConstraints();
            constraintsRhoStatistic.gridx = 1;
            constraintsRhoStatistic.gridy = 0;
            constraintsRhoStatistic.gridwidth = 3;
            constraintsRhoStatistic.fill = java.awt.GridBagConstraints.HORIZONTAL;
            constraintsRhoStatistic.anchor = java.awt.GridBagConstraints.WEST;
            constraintsRhoStatistic.insets = new java.awt.Insets(5, 0, 0, 0);
            add(getRhoStatistic(), constraintsRhoStatistic);

            java.awt.GridBagConstraints constraintsTauStatistic = new java.awt.GridBagConstraints();
            constraintsTauStatistic.gridx = 1;
            constraintsTauStatistic.gridy = 1;
            constraintsTauStatistic.gridwidth = 3;
            constraintsTauStatistic.fill = java.awt.GridBagConstraints.HORIZONTAL;
            constraintsTauStatistic.anchor = java.awt.GridBagConstraints.WEST;
            constraintsTauStatistic.insets = new java.awt.Insets(5, 0, 0, 0);
            add(getTauStatistic(), constraintsTauStatistic);

            java.awt.GridBagConstraints constraintsResultField = new java.awt.GridBagConstraints();
            constraintsResultField.gridx = 0;
            constraintsResultField.gridy = 0;
            constraintsResultField.gridheight = 7;
            constraintsResultField.fill = java.awt.GridBagConstraints.BOTH;
            constraintsResultField.weightx = 1.0;
            constraintsResultField.weighty = 1.0;
            constraintsResultField.insets = new java.awt.Insets(0, 0, 5, 5);
            add(getResultField(), constraintsResultField);
            initConnections();
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}
        // user code end
    }
}