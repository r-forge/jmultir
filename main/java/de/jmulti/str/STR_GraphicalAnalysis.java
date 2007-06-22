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

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import com.jstatcom.component.CompSettings;
import com.jstatcom.component.ResultField;
import com.jstatcom.component.StdMessages;
import com.jstatcom.engine.PCall;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.JSCSArray;
import com.jstatcom.model.ModelPanel;
import com.jstatcom.ts.TS;
import com.jstatcom.ts.TSDateRange;
import com.jstatcom.ts.TSHolder;
import com.jstatcom.util.FArg;
import com.jstatcom.util.UData;
import com.jstatcom.util.UMatrix;

import de.jmulti.proc.CrossPlotCall;
import de.jmulti.proc.PlotTSCall;

/**
 * Panel for graphical analysis of estimated STR model.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */

final class STR_GraphicalAnalysis extends ModelPanel {
    private static final Logger log = Logger
            .getLogger(STR_GraphicalAnalysis.class);

    private TSHolder tsHolder = TSHolder.getInstance();

    private JPanel ivjControlPanel = null;

    private ResultField ivjResultField = null;

    private JButton ivjAddRes = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private JButton ivjExecMiscPlot = null;

    private JCheckBox ivjMultGraph = null;

    private JCheckBox ivjPlotFit = null;

    private JCheckBox ivjPlotLin = null;

    private JCheckBox ivjPlotNonlin = null;

    private JButton ivjPlotRes = null;

    private JCheckBox ivjPlotTrans = null;

    private JCheckBox ivjPlotTransVar = null;

    private JCheckBox ivjSQRRes = null;

    private JCheckBox ivjStdRes = null;

    private JPanel ivjJPanel1 = null;

    private JPanel ivjJPanel2 = null;

    private JButton ivjShowVal = null;

    private JCheckBox ivjPlotOrig = null;

    private javax.swing.JLabel jLabel = null;

    private javax.swing.JCheckBox jCheckBox = null;

