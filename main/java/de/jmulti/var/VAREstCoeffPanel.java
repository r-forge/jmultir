/*
 This file is part of the econometric software package JMulTi.
 Copyright (C) 2000-2005  Alexander Bankwitz, Markus Kraetzig

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

package de.jmulti.var;

import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import com.jstatcom.component.ResultField;
import com.jstatcom.component.SymbolDisplay;
import com.jstatcom.engine.PCall;
import com.jstatcom.equation.EqPanel;
import com.jstatcom.equation.EqTermAR;
import com.jstatcom.equation.EqTermDefault;
import com.jstatcom.equation.EqTermLHS;
import com.jstatcom.model.JSCConstants;
import com.jstatcom.model.ModelPanel;
import com.jstatcom.model.SymbolEvent;
import com.jstatcom.model.SymbolListener;

import de.jmulti.proc.ModelStatsCall;
import de.jmulti.proc.VAREstimationCall;
import de.jmulti.tools.ModelTypes;

/**
 * This component initiates VAR model estimation and components to present the
 * results. It makes use of <code>VAREstimationCall</code>.
 * 
 * @author A. Benkwitz, <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
public final class VAREstCoeffPanel extends ModelPanel {
    private static final Logger log = Logger.getLogger(VAREstCoeffPanel.class);

    private static String FILE_NAME_RESULTS = "VAREST";

    private JLabel ivjJLabel1 = null;

    private JLabel ivjJLabel2 = null;

    private JPanel ivjJPanel1 = null;

    private JLabel ivjJLabel3 = null;

    private JLabel ivjJLabel4 = null;

    private SymbolDisplay ivjDataLabel_EstimationMethod = null;

    private JScrollPane ivjJScrollPane1 = null;

    private JToggleButton ivjCoefficients = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private JPanel ivjJPanel2 = null;

    private JToggleButton ivjStandardDev = null;

    private JToggleButton ivjTValues = null;

    private JTabbedPane ivjJTabbedPane1 = null;

    private JPanel ivjMatrix = null;

    private ResultField ivjResultField = null;

    private JPanel ivjText = null;

    private EqTermDefault ivjDeterministicsTerm = null;

    private EqPanel ivjEquationPanel = null;

    private EqTermAR ivjExogenousTerm = null;

    private EqTermAR ivjLaggedEndogenous = null;

    private EqTermLHS ivjLHS = null;

    private EqTermDefault ivjResiduals = null;

    private SymbolDisplay ivjEstimationPeriodLabel = null;

    private JButton ivjRecomputeButton = null;

    class IvjEventHandler implements java.awt.event.ActionListener {

        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == VAREstCoeffPanel.this.getCoefficients())
                connEtoC1();
            if (e.getSource() == VAREstCoeffPanel.this.getStandardDev())
                connEtoC2();
            if (e.getSource() == VAREstCoeffPanel.this.getTValues())
                connEtoC3();
            if (e.getSource() == VAREstCoeffPanel.this.getRecomputeButton())
                connEtoC5();
        };
    };

    private ResultField ivjResultFieldStats = null;

    /**
     * EstimatedCoefficientsPanel constructor comment.
     */
    public VAREstCoeffPanel() {
        super();
        initialize();
    }

    /**
     * Comment
     */
    private void coefficients_ActionEvents() {
        getLaggedEndogenous().setSymbolNameCoeff(VARConstants.Ay_Def.name);
        getExogenousTerm().setSymbolNameCoeff(VARConstants.Ax_Def.name);
        getDeterministicsTerm().setSymbolNameCoeff(VARConstants.Ad_Def.name);

        ivjJLabel3.setText("Model Coefficients");
        return;
    }

    /**
     * Comment
     */
    private void computeEstimation() {
        getResultField().clear();
        getResultFieldStats().clear();
        PCall job = new VAREstimationCall(global(), JSCConstants
                .getSystemTemp()
                + File.separator + FILE_NAME_RESULTS);
        job.setOutHolder(getResultField());

        PCall job1 = new ModelStatsCall(global(), ModelTypes.VAR);
        job1.setOutHolder(getResultFieldStats());
        job.add(job1);
        job.execute();
    }

    /**
     * connEtoC1: (Coefficients.action. -->
     * EstimatedCoefficientsPanel.coefficients_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC1() {
        try {
            // user code begin {1}
            // user code end
            this.coefficients_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC2: (StandardDev.action. -->
     * EstimatedCoefficientsPanel.standardDev_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC2() {
        try {
            // user code begin {1}
            // user code end
            this.standardDev_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC3: (TValues.action. -->
     * EstimatedCoefficientsPanel.tValues_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC3() {
        try {
            // user code begin {1}
            // user code end
            this.tValues_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC5: (RecomputeButton.action. -->
     * VAREstimatedCoefficientsPanel.computeEstimation()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC5() {
        try {
            // user code begin {1}
            // user code end
            this.computeEstimation();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * Return the Coefficients property value.
     * 
     * @return javax.swing.JToggleButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JToggleButton getCoefficients() {
        if (ivjCoefficients == null) {
            try {
                ivjCoefficients = new javax.swing.JToggleButton();
                ivjCoefficients.setName("Coefficients");
                ivjCoefficients.setSelected(true);
                ivjCoefficients
                        .setPreferredSize(new java.awt.Dimension(135, 27));
                ivjCoefficients.setText("Coefficients");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjCoefficients;
    }

    private EqTermDefault getDeterministicsTerm() {
        if (ivjDeterministicsTerm == null) {
            try {
                ivjDeterministicsTerm = new EqTermDefault();
                ivjDeterministicsTerm.setName("DeterministicsTerm");
                /*
                 * ivjDeterministicsTerm
                 * .setSymbolNameSubsetRes(VARConstants.Cd_Def.name);
                 */
                ivjDeterministicsTerm
                        .setSymbolNameCoeff(VARConstants.Ad_Def.name);
                ivjDeterministicsTerm.setBounds(196, 22, 20, 20);
                ivjDeterministicsTerm
                        .setSymbolNameVariables(VARConstants.Nd_Def.name);
                ivjDeterministicsTerm.setCoeffMatRequired(true);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjDeterministicsTerm;
    }

    private EqPanel getEquationPanel() {
        if (ivjEquationPanel == null) {
            try {
                ivjEquationPanel = new EqPanel();
                ivjEquationPanel.setName("EquationPanel");
                ivjEquationPanel.setBounds(0, 0, 528, 278);
                getEquationPanel().add(getLHS(), getLHS().getName());
                getEquationPanel().add(getLaggedEndogenous(),
                        getLaggedEndogenous().getName());
                getEquationPanel().add(getExogenousTerm(),
                        getExogenousTerm().getName());
                getEquationPanel().add(getDeterministicsTerm(),
                        getDeterministicsTerm().getName());
                getEquationPanel()
                        .add(getResiduals(), getResiduals().getName());
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjEquationPanel;
    }

    private SymbolDisplay getEstimationPeriodLabel() {
        if (ivjEstimationPeriodLabel == null) {
            try {
                ivjEstimationPeriodLabel = new SymbolDisplay();
                ivjEstimationPeriodLabel.setName("EstimationPeriodLabel");
                ivjEstimationPeriodLabel
                        .setPreferredSize(new java.awt.Dimension(28, 20));
                ivjEstimationPeriodLabel.setMinimumSize(new java.awt.Dimension(
                        28, 20));
                // user code begin {1}
                ivjEstimationPeriodLabel
                        .setSymbolDef(VARConstants.estRange_Def);
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjEstimationPeriodLabel;
    }

    private EqTermAR getExogenousTerm() {
        if (ivjExogenousTerm == null) {
            try {
                ivjExogenousTerm = new EqTermAR();
                ivjExogenousTerm.setName("ExogenousTerm");
                ivjExogenousTerm
                        .setSymbolNameVariables(VARConstants.Nx_Def.name);
                ivjExogenousTerm.setSymbolNameLags(VARConstants.px_Def.name);
                ivjExogenousTerm.setBounds(192, 20, 20, 20);
                /*
                 * ivjExogenousTerm
                 * .setSymbolNameSubsetRes(VARConstants.Cx_Def.name);
                 */
                ivjExogenousTerm.setSymbolNameCoeff(VARConstants.Ax_Def.name);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjExogenousTerm;
    }

    private SymbolDisplay getDataLabel_EstimationMethod() {
        if (ivjDataLabel_EstimationMethod == null) {
            try {
                ivjDataLabel_EstimationMethod = new SymbolDisplay();
                ivjDataLabel_EstimationMethod
                        .setName("DataLabel_EstimationMethod");
                ivjDataLabel_EstimationMethod
                        .setPreferredSize(new java.awt.Dimension(28, 20));
                ivjDataLabel_EstimationMethod
                        .setMinimumSize(new java.awt.Dimension(28, 20));
                // user code begin {1}
                ivjDataLabel_EstimationMethod
                        .setSymbolDef(VARConstants.est_method_Def);
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjDataLabel_EstimationMethod;
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
                ivjJLabel1.setPreferredSize(new java.awt.Dimension(160, 20));
                ivjJLabel1.setText("Estimation method");
                ivjJLabel1.setMinimumSize(new java.awt.Dimension(160, 20));
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
                ivjJLabel2.setPreferredSize(new java.awt.Dimension(160, 20));
                ivjJLabel2.setText("Estimation results");
                ivjJLabel2.setMinimumSize(new java.awt.Dimension(160, 20));
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
                ivjJLabel3.setPreferredSize(new java.awt.Dimension(28, 20));
                ivjJLabel3.setText("Model Coefficients");
                ivjJLabel3.setMinimumSize(new java.awt.Dimension(28, 20));
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
                ivjJLabel4.setPreferredSize(new java.awt.Dimension(160, 20));
                ivjJLabel4.setText("Estimation period");
                ivjJLabel4.setMinimumSize(new java.awt.Dimension(160, 20));
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
                ivjJPanel1.setPreferredSize(new java.awt.Dimension(160, 140));
                ivjJPanel1.setLayout(new java.awt.GridBagLayout());

                java.awt.GridBagConstraints constraintsCoefficients = new java.awt.GridBagConstraints();
                constraintsCoefficients.gridx = 1;
                constraintsCoefficients.gridy = 0;
                constraintsCoefficients.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsCoefficients.insets = new java.awt.Insets(0, 5, 0,
                        10);
                getJPanel1().add(getCoefficients(), constraintsCoefficients);

                java.awt.GridBagConstraints constraintsStandardDev = new java.awt.GridBagConstraints();
                constraintsStandardDev.gridx = 1;
                constraintsStandardDev.gridy = 1;
                constraintsStandardDev.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsStandardDev.insets = new java.awt.Insets(2, 5, 0, 10);
                getJPanel1().add(getStandardDev(), constraintsStandardDev);

                java.awt.GridBagConstraints constraintsTValues = new java.awt.GridBagConstraints();
                constraintsTValues.gridx = 1;
                constraintsTValues.gridy = 2;
                constraintsTValues.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsTValues.anchor = java.awt.GridBagConstraints.NORTH;
                constraintsTValues.insets = new java.awt.Insets(2, 5, 0, 10);
                getJPanel1().add(getTValues(), constraintsTValues);

                java.awt.GridBagConstraints constraintsJPanel2 = new java.awt.GridBagConstraints();
                constraintsJPanel2.gridx = 0;
                constraintsJPanel2.gridy = 0;
                constraintsJPanel2.gridheight = 4;
                constraintsJPanel2.fill = java.awt.GridBagConstraints.BOTH;
                constraintsJPanel2.weightx = 1.0;
                constraintsJPanel2.weighty = 1.0;
                constraintsJPanel2.insets = new java.awt.Insets(0, 5, 5, 5);
                getJPanel1().add(getJPanel2(), constraintsJPanel2);

                java.awt.GridBagConstraints constraintsRecomputeButton = new java.awt.GridBagConstraints();
                constraintsRecomputeButton.gridx = 1;
                constraintsRecomputeButton.gridy = 3;
                constraintsRecomputeButton.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsRecomputeButton.anchor = java.awt.GridBagConstraints.NORTH;
                constraintsRecomputeButton.weighty = 1.0;
                constraintsRecomputeButton.insets = new java.awt.Insets(2, 5,
                        5, 10);
                getJPanel1().add(getRecomputeButton(),
                        constraintsRecomputeButton);
                // user code begin {1}
                TitledBorder title = new TitledBorder(new BevelBorder(
                        BevelBorder.LOWERED), "Estimated Model",
                        TitledBorder.RIGHT, TitledBorder.TOP);
                getJPanel1().setBorder(title);

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
     * Return the JPanel2 property value.
     * 
     * @return javax.swing.JPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JPanel getJPanel2() {
        if (ivjJPanel2 == null) {
            try {
                ivjJPanel2 = new javax.swing.JPanel();
                ivjJPanel2.setName("JPanel2");
                ivjJPanel2.setPreferredSize(new java.awt.Dimension(197, 95));
                ivjJPanel2.setBorder(new javax.swing.border.EtchedBorder());
                ivjJPanel2.setLayout(new java.awt.GridBagLayout());
                ivjJPanel2.setMinimumSize(new java.awt.Dimension(197, 95));

                java.awt.GridBagConstraints constraintsJLabel2 = new java.awt.GridBagConstraints();
                constraintsJLabel2.gridx = 0;
                constraintsJLabel2.gridy = 0;
                constraintsJLabel2.insets = new java.awt.Insets(0, 5, 0, 0);
                getJPanel2().add(getJLabel2(), constraintsJLabel2);

                java.awt.GridBagConstraints constraintsJLabel1 = new java.awt.GridBagConstraints();
                constraintsJLabel1.gridx = 0;
                constraintsJLabel1.gridy = 1;
                constraintsJLabel1.insets = new java.awt.Insets(10, 5, 0, 0);
                getJPanel2().add(getJLabel1(), constraintsJLabel1);

                java.awt.GridBagConstraints constraintsJLabel4 = new java.awt.GridBagConstraints();
                constraintsJLabel4.gridx = 0;
                constraintsJLabel4.gridy = 2;
                constraintsJLabel4.insets = new java.awt.Insets(10, 5, 0, 0);
                getJPanel2().add(getJLabel4(), constraintsJLabel4);

                java.awt.GridBagConstraints constraintsJLabel3 = new java.awt.GridBagConstraints();
                constraintsJLabel3.gridx = 1;
                constraintsJLabel3.gridy = 0;
                constraintsJLabel3.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel3.weightx = 1.0;
                getJPanel2().add(getJLabel3(), constraintsJLabel3);

                java.awt.GridBagConstraints constraintsGaussDataLabel_EstimationMethod = new java.awt.GridBagConstraints();
                constraintsGaussDataLabel_EstimationMethod.gridx = 1;
                constraintsGaussDataLabel_EstimationMethod.gridy = 1;
                constraintsGaussDataLabel_EstimationMethod.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsGaussDataLabel_EstimationMethod.weightx = 1.0;
                constraintsGaussDataLabel_EstimationMethod.insets = new java.awt.Insets(
                        10, 0, 0, 0);
                getJPanel2().add(getDataLabel_EstimationMethod(),
                        constraintsGaussDataLabel_EstimationMethod);

                java.awt.GridBagConstraints constraintsEstimationPeriodLabel = new java.awt.GridBagConstraints();
                constraintsEstimationPeriodLabel.gridx = 1;
                constraintsEstimationPeriodLabel.gridy = 2;
                constraintsEstimationPeriodLabel.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsEstimationPeriodLabel.weightx = 1.0;
                constraintsEstimationPeriodLabel.insets = new java.awt.Insets(
                        10, 0, 0, 0);
                getJPanel2().add(getEstimationPeriodLabel(),
                        constraintsEstimationPeriodLabel);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJPanel2;
    }

    /**
     * Return the JScrollPane1 property value.
     * 
     * @return javax.swing.JScrollPane
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JScrollPane getJScrollPane1() {
        if (ivjJScrollPane1 == null) {
            try {
                ivjJScrollPane1 = new javax.swing.JScrollPane();
                ivjJScrollPane1.setName("JScrollPane1");
                getJScrollPane1().setViewportView(getEquationPanel());
                // user code begin {1}
                getJScrollPane1().setBorder(
                        new BevelBorder(BevelBorder.LOWERED));

                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJScrollPane1;
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
                ivjJTabbedPane1.insertTab("Matrix", null, getMatrix(), null, 0);
                ivjJTabbedPane1.insertTab("Output (save/print)", null,
                        getText(), null, 1);
                ivjJTabbedPane1.insertTab("Stats", null, getResultFieldStats(),
                        null, 2);
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

    private EqTermAR getLaggedEndogenous() {
        if (ivjLaggedEndogenous == null) {
            try {
                ivjLaggedEndogenous = new EqTermAR();
                ivjLaggedEndogenous.setName("LaggedEndogenous");
                ivjLaggedEndogenous
                        .setSymbolNameVariables(VARConstants.Ny_Def.name);
                ivjLaggedEndogenous.setStartLag(1);
                ivjLaggedEndogenous.setSymbolNameLags(VARConstants.py_Def.name);
                /*
                 * ivjLaggedEndogenous
                 * .setSymbolNameSubsetRes(VARConstants.Cy_Def.name);
                 */
                ivjLaggedEndogenous.setBounds(169, 32, 20, 20);
                ivjLaggedEndogenous
                        .setSymbolNameCoeff(VARConstants.Ay_Def.name);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjLaggedEndogenous;
    }

    private EqTermLHS getLHS() {
        if (ivjLHS == null) {
            try {
                ivjLHS = new EqTermLHS();
                ivjLHS.setName("LHS");
                ivjLHS.setBounds(114, 61, 20, 20);
                ivjLHS.setSymbolNameVariables(VARConstants.Ny_Def.name);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjLHS;
    }

    /**
     * Return the Matrix property value.
     * 
     * @return javax.swing.JPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JPanel getMatrix() {
        if (ivjMatrix == null) {
            try {
                ivjMatrix = new javax.swing.JPanel();
                ivjMatrix.setName("Matrix");
                ivjMatrix.setLayout(new java.awt.BorderLayout());
                getMatrix().add(getJPanel1(), "North");
                getMatrix().add(getJScrollPane1(), "Center");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjMatrix;
    }

    /**
     * Return the RecomputeButton property value.
     * 
     * @return javax.swing.JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JButton getRecomputeButton() {
        if (ivjRecomputeButton == null) {
            try {
                ivjRecomputeButton = new javax.swing.JButton();
                ivjRecomputeButton.setName("RecomputeButton");
                ivjRecomputeButton.setPreferredSize(new java.awt.Dimension(135,
                        27));
                ivjRecomputeButton.setText("Recompute");
                ivjRecomputeButton.setVisible(false);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjRecomputeButton;
    }

    private EqTermDefault getResiduals() {
        if (ivjResiduals == null) {
            try {
                ivjResiduals = new EqTermDefault();
                ivjResiduals.setName("Residuals");
                ivjResiduals.setBounds(179, 22, 20, 20);
                ivjResiduals.setSymbolNameVariables(VARConstants.Nu_Def.name);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjResiduals;
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
                ivjResultField.setBorder(new BevelBorder(BevelBorder.LOWERED));
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
     * Return the ResultFieldStats property value.
     * 
     * @return com.jstatcom.component.ResultField
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private com.jstatcom.component.ResultField getResultFieldStats() {
        if (ivjResultFieldStats == null) {
            try {
                ivjResultFieldStats = new com.jstatcom.component.ResultField();
                ivjResultFieldStats.setName("ResultFieldStats");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjResultFieldStats;
    }

    /**
     * Return the StandardDev property value.
     * 
     * @return javax.swing.JToggleButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JToggleButton getStandardDev() {
        if (ivjStandardDev == null) {
            try {
                ivjStandardDev = new javax.swing.JToggleButton();
                ivjStandardDev.setName("StandardDev");
                ivjStandardDev
                        .setPreferredSize(new java.awt.Dimension(135, 27));
                ivjStandardDev.setText("Standard Dev.");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjStandardDev;
    }

    /**
     * Return the Text property value.
     * 
     * @return javax.swing.JPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JPanel getText() {
        if (ivjText == null) {
            try {
                ivjText = new javax.swing.JPanel();
                ivjText.setName("Text");
                ivjText.setLayout(new javax.swing.BoxLayout(getText(),
                        javax.swing.BoxLayout.X_AXIS));
                getText().add(getResultField(), getResultField().getName());
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjText;
    }

    /**
     * Return the TValues property value.
     * 
     * @return javax.swing.JToggleButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JToggleButton getTValues() {
        if (ivjTValues == null) {
            try {
                ivjTValues = new javax.swing.JToggleButton();
                ivjTValues.setName("TValues");
                ivjTValues.setPreferredSize(new java.awt.Dimension(135, 27));
                ivjTValues.setText("t-values");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjTValues;
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
        getCoefficients().addActionListener(ivjEventHandler);
        getStandardDev().addActionListener(ivjEventHandler);
        getTValues().addActionListener(ivjEventHandler);
        getRecomputeButton().addActionListener(ivjEventHandler);
    }

    /**
     * Initialize the class.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initialize() {
        try {
            // user code begin {1}

            // user code end
            setName("EstimatedCoefficientsPanel");
            setOpaque(true);
            setLayout(new javax.swing.BoxLayout(this,
                    javax.swing.BoxLayout.X_AXIS));
            setSize(536, 354);
            add(getJTabbedPane1(), getJTabbedPane1().getName());
            initConnections();
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}

        ButtonGroup selectGroup = new javax.swing.ButtonGroup();
        selectGroup.add(getCoefficients());
        selectGroup.add(getStandardDev());
        selectGroup.add(getTValues());

        final String text = "please recompute for showing results";
        SymbolListener setText = new SymbolListener() {

            public void valueChanged(SymbolEvent evt) {
                getResultField().getTextArea().setText(text);

            }
        };
        global().get(VARConstants.Cd_Def).addSymbolListener(setText);
        global().get(VARConstants.Cx_Def).addSymbolListener(setText);
        global().get(VARConstants.Cy_Def).addSymbolListener(setText);

    }

    /**
     * Invokes estimation if card is shown.
     */
    public void shown(boolean isShown) {
        if (isShown) {
            coefficients_ActionEvents();

            getCoefficients().setSelected(true);
            getJTabbedPane1().setSelectedIndex(0);

            if (global().get(VARConstants.cv_u_hat_Def).isEmpty())
                computeEstimation();
        }

    }

    /**
     * Comment
     */
    private void standardDev_ActionEvents() {
        getLaggedEndogenous().setSymbolNameCoeff(VARConstants.SD_Ay_Def.name);
        getExogenousTerm().setSymbolNameCoeff(VARConstants.SD_Ax_Def.name);
        getDeterministicsTerm().setSymbolNameCoeff(VARConstants.SD_Ad_Def.name);

        ivjJLabel3.setText("Standard deviation of model coefficients");
        return;
    }

    /**
     * Comment
     */
    private void tValues_ActionEvents() {
        getLaggedEndogenous().setSymbolNameCoeff(VARConstants.TV_Ay_Def.name);
        getExogenousTerm().setSymbolNameCoeff(VARConstants.TV_Ax_Def.name);
        getDeterministicsTerm().setSymbolNameCoeff(VARConstants.TV_Ad_Def.name);

        ivjJLabel3.setText("t-values of model coefficients");
        return;
    }
}