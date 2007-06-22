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

package de.jmulti.arch;

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
import com.jstatcom.model.JSCTypes;
import com.jstatcom.model.ModelPanel;
import com.jstatcom.ts.TSDate;
import com.jstatcom.util.UData;

import de.jmulti.proc.AutocorrCall;
import de.jmulti.proc.MultvarArchLMCall;
import de.jmulti.proc.MultvarNonNormCall;
import de.jmulti.proc.MultvarPortmanCall;
import de.jmulti.proc.PlotGarchIvarCall;
import de.jmulti.proc.PlotTSCall;
import de.jmulti.proc.UnivarJBeraCall;
import de.jmulti.tools.ModelTypes;

/**
 * The panel provides diagnostics for multivariate GARCH analysis.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
final class MultARCH_diagTests extends ModelPanel {
    private static final Logger log = Logger
            .getLogger(MultARCH_diagTests.class);

    private JSCTypeDef origDataDef = new JSCTypeDef("MGARCH_DATA",
            JSCTypes.NARRAY);

    private JSCTypeDef dateRangeDef = null;

    private JPanel ivjJPanel1 = null;

    private ResultField ivjResultField = null;

    private JCheckBox ivjArchLM = null;

    private JLabel ivjJLabel1 = null;

    private NumSelector ivjMaxLagsPort = null;

    private JCheckBox ivjNormal = null;

    private JCheckBox ivjPortman = null;

    private JCheckBox ivjAutocorr = null;

    private JLabel ivjJLabel2 = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private JButton ivjExecute = null;

    private JLabel ivjJLabel4 = null;

    private NumSelector ivjLagsAutocorr = null;

    private JCheckBox ivjPlotResidsCheck = null;

    private JCheckBox ivjPlotSquaredRes = null;

    private JCheckBox ivjPlotIvar = null;

    private JCheckBox ivjAutoSquared = null;

    private NumSelector ivjLagsArch = null;

    private JCheckBox ivjPlotMultWindows = null;

    private JCheckBox ivjUnivar = null;

    class IvjEventHandler implements java.awt.event.ActionListener,
            java.awt.event.ItemListener {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == MultARCH_diagTests.this.getExecute())
                connEtoC1();
        };

        public void itemStateChanged(java.awt.event.ItemEvent e) {
            if (e.getSource() == MultARCH_diagTests.this.getPortman())
                connPtoP1SetTarget();
            if (e.getSource() == MultARCH_diagTests.this.getPortman())
                connPtoP2SetTarget();
            if (e.getSource() == MultARCH_diagTests.this.getArchLM())
                connPtoP3SetTarget();
            if (e.getSource() == MultARCH_diagTests.this.getArchLM())
                connPtoP4SetTarget();
            if (e.getSource() == MultARCH_diagTests.this.getAutocorr())
                connPtoP5SetTarget();
            if (e.getSource() == MultARCH_diagTests.this.getAutocorr())
                connPtoP6SetTarget();
            if (e.getSource() == MultARCH_diagTests.this.getPlotIvar())
                connPtoP8SetTarget();
            if (e.getSource() == MultARCH_diagTests.this.getPlotResidsCheck())
                connPtoP7SetTarget();
            if (e.getSource() == MultARCH_diagTests.this.getPlotResidsCheck())
                connPtoP9SetTarget();
            if (e.getSource() == MultARCH_diagTests.this.getAutocorr())
                connPtoP10SetTarget();
        };
    };

    /**
     * ARCH_diagTests constructor comment.
     */
    public MultARCH_diagTests() {
        super();
        initialize();
    }

    /**
     * connEtoC1: (Execute.action. --> CAFPE_diagTests.execute_ActionEvents()V)
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
     * connPtoP10SetTarget: (Autocorr.selected <-->AutoSquared.enabled)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connPtoP10SetTarget() {
        /* Set the target from the source */
        try {
            getAutoSquared().setEnabled(getAutocorr().isSelected());
            // user code begin {1}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connPtoP1SetTarget: (Portman.selected <-->JLabel1.enabled)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connPtoP1SetTarget() {
        /* Set the target from the source */
        try {
            getJLabel1().setEnabled(getPortman().isSelected());
            // user code begin {1}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connPtoP2SetTarget: (Portman.selected <-->MaxLagsPort.enabled)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connPtoP2SetTarget() {
        /* Set the target from the source */
        try {
            getMaxLagsPort().setEnabled(getPortman().isSelected());
            // user code begin {1}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connPtoP3SetTarget: (ArchLM.selected <-->JLabel2.enabled)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connPtoP3SetTarget() {
        /* Set the target from the source */
        try {
            getJLabel2().setEnabled(getArchLM().isSelected());
            // user code begin {1}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connPtoP4SetTarget: (ArchLM.selected <-->LagsUniArch.enabled)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connPtoP4SetTarget() {
        /* Set the target from the source */
        try {
            getLagsArch().setEnabled(getArchLM().isSelected());
            // user code begin {1}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connPtoP5SetTarget: (Autocorr.selected <-->JLabel4.enabled)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connPtoP5SetTarget() {
        /* Set the target from the source */
        try {
            getJLabel4().setEnabled(getAutocorr().isSelected());
            // user code begin {1}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connPtoP6SetTarget: (Autocorr.selected <-->LagsAutocorr.enabled)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connPtoP6SetTarget() {
        /* Set the target from the source */
        try {
            getLagsAutocorr().setEnabled(getAutocorr().isSelected());
            // user code begin {1}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connPtoP7SetTarget: (PlotResidsCheck.selected <-->
     * PlotSquaredRes.enabled)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connPtoP7SetTarget() {
        /* Set the target from the source */
        try {
            getPlotSquaredRes().setEnabled(getPlotResidsCheck().isSelected());
            // user code begin {1}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connPtoP8SetTarget: (PlotIvar.selected <-->ShowUnivar.enabled)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connPtoP8SetTarget() {
        /* Set the target from the source */
        try {
            getUnivar().setEnabled(getPlotIvar().isSelected());
            // user code begin {1}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connPtoP9SetTarget: (PlotResidsCheck.selected <-->
     * PlotMultWindows.enabled)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connPtoP9SetTarget() {
        /* Set the target from the source */
        try {
            getPlotMultWindows().setEnabled(getPlotResidsCheck().isSelected());
            // user code begin {1}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * Multivariate diagnostic tests for MGARCH(1,1) Residuals.
     */
    private void execute_ActionEvents() {

        if (!getArchLM().isSelected() && !getPortman().isSelected()
                && !getAutocorr().isSelected() && !getPlotIvar().isSelected()
                && !getPlotResidsCheck().isSelected()
                && !getNormal().isSelected()) {
            StdMessages.infoNothingSelected("Please select an option.");
            return;
        }

        JSCNArray res = upper().get(MultARCHAnalysisPanel.resName)
                .getJSCNArray();
        JSCSArray resNames = upper().get(MultARCHAnalysisPanel.resStrings)
                .getJSCSArray();

        // Portmanteau test.
        if (getPortman().isSelected()) {
            PCall job = new MultvarPortmanCall(upper(), local(),
                    ModelTypes.MGARCH, getMaxLagsPort().getIntNumber());
            job.setOutHolder(getResultField());
            job.execute();
        }
        // Nonnormality.
        if (getNormal().isSelected()) {
            PCall jobM = new MultvarNonNormCall(res, 1);
            jobM.setOutHolder(getResultField());
            jobM.setSymbolTable(local());
            jobM.execute();

            PCall job = new UnivarJBeraCall(res, resNames);
            job.setOutHolder(getResultField());
            job.setSymbolTable(local());
            job.execute();
        }

        // Multivariate ARCH-LM.
        if (getArchLM().isSelected()) {
            PCall job = new MultvarArchLMCall(res, getLagsArch().getIntNumber());
            job.setOutHolder(getResultField());
            job.setSymbolTable(local());
            job.execute();
        }

        // Autocorr.
        if (getAutocorr().isSelected()) {
            PCall job = new AutocorrCall(res, resNames, getLagsAutocorr()
                    .getIntNumber(), getAutoSquared().isSelected(), false,
                    false);

            job.setSymbolTable(local());
            job.setOutHolder(getResultField());
            job.execute();
        }
        TSDate startDate = global().get(dateRangeDef).getJSCDRange()
                .getTSDateRange().lowerBound();

        // Plot residuals.
        if (getPlotResidsCheck().isSelected()) {
            JSCSArray nn = null;
            if (getPlotSquaredRes().isSelected())
                nn = UData.appendSuffix(resNames, " (squared, std)", resNames
                        .name());
            else
                nn = UData.appendSuffix(resNames, " (std)", resNames.name());
            PCall jobPlot = new PlotTSCall(res, startDate, nn,
                    getPlotMultWindows().isSelected(), true);
            jobPlot.execute();
        }

        // Plot variance process.
        if (getPlotIvar().isSelected()) {
            PCall job = new PlotGarchIvarCall(upper().get(
                    MultARCHAnalysisPanel.ivarName).getJSCNArray(), global()
                    .get(origDataDef).getJSCNArray(), startDate, getUnivar()
                    .isSelected());
            job.setOutHolder(getResultField());
            job.execute();

        }

    }

    /**
     * Return the ArchLM property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getArchLM() {
        if (ivjArchLM == null) {
            try {
                ivjArchLM = new javax.swing.JCheckBox();
                ivjArchLM.setName("ArchLM");
                ivjArchLM.setSelected(true);
                ivjArchLM.setPreferredSize(new java.awt.Dimension(120, 21));
                ivjArchLM.setText("ARCH-LM");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjArchLM;
    }

    /**
     * Return the Autocorr property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getAutocorr() {
        if (ivjAutocorr == null) {
            try {
                ivjAutocorr = new javax.swing.JCheckBox();
                ivjAutocorr.setName("Autocorr");
                ivjAutocorr.setPreferredSize(new java.awt.Dimension(130, 21));
                ivjAutocorr.setText("Autocorrelation");
                ivjAutocorr.setMinimumSize(new java.awt.Dimension(120, 22));
                ivjAutocorr.setMaximumSize(new java.awt.Dimension(200, 22));
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

    /**
     * Return the AutoSquared property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getAutoSquared() {
        if (ivjAutoSquared == null) {
            try {
                ivjAutoSquared = new javax.swing.JCheckBox();
                ivjAutoSquared.setName("AutoSquared");
                ivjAutoSquared.setText("Of squared");
                ivjAutoSquared.setMaximumSize(new java.awt.Dimension(200, 22));
                ivjAutoSquared
                        .setPreferredSize(new java.awt.Dimension(130, 21));
                ivjAutoSquared.setMinimumSize(new java.awt.Dimension(120, 22));
                ivjAutoSquared.setEnabled(false);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjAutoSquared;
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
                ivjExecute.setPreferredSize(new java.awt.Dimension(125, 25));
                ivjExecute.setText("Execute");
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
                ivjJLabel1.setPreferredSize(new java.awt.Dimension(30, 21));
                ivjJLabel1.setText("lags");
                ivjJLabel1.setMinimumSize(new java.awt.Dimension(24, 21));
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
                ivjJLabel2.setPreferredSize(new java.awt.Dimension(30, 21));
                ivjJLabel2.setText("lags");
                ivjJLabel2.setMinimumSize(new java.awt.Dimension(24, 21));
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
                ivjJLabel4.setPreferredSize(new java.awt.Dimension(30, 21));
                ivjJLabel4.setText("lags");
                ivjJLabel4.setMinimumSize(new java.awt.Dimension(24, 21));
                ivjJLabel4.setEnabled(false);
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
                ivjJPanel1.setBorder(new BevelBorder(BevelBorder.LOWERED));
                ivjJPanel1.setLayout(new java.awt.GridBagLayout());
                ivjJPanel1.setPreferredSize(new java.awt.Dimension(550, 170));

                java.awt.GridBagConstraints constraintsPortman = new java.awt.GridBagConstraints();
                constraintsPortman.gridx = 0;
                constraintsPortman.gridy = 0;
                constraintsPortman.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsPortman.insets = new java.awt.Insets(10, 10, 0, 0);
                getJPanel1().add(getPortman(), constraintsPortman);

                java.awt.GridBagConstraints constraintsJLabel1 = new java.awt.GridBagConstraints();
                constraintsJLabel1.gridx = 1;
                constraintsJLabel1.gridy = 0;
                constraintsJLabel1.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel1.insets = new java.awt.Insets(10, 10, 0, 0);
                getJPanel1().add(getJLabel1(), constraintsJLabel1);

                java.awt.GridBagConstraints constraintsMaxLagsPort = new java.awt.GridBagConstraints();
                constraintsMaxLagsPort.gridx = 2;
                constraintsMaxLagsPort.gridy = 0;
                constraintsMaxLagsPort.anchor = java.awt.GridBagConstraints.WEST;
                constraintsMaxLagsPort.insets = new java.awt.Insets(10, 0, 0, 0);
                getJPanel1().add(getMaxLagsPort(), constraintsMaxLagsPort);

                java.awt.GridBagConstraints constraintsNormal = new java.awt.GridBagConstraints();
                constraintsNormal.gridx = 3;
                constraintsNormal.gridy = 0;
                constraintsNormal.gridwidth = 3;
                constraintsNormal.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsNormal.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsNormal.insets = new java.awt.Insets(10, 30, 0, 0);
                getJPanel1().add(getNormal(), constraintsNormal);

                java.awt.GridBagConstraints constraintsArchLM = new java.awt.GridBagConstraints();
                constraintsArchLM.gridx = 0;
                constraintsArchLM.gridy = 1;
                constraintsArchLM.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsArchLM.anchor = java.awt.GridBagConstraints.NORTH;
                constraintsArchLM.insets = new java.awt.Insets(5, 10, 0, 0);
                getJPanel1().add(getArchLM(), constraintsArchLM);

                java.awt.GridBagConstraints constraintsJLabel2 = new java.awt.GridBagConstraints();
                constraintsJLabel2.gridx = 1;
                constraintsJLabel2.gridy = 1;
                constraintsJLabel2.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel2.anchor = java.awt.GridBagConstraints.NORTH;
                constraintsJLabel2.insets = new java.awt.Insets(5, 10, 0, 0);
                getJPanel1().add(getJLabel2(), constraintsJLabel2);

                java.awt.GridBagConstraints constraintsLagsArch = new java.awt.GridBagConstraints();
                constraintsLagsArch.gridx = 2;
                constraintsLagsArch.gridy = 1;
                constraintsLagsArch.anchor = java.awt.GridBagConstraints.WEST;
                constraintsLagsArch.insets = new java.awt.Insets(5, 0, 0, 0);
                getJPanel1().add(getLagsArch(), constraintsLagsArch);

                java.awt.GridBagConstraints constraintsAutocorr = new java.awt.GridBagConstraints();
                constraintsAutocorr.gridx = 0;
                constraintsAutocorr.gridy = 2;
                constraintsAutocorr.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsAutocorr.anchor = java.awt.GridBagConstraints.NORTH;
                constraintsAutocorr.insets = new java.awt.Insets(5, 10, 0, 0);
                getJPanel1().add(getAutocorr(), constraintsAutocorr);

                java.awt.GridBagConstraints constraintsExecute = new java.awt.GridBagConstraints();
                constraintsExecute.gridx = 3;
                constraintsExecute.gridy = 5;
                constraintsExecute.gridwidth = 4;
                constraintsExecute.gridheight = 4;
                constraintsExecute.anchor = java.awt.GridBagConstraints.SOUTH;
                constraintsExecute.weighty = 1.0;
                constraintsExecute.insets = new java.awt.Insets(0, 40, 10, 10);
                getJPanel1().add(getExecute(), constraintsExecute);

                java.awt.GridBagConstraints constraintsJLabel4 = new java.awt.GridBagConstraints();
                constraintsJLabel4.gridx = 1;
                constraintsJLabel4.gridy = 2;
                constraintsJLabel4.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel4.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsJLabel4.insets = new java.awt.Insets(5, 10, 0, 0);
                getJPanel1().add(getJLabel4(), constraintsJLabel4);

                java.awt.GridBagConstraints constraintsLagsAutocorr = new java.awt.GridBagConstraints();
                constraintsLagsAutocorr.gridx = 2;
                constraintsLagsAutocorr.gridy = 2;
                constraintsLagsAutocorr.anchor = java.awt.GridBagConstraints.WEST;
                constraintsLagsAutocorr.insets = new java.awt.Insets(5, 0, 0, 0);
                getJPanel1().add(getLagsAutocorr(), constraintsLagsAutocorr);

                java.awt.GridBagConstraints constraintsPlotResidsCheck = new java.awt.GridBagConstraints();
                constraintsPlotResidsCheck.gridx = 3;
                constraintsPlotResidsCheck.gridy = 1;
                constraintsPlotResidsCheck.gridwidth = 3;
                constraintsPlotResidsCheck.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsPlotResidsCheck.anchor = java.awt.GridBagConstraints.NORTH;
                constraintsPlotResidsCheck.insets = new java.awt.Insets(5, 30,
                        0, 0);
                getJPanel1().add(getPlotResidsCheck(),
                        constraintsPlotResidsCheck);

                java.awt.GridBagConstraints constraintsPlotSquaredRes = new java.awt.GridBagConstraints();
                constraintsPlotSquaredRes.gridx = 5;
                constraintsPlotSquaredRes.gridy = 2;
                constraintsPlotSquaredRes.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsPlotSquaredRes.anchor = java.awt.GridBagConstraints.NORTH;
                constraintsPlotSquaredRes.insets = new java.awt.Insets(0, 40,
                        0, 0);
                getJPanel1()
                        .add(getPlotSquaredRes(), constraintsPlotSquaredRes);

                java.awt.GridBagConstraints constraintsPlotIvar = new java.awt.GridBagConstraints();
                constraintsPlotIvar.gridx = 0;
                constraintsPlotIvar.gridy = 4;
                constraintsPlotIvar.gridwidth = 3;
                constraintsPlotIvar.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsPlotIvar.anchor = java.awt.GridBagConstraints.NORTH;
                constraintsPlotIvar.insets = new java.awt.Insets(5, 10, 0, 0);
                getJPanel1().add(getPlotIvar(), constraintsPlotIvar);

                java.awt.GridBagConstraints constraintsAutoSquared = new java.awt.GridBagConstraints();
                constraintsAutoSquared.gridx = 0;
                constraintsAutoSquared.gridy = 3;
                constraintsAutoSquared.gridwidth = 3;
                constraintsAutoSquared.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsAutoSquared.anchor = java.awt.GridBagConstraints.NORTH;
                constraintsAutoSquared.insets = new java.awt.Insets(0, 20, 0, 0);
                getJPanel1().add(getAutoSquared(), constraintsAutoSquared);

                java.awt.GridBagConstraints constraintsUnivar = new java.awt.GridBagConstraints();
                constraintsUnivar.gridx = 0;
                constraintsUnivar.gridy = 5;
                constraintsUnivar.gridwidth = 3;
                constraintsUnivar.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsUnivar.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsUnivar.insets = new java.awt.Insets(0, 20, 0, 0);
                getJPanel1().add(getUnivar(), constraintsUnivar);

                java.awt.GridBagConstraints constraintsPlotMultWindows = new java.awt.GridBagConstraints();
                constraintsPlotMultWindows.gridx = 6;
                constraintsPlotMultWindows.gridy = 2;
                constraintsPlotMultWindows.gridwidth = 3;
                constraintsPlotMultWindows.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsPlotMultWindows.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsPlotMultWindows.weightx = 1.0;
                getJPanel1().add(getPlotMultWindows(),
                        constraintsPlotMultWindows);
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
     * Return the LagsUniArch property value.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private NumSelector getLagsArch() {
        if (ivjLagsArch == null) {
            try {
                ivjLagsArch = new NumSelector();
                ivjLagsArch.setName("LagsArch");
                ivjLagsArch.setPreferredSize(new java.awt.Dimension(55, 21));
                ivjLagsArch.setNumber(4.0);
                ivjLagsArch.setMinimumSize(new java.awt.Dimension(55, 21));
                ivjLagsArch.setRangeExpr("[1,100]");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjLagsArch;
    }

    /**
     * Return the LagsAutocorr property value.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private NumSelector getLagsAutocorr() {
        if (ivjLagsAutocorr == null) {
            try {
                ivjLagsAutocorr = new NumSelector();
                ivjLagsAutocorr.setName("LagsAutocorr");
                ivjLagsAutocorr.setNumber(16.0);
                ivjLagsAutocorr.setRangeExpr("[1,100]");
                ivjLagsAutocorr
                        .setPreferredSize(new java.awt.Dimension(55, 21));
                ivjLagsAutocorr.setMinimumSize(new java.awt.Dimension(55, 21));
                ivjLagsAutocorr.setEnabled(false);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjLagsAutocorr;
    }

    /**
     * Return the MaxLagsPort property value.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private NumSelector getMaxLagsPort() {
        if (ivjMaxLagsPort == null) {
            try {
                ivjMaxLagsPort = new NumSelector();
                ivjMaxLagsPort.setName("MaxLagsPort");
                ivjMaxLagsPort.setPreferredSize(new java.awt.Dimension(55, 21));
                ivjMaxLagsPort.setNumber(16.0);
                ivjMaxLagsPort.setMinimumSize(new java.awt.Dimension(55, 21));
                ivjMaxLagsPort.setRangeExpr("[1,100]");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjMaxLagsPort;
    }

    /**
     * Return the Normal property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getNormal() {
        if (ivjNormal == null) {
            try {
                ivjNormal = new javax.swing.JCheckBox();
                ivjNormal.setName("Normal");
                ivjNormal.setText("Jarque-Bera test");
                ivjNormal.setSelected(true);
                ivjNormal.setPreferredSize(new java.awt.Dimension(100, 21));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjNormal;
    }

    /**
     * Return the PlotIvar property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getPlotIvar() {
        if (ivjPlotIvar == null) {
            try {
                ivjPlotIvar = new javax.swing.JCheckBox();
                ivjPlotIvar.setName("PlotIvar");
                ivjPlotIvar.setPreferredSize(new java.awt.Dimension(150, 21));
                ivjPlotIvar.setText("Plot standard deviation process");
                ivjPlotIvar.setMinimumSize(new java.awt.Dimension(150, 22));
                ivjPlotIvar.setMaximumSize(new java.awt.Dimension(200, 22));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjPlotIvar;
    }

    /**
     * Return the PlotMultWindows property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getPlotMultWindows() {
        if (ivjPlotMultWindows == null) {
            try {
                ivjPlotMultWindows = new javax.swing.JCheckBox();
                ivjPlotMultWindows.setName("PlotMultWindows");
                ivjPlotMultWindows.setText("Multiple windows");
                ivjPlotMultWindows.setMaximumSize(new java.awt.Dimension(200,
                        22));
                ivjPlotMultWindows.setPreferredSize(new java.awt.Dimension(150,
                        21));
                ivjPlotMultWindows.setMinimumSize(new java.awt.Dimension(150,
                        22));
                ivjPlotMultWindows.setEnabled(false);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjPlotMultWindows;
    }

    /**
     * Return the PlotResidsCheck property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getPlotResidsCheck() {
        if (ivjPlotResidsCheck == null) {
            try {
                ivjPlotResidsCheck = new javax.swing.JCheckBox();
                ivjPlotResidsCheck.setName("PlotResidsCheck");
                ivjPlotResidsCheck.setPreferredSize(new java.awt.Dimension(100,
                        21));
                ivjPlotResidsCheck.setText("Plot residuals");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjPlotResidsCheck;
    }

    /**
     * Return the PlotSquaredRes property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getPlotSquaredRes() {
        if (ivjPlotSquaredRes == null) {
            try {
                ivjPlotSquaredRes = new javax.swing.JCheckBox();
                ivjPlotSquaredRes.setName("PlotSquaredRes");
                ivjPlotSquaredRes.setText("Squared");
                ivjPlotSquaredRes
                        .setMaximumSize(new java.awt.Dimension(200, 22));
                ivjPlotSquaredRes.setPreferredSize(new java.awt.Dimension(150,
                        21));
                ivjPlotSquaredRes
                        .setMinimumSize(new java.awt.Dimension(150, 22));
                ivjPlotSquaredRes.setEnabled(false);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjPlotSquaredRes;
    }

    /**
     * Return the Portman property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getPortman() {
        if (ivjPortman == null) {
            try {
                ivjPortman = new javax.swing.JCheckBox();
                ivjPortman.setName("Portman");
                ivjPortman.setSelected(true);
                ivjPortman.setPreferredSize(new java.awt.Dimension(120, 21));
                ivjPortman.setText("Portmanteau test");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjPortman;
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
     * Return the ShowUnivar property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getUnivar() {
        if (ivjUnivar == null) {
            try {
                ivjUnivar = new javax.swing.JCheckBox();
                ivjUnivar.setName("Univar");
                ivjUnivar.setSelected(true);
                ivjUnivar.setText("Univariate std. dev.");
                ivjUnivar.setEnabled(false);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjUnivar;
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
        getPortman().addItemListener(ivjEventHandler);
        getArchLM().addItemListener(ivjEventHandler);
        getAutocorr().addItemListener(ivjEventHandler);
        getPlotIvar().addItemListener(ivjEventHandler);
        getPlotResidsCheck().addItemListener(ivjEventHandler);
        connPtoP1SetTarget();
        connPtoP2SetTarget();
        connPtoP3SetTarget();
        connPtoP4SetTarget();
        connPtoP5SetTarget();
        connPtoP6SetTarget();
        connPtoP8SetTarget();
        connPtoP7SetTarget();
        connPtoP9SetTarget();
        connPtoP10SetTarget();
    }

    /**
     * Initialize the class.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initialize() {
        try {
            // user code begin {1}
            // user code end
            setName("MultARCH_diagTests");
            setLayout(new java.awt.BorderLayout());
            setSize(661, 428);
            add(getResultField(), "Center");
            add(getJPanel1(), "North");
            initConnections();
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}
        // user code end
    }

    /**
     * 
     * @param newOrigDataName
     *            String
     */
    void setOrigDataName(String newOrigDataName) {
        if (newOrigDataName == null)
            origDataDef = null;
        else
            origDataDef = new JSCTypeDef(newOrigDataName, JSCTypes.NARRAY);
    }

    /**
     * @return
     */
    JSCTypeDef getDateRangeDef() {
        return dateRangeDef;
    }

    /**
     * @param dateRangeName
     */
    void setDateRangeDef(JSCTypeDef dateRangeName) {
        this.dateRangeDef = dateRangeName;
    }
}