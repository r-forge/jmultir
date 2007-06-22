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

import java.awt.GridBagConstraints;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import com.jstatcom.component.ResultField;
import com.jstatcom.component.StdMessages;
import com.jstatcom.engine.PCall;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.JSCSArray;
import com.jstatcom.model.JSCTypeDef;
import com.jstatcom.model.JSCTypes;
import com.jstatcom.model.ModelPanel;
import com.jstatcom.model.Scope;
import com.jstatcom.model.SymbolEvent;
import com.jstatcom.model.SymbolListener;
import com.jstatcom.table.JSCDataTableScrollPane;
import com.jstatcom.table.JSCSArrayTable;
import com.jstatcom.util.UString;

import de.jmulti.proc.CausalityTestsCall;
import de.jmulti.var.VARConstants;
import de.jmulti.vecm.VECMConstants;

/**
 * Panel that binds together the tests for granger and instantanious causality.
 * 
 * @author A. Benkwitz, <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
public final class CausalityPanel extends ModelPanel {
    private static final Logger log = Logger.getLogger(CausalityPanel.class);

    private ModelTypes modelType = null;

    private JSCTypeDef causSel = new JSCTypeDef("cause_selection",
            JSCTypes.NARRAY, "selection index for variables");

    private String caus = null;

    private String eff = null;

    private JSCTypeDef endNames = null;

    private JSCTypeDef yName = null;

    private JSCTypeDef xName = null;

    private JSCTypeDef detName = null;

    private JSCTypeDef pylags = null;

    private JSCTypeDef pxlags = null;

    private JSCTypeDef Cx = null;

    private JSCTypeDef Cd = null;

    private JPanel ivjSelectPanel = null;

    private JButton ivjButton_Test = null;

    private JSCDataTableScrollPane ivjScrollPane_Selection = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private JPanel ivjResultsPanel = null;

    private ResultField ivjResultField = null;

    private JLabel ivjJLabel1 = null;

    private JSCSArrayTable ivjVariableNamesTable = null;

    private JLabel ivjCauseLabel = null;

    private JLabel ivjEffectLabel = null;

    private JLabel ivjJLabel = null;

    private JLabel ivjJLabel2 = null;

    private JLabel jLabel = null;

    class IvjEventHandler implements java.awt.event.ActionListener {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == CausalityPanel.this.getButton_Test())
                connEtoC1(e);
        };
    };

    /**
     * CausalityTestPanel constructor comment.
     */
    public CausalityPanel() {
        super();
        initialize();
    }

    /**
     * connEtoC1:
     * (Button_Test.action.actionPerformed(java.awt.event.ActionEvent) -->
     * CausalityTestsPanel.executeTest()V)
     * 
     * @param arg1
     *            java.awt.event.ActionEvent
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC1(java.awt.event.ActionEvent arg1) {
        try {
            // user code begin {1}
            // user code end
            this.executeTest();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * Sets the description for the H0 hypothesis.
     */
    private void evaluateSelection() {
        JSCNArray selection = local().get(causSel).getJSCNArray();
        JSCSArray names = local().get(endNames).getJSCSArray();
        StringBuffer cause = new StringBuffer();
        StringBuffer effect = new StringBuffer();
        for (int i = 0; i < selection.rows() && i < names.rows(); i++) {
            if (selection.intAt(i, 0) == 1)
                cause.append(names.stringAt(i, 0) + " ");
            else
                effect.append(names.stringAt(i, 0) + " ");

        }
        caus = cause.toString().trim();
        eff = effect.toString().trim();
        caus = UString.replaceAllSubStrings(caus, " ", ", ");
        eff = UString.replaceAllSubStrings(eff, " ", ", ");
        if (caus.length() == 0 || eff.length() == 0) {
            getCauseLabel().setText("INVALID SELECTION");
            getEffectLabel().setText("INVALID SELECTION");
        } else {
            getCauseLabel().setText(caus);
            getEffectLabel().setText(eff);
        }

    }

    /**
     * Comment
     */
    private void executeTest() {

        if (getVariableNamesTable().getSelectedRowCount() == 0) {
            StdMessages
                    .infoNothingSelected("Please select at least one variable.");
            return;
        }

        int py = global().get(pylags).getJSCInt().intVal();
        int px = global().get(pxlags).getJSCInt().intVal();

        if (py == 0) {
            StdMessages.errorSpecification("Test not possible for zero lags.");
            return;
        }
        if (getVariableNamesTable().getSelectedRowCount() == getVariableNamesTable()
                .getRowCount()) {
            StdMessages
                    .infoNothingSelected("You cannot select all variables,\nplease change your specification.");
            return;
        }
        JSCNArray idx = null;
        if (modelType == ModelTypes.VECM)
            idx = global().get(VECMConstants.idx_cd2ci_Def).getJSCNArray();
        PCall job = new CausalityTestsCall(modelType, global().get(yName)
                .getJSCNArray(), global().get(xName).getJSCNArray(), global()
                .get(detName).getJSCNArray(), px, py, local().get(causSel)
                .getJSCNArray(), global().get(Cx).getJSCNArray(), global().get(
                Cd).getJSCNArray(), caus, eff, idx);
        job.setSymbolTable(local());
        job.setOutHolder(getResultField());
        job.execute();
    }

    /**
     * Return the Button_Test property value.
     * 
     * @return javax.swing.JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JButton getButton_Test() {
        if (ivjButton_Test == null) {
            try {
                ivjButton_Test = new javax.swing.JButton();
                ivjButton_Test.setName("Button_Test");
                ivjButton_Test.setText("Execute Tests");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjButton_Test;
    }

    /**
     * Return the CausalityTestPanelBorderLayout property value.
     * 
     * @return java.awt.BorderLayout
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private java.awt.BorderLayout getCausalityTestPanelBorderLayout() {
        java.awt.BorderLayout ivjCausalityTestPanelBorderLayout = null;
        try {
            /* Create part */
            ivjCausalityTestPanelBorderLayout = new java.awt.BorderLayout();
            ivjCausalityTestPanelBorderLayout.setVgap(0);
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        ;
        return ivjCausalityTestPanelBorderLayout;
    }

    /**
     * Return the CauseLabel property value.
     * 
     * @return javax.swing.JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JLabel getCauseLabel() {
        if (ivjCauseLabel == null) {
            try {
                ivjCauseLabel = new javax.swing.JLabel();
                ivjCauseLabel.setName("CauseLabel");
                ivjCauseLabel.setText("");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjCauseLabel;
    }

    /**
     * Return the EffectLabel property value.
     * 
     * @return javax.swing.JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JLabel getEffectLabel() {
        if (ivjEffectLabel == null) {
            try {
                ivjEffectLabel = new javax.swing.JLabel();
                ivjEffectLabel.setName("EffectLabel");
                ivjEffectLabel.setText("");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjEffectLabel;
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
                ivjJLabel.setText("Cause variables");
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
                ivjJLabel1.setText("Specify H0");
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
                ivjJLabel2.setText("Effect variables");
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
     * Gets the current model type to be used for getting the variables.
     * 
     * @return the current model type
     */
    public ModelTypes getModelType() {
        return modelType;
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
                        .setPreferredSize(new java.awt.Dimension(300, 150));
                ivjResultField.setMinimumSize(new java.awt.Dimension(280, 150));
                ivjResultField
                        .setMaximumSize(new java.awt.Dimension(400, 32767));
                // user code begin {1}
                getResultField()
                        .setBorder(new BevelBorder(BevelBorder.LOWERED));

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
     * Return the ResultsPanel property value.
     * 
     * @return javax.swing.JPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JPanel getResultsPanel() {
        if (ivjResultsPanel == null) {
            try {
                ivjResultsPanel = new javax.swing.JPanel();
                ivjResultsPanel.setName("ResultsPanel");
                ivjResultsPanel
                        .setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
                ivjResultsPanel.setLayout(new java.awt.GridBagLayout());

                java.awt.GridBagConstraints constraintsResultField = new java.awt.GridBagConstraints();
                constraintsResultField.gridx = 0;
                constraintsResultField.gridy = 0;
                constraintsResultField.gridheight = 2;
                constraintsResultField.fill = java.awt.GridBagConstraints.BOTH;
                constraintsResultField.weightx = 1.0;
                constraintsResultField.weighty = 1.0;
                getResultsPanel().add(getResultField(), constraintsResultField);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjResultsPanel;
    }

    private JSCDataTableScrollPane getScrollPane_Selection() {
        if (ivjScrollPane_Selection == null) {
            try {
                ivjScrollPane_Selection = new JSCDataTableScrollPane();
                ivjScrollPane_Selection.setName("ScrollPane_Selection");
                ivjScrollPane_Selection
                        .setPreferredSize(new java.awt.Dimension(100, 100));
                ivjScrollPane_Selection.setMaximumVisibleColumns(1);
                ivjScrollPane_Selection.setMaximumVisibleRows(5);
                getScrollPane_Selection().setViewportView(
                        getVariableNamesTable());
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjScrollPane_Selection;
    }

    /**
     * Return the SelectPanel property value.
     * 
     * @return javax.swing.JPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JPanel getSelectPanel() {
        if (ivjSelectPanel == null) {
            try {
                jLabel = new JLabel();
                GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
                ivjSelectPanel = new javax.swing.JPanel();
                ivjSelectPanel.setName("SelectPanel");
                ivjSelectPanel.setLayout(new java.awt.GridBagLayout());
                ivjSelectPanel
                        .setPreferredSize(new java.awt.Dimension(324, 150));
                ivjSelectPanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
                ivjSelectPanel.setMinimumSize(new java.awt.Dimension(324, 150));

                java.awt.GridBagConstraints constraintsJLabel1 = new java.awt.GridBagConstraints();
                constraintsJLabel1.gridx = 0;
                constraintsJLabel1.gridy = 1;
                constraintsJLabel1.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsJLabel1.insets = new java.awt.Insets(0, 5, 5, 10);
                java.awt.GridBagConstraints constraintsScrollPane_Selection = new java.awt.GridBagConstraints();
                constraintsScrollPane_Selection.gridx = 1;
                constraintsScrollPane_Selection.gridy = 1;
                constraintsScrollPane_Selection.gridheight = 3;
                constraintsScrollPane_Selection.fill = java.awt.GridBagConstraints.BOTH;
                constraintsScrollPane_Selection.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsScrollPane_Selection.insets = new java.awt.Insets(0,
                        5, 0, 5);
                java.awt.GridBagConstraints constraintsButton_Test = new java.awt.GridBagConstraints();
                constraintsButton_Test.gridx = 2;
                constraintsButton_Test.gridy = 3;
                constraintsButton_Test.gridwidth = 6;
                constraintsButton_Test.anchor = java.awt.GridBagConstraints.SOUTH;
                constraintsButton_Test.weightx = 1.0;
                constraintsButton_Test.weighty = 1.0;
                constraintsButton_Test.insets = new java.awt.Insets(0, 10, 5, 5);
                java.awt.GridBagConstraints constraintsJLabel = new java.awt.GridBagConstraints();
                constraintsJLabel.gridx = 2;
                constraintsJLabel.gridy = 1;
                constraintsJLabel.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsJLabel.insets = new java.awt.Insets(0, 5, 5, 0);
                java.awt.GridBagConstraints constraintsJLabel2 = new java.awt.GridBagConstraints();
                constraintsJLabel2.gridx = 2;
                constraintsJLabel2.gridy = 2;
                constraintsJLabel2.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsJLabel2.insets = new java.awt.Insets(0, 5, 5, 0);
                java.awt.GridBagConstraints constraintsCauseLabel = new java.awt.GridBagConstraints();
                constraintsCauseLabel.gridx = 7;
                constraintsCauseLabel.gridy = 1;
                constraintsCauseLabel.fill = java.awt.GridBagConstraints.BOTH;
                constraintsCauseLabel.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsCauseLabel.insets = new java.awt.Insets(0, 20, 5, 10);
                java.awt.GridBagConstraints constraintsEffectLabel = new java.awt.GridBagConstraints();
                constraintsEffectLabel.gridx = 7;
                constraintsEffectLabel.gridy = 2;
                constraintsEffectLabel.fill = java.awt.GridBagConstraints.BOTH;
                constraintsEffectLabel.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsEffectLabel.insets = new java.awt.Insets(0, 20, 5,
                        10);
                gridBagConstraints1.gridx = 0;
                gridBagConstraints1.gridy = 0;
                gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
                gridBagConstraints1.insets = new java.awt.Insets(0, 5, 5, 10);
                gridBagConstraints1.gridwidth = 8;
                jLabel.setText("");
                constraintsScrollPane_Selection.weightx = 1.0D;
                ivjSelectPanel.add(getJLabel1(), constraintsJLabel1);
                ivjSelectPanel.add(getButton_Test(), constraintsButton_Test);
                ivjSelectPanel.add(getJLabel(), constraintsJLabel);
                ivjSelectPanel.add(getJLabel2(), constraintsJLabel2);
                ivjSelectPanel.add(getCauseLabel(), constraintsCauseLabel);
                ivjSelectPanel.add(getEffectLabel(), constraintsEffectLabel);
                ivjSelectPanel.add(jLabel, gridBagConstraints1);
                // user code end
                jLabel.setVisible(false);
                constraintsScrollPane_Selection.weighty = 1.0D;
                ivjSelectPanel.add(getScrollPane_Selection(),
                        constraintsScrollPane_Selection);
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSelectPanel;
    }

    private JSCSArrayTable getVariableNamesTable() {
        if (ivjVariableNamesTable == null) {
            try {
                ivjVariableNamesTable = new JSCSArrayTable();
                ivjVariableNamesTable.setName("VariableNamesTable");
                getScrollPane_Selection().setColumnHeaderView(
                        ivjVariableNamesTable.getTableHeader());
                ivjVariableNamesTable.setSymbolScope(Scope.LOCAL);
                ivjVariableNamesTable.setDynamicColumnWidth(true);
                ivjVariableNamesTable.setSymbolNameSelRows(causSel.name);
                ivjVariableNamesTable.setBounds(0, 0, 45, 17);
                ivjVariableNamesTable.setEditable(false);
                ivjVariableNamesTable.setRowSelectionAllowed(true);
                // user code begin {1}
                ivjVariableNamesTable.setMinimumColumnWidth(75);
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjVariableNamesTable;
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
        getButton_Test().addActionListener(ivjEventHandler);
    }

    /**
     * Initialize the class.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initialize() {
        try {
            // user code begin {1}
            // user code end
            setName("CausalityTestPanel");
            setLayout(getCausalityTestPanelBorderLayout());
            setSize(580, 500);
            add(getSelectPanel(), "North");
            add(getResultsPanel(), "Center");
            initConnections();
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}

        local().get(causSel).addSymbolListener(new SymbolListener() {
            public void valueChanged(SymbolEvent evt) {
                evaluateSelection();
            }
        });

        setModelType(ModelTypes.VAR);
        // user code end
    }

    /**
     * Sets the model type to be used for this panel. This affects the test
     * routine as well.
     * 
     * @param newModelType
     *            the model type
     * @throws IllegalArgumentException
     *             <code>if (newModelType == null)</code>
     */
    public void setModelType(ModelTypes newModelType) {

        if (newModelType == null)
            throw new IllegalArgumentException("Modeltype was null.");

        modelType = newModelType;

        TitledBorder title = new TitledBorder(new BevelBorder(
                BevelBorder.LOWERED), "Causality Tests for " + modelType
                + " Model", TitledBorder.RIGHT, TitledBorder.TOP);
        getSelectPanel().setBorder(title);
        if (modelType == ModelTypes.VAR) {
            endNames = VARConstants.Ny_Def;
            yName = VARConstants.y_Def;
            xName = VARConstants.x_Def;
            detName = VARConstants.d_all_Def;
            pxlags = VARConstants.px_Def;
            pylags = VARConstants.py_Def;
            Cd = VARConstants.Cd_Def;
            Cx = VARConstants.Cx_Def;
            getVariableNamesTable().setSymbolName(endNames.name);
            return;
        }
        if (modelType == ModelTypes.VECM) {
            jLabel.setText("Lag augmented causality test on unrestricted VAR:");
            jLabel.setVisible(true);

            endNames = VECMConstants.Ny_Def;
            yName = VECMConstants.y_Def;
            xName = VECMConstants.x_Def;
            detName = VECMConstants.d_Def;
            pxlags = VECMConstants.px_Def;
            pylags = VECMConstants.py_granger_Def;
            Cd = VECMConstants.S_C_VEC_Def;
            Cx = VECMConstants.S_B_Def;
            getVariableNamesTable().setSymbolName(endNames.name);
            return;
        }
        throw new IllegalArgumentException("Modeltype " + modelType
                + " is unknown.");

    }

    /**
     * propertyChange method comment.
     */
    public void shown(boolean isShown) {
        if (isShown) {
            local().set(global().getJSCData(endNames));
            evaluateSelection();
        }

    }
}