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

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import com.jstatcom.component.StdMessages;
import com.jstatcom.component.TopFrameReference;
import com.jstatcom.model.JSCData;
import com.jstatcom.model.JSCInt;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.JSCSArray;
import com.jstatcom.model.JSCString;
import com.jstatcom.model.ModelPanel;
import com.jstatcom.model.SymbolEvent;
import com.jstatcom.model.SymbolListener;


/**
 * Panel for selecting the estimation strategy.
 * 
 * @author A. Benkwitz, <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
final class VECMEstStratPanel extends ModelPanel {
    private static final Logger log = Logger.getLogger(VECMEstStratPanel.class);

    // to avoid that selection changed is executed
    private boolean projectLoadOnGoing = false;

    /**
     * Dialog for struct coeff settings.
     */
    private VECMStructCoeffDialog structCoeffDialog = null;

    /**
     * Dialog for first stage estimation settings.
     */
    private VECMStageOneSpecDialog stageOneDialog = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private JLabel ivjJLabel1 = null;

    private JLabel ivjJLabel2 = null;

    private JCheckBox ivjCheckBoxJoh = null;

    private JCheckBox ivjCheckBoxS2S = null;

    private JCheckBox ivjCheckBoxTwoStage = null;

    private JButton ivjFirstStageButton = null;

    private JLabel ivjLabel1 = null;

    private JLabel ivjLabel11 = null;

    private JComboBox ivjSecondStageCombo = null;

    private JCheckBox ivjCheckBoxStructForm = null;

    private JButton ivjStructFormButton = null;

    private JLabel ivjResLabel = null;

    class IvjEventHandler implements java.awt.event.ActionListener,
            java.awt.event.ItemListener {

        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == VECMEstStratPanel.this.getFirstStageButton())
                connEtoC1();
            if (e.getSource() == VECMEstStratPanel.this.getSecondStageCombo())
                connEtoC7();
            if (e.getSource() == VECMEstStratPanel.this.getStructFormButton())
                connEtoC8();
            if (e.getSource() == VECMEstStratPanel.this.getRemoveResButton())
                connEtoC4();
        };

        public void itemStateChanged(java.awt.event.ItemEvent e) {
            if (e.getSource() == VECMEstStratPanel.this.getCheckBoxJoh())
                connEtoC3(e);
            if (e.getSource() == VECMEstStratPanel.this.getCheckBoxS2S())
                connEtoC5(e);
            if (e.getSource() == VECMEstStratPanel.this.getCheckBoxTwoStage())
                connEtoC6(e);
            if (e.getSource() == VECMEstStratPanel.this.getCheckBoxStructForm())
                connEtoC2(e);
        };
    };

    private JButton ivjRemoveResButton = null;

    /**
     * VECMSelectEstimationStrategy constructor comment.
     */
    public VECMEstStratPanel() {
        super();
        initialize();
    }

    /**
     * Selects/deselects Johansen estimation.
     */
    private void checkBoxJoh_ItemStateChanged() {
        VECMDataManagerObs.getInstance().removeData();
    }

    /**
     * Selects/deselects S2S estimation.
     */
    private void checkBoxS2S_ItemStateChanged() {
        VECMDataManagerObs.getInstance().removeData();

    }

    /**
     * Comment
     */
    private void checkBoxStructForm_ItemStateChanged() {
        boolean enabled = getCheckBoxStructForm().isSelected();
        getStructFormButton().setEnabled(enabled);
        VECMDataManagerObs.getInstance().removeData();
    }

    /**
     * Selects/deselects two stage estimation.
     */
    private void checkBoxTwoStage_ItemStateChanged() {
        boolean enabled = getCheckBoxTwoStage().isSelected();
        getFirstStageButton().setEnabled(enabled);
        getSecondStageCombo().setEnabled(enabled);
        getCheckBoxStructForm().setEnabled(enabled);
        getStructFormButton().setEnabled(
                enabled && getCheckBoxStructForm().isSelected());

        VECMDataManagerObs.getInstance().removeData();
    }

    /**
     * connEtoC1: (FirstStageButton.action. -->
     * VECMEstStratPanel.firstStageButton_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC1() {
        try {
            // user code begin {1}
            // user code end
            this.firstStageButton_ActionEvents();
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
     * (CheckBoxStructForm.item.itemStateChanged(java.awt.event.ItemEvent) -->
     * VECMEstStratPanel.checkBoxStructForm_ItemStateChanged()V)
     * 
     * @param arg1
     *            java.awt.event.ItemEvent
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC2(java.awt.event.ItemEvent arg1) {
        try {
            // user code begin {1}
            // user code end
            this.checkBoxStructForm_ItemStateChanged();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC3: (CheckBoxJoh.item.itemStateChanged(java.awt.event.ItemEvent)
     * --> VECMEstStratPanel.checkBoxJoh_ItemStateChanged()V)
     * 
     * @param arg1
     *            java.awt.event.ItemEvent
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC3(java.awt.event.ItemEvent arg1) {
        try {
            // user code begin {1}
            // user code end
            this.checkBoxJoh_ItemStateChanged();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC4: (RemoveResButton.action. -->
     * VECMEstStratPanel.removeResButton_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC4() {
        try {
            // user code begin {1}
            // user code end
            this.removeResButton_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC5: (CheckBoxS2S.item.itemStateChanged(java.awt.event.ItemEvent)
     * --> VECMEstStratPanel.checkBoxS2S_ItemStateChanged()V)
     * 
     * @param arg1
     *            java.awt.event.ItemEvent
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC5(java.awt.event.ItemEvent arg1) {
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
     * connEtoC6:
     * (CheckBoxTwoStage.item.itemStateChanged(java.awt.event.ItemEvent) -->
     * VECMEstStratPanel.checkBoxTwoStage_ItemStateChanged()V)
     * 
     * @param arg1
     *            java.awt.event.ItemEvent
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC6(java.awt.event.ItemEvent arg1) {
        try {
            // user code begin {1}
            // user code end
            this.checkBoxTwoStage_ItemStateChanged();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC7: (SecondStageCombo.action. -->
     * VECMEstStratPanel.secondStageCombo_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC7() {
        try {
            // user code begin {1}
            // user code end
            this.secondStageCombo_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC8: (StructFormButton.action. -->
     * VECMEstStratPanel.structFormButton_ActionEvents1()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC8() {
        try {
            // user code begin {1}
            // user code end
            this.structFormButton_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * Invokes the stage one settings dialog.
     */
    private void firstStageButton_ActionEvents() {
        if (global().get(VECMConstants.Ny_Def).getJSCSArray().rows() < 2) {
            StdMessages
                    .infoNothingSelected("Please select at least 2 endogenous variables.");
            return;
        }
        getStageOneDialog().setBetaResSet(
                global().get(VECMConstants.resH_Def).getJSCNArray().rows() > 0);

        getStageOneDialog().setVisible(true);
        VECMDataManagerObs.getInstance().removeData();
    }

    /**
     * Return the JCheckBox1 property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getCheckBoxJoh() {
        if (ivjCheckBoxJoh == null) {
            try {
                ivjCheckBoxJoh = new javax.swing.JCheckBox();
                ivjCheckBoxJoh.setName("CheckBoxJoh");
                ivjCheckBoxJoh.setSelected(true);
                ivjCheckBoxJoh.setAlignmentY(java.awt.Component.TOP_ALIGNMENT);
                ivjCheckBoxJoh
                        .setText("Johansen procedure (restrictions on beta are ignored)");
                ivjCheckBoxJoh
                        .setVerticalAlignment(javax.swing.SwingConstants.TOP);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjCheckBoxJoh;
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
                ivjCheckBoxS2S.setSelected(false);
                ivjCheckBoxS2S.setAlignmentY(java.awt.Component.TOP_ALIGNMENT);
                ivjCheckBoxS2S
                        .setText("S2S procedure (restrictions on beta are used if available)");
                ivjCheckBoxS2S
                        .setVerticalAlignment(javax.swing.SwingConstants.TOP);
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
     * Return the CheckBoxStructForm property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getCheckBoxStructForm() {
        if (ivjCheckBoxStructForm == null) {
            try {
                ivjCheckBoxStructForm = new javax.swing.JCheckBox();
                ivjCheckBoxStructForm.setName("CheckBoxStructForm");
                ivjCheckBoxStructForm
                        .setAlignmentY(java.awt.Component.TOP_ALIGNMENT);
                ivjCheckBoxStructForm.setText("Estimate structural form");
                ivjCheckBoxStructForm.setEnabled(false);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjCheckBoxStructForm;
    }

    /**
     * Return the JCheckBox2 property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getCheckBoxTwoStage() {
        if (ivjCheckBoxTwoStage == null) {
            try {
                ivjCheckBoxTwoStage = new javax.swing.JCheckBox();
                ivjCheckBoxTwoStage.setName("CheckBoxTwoStage");
                ivjCheckBoxTwoStage
                        .setAlignmentY(java.awt.Component.TOP_ALIGNMENT);
                ivjCheckBoxTwoStage.setText("Two stage procedure");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjCheckBoxTwoStage;
    }

    /**
     * Return the JButton1 property value.
     * 
     * @return javax.swing.JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JButton getFirstStageButton() {
        if (ivjFirstStageButton == null) {
            try {
                ivjFirstStageButton = new javax.swing.JButton();
                ivjFirstStageButton.setName("FirstStageButton");
                ivjFirstStageButton.setPreferredSize(new java.awt.Dimension(
                        140, 25));
                ivjFirstStageButton.setText("Specify");
                ivjFirstStageButton.setEnabled(false);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjFirstStageButton;
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
                ivjJLabel1.setPreferredSize(new java.awt.Dimension(59, 25));
                ivjJLabel1.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
                ivjJLabel1.setText("1st stage");
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
                ivjJLabel2.setPreferredSize(new java.awt.Dimension(59, 25));
                ivjJLabel2.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
                ivjJLabel2.setText("2nd stage");
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
     * Return the Label1 property value.
     * 
     * @return javax.swing.JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JLabel getLabel1() {
        if (ivjLabel1 == null) {
            try {
                ivjLabel1 = new javax.swing.JLabel();
                ivjLabel1.setName("Label1");
                ivjLabel1
                        .setText("Exogenous variables, subset constraints and structural coefficients are ignored");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjLabel1;
    }

    /**
     * Return the Label11 property value.
     * 
     * @return javax.swing.JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JLabel getLabel11() {
        if (ivjLabel11 == null) {
            try {
                ivjLabel11 = new javax.swing.JLabel();
                ivjLabel11.setName("Label11");
                ivjLabel11
                        .setText("Exogenous variables, subset constraints and structural coefficients can be estimated");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjLabel11;
    }

    /**
     * Return the RemoveResButton property value.
     * 
     * @return javax.swing.JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JButton getRemoveResButton() {
        if (ivjRemoveResButton == null) {
            try {
                ivjRemoveResButton = new javax.swing.JButton();
                ivjRemoveResButton.setName("RemoveResButton");
                ivjRemoveResButton.setPreferredSize(new java.awt.Dimension(150,
                        25));
                ivjRemoveResButton.setText("Delete Restrictions");
                ivjRemoveResButton.setEnabled(false);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjRemoveResButton;
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
                ivjResLabel.setPreferredSize(new java.awt.Dimension(153, 22));
                ivjResLabel.setAlignmentY(java.awt.Component.TOP_ALIGNMENT);
                ivjResLabel.setText("No restrictions on beta set.");
                ivjResLabel
                        .setVerticalAlignment(javax.swing.SwingConstants.TOP);
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
     * Return the JComboBox1 property value.
     * 
     * @return javax.swing.JComboBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JComboBox getSecondStageCombo() {
        if (ivjSecondStageCombo == null) {
            try {
                ivjSecondStageCombo = new javax.swing.JComboBox();
                ivjSecondStageCombo.setName("SecondStageCombo");
                ivjSecondStageCombo.setPreferredSize(new java.awt.Dimension(
                        140, 25));
                ivjSecondStageCombo.setEnabled(false);
                // user code begin {1}
                ivjSecondStageCombo.setModel(new DefaultComboBoxModel(
                        new String[] { "Automatic", "OLS", "GLS", "3SLS" }));
                ivjSecondStageCombo.setSelectedIndex(0);
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSecondStageCombo;
    }

    /**
     * Initializer for the dialog for stage one settings.
     * 
     * @return VECMStageOneSpecDialog
     */
    private VECMStageOneSpecDialog getStageOneDialog() {
        if (stageOneDialog == null)
            stageOneDialog = new VECMStageOneSpecDialog(TopFrameReference
                    .getTopFrameRef(), true);

        stageOneDialog
                .setLocationRelativeTo(TopFrameReference.getTopFrameRef());
        return stageOneDialog;
    }

    /**
     * Initializer for the dialog for structural coefficients settings.
     * 
     * @return VECMStructCoeffDialog
     */
    private VECMStructCoeffDialog getStructCoeffDialog() {
        if (structCoeffDialog == null)
            structCoeffDialog = new VECMStructCoeffDialog(TopFrameReference
                    .getTopFrameRef(), true);

        structCoeffDialog.setLocationRelativeTo(TopFrameReference
                .getTopFrameRef());
        return structCoeffDialog;
    }

    /**
     * Return the StructFormButton property value.
     * 
     * @return javax.swing.JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JButton getStructFormButton() {
        if (ivjStructFormButton == null) {
            try {
                ivjStructFormButton = new javax.swing.JButton();
                ivjStructFormButton.setName("StructFormButton");
                ivjStructFormButton.setPreferredSize(new java.awt.Dimension(
                        150, 25));
                ivjStructFormButton.setText("Structural Form");
                ivjStructFormButton.setEnabled(false);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjStructFormButton;
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
        getFirstStageButton().addActionListener(ivjEventHandler);
        getCheckBoxJoh().addItemListener(ivjEventHandler);
        getCheckBoxS2S().addItemListener(ivjEventHandler);
        getCheckBoxTwoStage().addItemListener(ivjEventHandler);
        getSecondStageCombo().addActionListener(ivjEventHandler);
        getCheckBoxStructForm().addItemListener(ivjEventHandler);
        getStructFormButton().addActionListener(ivjEventHandler);
        getRemoveResButton().addActionListener(ivjEventHandler);
    }

    /**
     * Initialize the class.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initialize() {
        try {
            // user code begin {1}
            // user code end
            setName("VECMEstStratPanel");
            setLayout(new java.awt.GridBagLayout());
            setSize(625, 344);

            java.awt.GridBagConstraints constraintsCheckBoxJoh = new java.awt.GridBagConstraints();
            constraintsCheckBoxJoh.gridx = 0;
            constraintsCheckBoxJoh.gridy = 1;
            constraintsCheckBoxJoh.gridwidth = 4;
            constraintsCheckBoxJoh.fill = java.awt.GridBagConstraints.HORIZONTAL;
            constraintsCheckBoxJoh.insets = new java.awt.Insets(10, 20, 0, 0);
            add(getCheckBoxJoh(), constraintsCheckBoxJoh);

            java.awt.GridBagConstraints constraintsCheckBoxTwoStage = new java.awt.GridBagConstraints();
            constraintsCheckBoxTwoStage.gridx = 0;
            constraintsCheckBoxTwoStage.gridy = 4;
            constraintsCheckBoxTwoStage.fill = java.awt.GridBagConstraints.HORIZONTAL;
            constraintsCheckBoxTwoStage.insets = new java.awt.Insets(10, 20, 0,
                    0);
            add(getCheckBoxTwoStage(), constraintsCheckBoxTwoStage);

            java.awt.GridBagConstraints constraintsJLabel1 = new java.awt.GridBagConstraints();
            constraintsJLabel1.gridx = 1;
            constraintsJLabel1.gridy = 4;
            constraintsJLabel1.gridwidth = 2;
            constraintsJLabel1.fill = java.awt.GridBagConstraints.HORIZONTAL;
            constraintsJLabel1.insets = new java.awt.Insets(10, 5, 0, 0);
            add(getJLabel1(), constraintsJLabel1);

            java.awt.GridBagConstraints constraintsFirstStageButton = new java.awt.GridBagConstraints();
            constraintsFirstStageButton.gridx = 3;
            constraintsFirstStageButton.gridy = 4;
            constraintsFirstStageButton.anchor = java.awt.GridBagConstraints.WEST;
            constraintsFirstStageButton.insets = new java.awt.Insets(10, 5, 0,
                    0);
            add(getFirstStageButton(), constraintsFirstStageButton);

            java.awt.GridBagConstraints constraintsJLabel2 = new java.awt.GridBagConstraints();
            constraintsJLabel2.gridx = 1;
            constraintsJLabel2.gridy = 5;
            constraintsJLabel2.fill = java.awt.GridBagConstraints.HORIZONTAL;
            constraintsJLabel2.anchor = java.awt.GridBagConstraints.NORTHWEST;
            constraintsJLabel2.insets = new java.awt.Insets(5, 5, 0, 0);
            add(getJLabel2(), constraintsJLabel2);

            java.awt.GridBagConstraints constraintsSecondStageCombo = new java.awt.GridBagConstraints();
            constraintsSecondStageCombo.gridx = 3;
            constraintsSecondStageCombo.gridy = 5;
            constraintsSecondStageCombo.anchor = java.awt.GridBagConstraints.NORTHWEST;
            constraintsSecondStageCombo.weightx = 1.0;
            constraintsSecondStageCombo.insets = new java.awt.Insets(5, 5, 0, 0);
            add(getSecondStageCombo(), constraintsSecondStageCombo);

            java.awt.GridBagConstraints constraintsLabel1 = new java.awt.GridBagConstraints();
            constraintsLabel1.gridx = 0;
            constraintsLabel1.gridy = 0;
            constraintsLabel1.gridwidth = 6;
            constraintsLabel1.fill = java.awt.GridBagConstraints.HORIZONTAL;
            constraintsLabel1.insets = new java.awt.Insets(0, 5, 0, 0);
            add(getLabel1(), constraintsLabel1);

            java.awt.GridBagConstraints constraintsCheckBoxS2S = new java.awt.GridBagConstraints();
            constraintsCheckBoxS2S.gridx = 0;
            constraintsCheckBoxS2S.gridy = 2;
            constraintsCheckBoxS2S.gridwidth = 4;
            constraintsCheckBoxS2S.fill = java.awt.GridBagConstraints.HORIZONTAL;
            constraintsCheckBoxS2S.insets = new java.awt.Insets(5, 20, 0, 0);
            add(getCheckBoxS2S(), constraintsCheckBoxS2S);

            java.awt.GridBagConstraints constraintsLabel11 = new java.awt.GridBagConstraints();
            constraintsLabel11.gridx = 0;
            constraintsLabel11.gridy = 3;
            constraintsLabel11.gridwidth = 6;
            constraintsLabel11.fill = java.awt.GridBagConstraints.HORIZONTAL;
            constraintsLabel11.insets = new java.awt.Insets(10, 5, 0, 0);
            add(getLabel11(), constraintsLabel11);

            java.awt.GridBagConstraints constraintsCheckBoxStructForm = new java.awt.GridBagConstraints();
            constraintsCheckBoxStructForm.gridx = 4;
            constraintsCheckBoxStructForm.gridy = 4;
            constraintsCheckBoxStructForm.gridwidth = 2;
            constraintsCheckBoxStructForm.fill = java.awt.GridBagConstraints.HORIZONTAL;
            constraintsCheckBoxStructForm.anchor = java.awt.GridBagConstraints.NORTHWEST;
            constraintsCheckBoxStructForm.insets = new java.awt.Insets(10, 10,
                    0, 0);
            add(getCheckBoxStructForm(), constraintsCheckBoxStructForm);

            java.awt.GridBagConstraints constraintsStructFormButton = new java.awt.GridBagConstraints();
            constraintsStructFormButton.gridx = 5;
            constraintsStructFormButton.gridy = 5;
            constraintsStructFormButton.anchor = java.awt.GridBagConstraints.NORTHWEST;
            constraintsStructFormButton.weightx = 1.0;
            constraintsStructFormButton.weighty = 1.0;
            constraintsStructFormButton.insets = new java.awt.Insets(5, 10, 0,
                    0);
            add(getStructFormButton(), constraintsStructFormButton);

            java.awt.GridBagConstraints constraintsResLabel = new java.awt.GridBagConstraints();
            constraintsResLabel.gridx = 5;
            constraintsResLabel.gridy = 2;
            constraintsResLabel.gridwidth = 4;
            constraintsResLabel.fill = java.awt.GridBagConstraints.HORIZONTAL;
            constraintsResLabel.insets = new java.awt.Insets(5, 5, 0, 0);
            add(getResLabel(), constraintsResLabel);

            java.awt.GridBagConstraints constraintsRemoveResButton = new java.awt.GridBagConstraints();
            constraintsRemoveResButton.gridx = 5;
            constraintsRemoveResButton.gridy = 1;
            constraintsRemoveResButton.anchor = java.awt.GridBagConstraints.WEST;
            constraintsRemoveResButton.insets = new java.awt.Insets(5, 5, 0, 0);
            add(getRemoveResButton(), constraintsRemoveResButton);
            initConnections();
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}
        getResLabel().setForeground(java.awt.Color.red);

        setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED),
                "Select Estimation Strategy", TitledBorder.RIGHT,
                TitledBorder.TOP));

        ButtonGroup bg = new ButtonGroup();
        bg.add(getCheckBoxJoh());
        bg.add(getCheckBoxS2S());
        bg.add(getCheckBoxTwoStage());

        // Updates according to relevant changes in the selection of the model.
        SymbolListener selListener = new SymbolListener() {

            public void valueChanged(SymbolEvent e) {
                // listener disabled from project load in VECM class
                if (projectLoadOnGoing)
                    return;
                selectionChanged();
            }
        };
        global().get(VECMConstants.cointRank_Def)
                .addSymbolListener(selListener);
        global().get(VECMConstants.Ny_Def).addSymbolListener(selListener);
        global().get(VECMConstants.Nd_ec_Def).addSymbolListener(selListener);
        global().get(VECMConstants.Nd_Def).addSymbolListener(selListener);

        // Updates according to relevant changes in the selection of the model.
        SymbolListener resListener = new SymbolListener() {

            public void valueChanged(SymbolEvent e) {
                if (!e.isSourceEmpty()) {
                    getResLabel().setText("Restrictions on beta set.");
                    getRemoveResButton().setEnabled(true);
                    getResLabel().setForeground(java.awt.Color.blue);
                } else {
                    getResLabel().setText("No restrictions on beta set.");
                    getRemoveResButton().setEnabled(false);
                    getResLabel().setForeground(java.awt.Color.red);
                }
            }
        };
        global().get(VECMConstants.resH_Def).addSymbolListener(resListener);

    }

    /**
     * Comment
     */
    private void removeResButton_ActionEvents() {
        global().get(VECMConstants.resH_Def).clear();
        VECMDataManagerObs.getInstance().removeData();
    }

    /**
     * Comment
     */
    private void secondStageCombo_ActionEvents() {
        VECMDataManagerObs.getInstance().removeData();

    }

    /**
     * Updates the settings used for estimation. Should be called if coint rank,
     * variable selection, deterministics selection or deterministics restricted
     * to EC term have changed.
     * 
     * @param selChanged
     *            is not referenced at all
     */
    private void selectionChanged() {

        JSCSArray ny = global().get(VECMConstants.Ny_Def).getJSCSArray();
        if (ny.rows() > 0) {
            int r = global().get(VECMConstants.cointRank_Def).getJSCInt()
                    .intVal();
            JSCInt cr_joh = global().get(VECMConstants.firstStage_crJoh_Def)
                    .getJSCInt();
            int cr_joh_int = r;
            if (!cr_joh.isEmpty())
                cr_joh_int = (cr_joh.intVal() < r) ? cr_joh.intVal() : r;

            getStageOneDialog().setDisplay(ny,
                    global().get(VECMConstants.Nd_ec_Def).getJSCSArray(),
                    global().get(VECMConstants.beta_x_Def).getJSCNArray(),
                    global().get(VECMConstants.beta_d_x_Def).getJSCNArray(),
                    cr_joh_int, r);
        }

        getStructCoeffDialog().setDisplay(
                global().get(VECMConstants.Ny_Def).getJSCSArray());

    }

    /**
     * Sets the data objects for the estimation strategy and the first stage as
     * well as for the structural coefficients.
     * 
     */
    public void setEstStrategyData() {

        JSCData[] data = getStageOneDialog().getSelectionStatus();

        global().get(VECMConstants.firstStage_bySingleEquation_Def).setJSCData(
                data[0]);
        global().get(VECMConstants.firstStage_byJoh_Def).setJSCData(data[1]);
        global().get(VECMConstants.firstStage_byS2S_Def).setJSCData(data[2]);
        global().get(VECMConstants.beta_d_x_Def).setJSCData(data[3]);
        global().get(VECMConstants.beta_x_Def).setJSCData(data[4]);
        global().get(VECMConstants.firstStage_equaIdx_Def).setJSCData(data[5]);
        global().get(VECMConstants.firstStage_crJoh_Def).setJSCData(data[6]);
        if (getCheckBoxStructForm().isSelected())
            global().get(VECMConstants.S_G0_Def).setJSCData(
                    getStructCoeffDialog().getStructRes());
        else
            global().get(VECMConstants.S_G0_Def).clear();

        global().get(VECMConstants.secondStage_method_Def).setJSCData(
                new JSCString("toset", getSecondStageCombo().getSelectedItem()
                        .toString()));

        // Set the estimation strategy.
        if (getCheckBoxJoh().isSelected()) {
            global().get(VECMConstants.estimationStrategy_Def).setJSCData(
                    new JSCInt("JOH", VECMConstants.JOHANSEN));
        } else if (getCheckBoxTwoStage().isSelected()) {
            global().get(VECMConstants.estimationStrategy_Def).setJSCData(
                    new JSCInt("TWO_STAGE", VECMConstants.TWO_STAGE));
        } else if (getCheckBoxS2S().isSelected())
            global().get(VECMConstants.estimationStrategy_Def).setJSCData(
                    new JSCInt("S2S", VECMConstants.S2S));
    }

    /**
     * Comment
     */
    private void structFormButton_ActionEvents() {
        if (global().get(VECMConstants.Ny_Def).getJSCSArray().rows() < 2) {
            StdMessages
                    .infoNothingSelected("Please select at least 2 endogenous variables.");
            return;
        }
        getStructCoeffDialog().setVisible(true);
        VECMDataManagerObs.getInstance().removeData();
    }

    /**
     * Sets the selection of the estimation strategy.
     * 
     * @param stratSel
     *            array indicating strategy selection
     */
    void setStratSelection(JSCNArray stratSel) {
        if (stratSel == null || stratSel.isEmpty() || stratSel.rows() != 5)
            throw new IllegalArgumentException("Invalid array argument.");
        getCheckBoxJoh().setSelected(stratSel.intAt(0, 0) != 0);
        getCheckBoxS2S().setSelected(stratSel.intAt(1, 0) != 0);
        getCheckBoxTwoStage().setSelected(stratSel.intAt(2, 0) != 0);
        getSecondStageCombo().setSelectedIndex(stratSel.intAt(3, 0));
        getCheckBoxStructForm().setSelected(stratSel.intAt(4, 0) != 0);
        selectionChanged();
    }

    /**
     * Gets the current selection of the estimation strategy.
     * 
     * @return array indicating strategy selection
     */
    JSCNArray getStratSelection() {
        int[] selection = new int[5];
        selection[0] = getCheckBoxJoh().isSelected() ? 1 : 0;
        selection[1] = getCheckBoxS2S().isSelected() ? 1 : 0;
        selection[2] = getCheckBoxTwoStage().isSelected() ? 1 : 0;
        selection[3] = getSecondStageCombo().getSelectedIndex();
        selection[4] = getCheckBoxStructForm().isSelected() ? 1 : 0;
        return new JSCNArray("stratSelection", selection);
    }

    /**
     * @param projectLoadOnGoing
     */
    public void setProjectLoadOnGoing(boolean projectLoadOnGoing) {
        this.projectLoadOnGoing = projectLoadOnGoing;
    }
}