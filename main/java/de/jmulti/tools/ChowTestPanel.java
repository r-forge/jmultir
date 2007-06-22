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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import org.apache.log4j.Logger;

import com.jstatcom.component.NumSelector;
import com.jstatcom.component.ResultField;
import com.jstatcom.engine.PCall;
import com.jstatcom.model.JSCTypeDef;
import com.jstatcom.model.ModelPanel;
import com.jstatcom.ts.TSDate;
import com.jstatcom.ts.TSDateRange;
import com.jstatcom.ts.TSDateRangeSelector;
import com.jstatcom.ts.TSDateSelector;

import de.jmulti.proc.ChowTestCall;
import de.jmulti.var.VARConstants;
import de.jmulti.vecm.VECMConstants;

/**
 * Panel for boostrapped CHOW test for VAR models.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public final class ChowTestPanel extends ModelPanel {
    private static final Logger log = Logger.getLogger(ChowTestPanel.class);

    private ModelTypes modelType = null;

    private JSCTypeDef dRangeName = null;

    private JSCTypeDef pylags = null;

    private JSCTypeDef pxlags = null;

    private JSCTypeDef yDat = null;

    private JSCTypeDef xDat = null;

    private JSCTypeDef dDat = null;

    private JSCTypeDef u_hatName = null;

    private JSCTypeDef cointRankName = null;

    // Needed to compute indices according to original range.
    private TSDateRange origDateRange = null;

    private JPanel ivjJPanel1 = null;

    private ResultField ivjResultField = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private JButton ivjExecute = null;

    private TSDateSelector ivjBreakDate = null;

    private JLabel ivjJLabel1 = null;

    private JLabel ivjJLabel2 = null;

    private NumSelector ivjNob = null;

    private JCheckBox ivjSearchAll = null;

    private JComboBox ivjGridComboBox = null;

    private JCheckBox ivjGraphCheck = null;

    private JLabel ivjJLabel = null;

    private TSDateRangeSelector ivjTestRange = null;

    class IvjEventHandler implements java.awt.event.ActionListener {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == ChowTestPanel.this.getExecute())
                connEtoC1();
            if (e.getSource() == ChowTestPanel.this.getSearchAll())
                connEtoC3();
            if (e.getSource() == ChowTestPanel.this.getSearchAll())
                connPtoP1SetTarget();
            if (e.getSource() == ChowTestPanel.this.getSearchAll())
                connPtoP2SetTarget();
            if (e.getSource() == ChowTestPanel.this.getSearchAll())
                connPtoP4SetTarget();
            if (e.getSource() == ChowTestPanel.this.getSearchAll())
                connPtoP3SetTarget();
        };
    };

    /**
     * ChowTestPanel constructor comment.
     */
    public ChowTestPanel() {
        super();
        initialize();
    }

    /**
     * connEtoC1: (Execute.action. --> ChowTestPanel.execute_ActionEvents()V)
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
     * connEtoC3: (SearchAll.action. -->
     * ChowTestPanel.searchAll_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC3() {
        try {
            // user code begin {1}
            // user code end
            this.searchAll_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connPtoP1SetTarget: (SearchAll.selected <--> GridComboBox.enabled)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connPtoP1SetTarget() {
        /* Set the target from the source */
        try {
            getGridComboBox().setEnabled(getSearchAll().isSelected());
            // user code begin {1}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connPtoP2SetTarget: (SearchAll.selected <--> GraphCheck.enabled)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connPtoP2SetTarget() {
        /* Set the target from the source */
        try {
            getGraphCheck().setEnabled(getSearchAll().isSelected());
            // user code begin {1}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connPtoP3SetTarget: (SearchAll.selected <--> StartRange.enabled)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connPtoP3SetTarget() {
        /* Set the target from the source */
        try {
            getTestRange().setEnabled(getSearchAll().isSelected());
            // user code begin {1}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connPtoP4SetTarget: (SearchAll.selected <--> JLabel.enabled)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connPtoP4SetTarget() {
        /* Set the target from the source */
        try {
            getJLabel().setEnabled(getSearchAll().isSelected());
            // user code begin {1}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * Prepares and performs chow test for VAR.
     */
    private void execute_ActionEvents() {
        // Index is computed from original range.
        int breakPoint = origDateRange.indexForDate(getBreakDate().getTSDate());
        // Get indices according to orginal index.
        int[] indices = origDateRange.indicesForRange(getTestRange()
                .getTSDateRange());
        // Single break date.
        if (!getSearchAll().isSelected()) {
            indices[0] = breakPoint;
            indices[1] = breakPoint;
        }

        int gridIndex = 1;

        if (getGridComboBox().isEnabled()) {
            String grid = getGridComboBox().getSelectedItem().toString();
            if (grid.equals("every"))
                gridIndex = 1;
            if (grid.equals("every 2nd"))
                gridIndex = 2;
            if (grid.equals("every 3rd"))
                gridIndex = 3;
            if (grid.equals("every 4th"))
                gridIndex = 4;
            if (grid.equals("every 5th"))
                gridIndex = 5;
            if (grid.equals("every 6th"))
                gridIndex = 6;
            if (grid.equals("every 10th"))
                gridIndex = 10;
            if (grid.equals("every 12th"))
                gridIndex = 12;
            if (grid.equals("every 20th"))
                gridIndex = 20;
            if (grid.equals("every 50th"))
                gridIndex = 50;
            if (grid.equals("every 100th"))
                gridIndex = 100;
            if (grid.equals("every 200th"))
                gridIndex = 200;
        }

        PCall job = new ChowTestCall(global(), local(), origDateRange,
                modelType, getNob().getIntNumber(), indices[0], indices[1],
                gridIndex, getGraphCheck().isSelected());

        job.setOutHolder(getResultField());
        job.execute();
    }

    /**
     * Return the BreakDate property value.
     * 
     * @return com.jstatcom.ts.TSDateSelector
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private com.jstatcom.ts.TSDateSelector getBreakDate() {
        if (ivjBreakDate == null) {
            try {
                ivjBreakDate = new com.jstatcom.ts.TSDateSelector();
                ivjBreakDate.setName("BreakDate");
                ivjBreakDate.setPreferredSize(new java.awt.Dimension(100, 21));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjBreakDate;
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
                ivjExecute.setText("Execute Test");
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

    /**
     * Return the GraphCheck property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getGraphCheck() {
        if (ivjGraphCheck == null) {
            try {
                ivjGraphCheck = new javax.swing.JCheckBox();
                ivjGraphCheck.setName("GraphCheck");
                ivjGraphCheck.setSelected(true);
                ivjGraphCheck.setPreferredSize(new java.awt.Dimension(97, 21));
                ivjGraphCheck.setText("Graph of bootstrapped p-values");
                ivjGraphCheck.setEnabled(false);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjGraphCheck;
    }

    /**
     * Return the GridComboBox property value.
     * 
     * @return javax.swing.JComboBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JComboBox getGridComboBox() {
        if (ivjGridComboBox == null) {
            try {
                ivjGridComboBox = new javax.swing.JComboBox();
                ivjGridComboBox.setName("GridComboBox");
                ivjGridComboBox
                        .setPreferredSize(new java.awt.Dimension(150, 21));
                ivjGridComboBox.setEnabled(false);
                ivjGridComboBox.setMinimumSize(new java.awt.Dimension(150, 23));
                ivjGridComboBox.setMaximumSize(new java.awt.Dimension(200, 23));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjGridComboBox;
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
                ivjJLabel.setText("Test range");
                ivjJLabel.setEnabled(false);
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
                ivjJLabel1.setPreferredSize(new java.awt.Dimension(100, 21));
                ivjJLabel1.setText("Break date");
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
                ivjJLabel2.setPreferredSize(new java.awt.Dimension(156, 21));
                ivjJLabel2.setText("Number of bootstrap replications");
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
                ivjJPanel1.setPreferredSize(new java.awt.Dimension(0, 150));
                ivjJPanel1.setBorder(new BevelBorder(BevelBorder.LOWERED));
                ivjJPanel1.setLayout(new java.awt.GridBagLayout());

                java.awt.GridBagConstraints constraintsExecute = new java.awt.GridBagConstraints();
                constraintsExecute.gridx = 3;
                constraintsExecute.gridy = 3;
                constraintsExecute.gridwidth = 3;
                constraintsExecute.weighty = 1.0;
                constraintsExecute.insets = new java.awt.Insets(5, 10, 0, 0);
                java.awt.GridBagConstraints constraintsBreakDate = new java.awt.GridBagConstraints();
                constraintsBreakDate.gridx = 0;
                constraintsBreakDate.gridy = 0;
                constraintsBreakDate.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsBreakDate.insets = new java.awt.Insets(10, 10, 0, 0);
                getJPanel1().add(getBreakDate(), constraintsBreakDate);

                java.awt.GridBagConstraints constraintsJLabel1 = new java.awt.GridBagConstraints();
                constraintsJLabel1.gridx = 1;
                constraintsJLabel1.gridy = 0;
                constraintsJLabel1.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel1.insets = new java.awt.Insets(10, 10, 0, 0);
                getJPanel1().add(getJLabel1(), constraintsJLabel1);

                java.awt.GridBagConstraints constraintsNob = new java.awt.GridBagConstraints();
                constraintsNob.gridx = 0;
                constraintsNob.gridy = 1;
                constraintsNob.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsNob.insets = new java.awt.Insets(5, 10, 0, 0);
                getJPanel1().add(getNob(), constraintsNob);

                java.awt.GridBagConstraints constraintsJLabel2 = new java.awt.GridBagConstraints();
                constraintsJLabel2.gridx = 1;
                constraintsJLabel2.gridy = 1;
                constraintsJLabel2.gridwidth = 2;
                constraintsJLabel2.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel2.insets = new java.awt.Insets(5, 10, 0, 0);
                getJPanel1().add(getJLabel2(), constraintsJLabel2);

                java.awt.GridBagConstraints constraintsSearchAll = new java.awt.GridBagConstraints();
                constraintsSearchAll.gridx = 2;
                constraintsSearchAll.gridy = 0;
                constraintsSearchAll.insets = new java.awt.Insets(10, 10, 0, 0);
                getJPanel1().add(getSearchAll(), constraintsSearchAll);

                java.awt.GridBagConstraints constraintsGridComboBox = new java.awt.GridBagConstraints();
                constraintsGridComboBox.gridx = 3;
                constraintsGridComboBox.gridy = 0;
                constraintsGridComboBox.gridwidth = 2;
                constraintsGridComboBox.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsGridComboBox.anchor = java.awt.GridBagConstraints.WEST;
                constraintsGridComboBox.weightx = 1.0;
                constraintsGridComboBox.insets = new java.awt.Insets(10, 10, 0,
                        10);
                getJPanel1().add(getGridComboBox(), constraintsGridComboBox);

                java.awt.GridBagConstraints constraintsGraphCheck = new java.awt.GridBagConstraints();
                constraintsGraphCheck.gridx = 3;
                constraintsGraphCheck.gridy = 1;
                constraintsGraphCheck.gridwidth = 2;
                constraintsGraphCheck.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsGraphCheck.anchor = java.awt.GridBagConstraints.WEST;
                constraintsGraphCheck.insets = new java.awt.Insets(5, 10, 0, 0);
                getJPanel1().add(getGraphCheck(), constraintsGraphCheck);

                java.awt.GridBagConstraints constraintsTestRange = new java.awt.GridBagConstraints();
                constraintsTestRange.gridx = 3;
                constraintsTestRange.gridy = 2;
                constraintsTestRange.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsTestRange.insets = new java.awt.Insets(10, 10, 0, 0);
                getJPanel1().add(getTestRange(), constraintsTestRange);

                java.awt.GridBagConstraints constraintsJLabel = new java.awt.GridBagConstraints();
                constraintsExecute.anchor = java.awt.GridBagConstraints.WEST;
                constraintsJLabel.gridx = 4;
                constraintsJLabel.gridy = 2;
                constraintsJLabel.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsJLabel.insets = new java.awt.Insets(13, 10, 0, 0);
                ivjJPanel1.add(getExecute(), constraintsExecute);
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
     * Gets the current model type to be used for getting the variables.
     * 
     * @return the current model type
     */
    public ModelTypes getModelType() {
        return modelType;
    }

    private NumSelector getNob() {
        if (ivjNob == null) {
            try {
                ivjNob = new NumSelector();
                ivjNob.setName("Nob");
                ivjNob.setPreferredSize(new java.awt.Dimension(100, 21));
                ivjNob.setNumber(100.0);
                ivjNob.setRangeExpr("[10,Infinity)");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjNob;
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

    /**
     * Return the SearchAll property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getSearchAll() {
        if (ivjSearchAll == null) {
            try {
                ivjSearchAll = new javax.swing.JCheckBox();
                ivjSearchAll.setName("SearchAll");
                ivjSearchAll.setPreferredSize(new java.awt.Dimension(156, 21));
                ivjSearchAll.setText("Search over datapoints");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSearchAll;
    }

    /**
     * Return the StartRange property value.
     * 
     * @return com.jstatcom.ts.TSDateRangeSelector
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private com.jstatcom.ts.TSDateRangeSelector getTestRange() {
        if (ivjTestRange == null) {
            try {
                ivjTestRange = new com.jstatcom.ts.TSDateRangeSelector();
                ivjTestRange.setName("TestRange");
                ivjTestRange.setPreferredSize(new java.awt.Dimension(150, 21));
                ivjTestRange.setEnabled(false);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjTestRange;
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
        getSearchAll().addActionListener(ivjEventHandler);
        connPtoP1SetTarget();
        connPtoP2SetTarget();
        connPtoP4SetTarget();
        connPtoP3SetTarget();
    }

    /**
     * Initialize the class.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initialize() {
        try {
            // user code begin {1}
            // user code end
            setName("ChowTestPanel");
            setLayout(new java.awt.BorderLayout());
            setSize(665, 310);
            add(getResultField(), "Center");
            add(getJPanel1(), "North");
            initConnections();
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}

        getGridComboBox().addItem("every");
        getGridComboBox().addItem("every 2nd");
        getGridComboBox().addItem("every 3rd");
        getGridComboBox().addItem("every 4th");
        getGridComboBox().addItem("every 5th");
        getGridComboBox().addItem("every 6th");
        getGridComboBox().addItem("every 10th");
        getGridComboBox().addItem("every 12th");
        getGridComboBox().addItem("every 20th");
        getGridComboBox().addItem("every 50th");
        getGridComboBox().addItem("every 100th");
        getGridComboBox().addItem("every 200th");

        // user code end
    }

    /**
     * Comment
     */
    private void searchAll_ActionEvents() {
        getBreakDate().setEnabled(!getSearchAll().isSelected());
        getJLabel1().setEnabled(!getSearchAll().isSelected());

        return;
    }

    /**
     * Sets the model type to be used for this panel. This affects the test
     * routine as well.
     * 
     * @param newModelType
     *            the model type
     * @throws IllegalArgumentException
     *             <code>if (newModelType == null)</code>
     */
    public void setModelType(ModelTypes newModelType) {

        if (newModelType == null)
            throw new IllegalArgumentException("Modeltype was null.");

        modelType = newModelType;

        if (modelType == ModelTypes.VAR) {
            yDat = VARConstants.y_Def;
            xDat = VARConstants.x_Def;
            dDat = VARConstants.d_all_Def;
            pxlags = VARConstants.px_Def;
            pylags = VARConstants.py_Def;
            dRangeName = VARConstants.T1_Def;
            u_hatName = VARConstants.u_hat_Def;
            return;
        }
        if (modelType == ModelTypes.VECM) {
            yDat = VECMConstants.y_Def;
            xDat = VECMConstants.x_Def;
            dDat = VECMConstants.d_Def;
            pxlags = VECMConstants.px_Def;
            pylags = VECMConstants.py_Def;
            dRangeName = VECMConstants.T1_Def;
            u_hatName = VECMConstants.u_hat_Def;
            cointRankName = VECMConstants.cointRank_Def;
            return;
        }
        throw new IllegalArgumentException("Modeltype " + modelType
                + " is unknown.");

    }

    public void shown(boolean isShown) {
        if (isShown) {
            // Set the date text field and the possible range of dates
            int cointRank = 0;
            if (modelType == ModelTypes.VECM)
                cointRank = global().get(cointRankName).getJSCInt().intVal();

            TSDateRange range = global().get(dRangeName).getJSCDRange()
                    .getTSDateRange();
            int nr = global().get(u_hatName).getJSCNArray().rows();
            // First possible value : cols(y)*py + cols(x)*(px+1) + cols(d) +
            // cols(y) + 1
            // degfree are one less than 1st possible.
            int py = global().get(pylags).getJSCInt().intVal();
            int px = global().get(pxlags).getJSCInt().intVal();
            int K = global().get(yDat).getJSCNArray().cols();
            int degfree = py * K + (px + 1)
                    * global().get(xDat).getJSCNArray().cols()
                    + global().get(dDat).getJSCNArray().cols() + K + cointRank;
            degfree = (py == 0) ? ++degfree : degfree;
            // If the index is retrieved, it must be computed relative to the
            // original
            // start date!
            TSDate start = range.lowerBound();
            origDateRange = new TSDateRange(start, nr);
            TSDate firstPossibleDate = start.addPeriods(degfree);
            getBreakDate().setEnclosingRange(firstPossibleDate,
                    nr - degfree - 1);
            getTestRange().setEnclosingRange(firstPossibleDate,
                    nr - degfree - 1);
            getTestRange().setTSDateRange(getTestRange().getEnclosingRange());
        }

    }
}