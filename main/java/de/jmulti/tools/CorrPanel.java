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
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import org.apache.log4j.Logger;

import com.jstatcom.component.NumSelector;
import com.jstatcom.component.ResultField;
import com.jstatcom.component.StdMessages;
import com.jstatcom.engine.PCall;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.JSCSArray;
import com.jstatcom.model.JSCTypeDef;
import com.jstatcom.model.ModelPanel;
import com.jstatcom.table.JSCDataTableScrollPane;
import com.jstatcom.table.JSCSArrayTable;
import com.jstatcom.util.UMatrix;

import de.jmulti.proc.AutocorrCall;
import de.jmulti.proc.CrossCorrCall;
import de.jmulti.var.VARConstants;
import de.jmulti.vecm.VECMConstants;

/**
 * A panel for correlation analysis of residuals.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public final class CorrPanel extends ModelPanel {
    private static final Logger log = Logger.getLogger(CorrPanel.class);

    private ModelTypes modelType = null;

    private JSCTypeDef res = null;

    private JSCTypeDef resNames = null;

    private JSCTypeDef Ax = null;

    private JCheckBox ivjAsymCi = null;

    private JButton ivjAutocorr = null;

    private NumSelector ivjAutocorrLags = null;

    private JPanel ivjCorr = null;

    private JButton ivjCrossCorr = null;

    private JSCDataTableScrollPane ivjDataTableScrollPane0 = null;

    private JSCDataTableScrollPane ivjDataTableScrollPane1 = null;

    private JLabel ivjJLabel3 = null;

    private JLabel ivjJLabel4 = null;

    private ResultField ivjResultField = null;

    private JSCSArrayTable ivjScrollPaneTable_Nu = null;

    private JSCSArrayTable ivjScrollPaneTable_Nu1 = null;

    private JCheckBox ivjSquaredRes = null;

    private JCheckBox ivjTextOut = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    class IvjEventHandler implements java.awt.event.ActionListener {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == CorrPanel.this.getSquaredRes())
                connEtoC1();
            if (e.getSource() == CorrPanel.this.getAsymCi())
                connEtoC2();
            if (e.getSource() == CorrPanel.this.getAutocorr())
                connEtoC3();
            if (e.getSource() == CorrPanel.this.getCrossCorr())
                connEtoC4();
        };
    };

    /**
     * Correlation constructor comment.
     */
    public CorrPanel() {
        super();
        initialize();
    }

    /**
     * Comment
     */
    private void asymCi_ActionEvents() {
        if (getAsymCi().isSelected())
            getSquaredRes().setSelected(false);
    }

    /**
     * Comment
     */
    private void autocorr_ActionEvents() {
        if (getScrollPaneTable_Nu().getSelectedRow() == -1)
            getScrollPaneTable_Nu().getSelectionModel().setSelectionInterval(0,
                    getScrollPaneTable_Nu().getRowCount() - 1);

        JSCNArray resids = global().get(res).getJSCNArray();
        JSCSArray names = global().get(resNames).getJSCSArray();

        int[] index0 = getScrollPaneTable_Nu().getIntSelectionIndex();

        resids = new JSCNArray("AutoCorrResids", resids.selColsIf(index0));
        names = new JSCSArray("AutoCorrResidsNames", names.selRowsIf(index0));

        PCall job = new AutocorrCall(resids, names, getAutocorrLags()
                .getIntNumber(), getSquaredRes().isSelected(), getTextOut()
                .isSelected(), true);

        job.setSymbolTable(local());
        job.setOutHolder(getResultField());
        job.execute();

    }

    /**
     * connEtoC1: (SquaredRes.action. -->
     * Correlation.squaredRes_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC1() {
        try {
            // user code begin {1}
            // user code end
            this.squaredRes_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC2: (AsymCi.action. --> Correlation.asymCi_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC2() {
        try {
            // user code begin {1}
            // user code end
            this.asymCi_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC3: (Autocorr.action. --> Correlation.autocorr_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC3() {
        try {
            // user code begin {1}
            // user code end
            this.autocorr_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC4: (CrossCorr.action. --> Correlation.crossCorr_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC4() {
        try {
            // user code begin {1}
            // user code end
            this.crossCorr_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * Call to crosscorrelation computation,
     */
    private void crossCorr_ActionEvents() {

        if (modelType == ModelTypes.VAR && !global().get(Ax).isEmpty()
                && getAsymCi().isSelected()) {
            StdMessages
                    .infoGeneral("Exact confidence intervals not available with exogenous variables.\nPlease deselect this option.");
            return;
        }

        if (getScrollPaneTable_Nu().getSelectedRow() == -1)
            getScrollPaneTable_Nu().getSelectionModel().setSelectionInterval(0,
                    getScrollPaneTable_Nu().getRowCount() - 1);
        if (getScrollPaneTable_Nu1().getSelectedRow() == -1)
            getScrollPaneTable_Nu1().getSelectionModel().setSelectionInterval(
                    0, getScrollPaneTable_Nu1().getRowCount() - 1);

        // Gets selection indices.
        int[] index0 = getScrollPaneTable_Nu().getIntSelectionIndex();
        int[] index1 = getScrollPaneTable_Nu1().getIntSelectionIndex();

        if (UMatrix.getNonzeroIntCount(index0)
                * UMatrix.getNonzeroIntCount(index1) > 36) {
            StdMessages
                    .infoGeneral("Please select fewer residual series.\nThe number of graphs is too large.");
            return;
        }

        PCall job = new CrossCorrCall(global(), local(), modelType,
                getAutocorrLags().getIntNumber(), getAsymCi().isSelected(),
                getSquaredRes().isSelected(), index0, index1, getTextOut()
                        .isSelected());

        job.setOutHolder(getResultField());
        job.execute();
    }

    /**
     * Return the AsymCi property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getAsymCi() {
        if (ivjAsymCi == null) {
            try {
                ivjAsymCi = new javax.swing.JCheckBox();
                ivjAsymCi.setName("AsymCi");
                ivjAsymCi
                        .setToolTipText("compute asymptotic confidence intervals exactly");
                ivjAsymCi.setText("Exact CI for crosscorr.");
                ivjAsymCi.setMaximumSize(new java.awt.Dimension(137, 25));
                ivjAsymCi.setSelected(false);
                ivjAsymCi.setPreferredSize(new java.awt.Dimension(150, 20));
                ivjAsymCi.setMinimumSize(new java.awt.Dimension(137, 20));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjAsymCi;
    }

    /**
     * Return the Autocorr property value.
     * 
     * @return javax.swing.JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JButton getAutocorr() {
        if (ivjAutocorr == null) {
            try {
                ivjAutocorr = new javax.swing.JButton();
                ivjAutocorr.setName("Autocorr");
                ivjAutocorr.setText("Autocorrelation");
                ivjAutocorr.setMaximumSize(new java.awt.Dimension(213, 27));
                ivjAutocorr.setPreferredSize(new java.awt.Dimension(120, 27));
                ivjAutocorr.setMargin(new java.awt.Insets(2, 10, 2, 10));
                ivjAutocorr.setMinimumSize(new java.awt.Dimension(120, 27));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjAutocorr;
    }

    private NumSelector getAutocorrLags() {
        if (ivjAutocorrLags == null) {
            try {
                ivjAutocorrLags = new NumSelector();
                ivjAutocorrLags.setName("AutocorrLags");
                ivjAutocorrLags
                        .setPreferredSize(new java.awt.Dimension(55, 21));
                ivjAutocorrLags.setNumber(12.0);
                ivjAutocorrLags.setRangeExpr("[2,100]");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjAutocorrLags;
    }

    /**
     * Return the Corr property value.
     * 
     * @return javax.swing.JPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JPanel getCorr() {
        if (ivjCorr == null) {
            try {
                ivjCorr = new javax.swing.JPanel();
                ivjCorr.setName("Corr");
                ivjCorr.setBorder(new BevelBorder(BevelBorder.LOWERED));
                ivjCorr.setLayout(new java.awt.GridBagLayout());
                ivjCorr.setPreferredSize(new java.awt.Dimension(550, 130));
                ivjCorr.setMinimumSize(new java.awt.Dimension(536, 120));

                java.awt.GridBagConstraints constraintsAutocorrLags = new java.awt.GridBagConstraints();
                constraintsAutocorrLags.gridx = 4;
                constraintsAutocorrLags.gridy = 0;
                constraintsAutocorrLags.anchor = java.awt.GridBagConstraints.WEST;
                constraintsAutocorrLags.insets = new java.awt.Insets(5, 0, 0, 0);
                getCorr().add(getAutocorrLags(), constraintsAutocorrLags);

                java.awt.GridBagConstraints constraintsAutocorr = new java.awt.GridBagConstraints();
                constraintsAutocorr.gridx = 3;
                constraintsAutocorr.gridy = 3;
                constraintsAutocorr.anchor = java.awt.GridBagConstraints.SOUTHWEST;
                constraintsAutocorr.insets = new java.awt.Insets(5, 0, 5, 10);
                getCorr().add(getAutocorr(), constraintsAutocorr);

                java.awt.GridBagConstraints constraintsJLabel3 = new java.awt.GridBagConstraints();
                constraintsJLabel3.gridx = 3;
                constraintsJLabel3.gridy = 0;
                constraintsJLabel3.anchor = java.awt.GridBagConstraints.EAST;
                constraintsJLabel3.insets = new java.awt.Insets(5, 5, 0, 10);
                getCorr().add(getJLabel3(), constraintsJLabel3);

                java.awt.GridBagConstraints constraintsCrossCorr = new java.awt.GridBagConstraints();
                constraintsCrossCorr.gridx = 4;
                constraintsCrossCorr.gridy = 3;
                constraintsCrossCorr.anchor = java.awt.GridBagConstraints.SOUTHWEST;
                constraintsCrossCorr.insets = new java.awt.Insets(5, 0, 5, 5);
                getCorr().add(getCrossCorr(), constraintsCrossCorr);

                java.awt.GridBagConstraints constraintsGaussDataTableScrollPane0 = new java.awt.GridBagConstraints();
                constraintsGaussDataTableScrollPane0.gridx = 1;
                constraintsGaussDataTableScrollPane0.gridy = 0;
                constraintsGaussDataTableScrollPane0.gridheight = 4;
                constraintsGaussDataTableScrollPane0.fill = java.awt.GridBagConstraints.BOTH;
                constraintsGaussDataTableScrollPane0.anchor = java.awt.GridBagConstraints.EAST;
                constraintsGaussDataTableScrollPane0.weighty = 1.0;
                constraintsGaussDataTableScrollPane0.insets = new java.awt.Insets(
                        5, 10, 5, 20);
                getCorr().add(getDataTableScrollPane0(),
                        constraintsGaussDataTableScrollPane0);

                java.awt.GridBagConstraints constraintsJLabel4 = new java.awt.GridBagConstraints();
                constraintsJLabel4.gridx = 0;
                constraintsJLabel4.gridy = 0;
                constraintsJLabel4.anchor = java.awt.GridBagConstraints.EAST;
                constraintsJLabel4.weightx = 1.0;
                constraintsJLabel4.insets = new java.awt.Insets(5, 5, 0, 4);
                getCorr().add(getJLabel4(), constraintsJLabel4);

                java.awt.GridBagConstraints constraintsGaussDataTableScrollPane1 = new java.awt.GridBagConstraints();
                constraintsGaussDataTableScrollPane1.gridx = 2;
                constraintsGaussDataTableScrollPane1.gridy = 0;
                constraintsGaussDataTableScrollPane1.gridheight = 4;
                constraintsGaussDataTableScrollPane1.fill = java.awt.GridBagConstraints.BOTH;
                constraintsGaussDataTableScrollPane1.anchor = java.awt.GridBagConstraints.EAST;
                constraintsGaussDataTableScrollPane1.weighty = 1.0;
                constraintsGaussDataTableScrollPane1.insets = new java.awt.Insets(
                        5, 10, 5, 20);
                getCorr().add(getDataTableScrollPane1(),
                        constraintsGaussDataTableScrollPane1);

                java.awt.GridBagConstraints constraintsAsymCi = new java.awt.GridBagConstraints();
                constraintsAsymCi.gridx = 4;
                constraintsAsymCi.gridy = 1;
                constraintsAsymCi.anchor = java.awt.GridBagConstraints.SOUTHWEST;
                constraintsAsymCi.insets = new java.awt.Insets(5, 0, 0, 0);
                getCorr().add(getAsymCi(), constraintsAsymCi);

                java.awt.GridBagConstraints constraintsTextOut = new java.awt.GridBagConstraints();
                constraintsTextOut.gridx = 3;
                constraintsTextOut.gridy = 2;
                constraintsTextOut.anchor = java.awt.GridBagConstraints.SOUTHWEST;
                constraintsTextOut.insets = new java.awt.Insets(5, 0, 0, 0);
                getCorr().add(getTextOut(), constraintsTextOut);

                java.awt.GridBagConstraints constraintsSquaredRes = new java.awt.GridBagConstraints();
                constraintsSquaredRes.gridx = 4;
                constraintsSquaredRes.gridy = 2;
                constraintsSquaredRes.anchor = java.awt.GridBagConstraints.SOUTHWEST;
                constraintsSquaredRes.insets = new java.awt.Insets(5, 0, 0, 0);
                getCorr().add(getSquaredRes(), constraintsSquaredRes);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjCorr;
    }

    /**
     * Return the CrossCorr property value.
     * 
     * @return javax.swing.JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JButton getCrossCorr() {
        if (ivjCrossCorr == null) {
            try {
                ivjCrossCorr = new javax.swing.JButton();
                ivjCrossCorr.setName("CrossCorr");
                ivjCrossCorr.setText("Crosscorrelation");
                ivjCrossCorr.setMaximumSize(new java.awt.Dimension(211, 27));
                ivjCrossCorr.setPreferredSize(new java.awt.Dimension(120, 27));
                ivjCrossCorr.setMargin(new java.awt.Insets(2, 5, 2, 5));
                ivjCrossCorr.setEnabled(true);
                ivjCrossCorr.setMinimumSize(new java.awt.Dimension(120, 27));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjCrossCorr;
    }

    private JSCDataTableScrollPane getDataTableScrollPane0() {
        if (ivjDataTableScrollPane0 == null) {
            try {
                ivjDataTableScrollPane0 = new JSCDataTableScrollPane();
                ivjDataTableScrollPane0.setName("DataTableScrollPane0");
                ivjDataTableScrollPane0.setMaximumVisibleColumns(1);
                ivjDataTableScrollPane0.setMaximumVisibleRows(6);
                getDataTableScrollPane0().setViewportView(
                        getScrollPaneTable_Nu());
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjDataTableScrollPane0;
    }

    private JSCDataTableScrollPane getDataTableScrollPane1() {
        if (ivjDataTableScrollPane1 == null) {
            try {
                ivjDataTableScrollPane1 = new JSCDataTableScrollPane();
                ivjDataTableScrollPane1.setName("DataTableScrollPane1");
                ivjDataTableScrollPane1.setMaximumVisibleColumns(1);
                ivjDataTableScrollPane1.setMaximumVisibleRows(6);
                getDataTableScrollPane1().setViewportView(
                        getScrollPaneTable_Nu1());
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
                ivjJLabel3.setText("Number of lags");
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
                ivjJLabel4.setText("Select residual series");
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
     * Gets the model type currently in use.
     * 
     * @return model type
     */
    public ModelTypes getModelType() {
        return modelType;
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
                getDataTableScrollPane0().setColumnHeaderView(
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

    private JSCSArrayTable getScrollPaneTable_Nu1() {
        if (ivjScrollPaneTable_Nu1 == null) {
            try {
                ivjScrollPaneTable_Nu1 = new JSCSArrayTable();
                ivjScrollPaneTable_Nu1.setName("ScrollPaneTable_Nu1");
                getDataTableScrollPane1().setColumnHeaderView(
                        ivjScrollPaneTable_Nu1.getTableHeader());
                ivjScrollPaneTable_Nu1.setBounds(0, 0, 144, 104);
                ivjScrollPaneTable_Nu1.setRowSelectionAllowed(true);
                ivjScrollPaneTable_Nu1.setEditable(false);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjScrollPaneTable_Nu1;
    }

    /**
     * Return the SquaredRes property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getSquaredRes() {
        if (ivjSquaredRes == null) {
            try {
                ivjSquaredRes = new javax.swing.JCheckBox();
                ivjSquaredRes.setName("SquaredRes");
                ivjSquaredRes.setPreferredSize(new java.awt.Dimension(128, 20));
                ivjSquaredRes.setText("Squared residuals");
                ivjSquaredRes.setMinimumSize(new java.awt.Dimension(128, 20));
                ivjSquaredRes.setMaximumSize(new java.awt.Dimension(128, 25));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSquaredRes;
    }

    /**
     * Return the TextOut property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getTextOut() {
        if (ivjTextOut == null) {
            try {
                ivjTextOut = new javax.swing.JCheckBox();
                ivjTextOut.setName("TextOut");
                ivjTextOut
                        .setToolTipText("compute asymptotic confidence intervals exactly");
                ivjTextOut.setText("Output as text");
                ivjTextOut.setMaximumSize(new java.awt.Dimension(137, 25));
                ivjTextOut.setSelected(false);
                ivjTextOut.setPreferredSize(new java.awt.Dimension(137, 20));
                ivjTextOut.setMinimumSize(new java.awt.Dimension(137, 20));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjTextOut;
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
        getSquaredRes().addActionListener(ivjEventHandler);
        getAsymCi().addActionListener(ivjEventHandler);
        getAutocorr().addActionListener(ivjEventHandler);
        getCrossCorr().addActionListener(ivjEventHandler);
    }

    /**
     * Initialize the class.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initialize() {
        try {
            // user code begin {1}

            // user code end
            setName("Correlation");
            setLayout(new java.awt.BorderLayout());
            setSize(555, 317);
            add(getResultField(), "Center");
            add(getCorr(), "North");
            initConnections();
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}
        // user code end
    }

    /**
     * Sets the model type to be used for this component.
     * 
     * @param newModelType
     *            <code>VAR</code> or <code>VECM</code>
     */
    public void setModelType(ModelTypes newModelType) {
        modelType = newModelType;

        if (modelType == ModelTypes.VAR) {
            res = VARConstants.u_hat_Def;
            resNames = VARConstants.Nu_Def;
            Ax = VARConstants.Ax_Def;
            getAsymCi().setVisible(true);

        } else if (modelType == ModelTypes.VECM) {
            res = VECMConstants.u_hat_Def;
            resNames = VECMConstants.Nu_Def;
            getAsymCi().setVisible(false);
            getAsymCi().setSelected(false);
        } else
            throw new IllegalArgumentException(newModelType
                    + " is unknown model type.");
        getScrollPaneTable_Nu().setSymbolName(resNames.name);
        getScrollPaneTable_Nu1().setSymbolName(resNames.name);
    }

    /**
     * Comment
     */
    private void squaredRes_ActionEvents() {
        if (getSquaredRes().isSelected())
            getAsymCi().setSelected(false);

    }
}