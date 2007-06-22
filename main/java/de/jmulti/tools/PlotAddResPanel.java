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

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

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
import com.jstatcom.model.SymbolTable;
import com.jstatcom.table.JSCDataTableScrollPane;
import com.jstatcom.table.JSCSArrayTable;
import com.jstatcom.ts.TS;
import com.jstatcom.ts.TSDate;
import com.jstatcom.ts.TSHolder;
import com.jstatcom.util.UData;
import com.jstatcom.util.UMatrix;

import de.jmulti.proc.PlotTSCall;

/**
 * A panel for adding and plotting of residuals.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
public final class PlotAddResPanel extends ModelPanel {
    private static final Logger log = Logger.getLogger(PlotAddResPanel.class);

    private Scope scope = Scope.GLOBAL;

    private double[][] residData = null;

    private JSCSArray namData = null;

    private JSCTypeDef dRange = new JSCTypeDef("plotadd_drange",
            JSCTypes.DRANGE);

    private JSCTypeDef resids = new JSCTypeDef("plotadd__res", JSCTypes.NARRAY);

    private JSCTypeDef nameResids = new JSCTypeDef("plotadd__resnames",
            JSCTypes.SARRAY);

    private JButton ivjAddResids = null;

    private JSCDataTableScrollPane ivjDataTableScrollPane = null;

    private JLabel ivjJLabel5 = null;

    private JCheckBox ivjMultGraph = null;

    private JButton ivjPlot = null;

    private JPanel ivjPLotAdd = null;

    private ResultField ivjResultField = null;

    private JCheckBox ivjSquaredResPlot = null;

    private JCheckBox ivjStdResids = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private JSCSArrayTable ivjScrollPaneTable_Nu = null;

    private boolean isUnivariateOnly = false;

    class IvjEventHandler implements java.awt.event.ActionListener {

        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == PlotAddResPanel.this.getAddResids())
                connEtoC1();
            if (e.getSource() == PlotAddResPanel.this.getPlot())
                connEtoC2();
        };
    };

    /**
     * PlotAddResids constructor comment.
     */
    public PlotAddResPanel() {
        super();
        initialize();
    }

    /**
     * Adds selected residual series without standardizing or squaring.
     */
    private void addResids_ActionEvents() {
        if (isUnivariateOnly)
            getScrollPaneTable_Nu().getSelectionModel().setSelectionInterval(0,
                    0);

        if (getScrollPaneTable_Nu().getSelectedRow() == -1) {
            StdMessages
                    .infoNothingSelected("Please select at least one series.");
            return;
        }

        setSelectedData();

        JSCSArray added = new JSCSArray("Added");

        SymbolTable sTable = scope.getSymbolTable(this);
        TSDate tsDate = sTable.get(dRange).getJSCDRange().getTSDateRange()
                .lowerBound();

        int test = JOptionPane.YES_OPTION;
        for (int i = 0; i < namData.rows(); i++) {
            test = TSHolder.getInstance().addTS(
                    new TS(UMatrix.getDoubleCol(residData, i), namData
                            .stringAt(i, 0), tsDate));
            if (test == JOptionPane.CANCEL_OPTION)
                break;
            if (test == JOptionPane.YES_OPTION)
                added
                        .appendRows(new JSCSArray("append", namData.stringAt(i,
                                0)));
        }
        if (added.rows() > 0)
            getResultField()
                    .append(
                            "Residual series \""
                                    + UData.stringForArray(added)
                                    + "\" added to dataset (without any transformation).\n");
        else
            getResultField().append("No residuals added to dataset.\n");
    }

    /**
     * connEtoC1: (AddResids.action. -->
     * PlotAddResids.addResids_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC1() {
        try {
            // user code begin {1}
            // user code end
            this.addResids_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC2: (Plot.action. --> PlotAddResids.plot_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC2() {
        try {
            // user code begin {1}
            // user code end
            this.plot_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * Return the AddResids property value.
     * 
     * @return javax.swing.JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JButton getAddResids() {
        if (ivjAddResids == null) {
            try {
                ivjAddResids = new javax.swing.JButton();
                ivjAddResids.setName("AddResids");
                ivjAddResids.setPreferredSize(new java.awt.Dimension(120, 27));
                ivjAddResids.setText("Add to Dataset");
                ivjAddResids.setMinimumSize(new java.awt.Dimension(120, 27));
                ivjAddResids.setMaximumSize(new java.awt.Dimension(200, 27));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjAddResids;
    }

    private JSCDataTableScrollPane getDataTableScrollPane() {
        if (ivjDataTableScrollPane == null) {
            try {
                ivjDataTableScrollPane = new JSCDataTableScrollPane();
                ivjDataTableScrollPane.setName("DataTableScrollPane");
                ivjDataTableScrollPane.setMaximumVisibleColumns(1);
                ivjDataTableScrollPane.setMaximumVisibleRows(6);
                getDataTableScrollPane().setViewportView(
                        getScrollPaneTable_Nu());
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

    /**
     * Return the JLabel5 property value.
     * 
     * @return javax.swing.JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JLabel getJLabel5() {
        if (ivjJLabel5 == null) {
            try {
                ivjJLabel5 = new javax.swing.JLabel();
                ivjJLabel5.setName("JLabel5");
                ivjJLabel5.setText("Select residual series");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJLabel5;
    }

    /**
     * Return the MultGraph property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getMultGraph() {
        if (ivjMultGraph == null) {
            try {
                ivjMultGraph = new javax.swing.JCheckBox();
                ivjMultGraph.setName("MultGraph");
                ivjMultGraph.setText("One diagram for each graph");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjMultGraph;
    }

    /**
     * Gets the variable name for names of residuals.
     * 
     * @return variable name of data object
     */
    public String getDRange() {
        if (dRange == null)
            return null;
        return dRange.name;
    }

    /**
     * Gets the variable name for names of residuals.
     * 
     * @return variable name of data object
     */
    public String getNameResids() {
        if (nameResids == null)
            return null;

        return nameResids.name;
    }

    /**
     * Return the Plot property value.
     * 
     * @return javax.swing.JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JButton getPlot() {
        if (ivjPlot == null) {
            try {
                ivjPlot = new javax.swing.JButton();
                ivjPlot.setName("Plot");
                ivjPlot.setPreferredSize(new java.awt.Dimension(120, 27));
                ivjPlot.setText("Plot");
                ivjPlot.setMinimumSize(new java.awt.Dimension(120, 27));
                ivjPlot.setMaximumSize(new java.awt.Dimension(200, 27));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjPlot;
    }

    /**
     * Return the PLotAdd property value.
     * 
     * @return javax.swing.JPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JPanel getPLotAdd() {
        if (ivjPLotAdd == null) {
            try {
                ivjPLotAdd = new javax.swing.JPanel();
                ivjPLotAdd.setName("PLotAdd");
                ivjPLotAdd.setBorder(new BevelBorder(BevelBorder.LOWERED));
                ivjPLotAdd.setLayout(new java.awt.GridBagLayout());
                ivjPLotAdd.setPreferredSize(new java.awt.Dimension(550,140));
                ivjPLotAdd.setMinimumSize(new java.awt.Dimension(445, 120));

                java.awt.GridBagConstraints constraintsStdResids = new java.awt.GridBagConstraints();
                constraintsStdResids.gridx = 2;
                constraintsStdResids.gridy = 1;
                constraintsStdResids.gridwidth = 2;
                constraintsStdResids.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsStdResids.anchor = java.awt.GridBagConstraints.WEST;
                constraintsStdResids.insets = new java.awt.Insets(5,15,0,0);
                java.awt.GridBagConstraints constraintsPlot = new java.awt.GridBagConstraints();
                constraintsPlot.gridx = 3;
                constraintsPlot.gridy = 3;
                constraintsPlot.anchor = java.awt.GridBagConstraints.SOUTHWEST;
                constraintsPlot.weightx = 5.0D;
                constraintsPlot.weighty = 1.0D;
                constraintsPlot.insets = new java.awt.Insets(5,40,5,5);
                java.awt.GridBagConstraints constraintsAddResids = new java.awt.GridBagConstraints();
                constraintsAddResids.gridx = 2;
                constraintsAddResids.gridy = 3;
                constraintsAddResids.anchor = java.awt.GridBagConstraints.SOUTH;
                constraintsAddResids.insets = new java.awt.Insets(5,15,5,10);
                ivjPLotAdd.add(getStdResids(), constraintsStdResids);
                ivjPLotAdd.add(getPlot(), constraintsPlot);
                java.awt.GridBagConstraints constraintsMultGraph = new java.awt.GridBagConstraints();
                constraintsMultGraph.gridx = 2;
                constraintsMultGraph.gridy = 0;
                constraintsMultGraph.gridwidth = 2;
                constraintsMultGraph.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsMultGraph.insets = new java.awt.Insets(5,15,0,5);
                java.awt.GridBagConstraints constraintsGaussDataTableScrollPane = new java.awt.GridBagConstraints();
                constraintsGaussDataTableScrollPane.gridx = 1;
                constraintsGaussDataTableScrollPane.gridy = 0;
                constraintsGaussDataTableScrollPane.gridheight = 4;
                constraintsGaussDataTableScrollPane.fill = java.awt.GridBagConstraints.BOTH;
                constraintsGaussDataTableScrollPane.anchor = java.awt.GridBagConstraints.EAST;
                constraintsGaussDataTableScrollPane.weighty = 1.0;
                constraintsGaussDataTableScrollPane.insets = new java.awt.Insets(5,10,5,20);
                ivjPLotAdd.add(getAddResids(), constraintsAddResids);
                java.awt.GridBagConstraints constraintsJLabel5 = new java.awt.GridBagConstraints();
                constraintsJLabel5.gridx = 0;
                constraintsJLabel5.gridy = 0;
                constraintsJLabel5.anchor = java.awt.GridBagConstraints.EAST;
                constraintsJLabel5.weightx = 1.0;
                constraintsJLabel5.insets = new java.awt.Insets(5,5,5,10);
                java.awt.GridBagConstraints constraintsSquaredResPlot = new java.awt.GridBagConstraints();
                constraintsSquaredResPlot.gridx = 2;
                constraintsSquaredResPlot.gridy = 2;
                constraintsSquaredResPlot.gridwidth = 2;
                constraintsSquaredResPlot.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsSquaredResPlot.anchor = java.awt.GridBagConstraints.WEST;
                constraintsSquaredResPlot.insets = new java.awt.Insets(5,15,0,0);
                ivjPLotAdd.add(getMultGraph(), constraintsMultGraph);
                ivjPLotAdd.add(getDataTableScrollPane(), constraintsGaussDataTableScrollPane);
                ivjPLotAdd.add(getJLabel5(), constraintsJLabel5);
                ivjPLotAdd.add(getSquaredResPlot(), constraintsSquaredResPlot);
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjPLotAdd;
    }

    /**
     * Gets the variable name for residuals.
     * 
     * @return variable name of data object
     */
    public String getResids() {
        if (resids == null)
            return null;

        return resids.name;
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
                        .setPreferredSize(new java.awt.Dimension(200, 100));
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

    private JSCSArrayTable getScrollPaneTable_Nu() {
        if (ivjScrollPaneTable_Nu == null) {
            try {
                ivjScrollPaneTable_Nu = new JSCSArrayTable();
                ivjScrollPaneTable_Nu.setName("ScrollPaneTable_Nu");
                getDataTableScrollPane().setColumnHeaderView(
                        ivjScrollPaneTable_Nu.getTableHeader());
                ivjScrollPaneTable_Nu.setBounds(0, 0, 144, 104);
                ivjScrollPaneTable_Nu.setRowSelectionAllowed(true);
                ivjScrollPaneTable_Nu.setEditable(false);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjScrollPaneTable_Nu;
    }

    /**
     * Return the SquaredResPlot property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getSquaredResPlot() {
        if (ivjSquaredResPlot == null) {
            try {
                ivjSquaredResPlot = new javax.swing.JCheckBox();
                ivjSquaredResPlot.setName("SquaredResPlot");
                ivjSquaredResPlot.setPreferredSize(new java.awt.Dimension(150,
                        25));
                ivjSquaredResPlot.setText("Plot squared residuals");
                ivjSquaredResPlot
                        .setMinimumSize(new java.awt.Dimension(60, 25));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSquaredResPlot;
    }

    /**
     * Return the StdResids property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getStdResids() {
        if (ivjStdResids == null) {
            try {
                ivjStdResids = new javax.swing.JCheckBox();
                ivjStdResids.setName("StdResids");
                ivjStdResids.setPreferredSize(new java.awt.Dimension(150, 25));
                ivjStdResids.setText("Plot standardized residuals");
                ivjStdResids.setMinimumSize(new java.awt.Dimension(60, 25));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjStdResids;
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
        getAddResids().addActionListener(ivjEventHandler);
        getPlot().addActionListener(ivjEventHandler);
    }

    /**
     * Initialize the class.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initialize() {
        try {
            // user code begin {1}
            // user code end
            setName("PlotAddResids");
            setLayout(new java.awt.BorderLayout());
            setSize(576, 337);
            add(getResultField(), "Center");
            add(getPLotAdd(), "North");
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
    private void plot_ActionEvents() {
        if (isUnivariateOnly)
            getScrollPaneTable_Nu().getSelectionModel().setSelectionInterval(0,
                    0);

        if (getScrollPaneTable_Nu().getSelectedRow() == -1) 
            getScrollPaneTable_Nu().getSelectionModel().setSelectionInterval(0,
                    getScrollPaneTable_Nu().getRowCount() - 1);
        

        setSelectedData();

        double[][] resArray = residData;

        if (getStdResids().isSelected()) {
            // Standardization done in PlotTSCall. Just substract mean
            namData = UData.appendSuffix(namData, "_std", namData.name());
            double[] meanc = UMatrix.meanc(resArray);
            for (int i = 0; i < meanc.length; i++)
                for (int j = 0; j < resArray.length; j++)
                    resArray[j][i] = resArray[j][i] - meanc[i];

        }
        if (getSquaredResPlot().isSelected()) { // Square is done here.
            resArray = UMatrix.pow(resArray, 2);
            namData = UData.appendSuffix(namData, "^2", namData.name());
        }

        SymbolTable sTable = scope.getSymbolTable(this);
        // Plot Resids.
        PCall jobPlot = new PlotTSCall(
                new JSCNArray("PlotResids", resArray),
                sTable.get(dRange).getJSCDRange().getTSDateRange().lowerBound(),
                namData, getMultGraph().isSelected(), getStdResids()
                        .isSelected());
        jobPlot.execute();

    }

    /**
     * Gets selected resids and builds matrices.
     * 
     */
    private void setSelectedData() {

        int[] index = getScrollPaneTable_Nu().getIntSelectionIndex();
        SymbolTable sTable = scope.getSymbolTable(this);

        namData = new JSCSArray("PlotResidsNames", sTable.get(nameResids)
                .getJSCSArray().selRowsIf(index));

        residData = sTable.get(resids).getJSCNArray().selColsIf(index);
    }

    /**
     * Sets the variable name for the names of residuals.
     * 
     * @param newNameResids
     */
    public void setNameResids(String newNameResids) {
        if (newNameResids == null)
            nameResids = null;
        else
            nameResids = new JSCTypeDef(newNameResids, JSCTypes.SARRAY);
        getScrollPaneTable_Nu().setSymbolName(newNameResids);

    }

    /**
     * Sets the variable name for the date range.
     * 
     * @param newDRange
     */
    public void setDRange(String newDRange) {
        if (newDRange == null)
            dRange = null;
        else
            dRange = new JSCTypeDef(newDRange, JSCTypes.DRANGE);
    }

    /**
     * Sets the variable name for the residuals.
     * 
     * @param newResids
     */
    public void setResids(String newResids) {
        if (newResids == null)
            resids = null;
        else
            resids = new JSCTypeDef(newResids, JSCTypes.NARRAY);

    }

    /**
     * @return
     */
    public Scope getScope() {
        return scope;
    }

    /**
     * @param scope
     */
    public void setScope(Scope scope) {
        if (scope == null)
            throw new IllegalArgumentException("Argument was null.");
        this.scope = scope;
    }

    public void setUnivariateOnly(boolean isUnivariate) {
        getMultGraph().setSelected(!isUnivariate);
        getMultGraph().setVisible(!isUnivariate);

        getDataTableScrollPane().setVisible(!isUnivariate);
        getJLabel5().setVisible(!isUnivariate);

        isUnivariateOnly = isUnivariate;
    }

}