    class IvjEventHandler implements java.awt.event.ActionListener {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == STR_GraphicalAnalysis.this.getPlotRes())
                connEtoC1();
            if (e.getSource() == STR_GraphicalAnalysis.this.getExecMiscPlot())
                connEtoC2();
            if (e.getSource() == STR_GraphicalAnalysis.this.getAddRes())
                connEtoC3();
            if (e.getSource() == STR_GraphicalAnalysis.this.getShowVal())
                connEtoC4();
        };
    };

    /**
     * StrModelCheck constructor comment.
     */
    public STR_GraphicalAnalysis() {
        super();
        initialize();
    }

    /**
     * Comment
     */
    private void addRes_ActionEvents() {
        plotAddResids(2);
        return;
    }

    /**
     * connEtoC1: (ExecRes.action. --> StrModelCheck.execRes_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC1() {
        try {
            // user code begin {1}
            // user code end
            this.execRes_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC2: (ExecMiscPlot.action. -->
     * StrModelCheck.execMiscPlot_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC2() {
        try {
            // user code begin {1}
            // user code end
            this.execMiscPlot_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC3: (AddRes.action. --> StrModelCheck.addRes_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC3() {
        try {
            // user code begin {1}
            // user code end
            this.addRes_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC4: (ShowVal.action. --> StrModelCheck.showVal_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC4() {
        try {
            // user code begin {1}
            // user code end
            this.showVal_ActionEvents();
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
    private void execMiscPlot_ActionEvents() {
        plotShowPartsOfModel(1);
    }

    /**
     * Comment
     */
    private void execRes_ActionEvents() {
        plotAddResids(1);
        return;
    }

    /**
     * Return the AddRes property value.
     * 
     * @return javax.swing.JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JButton getAddRes() {
        if (ivjAddRes == null) {
            try {
                ivjAddRes = new javax.swing.JButton();
                ivjAddRes.setName("AddRes");
                ivjAddRes.setPreferredSize(new java.awt.Dimension(110, 27));
                ivjAddRes.setText("Add to Dataset");
                ivjAddRes.setMinimumSize(new java.awt.Dimension(110, 27));
                ivjAddRes.setMargin(new java.awt.Insets(2, 2, 2, 2));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjAddRes;
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
                ivjControlPanel
                        .setPreferredSize(new java.awt.Dimension(0, 200));
                ivjControlPanel.setLayout(new java.awt.BorderLayout());
                ivjControlPanel
                        .setMinimumSize(new java.awt.Dimension(230, 130));
                getControlPanel().add(getJPanel1(), "West");
                getControlPanel().add(getJPanel2(), "Center");
                // user code begin {1}
                TitledBorder title = new TitledBorder(new BevelBorder(
                        BevelBorder.LOWERED), "Graphical Analysis",
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
     * Return the ExecMiscPlot property value.
     * 
     * @return javax.swing.JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JButton getExecMiscPlot() {
        if (ivjExecMiscPlot == null) {
            try {
                ivjExecMiscPlot = new javax.swing.JButton();
                ivjExecMiscPlot.setName("ExecMiscPlot");
                ivjExecMiscPlot
                        .setPreferredSize(new java.awt.Dimension(110, 27));
                ivjExecMiscPlot.setText("Plot");
                ivjExecMiscPlot.setMinimumSize(new java.awt.Dimension(110, 27));
                ivjExecMiscPlot.setMaximumSize(new java.awt.Dimension(200, 27));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjExecMiscPlot;
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
                ivjJPanel1.setPreferredSize(new java.awt.Dimension(230, 150));
                ivjJPanel1.setLayout(new java.awt.GridBagLayout());
                ivjJPanel1.setMinimumSize(new java.awt.Dimension(230, 150));

                java.awt.GridBagConstraints constraintsSQRRes = new java.awt.GridBagConstraints();
                constraintsSQRRes.gridx = 0;
                constraintsSQRRes.gridy = 1;
                constraintsSQRRes.gridwidth = 2;
                constraintsSQRRes.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsSQRRes.anchor = java.awt.GridBagConstraints.WEST;
                constraintsSQRRes.insets = new java.awt.Insets(0, 5, 0, 0);
                getJPanel1().add(getSQRRes(), constraintsSQRRes);

                java.awt.GridBagConstraints constraintsStdRes = new java.awt.GridBagConstraints();
                constraintsStdRes.gridx = 0;
                constraintsStdRes.gridy = 0;
                constraintsStdRes.gridwidth = 2;
                constraintsStdRes.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsStdRes.anchor = java.awt.GridBagConstraints.WEST;
                constraintsStdRes.insets = new java.awt.Insets(5, 5, 0, 0);
                getJPanel1().add(getStdRes(), constraintsStdRes);

                java.awt.GridBagConstraints constraintsPlotRes = new java.awt.GridBagConstraints();
                constraintsPlotRes.gridx = 0;
                constraintsPlotRes.gridy = 3;
                constraintsPlotRes.anchor = java.awt.GridBagConstraints.SOUTHWEST;
                constraintsPlotRes.weightx = 1.0;
                constraintsPlotRes.weighty = 0.0D;
                constraintsPlotRes.insets = new java.awt.Insets(5, 5, 5, 5);
                java.awt.GridBagConstraints constraintsAddRes = new java.awt.GridBagConstraints();
                java.awt.GridBagConstraints consGridBagConstraints1 = new java.awt.GridBagConstraints();
                consGridBagConstraints1.gridy = 2;
                consGridBagConstraints1.gridx = 1;
                consGridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
                consGridBagConstraints1.insets = new java.awt.Insets(10, 5, 10,
                        5);
                consGridBagConstraints1.weightx = 0.0D;
                consGridBagConstraints1.weighty = 1.0D;
                consGridBagConstraints1.anchor = java.awt.GridBagConstraints.SOUTH;
                constraintsAddRes.gridx = 1;
                constraintsAddRes.gridy = 3;
                constraintsAddRes.anchor = java.awt.GridBagConstraints.SOUTHWEST;
                constraintsAddRes.weightx = 1.0;
                constraintsAddRes.weighty = 0.0D;
                constraintsAddRes.insets = new java.awt.Insets(5, 5, 5, 5);
                ivjJPanel1.add(getPlotRes(), constraintsPlotRes);
                ivjJPanel1.add(getAddRes(), constraintsAddRes);
                ivjJPanel1.add(getJLabel(), consGridBagConstraints1);
                TitledBorder title = new TitledBorder(new EtchedBorder(),
                        "Residuals", TitledBorder.RIGHT, TitledBorder.TOP);
                getJPanel1().setBorder(title);

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
                ivjJPanel2.setLayout(new java.awt.GridBagLayout());

                java.awt.GridBagConstraints constraintsPlotTrans = new java.awt.GridBagConstraints();
                constraintsPlotTrans.gridx = 0;
                constraintsPlotTrans.gridy = 0;
                constraintsPlotTrans.anchor = java.awt.GridBagConstraints.WEST;
                constraintsPlotTrans.insets = new java.awt.Insets(5, 5, 0, 0);
                getJPanel2().add(getPlotTrans(), constraintsPlotTrans);

                java.awt.GridBagConstraints constraintsPlotFit = new java.awt.GridBagConstraints();
                constraintsPlotFit.gridx = 0;
                constraintsPlotFit.gridy = 1;
                constraintsPlotFit.anchor = java.awt.GridBagConstraints.WEST;
                constraintsPlotFit.insets = new java.awt.Insets(0, 5, 0, 0);
                getJPanel2().add(getPlotFit(), constraintsPlotFit);

                java.awt.GridBagConstraints constraintsPlotNonlin = new java.awt.GridBagConstraints();
                constraintsPlotNonlin.gridx = 1;
                constraintsPlotNonlin.gridy = 0;
                constraintsPlotNonlin.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsPlotNonlin.anchor = java.awt.GridBagConstraints.WEST;
                constraintsPlotNonlin.insets = new java.awt.Insets(5, 10, 0, 0);
                getJPanel2().add(getPlotNonlin(), constraintsPlotNonlin);

                java.awt.GridBagConstraints constraintsPlotLin = new java.awt.GridBagConstraints();
                constraintsPlotLin.gridx = 1;
                constraintsPlotLin.gridy = 1;
                constraintsPlotLin.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsPlotLin.anchor = java.awt.GridBagConstraints.WEST;
                constraintsPlotLin.weightx = 1.0;
                constraintsPlotLin.insets = new java.awt.Insets(0, 10, 0, 0);
                getJPanel2().add(getPlotLin(), constraintsPlotLin);

                java.awt.GridBagConstraints constraintsPlotTransVar = new java.awt.GridBagConstraints();
                constraintsPlotTransVar.gridx = 1;
                constraintsPlotTransVar.gridy = 2;
                constraintsPlotTransVar.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsPlotTransVar.anchor = java.awt.GridBagConstraints.WEST;
                constraintsPlotTransVar.weightx = 1.0;
                constraintsPlotTransVar.insets = new java.awt.Insets(0, 10, 0,
                        0);
                getJPanel2().add(getPlotTransVar(), constraintsPlotTransVar);

                java.awt.GridBagConstraints constraintsMultGraph = new java.awt.GridBagConstraints();
                constraintsMultGraph.gridx = 0;
                constraintsMultGraph.gridy = 3;
                constraintsMultGraph.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsMultGraph.anchor = java.awt.GridBagConstraints.WEST;
                constraintsMultGraph.insets = new java.awt.Insets(20, 5, 0, 0);
                java.awt.GridBagConstraints constraintsExecMiscPlot = new java.awt.GridBagConstraints();
                constraintsExecMiscPlot.gridx = 0;
                constraintsExecMiscPlot.gridy = 4;
                constraintsExecMiscPlot.anchor = java.awt.GridBagConstraints.SOUTHWEST;
                constraintsExecMiscPlot.weighty = 1.0;
                constraintsExecMiscPlot.insets = new java.awt.Insets(5, 5, 5, 0);
                getJPanel2().add(getExecMiscPlot(), constraintsExecMiscPlot);

                java.awt.GridBagConstraints constraintsShowVal = new java.awt.GridBagConstraints();
                constraintsShowVal.gridx = 1;
                constraintsShowVal.gridy = 4;
                constraintsShowVal.anchor = java.awt.GridBagConstraints.SOUTHWEST;
                constraintsShowVal.weightx = 1.0;
                constraintsShowVal.weighty = 1.0;
                constraintsShowVal.insets = new java.awt.Insets(5, 10, 5, 0);
                getJPanel2().add(getShowVal(), constraintsShowVal);

                java.awt.GridBagConstraints constraintsPlotOrig = new java.awt.GridBagConstraints();
                java.awt.GridBagConstraints consGridBagConstraints11 = new java.awt.GridBagConstraints();
                consGridBagConstraints11.gridy = 3;
                consGridBagConstraints11.gridx = 1;
                consGridBagConstraints11.insets = new java.awt.Insets(0, 10, 0,
                        0);
                consGridBagConstraints11.fill = java.awt.GridBagConstraints.HORIZONTAL;
                consGridBagConstraints11.anchor = java.awt.GridBagConstraints.NORTH;
                constraintsMultGraph.gridwidth = 1;
                constraintsPlotOrig.gridx = 0;
                constraintsPlotOrig.gridy = 2;
                constraintsPlotOrig.anchor = java.awt.GridBagConstraints.WEST;
                constraintsPlotOrig.insets = new java.awt.Insets(0, 5, 0, 0);
                ivjJPanel2.add(getMultGraph(), constraintsMultGraph);
                getJPanel2().add(getPlotOrig(), constraintsPlotOrig);
                // user code begin {1}
                ivjJPanel2.add(getPlotTransFktVar(), consGridBagConstraints11);
                TitledBorder title = new TitledBorder(new EtchedBorder(),
                        "Parts of the Model", TitledBorder.RIGHT,
                        TitledBorder.TOP);
                getJPanel2().setBorder(title);

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
                ivjMultGraph.setText("Multiple graphs");
                ivjMultGraph.setSelected(true);
                ivjMultGraph.setPreferredSize(new java.awt.Dimension(150, 21));
                ivjMultGraph.setMinimumSize(new java.awt.Dimension(113, 21));
                ivjMultGraph
                        .setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
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
     * Return the PlotFit property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getPlotFit() {
        if (ivjPlotFit == null) {
            try {
                ivjPlotFit = new javax.swing.JCheckBox();
                ivjPlotFit.setName("PlotFit");
                ivjPlotFit.setSelected(true);
                ivjPlotFit.setPreferredSize(new java.awt.Dimension(150, 21));
                ivjPlotFit.setText("Fitted series");
                ivjPlotFit.setMinimumSize(new java.awt.Dimension(124, 21));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjPlotFit;
    }

    /**
     * Return the PlotLin property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getPlotLin() {
        if (ivjPlotLin == null) {
            try {
                ivjPlotLin = new javax.swing.JCheckBox();
                ivjPlotLin.setName("PlotLin");
                ivjPlotLin.setSelected(true);
                ivjPlotLin.setPreferredSize(new java.awt.Dimension(150, 21));
                ivjPlotLin.setText("Linear part");
                ivjPlotLin.setMinimumSize(new java.awt.Dimension(113, 21));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjPlotLin;
    }

    /**
     * Return the PlotNonlin property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getPlotNonlin() {
        if (ivjPlotNonlin == null) {
            try {
                ivjPlotNonlin = new javax.swing.JCheckBox();
                ivjPlotNonlin.setName("PlotNonlin");
                ivjPlotNonlin.setSelected(true);
                ivjPlotNonlin.setPreferredSize(new java.awt.Dimension(150, 21));
                ivjPlotNonlin.setText("Nonlinear part");
                ivjPlotNonlin.setMinimumSize(new java.awt.Dimension(124, 21));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjPlotNonlin;
    }

    /**
     * Return the PlotOrig property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getPlotOrig() {
        if (ivjPlotOrig == null) {
            try {
                ivjPlotOrig = new javax.swing.JCheckBox();
                ivjPlotOrig.setName("PlotOrig");
                ivjPlotOrig.setSelected(true);
                ivjPlotOrig.setPreferredSize(new java.awt.Dimension(150, 21));
                ivjPlotOrig.setText("Original series");
                ivjPlotOrig.setMinimumSize(new java.awt.Dimension(124, 21));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjPlotOrig;
    }

    /**
     * Return the ExecRes property value.
     * 
     * @return javax.swing.JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JButton getPlotRes() {
        if (ivjPlotRes == null) {
            try {
                ivjPlotRes = new javax.swing.JButton();
                ivjPlotRes.setName("PlotRes");
                ivjPlotRes.setPreferredSize(new java.awt.Dimension(110, 27));
                ivjPlotRes.setText("Plot");
                ivjPlotRes.setMinimumSize(new java.awt.Dimension(110, 27));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjPlotRes;
    }

    /**
     * Return the PlotTrans property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getPlotTrans() {
        if (ivjPlotTrans == null) {
            try {
                ivjPlotTrans = new javax.swing.JCheckBox();
                ivjPlotTrans.setName("PlotTrans");
                ivjPlotTrans.setSelected(true);
                ivjPlotTrans.setPreferredSize(new java.awt.Dimension(150, 21));
                ivjPlotTrans.setText("Transition function");
                ivjPlotTrans.setMinimumSize(new java.awt.Dimension(124, 21));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjPlotTrans;
    }

    /**
     * Return the PlotTransVar property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getPlotTransVar() {
        if (ivjPlotTransVar == null) {
            try {
                ivjPlotTransVar = new javax.swing.JCheckBox();
                ivjPlotTransVar.setName("PlotTransVar");
                ivjPlotTransVar.setSelected(true);
                ivjPlotTransVar
                        .setPreferredSize(new java.awt.Dimension(150, 21));
                ivjPlotTransVar.setText("Transition variable");
                ivjPlotTransVar.setMinimumSize(new java.awt.Dimension(113, 21));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjPlotTransVar;
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

    /**
     * Return the ShowVal property value.
     * 
     * @return javax.swing.JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JButton getShowVal() {
        if (ivjShowVal == null) {
            try {
                ivjShowVal = new javax.swing.JButton();
                ivjShowVal.setName("ShowVal");
                ivjShowVal.setPreferredSize(new java.awt.Dimension(110, 27));
                ivjShowVal.setText("Values as Text");
                ivjShowVal.setMinimumSize(new java.awt.Dimension(110, 27));
                ivjShowVal.setMaximumSize(new java.awt.Dimension(200, 27));
                // user code begin {1}
                ivjShowVal.setMargin(new java.awt.Insets(2, 5, 2, 5));
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjShowVal;
    }

    /**
     * Return the SQRRes property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getSQRRes() {
        if (ivjSQRRes == null) {
            try {
                ivjSQRRes = new javax.swing.JCheckBox();
                ivjSQRRes.setName("SQRRes");
                ivjSQRRes.setPreferredSize(new java.awt.Dimension(100, 21));
                ivjSQRRes.setText("Square resids for plot");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSQRRes;
    }

    /**
     * Return the StdRes property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getStdRes() {
        if (ivjStdRes == null) {
            try {
                ivjStdRes = new javax.swing.JCheckBox();
                ivjStdRes.setName("StdRes");
                ivjStdRes.setPreferredSize(new java.awt.Dimension(100, 21));
                ivjStdRes.setText("Standardize resids for plot");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjStdRes;
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
        getPlotRes().addActionListener(ivjEventHandler);
        getExecMiscPlot().addActionListener(ivjEventHandler);
        getAddRes().addActionListener(ivjEventHandler);
        getShowVal().addActionListener(ivjEventHandler);
    }

    /**
     * Initialize the class.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initialize() {
        try {
            // user code begin {1}
            // user code end
            setName("StrModelCheck");
            setLayout(new java.awt.BorderLayout());
            setSize(624, 323);
            add(getResultField(), "Center");
            add(getControlPanel(), "North");
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
    private void plotAddResids(int type) {
        TSDateRange range = global().get(STR_Constants.STR_T1).getJSCDRange()
                .getTSDateRange();

        JSCNArray rr = global().get(STR_Constants.STR_RTZ).getJSCNArray();
        JSCSArray namData = new JSCSArray("strNames", "str_resids");

        // Std done in procCall
        if (getStdRes().isSelected())
            namData = UData.appendSuffix(namData, "_std", namData.name());

        if (getSQRRes().isSelected()) {
            namData = UData.appendSuffix(namData, "^2", namData.name());
            rr = new JSCNArray(rr.name(), rr.getPow(2));
        }
        double[] res = rr.getCol(0);
        rr = new JSCNArray("str_Res", res);
        if (type == 1) {

            PCall job = new PlotTSCall(rr, range.lowerBound(), namData, false,
                    getStdRes().isSelected());
            job.setOutHolder(getResultField());
            job.execute();

        } else {
            TS addSeries = new TS(res, "str_resids", range.lowerBound());
            int accept = tsHolder.addTS(addSeries);
            if (accept == JOptionPane.YES_OPTION)
                getResultField().append(
                        "Residuals added " + CompSettings.getDateString()
                                + ".\n");
        }
    }

    /**
     * Comment
     */
    private void plotShowPartsOfModel(int type) {

        TSDateRange range = global().get(STR_Constants.STR_T1).getJSCDRange()
                .getTSDateRange();

        JSCNArray dataToPlot = new JSCNArray("toPlot");
        JSCSArray namesOfData = new JSCSArray("names");
        if (getPlotLin().isSelected()) {
            dataToPlot.appendCols(global().get(STR_Constants.STR_LINPART)
                    .getJSCNArray());
            namesOfData.appendRows(new JSCSArray("append", "Linear Part"));
        }
        if (getPlotNonlin().isSelected()) {
            dataToPlot.appendCols(global().get(STR_Constants.STR_NONLINPART)
                    .getJSCNArray());
            namesOfData.appendRows(new JSCSArray("append", "Nonlinear Part"));
        }
        if (getPlotFit().isSelected()) {
            dataToPlot.appendCols(global().get(STR_Constants.STR_FITTED)
                    .getJSCNArray());
            namesOfData.appendRows(new JSCSArray("append", "Fitted Series"));
        }
        if (getPlotOrig().isSelected()) {
            dataToPlot.appendCols(global().get(STR_Constants.STR_Y)
                    .getJSCNArray());
            namesOfData.appendRows(new JSCSArray("append", "Orig. Series"));
        }

        if (getPlotTrans().isSelected()) {
            dataToPlot.appendCols(new JSCNArray("append", global().get(
                    STR_Constants.STR_RTZ).getJSCNArray().getCol(1)));

            namesOfData.appendRows(new JSCSArray("append", "Trans. Function"));
        }
        int transIndex = global().get(STR_Constants.STR_TRANSINDEX).getJSCInt()
                .intVal();
        double[][] X = global().get(STR_Constants.STR_X).getJSCNArray()
                .getTransposed();

        double[] trans = UMatrix.getDoubleCol(X, transIndex);

        if (getPlotTransVar().isSelected()) {
            dataToPlot.appendCols(new JSCNArray("append", trans));
            namesOfData.appendRows(new JSCSArray("append", "Trans. Variable"));
        }
        if (dataToPlot.rows() == 0 && !getPlotTransFktVar().isSelected()) {
            String msg = "Please select something to plot.";
            StdMessages.infoNothingSelected(msg);
            return;
        }
        if (dataToPlot.rows() > 0)
            if (type == 1) {

                PCall job = new PlotTSCall(dataToPlot, range.lowerBound(),
                        namesOfData, getMultGraph().isSelected(), getStdRes()
                                .isSelected());
                job.setOutHolder(getResultField());
                job.execute();

            } else {
                StringBuffer buffer = new StringBuffer();
                double dat[][] = dataToPlot.doubleArray();
                String datum[] = range.lowerBound().timeAxisStringArray(
                        dat.length);
                buffer.append("*** " + CompSettings.getDateString() + " ***\n");
                buffer.append(FArg.sprintf("%-21s", new FArg("Time")));
                for (int i = 0; i < namesOfData.rows(); i++)
                    buffer.append(FArg.sprintf("%-20s", new FArg(namesOfData
                            .stringAt(i, 0))));
                buffer.append("\n");
                for (int i = 0; i < dat.length; i++) {
                    buffer.append(FArg.sprintf("%-20s", new FArg(datum[i])));
                    for (int j = 0; j < dat[0].length; j++) {
                        buffer.append(FArg.sprintf("%- 20.4f", new FArg(
                                dat[i][j])));
                    }
                    buffer.append("\n");
                }
                getResultField().append(buffer.toString() + "\n");
            }
        if (getPlotTransFktVar().isSelected()) {
            JSCNArray transFkt = new JSCNArray("transfkt", global().get(
                    STR_Constants.STR_RTZ).getJSCNArray().getCol(1));

            JSCNArray transVar = new JSCNArray("transvar", trans);
            String transName = global().get(STR_Constants.STR_TRANSNAME)
                    .getJSCString().string();
            PCall job = new CrossPlotCall(transVar, transFkt, transName, "G",
                    null, false, false, 0);
            job.execute();
        }
    }

    /**
     * Comment
     */
    private void showVal_ActionEvents() {
        plotShowPartsOfModel(2);
    }

    /**
     * This method initializes jLabel
     * 
     * @return javax.swing.JLabel
     */
    private javax.swing.JLabel getJLabel() {
        if (jLabel == null) {
            jLabel = new javax.swing.JLabel();
            jLabel.setForeground(Color.GREEN);
            jLabel.setText("");
            jLabel.setPreferredSize(new java.awt.Dimension(38, 20));
        }
        return jLabel;
    }

    /**
     * This method initializes jCheckBox
     * 
     * @return javax.swing.JCheckBox
     */
    private javax.swing.JCheckBox getPlotTransFktVar() {
        if (jCheckBox == null) {
            jCheckBox = new javax.swing.JCheckBox();
            jCheckBox.setSelected(true);
            jCheckBox.setText("G versus s_t (plot only)");
        }
        return jCheckBox;
    }
}