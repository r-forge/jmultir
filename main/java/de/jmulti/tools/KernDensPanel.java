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

package de.jmulti.tools;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import org.apache.log4j.Logger;

import com.jstatcom.component.NumSelector;
import com.jstatcom.component.ResultField;
import com.jstatcom.component.StdMessages;
import com.jstatcom.engine.PCall;
import com.jstatcom.model.JSCSArray;
import com.jstatcom.model.JSCTypeDef;
import com.jstatcom.model.JSCTypes;
import com.jstatcom.model.ModelPanel;
import com.jstatcom.model.Scope;
import com.jstatcom.model.SymbolTable;
import com.jstatcom.ts.TSDateRange;
import com.jstatcom.util.UData;

import de.jmulti.proc.KernelDensCall;

/**
 * A panel providing functionality to estimate kernel densities for different
 * kernels. It can be customized by setting the names of the dataobject for the
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 * 
 */
public final class KernDensPanel extends ModelPanel {
    private static final Logger log = Logger.getLogger(KernDensPanel.class);

    private Scope scope = Scope.GLOBAL;

    private JSCTypeDef dRange = new JSCTypeDef("kernel_drange", JSCTypes.DRANGE);

    private JSCTypeDef resids = new JSCTypeDef("kernel_res", JSCTypes.NARRAY);

    private JSCTypeDef nameResids = new JSCTypeDef("kernel_resnames",
            JSCTypes.SARRAY);

    private NumSelector ivjBandwidth = null;

    private JCheckBox ivjDefaultBW = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private JLabel ivjJLabel7 = null;

    private JLabel ivjJLabel8 = null;

    private JButton ivjKernelButton = null;

    private JComboBox ivjKernelChooser = null;

    private JPanel ivjKernelDensityTab = null;

    private JCheckBox ivjMultipleWindowsKernel = null;

    private JCheckBox ivjPlotDerivative = null;

    private ResultField ivjResultField = null;

    private JCheckBox ivjTextKernel = null;

    private JCheckBox ivjPlotNormal = null;

    private NumSelector ivjBoundsField = null;

    private JLabel ivjRangeLabel = null;

    private JCheckBox ivjStd = null;

