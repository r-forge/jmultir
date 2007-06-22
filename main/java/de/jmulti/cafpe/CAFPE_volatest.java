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

package de.jmulti.cafpe;

import java.awt.BorderLayout;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import com.jstatcom.component.NumSelector;
import com.jstatcom.component.ResultField;
import com.jstatcom.component.StdMessages;
import com.jstatcom.engine.PCall;
import com.jstatcom.engine.PCallAdapter;
import com.jstatcom.model.JSCConstants;
import com.jstatcom.model.JSCInt;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.JSCSArray;
import com.jstatcom.model.ModelPanel;
import com.jstatcom.model.NumberRange;
import com.jstatcom.model.NumberRangeTypes;
import com.jstatcom.model.SymbolEvent;
import com.jstatcom.model.SymbolListener;
import com.jstatcom.parser.CalcEngine;
import com.jstatcom.parser.ParseException;
import com.jstatcom.table.JSCDataTableScrollPane;
import com.jstatcom.table.JSCNArrayTable;
import com.jstatcom.ts.TSDateRange;

import de.jmulti.proc.CAFPEEstCall;

/**
 * Panel for estimating the volatility.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */

final class CAFPE_volatest extends ModelPanel {
    private static final Logger log = Logger.getLogger(CAFPE_volatest.class);

    private int[][] lags = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private JButton ivjExecute = null;

    private JCheckBox ivjPrintLog = null;

    private JPanel ivjJPanel1 = null;

    private JTabbedPane ivjJTabbedPane1 = null;

    private ResultField ivjResultField = null;

    private NumSelector ivjGridNum = null;

    private JLabel ivjJLabel1 = null;

    private NumSelector ivjPerGr = null;

    private JLabel ivjJLabel2 = null;

    private JComboBox ivjGridVector = null;

    private JComboBox ivjLagGrid1 = null;

    private JComboBox ivjLagGrid2 = null;

    private JLabel ivjJLabel3 = null;

    private JLabel ivjJLabel4 = null;

    private JLabel ivjJLabel5 = null;

    private JSCDataTableScrollPane ivjDataTableScrollPane1 = null;

    private JLabel ivjJLabel = null;

    private HashMap<String, Double> xconstMap = new HashMap<String, Double>();

    private JCheckBox ivjEditOptLags = null;

    private JSCDataTableScrollPane ivjDataTableScrollPane2 = null;

    private JSCNArrayTable ivjScrollPaneTable1 = null;

    private JSCNArrayTable ivjScrollPaneTable2 = null;

    private JCheckBox ivjShowGraph = null;

    private JCheckBox ivjWnCheck = null;

    private JLabel ivjJLabel6 = null;

    private NumSelector ivjBwFactorTextField = null;

    private javax.swing.JComboBox jComboBox = null;

    private javax.swing.JLabel jLabel = null;

