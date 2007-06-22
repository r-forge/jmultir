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
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import org.apache.log4j.Logger;

import com.jstatcom.component.NumSelector;

/**
 * This panel provides a user input mask for specifying the subset search
 * routine. The settings can be accessed via
 * <code>getThreshold, getCriterion and getStrategy</code>. <br>
 * Criterion: 1 - AIC; 2 - HQ; 3 - SC <br>
 * Strategy: 1 - System SER; 2 - SER; 3 - Top-down; 4 - STP
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public final class SubsetSpecPanel extends JPanel {
    private static final Logger log = Logger.getLogger(SubsetSpecPanel.class);

    private JCheckBox ivjAIC = null;

    private JCheckBox ivjHQ = null;

    private JLabel ivjJLabel2 = null;

    private JLabel ivjJLabel3 = null;

    private JLabel ivjJLabel4 = null;

    private JLabel ivjJLabel5 = null;

    private JLabel ivjJLabel6 = null;

    private JLabel ivjJLabel7 = null;

    private JLabel ivjJLabel8 = null;

    private JLabel ivjJLabel9 = null;

    private JCheckBox ivjSC = null;

    private JRadioButton ivjSER = null;

    private JRadioButton ivjSTP = null;

    private JRadioButton ivjSystemSER = null;

    private JRadioButton ivjTopDown = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private NumSelector ivjThresholdField = null;

    class IvjEventHandler implements java.awt.event.ItemListener {
        public void itemStateChanged(java.awt.event.ItemEvent e) {
            if (e.getSource() == SubsetSpecPanel.this.getSTP())
                connPtoP1SetTarget();
        };
    };

    /**
     * SubsetSpecPanel constructor comment.
     */
    public SubsetSpecPanel() {
        super();
        initialize();
    }

    /**
     * connPtoP1SetTarget: (STP.selected <--> Threshold.enabled)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connPtoP1SetTarget() {
        /* Set the target from the source */
        try {
            getThresholdField().setEnabled(getSTP().isSelected());
            // user code begin {1}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * Return the AIC property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getAIC() {
        if (ivjAIC == null) {
            try {
                ivjAIC = new javax.swing.JCheckBox();
                ivjAIC.setName("AIC");
                ivjAIC.setSelected(true);
                ivjAIC.setText("AIC");
                ivjAIC.setBounds(202, 7, 60, 20);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjAIC;
    }

    /**
     * Returns the selected criterion.
     * 
     * @return coded criterion: 1 - AIC; 2 - HQ; 3 - SC
     */
    public int getCriterion() {
        if (getAIC().isSelected())
            return 1;
        if (getHQ().isSelected())
            return 2;
        if (getSC().isSelected())
            return 3;

        return 1;

    }

    /**
     * Return the HQ property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getHQ() {
        if (ivjHQ == null) {
            try {
                ivjHQ = new javax.swing.JCheckBox();
                ivjHQ.setName("HQ");
                ivjHQ.setText("HQ");
                ivjHQ.setBounds(202, 32, 60, 20);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjHQ;
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
                ivjJLabel2.setText("Click on coefficients:");
                ivjJLabel2.setBounds(322, 7, 200, 20);
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
                ivjJLabel3.setText(":  Excluded from model");
                ivjJLabel3.setBounds(332, 32, 200, 20);
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
                ivjJLabel4.setText("Threshold");
                ivjJLabel4.setBounds(242, 82, 64, 20);
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
                ivjJLabel5.setText(":  Included in model, searched");
                ivjJLabel5.setBounds(332, 57, 200, 20);
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
     * Return the JLabel6 property value.
     * 
     * @return javax.swing.JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JLabel getJLabel6() {
        if (ivjJLabel6 == null) {
            try {
                ivjJLabel6 = new javax.swing.JLabel();
                ivjJLabel6.setName("JLabel6");
                ivjJLabel6.setText(":  Included in model, not searched");
                ivjJLabel6.setBounds(332, 82, 200, 20);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJLabel6;
    }

    /**
     * Return the JLabel7 property value.
     * 
     * @return javax.swing.JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JLabel getJLabel7() {
        if (ivjJLabel7 == null) {
            try {
                ivjJLabel7 = new javax.swing.JLabel();
                ivjJLabel7.setName("JLabel7");
                ivjJLabel7.setText("0");
                ivjJLabel7.setBounds(322, 32, 10, 20);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJLabel7;
    }

    /**
     * Return the JLabel8 property value.
     * 
     * @return javax.swing.JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JLabel getJLabel8() {
        if (ivjJLabel8 == null) {
            try {
                ivjJLabel8 = new javax.swing.JLabel();
                ivjJLabel8.setName("JLabel8");
                ivjJLabel8.setText("*");
                ivjJLabel8.setBounds(322, 57, 10, 20);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJLabel8;
    }

    /**
     * Return the JLabel9 property value.
     * 
     * @return javax.swing.JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JLabel getJLabel9() {
        if (ivjJLabel9 == null) {
            try {
                ivjJLabel9 = new javax.swing.JLabel();
                ivjJLabel9.setName("JLabel9");
                ivjJLabel9.setText("!");
                ivjJLabel9.setBounds(322, 82, 10, 20);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJLabel9;
    }

    /**
     * Return the SC property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getSC() {
        if (ivjSC == null) {
            try {
                ivjSC = new javax.swing.JCheckBox();
                ivjSC.setName("SC");
                ivjSC.setText("SC");
                ivjSC.setBounds(202, 57, 60, 20);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSC;
    }

    /**
     * Return the SER property value.
     * 
     * @return javax.swing.JRadioButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JRadioButton getSER() {
        if (ivjSER == null) {
            try {
                ivjSER = new javax.swing.JRadioButton();
                ivjSER.setName("SER");
                ivjSER.setText("SER / Testing Procedure");
                ivjSER.setBounds(7, 32, 175, 20);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSER;
    }

    /**
     * Return the STP property value.
     * 
     * @return javax.swing.JRadioButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JRadioButton getSTP() {
        if (ivjSTP == null) {
            try {
                ivjSTP = new javax.swing.JRadioButton();
                ivjSTP.setName("STP");
                ivjSTP.setText("System Testing Procedure");
                ivjSTP.setBounds(7, 82, 175, 20);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSTP;
    }

    /**
     * Returns the selected strategy.
     * 
     * @return coded strategy: 1 - System SER; 2 - SER; 3 - Top-down; 4 - STP
     */
    public int getStrategy() {
        if (getSystemSER().isSelected())
            return 1;
        if (getSER().isSelected())
            return 2;
        if (getTopDown().isSelected())
            return 3;
        if (getSTP().isSelected())
            return 4;

        return 1;

    }

    /**
     * Return the SystemSER property value.
     * 
     * @return javax.swing.JRadioButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JRadioButton getSystemSER() {
        if (ivjSystemSER == null) {
            try {
                ivjSystemSER = new javax.swing.JRadioButton();
                ivjSystemSER.setName("SystemSER");
                ivjSystemSER.setText("System SER");
                ivjSystemSER.setBounds(7, 7, 175, 20);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSystemSER;
    }

    /**
     * Gets the threshold for STP.
     * 
     * @return the threshold
     */
    public double getThreshold() {
        return getThresholdField().getNumber();
    }

    private NumSelector getThresholdField() {
        if (ivjThresholdField == null) {
            try {
                ivjThresholdField = new NumSelector();
                ivjThresholdField.setName("ThresholdField");
                ivjThresholdField.setNumber(2.0);
                ivjThresholdField.setPrecision(2);
                ivjThresholdField.setBounds(202, 82, 35, 20);
                ivjThresholdField.setEnabled(false);
                ivjThresholdField.setRangeExpr("(0,20]");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjThresholdField;
    }

    /**
     * Return the TopDown property value.
     * 
     * @return javax.swing.JRadioButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JRadioButton getTopDown() {
        if (ivjTopDown == null) {
            try {
                ivjTopDown = new javax.swing.JRadioButton();
                ivjTopDown.setName("TopDown");
                ivjTopDown.setSelected(true);
                ivjTopDown.setText("Top Down");
                ivjTopDown.setBounds(7, 57, 175, 20);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjTopDown;
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
        getSTP().addItemListener(ivjEventHandler);
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
            setName("SubsetSpecPanel");
            setBorder(new javax.swing.border.EtchedBorder());
            setLayout(null);
            setSize(548, 130);
            add(getJLabel2(), getJLabel2().getName());
            add(getJLabel7(), getJLabel7().getName());
            add(getJLabel3(), getJLabel3().getName());
            add(getJLabel8(), getJLabel8().getName());
            add(getJLabel5(), getJLabel5().getName());
            add(getJLabel9(), getJLabel9().getName());
            add(getJLabel6(), getJLabel6().getName());
            add(getThresholdField(), getThresholdField().getName());
            add(getJLabel4(), getJLabel4().getName());
            add(getSC(), getSC().getName());
            add(getHQ(), getHQ().getName());
            add(getAIC(), getAIC().getName());
            add(getSystemSER(), getSystemSER().getName());
            add(getSER(), getSER().getName());
            add(getTopDown(), getTopDown().getName());
            add(getSTP(), getSTP().getName());
            initConnections();
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}
        ButtonGroup selectGroup = new javax.swing.ButtonGroup();
        selectGroup.add(getTopDown());
        selectGroup.add(getSER());
        selectGroup.add(getSystemSER());
        selectGroup.add(getSTP());
        ButtonGroup selectGroup1 = new javax.swing.ButtonGroup();
        selectGroup1.add(getAIC());
        selectGroup1.add(getSC());
        selectGroup1.add(getHQ());

        // user code end
    }
}
