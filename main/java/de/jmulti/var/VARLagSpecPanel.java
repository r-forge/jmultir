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

import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import com.jstatcom.component.NumSelector;
import com.jstatcom.component.ResultField;
import com.jstatcom.component.SymbolDisplay;
import com.jstatcom.model.JSCDRange;
import com.jstatcom.model.JSCInt;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.JSCString;
import com.jstatcom.model.ModelPanel;
import com.jstatcom.model.NumberRange;
import com.jstatcom.model.NumberRangeTypes;
import com.jstatcom.model.SymbolEvent;
import com.jstatcom.model.SymbolListener;
import com.jstatcom.ts.TSDateRange;

import de.jmulti.proc.InfoCritCall;

/**
 * This component provides a user input mask for specifying a VAR model. The
 * information criteria can be computed and displayed as well. All data objects
 * needed for the estimation are created.
 * 
 * @author A. Benkwitz, <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
final class VARLagSpecPanel extends ModelPanel {
    private static final Logger log = Logger.getLogger(VARLagSpecPanel.class);

    private boolean mustRecompute = true;

    private javax.swing.JLabel ivjJLabel3 = null;

    private javax.swing.JLabel ivjJLabel4 = null;

    private SymbolDisplay ivjEstimationPeriodLabel = null;

    private javax.swing.JCheckBox ivjCheckBox_Dummies = null;

    private javax.swing.JCheckBox ivjCheckBox_Intercept = null;

    private javax.swing.JCheckBox ivjCheckBox_Trend = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private javax.swing.JButton ivjComputeButton = null;

    private javax.swing.JLabel ivjJLabel1 = null;

    private NumSelector ivjMaxLagsTextField = null;

    private ResultField ivjResultField = null;

    private javax.swing.JPanel ivjJPanel1 = null;

    private NumSelector ivjEndLagsField = null;

    private NumSelector ivjExLagsField = null;

    class IvjEventHandler implements java.awt.event.ActionListener,
            java.awt.event.ItemListener {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == VARLagSpecPanel.this.getComputeButton())
                connEtoC4();
        };

        public void itemStateChanged(java.awt.event.ItemEvent e) {
            if (e.getSource() == VARLagSpecPanel.this.getCheckBox_Intercept())
                connEtoC1(e);
            if (e.getSource() == VARLagSpecPanel.this.getCheckBox_Dummies())
                connEtoC2(e);
            if (e.getSource() == VARLagSpecPanel.this.getCheckBox_Trend())
                connEtoC3(e);
        };
    };

    /**
     * SamplePeriodAndLagsPanel constructor comment.
     */
    public VARLagSpecPanel() {
        super();
        initialize();
    }

    /**
     * Computes the information criteria to provide a suggestion for the lag
     * selection.
     */
    private void computeButton_ActionEvents() {
        computeData();

        TSDateRange range = global().get(VARConstants.T1_raw_Def)
                .getJSCDRange().getTSDateRange();
        final InfoCritCall job = new InfoCritCall(getMaxLagsTextField()
                .getIntNumber(), getExLagsField().getIntNumber(), global().get(
                VARConstants.Ny_Def).getJSCSArray(), global().get(
                VARConstants.Nx_Def).getJSCSArray(), global().get(
                VARConstants.Nd_Def).getJSCSArray(), global().get(
                VARConstants.y_raw_Def).getJSCNArray(), global().get(
                VARConstants.x_raw_Def).getJSCNArray(), global().get(
                VARConstants.d_all_Def).getJSCNArray(), range.lowerBound(), 0);

        job.setSymbolTable(local());
        job.setOutHolder(getResultField());
        job.execute();

    }

    /**
     * Initiates computation of necessary data objects by the data manager if
     * necessary.
     * 
     */
    private void computeData() {
        if (!mustRecompute)
            return;
        VARDataManager.getInstance().computeData();
        mustRecompute = false;

    }

    /**
     * connEtoC1:
     * (CheckBox_Intercept.item.itemStateChanged(java.awt.event.ItemEvent) -->
     * SamplePeriodAndLagsPanel.computeData()V)
     * 
     * @param arg1
     *            java.awt.event.ItemEvent
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC1(java.awt.event.ItemEvent arg1) {
        try {
            // user code begin {1}
            // user code end
            this.setRecomputeData();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC2:
     * (CheckBox_Dummies.item.itemStateChanged(java.awt.event.ItemEvent) -->
     * SamplePeriodAndLagsPanel.setMustRecompute(Z)V)
     * 
     * @param arg1
     *            java.awt.event.ItemEvent
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC2(java.awt.event.ItemEvent arg1) {
        try {
            // user code begin {1}
            // user code end
            this.setRecomputeData();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC3:
     * (CheckBox_Trend.item.itemStateChanged(java.awt.event.ItemEvent) -->
     * SamplePeriodAndLagsPanel.setMustRecompute(Z)V)
     * 
     * @param arg1
     *            java.awt.event.ItemEvent
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC3(java.awt.event.ItemEvent arg1) {
        try {
            // user code begin {1}
            // user code end
            this.setRecomputeData();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC4: (ComputeButton.action. -->
     * VARSamplePeriodAndLagsPanel.computeButton_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC4() {
        try {
            // user code begin {1}
            // user code end
            this.computeButton_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * Return the CheckBox_Dummies property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getCheckBox_Dummies() {
        if (ivjCheckBox_Dummies == null) {
            try {
                ivjCheckBox_Dummies = new javax.swing.JCheckBox();
                ivjCheckBox_Dummies.setName("CheckBox_Dummies");
                ivjCheckBox_Dummies.setPreferredSize(new java.awt.Dimension(
                        150, 21));
                ivjCheckBox_Dummies
                        .setAlignmentX(java.awt.Component.RIGHT_ALIGNMENT);
                ivjCheckBox_Dummies.setText("Seasonal dummies");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjCheckBox_Dummies;
    }

    /**
     * Return the CheckBox_Intercept property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getCheckBox_Intercept() {
        if (ivjCheckBox_Intercept == null) {
            try {
                ivjCheckBox_Intercept = new javax.swing.JCheckBox();
                ivjCheckBox_Intercept.setName("CheckBox_Intercept");
                ivjCheckBox_Intercept.setSelected(true);
                ivjCheckBox_Intercept.setPreferredSize(new java.awt.Dimension(
                        55, 21));
                ivjCheckBox_Intercept
                        .setAlignmentX(java.awt.Component.RIGHT_ALIGNMENT);
                ivjCheckBox_Intercept.setText("Intercept");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjCheckBox_Intercept;
    }

    /**
     * Return the CheckBox_Trend property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getCheckBox_Trend() {
        if (ivjCheckBox_Trend == null) {
            try {
                ivjCheckBox_Trend = new javax.swing.JCheckBox();
                ivjCheckBox_Trend.setName("CheckBox_Trend");
                ivjCheckBox_Trend.setPreferredSize(new java.awt.Dimension(55,
                        21));
                ivjCheckBox_Trend
                        .setAlignmentX(java.awt.Component.RIGHT_ALIGNMENT);
                ivjCheckBox_Trend.setText("Trend");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjCheckBox_Trend;
    }

    /**
     * Return the ComputeButton property value.
     * 
     * @return javax.swing.JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JButton getComputeButton() {
        if (ivjComputeButton == null) {
            try {
                ivjComputeButton = new javax.swing.JButton();
                ivjComputeButton.setName("ComputeButton");
                ivjComputeButton.setText("Compute Infocriteria");
                ivjComputeButton.setActionCommand("ComputeButton");
                ivjComputeButton.setPreferredSize(new java.awt.Dimension(140,
                        25));
                ivjComputeButton.setEnabled(false);
                ivjComputeButton.setMargin(new java.awt.Insets(2, 5, 2, 5));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjComputeButton;
    }

    private NumSelector getEndLagsField() {
        if (ivjEndLagsField == null) {
            try {
                ivjEndLagsField = new NumSelector();
                ivjEndLagsField.setName("EndLagsField");
                ivjEndLagsField.setNumber(0.0);
                ivjEndLagsField.setRangeExpr("[0,20]");
                ivjEndLagsField.setColumns(7);
                ivjEndLagsField
                        .setPreferredSize(new java.awt.Dimension(77, 21));
                ivjEndLagsField.setMinimumSize(new java.awt.Dimension(77, 21));
                ivjEndLagsField.setEnabled(false);
                // user code begin {1}
                ivjEndLagsField.setIntType(true);
                ivjEndLagsField.setSymbolName(VARConstants.py_Def.name);
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjEndLagsField;
    }

    private SymbolDisplay getEstimationPeriodLabel() {
        if (ivjEstimationPeriodLabel == null) {
            try {
                ivjEstimationPeriodLabel = new SymbolDisplay();
                ivjEstimationPeriodLabel.setName("EstimationPeriodLabel");
                ivjEstimationPeriodLabel
                        .setPreferredSize(new java.awt.Dimension(42, 21));
                ivjEstimationPeriodLabel.setText("");
                // user code begin {1}
                ivjEstimationPeriodLabel
                        .setSymbolDef(VARConstants.estRange_Def);
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjEstimationPeriodLabel;
    }

    private NumSelector getExLagsField() {
        if (ivjExLagsField == null) {
            try {
                ivjExLagsField = new NumSelector();
                ivjExLagsField.setName("ExLagsField");
                ivjExLagsField.setRangeExpr("[0,20]");
                ivjExLagsField.setColumns(7);
                ivjExLagsField.setPreferredSize(new java.awt.Dimension(77, 21));
                ivjExLagsField.setMinimumSize(new java.awt.Dimension(77, 21));
                ivjExLagsField.setEnabled(false);
                // user code begin {1}
                ivjExLagsField.setIntType(true);
                ivjExLagsField.setSymbolName(VARConstants.px_Def.name);
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjExLagsField;
    }

    /**
     * Return the JLabel1 property value.
     * 
     * @return javax.swing.JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JLabel getJLabel1() {
        if (ivjJLabel1 == null) {
            try {
                ivjJLabel1 = new javax.swing.JLabel();
                ivjJLabel1.setName("JLabel1");
                ivjJLabel1.setPreferredSize(new java.awt.Dimension(130, 21));
                ivjJLabel1.setText("Endogenous max lags");
                ivjJLabel1.setMaximumSize(new java.awt.Dimension(130, 21));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJLabel1;
    }

    /**
     * Return the JLabel3 property value.
     * 
     * @return javax.swing.JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JLabel getJLabel3() {
        if (ivjJLabel3 == null) {
            try {
                ivjJLabel3 = new javax.swing.JLabel();
                ivjJLabel3.setName("JLabel3");
                ivjJLabel3.setPreferredSize(new java.awt.Dimension(130, 21));
                ivjJLabel3.setText("Endogenous lags");
                ivjJLabel3.setMinimumSize(new java.awt.Dimension(220, 17));
                ivjJLabel3.setMaximumSize(new java.awt.Dimension(130, 21));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJLabel3;
    }

    /**
     * Return the JLabel4 property value.
     * 
     * @return javax.swing.JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JLabel getJLabel4() {
        if (ivjJLabel4 == null) {
            try {
                ivjJLabel4 = new javax.swing.JLabel();
                ivjJLabel4.setName("JLabel4");
                ivjJLabel4.setPreferredSize(new java.awt.Dimension(130, 21));
                ivjJLabel4.setText("Exogenous lags");
                ivjJLabel4.setMinimumSize(new java.awt.Dimension(210, 17));
                ivjJLabel4.setMaximumSize(new java.awt.Dimension(130, 21));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJLabel4;
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
                ivjJPanel1.setPreferredSize(new java.awt.Dimension(0, 170));
                ivjJPanel1.setLayout(new java.awt.GridBagLayout());

                java.awt.GridBagConstraints constraintsJLabel3 = new java.awt.GridBagConstraints();
                constraintsJLabel3.gridx = 1;
                constraintsJLabel3.gridy = 2;
                constraintsJLabel3.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel3.insets = new java.awt.Insets(5, 5, 0, 0);
                getJPanel1().add(getJLabel3(), constraintsJLabel3);

                java.awt.GridBagConstraints constraintsJLabel4 = new java.awt.GridBagConstraints();
                constraintsJLabel4.gridx = 1;
                constraintsJLabel4.gridy = 3;
                constraintsJLabel4.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel4.insets = new java.awt.Insets(5, 5, 0, 0);
                getJPanel1().add(getJLabel4(), constraintsJLabel4);

                java.awt.GridBagConstraints constraintsJLabel1 = new java.awt.GridBagConstraints();
                constraintsJLabel1.gridx = 1;
                constraintsJLabel1.gridy = 5;
                constraintsJLabel1.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel1.insets = new java.awt.Insets(5, 5, 0, 0);
                getJPanel1().add(getJLabel1(), constraintsJLabel1);

                java.awt.GridBagConstraints constraintsEndLagsField = new java.awt.GridBagConstraints();
                constraintsEndLagsField.gridx = 2;
                constraintsEndLagsField.gridy = 2;
                constraintsEndLagsField.weightx = 1.0;
                constraintsEndLagsField.insets = new java.awt.Insets(5, 0, 0, 0);
                getJPanel1().add(getEndLagsField(), constraintsEndLagsField);

                java.awt.GridBagConstraints constraintsExLagsField = new java.awt.GridBagConstraints();
                constraintsExLagsField.gridx = 2;
                constraintsExLagsField.gridy = 3;
                constraintsExLagsField.weightx = 1.0;
                constraintsExLagsField.insets = new java.awt.Insets(5, 0, 0, 0);
                getJPanel1().add(getExLagsField(), constraintsExLagsField);

                java.awt.GridBagConstraints constraintsMaxLagsTextField = new java.awt.GridBagConstraints();
                constraintsMaxLagsTextField.gridx = 2;
                constraintsMaxLagsTextField.gridy = 5;
                constraintsMaxLagsTextField.weightx = 1.0;
                getJPanel1().add(getMaxLagsTextField(),
                        constraintsMaxLagsTextField);

                java.awt.GridBagConstraints constraintsEstimationPeriodLabel = new java.awt.GridBagConstraints();
                constraintsEstimationPeriodLabel.gridx = 1;
                constraintsEstimationPeriodLabel.gridy = 1;
                constraintsEstimationPeriodLabel.gridwidth = 3;
                constraintsEstimationPeriodLabel.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsEstimationPeriodLabel.insets = new java.awt.Insets(
                        0, 5, 0, 0);
                getJPanel1().add(getEstimationPeriodLabel(),
                        constraintsEstimationPeriodLabel);

                java.awt.GridBagConstraints constraintsCheckBox_Intercept = new java.awt.GridBagConstraints();
                constraintsCheckBox_Intercept.gridx = 3;
                constraintsCheckBox_Intercept.gridy = 2;
                constraintsCheckBox_Intercept.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsCheckBox_Intercept.insets = new java.awt.Insets(5,
                        0, 0, 0);
                getJPanel1().add(getCheckBox_Intercept(),
                        constraintsCheckBox_Intercept);

                java.awt.GridBagConstraints constraintsCheckBox_Dummies = new java.awt.GridBagConstraints();
                constraintsCheckBox_Dummies.gridx = 3;
                constraintsCheckBox_Dummies.gridy = 3;
                constraintsCheckBox_Dummies.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsCheckBox_Dummies.insets = new java.awt.Insets(5, 0,
                        0, 0);
                getJPanel1().add(getCheckBox_Dummies(),
                        constraintsCheckBox_Dummies);

                java.awt.GridBagConstraints constraintsCheckBox_Trend = new java.awt.GridBagConstraints();
                constraintsCheckBox_Trend.gridx = 3;
                constraintsCheckBox_Trend.gridy = 4;
                constraintsCheckBox_Trend.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsCheckBox_Trend.insets = new java.awt.Insets(5, 0, 0,
                        0);
                getJPanel1()
                        .add(getCheckBox_Trend(), constraintsCheckBox_Trend);

                java.awt.GridBagConstraints constraintsComputeButton = new java.awt.GridBagConstraints();
                constraintsComputeButton.gridx = 3;
                constraintsComputeButton.gridy = 5;
                constraintsComputeButton.weightx = 1.0;
                constraintsComputeButton.weighty = 1.0;
                getJPanel1().add(getComputeButton(), constraintsComputeButton);
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

    private NumSelector getMaxLagsTextField() {
        if (ivjMaxLagsTextField == null) {
            try {
                ivjMaxLagsTextField = new NumSelector();
                ivjMaxLagsTextField.setName("MaxLagsTextField");
                ivjMaxLagsTextField.setNumber(10.0);
                ivjMaxLagsTextField.setRangeExpr("[1,100]");
                ivjMaxLagsTextField.setPreferredSize(new java.awt.Dimension(77,
                        21));
                ivjMaxLagsTextField.setEnabled(false);
                ivjMaxLagsTextField.setMinimumSize(new java.awt.Dimension(50,
                        20));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjMaxLagsTextField;
    }

    /**
     * Return the ResultField property value.
     * 
     * @return com.jstatcom.component.ResultField
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
        getCheckBox_Intercept().addItemListener(ivjEventHandler);
        getCheckBox_Dummies().addItemListener(ivjEventHandler);
        getCheckBox_Trend().addItemListener(ivjEventHandler);
        getComputeButton().addActionListener(ivjEventHandler);
    }

    /**
     * Initialize the class.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initialize() {
        try {
            // user code begin {1}
            //
            // user code end
            setName("SamplePeriodAndLagsPanel");
            setPreferredSize(new java.awt.Dimension(450, 230));
            setLayout(new java.awt.BorderLayout());
            setSize(498, 384);
            setMinimumSize(new java.awt.Dimension(450, 230));
            add(getResultField(), "Center");
            add(getJPanel1(), "North");
            initConnections();
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}
        TitledBorder title = new TitledBorder(new BevelBorder(
                BevelBorder.LOWERED), "Specify VAR Model", TitledBorder.RIGHT,
                TitledBorder.TOP);
        getJPanel1().setBorder(title);

        // sets the sample period and the number of obs if lags change
        SymbolListener lagsListener = new SymbolListener() {
            public void valueChanged(SymbolEvent e) {
                JSCDRange jscrange = global().get(VARConstants.T1_raw_Def)
                        .getJSCDRange();

                int py = getEndLagsField().getIntNumber();
                int px = getExLagsField().getIntNumber();
                if (!jscrange.isEmpty()) {
                    int lags = Math.max(py, px);
                    TSDateRange range = jscrange.getTSDateRange()
                            .addPeriodsToStart(lags);

                    global().get(VARConstants.T1_Def).setJSCData(
                            new JSCDRange("estimationRange", range));

                    global().get(VARConstants.estRange_Def).setJSCData(
                            new JSCString("estRange", range.format("", 0)));

                    setRecomputeData();
                }
            }
        };
        global().get(VARConstants.py_Def).addSymbolListener(lagsListener);
        global().get(VARConstants.px_Def).addSymbolListener(lagsListener);

    }

    /**
     * propertyChange method comment.
     */
    public void selectionChanged() {
        setRecomputeData();

        JSCDRange jscrange = global().get(VARConstants.T1_raw_Def)
                .getJSCDRange();

        if (!jscrange.isEmpty()) {
            TSDateRange range = jscrange.getTSDateRange();

            double max = Math.ceil(range.numOfObs());
            int mlags = Math.max(global().get(VARConstants.py_Def).getJSCInt()
                    .intVal(), global().get(VARConstants.px_Def).getJSCInt()
                    .intVal());
            if (mlags > max) {
                global().get(VARConstants.px_Def).setJSCData(
                        new JSCInt("px", 0));
                global().get(VARConstants.py_Def).setJSCData(
                        new JSCInt("py", 0));
                mlags = 0;
            }
            TSDateRange estRange = range.addPeriodsToStart(mlags);
            global().get(VARConstants.T1_Def).setJSCData(
                    new JSCDRange("estRange", estRange));
            global().get(VARConstants.estRange_Def).setJSCData(
                    new JSCString("estRange", estRange.format("", 0)));

            getEndLagsField().setNumberRange(
                    new NumberRange(0, max, NumberRangeTypes.CLOSED));
            getExLagsField().setNumberRange(
                    new NumberRange(0, max, NumberRangeTypes.CLOSED));

        } else {
            global().get(VARConstants.T1_Def).clear();
            global().get(VARConstants.estRange_Def).clear();
        }
        getExLagsField().setEnabled(
                !global().get(VARConstants.x_raw_Def).isEmpty());
        boolean enabled = !global().get(VARConstants.y_raw_Def).isEmpty();
        getEndLagsField().setEnabled(enabled);
        getMaxLagsTextField().setEnabled(enabled);
        getComputeButton().setEnabled(enabled);
    }

    /**
     * Sets recomputation true and computes variable names.
     */
    private void setRecomputeData() {
        mustRecompute = true;

        // Set the selected deterministics in the data manager.
        VARDataManager.getInstance().setDetSel(
                getCheckBox_Intercept().isSelected(),
                getCheckBox_Trend().isSelected(),
                getCheckBox_Dummies().isSelected());

    }

    /**
     * propertyChange method comment.
     */
    public void shown(boolean isShown) {
        if (!isShown)
            computeData();

    }

    /**
     * Sets the selection of predefined deterministic variables.
     * 
     * @param detSel
     *            3x1 array indicating const-trend-seasdum (1:selected 0:not
     *            selected)
     */
    void setDetSelection(JSCNArray detSel) {
        if (detSel == null || detSel.isEmpty() || detSel.rows() != 3)
            throw new IllegalArgumentException("Invalid array argument.");
        // intercept
        getCheckBox_Intercept().setSelected(detSel.intAt(0, 0) == 1);
        // trend
        getCheckBox_Trend().setSelected(detSel.intAt(1, 0) == 1);
        // seasonal dummies
        getCheckBox_Dummies().setSelected(detSel.intAt(2, 0) == 1);
    }

    /**
     * Gets the current selection of predefined deterministic variables.
     * 
     * @return 3x1 array indicating const-trend-seasdum (1:selected 0:not
     *         selected)
     */
    JSCNArray getDetSelection() {
        int[] selection = new int[3];
        // intercept
        selection[0] = (getCheckBox_Intercept().isSelected()) ? 1 : 0;
        // trend
        selection[1] = (getCheckBox_Trend().isSelected()) ? 1 : 0;
        // seasonal dummies
        selection[2] = (getCheckBox_Dummies().isSelected()) ? 1 : 0;

        return new JSCNArray("detSelection", selection);
    }
}