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

import de.jmulti.proc.KPSSTestCall;

/**
 * Panel to perform the KPSS test for stationarity.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
final class KPSSPanel extends ModelPanel {
    private static final Logger log = Logger.getLogger(KPSSPanel.class);

    private JLabel ivjJLabel2 = null;

    private NumSelector ivjLagLength = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private ResultField ivjResultField = null;

    private JRadioButton ivjLevel = null;

    private JRadioButton ivjTrend = null;

    private JButton ivjExecute = null;

    class IvjEventHandler implements java.awt.event.ActionListener {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == KPSSPanel.this.getExecute())
                connEtoC1();
        };
    };

    /**
     * ADFPanel constructor comment.
     */
    public KPSSPanel() {
        super();
        initialize();
    }

    /**
     * connEtoC1: (Execute.action. --> KPSSPanel.execute_ActionEvents()V)
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
     * Comment
     */
    private void execute_ActionEvents() {
        if (global().get(UnitRootPanel.UR_ENDDATA).isEmpty()) {
            StdMessages.infoNothingSelected(UnitRootPanel.NO_VAR_MSG);
            return;
        }
        int lagLength = getLagLength().getIntNumber();

        int testState = 1; // Level selected.
        if (getTrend().isSelected())
            testState = 2;

        TSDateRange range = global().get(UnitRootPanel.UR_DRANGE)
                .getJSCDRange().getTSDateRange();

        // Call KPSS test.
        PCall job = new KPSSTestCall(global().get(UnitRootPanel.UR_ENDDATA)
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
                ivjJLabel2.setMaximumSize(new java.awt.Dimension(85, 20));
                ivjJLabel2.setMinimumSize(new java.awt.Dimension(85, 20));
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
     * Return the Level property value.
     * 
     * @return javax.swing.JRadioButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JRadioButton getLevel() {
        if (ivjLevel == null) {
            try {
                ivjLevel = new javax.swing.JRadioButton();
                ivjLevel.setName("Level");
                ivjLevel.setSelected(true);
                ivjLevel.setText("Level stationarity");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjLevel;
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
     * Return the Trend property value.
     * 
     * @return javax.swing.JRadioButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JRadioButton getTrend() {
        if (ivjTrend == null) {
            try {
                ivjTrend = new javax.swing.JRadioButton();
                ivjTrend.setName("Trend");
                ivjTrend.setText("Trend stationarity");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjTrend;
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
                    BevelBorder.LOWERED), "KPSS Test", TitledBorder.RIGHT,
                    TitledBorder.TOP);
            this.setBorder(title);
            ButtonGroup selectGroup = new javax.swing.ButtonGroup();
            selectGroup.add(getLevel());
            selectGroup.add(getTrend());

            // user code end
            setName("ADFPanel");
            setLayout(new java.awt.GridBagLayout());
            setMaximumSize(new java.awt.Dimension(100000, 1000000));
            setPreferredSize(new java.awt.Dimension(485, 350));
            setSize(485, 350);
            setMinimumSize(new java.awt.Dimension(485, 350));

            java.awt.GridBagConstraints constraintsLagLength = new java.awt.GridBagConstraints();
            constraintsLagLength.gridx = 1;
            constraintsLagLength.gridy = 2;
            constraintsLagLength.fill = java.awt.GridBagConstraints.HORIZONTAL;
            constraintsLagLength.anchor = java.awt.GridBagConstraints.WEST;
            constraintsLagLength.insets = new java.awt.Insets(10, 0, 0, 0);
            add(getLagLength(), constraintsLagLength);

            java.awt.GridBagConstraints constraintsJLabel2 = new java.awt.GridBagConstraints();
            constraintsJLabel2.gridx = 2;
            constraintsJLabel2.gridy = 2;
            constraintsJLabel2.gridwidth = 2;
            constraintsJLabel2.fill = java.awt.GridBagConstraints.HORIZONTAL;
            constraintsJLabel2.anchor = java.awt.GridBagConstraints.WEST;
            constraintsJLabel2.insets = new java.awt.Insets(10, 5, 0, 0);
            add(getJLabel2(), constraintsJLabel2);

            java.awt.GridBagConstraints constraintsExecute = new java.awt.GridBagConstraints();
            constraintsExecute.gridx = 1;
            constraintsExecute.gridy = 6;
            constraintsExecute.gridwidth = 3;
            constraintsExecute.fill = java.awt.GridBagConstraints.HORIZONTAL;
            constraintsExecute.anchor = java.awt.GridBagConstraints.SOUTHEAST;
            constraintsExecute.insets = new java.awt.Insets(0, 0, 5, 5);
            add(getExecute(), constraintsExecute);

            java.awt.GridBagConstraints constraintsResultField = new java.awt.GridBagConstraints();
            constraintsResultField.gridx = 0;
            constraintsResultField.gridy = 0;
            constraintsResultField.gridheight = 7;
            constraintsResultField.fill = java.awt.GridBagConstraints.BOTH;
            constraintsResultField.weightx = 1.0;
            constraintsResultField.weighty = 1.0;
            constraintsResultField.insets = new java.awt.Insets(0, 0, 5, 5);
            add(getResultField(), constraintsResultField);

            java.awt.GridBagConstraints constraintsLevel = new java.awt.GridBagConstraints();
            constraintsLevel.gridx = 1;
            constraintsLevel.gridy = 0;
            constraintsLevel.gridwidth = 3;
            constraintsLevel.fill = java.awt.GridBagConstraints.HORIZONTAL;
            add(getLevel(), constraintsLevel);

            java.awt.GridBagConstraints constraintsTrend = new java.awt.GridBagConstraints();
            constraintsTrend.gridx = 1;
            constraintsTrend.gridy = 1;
            constraintsTrend.gridwidth = 3;
            constraintsTrend.fill = java.awt.GridBagConstraints.HORIZONTAL;
            constraintsTrend.insets = new java.awt.Insets(5, 0, 0, 0);
            add(getTrend(), constraintsTrend);
            initConnections();
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}
        // user code end
    }
}