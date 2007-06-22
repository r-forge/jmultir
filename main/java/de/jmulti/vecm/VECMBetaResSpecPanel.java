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

import javax.swing.JCheckBox;
import javax.swing.JLabel;

import org.apache.log4j.Logger;

import com.jstatcom.component.NumSelector;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.JSCSArray;
import com.jstatcom.model.ModelPanel;
import com.jstatcom.model.NumberRange;
import com.jstatcom.model.NumberRangeTypes;
import com.jstatcom.table.JSCCellRendererTypes;
import com.jstatcom.table.JSCDataTableScrollPane;
import com.jstatcom.table.JSCMouseListenerTypes;
import com.jstatcom.table.JSCNArrayTable;
import com.jstatcom.table.JSCSArrayTable;


/**
 * Panel to set restrictions on the beta coefficients. The selected restrictions
 * can be accessed via <code>getResMat</code>. The display is synchronized
 * with the data, if <code>update</code> is called. The
 * <code>VECMConstants</code> classed is used to retrieve the necessary
 * variable names.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
public final class VECMBetaResSpecPanel extends ModelPanel {
    private static final Logger log = Logger
            .getLogger(VECMBetaResSpecPanel.class);

    private JSCNArrayTable ivjDataTableRes = null;

    private JSCDataTableScrollPane ivjDataTableScrollPaneRes = null;

    private JLabel ivjJLabel1 = null;

    private JSCNArrayTable ivjDataTableR = null;

    private JSCDataTableScrollPane ivjDataTableScrollPaneR = null;

    private JLabel ivjJLabel = null;

    private NumSelector ivjNumberSelector = null;

    private JSCSArrayTable ivjDataTableBetaNam = null;

    private JSCDataTableScrollPane ivjDataTableScrollPaneBetaNam = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private JSCSArrayTable ivjDataTableMap = null;

    private JSCDataTableScrollPane ivjDataTableScrollPaneMap = null;

    private JLabel ivjJLabel2 = null;

    private JCheckBox ivjEditCheck = null;

    class IvjEventHandler implements java.awt.event.ActionListener,
            java.beans.PropertyChangeListener {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == VECMBetaResSpecPanel.this.getEditCheck())
                connEtoC2();
        };

        public void propertyChange(java.beans.PropertyChangeEvent evt) {
            if (evt.getSource() == VECMBetaResSpecPanel.this
                    .getNumberSelector()
                    && (evt.getPropertyName().equals("number")))
                connEtoC1(evt);
        };
    };

    /**
     * BetaResSpecPanel constructor comment.
     */
    public VECMBetaResSpecPanel() {
        super();
        initialize();
    }

    /**
     * connEtoC1: (NumberSelector.number -->
     * BetaResSpecPanel.numberSelector_Number()V)
     * 
     * @param arg1
     *            java.beans.PropertyChangeEvent
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC1(java.beans.PropertyChangeEvent arg1) {
        try {
            // user code begin {1}
            // user code end
            this.numberSelector_Number();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC2: (EditCheck.action. -->
     * BetaResSpecPanel.editCheck_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC2() {
        try {
            // user code begin {1}
            // user code end
            this.editCheck_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * Comment
     */
    private void editCheck_ActionEvents() {
        getDataTableRes().setEditable(getEditCheck().isSelected());
        // disable mouselistener for manual editing
        if (getEditCheck().isSelected())
            getDataTableRes().setMouseListener(null);
        else
            getDataTableRes().setMouseListener(
                    JSCMouseListenerTypes.SUBSET_01M1);

    }

    /**
     * Gets the data object with the names of the beta coefficients.
     * 
     * @return the data object
     */
    public JSCSArray getBetaNam() {
        return getDataTableBetaNam().getJSCSArray();
    }

    private JSCSArrayTable getDataTableBetaNam() {
        if (ivjDataTableBetaNam == null) {
            try {
                ivjDataTableBetaNam = new JSCSArrayTable();
                ivjDataTableBetaNam.setName("DataTableBetaNam");
                getDataTableScrollPaneBetaNam().setColumnHeaderView(
                        ivjDataTableBetaNam.getTableHeader());
                ivjDataTableBetaNam.setTablePopup(null);
                ivjDataTableBetaNam.setBounds(0, 0, 147, 107);
                ivjDataTableBetaNam.setEditable(false);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjDataTableBetaNam;
    }

    private JSCSArrayTable getDataTableMap() {
        if (ivjDataTableMap == null) {
            try {
                ivjDataTableMap = new JSCSArrayTable();
                ivjDataTableMap.setName("DataTableMap");
                getDataTableScrollPaneMap().setColumnHeaderView(
                        ivjDataTableMap.getTableHeader());
                ivjDataTableMap.setTablePopup(null);
                ivjDataTableMap.setBounds(0, 0, 147, 107);
                ivjDataTableMap.setEditable(false);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjDataTableMap;
    }

    private JSCNArrayTable getDataTableR() {
        if (ivjDataTableR == null) {
            try {
                ivjDataTableR = new JSCNArrayTable();
                ivjDataTableR.setName("DataTableR");
                getDataTableScrollPaneR().setColumnHeaderView(
                        ivjDataTableR.getTableHeader());
                ivjDataTableR.setPrecision(4);
                ivjDataTableR.setBounds(0, 0, 147, 107);
                // user code begin {1}
                ivjDataTableR.setEditable(true);
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjDataTableR;
    }

    private JSCNArrayTable getDataTableRes() {
        if (ivjDataTableRes == null) {
            try {
                ivjDataTableRes = new JSCNArrayTable();
                ivjDataTableRes.setName("DataTableRes");
                getDataTableScrollPaneRes().setColumnHeaderView(
                        ivjDataTableRes.getTableHeader());
                ivjDataTableRes.setPrecision(4);
                ivjDataTableRes
                        .setCellRenderer(JSCCellRendererTypes.SUBSET_01M1NEGINF);
                ivjDataTableRes.setTablePopup(null);
                ivjDataTableRes
                        .setMouseListener(JSCMouseListenerTypes.SUBSET_01M1);
                ivjDataTableRes.setBounds(0, 0, 147, 107);
                ivjDataTableRes.setEditable(false);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjDataTableRes;
    }

    private JSCDataTableScrollPane getDataTableScrollPaneBetaNam() {
        if (ivjDataTableScrollPaneBetaNam == null) {
            try {
                ivjDataTableScrollPaneBetaNam = new JSCDataTableScrollPane();
                ivjDataTableScrollPaneBetaNam
                        .setName("DataTableScrollPaneBetaNam");
                ivjDataTableScrollPaneBetaNam.setColumnHeaderShowing(true);
                ivjDataTableScrollPaneBetaNam.setMinimumVisibleRows(3);
                String ivjLocal56columnHeaderStringData[] = { "vec(beta'(K-r))" };
                ivjDataTableScrollPaneBetaNam
                        .setColumnHeaderStringData(ivjLocal56columnHeaderStringData);
                getDataTableScrollPaneBetaNam().setViewportView(
                        getDataTableBetaNam());
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjDataTableScrollPaneBetaNam;
    }

    private JSCDataTableScrollPane getDataTableScrollPaneMap() {
        if (ivjDataTableScrollPaneMap == null) {
            try {
                ivjDataTableScrollPaneMap = new JSCDataTableScrollPane();
                ivjDataTableScrollPaneMap.setName("DataTableScrollPaneMap");
                ivjDataTableScrollPaneMap.setColumnHeaderShowing(true);
                getDataTableScrollPaneMap().setViewportView(getDataTableMap());
                // user code begin {1}
                // ivjDataTableScrollPaneMap.setMinimumVisibleRows(2);
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjDataTableScrollPaneMap;
    }

    private JSCDataTableScrollPane getDataTableScrollPaneR() {
        if (ivjDataTableScrollPaneR == null) {
            try {
                ivjDataTableScrollPaneR = new JSCDataTableScrollPane();
                ivjDataTableScrollPaneR.setName("DataTableScrollPaneR");
                ivjDataTableScrollPaneR.setMaximumVisibleColumns(1);
                String ivjLocal50columnHeaderStringData[] = { "r  " };
                ivjDataTableScrollPaneR
                        .setColumnHeaderStringData(ivjLocal50columnHeaderStringData);
                ivjDataTableScrollPaneR
                        .setPreferredSize(new java.awt.Dimension(100, 17));
                ivjDataTableScrollPaneR.setColumnHeaderShowing(true);
                ivjDataTableScrollPaneR.setMinimumVisibleRows(5);
                ivjDataTableScrollPaneR.setMaximumVisibleRows(6);
                getDataTableScrollPaneR().setViewportView(getDataTableR());
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjDataTableScrollPaneR;
    }

    private JSCDataTableScrollPane getDataTableScrollPaneRes() {
        if (ivjDataTableScrollPaneRes == null) {
            try {
                ivjDataTableScrollPaneRes = new JSCDataTableScrollPane();
                ivjDataTableScrollPaneRes.setName("DataTableScrollPaneRes");
                ivjDataTableScrollPaneRes.setColumnHeaderShowing(true);
                getDataTableScrollPaneRes().setViewportView(getDataTableRes());
                // user code begin {1}
                ivjDataTableScrollPaneRes.setMaximumVisibleColumns(10);
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjDataTableScrollPaneRes;
    }

    /**
     * Return the EditCheck property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getEditCheck() {
        if (ivjEditCheck == null) {
            try {
                ivjEditCheck = new javax.swing.JCheckBox();
                ivjEditCheck.setName("EditCheck");
                ivjEditCheck.setText("Edit manually");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjEditCheck;
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
                ivjJLabel.setText("R*vec(beta'(K-r)) = r");
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
                ivjJLabel1.setPreferredSize(new java.awt.Dimension(149, 20));
                ivjJLabel1.setText("Number of Restrictions (J)");
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
                ivjJLabel2.setText("Mapping of beta to variable names");
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

    private NumSelector getNumberSelector() {
        if (ivjNumberSelector == null) {
            try {
                ivjNumberSelector = new NumSelector();
                ivjNumberSelector.setName("NumberSelector");
                ivjNumberSelector.setPreferredSize(new java.awt.Dimension(100,
                        20));
                ivjNumberSelector.setNumber(1.0);
                ivjNumberSelector
                        .setMinimumSize(new java.awt.Dimension(100, 20));
                ivjNumberSelector.setRangeExpr("[0,20]");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjNumberSelector;
    }

    /**
     * Gets the data object with the restrictions on beta selected by the user.
     * 
     * @return the data object with the restrictions R in
     *         <code>R*vec(beta)=r</code>
     */
    public JSCNArray getResMat() {
        return getDataTableRes().getJSCNArray();
    }

    /**
     * Gets the data object with the r vector selected by the user.
     * 
     * @return the data object with r in <code>R*vec(beta)=r</code>
     */
    public JSCNArray getRMat() {
        return getDataTableR().getJSCNArray();
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
        getNumberSelector().addPropertyChangeListener(ivjEventHandler);
        getEditCheck().addActionListener(ivjEventHandler);
    }

    /**
     * Initialize the class.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initialize() {
        try {
            // user code begin {1}
            // user code end
            setName("BetaResSpecPanel");

            setLayout(new java.awt.GridBagLayout());
            setSize(732, 495);

            java.awt.GridBagConstraints constraintsDataTableScrollPaneRes = new java.awt.GridBagConstraints();
            constraintsDataTableScrollPaneRes.gridx = 0;
            constraintsDataTableScrollPaneRes.gridy = 4;
            constraintsDataTableScrollPaneRes.gridwidth = 1;
            constraintsDataTableScrollPaneRes.fill = java.awt.GridBagConstraints.BOTH;
            constraintsDataTableScrollPaneRes.weightx = 1.0;
            constraintsDataTableScrollPaneRes.weighty = 1.0;
            constraintsDataTableScrollPaneRes.insets = new java.awt.Insets(10,
                    5, 0, 0);
            java.awt.GridBagConstraints constraintsDataTableScrollPaneBetaNam = new java.awt.GridBagConstraints();
            constraintsDataTableScrollPaneBetaNam.gridx = 1;
            constraintsDataTableScrollPaneBetaNam.gridy = 4;
            constraintsDataTableScrollPaneBetaNam.fill = java.awt.GridBagConstraints.BOTH;
            constraintsDataTableScrollPaneBetaNam.weightx = 1.0;
            constraintsDataTableScrollPaneBetaNam.weighty = 1.0;
            constraintsDataTableScrollPaneBetaNam.insets = new java.awt.Insets(
                    10, 5, 0, 0);
            java.awt.GridBagConstraints constraintsDataTableScrollPaneR = new java.awt.GridBagConstraints();
            constraintsDataTableScrollPaneR.gridx = 2;
            constraintsDataTableScrollPaneR.gridy = 4;
            constraintsDataTableScrollPaneR.fill = java.awt.GridBagConstraints.BOTH;
            constraintsDataTableScrollPaneR.weighty = 1.0;
            constraintsDataTableScrollPaneR.insets = new java.awt.Insets(10, 5,
                    0, 5);
            java.awt.GridBagConstraints constraintsJLabel = new java.awt.GridBagConstraints();
            constraintsJLabel.gridx = 1;
            constraintsJLabel.gridy = 3;
            constraintsJLabel.fill = java.awt.GridBagConstraints.BOTH;
            constraintsJLabel.insets = new java.awt.Insets(5, 5, 0, 0);
            java.awt.GridBagConstraints constraintsNumberSelector = new java.awt.GridBagConstraints();
            constraintsNumberSelector.gridx = 0;
            constraintsNumberSelector.gridy = 2;
            constraintsNumberSelector.anchor = java.awt.GridBagConstraints.SOUTHEAST;
            constraintsNumberSelector.insets = new java.awt.Insets(5, 5, 0, 0);
            java.awt.GridBagConstraints constraintsJLabel1 = new java.awt.GridBagConstraints();
            constraintsJLabel1.gridx = 1;
            constraintsJLabel1.gridy = 2;
            constraintsJLabel1.fill = java.awt.GridBagConstraints.HORIZONTAL;
            constraintsJLabel1.anchor = java.awt.GridBagConstraints.SOUTH;
            constraintsJLabel1.insets = new java.awt.Insets(5, 5, 0, 0);
            java.awt.GridBagConstraints constraintsDataTableScrollPaneMap = new java.awt.GridBagConstraints();
            constraintsDataTableScrollPaneMap.gridx = 0;
            constraintsDataTableScrollPaneMap.gridy = 1;
            constraintsDataTableScrollPaneMap.gridwidth = 4;
            constraintsDataTableScrollPaneMap.fill = java.awt.GridBagConstraints.BOTH;
            constraintsDataTableScrollPaneMap.weightx = 1.0;
            constraintsDataTableScrollPaneMap.insets = new java.awt.Insets(5,
                    5, 0, 5);
            java.awt.GridBagConstraints constraintsJLabel2 = new java.awt.GridBagConstraints();
            constraintsJLabel2.gridx = 0;
            constraintsJLabel2.gridy = 0;
            constraintsJLabel2.gridwidth = 4;
            constraintsJLabel2.fill = java.awt.GridBagConstraints.HORIZONTAL;
            constraintsJLabel2.insets = new java.awt.Insets(5, 5, 0, 0);
            java.awt.GridBagConstraints constraintsEditCheck = new java.awt.GridBagConstraints();
            constraintsEditCheck.gridx = 0;
            constraintsEditCheck.gridy = 3;
            constraintsEditCheck.anchor = java.awt.GridBagConstraints.WEST;
            constraintsEditCheck.insets = new java.awt.Insets(5, 5, 0, 0);
            constraintsEditCheck.gridwidth = 1;
            constraintsEditCheck.fill = java.awt.GridBagConstraints.HORIZONTAL;

            constraintsJLabel1.gridwidth = 2;
            constraintsJLabel.gridwidth = 2;
            this.add(getDataTableScrollPaneRes(),
                    constraintsDataTableScrollPaneRes);
            this.add(getDataTableScrollPaneBetaNam(),
                    constraintsDataTableScrollPaneBetaNam);
            this
                    .add(getDataTableScrollPaneR(),
                            constraintsDataTableScrollPaneR);
            this.add(getJLabel(), constraintsJLabel);
            this.add(getNumberSelector(), constraintsNumberSelector);
            this.add(getJLabel1(), constraintsJLabel1);
            this.add(getJLabel2(), constraintsJLabel2);
            this.add(getDataTableScrollPaneMap(),
                    constraintsDataTableScrollPaneMap);
            this.add(getEditCheck(), constraintsEditCheck);
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
    private void numberSelector_Number() {
        int betaKNum = getDataTableBetaNam().getRowCount();
        int rNum = getNumberSelector().getIntNumber();
        int rowCount = getDataTableR().getModel().getRowCount();
        if (rNum > rowCount) {
            JSCNArray R = getDataTableR().getJSCNArray();
            R.appendRows(new JSCNArray("toAppend",
                    new double[rNum - rowCount][1]));
            getDataTableR().setJSCData(R);

            JSCNArray Res = getDataTableRes().getJSCNArray();
            Res.appendRows(new JSCNArray("res",
                    new double[rNum - rowCount][betaKNum]));
            getDataTableRes().setJSCData(Res);
        }

        if (rNum < rowCount) {
            getDataTableR().setJSCData(
                    new JSCNArray("newR", getDataTableR().getJSCNArray()
                            .getRows(0, rNum - 1)));
            getDataTableRes().setJSCData(
                    new JSCNArray("newR", getDataTableRes().getJSCNArray()
                            .getRows(0, rNum - 1)));

            // delRows(rNum, rowCount - 1)
            // delRows(rNum, rowCount - 1)
        }
    }

    /**
     * Updates the displayed restrictions and synchronizes it with the
     * underlying data for the VEC model.
     */
    public void update() {

        int r = global().get(VECMConstants.cointRank_Def).getJSCInt().intVal();
        int k = global().get(VECMConstants.Ny_Def).getJSCSArray().rows();
        // Get number of restricted ec terms.
        int det_num_ec = global().get(VECMConstants.Nd_ec_Def).getJSCSArray()
                .rows();
        int betanum = det_num_ec + k - r;
        getNumberSelector().setNumberRange(
                new NumberRange(1, betanum * r, NumberRangeTypes.CLOSED));
        int rNum = getNumberSelector().getIntNumber();

        // Set the restriction matrix, default is unrestricted.
        if (rNum != getDataTableR().getModel().getRowCount())
            getDataTableR().setJSCData(new JSCNArray("r", new double[rNum][1]));

        String[] betaNam = new String[betanum * r];
        for (int i = 0, n = 0; i < betanum; i++)
            for (int j = 0; j < r; j++, n++)
                betaNam[n] = "beta(" + (i + 1 + r) + ", " + (j + 1) + ")";
        getDataTableBetaNam().setJSCData(new JSCSArray("betaNam", betaNam));

        // The R matrix
        if (rNum != getDataTableRes().getRowCount()
                || betanum * r != getDataTableRes().getColumnCount())
            getDataTableRes().setJSCData(
                    new JSCNArray("res", new double[rNum][betaNam.length]));
        getDataTableScrollPaneRes().setColumnHeaderStringData(betaNam);

        // Set the names in the Map header.
        String[] namRes = new String[betanum];
        for (int i = r; i < k; i++)
            namRes[i - r] = global().get(VECMConstants.Ny_Def).getJSCSArray()
                    .stringAt(i, 0);
        // The remaining deterministics.
        for (int i = 0; i < det_num_ec; i++)
            namRes[k - r + i] = global().get(VECMConstants.Nd_ec_Def)
                    .getJSCSArray().stringAt(i, 0);
        String[][] betaMap = new String[r][betanum];
        for (int i = 0; i < r; i++)
            for (int j = 0; j < betanum; j++)
                betaMap[i][j] = "beta(" + (j + 1 + r) + ", " + (i + 1) + ")";
        getDataTableMap().setJSCData(new JSCSArray("betaMap", betaMap));
        getDataTableScrollPaneMap().setColumnHeaderStringData(namRes);

    }
}