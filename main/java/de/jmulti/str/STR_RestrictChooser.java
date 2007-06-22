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
import javax.swing.border.BevelBorder;

import org.apache.log4j.Logger;

import com.jstatcom.component.StdMessages;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.SymbolTable;
import com.jstatcom.table.JSCDataTableScrollPane;
import com.jstatcom.table.JSCSArrayTable;


/**
 * Dialog for choosing estimation restrictions in STR analysis. The possible
 * restriction are:
 * <ul>
 * <li> theta = 0 (exlude variable from nonlinear part)
 * <li> theta = -phi (exlude variable from model if transition function G == 1 )
 * <li> phi = 0 (exlude variable from model if transition function G == 0 )
 * </ul>
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */

final class STR_RestrictChooser extends JDialog {
    private static final Logger log = Logger
            .getLogger(STR_RestrictChooser.class);

    private JPanel ivjJDialogContentPane = null;

    private SymbolTable symbolTable = null;

    private JPanel ivjJPanel1 = null;

    private JPanel ivjJPanel2 = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private JButton ivjOK = null;

    private JSCDataTableScrollPane ivjScrollPane_phi = null;

    private JSCDataTableScrollPane ivjScrollPane_thetphi = null;

    private JSCSArrayTable ivjTable_phi = null;

    private JSCSArrayTable ivjTable_thetphi = null;

    private JLabel ivjJLabel1 = null;

    private JLabel ivjJLabel2 = null;

    private JLabel ivjJLabel = null;

    private JSCDataTableScrollPane ivjScrollPane_const = null;

    private JSCSArrayTable ivjTable_Const = null;

    private javax.swing.JButton jCancelButton = null;

