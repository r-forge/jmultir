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

package de.jmulti.vecm;

import static de.jmulti.vecm.VECMConstants.A0_Def;
import static de.jmulti.vecm.VECMConstants.A_Def;
import static de.jmulti.vecm.VECMConstants.B_Def;
import static de.jmulti.vecm.VECMConstants.C_var_Def;
import static de.jmulti.vecm.VECMConstants.C_vec_Def;
import static de.jmulti.vecm.VECMConstants.G0_Def;
import static de.jmulti.vecm.VECMConstants.G_Def;
import static de.jmulti.vecm.VECMConstants.Nd_ec_Def;
import static de.jmulti.vecm.VECMConstants.Nd_var_Def;
import static de.jmulti.vecm.VECMConstants.Nd_vec_Def;
import static de.jmulti.vecm.VECMConstants.Ndy_Def;
import static de.jmulti.vecm.VECMConstants.Nu_Def;
import static de.jmulti.vecm.VECMConstants.Nx_Def;
import static de.jmulti.vecm.VECMConstants.Ny_Def;
import static de.jmulti.vecm.VECMConstants.SD_B_Def;
import static de.jmulti.vecm.VECMConstants.SD_C_vec_Def;
import static de.jmulti.vecm.VECMConstants.SD_G0_Def;
import static de.jmulti.vecm.VECMConstants.SD_G_Def;
import static de.jmulti.vecm.VECMConstants.SD_alpha_Def;
import static de.jmulti.vecm.VECMConstants.SD_beta_Def;
import static de.jmulti.vecm.VECMConstants.SD_beta_d_Def;
import static de.jmulti.vecm.VECMConstants.TV_B_Def;
import static de.jmulti.vecm.VECMConstants.TV_C_vec_Def;
import static de.jmulti.vecm.VECMConstants.TV_G0_Def;
import static de.jmulti.vecm.VECMConstants.TV_G_Def;
import static de.jmulti.vecm.VECMConstants.TV_alpha_Def;
import static de.jmulti.vecm.VECMConstants.TV_beta_Def;
import static de.jmulti.vecm.VECMConstants.TV_beta_d_Def;
import static de.jmulti.vecm.VECMConstants.alpha_Def;
import static de.jmulti.vecm.VECMConstants.beta_Def;
import static de.jmulti.vecm.VECMConstants.beta_d_Def;
import static de.jmulti.vecm.VECMConstants.cv_u_hat_Def;
import static de.jmulti.vecm.VECMConstants.estRange_Def;
import static de.jmulti.vecm.VECMConstants.estimationMethod_Def;
import static de.jmulti.vecm.VECMConstants.estimationStrategy_Def;
import static de.jmulti.vecm.VECMConstants.pdy_Def;
import static de.jmulti.vecm.VECMConstants.px_Def;
import static de.jmulti.vecm.VECMConstants.py_Def;

import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import com.jstatcom.component.MultiLineLabel;
import com.jstatcom.component.ResultField;
import com.jstatcom.component.SymbolDisplay;
import com.jstatcom.engine.PCall;
import com.jstatcom.engine.PCallAdapter;
import com.jstatcom.equation.EqPanel;
import com.jstatcom.equation.EqTermAR;
import com.jstatcom.equation.EqTermDefault;
import com.jstatcom.equation.EqTermEC;
import com.jstatcom.equation.EqTermLHS;
import com.jstatcom.model.JSCConstants;
import com.jstatcom.model.ModelPanel;

import de.jmulti.proc.ModelStatsCall;
import de.jmulti.proc.VECMEstimationCall;
import de.jmulti.tools.ModelTypes;

