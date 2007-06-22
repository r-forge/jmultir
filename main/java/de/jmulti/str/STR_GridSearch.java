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

package de.jmulti.str;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.log4j.Logger;

import com.jstatcom.component.NumRangeSelector;
import com.jstatcom.component.NumSelector;
import com.jstatcom.component.ResultField;
import com.jstatcom.component.StdMessages;
import com.jstatcom.component.TopFrameReference;
import com.jstatcom.engine.PCall;
import com.jstatcom.engine.PCallAdapter;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.JSCNumber;
import com.jstatcom.model.ModelPanel;
import com.jstatcom.model.NumberRange;
import com.jstatcom.model.NumberRangeTypes;
import com.jstatcom.table.JSCDataTableScrollPane;
import com.jstatcom.table.JSCSArrayTable;
import com.jstatcom.ts.TSDateRange;
import com.jstatcom.util.UMatrix;

import de.jmulti.proc.STRGridSearchCall;

/**
 * Panel for pre-estimation grid search to find proper start values in STR
 * analysis.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */

final class STR_GridSearch extends ModelPanel {
    private static final Logger log = Logger.getLogger(STR_GridSearch.class);

    private JPanel ivjControlPanel = null;

    private JButton ivjExecute = null;

    private ResultField ivjResultField = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private JSCSArrayTable ivjDataTable_Const = null;

    private JSCSArrayTable ivjDataTable_Trans = null;

    private JSCDataTableScrollPane ivjScrollPaneTrans = null;

    private JSCDataTableScrollPane ivjScrollPaneConst = null;

    private JRadioButton ivjLSTR1 = null;

    private JRadioButton ivjLSTR2 = null;

    private JLabel ivjJLabel = null;

    private JLabel ivjJLabel2 = null;

    private NumSelector ivjCgridSteps = null;

    private JCheckBox ivjGraphicalGrid = null;

    private NumRangeSelector ivjGammaGridStart = null;

    private NumSelector ivjGammaGridSteps = null;

    private JLabel ivjJLabel3 = null;

    private NumSelector ivjNr = null;

    private JLabel ivjJLabel1 = null;

    private NumRangeSelector ivjCgridRange = null;

