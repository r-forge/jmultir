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

package de.jmulti.initanal;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

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

import de.jmulti.proc.CrossPlotCall;

/**
 * Panel for crossplotting series.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
public class CrossPlotPanel extends ModelPanel {
    private static final Logger log = Logger.getLogger(CrossPlotPanel.class);

    private JButton ivjCrossPlot = null;

    private JPanel ivjCrossPlotsTab = null;

    private JSCDataTableScrollPane ivjDataTableScrollPaneX = null;

    private JSCDataTableScrollPane ivjDataTableScrollPaneY = null;

    private JComboBox ivjKernelChooser = null;

    private JCheckBox ivjNWEstimate = null;

    private JCheckBox ivjOls = null;

    private ResultField ivjResultField = null;

    private JSCSArrayTable ivjScrollPaneTable_X = null;

    private JSCSArrayTable ivjScrollPaneTable_Y = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    class IvjEventHandler implements java.awt.event.ActionListener {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == CrossPlotPanel.this.getCrossPlot())
                connEtoC1();
        };
    };

    /**
     * CrossPlotPanel constructor comment.
     */
    public CrossPlotPanel() {
        super();
        initialize();
    }

    /**
     * connEtoC1: (CrossPlot.action. -->
     * CrossPlotPanel.crossPlot_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC1() {
        try {
            // user code begin {1}
            // user code end
            this.crossPlot_ActionEvents();
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
    private void crossPlot_ActionEvents() {
        int x = getScrollPaneTable_X().getSelectedRow();
        int y = getScrollPaneTable_Y().getSelectedRow();

        if (x == -1 || y == -1) {
            StdMessages
                    .infoNothingSelected("Please select one series for x and one for y.");
            return;
        }
        JSCSArray names = global().get(WorkbenchPanel.WB_DATANAMES)
                .getJSCSArray();
        String xName = names.stringAt(x, 0);
        String yName = names.stringAt(y, 0);

        JSCNArray data = global().get(WorkbenchPanel.WB_DATA).getJSCNArray();
        TSDateRange range = global().get(WorkbenchPanel.WB_DRANGE)
                .getJSCDRange().getTSDateRange();
        JSCNArray xData = new JSCNArray("xData", data.getCol(x));

        JSCNArray yData = new JSCNArray("yData", data.getCol(y));

        PCall job = new CrossPlotCall(xData, yData, xName, yName, range,
                getOls().isSelected(), getNWEstimate().isSelected(),
                getKernelChooser().getSelectedIndex());
        job.setOutHolder(getResultField());
        job.execute();

    }

    /**
     * Return the CrossPlot property value.
     * 
     * @return javax.swing.JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JButton getCrossPlot() {
        if (ivjCrossPlot == null) {
            try {
                ivjCrossPlot = new javax.swing.JButton();
                ivjCrossPlot.setName("CrossPlot");
                ivjCrossPlot.setPreferredSize(new java.awt.Dimension(100, 27));
                ivjCrossPlot.setMinimumSize(new java.awt.Dimension(100, 27));
                ivjCrossPlot.setText("Execute");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjCrossPlot;
    }

    /**
     * Return the CrossPlotsTab property value.
     * 
     * @return javax.swing.JPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JPanel getCrossPlotsTab() {
        if (ivjCrossPlotsTab == null) {
            try {
                ivjCrossPlotsTab = new javax.swing.JPanel();
                ivjCrossPlotsTab.setName("CrossPlotsTab");
                ivjCrossPlotsTab.setPreferredSize(new java.awt.Dimension(246,
                        130));
                ivjCrossPlotsTab
                        .setBorder(new BevelBorder(BevelBorder.LOWERED));
                ivjCrossPlotsTab.setLayout(new java.awt.GridBagLayout());
                ivjCrossPlotsTab
                        .setMinimumSize(new java.awt.Dimension(246, 130));

                java.awt.GridBagConstraints constraintsOls = new java.awt.GridBagConstraints();
                constraintsOls.gridx = 2;
                constraintsOls.gridy = 0;
                constraintsOls.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsOls.anchor = java.awt.GridBagConstraints.WEST;
                constraintsOls.weightx = 1.0;
                constraintsOls.insets = new java.awt.Insets(5, 10, 0, 0);
                getCrossPlotsTab().add(getOls(), constraintsOls);

                java.awt.GridBagConstraints constraintsCrossPlot = new java.awt.GridBagConstraints();
                constraintsCrossPlot.gridx = 2;
                constraintsCrossPlot.gridy = 5;
                constraintsCrossPlot.gridwidth = 3;
                constraintsCrossPlot.anchor = java.awt.GridBagConstraints.SOUTHEAST;
                constraintsCrossPlot.insets = new java.awt.Insets(0, 0, 5, 5);
                getCrossPlotsTab().add(getCrossPlot(), constraintsCrossPlot);

                java.awt.GridBagConstraints constraintsGaussDataTableScrollPaneX = new java.awt.GridBagConstraints();
                constraintsGaussDataTableScrollPaneX.gridx = 0;
                constraintsGaussDataTableScrollPaneX.gridy = 0;
                constraintsGaussDataTableScrollPaneX.gridheight = 6;
                constraintsGaussDataTableScrollPaneX.fill = java.awt.GridBagConstraints.BOTH;
                constraintsGaussDataTableScrollPaneX.anchor = java.awt.GridBagConstraints.WEST;
                constraintsGaussDataTableScrollPaneX.weighty = 1.0;
                constraintsGaussDataTableScrollPaneX.insets = new java.awt.Insets(
                        5, 5, 0, 0);
                getCrossPlotsTab().add(getJSCDataTableScrollPaneX(),
                        constraintsGaussDataTableScrollPaneX);

                java.awt.GridBagConstraints constraintsGaussDataTableScrollPaneY = new java.awt.GridBagConstraints();
                constraintsGaussDataTableScrollPaneY.gridx = 1;
                constraintsGaussDataTableScrollPaneY.gridy = 0;
                constraintsGaussDataTableScrollPaneY.gridheight = 6;
                constraintsGaussDataTableScrollPaneY.fill = java.awt.GridBagConstraints.BOTH;
                constraintsGaussDataTableScrollPaneY.anchor = java.awt.GridBagConstraints.WEST;
                constraintsGaussDataTableScrollPaneY.weighty = 1.0;
                constraintsGaussDataTableScrollPaneY.insets = new java.awt.Insets(
                        5, 5, 0, 0);
                getCrossPlotsTab().add(getJSCDataTableScrollPaneY(),
                        constraintsGaussDataTableScrollPaneY);

                java.awt.GridBagConstraints constraintsNWEstimate = new java.awt.GridBagConstraints();
                constraintsNWEstimate.gridx = 2;
                constraintsNWEstimate.gridy = 1;
                constraintsNWEstimate.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsNWEstimate.anchor = java.awt.GridBagConstraints.WEST;
                constraintsNWEstimate.insets = new java.awt.Insets(0, 10, 0, 0);
                getCrossPlotsTab().add(getNWEstimate(), constraintsNWEstimate);

                java.awt.GridBagConstraints constraintsKernelChooser = new java.awt.GridBagConstraints();
                constraintsKernelChooser.gridx = 2;
                constraintsKernelChooser.gridy = 2;
                constraintsKernelChooser.gridwidth = 3;
                constraintsKernelChooser.anchor = java.awt.GridBagConstraints.WEST;
                constraintsKernelChooser.insets = new java.awt.Insets(5, 10, 5,
                        5);
                getCrossPlotsTab().add(getKernelChooser(),
                        constraintsKernelChooser);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjCrossPlotsTab;
    }

    private JSCDataTableScrollPane getJSCDataTableScrollPaneX() {
        if (ivjDataTableScrollPaneX == null) {
            try {
                ivjDataTableScrollPaneX = new JSCDataTableScrollPane();
                ivjDataTableScrollPaneX.setName("DataTableScrollPaneX");
                ivjDataTableScrollPaneX.setMaximumVisibleColumns(1);
                String ivjLocal55columnHeaderStringData[] = { " x-axis" };
                ivjDataTableScrollPaneX
                        .setColumnHeaderStringData(ivjLocal55columnHeaderStringData);
                ivjDataTableScrollPaneX.setColumnHeaderShowing(true);
                ivjDataTableScrollPaneX.setMaximumVisibleRows(6);
                getJSCDataTableScrollPaneX().setViewportView(
                        getScrollPaneTable_X());
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjDataTableScrollPaneX;
    }

    private JSCDataTableScrollPane getJSCDataTableScrollPaneY() {
        if (ivjDataTableScrollPaneY == null) {
            try {
                ivjDataTableScrollPaneY = new JSCDataTableScrollPane();
                ivjDataTableScrollPaneY.setName("DataTableScrollPaneY");
                ivjDataTableScrollPaneY.setMaximumVisibleColumns(1);
                String ivjLocal55columnHeaderStringData[] = { " y-axis" };
                ivjDataTableScrollPaneY
                        .setColumnHeaderStringData(ivjLocal55columnHeaderStringData);
                ivjDataTableScrollPaneY.setColumnHeaderShowing(true);
                ivjDataTableScrollPaneY.setMaximumVisibleRows(6);
                getJSCDataTableScrollPaneY().setViewportView(
                        getScrollPaneTable_Y());
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjDataTableScrollPaneY;
    }

    /**
     * Return the KernelChooser property value.
     * 
     * @return javax.swing.JComboBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JComboBox getKernelChooser() {
        if (ivjKernelChooser == null) {
            try {
                ivjKernelChooser = new javax.swing.JComboBox();
                ivjKernelChooser.setName("KernelChooser");
                ivjKernelChooser.setPreferredSize(new java.awt.Dimension(180,
                        25));
                ivjKernelChooser
                        .setMinimumSize(new java.awt.Dimension(150, 25));
                // user code begin {1}
                ivjKernelChooser.addItem("Gaussian kernel");
                ivjKernelChooser.addItem("biweight kernel");
                ivjKernelChooser.addItem("rectangular kernel");
                ivjKernelChooser.addItem("triangular kernel");
                ivjKernelChooser.addItem("Epanechnikov kernel");
                ivjKernelChooser.setSelectedIndex(0);

                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjKernelChooser;
    }

    /**
     * Return the NWEstimate property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getNWEstimate() {
        if (ivjNWEstimate == null) {
            try {
                ivjNWEstimate = new javax.swing.JCheckBox();
                ivjNWEstimate.setName("NWEstimate");
                ivjNWEstimate.setText("Nadaraya-Watson regression");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjNWEstimate;
    }

    /**
     * Return the Ols property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getOls() {
        if (ivjOls == null) {
            try {
                ivjOls = new javax.swing.JCheckBox();
                ivjOls.setName("Ols");
                ivjOls.setText("OLS regression");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjOls;
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
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjResultField;
    }

    private JSCSArrayTable getScrollPaneTable_X() {
        if (ivjScrollPaneTable_X == null) {
            try {
                ivjScrollPaneTable_X = new JSCSArrayTable();
                ivjScrollPaneTable_X.setName("ScrollPaneTable_X");
                getJSCDataTableScrollPaneX().setColumnHeaderView(
                        ivjScrollPaneTable_X.getTableHeader());
                ivjScrollPaneTable_X.setRowSelectionMode(0);
                ivjScrollPaneTable_X.setBounds(0, 0, 144, 104);
                ivjScrollPaneTable_X.setRowSelectionAllowed(true);
                ivjScrollPaneTable_X.setEditable(false);
                // user code begin {1}
                ivjScrollPaneTable_X
                        .setSymbolName(WorkbenchPanel.WB_DATANAMES.name);
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjScrollPaneTable_X;
    }

    private JSCSArrayTable getScrollPaneTable_Y() {
        if (ivjScrollPaneTable_Y == null) {
            try {
                ivjScrollPaneTable_Y = new JSCSArrayTable();
                ivjScrollPaneTable_Y.setName("ScrollPaneTable_Y");
                getJSCDataTableScrollPaneY().setColumnHeaderView(
                        ivjScrollPaneTable_Y.getTableHeader());
                ivjScrollPaneTable_Y.setRowSelectionMode(0);
                ivjScrollPaneTable_Y.setBounds(0, 0, 144, 104);
                ivjScrollPaneTable_Y.setRowSelectionAllowed(true);
                ivjScrollPaneTable_Y.setEditable(false);
                // user code begin {1}
                ivjScrollPaneTable_Y
                        .setSymbolName(WorkbenchPanel.WB_DATANAMES.name);
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjScrollPaneTable_Y;
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
        getCrossPlot().addActionListener(ivjEventHandler);
    }

    /**
     * Initialize the class.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initialize() {
        try {
            // user code begin {1}
            // user code end
            setName("CrossPlotPanel");
            setLayout(new java.awt.BorderLayout());
            setSize(505, 318);
            add(getResultField(), "Center");
            add(getCrossPlotsTab(), "North");
            initConnections();
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}
        // user code end
    }
}