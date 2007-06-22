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
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import com.jstatcom.component.NumSelector;
import com.jstatcom.component.ResultField;
import com.jstatcom.component.StdMessages;
import com.jstatcom.engine.PCall;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.ModelPanel;
import com.jstatcom.ts.TSDateRange;
import com.jstatcom.util.UData;

import de.jmulti.proc.InfoCritCall;
import de.jmulti.proc.SLCointTestCall;

/**
 * Panel to perform the S&L test for cointegration.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
final class SLCointPanel extends ModelPanel {
    private static final Logger log = Logger.getLogger(SLCointPanel.class);

    private JRadioButton ivjConst = null;

    private JRadioButton ivjConstAndTrend = null;

    private JLabel ivjJLabel2 = null;

    private JLabel ivjJLabel21 = null;

    private NumSelector ivjLagLength = null;

    private NumSelector ivjMaxLagLength = null;

    private JRadioButton ivjOrthConstAndTrend = null;

    private ResultField ivjResultField = null;

    private JCheckBox ivjSeasonalDummies = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private JButton ivjExecute = null;

    class IvjEventHandler implements java.awt.event.ActionListener {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == SLCointPanel.this.getExecute())
                connEtoC1();
        };
    };

    /**
     * LuetkeSaikkPanel constructor comment.
     */
    public SLCointPanel() {
        super();
        initialize();
    }

    /**
     * connEtoC1: (JButton1.action. --> SLCointPanel.jButton1_ActionEvents()V)
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
        JSCNArray data = global().get(CointPanel.COINT_ENDDATA).getJSCNArray();
        if (!data.isEmpty()) {
            int k = data.cols();
            if (getOrthConstAndTrend().isSelected() && k < 2) {
                StdMessages
                        .infoNothingSelected("Please select at least two endogenous series for this model.");
                return;
            }
        } else {
            StdMessages.infoNothingSelected(UnitRootPanel.NO_VAR_MSG);
            return;
        }
        TSDateRange range = global().get(CointPanel.COINT_DRANGE)
                .getJSCDRange().getTSDateRange();

        int period = range.subPeriodicity();
        if (period == 1 && getSeasonalDummies().isSelected()) {
            StdMessages.errorSpecification(UnitRootPanel.NO_SEAS_MSG);
            return;
        }

        // Checks are ok, prepare call.
        int testState = 1;
        if (getConst().isSelected())
            testState = 1;
        if (getConstAndTrend().isSelected())
            testState = 2;
        if (getOrthConstAndTrend().isSelected())
            testState = 3;

        // Check rank of lag truncated deterministics, because dummies might
        // have
        // a break in the lag range, causing a singular regressor.
        JSCNArray dets = UData.createDeterministic(range, global().get(
                CointPanel.COINT_DET).getJSCNArray(), true,
                getSeasonalDummies().isSelected(), true, getConstAndTrend()
                        .isSelected()
                        || getOrthConstAndTrend().isSelected(), "dets");

        if (!dets.isEmpty())
            if (!UData.hasFullColumnRank(dets, getLagLength().getIntNumber())) {
                StdMessages
                        .errorSpecification("Matrix of deterministic variables is singular after truncating "
                                + getLagLength().getIntNumber()
                                + " lags.\nPlease check the dummy specification.");
                return;
            }

        // S&L Test call.
        PCall job = new SLCointTestCall(data, global().get(
                CointPanel.COINT_ENDNAMES).getJSCSArray(), global().get(
                CointPanel.COINT_DET).getJSCNArray(), global().get(
                CointPanel.COINT_DETNAMES).getJSCSArray(), getSeasonalDummies()
                .isSelected(), getLagLength().getIntNumber(), testState, range);
        job.setSymbolTable(local());
        job.setOutHolder(getResultField());
        job.execute();

        // Infocriteria call.
        PCall jobInfo = new InfoCritCall(getMaxLagLength().getIntNumber(),
                null, null, data, dets, range.lowerBound(), 0);
        jobInfo.setOutHolder(getResultField());
        jobInfo.setPrintDate(false);
        job.add(jobInfo);
    }

    /**
     * Return the Const property value.
     * 
     * @return javax.swing.JRadioButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JRadioButton getConst() {
        if (ivjConst == null) {
            try {
                ivjConst = new javax.swing.JRadioButton();
                ivjConst.setName("Const");
                ivjConst.setSelected(true);
                ivjConst.setText("Constant");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjConst;
    }

    /**
     * Return the ConstAndTrend property value.
     * 
     * @return javax.swing.JRadioButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JRadioButton getConstAndTrend() {
        if (ivjConstAndTrend == null) {
            try {
                ivjConstAndTrend = new javax.swing.JRadioButton();
                ivjConstAndTrend.setName("ConstAndTrend");
                ivjConstAndTrend.setText("Constant and trend");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjConstAndTrend;
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
     * Return the JLabel21 property value.
     * 
     * @return javax.swing.JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JLabel getJLabel21() {
        if (ivjJLabel21 == null) {
            try {
                ivjJLabel21 = new javax.swing.JLabel();
                ivjJLabel21.setName("JLabel21");
                ivjJLabel21.setPreferredSize(new java.awt.Dimension(130, 20));
                ivjJLabel21.setText("Max number of lags");
                ivjJLabel21.setMaximumSize(new java.awt.Dimension(100, 20));
                ivjJLabel21.setMinimumSize(new java.awt.Dimension(130, 20));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJLabel21;
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
                ivjLagLength.setRangeExpr("[1,100]");
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
     * Return the MaxLagLength property value.
     * 
     * @return com.jstatcom.model.NumberSelector
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private NumSelector getMaxLagLength() {
        if (ivjMaxLagLength == null) {
            try {
                ivjMaxLagLength = new NumSelector();
                ivjMaxLagLength.setName("MaxLagLength");
                ivjMaxLagLength.setNumber(10.0);
                ivjMaxLagLength.setText("10");
                ivjMaxLagLength.setRangeExpr("[1,100]");
                ivjMaxLagLength
                        .setPreferredSize(new java.awt.Dimension(25, 20));
                ivjMaxLagLength.setMinimumSize(new java.awt.Dimension(25, 20));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjMaxLagLength;
    }

    /**
     * Return the OrthConstAndTrend property value.
     * 
     * @return javax.swing.JRadioButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JRadioButton getOrthConstAndTrend() {
        if (ivjOrthConstAndTrend == null) {
            try {
                ivjOrthConstAndTrend = new javax.swing.JRadioButton();
                ivjOrthConstAndTrend.setName("OrthConstAndTrend");
                ivjOrthConstAndTrend.setText("Orthogonal trend");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjOrthConstAndTrend;
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
     * Return the SeasonalDummies property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getSeasonalDummies() {
        if (ivjSeasonalDummies == null) {
            try {
                ivjSeasonalDummies = new javax.swing.JCheckBox();
                ivjSeasonalDummies.setName("SeasonalDummies");
                ivjSeasonalDummies.setText("Seasonal dummies");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSeasonalDummies;
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
                    BevelBorder.LOWERED), "Saikkonen & Lütkepohl Test",
                    TitledBorder.RIGHT, TitledBorder.TOP);
            this.setBorder(title);
            ButtonGroup selectGroup = new javax.swing.ButtonGroup();
            selectGroup.add(getConst());
            selectGroup.add(getConstAndTrend());
            selectGroup.add(getOrthConstAndTrend());

            // user code end
            setName("SLCointPanel");
            setPreferredSize(new java.awt.Dimension(485, 350));
            setLayout(new java.awt.GridBagLayout());
            setSize(485, 373);
            setMinimumSize(new java.awt.Dimension(485, 350));

            java.awt.GridBagConstraints constraintsResultField = new java.awt.GridBagConstraints();
            constraintsResultField.gridx = 0;
            constraintsResultField.gridy = 0;
            constraintsResultField.gridheight = 13;
            constraintsResultField.fill = java.awt.GridBagConstraints.BOTH;
            constraintsResultField.weightx = 1.0;
            constraintsResultField.weighty = 1.0;
            constraintsResultField.insets = new java.awt.Insets(0, 0, 5, 5);
            add(getResultField(), constraintsResultField);

            java.awt.GridBagConstraints constraintsConst = new java.awt.GridBagConstraints();
            constraintsConst.gridx = 1;
            constraintsConst.gridy = 0;
            constraintsConst.gridwidth = 2;
            constraintsConst.fill = java.awt.GridBagConstraints.HORIZONTAL;
            constraintsConst.anchor = java.awt.GridBagConstraints.WEST;
            add(getConst(), constraintsConst);

            java.awt.GridBagConstraints constraintsConstAndTrend = new java.awt.GridBagConstraints();
            constraintsConstAndTrend.gridx = 1;
            constraintsConstAndTrend.gridy = 1;
            constraintsConstAndTrend.gridwidth = 2;
            constraintsConstAndTrend.fill = java.awt.GridBagConstraints.HORIZONTAL;
            constraintsConstAndTrend.anchor = java.awt.GridBagConstraints.WEST;
            add(getConstAndTrend(), constraintsConstAndTrend);

            java.awt.GridBagConstraints constraintsOrthConstAndTrend = new java.awt.GridBagConstraints();
            constraintsOrthConstAndTrend.gridx = 1;
            constraintsOrthConstAndTrend.gridy = 2;
            constraintsOrthConstAndTrend.gridwidth = 2;
            constraintsOrthConstAndTrend.fill = java.awt.GridBagConstraints.HORIZONTAL;
            constraintsOrthConstAndTrend.anchor = java.awt.GridBagConstraints.WEST;
            constraintsOrthConstAndTrend.insets = new java.awt.Insets(0, 0, 10,
                    0);
            add(getOrthConstAndTrend(), constraintsOrthConstAndTrend);

            java.awt.GridBagConstraints constraintsLagLength = new java.awt.GridBagConstraints();
            constraintsLagLength.gridx = 1;
            constraintsLagLength.gridy = 3;
            constraintsLagLength.anchor = java.awt.GridBagConstraints.WEST;
            constraintsLagLength.insets = new java.awt.Insets(0, 0, 5, 0);
            add(getLagLength(), constraintsLagLength);

            java.awt.GridBagConstraints constraintsJLabel2 = new java.awt.GridBagConstraints();
            constraintsJLabel2.gridx = 2;
            constraintsJLabel2.gridy = 3;
            constraintsJLabel2.fill = java.awt.GridBagConstraints.HORIZONTAL;
            constraintsJLabel2.anchor = java.awt.GridBagConstraints.WEST;
            constraintsJLabel2.insets = new java.awt.Insets(0, 5, 5, 0);
            add(getJLabel2(), constraintsJLabel2);

            java.awt.GridBagConstraints constraintsMaxLagLength = new java.awt.GridBagConstraints();
            constraintsMaxLagLength.gridx = 1;
            constraintsMaxLagLength.gridy = 4;
            constraintsMaxLagLength.anchor = java.awt.GridBagConstraints.WEST;
            constraintsMaxLagLength.insets = new java.awt.Insets(0, 0, 10, 0);
            add(getMaxLagLength(), constraintsMaxLagLength);

            java.awt.GridBagConstraints constraintsJLabel21 = new java.awt.GridBagConstraints();
            constraintsJLabel21.gridx = 2;
            constraintsJLabel21.gridy = 4;
            constraintsJLabel21.fill = java.awt.GridBagConstraints.HORIZONTAL;
            constraintsJLabel21.anchor = java.awt.GridBagConstraints.WEST;
            constraintsJLabel21.insets = new java.awt.Insets(0, 5, 10, 0);
            add(getJLabel21(), constraintsJLabel21);

            java.awt.GridBagConstraints constraintsSeasonalDummies = new java.awt.GridBagConstraints();
            constraintsSeasonalDummies.gridx = 1;
            constraintsSeasonalDummies.gridy = 5;
            constraintsSeasonalDummies.gridwidth = 2;
            constraintsSeasonalDummies.fill = java.awt.GridBagConstraints.HORIZONTAL;
            constraintsSeasonalDummies.anchor = java.awt.GridBagConstraints.WEST;
            add(getSeasonalDummies(), constraintsSeasonalDummies);

            java.awt.GridBagConstraints constraintsExecute = new java.awt.GridBagConstraints();
            constraintsExecute.gridx = 1;
            constraintsExecute.gridy = 12;
            constraintsExecute.gridwidth = 2;
            constraintsExecute.fill = java.awt.GridBagConstraints.HORIZONTAL;
            constraintsExecute.anchor = java.awt.GridBagConstraints.SOUTHEAST;
            constraintsExecute.insets = new java.awt.Insets(0, 0, 5, 5);
            add(getExecute(), constraintsExecute);
            initConnections();
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}
        // user code end
    }
}