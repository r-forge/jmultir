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
import javax.swing.border.BevelBorder;

import org.apache.log4j.Logger;

import com.jstatcom.component.StdMessages;
import com.jstatcom.engine.PCall;
import com.jstatcom.model.JSCDRange;
import com.jstatcom.model.JSCData;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.JSCSArray;
import com.jstatcom.model.ModelPanel;
import com.jstatcom.ts.TSDate;
import com.jstatcom.ts.TSDateRange;
import com.jstatcom.util.UData;

import de.jmulti.proc.PlotTSCall;

/**
 * Panel for plotting the selected time series.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public class PlotPanel extends ModelPanel {
    private static final Logger log = Logger.getLogger(PlotPanel.class);

    private JButton ivjJButton = null;

    private JCheckBox ivjCompletePlot = null;

    private JCheckBox ivjIndexed = null;

    private JCheckBox ivjMultGraph = null;

    private JCheckBox ivjStd = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    class IvjEventHandler implements java.awt.event.ActionListener {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == PlotPanel.this.getJButton())
                connEtoC1();
        };
    };

    /**
     * PlotPanel constructor comment.
     */
    public PlotPanel() {
        super();
        initialize();
    }

    /**
     * connEtoC1: (JButton.action. --> PlotPanel.plotButton_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC1() {
        try {
            // user code begin {1}
            // user code end
            this.plotButton_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * Return the JCheckBox3 property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getCompletePlot() {
        if (ivjCompletePlot == null) {
            try {
                ivjCompletePlot = new javax.swing.JCheckBox();
                ivjCompletePlot.setName("CompletePlot");
                ivjCompletePlot.setText("Plot complete series");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjCompletePlot;
    }

    /**
     * Return the JCheckBox property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getIndexed() {
        if (ivjIndexed == null) {
            try {
                ivjIndexed = new javax.swing.JCheckBox();
                ivjIndexed.setName("Indexed");
                ivjIndexed.setPreferredSize(new java.awt.Dimension(300, 25));
                ivjIndexed.setText("Time series indexed (mean = 100)");
                ivjIndexed.setMaximumSize(new java.awt.Dimension(250, 25));
                ivjIndexed.setMinimumSize(new java.awt.Dimension(250, 25));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjIndexed;
    }

    /**
     * Return the JButton property value.
     * 
     * @return javax.swing.JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JButton getJButton() {
        if (ivjJButton == null) {
            try {
                ivjJButton = new javax.swing.JButton();
                ivjJButton.setName("JButton");
                ivjJButton.setText("Plot Series");
                ivjJButton.setPreferredSize(new java.awt.Dimension(135, 27));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJButton;
    }

    /**
     * Return the JCheckBox2 property value.
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
     * Return the JCheckBox1 property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getStd() {
        if (ivjStd == null) {
            try {
                ivjStd = new javax.swing.JCheckBox();
                ivjStd.setName("Std");
                ivjStd.setText("Divide by standard deviation");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjStd;
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
        getJButton().addActionListener(ivjEventHandler);
    }

    /**
     * Initialize the class.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initialize() {
        try {
            // user code begin {1}
            // user code end
            setName("PlotPanel");
            setBorder(new BevelBorder(BevelBorder.LOWERED));
            setLayout(new java.awt.GridBagLayout());
            setSize(556, 301);

            java.awt.GridBagConstraints constraintsIndexed = new java.awt.GridBagConstraints();
            constraintsIndexed.gridx = 0;
            constraintsIndexed.gridy = 0;
            constraintsIndexed.fill = java.awt.GridBagConstraints.HORIZONTAL;
            constraintsIndexed.anchor = java.awt.GridBagConstraints.SOUTH;
            constraintsIndexed.weightx = 1.0;
            constraintsIndexed.insets = new java.awt.Insets(5, 5, 0, 0);
            add(getIndexed(), constraintsIndexed);

            java.awt.GridBagConstraints constraintsStd = new java.awt.GridBagConstraints();
            constraintsStd.gridx = 0;
            constraintsStd.gridy = 1;
            constraintsStd.fill = java.awt.GridBagConstraints.HORIZONTAL;
            constraintsStd.insets = new java.awt.Insets(5, 5, 0, 0);
            add(getStd(), constraintsStd);

            java.awt.GridBagConstraints constraintsMultGraph = new java.awt.GridBagConstraints();
            constraintsMultGraph.gridx = 0;
            constraintsMultGraph.gridy = 2;
            constraintsMultGraph.fill = java.awt.GridBagConstraints.HORIZONTAL;
            constraintsMultGraph.insets = new java.awt.Insets(5, 5, 0, 0);
            add(getMultGraph(), constraintsMultGraph);

            java.awt.GridBagConstraints constraintsCompletePlot = new java.awt.GridBagConstraints();
            constraintsCompletePlot.gridx = 0;
            constraintsCompletePlot.gridy = 3;
            constraintsCompletePlot.fill = java.awt.GridBagConstraints.HORIZONTAL;
            constraintsCompletePlot.anchor = java.awt.GridBagConstraints.NORTHWEST;
            constraintsCompletePlot.insets = new java.awt.Insets(5, 5, 5, 0);
            add(getCompletePlot(), constraintsCompletePlot);

            java.awt.GridBagConstraints constraintsJButton = new java.awt.GridBagConstraints();
            constraintsJButton.gridx = 1;
            constraintsJButton.gridy = 3;
            constraintsJButton.anchor = java.awt.GridBagConstraints.NORTHEAST;
            constraintsJButton.weightx = 1.0;
            constraintsJButton.weighty = 1.0;
            constraintsJButton.insets = new java.awt.Insets(0, 5, 5, 5);
            add(getJButton(), constraintsJButton);
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
    private void plotButton_ActionEvents() {
        if (global().get(WorkbenchPanel.WB_DATA).isEmpty()) {
            StdMessages
                    .infoNothingSelected("Please select at least one series.");
            return;
        }

        TSDateRange range = global().get(WorkbenchPanel.WB_DRANGE)
                .getJSCDRange().getTSDateRange();

        TSDate earliestStart = range.lowerBound();

        JSCSArray namesOfData = global().get(WorkbenchPanel.WB_DATANAMES)
                .getJSCSArray();
        JSCNArray allData = global().get(WorkbenchPanel.WB_DATA).getJSCNArray();

        // Merge TS.
        if (getCompletePlot().isSelected()) {
            JSCData[] all = UData.mergeTS(namesOfData, "plotDat",
                    "earliestStart");
            allData = (JSCNArray) all[0];
            earliestStart = ((JSCDRange) all[1]).getTSDateRange().lowerBound();
        }

        if (getStd().isSelected()) // Standardization done in PlotTSCall.
            namesOfData = UData.appendSuffix(namesOfData, "_std", namesOfData
                    .name());

        if (getIndexed().isSelected()) {
            allData = UData.indexData(allData, allData.name());
            namesOfData = UData.appendSuffix(namesOfData, "_index", namesOfData
                    .name());
        }

        // Plot Resids.
        PCall jobPlot = new PlotTSCall(allData, earliestStart, namesOfData,
                getMultGraph().isSelected(), getStd().isSelected());
        jobPlot.execute();

    }
}