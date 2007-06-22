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
import com.jstatcom.model.JSCNumber;
import com.jstatcom.model.JSCString;
import com.jstatcom.model.ModelPanel;
import com.jstatcom.model.SymbolEvent;
import com.jstatcom.model.SymbolListener;
import com.jstatcom.ts.TSDateRange;
import com.jstatcom.ts.TSSel;
import com.jstatcom.util.UMatrix;

import de.jmulti.proc.CAFPESelCall;

/**
 * Panel to specify and execute the nonparametric lag selection via CAFPE.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
final class CAFPE_sel extends ModelPanel {
    private static final Logger log = Logger.getLogger(CAFPE_sel.class);

    private static final String[] modelKeys = new String[] {
            "Heteroskedastic nonlinear autoregressive (NAR model)",
            "Seasonal nonlinear autoregressive (SNAR) model",
            "Seasonal dummy nonlinear autoregressive (SDNAR) model",
            "Seasonal shift nonlinear autoregressive (SHNAR) model",
            "Linear autoregressive (AR) model",
            "Periodic autoregressive (PAR) model",
            "Seasonal dummy linear autoregressive (SDAR) model",
            "Seasonal shift linear autoregressive (SHAR) model",
            "Seasonal nonlinear autoregressive (SNAR12) model",
            "Seasonal shift nonlinear autoregressive (SHNAR12) model",
            "Periodic autoregressive (PAR12) model",
            "Seasonal shift linear autoregressive (SHAR12) model", };

    static final String[] searchStratKeys = new String[] { "full",
            "directed/sequential" };

    static final String[] critKeys = new String[] { "CAFPE", "AFPE", "FPE",
            "SC", "HQ", "AIC", };

    private static final HashMap<String, String> modelMap = new HashMap<String, String>();

    static final HashMap<String, String> searchMap = new HashMap<String, String>();

    static final HashMap<String, String> critMap = new HashMap<String, String>();
    static {
        // mapping of the keys in the select combobox to the
        // names used by the program
        modelMap.put(modelKeys[0], "NAR");
        modelMap.put(modelKeys[1], "SNAR");
        modelMap.put(modelKeys[2], "SDNAR");
        modelMap.put(modelKeys[3], "SHNAR");
        modelMap.put(modelKeys[4], "NAR");
        modelMap.put(modelKeys[5], "SNAR");
        modelMap.put(modelKeys[6], "SDNAR");
        modelMap.put(modelKeys[7], "SHNAR");
        modelMap.put(modelKeys[8], "SNAR12");
        modelMap.put(modelKeys[9], "SHNAR12");
        modelMap.put(modelKeys[10], "SNAR12");
        modelMap.put(modelKeys[11], "SHNAR12");
        critMap.put(critKeys[0], "lqcafpe");
        critMap.put(critKeys[1], "lqafpe");
        critMap.put(critKeys[2], "arfpe");
        critMap.put(critKeys[3], "arsc");
        critMap.put(critKeys[4], "arhq");
        critMap.put(critKeys[5], "araic");
        searchMap.put(searchStratKeys[0], "full");
        searchMap.put(searchStratKeys[1], "directed");
    }

    private TSSel ivjCAFPESelect = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private JButton ivjExecute = null;

    private ResultField ivjResultField = null;

    private JPanel ivjJPanel1 = null;

    private JTabbedPane ivjJTabbedPane1 = null;

    private JPanel ivjSelectTab = null;

    private JLabel ivjJLabel1 = null;

    private NumSelector ivjDataTextField1 = null;

    private NumSelector ivjDataTextField2 = null;

    private JLabel ivjJLabel2 = null;

    private JComboBox ivjSearchMeth = null;

    private JComboBox ivjSelCrit = null;

    private JLabel ivjJLabel3 = null;

    private JLabel ivjJLabel4 = null;

    private JLabel ivjJLabel5 = null;

    private JComboBox ivjModel = null;

    private JCheckBox ivjPrintLog = null;

    private JLabel ivjJLabel = null;

    private JComboBox ivjStartStrat = null;

    private JCheckBox ivjStandData = null;

    private javax.swing.JLabel jLabel = null;

    class IvjEventHandler implements java.awt.event.ActionListener,
            java.awt.event.ComponentListener, java.beans.PropertyChangeListener {

        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == CAFPE_sel.this.getExecute())
                connEtoC1();
            if (e.getSource() == CAFPE_sel.this.getSelCrit())
                connEtoC2();
            if (e.getSource() == CAFPE_sel.this.getModel())
                connEtoC5();
            if (e.getSource() == CAFPE_sel.this.getStandData())
                connEtoC6();
        };

        public void componentHidden(java.awt.event.ComponentEvent e) {
            if (e.getSource() == CAFPE_sel.this)
                connEtoC3(e);
        };

        public void componentMoved(java.awt.event.ComponentEvent e) {
        };

        public void componentResized(java.awt.event.ComponentEvent e) {
        };

        public void componentShown(java.awt.event.ComponentEvent e) {
            if (e.getSource() == CAFPE_sel.this)
                connEtoC7(e);
        };

        public void propertyChange(java.beans.PropertyChangeEvent evt) {
            if (evt.getSource() == CAFPE_sel.this.getCAFPESelect()
                    && (evt.getPropertyName().equals("selectionChanged")))
                connEtoC4(evt);
        };
    };

    /**
     * CAFPE constructor comment.
     */
    public CAFPE_sel() {
        super();
        initialize();
    }

    /**
     * Comment
     */
    private void cAFPE_ComponentHidden() {
        // here we create all the data that is needed by Cafpe_est
        global().get(CAFPE_constants.MOD_METH)
                .setJSCData(
                        new JSCString("mod", modelMap.get(getModel()
                                .getSelectedItem())));
        global().get(CAFPE_constants.SEARCH_METH).setJSCData(
                new JSCString("search", searchMap.get(getSearchMeth()
                        .getSelectedItem())));
        global().get(CAFPE_constants.SELCRIT).setJSCData(
                new JSCString("crit", critMap.get(getSelCrit()
                        .getSelectedItem())));
        global().get(CAFPE_constants.STARTSTRAT).setJSCData(
                new JSCString("startstrat", getStartStrat().getSelectedItem()
                        .toString()));
        global().get(CAFPE_constants.IS_STAND).setJSCData(
                new JSCInt("stand", getStandData().isSelected()));
    }

    /**
     * Comment
     */
    private void cAFPE_ComponentShown() {
        getJTabbedPane1().setSelectedIndex(0);
    }

    /**
     * Comment
     */
    private void cAFPESelect_SelectionChanged() {
        removeDataObjects();
        computeYMean();
    }

    /**
     * Computes the means of the selected endogenous variable and takes
     * standardization into account. Sets the MEAN_Y variable.
     */
    private void computeYMean() {
        if (!global().get(CAFPE_constants.Y).isEmpty()) {
            double[][] yData = global().get(CAFPE_constants.Y).getJSCNArray()
                    .doubleArray();
            if (getStandData().isSelected())
                yData = UMatrix.standardize(yData);
            global().get(CAFPE_constants.MEAN_Y).setJSCData(
                    new JSCNumber("mean", UMatrix.meanc(yData)[0]));
        } else
            global().get(CAFPE_constants.MEAN_Y).clear();
    }

    /**
     * connEtoC1: (Execute.action. --> CAFPE.execute_ActionEvents()V)
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
     * connEtoC2: (SelCrit.action. --> CAFPE_sel.selCrit_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC2() {
        try {
            // user code begin {1}
            // user code end
            this.selCrit_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC3:
     * (CAFPE.component.componentHidden(java.awt.event.ComponentEvent) -->
     * CAFPE_sel.cAFPE_ComponentHidden()V)
     * 
     * @param arg1
     *            java.awt.event.ComponentEvent
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC3(java.awt.event.ComponentEvent arg1) {
        try {
            // user code begin {1}
            // user code end
            this.cAFPE_ComponentHidden();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC4: (CAFPESelect.selectionChanged -->
     * CAFPE_sel.cAFPESelect_SelectionChanged()V)
     * 
     * @param arg1
     *            java.beans.PropertyChangeEvent
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC4(java.beans.PropertyChangeEvent arg1) {
        try {
            // user code begin {1}
            // user code end
            this.cAFPESelect_SelectionChanged();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC5: (Model.action. --> CAFPE_sel.model_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC5() {
        try {
            // user code begin {1}
            // user code end
            this.model_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC6: (StandData.action. --> CAFPE_sel.standData_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC6() {
        try {
            // user code begin {1}
            // user code end
            this.standData_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC7: (CAFPE.component.componentShown(java.awt.event.ComponentEvent)
     * --> CAFPE_sel.cAFPE_ComponentShown()V)
     * 
     * @param arg1
     *            java.awt.event.ComponentEvent
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC7(java.awt.event.ComponentEvent arg1) {
        try {
            // user code begin {1}
            // user code end
            this.cAFPE_ComponentShown();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * Execute the nonparametric model selection with CAFPE.
     */
    private void execute_ActionEvents() {
        if (global().get(CAFPE_constants.Y).isEmpty()) {
            StdMessages.infoNothingSelected("Please select a series.");
            return;
        }
        TSDateRange range = global().get(CAFPE_constants.DRANGE).getJSCDRange()
                .getTSDateRange();
        if (range.numOfObs() < 2 * getMaxLag().getIntNumber()) {
            StdMessages
                    .infoNothingSelected("Not enough observations in selected series.");
            return;
        }
        final File outFile = new File(JSCConstants.getSystemTemp()
                + File.separator + CAFPE_constants.OUT_FILE_SEL);
        PCall job = new CAFPESelCall(global().get(CAFPE_constants.Y)
                .getJSCNArray(), new JSCNArray("res", 0), global().get(
                CAFPE_constants.Y_NAME).getJSCString().string(), range
                .lowerBound(), getMaxLag().getIntNumber(), getDMax()
                .getIntNumber(), outFile, modelMap.get(
                getModel().getSelectedItem()).toString(), searchMap.get(
                getSearchMeth().getSelectedItem()).toString(), critMap.get(
                getSelCrit().getSelectedItem()).toString(), getStartStrat()
                .getSelectedItem().toString(), getPrintLog().isSelected(),
                getStandData().isSelected(), false);
        job.setSymbolTable(global());
        job.setOutHolder(getResultField());
        job.addPCallListener(new PCallAdapter() {

            public void success() {
                CAFPE_sel.this.getJTabbedPane1().setSelectedIndex(1);
            }

            public void finished(PCall pCall) {
                outFile.delete();
            }
        });
        job.execute();
    }

    /**
     * Return the CAFPESelect property value.
     * 
     * @return TSSelector
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    TSSel getCAFPESelect() {
        if (ivjCAFPESelect == null) {
            try {
                ivjCAFPESelect = new TSSel();
                ivjCAFPESelect.setName("CAFPESelect");
                ivjCAFPESelect.setDateRangeName(CAFPE_constants.DRANGE.name);
                ivjCAFPESelect
                        .setEndogenousStringsName(CAFPE_constants.Y_NAME.name);
                ivjCAFPESelect.setDeterministicEnabled(false);
                ivjCAFPESelect.setExogenousEnabled(false);
                ivjCAFPESelect.setEndogenousDataName(CAFPE_constants.Y.name);
                ivjCAFPESelect.setSelectionMode(0);
                // just for making description shown immediately
                global().get(CAFPE_constants.DRANGE);
                global().get(CAFPE_constants.Y_NAME);
                global().get(CAFPE_constants.Y);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjCAFPESelect;
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
                ivjExecute.setPreferredSize(new java.awt.Dimension(200, 25));
                ivjExecute.setText("Execute Lag Selection");
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

    private NumSelector getMaxLag() {
        if (ivjDataTextField1 == null) {
            try {
                ivjDataTextField1 = new NumSelector();
                ivjDataTextField1.setPreferredSize(new java.awt.Dimension(50,
                        20));
                ivjDataTextField1
                        .setMinimumSize(new java.awt.Dimension(50, 20));
                ivjDataTextField1.setNumber(5.0);
                ivjDataTextField1.setRangeExpr("[" + CAFPE_constants.D_MAX.name
                        + ",50]");
                ivjDataTextField1.setIntType(true);
                ivjDataTextField1.setSymbolName(CAFPE_constants.MAX_LAG.name);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjDataTextField1;
    }

    private NumSelector getDMax() {
        if (ivjDataTextField2 == null) {
            try {
                ivjDataTextField2 = new NumSelector();
                ivjDataTextField2.setPreferredSize(new java.awt.Dimension(50,
                        20));
                ivjDataTextField2
                        .setMinimumSize(new java.awt.Dimension(50, 20));
                ivjDataTextField2.setNumber(2.0);
                ivjDataTextField2.setRangeExpr("[1,"
                        + CAFPE_constants.MAX_LAG.name + "]");
                ivjDataTextField2.setIntType(true);
                ivjDataTextField2.setSymbolName(CAFPE_constants.D_MAX.name);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjDataTextField2;
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
                ivjJLabel.setPreferredSize(new java.awt.Dimension(73, 20));
                ivjJLabel.setText("Selection of start values");
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
                ivjJLabel1.setPreferredSize(new java.awt.Dimension(73, 20));
                ivjJLabel1.setText("Largest candidate lag");
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
                ivjJLabel2.setPreferredSize(new java.awt.Dimension(182, 20));
                ivjJLabel2.setText("Maximum number of lags");
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
                ivjJLabel3.setPreferredSize(new java.awt.Dimension(73, 20));
                ivjJLabel3.setText("Search method over set of candidate lags");
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
                ivjJLabel4.setPreferredSize(new java.awt.Dimension(73, 20));
                ivjJLabel4.setText("Lag selection criterion");
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
                ivjJLabel5.setPreferredSize(new java.awt.Dimension(73, 20));
                ivjJLabel5.setText("Model Choice and Data Preparation");
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
                constraintsExecute.gridx = 0;
                constraintsExecute.gridy = 15;
                constraintsExecute.gridwidth = 2;
                constraintsExecute.weightx = 1.0;
                constraintsExecute.weighty = 1.0;
                constraintsExecute.insets = new java.awt.Insets(0, 10, 5, 10);
                java.awt.GridBagConstraints constraintsGaussDataTextField1 = new java.awt.GridBagConstraints();
                constraintsGaussDataTextField1.gridx = 0;
                constraintsGaussDataTextField1.gridy = 7;
                constraintsGaussDataTextField1.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsGaussDataTextField1.insets = new java.awt.Insets(5,
                        40, 0, 0);
                java.awt.GridBagConstraints constraintsGaussDataTextField2 = new java.awt.GridBagConstraints();
                constraintsGaussDataTextField2.gridx = 0;
                constraintsGaussDataTextField2.gridy = 8;
                constraintsGaussDataTextField2.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsGaussDataTextField2.insets = new java.awt.Insets(5,
                        40, 0, 0);
                java.awt.GridBagConstraints constraintsJLabel1 = new java.awt.GridBagConstraints();
                constraintsJLabel1.gridx = 1;
                constraintsJLabel1.gridy = 7;
                constraintsJLabel1.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel1.anchor = java.awt.GridBagConstraints.WEST;
                constraintsJLabel1.insets = new java.awt.Insets(5, 10, 0, 0);
                java.awt.GridBagConstraints constraintsJLabel2 = new java.awt.GridBagConstraints();
                constraintsJLabel2.gridx = 1;
                constraintsJLabel2.gridy = 8;
                constraintsJLabel2.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel2.anchor = java.awt.GridBagConstraints.WEST;
                constraintsJLabel2.insets = new java.awt.Insets(5, 10, 0, 0);
                java.awt.GridBagConstraints constraintsSearchMeth = new java.awt.GridBagConstraints();
                constraintsSearchMeth.gridx = 0;
                constraintsSearchMeth.gridy = 10;
                constraintsSearchMeth.gridwidth = 2;
                constraintsSearchMeth.anchor = java.awt.GridBagConstraints.WEST;
                constraintsSearchMeth.insets = new java.awt.Insets(3, 40, 0, 0);
                java.awt.GridBagConstraints constraintsSelCrit = new java.awt.GridBagConstraints();
                constraintsSelCrit.gridx = 0;
                constraintsSelCrit.gridy = 6;
                constraintsSelCrit.gridwidth = 2;
                constraintsSelCrit.anchor = java.awt.GridBagConstraints.WEST;
                constraintsSelCrit.insets = new java.awt.Insets(3, 40, 0, 10);
                java.awt.GridBagConstraints constraintsModel = new java.awt.GridBagConstraints();
                constraintsModel.gridx = 0;
                constraintsModel.gridy = 2;
                constraintsModel.gridwidth = 2;
                constraintsModel.anchor = java.awt.GridBagConstraints.WEST;
                constraintsModel.insets = new java.awt.Insets(3, 40, 0, 10);
                java.awt.GridBagConstraints constraintsJLabel3 = new java.awt.GridBagConstraints();
                constraintsJLabel3.gridx = 0;
                constraintsJLabel3.gridy = 9;
                constraintsJLabel3.gridwidth = 2;
                constraintsJLabel3.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel3.anchor = java.awt.GridBagConstraints.WEST;
                constraintsJLabel3.insets = new java.awt.Insets(5, 40, 0, 0);
                java.awt.GridBagConstraints constraintsJLabel4 = new java.awt.GridBagConstraints();
                constraintsJLabel4.gridx = 0;
                constraintsJLabel4.gridy = 5;
                constraintsJLabel4.gridwidth = 2;
                constraintsJLabel4.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel4.anchor = java.awt.GridBagConstraints.WEST;
                constraintsJLabel4.insets = new java.awt.Insets(5, 40, 0, 0);
                java.awt.GridBagConstraints constraintsJLabel5 = new java.awt.GridBagConstraints();
                constraintsJLabel5.gridx = 0;
                constraintsJLabel5.gridy = 0;
                constraintsJLabel5.gridwidth = 2;
                constraintsJLabel5.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel5.anchor = java.awt.GridBagConstraints.WEST;
                constraintsJLabel5.insets = new java.awt.Insets(0, 5, 0, 0);
                java.awt.GridBagConstraints constraintsPrintLog = new java.awt.GridBagConstraints();
                constraintsPrintLog.gridx = 0;
                constraintsPrintLog.gridy = 14;
                constraintsPrintLog.gridwidth = 2;
                constraintsPrintLog.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsPrintLog.anchor = java.awt.GridBagConstraints.WEST;
                constraintsPrintLog.insets = new java.awt.Insets(10, 5, 0, 0);
                java.awt.GridBagConstraints constraintsJLabel = new java.awt.GridBagConstraints();
                constraintsJLabel.gridx = 0;
                constraintsJLabel.gridy = 11;
                constraintsJLabel.gridwidth = 2;
                constraintsJLabel.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel.anchor = java.awt.GridBagConstraints.WEST;
                constraintsJLabel.insets = new java.awt.Insets(5, 40, 0, 0);
                java.awt.GridBagConstraints constraintsStartStrat = new java.awt.GridBagConstraints();
                constraintsStartStrat.gridx = 0;
                constraintsStartStrat.gridy = 12;
                constraintsStartStrat.gridwidth = 2;
                constraintsStartStrat.anchor = java.awt.GridBagConstraints.WEST;
                constraintsStartStrat.insets = new java.awt.Insets(3, 40, 0, 0);
                java.awt.GridBagConstraints constraintsStandData = new java.awt.GridBagConstraints();
                java.awt.GridBagConstraints consGridBagConstraints2 = new java.awt.GridBagConstraints();
                java.awt.GridBagConstraints consGridBagConstraints1 = new java.awt.GridBagConstraints();
                consGridBagConstraints2.gridy = 4;
                consGridBagConstraints2.gridx = 0;
                consGridBagConstraints2.insets = new java.awt.Insets(10, 5, 0,
                        0);
                constraintsSelCrit.fill = java.awt.GridBagConstraints.NONE;
                constraintsModel.fill = java.awt.GridBagConstraints.NONE;
                consGridBagConstraints1.gridy = 1;
                consGridBagConstraints1.gridx = 0;
                consGridBagConstraints1.insets = new java.awt.Insets(5, 40, 0,
                        0);
                constraintsStandData.gridx = 0;
                constraintsStandData.gridy = 3;
                constraintsStandData.gridwidth = 2;
                constraintsStandData.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsStandData.anchor = java.awt.GridBagConstraints.WEST;
                constraintsStandData.insets = new java.awt.Insets(5, 40, 0, 0);
                consGridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
                consGridBagConstraints1.gridwidth = 2;
                consGridBagConstraints2.fill = java.awt.GridBagConstraints.HORIZONTAL;
                consGridBagConstraints2.gridwidth = 2;
                ivjJPanel1.add(getJLabel5(), constraintsJLabel5);
                ivjJPanel1.add(getExecute(), constraintsExecute);
                ivjJPanel1.add(getMaxLag(), constraintsGaussDataTextField1);
                ivjJPanel1.add(getDMax(), constraintsGaussDataTextField2);
                ivjJPanel1.add(getJLabel1(), constraintsJLabel1);
                ivjJPanel1.add(getJLabel2(), constraintsJLabel2);
                ivjJPanel1.add(getSearchMeth(), constraintsSearchMeth);
                ivjJPanel1.add(getSelCrit(), constraintsSelCrit);
                ivjJPanel1.add(getJLabel3(), constraintsJLabel3);
                ivjJPanel1.add(getJLabel4(), constraintsJLabel4);
                ivjJPanel1.add(getPrintLog(), constraintsPrintLog);
                ivjJPanel1.add(getJLabel(), constraintsJLabel);
                ivjJPanel1.add(getStartStrat(), constraintsStartStrat);
                ivjJPanel1.add(getStandData(), constraintsStandData);
                ivjJPanel1.add(getJLabel6(), consGridBagConstraints1);
                ivjJPanel1.add(getJLabel12(), consGridBagConstraints2);
                TitledBorder title = new TitledBorder(new BevelBorder(
                        BevelBorder.LOWERED), "Specify Lag Selection",
                        TitledBorder.RIGHT, TitledBorder.TOP);
                ivjJPanel1.setBorder(title);
                // user code end
                ivjJPanel1.add(getModel(), constraintsModel);
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
                ivjJTabbedPane1.insertTab("Specify Lag Selection", null,
                        getSelectTab(), null, 0);
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
     * Return the Model property value.
     * 
     * @return javax.swing.JComboBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JComboBox getModel() {
        if (ivjModel == null) {
            try {
                ivjModel = new javax.swing.JComboBox();
                ivjModel.setName("Model");
                ivjModel.setPreferredSize(new java.awt.Dimension(400,23));
                // user code begin {1}
                ivjModel.setMinimumSize(new java.awt.Dimension(200, 23));
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjModel;
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

    /**
     * Return the SearchMeth property value.
     * 
     * @return javax.swing.JComboBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JComboBox getSearchMeth() {
        if (ivjSearchMeth == null) {
            try {
                ivjSearchMeth = new javax.swing.JComboBox();
                ivjSearchMeth.setName("SearchMeth");
                // user code begin {1}
                ivjSearchMeth.setMinimumSize(new java.awt.Dimension(200, 23));
                // user code end
                ivjSearchMeth.setPreferredSize(new java.awt.Dimension(200, 23));
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSearchMeth;
    }

    /**
     * Return the SelCrit property value.
     * 
     * @return javax.swing.JComboBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JComboBox getSelCrit() {
        if (ivjSelCrit == null) {
            try {
                ivjSelCrit = new javax.swing.JComboBox();
                ivjSelCrit.setName("SelCrit");
                ivjSelCrit.setPreferredSize(new java.awt.Dimension(200, 23));
                // user code begin {1}
                ivjSelCrit.setMinimumSize(new java.awt.Dimension(200, 23));
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSelCrit;
    }

    /**
     * Return the Page property value.
     * 
     * @return javax.swing.JPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JPanel getSelectTab() {
        if (ivjSelectTab == null) {
            try {
                ivjSelectTab = new javax.swing.JPanel();
                ivjSelectTab.setName("SelectTab");
                ivjSelectTab.setLayout(new java.awt.BorderLayout());
                getSelectTab().add(getCAFPESelect(), "East");
                getSelectTab().add(getJPanel1(), "Center");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSelectTab;
    }

    /**
     * Return the StandData property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getStandData() {
        if (ivjStandData == null) {
            try {
                ivjStandData = new javax.swing.JCheckBox();
                ivjStandData.setName("StandData");
                ivjStandData.setText("Standardize data");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjStandData;
    }

    /**
     * Return the StartStrat property value.
     * 
     * @return javax.swing.JComboBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JComboBox getStartStrat() {
        if (ivjStartStrat == null) {
            try {
                ivjStartStrat = new javax.swing.JComboBox();
                ivjStartStrat.setName("StartStrat");
                ivjStartStrat.setPreferredSize(new java.awt.Dimension(200, 23));
                // user code begin {1}
                ivjStartStrat.setMinimumSize(new java.awt.Dimension(200, 23));
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjStartStrat;
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
        getSelCrit().addActionListener(ivjEventHandler);
        this.addComponentListener(ivjEventHandler);
        getModel().addActionListener(ivjEventHandler);
        getStandData().addActionListener(ivjEventHandler);
        getCAFPESelect().addPropertyChangeListener(ivjEventHandler);
    }

    /**
     * Initialize the class.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initialize() {
        try {
            // user code begin {1}
            // user code end
            setName("CAFPE");
            setLayout(new BorderLayout());
            setSize(720, 500);
            add(getJTabbedPane1(), BorderLayout.CENTER);
            initConnections();
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}
        getSearchMeth().addItem(searchStratKeys[0]);
        getSearchMeth().addItem(searchStratKeys[1]);
        getSelCrit().addItem(critKeys[0]);
        getSelCrit().addItem(critKeys[1]);
        getSelCrit().addItem(critKeys[2]);
        getSelCrit().addItem(critKeys[3]);
        getSelCrit().addItem(critKeys[4]);
        getSelCrit().addItem(critKeys[5]);
        getModel().addItem(modelKeys[0]);
        getStartStrat().addItem("same");
        getStartStrat().addItem("different");
        getStartStrat().setSelectedIndex(0);
        global().get(CAFPE_constants.DRANGE).addSymbolListener(
                new SymbolListener() {

                    public void valueChanged(SymbolEvent evt) {
                        getModel().removeAllItems();
                        getModel().addItem(modelKeys[0]);
                        getModel().addItem(modelKeys[4]);
                        if (evt.getSource().isEmpty())
                            return;
                        int period = evt.getSource().getJSCDRange()
                                .getTSDateRange().subPeriodicity();
                        if (period == 4) {
                            getModel().addItem(modelKeys[1]);
                            getModel().addItem(modelKeys[2]);
                            getModel().addItem(modelKeys[3]);
                            getModel().addItem(modelKeys[5]);
                            getModel().addItem(modelKeys[6]);
                            getModel().addItem(modelKeys[7]);
                        }
                        if (period == 12) {
                            // seasonal dummy linear not for 12
                            getModel().addItem(modelKeys[8]);
                            getModel().addItem(modelKeys[9]);
                            getModel().addItem(modelKeys[10]);
                            getModel().addItem(modelKeys[11]);
                        }
                    }
                });
        global().get(CAFPE_constants.D_MAX).addSymbolListener(
                new SymbolListener() {

                    public void valueChanged(SymbolEvent evt) {
                        global().get(CAFPE_constants.D_MAX_VOLAT).setJSCData(
                                evt.getSource().getJSCData());
                    }
                });
        global().get(CAFPE_constants.MAX_LAG).addSymbolListener(
                new SymbolListener() {

                    public void valueChanged(SymbolEvent evt) {
                        global().get(CAFPE_constants.MAX_LAG_VOLAT).setJSCData(
                                evt.getSource().getJSCData());
                    }
                });
        // user code end
    }

    /**
     * Comment
     */
    private void model_ActionEvents() {
        if (getModel().getSelectedIndex() < 0)
            return;
        removeDataObjects();
        getSelCrit().removeAllItems();
        Object item = getModel().getSelectedItem();
        // nonlinear model selected
        if (item == modelKeys[0] || item == modelKeys[1]
                || item == modelKeys[2] || item == modelKeys[3]
                || item == modelKeys[8] || item == modelKeys[9]) {
            getSelCrit().addItem(critKeys[0]);
            getSelCrit().addItem(critKeys[1]);
        } else { // linear model
            getSelCrit().addItem(critKeys[2]);
            getSelCrit().addItem(critKeys[3]);
            getSelCrit().addItem(critKeys[4]);
            getSelCrit().addItem(critKeys[5]);
        }
    }

    /**
     * Comment
     */
    private void removeDataObjects() {
        global().get(CAFPE_constants.OPT_LAGS).clear();
        global().get(CAFPE_constants.RESID_EST).clear();
    }

    /**
     * Comment
     */
    private void selCrit_ActionEvents() {
        if (getSelCrit().getSelectedIndex() < 0)
            return;
        removeDataObjects();
        global().get(CAFPE_constants.SELCRIT).setJSCData(
                new JSCString("crit", critMap.get(
                        getSelCrit().getSelectedItem()).toString()));
    }

    /**
     * Comment
     */
    private void standData_ActionEvents() {
        removeDataObjects();
        computeYMean();
    }

    /**
     * This method initializes jLabel
     * 
     * @return javax.swing.JLabel
     */
    int verb = (getPrintLog().isSelected()) ? 1 : 0;

    private javax.swing.JLabel jLabel1 = null;

    private javax.swing.JLabel getJLabel6() {
        if (jLabel == null) {
            jLabel = new javax.swing.JLabel();
            jLabel.setText("Select Model");
            jLabel.setPreferredSize(new java.awt.Dimension(73, 20));
        }
        return jLabel;
    }

    /**
     * This method initializes jLabel1
     * 
     * @return javax.swing.JLabel
     */
    private javax.swing.JLabel getJLabel12() {
        if (jLabel1 == null) {
            jLabel1 = new javax.swing.JLabel();
            jLabel1.setText("Lag Selection");
            jLabel1.setPreferredSize(new java.awt.Dimension(77, 20));
        }
        return jLabel1;
    }

    /**
     * Gets an array indicating the selection of model and lags.
     * 
     * @return
     */
    JSCNArray getMSel() {

        return new JSCNArray("MSel", new double[] {
                getModel().getSelectedIndex(),
                getStandData().isSelected() ? 1 : 0,
                getSelCrit().getSelectedIndex(), getMaxLag().getNumber(),
                getDMax().getNumber(), getSearchMeth().getSelectedIndex(),
                getStartStrat().getSelectedIndex() });
    }

    /**
     * Sets the selection from the argument.
     * 
     * @param mSel
     */
    void setMSel(JSCNArray mSel) {
        if (mSel == null || mSel.isEmpty() || mSel.rows() < 7)
            return;
        getModel().setSelectedIndex(mSel.intAt(0, 0));
        getStandData().setSelected(mSel.intAt(1, 0) != 0);
        getSelCrit().setSelectedIndex(mSel.intAt(2, 0));
        getMaxLag().setNumber(mSel.intAt(3, 0));
        getDMax().setNumber(mSel.intAt(4, 0));
        getSearchMeth().setSelectedIndex(mSel.intAt(5, 0));
        getStartStrat().setSelectedIndex(mSel.intAt(6, 0));
    }
}