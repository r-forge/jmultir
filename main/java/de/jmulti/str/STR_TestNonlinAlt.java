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

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import com.jstatcom.component.ResultField;
import com.jstatcom.component.StdMessages;
import com.jstatcom.engine.PCall;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.JSCSArray;
import com.jstatcom.model.ModelPanel;
import com.jstatcom.table.JSCDataTableScrollPane;
import com.jstatcom.table.JSCSArrayTable;
import com.jstatcom.ts.TSDateRange;

import de.jmulti.proc.STRNonLinTestCall;

/**
 * Panel for testing against the nonlinear alternative in STR analysis.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */

final class STR_TestNonlinAlt extends ModelPanel {
    private static final Logger log = Logger.getLogger(STR_TestNonlinAlt.class);

    private ResultField ivjResultField = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private JButton ivjExecute = null;

    private JPanel ivjControlPanel = null;

    private JSCSArrayTable ivjDataTable_Trans = null;

    private JSCDataTableScrollPane ivjScrollPaneTrans = null;

    private JSCSArrayTable ivjDataTable_Const = null;

    private JSCDataTableScrollPane ivjScrollPaneTable_Const = null;

    private JSCDataTableScrollPane jScrollPane_Phi0 = null;

    private JSCSArrayTable dataTable_Phi0 = null;

