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

package de.jmulti.vecm;

import javax.swing.border.BevelBorder;

import org.apache.log4j.Logger;

import com.jstatcom.component.CheckBoxList;
import com.jstatcom.component.NumSelector;
import com.jstatcom.component.ResultField;
import com.jstatcom.component.SymbolDisplay;
import com.jstatcom.model.JSCInt;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.ModelPanel;
import com.jstatcom.model.NumberRange;
import com.jstatcom.model.NumberRangeTypes;
import com.jstatcom.model.SymbolEvent;
import com.jstatcom.model.SymbolListener;
import com.jstatcom.ts.TSDateRange;

import de.jmulti.proc.InfoCritCall;
import javax.swing.JComboBox;
import java.awt.GridBagConstraints;

/**
 * This component provides a user input mask for specifying a VECM model. The
 * information criteria can be computed and displayed as well. All data objects
 * needed for the estimation are created.
 * 
 * @author A. Benkwitz, <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
final class VECMLagSpecPanel extends ModelPanel {
    private static final Logger log = Logger.getLogger(VECMLagSpecPanel.class);

    private boolean mustRecompute = true;

    private javax.swing.JLabel ivjJLabel3 = null;

    private SymbolDisplay ivjEstimationPeriodLabel = null;

    private javax.swing.JCheckBox ivjCheckBox_Dummies = null;

    private javax.swing.JCheckBox ivjCheckBox_Intercept = null;

    private javax.swing.JCheckBox ivjCheckBox_Trend = null;

    private javax.swing.JLabel ivjJLabel = null;

    private javax.swing.JLabel ivjLabel_ExogenousLags = null;

    private javax.swing.JLabel ivjJLabel1 = null;

    private NumSelector ivjMaxLagsTextField = null;

    private ResultField ivjResultField = null;

    private javax.swing.JButton ivjComputeButton = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private NumSelector ivjCointegrationRankSelector = null;

    private NumSelector ivjEndogenousLagsSelector = null;

    private NumSelector ivjExogenousLagsSelector = null;

    private CheckBoxList ivjDetCheckList = null;

    private javax.swing.JScrollPane ivjJScrollPane1 = null;

    private javax.swing.JButton ivjClearButton = null;

    private javax.swing.JPanel ivjInfoTab = null;

    private javax.swing.JTabbedPane ivjJTabbedPane1 = null;

    private javax.swing.JPanel ivjSpecTab = null;

    private VECMEstStratPanel ivjVECMEstStratPanel = null;

    private javax.swing.JPanel ivjLagECPanel = null;

    private JComboBox seasDumComboBox = null;

    class IvjEventHandler implements java.awt.event.ActionListener,
            java.awt.event.ItemListener, java.beans.PropertyChangeListener {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == VECMLagSpecPanel.this.getComputeButton())
                connEtoC1();
            if (e.getSource() == VECMLagSpecPanel.this.getClearButton())
                connEtoC5();
        };

        public void itemStateChanged(java.awt.event.ItemEvent e) {
            if (e.getSource() == VECMLagSpecPanel.this.getCheckBox_Intercept())
                connEtoC2(e);
            if (e.getSource() == VECMLagSpecPanel.this.getCheckBox_Dummies())
                connEtoC3(e);
            if (e.getSource() == VECMLagSpecPanel.this.getCheckBox_Trend())
                connEtoC4(e);
        };

        public void propertyChange(java.beans.PropertyChangeEvent evt) {
            if (evt.getSource() == VECMLagSpecPanel.this
                    .getEndogenousLagsSelector()
                    && (evt.getPropertyName().equals("number")))
                connEtoC6(evt);
            if (evt.getSource() == VECMLagSpecPanel.this
                    .getExogenousLagsSelector()
                    && (evt.getPropertyName().equals("number")))
                connEtoC7(evt);
            if (evt.getSource() == VECMLagSpecPanel.this.getDetCheckList()
                    && (evt.getPropertyName().equals("selectionChanged")))
                connEtoC8(evt);
            if (evt.getSource() == VECMLagSpecPanel.this
                    .getCointegrationRankSelector()
                    && (evt.getPropertyName().equals("number")))
                connEtoC9(evt);
        };
    };

    /**
     * SamplePeriodAndLagsPanel constructor comment.
     */
    public VECMLagSpecPanel() {
        super();
        initialize();
    }

    /**
     * Checks range for the cointegration rank.
     * 
     */
    private void checkBoundsCointegrationRank() {
        int old_value = getCointegrationRankSelector().getIntNumber();
        int k = global().get(VECMConstants.y_raw_Def).getJSCNArray().cols();
        int min_value = 1;
        int max_value = Math.max(min_value, k - 1);
        int new_value = Math.min(Math.max(min_value, old_value), max_value);

        global().get(VECMConstants.cointRank_Def).setJSCData(
                new JSCInt("r", new_value));

        getCointegrationRankSelector().setNumberRange(
                new NumberRange(min_value, max_value, NumberRangeTypes.CLOSED));
        getCointegrationRankSelector().setEnabled(k > 1);

    }

    /**
     * Checks range for endogenous lags.
     */
    private void checkBoundsEndogenousLags() {
        int t = global().get(VECMConstants.y_raw_Def).getJSCNArray().rows();
        int max_value = (int) (t * 0.4);
        int old_value = getEndogenousLagsSelector().getIntNumber();
        int new_value = Math.min(old_value, max_value);

        global().get(VECMConstants.pdy_Def).setJSCData(
                new JSCInt("pdy", new_value));

        getEndogenousLagsSelector().setNumberRange(
                new NumberRange(0, max_value, NumberRangeTypes.CLOSED));

    }

    /**
     * Checks range for exogenous lags.
     */
    private void checkBoundsExogenousLags() {
        int t = global().get(VECMConstants.x_raw_Def).getJSCNArray().rows();

        int max_value = (int) (t * 0.4);
        int old_value = getExogenousLagsSelector().getIntNumber();

        int new_value = Math.min(old_value, max_value);
        global().get(VECMConstants.px_Def).setJSCData(
                new JSCInt("px", new_value));

        getExogenousLagsSelector().setNumberRange(
                new NumberRange(0, max_value, NumberRangeTypes.CLOSED));

    }

    /**
     * Clears the selected deterministics to the EC term.
     */
    private void clearButton_ActionEvents() {
        getDetCheckList().clearSelectionStatus();
        global().get(VECMConstants.Nd_ec_Def).clear();

    }

    /**
     * Computes and displays information criteria.
     */
    private void computeButton_ActionEvents() {
        computeData();
        TSDateRange range = global().get(VECMConstants.T1_raw_Def)
                .getJSCDRange().getTSDateRange();
        final InfoCritCall job = new InfoCritCall(getMaxLagsTextField()
                .getIntNumber(), getExogenousLagsSelector().getIntNumber(),
                global().get(VECMConstants.Ny_Def).getJSCSArray(), global()
                        .get(VECMConstants.Nx_Def).getJSCSArray(), global()
                        .get(VECMConstants.Nd_Def).getJSCSArray(), global()
                        .get(VECMConstants.y_raw_Def).getJSCNArray(), global()
                        .get(VECMConstants.x_raw_Def).getJSCNArray(), global()
                        .get(VECMConstants.d_Def).getJSCNArray(), range
                        .lowerBound(), 1);

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
        VECMDataManagerObs.getInstance().computeData();
        mustRecompute = false;

    }

    /**
     * connEtoC1: (ComputeButton.action. -->
     * VECMLagSpecPanel.computeButton_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC1() {
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
     * connEtoC2:
     * (CheckBox_Intercept.item.itemStateChanged(java.awt.event.ItemEvent) -->
     * VECMLagSpecPanel.setRecomputeData()V)
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
     * (CheckBox_Dummies.item.itemStateChanged(java.awt.event.ItemEvent) -->
     * VECMLagSpecPanel.setRecomputeData()V)
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
     * connEtoC4:
     * (CheckBox_Trend.item.itemStateChanged(java.awt.event.ItemEvent) -->
     * VECMLagSpecPanel.setRecomputeData()V)
     * 
     * @param arg1
     *            java.awt.event.ItemEvent
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC4(java.awt.event.ItemEvent arg1) {
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
     * connEtoC5: (ClearButton.action. -->
     * VECMLagSpecPanel.clearButton_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC5() {
        try {
            // user code begin {1}
            // user code end
            this.clearButton_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC6: (EndogenousLagsSelector.number -->
     * VECMLagSpecPanel.lagsChanged()V)
     * 
     * @param arg1
     *            java.beans.PropertyChangeEvent
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC6(java.beans.PropertyChangeEvent arg1) {
        try {
            // user code begin {1}
            // user code end
            this.lagsChanged();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC7: (ExogenousLagsSelector.number -->
     * VECMLagSpecPanel.lagsChanged()V)
     * 
     * @param arg1
     *            java.beans.PropertyChangeEvent
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC7(java.beans.PropertyChangeEvent arg1) {
        try {
            // user code begin {1}
            // user code end
            this.lagsChanged();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC8: (DetCheckList.selectionChanged -->
     * VECMLagSpecPanel.setRecomputeData()V)
     * 
     * @param arg1
     *            java.beans.PropertyChangeEvent
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC8(java.beans.PropertyChangeEvent arg1) {
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
     * connEtoC9: (CointegrationRankSelector.number -->
     * VECMLagSpecPanel.lagsChanged()V)
     * 
     * @param arg1
     *            java.beans.PropertyChangeEvent
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC9(java.beans.PropertyChangeEvent arg1) {
        try {
            // user code begin {1}
            // user code end
            this.lagsChanged();
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
                        150, 22));
                ivjCheckBox_Dummies.setText("Seasonal dummies");
                // user code begin {1}
                ivjCheckBox_Dummies
                        .addActionListener(new java.awt.event.ActionListener() {
                            public void actionPerformed(
                                    java.awt.event.ActionEvent e) {
                                if (ivjCheckBox_Dummies.isSelected())
                                    getSeasDumComboBox().setEnabled(true);
                                else
                                    getSeasDumComboBox().setEnabled(false);
                            }
                        });
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
     * Return the ClearButton property value.
     * 
     * @return javax.swing.JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JButton getClearButton() {
        if (ivjClearButton == null) {
            try {
                ivjClearButton = new javax.swing.JButton();
                ivjClearButton.setName("ClearButton");
                ivjClearButton.setText("Clear EC Selection");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjClearButton;
    }

    private NumSelector getCointegrationRankSelector() {
        if (ivjCointegrationRankSelector == null) {
            try {
                ivjCointegrationRankSelector = new NumSelector();
                ivjCointegrationRankSelector
                        .setName("CointegrationRankSelector");
                ivjCointegrationRankSelector.setNumber(1.0);
                ivjCointegrationRankSelector.setPrecision(0);
                ivjCointegrationRankSelector.setRangeExpr("[1,15]");
                ivjCointegrationRankSelector.setColumns(7);
                ivjCointegrationRankSelector.setEnabled(false);
                ivjCointegrationRankSelector
                        .setMinimumSize(new java.awt.Dimension(50, 20));
                ivjCointegrationRankSelector.setIntType(true);
                // user code begin {1}
                ivjCointegrationRankSelector
                        .setSymbolName(VECMConstants.cointRank_Def.name);
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjCointegrationRankSelector;
    }

    /**
     * Return the JButton property value.
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
                ivjComputeButton.setPreferredSize(new java.awt.Dimension(150,
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

    private CheckBoxList getDetCheckList() {
        if (ivjDetCheckList == null) {
            try {
                ivjDetCheckList = new CheckBoxList();
                ivjDetCheckList.setName("DetCheckList");
                ivjDetCheckList.setBorderTitle("Restrict Dets to EC Term");
                ivjDetCheckList.setLocation(0, 0);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjDetCheckList;
    }

    private NumSelector getEndogenousLagsSelector() {
        if (ivjEndogenousLagsSelector == null) {
            try {
                ivjEndogenousLagsSelector = new NumSelector();
                ivjEndogenousLagsSelector.setName("EndogenousLagsSelector");
                ivjEndogenousLagsSelector.setPrecision(0);
                ivjEndogenousLagsSelector.setText("0");
                ivjEndogenousLagsSelector.setRangeExpr("[0,20]");
                ivjEndogenousLagsSelector.setColumns(7);
                ivjEndogenousLagsSelector.setEnabled(false);
                ivjEndogenousLagsSelector
                        .setMinimumSize(new java.awt.Dimension(50, 20));
                // user code begin {1}
                ivjEndogenousLagsSelector.setIntType(true);
                ivjEndogenousLagsSelector
                        .setSymbolName(VECMConstants.pdy_Def.name);
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjEndogenousLagsSelector;
    }

    private SymbolDisplay getEstimationPeriodLabel() {
        if (ivjEstimationPeriodLabel == null) {
            try {
                ivjEstimationPeriodLabel = new SymbolDisplay();
                ivjEstimationPeriodLabel.setName("EstimationPeriodLabel");
                ivjEstimationPeriodLabel
                        .setPreferredSize(new java.awt.Dimension(100, 21));
                ivjEstimationPeriodLabel.setText("");
                ivjEstimationPeriodLabel.setMinimumSize(new java.awt.Dimension(
                        120, 14));
                // user code begin {1}
                ivjEstimationPeriodLabel
                        .setSymbolDef(VECMConstants.estRange_Def);
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjEstimationPeriodLabel;
    }

    private NumSelector getExogenousLagsSelector() {
        if (ivjExogenousLagsSelector == null) {
            try {
                ivjExogenousLagsSelector = new NumSelector();
                ivjExogenousLagsSelector.setName("ExogenousLagsSelector");
                ivjExogenousLagsSelector.setPrecision(0);
                ivjExogenousLagsSelector.setRangeExpr("[0,20]");
                ivjExogenousLagsSelector.setColumns(7);
                ivjExogenousLagsSelector.setEnabled(false);
                ivjExogenousLagsSelector.setMinimumSize(new java.awt.Dimension(
                        50, 20));
                // user code begin {1}
                ivjExogenousLagsSelector.setIntType(true);
                ivjExogenousLagsSelector
                        .setSymbolName(VECMConstants.px_Def.name);
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjExogenousLagsSelector;
    }

    /**
     * Return the InfoTab property value.
     * 
     * @return javax.swing.JPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JPanel getInfoTab() {
        if (ivjInfoTab == null) {
            try {
                ivjInfoTab = new javax.swing.JPanel();
                ivjInfoTab.setName("InfoTab");
                ivjInfoTab.setBorder(new BevelBorder(BevelBorder.LOWERED));
                ivjInfoTab.setLayout(new java.awt.GridBagLayout());

                java.awt.GridBagConstraints constraintsJLabel1 = new java.awt.GridBagConstraints();
                constraintsJLabel1.gridx = 1;
                constraintsJLabel1.gridy = 1;
                constraintsJLabel1.insets = new java.awt.Insets(5, 10, 5, 0);
                getInfoTab().add(getJLabel1(), constraintsJLabel1);

                java.awt.GridBagConstraints constraintsMaxLagsTextField = new java.awt.GridBagConstraints();
                constraintsMaxLagsTextField.gridx = 2;
                constraintsMaxLagsTextField.gridy = 1;
                constraintsMaxLagsTextField.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsMaxLagsTextField.insets = new java.awt.Insets(5, 10,
                        5, 0);
                getInfoTab().add(getMaxLagsTextField(),
                        constraintsMaxLagsTextField);

                java.awt.GridBagConstraints constraintsComputeButton = new java.awt.GridBagConstraints();
                constraintsComputeButton.gridx = 3;
                constraintsComputeButton.gridy = 1;
                constraintsComputeButton.insets = new java.awt.Insets(5, 0, 5,
                        0);
                getInfoTab().add(getComputeButton(), constraintsComputeButton);

                java.awt.GridBagConstraints constraintsResultField = new java.awt.GridBagConstraints();
                constraintsResultField.gridx = 1;
                constraintsResultField.gridy = 2;
                constraintsResultField.gridwidth = 3;
                constraintsResultField.fill = java.awt.GridBagConstraints.BOTH;
                constraintsResultField.weightx = 1.0;
                constraintsResultField.weighty = 1.0;
                getInfoTab().add(getResultField(), constraintsResultField);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjInfoTab;
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
                ivjJLabel.setText("Cointegration rank");
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
                ivjJLabel1.setPreferredSize(new java.awt.Dimension(93, 20));
                ivjJLabel1.setText("Endog. max lags");
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
                ivjJLabel3.setText("Endog. lags (diff)");
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
     * Return the JScrollPane1 property value.
     * 
     * @return javax.swing.JScrollPane
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JScrollPane getJScrollPane1() {
        if (ivjJScrollPane1 == null) {
            try {
                ivjJScrollPane1 = new javax.swing.JScrollPane();
                ivjJScrollPane1.setName("JScrollPane1");
                getJScrollPane1().setViewportView(getDetCheckList());
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJScrollPane1;
    }

    /**
     * Return the JTabbedPane1 property value.
     * 
     * @return javax.swing.JTabbedPane
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JTabbedPane getJTabbedPane1() {
        if (ivjJTabbedPane1 == null) {
            try {
                ivjJTabbedPane1 = new javax.swing.JTabbedPane();
                ivjJTabbedPane1.setName("JTabbedPane1");
                ivjJTabbedPane1.insertTab("Specify VEC Model", null,
                        getSpecTab(), null, 0);
                ivjJTabbedPane1.insertTab("Compute Infocriteria", null,
                        getInfoTab(), null, 1);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJTabbedPane1;
    }

    /**
     * Return the JLabel4 property value.
     * 
     * @return javax.swing.JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JLabel getLabel_ExogenousLags() {
        if (ivjLabel_ExogenousLags == null) {
            try {
                ivjLabel_ExogenousLags = new javax.swing.JLabel();
                ivjLabel_ExogenousLags.setName("Label_ExogenousLags");
                ivjLabel_ExogenousLags.setText("Exog. lags (levels)");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjLabel_ExogenousLags;
    }

    /**
     * Return the JPanel1 property value.
     * 
     * @return javax.swing.JPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JPanel getLagECPanel() {
        if (ivjLagECPanel == null) {
            try {
                GridBagConstraints gridBagConstraints = new GridBagConstraints();
                gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
                gridBagConstraints.gridy = 3;
                gridBagConstraints.weightx = 0.0D;
                gridBagConstraints.insets = new java.awt.Insets(5, 40, 0, 5);
                gridBagConstraints.gridx = 3;
                ivjLagECPanel = new javax.swing.JPanel();
                ivjLagECPanel.setName("LagECPanel");
                ivjLagECPanel.setPreferredSize(new java.awt.Dimension(0, 185));
                ivjLagECPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
                ivjLagECPanel.setLayout(new java.awt.GridBagLayout());

                java.awt.GridBagConstraints constraintsEstimationPeriodLabel = new java.awt.GridBagConstraints();
                constraintsEstimationPeriodLabel.gridx = 0;
                constraintsEstimationPeriodLabel.gridy = 0;
                constraintsEstimationPeriodLabel.gridwidth = 4;
                constraintsEstimationPeriodLabel.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsEstimationPeriodLabel.insets = new java.awt.Insets(
                        5, 5, 5, 0);
                java.awt.GridBagConstraints constraintsJLabel3 = new java.awt.GridBagConstraints();
                constraintsJLabel3.gridx = 0;
                constraintsJLabel3.gridy = 1;
                constraintsJLabel3.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel3.insets = new java.awt.Insets(5, 5, 0, 0);
                ivjLagECPanel.add(getEstimationPeriodLabel(),
                        constraintsEstimationPeriodLabel);
                getLagECPanel().add(getJLabel3(), constraintsJLabel3);

                java.awt.GridBagConstraints constraintsLabel_ExogenousLags = new java.awt.GridBagConstraints();
                constraintsLabel_ExogenousLags.gridx = 0;
                constraintsLabel_ExogenousLags.gridy = 2;
                constraintsLabel_ExogenousLags.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsLabel_ExogenousLags.insets = new java.awt.Insets(5,
                        5, 0, 0);
                getLagECPanel().add(getLabel_ExogenousLags(),
                        constraintsLabel_ExogenousLags);

                java.awt.GridBagConstraints constraintsJLabel = new java.awt.GridBagConstraints();
                constraintsJLabel.gridx = 0;
                constraintsJLabel.gridy = 4;
                constraintsJLabel.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel.insets = new java.awt.Insets(5, 5, 0, 0);
                java.awt.GridBagConstraints constraintsCointegrationRankSelector = new java.awt.GridBagConstraints();
                constraintsCointegrationRankSelector.gridx = 1;
                constraintsCointegrationRankSelector.gridy = 4;
                constraintsCointegrationRankSelector.insets = new java.awt.Insets(
                        5, 10, 0, 0);
                ivjLagECPanel.add(getJLabel(), constraintsJLabel);
                java.awt.GridBagConstraints constraintsExogenousLagsSelector = new java.awt.GridBagConstraints();
                constraintsExogenousLagsSelector.gridx = 1;
                constraintsExogenousLagsSelector.gridy = 2;
                constraintsExogenousLagsSelector.insets = new java.awt.Insets(
                        5, 10, 0, 0);
                ivjLagECPanel.add(getCointegrationRankSelector(),
                        constraintsCointegrationRankSelector);
                getLagECPanel().add(getExogenousLagsSelector(),
                        constraintsExogenousLagsSelector);

                java.awt.GridBagConstraints constraintsEndogenousLagsSelector = new java.awt.GridBagConstraints();
                constraintsEndogenousLagsSelector.gridx = 1;
                constraintsEndogenousLagsSelector.gridy = 1;
                constraintsEndogenousLagsSelector.insets = new java.awt.Insets(
                        5, 10, 0, 0);
                getLagECPanel().add(getEndogenousLagsSelector(),
                        constraintsEndogenousLagsSelector);

                java.awt.GridBagConstraints constraintsCheckBox_Intercept = new java.awt.GridBagConstraints();
                constraintsCheckBox_Intercept.gridx = 2;
                constraintsCheckBox_Intercept.gridy = 1;
                constraintsCheckBox_Intercept.gridwidth = 2;
                constraintsCheckBox_Intercept.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsCheckBox_Intercept.insets = new java.awt.Insets(5,
                        20, 0, 0);
                getLagECPanel().add(getCheckBox_Intercept(),
                        constraintsCheckBox_Intercept);

                java.awt.GridBagConstraints constraintsCheckBox_Dummies = new java.awt.GridBagConstraints();
                constraintsCheckBox_Dummies.gridx = 2;
                constraintsCheckBox_Dummies.gridy = 2;
                constraintsCheckBox_Dummies.gridwidth = 2;
                constraintsCheckBox_Dummies.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsCheckBox_Dummies.insets = new java.awt.Insets(5, 20,
                        0, 0);
                getLagECPanel().add(getCheckBox_Dummies(),
                        constraintsCheckBox_Dummies);

                java.awt.GridBagConstraints constraintsCheckBox_Trend = new java.awt.GridBagConstraints();
                constraintsCheckBox_Trend.gridx = 2;
                constraintsCheckBox_Trend.gridy = 4;
                constraintsCheckBox_Trend.gridwidth = 2;
                constraintsCheckBox_Trend.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsCheckBox_Trend.insets = new java.awt.Insets(5, 20,
                        0, 0);
                java.awt.GridBagConstraints constraintsJScrollPane1 = new java.awt.GridBagConstraints();
                constraintsJScrollPane1.gridx = 5;
                constraintsJScrollPane1.gridy = 0;
                constraintsJScrollPane1.gridheight = 6;
                constraintsJScrollPane1.fill = java.awt.GridBagConstraints.BOTH;
                constraintsJScrollPane1.weightx = 1.0;
                constraintsJScrollPane1.weighty = 1.0;
                constraintsJScrollPane1.ipadx = 177;
                constraintsJScrollPane1.ipady = 135;
                constraintsJScrollPane1.insets = new java.awt.Insets(5, 5, 5, 5);
                java.awt.GridBagConstraints constraintsClearButton = new java.awt.GridBagConstraints();
                constraintsClearButton.gridx = 2;
                constraintsClearButton.gridy = 5;
                constraintsClearButton.gridwidth = 2;
                constraintsClearButton.anchor = java.awt.GridBagConstraints.SOUTH;
                constraintsClearButton.insets = new java.awt.Insets(0, 20, 5, 0);
                ivjLagECPanel.add(getCheckBox_Trend(),
                        constraintsCheckBox_Trend);
                ivjLagECPanel.add(getJScrollPane1(), constraintsJScrollPane1);
                ivjLagECPanel.add(getClearButton(), constraintsClearButton);
                ivjLagECPanel.add(getSeasDumComboBox(), gridBagConstraints);
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjLagECPanel;
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
     * Return the SpecTab property value.
     * 
     * @return javax.swing.JPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JPanel getSpecTab() {
        if (ivjSpecTab == null) {
            try {
                ivjSpecTab = new javax.swing.JPanel();
                ivjSpecTab.setName("SpecTab");
                ivjSpecTab.setLayout(new java.awt.BorderLayout());
                getSpecTab().add(getLagECPanel(), "North");
                getSpecTab().add(getVECMEstStratPanel(), "Center");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSpecTab;
    }

    /**
     * Return the VECMEstStratPanel property value.
     * 
     * @return de.jmulti.vecm.VECMEstStratPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    VECMEstStratPanel getVECMEstStratPanel() {
        if (ivjVECMEstStratPanel == null) {
            try {
                ivjVECMEstStratPanel = new de.jmulti.vecm.VECMEstStratPanel();
                ivjVECMEstStratPanel.setName("VECMEstStratPanel");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjVECMEstStratPanel;
    }

    /**
     * Called whenever the part throws an exception.
     * 
     * @param exception
     *            Exception
     */
    void handleException(Throwable exception) {

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
        getComputeButton().addActionListener(ivjEventHandler);
        getCheckBox_Intercept().addItemListener(ivjEventHandler);
        getCheckBox_Dummies().addItemListener(ivjEventHandler);
        getCheckBox_Trend().addItemListener(ivjEventHandler);
        getClearButton().addActionListener(ivjEventHandler);
        getEndogenousLagsSelector().addPropertyChangeListener(ivjEventHandler);
        getExogenousLagsSelector().addPropertyChangeListener(ivjEventHandler);
        getDetCheckList().addPropertyChangeListener(ivjEventHandler);
        getCointegrationRankSelector().addPropertyChangeListener(
                ivjEventHandler);
    }

    /**
     * Initialize the class.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initialize() {
        try {
            // user code begin {1}
            // user code end
            setName("VECSpecModelPanel");
            setLayout(new javax.swing.BoxLayout(this,
                    javax.swing.BoxLayout.X_AXIS));
            setSize(673, 454);
            add(getJTabbedPane1(), getJTabbedPane1().getName());
            initConnections();
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}

        getDetCheckList().setSymbolName(VECMConstants.Nd_Def.name);
        // The selection state for the restricted deterministics to the EC term.
        getDetCheckList().setSymbolNameSelected(
                VECMConstants.idx_cd2ci_Def.name);

        // Listener to reset Restrictions on beta.
        SymbolListener removeRes = new SymbolListener() {
            public void valueChanged(SymbolEvent evt) {
                VECMDataManagerObs.getInstance().removeBetaRestrictions();
            }
        };
        global().get(VECMConstants.cointRank_Def).addSymbolListener(removeRes);
        global().get(VECMConstants.Nd_ec_Def).addSymbolListener(removeRes);

        // user code end
    }

    /**
     * Comment
     */
    private void lagsChanged() {
        mustRecompute = true;
        VECMDataManagerObs.getInstance()
                .computeSampleSizeAndBeginningOfSample();
        VECMDataManagerObs.getInstance().removeData();

    }

    /**
     * propertyChange method comment.
     */
    public void shown(boolean isShown) {
        if (!isShown) {
            getVECMEstStratPanel().setEstStrategyData();
            computeData();
        }
    }

    /**
     * Checks the bound for the lags fields and enables/disables the number
     * selectors accordingly.
     */
    public void selectionChanged() {

        setRecomputeData();

        checkBoundsEndogenousLags();
        checkBoundsExogenousLags();
        checkBoundsCointegrationRank();

        VECMDataManagerObs.getInstance().removeBetaRestrictions();

        VECMDataManagerObs.getInstance()
                .computeSampleSizeAndBeginningOfSample();
        getExogenousLagsSelector().setEnabled(
                !global().get(VECMConstants.x_raw_Def).isEmpty());
        boolean enabled = !global().get(VECMConstants.y_raw_Def).isEmpty();
        getEndogenousLagsSelector().setEnabled(enabled);
        getMaxLagsTextField().setEnabled(enabled);
        getComputeButton().setEnabled(enabled);

    }

    /**
     * Sets recomputation true and computes variable names.
     */
    private void setRecomputeData() {
        mustRecompute = true;

        // Set the selected deterministics in the data manager.
        VECMDataManagerObs.getInstance().setDetSel(
                getCheckBox_Intercept().isSelected(),
                getCheckBox_Trend().isSelected(),
                getCheckBox_Dummies().isSelected(),
                getSeasDumComboBox().getSelectedIndex() == 0);

    }

    /**
     * Sets the selection of predefined deterministic variables.
     * 
     * @param detSel
     *            4x1 array indicating const-trend-seasdum-centered/std
     *            (1:selected 0:not selected)
     */
    void setDetSelection(JSCNArray detSel) {
        if (detSel == null || detSel.isEmpty() || detSel.rows() < 3)
            throw new IllegalArgumentException("Invalid array argument.");
        // intercept
        getCheckBox_Intercept().setSelected(detSel.intAt(0, 0) == 1);
        // trend
        getCheckBox_Trend().setSelected(detSel.intAt(1, 0) == 1);
        // seasonal dummies
        getCheckBox_Dummies().setSelected(detSel.intAt(2, 0) == 1);

        getSeasDumComboBox().setEnabled(getCheckBox_Dummies().isSelected());
        if (detSel.rows() == 4) {
            int sel = detSel.intAt(3, 0);
            // 0 selects centered, 1 selects standard
            if (sel == 0 || sel == 1)
                getSeasDumComboBox().setSelectedIndex(sel);
        }

    }

    /**
     * Gets the current selection of predefined deterministic variables.
     * 
     * @return 4x1 array indicating const-trend-seasdum-centered/std (1:selected
     *         0:not selected)
     */
    JSCNArray getDetSelection() {
        int[] selection = new int[4];
        // intercept
        selection[0] = (getCheckBox_Intercept().isSelected()) ? 1 : 0;
        // trend
        selection[1] = (getCheckBox_Trend().isSelected()) ? 1 : 0;
        // seasonal dummies
        selection[2] = (getCheckBox_Dummies().isSelected()) ? 1 : 0;

        // 0 selects centered, 1 selects standard
        selection[3] = getSeasDumComboBox().getSelectedIndex();
        if (selection[3] < 0)
            selection[3] = 0;

        return new JSCNArray("detSelection", selection);
    }

    /**
     * Sets the deterministic variables restricted to EC term.
     * 
     * @param ecSel
     *            array indicating selection (1:selected 0:not selected)
     */
    void setECSelection(JSCNArray ecSel) {
        if (ecSel == null)
            throw new IllegalArgumentException("Invalid array argument.");
        int[] intSel = new int[ecSel.rows()];
        for (int i = 0; i < intSel.length; i++)
            intSel[i] = ecSel.intAt(i, 0);

        getDetCheckList().setSelectionStatus(intSel);
    }

    /**
     * Gets the current EC selection.
     * 
     * @return array indicating selection (1:selected 0:not selected)
     */
    JSCNArray getECSelection() {
        return new JSCNArray("ecSelection", getDetCheckList()
                .getIntSelectionStatus());
    }

    /**
     * This method initializes seasDumComboBox
     * 
     * @return javax.swing.JComboBox
     */
    private JComboBox getSeasDumComboBox() {
        if (seasDumComboBox == null) {
            seasDumComboBox = new JComboBox();
            seasDumComboBox.setPreferredSize(new java.awt.Dimension(31, 20));
            seasDumComboBox.setMinimumSize(new java.awt.Dimension(31, 20));
            seasDumComboBox.setEnabled(false);

            seasDumComboBox.addItem("centered");
            seasDumComboBox.addItem("standard");
            seasDumComboBox.setSelectedIndex(0);
            seasDumComboBox.addItemListener(new java.awt.event.ItemListener() {
                public void itemStateChanged(java.awt.event.ItemEvent e) {
                    setRecomputeData();
                }
            });
        }
        return seasDumComboBox;
    }
} // @jve:decl-index=0:visual-constraint="10,10"