    class IvjEventHandler implements java.awt.event.ActionListener {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == STR_RestrictChooser.this.getOK())
                connEtoC1();
            if (e.getSource() == STR_RestrictChooser.this.getJCancelButton())
                connEtoC2();

        };
    };

    /**
     * StrRestrictionChooser constructor comment.
     */
    public STR_RestrictChooser() {
        super();
        initialize();
    }

    /**
     * StrRestrictionChooser constructor comment.
     * 
     * @param owner
     *            java.awt.Frame
     * @param modal
     *            boolean
     */
    public STR_RestrictChooser(java.awt.Frame owner, boolean modal,
            SymbolTable st) {
        super(owner, modal);
        symbolTable = st;
        initialize();
    }

    /**
     * connEtoC1: (OK.action. --> StrRestrictionChooser.oK_ActionEvents()V)
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

    /**
     * connEtoC1: (CANCEL.action. -->
     * StrRestrictionChooser.cancel_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC2() {
        try {
            // user code begin {1}
            // user code end
            this.cancel_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
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
                ivjJLabel.setText("Parameter will be constant");
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
                ivjJLabel1.setText("Variable will disappear, if G=1");
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
                ivjJLabel2.setText("Variable will disappear, if G=0");
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

                java.awt.GridBagConstraints constraintsScrollPane_thetphi = new java.awt.GridBagConstraints();
                constraintsScrollPane_thetphi.gridx = 1;
                constraintsScrollPane_thetphi.gridy = 1;
                constraintsScrollPane_thetphi.fill = java.awt.GridBagConstraints.BOTH;
                constraintsScrollPane_thetphi.weighty = 1.0;
                constraintsScrollPane_thetphi.insets = new java.awt.Insets(5,
                        10, 0, 0);
                getJPanel1().add(getScrollPane_thetphi(),
                        constraintsScrollPane_thetphi);

                java.awt.GridBagConstraints constraintsScrollPane_phi = new java.awt.GridBagConstraints();
                constraintsScrollPane_phi.gridx = 2;
                constraintsScrollPane_phi.gridy = 1;
                constraintsScrollPane_phi.fill = java.awt.GridBagConstraints.BOTH;
                constraintsScrollPane_phi.weighty = 1.0;
                constraintsScrollPane_phi.insets = new java.awt.Insets(5, 20,
                        0, 0);
                getJPanel1()
                        .add(getScrollPane_phi(), constraintsScrollPane_phi);

                java.awt.GridBagConstraints constraintsJLabel1 = new java.awt.GridBagConstraints();
                constraintsJLabel1.gridx = 1;
                constraintsJLabel1.gridy = 0;
                constraintsJLabel1.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel1.insets = new java.awt.Insets(5, 10, 0, 0);
                getJPanel1().add(getJLabel1(), constraintsJLabel1);

                java.awt.GridBagConstraints constraintsJLabel2 = new java.awt.GridBagConstraints();
                constraintsJLabel2.gridx = 2;
                constraintsJLabel2.gridy = 0;
                constraintsJLabel2.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel2.insets = new java.awt.Insets(5, 20, 0, 0);
                getJPanel1().add(getJLabel2(), constraintsJLabel2);

                java.awt.GridBagConstraints constraintsScrollPane_const = new java.awt.GridBagConstraints();
                constraintsScrollPane_const.gridx = 3;
                constraintsScrollPane_const.gridy = 1;
                constraintsScrollPane_const.gridheight = 5;
                constraintsScrollPane_const.fill = java.awt.GridBagConstraints.BOTH;
                constraintsScrollPane_const.weighty = 1.0;
                constraintsScrollPane_const.insets = new java.awt.Insets(5, 20,
                        0, 10);
                getJPanel1().add(getScrollPane_const(),
                        constraintsScrollPane_const);

                java.awt.GridBagConstraints constraintsJLabel = new java.awt.GridBagConstraints();
                constraintsJLabel.gridx = 3;
                constraintsJLabel.gridy = 0;
                constraintsJLabel.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel.insets = new java.awt.Insets(5, 20, 0, 0);
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
                java.awt.FlowLayout layFlowLayout1 = new java.awt.FlowLayout();
                layFlowLayout1.setHgap(100);
                ivjJPanel2.setLayout(layFlowLayout1);
                ivjJPanel2.setName("JPanel2");
                ivjJPanel2.setPreferredSize(new java.awt.Dimension(0, 50));
                ivjJPanel2.setMinimumSize(new java.awt.Dimension(0, 50));
                getJPanel2().add(getOK(), getOK().getName());
                // user code begin {1}
                ivjJPanel2.add(getJCancelButton(), null);
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

    private JSCDataTableScrollPane getScrollPane_const() {
        if (ivjScrollPane_const == null) {
            try {
                ivjScrollPane_const = new JSCDataTableScrollPane();
                ivjScrollPane_const.setName("ScrollPane_const");
                ivjScrollPane_const.setMaximumVisibleColumns(1);
                String ivjLocal46columnHeaderStringData[] = { " theta=0 " };
                ivjScrollPane_const
                        .setColumnHeaderStringData(ivjLocal46columnHeaderStringData);
                ivjScrollPane_const.setColumnHeaderShowing(true);
                ivjScrollPane_const.setMaximumVisibleRows(10);
                getScrollPane_const().setViewportView(getTable_Const());
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjScrollPane_const;
    }

    private JSCDataTableScrollPane getScrollPane_phi() {
        if (ivjScrollPane_phi == null) {
            try {
                ivjScrollPane_phi = new JSCDataTableScrollPane();
                ivjScrollPane_phi.setName("ScrollPane_phi");
                ivjScrollPane_phi.setColumnHeaderShowing(true);
                String ivjLocal44columnHeaderStringData[] = { " phi=0 " };
                ivjScrollPane_phi
                        .setColumnHeaderStringData(ivjLocal44columnHeaderStringData);
                ivjScrollPane_phi.setMaximumVisibleColumns(1);
                ivjScrollPane_phi.setMaximumVisibleRows(10);
                getScrollPane_phi().setViewportView(getTable_phi());
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjScrollPane_phi;
    }

    private JSCDataTableScrollPane getScrollPane_thetphi() {
        if (ivjScrollPane_thetphi == null) {
            try {
                ivjScrollPane_thetphi = new JSCDataTableScrollPane();
                ivjScrollPane_thetphi.setName("ScrollPane_thetphi");
                ivjScrollPane_thetphi.setColumnHeaderShowing(true);
                String ivjLocal48columnHeaderStringData[] = { " theta=-phi " };
                ivjScrollPane_thetphi
                        .setColumnHeaderStringData(ivjLocal48columnHeaderStringData);
                ivjScrollPane_thetphi.setMaximumVisibleColumns(1);
                ivjScrollPane_thetphi.setMaximumVisibleRows(10);
                getScrollPane_thetphi().setViewportView(getTable_thetphi());
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjScrollPane_thetphi;
    }

    private JSCSArrayTable getTable_Const() {
        if (ivjTable_Const == null) {
            try {
                ivjTable_Const = new JSCSArrayTable();
                ivjTable_Const.setName("Table_Const");
                getScrollPane_const().setColumnHeaderView(
                        ivjTable_Const.getTableHeader());
                ivjTable_Const.setColumnWidth(120);
                ivjTable_Const.setSymbolName(STR_Constants.STR_thet0Names.name);
                ivjTable_Const.setSymbolTable(symbolTable);
                ivjTable_Const.setBounds(0, 0, 144, 104);
                ivjTable_Const.setRowSelectionAllowed(true);
                ivjTable_Const.setEditable(false);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjTable_Const;
    }

    private JSCSArrayTable getTable_phi() {
        if (ivjTable_phi == null) {
            try {
                ivjTable_phi = new JSCSArrayTable();
                ivjTable_phi.setName("Table_phi");
                getScrollPane_phi().setColumnHeaderView(
                        ivjTable_phi.getTableHeader());
                ivjTable_phi.setColumnWidth(120);
                ivjTable_phi.setSymbolName(STR_Constants.STR_thet0Names.name);
                ivjTable_phi.setSymbolTable(symbolTable);
                ivjTable_phi.setBounds(0, 0, 144, 104);
                ivjTable_phi.setRowSelectionAllowed(true);
                ivjTable_phi.setEditable(false);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjTable_phi;
    }

    private JSCSArrayTable getTable_thetphi() {
        if (ivjTable_thetphi == null) {
            try {
                ivjTable_thetphi = new JSCSArrayTable();
                ivjTable_thetphi.setName("Table_thetphi");
                getScrollPane_thetphi().setColumnHeaderView(
                        ivjTable_thetphi.getTableHeader());
                ivjTable_thetphi.setColumnWidth(120);
                ivjTable_thetphi
                        .setSymbolName(STR_Constants.STR_thet0Names.name);
                ivjTable_thetphi.setSymbolTable(symbolTable);
                ivjTable_thetphi.setBounds(0, 0, 144, 104);
                ivjTable_thetphi.setRowSelectionAllowed(true);
                ivjTable_thetphi.setEditable(false);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjTable_thetphi;
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
        getJCancelButton().addActionListener(ivjEventHandler);

    }

    /**
     * Initialize the class.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initialize() {
        try {
            // user code begin {1}
            // user code end
            setName("StrRestrictionChooser");
            setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
            setSize(560, 271);
            setTitle("Set Restrictions");
            setContentPane(getJDialogContentPane());
            initConnections();
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}
        // user code end
    }

    /**
     * Does consistency checks.
     */
    private void oK_ActionEvents() {
        JSCNArray thet0Sel1 = new JSCNArray("thet0Sel", getTable_Const()
                .getIntSelectionIndex());

        JSCNArray phi0Sel1 = new JSCNArray("phi0Sel", getTable_phi()
                .getIntSelectionIndex());

        JSCNArray thetMinusPhiSel1 = new JSCNArray("thetMinusPhiSel",
                getTable_thetphi().getIntSelectionIndex());

        if (getTable_Const().getSelectedRowCount() == getTable_Const()
                .getRowCount()
                && getTable_Const().getSelectedRowCount() > 0) {
            String msg = "You have specified a linear model.\nDeselect one or more variables from the rightmost table.";
            StdMessages.errorSpecification(msg);
            return;
        }

        for (int i = 0; i < thet0Sel1.rows(); i++) {
            if (thet0Sel1.intAt(i, 0) + phi0Sel1.intAt(i, 0)
                    + thetMinusPhiSel1.intAt(i, 0) > 1) {
                String message = "The selection does not make sense.\nA variable should be selected only once.";
                StdMessages.errorSpecification(message);
                return;
            }
        }
        setVisible(false);
    }

    /**
     * Keeps old selection.
     */
    private void cancel_ActionEvents() {
        getTable_Const().clearSelection();
        getTable_phi().clearSelection();
        getTable_thetphi().clearSelection();
        oK_ActionEvents();
    }

    /**
     * Gets the selected indices for variables with the restriction Phi = 0.
     * 
     * @return zero based index data: 1 - restricted; 0 - not restricted
     */
    public JSCNArray getPhi0Sel() {
        return new JSCNArray("phi0Sel", getTable_phi().getIntSelectionIndex());
    }

    /**
     * Gets the selected indices for variables with the restriction Theta = 0.
     * 
     * @return zero based index data: 1 - restricted; 0 - not restricted
     */
    public JSCNArray getThet0Sel() {
        return new JSCNArray("thet0Sel", getTable_Const()
                .getIntSelectionIndex());

    }

    /**
     * Gets the selected indices for variables with the restriction Theta =
     * -Phi.
     * 
     * @return zero based index data: 1 - restricted; 0 - not restricted
     */
    public JSCNArray getThetMinusPhiSel() {
        return new JSCNArray("thetMinusPhiSel", getTable_thetphi()
                .getIntSelectionIndex());
    }

    /**
     * This method initializes jCancelButton
     * 
     * @return javax.swing.JButton
     */
    private javax.swing.JButton getJCancelButton() {
        if (jCancelButton == null) {
            jCancelButton = new javax.swing.JButton();
            jCancelButton.setPreferredSize(new java.awt.Dimension(100, 27));
            jCancelButton.setText("Clear All");
        }
        return jCancelButton;
    }
}