    class IvjEventHandler implements java.awt.event.ActionListener {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == STR_TestNonlinAlt.this.getExecute())
                connEtoC1();
        };
    };

    /**
     * CAFPE constructor comment.
     */
    public STR_TestNonlinAlt() {
        super();
        initialize();
    }

    /**
     * connEtoC1: (Execute.action. -->
     * TestNonlinearAlternative.execute_ActionEvents()V)
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
     * Prepares and performs the test for nonlinear alternative,
     */
    private void execute_ActionEvents() {

        int selNum = getDataTable_Trans().getSelectedRowCount();
        if (selNum == 0) {
            String msg = "Please select one or more possible transition variables.";
            StdMessages.infoNothingSelected(msg);
            return;
        }
        for (int i = 0; i < getDataTable_Const().getRowCount(); i++)
            if (getDataTable_Const().isRowSelected(i)
                    && getDataTable_Phi0().isRowSelected(i)) {
                String msg = "A variable can only be selected for theta=0 or phi=0.\n"
                        + "To exclude a variable completely, use the subset restrictions.";
                StdMessages.errorSpecification(msg);
                return;
            }

        // The selected transition variables.
        JSCNArray selTrans = new JSCNArray("transition_lintest",
                getDataTable_Trans().getIntSelectionIndex());
        JSCSArray transNames = global().get(STR_Constants.STR_transNames)
                .getJSCSArray();
        JSCNArray selConst_noConst = new JSCNArray("const_Lintest",
                getDataTable_Const().getIntSelectionIndex());

        JSCNArray selPhi0_noConst = new JSCNArray("phi0_Lintest",
                getDataTable_Phi0().getIntSelectionIndex());

        JSCSArray constNames_noCons = global().get(
                STR_Constants.STR_thet0Names_noConst).getJSCSArray();

        int py = global().get(STR_Constants.STR_py).getJSCInt().intVal();
        int pz = global().get(STR_Constants.STR_pz).getJSCInt().intVal();

        TSDateRange sampleRange = global().get(STR_Constants.STR_drange)
                .getJSCDRange().getTSDateRange();

        // Make call.
        PCall job = new STRNonLinTestCall(global().get(STR_Constants.STR_Y)
                .getJSCNArray(), global().get(STR_Constants.STR_X)
                .getJSCNArray(), global().get(STR_Constants.STR_D)
                .getJSCNArray(), py, pz, sampleRange, selTrans, transNames,
                selConst_noConst, selPhi0_noConst, constNames_noCons, global()
                        .get(STR_Constants.STR_allRes).getJSCNArray());
        job.setSymbolTable(local());
        job.setOutHolder(getResultField());
        job.execute();
    }

    /**
     * Return the JPanel property value.
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
                        180));
                ivjControlPanel.setLayout(new java.awt.GridBagLayout());
                ivjControlPanel
                        .setMinimumSize(new java.awt.Dimension(300, 100));

                java.awt.GridBagConstraints constraintsExecute = new java.awt.GridBagConstraints();
                constraintsExecute.gridx = 3;
                constraintsExecute.gridy = 1;
                constraintsExecute.anchor = java.awt.GridBagConstraints.SOUTHWEST;
                constraintsExecute.weightx = 1.0;
                constraintsExecute.insets = new java.awt.Insets(0, 0, 5, 0);
                java.awt.GridBagConstraints constraintsScrollPaneTrans = new java.awt.GridBagConstraints();
                constraintsScrollPaneTrans.gridx = 0;
                constraintsScrollPaneTrans.gridy = 0;
                constraintsScrollPaneTrans.gridheight = 2;
                constraintsScrollPaneTrans.fill = java.awt.GridBagConstraints.BOTH;
                constraintsScrollPaneTrans.weighty = 1.0;
                constraintsScrollPaneTrans.insets = new java.awt.Insets(0, 10,
                        0, 10);
                getControlPanel().add(getScrollPaneTrans(),
                        constraintsScrollPaneTrans);

                java.awt.GridBagConstraints constraintsScrollPaneTable_Const = new java.awt.GridBagConstraints();
                java.awt.GridBagConstraints consGridBagConstraints1 = new java.awt.GridBagConstraints();
                consGridBagConstraints1.fill = java.awt.GridBagConstraints.BOTH;
                consGridBagConstraints1.weighty = 1.0;
                consGridBagConstraints1.weightx = 0.0D;
                consGridBagConstraints1.gridy = 1;
                consGridBagConstraints1.gridx = 2;
                constraintsScrollPaneTable_Const.gridx = 1;
                constraintsScrollPaneTable_Const.gridy = 0;
                constraintsScrollPaneTable_Const.gridheight = 2;
                constraintsScrollPaneTable_Const.fill = java.awt.GridBagConstraints.BOTH;
                constraintsScrollPaneTable_Const.weighty = 1.0;
                constraintsScrollPaneTable_Const.insets = new java.awt.Insets(
                        0, 10, 0, 10);
                ivjControlPanel.add(getExecute(), constraintsExecute);
                getControlPanel().add(getScrollPaneTable_Const(),
                        constraintsScrollPaneTable_Const);
                // user code begin {1}
                ivjControlPanel.add(getJScrollPane_Phi0(),
                        consGridBagConstraints1);
                TitledBorder title = new TitledBorder(new BevelBorder(
                        BevelBorder.LOWERED), "Testing Linearity Against STR",
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
                ivjExecute.setMaximumSize(new java.awt.Dimension(115, 27));
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

    private JSCSArrayTable getDataTable_Const() {
        if (ivjDataTable_Const == null) {
            try {
                ivjDataTable_Const = new JSCSArrayTable();
                ivjDataTable_Const.setName("DataTable_Const");
                getScrollPaneTable_Const().setColumnHeaderView(
                        ivjDataTable_Const.getTableHeader());
                ivjDataTable_Const.setDynamicColumnWidth(true);
                ivjDataTable_Const
                        .setSymbolName(STR_Constants.STR_thet0Names_noConst.name);
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
                ivjDataTable_Trans.setDynamicColumnWidth(true);
                ivjDataTable_Trans
                        .setSymbolName(STR_Constants.STR_transNames.name);
                ivjDataTable_Trans
                        .setRowSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
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

    private JSCDataTableScrollPane getScrollPaneTable_Const() {
        if (ivjScrollPaneTable_Const == null) {
            try {
                ivjScrollPaneTable_Const = new JSCDataTableScrollPane();
                ivjScrollPaneTable_Const.setName("ScrollPaneTable_Const");
                ivjScrollPaneTable_Const.setMaximumVisibleColumns(1);
                String ivjLocal51columnHeaderStringData[] = { "Theta=0" };
                ivjScrollPaneTable_Const
                        .setColumnHeaderStringData(ivjLocal51columnHeaderStringData);
                ivjScrollPaneTable_Const.setColumnHeaderShowing(true);
                ivjScrollPaneTable_Const.setMaximumVisibleRows(8);
                getScrollPaneTable_Const()
                        .setViewportView(getDataTable_Const());
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjScrollPaneTable_Const;
    }

    private JSCDataTableScrollPane getScrollPaneTrans() {
        if (ivjScrollPaneTrans == null) {
            try {
                ivjScrollPaneTrans = new JSCDataTableScrollPane();
                ivjScrollPaneTrans.setName("ScrollPaneTrans");
                ivjScrollPaneTrans.setMaximumVisibleColumns(1);
                String ivjLocal45columnHeaderStringData[] = { "transition variables" };
                ivjScrollPaneTrans
                        .setColumnHeaderStringData(ivjLocal45columnHeaderStringData);
                ivjScrollPaneTrans.setColumnHeaderShowing(true);
                ivjScrollPaneTrans.setMaximumVisibleRows(8);
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
            // user code end
            setName("TestNonlinearAlternative");
            setLayout(new java.awt.BorderLayout());
            this.add(getControlPanel(), java.awt.BorderLayout.NORTH);
            setSize(579, 400);
            add(getResultField(), "Center");
            initConnections();
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}
        // user code end
    }

    private JSCDataTableScrollPane getJScrollPane_Phi0() {
        if (jScrollPane_Phi0 == null) {
            jScrollPane_Phi0 = new JSCDataTableScrollPane();
            jScrollPane_Phi0.setViewportView(getDataTable_Phi0());
            jScrollPane_Phi0.setMaximumVisibleColumns(1);
            String ivjLocal51columnHeaderStringData[] = { "Phi=0" };
            jScrollPane_Phi0
                    .setColumnHeaderStringData(ivjLocal51columnHeaderStringData);
            jScrollPane_Phi0.setColumnHeaderShowing(true);
            jScrollPane_Phi0.setMaximumVisibleRows(8);

        }
        return jScrollPane_Phi0;
    }

    private JSCSArrayTable getDataTable_Phi0() {
        if (dataTable_Phi0 == null) {
            dataTable_Phi0 = new JSCSArrayTable();
            dataTable_Phi0.setName("DataTable_Phi0");
            getJScrollPane_Phi0().setColumnHeaderView(
                    dataTable_Phi0.getTableHeader());
            dataTable_Phi0.setDynamicColumnWidth(true);
            dataTable_Phi0
                    .setSymbolName(STR_Constants.STR_thet0Names_noConst.name);
            dataTable_Phi0
                    .setRowSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            dataTable_Phi0.setBounds(0, 0, 144, 104);
            dataTable_Phi0.setRowSelectionAllowed(true);
            dataTable_Phi0.setEditable(false);

        }
        return dataTable_Phi0;
    }
}