    class IvjEventHandler implements java.awt.event.ActionListener,
            java.awt.event.ItemListener {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == KernDensPanel.this.getKernelButton())
                connEtoC2();
        };

        public void itemStateChanged(java.awt.event.ItemEvent e) {
            if (e.getSource() == KernDensPanel.this.getDefaultBW())
                connEtoC1(e);
        };
    };

    /**
     * KernelDensity constructor comment.
     */
    public KernDensPanel() {
        super();
        initialize();
    }

    /**
     * connEtoC1: (DefaultBW.item.itemStateChanged(java.awt.event.ItemEvent) -->
     * KernelDensity.defaultBW_ItemStateChanged()V)
     * 
     * @param arg1
     *            java.awt.event.ItemEvent
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC1(java.awt.event.ItemEvent arg1) {
        try {
            // user code begin {1}
            // user code end
            this.defaultBW_ItemStateChanged();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC2: (KernelButton.action. -->
     * KernelDensity.kernelButton_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC2() {
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
    private void defaultBW_ItemStateChanged() {
        getBandwidth().setEnabled(!getDefaultBW().isSelected());
        return;
    }

    /**
     * Return the Bandwidth property value.
     * 
     * @return com.jstatcom.model.NumberSelector
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private NumSelector getBandwidth() {
        if (ivjBandwidth == null) {
            try {
                ivjBandwidth = new NumSelector();
                ivjBandwidth.setName("Bandwidth");
                ivjBandwidth.setNumber(0.5);
                ivjBandwidth.setPrecision(1);
                ivjBandwidth.setRangeExpr("(0,1000]");
                ivjBandwidth.setPreferredSize(new java.awt.Dimension(55, 21));
                ivjBandwidth.setEnabled(false);
                ivjBandwidth.setMinimumSize(new java.awt.Dimension(55, 21));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjBandwidth;
    }

    /**
     * Return the RangeField property value.
     * 
     * @return com.jstatcom.model.NumberSelector
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private NumSelector getBoundsField() {
        if (ivjBoundsField == null) {
            try {
                ivjBoundsField = new NumSelector();
                ivjBoundsField.setName("BoundsField");
                ivjBoundsField.setPreferredSize(new java.awt.Dimension(55, 21));
                ivjBoundsField.setNumber(3.0);
                ivjBoundsField.setMinimumSize(new java.awt.Dimension(55, 21));
                ivjBoundsField.setRangeExpr("[1,100]");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjBoundsField;
    }

    /**
     * Return the DefaultBW property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getDefaultBW() {
        if (ivjDefaultBW == null) {
            try {
                ivjDefaultBW = new javax.swing.JCheckBox();
                ivjDefaultBW.setName("DefaultBW");
                ivjDefaultBW.setSelected(true);
                ivjDefaultBW.setPreferredSize(new java.awt.Dimension(119, 21));
                ivjDefaultBW.setText("Default bandwidth");
                ivjDefaultBW.setMinimumSize(new java.awt.Dimension(119, 21));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjDefaultBW;
    }

    /**
     * Return the JLabel7 property value.
     * 
     * @return javax.swing.JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JLabel getJLabel7() {
        if (ivjJLabel7 == null) {
            try {
                ivjJLabel7 = new javax.swing.JLabel();
                ivjJLabel7.setName("JLabel7");
                ivjJLabel7.setPreferredSize(new java.awt.Dimension(7, 21));
                ivjJLabel7.setText("h");
                ivjJLabel7.setMinimumSize(new java.awt.Dimension(7, 21));
                ivjJLabel7.setMaximumSize(new java.awt.Dimension(7, 21));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJLabel7;
    }

    /**
     * Return the JLabel8 property value.
     * 
     * @return javax.swing.JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JLabel getJLabel8() {
        if (ivjJLabel8 == null) {
            try {
                ivjJLabel8 = new javax.swing.JLabel();
                ivjJLabel8.setName("JLabel8");
                ivjJLabel8.setPreferredSize(new java.awt.Dimension(7, 21));
                ivjJLabel8.setText("x");
                ivjJLabel8.setMinimumSize(new java.awt.Dimension(7, 21));
                ivjJLabel8.setMaximumSize(new java.awt.Dimension(7, 21));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJLabel8;
    }

    /**
     * Return the KernelButton property value.
     * 
     * @return javax.swing.JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JButton getKernelButton() {
        if (ivjKernelButton == null) {
            try {
                ivjKernelButton = new javax.swing.JButton();
                ivjKernelButton.setName("KernelButton");
                ivjKernelButton
                        .setPreferredSize(new java.awt.Dimension(115, 27));
                ivjKernelButton.setText("Execute");
                ivjKernelButton.setMinimumSize(new java.awt.Dimension(115, 27));
                ivjKernelButton.setMaximumSize(new java.awt.Dimension(115, 27));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjKernelButton;
    }

    /**
     * Return the KernelChooser property value.
     * 
     * @return javax.swing.JComboBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JComboBox getKernelChooser() {
        if (ivjKernelChooser == null) {
            try {
                ivjKernelChooser = new javax.swing.JComboBox();
                ivjKernelChooser.setName("KernelChooser");
                ivjKernelChooser.setPreferredSize(new java.awt.Dimension(150,
                        25));
                ivjKernelChooser
                        .setMinimumSize(new java.awt.Dimension(150, 25));
                // user code begin {1}
                ivjKernelChooser.addItem("Gaussian kernel");
                ivjKernelChooser.addItem("biweight kernel");
                ivjKernelChooser.addItem("rectangular kernel");
                ivjKernelChooser.addItem("triangular kernel");
                ivjKernelChooser.addItem("Epanechnikov kernel");
                ivjKernelChooser.setSelectedIndex(0);
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjKernelChooser;
    }

    /**
     * Return the KernelDensityTab property value.
     * 
     * @return javax.swing.JPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JPanel getKernelDensityTab() {
        if (ivjKernelDensityTab == null) {
            try {
                ivjKernelDensityTab = new javax.swing.JPanel();
                ivjKernelDensityTab.setName("KernelDensityTab");
                ivjKernelDensityTab.setPreferredSize(new java.awt.Dimension(
                        566, 150));
                ivjKernelDensityTab.setBorder(new BevelBorder(
                        BevelBorder.LOWERED));
                ivjKernelDensityTab.setLayout(new java.awt.GridBagLayout());
                ivjKernelDensityTab.setMinimumSize(new java.awt.Dimension(566,
                        130));

                java.awt.GridBagConstraints constraintsKernelButton = new java.awt.GridBagConstraints();
                constraintsKernelButton.gridx = 3;
                constraintsKernelButton.gridy = 4;
                constraintsKernelButton.gridwidth = 2;
                constraintsKernelButton.anchor = java.awt.GridBagConstraints.SOUTHWEST;
                constraintsKernelButton.weightx = 1.0;
                constraintsKernelButton.weighty = 1.0;
                constraintsKernelButton.insets = new java.awt.Insets(0, 10, 5,
                        5);
                getKernelDensityTab().add(getKernelButton(),
                        constraintsKernelButton);

                java.awt.GridBagConstraints constraintsDefaultBW = new java.awt.GridBagConstraints();
                constraintsDefaultBW.gridx = 0;
                constraintsDefaultBW.gridy = 0;
                constraintsDefaultBW.anchor = java.awt.GridBagConstraints.SOUTHWEST;
                constraintsDefaultBW.insets = new java.awt.Insets(10, 5, 5, 0);
                getKernelDensityTab().add(getDefaultBW(), constraintsDefaultBW);

                java.awt.GridBagConstraints constraintsKernelChooser = new java.awt.GridBagConstraints();
                constraintsKernelChooser.gridx = 3;
                constraintsKernelChooser.gridy = 0;
                constraintsKernelChooser.gridwidth = 3;
                constraintsKernelChooser.anchor = java.awt.GridBagConstraints.SOUTHWEST;
                constraintsKernelChooser.insets = new java.awt.Insets(0, 10, 5,
                        0);
                getKernelDensityTab().add(getKernelChooser(),
                        constraintsKernelChooser);

                java.awt.GridBagConstraints constraintsBandwidth = new java.awt.GridBagConstraints();
                constraintsBandwidth.gridx = 2;
                constraintsBandwidth.gridy = 0;
                constraintsBandwidth.anchor = java.awt.GridBagConstraints.SOUTHWEST;
                constraintsBandwidth.insets = new java.awt.Insets(10, 0, 5, 0);
                getKernelDensityTab().add(getBandwidth(), constraintsBandwidth);

                java.awt.GridBagConstraints constraintsJLabel7 = new java.awt.GridBagConstraints();
                constraintsJLabel7.gridx = 1;
                constraintsJLabel7.gridy = 0;
                constraintsJLabel7.anchor = java.awt.GridBagConstraints.SOUTHWEST;
                constraintsJLabel7.insets = new java.awt.Insets(10, 0, 5, 5);
                getKernelDensityTab().add(getJLabel7(), constraintsJLabel7);

                java.awt.GridBagConstraints constraintsTextKernel = new java.awt.GridBagConstraints();
                constraintsTextKernel.gridx = 3;
                constraintsTextKernel.gridy = 1;
                constraintsTextKernel.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsTextKernel.anchor = java.awt.GridBagConstraints.WEST;
                constraintsTextKernel.weightx = 1.0;
                constraintsTextKernel.insets = new java.awt.Insets(0, 10, 5, 0);
                getKernelDensityTab().add(getTextKernel(),
                        constraintsTextKernel);

                java.awt.GridBagConstraints constraintsMultipleWindowsKernel = new java.awt.GridBagConstraints();
                constraintsMultipleWindowsKernel.gridx = 3;
                constraintsMultipleWindowsKernel.gridy = 2;
                constraintsMultipleWindowsKernel.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsMultipleWindowsKernel.anchor = java.awt.GridBagConstraints.WEST;
                constraintsMultipleWindowsKernel.weightx = 1.0;
                constraintsMultipleWindowsKernel.insets = new java.awt.Insets(
                        0, 10, 5, 0);
                getKernelDensityTab().add(getMultipleWindowsKernel(),
                        constraintsMultipleWindowsKernel);

                java.awt.GridBagConstraints constraintsRangeLabel = new java.awt.GridBagConstraints();
                constraintsRangeLabel.gridx = 0;
                constraintsRangeLabel.gridy = 1;
                constraintsRangeLabel.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsRangeLabel.anchor = java.awt.GridBagConstraints.SOUTHWEST;
                constraintsRangeLabel.insets = new java.awt.Insets(0, 5, 5, 0);
                getKernelDensityTab().add(getRangeLabel(),
                        constraintsRangeLabel);

                java.awt.GridBagConstraints constraintsJLabel8 = new java.awt.GridBagConstraints();
                constraintsJLabel8.gridx = 1;
                constraintsJLabel8.gridy = 1;
                constraintsJLabel8.anchor = java.awt.GridBagConstraints.SOUTHWEST;
                constraintsJLabel8.insets = new java.awt.Insets(0, 0, 5, 5);
                getKernelDensityTab().add(getJLabel8(), constraintsJLabel8);

                java.awt.GridBagConstraints constraintsBoundsField = new java.awt.GridBagConstraints();
                constraintsBoundsField.gridx = 2;
                constraintsBoundsField.gridy = 1;
                constraintsBoundsField.anchor = java.awt.GridBagConstraints.SOUTHWEST;
                constraintsBoundsField.insets = new java.awt.Insets(0, 0, 5, 0);
                getKernelDensityTab().add(getBoundsField(),
                        constraintsBoundsField);

                java.awt.GridBagConstraints constraintsPlotDerivative = new java.awt.GridBagConstraints();
                constraintsPlotDerivative.gridx = 0;
                constraintsPlotDerivative.gridy = 3;
                constraintsPlotDerivative.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsPlotDerivative.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsPlotDerivative.insets = new java.awt.Insets(0, 5, 5,
                        0);
                getKernelDensityTab().add(getPlotDerivative(),
                        constraintsPlotDerivative);

                java.awt.GridBagConstraints constraintsStd = new java.awt.GridBagConstraints();
                constraintsStd.gridx = 0;
                constraintsStd.gridy = 2;
                constraintsStd.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsStd.anchor = java.awt.GridBagConstraints.WEST;
                constraintsStd.insets = new java.awt.Insets(0, 5, 5, 0);
                getKernelDensityTab().add(getStd(), constraintsStd);

                java.awt.GridBagConstraints constraintsPlotNormal = new java.awt.GridBagConstraints();
                constraintsPlotNormal.gridx = 0;
                constraintsPlotNormal.gridy = 4;
                constraintsPlotNormal.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsPlotNormal.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsPlotNormal.insets = new java.awt.Insets(0, 5, 5, 0);
                getKernelDensityTab().add(getPlotNormal(),
                        constraintsPlotNormal);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjKernelDensityTab;
    }

    /**
     * Return the MultipleWindowsKernel property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getMultipleWindowsKernel() {
        if (ivjMultipleWindowsKernel == null) {
            try {
                ivjMultipleWindowsKernel = new javax.swing.JCheckBox();
                ivjMultipleWindowsKernel.setName("MultipleWindowsKernel");
                ivjMultipleWindowsKernel
                        .setPreferredSize(new java.awt.Dimension(182, 21));
                ivjMultipleWindowsKernel.setText("One diagram for each graph");
                ivjMultipleWindowsKernel.setMinimumSize(new java.awt.Dimension(
                        182, 21));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjMultipleWindowsKernel;
    }

    /**
     * Gets the variable name for names of residuals.
     * 
     * @return variable name of data object
     */
    public String getNameResids() {
        if (nameResids == null)
            return null;

        return nameResids.name;
    }

    /**
     * Gets the variable name for date range.
     * 
     * @return variable name of data object
     */
    public String getDRange() {
        if (dRange == null)
            return null;
        return dRange.name;
    }

    /**
     * Return the PlotDerivative property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getPlotDerivative() {
        if (ivjPlotDerivative == null) {
            try {
                ivjPlotDerivative = new javax.swing.JCheckBox();
                ivjPlotDerivative.setName("PlotDerivative");
                ivjPlotDerivative.setPreferredSize(new java.awt.Dimension(97,
                        21));
                ivjPlotDerivative.setText("Plot first derivative");
                ivjPlotDerivative
                        .setMinimumSize(new java.awt.Dimension(97, 21));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjPlotDerivative;
    }

    /**
     * Return the PlotNormal property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getPlotNormal() {
        if (ivjPlotNormal == null) {
            try {
                ivjPlotNormal = new javax.swing.JCheckBox();
                ivjPlotNormal.setName("PlotNormal");
                ivjPlotNormal.setPreferredSize(new java.awt.Dimension(97, 21));
                ivjPlotNormal.setText("Plot normal distribution");
                ivjPlotNormal.setMinimumSize(new java.awt.Dimension(97, 21));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjPlotNormal;
    }

    /**
     * Return the Range1 property value.
     * 
     * @return javax.swing.JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JLabel getRangeLabel() {
        if (ivjRangeLabel == null) {
            try {
                ivjRangeLabel = new javax.swing.JLabel();
                ivjRangeLabel.setName("RangeLabel");
                ivjRangeLabel.setPreferredSize(new java.awt.Dimension(178, 21));
                ivjRangeLabel.setText("Estimate in range [-x,+x]");
                ivjRangeLabel.setMinimumSize(new java.awt.Dimension(178, 21));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjRangeLabel;
    }

    /**
     * Gets the variable name for residuals.
     * 
     * @return variable name of data object
     */
    public String getResids() {
        if (resids == null)
            return null;

        return resids.name;
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
                        .setPreferredSize(new java.awt.Dimension(200, 100));
                ivjResultField.setBorder(new BevelBorder(BevelBorder.LOWERED));
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
     * Return the StandardizeForKernel property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getStd() {
        if (ivjStd == null) {
            try {
                ivjStd = new javax.swing.JCheckBox();
                ivjStd.setName("Std");
                ivjStd.setSelected(true);
                ivjStd.setPreferredSize(new java.awt.Dimension(182, 21));
                ivjStd.setText("Standardize series");
                ivjStd.setMinimumSize(new java.awt.Dimension(182, 21));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjStd;
    }

    /**
     * Return the TextKernel property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getTextKernel() {
        if (ivjTextKernel == null) {
            try {
                ivjTextKernel = new javax.swing.JCheckBox();
                ivjTextKernel.setName("TextKernel");
                ivjTextKernel.setPreferredSize(new java.awt.Dimension(182, 21));
                ivjTextKernel.setText("Output as text (no graph)");
                ivjTextKernel.setMinimumSize(new java.awt.Dimension(182, 21));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjTextKernel;
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
        getDefaultBW().addItemListener(ivjEventHandler);
        getKernelButton().addActionListener(ivjEventHandler);
    }

    /**
     * Initialize the class.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initialize() {
        try {
            // user code begin {1}
            // user code end
            setName("KernelDensity");
            setLayout(new java.awt.BorderLayout());
            setSize(575, 320);
            add(getResultField(), "Center");
            add(getKernelDensityTab(), "North");
            initConnections();
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}
        // user code end
    }

    /**
     * Makes the call to kernel density estimation.
     */
    private void execute_ActionEvents() {

        SymbolTable sTable = scope.getSymbolTable(this);

        if (sTable.get(resids).isEmpty()) {
            StdMessages
                    .infoNothingSelected("No series available for kernel density estimation.");
            return;
        }

        TSDateRange range = sTable.get(dRange).getJSCDRange().getTSDateRange();

        JSCSArray nam = sTable.get(nameResids).getJSCSArray();
        if (getStd().isSelected())
            nam = UData.appendSuffix(nam, "_std", "datNames");

        PCall job = new KernelDensCall(sTable.get(resids).getJSCNArray(), nam,
                range, getKernelChooser().getSelectedIndex(), getBoundsField()
                        .getIntNumber(), getDefaultBW().isSelected(),
                getBandwidth().getNumber(), getStd().isSelected(),
                getPlotNormal().isSelected(), getTextKernel().isSelected(),
                getMultipleWindowsKernel().isSelected(), getPlotDerivative()
                        .isSelected());

        job.setSymbolTable(local());
        job.setOutHolder(getResultField());
        job.execute();

    }

    /**
     * Sets the variable name for the names of residuals.
     * 
     * @param newNameResids
     */
    public void setNameResids(String newNameResids) {
        if (newNameResids == null)
            nameResids = null;
        else
            nameResids = new JSCTypeDef(newNameResids, JSCTypes.SARRAY);
    }

    /**
     * Sets the variable name for the date range.
     * 
     * @param newDRange
     */
    public void setDRange(String newDRange) {
        if (newDRange == null)
            dRange = null;
        else
            dRange = new JSCTypeDef(newDRange, JSCTypes.DRANGE);
    }

    /**
     * Sets the variable name for the residuals.
     * 
     * @param newResids
     */
    public void setResids(String newResids) {
        if (newResids == null)
            resids = null;
        else
            resids = new JSCTypeDef(newResids, JSCTypes.NARRAY);

    }

    /**
     * @return
     */
    public Scope getScope() {
        return scope;
    }

    /**
     * @param scope
     */
    public void setScope(Scope scope) {
        if (scope == null)
            throw new IllegalArgumentException("Argument was null.");
        this.scope = scope;
    }

}