    class IvjEventHandler implements java.awt.event.ActionListener,
            java.awt.event.ComponentListener {

        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == CAFPE_volatest.this.getExecute())
                connEtoC1();
            if (e.getSource() == CAFPE_volatest.this.getGridVector())
                connEtoC3();
            if (e.getSource() == CAFPE_volatest.this.getLagGrid1())
                connEtoC4();
            if (e.getSource() == CAFPE_volatest.this.getLagGrid2())
                connEtoC5();
            if (e.getSource() == CAFPE_volatest.this.getEditOptLags())
                connEtoC6();
        };

        public void componentHidden(java.awt.event.ComponentEvent e) {
        };

        public void componentMoved(java.awt.event.ComponentEvent e) {
        };

        public void componentResized(java.awt.event.ComponentEvent e) {
        };

        public void componentShown(java.awt.event.ComponentEvent e) {
            if (e.getSource() == CAFPE_volatest.this)
                connEtoC2(e);
        };
    };

    /**
     * EstimateCAFPE constructor comment.
     */
    public CAFPE_volatest() {
        super();
        initialize();
    }

    /**
     * connEtoC1: (Execute.action. --> CAFPE_est.execute_ActionEvents()V)
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
     * connEtoC2:
     * (EstimateCAFPE.component.componentShown(java.awt.event.ComponentEvent)
     * --> CAFPE_est.estimateCAFPE_ComponentShown()V)
     * 
     * @param arg1
     *            java.awt.event.ComponentEvent
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC2(java.awt.event.ComponentEvent arg1) {
        try {
            // user code begin {1}
            // user code end
            this.this_componentShown(arg1);
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC3: (GridVector.action. --> CAFPE_est.gridVector_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC3() {
        try {
            // user code begin {1}
            // user code end
            this.gridVector_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC4: (LagGrid1.action. --> CAFPE_est.lagGrid1_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC4() {
        try {
            // user code begin {1}
            // user code end
            this.lagGrid1_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC5: (LagGrid2.action. --> CAFPE_est.lagGrid2_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC5() {
        try {
            // user code begin {1}
            // user code end
            this.lagGrid2_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC6: (EditOptLags.action. -->
     * CAFPE_est.editOptLags_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC6() {
        try {
            // user code begin {1}
            // user code end
            this.editOptLags_ActionEvents();
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
    private void editOptLags_ActionEvents() {
        getScrollPaneTable2().setVisible(getEditOptLags().isSelected());
        return;
    }

    /**
     * Comment
     */
    private void execute_ActionEvents() {
        boolean isWn = getWnCheck().isSelected();
        if (global().get(CAFPE_constants.RESID_EST).isEmpty() && !isWn) {
            StdMessages
                    .errorSpecification("There are no estimated residuals available.\nYou could assume that f(y)=0 and resids=y\nby selecting the available checkbox.");
            return;
        }

        if (getGridVector().getItemCount() == 0) {
            StdMessages.errorSpecification("The lag selection is empty.");
            return;
        }
        JSCNArray res = (isWn) ? global().get(CAFPE_constants.Y).getJSCNArray()
                : global().get(CAFPE_constants.RESID_EST).getJSCNArray();

        final File outFile = new File(JSCConstants.getSystemTemp()
                + File.separator + CAFPE_constants.OUT_FILE_VOLATEST);
        String lags_grid = getGridVector().getSelectedItem().toString();
        String grid_var = getLagGrid1().getSelectedItem().toString();
        if (getLagGrid2().isEnabled())
            grid_var = grid_var + "|"
                    + getLagGrid2().getSelectedItem().toString();

        CalcEngine parser = new CalcEngine();
        double[] lagsGridArray;
        double[] gridVarArray;

        try { // use times series parser for expressions like "1|2|4"
            parser.parseString("d = " + lags_grid);
            lagsGridArray = parser.getVariable("d");
            parser.parseString("d = " + grid_var);
            gridVarArray = parser.getVariable("d");
        } catch (ParseException ex) {
            throw new RuntimeException(ex.toString());
        }

        JSCNArray lagsGrid = new JSCNArray("CAFPE_VOLEST_LAGSGRID",
                lagsGridArray);
        JSCNArray gridVar = new JSCNArray("CAFPE_VOLEST_GRIDVAR", gridVarArray);

        int index = getRotateCombo().getSelectedIndex();
        int rotateDegree = 90 * index;

        TSDateRange range = global().get(CAFPE_constants.DRANGE).getJSCDRange()
                .getTSDateRange();

        PCall job = new CAFPEEstCall(global().get(CAFPE_constants.Y)
                .getJSCNArray(), res, global().get(CAFPE_constants.Y_NAME)
                .getJSCString().string(), range.lowerBound(), lagsGrid,
                gridVar, outFile, global().get(CAFPE_constants.MOD_METH_VOLAT)
                        .getJSCString().string(), global().get(
                        CAFPE_constants.SEARCH_METH_VOLAT).getJSCString()
                        .string(), global().get(CAFPE_constants.SELCRIT_VOLAT)
                        .getJSCString().string(), global().get(
                        CAFPE_constants.STARTSTRAT_VOLAT).getJSCString()
                        .string(), global().get(
                        CAFPE_constants.XCONST_FAC_VOLAT).getJSCNArray(),
                new JSCNArray("XCONST_ALL_VOLAT",
                        new double[lagsGrid.rows()][1]), getPrintLog()
                        .isSelected(),
                global().get(CAFPE_constants.IS_STAND_VOLAT).getJSCInt()
                        .isNonZero(), getPerGr().getNumber(), getGridNum()
                        .getIntNumber(), false, 0.95, false, getBwFactor()
                        .getNumber(), getShowGraph().isSelected(),
                rotateDegree, true, isWn);
        job.setSymbolTable(global());
        job.setOutHolder(getResultField());
        job.addPCallListener(new PCallAdapter() {

            public void success() {
                getJTabbedPane1().setSelectedIndex(1);
            }

            public void finished(PCall pCall) {
                outFile.delete();
            }
        });
        job.execute();

    }

    private NumSelector getBwFactor() {
        if (ivjBwFactorTextField == null) {
            try {
                ivjBwFactorTextField = new NumSelector();
                ivjBwFactorTextField.setName("BwFactorTextField");
                ivjBwFactorTextField.setNumber(1.0);
                ivjBwFactorTextField.setPrecision(2);
                ivjBwFactorTextField.setRangeExpr("(0,+infinity)");
                ivjBwFactorTextField.setPreferredSize(new java.awt.Dimension(
                        55, 20));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjBwFactorTextField;
    }

    /**
     * This function sets the values where the estimated function is evaluated.
     */
    private int getCurrentNonzeroLags() {
        int sum = 0;
        for (int i = 0; i < lags[0].length; i++)
            if (lags[getGridVector().getSelectedIndex()][i] > 0)
                sum++;
        return sum;
    }

    /**
     * Return the EditOptLags property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getEditOptLags() {
        if (ivjEditOptLags == null) {
            try {
                ivjEditOptLags = new javax.swing.JCheckBox();
                ivjEditOptLags.setName("EditOptLags");
                ivjEditOptLags
                        .setPreferredSize(new java.awt.Dimension(250, 22));
                ivjEditOptLags.setText("Edit sets obtained from lag selection");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjEditOptLags;
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
                ivjExecute.setPreferredSize(new java.awt.Dimension(150, 25));
                ivjExecute.setText("Execute Estimation");
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

    private JSCDataTableScrollPane getDataTableScrollPane1() {
        if (ivjDataTableScrollPane1 == null) {
            try {
                ivjDataTableScrollPane1 = new JSCDataTableScrollPane();
                ivjDataTableScrollPane1.setName("DataTableScrollPane1");
                ivjDataTableScrollPane1.setMaximumVisibleColumns(1);
                String ivjLocal55columnHeaderStringData[] = { "values" };
                ivjDataTableScrollPane1
                        .setColumnHeaderStringData(ivjLocal55columnHeaderStringData);
                ivjDataTableScrollPane1.setRowHeaderShowing(true);
                ivjDataTableScrollPane1
                        .setPreferredSize(new java.awt.Dimension(200, 100));
                ivjDataTableScrollPane1.setColumnHeaderShowing(true);
                ivjDataTableScrollPane1.setMinimumVisibleRows(3);
                ivjDataTableScrollPane1.setMaximumVisibleRows(10);
                getDataTableScrollPane1()
                        .setViewportView(getScrollPaneTable1());
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

    private JSCDataTableScrollPane getDataTableScrollPane2() {
        if (ivjDataTableScrollPane2 == null) {
            try {
                ivjDataTableScrollPane2 = new JSCDataTableScrollPane();
                ivjDataTableScrollPane2.setName("DataTableScrollPane2");
                ivjDataTableScrollPane2.setMinimumVisibleColumns(4);
                ivjDataTableScrollPane2.setMaximumVisibleColumns(4);
                ivjDataTableScrollPane2.setMinimumVisibleRows(3);
                ivjDataTableScrollPane2.setMaximumVisibleRows(3);
                getDataTableScrollPane2()
                        .setViewportView(getScrollPaneTable2());
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjDataTableScrollPane2;
    }

    private NumSelector getGridNum() {
        if (ivjGridNum == null) {
            try {
                ivjGridNum = new NumSelector();
                ivjGridNum.setName("GridNum");
                ivjGridNum.setPreferredSize(new java.awt.Dimension(55, 20));
                ivjGridNum.setRangeExpr("[5,+infinity)");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjGridNum;
    }

    /**
     * Return the GridVector property value.
     * 
     * @return javax.swing.JComboBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JComboBox getGridVector() {
        if (ivjGridVector == null) {
            try {
                ivjGridVector = new javax.swing.JComboBox();
                ivjGridVector.setName("GridVector");
                ivjGridVector.setPreferredSize(new java.awt.Dimension(250, 23));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjGridVector;
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
                ivjJLabel.setPreferredSize(new java.awt.Dimension(100, 20));
                ivjJLabel.setText("Condition plot at");
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
                ivjJLabel1.setPreferredSize(new java.awt.Dimension(55, 20));
                ivjJLabel1.setText("Grid points in each direction");
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
                ivjJLabel2.setPreferredSize(new java.awt.Dimension(55, 20));
                ivjJLabel2
                        .setText("Quantile of grid points to be suppressed in plot");
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
     * Return the JLabel3 property value.
     * 
     * @return javax.swing.JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JLabel getJLabel3() {
        if (ivjJLabel3 == null) {
            try {
                ivjJLabel3 = new javax.swing.JLabel();
                ivjJLabel3.setName("JLabel3");
                ivjJLabel3.setPreferredSize(new java.awt.Dimension(55, 20));
                ivjJLabel3.setText("Select set of lags for estimation");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJLabel3;
    }

    /**
     * Return the JLabel4 property value.
     * 
     * @return javax.swing.JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JLabel getJLabel4() {
        if (ivjJLabel4 == null) {
            try {
                ivjJLabel4 = new javax.swing.JLabel();
                ivjJLabel4.setName("JLabel4");
                ivjJLabel4.setPreferredSize(new java.awt.Dimension(100, 20));
                ivjJLabel4.setText("1st lag to plot ");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJLabel4;
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
                ivjJLabel5.setPreferredSize(new java.awt.Dimension(150, 20));
                ivjJLabel5.setText("2nd lag to plot (for 3D)");
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
     * Return the JLabel6 property value.
     * 
     * @return javax.swing.JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JLabel getJLabel6() {
        if (ivjJLabel6 == null) {
            try {
                ivjJLabel6 = new javax.swing.JLabel();
                ivjJLabel6.setName("JLabel6");
                ivjJLabel6.setPreferredSize(new java.awt.Dimension(182, 20));
                ivjJLabel6.setText("Factor to multiply plug-in bandwidth with");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJLabel6;
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
                ivjJPanel1.setLayout(new java.awt.GridBagLayout());

                java.awt.GridBagConstraints constraintsExecute = new java.awt.GridBagConstraints();
                constraintsExecute.gridx = 5;
                constraintsExecute.gridy = 10;
                constraintsExecute.gridwidth = 2;
                constraintsExecute.anchor = java.awt.GridBagConstraints.SOUTHEAST;
                constraintsExecute.insets = new java.awt.Insets(5, 20, 10, 10);
                java.awt.GridBagConstraints constraintsPrintLog = new java.awt.GridBagConstraints();
                constraintsPrintLog.gridx = 0;
                constraintsPrintLog.gridy = 8;
                constraintsPrintLog.gridwidth = 3;
                constraintsPrintLog.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsPrintLog.insets = new java.awt.Insets(5, 10, 0, 0);
                java.awt.GridBagConstraints constraintsGridNum = new java.awt.GridBagConstraints();
                constraintsGridNum.gridx = 0;
                constraintsGridNum.gridy = 6;
                constraintsGridNum.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsGridNum.insets = new java.awt.Insets(0, 10, 0, 0);
                java.awt.GridBagConstraints constraintsJLabel1 = new java.awt.GridBagConstraints();
                constraintsJLabel1.gridx = 1;
                constraintsJLabel1.gridy = 6;
                constraintsJLabel1.gridwidth = 4;
                constraintsJLabel1.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel1.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsJLabel1.insets = new java.awt.Insets(0, 10, 0, 0);
                java.awt.GridBagConstraints constraintsPerGr = new java.awt.GridBagConstraints();
                constraintsPerGr.gridx = 0;
                constraintsPerGr.gridy = 5;
                constraintsPerGr.anchor = java.awt.GridBagConstraints.WEST;
                constraintsPerGr.insets = new java.awt.Insets(5, 10, 0, 0);
                java.awt.GridBagConstraints constraintsJLabel2 = new java.awt.GridBagConstraints();
                constraintsJLabel2.gridx = 1;
                constraintsJLabel2.gridy = 5;
                constraintsJLabel2.gridwidth = 4;
                constraintsJLabel2.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel2.insets = new java.awt.Insets(5, 10, 0, 0);
                java.awt.GridBagConstraints constraintsGridVector = new java.awt.GridBagConstraints();
                constraintsGridVector.gridx = 0;
                constraintsGridVector.gridy = 2;
                constraintsGridVector.gridwidth = 4;
                constraintsGridVector.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsGridVector.anchor = java.awt.GridBagConstraints.WEST;
                constraintsGridVector.insets = new java.awt.Insets(0, 10, 0, 0);
                java.awt.GridBagConstraints constraintsLagGrid1 = new java.awt.GridBagConstraints();
                constraintsLagGrid1.gridx = 4;
                constraintsLagGrid1.gridy = 2;
                constraintsLagGrid1.gridwidth = 2;
                constraintsLagGrid1.anchor = java.awt.GridBagConstraints.WEST;
                constraintsLagGrid1.insets = new java.awt.Insets(0, 20, 0, 0);
                java.awt.GridBagConstraints constraintsLagGrid2 = new java.awt.GridBagConstraints();
                constraintsLagGrid2.gridx = 6;
                constraintsLagGrid2.gridy = 2;
                constraintsLagGrid2.anchor = java.awt.GridBagConstraints.WEST;
                constraintsLagGrid2.insets = new java.awt.Insets(0, 20, 0, 0);
                java.awt.GridBagConstraints constraintsJLabel3 = new java.awt.GridBagConstraints();
                constraintsJLabel3.gridx = 0;
                constraintsJLabel3.gridy = 1;
                constraintsJLabel3.gridwidth = 4;
                constraintsJLabel3.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel3.anchor = java.awt.GridBagConstraints.WEST;
                constraintsJLabel3.insets = new java.awt.Insets(5, 10, 0, 0);
                java.awt.GridBagConstraints constraintsJLabel4 = new java.awt.GridBagConstraints();
                constraintsJLabel4.gridx = 4;
                constraintsJLabel4.gridy = 1;
                constraintsJLabel4.gridwidth = 2;
                constraintsJLabel4.anchor = java.awt.GridBagConstraints.WEST;
                constraintsJLabel4.insets = new java.awt.Insets(0, 20, 0, 0);
                java.awt.GridBagConstraints constraintsJLabel5 = new java.awt.GridBagConstraints();
                constraintsJLabel5.gridx = 6;
                constraintsJLabel5.gridy = 1;
                constraintsJLabel5.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel5.anchor = java.awt.GridBagConstraints.WEST;
                constraintsJLabel5.insets = new java.awt.Insets(0, 20, 0, 0);
                java.awt.GridBagConstraints constraintsGaussDataTableScrollPane1 = new java.awt.GridBagConstraints();
                constraintsGaussDataTableScrollPane1.gridx = 0;
                constraintsGaussDataTableScrollPane1.gridy = 4;
                constraintsGaussDataTableScrollPane1.gridwidth = 3;
                constraintsGaussDataTableScrollPane1.fill = java.awt.GridBagConstraints.BOTH;
                constraintsGaussDataTableScrollPane1.weightx = 1.0;
                constraintsGaussDataTableScrollPane1.weighty = 1.0;
                constraintsGaussDataTableScrollPane1.insets = new java.awt.Insets(
                        0, 10, 0, 0);
                java.awt.GridBagConstraints constraintsJLabel = new java.awt.GridBagConstraints();
                constraintsJLabel.gridx = 0;
                constraintsJLabel.gridy = 3;
                constraintsJLabel.gridwidth = 4;
                constraintsJLabel.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel.anchor = java.awt.GridBagConstraints.WEST;
                constraintsJLabel.insets = new java.awt.Insets(10, 10, 0, 0);
                java.awt.GridBagConstraints constraintsGaussDataTableScrollPane2 = new java.awt.GridBagConstraints();
                constraintsGaussDataTableScrollPane2.gridx = 5;
                constraintsGaussDataTableScrollPane2.gridy = 6;
                constraintsGaussDataTableScrollPane2.gridwidth = 2;
                constraintsGaussDataTableScrollPane2.fill = java.awt.GridBagConstraints.BOTH;
                constraintsGaussDataTableScrollPane2.insets = new java.awt.Insets(
                        0, 20, 0, 10);
                java.awt.GridBagConstraints constraintsEditOptLags = new java.awt.GridBagConstraints();
                constraintsEditOptLags.gridx = 5;
                constraintsEditOptLags.gridy = 5;
                constraintsEditOptLags.gridwidth = 2;
                constraintsEditOptLags.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsEditOptLags.insets = new java.awt.Insets(5, 20, 0, 0);
                java.awt.GridBagConstraints constraintsShowGraph = new java.awt.GridBagConstraints();
                constraintsShowGraph.gridx = 0;
                constraintsShowGraph.gridy = 10;
                constraintsShowGraph.gridwidth = 3;
                constraintsShowGraph.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsShowGraph.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsShowGraph.insets = new java.awt.Insets(0, 10, 0, 0);
                java.awt.GridBagConstraints constraintsWnCheck = new java.awt.GridBagConstraints();
                constraintsWnCheck.gridx = 0;
                constraintsWnCheck.gridy = 0;
                constraintsWnCheck.gridwidth = 7;
                constraintsWnCheck.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsWnCheck.insets = new java.awt.Insets(0, 10, 0, 0);
                java.awt.GridBagConstraints constraintsBwFactorTextField = new java.awt.GridBagConstraints();
                constraintsBwFactorTextField.gridx = 0;
                constraintsBwFactorTextField.gridy = 7;
                constraintsBwFactorTextField.anchor = java.awt.GridBagConstraints.WEST;
                constraintsBwFactorTextField.insets = new java.awt.Insets(0,
                        10, 0, 0);
                java.awt.GridBagConstraints constraintsJLabel6 = new java.awt.GridBagConstraints();
                java.awt.GridBagConstraints consGridBagConstraints2 = new java.awt.GridBagConstraints();
                java.awt.GridBagConstraints consGridBagConstraints3 = new java.awt.GridBagConstraints();
                consGridBagConstraints2.fill = java.awt.GridBagConstraints.HORIZONTAL;
                consGridBagConstraints2.weightx = 1.0;
                consGridBagConstraints2.gridy = 10;
                consGridBagConstraints2.gridx = 3;
                consGridBagConstraints2.anchor = java.awt.GridBagConstraints.NORTH;
                consGridBagConstraints3.gridy = 8;
                consGridBagConstraints3.gridx = 3;
                consGridBagConstraints3.fill = java.awt.GridBagConstraints.HORIZONTAL;
                consGridBagConstraints3.insets = new java.awt.Insets(5, 0, 0, 0);
                constraintsJLabel6.gridx = 1;
                constraintsJLabel6.gridy = 7;
                constraintsJLabel6.gridwidth = 4;
                constraintsJLabel6.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel6.insets = new java.awt.Insets(0, 10, 0, 0);
                ivjJPanel1.add(getExecute(), constraintsExecute);
                ivjJPanel1.add(getPrintLog(), constraintsPrintLog);
                ivjJPanel1.add(getGridNum(), constraintsGridNum);
                ivjJPanel1.add(getJLabel1(), constraintsJLabel1);
                ivjJPanel1.add(getPerGr(), constraintsPerGr);
                ivjJPanel1.add(getJLabel2(), constraintsJLabel2);
                ivjJPanel1.add(getGridVector(), constraintsGridVector);
                ivjJPanel1.add(getLagGrid1(), constraintsLagGrid1);
                ivjJPanel1.add(getLagGrid2(), constraintsLagGrid2);
                ivjJPanel1.add(getJLabel3(), constraintsJLabel3);
                ivjJPanel1.add(getJLabel4(), constraintsJLabel4);
                ivjJPanel1.add(getJLabel5(), constraintsJLabel5);
                ivjJPanel1.add(getDataTableScrollPane1(),
                        constraintsGaussDataTableScrollPane1);
                ivjJPanel1.add(getJLabel(), constraintsJLabel);
                ivjJPanel1.add(getDataTableScrollPane2(),
                        constraintsGaussDataTableScrollPane2);
                ivjJPanel1.add(getEditOptLags(), constraintsEditOptLags);
                ivjJPanel1.add(getShowGraph(), constraintsShowGraph);
                ivjJPanel1.add(getWnCheck(), constraintsWnCheck);
                ivjJPanel1.add(getBwFactor(), constraintsBwFactorTextField);
                ivjJPanel1.add(getJLabel6(), constraintsJLabel6);
                ivjJPanel1.add(getRotateCombo(), consGridBagConstraints2);
                ivjJPanel1.add(getJLabel7(), consGridBagConstraints3);
                TitledBorder title = new TitledBorder(new BevelBorder(
                        BevelBorder.LOWERED), "Specify Estimation of Model",
                        TitledBorder.RIGHT, TitledBorder.TOP);
                ivjJPanel1.setBorder(title);

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
     * Return the JTabbedPane1 property value.
     * 
     * @return javax.swing.JTabbedPane
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JTabbedPane getJTabbedPane1() {
        if (ivjJTabbedPane1 == null) {
            try {
                ivjJTabbedPane1 = new javax.swing.JTabbedPane();
                ivjJTabbedPane1.setName("JTabbedPane1");
                ivjJTabbedPane1.insertTab("Specify Volatility Estimation",
                        null, getJPanel1(), null, 0);
                ivjJTabbedPane1.insertTab("Output (save/print)", null,
                        getResultField(), null, 1);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJTabbedPane1;
    }

    /**
     * Return the LagGrid1 property value.
     * 
     * @return javax.swing.JComboBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JComboBox getLagGrid1() {
        if (ivjLagGrid1 == null) {
            try {
                ivjLagGrid1 = new javax.swing.JComboBox();
                ivjLagGrid1.setName("LagGrid1");
                ivjLagGrid1.setPreferredSize(new java.awt.Dimension(100, 23));
                ivjLagGrid1.setMinimumSize(new java.awt.Dimension(100, 23));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjLagGrid1;
    }

    /**
     * Return the LagGrid2 property value.
     * 
     * @return javax.swing.JComboBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JComboBox getLagGrid2() {
        if (ivjLagGrid2 == null) {
            try {
                ivjLagGrid2 = new javax.swing.JComboBox();
                ivjLagGrid2.setName("LagGrid2");
                ivjLagGrid2.setPreferredSize(new java.awt.Dimension(100, 23));
                ivjLagGrid2.setMinimumSize(new java.awt.Dimension(100, 23));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjLagGrid2;
    }

    private NumSelector getPerGr() {
        if (ivjPerGr == null) {
            try {
                ivjPerGr = new NumSelector();
                ivjPerGr.setName("PerGr");
                ivjPerGr.setPreferredSize(new java.awt.Dimension(55, 20));
                ivjPerGr.setMinimumSize(new java.awt.Dimension(55, 20));
                ivjPerGr.setPrecision(2);
                ivjPerGr.setRangeExpr("[0,1)");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjPerGr;
    }

    /**
     * Return the PrintLog property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getPrintLog() {
        if (ivjPrintLog == null) {
            try {
                ivjPrintLog = new javax.swing.JCheckBox();
                ivjPrintLog.setName("PrintLog");
                ivjPrintLog.setText("More detailled output");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjPrintLog;
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

    private JSCNArrayTable getScrollPaneTable1() {
        if (ivjScrollPaneTable1 == null) {
            try {
                ivjScrollPaneTable1 = new JSCNArrayTable();
                ivjScrollPaneTable1.setName("ScrollPaneTable1");
                getDataTableScrollPane1().setColumnHeaderView(
                        ivjScrollPaneTable1.getTableHeader());
                ivjScrollPaneTable1.setPrecision(4);
                ivjScrollPaneTable1.setBounds(0, 0, 147, 107);
                ivjScrollPaneTable1
                        .setSymbolName(CAFPE_constants.XCONST_FAC_VOLAT.name);
                ivjScrollPaneTable1.setEditable(true);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjScrollPaneTable1;
    }

    private JSCNArrayTable getScrollPaneTable2() {
        if (ivjScrollPaneTable2 == null) {
            try {
                ivjScrollPaneTable2 = new JSCNArrayTable();
                ivjScrollPaneTable2.setName("ScrollPaneTable2");
                getDataTableScrollPane2().setColumnHeaderView(
                        ivjScrollPaneTable2.getTableHeader());
                ivjScrollPaneTable2.setPrecision(0);
                ivjScrollPaneTable2.setTablePopup(null);
                ivjScrollPaneTable2.setVisible(false);
                ivjScrollPaneTable2
                        .setSymbolName(CAFPE_constants.OPT_LAGS_VOLAT.name);
                ivjScrollPaneTable2.setBounds(0, 0, 147, 107);
                ivjScrollPaneTable2.setEditable(true);
                ivjScrollPaneTable2.setEnabled(true);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjScrollPaneTable2;
    }

    /**
     * Return the NoGraph property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getShowGraph() {
        if (ivjShowGraph == null) {
            try {
                ivjShowGraph = new javax.swing.JCheckBox();
                ivjShowGraph.setName("ShowGraph");
                ivjShowGraph.setSelected(true);
                ivjShowGraph.setText("Show graphics");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjShowGraph;
    }

    /**
     * Return the WnCheck property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getWnCheck() {
        if (ivjWnCheck == null) {
            try {
                ivjWnCheck = new javax.swing.JCheckBox();
                ivjWnCheck.setName("WnCheck");
                ivjWnCheck
                        .setText("Set conditional mean to zero (f(y) = 0; resids = y)");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjWnCheck;
    }

    /**
     * Method generated to support the promotion of the wnCheckSelected
     * attribute.
     * 
     * @return boolean
     */
    public boolean getWnCheckSelected() {
        return getWnCheck().isSelected();
    }

    /**
     * Comment
     */
    private void gridVector_ActionEvents() {
        // set the maximum lag that is used for the estimation
        int index = getGridVector().getSelectedIndex();
        if (index < 0)
            return;

        int lagmax = 0;
        for (int j = 0; j < lags[0].length; j++) {
            if (lags[getGridVector().getSelectedIndex()][j] > lagmax)
                lagmax = lags[getGridVector().getSelectedIndex()][j];
        }
        global().get(CAFPE_constants.LAG_MAX_VOLAT).setJSCData(
                new JSCInt("lagmax", lagmax));
        setXGridLag();
        setYGridLag();
        setXconstTable();

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
        this.addComponentListener(ivjEventHandler);
        getGridVector().addActionListener(ivjEventHandler);
        getLagGrid1().addActionListener(ivjEventHandler);
        getLagGrid2().addActionListener(ivjEventHandler);
        getEditOptLags().addActionListener(ivjEventHandler);
    }

    /**
     * Initialize the class.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initialize() {
        try {
            // user code begin {1}
            // user code end
            setName("EstimateCAFPE");
            setLayout(new BorderLayout());
            setSize(637, 423);
            add(getJTabbedPane1(), BorderLayout.CENTER);
            initConnections();
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}
        global().get(CAFPE_constants.OPT_LAGS_VOLAT).addSymbolListener(
                new SymbolListener() {

                    public void valueChanged(SymbolEvent evt) {
                        setGridVectorFromOptLags();
                    }
                });

        // user code end
    }

    /**
     * Comment
     */
    private void lagGrid1_ActionEvents() {
        // first make sure, that lagGrid2 has not the same selection
        // as lagGrid1, then update the dataTable
        if (getLagGrid1().getSelectedIndex() < 0
                || getLagGrid2().getSelectedIndex() < 0)
            return;

        if (getLagGrid2().getItemCount() > 0)
            if ((getLagGrid1().getSelectedItem() + "").equals(getLagGrid2()
                    .getSelectedItem()
                    + "")) {
                if (getLagGrid1().getSelectedIndex() - 1 >= 0) {
                    getLagGrid2().setSelectedIndex(
                            getLagGrid1().getSelectedIndex() - 1);

                } else if (getLagGrid1().getSelectedIndex() + 1 < getLagGrid2()
                        .getItemCount())
                    getLagGrid2().setSelectedIndex(
                            getLagGrid1().getSelectedIndex() + 1);
            }
        setXconstTable();

    }

    /**
     * Comment
     */
    private void lagGrid2_ActionEvents() {
        // first make sure, that lagGrid1 has not the same selection
        // as lagGrid2, then update the dataTable
        if (getLagGrid1().getSelectedIndex() < 0
                || getLagGrid2().getSelectedIndex() < 0)
            return;

        if ((getLagGrid2().getSelectedItem() + "").equals(getLagGrid1()
                .getSelectedItem()
                + "")) {
            if (getLagGrid2().getSelectedIndex() - 1 >= 0) {
                getLagGrid1().setSelectedIndex(
                        getLagGrid2().getSelectedIndex() - 1);

            } else if (getLagGrid2().getSelectedIndex() + 1 < getLagGrid1()
                    .getItemCount())
                getLagGrid1().setSelectedIndex(
                        getLagGrid2().getSelectedIndex() + 1);
        }
        setXconstTable();
    }

    /**
     * Comment
     */
    private void setGridVectorFromOptLags() {
        // set the selected lags
        String grid = null;

        if (global().get(CAFPE_constants.OPT_LAGS_VOLAT).isEmpty()
                || global().get(CAFPE_constants.CRMIN_VOLAT).isEmpty())
            return;
        getGridVector().removeAllItems();

        lags = global().get(CAFPE_constants.OPT_LAGS_VOLAT).getJSCNArray()
                .intArray();
        // check whether there are only 0 in a row
        for (int i = 0; i < lags.length; i++) {
            int check = 0;
            for (int j = 0; j < lags[0].length; j++)
                check += lags[i][j];
            if (check == 0) {
                StdMessages.errorSpecification("Row " + (i + 1)
                        + " contains only 0.");
                global().get(CAFPE_constants.OPT_LAGS_VOLAT).getJSCNArray()
                        .setValAt(1, i, 0);

                return;
            }
        }
        // check whether the order is increasing to avoid same values and order
        // mixing
        for (int i = 0; i < lags.length; i++) {
            for (int j = 0; j < lags[0].length - 1; j++) {
                if (lags[i][j] >= lags[i][j + 1] && lags[i][j + 1] > 0) {
                    StdMessages
                            .errorSpecification("Row "
                                    + (i + 1)
                                    + " is not in strictly increasing order,\ne.g. (1 3 4) instead of (3 1 4).\nZeros in between are allowed");
                    return;
                }

            }
        }

        for (int i = 0; i < lags.length; i++) {
            grid = "";
            for (int j = 0; j < lags[0].length; j++) {
                if (lags[i][j] > 0)
                    grid = grid + new Double(lags[i][j]).intValue() + "|";
            }
            if (grid.length() > 0)
                getGridVector().addItem(grid.substring(0, grid.length() - 1));
        }
        int sel = 0;
        for (int i = 0; i < global().get(CAFPE_constants.D_MAX_VOLAT)
                .getJSCInt().intVal(); i++)
            sel += (global().get(CAFPE_constants.CRMIN_VOLAT).getJSCNArray()
                    .doubleAt(0, i) > 0) ? 1 : 0;
        if (sel - 1 >= 0)
            getGridVector().setSelectedIndex(sel - 1);
        else
            getGridVector().setSelectedIndex(0);

    }

    /**
     * Method generated to support the promotion of the wnCheckSelected
     * attribute.
     * 
     * @param arg1
     *            boolean
     */
    public void setWnCheckSelected(boolean arg1) {
        getWnCheck().setSelected(arg1);
    }

    /**
     * Comment
     */
    private void setXconstTable() {
        // the map contains the values in the table an remembers them
        // if the selection changes
        int isSel = 0;
        xconstMap.clear();
        for (int i = 0; i < global().get(CAFPE_constants.XCONST_FAC_VOLAT)
                .getJSCNArray().rows(); i++) {
            xconstMap.put(getDataTableScrollPane1().getRowHeaderData()
                    .stringAt(i, 0), global().get(
                    CAFPE_constants.XCONST_FAC_VOLAT).getJSCNArray()
                    .doubleValAt(i, 0));
        }

        // check whether a selection makes sense and set the DataTable
        if (getCurrentNonzeroLags() < 3 - isSel) {
            getDataTableScrollPane1().setRowHeaderData(
                    new JSCSArray("rowheader", "no lags"));
            global().get(CAFPE_constants.XCONST_FAC_VOLAT).setJSCData(
                    new JSCNArray("toSet", new double[1][1]));
            getScrollPaneTable1().setEditable(false);
            return;

        }
        // ok, there is something to condition
        getScrollPaneTable1().setEditable(true);
        String[] condLags = new String[getCurrentNonzeroLags() - 2 + isSel];
        double[] xconstfac = new double[condLags.length];
        Arrays.fill(xconstfac, global().get(CAFPE_constants.MEAN_Y)
                .getJSCNumber().doubleVal());
        String toComp = "EMPTY";
        if (getLagGrid2().getSelectedItem() != null)
            toComp = getLagGrid2().getSelectedItem().toString();

        for (int i = 0, j = 0; i < lags[0].length; i++) {
            String comp = new Double(
                    lags[getGridVector().getSelectedIndex()][i]).intValue()
                    + "";
            // make sure that LagGrid1 and LagGrid2 do not have the same
            // selection!
            if (!comp.equals(getLagGrid1().getSelectedItem().toString())
                    && !comp.equals(toComp)
                    && lags[getGridVector().getSelectedIndex()][i] > 0) {
                if (j < condLags.length) {
                    condLags[j] = "lag " + comp;
                    if (xconstMap.containsKey(comp))
                        xconstfac[j] = xconstMap.get(comp).doubleValue();
                    j++;
                }
            }

        }
        getDataTableScrollPane1().setRowHeaderData(
                new JSCSArray("rowheader", condLags));
        global().get(CAFPE_constants.XCONST_FAC_VOLAT).setJSCData(
                new JSCNArray("xconstfac", xconstfac));
    }

    /**
     * Comment
     */
    private void setXGridLag() {
        int index = getGridVector().getSelectedIndex();
        if (index < 0)
            return;
        getLagGrid1().removeAllItems();
        // fill the "x" plot
        for (int j = 0; j < lags[0].length; j++) {
            if (lags[index][j] > 0)
                getLagGrid1().addItem(
                        new Double(lags[index][j]).intValue() + "");
        }

    }

    /**
     * Comment
     */
    private void setYGridLag() {

        // fill the "y" plot in case it makes sense
        getLagGrid2().removeAllItems();
        int lagCount = 0;
        for (int j = 0; j < lags[0].length; j++)
            if (lags[getGridVector().getSelectedIndex()][j] > 0)
                lagCount++;

        if (lagCount < 2) {
            getJLabel5().setEnabled(false);
            getLagGrid2().setEnabled(false);
            return;
        }
        getJLabel5().setEnabled(true);
        getLagGrid2().setEnabled(true);

        for (int j = 0; j < lags[0].length; j++) {
            if (lags[getGridVector().getSelectedIndex()][j] > 0)
                getLagGrid2().addItem(
                        new Double(lags[getGridVector().getSelectedIndex()][j])
                                .intValue()
                                + "");
        }
        if (getLagGrid2().getItemCount() > 1)
            getLagGrid2().setSelectedIndex(1);
    }

    /**
     * Comment
     */
    private void this_componentShown(
            java.awt.event.ComponentEvent componentEvent) {

        getJTabbedPane1().setSelectedIndex(0);

        double upper = global().get(CAFPE_constants.MAX_LAG_VOLAT).getJSCInt()
                .intVal();

        getScrollPaneTable2().setNumberRange(
                new NumberRange(0, upper, NumberRangeTypes.CLOSED));
    }

    /**
     * This method initializes jComboBox
     * 
     * @return javax.swing.JComboBox
     */
    private javax.swing.JComboBox getRotateCombo() {
        if (jComboBox == null) {
            jComboBox = new javax.swing.JComboBox();
            jComboBox.addItem("0 degrees");
            jComboBox.addItem("90 degrees");
            jComboBox.addItem("180 degrees");
            jComboBox.addItem("270 degrees");
            jComboBox.setSelectedIndex(0);
        }
        return jComboBox;
    }

    /**
     * This method initializes jLabel
     * 
     * @return javax.swing.JLabel
     */
    private javax.swing.JLabel getJLabel7() {
        if (jLabel == null) {
            jLabel = new javax.swing.JLabel();
            jLabel.setText("Rotate surface plot");
            jLabel.setPreferredSize(new java.awt.Dimension(130, 24));
        }
        return jLabel;
    }
}