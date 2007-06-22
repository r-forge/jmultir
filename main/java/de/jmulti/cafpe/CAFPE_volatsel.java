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

import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
import com.jstatcom.model.JSCString;
import com.jstatcom.model.ModelPanel;
import com.jstatcom.ts.TSDateRange;

import de.jmulti.proc.CAFPESelCall;

/**
 * Panel to specify and execute the nonparametric lag selection for the
 * volatility estimation.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */

final class CAFPE_volatsel extends ModelPanel {
    private static final Logger log = Logger.getLogger(CAFPE_volatsel.class);

    private JPanel ivjJPanel1 = null;

    private ResultField ivjResultField = null;

    private NumSelector ivjDataTextField1 = null;

    private NumSelector ivjDataTextField2 = null;

    private JLabel ivjJLabel1 = null;

    private JLabel ivjJLabel2 = null;

    private JLabel ivjJLabel4 = null;

    private JComboBox ivjSelCrit = null;

    private JLabel ivjJLabel = null;

    private JLabel ivjJLabel3 = null;

    private JLabel ivjJLabel5 = null;

    private JComboBox ivjModel = null;

    private JCheckBox ivjPrintLog = null;

    private JComboBox ivjSearchMeth = null;

    private JCheckBox ivjStandData = null;

    private JComboBox ivjStartStrat = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private JButton ivjExecute = null;

    private JCheckBox ivjWhiteNoise = null;

