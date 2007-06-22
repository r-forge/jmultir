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
import javax.swing.JLabel;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import com.jstatcom.component.NumSelector;
import com.jstatcom.component.ResultField;
import com.jstatcom.component.StdMessages;
import com.jstatcom.engine.PCall;
import com.jstatcom.engine.PCallAdapter;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.JSCSArray;
import com.jstatcom.model.ModelPanel;
import com.jstatcom.ts.TSDateRange;
import com.jstatcom.util.UData;

import de.jmulti.proc.AutocorrCall;
import de.jmulti.proc.HegyTestCall;
import de.jmulti.proc.InfoCritCall;
import de.jmulti.proc.PlotTSCall;
import de.jmulti.proc.UnivarARCHLMCall;
import de.jmulti.proc.UnivarJBeraCall;
import de.jmulti.proc.UnivarPortmanCall;
import de.jmulti.tools.DiagnosSelector;

/**
 * Panel to perform the HEGY test for monthly and quarterly data.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
final class HegyPanel extends ModelPanel {
    private static final Logger log = Logger.getLogger(HegyPanel.class);

    private JButton ivjDiagnostics = null;

    private JLabel ivjJLabel2 = null;

    private JLabel ivjJLabel21 = null;

    private NumSelector ivjLagLength = null;

    private NumSelector ivjMaxLagLength = null;

    private ResultField ivjResultField = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private JCheckBox ivjConstCheck = null;

    private JCheckBox ivjSeasDumCheck = null;

    private JCheckBox ivjTrendCheck = null;

    private JButton ivjExecute = null;

    class IvjEventHandler implements java.awt.event.ActionListener {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == HegyPanel.this.getDiagnostics())
                connEtoC3();
            if (e.getSource() == HegyPanel.this.getTrendCheck())
                connEtoC4();
            if (e.getSource() == HegyPanel.this.getConstCheck())
                connEtoC5();
            if (e.getSource() == HegyPanel.this.getSeasDumCheck())
                connEtoC2();
            if (e.getSource() == HegyPanel.this.getExecute())
                connEtoC1();
        };
    };

    /**
     * HegyPanel constructor comment.
     */
    public HegyPanel() {
        super();
        initialize();
    }

    /**
     * connEtoC1: (saisonDummies.action. -->
     * HegyPanel.saisonDummies_ActionPerformed()V)
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
     * connEtoC2: (JButton1.action. --> seasDumCheck_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC2() {
        try {
            // user code begin {1}
            // user code end
            this.seasDumCheck_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC3: (Diagnostics.action. -->
     * HegyPanel.diagnostics_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC3() {
        try {
            // user code begin {1}
            // user code end
            this.diagnostics_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC4: (timetrend.action. --> HegyPanel.timetrend_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC4() {
        try {
            // user code begin {1}
            // user code end
            this.timetrend_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC5: (intercept.action. --> HegyPanel.timetrend_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC5() {
        try {
            // user code begin {1}
            // user code end
            this.timetrend_ActionEvents();
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
    private void diagnostics_ActionEvents() {

        DiagnosSelector.getSharedInstance().setVisible(true);
        return;
    }

    /**
     * Comment
     */
    private void execute_ActionEvents() {
        if (global().get(UnitRootPanel.UR_ENDDATA).isEmpty()) {
            StdMessages.infoNothingSelected(UnitRootPanel.NO_VAR_MSG);
            return;
        }
        final TSDateRange range = global().get(UnitRootPanel.UR_DRANGE)
                .getJSCDRange().getTSDateRange();

        int period = range.subPeriodicity();

        if (period != 4 && period != 12) {
            StdMessages
                    .infoGeneral("The HEGY test is implemented for periodicities 4 and 12 (current = "
                            + period + ").");
            return;
        }
        final int lagLength = getLagLength().getIntNumber();

        int testState = 1; // Nothing selected.
        if (getConstCheck().isSelected() && !getTrendCheck().isSelected()
                && !getSeasDumCheck().isSelected())
            testState = 2;
        if (getConstCheck().isSelected() && getTrendCheck().isSelected()
                && !getSeasDumCheck().isSelected())
            testState = 3;
        if (getTrendCheck().isSelected() && getSeasDumCheck().isSelected())
            testState = 4;
        if (!getTrendCheck().isSelected() && getSeasDumCheck().isSelected())
            testState = 5;

        // Call HEGY test.
        PCall job = new HegyTestCall(global().get(UnitRootPanel.UR_ENDDATA)
                .getJSCNArray(), global().get(UnitRootPanel.UR_ENDNAMES)
                .getJSCString(), range, lagLength, testState);
        job.setOutHolder(getResultField());
        job.setSymbolTable(local());

        JSCNArray dets = UData.createDeterministic(range, null, getConstCheck()
                .isSelected(), getSeasDumCheck().isSelected(), false,
                getTrendCheck().isSelected(), "dets");

        // Infocriteria call in 1st diffs.
        PCall jobInfo = new InfoCritCall(getMaxLagLength().getIntNumber(),
                null, null, global().get(UnitRootPanel.UR_ENDDATA)
                        .getJSCNArray(), dets, range.lowerBound(), period);
        jobInfo.setOutHolder(getResultField());
        jobInfo.setPrintDate(false);
        job.add(jobInfo);

        final DiagnosSelector diag = DiagnosSelector.getSharedInstance();
        job.addPCallListener(new PCallAdapter() {
            public void success() {
                // Portman.
                if (diag.isPortMan()) {
                    PCall jobPort = new UnivarPortmanCall(local().get(
                            HegyTestCall.HEGY_RESIDS).getJSCNArray(), diag
                            .getPortmanteauLags(), 0);
                    jobPort.setOutHolder(getResultField());
                    jobPort.setSymbolTable(local());
                    jobPort.setPrintDate(false);
                    jobPort.execute();
                }
                // ARCH-LM.
                if (diag.isArchLM()) {
                    PCall jobArch = new UnivarARCHLMCall(local().get(
                            HegyTestCall.HEGY_RESIDS).getJSCNArray(), null,
                            diag.getArchLMLags());
                    jobArch.setOutHolder(getResultField());
                    jobArch.setSymbolTable(local());
                    jobArch.setPrintDate(false);
                    jobArch.execute();
                }
                // Jarque-Bera.
                if (diag.isJarqueBera()) {
                    PCall jobJB = new UnivarJBeraCall(local().get(
                            HegyTestCall.HEGY_RESIDS).getJSCNArray(), null);
                    jobJB.setOutHolder(getResultField());
                    jobJB.setSymbolTable(local());
                    jobJB.setPrintDate(false);
                    jobJB.execute();
                }

                // Plot Resids.
                if (diag.isPlotResiduals()) {
                    String name = global().get(UnitRootPanel.UR_ENDNAMES)
                            .getJSCString().string();
                    if (diag.isResidsStandardized())
                        name = "std. HEGY residuals (" + name + ")";
                    else
                        name = "HEGY residuals (" + name + ")";
                    PCall jobPlot = new PlotTSCall(local().get(
                            HegyTestCall.HEGY_RESIDS).getJSCNArray(), range
                            .lowerBound().addPeriods(lagLength + 1),
                            new JSCSArray("plotname", new String[] { name }),
                            false, diag.isResidsStandardized());
                    jobPlot.execute();
                }
                // Autocorrelation of Resids.
                if (diag.isPlotAutoCorrelation()) {
                    String name = global().get(UnitRootPanel.UR_ENDNAMES)
                            .getJSCString().string();
                    name = "HEGY residuals (" + name + ")";
                    PCall jobAuto = new AutocorrCall(local().get(
                            HegyTestCall.HEGY_RESIDS).getJSCNArray(),
                            new JSCSArray("plotname", new String[] { name }),
                            diag.getAutoCorrelationLags(), false, false, false);
                    jobAuto.setSymbolTable(local());
                    jobAuto.execute();
                }
            }
        });
        job.execute();

    }

    /**
     * Return the intercept property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getConstCheck() {
        if (ivjConstCheck == null) {
            try {
                ivjConstCheck = new javax.swing.JCheckBox();
                ivjConstCheck.setName("ConstCheck");
                ivjConstCheck.setText("Nonzero mean");
                ivjConstCheck.setMaximumSize(new java.awt.Dimension(130, 25));
                ivjConstCheck.setPreferredSize(new java.awt.Dimension(130, 25));
                ivjConstCheck.setMinimumSize(new java.awt.Dimension(130, 25));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjConstCheck;
    }

    /**
     * Return the Diagnostics property value.
     * 
     * @return javax.swing.JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JButton getDiagnostics() {
        if (ivjDiagnostics == null) {
            try {
                ivjDiagnostics = new javax.swing.JButton();
                ivjDiagnostics.setName("Diagnostics");
                ivjDiagnostics
                        .setPreferredSize(new java.awt.Dimension(150, 27));
                ivjDiagnostics.setText("Residual Analysis");
                ivjDiagnostics.setMaximumSize(new java.awt.Dimension(100, 27));
                ivjDiagnostics.setMinimumSize(new java.awt.Dimension(150, 27));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjDiagnostics;
    }

    /**
     * Return the JButton1 property value.
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
                ivjExecute.setMaximumSize(new java.awt.Dimension(135, 27));
                ivjExecute.setPreferredSize(new java.awt.Dimension(135, 27));
                ivjExecute.setMinimumSize(new java.awt.Dimension(135, 27));
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
                ivjJLabel2.setPreferredSize(new java.awt.Dimension(123, 20));
                ivjJLabel2.setText("Actual number of lags");
                ivjJLabel2.setMaximumSize(new java.awt.Dimension(123, 20));
                ivjJLabel2.setMinimumSize(new java.awt.Dimension(123, 20));
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
     * Return the JLabel21 property value.
     * 
     * @return javax.swing.JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JLabel getJLabel21() {
        if (ivjJLabel21 == null) {
            try {
                ivjJLabel21 = new javax.swing.JLabel();
                ivjJLabel21.setName("JLabel21");
                ivjJLabel21.setText("Max number of lags");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJLabel21;
    }

    /**
     * Return the LagLength property value.
     * 
     * @return com.jstatcom.model.NumberSelector
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private NumSelector getLagLength() {
        if (ivjLagLength == null) {
            try {
                ivjLagLength = new NumSelector();
                ivjLagLength.setName("LagLength");
                ivjLagLength.setPreferredSize(new java.awt.Dimension(25, 20));
                ivjLagLength.setNumber(2.0);
                ivjLagLength.setMinimumSize(new java.awt.Dimension(25, 20));
                ivjLagLength.setRangeExpr("[0,100]");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjLagLength;
    }

    /**
     * Return the MaxLagLength property value.
     * 
     * @return com.jstatcom.model.NumberSelector
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private NumSelector getMaxLagLength() {
        if (ivjMaxLagLength == null) {
            try {
                ivjMaxLagLength = new NumSelector();
                ivjMaxLagLength.setName("MaxLagLength");
                ivjMaxLagLength
                        .setPreferredSize(new java.awt.Dimension(25, 20));
                ivjMaxLagLength.setNumber(10.0);
                ivjMaxLagLength.setMinimumSize(new java.awt.Dimension(25, 20));
                ivjMaxLagLength.setRangeExpr("[1,100]");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjMaxLagLength;
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
                        .setPreferredSize(new java.awt.Dimension(300, 350));
                ivjResultField.setMinimumSize(new java.awt.Dimension(300, 350));
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
     * Return the saisonDummies property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getSeasDumCheck() {
        if (ivjSeasDumCheck == null) {
            try {
                ivjSeasDumCheck = new javax.swing.JCheckBox();
                ivjSeasDumCheck.setName("SeasDumCheck");
                ivjSeasDumCheck.setText("Seasonal dummies");
                ivjSeasDumCheck.setMaximumSize(new java.awt.Dimension(130, 25));
                ivjSeasDumCheck
                        .setPreferredSize(new java.awt.Dimension(130, 25));
                ivjSeasDumCheck.setEnabled(true);
                ivjSeasDumCheck.setMinimumSize(new java.awt.Dimension(130, 25));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSeasDumCheck;
    }

    /**
     * Return the timetrend property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getTrendCheck() {
        if (ivjTrendCheck == null) {
            try {
                ivjTrendCheck = new javax.swing.JCheckBox();
                ivjTrendCheck.setName("TrendCheck");
                ivjTrendCheck.setText("Time trend");
                ivjTrendCheck.setMaximumSize(new java.awt.Dimension(130, 25));
                ivjTrendCheck.setPreferredSize(new java.awt.Dimension(130, 25));
                ivjTrendCheck.setMinimumSize(new java.awt.Dimension(130, 25));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjTrendCheck;
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
        getDiagnostics().addActionListener(ivjEventHandler);
        getTrendCheck().addActionListener(ivjEventHandler);
        getConstCheck().addActionListener(ivjEventHandler);
        getSeasDumCheck().addActionListener(ivjEventHandler);
        getExecute().addActionListener(ivjEventHandler);
    }

    /**
     * Initialize the class.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initialize() {
        try {
            // user code begin {1}
            TitledBorder title = new TitledBorder(new BevelBorder(
                    BevelBorder.LOWERED), "Hegy Test", TitledBorder.RIGHT,
                    TitledBorder.TOP);
            this.setBorder(title);

            // user code end
            setName("HegyPanel");
            setPreferredSize(new java.awt.Dimension(485, 350));
            setLayout(new java.awt.GridBagLayout());
            setSize(485, 350);
            setMinimumSize(new java.awt.Dimension(485, 350));

            java.awt.GridBagConstraints constraintsResultField = new java.awt.GridBagConstraints();
            constraintsResultField.gridx = 0;
            constraintsResultField.gridy = 0;
            constraintsResultField.gridheight = 13;
            constraintsResultField.fill = java.awt.GridBagConstraints.BOTH;
            constraintsResultField.weightx = 1.0;
            constraintsResultField.weighty = 1.0;
            constraintsResultField.insets = new java.awt.Insets(0, 0, 5, 5);
            add(getResultField(), constraintsResultField);

            java.awt.GridBagConstraints constraintsDiagnostics = new java.awt.GridBagConstraints();
            constraintsDiagnostics.gridx = 1;
            constraintsDiagnostics.gridy = 0;
            constraintsDiagnostics.gridwidth = 2;
            constraintsDiagnostics.fill = java.awt.GridBagConstraints.HORIZONTAL;
            constraintsDiagnostics.anchor = java.awt.GridBagConstraints.WEST;
            constraintsDiagnostics.insets = new java.awt.Insets(0, 0, 10, 5);
            add(getDiagnostics(), constraintsDiagnostics);

            java.awt.GridBagConstraints constraintsConstCheck = new java.awt.GridBagConstraints();
            constraintsConstCheck.gridx = 1;
            constraintsConstCheck.gridy = 1;
            constraintsConstCheck.gridwidth = 2;
            constraintsConstCheck.fill = java.awt.GridBagConstraints.HORIZONTAL;
            constraintsConstCheck.anchor = java.awt.GridBagConstraints.WEST;
            add(getConstCheck(), constraintsConstCheck);

            java.awt.GridBagConstraints constraintsTrendCheck = new java.awt.GridBagConstraints();
            constraintsTrendCheck.gridx = 1;
            constraintsTrendCheck.gridy = 2;
            constraintsTrendCheck.gridwidth = 2;
            constraintsTrendCheck.fill = java.awt.GridBagConstraints.HORIZONTAL;
            constraintsTrendCheck.anchor = java.awt.GridBagConstraints.WEST;
            add(getTrendCheck(), constraintsTrendCheck);

            java.awt.GridBagConstraints constraintsSeasDumCheck = new java.awt.GridBagConstraints();
            constraintsSeasDumCheck.gridx = 1;
            constraintsSeasDumCheck.gridy = 3;
            constraintsSeasDumCheck.gridwidth = 2;
            constraintsSeasDumCheck.fill = java.awt.GridBagConstraints.HORIZONTAL;
            constraintsSeasDumCheck.anchor = java.awt.GridBagConstraints.WEST;
            constraintsSeasDumCheck.insets = new java.awt.Insets(0, 0, 10, 0);
            add(getSeasDumCheck(), constraintsSeasDumCheck);

            java.awt.GridBagConstraints constraintsLagLength = new java.awt.GridBagConstraints();
            constraintsLagLength.gridx = 1;
            constraintsLagLength.gridy = 4;
            constraintsLagLength.anchor = java.awt.GridBagConstraints.WEST;
            constraintsLagLength.insets = new java.awt.Insets(0, 0, 5, 0);
            add(getLagLength(), constraintsLagLength);

            java.awt.GridBagConstraints constraintsJLabel2 = new java.awt.GridBagConstraints();
            constraintsJLabel2.gridx = 2;
            constraintsJLabel2.gridy = 4;
            constraintsJLabel2.fill = java.awt.GridBagConstraints.HORIZONTAL;
            constraintsJLabel2.anchor = java.awt.GridBagConstraints.WEST;
            constraintsJLabel2.insets = new java.awt.Insets(0, 5, 5, 0);
            add(getJLabel2(), constraintsJLabel2);

            java.awt.GridBagConstraints constraintsMaxLagLength = new java.awt.GridBagConstraints();
            constraintsMaxLagLength.gridx = 1;
            constraintsMaxLagLength.gridy = 5;
            constraintsMaxLagLength.anchor = java.awt.GridBagConstraints.WEST;
            add(getMaxLagLength(), constraintsMaxLagLength);

            java.awt.GridBagConstraints constraintsJLabel21 = new java.awt.GridBagConstraints();
            constraintsJLabel21.gridx = 2;
            constraintsJLabel21.gridy = 5;
            constraintsJLabel21.fill = java.awt.GridBagConstraints.HORIZONTAL;
            constraintsJLabel21.anchor = java.awt.GridBagConstraints.WEST;
            constraintsJLabel21.insets = new java.awt.Insets(0, 5, 0, 0);
            add(getJLabel21(), constraintsJLabel21);

            java.awt.GridBagConstraints constraintsExecute = new java.awt.GridBagConstraints();
            constraintsExecute.gridx = 1;
            constraintsExecute.gridy = 12;
            constraintsExecute.gridwidth = 3;
            constraintsExecute.fill = java.awt.GridBagConstraints.HORIZONTAL;
            constraintsExecute.anchor = java.awt.GridBagConstraints.SOUTHEAST;
            constraintsExecute.insets = new java.awt.Insets(0, 0, 5, 5);
            add(getExecute(), constraintsExecute);
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
    private void seasDumCheck_ActionEvents() {
        if (getSeasDumCheck().isSelected()) {
            getConstCheck().setSelected(true);
            getConstCheck().setEnabled(false);
        } else if (!getTrendCheck().isSelected())
            getConstCheck().setEnabled(true);

    }

    /**
     * Comment
     */
    private void timetrend_ActionEvents() {
        if (getTrendCheck().isSelected() && !getSeasDumCheck().isSelected()) {
            getConstCheck().setSelected(true);
            getConstCheck().setEnabled(false);
        } else if (!getSeasDumCheck().isSelected())
            getConstCheck().setEnabled(true);
        return;
    }
}