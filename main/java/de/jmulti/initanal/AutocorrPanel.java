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

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import org.apache.log4j.Logger;

import com.jstatcom.component.NumSelector;
import com.jstatcom.component.ResultField;
import com.jstatcom.component.StdMessages;
import com.jstatcom.engine.PCall;
import com.jstatcom.model.ModelPanel;

import de.jmulti.proc.AutocorrCall;

/**
 * Panel for plotting and displaying autocorrelation/partial autocorr.
 * functions.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public class AutocorrPanel extends ModelPanel {
    private static final Logger log = Logger.getLogger(AutocorrPanel.class);

    private int maxLags = 0;

    private JButton ivjAutocorrelation = null;

    private JCheckBox ivjAutocorrText = null;

    private JPanel ivjCorrTab = null;

    private JLabel ivjJLabel1 = null;

    private NumSelector ivjMaxLags = null;

    private ResultField ivjResultField = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private JCheckBox ivjSquaredCheck = null;

    class IvjEventHandler implements java.awt.event.ActionListener {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == AutocorrPanel.this.getAutocorrelation())
                connEtoC1();
        };
    };

    /**
     * AutocorrPanel constructor comment.
     */
    public AutocorrPanel() {
        super();
        initialize();
    }

    /**
     * Comment
     */
    private void autocorrelation_ActionEvents() {

        if (global().get(WorkbenchPanel.WB_DATA).isEmpty()) {
            StdMessages
                    .infoNothingSelected("Please select at least one series.");
            return;
        }

        maxLags = getMaxLags().getIntNumber();

        PCall jobAuto = new AutocorrCall(global().get(WorkbenchPanel.WB_DATA)
                .getJSCNArray(), global().get(WorkbenchPanel.WB_DATANAMES)
                .getJSCSArray(), maxLags, getSquaredCheck().isSelected(),
                getAutocorrText().isSelected(), true);

        jobAuto.setSymbolTable(local());
        jobAuto.setOutHolder(getResultField());
        jobAuto.execute();

    }

    /**
     * connEtoC1: (Autocorrelation.action. -->
     * AutocorrPanel.autocorrelation_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC1() {
        try {
            // user code begin {1}
            // user code end
            this.autocorrelation_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * Return the Autocorrelation property value.
     * 
     * @return javax.swing.JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JButton getAutocorrelation() {
        if (ivjAutocorrelation == null) {
            try {
                ivjAutocorrelation = new javax.swing.JButton();
                ivjAutocorrelation.setName("Autocorrelation");
                ivjAutocorrelation.setText("Execute");
                ivjAutocorrelation.setPreferredSize(new java.awt.Dimension(100,
                        27));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjAutocorrelation;
    }

    /**
     * Return the AutocorrText property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getAutocorrText() {
        if (ivjAutocorrText == null) {
            try {
                ivjAutocorrText = new javax.swing.JCheckBox();
                ivjAutocorrText.setName("AutocorrText");
                ivjAutocorrText
                        .setText("Output as text (with Portmanteau stat)");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjAutocorrText;
    }

    /**
     * Return the CorrTab property value.
     * 
     * @return javax.swing.JPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JPanel getCorrTab() {
        if (ivjCorrTab == null) {
            try {
                ivjCorrTab = new javax.swing.JPanel();
                ivjCorrTab.setName("CorrTab");
                ivjCorrTab.setPreferredSize(new java.awt.Dimension(200, 80));
                ivjCorrTab.setBorder(new BevelBorder(BevelBorder.LOWERED));
                ivjCorrTab.setLayout(new java.awt.GridBagLayout());

                java.awt.GridBagConstraints constraintsMaxLags = new java.awt.GridBagConstraints();
                constraintsMaxLags.gridx = 0;
                constraintsMaxLags.gridy = 0;
                constraintsMaxLags.weighty = 1.0;
                constraintsMaxLags.insets = new java.awt.Insets(5, 5, 5, 5);
                getCorrTab().add(getMaxLags(), constraintsMaxLags);

                java.awt.GridBagConstraints constraintsJLabel1 = new java.awt.GridBagConstraints();
                constraintsJLabel1.gridx = 1;
                constraintsJLabel1.gridy = 0;
                constraintsJLabel1.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel1.weightx = 1.0;
                constraintsJLabel1.weighty = 1.0;
                getCorrTab().add(getJLabel1(), constraintsJLabel1);

                java.awt.GridBagConstraints constraintsAutocorrelation = new java.awt.GridBagConstraints();
                constraintsAutocorrelation.gridx = 2;
                constraintsAutocorrelation.gridy = 1;
                constraintsAutocorrelation.anchor = java.awt.GridBagConstraints.SOUTH;
                constraintsAutocorrelation.insets = new java.awt.Insets(0, 5,
                        5, 5);
                getCorrTab().add(getAutocorrelation(),
                        constraintsAutocorrelation);

                java.awt.GridBagConstraints constraintsAutocorrText = new java.awt.GridBagConstraints();
                constraintsAutocorrText.gridx = 0;
                constraintsAutocorrText.gridy = 1;
                constraintsAutocorrText.gridwidth = 2;
                constraintsAutocorrText.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsAutocorrText.anchor = java.awt.GridBagConstraints.SOUTH;
                constraintsAutocorrText.weighty = 1.0;
                constraintsAutocorrText.insets = new java.awt.Insets(0, 5, 5, 0);
                getCorrTab().add(getAutocorrText(), constraintsAutocorrText);

                java.awt.GridBagConstraints constraintsSquaredCheck = new java.awt.GridBagConstraints();
                constraintsSquaredCheck.gridx = 2;
                constraintsSquaredCheck.gridy = 0;
                constraintsSquaredCheck.gridwidth = 2;
                constraintsSquaredCheck.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsSquaredCheck.insets = new java.awt.Insets(0, 5, 5, 0);
                getCorrTab().add(getSquaredCheck(), constraintsSquaredCheck);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjCorrTab;
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
                ivjJLabel1.setText("Lags for autocorrelation");
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
     * Return the MaxLags property value.
     * 
     * @return com.jstatcom.model.NumberSelector
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private NumSelector getMaxLags() {
        if (ivjMaxLags == null) {
            try {
                ivjMaxLags = new NumSelector();
                ivjMaxLags.setName("MaxLags");
                ivjMaxLags.setNumber(36.0);
                ivjMaxLags.setRangeExpr("[2,100]");
                ivjMaxLags.setPreferredSize(new java.awt.Dimension(40, 20));
                ivjMaxLags.setHorizontalAlignment(SwingConstants.LEFT);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjMaxLags;
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
     * Return the SquaredCheck property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getSquaredCheck() {
        if (ivjSquaredCheck == null) {
            try {
                ivjSquaredCheck = new javax.swing.JCheckBox();
                ivjSquaredCheck.setName("SquaredCheck");
                ivjSquaredCheck
                        .setPreferredSize(new java.awt.Dimension(200, 22));
                ivjSquaredCheck.setText("Autocorrelation of squared");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSquaredCheck;
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
        getAutocorrelation().addActionListener(ivjEventHandler);
    }

    /**
     * Initialize the class.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initialize() {
        try {
            // user code begin {1}
            // user code end
            setName("AutocorrPanel");
            setLayout(new java.awt.BorderLayout());
            setSize(567, 309);
            add(getResultField(), "Center");
            add(getCorrTab(), "North");
            initConnections();
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}
        // user code end
    }
}