    class IvjEventHandler implements java.awt.event.ActionListener,
            java.awt.event.ComponentListener {

        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == CAFPE_volatsel.this.getExecute())
                connEtoC1();
            if (e.getSource() == CAFPE_volatsel.this.getModel())
                connEtoC4();
            if (e.getSource() == CAFPE_volatsel.this.getSelCrit())
                connEtoC3();
            if (e.getSource() == CAFPE_volatsel.this.getStandData())
                connEtoC5();
        };

        public void componentHidden(java.awt.event.ComponentEvent e) {
            if (e.getSource() == CAFPE_volatsel.this)
                connEtoC2(e);
        };

        public void componentMoved(java.awt.event.ComponentEvent e) {
        };

        public void componentResized(java.awt.event.ComponentEvent e) {
        };

        public void componentShown(java.awt.event.ComponentEvent e) {
        };
    };

    /**
     * CAFPE_forecast constructor comment.
     */
    public CAFPE_volatsel() {
        super();
        initialize();
    }

    /**
     * Comment
     */
    private void cAFPE_volatsel_ComponentHidden() {

        // here we create all the data that is needed by Cafpe_est
        // this is easier here than in cafp_sel, because we have only
        // nonparametric models

        global().get(CAFPE_constants.MOD_METH_VOLAT).setJSCData(
                new JSCString("mod", getModel().getSelectedItem().toString()
                        + "_RES"));

        global().get(CAFPE_constants.SEARCH_METH_VOLAT).setJSCData(
                new JSCString("search", CAFPE_sel.searchMap.get(getSearchMeth()
                        .getSelectedItem())));

        global().get(CAFPE_constants.SELCRIT_VOLAT).setJSCData(
                new JSCString("selcrit", CAFPE_sel.critMap.get(getSelCrit()
                        .getSelectedItem())));

        global().get(CAFPE_constants.STARTSTRAT_VOLAT).setJSCData(
                new JSCString("start", getStartStrat().getSelectedItem()
                        .toString()));

        global().get(CAFPE_constants.IS_STAND_VOLAT).setJSCData(
                new JSCInt("isstand", getStandData().isSelected()));

    }

    /**
     * connEtoC1: (Execute.action. --> CAFPE_volatsel.execute_ActionEvents()V)
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
     * (CAFPE_volatsel.component.componentHidden(java.awt.event.ComponentEvent)
     * --> CAFPE_volatsel.cAFPE_volatsel_ComponentHidden()V)
     * 
     * @param arg1
     *            java.awt.event.ComponentEvent
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC2(java.awt.event.ComponentEvent arg1) {
        try {
            // user code begin {1}
            // user code end
            this.cAFPE_volatsel_ComponentHidden();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC3: (SelCrit.action. --> CAFPE_volatsel.removeDataObjects()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC3() {
        try {
            // user code begin {1}
            // user code end
            this.removeDataObjects();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC4: (Model.action. --> CAFPE_volatsel.removeDataObjects()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC4() {
        try {
            // user code begin {1}
            // user code end
            this.removeDataObjects();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC5: (StandData.action. --> CAFPE_volatsel.removeDataObjects()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC5() {
        try {
            // user code begin {1}
            // user code end
            this.removeDataObjects();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * Execute the lag selection via CAFPE for volatility.
     */
    private void execute_ActionEvents() {
        boolean isWn = getWhiteNoise().isSelected();

        if (global().get(CAFPE_constants.RESID_EST).isEmpty() && !isWn) {
            StdMessages
                    .errorSpecification("There are no estimated residuals available.\nYou could assume that f(y)=0 and resids=y\nby selecting the available checkbox.");
            return;
        }

        JSCNArray res = (isWn) ? global().get(CAFPE_constants.Y).getJSCNArray()
                : global().get(CAFPE_constants.RESID_EST).getJSCNArray();

        TSDateRange range = global().get(CAFPE_constants.DRANGE).getJSCDRange()
                .getTSDateRange();

        final File outFile = new File(JSCConstants.getSystemTemp()
                + File.separator + CAFPE_constants.OUT_FILE_VOLATSEL);
        PCall job = new CAFPESelCall(global().get(CAFPE_constants.Y)
                .getJSCNArray(), res, global().get(CAFPE_constants.Y_NAME)
                .getJSCString().string(), range.lowerBound(), getMaxLag()
                .getIntNumber(), getDMax().getIntNumber(), outFile, getModel()
                .getSelectedItem()
                + "_RES", CAFPE_sel.searchMap.get(
                getSearchMeth().getSelectedItem()).toString(),
                CAFPE_sel.critMap.get(getSelCrit().getSelectedItem())
                        .toString(), getStartStrat().getSelectedItem()
                        .toString(), getPrintLog().isSelected(), getStandData()
                        .isSelected(), true);

        job.setSymbolTable(global());
        job.setOutHolder(getResultField());

        job.addPCallListener(new PCallAdapter() {

            public void finished(PCall pCall) {
                outFile.delete();
            }
        });
        job.execute();
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
                ivjExecute.setPreferredSize(new java.awt.Dimension(120, 25));
                ivjExecute.setText("Execute");
                ivjExecute.setMinimumSize(new java.awt.Dimension(120, 25));
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
                ivjDataTextField1.setName("DataTextField1");
                ivjDataTextField1.setPreferredSize(new java.awt.Dimension(50,
                        20));
                ivjDataTextField1.setNumber(2.0);
                ivjDataTextField1.setRangeExpr("["
                        + CAFPE_constants.D_MAX_VOLAT.name + ",50]");
                ivjDataTextField1
                        .setSymbolName(CAFPE_constants.MAX_LAG_VOLAT.name);
                ivjDataTextField1.setIntType(true);
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
                ivjDataTextField2.setName("DataTextField2");
                ivjDataTextField2.setPreferredSize(new java.awt.Dimension(50,
                        20));
                ivjDataTextField2.setNumber(2.0);
                ivjDataTextField2.setRangeExpr("[1,"
                        + CAFPE_constants.MAX_LAG_VOLAT.name + "]");
                ivjDataTextField2
                        .setSymbolName(CAFPE_constants.D_MAX_VOLAT.name);
                ivjDataTextField2.setIntType(true);

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
                ivjJLabel.setPreferredSize(new java.awt.Dimension(150, 23));
                ivjJLabel.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
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
                ivjJLabel3.setPreferredSize(new java.awt.Dimension(150, 23));
                ivjJLabel3.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
                ivjJLabel3.setText("Select search method");
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
                ivjJLabel4.setPreferredSize(new java.awt.Dimension(150, 23));
                ivjJLabel4.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
                ivjJLabel4.setText("Select criterion ");
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
                ivjJLabel5.setPreferredSize(new java.awt.Dimension(150, 23));
                ivjJLabel5.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
                ivjJLabel5.setText("Select model");
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
                ivjJPanel1.setPreferredSize(new java.awt.Dimension(0, 170));
                ivjJPanel1.setLayout(new java.awt.GridBagLayout());

                java.awt.GridBagConstraints constraintsGaussDataTextField1 = new java.awt.GridBagConstraints();
                constraintsGaussDataTextField1.gridx = 2;
                constraintsGaussDataTextField1.gridy = 0;
                constraintsGaussDataTextField1.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsGaussDataTextField1.insets = new java.awt.Insets(0,
                        10, 0, 0);
                java.awt.GridBagConstraints constraintsGaussDataTextField2 = new java.awt.GridBagConstraints();
                constraintsGaussDataTextField2.gridx = 2;
                constraintsGaussDataTextField2.gridy = 1;
                constraintsGaussDataTextField2.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsGaussDataTextField2.insets = new java.awt.Insets(5,
                        10, 0, 0);
                java.awt.GridBagConstraints constraintsJLabel1 = new java.awt.GridBagConstraints();
                constraintsJLabel1.gridx = 3;
                constraintsJLabel1.gridy = 0;
                constraintsJLabel1.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel1.anchor = java.awt.GridBagConstraints.WEST;
                constraintsJLabel1.insets = new java.awt.Insets(0, 10, 0, 0);
                java.awt.GridBagConstraints constraintsJLabel2 = new java.awt.GridBagConstraints();
                constraintsJLabel2.gridx = 3;
                constraintsJLabel2.gridy = 1;
                constraintsJLabel2.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel2.anchor = java.awt.GridBagConstraints.WEST;
                constraintsJLabel2.insets = new java.awt.Insets(5, 10, 0, 0);
                java.awt.GridBagConstraints constraintsJLabel4 = new java.awt.GridBagConstraints();
                constraintsJLabel4.gridx = 1;
                constraintsJLabel4.gridy = 1;
                constraintsJLabel4.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel4.anchor = java.awt.GridBagConstraints.WEST;
                constraintsJLabel4.insets = new java.awt.Insets(5, 10, 0, 0);
                getJPanel1().add(getJLabel4(), constraintsJLabel4);

                java.awt.GridBagConstraints constraintsSelCrit = new java.awt.GridBagConstraints();
                constraintsSelCrit.gridx = 0;
                constraintsSelCrit.gridy = 1;
                constraintsSelCrit.anchor = java.awt.GridBagConstraints.WEST;
                constraintsSelCrit.insets = new java.awt.Insets(5, 10, 0, 0);
                java.awt.GridBagConstraints constraintsModel = new java.awt.GridBagConstraints();
                constraintsModel.gridx = 0;
                constraintsModel.gridy = 0;
                constraintsModel.anchor = java.awt.GridBagConstraints.WEST;
                constraintsModel.insets = new java.awt.Insets(0, 10, 0, 0);
                java.awt.GridBagConstraints constraintsJLabel5 = new java.awt.GridBagConstraints();
                constraintsJLabel5.gridx = 1;
                constraintsJLabel5.gridy = 0;
                constraintsJLabel5.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel5.anchor = java.awt.GridBagConstraints.WEST;
                constraintsJLabel5.insets = new java.awt.Insets(0, 10, 0, 0);
                java.awt.GridBagConstraints constraintsSearchMeth = new java.awt.GridBagConstraints();
                constraintsSearchMeth.gridx = 0;
                constraintsSearchMeth.gridy = 2;
                constraintsSearchMeth.anchor = java.awt.GridBagConstraints.WEST;
                constraintsSearchMeth.insets = new java.awt.Insets(5, 10, 0, 0);
                java.awt.GridBagConstraints constraintsJLabel3 = new java.awt.GridBagConstraints();
                constraintsJLabel3.gridx = 1;
                constraintsJLabel3.gridy = 2;
                constraintsJLabel3.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel3.anchor = java.awt.GridBagConstraints.WEST;
                constraintsJLabel3.insets = new java.awt.Insets(5, 10, 0, 0);
                java.awt.GridBagConstraints constraintsJLabel = new java.awt.GridBagConstraints();
                constraintsJLabel.gridx = 1;
                constraintsJLabel.gridy = 3;
                constraintsJLabel.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel.anchor = java.awt.GridBagConstraints.WEST;
                constraintsJLabel.insets = new java.awt.Insets(5, 10, 0, 0);
                java.awt.GridBagConstraints constraintsStartStrat = new java.awt.GridBagConstraints();
                constraintsStartStrat.gridx = 0;
                constraintsStartStrat.gridy = 3;
                constraintsStartStrat.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsStartStrat.insets = new java.awt.Insets(5, 10, 0, 0);
                java.awt.GridBagConstraints constraintsPrintLog = new java.awt.GridBagConstraints();
                constraintsPrintLog.gridx = 2;
                constraintsPrintLog.gridy = 3;
                constraintsPrintLog.gridwidth = 2;
                constraintsPrintLog.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsPrintLog.anchor = java.awt.GridBagConstraints.WEST;
                constraintsPrintLog.insets = new java.awt.Insets(5, 10, 0, 0);
                java.awt.GridBagConstraints constraintsStandData = new java.awt.GridBagConstraints();
                constraintsStandData.gridx = 2;
                constraintsStandData.gridy = 2;
                constraintsStandData.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsStandData.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsStandData.insets = new java.awt.Insets(5, 10, 0, 0);
                java.awt.GridBagConstraints constraintsExecute = new java.awt.GridBagConstraints();
                constraintsExecute.gridx = 3;
                constraintsExecute.gridy = 6;
                constraintsExecute.anchor = java.awt.GridBagConstraints.EAST;
                constraintsExecute.weightx = 1.0;
                constraintsExecute.weighty = 1.0;
                constraintsExecute.insets = new java.awt.Insets(0, 0, 5, 5);
                java.awt.GridBagConstraints constraintsWhiteNoise = new java.awt.GridBagConstraints();
                constraintsWhiteNoise.gridx = 3;
                constraintsWhiteNoise.gridy = 2;
                constraintsWhiteNoise.gridwidth = 2;
                constraintsWhiteNoise.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsWhiteNoise.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsWhiteNoise.insets = new java.awt.Insets(5, 10, 0, 0);
                constraintsStartStrat.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsSearchMeth.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsModel.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsSelCrit.fill = java.awt.GridBagConstraints.HORIZONTAL;
                ivjJPanel1.add(getJLabel5(), constraintsJLabel5);
                ivjJPanel1.add(getMaxLag(), constraintsGaussDataTextField1);
                ivjJPanel1.add(getDMax(), constraintsGaussDataTextField2);
                ivjJPanel1.add(getJLabel1(), constraintsJLabel1);
                ivjJPanel1.add(getJLabel2(), constraintsJLabel2);
                ivjJPanel1.add(getSelCrit(), constraintsSelCrit);
                ivjJPanel1.add(getModel(), constraintsModel);
                ivjJPanel1.add(getSearchMeth(), constraintsSearchMeth);
                ivjJPanel1.add(getJLabel3(), constraintsJLabel3);
                ivjJPanel1.add(getJLabel(), constraintsJLabel);
                ivjJPanel1.add(getStartStrat(), constraintsStartStrat);
                ivjJPanel1.add(getPrintLog(), constraintsPrintLog);
                ivjJPanel1.add(getStandData(), constraintsStandData);
                ivjJPanel1.add(getExecute(), constraintsExecute);
                ivjJPanel1.add(getWhiteNoise(), constraintsWhiteNoise);
                TitledBorder title = new TitledBorder(new BevelBorder(
                        BevelBorder.LOWERED),
                        "Specify Lag Selection for Volatility",
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
                ivjModel.setPreferredSize(new java.awt.Dimension(200, 23));
                // user code begin {1}
                ivjModel.setMinimumSize(new java.awt.Dimension(150, 25));
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
                ivjSearchMeth.setPreferredSize(new java.awt.Dimension(200, 23));
                // user code begin {1}
                // user code end
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
     * Return the WhiteNoise property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getWhiteNoise() {
        if (ivjWhiteNoise == null) {
            try {
                ivjWhiteNoise = new javax.swing.JCheckBox();
                ivjWhiteNoise.setName("WhiteNoise");
                ivjWhiteNoise.setText("set f(y) = 0; resids = y ");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjWhiteNoise;
    }

    /**
     * Method generated to support the promotion of the whiteNoiseSelected
     * attribute.
     * 
     * @return boolean
     */
    public boolean getWhiteNoiseSelected() {
        return getWhiteNoise().isSelected();
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
        getModel().addActionListener(ivjEventHandler);
        getSelCrit().addActionListener(ivjEventHandler);
        getStandData().addActionListener(ivjEventHandler);
    }

    /**
     * Initialize the class.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initialize() {
        try {
            // user code begin {1}
            // user code end
            setName("CAFPE_volatsel");
            setLayout(new java.awt.BorderLayout());
            setSize(719, 463);
            add(getResultField(), "Center");
            add(getJPanel1(), "North");
            initConnections();
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}
        getSearchMeth().addItem(CAFPE_sel.searchStratKeys[0]);
        getSearchMeth().addItem(CAFPE_sel.searchStratKeys[1]);

        getModel().addItem("NAR");
        getModel().addItem("SDNAR");

        getSelCrit().addItem(CAFPE_sel.critKeys[0]);
        getSelCrit().addItem(CAFPE_sel.critKeys[1]);

        getStartStrat().addItem("same");
        getStartStrat().addItem("different");
        getStartStrat().setSelectedIndex(0);

        // user code end
    }

    /**
     * Comment
     */
    private void removeDataObjects() {

        global().get(CAFPE_constants.OPT_LAGS_VOLAT).clear();
        global().get(CAFPE_constants.RESID_EST_VOLAT).clear();
    }

    /**
     * Method generated to support the promotion of the whiteNoiseSelected
     * attribute.
     * 
     * @param arg1
     *            boolean
     */
    public void setWhiteNoiseSelected(boolean arg1) {
        getWhiteNoise().setSelected(arg1);
    }
}