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

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.BevelBorder;

import org.apache.log4j.Logger;

import com.jstatcom.component.NumSelector;
import com.jstatcom.component.NumberFormatTypes;

/**
 * Dialog allowing the user to specify optimization settings for SVAR
 * optimization.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public final class SVAROptSetDialog extends JDialog {
    private static final Logger log = Logger.getLogger(SVAROptSetDialog.class);

    private ModelTypes modelType = ModelTypes.SVAR;

    // Constants for startvalue method.
    public static final int DRAW_RANDOM = 1;

    public static final int DRAW_FIXED = 2;

    // Default values.
    private static final int ITER_NUM_DEFAULT = 500;

    private static final double REL_PARAM_DEFAULT = 1E-6;

    private static final double REL_LIK_DEFAULT = 1E-10;

    private static final double FIXED_DEFAULT = 0.1;

    private static boolean USE_DECOMP_DEFAULT = true;

    private static final int STARTVALUE_METHOD_DEFAULT = DRAW_RANDOM;

    private static final int MAX_RETRIES_DEFAULT = 10;

    private JButton ivjDefaultsButton = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private JRadioButton ivjFixedRadioButton = null;

    private JPanel ivjJDialogContentPane = null;

    private JLabel ivjJLabel1 = null;

    private JLabel ivjJLabel2 = null;

    private JLabel ivjJLabel3 = null;

    private JLabel ivjJLabel4 = null;

    private JLabel ivjJLabel5 = null;

    private JPanel ivjJPanel1 = null;

    private JPanel ivjJPanel2 = null;

    private JButton ivjOKButton = null;

    private JRadioButton ivjRandomRadioButton = null;

    private NumSelector ivjIterNumTextField = null;

    private NumSelector ivjRelParamTextField = null;

    private NumSelector ivjRelLikTextField = null;

    private NumSelector ivjFixedTextField = null;

    private JCheckBox ivjDecompCheck = null;

    private JComboBox ivjMaxRetriesCombo = null;

    class IvjEventHandler implements java.awt.event.ActionListener,
            java.awt.event.ItemListener {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == SVAROptSetDialog.this.getOKButton())
                connEtoC1();
            if (e.getSource() == SVAROptSetDialog.this.getDefaultsButton())
                connEtoC3();
        };

        public void itemStateChanged(java.awt.event.ItemEvent e) {
            if (e.getSource() == SVAROptSetDialog.this.getRandomRadioButton())
                connPtoP2SetTarget();
            if (e.getSource() == SVAROptSetDialog.this.getFixedRadioButton())
                connPtoP1SetTarget();
        };
    };

    /**
     * SVAROptSetDialog constructor comment.
     */
    public SVAROptSetDialog() {
        super();
        initialize();
    }

    /**
     * SVAROptSetDialog constructor comment.
     * 
     * @param owner
     *            java.awt.Frame
     * @param modal
     *            boolean
     */
    public SVAROptSetDialog(java.awt.Frame owner, boolean modal) {
        super(owner, modal);
        initialize();
    }

    /**
     * connEtoC1: (OKButton.action. -->
     * SVAROptSetDialog.oKButton_ActionEvents()V)
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
     * connEtoC3: (DefaultsButton.action. -->
     * SVAROptSetDialog.defaultsButton_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC3() {
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
     * connPtoP1SetTarget: (FixedRadioButton.selected <-->
     * FixedTextField.enabled)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connPtoP1SetTarget() {
        /* Set the target from the source */
        try {
            getFixedTextField().setEnabled(getFixedRadioButton().isSelected());
            // user code begin {1}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connPtoP2SetTarget: (RandomRadioButton.selected <-->
     * DrawRandomCombo.enabled)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connPtoP2SetTarget() {
        /* Set the target from the source */
        try {
            getMaxRetriesCombo()
                    .setEnabled(getRandomRadioButton().isSelected());
            // user code begin {1}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * Sets back all settings to its respective default values.
     */
    private void defaultsButton_ActionEvents() {
        int sel = JOptionPane.showConfirmDialog(this,
                "Set back optimization settings to default values?",
                "Default Settings?", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (sel != JOptionPane.YES_OPTION)
            return;

        setDefaults();
    }

    /**
     * Return the ResDecompCheck property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getDecompCheck() {
        if (ivjDecompCheck == null) {
            try {
                ivjDecompCheck = new javax.swing.JCheckBox();
                ivjDecompCheck.setName("DecompCheck");
                ivjDecompCheck.setSelected(true);
                ivjDecompCheck
                        .setPreferredSize(new java.awt.Dimension(150, 20));
                ivjDecompCheck
                        .setText("Use decomposition of residual correlation matrix");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjDecompCheck;
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

    /**
     * Return the FixedRadioButton property value.
     * 
     * @return javax.swing.JRadioButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JRadioButton getFixedRadioButton() {
        if (ivjFixedRadioButton == null) {
            try {
                ivjFixedRadioButton = new javax.swing.JRadioButton();
                ivjFixedRadioButton.setName("FixedRadioButton");
                ivjFixedRadioButton.setPreferredSize(new java.awt.Dimension(
                        150, 20));
                ivjFixedRadioButton.setText("Fixed start values");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjFixedRadioButton;
    }

    private NumSelector getFixedTextField() {
        if (ivjFixedTextField == null) {
            try {
                ivjFixedTextField = new NumSelector();
                ivjFixedTextField.setName("FixedTextField");
                ivjFixedTextField.setNumber(0.1);
                ivjFixedTextField.setPrecision(6);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjFixedTextField;
    }

    /**
     * Returns value for fixed start value setting.
     * 
     * @return double
     */
    public double getFixedValue() {
        return getFixedTextField().getNumber();
    }

    private NumSelector getIterNumTextField() {
        if (ivjIterNumTextField == null) {
            try {
                ivjIterNumTextField = new NumSelector();
                ivjIterNumTextField.setName("IterNumTextField");
                ivjIterNumTextField.setNumber(500.0);
                ivjIterNumTextField.setRangeExpr("[1,+Infinity)");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjIterNumTextField;
    }

    /**
     * Return the JDialogContentPane property value.
     * 
     * @return javax.swing.JPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JPanel getJDialogContentPane() {
        if (ivjJDialogContentPane == null) {
            try {
                ivjJDialogContentPane = new javax.swing.JPanel();
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
                ivjJLabel1.setPreferredSize(new java.awt.Dimension(150, 20));
                ivjJLabel1.setText("Max num of iterations");
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
                ivjJLabel2.setPreferredSize(new java.awt.Dimension(150, 20));
                ivjJLabel2.setText("Convergence tolerance:");
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
                ivjJLabel3.setPreferredSize(new java.awt.Dimension(150, 20));
                ivjJLabel3.setText("Relative change in parameters");
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
                ivjJLabel4.setPreferredSize(new java.awt.Dimension(150, 20));
                ivjJLabel4.setText("Relative change in log-likelihood");
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
                ivjJLabel5.setPreferredSize(new java.awt.Dimension(150, 20));
                ivjJLabel5.setText("Start value settings:");
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
                ivjJPanel2.setPreferredSize(new java.awt.Dimension(0, 300));
                ivjJPanel2.setBorder(new BevelBorder(BevelBorder.LOWERED));
                ivjJPanel2.setLayout(new java.awt.GridBagLayout());

                java.awt.GridBagConstraints constraintsJLabel1 = new java.awt.GridBagConstraints();
                constraintsJLabel1.gridx = 0;
                constraintsJLabel1.gridy = 0;
                constraintsJLabel1.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel1.insets = new java.awt.Insets(10, 20, 0, 0);
                getJPanel2().add(getJLabel1(), constraintsJLabel1);

                java.awt.GridBagConstraints constraintsJLabel2 = new java.awt.GridBagConstraints();
                constraintsJLabel2.gridx = 0;
                constraintsJLabel2.gridy = 1;
                constraintsJLabel2.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel2.insets = new java.awt.Insets(15, 20, 0, 0);
                getJPanel2().add(getJLabel2(), constraintsJLabel2);

                java.awt.GridBagConstraints constraintsJLabel3 = new java.awt.GridBagConstraints();
                constraintsJLabel3.gridx = 0;
                constraintsJLabel3.gridy = 2;
                constraintsJLabel3.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel3.insets = new java.awt.Insets(10, 30, 0, 0);
                getJPanel2().add(getJLabel3(), constraintsJLabel3);

                java.awt.GridBagConstraints constraintsJLabel4 = new java.awt.GridBagConstraints();
                constraintsJLabel4.gridx = 0;
                constraintsJLabel4.gridy = 3;
                constraintsJLabel4.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel4.insets = new java.awt.Insets(5, 30, 0, 0);
                getJPanel2().add(getJLabel4(), constraintsJLabel4);

                java.awt.GridBagConstraints constraintsJLabel5 = new java.awt.GridBagConstraints();
                constraintsJLabel5.gridx = 0;
                constraintsJLabel5.gridy = 4;
                constraintsJLabel5.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel5.insets = new java.awt.Insets(15, 20, 0, 0);
                getJPanel2().add(getJLabel5(), constraintsJLabel5);

                java.awt.GridBagConstraints constraintsDecompCheck = new java.awt.GridBagConstraints();
                constraintsDecompCheck.gridx = 0;
                constraintsDecompCheck.gridy = 5;
                constraintsDecompCheck.gridwidth = 2;
                constraintsDecompCheck.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsDecompCheck.insets = new java.awt.Insets(5, 30, 0, 0);
                getJPanel2().add(getDecompCheck(), constraintsDecompCheck);

                java.awt.GridBagConstraints constraintsRandomRadioButton = new java.awt.GridBagConstraints();
                constraintsRandomRadioButton.gridx = 0;
                constraintsRandomRadioButton.gridy = 7;
                constraintsRandomRadioButton.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsRandomRadioButton.insets = new java.awt.Insets(5,
                        30, 0, 0);
                java.awt.GridBagConstraints constraintsFixedRadioButton = new java.awt.GridBagConstraints();
                constraintsFixedRadioButton.gridx = 0;
                constraintsFixedRadioButton.gridy = 6;
                constraintsFixedRadioButton.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsFixedRadioButton.insets = new java.awt.Insets(10,
                        30, 0, 0);
                getJPanel2().add(getFixedRadioButton(),
                        constraintsFixedRadioButton);

                java.awt.GridBagConstraints constraintsIterNumTextField = new java.awt.GridBagConstraints();
                constraintsIterNumTextField.gridx = 1;
                constraintsIterNumTextField.gridy = 0;
                constraintsIterNumTextField.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsIterNumTextField.insets = new java.awt.Insets(10, 0,
                        0, 20);
                getJPanel2().add(getIterNumTextField(),
                        constraintsIterNumTextField);

                java.awt.GridBagConstraints constraintsFixedTextField = new java.awt.GridBagConstraints();
                constraintsFixedTextField.gridx = 1;
                constraintsFixedTextField.gridy = 6;
                constraintsFixedTextField.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsFixedTextField.insets = new java.awt.Insets(10, 0,
                        0, 20);
                getJPanel2()
                        .add(getFixedTextField(), constraintsFixedTextField);

                java.awt.GridBagConstraints constraintsMaxRetriesCombo = new java.awt.GridBagConstraints();
                constraintsMaxRetriesCombo.gridx = 1;
                constraintsMaxRetriesCombo.gridy = 7;
                constraintsMaxRetriesCombo.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsMaxRetriesCombo.insets = new java.awt.Insets(5, 0,
                        0, 20);
                java.awt.GridBagConstraints constraintsRelParamTextField = new java.awt.GridBagConstraints();
                constraintsRelParamTextField.gridx = 1;
                constraintsRelParamTextField.gridy = 2;
                constraintsRelParamTextField.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsRelParamTextField.insets = new java.awt.Insets(0, 0,
                        0, 20);
                getJPanel2().add(getRelParamTextField(),
                        constraintsRelParamTextField);

                java.awt.GridBagConstraints constraintsRelLikTextField = new java.awt.GridBagConstraints();
                constraintsRandomRadioButton.anchor = java.awt.GridBagConstraints.NORTH;
                constraintsMaxRetriesCombo.weightx = 1.0D;
                constraintsMaxRetriesCombo.weighty = 1.0D;
                constraintsMaxRetriesCombo.anchor = java.awt.GridBagConstraints.NORTH;
                constraintsRelLikTextField.gridx = 1;
                constraintsRelLikTextField.gridy = 3;
                constraintsRelLikTextField.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsRelLikTextField.insets = new java.awt.Insets(0, 0,
                        0, 20);
                ivjJPanel2.add(getRandomRadioButton(),
                        constraintsRandomRadioButton);
                ivjJPanel2
                        .add(getMaxRetriesCombo(), constraintsMaxRetriesCombo);
                getJPanel2().add(getRelLikTextField(),
                        constraintsRelLikTextField);
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
     * Returns value for max number of iterations.
     * 
     * @return int
     */
    public int getMaxIterNum() {
        return getIterNumTextField().getIntNumber();
    }

    /**
     * Returns selected maximum retries for random draw..
     * 
     * @return int
     */
    public int getMaxRetries() {
        double sel = Double.parseDouble(getMaxRetriesCombo().getSelectedItem()
                .toString());
        return new Double(sel).intValue();
    }

    /**
     * Return the DrawRandomCombo property value.
     * 
     * @return javax.swing.JComboBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JComboBox getMaxRetriesCombo() {
        if (ivjMaxRetriesCombo == null) {
            try {
                ivjMaxRetriesCombo = new javax.swing.JComboBox();
                ivjMaxRetriesCombo.setName("MaxRetriesCombo");
                ivjMaxRetriesCombo.setPreferredSize(new java.awt.Dimension(150,
                        20));
                // user code begin {1}
                ivjMaxRetriesCombo.addItem("5");
                ivjMaxRetriesCombo.addItem("10");
                ivjMaxRetriesCombo.addItem("20");
                ivjMaxRetriesCombo.addItem("50");
                ivjMaxRetriesCombo.setSelectedIndex(1);
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjMaxRetriesCombo;
    }

    /**
     * Get the SVAR model type for the optimization settings, which affects the
     * defaults.
     * 
     * @return the current modeltype
     */
    public ModelTypes getModelType() {
        return modelType;
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
     * Return the RandomRadioButton property value.
     * 
     * @return javax.swing.JRadioButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JRadioButton getRandomRadioButton() {
        if (ivjRandomRadioButton == null) {
            try {
                ivjRandomRadioButton = new javax.swing.JRadioButton();
                ivjRandomRadioButton.setName("RandomRadioButton");
                ivjRandomRadioButton.setSelected(true);
                ivjRandomRadioButton.setPreferredSize(new java.awt.Dimension(
                        150, 20));
                ivjRandomRadioButton.setText("Draw randomly");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjRandomRadioButton;
    }

    /**
     * Returns value for relative change in log-likelihood.
     * 
     * @return double
     */
    public double getRelLikChange() {
        return getRelLikTextField().getNumber();
    }

    private NumSelector getRelLikTextField() {
        if (ivjRelLikTextField == null) {
            try {
                ivjRelLikTextField = new NumSelector();
                ivjRelLikTextField.setName("RelLikTextField");
                ivjRelLikTextField.setNumber(1.0E-10);
                ivjRelLikTextField.setPrecision(1);
                ivjRelLikTextField
                        .setNumberFormatType(NumberFormatTypes.SCIENTIFIC);
                ivjRelLikTextField.setRangeExpr("(0, 1)");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjRelLikTextField;
    }

    /**
     * Returns value for relative change in parameters.
     * 
     * @return double
     */
    public double getRelParamChange() {
        return getRelParamTextField().getNumber();
    }

    private NumSelector getRelParamTextField() {
        if (ivjRelParamTextField == null) {
            try {
                ivjRelParamTextField = new NumSelector();
                ivjRelParamTextField.setName("RelParamTextField");
                ivjRelParamTextField.setNumber(1.0E-6);
                ivjRelParamTextField.setPrecision(1);
                ivjRelParamTextField
                        .setNumberFormatType(NumberFormatTypes.SCIENTIFIC);
                ivjRelParamTextField.setRangeExpr("(0, 1)");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjRelParamTextField;
    }

    /**
     * Returns selected start value method.
     * 
     * @return int
     */
    public int getStartMethod() {
        if (getRandomRadioButton().isSelected())
            return 1;
        if (getFixedRadioButton().isSelected())
            return 2;

        return 1;
    }

    /**
     * Returns whether decomposition of residual corr. matrix should be used.
     * 
     * @return boolean
     */
    public boolean getUseDecomp() {
        return getDecompCheck().isSelected();
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
        getOKButton().addActionListener(ivjEventHandler);
        getDefaultsButton().addActionListener(ivjEventHandler);
        getRandomRadioButton().addItemListener(ivjEventHandler);
        getFixedRadioButton().addItemListener(ivjEventHandler);
        connPtoP2SetTarget();
        connPtoP1SetTarget();
    }

    /**
     * Initialize the class.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initialize() {
        try {
            // user code begin {1}
            // user code end
            setName("SVAROptSetDialog");
            setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
            setTitle("Optimization Settings for SVAR Estimation");
            setSize(600, 351);
            setResizable(false);
            setContentPane(getJDialogContentPane());
            initConnections();
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}
        ButtonGroup bb = new ButtonGroup();
        bb.add(getRandomRadioButton());
        bb.add(getFixedRadioButton());
        // user code end
    }

    /**
     * Sets fields and hides dialog.
     */
    private void oKButton_ActionEvents() {
        setVisible(false);

    }

    /**
     * Sets back all settings to its respective default values.
     */
    private void setDefaults() {

        // Set defaults.
        getIterNumTextField().setNumber(ITER_NUM_DEFAULT);
        getRelLikTextField().setNumber(REL_LIK_DEFAULT);
        getRelParamTextField().setNumber(REL_PARAM_DEFAULT);
        getFixedTextField().setNumber(FIXED_DEFAULT);
        getDecompCheck().setSelected(USE_DECOMP_DEFAULT);

        if (STARTVALUE_METHOD_DEFAULT == DRAW_RANDOM)
            getRandomRadioButton().setSelected(true);
        else if (STARTVALUE_METHOD_DEFAULT == DRAW_FIXED)
            getFixedRadioButton().setSelected(true);

        getMaxRetriesCombo().setSelectedItem(MAX_RETRIES_DEFAULT + "");
        return;
    }

    /**
     * Sets the SVAR model type for the optimization settings, which affects the
     * defaults.
     * 
     * @param newModelType
     *            the new modeltype
     * @throws IllegalArgumentException
     *             <code>if (modelType != ModelTypes.SVAR && modelType != ModelTypes.SVEC)</code>
     */
    public void setModelType(ModelTypes newModelType) {

        if (modelType != ModelTypes.SVAR && modelType != ModelTypes.SVEC)
            throw new IllegalArgumentException(newModelType + " not valid.");
        modelType = newModelType;

        if (modelType == ModelTypes.SVEC)
            USE_DECOMP_DEFAULT = false;
        else
            USE_DECOMP_DEFAULT = true;

        setDefaults();
    }

} // @jve:visual-info decl-index=0 visual-constraint="10,113"
