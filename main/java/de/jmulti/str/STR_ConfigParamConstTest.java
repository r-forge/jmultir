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
 * Dialog for configuration of parameter constancy tests in STR analysis.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
final class STR_ConfigParamConstTest extends JDialog {
    private static final Logger log = Logger
            .getLogger(STR_ConfigParamConstTest.class);

    private SymbolTable symbolTable = null;

    private JPanel ivjJDialogContentPane = null;

    private JPanel ivjJPanel1 = null;

    private JPanel ivjJPanel2 = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private JButton ivjOK = null;

    private JSCSArrayTable ivjDataTable_Trans = null;

    private JSCDataTableScrollPane ivjScrollPaneTrans = null;

    private JLabel ivjJLabel = null;

    class IvjEventHandler implements java.awt.event.ActionListener {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == STR_ConfigParamConstTest.this.getOK())
                connEtoC1();
        };
    };

    /**
     * StrParaconsConfig constructor comment.
     */
    public STR_ConfigParamConstTest() {
        super();
        initialize();
    }

    /**
     * StrParaconsConfig constructor comment.
     */
    public STR_ConfigParamConstTest(java.awt.Frame owner, boolean modal,
            SymbolTable st) {
        super(owner, modal);
        symbolTable = st;
        initialize();
    }

    /**
     * connEtoC1: (OK.action. --> StrParaconsConfig.oK_ActionEvents()V)
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

    private JSCSArrayTable getDataTable_Ex() {
        if (ivjDataTable_Trans == null) {
            try {
                ivjDataTable_Trans = new JSCSArrayTable();
                ivjDataTable_Trans.setName("DataTable_Trans");
                getScrollPaneEx().setColumnHeaderView(
                        ivjDataTable_Trans.getTableHeader());
                ivjDataTable_Trans.setColumnWidth(120);
                ivjDataTable_Trans
                        .setSymbolName(STR_Constants.STR_thet0Names.name);
                ivjDataTable_Trans.setSymbolTable(symbolTable);
                ivjDataTable_Trans
                        .setRowSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
                ivjDataTable_Trans.setBounds(0, 0, 144, 104);
                ivjDataTable_Trans.setRowSelectionAllowed(true);
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
                ivjJLabel.setText("Select Variables to Exclude");
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
                constraintsScrollPaneTrans.gridy = 1;
                constraintsScrollPaneTrans.fill = java.awt.GridBagConstraints.BOTH;
                constraintsScrollPaneTrans.weighty = 1.0;
                constraintsScrollPaneTrans.insets = new java.awt.Insets(5, 0,
                        0, 0);
                getJPanel1().add(getScrollPaneEx(), constraintsScrollPaneTrans);

                java.awt.GridBagConstraints constraintsJLabel = new java.awt.GridBagConstraints();
                constraintsJLabel.gridx = 1;
                constraintsJLabel.gridy = 0;
                constraintsJLabel.insets = new java.awt.Insets(5, 0, 0, 0);
                getJPanel1().add(getJLabel(), constraintsJLabel);
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

    private JSCDataTableScrollPane getScrollPaneEx() {
        if (ivjScrollPaneTrans == null) {
            try {
                ivjScrollPaneTrans = new JSCDataTableScrollPane();
                ivjScrollPaneTrans.setName("ScrollPaneTrans");
                ivjScrollPaneTrans.setMaximumVisibleColumns(1);
                String ivjLocal45columnHeaderStringData[] = { "variables" };
                ivjScrollPaneTrans
                        .setColumnHeaderStringData(ivjLocal45columnHeaderStringData);
                ivjScrollPaneTrans.setColumnHeaderShowing(true);
                ivjScrollPaneTrans.setMaximumVisibleRows(10);
                getScrollPaneEx().setViewportView(getDataTable_Ex());
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
            setName("StrParaconsConfig");
            setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
            setSize(350, 240);
            setTitle("Exclude Variables from Nonconstancy Test");
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
        if (getDataTable_Ex().getSelectedRowCount() == getDataTable_Ex()
                .getRowCount()) {
            String msg = "Please deselect one or more variables.";
            StdMessages.errorSpecification(msg);
            return;
        }
        setVisible(false);
        return;
    }

    /**
     * Gets the index of variables excluded from the parameter constancy test.
     * 
     * @return zero based index indicating which variables in X to exclude
     */
    public JSCNArray getParaconsEx() {
        return new JSCNArray("paracons_ex", UMatrix
                .toDoubleMatrix(getDataTable_Ex().getIntSelectionIndex()));
    }
}