    class IvjEventHandler implements java.awt.event.ActionListener,
            java.awt.event.ItemListener {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == STR_GridSearch.this.getExecute())
                connEtoC1();
        };

        public void itemStateChanged(java.awt.event.ItemEvent e) {
            if (e.getSource() == STR_GridSearch.this.getGraphicalGrid())
                connEtoC2(e);
        };
    };

    /**
     * StrGrid constructor comment.
     */
    public STR_GridSearch() {
        super();
        initialize();
    }

    /**
     * connEtoC1: (Execute.action. --> StrGrid.execute_ActionEvents()V)
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
     * connEtoC2: (GraphicalGrid.item.itemStateChanged(java.awt.event.ItemEvent)
     * --> StrGrid.graphicalGrid_ItemStateChanged()V)
     * 
     * @param arg1
     *            java.awt.event.ItemEvent
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC2(java.awt.event.ItemEvent arg1) {
        try {
            // user code begin {1}
            // user code end
            this.graphicalGrid_ItemStateChanged();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * Prepares and performs grid search.
     */
    private void execute_ActionEvents() {
        if (getDataTable_Const().getSelectedRowCount() == getDataTable_Const()
                .getRowCount()
                && getDataTable_Const().getSelectedRowCount() > 0) {
            String msg = "You have specified a linear model.\nDeselect one or more variables from the right table.";
            StdMessages.errorSpecification(msg);
            return;
        }
        JSCNArray selConst = new JSCNArray("selConst", getDataTable_Const()
                .getIntSelectionIndex());

        int selTransIndex = getDataTable_Trans().getSelectedRow();
        if (selTransIndex == -1) {
            String msg = "Please select one transition variable.";
            StdMessages.infoNothingSelected(msg);
            return;
        }

        if (getNr().getNumber() > 5 && getLSTR2().isSelected()
                && getGraphicalGrid().isSelected()) {
            String msg1 = "You selected to have more than 5 grid plots.\n";
            msg1 = msg1
                    + "Every plot contains a surface and a contour window.\n";
            msg1 = msg1 + "There will be " + (getNr().getNumber() * 2)
                    + " new windows. Do you want to continue?";
            int option = JOptionPane.showConfirmDialog(TopFrameReference
                    .getTopFrameRef(), msg1, "Information",
                    JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if (option == JOptionPane.NO_OPTION)
                return;
        }
        double[] cinfo = new double[] {
                getCgridRange().getNumberRange().lowerBound,
                getCgridRange().getNumberRange().upperBound,
                getCgridSteps().getNumber() };
        double[] ginfo = new double[] {
                getGammaGridRange().getNumberRange().lowerBound,
                getGammaGridRange().getNumberRange().upperBound,
                getGammaGridSteps().getNumber() };
        JSCNArray ci = new JSCNArray("cinfo", cinfo);
        JSCNArray gi = new JSCNArray("ginfo", ginfo);
        int k = 1;
        if (getLSTR2().isSelected())
            k = 2;

        TSDateRange sampleRange = global().get(STR_Constants.STR_drange)
                .getJSCDRange().getTSDateRange();
        int py = global().get(STR_Constants.STR_py).getJSCInt().intVal();
        int pz = global().get(STR_Constants.STR_pz).getJSCInt().intVal();

        PCall job = new STRGridSearchCall(global().get(STR_Constants.STR_Y)
                .getJSCNArray(), global().get(STR_Constants.STR_X)
                .getJSCNArray(), global().get(STR_Constants.STR_D)
                .getJSCNArray(), py, pz, sampleRange, selTransIndex, global()
                .get(STR_Constants.STR_transNames).getJSCSArray(), selConst,
                global().get(STR_Constants.STR_thet0Names).getJSCSArray(),
                global().get(STR_Constants.STR_allRes).getJSCNArray(), ci, gi,
                getGraphicalGrid().isSelected(), getNr().getIntNumber(), k);

        job.setSymbolTable(local());
        job.setOutHolder(getResultField());

        // store result in global symbol table after finish to set
        // for estimation start values
        job.addPCallListener(new PCallAdapter() {
            public void success() {
                JSCNArray result = local().get(
                        STRGridSearchCall.STR_GRIDSEARCH_RESULT).getJSCNArray();
                global().get(STR_Constants.STR_GAMMA).setJSCData(
                        new JSCNumber("toset", result.doubleAt(0, 1)));
                global().get(STR_Constants.STR_C1).setJSCData(
                        new JSCNumber("toset", result.doubleAt(0, 2)));
                global().get(STR_Constants.STR_C2).setJSCData(
                        new JSCNumber("toset", result.doubleAt(0, 3)));

            }
        });
        job.execute();

    }

    private NumRangeSelector getCgridRange() {
        if (ivjCgridRange == null) {
            try {
                ivjCgridRange = new NumRangeSelector();
                ivjCgridRange.setName("CgridRange");
                ivjCgridRange.setPrecision(4);
                ivjCgridRange.setRangeBounds("0.1,10");
                ivjCgridRange.setPreferredSize(new java.awt.Dimension(150, 21));
                ivjCgridRange.setMinimumSize(new java.awt.Dimension(150, 21));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjCgridRange;
    }

    private NumSelector getCgridSteps() {
        if (ivjCgridSteps == null) {
            try {
                ivjCgridSteps = new NumSelector();
                ivjCgridSteps.setName("CgridSteps");
                ivjCgridSteps.setPreferredSize(new java.awt.Dimension(55, 21));
                ivjCgridSteps.setNumber(30.0);
                ivjCgridSteps.setMinimumSize(new java.awt.Dimension(55, 21));
                ivjCgridSteps.setRangeExpr("[10,1000]");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjCgridSteps;
    }

    /**
     * Return the ControlPanel property value.
     * 
     * @return javax.swing.JPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JPanel getControlPanel() {
        if (ivjControlPanel == null) {
            try {
                ivjControlPanel = new javax.swing.JPanel();
                ivjControlPanel.setName("ControlPanel");
                ivjControlPanel.setPreferredSize(new java.awt.Dimension(300,
                        230));
                ivjControlPanel.setLayout(new java.awt.GridBagLayout());
                ivjControlPanel
                        .setMinimumSize(new java.awt.Dimension(300, 180));

                java.awt.GridBagConstraints constraintsExecute = new java.awt.GridBagConstraints();
                constraintsExecute.gridx = 2;
                constraintsExecute.gridy = 8;
                constraintsExecute.gridwidth = 4;
                constraintsExecute.anchor = java.awt.GridBagConstraints.SOUTH;
                constraintsExecute.weightx = 1.0;
                constraintsExecute.insets = new java.awt.Insets(0, 0, 10, 0);
                java.awt.GridBagConstraints constraintsScrollPaneTrans = new java.awt.GridBagConstraints();
                constraintsScrollPaneTrans.gridx = 0;
                constraintsScrollPaneTrans.gridy = 0;
                constraintsScrollPaneTrans.gridheight = 9;
                constraintsScrollPaneTrans.fill = java.awt.GridBagConstraints.BOTH;
                constraintsScrollPaneTrans.weighty = 1.0;
                constraintsScrollPaneTrans.insets = new java.awt.Insets(0, 10,
                        0, 10);
                getControlPanel().add(getScrollPaneTrans(),
                        constraintsScrollPaneTrans);

                java.awt.GridBagConstraints constraintsScrollPaneConst = new java.awt.GridBagConstraints();
                constraintsScrollPaneConst.gridx = 1;
                constraintsScrollPaneConst.gridy = 0;
                constraintsScrollPaneConst.gridheight = 9;
                constraintsScrollPaneConst.fill = java.awt.GridBagConstraints.BOTH;
                constraintsScrollPaneConst.weighty = 1.0;
                constraintsScrollPaneConst.insets = new java.awt.Insets(0, 10,
                        0, 10);
                getControlPanel().add(getScrollPaneConst(),
                        constraintsScrollPaneConst);

                java.awt.GridBagConstraints constraintsLSTR2 = new java.awt.GridBagConstraints();
                constraintsLSTR2.gridx = 3;
                constraintsLSTR2.gridy = 0;
                constraintsLSTR2.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsLSTR2.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsLSTR2.insets = new java.awt.Insets(0, 0, 0, 0);
                java.awt.GridBagConstraints constraintsLSTR1 = new java.awt.GridBagConstraints();
                constraintsLSTR1.gridx = 2;
                constraintsLSTR1.gridy = 0;
                constraintsLSTR1.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsLSTR1.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsLSTR1.insets = new java.awt.Insets(0, 0, 0, 0);
                java.awt.GridBagConstraints constraintsJLabel = new java.awt.GridBagConstraints();
                constraintsJLabel.gridx = 2;
                constraintsJLabel.gridy = 3;
                constraintsJLabel.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel.anchor = java.awt.GridBagConstraints.NORTHWEST;
                java.awt.GridBagConstraints constraintsJLabel2 = new java.awt.GridBagConstraints();
                constraintsJLabel2.gridx = 2;
                constraintsJLabel2.gridy = 5;
                constraintsJLabel2.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel2.anchor = java.awt.GridBagConstraints.NORTHWEST;
                java.awt.GridBagConstraints constraintsCgridRange = new java.awt.GridBagConstraints();
                constraintsCgridRange.gridx = 3;
                constraintsCgridRange.gridy = 3;
                constraintsCgridRange.gridwidth = 2;
                constraintsCgridRange.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsCgridRange.anchor = java.awt.GridBagConstraints.NORTHEAST;
                constraintsCgridRange.insets = new java.awt.Insets(2, 0, 0, 5);
                java.awt.GridBagConstraints constraintsGammaGridStart = new java.awt.GridBagConstraints();
                constraintsGammaGridStart.gridx = 3;
                constraintsGammaGridStart.gridy = 5;
                constraintsGammaGridStart.gridwidth = 2;
                constraintsGammaGridStart.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsGammaGridStart.anchor = java.awt.GridBagConstraints.NORTHEAST;
                constraintsGammaGridStart.insets = new java.awt.Insets(2, 0, 0,
                        5);
                java.awt.GridBagConstraints constraintsCgridSteps = new java.awt.GridBagConstraints();
                constraintsCgridSteps.gridx = 5;
                constraintsCgridSteps.gridy = 3;
                constraintsCgridSteps.anchor = java.awt.GridBagConstraints.NORTHWEST;
                java.awt.GridBagConstraints constraintsGammaGridSteps = new java.awt.GridBagConstraints();
                constraintsGammaGridSteps.gridx = 5;
                constraintsGammaGridSteps.gridy = 5;
                constraintsGammaGridSteps.anchor = java.awt.GridBagConstraints.NORTHWEST;
                java.awt.GridBagConstraints constraintsGraphicalGrid = new java.awt.GridBagConstraints();
                constraintsGraphicalGrid.gridx = 2;
                constraintsGraphicalGrid.gridy = 6;
                constraintsGraphicalGrid.gridwidth = 4;
                constraintsGraphicalGrid.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsGraphicalGrid.insets = new java.awt.Insets(5, 0, 5,
                        0);
                java.awt.GridBagConstraints constraintsNr = new java.awt.GridBagConstraints();
                constraintsNr.gridx = 5;
                constraintsNr.gridy = 7;
                constraintsNr.anchor = java.awt.GridBagConstraints.NORTHWEST;
                getControlPanel().add(getNr(), constraintsNr);

                java.awt.GridBagConstraints constraintsJLabel3 = new java.awt.GridBagConstraints();
                constraintsJLabel3.gridx = 2;
                constraintsJLabel3.gridy = 7;
                constraintsJLabel3.gridwidth = 3;
                constraintsJLabel3.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel3.anchor = java.awt.GridBagConstraints.NORTHWEST;
                getControlPanel().add(getJLabel3(), constraintsJLabel3);

                java.awt.GridBagConstraints constraintsJLabel1 = new java.awt.GridBagConstraints();
                constraintsJLabel2.insets = new java.awt.Insets(2, 0, 0, 0);
                constraintsGammaGridSteps.insets = new java.awt.Insets(2, 0, 0,
                        0);
                constraintsJLabel1.gridx = 5;
                constraintsJLabel1.gridy = 0;
                constraintsJLabel1.anchor = java.awt.GridBagConstraints.WEST;
                constraintsCgridSteps.insets = new java.awt.Insets(2, 0, 0, 0);
                constraintsJLabel.insets = new java.awt.Insets(2, 0, 0, 0);
                ivjControlPanel.add(getExecute(), constraintsExecute);
                ivjControlPanel.add(getLSTR2(), constraintsLSTR2);
                ivjControlPanel.add(getLSTR1(), constraintsLSTR1);
                ivjControlPanel.add(getJLabel(), constraintsJLabel);
                ivjControlPanel.add(getCgridRange(), constraintsCgridRange);
                ivjControlPanel.add(getCgridSteps(), constraintsCgridSteps);
                ivjControlPanel.add(getGraphicalGrid(),
                        constraintsGraphicalGrid);
                ivjControlPanel.add(getJLabel2(), constraintsJLabel2);
                ivjControlPanel.add(getGammaGridRange(),
                        constraintsGammaGridStart);
                ivjControlPanel.add(getGammaGridSteps(),
                        constraintsGammaGridSteps);
                getControlPanel().add(getJLabel1(), constraintsJLabel1);
                // user code begin {1}

                TitledBorder title = new TitledBorder(new BevelBorder(
                        BevelBorder.LOWERED), "STR Grid Search",
                        TitledBorder.RIGHT, TitledBorder.TOP);
                getControlPanel().setBorder(title);

                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjControlPanel;
    }

    /**
     * Return the Execute property value.
     * 
     * @return javax.swing.JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JButton getExecute() {
        if (ivjExecute == null) {
            try {
                ivjExecute = new javax.swing.JButton();
                ivjExecute.setName("Execute");
                ivjExecute.setText("Execute");
                ivjExecute.setMaximumSize(new java.awt.Dimension(200, 27));
                ivjExecute.setPreferredSize(new java.awt.Dimension(115, 27));
                ivjExecute.setMinimumSize(new java.awt.Dimension(115, 27));
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

    private NumRangeSelector getGammaGridRange() {
        if (ivjGammaGridStart == null) {
            try {
                ivjGammaGridStart = new NumRangeSelector();
                ivjGammaGridStart.setName("GammaGridStart");
                ivjGammaGridStart.setPrecision(2);
                ivjGammaGridStart.setRangeBounds("0.5,10");
                ivjGammaGridStart.setOverallRangeExpr("(0,1000]");
                ivjGammaGridStart.setPreferredSize(new java.awt.Dimension(150,
                        21));
                ivjGammaGridStart
                        .setMinimumSize(new java.awt.Dimension(150, 21));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjGammaGridStart;
    }

    private NumSelector getGammaGridSteps() {
        if (ivjGammaGridSteps == null) {
            try {
                ivjGammaGridSteps = new NumSelector();
                ivjGammaGridSteps.setName("GammaGridSteps");
                ivjGammaGridSteps.setPreferredSize(new java.awt.Dimension(55,
                        21));
                ivjGammaGridSteps.setNumber(30.0);
                ivjGammaGridSteps
                        .setMinimumSize(new java.awt.Dimension(55, 21));
                ivjGammaGridSteps.setRangeExpr("[10,1000]");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjGammaGridSteps;
    }

    private JSCSArrayTable getDataTable_Const() {
        if (ivjDataTable_Const == null) {
            try {
                ivjDataTable_Const = new JSCSArrayTable();
                ivjDataTable_Const.setName("DataTable_Const");
                getScrollPaneConst().setColumnHeaderView(
                        ivjDataTable_Const.getTableHeader());
                ivjDataTable_Const.setColumnWidth(120);
                ivjDataTable_Const
                        .setSymbolName(STR_Constants.STR_thet0Names.name);
                ivjDataTable_Const
                        .setRowSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
                ivjDataTable_Const.setBounds(0, 0, 144, 104);
                ivjDataTable_Const.setRowSelectionAllowed(true);
                ivjDataTable_Const.setEditable(false);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjDataTable_Const;
    }

    private JSCSArrayTable getDataTable_Trans() {
        if (ivjDataTable_Trans == null) {
            try {
                ivjDataTable_Trans = new JSCSArrayTable();
                ivjDataTable_Trans.setName("DataTable_Trans");
                getScrollPaneTrans().setColumnHeaderView(
                        ivjDataTable_Trans.getTableHeader());
                ivjDataTable_Trans.setColumnWidth(120);
                ivjDataTable_Trans
                        .setSymbolName(STR_Constants.STR_transNames.name);
                ivjDataTable_Trans
                        .setRowSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                ivjDataTable_Trans.setBounds(0, 0, 144, 104);
                ivjDataTable_Trans.setRowSelectionAllowed(true);
                ivjDataTable_Trans.setEditable(false);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjDataTable_Trans;
    }

    /**
     * Return the GraphicalGrid property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getGraphicalGrid() {
        if (ivjGraphicalGrid == null) {
            try {
                ivjGraphicalGrid = new javax.swing.JCheckBox();
                ivjGraphicalGrid.setName("GraphicalGrid");
                ivjGraphicalGrid
                        .setPreferredSize(new java.awt.Dimension(50, 21));
                ivjGraphicalGrid.setText("Graphical representation of grid");
                ivjGraphicalGrid.setMinimumSize(new java.awt.Dimension(50, 21));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjGraphicalGrid;
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
                ivjJLabel.setPreferredSize(new java.awt.Dimension(65, 21));
                ivjJLabel.setText("Grid c1,c2");
                ivjJLabel.setMinimumSize(new java.awt.Dimension(45, 21));
                ivjJLabel.setMaximumSize(new java.awt.Dimension(45, 21));
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
                ivjJLabel1.setText("Step number");
                // user code begin {1}
                ivjJLabel1.setPreferredSize(new java.awt.Dimension(73, 24));
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
                ivjJLabel2.setPreferredSize(new java.awt.Dimension(85, 21));
                ivjJLabel2.setText("Grid gamma");
                ivjJLabel2.setMinimumSize(new java.awt.Dimension(80, 21));
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
                ivjJLabel3.setPreferredSize(new java.awt.Dimension(75, 21));
                ivjJLabel3.setText("Number of gridplots for LSTR2");
                ivjJLabel3.setMinimumSize(new java.awt.Dimension(65, 21));
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
     * Return the LSTR1 property value.
     * 
     * @return javax.swing.JRadioButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JRadioButton getLSTR1() {
        if (ivjLSTR1 == null) {
            try {
                ivjLSTR1 = new javax.swing.JRadioButton();
                ivjLSTR1.setName("LSTR1");
                ivjLSTR1.setSelected(true);
                ivjLSTR1.setText("LSTR1");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjLSTR1;
    }

    /**
     * Return the LSTR2 property value.
     * 
     * @return javax.swing.JRadioButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JRadioButton getLSTR2() {
        if (ivjLSTR2 == null) {
            try {
                ivjLSTR2 = new javax.swing.JRadioButton();
                ivjLSTR2.setName("LSTR2");
                ivjLSTR2.setText("LSTR2");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjLSTR2;
    }

    private NumSelector getNr() {
        if (ivjNr == null) {
            try {
                ivjNr = new NumSelector();
                ivjNr.setName("Nr");
                ivjNr.setNumber(4.0);
                ivjNr.setRangeExpr("[1,20]");
                ivjNr.setPreferredSize(new java.awt.Dimension(55, 21));
                ivjNr.setMinimumSize(new java.awt.Dimension(55, 21));
                ivjNr.setEnabled(false);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjNr;
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

    private JSCDataTableScrollPane getScrollPaneConst() {
        if (ivjScrollPaneConst == null) {
            try {
                ivjScrollPaneConst = new JSCDataTableScrollPane();
                ivjScrollPaneConst.setName("ScrollPaneConst");
                ivjScrollPaneConst.setMaximumVisibleColumns(1);
                String ivjLocal45columnHeaderStringData[] = { "theta=0" };
                ivjScrollPaneConst
                        .setColumnHeaderStringData(ivjLocal45columnHeaderStringData);
                ivjScrollPaneConst.setColumnHeaderShowing(true);
                ivjScrollPaneConst.setMaximumVisibleRows(10);
                getScrollPaneConst().setViewportView(getDataTable_Const());
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjScrollPaneConst;
    }

    private JSCDataTableScrollPane getScrollPaneTrans() {
        if (ivjScrollPaneTrans == null) {
            try {
                ivjScrollPaneTrans = new JSCDataTableScrollPane();
                ivjScrollPaneTrans.setName("ScrollPaneTrans");
                ivjScrollPaneTrans.setMaximumVisibleColumns(1);
                String ivjLocal45columnHeaderStringData[] = { "transition variable" };
                ivjScrollPaneTrans
                        .setColumnHeaderStringData(ivjLocal45columnHeaderStringData);
                ivjScrollPaneTrans.setColumnHeaderShowing(true);
                ivjScrollPaneTrans.setMaximumVisibleRows(10);
                getScrollPaneTrans().setViewportView(getDataTable_Trans());
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjScrollPaneTrans;
    }

    /**
     * Comment
     */
    private void graphicalGrid_ItemStateChanged() {
        getNr().setEnabled(getGraphicalGrid().isSelected());
        return;
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
        getGraphicalGrid().addItemListener(ivjEventHandler);
    }

    /**
     * Initialize the class.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initialize() {
        try {
            // user code begin {1}
            ButtonGroup selectGroup = new javax.swing.ButtonGroup();
            selectGroup.add(getLSTR1());
            selectGroup.add(getLSTR2());

            // user code end
            setName("StrGrid");
            setLayout(new java.awt.BorderLayout());
            setSize(564, 323);
            add(getResultField(), "Center");
            add(getControlPanel(), "North");
            initConnections();
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}

        // Sets the gridrange for C according to the minimum and
        // maximum of the selected transition variable.
        getDataTable_Trans().getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent e) {
                        if (getDataTable_Trans().getSelectedRowCount() == 0)
                            return;
                        int sel = getDataTable_Trans().getSelectedRow();
                        double[][] x = global().get(STR_Constants.STR_X)
                                .getJSCNArray().getTransposed();

                        double[] min = UMatrix.minc(x);
                        double[] max = UMatrix.maxc(x);
                        getCgridRange().setNumberRange(
                                new NumberRange(min[sel], max[sel],
                                        NumberRangeTypes.CLOSED));
                    }
                });

        // user code end
    }
}