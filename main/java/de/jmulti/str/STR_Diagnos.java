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

package de.jmulti.str;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import com.jstatcom.component.NumSelector;
import com.jstatcom.component.ResultField;
import com.jstatcom.component.StdMessages;
import com.jstatcom.component.TopFrameReference;
import com.jstatcom.engine.PCall;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.ModelPanel;
import com.jstatcom.util.UMatrix;

import de.jmulti.proc.STRAutoCorrCall;
import de.jmulti.proc.STRParaConstCall;
import de.jmulti.proc.STRRemNonLinCall;
import de.jmulti.proc.UnivarARCHLMCall;
import de.jmulti.proc.UnivarJBeraCall;

/**
 * Panel for configuration of misc. diagnostic tests for residuals in STR
 * analysis.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */

final class STR_Diagnos extends ModelPanel {
    private static final Logger log = Logger.getLogger(STR_Diagnos.class);

    private STR_ConfigParamConstTest strParaconsConfig = null;

    private STR_ConfigRemNonlinTest strRemnonlinConfig = null;

    private JPanel ivjControlPanel = null;

    private ResultField ivjResultField = null;

    private JCheckBox ivjAutocorr = null;

    private NumSelector ivjAutocorrLags = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private JButton ivjExecute = null;

    private JLabel ivjJLabel1 = null;

    private JCheckBox ivjRemNonlin = null;

    private JButton ivjConfigRemnonlin = null;

    private JCheckBox ivjParaCons = null;

    private JButton ivjConfigParacons = null;

    private JCheckBox ivjArchLMuni = null;

    private JLabel ivjJLabel = null;

    private NumSelector ivjArchLMLags = null;

    private JCheckBox ivjJarqueBera = null;

