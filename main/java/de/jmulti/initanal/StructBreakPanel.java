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

import java.awt.CardLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import com.jstatcom.component.CompSettings;
import com.jstatcom.component.MultiLineLabel;
import com.jstatcom.component.NumSelector;
import com.jstatcom.component.ResultField;
import com.jstatcom.component.StdMessages;
import com.jstatcom.engine.PCall;
import com.jstatcom.engine.PCallAdapter;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.JSCSArray;
import com.jstatcom.model.ModelPanel;
import com.jstatcom.ts.TSDate;
import com.jstatcom.ts.TSDateRange;
import com.jstatcom.ts.TSDateRangeSelector;
import com.jstatcom.ts.TSDateSelector;
import com.jstatcom.util.UData;
import com.jstatcom.util.UMatrix;

import de.jmulti.proc.AutocorrCall;
import de.jmulti.proc.InfoCritCall;
import de.jmulti.proc.PlotTSCall;
import de.jmulti.proc.StructBreakURCall;
import de.jmulti.proc.UnivarARCHLMCall;
import de.jmulti.proc.UnivarJBeraCall;
import de.jmulti.proc.UnivarPortmanCall;
import de.jmulti.tools.DiagnosSelector;

/**
 * Panel to perform the unit root test with structural break. It also contains
 * the option to search for the break date.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
final class StructBreakPanel extends ModelPanel {
    private static final Logger log = Logger.getLogger(StructBreakPanel.class);

    private JButton ivjDiagnostics = null;

    private JLabel ivjJLabel2 = null;

    private JLabel ivjJLabel21 = null;

    private JRadioButton ivjJRadioButton1 = null;

    private JRadioButton ivjJRadioButton2 = null;

    private JRadioButton ivjJRadioButton3 = null;

    private NumSelector ivjLagLength = null;

    private NumSelector ivjMaxLagLength = null;

    private ResultField ivjResultField = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private JCheckBox ivjTimeTrend = null;

    private JLabel ivjJLabel1 = null;

    private JCheckBox ivjSeasDum = null;

    private JCheckBox ivjGraphicalAnalysis = null;

    private JButton ivjBack = null;

    private JPanel ivjConfigContainer = null;

    private JPanel ivjConfigPanel1 = null;

    private JPanel ivjConfigPanel2 = null;

    private JButton ivjSearchBreak = null;

    private java.awt.CardLayout card = null;

    private JLabel ivjJLabel4 = null;

    private TSDateSelector ivjBreakDate = null;

    private JButton ivjExecuteSearch = null;

    private JLabel ivjJLabel5 = null;

    private JRadioButton ivjJRadioButton0 = null;

    private JLabel ivjJLabel3 = null;

    private JButton ivjExecute = null;

    private TSDateRangeSelector ivjSearchRange = null;

    private JButton ivjMaxButton = null;

    private MultiLineLabel ivjJTextArea = null;

    class IvjEventHandler implements java.awt.event.ActionListener,
            java.beans.PropertyChangeListener {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == StructBreakPanel.this.getDiagnostics())
                connEtoC2();
            if (e.getSource() == StructBreakPanel.this.getSearchBreak())
                connEtoC1();
            if (e.getSource() == StructBreakPanel.this.getBack())
                connEtoC3();
            if (e.getSource() == StructBreakPanel.this.getExecuteSearch())
                connEtoC5();
            if (e.getSource() == StructBreakPanel.this.getExecute())
                connEtoC6();
            if (e.getSource() == StructBreakPanel.this.getMaxButton())
                connEtoC7();
        };

        public void propertyChange(java.beans.PropertyChangeEvent evt) {
            if (evt.getSource() == StructBreakPanel.this.getLagLength()
                    && (evt.getPropertyName().equals("number")))
                connEtoC4(evt);
        };
    };

    /**
     * StructBreakPanel constructor comment.
     */
    public StructBreakPanel() {
        super();
        initialize();
    }

    /**
     * Comment
     */
    private void back_ActionEvents() {
        if (card == null)
            card = (CardLayout) getConfigContainer().getLayout();
        card.show(getConfigContainer(), getConfigPanel1().getName());
        return;
    }

    /**
     * connEtoC1: (SearchBreak.action. -->
     * StructBreakPanel.searchBreak_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC1() {
        try {
            // user code begin {1}
            // user code end
            this.searchBreak_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC2: (Diagnostics.action. -->
     * StructBreakPanel.diagnostics_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC2() {
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
     * connEtoC3: (Back.action. --> StructBreakPanel.back_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC3() {
        try {
            // user code begin {1}
            // user code end
            this.back_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC4: (LagLength.number --> StructBreakPanel.lagLength_Number()V)
     * 
     * @param arg1
     *            java.beans.PropertyChangeEvent
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC4(java.beans.PropertyChangeEvent arg1) {
        try {
            // user code begin {1}
            // user code end
            this.selectionChanged();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC5: (ExecuteSearch.action. -->
     * StructBreakPanel.executeSearch_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC5() {
        try {
            // user code begin {1}
            // user code end
            this.executeSearch_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC6: (Execute.action. --> StructBreakPanel.execute_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC6() {
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
     * connEtoC7: (MaxButton.action. -->
     * StructBreakPanel.maxButton_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC7() {
        try {
            // user code begin {1}
            // user code end
            this.maxButton_ActionEvents();
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

        if (period == 1 && getSeasDum().isSelected()) {
            StdMessages.errorSpecification(UnitRootPanel.NO_SEAS_MSG);
            return;
        }

        int shiftFunction = 0;
        if (getJRadioButton1().isSelected())
            shiftFunction = 1;
        if (getJRadioButton2().isSelected())
            shiftFunction = 2;
        if (getJRadioButton3().isSelected())
            shiftFunction = 3;

        final int lagLength = getLagLength().getIntNumber();

        // Call UR test.
        PCall job = new StructBreakURCall(global()
                .get(UnitRootPanel.UR_ENDDATA).getJSCNArray(), global().get(
                UnitRootPanel.UR_ENDNAMES).getJSCString(), range,
                new TSDateRange(getBreakDate().getTSDate(), getBreakDate()
                        .getTSDate()), lagLength, shiftFunction, getTimeTrend()
                        .isSelected(), getSeasDum().isSelected(),
                getGraphicalAnalysis().isSelected(), 2000);
        job.setOutHolder(getResultField());
        job.setSymbolTable(local());

        // Invoke all other calls when test proc has finished,
        // because shift function is needed for deterministics creation
        // in this thread.
        job.addPCallListener(new PCallAdapter() {
            public void success() {

                JSCNArray shiftFkt = local().get(
                        StructBreakURCall.SBREAK_SHIFTFKT).getJSCNArray();

                JSCNArray dets = UData.createDeterministic(range, shiftFkt,
                        true, getSeasDum().isSelected(), false, getTimeTrend()
                                .isSelected(), "dets");

                // Add estimated shift function only if it has full rank after
                // truncation.
                if (!UData.hasFullColumnRank(dets, getMaxLagLength()
                        .getIntNumber() + 1))
                    dets = new JSCNArray("dets", UMatrix.getDoubleCols(dets
                            .doubleArray(), shiftFkt.cols(), dets.cols() - 1));

                // Infocriteria call in 1st diffs.
                PCall jobInfo = new InfoCritCall(getMaxLagLength()
                        .getIntNumber(), null, null, global().get(
                        UnitRootPanel.UR_ENDDATA).getJSCNArray(), dets, range
                        .lowerBound(), 1);
                jobInfo.setOutHolder(getResultField());
                jobInfo.setPrintDate(false);
                jobInfo.execute();

                DiagnosSelector diag = DiagnosSelector.getSharedInstance();

                // Portman.
                if (diag.isPortMan()) {
                    PCall jobPort = new UnivarPortmanCall(local().get(
                            StructBreakURCall.SBREAK_RESIDS).getJSCNArray(),
                            diag.getPortmanteauLags(),  0);
                    jobPort.setOutHolder(getResultField());
                    jobPort.setSymbolTable(local());
                    jobPort.setPrintDate(false);
                    jobPort.execute();
                }
                // ARCH-LM.
                if (diag.isArchLM()) {
                    PCall jobArch = new UnivarARCHLMCall(local().get(
                            StructBreakURCall.SBREAK_RESIDS).getJSCNArray(),
                            null, diag.getArchLMLags());
                    jobArch.setOutHolder(getResultField());
                    jobArch.setSymbolTable(local());
                    jobArch.setPrintDate(false);
                    jobArch.execute();
                }
                // Jarque-Bera.
                if (diag.isJarqueBera()) {
                    PCall jobJB = new UnivarJBeraCall(local().get(
                            StructBreakURCall.SBREAK_RESIDS).getJSCNArray(),
                            null);
                    jobJB.setOutHolder(getResultField());
                    jobJB.setSymbolTable(local());
                    jobJB.setPrintDate(false);
                    jobJB.execute();
                }
                // Plot Resids.
                if (diag.isPlotResiduals()) {
                    String name = global().get(UnitRootPanel.UR_ENDNAMES)
                            .getJSCSArray().stringAt(0, 0);
                    if (diag.isResidsStandardized())
                        name = "std. struct break resids (" + name + ")";
                    else
                        name = "struct break resids (" + name + ")";
                    PCall jobPlot = new PlotTSCall(local().get(
                            StructBreakURCall.SBREAK_RESIDS).getJSCNArray(),
                            range.lowerBound().addPeriods(lagLength + 1),
                            new JSCSArray("plotname", new String[] { name }),
                            false, diag.isResidsStandardized());
                    jobPlot.execute();
                }
                // Autocorrelation of Resids.
                if (diag.isPlotAutoCorrelation()) {
                    String name = global().get(UnitRootPanel.UR_ENDNAMES)
                            .getJSCSArray().stringAt(0, 0);
                    name = "struct break resids (" + name + ")";
                    PCall jobAuto = new AutocorrCall(local().get(
                            StructBreakURCall.SBREAK_RESIDS).getJSCNArray(),
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
     * Comment
     */
    private void executeSearch_ActionEvents() {
        if (global().get(UnitRootPanel.UR_ENDDATA).isEmpty()) {
            StdMessages.infoNothingSelected(UnitRootPanel.NO_VAR_MSG);
            return;
        }

        final TSDateRange range = global().get(UnitRootPanel.UR_DRANGE)
                .getJSCDRange().getTSDateRange();

        int period = range.subPeriodicity();
        if (period == 1 && getSeasDum().isSelected()) {
            StdMessages.errorSpecification(UnitRootPanel.NO_SEAS_MSG);
            return;
        }
        int shiftFunction = 1; // Use shift dummy or impulse dummy if selected.
        if (getJRadioButton0().isSelected())
            shiftFunction = 0;

        final int lagLength = getLagLength().getIntNumber();

        // Call UR test.
        PCall job = new StructBreakURCall(global()
                .get(UnitRootPanel.UR_ENDDATA).getJSCNArray(), global().get(
                UnitRootPanel.UR_ENDNAMES).getJSCString(), range,
                getSearchRange().getTSDateRange(), lagLength, shiftFunction,
                getTimeTrend().isSelected(), getSeasDum().isSelected(),
                getGraphicalAnalysis().isSelected(), 2000);
        job.setOutHolder(getResultField());
        job.setSymbolTable(local());
        job.addPCallListener(new PCallAdapter() {
            // Sets break date to searched.
            public void success() {
                JSCNArray sb_result = local().get(
                        StructBreakURCall.SBREAK_RESULT).getJSCNArray();
                getBreakDate().setTSDate(
                        range.lowerBound()
                                .addPeriods(sb_result.intAt(0, 1) - 1));
            }
        });
        job.execute();

    }

    /**
     * Return the Back property value.
     * 
     * @return javax.swing.JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JButton getBack() {
        if (ivjBack == null) {
            try {
                ivjBack = new javax.swing.JButton();
                ivjBack.setName("Back");
                ivjBack.setText("<< Back to Test  ");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjBack;
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
                ivjBreakDate.setPreferredSize(new java.awt.Dimension(80, 20));
                ivjBreakDate.setMinimumSize(new java.awt.Dimension(80, 20));
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
     * Return the ConfigContainer property value.
     * 
     * @return javax.swing.JPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JPanel getConfigContainer() {
        if (ivjConfigContainer == null) {
            try {
                ivjConfigContainer = new javax.swing.JPanel();
                ivjConfigContainer.setName("ConfigContainer");
                ivjConfigContainer.setPreferredSize(new java.awt.Dimension(155,
                        350));
                ivjConfigContainer.setLayout(new java.awt.CardLayout());
                ivjConfigContainer.setMinimumSize(new java.awt.Dimension(155,
                        350));
                getConfigContainer().add(getConfigPanel1(),
                        getConfigPanel1().getName());
                getConfigContainer().add(getConfigPanel2(),
                        getConfigPanel2().getName());
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjConfigContainer;
    }

    /**
     * Return the ConfigPanel1 property value.
     * 
     * @return javax.swing.JPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JPanel getConfigPanel1() {
        if (ivjConfigPanel1 == null) {
            try {
                ivjConfigPanel1 = new javax.swing.JPanel();
                ivjConfigPanel1.setName("ConfigPanel1");
                ivjConfigPanel1.setLayout(new java.awt.GridBagLayout());

                java.awt.GridBagConstraints constraintsDiagnostics = new java.awt.GridBagConstraints();
                constraintsDiagnostics.gridx = 0;
                constraintsDiagnostics.gridy = 0;
                constraintsDiagnostics.gridwidth = 4;
                constraintsDiagnostics.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsDiagnostics.anchor = java.awt.GridBagConstraints.NORTH;
                constraintsDiagnostics.weightx = 1.0;
                getConfigPanel1().add(getDiagnostics(), constraintsDiagnostics);

                java.awt.GridBagConstraints constraintsJRadioButton1 = new java.awt.GridBagConstraints();
                constraintsJRadioButton1.gridx = 0;
                constraintsJRadioButton1.gridy = 2;
                constraintsJRadioButton1.gridwidth = 4;
                constraintsJRadioButton1.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJRadioButton1.weightx = 1.0;
                getConfigPanel1().add(getJRadioButton1(),
                        constraintsJRadioButton1);

                java.awt.GridBagConstraints constraintsJRadioButton2 = new java.awt.GridBagConstraints();
                constraintsJRadioButton2.gridx = 0;
                constraintsJRadioButton2.gridy = 3;
                constraintsJRadioButton2.gridwidth = 4;
                constraintsJRadioButton2.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJRadioButton2.weightx = 1.0;
                getConfigPanel1().add(getJRadioButton2(),
                        constraintsJRadioButton2);

                java.awt.GridBagConstraints constraintsJRadioButton3 = new java.awt.GridBagConstraints();
                constraintsJRadioButton3.gridx = 0;
                constraintsJRadioButton3.gridy = 4;
                constraintsJRadioButton3.gridwidth = 4;
                constraintsJRadioButton3.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJRadioButton3.weightx = 1.0;
                getConfigPanel1().add(getJRadioButton3(),
                        constraintsJRadioButton3);

                java.awt.GridBagConstraints constraintsTimeTrend = new java.awt.GridBagConstraints();
                constraintsTimeTrend.gridx = 0;
                constraintsTimeTrend.gridy = 7;
                constraintsTimeTrend.gridwidth = 4;
                constraintsTimeTrend.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsTimeTrend.weightx = 1.0;
                constraintsTimeTrend.insets = new java.awt.Insets(5, 0, 0, 0);
                getConfigPanel1().add(getTimeTrend(), constraintsTimeTrend);

                java.awt.GridBagConstraints constraintsSeasDum = new java.awt.GridBagConstraints();
                constraintsSeasDum.gridx = 0;
                constraintsSeasDum.gridy = 8;
                constraintsSeasDum.gridwidth = 4;
                constraintsSeasDum.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsSeasDum.weightx = 1.0;
                getConfigPanel1().add(getSeasDum(), constraintsSeasDum);

                java.awt.GridBagConstraints constraintsLagLength = new java.awt.GridBagConstraints();
                constraintsLagLength.gridx = 0;
                constraintsLagLength.gridy = 9;
                constraintsLagLength.weightx = 1.0;
                constraintsLagLength.insets = new java.awt.Insets(5, 0, 0, 5);
                getConfigPanel1().add(getLagLength(), constraintsLagLength);

                java.awt.GridBagConstraints constraintsJLabel2 = new java.awt.GridBagConstraints();
                constraintsJLabel2.gridx = 1;
                constraintsJLabel2.gridy = 9;
                constraintsJLabel2.gridwidth = 3;
                constraintsJLabel2.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel2.insets = new java.awt.Insets(5, 0, 0, 0);
                getConfigPanel1().add(getJLabel2(), constraintsJLabel2);

                java.awt.GridBagConstraints constraintsJLabel21 = new java.awt.GridBagConstraints();
                constraintsJLabel21.gridx = 2;
                constraintsJLabel21.gridy = 10;
                constraintsJLabel21.gridwidth = 3;
                constraintsJLabel21.fill = java.awt.GridBagConstraints.HORIZONTAL;
                getConfigPanel1().add(getJLabel21(), constraintsJLabel21);

                java.awt.GridBagConstraints constraintsMaxLagLength = new java.awt.GridBagConstraints();
                constraintsMaxLagLength.gridx = 0;
                constraintsMaxLagLength.gridy = 10;
                constraintsMaxLagLength.weightx = 1.0;
                constraintsMaxLagLength.insets = new java.awt.Insets(0, 0, 0, 5);
                getConfigPanel1().add(getMaxLagLength(),
                        constraintsMaxLagLength);

                java.awt.GridBagConstraints constraintsGraphicalAnalysis = new java.awt.GridBagConstraints();
                constraintsGraphicalAnalysis.gridx = 0;
                constraintsGraphicalAnalysis.gridy = 11;
                constraintsGraphicalAnalysis.gridwidth = 4;
                constraintsGraphicalAnalysis.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsGraphicalAnalysis.weightx = 1.0;
                constraintsGraphicalAnalysis.insets = new java.awt.Insets(5, 0,
                        0, 0);
                getConfigPanel1().add(getGraphicalAnalysis(),
                        constraintsGraphicalAnalysis);

                java.awt.GridBagConstraints constraintsExecute = new java.awt.GridBagConstraints();
                constraintsExecute.gridx = 0;
                constraintsExecute.gridy = 14;
                constraintsExecute.gridwidth = 4;
                constraintsExecute.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsExecute.anchor = java.awt.GridBagConstraints.SOUTH;
                constraintsExecute.weightx = 1.0;
                getConfigPanel1().add(getExecute(), constraintsExecute);

                java.awt.GridBagConstraints constraintsBreakDate = new java.awt.GridBagConstraints();
                constraintsBreakDate.gridx = 0;
                constraintsBreakDate.gridy = 6;
                constraintsBreakDate.gridwidth = 3;
                constraintsBreakDate.weightx = 1.0;
                constraintsBreakDate.insets = new java.awt.Insets(0, 0, 0, 5);
                getConfigPanel1().add(getBreakDate(), constraintsBreakDate);

                java.awt.GridBagConstraints constraintsSearchBreak = new java.awt.GridBagConstraints();
                constraintsSearchBreak.gridx = 3;
                constraintsSearchBreak.gridy = 5;
                constraintsSearchBreak.gridheight = 2;
                constraintsSearchBreak.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsSearchBreak.anchor = java.awt.GridBagConstraints.SOUTH;
                getConfigPanel1().add(getSearchBreak(), constraintsSearchBreak);

                java.awt.GridBagConstraints constraintsJLabel1 = new java.awt.GridBagConstraints();
                constraintsJLabel1.gridx = 0;
                constraintsJLabel1.gridy = 5;
                constraintsJLabel1.gridwidth = 3;
                constraintsJLabel1.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel1.weightx = 1.0;
                constraintsJLabel1.insets = new java.awt.Insets(5, 0, 2, 0);
                getConfigPanel1().add(getJLabel1(), constraintsJLabel1);

                java.awt.GridBagConstraints constraintsJLabel3 = new java.awt.GridBagConstraints();
                constraintsJLabel3.gridx = 2;
                constraintsJLabel3.gridy = 12;
                constraintsJLabel3.ipadx = 45;
                getConfigPanel1().add(getJLabel3(), constraintsJLabel3);

                java.awt.GridBagConstraints constraintsJLabel4 = new java.awt.GridBagConstraints();
                constraintsJLabel4.gridx = 0;
                constraintsJLabel4.gridy = 13;
                constraintsJLabel4.gridwidth = 4;
                constraintsJLabel4.fill = java.awt.GridBagConstraints.BOTH;
                constraintsJLabel4.weightx = 1.0;
                constraintsJLabel4.weighty = 1.0;
                constraintsJLabel4.insets = new java.awt.Insets(5, 0, 0, 0);
                getConfigPanel1().add(getJLabel4(), constraintsJLabel4);

                java.awt.GridBagConstraints constraintsJRadioButton0 = new java.awt.GridBagConstraints();
                constraintsJRadioButton0.gridx = 0;
                constraintsJRadioButton0.gridy = 1;
                constraintsJRadioButton0.gridwidth = 4;
                constraintsJRadioButton0.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJRadioButton0.anchor = java.awt.GridBagConstraints.WEST;
                getConfigPanel1().add(getJRadioButton0(),
                        constraintsJRadioButton0);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjConfigPanel1;
    }

    /**
     * Return the ConfigPanel2 property value.
     * 
     * @return javax.swing.JPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JPanel getConfigPanel2() {
        if (ivjConfigPanel2 == null) {
            try {
                ivjConfigPanel2 = new javax.swing.JPanel();
                ivjConfigPanel2.setName("ConfigPanel2");
                ivjConfigPanel2.setLayout(new java.awt.GridBagLayout());

                java.awt.GridBagConstraints constraintsSearchRange = new java.awt.GridBagConstraints();
                constraintsSearchRange.gridx = 0;
                constraintsSearchRange.gridy = 2;
                constraintsSearchRange.gridwidth = 2;
                constraintsSearchRange.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsSearchRange.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsSearchRange.weightx = 1.0;
                constraintsSearchRange.insets = new java.awt.Insets(0, 0, 5, 0);
                getConfigPanel2().add(getSearchRange(), constraintsSearchRange);

                java.awt.GridBagConstraints constraintsBack = new java.awt.GridBagConstraints();
                constraintsBack.gridx = 1;
                constraintsBack.gridy = 5;
                constraintsBack.gridwidth = 2;
                constraintsBack.fill = java.awt.GridBagConstraints.HORIZONTAL;
                getConfigPanel2().add(getBack(), constraintsBack);

                java.awt.GridBagConstraints constraintsJLabel5 = new java.awt.GridBagConstraints();
                constraintsJLabel5.gridx = 1;
                constraintsJLabel5.gridy = 1;
                constraintsJLabel5.gridwidth = 2;
                constraintsJLabel5.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel5.anchor = java.awt.GridBagConstraints.NORTH;
                constraintsJLabel5.insets = new java.awt.Insets(5, 0, 5, 0);
                getConfigPanel2().add(getJLabel5(), constraintsJLabel5);

                java.awt.GridBagConstraints constraintsExecuteSearch = new java.awt.GridBagConstraints();
                constraintsExecuteSearch.gridx = 1;
                constraintsExecuteSearch.gridy = 4;
                constraintsExecuteSearch.gridwidth = 2;
                constraintsExecuteSearch.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsExecuteSearch.anchor = java.awt.GridBagConstraints.SOUTH;
                constraintsExecuteSearch.weighty = 1.0;
                constraintsExecuteSearch.insets = new java.awt.Insets(0, 0, 5,
                        0);
                getConfigPanel2().add(getExecuteSearch(),
                        constraintsExecuteSearch);

                java.awt.GridBagConstraints constraintsJTextArea = new java.awt.GridBagConstraints();
                constraintsJTextArea.gridx = 0;
                constraintsJTextArea.gridy = 0;
                constraintsJTextArea.gridwidth = 2;
                constraintsJTextArea.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJTextArea.anchor = java.awt.GridBagConstraints.NORTH;
                constraintsJTextArea.insets = new java.awt.Insets(0, 0, 10, 0);
                getConfigPanel2().add(getJTextArea(), constraintsJTextArea);

                java.awt.GridBagConstraints constraintsMaxButton = new java.awt.GridBagConstraints();
                constraintsMaxButton.gridx = 1;
                constraintsMaxButton.gridy = 3;
                constraintsMaxButton.gridwidth = 3;
                constraintsMaxButton.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsMaxButton.insets = new java.awt.Insets(5, 0, 5, 0);
                getConfigPanel2().add(getMaxButton(), constraintsMaxButton);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjConfigPanel2;
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
                ivjExecute.setToolTipText("execute test");
                ivjExecute.setText("Execute");
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
     * Return the ExecuteSearch property value.
     * 
     * @return javax.swing.JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JButton getExecuteSearch() {
        if (ivjExecuteSearch == null) {
            try {
                ivjExecuteSearch = new javax.swing.JButton();
                ivjExecuteSearch.setName("ExecuteSearch");
                ivjExecuteSearch.setText("Search Break Date");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjExecuteSearch;
    }

    /**
     * Return the JCheckBox1 property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getGraphicalAnalysis() {
        if (ivjGraphicalAnalysis == null) {
            try {
                ivjGraphicalAnalysis = new javax.swing.JCheckBox();
                ivjGraphicalAnalysis.setName("GraphicalAnalysis");
                ivjGraphicalAnalysis.setText("Graphical analysis");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjGraphicalAnalysis;
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
                ivjJLabel2.setPreferredSize(new java.awt.Dimension(125, 20));
                ivjJLabel2.setText("Actual number of lags");
                ivjJLabel2.setMinimumSize(new java.awt.Dimension(125, 20));
                ivjJLabel2.setMaximumSize(new java.awt.Dimension(125, 20));
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
                ivjJLabel21.setMaximumSize(new java.awt.Dimension(113, 20));
                ivjJLabel21.setPreferredSize(new java.awt.Dimension(113, 20));
                ivjJLabel21.setEnabled(true);
                ivjJLabel21.setMinimumSize(new java.awt.Dimension(113, 20));
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
                ivjJLabel3.setText("");
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
                ivjJLabel4.setPreferredSize(new java.awt.Dimension(45, 0));
                ivjJLabel4.setText("");
                ivjJLabel4.setMaximumSize(new java.awt.Dimension(45, 0));
                ivjJLabel4.setMinimumSize(new java.awt.Dimension(45, 0));
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
                ivjJLabel5.setText("Search range for break");
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
     * Return the JRadioButton0 property value.
     * 
     * @return javax.swing.JRadioButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JRadioButton getJRadioButton0() {
        if (ivjJRadioButton0 == null) {
            try {
                ivjJRadioButton0 = new javax.swing.JRadioButton();
                ivjJRadioButton0.setName("JRadioButton0");
                ivjJRadioButton0.setText("Impulse dummy");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJRadioButton0;
    }

    /**
     * Return the JRadioButton1 property value.
     * 
     * @return javax.swing.JRadioButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JRadioButton getJRadioButton1() {
        if (ivjJRadioButton1 == null) {
            try {
                ivjJRadioButton1 = new javax.swing.JRadioButton();
                ivjJRadioButton1.setName("JRadioButton1");
                ivjJRadioButton1.setText("Shift dummy");
                ivjJRadioButton1
                        .setMaximumSize(new java.awt.Dimension(100, 25));
                ivjJRadioButton1.setSelected(true);
                ivjJRadioButton1.setPreferredSize(new java.awt.Dimension(100,
                        25));
                ivjJRadioButton1
                        .setMinimumSize(new java.awt.Dimension(100, 25));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJRadioButton1;
    }

    /**
     * Return the JRadioButton2 property value.
     * 
     * @return javax.swing.JRadioButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JRadioButton getJRadioButton2() {
        if (ivjJRadioButton2 == null) {
            try {
                ivjJRadioButton2 = new javax.swing.JRadioButton();
                ivjJRadioButton2.setName("JRadioButton2");
                ivjJRadioButton2.setPreferredSize(new java.awt.Dimension(100,
                        25));
                ivjJRadioButton2.setText("Exponential shift");
                ivjJRadioButton2
                        .setMaximumSize(new java.awt.Dimension(100, 25));
                ivjJRadioButton2
                        .setMinimumSize(new java.awt.Dimension(120, 25));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJRadioButton2;
    }

    /**
     * Return the JRadioButton3 property value.
     * 
     * @return javax.swing.JRadioButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JRadioButton getJRadioButton3() {
        if (ivjJRadioButton3 == null) {
            try {
                ivjJRadioButton3 = new javax.swing.JRadioButton();
                ivjJRadioButton3.setName("JRadioButton3");
                ivjJRadioButton3.setPreferredSize(new java.awt.Dimension(100,
                        25));
                ivjJRadioButton3.setText("Rational shift");
                ivjJRadioButton3
                        .setMaximumSize(new java.awt.Dimension(100, 25));
                ivjJRadioButton3
                        .setMinimumSize(new java.awt.Dimension(100, 25));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJRadioButton3;
    }

    /**
     * Return the JTextArea property value.
     * 
     * @return com.jstatcom.component.MultiLineLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private com.jstatcom.component.MultiLineLabel getJTextArea() {
        if (ivjJTextArea == null) {
            try {
                ivjJTextArea = new com.jstatcom.component.MultiLineLabel();
                ivjJTextArea.setName("JTextArea");
                ivjJTextArea.setPreferredSize(new java.awt.Dimension(147, 90));
                ivjJTextArea
                        .setText("Search for the break date \nin the specified range. \nThe shift function used for the \nsearch procedure is a dummy \n(impulse or shift otherwise). ");
                ivjJTextArea.setBackground(java.awt.SystemColor.info);
                ivjJTextArea.setMinimumSize(new java.awt.Dimension(147, 90));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJTextArea;
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
     * Return the MaxButton property value.
     * 
     * @return javax.swing.JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JButton getMaxButton() {
        if (ivjMaxButton == null) {
            try {
                ivjMaxButton = new javax.swing.JButton();
                ivjMaxButton.setName("MaxButton");
                ivjMaxButton.setText("Default Range (max)");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjMaxButton;
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
                ivjMaxLagLength.setNumber(10.0);
                ivjMaxLagLength.setText("5");
                ivjMaxLagLength.setRangeExpr("[1,100]");
                ivjMaxLagLength
                        .setPreferredSize(new java.awt.Dimension(25, 20));
                ivjMaxLagLength.setEnabled(true);
                ivjMaxLagLength.setMinimumSize(new java.awt.Dimension(25, 20));
                ivjMaxLagLength.setEditable(true);
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
                        .setPreferredSize(new java.awt.Dimension(340, 350));
                ivjResultField.setMinimumSize(new java.awt.Dimension(340, 350));
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
     * Return the SearchBreak property value.
     * 
     * @return javax.swing.JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JButton getSearchBreak() {
        if (ivjSearchBreak == null) {
            try {
                ivjSearchBreak = new javax.swing.JButton();
                ivjSearchBreak.setName("SearchBreak");
                ivjSearchBreak.setText("Search >>");
                ivjSearchBreak.setMargin(new java.awt.Insets(2, 1, 2, 1));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSearchBreak;
    }

    /**
     * Return the SearchRange property value.
     * 
     * @return com.jstatcom.ts.TSDateRangeSelector
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private com.jstatcom.ts.TSDateRangeSelector getSearchRange() {
        if (ivjSearchRange == null) {
            try {
                ivjSearchRange = new com.jstatcom.ts.TSDateRangeSelector();
                ivjSearchRange.setName("SearchRange");
                ivjSearchRange.setMinNumberOfObs(2);
                ivjSearchRange.setPreferredSize(new java.awt.Dimension(80, 20));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSearchRange;
    }

    /**
     * Return the SeasDum property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getSeasDum() {
        if (ivjSeasDum == null) {
            try {
                ivjSeasDum = new javax.swing.JCheckBox();
                ivjSeasDum.setName("SeasDum");
                ivjSeasDum.setText("Seasonal dummies");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSeasDum;
    }

    /**
     * Return the timetrend property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getTimeTrend() {
        if (ivjTimeTrend == null) {
            try {
                ivjTimeTrend = new javax.swing.JCheckBox();
                ivjTimeTrend.setName("TimeTrend");
                ivjTimeTrend.setText("Time trend");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjTimeTrend;
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
        getSearchBreak().addActionListener(ivjEventHandler);
        getBack().addActionListener(ivjEventHandler);
        getExecuteSearch().addActionListener(ivjEventHandler);
        getExecute().addActionListener(ivjEventHandler);
        getLagLength().addPropertyChangeListener(ivjEventHandler);
        getMaxButton().addActionListener(ivjEventHandler);
    }

    /**
     * Initialize the class.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initialize() {
        try {
            // user code begin {1}
            TitledBorder title = new TitledBorder(new BevelBorder(
                    BevelBorder.LOWERED),
                    "Unit Root Test with Structural Break", TitledBorder.RIGHT,
                    TitledBorder.TOP);
            this.setBorder(title);
            ButtonGroup selectGroup = new javax.swing.ButtonGroup();
            selectGroup.add(getJRadioButton0());
            selectGroup.add(getJRadioButton1());
            selectGroup.add(getJRadioButton2());
            selectGroup.add(getJRadioButton3());

            // user code end
            setName("StructBreakPanel");
            setPreferredSize(new java.awt.Dimension(485, 350));
            setLayout(new java.awt.GridBagLayout());
            setSize(485, 350);
            setMinimumSize(new java.awt.Dimension(485, 350));
            setMaximumSize(new java.awt.Dimension(100000, 1000000));

            java.awt.GridBagConstraints constraintsResultField = new java.awt.GridBagConstraints();
            constraintsResultField.gridx = 0;
            constraintsResultField.gridy = 1;
            constraintsResultField.fill = java.awt.GridBagConstraints.BOTH;
            constraintsResultField.weightx = 1.0;
            constraintsResultField.weighty = 1.0;
            constraintsResultField.insets = new java.awt.Insets(0, 0, 5, 5);
            add(getResultField(), constraintsResultField);

            java.awt.GridBagConstraints constraintsConfigContainer = new java.awt.GridBagConstraints();
            constraintsConfigContainer.gridx = 1;
            constraintsConfigContainer.gridy = 0;
            constraintsConfigContainer.gridheight = 2;
            constraintsConfigContainer.fill = java.awt.GridBagConstraints.VERTICAL;
            constraintsConfigContainer.weighty = 1.0;
            constraintsConfigContainer.insets = new java.awt.Insets(0, 0, 5, 5);
            add(getConfigContainer(), constraintsConfigContainer);
            initConnections();
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}
        getJTextArea().setFont(CompSettings.smallFontDefault);

        // user code end
    }

    /**
     * Comment
     */
    private void maxButton_ActionEvents() {
        getSearchRange().maxRange();
    }

    /**
     * Comment
     */
    private void searchBreak_ActionEvents() {
        if (card == null)
            card = (CardLayout) getConfigContainer().getLayout();
        card.show(getConfigContainer(), getConfigPanel2().getName());
        return;
    }

    /**
     * Comment
     */
    public void selectionChanged() {

        if (global().get(UnitRootPanel.UR_DRANGE).isEmpty())
            return;
        TSDateRange range = global().get(UnitRootPanel.UR_DRANGE)
                .getJSCDRange().getTSDateRange();

        // Set the range of the date fields.
        int num = range.numOfObs();
        TSDate origStart = range.lowerBound();
        TSDateRange newRange = new TSDateRange(origStart
                .addPeriods(2 * getLagLength().getIntNumber() + 1), origStart
                .addPeriods(num - 3));
        getSearchRange().setEnclosingRange(newRange);
        getBreakDate().setEnclosingRange(newRange);

    }
}