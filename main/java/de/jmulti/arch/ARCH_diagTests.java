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
import com.jstatcom.model.ModelPanel;
import com.jstatcom.ts.TSDate;

import de.jmulti.proc.AutocorrCall;
import de.jmulti.proc.PlotTSCall;
import de.jmulti.proc.UnivarARCHLMCall;
import de.jmulti.proc.UnivarJBeraCall;
import de.jmulti.proc.UnivarRobNoArchCall;

/**
 * The panel provides diagnostics for ARCH analysis.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
final class ARCH_diagTests extends ModelPanel {
    private static final Logger log = Logger.getLogger(ARCH_diagTests.class);

    private JSCTypeDef dateRangeDef = null;

    private JPanel ivjJPanel1 = null;

    private ResultField ivjResultField = null;

    private JCheckBox ivjArchLM = null;

    private JLabel ivjJLabel1 = null;

    private NumSelector ivjLagsUniArch = null;

    private JCheckBox ivjNormal = null;

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

    private NumSelector ivjNoARCHLags = null;

    private JCheckBox ivjRobNoArch = null;

    class IvjEventHandler implements java.awt.event.ActionListener,
            java.awt.event.ItemListener {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == ARCH_diagTests.this.getExecute())
                connEtoC1();
        };

        public void itemStateChanged(java.awt.event.ItemEvent e) {
            if (e.getSource() == ARCH_diagTests.this.getRobNoArch())
                connPtoP1SetTarget();
            if (e.getSource() == ARCH_diagTests.this.getRobNoArch())
                connPtoP2SetTarget();
            if (e.getSource() == ARCH_diagTests.this.getArchLM())
                connPtoP3SetTarget();
            if (e.getSource() == ARCH_diagTests.this.getArchLM())
                connPtoP4SetTarget();
            if (e.getSource() == ARCH_diagTests.this.getAutocorr())
                connPtoP5SetTarget();
            if (e.getSource() == ARCH_diagTests.this.getAutocorr())
                connPtoP6SetTarget();
            if (e.getSource() == ARCH_diagTests.this.getPlotResidsCheck())
                connPtoP7SetTarget();
            if (e.getSource() == ARCH_diagTests.this.getAutocorr())
                connPtoP8SetTarget();
        };
    };

    /**
     * ARCH_diagTests constructor comment.
     */
    public ARCH_diagTests() {
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
        } catch (java.lang.Exception ivjExc) {
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
            getJLabel1().setEnabled(getRobNoArch().isSelected());
            // user code begin {1}
            // user code end
        } catch (java.lang.Exception ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connPtoP2SetTarget: (Portman.selected <-->NoARCHLags.enabled)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connPtoP2SetTarget() {
        /* Set the target from the source */
        try {
            getNoARCHLags().setEnabled(getRobNoArch().isSelected());
            // user code begin {1}
            // user code end
        } catch (java.lang.Exception ivjExc) {
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
        } catch (java.lang.Exception ivjExc) {
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
            getLagsUniArch().setEnabled(getArchLM().isSelected());
            // user code begin {1}
            // user code end
        } catch (java.lang.Exception ivjExc) {
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
        } catch (java.lang.Exception ivjExc) {
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
        } catch (java.lang.Exception ivjExc) {
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
        } catch (java.lang.Exception ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connPtoP8SetTarget: (Autocorr.selected <-->AutoSquared.enabled)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connPtoP8SetTarget() {
        /* Set the target from the source */
        try {
            getAutoSquared().setEnabled(getAutocorr().isSelected());
            // user code begin {1}
            // user code end
        } catch (java.lang.Exception ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * Makes the various calls.
     */
    private void execute_ActionEvents() {

        if (!getArchLM().isSelected() && !getRobNoArch().isSelected()
                && !getAutocorr().isSelected() && !getPlotIvar().isSelected()
                && !getPlotResidsCheck().isSelected()
                && !getNormal().isSelected()) {
            StdMessages.infoNothingSelected("Please select an option.");
            return;
        }
        JSCNArray res = upper().get(ARCHAnalysisPanel.resName).getJSCNArray();

        String modelName = upper().get(ARCHAnalysisPanel.modelName)
                .getJSCString().string();

        JSCSArray nam = new JSCSArray("nam", new String[] { modelName
                + " Residuals" });

        // Robustified test for no remaining ARCH.
        if (getRobNoArch().isSelected()) {
            int p = upper().get(ARCHAnalysisPanel.pName).getJSCInt().intVal();
            int q = upper().get(ARCHAnalysisPanel.qName).getJSCInt().intVal();

            PCall job = new UnivarRobNoArchCall(res, upper().get(
                    ARCHAnalysisPanel.ivarName).getJSCNArray(), upper().get(
                    ARCHAnalysisPanel.paramVector).getJSCNArray(), p, q,
                    getNoARCHLags().getIntNumber());
            job.setOutHolder(getResultField());
            job.setSymbolTable(local());
            job.execute();
        }
        ;
        // Arch-LM:
        if (getArchLM().isSelected()) {
            PCall job = new UnivarARCHLMCall(res, nam, getLagsUniArch()
                    .getIntNumber());
            job.setOutHolder(getResultField());
            job.setSymbolTable(local());
            job.execute();
        }
        ;
        // Nonnormality.
        if (getNormal().isSelected()) {
            PCall job = new UnivarJBeraCall(res, nam);
            job.setOutHolder(getResultField());
            job.setSymbolTable(local());
            job.execute();
        }
        ;
        // Autocorr plots.
        if (getAutocorr().isSelected()) {
            PCall jobAuto = new AutocorrCall(res, nam, getLagsAutocorr()
                    .getIntNumber(), getAutoSquared().isSelected(), false,
                    false);
            jobAuto.setSymbolTable(local());
            jobAuto.execute();
        }
        ;

        TSDate startDate = global().get(dateRangeDef).getJSCDRange()
                .getTSDateRange().lowerBound();
        // Plot Resids.
        if (getPlotResidsCheck().isSelected()) {
            JSCSArray name = null;
            if (getPlotSquaredRes().isSelected())
                name = new JSCSArray("res_nam", new String[] { "Squared Std. "
                        + nam.stringAt(0, 0) });
            else
                name = new JSCSArray("res_nam", new String[] { "Std. "
                        + nam.stringAt(0, 0) });

            PCall jobPlot = new PlotTSCall(res, startDate, name, false, true);
            jobPlot.execute();
        }
        // Plot Ivar process.
        if (getPlotIvar().isSelected()) {
            JSCSArray name = new JSCSArray("ivar_nam",
                    new String[] { "Std. Dev. Process for " + modelName
                            + " Model" });

            PCall jobPlot = new PlotTSCall(upper().get(
                    ARCHAnalysisPanel.ivarName).getJSCNArray(), startDate,
                    name, false, true);
            jobPlot.execute();
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
            } catch (java.lang.Exception ivjExc) {
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
            } catch (java.lang.Exception ivjExc) {
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
            } catch (java.lang.Exception ivjExc) {
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
            } catch (java.lang.Exception ivjExc) {
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
            } catch (java.lang.Exception ivjExc) {
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
            } catch (java.lang.Exception ivjExc) {
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
            } catch (java.lang.Exception ivjExc) {
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
                ivjJPanel1.setPreferredSize(new java.awt.Dimension(550, 150));

                java.awt.GridBagConstraints constraintsRobNoArch = new java.awt.GridBagConstraints();
                constraintsRobNoArch.gridx = 0;
                constraintsRobNoArch.gridy = 0;
                constraintsRobNoArch.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsRobNoArch.insets = new java.awt.Insets(10, 10, 0, 0);
                getJPanel1().add(getRobNoArch(), constraintsRobNoArch);

                java.awt.GridBagConstraints constraintsJLabel1 = new java.awt.GridBagConstraints();
                constraintsJLabel1.gridx = 1;
                constraintsJLabel1.gridy = 0;
                constraintsJLabel1.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel1.insets = new java.awt.Insets(10, 30, 0, 0);
                getJPanel1().add(getJLabel1(), constraintsJLabel1);

                java.awt.GridBagConstraints constraintsNoARCHLags = new java.awt.GridBagConstraints();
                constraintsNoARCHLags.gridx = 2;
                constraintsNoARCHLags.gridy = 0;
                constraintsNoARCHLags.insets = new java.awt.Insets(10, 0, 0, 0);
                getJPanel1().add(getNoARCHLags(), constraintsNoARCHLags);

                java.awt.GridBagConstraints constraintsNormal = new java.awt.GridBagConstraints();
                constraintsNormal.gridx = 5;
                constraintsNormal.gridy = 0;
                constraintsNormal.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsNormal.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsNormal.weightx = 1.0;
                constraintsNormal.insets = new java.awt.Insets(10, 20, 0, 0);
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
                constraintsJLabel2.insets = new java.awt.Insets(5, 30, 0, 0);
                getJPanel1().add(getJLabel2(), constraintsJLabel2);

                java.awt.GridBagConstraints constraintsLagsUniArch = new java.awt.GridBagConstraints();
                constraintsLagsUniArch.gridx = 2;
                constraintsLagsUniArch.gridy = 1;
                constraintsLagsUniArch.anchor = java.awt.GridBagConstraints.NORTH;
                constraintsLagsUniArch.insets = new java.awt.Insets(5, 0, 0, 0);
                getJPanel1().add(getLagsUniArch(), constraintsLagsUniArch);

                java.awt.GridBagConstraints constraintsAutocorr = new java.awt.GridBagConstraints();
                constraintsAutocorr.gridx = 0;
                constraintsAutocorr.gridy = 2;
                constraintsAutocorr.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsAutocorr.anchor = java.awt.GridBagConstraints.NORTH;
                constraintsAutocorr.insets = new java.awt.Insets(5, 10, 0, 0);
                getJPanel1().add(getAutocorr(), constraintsAutocorr);

                java.awt.GridBagConstraints constraintsExecute = new java.awt.GridBagConstraints();
                constraintsExecute.gridx = 5;
                constraintsExecute.gridy = 3;
                constraintsExecute.gridheight = 2;
                constraintsExecute.anchor = java.awt.GridBagConstraints.SOUTH;
                constraintsExecute.weighty = 1.0;
                constraintsExecute.insets = new java.awt.Insets(0, 40, 10, 10);
                getJPanel1().add(getExecute(), constraintsExecute);

                java.awt.GridBagConstraints constraintsJLabel4 = new java.awt.GridBagConstraints();
                constraintsJLabel4.gridx = 1;
                constraintsJLabel4.gridy = 2;
                constraintsJLabel4.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel4.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsJLabel4.insets = new java.awt.Insets(5, 30, 0, 0);
                getJPanel1().add(getJLabel4(), constraintsJLabel4);

                java.awt.GridBagConstraints constraintsLagsAutocorr = new java.awt.GridBagConstraints();
                constraintsLagsAutocorr.gridx = 2;
                constraintsLagsAutocorr.gridy = 2;
                constraintsLagsAutocorr.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsLagsAutocorr.insets = new java.awt.Insets(5, 0, 0, 0);
                getJPanel1().add(getLagsAutocorr(), constraintsLagsAutocorr);

                java.awt.GridBagConstraints constraintsPlotResidsCheck = new java.awt.GridBagConstraints();
                constraintsPlotResidsCheck.gridx = 3;
                constraintsPlotResidsCheck.gridy = 1;
                constraintsPlotResidsCheck.gridwidth = 3;
                constraintsPlotResidsCheck.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsPlotResidsCheck.anchor = java.awt.GridBagConstraints.NORTH;
                constraintsPlotResidsCheck.insets = new java.awt.Insets(5, 20,
                        0, 0);
                getJPanel1().add(getPlotResidsCheck(),
                        constraintsPlotResidsCheck);

                java.awt.GridBagConstraints constraintsPlotSquaredRes = new java.awt.GridBagConstraints();
                constraintsPlotSquaredRes.gridx = 3;
                constraintsPlotSquaredRes.gridy = 2;
                constraintsPlotSquaredRes.gridwidth = 3;
                constraintsPlotSquaredRes.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsPlotSquaredRes.anchor = java.awt.GridBagConstraints.NORTH;
                constraintsPlotSquaredRes.insets = new java.awt.Insets(0, 30,
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
                // user code begin {1}
                // user code end
            } catch (java.lang.Exception ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJPanel1;
    }

    /**
     * Return the LagsAutocorr property value.
     * 
     * @return NumSelector
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
            } catch (java.lang.Exception ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjLagsAutocorr;
    }

    /**
     * Return the LagsUniArch property value.
     * 
     * @return NumSelector
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private NumSelector getLagsUniArch() {
        if (ivjLagsUniArch == null) {
            try {
                ivjLagsUniArch = new NumSelector();
                ivjLagsUniArch.setName("LagsUniArch");
                ivjLagsUniArch.setPreferredSize(new java.awt.Dimension(55, 21));
                ivjLagsUniArch.setNumber(4.0);
                ivjLagsUniArch.setMinimumSize(new java.awt.Dimension(55, 21));
                ivjLagsUniArch.setRangeExpr("[1,100]");
                // user code begin {1}
                // user code end
            } catch (java.lang.Exception ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjLagsUniArch;
    }

    /**
     * Return the MaxLagsPort property value.
     * 
     * @return NumSelector
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private NumSelector getNoARCHLags() {
        if (ivjNoARCHLags == null) {
            try {
                ivjNoARCHLags = new NumSelector();
                ivjNoARCHLags.setName("NoARCHLags");
                ivjNoARCHLags.setPreferredSize(new java.awt.Dimension(55, 21));
                ivjNoARCHLags.setNumber(1.0);
                ivjNoARCHLags.setMinimumSize(new java.awt.Dimension(55, 21));
                ivjNoARCHLags.setRangeExpr("[1,15]");
                // user code begin {1}
                // user code end
            } catch (java.lang.Exception ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjNoARCHLags;
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
                ivjNormal.setMaximumSize(new java.awt.Dimension(200, 22));
                ivjNormal.setSelected(true);
                ivjNormal.setPreferredSize(new java.awt.Dimension(120, 21));
                ivjNormal.setMinimumSize(new java.awt.Dimension(150, 22));
                // user code begin {1}
                // user code end
            } catch (java.lang.Exception ivjExc) {
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
                ivjPlotIvar.setText("Plot std. dev. process");
                ivjPlotIvar.setMinimumSize(new java.awt.Dimension(150, 22));
                ivjPlotIvar.setMaximumSize(new java.awt.Dimension(200, 22));
                // user code begin {1}
                // user code end
            } catch (java.lang.Exception ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjPlotIvar;
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
                ivjPlotResidsCheck.setPreferredSize(new java.awt.Dimension(150,
                        21));
                ivjPlotResidsCheck.setText("Plot residuals");
                ivjPlotResidsCheck.setMinimumSize(new java.awt.Dimension(150,
                        22));
                ivjPlotResidsCheck.setMaximumSize(new java.awt.Dimension(200,
                        22));
                // user code begin {1}
                // user code end
            } catch (java.lang.Exception ivjExc) {
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
            } catch (java.lang.Exception ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjPlotSquaredRes;
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
            } catch (java.lang.Exception ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjResultField;
    }

    /**
     * Return the Portman property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getRobNoArch() {
        if (ivjRobNoArch == null) {
            try {
                ivjRobNoArch = new javax.swing.JCheckBox();
                ivjRobNoArch.setName("RobNoArch");
                ivjRobNoArch.setSelected(true);
                ivjRobNoArch.setPreferredSize(new java.awt.Dimension(120, 21));
                ivjRobNoArch.setText("No remaining ARCH");
                // user code begin {1}
                // user code end
            } catch (java.lang.Exception ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjRobNoArch;
    }

    /**
     * Called whenever the part throws an exception.
     * 
     * @param exception
     *            java.lang.Exception
     */
    private void handleException(java.lang.Exception exception) {

        log.error("Unhandled Exception", exception);
    }

    /**
     * Initializes connections
     * 
     * @exception java.lang.Exception
     *                The exception description.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initConnections() throws java.lang.Exception {
        // user code begin {1}
        // user code end
        getExecute().addActionListener(ivjEventHandler);
        getRobNoArch().addItemListener(ivjEventHandler);
        getArchLM().addItemListener(ivjEventHandler);
        getAutocorr().addItemListener(ivjEventHandler);
        getPlotResidsCheck().addItemListener(ivjEventHandler);
        connPtoP1SetTarget();
        connPtoP2SetTarget();
        connPtoP3SetTarget();
        connPtoP4SetTarget();
        connPtoP5SetTarget();
        connPtoP6SetTarget();
        connPtoP7SetTarget();
        connPtoP8SetTarget();
    }

    /**
     * Initialize the class.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initialize() {
        try {
            // user code begin {1}
            // user code end
            setName("ARCH_diagTests");
            setLayout(new java.awt.BorderLayout());
            setSize(661, 428);
            add(getResultField(), "Center");
            add(getJPanel1(), "North");
            initConnections();
        } catch (java.lang.Exception ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}
        // user code end
    }

    /**
     * @return
     */
    public JSCTypeDef getDateRangeDef() {
        return dateRangeDef;
    }

    /**
     * @param dateRangeDef
     */
    public void setDateRangeDef(JSCTypeDef dateRangeDef) {
        this.dateRangeDef = dateRangeDef;
    }
}