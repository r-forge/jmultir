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

import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.border.BevelBorder;

import org.apache.log4j.Logger;

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
import com.jstatcom.table.JSCCellRendererTypes;
import com.jstatcom.table.JSCDataTableScrollPane;
import com.jstatcom.table.JSCMouseListenerTypes;
import com.jstatcom.table.JSCNArrayTable;
import com.jstatcom.ts.TSDateRange;
import com.jstatcom.util.UMatrix;

import de.jmulti.proc.SVAREstABCall;
import de.jmulti.tools.SVAROptSetDialog;

/**
 * Panel for specifying and performing the SVAR estimation for an AB model.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
final class SVARABModelPanel extends ModelPanel {
    private static final Logger log = Logger.getLogger(SVARABModelPanel.class);

    // Dialog for optimization settings.
    private SVAROptSetDialog optSetDialog = null;

    // Variable names to be used locally.
    public static final JSCTypeDef A_MATRIX = new JSCTypeDef("SVAR_A_MATRIX",
            JSCTypes.NARRAY, "restrictions for SVAR A matrix");

    public static final JSCTypeDef B_MATRIX = new JSCTypeDef("SVAR_B_MATRIX",
            JSCTypes.NARRAY, "restrictions for SVAR B matrix");

    private static final String OUT_FILE_EST = "svar_ab_model_est.out";

    private int K = 0;

    private JTabbedPane ivjJTabbedPane = null;

    private ResultField ivjResultField = null;

    private JPanel ivjSpecTab = null;

    private JRadioButton ivjCRadioButton = null;

    private JRadioButton ivjGeneralRadioButton = null;

    private JRadioButton ivjKRadioButton = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private JButton ivjOptSetButton = null;

    private JButton ivjExecuteButton = null;

    private JLabel ivjRLabel = null;

    private JCheckBox ivjGammaEditCheck = null;

    private JCheckBox ivjREditCheck = null;

    private JLabel ivjALabel = null;

    private JSCNArrayTable ivjDataTableA = null;

    private JSCNArrayTable ivjDataTableB = null;

    private JSCDataTableScrollPane ivjDataTableScrollPaneA = null;

    private JSCDataTableScrollPane ivjDataTableScrollPaneB = null;

    class IvjEventHandler implements java.awt.event.ActionListener,
            java.awt.event.ItemListener {

        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == SVARABModelPanel.this.getOptSetButton())
                connEtoC1();
            if (e.getSource() == SVARABModelPanel.this.getGammaEditCheck())
                connEtoC3();
            if (e.getSource() == SVARABModelPanel.this.getREditCheck())
                connEtoC4();
            if (e.getSource() == SVARABModelPanel.this.getExecuteButton())
                connEtoC2();
        };

        public void itemStateChanged(java.awt.event.ItemEvent e) {
            if (e.getSource() == SVARABModelPanel.this.getGeneralRadioButton())
                connEtoC5(e);
            if (e.getSource() == SVARABModelPanel.this.getCRadioButton())
                connEtoC6(e);
            if (e.getSource() == SVARABModelPanel.this.getKRadioButton())
                connEtoC7(e);
        };
    };

    /**
     * SVARPanel constructor comment.
     */
    public SVARABModelPanel() {
        super();
        initialize();
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
     * connEtoC2: (ExecuteButton.action. -->
     * SVARPanel.executeButton_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC2() {
        try {
            // user code begin {1}
            // user code end
            this.execute();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC3: (GammaEditCheck.action. -->
     * SVAR_ABModelPanel.gammaEditCheck_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC3() {
        try {
            // user code begin {1}
            // user code end
            this.gammaEditCheck_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC4: (REditCheck.action. -->
     * SVAR_ABModelPanel.rEditCheck_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC4() {
        try {
            // user code begin {1}
            // user code end
            this.rEditCheck_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC5:
     * (GeneralRadioButton.item.itemStateChanged(java.awt.event.ItemEvent) -->
     * SVAR_ABModelPanel.generalRadioButton_ItemStateChanged()V)
     * 
     * @param arg1
     *            java.awt.event.ItemEvent
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC5(java.awt.event.ItemEvent arg1) {
        try {
            // user code begin {1}
            // user code end
            this.generalRadioButton_ItemStateChanged();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC6: (CRadioButton.item.itemStateChanged(java.awt.event.ItemEvent)
     * --> SVAR_ABModelPanel.cRadioButton_ItemStateChanged()V)
     * 
     * @param arg1
     *            java.awt.event.ItemEvent
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC6(java.awt.event.ItemEvent arg1) {
        try {
            // user code begin {1}
            // user code end
            this.cRadioButton_ItemStateChanged();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC7: (KRadioButton.item.itemStateChanged(java.awt.event.ItemEvent)
     * --> SVAR_ABModelPanel.kRadioButton_ItemStateChanged()V)
     * 
     * @param arg1
     *            java.awt.event.ItemEvent
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC7(java.awt.event.ItemEvent arg1) {
        try {
            // user code begin {1}
            // user code end
            this.kRadioButton_ItemStateChanged();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * Enable special C model case (B-model).
     */
    private void cRadioButton_ItemStateChanged() {
        if (getCRadioButton().isSelected()) {
            local().get(A_MATRIX)
                    .setJSCData(new JSCNArray("A", UMatrix.eye(K)));
            getDataTableA().setEnabled(false);
            getDataTableB().setEnabled(true);
            double[][] bMat = UMatrix.eye(K);
            for (int i = 0; i < K; i++)
                bMat[i][i] = Double.NEGATIVE_INFINITY;
            local().get(B_MATRIX).setJSCData(new JSCNArray("B", bMat));
        }
        return;
    }

    /**
     * Executes and performs SVAR estimation.
     */
    private void execute() {

        JSCNArray mSigmaU = global().get(VARConstants.cv_u_hat_Def)
                .getJSCNArray();

        TSDateRange range = global().get(VARConstants.T1_Def).getJSCDRange()
                .getTSDateRange();
        int T = range.numOfObs();
        double eps1_tol = getOptSetDialog().getRelParamChange();
        double eps2_tol = getOptSetDialog().getRelLikChange();
        int maxIterations = getOptSetDialog().getMaxIterNum();
        int iStartValueMethod = getOptSetDialog().getStartMethod();
        double fixStart = getOptSetDialog().getFixedValue();
        boolean iCorr = getOptSetDialog().getUseDecomp();
        int iMaxRetries = getOptSetDialog().getMaxRetries();
        String outFile = JSCConstants.getSystemTemp() + File.separator
                + OUT_FILE_EST;
        getResultField().clear();

        PCall job = new SVAREstABCall(mSigmaU, T, local().get(A_MATRIX)
                .getJSCNArray(), local().get(B_MATRIX).getJSCNArray(),
                eps1_tol, eps2_tol, fixStart, maxIterations, iStartValueMethod,
                iCorr, iMaxRetries, outFile);

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
     * Changes data table from editable to not editable and back. This changes
     * the CLICK behaviour.
     */
    private void gammaEditCheck_ActionEvents() {
        getDataTableA().setEditable(getGammaEditCheck().isSelected());
        return;
    }

    /**
     * Enable general case.
     */
    private void generalRadioButton_ItemStateChanged() {
        if (getGeneralRadioButton().isSelected()) {
            setABDefaults();
            getDataTableA().setEnabled(true);
            getDataTableA().setMouseListener(
                    JSCMouseListenerTypes.DIAG_01NEGINF);
            getDataTableB().setEnabled(true);
        }
        return;
    }

    /**
     * Return the GammaLabel property value.
     * 
     * @return javax.swing.JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JLabel getALabel() {
        if (ivjALabel == null) {
            try {
                ivjALabel = new javax.swing.JLabel();
                ivjALabel.setName("ALabel");
                ivjALabel.setPreferredSize(new java.awt.Dimension(58, 22));
                ivjALabel.setText("A matrix");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjALabel;
    }

    /**
     * Return the CRadioButton property value.
     * 
     * @return javax.swing.JRadioButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JRadioButton getCRadioButton() {
        if (ivjCRadioButton == null) {
            try {
                ivjCRadioButton = new javax.swing.JRadioButton();
                ivjCRadioButton.setName("CRadioButton");
                ivjCRadioButton.setText("Specify B model");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjCRadioButton;
    }

    private JSCNArrayTable getDataTableA() {
        if (ivjDataTableA == null) {
            try {
                ivjDataTableA = new JSCNArrayTable();
                ivjDataTableA.setName("DataTableA");
                getDataTableScrollPaneA().setColumnHeaderView(
                        ivjDataTableA.getTableHeader());
                ivjDataTableA.setPrecision(4);
                ivjDataTableA.setSymbolScope(Scope.LOCAL);
                ivjDataTableA
                        .setCellRenderer(JSCCellRendererTypes.DIAG_01M1NEGINF);
                ivjDataTableA.setTablePopup(null);
                ivjDataTableA
                        .setMouseListener(JSCMouseListenerTypes.DIAG_01NEGINF);
                ivjDataTableA.setBounds(0, 0, 147, 107);
                ivjDataTableA.setEditable(false);
                // user code begin {1}
                ivjDataTableA.setSymbolName(A_MATRIX.name);
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjDataTableA;
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
                ivjDataTableB.setBounds(0, 0, 146, 106);
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

    private JSCDataTableScrollPane getDataTableScrollPaneA() {
        if (ivjDataTableScrollPaneA == null) {
            try {
                ivjDataTableScrollPaneA = new JSCDataTableScrollPane();
                ivjDataTableScrollPaneA.setName("DataTableScrollPaneA");
                ivjDataTableScrollPaneA.setColumnHeaderShowing(true);
                ivjDataTableScrollPaneA.setMinimumVisibleColumns(5);
                ivjDataTableScrollPaneA.setMinimumVisibleRows(5);
                getDataTableScrollPaneA().setViewportView(getDataTableA());
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjDataTableScrollPaneA;
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

    /**
     * Return the ExecuteButton property value.
     * 
     * @return javax.swing.JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JButton getExecuteButton() {
        if (ivjExecuteButton == null) {
            try {
                ivjExecuteButton = new javax.swing.JButton();
                ivjExecuteButton.setName("ExecuteButton");
                ivjExecuteButton.setPreferredSize(new java.awt.Dimension(180,
                        25));
                ivjExecuteButton.setText("Execute ");
                ivjExecuteButton
                        .setMinimumSize(new java.awt.Dimension(180, 25));
                ivjExecuteButton
                        .setMaximumSize(new java.awt.Dimension(180, 25));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjExecuteButton;
    }

    /**
     * Return the GammaEditCheck property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getGammaEditCheck() {
        if (ivjGammaEditCheck == null) {
            try {
                ivjGammaEditCheck = new javax.swing.JCheckBox();
                ivjGammaEditCheck.setName("GammaEditCheck");
                ivjGammaEditCheck.setText("Edit coefficients manually");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjGammaEditCheck;
    }

    /**
     * Return the GeneralRadioButton property value.
     * 
     * @return javax.swing.JRadioButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JRadioButton getGeneralRadioButton() {
        if (ivjGeneralRadioButton == null) {
            try {
                ivjGeneralRadioButton = new javax.swing.JRadioButton();
                ivjGeneralRadioButton.setName("GeneralRadioButton");
                ivjGeneralRadioButton.setSelected(true);
                ivjGeneralRadioButton.setText("Specify general case");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjGeneralRadioButton;
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
                ivjJTabbedPane.insertTab("Specify/Estimate SVAR AB Model",
                        null, getSpecTab(), null, 0);
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
     * Return the KRadioButton property value.
     * 
     * @return javax.swing.JRadioButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JRadioButton getKRadioButton() {
        if (ivjKRadioButton == null) {
            try {
                ivjKRadioButton = new javax.swing.JRadioButton();
                ivjKRadioButton.setName("KRadioButton");
                ivjKRadioButton.setText("Specify A model");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjKRadioButton;
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
                        .setPreferredSize(new java.awt.Dimension(180, 25));
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
        if (optSetDialog == null)
            optSetDialog = new SVAROptSetDialog(TopFrameReference
                    .getTopFrameRef(), true);

        optSetDialog.setLocationRelativeTo(TopFrameReference.getTopFrameRef());
        return optSetDialog;
    }

    /**
     * Return the REditCheck property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getREditCheck() {
        if (ivjREditCheck == null) {
            try {
                ivjREditCheck = new javax.swing.JCheckBox();
                ivjREditCheck.setName("REditCheck");
                ivjREditCheck.setText("Edit coefficients manually");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjREditCheck;
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
     * Return the RLabel property value.
     * 
     * @return javax.swing.JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JLabel getRLabel() {
        if (ivjRLabel == null) {
            try {
                ivjRLabel = new javax.swing.JLabel();
                ivjRLabel.setName("RLabel");
                ivjRLabel.setPreferredSize(new java.awt.Dimension(58, 22));
                ivjRLabel.setText("B matrix");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjRLabel;
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

                java.awt.GridBagConstraints constraintsKRadioButton = new java.awt.GridBagConstraints();
                constraintsKRadioButton.gridx = 0;
                constraintsKRadioButton.gridy = 2;
                constraintsKRadioButton.gridwidth = 2;
                constraintsKRadioButton.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsKRadioButton.insets = new java.awt.Insets(10, 10, 0,
                        0);
                getSpecTab().add(getKRadioButton(), constraintsKRadioButton);

                java.awt.GridBagConstraints constraintsCRadioButton = new java.awt.GridBagConstraints();
                constraintsCRadioButton.gridx = 0;
                constraintsCRadioButton.gridy = 4;
                constraintsCRadioButton.gridwidth = 2;
                constraintsCRadioButton.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsCRadioButton.insets = new java.awt.Insets(5, 10, 0,
                        0);
                getSpecTab().add(getCRadioButton(), constraintsCRadioButton);

                java.awt.GridBagConstraints constraintsGeneralRadioButton = new java.awt.GridBagConstraints();
                constraintsGeneralRadioButton.gridx = 0;
                constraintsGeneralRadioButton.gridy = 5;
                constraintsGeneralRadioButton.gridwidth = 2;
                constraintsGeneralRadioButton.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsGeneralRadioButton.insets = new java.awt.Insets(5,
                        10, 10, 0);
                getSpecTab().add(getGeneralRadioButton(),
                        constraintsGeneralRadioButton);

                java.awt.GridBagConstraints constraintsOptSetButton = new java.awt.GridBagConstraints();
                constraintsOptSetButton.gridx = 3;
                constraintsOptSetButton.gridy = 2;
                constraintsOptSetButton.gridwidth = 2;
                constraintsOptSetButton.gridheight = 3;
                constraintsOptSetButton.anchor = java.awt.GridBagConstraints.WEST;
                constraintsOptSetButton.insets = new java.awt.Insets(10, 0, 0,
                        10);
                getSpecTab().add(getOptSetButton(), constraintsOptSetButton);

                java.awt.GridBagConstraints constraintsExecuteButton = new java.awt.GridBagConstraints();
                constraintsExecuteButton.gridx = 3;
                constraintsExecuteButton.gridy = 5;
                constraintsExecuteButton.gridwidth = 2;
                constraintsExecuteButton.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsExecuteButton.insets = new java.awt.Insets(5, 0, 0,
                        10);
                getSpecTab().add(getExecuteButton(), constraintsExecuteButton);

                java.awt.GridBagConstraints constraintsALabel = new java.awt.GridBagConstraints();
                constraintsALabel.gridx = 0;
                constraintsALabel.gridy = 0;
                constraintsALabel.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsALabel.insets = new java.awt.Insets(10, 10, 0, 0);
                getSpecTab().add(getALabel(), constraintsALabel);

                java.awt.GridBagConstraints constraintsRLabel = new java.awt.GridBagConstraints();
                constraintsRLabel.gridx = 3;
                constraintsRLabel.gridy = 0;
                constraintsRLabel.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsRLabel.insets = new java.awt.Insets(10, 5, 0, 0);
                getSpecTab().add(getRLabel(), constraintsRLabel);

                java.awt.GridBagConstraints constraintsDataTableScrollPaneA = new java.awt.GridBagConstraints();
                constraintsDataTableScrollPaneA.gridx = 0;
                constraintsDataTableScrollPaneA.gridy = 1;
                constraintsDataTableScrollPaneA.gridwidth = 2;
                constraintsDataTableScrollPaneA.fill = java.awt.GridBagConstraints.BOTH;
                constraintsDataTableScrollPaneA.weightx = 1.0;
                constraintsDataTableScrollPaneA.weighty = 1.0;
                constraintsDataTableScrollPaneA.insets = new java.awt.Insets(5,
                        10, 0, 0);
                getSpecTab().add(getDataTableScrollPaneA(),
                        constraintsDataTableScrollPaneA);

                java.awt.GridBagConstraints constraintsDataTableScrollPaneB = new java.awt.GridBagConstraints();
                constraintsDataTableScrollPaneB.gridx = 3;
                constraintsDataTableScrollPaneB.gridy = 1;
                constraintsDataTableScrollPaneB.gridwidth = 2;
                constraintsDataTableScrollPaneB.fill = java.awt.GridBagConstraints.BOTH;
                constraintsDataTableScrollPaneB.weightx = 1.0;
                constraintsDataTableScrollPaneB.weighty = 1.0;
                constraintsDataTableScrollPaneB.insets = new java.awt.Insets(5,
                        5, 0, 10);
                getSpecTab().add(getDataTableScrollPaneB(),
                        constraintsDataTableScrollPaneB);

                java.awt.GridBagConstraints constraintsGammaEditCheck = new java.awt.GridBagConstraints();
                constraintsGammaEditCheck.gridx = 1;
                constraintsGammaEditCheck.gridy = 0;
                constraintsGammaEditCheck.gridwidth = 2;
                constraintsGammaEditCheck.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsGammaEditCheck.insets = new java.awt.Insets(10, 10,
                        0, 0);
                getSpecTab()
                        .add(getGammaEditCheck(), constraintsGammaEditCheck);

                java.awt.GridBagConstraints constraintsREditCheck = new java.awt.GridBagConstraints();
                constraintsREditCheck.gridx = 4;
                constraintsREditCheck.gridy = 0;
                constraintsREditCheck.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsREditCheck.insets = new java.awt.Insets(10, 10, 0, 0);
                getSpecTab().add(getREditCheck(), constraintsREditCheck);
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
        getGammaEditCheck().addActionListener(ivjEventHandler);
        getREditCheck().addActionListener(ivjEventHandler);
        getGeneralRadioButton().addItemListener(ivjEventHandler);
        getCRadioButton().addItemListener(ivjEventHandler);
        getKRadioButton().addItemListener(ivjEventHandler);
        getExecuteButton().addActionListener(ivjEventHandler);
    }

    /**
     * Initialize the class.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initialize() {
        try {
            // user code begin {1}
            // user code end
            setName("SVAR_ABModelPanel");
            setLayout(new javax.swing.BoxLayout(this,
                    javax.swing.BoxLayout.X_AXIS));
            setSize(652, 411);
            add(getJTabbedPane(), getJTabbedPane().getName());
            initConnections();
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}
        ButtonGroup bb = new ButtonGroup();
        bb.add(getKRadioButton());
        bb.add(getCRadioButton());
        bb.add(getGeneralRadioButton());

        getDataTableScrollPaneA().setColumnHeaderSymbolName(
                VARConstants.Ny_Def.name);
        getDataTableScrollPaneB().setColumnHeaderSymbolName(
                VARConstants.Ny_Def.name);

        // user code end
    }

    /**
     * Enable special K model case (A-model).
     */
    private void kRadioButton_ItemStateChanged() {
        if (getKRadioButton().isSelected()) {
            local().get(B_MATRIX)
                    .setJSCData(new JSCNArray("B", UMatrix.eye(K)));
            getDataTableB().setEnabled(false);
            getDataTableA().setEnabled(true);
            double[][] aMat = UMatrix.eye(K);
            for (int i = 0; i < K; i++)
                aMat[i][i] = Double.NEGATIVE_INFINITY;
            local().get(A_MATRIX).setJSCData(new JSCNArray("A", aMat));
            getDataTableA()
                    .setMouseListener(JSCMouseListenerTypes.DIAG_0NEGINF);
        }
        return;
    }

    /**
     * Displays dialog to specify optimization settings.
     */
    private void optSetButton_ActionEvents() {
        getOptSetDialog().setVisible(true);

    }

    public void shown(boolean isShown) {
        if (!isShown)
            return;

        // resetting of
        // A and B matrizes when component is shown.
        int k = global().get(VARConstants.cv_u_hat_Def).getJSCNArray().rows();

        if (k != getDataTableA().getJSCNArray().rows()) {
            K = global().get(VARConstants.y_Def).getJSCNArray().cols();
            setABDefaults();
            getGeneralRadioButton().setSelected(true);
        }

    }

    /**
     * Changes data table from editable to not editable and back. This changes
     * the CLICK behaviour.
     */
    private void rEditCheck_ActionEvents() {
        getDataTableB().setEditable(getREditCheck().isSelected());
        return;
    }

    /**
     * Sets matrizes to default values.
     */
    private void setABDefaults() {
        double[][] gammaMat = UMatrix.eye(K);
        local().get(A_MATRIX).setJSCData(new JSCNArray("A", gammaMat));

        double[][] rMat = UMatrix.eye(K);
        for (int i = 0; i < K; i++)
            rMat[i][i] = Double.NEGATIVE_INFINITY;
        local().get(B_MATRIX).setJSCData(new JSCNArray("B", rMat));

    }
}