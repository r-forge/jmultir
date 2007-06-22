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
 * Nonparametric estimation of univariate time series model.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */

final class CAFPE_est extends ModelPanel {
    private static final Logger log = Logger.getLogger(CAFPE_est.class);

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

    private JCheckBox ivjEditOptLags = null;

    private JSCDataTableScrollPane ivjDataTableScrollPane2 = null;

    private JSCNArrayTable ivjScrollPaneTable1 = null;

    private JLabel ivjJLabel6 = null;

    private JSCNArrayTable ivjScrollPaneTable2 = null;

    private JCheckBox ivjPlotCI = null;

    private JSCDataTableScrollPane ivjDataTableScrollPane3 = null;

    private JSCNArrayTable ivjScrollPaneTable3 = null;

    private JCheckBox ivjShowGraph = null;

    private NumSelector ivjCiLevel = null;

    private JLabel ivjJLabel7 = null;

    private JCheckBox ivjBonf = null;

    private NumSelector ivjBwFactorTextField = null;

    private JLabel ivjJLabel8 = null;

    private javax.swing.JComboBox jComboBox = null;

    private javax.swing.JLabel jLabel = null;

    class IvjEventHandler implements java.awt.event.ActionListener,
            java.awt.event.ComponentListener {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == CAFPE_est.this.getExecute())
                connEtoC1();
            if (e.getSource() == CAFPE_est.this.getGridVector())
                connEtoC3();
            if (e.getSource() == CAFPE_est.this.getLagGrid1())
                connEtoC4();
            if (e.getSource() == CAFPE_est.this.getLagGrid2())
                connEtoC5();
            if (e.getSource() == CAFPE_est.this.getEditOptLags())
                connEtoC6();
            if (e.getSource() == CAFPE_est.this.getPlotCI())
                connEtoC7();
        };

        public void componentHidden(java.awt.event.ComponentEvent e) {
        };

        public void componentMoved(java.awt.event.ComponentEvent e) {
        };

        public void componentResized(java.awt.event.ComponentEvent e) {
        };

        public void componentShown(java.awt.event.ComponentEvent e) {
            if (e.getSource() == CAFPE_est.this)
                connEtoC8(e);
        };
    };

    /**
     * EstimateCAFPE constructor comment.
     */
    public CAFPE_est() {
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
     * connEtoC7: (PlotCI.action. --> CAFPE_est.plotCI_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC7() {
        try {
            // user code begin {1}
            // user code end
            this.plotCI_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC8:
     * (JTabbedPane1.component.componentShown(java.awt.event.ComponentEvent) -->
     * CAFPE_est.this_componentShown(Ljava.awt.event.ComponentEvent;)V)
     * 
     * @param arg1
     *            java.awt.event.ComponentEvent
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC8(java.awt.event.ComponentEvent arg1) {
        try {
            // user code begin {1}
            // user code end
            this.this_componentShown();
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
    }

    /**
     * Execute CAFPE estimation.
     */
    private void execute_ActionEvents() {
        if (getGridVector().getItemCount() == 0) {
            StdMessages.errorSpecification("The lag selection is empty.");
            return;
        }
        final File outFile = new File(JSCConstants.getSystemTemp() + "/"
                + CAFPE_constants.OUT_FILE_EST);
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

        JSCNArray lagsGrid = new JSCNArray("CAFPE_EST_LAGSGRID", lagsGridArray);
        JSCNArray gridVar = new JSCNArray("CAFPE_EST_GRIDVAR", gridVarArray);

        int index = getRotateCombo().getSelectedIndex();
        int rotateDegree = 90 * index;

        TSDateRange range = global().get(CAFPE_constants.DRANGE).getJSCDRange()
                .getTSDateRange();

        PCall job = new CAFPEEstCall(global().get(CAFPE_constants.Y)
                .getJSCNArray(), new JSCNArray("res", 0), global().get(
                CAFPE_constants.Y_NAME).getJSCString().string(), range
                .lowerBound(), lagsGrid, gridVar, outFile, global().get(
                CAFPE_constants.MOD_METH).getJSCString().string(), global()
                .get(CAFPE_constants.SEARCH_METH).getJSCString().string(),
                global().get(CAFPE_constants.SELCRIT).getJSCString().string(),
                global().get(CAFPE_constants.STARTSTRAT).getJSCString()
                        .string(), global().get(CAFPE_constants.XCONST_FAC)
                        .getJSCNArray(), global().get(
                        CAFPE_constants.XCONST_ALL).getJSCNArray(),
                getPrintLog().isSelected(), global().get(
                        CAFPE_constants.IS_STAND).getJSCInt().isNonZero(),
                getPerGr().getNumber(), getGridNum().getIntNumber(),
                getPlotCI().isSelected(), getCiLevel().getNumber(), getBonf()
                        .isSelected(), getBwFactor().getNumber(),
                getShowGraph().isSelected(), rotateDegree, false, false);
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

    /**
     * Return the Bonf property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getBonf() {
        if (ivjBonf == null) {
            try {
                ivjBonf = new javax.swing.JCheckBox();
                ivjBonf.setName("Bonf");
                ivjBonf.setPreferredSize(new java.awt.Dimension(100, 22));
                ivjBonf.setText("Bonferroni CIs");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjBonf;
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

    private NumSelector getCiLevel() {
        if (ivjCiLevel == null) {
            try {
                ivjCiLevel = new NumSelector();
                ivjCiLevel.setName("CiLevel");
                ivjCiLevel.setNumber(0.95);
                ivjCiLevel.setPrecision(2);
                ivjCiLevel.setRangeExpr("(0,1)");
                ivjCiLevel.setPreferredSize(new java.awt.Dimension(55, 20));
                ivjCiLevel.setMinimumSize(new java.awt.Dimension(55, 20));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjCiLevel;
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
                        .setPreferredSize(new java.awt.Dimension(100, 22));
                ivjEditOptLags
                        .setText("Edit sets of lags obtained from lag selection");
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

    private JSCDataTableScrollPane getDataTableScrollPane3() {
        if (ivjDataTableScrollPane3 == null) {
            try {
                ivjDataTableScrollPane3 = new JSCDataTableScrollPane();
                ivjDataTableScrollPane3.setName("DataTableScrollPane3");
                ivjDataTableScrollPane3.setMaximumVisibleColumns(1);
                String ivjLocal55columnHeaderStringData[] = { "values" };
                ivjDataTableScrollPane3
                        .setColumnHeaderStringData(ivjLocal55columnHeaderStringData);
                ivjDataTableScrollPane3.setRowHeaderShowing(true);
                ivjDataTableScrollPane3
                        .setPreferredSize(new java.awt.Dimension(200, 100));
                ivjDataTableScrollPane3.setColumnHeaderShowing(true);
                ivjDataTableScrollPane3.setMinimumVisibleRows(3);
                ivjDataTableScrollPane3.setMaximumVisibleRows(10);
                getDataTableScrollPane3()
                        .setViewportView(getScrollPaneTable3());
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjDataTableScrollPane3;
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
                ivjGridVector.setPreferredSize(new java.awt.Dimension(50, 23));
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
     * Insert the method's description here. Creation date: (18.04.2002
     * 15:43:37)
     * 
     * @return javax.swing.JComboBox
     */
    protected javax.swing.JComboBox getIvjGridVector() {
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
                ivjJLabel2.setPreferredSize(new java.awt.Dimension(280, 20));
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
                ivjJLabel5.setText("2nd lag to plot ");
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
                ivjJLabel6.setPreferredSize(new java.awt.Dimension(100, 20));
                ivjJLabel6.setText("Evaluate function at");
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
     * Return the JLabel7 property value.
     * 
     * @return javax.swing.JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JLabel getJLabel7() {
        if (ivjJLabel7 == null) {
            try {
                ivjJLabel7 = new javax.swing.JLabel();
                ivjJLabel7.setName("JLabel7");
                ivjJLabel7.setPreferredSize(new java.awt.Dimension(100, 20));
                ivjJLabel7.setText("CI level");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJLabel7;
    }

    /**
     * Return the JLabel8 property value.
     * 
     * @return javax.swing.JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JLabel getJLabel8() {
        if (ivjJLabel8 == null) {
            try {
                ivjJLabel8 = new javax.swing.JLabel();
                ivjJLabel8.setName("JLabel8");
                ivjJLabel8.setPreferredSize(new java.awt.Dimension(182, 20));
                ivjJLabel8.setText("Factor to multiply plug-in bandwidth with");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJLabel8;
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
                constraintsExecute.gridx = 4;
                constraintsExecute.gridy = 9;
                constraintsExecute.gridwidth = 2;
                constraintsExecute.anchor = java.awt.GridBagConstraints.SOUTH;
                constraintsExecute.insets = new java.awt.Insets(5, 20, 10, 10);
                java.awt.GridBagConstraints constraintsPrintLog = new java.awt.GridBagConstraints();
                constraintsPrintLog.gridx = 0;
                constraintsPrintLog.gridy = 8;
                constraintsPrintLog.gridwidth = 2;
                constraintsPrintLog.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsPrintLog.insets = new java.awt.Insets(5, 10, 0, 0);
                java.awt.GridBagConstraints constraintsGridNum = new java.awt.GridBagConstraints();
                constraintsGridNum.gridx = 0;
                constraintsGridNum.gridy = 6;
                constraintsGridNum.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsGridNum.insets = new java.awt.Insets(0, 10, 0, 0);
                getJPanel1().add(getGridNum(), constraintsGridNum);

                java.awt.GridBagConstraints constraintsJLabel1 = new java.awt.GridBagConstraints();
                constraintsJLabel1.gridx = 1;
                constraintsJLabel1.gridy = 6;
                constraintsJLabel1.gridwidth = 2;
                constraintsJLabel1.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel1.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsJLabel1.insets = new java.awt.Insets(0, 10, 0, 0);
                getJPanel1().add(getJLabel1(), constraintsJLabel1);

                java.awt.GridBagConstraints constraintsPerGr = new java.awt.GridBagConstraints();
                constraintsPerGr.gridx = 0;
                constraintsPerGr.gridy = 5;
                constraintsPerGr.anchor = java.awt.GridBagConstraints.WEST;
                constraintsPerGr.insets = new java.awt.Insets(5, 10, 0, 0);
                java.awt.GridBagConstraints constraintsJLabel2 = new java.awt.GridBagConstraints();
                constraintsJLabel2.gridx = 1;
                constraintsJLabel2.gridy = 5;
                constraintsJLabel2.gridwidth = 2;
                constraintsJLabel2.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel2.insets = new java.awt.Insets(5, 10, 0, 0);
                java.awt.GridBagConstraints constraintsGridVector = new java.awt.GridBagConstraints();
                constraintsGridVector.gridx = 0;
                constraintsGridVector.gridy = 1;
                constraintsGridVector.gridwidth = 3;
                constraintsGridVector.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsGridVector.anchor = java.awt.GridBagConstraints.WEST;
                constraintsGridVector.insets = new java.awt.Insets(0, 10, 0, 0);
                getJPanel1().add(getGridVector(), constraintsGridVector);

                java.awt.GridBagConstraints constraintsLagGrid1 = new java.awt.GridBagConstraints();
                constraintsLagGrid1.gridx = 3;
                constraintsLagGrid1.gridy = 1;
                constraintsLagGrid1.gridwidth = 2;
                constraintsLagGrid1.anchor = java.awt.GridBagConstraints.WEST;
                constraintsLagGrid1.insets = new java.awt.Insets(0, 20, 0, 0);
                getJPanel1().add(getLagGrid1(), constraintsLagGrid1);

                java.awt.GridBagConstraints constraintsLagGrid2 = new java.awt.GridBagConstraints();
                constraintsLagGrid2.gridx = 5;
                constraintsLagGrid2.gridy = 1;
                constraintsLagGrid2.anchor = java.awt.GridBagConstraints.WEST;
                constraintsLagGrid2.insets = new java.awt.Insets(0, 20, 0, 0);
                getJPanel1().add(getLagGrid2(), constraintsLagGrid2);

                java.awt.GridBagConstraints constraintsJLabel3 = new java.awt.GridBagConstraints();
                constraintsJLabel3.gridx = 0;
                constraintsJLabel3.gridy = 0;
                constraintsJLabel3.gridwidth = 3;
                constraintsJLabel3.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel3.anchor = java.awt.GridBagConstraints.WEST;
                constraintsJLabel3.insets = new java.awt.Insets(0, 10, 0, 0);
                getJPanel1().add(getJLabel3(), constraintsJLabel3);

                java.awt.GridBagConstraints constraintsJLabel4 = new java.awt.GridBagConstraints();
                constraintsJLabel4.gridx = 3;
                constraintsJLabel4.gridy = 0;
                constraintsJLabel4.gridwidth = 2;
                constraintsJLabel4.anchor = java.awt.GridBagConstraints.WEST;
                constraintsJLabel4.insets = new java.awt.Insets(0, 20, 0, 0);
                getJPanel1().add(getJLabel4(), constraintsJLabel4);

                java.awt.GridBagConstraints constraintsJLabel5 = new java.awt.GridBagConstraints();
                constraintsJLabel5.gridx = 5;
                constraintsJLabel5.gridy = 0;
                constraintsJLabel5.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel5.anchor = java.awt.GridBagConstraints.WEST;
                constraintsJLabel5.insets = new java.awt.Insets(0, 20, 0, 0);
                getJPanel1().add(getJLabel5(), constraintsJLabel5);

                java.awt.GridBagConstraints constraintsGaussDataTableScrollPane1 = new java.awt.GridBagConstraints();
                constraintsGaussDataTableScrollPane1.gridx = 0;
                constraintsGaussDataTableScrollPane1.gridy = 4;
                constraintsGaussDataTableScrollPane1.gridwidth = 3;
                constraintsGaussDataTableScrollPane1.fill = java.awt.GridBagConstraints.BOTH;
                constraintsGaussDataTableScrollPane1.weightx = 0.0D;
                constraintsGaussDataTableScrollPane1.weighty = 1.0;
                constraintsGaussDataTableScrollPane1.insets = new java.awt.Insets(
                        0, 10, 0, 0);
                java.awt.GridBagConstraints constraintsJLabel = new java.awt.GridBagConstraints();
                constraintsJLabel.gridx = 0;
                constraintsJLabel.gridy = 3;
                constraintsJLabel.gridwidth = 2;
                constraintsJLabel.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel.anchor = java.awt.GridBagConstraints.WEST;
                constraintsJLabel.insets = new java.awt.Insets(5, 10, 0, 0);
                getJPanel1().add(getJLabel(), constraintsJLabel);

                java.awt.GridBagConstraints constraintsGaussDataTableScrollPane2 = new java.awt.GridBagConstraints();
                constraintsGaussDataTableScrollPane2.gridx = 4;
                constraintsGaussDataTableScrollPane2.gridy = 6;
                constraintsGaussDataTableScrollPane2.gridwidth = 2;
                constraintsGaussDataTableScrollPane2.fill = java.awt.GridBagConstraints.BOTH;
                constraintsGaussDataTableScrollPane2.insets = new java.awt.Insets(
                        0, 20, 0, 10);
                getJPanel1().add(getDataTableScrollPane2(),
                        constraintsGaussDataTableScrollPane2);

                java.awt.GridBagConstraints constraintsEditOptLags = new java.awt.GridBagConstraints();
                constraintsEditOptLags.gridx = 4;
                constraintsEditOptLags.gridy = 5;
                constraintsEditOptLags.gridwidth = 2;
                constraintsEditOptLags.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsEditOptLags.insets = new java.awt.Insets(5, 20, 0, 0);
                java.awt.GridBagConstraints constraintsJLabel6 = new java.awt.GridBagConstraints();
                constraintsJLabel6.gridx = 4;
                constraintsJLabel6.gridy = 3;
                constraintsJLabel6.gridwidth = 3;
                constraintsJLabel6.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel6.anchor = java.awt.GridBagConstraints.WEST;
                constraintsJLabel6.insets = new java.awt.Insets(5, 20, 0, 0);
                getJPanel1().add(getJLabel6(), constraintsJLabel6);

                java.awt.GridBagConstraints constraintsGaussDataTableScrollPane3 = new java.awt.GridBagConstraints();
                constraintsGaussDataTableScrollPane3.gridx = 4;
                constraintsGaussDataTableScrollPane3.gridy = 4;
                constraintsGaussDataTableScrollPane3.gridwidth = 2;
                constraintsGaussDataTableScrollPane3.fill = java.awt.GridBagConstraints.BOTH;
                constraintsGaussDataTableScrollPane3.weightx = 1.0;
                constraintsGaussDataTableScrollPane3.weighty = 1.0;
                constraintsGaussDataTableScrollPane3.insets = new java.awt.Insets(
                        0, 20, 0, 0);
                getJPanel1().add(getDataTableScrollPane3(),
                        constraintsGaussDataTableScrollPane3);

                java.awt.GridBagConstraints constraintsPlotCI = new java.awt.GridBagConstraints();
                constraintsPlotCI.gridx = 0;
                constraintsPlotCI.gridy = 2;
                constraintsPlotCI.gridwidth = 2;
                constraintsPlotCI.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsPlotCI.insets = new java.awt.Insets(10, 10, 0, 0);
                getJPanel1().add(getPlotCI(), constraintsPlotCI);

                java.awt.GridBagConstraints constraintsShowGraph = new java.awt.GridBagConstraints();
                constraintsShowGraph.gridx = 0;
                constraintsShowGraph.gridy = 9;
                constraintsShowGraph.gridwidth = 2;
                constraintsShowGraph.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsShowGraph.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsShowGraph.insets = new java.awt.Insets(0, 10, 0, 0);
                java.awt.GridBagConstraints constraintsCiLevel = new java.awt.GridBagConstraints();
                constraintsCiLevel.gridx = 5;
                constraintsCiLevel.gridy = 2;
                constraintsCiLevel.anchor = java.awt.GridBagConstraints.WEST;
                constraintsCiLevel.insets = new java.awt.Insets(10, 20, 0, 0);
                getJPanel1().add(getCiLevel(), constraintsCiLevel);

                java.awt.GridBagConstraints constraintsJLabel7 = new java.awt.GridBagConstraints();
                constraintsJLabel7.gridx = 4;
                constraintsJLabel7.gridy = 2;
                constraintsJLabel7.gridwidth = 2;
                constraintsJLabel7.anchor = java.awt.GridBagConstraints.WEST;
                constraintsJLabel7.insets = new java.awt.Insets(10, 20, 0, 0);
                getJPanel1().add(getJLabel7(), constraintsJLabel7);

                java.awt.GridBagConstraints constraintsBonf = new java.awt.GridBagConstraints();
                constraintsBonf.gridx = 2;
                constraintsBonf.gridy = 2;
                constraintsBonf.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsBonf.insets = new java.awt.Insets(10, 10, 0, 0);
                getJPanel1().add(getBonf(), constraintsBonf);

                java.awt.GridBagConstraints constraintsBwFactorTextField = new java.awt.GridBagConstraints();
                constraintsBwFactorTextField.gridx = 0;
                constraintsBwFactorTextField.gridy = 7;
                constraintsBwFactorTextField.anchor = java.awt.GridBagConstraints.WEST;
                constraintsBwFactorTextField.insets = new java.awt.Insets(0,
                        10, 0, 0);
                getJPanel1().add(getBwFactor(), constraintsBwFactorTextField);

                java.awt.GridBagConstraints constraintsJLabel8 = new java.awt.GridBagConstraints();
                java.awt.GridBagConstraints consGridBagConstraints1 = new java.awt.GridBagConstraints();
                java.awt.GridBagConstraints consGridBagConstraints3 = new java.awt.GridBagConstraints();
                consGridBagConstraints3.gridy = 8;
                consGridBagConstraints3.gridx = 2;
                consGridBagConstraints3.fill = java.awt.GridBagConstraints.HORIZONTAL;
                consGridBagConstraints3.insets = new java.awt.Insets(5, 0, 0, 0);
                consGridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
                consGridBagConstraints1.weightx = 0.0D;
                consGridBagConstraints1.gridy = 9;
                consGridBagConstraints1.gridx = 2;
                consGridBagConstraints1.insets = new java.awt.Insets(0, 0, 0, 0);
                consGridBagConstraints1.anchor = java.awt.GridBagConstraints.NORTH;
                constraintsJLabel8.gridx = 1;
                constraintsJLabel8.gridy = 7;
                constraintsJLabel8.gridwidth = 2;
                constraintsJLabel8.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel8.insets = new java.awt.Insets(0, 10, 0, 0);
                ivjJPanel1.add(getDataTableScrollPane1(),
                        constraintsGaussDataTableScrollPane1);
                ivjJPanel1.add(getPerGr(), constraintsPerGr);
                ivjJPanel1.add(getJLabel2(), constraintsJLabel2);
                ivjJPanel1.add(getEditOptLags(), constraintsEditOptLags);
                ivjJPanel1.add(getExecute(), constraintsExecute);
                ivjJPanel1.add(getShowGraph(), constraintsShowGraph);
                ivjJPanel1.add(getPrintLog(), constraintsPrintLog);
                getJPanel1().add(getJLabel8(), constraintsJLabel8);
                // user code begin {1}
                ivjJPanel1.add(getRotateCombo(), consGridBagConstraints1);
                ivjJPanel1.add(getJLabel9(), consGridBagConstraints3);
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
                ivjJTabbedPane1.insertTab("Specify Mean Estimation", null,
                        getJPanel1(), null, 0);
                ivjJTabbedPane1.addTab("Output (save/print)", null,
                        getResultField(), null);
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

    /**
     * Return the PerGr property value.
     * 
     * @return com.jstatcom.model.NumberSelector
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private NumSelector getPerGr() {
        if (ivjPerGr == null) {
            try {
                ivjPerGr = new NumSelector();
                ivjPerGr.setName("PerGr");
                ivjPerGr.setPreferredSize(new java.awt.Dimension(55, 20));
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
     * Return the PlotCI property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getPlotCI() {
        if (ivjPlotCI == null) {
            try {
                ivjPlotCI = new javax.swing.JCheckBox();
                ivjPlotCI.setName("PlotCI");
                ivjPlotCI.setPreferredSize(new java.awt.Dimension(200, 22));
                ivjPlotCI.setText("Plot CIs (no 3D)");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjPlotCI;
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
                        .setSymbolName(CAFPE_constants.XCONST_FAC.name);
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
                        .setSymbolName(CAFPE_constants.OPT_LAGS.name);
                ivjScrollPaneTable2.setBounds(0, 0, 147, 107);
                ivjScrollPaneTable2.setEditable(true);

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

    private JSCNArrayTable getScrollPaneTable3() {
        if (ivjScrollPaneTable3 == null) {
            try {
                ivjScrollPaneTable3 = new JSCNArrayTable();
                ivjScrollPaneTable3.setName("ScrollPaneTable3");
                getDataTableScrollPane3().setColumnHeaderView(
                        ivjScrollPaneTable3.getTableHeader());
                ivjScrollPaneTable3.setPrecision(4);
                ivjScrollPaneTable3.setBounds(0, 0, 147, 107);
                ivjScrollPaneTable3
                        .setSymbolName(CAFPE_constants.XCONST_ALL.name);
                ivjScrollPaneTable3.setEditable(true);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjScrollPaneTable3;
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
     * Comment
     */
    private void gridVector_ActionEvents() {
        // set the maximum lag that is used for the estimation
        int index = getGridVector().getSelectedIndex();
        if (index < 0)
            return;
        int lagmax = 0;
        for (int j = 0; j < lags[0].length; j++) {
            if (lags[index][j] > lagmax)
                lagmax = lags[index][j];
        }
        global().get(CAFPE_constants.LAG_MAX).setJSCData(
                new JSCInt("lagmax", lagmax));
        setXGridLag();
        setYGridLag();
        setXconstTable();
        setXconstAllTable();

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
        getGridVector().addActionListener(ivjEventHandler);
        getLagGrid1().addActionListener(ivjEventHandler);
        getLagGrid2().addActionListener(ivjEventHandler);
        getEditOptLags().addActionListener(ivjEventHandler);
        getPlotCI().addActionListener(ivjEventHandler);
        addComponentListener(ivjEventHandler);
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
            setSize(760, 435);
            add(getJTabbedPane1(), BorderLayout.CENTER);
            initConnections();
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}

        global().get(CAFPE_constants.OPT_LAGS).addSymbolListener(
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
    private void plotCI_ActionEvents() {
        setYGridLag();
        setXconstTable();
    }

    /**
     * Comment
     */
    private void setGridVectorFromOptLags() {
        // set the selected lags
        String grid = null;

        if (global().get(CAFPE_constants.OPT_LAGS).isEmpty()
                || global().get(CAFPE_constants.CRMIN).isEmpty())
            return;

        getGridVector().removeAllItems();

        lags = global().get(CAFPE_constants.OPT_LAGS).getJSCNArray().intArray();
        // check whether there are only 0 in a row
        for (int i = 0; i < lags.length; i++) {
            int check = 0;
            for (int j = 0; j < lags[0].length; j++)
                check += lags[i][j];
            if (check == 0) {
                StdMessages.errorSpecification("Row " + (i + 1)
                        + " contains only 0.");
                global().get(CAFPE_constants.OPT_LAGS).getJSCNArray().setValAt(
                        1, i, 0);

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
        for (int i = 0; i < global().get(CAFPE_constants.D_MAX).getJSCInt()
                .intVal(); i++)
            sel += (global().get(CAFPE_constants.CRMIN).getJSCNArray()
                    .doubleAt(0, i) > 0) ? 1 : 0;
        if (sel - 1 >= 0)
            getGridVector().setSelectedIndex(sel - 1);
        else
            getGridVector().setSelectedIndex(0);

    }

    /**
     * This function sets the values where the estimated function is evaluated.
     */
    private void setXconstAllTable() {

        String[] condLags = new String[getCurrentNonzeroLags()];
        double[] xconstall = new double[condLags.length];
        Arrays.fill(xconstall, global().get(CAFPE_constants.MEAN_Y)
                .getJSCNumber().doubleVal());
        for (int i = 0, j = 0; i < lags[0].length; i++) {
            String comp = new Double(
                    lags[getGridVector().getSelectedIndex()][i]).intValue()
                    + "";
            if (lags[getGridVector().getSelectedIndex()][i] > 0) {
                if (j < condLags.length) {
                    condLags[j] = "lag " + comp;
                    j++;
                }
            }

        }

        getDataTableScrollPane3().setRowHeaderData(
                new JSCSArray("rowheader", condLags));
        global().get(CAFPE_constants.XCONST_ALL).setJSCData(
                new JSCNArray("xconstall", xconstall));
    }

    /**
     * This function sets the values where 3 dimensional plot is conditioned on.
     */
    private void setXconstTable() {
        // the map contains the values in the table an remembers them
        // if the selection changes
        int isSel = (getPlotCI().isSelected()) ? 1 : 0;

        // check whether a selection makes sense and set the DataTable
        if (getCurrentNonzeroLags() < 3 - isSel) {
            getDataTableScrollPane1().setRowHeaderData(
                    new JSCSArray("rowheader", "no lags"));
            global().get(CAFPE_constants.XCONST_FAC).setJSCData(
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
                    j++;
                }
            }
        }

        getDataTableScrollPane1().setRowHeaderData(
                new JSCSArray("rowheader", condLags));
        global().get(CAFPE_constants.XCONST_FAC).setJSCData(
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

        if (lagCount < 2 || getPlotCI().isSelected()) {
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
    private void this_componentShown() {
        getJTabbedPane1().setSelectedIndex(0);

        double upper = global().get(CAFPE_constants.MAX_LAG).getJSCInt()
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
            jComboBox.setPreferredSize(new java.awt.Dimension(31, 24));
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
    private javax.swing.JLabel getJLabel9() {
        if (jLabel == null) {
            jLabel = new javax.swing.JLabel();
            jLabel.setText("Rotate surface plot");
            jLabel.setPreferredSize(new java.awt.Dimension(108, 24));
        }
        return jLabel;
    }
}