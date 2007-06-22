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
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;

import org.apache.log4j.Logger;

import com.jstatcom.component.StdMessages;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.SymbolTable;
import com.jstatcom.table.JSCDataTableScrollPane;
import com.jstatcom.table.JSCSArrayTable;
import com.jstatcom.util.UMatrix;


/**
 * Dialog for configuration of remaining nonlinearity tests in STR analysis.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */

final class STR_ConfigRemNonlinTest extends JDialog {
    private static final Logger log = Logger
            .getLogger(STR_ConfigRemNonlinTest.class);

    private JPanel ivjJDialogContentPane = null;

    private JPanel ivjJPanel1 = null;

    private JPanel ivjJPanel2 = null;

    private SymbolTable symbolTable = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private JButton ivjOK = null;

    private JSCSArrayTable ivjDataTable_Const = null;

    private JSCSArrayTable ivjDataTable_Trans = null;

    private JLabel ivjJLabel1 = null;

    private JLabel ivjJLabel2 = null;

    private JSCDataTableScrollPane ivjScrollPaneConst = null;

    private JSCDataTableScrollPane ivjScrollPaneTrans = null;

    class IvjEventHandler implements java.awt.event.ActionListener {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == STR_ConfigRemNonlinTest.this.getOK())
                connEtoC1();
        };
    };

    /**
     * StrRemnonlinConfig constructor comment.
     */
    public STR_ConfigRemNonlinTest() {
        super();
        initialize();
    }

    /**
     * StrRemnonlinConfig constructor comment.
     * 
     * @param owner
     *            java.awt.Frame
     * @param modal
     *            boolean
     */
    public STR_ConfigRemNonlinTest(java.awt.Frame owner, boolean modal,
            SymbolTable st) {
        super(owner, modal);
        symbolTable = st;
        initialize();
    }

    /**
     * connEtoC1: (OK.action. --> StrRemnonlinConfig.oK_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC1() {
        try {
            // user code begin {1}
            // user code end
            this.oK_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
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
                        .setSymbolName(STR_Constants.STR_thet0Names_noConst.name);
                ivjDataTable_Const.setSymbolTable(symbolTable);
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
                        .setSymbolName(STR_Constants.STR_transNames_noTrend.name);
                ivjDataTable_Trans.setSymbolTable(symbolTable);
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
     * Return the JDialogContentPane property value.
     * 
     * @return javax.swing.JPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JPanel getJDialogContentPane() {
        if (ivjJDialogContentPane == null) {
            try {
                ivjJDialogContentPane = new javax.swing.JPanel();
                ivjJDialogContentPane.setName("JDialogContentPane");
                ivjJDialogContentPane.setLayout(new java.awt.BorderLayout());
                getJDialogContentPane().add(getJPanel1(), "Center");
                getJDialogContentPane().add(getJPanel2(), "South");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJDialogContentPane;
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
                ivjJLabel1.setText("Transition variable of 2nd STR");
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
                ivjJLabel2.setText("Exclude from 2nd nonlinear part");
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
                ivjJPanel1.setBorder(new BevelBorder(BevelBorder.LOWERED));
                ivjJPanel1.setLayout(new java.awt.GridBagLayout());

                java.awt.GridBagConstraints constraintsScrollPaneTrans = new java.awt.GridBagConstraints();
                constraintsScrollPaneTrans.gridx = 1;
                constraintsScrollPaneTrans.gridy = 2;
                constraintsScrollPaneTrans.fill = java.awt.GridBagConstraints.BOTH;
                constraintsScrollPaneTrans.weighty = 1.0;
                constraintsScrollPaneTrans.insets = new java.awt.Insets(5, 10,
                        0, 10);
                getJPanel1().add(getScrollPaneTrans(),
                        constraintsScrollPaneTrans);

                java.awt.GridBagConstraints constraintsScrollPaneConst = new java.awt.GridBagConstraints();
                constraintsScrollPaneConst.gridx = 2;
                constraintsScrollPaneConst.gridy = 2;
                constraintsScrollPaneConst.fill = java.awt.GridBagConstraints.BOTH;
                constraintsScrollPaneConst.weighty = 1.0;
                constraintsScrollPaneConst.insets = new java.awt.Insets(5, 10,
                        0, 10);
                getJPanel1().add(getScrollPaneConst(),
                        constraintsScrollPaneConst);

                java.awt.GridBagConstraints constraintsJLabel1 = new java.awt.GridBagConstraints();
                constraintsJLabel1.gridx = 1;
                constraintsJLabel1.gridy = 0;
                constraintsJLabel1.insets = new java.awt.Insets(5, 10, 0, 10);
                getJPanel1().add(getJLabel1(), constraintsJLabel1);

                java.awt.GridBagConstraints constraintsJLabel2 = new java.awt.GridBagConstraints();
                constraintsJLabel2.gridx = 2;
                constraintsJLabel2.gridy = 0;
                constraintsJLabel2.insets = new java.awt.Insets(5, 10, 0, 10);
                getJPanel1().add(getJLabel2(), constraintsJLabel2);
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
     * Return the JPanel2 property value.
     * 
     * @return javax.swing.JPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JPanel getJPanel2() {
        if (ivjJPanel2 == null) {
            try {
                ivjJPanel2 = new javax.swing.JPanel();
                ivjJPanel2.setName("JPanel2");
                ivjJPanel2.setPreferredSize(new java.awt.Dimension(0, 50));
                ivjJPanel2.setLayout(new java.awt.FlowLayout());
                ivjJPanel2.setMinimumSize(new java.awt.Dimension(0, 50));
                getJPanel2().add(getOK(), getOK().getName());
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJPanel2;
    }

    /**
     * Return the OK property value.
     * 
     * @return javax.swing.JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JButton getOK() {
        if (ivjOK == null) {
            try {
                ivjOK = new javax.swing.JButton();
                ivjOK.setName("OK");
                ivjOK.setPreferredSize(new java.awt.Dimension(100, 27));
                ivjOK.setText("OK");
                ivjOK.setMaximumSize(new java.awt.Dimension(100, 27));
                ivjOK.setMinimumSize(new java.awt.Dimension(100, 27));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjOK;
    }

    private JSCDataTableScrollPane getScrollPaneConst() {
        if (ivjScrollPaneConst == null) {
            try {
                ivjScrollPaneConst = new JSCDataTableScrollPane();
                ivjScrollPaneConst.setName("ScrollPaneConst");
                ivjScrollPaneConst.setMaximumVisibleColumns(1);
                String ivjLocal45columnHeaderStringData[] = { "theta2=0" };
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
        getOK().addActionListener(ivjEventHandler);
    }

    /**
     * Initialize the class.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initialize() {
        try {
            // user code begin {1}
            // user code end
            setName("StrRemnonlinConfig");
            setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
            setSize(531, 259);
            setTitle("Configure Test of No Remaining Nonlinearity ");
            setContentPane(getJDialogContentPane());
            initConnections();
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}
        // user code end
    }

    /**
     * Comment
     */
    private void oK_ActionEvents() {
        if (getDataTable_Const().getSelectedRowCount() == getDataTable_Const()
                .getRowCount()) {
            String msg = "You have excluded all variables from the 2nd nonlinear part.\nDeselect one or more variables from the right table.";
            StdMessages.errorSpecification(msg);
            return;
        }
        if (getDataTable_Trans().getSelectedRowCount() == 0) {
            String msg = "Please select at least one transition variable from the left table.";
            StdMessages.infoNothingSelected(msg);
            return;
        }
        setVisible(false);
    }

    /**
     * Gets the selected indices for variables with the restriction Theta = 0.
     * 
     * @return zero based index data: 1 - restricted; 0 - not restricted
     */
    public JSCNArray getThet0Sel() {
        return new JSCNArray("thet0Sel_remnonlin", UMatrix
                .toDoubleMatrix(getDataTable_Const().getIntSelectionIndex()));

    }

    /**
     * Gets the selected index for s2_t in auxiliary regression.
     * 
     * @return zero based index data: 1 - selected; 0 - not selected
     */
    public JSCNArray getTrans() {
        return new JSCNArray("trans_remnonlin", UMatrix
                .toDoubleMatrix(getDataTable_Trans().getIntSelectionIndex()));
    }
}