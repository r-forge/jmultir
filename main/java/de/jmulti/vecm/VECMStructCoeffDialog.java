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

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import org.apache.log4j.Logger;

import com.jstatcom.component.MultiLineLabel;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.JSCSArray;
import com.jstatcom.model.JSCTypeDef;
import com.jstatcom.model.JSCTypes;
import com.jstatcom.model.ModelPanel;
import com.jstatcom.model.Scope;
import com.jstatcom.model.SymbolTable;
import com.jstatcom.table.JSCCellRendererTypes;
import com.jstatcom.table.JSCDataTableScrollPane;
import com.jstatcom.table.JSCMouseListenerTypes;
import com.jstatcom.table.JSCNArrayTable;
import com.jstatcom.table.JSCSArrayTable;
import com.jstatcom.util.UMatrix;

/**
 * Dialog to specify the restrictions on the matrix of structural coefficients.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
public final class VECMStructCoeffDialog extends JDialog {

    private static final Logger log = Logger
            .getLogger(VECMStructCoeffDialog.class);

    private JSCTypeDef s_g0 = new JSCTypeDef("S_G0", JSCTypes.NARRAY,
            "the structural restriction matrix");

    private JSCTypeDef s_ny = new JSCTypeDef("s_ny", JSCTypes.SARRAY,
            "the structural equation names");

    private SymbolTable symbolTable = null;

    private ModelPanel ivjJDialogContentPane = null;

    private JPanel ivjJPanel1 = null;

    private JPanel ivjJPanel2 = null;

    private JButton ivjOKButton = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private JSCSArrayTable ivjEQTable = null;

    private JSCDataTableScrollPane ivjDataTableScrollPane = null;

    private JSCNArrayTable ivjCoeffTable = null;

    private JSCDataTableScrollPane ivjDataTableScrollPane1 = null;

    private MultiLineLabel ivjMultiLineLabel1 = null;

    class IvjEventHandler implements java.awt.event.ActionListener {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == VECMStructCoeffDialog.this.getOKButton())
                connEtoC1();
        };
    };

    /**
     * VECMStructCoeffDialog constructor comment.
     */
    public VECMStructCoeffDialog() {
        super();
        initialize();
    }

    /**
     * VECMStructCoeffDialog constructor comment.
     * 
     * @param owner
     *            java.awt.Frame
     * @param modal
     *            boolean
     */
    public VECMStructCoeffDialog(java.awt.Frame owner, boolean modal) {
        super(owner, modal);
        initialize();
    }

    /**
     * connEtoC1: (OKButton.action. -->
     * VECMStructCoeffDialog.oKButton_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC1() {
        try {
            // user code begin {1}
            // user code end
            this.oKButton_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    private JSCNArrayTable getCoeffTable() {
        if (ivjCoeffTable == null) {
            try {
                ivjCoeffTable = new JSCNArrayTable();
                ivjCoeffTable.setName("CoeffTable");
                getDataTableScrollPane1().setColumnHeaderView(
                        ivjCoeffTable.getTableHeader());
                ivjCoeffTable.setSymbolScope(Scope.LOCAL);
                ivjCoeffTable.setCellRenderer(JSCCellRendererTypes.DIAGONAL_01);
                ivjCoeffTable.setColumnWidth(50);
                ivjCoeffTable
                        .setMouseListener(JSCMouseListenerTypes.DIAGONAL_01);
                ivjCoeffTable.setMaximumColumnWidth(50);
                ivjCoeffTable.setSymbolName(s_g0.name);
                ivjCoeffTable.setBounds(0, 0, 144, 104);
                ivjCoeffTable.setEditable(false);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjCoeffTable;
    }

    private JSCSArrayTable getEQTable() {
        if (ivjEQTable == null) {
            try {
                ivjEQTable = new JSCSArrayTable();
                ivjEQTable.setName("EQTable");
                getDataTableScrollPane().setColumnHeaderView(
                        ivjEQTable.getTableHeader());
                ivjEQTable.setSymbolScope(Scope.LOCAL);
                ivjEQTable.setCellRenderer(JSCCellRendererTypes.EQUATION_NAMES);
                ivjEQTable.setColumnWidth(120);
                ivjEQTable.setSymbolName(s_ny.name);
                ivjEQTable.setBounds(0, 0, 144, 104);
                ivjEQTable.setEditable(false);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjEQTable;
    }

    private JSCDataTableScrollPane getDataTableScrollPane() {
        if (ivjDataTableScrollPane == null) {
            try {
                ivjDataTableScrollPane = new JSCDataTableScrollPane();
                ivjDataTableScrollPane.setName("DataTableScrollPane");
                ivjDataTableScrollPane.setMaximumVisibleColumns(1);
                ivjDataTableScrollPane.setMaximumVisibleRows(10);
                getDataTableScrollPane().setViewportView(getEQTable());
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjDataTableScrollPane;
    }

    private JSCDataTableScrollPane getDataTableScrollPane1() {
        if (ivjDataTableScrollPane1 == null) {
            try {
                ivjDataTableScrollPane1 = new JSCDataTableScrollPane();
                ivjDataTableScrollPane1.setName("DataTableScrollPane1");
                ivjDataTableScrollPane1.setMinimumVisibleColumns(2);
                ivjDataTableScrollPane1.setMaximumVisibleColumns(10);
                ivjDataTableScrollPane1.setMaximumVisibleRows(10);
                ivjDataTableScrollPane1.setMinimumVisibleRows(2);
                getDataTableScrollPane1().setViewportView(getCoeffTable());
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjDataTableScrollPane1;
    }

    private ModelPanel getJDialogContentPane() {
        if (ivjJDialogContentPane == null) {
            try {
                ivjJDialogContentPane = new ModelPanel();
                ivjJDialogContentPane.setName("JDialogContentPane");
                ivjJDialogContentPane.setLayout(new java.awt.BorderLayout());
                getJDialogContentPane().add(getJPanel1(), "South");
                getJDialogContentPane().add(getJPanel2(), "Center");
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
                ivjJPanel1.setPreferredSize(new java.awt.Dimension(0, 50));
                ivjJPanel1.setLayout(getJPanel1FlowLayout());
                getJPanel1().add(getOKButton(), getOKButton().getName());
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
     * Return the JPanel1FlowLayout property value.
     * 
     * @return java.awt.FlowLayout
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private java.awt.FlowLayout getJPanel1FlowLayout() {
        java.awt.FlowLayout ivjJPanel1FlowLayout = null;
        try {
            /* Create part */
            ivjJPanel1FlowLayout = new java.awt.FlowLayout();
            ivjJPanel1FlowLayout.setVgap(10);
            ivjJPanel1FlowLayout.setHgap(60);
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        ;
        return ivjJPanel1FlowLayout;
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
                ivjJPanel2.setBorder(new BevelBorder(BevelBorder.LOWERED));
                ivjJPanel2.setLayout(new java.awt.GridBagLayout());

                java.awt.GridBagConstraints constraintsGaussDataTableScrollPane = new java.awt.GridBagConstraints();
                constraintsGaussDataTableScrollPane.gridx = 0;
                constraintsGaussDataTableScrollPane.gridy = 1;
                constraintsGaussDataTableScrollPane.fill = java.awt.GridBagConstraints.BOTH;
                constraintsGaussDataTableScrollPane.weighty = 1.0;
                constraintsGaussDataTableScrollPane.insets = new java.awt.Insets(
                        5, 10, 0, 0);
                getJPanel2().add(getDataTableScrollPane(),
                        constraintsGaussDataTableScrollPane);

                java.awt.GridBagConstraints constraintsGaussDataTableScrollPane1 = new java.awt.GridBagConstraints();
                constraintsGaussDataTableScrollPane1.gridx = 1;
                constraintsGaussDataTableScrollPane1.gridy = 1;
                constraintsGaussDataTableScrollPane1.fill = java.awt.GridBagConstraints.BOTH;
                constraintsGaussDataTableScrollPane1.weightx = 1.0;
                constraintsGaussDataTableScrollPane1.weighty = 1.0;
                constraintsGaussDataTableScrollPane1.insets = new java.awt.Insets(
                        5, 0, 0, 0);
                getJPanel2().add(getDataTableScrollPane1(),
                        constraintsGaussDataTableScrollPane1);

                java.awt.GridBagConstraints constraintsMultiLineLabel1 = new java.awt.GridBagConstraints();
                constraintsMultiLineLabel1.gridx = 0;
                constraintsMultiLineLabel1.gridy = 0;
                constraintsMultiLineLabel1.gridwidth = 2;
                constraintsMultiLineLabel1.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsMultiLineLabel1.insets = new java.awt.Insets(10, 10,
                        10, 0);
                getJPanel2().add(getMultiLineLabel1(),
                        constraintsMultiLineLabel1);
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
     * Return the MultiLineLabel1 property value.
     * 
     * @return com.jstatcom.component.MultiLineLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private com.jstatcom.component.MultiLineLabel getMultiLineLabel1() {
        if (ivjMultiLineLabel1 == null) {
            try {
                ivjMultiLineLabel1 = new com.jstatcom.component.MultiLineLabel();
                ivjMultiLineLabel1.setName("MultiLineLabel1");
                ivjMultiLineLabel1
                        .setText("Click to include/exclude elements from matrix of structural coefficients.\n\'*\' means that the coefficient is included. The diagonal is not editable.");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjMultiLineLabel1;
    }

    /**
     * Return the OKButton property value.
     * 
     * @return javax.swing.JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JButton getOKButton() {
        if (ivjOKButton == null) {
            try {
                ivjOKButton = new javax.swing.JButton();
                ivjOKButton.setName("OKButton");
                ivjOKButton.setPreferredSize(new java.awt.Dimension(100, 25));
                ivjOKButton.setText("OK");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjOKButton;
    }

    /**
     * Gets the specified matrixc of structural coefficients.
     * 
     * @return data object with correct name according to
     *         <code>VECMConstants</code>
     */
    public JSCNArray getStructRes() {
        JSCNArray rtn = getCoeffTable().getJSCNArray();
        if (rtn == null)
            rtn = (JSCNArray) s_g0.getInstance();
        return rtn;
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
        getOKButton().addActionListener(ivjEventHandler);
    }

    /**
     * Initialize the class.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initialize() {
        try {
            // user code begin {1}
            // user code end
            setName("VECMStructCoeffDialog");
            setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
            setTitle("Specify Structural Form");
            setSize(550, 350);
            setResizable(false);
            setContentPane(getJDialogContentPane());
            initConnections();
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}
        symbolTable = getJDialogContentPane().local();

        // user code end
    }

    /**
     * Comment
     */
    private void oKButton_ActionEvents() {
        setVisible(false);
    }

    /**
     * Sets the data necessary for the display. Should be invoked, before this
     * dialog is shown.
     * 
     * @param ny
     *            names of endogenous selected variables
     */
    public void setDisplay(JSCSArray ny) {

        int k = ny.rows();
        symbolTable.get(s_ny).setJSCData(ny);
        JSCNArray res_s_g0 = symbolTable.get(s_g0).getJSCNArray();

        if (res_s_g0.rows() != k) // reset restrictions
            symbolTable.get(s_g0).setJSCData(
                    new JSCNArray("res", UMatrix.eye(k)));
    }
}