    class IvjEventHandler implements java.awt.event.ActionListener,
            java.awt.event.ItemListener {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == STR_Diagnos.this.getExecute())
                connEtoC1();
            if (e.getSource() == STR_Diagnos.this.getConfigRemnonlin())
                connEtoC3();
            if (e.getSource() == STR_Diagnos.this.getConfigParacons())
                connEtoC4();
        };

        public void itemStateChanged(java.awt.event.ItemEvent e) {
            if (e.getSource() == STR_Diagnos.this.getAutocorr())
                connEtoC2(e);
            if (e.getSource() == STR_Diagnos.this.getArchLMuni())
                connEtoC5(e);
        };
    };

    /**
     * StrMissSpecTests constructor comment.
     */
    public STR_Diagnos() {
        super();
        initialize();
    }

    /**
     * Comment
     */
    private void archLMuni_ItemStateChanged() {
        getArchLMLags().setEnabled(getArchLMuni().isSelected());
        return;
    }

    /**
     * Gets the dialog for excluding variables from test for parameter
     * constancy.
     * 
     * @return a dialog
     */
    private STR_ConfigParamConstTest getCfgParacons() {
        if (strParaconsConfig == null) {
            strParaconsConfig = new STR_ConfigParamConstTest(TopFrameReference
                    .getTopFrameRef(), true, global());
        }
        return strParaconsConfig;
    }

    /**
     * Gets the dialog for setting transition variable and constant parameters
     * for test on remaining nonlinearity.
     * 
     * @return a dialog
     */
    private STR_ConfigRemNonlinTest getCfgRemNonLin() {
        if (strRemnonlinConfig == null) {
            strRemnonlinConfig = new STR_ConfigRemNonlinTest(TopFrameReference
                    .getTopFrameRef(), true, global());
        }
        return strRemnonlinConfig;
    }

    /**
     * Configure test for parameter constancy.
     */
    private void configParacons_ActionEvents() {
        getCfgParacons().setLocationRelativeTo(
                TopFrameReference.getTopFrameRef());
        getCfgParacons().setVisible(true);
    }

    /**
     * Configure test for remaining nonlinearity.
     */
    private void configRemnonlin_ActionEvents() {
        if (global().get(STR_Constants.STR_transNames_noTrend).isEmpty()) {
            StdMessages
                    .infoGeneral("The test for remaining nonlinearity is not"
                            + " possible for this model.");
            return;
        }
        getCfgRemNonLin().setLocationRelativeTo(
                TopFrameReference.getTopFrameRef());
        getCfgRemNonLin().setVisible(true);
    }

    /**
     * connEtoC1: (Execute.action. --> StrMisspecTests.execute_ActionEvents()V)
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
     * connEtoC2: (Autocorr.item.itemStateChanged(java.awt.event.ItemEvent) -->
     * StrMisspecTests.itemStateChanged()V)
     * 
     * @param arg1
     *            java.awt.event.ItemEvent
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC2(java.awt.event.ItemEvent arg1) {
        try {
            // user code begin {1}
            // user code end
            this.itemStateChanged();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC3: (ConfigRemnonlin.action. -->
     * StrMisspecTests.configRemnonlin_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC3() {
        try {
            // user code begin {1}
            // user code end
            this.configRemnonlin_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC4: (ConfigParacons.action. -->
     * StrMisspecTests.configParacons_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC4() {
        try {
            // user code begin {1}
            // user code end
            this.configParacons_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC5: (ArchLMuni.item.itemStateChanged(java.awt.event.ItemEvent) -->
     * StrMisspecTests.archLMuni_ItemStateChanged()V)
     * 
     * @param arg1
     *            java.awt.event.ItemEvent
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC5(java.awt.event.ItemEvent arg1) {
        try {
            // user code begin {1}
            // user code end
            this.archLMuni_ItemStateChanged();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * Here we collect the specification an build the method calls.
     */
    private void execute_ActionEvents() {

        if (!getAutocorr().isSelected() && !getRemNonlin().isSelected()
                && !getParaCons().isSelected() && !getArchLMuni().isSelected()
                && !getJarqueBera().isSelected()) {
            StdMessages.infoNothingSelected("Please select an option");
            return;
        }

        int lagsAutocorr = getAutocorrLags().getIntNumber();

        JSCNArray res = new JSCNArray("resids", global().get(
                STR_Constants.STR_RTZ).getJSCNArray().getCol(0));

        // No remaining error autocorrelation.
        if (getAutocorr().isSelected()) {
            PCall job = new STRAutoCorrCall(global().get(STR_Constants.STR_RTZ)
                    .getJSCNArray(), lagsAutocorr);
            job.setOutHolder(getResultField());
            job.setSymbolTable(local());
            job.execute();
        }

        if (getRemNonlin().isSelected()
                && !global().get(STR_Constants.STR_transNames_noTrend)
                        .isEmpty()) {
            JSCNArray selThet0Data = getCfgRemNonLin().getThet0Sel();
            JSCNArray selTransData = getCfgRemNonLin().getTrans();
            if (UMatrix.getNonzeroDoubleCount(UMatrix.vec(selTransData
                    .doubleArray())) == 0) {
                String msg = "Please select at least one transition variable\nfor the test of no remaining nonlinearity.\n"
                        + "You can also deselect this test.";
                StdMessages.infoNothingSelected(msg);
            } else {

                // No remaining nonlinearity.
                PCall job = new STRRemNonLinCall(global().get(
                        STR_Constants.STR_X).getJSCNArray(), global().get(
                        STR_Constants.STR_D).getJSCNArray(), global().get(
                        STR_Constants.STR_RTZ).getJSCNArray(), global().get(
                        STR_Constants.STR_transNames).getJSCSArray(),
                        selTransData, selThet0Data, global().get(
                                STR_Constants.STR_PHIRES_EST).getJSCNArray(),
                        global().get(STR_Constants.STR_thet0Names)
                                .getJSCSArray(), global().get(
                                STR_Constants.STR_allRes).getJSCNArray());
                job.setOutHolder(getResultField());
                job.setSymbolTable(local());
                job.execute();
            }
        }

        if (getParaCons().isSelected()) {
            JSCNArray selEx = getCfgParacons().getParaconsEx();

            // Parameter constancy.
            PCall job = new STRParaConstCall(global().get(STR_Constants.STR_X)
                    .getJSCNArray(), global().get(STR_Constants.STR_D)
                    .getJSCNArray(), global().get(STR_Constants.STR_RTZ)
                    .getJSCNArray(), global().get(STR_Constants.STR_CONST_EST)
                    .getJSCNArray(), global().get(STR_Constants.STR_PHIRES_EST)
                    .getJSCNArray(), global().get(STR_Constants.STR_thet0Names)
                    .getJSCSArray(), global().get(STR_Constants.STR_allRes)
                    .getJSCNArray(), selEx);
            job.setOutHolder(getResultField());
            job.setSymbolTable(local());
            job.execute();

        }

        if (getArchLMuni().isSelected()) {

            // ARCH.
            PCall job = new UnivarARCHLMCall(res, null, getArchLMLags()
                    .getIntNumber());

            job.setOutHolder(getResultField());
            job.setSymbolTable(local());
            job.execute();

        }

        if (getJarqueBera().isSelected()) {

            // Nonnormality.
            PCall job = new UnivarJBeraCall(res, null);
            job.setOutHolder(getResultField());
            job.setSymbolTable(local());
            job.execute();
        }

    }

    private NumSelector getArchLMLags() {
        if (ivjArchLMLags == null) {
            try {
                ivjArchLMLags = new NumSelector();
                ivjArchLMLags.setName("ArchLMLags");
                ivjArchLMLags.setPreferredSize(new java.awt.Dimension(11, 21));
                ivjArchLMLags.setNumber(8.0);
                ivjArchLMLags.setRangeExpr("[1,100]");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjArchLMLags;
    }

    /**
     * Return the ArchLMuni property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getArchLMuni() {
        if (ivjArchLMuni == null) {
            try {
                ivjArchLMuni = new javax.swing.JCheckBox();
                ivjArchLMuni.setName("ArchLMuni");
                ivjArchLMuni.setSelected(true);
                ivjArchLMuni.setPreferredSize(new java.awt.Dimension(210, 21));
                ivjArchLMuni.setText("Univariate ARCH-LM");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjArchLMuni;
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
                ivjAutocorr.setSelected(true);
                ivjAutocorr.setPreferredSize(new java.awt.Dimension(210, 21));
                ivjAutocorr.setText("Test of no error autocorrelation");
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
                        .setPreferredSize(new java.awt.Dimension(11, 21));
                ivjAutocorrLags.setNumber(8.0);
                ivjAutocorrLags.setRangeExpr("[1,100]");
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
     * Return the ConfigParacons property value.
     * 
     * @return javax.swing.JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JButton getConfigParacons() {
        if (ivjConfigParacons == null) {
            try {
                ivjConfigParacons = new javax.swing.JButton();
                ivjConfigParacons.setName("ConfigParacons");
                ivjConfigParacons.setPreferredSize(new java.awt.Dimension(100,
                        21));
                ivjConfigParacons.setText("Configure");
                ivjConfigParacons
                        .setMinimumSize(new java.awt.Dimension(89, 21));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjConfigParacons;
    }

    /**
     * Return the ConfigRemnonlin property value.
     * 
     * @return javax.swing.JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JButton getConfigRemnonlin() {
        if (ivjConfigRemnonlin == null) {
            try {
                ivjConfigRemnonlin = new javax.swing.JButton();
                ivjConfigRemnonlin.setName("ConfigRemnonlin");
                ivjConfigRemnonlin.setPreferredSize(new java.awt.Dimension(100,
                        21));
                ivjConfigRemnonlin.setText("Configure");
                ivjConfigRemnonlin
                        .setMinimumSize(new java.awt.Dimension(89, 21));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjConfigRemnonlin;
    }

    /**
     * Return the ControlPanel property value.
     * 
     * @return javax.swing.JPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JPanel getControlPanel() {
        if (ivjControlPanel == null) {
            try {
                ivjControlPanel = new javax.swing.JPanel();
                ivjControlPanel.setName("ControlPanel");
                ivjControlPanel
                        .setPreferredSize(new java.awt.Dimension(0, 180));
                ivjControlPanel.setLayout(new java.awt.GridBagLayout());

                java.awt.GridBagConstraints constraintsExecute = new java.awt.GridBagConstraints();
                constraintsExecute.gridx = 3;
                constraintsExecute.gridy = 4;
                constraintsExecute.anchor = java.awt.GridBagConstraints.SOUTH;
                constraintsExecute.weightx = 1.0;
                constraintsExecute.weighty = 1.0;
                constraintsExecute.insets = new java.awt.Insets(0, 0, 10, 0);
                java.awt.GridBagConstraints constraintsAutocorr = new java.awt.GridBagConstraints();
                constraintsAutocorr.gridx = 0;
                constraintsAutocorr.gridy = 0;
                constraintsAutocorr.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsAutocorr.anchor = java.awt.GridBagConstraints.WEST;
                constraintsAutocorr.insets = new java.awt.Insets(5, 5, 0, 0);
                getControlPanel().add(getAutocorr(), constraintsAutocorr);

                java.awt.GridBagConstraints constraintsAutocorrLags = new java.awt.GridBagConstraints();
                constraintsAutocorrLags.gridx = 2;
                constraintsAutocorrLags.gridy = 0;
                constraintsAutocorrLags.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsAutocorrLags.insets = new java.awt.Insets(5, 5, 0, 0);
                getControlPanel().add(getAutocorrLags(),
                        constraintsAutocorrLags);

                java.awt.GridBagConstraints constraintsJLabel1 = new java.awt.GridBagConstraints();
                constraintsJLabel1.gridx = 1;
                constraintsJLabel1.gridy = 0;
                constraintsJLabel1.insets = new java.awt.Insets(5, 40, 0, 0);
                getControlPanel().add(getJLabel1(), constraintsJLabel1);

                java.awt.GridBagConstraints constraintsRemNonlin = new java.awt.GridBagConstraints();
                constraintsRemNonlin.gridx = 0;
                constraintsRemNonlin.gridy = 1;
                constraintsRemNonlin.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsRemNonlin.anchor = java.awt.GridBagConstraints.WEST;
                constraintsRemNonlin.insets = new java.awt.Insets(5, 5, 0, 0);
                getControlPanel().add(getRemNonlin(), constraintsRemNonlin);

                java.awt.GridBagConstraints constraintsParaCons = new java.awt.GridBagConstraints();
                constraintsParaCons.gridx = 0;
                constraintsParaCons.gridy = 2;
                constraintsParaCons.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsParaCons.anchor = java.awt.GridBagConstraints.WEST;
                constraintsParaCons.insets = new java.awt.Insets(5, 5, 0, 0);
                getControlPanel().add(getParaCons(), constraintsParaCons);

                java.awt.GridBagConstraints constraintsConfigRemnonlin = new java.awt.GridBagConstraints();
                constraintsConfigRemnonlin.gridx = 1;
                constraintsConfigRemnonlin.gridy = 1;
                constraintsConfigRemnonlin.gridwidth = 2;
                constraintsConfigRemnonlin.insets = new java.awt.Insets(5, 40,
                        0, 0);
                getControlPanel().add(getConfigRemnonlin(),
                        constraintsConfigRemnonlin);

                java.awt.GridBagConstraints constraintsConfigParacons = new java.awt.GridBagConstraints();
                constraintsConfigParacons.gridx = 1;
                constraintsConfigParacons.gridy = 2;
                constraintsConfigParacons.gridwidth = 2;
                constraintsConfigParacons.insets = new java.awt.Insets(5, 40,
                        0, 0);
                getControlPanel().add(getConfigParacons(),
                        constraintsConfigParacons);

                java.awt.GridBagConstraints constraintsArchLMuni = new java.awt.GridBagConstraints();
                constraintsArchLMuni.gridx = 0;
                constraintsArchLMuni.gridy = 3;
                constraintsArchLMuni.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsArchLMuni.anchor = java.awt.GridBagConstraints.WEST;
                constraintsArchLMuni.insets = new java.awt.Insets(5, 5, 0, 0);
                getControlPanel().add(getArchLMuni(), constraintsArchLMuni);

                java.awt.GridBagConstraints constraintsJLabel = new java.awt.GridBagConstraints();
                constraintsJLabel.gridx = 1;
                constraintsJLabel.gridy = 3;
                constraintsJLabel.insets = new java.awt.Insets(5, 40, 0, 0);
                getControlPanel().add(getJLabel(), constraintsJLabel);

                java.awt.GridBagConstraints constraintsArchLMLags = new java.awt.GridBagConstraints();
                constraintsArchLMLags.gridx = 2;
                constraintsArchLMLags.gridy = 3;
                constraintsArchLMLags.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsArchLMLags.insets = new java.awt.Insets(5, 5, 0, 0);
                getControlPanel().add(getArchLMLags(), constraintsArchLMLags);

                java.awt.GridBagConstraints constraintsJarqueBera = new java.awt.GridBagConstraints();
                constraintsJarqueBera.gridx = 0;
                constraintsJarqueBera.gridy = 4;
                constraintsJarqueBera.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJarqueBera.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsJarqueBera.insets = new java.awt.Insets(5, 5, 0, 0);
                ivjControlPanel.add(getExecute(), constraintsExecute);
                getControlPanel().add(getJarqueBera(), constraintsJarqueBera);
                // user code begin {1}
                TitledBorder title = new TitledBorder(new BevelBorder(
                        BevelBorder.LOWERED), "STR Misspecification Tests",
                        TitledBorder.RIGHT, TitledBorder.TOP);
                getControlPanel().setBorder(title);

                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjControlPanel;
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
                ivjExecute.setPreferredSize(new java.awt.Dimension(100, 27));
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
     * Return the JarqueBera property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getJarqueBera() {
        if (ivjJarqueBera == null) {
            try {
                ivjJarqueBera = new javax.swing.JCheckBox();
                ivjJarqueBera.setName("JarqueBera");
                ivjJarqueBera.setSelected(true);
                ivjJarqueBera.setPreferredSize(new java.awt.Dimension(210, 21));
                ivjJarqueBera.setText("Jarque-Bera test");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJarqueBera;
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
                ivjJLabel.setPreferredSize(new java.awt.Dimension(30, 21));
                ivjJLabel.setText("Lags");
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
                ivjJLabel1.setPreferredSize(new java.awt.Dimension(30, 21));
                ivjJLabel1.setText("Lags");
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
     * Return the RemNonlin1 property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getParaCons() {
        if (ivjParaCons == null) {
            try {
                ivjParaCons = new javax.swing.JCheckBox();
                ivjParaCons.setName("ParaCons");
                ivjParaCons.setSelected(true);
                ivjParaCons.setPreferredSize(new java.awt.Dimension(210, 21));
                ivjParaCons.setText("Test of parameter constancy");
                ivjParaCons.setEnabled(true);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjParaCons;
    }

    /**
     * Return the RemNonlin property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getRemNonlin() {
        if (ivjRemNonlin == null) {
            try {
                ivjRemNonlin = new javax.swing.JCheckBox();
                ivjRemNonlin.setName("RemNonlin");
                ivjRemNonlin.setSelected(true);
                ivjRemNonlin.setPreferredSize(new java.awt.Dimension(210, 21));
                ivjRemNonlin.setText("Test of no remaining nonlinearity");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjRemNonlin;
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
        getAutocorr().addItemListener(ivjEventHandler);
        getConfigRemnonlin().addActionListener(ivjEventHandler);
        getConfigParacons().addActionListener(ivjEventHandler);
        getArchLMuni().addItemListener(ivjEventHandler);
    }

    /**
     * Initialize the class.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initialize() {
        try {
            // user code begin {1}
            // user code end
            setName("StrMissSpecTests");
            setLayout(new java.awt.BorderLayout());
            setSize(722, 327);
            add(getResultField(), "Center");
            add(getControlPanel(), "North");
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
    private void itemStateChanged() {
        getAutocorrLags().setEnabled(getAutocorr().isSelected());
        return;
    }
}