/**
 * Panel to invoke estimation of VECM and to display results.
 * 
 * @author A. Benkwitz, <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
public final class VECMEstCoeffPanel extends ModelPanel {
    private static final Logger log = Logger.getLogger(VECMEstCoeffPanel.class);

    private static String FILE_NAME_RESULTS_VEC = "VECEST";

    private static String FILE_NAME_RESULTS_VAR = "VAREST";

    private JLabel ivjJLabel1 = null;

    private JLabel ivjJLabel2 = null;

    private JPanel ivjJPanel1 = null;

    private JLabel ivjJLabel3 = null;

    private SymbolDisplay ivjEstimationPeriodLabel = null;

    private JLabel ivjJLabel4 = null;

    private JScrollPane ivjJScrollPane1 = null;

    private JToggleButton ivjCoefficients = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private JPanel ivjJPanel2 = null;

    private JToggleButton ivjStandardDev = null;

    private JToggleButton ivjTValues = null;

    private JTabbedPane ivjJTabbedPane1 = null;

    private ResultField ivjResultField = null;

    private JPanel ivjText = null;

    private EqTermDefault ivjDeterministicsTerm = null;

    private EqTermEC ivjECTerm = null;

    private EqPanel ivjEquationPanel = null;

    private EqTermAR ivjExogenousTerm = null;

    private EqTermAR ivjLaggedEndogenous = null;

    private EqTermLHS ivjLHS = null;

    private EqTermDefault ivjResiduals = null;

    private EqTermDefault ivjDeterministicsTerm1 = null;

    private EqPanel ivjEquationPanel1 = null;

    private EqTermAR ivjExogenousTerm1 = null;

    private JScrollPane ivjJScrollPane11 = null;

    private EqTermAR ivjLaggedEndogenous1 = null;

    private EqTermLHS ivjLHS1 = null;

    private JPanel ivjMatrixVAR = null;

    private JPanel ivjMatrixVEC = null;

    private EqTermDefault ivjResiduals1 = null;

    private MultiLineLabel ivjLabelVAR = null;

    private SymbolDisplay ivjEstimationMethodLabel = null;

    class IvjEventHandler implements java.awt.event.ActionListener {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == VECMEstCoeffPanel.this.getCoefficients())
                connEtoC1();
            if (e.getSource() == VECMEstCoeffPanel.this.getStandardDev())
                connEtoC2();
            if (e.getSource() == VECMEstCoeffPanel.this.getTValues())
                connEtoC3();
        };
    };

    private ResultField ivjResultFieldStats = null;

    /**
     * EstimatedCoefficientsPanel constructor comment.
     */
    public VECMEstCoeffPanel() {
        super();
        initialize();
    }

    /**
     * Comment
     */
    private void action_Coefficients() {
        getLHS().setSymbolNameCoeff(G0_Def.name);
        getECTerm().setSymbolNameAlphaCoeff(alpha_Def.name);
        getECTerm().setSymbolNameBetaCoeff(beta_Def.name);
        getECTerm().setSymbolNameBetaDetCoeff(beta_d_Def.name);
        getLaggedEndogenous().setSymbolNameCoeff(G_Def.name);
        getExogenousTerm().setSymbolNameCoeff(B_Def.name);
        getDeterministicsTerm().setSymbolNameCoeff(C_vec_Def.name);
        ivjJLabel3.setText("Model Coefficients");
        return;
    }

    /**
     * Comment
     */
    private void action_StandardDeviation() {
        getLHS().setSymbolNameCoeff(SD_G0_Def.name);
        getECTerm().setSymbolNameAlphaCoeff(SD_alpha_Def.name);
        getECTerm().setSymbolNameBetaCoeff(SD_beta_Def.name);
        getECTerm().setSymbolNameBetaDetCoeff(SD_beta_d_Def.name);
        getLaggedEndogenous().setSymbolNameCoeff(SD_G_Def.name);
        getExogenousTerm().setSymbolNameCoeff(SD_B_Def.name);
        getDeterministicsTerm().setSymbolNameCoeff(SD_C_vec_Def.name);
        ivjJLabel3.setText("Standard deviation of model coefficients");
        return;
    }

    /**
     * Comment
     */
    private void action_T_Values() {
        getLHS().setSymbolNameCoeff(TV_G0_Def.name);
        getECTerm().setSymbolNameAlphaCoeff(TV_alpha_Def.name);
        getECTerm().setSymbolNameBetaCoeff(TV_beta_Def.name);
        getECTerm().setSymbolNameBetaDetCoeff(TV_beta_d_Def.name);
        getLaggedEndogenous().setSymbolNameCoeff(TV_G_Def.name);
        getExogenousTerm().setSymbolNameCoeff(TV_B_Def.name);
        getDeterministicsTerm().setSymbolNameCoeff(TV_C_vec_Def.name);
        ivjJLabel3.setText("t-values of model coefficients");
    }

    /**
     * Invokes the VECM Procedure Call.
     */
    private void computeEstimation() {

        String tempPath = JSCConstants.getSystemTemp();
        getResultField().clear();
        getResultFieldStats().clear();

        PCall job = new VECMEstimationCall(global(), tempPath + File.separator
                + FILE_NAME_RESULTS_VEC, tempPath + File.separator
                + FILE_NAME_RESULTS_VAR, global().get(estimationStrategy_Def)
                .getJSCInt().intVal());
        job.setOutHolder(getResultField());
        job.addPCallListener(new PCallAdapter() {
            public void success() {
                PCall job = new ModelStatsCall(global(), ModelTypes.VECM);
                job.setOutHolder(getResultFieldStats());
                job.execute();
            }
        });
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
            this.action_Coefficients();
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
            this.action_StandardDeviation();
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
            this.action_T_Values();
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
                ivjDeterministicsTerm.setSymbolNameCoeff(C_vec_Def.name);
                ivjDeterministicsTerm.setBounds(196, 22, 20, 20);
                ivjDeterministicsTerm.setSymbolNameVariables(Nd_vec_Def.name);
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

    private EqTermDefault getDeterministicsTerm1() {
        if (ivjDeterministicsTerm1 == null) {
            try {
                ivjDeterministicsTerm1 = new EqTermDefault();
                ivjDeterministicsTerm1.setName("DeterministicsTerm1");
                ivjDeterministicsTerm1.setShowingTimeIndex(false);
                ivjDeterministicsTerm1.setSymbolNameCoeff(C_var_Def.name);
                ivjDeterministicsTerm1.setBounds(196, 22, 20, 20);
                ivjDeterministicsTerm1.setSymbolNameVariables(Nd_var_Def.name);
                ivjDeterministicsTerm1.setCoeffMatRequired(true);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjDeterministicsTerm1;
    }

    private EqTermEC getECTerm() {
        if (ivjECTerm == null) {
            try {
                ivjECTerm = new EqTermEC();
                ivjECTerm.setName("ECTerm");
                ivjECTerm.setSymbolNameAlphaCoeff(alpha_Def.name);
                ivjECTerm.setSymbolNameBetaDetCoeff(beta_d_Def.name);
                ivjECTerm.setSymbolNameEndVarNames(Ny_Def.name);
                ivjECTerm.setSymbolNameDetVarNames(Nd_ec_Def.name);
                ivjECTerm.setBounds(157, 23, 20, 20);
                ivjECTerm.setSymbolNameBetaCoeff(beta_Def.name);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjECTerm;
    }

    private EqPanel getEquationPanel() {
        if (ivjEquationPanel == null) {
            try {
                ivjEquationPanel = new EqPanel();
                ivjEquationPanel.setName("EquationPanel");
                ivjEquationPanel.setBounds(0, 0, 160, 120);
                getEquationPanel().add(getLHS(), getLHS().getName());
                getEquationPanel().add(getECTerm(), getECTerm().getName());
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

    private EqPanel getEquationPanel1() {
        if (ivjEquationPanel1 == null) {
            try {
                ivjEquationPanel1 = new EqPanel();
                ivjEquationPanel1.setName("EquationPanel1");
                ivjEquationPanel1.setBounds(0, 0, 528, 260);
                getEquationPanel1().add(getLHS1(), getLHS1().getName());
                getEquationPanel1().add(getLaggedEndogenous1(),
                        getLaggedEndogenous1().getName());
                getEquationPanel1().add(getExogenousTerm1(),
                        getExogenousTerm1().getName());
                getEquationPanel1().add(getDeterministicsTerm1(),
                        getDeterministicsTerm1().getName());
                getEquationPanel1().add(getResiduals1(),
                        getResiduals1().getName());
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjEquationPanel1;
    }

    private SymbolDisplay getEstimationMethodLabel() {
        if (ivjEstimationMethodLabel == null) {
            try {
                ivjEstimationMethodLabel = new SymbolDisplay();
                ivjEstimationMethodLabel.setName("EstimationMethodLabel");
                ivjEstimationMethodLabel
                        .setPreferredSize(new java.awt.Dimension(28, 20));
                ivjEstimationMethodLabel.setMinimumSize(new java.awt.Dimension(
                        28, 20));
                // user code begin {1}
                ivjEstimationMethodLabel.setSymbolDef(estimationMethod_Def);
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjEstimationMethodLabel;
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
                ivjEstimationPeriodLabel.setSymbolDef(estRange_Def);
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
                ivjExogenousTerm.setSymbolNameVariables(Nx_Def.name);
                ivjExogenousTerm.setSymbolNameLags(px_Def.name);
                ivjExogenousTerm.setBounds(192, 20, 20, 20);
                ivjExogenousTerm.setSymbolNameCoeff(B_Def.name);
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

    private EqTermAR getExogenousTerm1() {
        if (ivjExogenousTerm1 == null) {
            try {
                ivjExogenousTerm1 = new EqTermAR();
                ivjExogenousTerm1.setName("ExogenousTerm1");
                ivjExogenousTerm1.setSymbolNameVariables(Nx_Def.name);
                ivjExogenousTerm1.setSymbolNameLags(px_Def.name);
                ivjExogenousTerm1.setBounds(192, 20, 20, 20);
                ivjExogenousTerm1.setSymbolNameCoeff(B_Def.name);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjExogenousTerm1;
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
                ivjJLabel3.setText("VECM Coefficients");
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
                ivjJPanel1.setLayout(new java.awt.GridBagLayout());

                java.awt.GridBagConstraints constraintsCoefficients = new java.awt.GridBagConstraints();
                constraintsCoefficients.gridx = 1;
                constraintsCoefficients.gridy = 0;
                constraintsCoefficients.fill = java.awt.GridBagConstraints.BOTH;
                constraintsCoefficients.insets = new java.awt.Insets(0, 5, 0,
                        10);
                getJPanel1().add(getCoefficients(), constraintsCoefficients);

                java.awt.GridBagConstraints constraintsStandardDev = new java.awt.GridBagConstraints();
                constraintsStandardDev.gridx = 1;
                constraintsStandardDev.gridy = 1;
                constraintsStandardDev.fill = java.awt.GridBagConstraints.BOTH;
                constraintsStandardDev.insets = new java.awt.Insets(5, 5, 0, 10);
                getJPanel1().add(getStandardDev(), constraintsStandardDev);

                java.awt.GridBagConstraints constraintsTValues = new java.awt.GridBagConstraints();
                constraintsTValues.gridx = 1;
                constraintsTValues.gridy = 2;
                constraintsTValues.fill = java.awt.GridBagConstraints.BOTH;
                constraintsTValues.insets = new java.awt.Insets(5, 5, 5, 10);
                getJPanel1().add(getTValues(), constraintsTValues);

                java.awt.GridBagConstraints constraintsJPanel2 = new java.awt.GridBagConstraints();
                constraintsJPanel2.gridx = 0;
                constraintsJPanel2.gridy = 0;
                constraintsJPanel2.gridheight = 3;
                constraintsJPanel2.fill = java.awt.GridBagConstraints.BOTH;
                constraintsJPanel2.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsJPanel2.weightx = 1.0;
                constraintsJPanel2.weighty = 1.0;
                constraintsJPanel2.insets = new java.awt.Insets(0, 5, 5, 5);
                getJPanel1().add(getJPanel2(), constraintsJPanel2);
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
                ivjJPanel2.setBorder(new javax.swing.border.EtchedBorder());
                ivjJPanel2.setLayout(new java.awt.GridBagLayout());

                java.awt.GridBagConstraints constraintsJLabel2 = new java.awt.GridBagConstraints();
                constraintsJLabel2.gridx = 0;
                constraintsJLabel2.gridy = 0;
                constraintsJLabel2.insets = new java.awt.Insets(0, 5, 0, 0);
                getJPanel2().add(getJLabel2(), constraintsJLabel2);

                java.awt.GridBagConstraints constraintsJLabel1 = new java.awt.GridBagConstraints();
                constraintsJLabel1.gridx = 0;
                constraintsJLabel1.gridy = 1;
                constraintsJLabel1.insets = new java.awt.Insets(5, 5, 0, 0);
                getJPanel2().add(getJLabel1(), constraintsJLabel1);

                java.awt.GridBagConstraints constraintsJLabel4 = new java.awt.GridBagConstraints();
                constraintsJLabel4.gridx = 0;
                constraintsJLabel4.gridy = 2;
                constraintsJLabel4.insets = new java.awt.Insets(5, 5, 0, 0);
                getJPanel2().add(getJLabel4(), constraintsJLabel4);

                java.awt.GridBagConstraints constraintsJLabel3 = new java.awt.GridBagConstraints();
                constraintsJLabel3.gridx = 1;
                constraintsJLabel3.gridy = 0;
                constraintsJLabel3.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel3.weightx = 1.0;
                getJPanel2().add(getJLabel3(), constraintsJLabel3);

                java.awt.GridBagConstraints constraintsEstimationMethodLabel = new java.awt.GridBagConstraints();
                constraintsEstimationMethodLabel.gridx = 1;
                constraintsEstimationMethodLabel.gridy = 1;
                constraintsEstimationMethodLabel.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsEstimationMethodLabel.weightx = 1.0;
                constraintsEstimationMethodLabel.insets = new java.awt.Insets(
                        5, 0, 0, 0);
                getJPanel2().add(getEstimationMethodLabel(),
                        constraintsEstimationMethodLabel);

                java.awt.GridBagConstraints constraintsEstimationPeriodLabel = new java.awt.GridBagConstraints();
                constraintsEstimationPeriodLabel.gridx = 1;
                constraintsEstimationPeriodLabel.gridy = 2;
                constraintsEstimationPeriodLabel.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsEstimationPeriodLabel.weightx = 1.0;
                constraintsEstimationPeriodLabel.insets = new java.awt.Insets(
                        5, 0, 0, 0);
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
     * Return the JScrollPane11 property value.
     * 
     * @return javax.swing.JScrollPane
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JScrollPane getJScrollPane11() {
        if (ivjJScrollPane11 == null) {
            try {
                ivjJScrollPane11 = new javax.swing.JScrollPane();
                ivjJScrollPane11.setName("JScrollPane11");
                getJScrollPane11().setViewportView(getEquationPanel1());
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJScrollPane11;
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
                ivjJTabbedPane1.insertTab("Matrix (VEC)", null, getMatrixVEC(),
                        null, 0);
                ivjJTabbedPane1.insertTab("Matrix (VAR)", null, getMatrixVAR(),
                        null, 1);
                ivjJTabbedPane1.insertTab("Output (save/print)", null,
                        getText(), null, 2);
                ivjJTabbedPane1.insertTab("Stats", null, getResultFieldStats(),
                        null, 3);
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
     * Return the TextAreaVAR property value.
     * 
     * @return com.jstatcom.component.MultiLineLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private com.jstatcom.component.MultiLineLabel getLabelVAR() {
        if (ivjLabelVAR == null) {
            try {
                ivjLabelVAR = new com.jstatcom.component.MultiLineLabel();
                ivjLabelVAR.setName("LabelVAR");
                ivjLabelVAR.setPreferredSize(new java.awt.Dimension(500, 40));
                ivjLabelVAR
                        .setText("The  VEC model analyzed can be linearly rewritten, such that it takes the usual VAR form.\nThe panel below shows the VAR representation of the estimated VEC model.");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjLabelVAR;
    }

    private EqTermAR getLaggedEndogenous() {
        if (ivjLaggedEndogenous == null) {
            try {
                ivjLaggedEndogenous = new EqTermAR();
                ivjLaggedEndogenous.setName("LaggedEndogenous");
                ivjLaggedEndogenous.setSymbolNameVariables(Ndy_Def.name);
                ivjLaggedEndogenous.setStartLag(1);
                ivjLaggedEndogenous.setSymbolNameLags(pdy_Def.name);
                ivjLaggedEndogenous.setBounds(169, 32, 20, 20);
                ivjLaggedEndogenous.setSymbolNameCoeff(G_Def.name);
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

    private EqTermAR getLaggedEndogenous1() {
        if (ivjLaggedEndogenous1 == null) {
            try {
                ivjLaggedEndogenous1 = new EqTermAR();
                ivjLaggedEndogenous1.setName("LaggedEndogenous1");
                ivjLaggedEndogenous1.setSymbolNameVariables(Ny_Def.name);
                ivjLaggedEndogenous1.setStartLag(1);
                ivjLaggedEndogenous1.setSymbolNameLags(py_Def.name);
                ivjLaggedEndogenous1.setBounds(169, 32, 20, 20);
                ivjLaggedEndogenous1.setSymbolNameCoeff(A_Def.name);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjLaggedEndogenous1;
    }

    private EqTermLHS getLHS() {
        if (ivjLHS == null) {
            try {
                ivjLHS = new EqTermLHS();
                ivjLHS.setName("LHS");
                ivjLHS.setSymbolNameCoeff(G0_Def.name);
                ivjLHS.setBounds(114, 61, 20, 20);
                ivjLHS.setSymbolNameVariables(Ndy_Def.name);
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

    private EqTermLHS getLHS1() {
        if (ivjLHS1 == null) {
            try {
                ivjLHS1 = new EqTermLHS();
                ivjLHS1.setName("LHS1");
                ivjLHS1.setSymbolNameCoeff(A0_Def.name);
                ivjLHS1.setBounds(114, 61, 20, 20);
                ivjLHS1.setSymbolNameVariables(Ny_Def.name);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjLHS1;
    }

    /**
     * Return the MatrixVAR property value.
     * 
     * @return javax.swing.JPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JPanel getMatrixVAR() {
        if (ivjMatrixVAR == null) {
            try {
                ivjMatrixVAR = new javax.swing.JPanel();
                ivjMatrixVAR.setName("MatrixVAR");
                ivjMatrixVAR.setBorder(new TitledBorder(new BevelBorder(
                        BevelBorder.LOWERED), " VAR Representation ",
                        TitledBorder.RIGHT, TitledBorder.TOP));
                ivjMatrixVAR.setLayout(new java.awt.BorderLayout());
                getMatrixVAR().add(getJScrollPane11(), "Center");
                getMatrixVAR().add(getLabelVAR(), "North");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjMatrixVAR;
    }

    /**
     * Return the MatrixVEC property value.
     * 
     * @return javax.swing.JPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JPanel getMatrixVEC() {
        if (ivjMatrixVEC == null) {
            try {
                ivjMatrixVEC = new javax.swing.JPanel();
                ivjMatrixVEC.setName("MatrixVEC");
                ivjMatrixVEC.setBorder(new TitledBorder(new BevelBorder(
                        BevelBorder.LOWERED), " VECM Estimation Results ",
                        TitledBorder.RIGHT, TitledBorder.TOP));
                ivjMatrixVEC.setLayout(new java.awt.BorderLayout());
                getMatrixVEC().add(getJScrollPane1(), "Center");
                getMatrixVEC().add(getJPanel1(), "North");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjMatrixVEC;
    }

    private EqTermDefault getResiduals() {
        if (ivjResiduals == null) {
            try {
                ivjResiduals = new EqTermDefault();
                ivjResiduals.setName("Residuals");
                ivjResiduals.setBounds(179, 22, 20, 20);
                ivjResiduals.setSymbolNameVariables(Nu_Def.name);
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

    private EqTermDefault getResiduals1() {
        if (ivjResiduals1 == null) {
            try {
                ivjResiduals1 = new EqTermDefault();
                ivjResiduals1.setName("Residuals1");
                ivjResiduals1.setBounds(179, 22, 20, 20);
                ivjResiduals1.setSymbolNameVariables(Nu_Def.name);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjResiduals1;
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
                ivjText.setToolTipText("RIGHT click mouse over textarea");
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
            setSize(536, 395);
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

        // user code end
    }

    public void shown(boolean isShown) {

        if (isShown) {
            action_Coefficients();

            getCoefficients().setSelected(true);
            getJTabbedPane1().setSelectedIndex(0);

            if (global().get(cv_u_hat_Def).isEmpty()) {

                computeEstimation();
            }
        }

    }
}