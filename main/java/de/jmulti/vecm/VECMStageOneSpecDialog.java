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

package de.jmulti.vecm;

import java.awt.CardLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import com.jstatcom.component.MultiLineLabel;
import com.jstatcom.component.NumSelector;
import com.jstatcom.component.StdMessages;
import com.jstatcom.equation.EqPanel;
import com.jstatcom.equation.EqTermDefault;
import com.jstatcom.equation.EqTermLHS;
import com.jstatcom.model.JSCData;
import com.jstatcom.model.JSCInt;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.JSCSArray;
import com.jstatcom.model.JSCTypeDef;
import com.jstatcom.model.JSCTypes;
import com.jstatcom.model.ModelPanel;
import com.jstatcom.model.NumberRange;
import com.jstatcom.model.NumberRangeTypes;
import com.jstatcom.model.Scope;
import com.jstatcom.model.SymbolTable;
import com.jstatcom.util.UMatrix;
import com.jstatcom.util.UStringArray;

/**
 * A dialog to specify the estimation of the cointegration relation in the first
 * stage of a two stage procedure. The selected settings can be accessed by the
 * <code>getSelectionStatus</code> method.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
public final class VECMStageOneSpecDialog extends JDialog {
    private static final Logger log = Logger
            .getLogger(VECMStageOneSpecDialog.class);

    private static final JSCTypeDef nec_vec = new JSCTypeDef("nec_vec",
            JSCTypes.SARRAY, "EC equation names");

    private int r = 1;

    private SymbolTable symbolTable = null;

    private ModelPanel ivjJDialogContentPane = null;

    private JButton ivjDefaultsButton = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private JPanel ivjJPanel1 = null;

    private JButton ivjOKButton = null;

    private JPanel ivjJPanel2 = null;

    private JCheckBox ivjCheckBoxEstimateFromSingleEquation = null;

    private JCheckBox ivjCheckBoxJohansen = null;

    private JCheckBox ivjCheckBoxSetAll = null;

    private EqPanel ivjEquationPanel = null;

    private EqTermDefault ivjEquationTermDeterministic = null;

    private EqTermDefault ivjEquationTermEndogenous = null;

    private EqTermLHS ivjEquationTermLHS = null;

    private JPanel ivjPanelCheckBoxes = null;

    private NumSelector ivjTextField_EquationNumber = null;

    private NumSelector ivjTextField_Johansen = null;

    private JCheckBox ivjCheckBoxS2S = null;

    private JPanel ivjBetaResSpecPanel = null;

    private JPanel ivjJPanel3 = null;

    private MultiLineLabel ivjMultiLineLabel = null;

    private JLabel ivjResLabel = null;

    class IvjEventHandler implements java.awt.event.ActionListener,
            java.awt.event.ItemListener, java.beans.PropertyChangeListener {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == VECMStageOneSpecDialog.this
                    .getDefaultsButton())
                connEtoC2();
            if (e.getSource() == VECMStageOneSpecDialog.this.getOKButton())
                connEtoC1();
        };

        public void itemStateChanged(java.awt.event.ItemEvent e) {
            if (e.getSource() == VECMStageOneSpecDialog.this
                    .getCheckBoxJohansen())
                connPtoP1SetTarget();
            if (e.getSource() == VECMStageOneSpecDialog.this
                    .getCheckBoxEstimateFromSingleEquation())
                connPtoP2SetTarget();
            if (e.getSource() == VECMStageOneSpecDialog.this
                    .getCheckBoxSetAll())
                connEtoC3(e);
            if (e.getSource() == VECMStageOneSpecDialog.this
                    .getCheckBoxJohansen())
                connEtoC5(e);
            if (e.getSource() == VECMStageOneSpecDialog.this
                    .getCheckBoxEstimateFromSingleEquation())
                connEtoC6(e);
            if (e.getSource() == VECMStageOneSpecDialog.this.getCheckBoxS2S())
                connEtoC7(e);
        };

        public void propertyChange(java.beans.PropertyChangeEvent evt) {
            if (evt.getSource() == VECMStageOneSpecDialog.this
                    .getTextField_Johansen()
                    && (evt.getPropertyName().equals("number")))
                connEtoC4(evt);
        };
    };

    /**
     * VECMStageOneSpecDialog constructor comment.
     */
    public VECMStageOneSpecDialog() {
        super();
        initialize();
    }

    /**
     * VECMStageOneSpecDialog constructor comment.
     * 
     * @param owner
     *            java.awt.Frame
     * @param modal
     *            boolean
     */
    public VECMStageOneSpecDialog(java.awt.Frame owner, boolean modal) {
        super(owner, modal);
        initialize();
    }

    /**
     * Set to r - 1 equations.
     */
    private void checkBoxEstimateFromSingleEquation_ItemStateChanged() {
        if (!getCheckBoxEstimateFromSingleEquation().isSelected())
            return;

        setEquationsDisplay(r - 1);
    }

    /**
     * Adjust selectable coint relations of necessary.
     */
    private void checkBoxJohansen_ItemStateChanged() {
        if (!getCheckBoxJohansen().isSelected())
            return;

        setEquationsDisplay(r - getTextField_Johansen().getIntNumber());
    }

    /**
     * Show panel to input restrictions.
     */
    private void checkBoxS2S_ItemStateChanged() {
        CardLayout card = (CardLayout) getJPanel3().getLayout();
        if (getCheckBoxS2S().isSelected())
            card.show(getJPanel3(), getBetaResSpecPanel().getName());
        else
            card.show(getJPanel3(), getEquationPanel().getName());

    }

    /**
     * Do not estimate any coint relation, set all manually if selected.
     */
    private void checkBoxSetAll_ItemStateChanged() {
        if (!getCheckBoxSetAll().isSelected())
            return;

        setEquationsDisplay(r);
    }

    /**
     * connEtoC1: (OKButton.action. -->
     * VECMStageOneSpecDialog.oKButton_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC1() {
        try {
            // user code begin {1}
            // user code end
            this.oKButton_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC2: (DefaultsButton.action. -->
     * VECMStageOneSpecDialog.defaultsButton_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC2() {
        try {
            // user code begin {1}
            // user code end
            this.defaultsButton_ActionEvents();
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
     * (CheckBoxSetAll.item.itemStateChanged(java.awt.event.ItemEvent) -->
     * VECMStageOneSpecDialog.checkBoxSetAll_ItemStateChanged()V)
     * 
     * @param arg1
     *            java.awt.event.ItemEvent
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC3(java.awt.event.ItemEvent arg1) {
        try {
            // user code begin {1}
            // user code end
            this.checkBoxSetAll_ItemStateChanged();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC4: (TextField_Johansen.number -->
     * VECMStageOneSpecDialog.textField_Johansen_Number()V)
     * 
     * @param arg1
     *            java.beans.PropertyChangeEvent
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC4(java.beans.PropertyChangeEvent arg1) {
        try {
            // user code begin {1}
            // user code end
            this.textField_Johansen_Number();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC5:
     * (CheckBoxJohansen.item.itemStateChanged(java.awt.event.ItemEvent) -->
     * VECMStageOneSpecDialog.checkBoxJohansen_ItemStateChanged()V)
     * 
     * @param arg1
     *            java.awt.event.ItemEvent
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC5(java.awt.event.ItemEvent arg1) {
        try {
            // user code begin {1}
            // user code end
            this.checkBoxJohansen_ItemStateChanged();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC6:
     * (CheckBoxEstimateFromSingleEquation.item.itemStateChanged(java.awt.event.ItemEvent)
     * -->
     * VECMStageOneSpecDialog.checkBoxEstimateFromSingleEquation_ItemStateChanged()V)
     * 
     * @param arg1
     *            java.awt.event.ItemEvent
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC6(java.awt.event.ItemEvent arg1) {
        try {
            // user code begin {1}
            // user code end
            this.checkBoxEstimateFromSingleEquation_ItemStateChanged();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC7: (CheckBoxS2S.item.itemStateChanged(java.awt.event.ItemEvent)
     * --> VECMStageOneSpecDialog.checkBoxS2S_ItemStateChanged()V)
     * 
     * @param arg1
     *            java.awt.event.ItemEvent
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC7(java.awt.event.ItemEvent arg1) {
        try {
            // user code begin {1}
            // user code end
            this.checkBoxS2S_ItemStateChanged();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connPtoP1SetTarget: (CheckBoxJohansen.selected <-->
     * TextField_Johansen.enabled)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connPtoP1SetTarget() {
        /* Set the target from the source */
        try {
            getTextField_Johansen().setEnabled(
                    getCheckBoxJohansen().isSelected());
            // user code begin {1}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connPtoP2SetTarget: (CheckBoxEstimateFromSingleEquation.selected <-->
     * TextField_EquationNumber.enabled)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connPtoP2SetTarget() {
        /* Set the target from the source */
        try {
            getTextField_EquationNumber().setEnabled(
                    getCheckBoxEstimateFromSingleEquation().isSelected());
            // user code begin {1}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * Sets the default values.
     */
    private void defaultsButton_ActionEvents() {
        int sel = JOptionPane
                .showConfirmDialog(
                        this,
                        "Set back first stage estimation settings to default values?\nThe default is to estimate all coint relations unrestricted by Johansen.",
                        "Default Settings?", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
        if (sel != JOptionPane.YES_OPTION)
            return;

        setDefaults();

    }

    private void setDefaults() {
        getTextField_Johansen().setNumber(r);
        getCheckBoxJohansen().setSelected(true);
        setEquationsDisplay(0);

    }

    /**
     * Return the BetaResSpecPanel property value.
     * 
     * @return javax.swing.JPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JPanel getBetaResSpecPanel() {
        if (ivjBetaResSpecPanel == null) {
            try {
                ivjBetaResSpecPanel = new javax.swing.JPanel();
                ivjBetaResSpecPanel.setName("BetaResSpecPanel");
                ivjBetaResSpecPanel.setLayout(null);
                getBetaResSpecPanel().add(getMultiLineLabel(),
                        getMultiLineLabel().getName());
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjBetaResSpecPanel;
    }

    /**
     * Return the CheckBoxEstimateFromSingleEquation property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getCheckBoxEstimateFromSingleEquation() {
        if (ivjCheckBoxEstimateFromSingleEquation == null) {
            try {
                ivjCheckBoxEstimateFromSingleEquation = new javax.swing.JCheckBox();
                ivjCheckBoxEstimateFromSingleEquation
                        .setName("CheckBoxEstimateFromSingleEquation");
                ivjCheckBoxEstimateFromSingleEquation.setSelected(false);
                ivjCheckBoxEstimateFromSingleEquation
                        .setAlignmentY(java.awt.Component.CENTER_ALIGNMENT);
                ivjCheckBoxEstimateFromSingleEquation
                        .setText("Estimate ONE cointegration relation from VECM-equation number");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjCheckBoxEstimateFromSingleEquation;
    }

    /**
     * Return the CheckBoxJohansen property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getCheckBoxJohansen() {
        if (ivjCheckBoxJohansen == null) {
            try {
                ivjCheckBoxJohansen = new javax.swing.JCheckBox();
                ivjCheckBoxJohansen.setName("CheckBoxJohansen");
                ivjCheckBoxJohansen.setSelected(true);
                ivjCheckBoxJohansen
                        .setAlignmentY(java.awt.Component.CENTER_ALIGNMENT);
                ivjCheckBoxJohansen
                        .setText("Estimate coint relations by Johansen approach, number of coint relations");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjCheckBoxJohansen;
    }

    /**
     * Return the CheckBoxS2S property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getCheckBoxS2S() {
        if (ivjCheckBoxS2S == null) {
            try {
                ivjCheckBoxS2S = new javax.swing.JCheckBox();
                ivjCheckBoxS2S.setName("CheckBoxS2S");
                ivjCheckBoxS2S
                        .setText("Estimate coint relations by S2S with possible restrictions on beta");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjCheckBoxS2S;
    }

    /**
     * Return the CheckBoxSetAll property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getCheckBoxSetAll() {
        if (ivjCheckBoxSetAll == null) {
            try {
                ivjCheckBoxSetAll = new javax.swing.JCheckBox();
                ivjCheckBoxSetAll.setName("CheckBoxSetAll");
                ivjCheckBoxSetAll
                        .setText("Do not estimate any cointegration relation, set the below defined");
                ivjCheckBoxSetAll.setMargin(new java.awt.Insets(2, 2, 2, 2));
                ivjCheckBoxSetAll
                        .setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
                ivjCheckBoxSetAll
                        .setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjCheckBoxSetAll;
    }

    /**
     * Return the DefaultsButton property value.
     * 
     * @return javax.swing.JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JButton getDefaultsButton() {
        if (ivjDefaultsButton == null) {
            try {
                ivjDefaultsButton = new javax.swing.JButton();
                ivjDefaultsButton.setName("DefaultsButton");
                ivjDefaultsButton.setPreferredSize(new java.awt.Dimension(100,
                        25));
                ivjDefaultsButton.setText("Defaults");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjDefaultsButton;
    }

    private EqPanel getEquationPanel() {
        if (ivjEquationPanel == null) {
            try {
                ivjEquationPanel = new EqPanel();
                ivjEquationPanel.setName("EquationPanel");
                getEquationPanel().add(getEquationTermLHS(),
                        getEquationTermLHS().getName());
                getEquationPanel().add(getEquationTermEndogenous(),
                        getEquationTermEndogenous().getName());
                getEquationPanel().add(getEquationTermDeterministic(),
                        getEquationTermDeterministic().getName());
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

    private EqTermDefault getEquationTermDeterministic() {
        if (ivjEquationTermDeterministic == null) {
            try {
                ivjEquationTermDeterministic = new EqTermDefault();
                ivjEquationTermDeterministic
                        .setName("EquationTermDeterministic");
                ivjEquationTermDeterministic.setShowingTimeIndex(false);
                ivjEquationTermDeterministic.setSymbolScope(Scope.LOCAL);
                ivjEquationTermDeterministic
                        .setSymbolNameCoeff(VECMConstants.beta_d_x_Def.name);
                ivjEquationTermDeterministic.setBounds(84, 24, 20, 20);
                ivjEquationTermDeterministic.setEditable(true);
                ivjEquationTermDeterministic.setCoeffMatRequired(true);
                // user code begin {1}
                ivjEquationTermDeterministic
                        .setSymbolNameVariables(VECMConstants.Nd_ec_Def.name);
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjEquationTermDeterministic;
    }

    private EqTermDefault getEquationTermEndogenous() {
        if (ivjEquationTermEndogenous == null) {
            try {
                ivjEquationTermEndogenous = new EqTermDefault();
                ivjEquationTermEndogenous.setName("EquationTermEndogenous");
                ivjEquationTermEndogenous.setSymbolScope(Scope.LOCAL);
                ivjEquationTermEndogenous
                        .setSymbolNameCoeff(VECMConstants.beta_x_Def.name);
                ivjEquationTermEndogenous.setBounds(143, 40, 20, 20);
                ivjEquationTermEndogenous.setEditable(true);
                ivjEquationTermEndogenous.setCoeffMatRequired(true);
                // user code begin {1}
                ivjEquationTermEndogenous
                        .setSymbolNameVariables(VECMConstants.Ny_Def.name);

                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjEquationTermEndogenous;
    }

    private EqTermLHS getEquationTermLHS() {
        if (ivjEquationTermLHS == null) {
            try {
                ivjEquationTermLHS = new EqTermLHS();
                ivjEquationTermLHS.setName("EquationTermLHS");
                ivjEquationTermLHS.setSymbolScope(Scope.LOCAL);
                ivjEquationTermLHS.setBounds(41, 29, 20, 20);
                ivjEquationTermLHS.setSymbolNameVariables(nec_vec.name);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjEquationTermLHS;
    }

    private ModelPanel getJDialogContentPane() {
        if (ivjJDialogContentPane == null) {
            try {
                ivjJDialogContentPane = new ModelPanel();
                ivjJDialogContentPane.setName("JDialogContentPane");
                ivjJDialogContentPane.setLayout(new java.awt.BorderLayout());
                getJDialogContentPane().add(getJPanel1(), "South");
                getJDialogContentPane().add(getJPanel2(), "Center");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJDialogContentPane;
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
                ivjJPanel1.setPreferredSize(new java.awt.Dimension(0, 50));
                ivjJPanel1.setLayout(getJPanel1FlowLayout());
                getJPanel1().add(getOKButton(), getOKButton().getName());
                getJPanel1().add(getDefaultsButton(),
                        getDefaultsButton().getName());
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
     * Return the JPanel1FlowLayout property value.
     * 
     * @return java.awt.FlowLayout
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private java.awt.FlowLayout getJPanel1FlowLayout() {
        java.awt.FlowLayout ivjJPanel1FlowLayout = null;
        try {
            /* Create part */
            ivjJPanel1FlowLayout = new java.awt.FlowLayout();
            ivjJPanel1FlowLayout.setVgap(10);
            ivjJPanel1FlowLayout.setHgap(60);
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        ;
        return ivjJPanel1FlowLayout;
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
                ivjJPanel2.setBorder(new BevelBorder(BevelBorder.LOWERED));
                ivjJPanel2.setLayout(new java.awt.BorderLayout());
                getJPanel2().add(getPanelCheckBoxes(), "North");
                getJPanel2().add(getJPanel3(), "Center");
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
     * Return the JPanel3 property value.
     * 
     * @return javax.swing.JPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JPanel getJPanel3() {
        if (ivjJPanel3 == null) {
            try {
                ivjJPanel3 = new javax.swing.JPanel();
                ivjJPanel3.setName("JPanel3");
                ivjJPanel3.setLayout(new java.awt.CardLayout());
                getJPanel3().add(getEquationPanel(),
                        getEquationPanel().getName());
                getJPanel3().add(getBetaResSpecPanel(),
                        getBetaResSpecPanel().getName());
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJPanel3;
    }

    /**
     * Return the MultiLineLabel property value.
     * 
     * @return com.jstatcom.component.MultiLineLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private com.jstatcom.component.MultiLineLabel getMultiLineLabel() {
        if (ivjMultiLineLabel == null) {
            try {
                ivjMultiLineLabel = new com.jstatcom.component.MultiLineLabel();
                ivjMultiLineLabel.setName("MultiLineLabel");
                ivjMultiLineLabel
                        .setText("The S2S estimator takes restrictions on beta into account if they are available.\nThe restrictions can be specified and tested in the panel \"Specify and Test Restrictions on Beta\".");
                ivjMultiLineLabel
                        .setVerticalAlignment(javax.swing.SwingConstants.TOP);
                ivjMultiLineLabel.setBounds(20, 20, 622, 88);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjMultiLineLabel;
    }

    /**
     * Return the OKButton property value.
     * 
     * @return javax.swing.JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JButton getOKButton() {
        if (ivjOKButton == null) {
            try {
                ivjOKButton = new javax.swing.JButton();
                ivjOKButton.setName("OKButton");
                ivjOKButton.setPreferredSize(new java.awt.Dimension(100, 25));
                ivjOKButton.setText("OK");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjOKButton;
    }

    /**
     * Return the PanelCheckBoxes property value.
     * 
     * @return javax.swing.JPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JPanel getPanelCheckBoxes() {
        if (ivjPanelCheckBoxes == null) {
            try {
                ivjPanelCheckBoxes = new javax.swing.JPanel();
                ivjPanelCheckBoxes.setName("PanelCheckBoxes");
                ivjPanelCheckBoxes.setLayout(new java.awt.GridBagLayout());

                java.awt.GridBagConstraints constraintsCheckBoxSetAll = new java.awt.GridBagConstraints();
                constraintsCheckBoxSetAll.gridx = 0;
                constraintsCheckBoxSetAll.gridy = 0;
                constraintsCheckBoxSetAll.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsCheckBoxSetAll.anchor = java.awt.GridBagConstraints.WEST;
                constraintsCheckBoxSetAll.insets = new java.awt.Insets(5, 5, 0,
                        0);
                getPanelCheckBoxes().add(getCheckBoxSetAll(),
                        constraintsCheckBoxSetAll);

                java.awt.GridBagConstraints constraintsCheckBoxEstimateFromSingleEquation = new java.awt.GridBagConstraints();
                constraintsCheckBoxEstimateFromSingleEquation.gridx = 0;
                constraintsCheckBoxEstimateFromSingleEquation.gridy = 1;
                constraintsCheckBoxEstimateFromSingleEquation.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsCheckBoxEstimateFromSingleEquation.anchor = java.awt.GridBagConstraints.WEST;
                constraintsCheckBoxEstimateFromSingleEquation.insets = new java.awt.Insets(
                        5, 5, 0, 0);
                getPanelCheckBoxes().add(
                        getCheckBoxEstimateFromSingleEquation(),
                        constraintsCheckBoxEstimateFromSingleEquation);

                java.awt.GridBagConstraints constraintsTextField_EquationNumber = new java.awt.GridBagConstraints();
                constraintsTextField_EquationNumber.gridx = 1;
                constraintsTextField_EquationNumber.gridy = 1;
                constraintsTextField_EquationNumber.anchor = java.awt.GridBagConstraints.WEST;
                constraintsTextField_EquationNumber.insets = new java.awt.Insets(
                        5, 5, 0, 0);
                getPanelCheckBoxes().add(getTextField_EquationNumber(),
                        constraintsTextField_EquationNumber);

                java.awt.GridBagConstraints constraintsCheckBoxJohansen = new java.awt.GridBagConstraints();
                constraintsCheckBoxJohansen.gridx = 0;
                constraintsCheckBoxJohansen.gridy = 2;
                constraintsCheckBoxJohansen.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsCheckBoxJohansen.anchor = java.awt.GridBagConstraints.WEST;
                constraintsCheckBoxJohansen.insets = new java.awt.Insets(5, 5,
                        0, 0);
                getPanelCheckBoxes().add(getCheckBoxJohansen(),
                        constraintsCheckBoxJohansen);

                java.awt.GridBagConstraints constraintsTextField_Johansen = new java.awt.GridBagConstraints();
                constraintsTextField_Johansen.gridx = 1;
                constraintsTextField_Johansen.gridy = 2;
                constraintsTextField_Johansen.anchor = java.awt.GridBagConstraints.WEST;
                constraintsTextField_Johansen.weightx = 1.0;
                constraintsTextField_Johansen.weighty = 1.0;
                constraintsTextField_Johansen.insets = new java.awt.Insets(5,
                        5, 0, 0);
                getPanelCheckBoxes().add(getTextField_Johansen(),
                        constraintsTextField_Johansen);

                java.awt.GridBagConstraints constraintsCheckBoxS2S = new java.awt.GridBagConstraints();
                constraintsCheckBoxS2S.gridx = 0;
                constraintsCheckBoxS2S.gridy = 3;
                constraintsCheckBoxS2S.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsCheckBoxS2S.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsCheckBoxS2S.insets = new java.awt.Insets(5, 5, 5, 0);
                getPanelCheckBoxes().add(getCheckBoxS2S(),
                        constraintsCheckBoxS2S);

                java.awt.GridBagConstraints constraintsResLabel = new java.awt.GridBagConstraints();
                constraintsResLabel.gridx = 1;
                constraintsResLabel.gridy = 3;
                constraintsResLabel.fill = java.awt.GridBagConstraints.BOTH;
                constraintsResLabel.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsResLabel.insets = new java.awt.Insets(5, 5, 5, 0);
                getPanelCheckBoxes().add(getResLabel(), constraintsResLabel);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjPanelCheckBoxes;
    }

    /**
     * Return the JCheckBox property value.
     * 
     * @return javax.swing.JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JLabel getResLabel() {
        if (ivjResLabel == null) {
            try {
                ivjResLabel = new javax.swing.JLabel();
                ivjResLabel.setName("ResLabel");
                ivjResLabel.setText("No Restrictions on beta set.");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjResLabel;
    }

    /**
     * Gets an array of 7 data objects to define all parameters of the first
     * stage estimation. The data objects in this array have the correct names
     * defined in <code>VECMConstants</code>.
     * 
     * @return 7 x 1 data object array that can directly be set to the symbol
     *         table for two-stage estimation
     */
    public JSCData[] getSelectionStatus() {
        int singleEq = getCheckBoxEstimateFromSingleEquation().isSelected() ? 1
                : 0;
        int joh = getCheckBoxJohansen().isSelected() ? 1 : 0;
        int s2s = getCheckBoxS2S().isSelected() ? 1 : 0;

        // check integrity of beta_x and beta_x_d, set defaults if wrong
        String error = checkBetaRes();
        if (error != null)
            setDefaults();

        JSCData[] selState = new JSCData[] {
                new JSCInt(VECMConstants.firstStage_bySingleEquation_Def.name,
                        singleEq),
                new JSCInt(VECMConstants.firstStage_byJoh_Def.name, joh),
                new JSCInt(VECMConstants.firstStage_byS2S_Def.name, s2s),
                symbolTable.get(VECMConstants.beta_d_x_Def).getJSCNArray()
                        .copy(),
                symbolTable.get(VECMConstants.beta_x_Def).getJSCNArray().copy(),
                new JSCInt(VECMConstants.firstStage_equaIdx_Def.name,
                        getTextField_EquationNumber().getIntNumber()),
                new JSCInt(VECMConstants.firstStage_crJoh_Def.name,
                        getTextField_Johansen().getIntNumber()) };

        return selState;
    }

    /**
     * Check integrity of user specified beta coefficients.
     * 
     * @return error message or <code>null</code> if ok
     */
    private String checkBetaRes() {
        JSCNArray beta_x = symbolTable.get(VECMConstants.beta_x_Def)
                .getJSCNArray();
        if (beta_x.rows() > 0) {
            double[][] check = beta_x.doubleArray();
            for (int i = 0; i < check.length; i++)
                if (UMatrix.getNonzeroDoubleCount(check[i]) < 2) {
                    return "At least 2 coefficients in each coint relation for the lagged endogenous levels\nmust be different from zero. Please change your input.";
                }
            if (symbolTable.get(VECMConstants.Nd_ec_Def).getJSCSArray().rows() > 0) {
                JSCNArray beta_d_x = symbolTable
                        .get(VECMConstants.beta_d_x_Def).getJSCNArray();
                boolean nonzero = false;
                for (int i = 0; i < beta_d_x.rows(); i++)
                    if (beta_d_x.doubleAt(i, 0) != 0) {
                        nonzero = true;
                        break;
                    }

                if (!nonzero) {
                    return "Not all coefficients for deterministics in the EC term can be zero.\nPlease change your input.";

                }
            }
        }
        return null;
    }

    private NumSelector getTextField_EquationNumber() {
        if (ivjTextField_EquationNumber == null) {
            try {
                ivjTextField_EquationNumber = new NumSelector();
                ivjTextField_EquationNumber.setName("TextField_EquationNumber");
                ivjTextField_EquationNumber
                        .setPreferredSize(new java.awt.Dimension(50, 20));
                ivjTextField_EquationNumber.setNumber(1.0);
                ivjTextField_EquationNumber.setEnabled(false);
                ivjTextField_EquationNumber.setRangeExpr("[1,1]");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjTextField_EquationNumber;
    }

    private NumSelector getTextField_Johansen() {
        if (ivjTextField_Johansen == null) {
            try {
                ivjTextField_Johansen = new NumSelector();
                ivjTextField_Johansen.setName("TextField_Johansen");
                ivjTextField_Johansen.setPreferredSize(new java.awt.Dimension(
                        50, 20));
                ivjTextField_Johansen.setNumber(1.0);
                ivjTextField_Johansen.setRangeExpr("[1,1]");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjTextField_Johansen;
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
        getCheckBoxJohansen().addItemListener(ivjEventHandler);
        getDefaultsButton().addActionListener(ivjEventHandler);
        getOKButton().addActionListener(ivjEventHandler);
        getCheckBoxEstimateFromSingleEquation()
                .addItemListener(ivjEventHandler);
        getCheckBoxSetAll().addItemListener(ivjEventHandler);
        getTextField_Johansen().addPropertyChangeListener(ivjEventHandler);
        getCheckBoxJohansen().addItemListener(ivjEventHandler);
        getCheckBoxEstimateFromSingleEquation()
                .addItemListener(ivjEventHandler);
        getCheckBoxS2S().addItemListener(ivjEventHandler);
        connPtoP1SetTarget();
        connPtoP2SetTarget();
    }

    /**
     * Initialize the class.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initialize() {
        try {
            // user code begin {1}
            // user code end
            setName("VECMStageOneSpecDialog");
            setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
            setResizable(false);
            setSize(706, 471);
            setTitle("Specify Estimation of Cointegration Relation in First Stage");
            setContentPane(getJDialogContentPane());
            initConnections();
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}
        ButtonGroup bg = new ButtonGroup();
        bg.add(getCheckBoxEstimateFromSingleEquation());
        bg.add(getCheckBoxJohansen());
        bg.add(getCheckBoxS2S());
        bg.add(getCheckBoxSetAll());

        symbolTable = getJDialogContentPane().local();

        TitledBorder title = new TitledBorder(
                new BevelBorder(BevelBorder.LOWERED),
                "Set Coefficients for Cointegration Relation(s) not Being Estimated",
                TitledBorder.RIGHT, TitledBorder.TOP);
        getEquationPanel().setBorder(title);

        getBetaResSpecPanel().setBorder(new BevelBorder(BevelBorder.LOWERED));
        getResLabel().setForeground(java.awt.Color.red);

        // user code end
    }

    /**
     * Confirm settings, check if external specified relations have at least 2
     * nonzero values in each row (beta_x). Check also, if beta_d_x does not
     * have only 0's in it.
     */
    private void oKButton_ActionEvents() {

        // No checks for S2S first stage estimator.
        if (getCheckBoxS2S().isSelected()) {
            setVisible(false);
            return;
        }

        // Check whether first stage coint relation is properly specified.
        String error = checkBetaRes();
        if (error != null) {
            StdMessages.errorInput(error);
            return;
        }

        setVisible(false);
    }

    /**
     * Sets the display to show whether restrictions on beta are set or not.
     * 
     * @param betaSet
     *            <code>true</code> if restrictions on beta are set,
     *            <code>false</code> otherwise
     */
    public void setBetaResSet(boolean betaSet) {
        if (betaSet) {
            getResLabel().setText("Restrictions on beta set.");
            getResLabel().setForeground(java.awt.Color.blue);
        } else {
            getResLabel().setText("No restrictions on beta set.");
            getResLabel().setForeground(java.awt.Color.red);

        }

    }

    /**
     * Sets the data necessary for the display of the selection possibilities.
     * Should be invoked, before this dialog is shown.
     * 
     * @param ny
     *            names of endogenous selected variables
     * @param nd_ec
     *            names of deterministics restricted to EC term
     * @param r
     *            numer of coint relations
     */
    public void setDisplay(JSCSArray ny, JSCSArray nd_ec, JSCNArray beta_x,
            JSCNArray beta_d_x, int cr_joh, int r) {

        this.r = r;
        getTextField_EquationNumber().setNumberRange(
                new NumberRange(1, ny.rows(), NumberRangeTypes.CLOSED));

        getTextField_Johansen().setNumberRange(
                new NumberRange(1, r, NumberRangeTypes.CLOSED));

        // Copy data objects to respective symbol table.
        symbolTable.get(VECMConstants.cointRank_Def).setJSCData(
                new JSCInt("r", r));
        symbolTable.get(VECMConstants.Ny_Def).setJSCData(ny);
        symbolTable.get(VECMConstants.Nd_ec_Def).setJSCData(nd_ec);

        // Set the selection is made.
        symbolTable.get(VECMConstants.beta_x_Def).setJSCData(beta_x);
        symbolTable.get(VECMConstants.beta_d_x_Def).setJSCData(beta_d_x);
        getTextField_Johansen().setNumber(cr_joh);

        checkBoxJohansen_ItemStateChanged();
        checkBoxEstimateFromSingleEquation_ItemStateChanged();
        checkBoxSetAll_ItemStateChanged();

    }

    /**
     * Sets the number of the displayed equations and prepares the equation
     * display.
     * 
     * @param nrExtern
     *            number of coint relation that need to be specified externally
     */
    private void setEquationsDisplay(int nrExtern) {

        if (nrExtern == 0) {
            symbolTable.get(nec_vec).clear();
            symbolTable.get(VECMConstants.beta_d_x_Def).clear();
            symbolTable.get(VECMConstants.beta_x_Def).clear();
        }

        String[] ecNames = UStringArray.createNamedIndex("ec", UMatrix.seqa(1,
                1, nrExtern));
        symbolTable.get(nec_vec).setJSCData(new JSCSArray("ec", ecNames));

        // I should preserve as much of the old settings as possible, if
        // something changed.

        // If nrExtern is bigger than before, just append the difference
        // rows, this keeps the old numbers.
        int d_ec_cols = symbolTable.get(VECMConstants.Nd_ec_Def).getJSCSArray()
                .rows();
        if (d_ec_cols > 0) {
            JSCNArray beta_d_x = symbolTable.get(VECMConstants.beta_d_x_Def)
                    .getJSCNArray();
            if (beta_d_x.rows() < nrExtern)
                beta_d_x.appendRows(new JSCNArray("beta_d_x",
                        new double[nrExtern - beta_d_x.rows()][d_ec_cols]));

            // If nrExtern is smaller than before, just get first nrExtern rows.
            if (beta_d_x.rows() > nrExtern)
                beta_d_x.setVal(beta_d_x.getRows(0, nrExtern - 1));

            // Adjust the columns if necessary (ND_ec might have changed).
            if (beta_d_x.cols() < d_ec_cols)
                beta_d_x
                        .appendCols(new JSCNArray("beta_d_x",
                                new double[beta_d_x.rows()][d_ec_cols
                                        - beta_d_x.cols()]));
            if (beta_d_x.cols() > d_ec_cols)
                beta_d_x.setVal(beta_d_x.getCols(0, d_ec_cols - 1));

        } else
            symbolTable.get(VECMConstants.beta_d_x_Def).clear();

        // Same procedure for beta_d
        int ny_cols = symbolTable.get(VECMConstants.Ny_Def).getJSCSArray()
                .rows();
        if (ny_cols > 0) {

            JSCNArray beta_x = symbolTable.get(VECMConstants.beta_x_Def)
                    .getJSCNArray();
            if (beta_x.rows() < nrExtern)
                beta_x.appendRows(new JSCNArray("beta_x", new double[nrExtern
                        - beta_x.rows()][ny_cols]));

            // If nrExtern is smaller than before, just get first nrExtern rows.
            if (beta_x.rows() > nrExtern)
                beta_x.setVal(beta_x.getRows(0, nrExtern - 1));

            if (beta_x.cols() < ny_cols)
                beta_x.appendCols(new JSCNArray("beta_x", new double[beta_x
                        .rows()][ny_cols - beta_x.cols()]));
            if (beta_x.cols() > ny_cols)
                beta_x.setVal(beta_x.getCols(0, ny_cols - 1));
        } else
            symbolTable.get(VECMConstants.beta_x_Def).clear();

    }

    /**
     * Adjust the display of the selectable ec equations.
     */
    private void textField_Johansen_Number() {
        if (getCheckBoxJohansen().isSelected())
            setEquationsDisplay(r - getTextField_Johansen().getIntNumber());
    }
}