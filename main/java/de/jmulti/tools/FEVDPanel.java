/*
 This file is part of the econometric software package JMulTi.
 Copyright (C) 2000-2005  Alexander Bankwitz, Markus Kraetzig

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
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import com.jstatcom.component.NumSelector;
import com.jstatcom.component.ResultField;
import com.jstatcom.component.StdMessages;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.JSCSArray;
import com.jstatcom.model.JSCTypeDef;
import com.jstatcom.model.JSCTypes;
import com.jstatcom.model.ModelPanel;
import com.jstatcom.table.JSCDataTableScrollPane;
import com.jstatcom.table.JSCSArrayTable;
import common.Logger;

import de.jmulti.proc.FEVDCall;
import de.jmulti.var.VARConstants;

/**
 * 
 * Configurable panel for specification and execution of a Forecast Error
 * Variance Decomposition. The default settings can be used with the VAR model
 * specification in <i>JMulTi </i>. For other models, the settings for the
 * variable names may be changed.
 * 
 * @author A. Benkwitz, <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
public final class FEVDPanel extends ModelPanel implements ActionListener {
    private static final Logger log = Logger.getLogger(FEVDPanel.class);

    private ModelTypes modelType = ModelTypes.VAR;

    private FEVDBarConfigDialog barConfigDialog = null;

    private JSCTypeDef covarDef = VARConstants.cv_u_hat_Def;

    private JSCTypeDef coeffDef = VARConstants.Ay_Def;

    private JSCTypeDef variableNamesDef = VARConstants.Ny_Def;

    private JSCTypeDef structMatrixDef = new JSCTypeDef("A0", JSCTypes.NARRAY);

    private JPanel ivjChoicePanel = null;

    private ResultField ivjResultField = null;

    private JSCDataTableScrollPane ivjScrollPane_Selection = null;

    private JLabel ivjJLabel1 = null;

    private JButton ivjExecuteButton = null;

    private JPanel ivjContentPanel = null;

    private JSCSArrayTable ivjDataTableSelection = null;

    private NumSelector ivjForecastHorizonSelector = null;

    private JButton barConfigButton = null;

    /**
     * ForecastErrorImpulseResponses constructor comment.
     */
    public FEVDPanel() {
        super();
        initialize();
    }

    /**
     * Method to handle events for the ActionListener interface.
     * 
     * @param e
     *            java.awt.event.ActionEvent
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    public void actionPerformed(java.awt.event.ActionEvent e) {
        // user code begin {1}
        // user code end
        if (e.getSource() == getExecuteButton())
            connEtoC2();
        // user code begin {2}
        // user code end
    }

    /**
     * connEtoC2: (ExecuteButton.action. -->
     * FEVDPanel.executeButton_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC2() {
        try {
            // user code begin {1}
            // user code end
            this.executeButton_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * 
     */
    private void executeButton_ActionEvents() {
        if (getDataTableSelection().getSelectedRow() < 0) {
            StdMessages
                    .infoNothingSelected("Please select a variable from the list.");
            return;
        }

        JSCNArray selection = new JSCNArray("fevd_selection",
                getDataTableSelection().getIntSelectionIndex());

        int horizon = getForecastHorizonSelector().getIntNumber();

        final FEVDCall fcall = new FEVDCall(horizon, global().get(
                variableNamesDef).getJSCSArray(), selection, global().get(
                coeffDef).getJSCNArray(),
                global().get(covarDef).getJSCNArray(), global().get(
                        structMatrixDef).getJSCNArray(), modelType,
                getBarConfigDialog().getSelectedBarTypes(), true);

        getResultField().clear();
        fcall.setOutHolder(getResultField());
        fcall.setSymbolTable(local());
        fcall.execute();

    }

    /**
     * Return the JPanel2 property value.
     * 
     * @return javax.swing.JPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JPanel getChoicePanel() {
        if (ivjChoicePanel == null) {
            try {
                GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
                ivjChoicePanel = new javax.swing.JPanel();
                ivjChoicePanel.setName("ChoicePanel");
                ivjChoicePanel.setLayout(new java.awt.GridBagLayout());
                ivjChoicePanel.setAlignmentY(java.awt.Component.TOP_ALIGNMENT);
                ivjChoicePanel
                        .setPreferredSize(new java.awt.Dimension(351, 140));
                ivjChoicePanel.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
                ivjChoicePanel.setMinimumSize(new java.awt.Dimension(324, 82));

                java.awt.GridBagConstraints constraintsScrollPane_Selection = new java.awt.GridBagConstraints();
                constraintsScrollPane_Selection.gridx = 2;
                constraintsScrollPane_Selection.gridy = 0;
                constraintsScrollPane_Selection.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsScrollPane_Selection.insets = new java.awt.Insets(5,
                        40, 0, 20);
                java.awt.GridBagConstraints constraintsJLabel1 = new java.awt.GridBagConstraints();
                constraintsJLabel1.gridx = 0;
                constraintsJLabel1.gridy = 0;
                constraintsJLabel1.anchor = java.awt.GridBagConstraints.NORTHEAST;
                constraintsJLabel1.insets = new java.awt.Insets(5, 10, 0, 0);
                java.awt.GridBagConstraints constraintsForecastHorizonSelector = new java.awt.GridBagConstraints();
                constraintsForecastHorizonSelector.gridx = 1;
                constraintsForecastHorizonSelector.gridy = 0;
                constraintsForecastHorizonSelector.anchor = java.awt.GridBagConstraints.NORTHEAST;
                constraintsForecastHorizonSelector.insets = new java.awt.Insets(
                        5, 10, 0, 0);
                java.awt.GridBagConstraints constraintsExecuteButton = new java.awt.GridBagConstraints();
                constraintsExecuteButton.gridx = 3;
                constraintsExecuteButton.gridy = 1;
                constraintsExecuteButton.anchor = java.awt.GridBagConstraints.SOUTH;
                constraintsExecuteButton.weightx = 0.0D;
                constraintsExecuteButton.weighty = 0.0D;
                constraintsExecuteButton.insets = new java.awt.Insets(0, 10,
                        10, 50);
                constraintsScrollPane_Selection.weightx = 1.0D;
                constraintsScrollPane_Selection.fill = java.awt.GridBagConstraints.BOTH;
                constraintsScrollPane_Selection.weighty = 1.0D;
                constraintsScrollPane_Selection.gridheight = 2;
                gridBagConstraints1.gridx = 3;
                gridBagConstraints1.gridy = 0;
                gridBagConstraints1.anchor = java.awt.GridBagConstraints.SOUTH;
                gridBagConstraints1.insets = new java.awt.Insets(0, 10, 10, 50);
                gridBagConstraints1.weighty = 1.0D;
                ivjChoicePanel.add(getJLabel1(), constraintsJLabel1);
                ivjChoicePanel.add(getForecastHorizonSelector(),
                        constraintsForecastHorizonSelector);
                ivjChoicePanel.add(getScrollPane_Selection(),
                        constraintsScrollPane_Selection);
                ivjChoicePanel
                        .add(getExecuteButton(), constraintsExecuteButton);
                ivjChoicePanel.add(getBarConfigButton(), gridBagConstraints1);
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjChoicePanel;
    }

    /**
     * Gets the name of the data object containing the coefficients values.
     * 
     * @return symbol name for coefficients values
     */
    public String getCoeffName() {
        if (coeffDef == null)
            return null;
        return coeffDef.name;
    }

    /**
     * Return the CenterPanel property value.
     * 
     * @return javax.swing.JPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JPanel getContentPanel() {
        if (ivjContentPanel == null) {
            try {
                ivjContentPanel = new javax.swing.JPanel();
                ivjContentPanel.setName("ContentPanel");
                ivjContentPanel.setLayout(new java.awt.BorderLayout());
                getContentPanel().add(getResultField(), "Center");
                getContentPanel().add(getChoicePanel(), "North");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjContentPanel;
    }

    /**
     * Gets the name of the data object containing the covariance matrix.
     * 
     * @return symbol name for covariance matrix
     */
    public String getCovarName() {
        if (covarDef == null)
            return null;
        return covarDef.name;
    }

    private JSCSArrayTable getDataTableSelection() {
        if (ivjDataTableSelection == null) {
            try {
                ivjDataTableSelection = new JSCSArrayTable();
                ivjDataTableSelection.setName("DataTableSelection");
                getScrollPane_Selection().setColumnHeaderView(
                        ivjDataTableSelection.getTableHeader());
                ivjDataTableSelection.setDynamicColumnWidth(true);
                ivjDataTableSelection.setSymbolName(variableNamesDef.name);
                ivjDataTableSelection.setBounds(0, 0, 125, 30);
                ivjDataTableSelection.setEditable(false);
                ivjDataTableSelection.setRowSelectionAllowed(true);
                ivjDataTableSelection.setMinimumColumnWidth(100);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjDataTableSelection;
    }

    /**
     * Return the JButton1 property value.
     * 
     * @return javax.swing.JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JButton getExecuteButton() {
        if (ivjExecuteButton == null) {
            try {
                ivjExecuteButton = new javax.swing.JButton();
                ivjExecuteButton.setName("ExecuteButton");
                ivjExecuteButton.setText("Display FEVD");
                ivjExecuteButton
                        .setMinimumSize(new java.awt.Dimension(150, 27));
                ivjExecuteButton.setPreferredSize(new java.awt.Dimension(150,
                        27));
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

    private NumSelector getForecastHorizonSelector() {
        if (ivjForecastHorizonSelector == null) {
            try {
                ivjForecastHorizonSelector = new NumSelector();
                ivjForecastHorizonSelector.setName("ForecastHorizonSelector");
                ivjForecastHorizonSelector.setNumber(20.0);
                ivjForecastHorizonSelector.setRangeExpr("[2, 200]");
                ivjForecastHorizonSelector
                        .setPreferredSize(new java.awt.Dimension(60, 20));
                ivjForecastHorizonSelector
                        .setMinimumSize(new java.awt.Dimension(60, 20));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjForecastHorizonSelector;
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
                ivjJLabel1.setPreferredSize(new java.awt.Dimension(100, 20));
                ivjJLabel1.setText("Forecast horizon");
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
     * Sets the type of model in which this component is used.
     * 
     * @return the model type
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
                // user code begin {1}
                ivjResultField.setBorder(new BevelBorder(BevelBorder.LOWERED));

                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjResultField;
    }

    private JSCDataTableScrollPane getScrollPane_Selection() {
        if (ivjScrollPane_Selection == null) {
            try {
                ivjScrollPane_Selection = new JSCDataTableScrollPane();
                ivjScrollPane_Selection.setName("ScrollPane_Selection");
                ivjScrollPane_Selection
                        .setAlignmentY(java.awt.Component.TOP_ALIGNMENT);
                ivjScrollPane_Selection.setMaximumVisibleColumns(1);
                String ivjLocal50columnHeaderStringData[] = { "Select Variables" };
                ivjScrollPane_Selection
                        .setColumnHeaderStringData(ivjLocal50columnHeaderStringData);
                ivjScrollPane_Selection
                        .setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
                ivjScrollPane_Selection.setColumnHeaderShowing(true);
                ivjScrollPane_Selection.setMaximumVisibleRows(10);
                ivjScrollPane_Selection.setMinimumVisibleRows(4);
                getScrollPane_Selection().setViewportView(
                        getDataTableSelection());
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
     * Gets the symbol name of the (A0) matrix of structural coefficients. This
     * variable is ignored for VAR models.
     * 
     * @return the symbol name for A0
     */
    public String getStructMatrixName() {
        if (structMatrixDef == null)
            return null;
        return structMatrixDef.name;
    }

    /**
     * Gets the name of the data object containing the variable names.
     * 
     * @return symbol name for variable names
     */
    public String getVariableNames() {
        if (variableNamesDef == null)
            return null;
        return variableNamesDef.name;
    }

    /**
     * Called whenever the part throws an exception.
     * 
     * @param exception
     *            Exception
     */
    private void handleException(Throwable exception) {

        log.error("Unhandled Exception", exception);
        getExecuteButton().setEnabled(true);
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
        getExecuteButton().addActionListener(this);
    }

    /**
     * Initialize the class.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initialize() {
        try {
            // user code begin {1}
            // user code end
            setName("FEVD");
            setLayout(new java.awt.BorderLayout());
            setSize(588, 200);
            add(getContentPanel(), "Center");
            initConnections();
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}
        setModelType(ModelTypes.VAR);

        // user code end
    }

    /**
     * This method initializes jButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getBarConfigButton() {
        if (barConfigButton == null) {
            barConfigButton = new JButton();
            barConfigButton.setText("Configure Bar Plot");
            barConfigButton.setMinimumSize(new java.awt.Dimension(150, 27));
            barConfigButton.setPreferredSize(new java.awt.Dimension(150, 27));
            barConfigButton
                    .addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent e) {
                            // variable names are set
                            JSCSArray varNames = getBarConfigDialog()
                                    .getVariableNames();
                            JSCSArray currentNames = getDataTableSelection()
                                    .getJSCSArray();
                            if (varNames == null
                                    || !varNames.isEqual(currentNames))
                                getBarConfigDialog().setVariableNames(
                                        currentNames);
                            getBarConfigDialog().setVisible(true);
                        }
                    });
        }
        return barConfigButton;
    }

    /**
     * Sets the name of the data object containing the coefficients values.
     * 
     * @param newCoeffName
     *            symbol name of coefficients values
     */
    public void setCoeffName(String newCoeffName) {
        if (newCoeffName == null)
            coeffDef = null;
        else
            coeffDef = new JSCTypeDef(newCoeffName, JSCTypes.NARRAY);
    }

    /**
     * Sets the name of the data object containing the covariance matrix.
     * 
     * @param newCovarName
     *            symbol name of covariance matrix
     */
    public void setCovarName(String newCovarName) {
        if (newCovarName == null)
            covarDef = null;
        else
            covarDef = new JSCTypeDef(newCovarName, JSCTypes.NARRAY);
    }

    /**
     * Gets the type of model in which this component is used.
     * 
     * @param newModelType
     *            the type defined in the typesave enum <code>ModelTypes</code>
     */
    public void setModelType(ModelTypes newModelType) {
        modelType = newModelType;
        TitledBorder title = new TitledBorder(new BevelBorder(
                BevelBorder.LOWERED), modelType
                + " Forecast Error Variance Decomposition", TitledBorder.RIGHT,
                TitledBorder.TOP);
        getChoicePanel().setBorder(title);

    }

    /**
     * Sets the symbol name of the (A0) matrix of structural coefficients.
     * 
     * @param newStructMatrixName
     *            the symbol name for A0
     */
    public void setStructMatrixName(String newStructMatrixName) {
        if (newStructMatrixName == null)
            structMatrixDef = null;
        else
            structMatrixDef = new JSCTypeDef(newStructMatrixName,
                    JSCTypes.NARRAY);
    }

    /**
     * Sets the name of the data object containing the variable names.
     * 
     * @param newVariableNames
     *            symbol name of variable names
     */
    public void setVariableNames(String newVariableNames) {
        if (newVariableNames == null)
            variableNamesDef = null;
        else
            variableNamesDef = new JSCTypeDef(newVariableNames, JSCTypes.SARRAY);
        getDataTableSelection().setSymbolName(newVariableNames);

    }

    /**
     * @return
     */
    private FEVDBarConfigDialog getBarConfigDialog() {
        if (barConfigDialog == null) {
            barConfigDialog = new FEVDBarConfigDialog();
            barConfigDialog.setLocationRelativeTo(this);

        }
        return barConfigDialog;
    }
}