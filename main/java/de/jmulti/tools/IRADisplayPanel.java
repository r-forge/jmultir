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

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.log4j.Logger;

import com.jstatcom.component.CheckBoxList;
import com.jstatcom.component.ResultField;
import com.jstatcom.component.StdMessages;
import com.jstatcom.component.TopFrameReference;
import com.jstatcom.engine.PCall;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.JSCSArray;
import com.jstatcom.model.JSCTypeDef;
import com.jstatcom.model.JSCTypes;
import com.jstatcom.model.ModelPanel;
import com.jstatcom.model.Scope;
import com.jstatcom.table.JSCDataTableScrollPane;
import com.jstatcom.table.JSCSArrayTable;

import de.jmulti.proc.IRADisplayCall;
import de.jmulti.proc.IRAFuncCall;
import de.jmulti.var.VARConstants;

/**
 * This component provides a user input mask for selecting display options for
 * the impulse response analysis. It has to be configured with the symbol names
 * of the impulse response function to be plotted, the number of periods, the
 * names of the variables, the confidence intervals and the names of the
 * confidence intervals. It just makes the call to
 * <code>{@link de.jmulti.proc.IRADisplayCall}</code>.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
public final class IRADisplayPanel extends ModelPanel {
    private static final Logger log = Logger.getLogger(IRADisplayPanel.class);

    private PlotControlDialog plotControlDialog = null;

    private PlotControlModel plotModel = new PlotControlModel() {
        public void setDefaultValues() {
            super.setDefaultValues();
            show_date.setVal(1);
            show_legend.setVal(1);
            show_main_title.setVal(1);
            show_plot_title.setVal(1);
            line_color.setVal(new double[] { 15, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                    11, 12, 13, 14 });
            line_width.setVal(new double[] { 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
                    7, 7, 7 });
            line_type.setVal(new double[] { 6, 1, 2, 3, 5, 1, 2, 3, 4, 1, 2, 3,
                    4, 1 });
            height_axis.setVal(0.2);
            height_legend.setVal(5);
            height_main_title.setVal(0.2);
            height_numbers.setVal(0.2);
            height_plot_title.setVal(0.2);
            legend_x.setVal(0.1);
            legend_y.setVal(0.1);
        }
    };

    private ModelTypes modelType = ModelTypes.VAR;

    private String variableNames = "Ny";

    private ResultField ivjResultField = null;

    private JPanel ivjJPanel1 = null;

    private JButton ivjDisplayButton = null;

    private JButton ivjPlotConfigButton = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private JCheckBox ivjFERCheck = null;

    private JCheckBox ivjOrthCheck = null;

    private JSCSArrayTable ivjCISelectionTable = null;

    private JSCDataTableScrollPane ivjCITableScrollPane = null;

    private JSCSArrayTable ivjImpulsesTable = null;

    private JSCDataTableScrollPane ivjImpulseTableScrollPane = null;

    private JSCSArrayTable ivjResponsesTable = null;

    private JSCDataTableScrollPane ivjResponseTableScrollPane = null;

    private CheckBoxList ivjCheckBoxList = null;

    class IvjEventHandler implements java.awt.event.ActionListener {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == IRADisplayPanel.this.getPlotConfigButton())
                connEtoC1();
            if (e.getSource() == IRADisplayPanel.this.getDisplayButton())
                connEtoC2();
        };
    };

    private JScrollPane ivjAccumScrollPane = null;

    /**
     * IRADisplayPanel constructor comment.
     */
    public IRADisplayPanel() {
        super();
        initialize();
    }

    /**
     * connEtoC1: (PlotConfigButton.action. -->
     * IRADisplayPanel.plotConfigButton_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC1() {
        try {
            // user code begin {1}
            // user code end
            this.plotConfigButton_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC2: (DisplayButton.action. -->
     * IRADisplayPanel.displayButton_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC2() {
        try {
            // user code begin {1}
            // user code end
            this.displayButton_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * Assembles and makes the call to <code>IRADisplayCall</code>.
     */
    private void displayButton_ActionEvents() {

        if (!checkNumberOfSelCI())
            return;

        if (getImpulsesTable().getSelectedRowCount() == 0
                || getResponsesTable().getSelectedRowCount() == 0) {
            StdMessages
                    .infoNothingSelected("Please select variables for impulses and responses.");
            return;
        }

        boolean isOrth = getOrthCheck().isSelected();

        // Get the name under which the ir function is stored in the symbol
        // table.
        JSCTypeDef iraName = IRAFuncCall.IR_FERR;
        JSCTypeDef iraName_ac = IRAFuncCall.IR_FERR_AC;

        if (isOrth) {
            iraName = IRAFuncCall.IR_ORTH;
            iraName_ac = IRAFuncCall.IR_ORTH_AC;
        }

        if (modelType == ModelTypes.SVAR || modelType == ModelTypes.SVEC) {
            iraName = IRAFuncCall.IR_SVAR;
            iraName_ac = IRAFuncCall.IR_SVAR_AC;
        }

        JSCNArray irFunct = new JSCNArray("irFunc");
        // Append the point and accumulated ir functions.
        irFunct.setVal(upper().get(iraName).getJSCNArray());
        irFunct.appendCols(upper().get(iraName_ac).getJSCNArray());

        String[] selCINames = getSelCINames();
        int periods = irFunct.rows() - 1;

        JSCNArray selImpulseIndex = new JSCNArray("selImpulses",
                getImpulsesTable().getIntSelectionIndex());

        JSCNArray selResponseIndex = new JSCNArray("selResponses",
                getResponsesTable().getIntSelectionIndex());

        double[][] selStat = new double[getCheckBoxList()
                .getIntSelectionStatus().length][1];
        for (int i = 0; i < selStat.length; i++)
            selStat[i][0] = getCheckBoxList().getIntSelectionStatus()[i];

        JSCNArray responseAccumIndex = new JSCNArray("responseAccumIndex",
                selStat);

        JSCSArray varNames = global().get(
                new JSCTypeDef(getVariableNames(), JSCTypes.SARRAY))
                .getJSCSArray();
        String title = getIRDisplayTitle();

        getResultField().clear();
        PCall job = new IRADisplayCall(irFunct, selCINames, isOrth, periods,
                selImpulseIndex, selResponseIndex, responseAccumIndex,
                varNames, title, plotModel, modelType);
        job.setSymbolTable(upper());
        job.setOutHolder(getResultField());
        job.execute();

    }

    /**
     * Return the JScrollPane1 property value.
     * 
     * @return javax.swing.JScrollPane
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JScrollPane getAccumScrollPane() {
        if (ivjAccumScrollPane == null) {
            try {
                ivjAccumScrollPane = new javax.swing.JScrollPane();
                ivjAccumScrollPane.setName("AccumScrollPane");
                ivjAccumScrollPane.setMinimumSize(new java.awt.Dimension(100,
                        22));
                getAccumScrollPane().setViewportView(getCheckBoxList());
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjAccumScrollPane;
    }

    private CheckBoxList getCheckBoxList() {
        if (ivjCheckBoxList == null) {
            try {
                ivjCheckBoxList = new CheckBoxList();
                ivjCheckBoxList.setName("CheckBoxList");
                ivjCheckBoxList.setBorderTitle("Accum.Resp.");
                ivjCheckBoxList.setBounds(0, 0, 99, 152);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjCheckBoxList;
    }

    private JSCSArrayTable getCISelectionTable() {
        if (ivjCISelectionTable == null) {
            try {
                ivjCISelectionTable = new JSCSArrayTable();
                ivjCISelectionTable.setName("CISelectionTable");
                getCITableScrollPane().setColumnHeaderView(
                        ivjCISelectionTable.getTableHeader());
                ivjCISelectionTable.setSymbolScope(Scope.UPPER);
                ivjCISelectionTable.setDynamicColumnWidth(true);
                ivjCISelectionTable.setMaximumColumnWidth(300);
                ivjCISelectionTable.setBounds(0, 0, 125, 30);
                ivjCISelectionTable.setEditable(false);
                ivjCISelectionTable.setRowSelectionAllowed(true);
                ivjCISelectionTable.setMinimumColumnWidth(200);
                ivjCISelectionTable.getSelectionModel()
                        .addListSelectionListener(new ListSelectionListener() {
                            public void valueChanged(ListSelectionEvent e) {
                                if (!e.getValueIsAdjusting())
                                    setLegend();
                            }
                        });
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjCISelectionTable;
    }

    private JSCDataTableScrollPane getCITableScrollPane() {
        if (ivjCITableScrollPane == null) {
            try {
                ivjCITableScrollPane = new JSCDataTableScrollPane();
                ivjCITableScrollPane.setName("CITableScrollPane");
                ivjCITableScrollPane.setMinimumVisibleColumns(1);
                ivjCITableScrollPane.setMaximumVisibleColumns(1);
                String ivjLocal47columnHeaderStringData[] = { "Select Confidence Intervals" };
                ivjCITableScrollPane
                        .setColumnHeaderStringData(ivjLocal47columnHeaderStringData);
                ivjCITableScrollPane.setColumnHeaderShowing(true);
                ivjCITableScrollPane.setMaximumVisibleRows(6);
                ivjCITableScrollPane.setMinimumVisibleRows(5);
                getCITableScrollPane().setViewportView(getCISelectionTable());
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjCITableScrollPane;
    }

    /**
     * Return the DisplayButton property value.
     * 
     * @return javax.swing.JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JButton getDisplayButton() {
        if (ivjDisplayButton == null) {
            try {
                ivjDisplayButton = new javax.swing.JButton();
                ivjDisplayButton.setName("DisplayButton");
                ivjDisplayButton.setPreferredSize(new java.awt.Dimension(120,
                        25));
                ivjDisplayButton.setText("Display IR");
                ivjDisplayButton
                        .setMinimumSize(new java.awt.Dimension(120, 25));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjDisplayButton;
    }

    /**
     * Return the FERCheck property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getFERCheck() {
        if (ivjFERCheck == null) {
            try {
                ivjFERCheck = new javax.swing.JCheckBox();
                ivjFERCheck.setName("FERCheck");
                ivjFERCheck.setSelected(true);
                ivjFERCheck.setPreferredSize(new java.awt.Dimension(120, 22));
                ivjFERCheck.setText("Forecast error IR");
                ivjFERCheck.setMinimumSize(new java.awt.Dimension(120, 22));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjFERCheck;
    }

    private JSCSArrayTable getImpulsesTable() {
        if (ivjImpulsesTable == null) {
            try {
                ivjImpulsesTable = new JSCSArrayTable();
                ivjImpulsesTable.setName("ImpulsesTable");
                getImpulseTableScrollPane().setColumnHeaderView(
                        ivjImpulsesTable.getTableHeader());
                ivjImpulsesTable.setBounds(0, 0, 125, 30);
                ivjImpulsesTable.setEditable(false);
                ivjImpulsesTable.setRowSelectionAllowed(true);
                ivjImpulsesTable.setDynamicColumnWidth(true);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjImpulsesTable;
    }

    private JSCDataTableScrollPane getImpulseTableScrollPane() {
        if (ivjImpulseTableScrollPane == null) {
            try {
                ivjImpulseTableScrollPane = new JSCDataTableScrollPane();
                ivjImpulseTableScrollPane.setName("ImpulseTableScrollPane");
                ivjImpulseTableScrollPane.setMaximumVisibleColumns(1);
                String ivjLocal52columnHeaderStringData[] = { "Select Impulses" };
                ivjImpulseTableScrollPane
                        .setColumnHeaderStringData(ivjLocal52columnHeaderStringData);
                ivjImpulseTableScrollPane.setColumnHeaderShowing(true);
                ivjImpulseTableScrollPane
                        .setAlignmentX(java.awt.Component.RIGHT_ALIGNMENT);
                ivjImpulseTableScrollPane.setMaximumVisibleRows(6);
                ivjImpulseTableScrollPane.setMinimumVisibleRows(5);
                getImpulseTableScrollPane().setViewportView(getImpulsesTable());
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjImpulseTableScrollPane;
    }

    /**
     * Gets the title of this display. Used for plots.
     * 
     * @return display title string
     */
    private String getIRDisplayTitle() {
        // Assemble the names of the main lines,, first the IR function, then
        // the CIs.
        if (getFERCheck().isSelected() && getFERCheck().isVisible())
            return modelType + " Forecast Error Impulse Responses";
        else if (getOrthCheck().isSelected() && getOrthCheck().isVisible())
            return modelType + " Orthogonal Impulse Responses";
        else
            return modelType + " Impulse Responses";
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
                ivjJPanel1.setPreferredSize(new java.awt.Dimension(100, 160));
                ivjJPanel1.setLayout(new java.awt.GridBagLayout());

                java.awt.GridBagConstraints constraintsCITableScrollPane = new java.awt.GridBagConstraints();
                constraintsCITableScrollPane.gridx = 3;
                constraintsCITableScrollPane.gridy = 0;
                constraintsCITableScrollPane.gridheight = 4;
                constraintsCITableScrollPane.fill = java.awt.GridBagConstraints.VERTICAL;
                constraintsCITableScrollPane.anchor = java.awt.GridBagConstraints.WEST;
                constraintsCITableScrollPane.weightx = 1.0;
                constraintsCITableScrollPane.weighty = 1.0;
                constraintsCITableScrollPane.insets = new java.awt.Insets(5, 5,
                        0, 0);
                getJPanel1().add(getCITableScrollPane(),
                        constraintsCITableScrollPane);

                java.awt.GridBagConstraints constraintsImpulseTableScrollPane = new java.awt.GridBagConstraints();
                constraintsImpulseTableScrollPane.gridx = 0;
                constraintsImpulseTableScrollPane.gridy = 0;
                constraintsImpulseTableScrollPane.gridheight = 4;
                constraintsImpulseTableScrollPane.fill = java.awt.GridBagConstraints.VERTICAL;
                constraintsImpulseTableScrollPane.anchor = java.awt.GridBagConstraints.WEST;
                constraintsImpulseTableScrollPane.weightx = 1.0;
                constraintsImpulseTableScrollPane.weighty = 1.0;
                constraintsImpulseTableScrollPane.insets = new java.awt.Insets(
                        5, 5, 0, 0);
                getJPanel1().add(getImpulseTableScrollPane(),
                        constraintsImpulseTableScrollPane);

                java.awt.GridBagConstraints constraintsResponseTableScrollPane = new java.awt.GridBagConstraints();
                constraintsResponseTableScrollPane.gridx = 1;
                constraintsResponseTableScrollPane.gridy = 0;
                constraintsResponseTableScrollPane.gridheight = 4;
                constraintsResponseTableScrollPane.fill = java.awt.GridBagConstraints.VERTICAL;
                constraintsResponseTableScrollPane.anchor = java.awt.GridBagConstraints.WEST;
                constraintsResponseTableScrollPane.weightx = 1.0;
                constraintsResponseTableScrollPane.weighty = 1.0;
                constraintsResponseTableScrollPane.insets = new java.awt.Insets(
                        5, 5, 0, 5);
                getJPanel1().add(getResponseTableScrollPane(),
                        constraintsResponseTableScrollPane);

                java.awt.GridBagConstraints constraintsDisplayButton = new java.awt.GridBagConstraints();
                constraintsDisplayButton.gridx = 4;
                constraintsDisplayButton.gridy = 1;
                constraintsDisplayButton.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsDisplayButton.insets = new java.awt.Insets(10, 5, 0,
                        20);
                getJPanel1().add(getDisplayButton(), constraintsDisplayButton);

                java.awt.GridBagConstraints constraintsPlotConfigButton = new java.awt.GridBagConstraints();
                constraintsPlotConfigButton.gridx = 4;
                constraintsPlotConfigButton.gridy = 0;
                constraintsPlotConfigButton.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsPlotConfigButton.insets = new java.awt.Insets(10, 5,
                        0, 20);
                getJPanel1().add(getPlotConfigButton(),
                        constraintsPlotConfigButton);

                java.awt.GridBagConstraints constraintsOrthCheck = new java.awt.GridBagConstraints();
                constraintsOrthCheck.gridx = 4;
                constraintsOrthCheck.gridy = 2;
                constraintsOrthCheck.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsOrthCheck.insets = new java.awt.Insets(10, 5, 0, 20);
                getJPanel1().add(getOrthCheck(), constraintsOrthCheck);

                java.awt.GridBagConstraints constraintsFERCheck = new java.awt.GridBagConstraints();
                constraintsFERCheck.gridx = 4;
                constraintsFERCheck.gridy = 3;
                constraintsFERCheck.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsFERCheck.insets = new java.awt.Insets(5, 5, 0, 20);
                getJPanel1().add(getFERCheck(), constraintsFERCheck);

                java.awt.GridBagConstraints constraintsAccumScrollPane = new java.awt.GridBagConstraints();
                constraintsAccumScrollPane.gridx = 2;
                constraintsAccumScrollPane.gridy = 0;
                constraintsAccumScrollPane.gridheight = 4;
                constraintsAccumScrollPane.fill = java.awt.GridBagConstraints.BOTH;
                constraintsAccumScrollPane.weightx = 1.0;
                constraintsAccumScrollPane.weighty = 1.0;
                constraintsAccumScrollPane.insets = new java.awt.Insets(5, 5,
                        5, 5);
                getJPanel1().add(getAccumScrollPane(),
                        constraintsAccumScrollPane);
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

    /**
     * Gets the model type that defines, how the computations are carried out.
     * 
     * @return the current model type
     */
    public ModelTypes getModelType() {
        return modelType;
    }

    /**
     * Return the OrthCheck property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getOrthCheck() {
        if (ivjOrthCheck == null) {
            try {
                ivjOrthCheck = new javax.swing.JCheckBox();
                ivjOrthCheck.setName("OrthCheck");
                ivjOrthCheck.setPreferredSize(new java.awt.Dimension(120, 22));
                ivjOrthCheck.setText("Orthogonal IR");
                ivjOrthCheck.setMinimumSize(new java.awt.Dimension(120, 22));
                ivjOrthCheck.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent evt) {
                        setLegend();
                    }
                });
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjOrthCheck;
    }

    /**
     * Return the PlotConfigButton property value.
     * 
     * @return javax.swing.JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JButton getPlotConfigButton() {
        if (ivjPlotConfigButton == null) {
            try {
                ivjPlotConfigButton = new javax.swing.JButton();
                ivjPlotConfigButton.setName("PlotConfigButton");
                ivjPlotConfigButton.setPreferredSize(new java.awt.Dimension(
                        120, 25));
                ivjPlotConfigButton.setText("Configure Plot");
                ivjPlotConfigButton.setMinimumSize(new java.awt.Dimension(120,
                        25));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjPlotConfigButton;
    }

    private JSCSArrayTable getResponsesTable() {
        if (ivjResponsesTable == null) {
            try {
                ivjResponsesTable = new JSCSArrayTable();
                ivjResponsesTable.setName("ResponsesTable");
                getResponseTableScrollPane().setColumnHeaderView(
                        ivjResponsesTable.getTableHeader());
                ivjResponsesTable.setBounds(0, 0, 125, 30);
                ivjResponsesTable.setEditable(false);
                ivjResponsesTable.setRowSelectionAllowed(true);
                ivjResponsesTable.setDynamicColumnWidth(true);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjResponsesTable;
    }

    private JSCDataTableScrollPane getResponseTableScrollPane() {
        if (ivjResponseTableScrollPane == null) {
            try {
                ivjResponseTableScrollPane = new JSCDataTableScrollPane();
                ivjResponseTableScrollPane.setName("ResponseTableScrollPane");
                ivjResponseTableScrollPane.setMaximumVisibleColumns(1);
                String ivjLocal53columnHeaderStringData[] = { "Select Responses" };
                ivjResponseTableScrollPane
                        .setColumnHeaderStringData(ivjLocal53columnHeaderStringData);
                ivjResponseTableScrollPane.setColumnHeaderShowing(true);
                ivjResponseTableScrollPane
                        .setAlignmentX(java.awt.Component.RIGHT_ALIGNMENT);
                ivjResponseTableScrollPane.setMaximumVisibleRows(6);
                ivjResponseTableScrollPane.setMinimumVisibleRows(5);
                getResponseTableScrollPane().setViewportView(
                        getResponsesTable());
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjResponseTableScrollPane;
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
     * Gets the selected CI names. Returns <code>new String[0]</code> if
     * nothing was selected.
     * 
     * @return string array with CI names
     */
    private String[] getSelCINames() {
        String[] selCINames = new String[getCISelectionTable()
                .getSelectedRowCount()];
        for (int i = 0, k = 0; i < getCISelectionTable().getRowCount(); i++)
            if (getCISelectionTable().isRowSelected(i))
                selCINames[k++] = getCISelectionTable().getModel().getValueAt(
                        i, 0).toString();
        return selCINames;

    }

    /**
     * Gets the variable names for the IA selection.
     * 
     * @return name of the data object in the global symbol table
     */
    public String getVariableNames() {
        return variableNames;
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
        getPlotConfigButton().addActionListener(ivjEventHandler);
        getDisplayButton().addActionListener(ivjEventHandler);
    }

    /**
     * Initialize the class.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initialize() {
        try {
            // user code begin {1}
            // user code end
            setName("IRADisplayPanel");
            setBorder(new BevelBorder(BevelBorder.LOWERED));
            setLayout(new java.awt.BorderLayout());
            setSize(623, 358);
            add(getJPanel1(), "North");
            add(getResultField(), "Center");
            initConnections();
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}
        ButtonGroup bg = new ButtonGroup();
        bg.add(getOrthCheck());
        bg.add(getFERCheck());

        // set the default variable names
        setVariableNames(VARConstants.Ny_Def.name);

        getCISelectionTable().setSymbolName(IRAComputePanel.CI_NAMES.name);

        plotModel.setDefaultValues();

        // to set main line name in any case, further changes
        // when CI selection changes
        setLegend();

        // user code end
    }

    /**
     * Checks number of selected CIs. Checked by plotConfig and display calls.
     * 
     * @return
     */
    private boolean checkNumberOfSelCI() {
        if (plotModel.legend_strings.rows() > 11) {
            StdMessages
                    .errorSpecification("Too many selected confidence intervals. Maximum is 10.");
            return false;
        }
        return true;
    }

    /**
     * Initialize and show plot configuration.
     */
    private void plotConfigButton_ActionEvents() {
        if (!checkNumberOfSelCI())
            return;
        if (plotControlDialog == null) {
            plotControlDialog = new PlotControlDialog();
            plotModel.setDefaultValues();
            plotControlDialog.setPlotControlModel(plotModel);
        }
        setLegend();
        plotControlDialog.setLocationRelativeTo(TopFrameReference
                .getTopFrameRef());
        plotControlDialog.setVisible(true);
    }

    /**
     * Gets string for legends and puts them into <code>plotModel</code>.
     * 
     */
    private void setLegend() {

        String[] selCI = getSelCINames();

        String[] names = new String[1 + selCI.length];

        names[0] = getIRDisplayTitle();

        for (int i = 1; i < names.length; i++)
            names[i] = selCI[i - 1];

        plotModel.legend_strings.setVal(names);

    }

    /**
     * Gets the model type that defines, how the computations are carried out.
     * 
     * @param newModelType
     *            the model type to be used
     * @throws IllegalArgumentException
     *             <code>if (newModelType == null)</code>
     */
    public void setModelType(ModelTypes newModelType) {
        if (newModelType == null)
            throw new IllegalArgumentException("Model type was null.");
        modelType = newModelType;
        getOrthCheck().setVisible(
                modelType != ModelTypes.SVAR && modelType != ModelTypes.SVEC);
        getFERCheck().setVisible(
                modelType != ModelTypes.SVAR && modelType != ModelTypes.SVEC);
    }

    /**
     * Sets the variable names for the IA selection.
     * 
     * @param newVariableNames
     *            name of the data object in the global symbol table
     */
    public void setVariableNames(String newVariableNames) {
        variableNames = newVariableNames;
        getImpulsesTable().setSymbolName(variableNames);
        getResponsesTable().setSymbolName(variableNames);
        getCheckBoxList().setSymbolName(variableNames);

    }
}