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

import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.BevelBorder;

import org.apache.log4j.Logger;

import com.jstatcom.component.NumSelector;
import com.jstatcom.component.ResultField;
import com.jstatcom.component.TopFrameReference;
import com.jstatcom.engine.PCall;
import com.jstatcom.engine.PCallAdapter;
import com.jstatcom.model.JSCConstants;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.JSCTypeDef;
import com.jstatcom.model.JSCTypes;
import com.jstatcom.model.ModelPanel;
import com.jstatcom.model.Scope;
import com.jstatcom.model.SymbolEvent;
import com.jstatcom.model.SymbolListener;
import com.jstatcom.model.SymbolTable;
import com.jstatcom.table.JSCCellRendererTypes;
import com.jstatcom.table.JSCDataTableScrollPane;
import com.jstatcom.table.JSCMouseListenerTypes;
import com.jstatcom.table.JSCNArrayTable;
import com.jstatcom.ts.TSDateRange;
import com.jstatcom.util.UMatrix;

import de.jmulti.proc.SVECEstCall;
import de.jmulti.tools.ModelTypes;
import de.jmulti.tools.SVAROptSetDialog;

/**
 * Panel for specifying and performing the SVEC estimation.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
public final class SVECModelPanel extends ModelPanel {
    private static final Logger log = Logger.getLogger(SVECModelPanel.class);

    // Checks whether reset of matrizes is necessary.
    private boolean isReset = true;

    // Dialog for optimization settings.
    private SVAROptSetDialog optSetDialog = null;

    // Variable names to be used locally.
    public static final JSCTypeDef B_MATRIX = new JSCTypeDef("SVEC_B_MATRIX",
            JSCTypes.NARRAY, "B restriction matrix for SVEC model");

    public static final JSCTypeDef XI_B_MATRIX = new JSCTypeDef(
            "SVEC_XI_B_MATRIX", JSCTypes.NARRAY,
            "XI_B restriction matrix for SVEC model");

    private static final String OUT_FILE_EST = "svec_model_est.out";

    private int K = 0;

    private JTabbedPane ivjJTabbedPane = null;

    private ResultField ivjResultField = null;

    private JPanel ivjSpecTab = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private JButton ivjOptSetButton = null;

    private JLabel ivjBLabel = null;

    private JSCNArrayTable ivjDataTableB = null;

    private JSCDataTableScrollPane ivjDataTableScrollPaneB = null;

    private JSCDataTableScrollPane ivjDataTableScrollPaneXi = null;

    private JSCNArrayTable ivjDataTableXi = null;

    private JLabel ivjXiLabel = null;

    private NumSelector ivjBootRepSelector = null;

    private JButton ivjBootstrapButton = null;

    private JButton ivjEstimateButton = null;

    private JLabel ivjJLabel2 = null;

    private JCheckBox ivjSeedCheck = null;

    private NumSelector ivjSeedSelector = null;

    class IvjEventHandler implements java.awt.event.ActionListener,
            java.awt.event.ItemListener {

        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == SVECModelPanel.this.getOptSetButton())
                connEtoC1();
            if (e.getSource() == SVECModelPanel.this.getEstimateButton())
                connEtoC2();
            if (e.getSource() == SVECModelPanel.this.getBootstrapButton())
                connEtoC3();
        };

        public void itemStateChanged(java.awt.event.ItemEvent e) {
            if (e.getSource() == SVECModelPanel.this.getSeedCheck())
                connPtoP1SetTarget();
        };
    };

    /**
     * SVARPanel constructor comment.
     */
    public SVECModelPanel() {
        super();
        initialize();
    }

    /**
     * Comment
     */
    private void bootstrapButton_ActionEvents() {
        estimate(true);
    }

    /**
     * connEtoC1: (OptSetButton.action. -->
     * SVARPanel.optSetButton_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC1() {
        try {
            // user code begin {1}
            // user code end
            this.optSetButton_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC2: (EstimateButton.action. -->
     * SVECModelPanel.estimateButton_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC2() {
        try {
            // user code begin {1}
            // user code end
            this.estimateButton_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC3: (BootstrapButton.action. -->
     * SVECModelPanel.bootstrapButton_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC3() {
        try {
            // user code begin {1}
            // user code end
            this.bootstrapButton_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connPtoP1SetTarget: (SeedCheck.selected <-->SeedSelector.enabled)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connPtoP1SetTarget() {
        /* Set the target from the source */
        try {
            getSeedSelector().setEnabled(getSeedCheck().isSelected());
            // user code begin {1}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * Initiates the estimation with bootstrap of std.errors or without it.
     */
    private void estimate(boolean useBootStrap) {

        JSCNArray mSigmaU = global().get(VECMConstants.cv_u_hat_Def)
                .getJSCNArray();
        TSDateRange range = global().get(VECMConstants.T1_Def).getJSCDRange()
                .getTSDateRange();
        int T = range.numOfObs();
        double eps1_tol = getOptSetDialog().getRelParamChange();
        double eps2_tol = getOptSetDialog().getRelLikChange();
        int maxIterations = getOptSetDialog().getMaxIterNum();
        int iStartValueMethod = getOptSetDialog().getStartMethod();
        double fixStart = getOptSetDialog().getFixedValue();
        boolean iCorr = getOptSetDialog().getUseDecomp();
        int iMaxRetries = getOptSetDialog().getMaxRetries();
        int seed = getSeedCheck().isSelected() ? getSeedSelector()
                .getIntNumber() : 0;
        int bootrep = getBootRepSelector().getIntNumber();

        global().get(SVECConstants.resB_Def).setJSCData(
                local().get(B_MATRIX).getJSCData());
        global().get(SVECConstants.resC_Def).setJSCData(
                local().get(XI_B_MATRIX).getJSCData());

        String outFile = JSCConstants.getSystemTemp() + File.separator
                + OUT_FILE_EST;
        getResultField().clear();
        PCall job = new SVECEstCall(global(), global().get(
                VECMConstants.alpha_Def).getJSCNArray(), global().get(
                VECMConstants.beta_Def).getJSCNArray(), global().get(
                VECMConstants.beta_d_Def).getJSCNArray(), global().get(
                VECMConstants.G_Def).getJSCNArray(), mSigmaU, T, local().get(
                B_MATRIX).getJSCNArray(), local().get(XI_B_MATRIX)
                .getJSCNArray(), eps1_tol, eps2_tol, fixStart, maxIterations,
                iStartValueMethod, iCorr, iMaxRetries, outFile, useBootStrap,
                bootrep, seed);

        job.setOutHolder(getResultField());
        job.setSymbolTable(global());
        job.addPCallListener(new PCallAdapter() {

            public void success() {
                // set result tab
                getJTabbedPane().setSelectedIndex(1);
            }
        });

        job.execute();
    }

    /**
     * Comment
     */
    private void estimateButton_ActionEvents() {
        estimate(false);
    }

    /**
     * Return the GammaLabel property value.
     * 
     * @return javax.swing.JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JLabel getBLabel() {
        if (ivjBLabel == null) {
            try {
                ivjBLabel = new javax.swing.JLabel();
                ivjBLabel.setName("BLabel");
                ivjBLabel.setPreferredSize(new java.awt.Dimension(58, 22));
                ivjBLabel.setText("B matrix");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjBLabel;
    }

    private NumSelector getBootRepSelector() {
        if (ivjBootRepSelector == null) {
            try {
                ivjBootRepSelector = new NumSelector();
                ivjBootRepSelector.setName("BootRepSelector");
                ivjBootRepSelector.setNumber(100.0);
                ivjBootRepSelector.setRangeExpr("[50,Infinity)");
                ivjBootRepSelector.setPreferredSize(new java.awt.Dimension(100,
                        20));
                ivjBootRepSelector
                        .setAlignmentX(java.awt.Component.RIGHT_ALIGNMENT);
                ivjBootRepSelector.setMinimumSize(new java.awt.Dimension(100,
                        20));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjBootRepSelector;
    }

    /**
     * Return the BootstrapButton property value.
     * 
     * @return javax.swing.JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JButton getBootstrapButton() {
        if (ivjBootstrapButton == null) {
            try {
                ivjBootstrapButton = new javax.swing.JButton();
                ivjBootstrapButton.setName("BootstrapButton");
                ivjBootstrapButton
                        .setToolTipText("Estimates with bootrapping std. errors.");
                ivjBootstrapButton.setText("Estimate with Boot. Std. Err.");
                ivjBootstrapButton.setPreferredSize(new java.awt.Dimension(210,
                        25));
                ivjBootstrapButton.setMinimumSize(new java.awt.Dimension(180,
                        25));
                ivjBootstrapButton.setMargin(new java.awt.Insets(2, 5, 2, 5));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjBootstrapButton;
    }

    private JSCNArrayTable getDataTableB() {
        if (ivjDataTableB == null) {
            try {
                ivjDataTableB = new JSCNArrayTable();
                ivjDataTableB.setName("DataTableB");
                getDataTableScrollPaneB().setColumnHeaderView(
                        ivjDataTableB.getTableHeader());
                ivjDataTableB.setPrecision(4);
                ivjDataTableB.setSymbolScope(Scope.LOCAL);
                ivjDataTableB
                        .setCellRenderer(JSCCellRendererTypes.DIAG_01M1NEGINF);
                ivjDataTableB.setTablePopup(null);
                ivjDataTableB
                        .setMouseListener(JSCMouseListenerTypes.DIAG_0NEGINF);
                ivjDataTableB.setBounds(0, 0, 147, 107);
                ivjDataTableB.setEditable(false);
                // user code begin {1}
                ivjDataTableB.setSymbolName(B_MATRIX.name);
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjDataTableB;
    }

    private JSCDataTableScrollPane getDataTableScrollPaneB() {
        if (ivjDataTableScrollPaneB == null) {
            try {
                ivjDataTableScrollPaneB = new JSCDataTableScrollPane();
                ivjDataTableScrollPaneB.setName("DataTableScrollPaneB");
                ivjDataTableScrollPaneB.setColumnHeaderShowing(true);
                ivjDataTableScrollPaneB.setMinimumVisibleColumns(5);
                ivjDataTableScrollPaneB.setMinimumVisibleRows(5);
                getDataTableScrollPaneB().setViewportView(getDataTableB());
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjDataTableScrollPaneB;
    }

    private JSCDataTableScrollPane getDataTableScrollPaneXi() {
        if (ivjDataTableScrollPaneXi == null) {
            try {
                ivjDataTableScrollPaneXi = new JSCDataTableScrollPane();
                ivjDataTableScrollPaneXi.setName("DataTableScrollPaneXi");
                ivjDataTableScrollPaneXi.setColumnHeaderShowing(true);
                ivjDataTableScrollPaneXi.setMinimumVisibleColumns(5);
                ivjDataTableScrollPaneXi.setMinimumVisibleRows(5);
                getDataTableScrollPaneXi().setViewportView(getDataTableXi());
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjDataTableScrollPaneXi;
    }

    private JSCNArrayTable getDataTableXi() {
        if (ivjDataTableXi == null) {
            try {
                ivjDataTableXi = new JSCNArrayTable();
                ivjDataTableXi.setName("DataTableXi");
                getDataTableScrollPaneXi().setColumnHeaderView(
                        ivjDataTableXi.getTableHeader());
                ivjDataTableXi.setPrecision(4);
                ivjDataTableXi.setSymbolScope(Scope.LOCAL);
                ivjDataTableXi
                        .setCellRenderer(JSCCellRendererTypes.SUBSET_01M1NEGINF);
                ivjDataTableXi.setTablePopup(null);
                ivjDataTableXi
                        .setMouseListener(JSCMouseListenerTypes.SUBSET_0NEGINF);
                ivjDataTableXi.setBounds(0, 0, 146, 106);
                ivjDataTableXi.setEditable(false);
                // user code begin {1}
                ivjDataTableXi.setSymbolName(XI_B_MATRIX.name);
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjDataTableXi;
    }

    /**
     * Return the EstimateButton property value.
     * 
     * @return javax.swing.JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JButton getEstimateButton() {
        if (ivjEstimateButton == null) {
            try {
                ivjEstimateButton = new javax.swing.JButton();
                ivjEstimateButton.setName("EstimateButton");
                ivjEstimateButton
                        .setToolTipText("Estimates without bootrapping std. errors.");
                ivjEstimateButton.setPreferredSize(new java.awt.Dimension(210,
                        25));
                ivjEstimateButton.setText("Point Estimates Only");
                ivjEstimateButton
                        .setMinimumSize(new java.awt.Dimension(180, 25));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjEstimateButton;
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
                ivjJLabel2.setPreferredSize(new java.awt.Dimension(190, 20));
                ivjJLabel2.setText("Number of bootstrap replications");
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
     * Return the JTabbedPane property value.
     * 
     * @return javax.swing.JTabbedPane
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JTabbedPane getJTabbedPane() {
        if (ivjJTabbedPane == null) {
            try {
                ivjJTabbedPane = new javax.swing.JTabbedPane();
                ivjJTabbedPane.setName("JTabbedPane");
                ivjJTabbedPane
                        .setPreferredSize(new java.awt.Dimension(300, 176));
                ivjJTabbedPane.setMinimumSize(new java.awt.Dimension(300, 176));
                ivjJTabbedPane.insertTab("Specify/Estimate SVEC Model", null,
                        getSpecTab(), null, 0);
                ivjJTabbedPane.insertTab("Output (save/print)", null,
                        getResultField(), null, 1);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJTabbedPane;
    }

    /**
     * Return the OptSetButton property value.
     * 
     * @return javax.swing.JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JButton getOptSetButton() {
        if (ivjOptSetButton == null) {
            try {
                ivjOptSetButton = new javax.swing.JButton();
                ivjOptSetButton.setName("OptSetButton");
                ivjOptSetButton
                        .setPreferredSize(new java.awt.Dimension(210, 25));
                ivjOptSetButton.setText("Optimization Settings");
                ivjOptSetButton.setMinimumSize(new java.awt.Dimension(180, 25));
                ivjOptSetButton.setMaximumSize(new java.awt.Dimension(180, 25));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjOptSetButton;
    }

    /**
     * Initializer for the dialog for optimization settings.
     * 
     * @return SVAROptSetDialog
     */
    private SVAROptSetDialog getOptSetDialog() {
        if (optSetDialog == null) {
            optSetDialog = new SVAROptSetDialog(TopFrameReference
                    .getTopFrameRef(), true);
            optSetDialog.setTitle("Optimization Settings for SVEC Estimation");
            optSetDialog.setModelType(ModelTypes.SVEC);
        }
        optSetDialog.setLocationRelativeTo(TopFrameReference.getTopFrameRef());
        return optSetDialog;
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
     * Return the SeedCheck property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getSeedCheck() {
        if (ivjSeedCheck == null) {
            try {
                ivjSeedCheck = new javax.swing.JCheckBox();
                ivjSeedCheck.setName("SeedCheck");
                ivjSeedCheck.setText("Use this seed (0 < s < 2,147,483,647) ");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSeedCheck;
    }

    private NumSelector getSeedSelector() {
        if (ivjSeedSelector == null) {
            try {
                ivjSeedSelector = new NumSelector();
                ivjSeedSelector.setName("SeedSelector");
                ivjSeedSelector.setNumber(1.0);
                ivjSeedSelector.setRangeExpr("(0,2.147483647E9)");
                ivjSeedSelector
                        .setPreferredSize(new java.awt.Dimension(100, 20));
                ivjSeedSelector
                        .setAlignmentX(java.awt.Component.RIGHT_ALIGNMENT);
                ivjSeedSelector.setMinimumSize(new java.awt.Dimension(100, 20));
                ivjSeedSelector.setEnabled(false);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSeedSelector;
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
                ivjSpecTab.setBorder(new BevelBorder(BevelBorder.LOWERED));
                ivjSpecTab.setLayout(new java.awt.GridBagLayout());

                java.awt.GridBagConstraints constraintsOptSetButton = new java.awt.GridBagConstraints();
                constraintsOptSetButton.gridx = 2;
                constraintsOptSetButton.gridy = 2;
                constraintsOptSetButton.gridwidth = 2;
                constraintsOptSetButton.gridheight = 3;
                constraintsOptSetButton.insets = new java.awt.Insets(10, 10, 0,
                        0);
                getSpecTab().add(getOptSetButton(), constraintsOptSetButton);

                java.awt.GridBagConstraints constraintsBLabel = new java.awt.GridBagConstraints();
                constraintsBLabel.gridx = 0;
                constraintsBLabel.gridy = 0;
                constraintsBLabel.gridwidth = 2;
                constraintsBLabel.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsBLabel.insets = new java.awt.Insets(10, 10, 0, 0);
                getSpecTab().add(getBLabel(), constraintsBLabel);

                java.awt.GridBagConstraints constraintsXiLabel = new java.awt.GridBagConstraints();
                constraintsXiLabel.gridx = 2;
                constraintsXiLabel.gridy = 0;
                constraintsXiLabel.gridwidth = 2;
                constraintsXiLabel.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsXiLabel.insets = new java.awt.Insets(10, 5, 0, 0);
                getSpecTab().add(getXiLabel(), constraintsXiLabel);

                java.awt.GridBagConstraints constraintsDataTableScrollPaneB = new java.awt.GridBagConstraints();
                constraintsDataTableScrollPaneB.gridx = 0;
                constraintsDataTableScrollPaneB.gridy = 1;
                constraintsDataTableScrollPaneB.gridwidth = 2;
                constraintsDataTableScrollPaneB.fill = java.awt.GridBagConstraints.BOTH;
                constraintsDataTableScrollPaneB.weightx = 1.0;
                constraintsDataTableScrollPaneB.weighty = 1.0;
                constraintsDataTableScrollPaneB.insets = new java.awt.Insets(5,
                        10, 0, 0);
                getSpecTab().add(getDataTableScrollPaneB(),
                        constraintsDataTableScrollPaneB);

                java.awt.GridBagConstraints constraintsDataTableScrollPaneXi = new java.awt.GridBagConstraints();
                constraintsDataTableScrollPaneXi.gridx = 2;
                constraintsDataTableScrollPaneXi.gridy = 1;
                constraintsDataTableScrollPaneXi.gridwidth = 2;
                constraintsDataTableScrollPaneXi.fill = java.awt.GridBagConstraints.BOTH;
                constraintsDataTableScrollPaneXi.weightx = 1.0;
                constraintsDataTableScrollPaneXi.weighty = 1.0;
                constraintsDataTableScrollPaneXi.insets = new java.awt.Insets(
                        5, 5, 0, 10);
                getSpecTab().add(getDataTableScrollPaneXi(),
                        constraintsDataTableScrollPaneXi);

                java.awt.GridBagConstraints constraintsJLabel2 = new java.awt.GridBagConstraints();
                constraintsJLabel2.gridx = 0;
                constraintsJLabel2.gridy = 5;
                constraintsJLabel2.anchor = java.awt.GridBagConstraints.WEST;
                constraintsJLabel2.insets = new java.awt.Insets(5, 10, 0, 0);
                getSpecTab().add(getJLabel2(), constraintsJLabel2);

                java.awt.GridBagConstraints constraintsSeedCheck = new java.awt.GridBagConstraints();
                constraintsSeedCheck.gridx = 0;
                constraintsSeedCheck.gridy = 6;
                constraintsSeedCheck.anchor = java.awt.GridBagConstraints.WEST;
                constraintsSeedCheck.insets = new java.awt.Insets(5, 10, 5, 0);
                getSpecTab().add(getSeedCheck(), constraintsSeedCheck);

                java.awt.GridBagConstraints constraintsBootRepSelector = new java.awt.GridBagConstraints();
                constraintsBootRepSelector.gridx = 1;
                constraintsBootRepSelector.gridy = 5;
                constraintsBootRepSelector.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsBootRepSelector.anchor = java.awt.GridBagConstraints.WEST;
                constraintsBootRepSelector.insets = new java.awt.Insets(5, 5,
                        0, 0);
                getSpecTab().add(getBootRepSelector(),
                        constraintsBootRepSelector);

                java.awt.GridBagConstraints constraintsSeedSelector = new java.awt.GridBagConstraints();
                constraintsSeedSelector.gridx = 1;
                constraintsSeedSelector.gridy = 6;
                constraintsSeedSelector.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsSeedSelector.anchor = java.awt.GridBagConstraints.WEST;
                constraintsSeedSelector.insets = new java.awt.Insets(5, 5, 5, 0);
                getSpecTab().add(getSeedSelector(), constraintsSeedSelector);

                java.awt.GridBagConstraints constraintsEstimateButton = new java.awt.GridBagConstraints();
                constraintsEstimateButton.gridx = 3;
                constraintsEstimateButton.gridy = 5;
                constraintsEstimateButton.insets = new java.awt.Insets(5, 10,
                        0, 0);
                getSpecTab()
                        .add(getEstimateButton(), constraintsEstimateButton);

                java.awt.GridBagConstraints constraintsBootstrapButton = new java.awt.GridBagConstraints();
                constraintsBootstrapButton.gridx = 3;
                constraintsBootstrapButton.gridy = 6;
                constraintsBootstrapButton.insets = new java.awt.Insets(5, 10,
                        5, 0);
                getSpecTab().add(getBootstrapButton(),
                        constraintsBootstrapButton);
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
     * Return the RLabel property value.
     * 
     * @return javax.swing.JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JLabel getXiLabel() {
        if (ivjXiLabel == null) {
            try {
                ivjXiLabel = new javax.swing.JLabel();
                ivjXiLabel.setName("XiLabel");
                ivjXiLabel.setPreferredSize(new java.awt.Dimension(58, 22));
                ivjXiLabel.setText("Identified long-run impact matrix");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjXiLabel;
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
        getOptSetButton().addActionListener(ivjEventHandler);
        getEstimateButton().addActionListener(ivjEventHandler);
        getBootstrapButton().addActionListener(ivjEventHandler);
        getSeedCheck().addItemListener(ivjEventHandler);
        connPtoP1SetTarget();
    }

    /**
     * Initialize the class.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initialize() {
        try {
            // user code begin {1}
            // user code end
            setName("SVECModelPanel");
            setLayout(new javax.swing.BoxLayout(this,
                    javax.swing.BoxLayout.X_AXIS));
            setSize(652, 411);
            add(getJTabbedPane(), getJTabbedPane().getName());
            initConnections();
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}
        getDataTableScrollPaneB().setColumnHeaderSymbolName(
                VECMConstants.Ny_Def.name);
        getDataTableScrollPaneXi().setColumnHeaderSymbolName(
                VECMConstants.Ny_Def.name);

        // Installs a listener that enables resetting of
        // B and Xi_B matrizes when component is shown.
        global().get(VECMConstants.Ny_Def).addSymbolListener(
                new SymbolListener() {

                    public void valueChanged(SymbolEvent evt) {
                        isReset = true;
                        getJTabbedPane().setSelectedIndex(0);
                    }
                });

        // user code end
    }

    /**
     * Displays dialog to specify optimization settings.
     */
    private void optSetButton_ActionEvents() {
        getOptSetDialog().setVisible(true);

    }

    /**
     * Sets matrizes to default values.
     */
    private void setDefaults() {
        double[][] defMat = UMatrix.multiply(UMatrix.ones(K, K),
                Double.NEGATIVE_INFINITY);
        double[][] defMat1 = UMatrix
                .lowerTringular(Double.NEGATIVE_INFINITY, K);

        local().get(B_MATRIX).setJSCData(new JSCNArray("B", defMat1));
        local().get(XI_B_MATRIX).setJSCData(new JSCNArray("XI_B", defMat));

    }

    public void shown(boolean isShown) {
        if (isShown && isReset) {
            K = global().get(VECMConstants.Ny_Def).getJSCSArray().rows();

            if (K != getDataTableXi().getJSCNArray().rows()) {
                setDefaults();
                isReset = false;
            }

        }

    }

    /**
     * Sets the symbol table for the SVEC model panel. Used by project
     * management.
     * 
     * @param table
     *            symbol table
     */
    void setSVECModelPanelTable(SymbolTable table) {
        if (table == null)
            throw new IllegalArgumentException("Argument was null.");
        local().setSymbolTable(table);
    }
}