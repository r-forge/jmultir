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

package de.jmulti.arima;

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
import com.jstatcom.model.ModelPanel;

import de.jmulti.proc.UnivarARCHLMCall;
import de.jmulti.proc.UnivarJBeraCall;
import de.jmulti.proc.UnivarPortmanCall;

/**
 * Diagnostic tests for CAFPE analysis residuals. Can be set for residuals from
 * mean and volatility estimation.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */

final class DiagTestsPanel extends ModelPanel {
    private static final Logger log = Logger.getLogger(DiagTestsPanel.class);

    private JPanel ivjJPanel1 = null;

    private ResultField ivjResultField = null;

    private JCheckBox ivjArchLM = null;

    private JLabel ivjJLabel1 = null;

    private NumSelector ivjLagsUniArch = null;

    private NumSelector ivjMaxLagsPort = null;

    private JCheckBox ivjNormal = null;

    private JCheckBox ivjPortman = null;

    private JLabel ivjJLabel2 = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private JButton ivjExecute = null;

    class IvjEventHandler implements java.awt.event.ActionListener {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == DiagTestsPanel.this.getExecute())
                connEtoC1();
            if (e.getSource() == DiagTestsPanel.this.getPortman())
                connEtoC2();
            if (e.getSource() == DiagTestsPanel.this.getArchLM())
                connEtoC3();
        };
    };

    /**
     * CAFPE_diagTests constructor comment.
     */
    public DiagTestsPanel() {
        super();
        setName("DiagTestsPanel");
        initialize();
    }

    /**
     * Comment
     */
    private void archLM_ActionEvents() {
        getJLabel2().setEnabled(getArchLM().isSelected());
        getLagsUniArch().setEnabled(getArchLM().isSelected());

        return;
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
     * connEtoC2: (Portman.action. --> CAFPE_diagTests.portman_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC2() {
        try {
            // user code begin {1}
            // user code end
            this.portman_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC3: (ArchLM.action. --> CAFPE_diagTests.archLM_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC3() {
        try {
            // user code begin {1}
            // user code end
            this.archLM_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * Comment
     */
    private void execute_ActionEvents() {
        if (!getPortman().isSelected() && !getArchLM().isSelected()
                && !getNormal().isSelected()) {
            StdMessages.infoNothingSelected("Please select an option.");
            return;
        }

        JSCNArray res = global().get(ARIMAConstants.EST_RESIDS).getJSCNArray();

        // Portman.
        if (getPortman().isSelected()) {
            int p = global().get(ARIMAConstants.P).getJSCInt().intVal();
            int q = global().get(ARIMAConstants.Q).getJSCInt().intVal();
            int k = global().get(ARIMAConstants.DET_NAMES_ALL).getJSCSArray()
                    .cols();

            PCall job = new UnivarPortmanCall(res, getMaxLagsPort()
                    .getIntNumber(), p + q + k);
            job.setOutHolder(getResultField());
            job.setSymbolTable(local());
            job.execute();
        }
        if (getNormal().isSelected()) {

            // Nonnormality.
            PCall job = new UnivarJBeraCall(res, null);
            job.setOutHolder(getResultField());
            job.setSymbolTable(local());
            job.execute();
        }

        if (getArchLM().isSelected()) {

            // ARCH.
            PCall job = new UnivarARCHLMCall(res, null, getLagsUniArch()
                    .getIntNumber());

            job.setOutHolder(getResultField());
            job.setSymbolTable(local());
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
                ivjArchLM.setPreferredSize(new java.awt.Dimension(150, 21));
                ivjArchLM.setText("Univariate ARCH-LM");
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
                ivjJLabel1.setText("Lags");
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
                ivjJLabel2.setText("Lags");
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
                ivjJPanel1.setPreferredSize(new java.awt.Dimension(550, 140));

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
                constraintsMaxLagsPort.insets = new java.awt.Insets(10, 5, 0, 0);
                getJPanel1().add(getMaxLagsPort(), constraintsMaxLagsPort);

                java.awt.GridBagConstraints constraintsNormal = new java.awt.GridBagConstraints();
                constraintsNormal.gridx = 0;
                constraintsNormal.gridy = 2;
                constraintsNormal.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsNormal.anchor = java.awt.GridBagConstraints.NORTH;
                constraintsNormal.insets = new java.awt.Insets(5, 10, 0, 0);
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

                java.awt.GridBagConstraints constraintsLagsUniArch = new java.awt.GridBagConstraints();
                constraintsLagsUniArch.gridx = 2;
                constraintsLagsUniArch.gridy = 1;
                constraintsLagsUniArch.anchor = java.awt.GridBagConstraints.NORTH;
                constraintsLagsUniArch.insets = new java.awt.Insets(5, 5, 0, 0);
                getJPanel1().add(getLagsUniArch(), constraintsLagsUniArch);

                java.awt.GridBagConstraints constraintsExecute = new java.awt.GridBagConstraints();
                constraintsExecute.gridx = 3;
                constraintsExecute.gridy = 2;
                constraintsExecute.anchor = java.awt.GridBagConstraints.SOUTHWEST;
                constraintsExecute.weighty = 1.0;
                constraintsExecute.weightx = 1.0D;
                constraintsExecute.insets = new java.awt.Insets(0, 10, 10, 10);
                ivjJPanel1.add(getExecute(), constraintsExecute);
                ivjJPanel1.add(getNormal(), constraintsNormal);
                constraintsNormal.gridwidth = 3;
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJPanel1;
    }

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
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjLagsUniArch;
    }

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
                ivjNormal.setText("Tests for nonnormality");
                ivjNormal.setMaximumSize(new java.awt.Dimension(200, 22));
                ivjNormal.setSelected(true);
                ivjNormal.setPreferredSize(new java.awt.Dimension(150, 21));
                ivjNormal.setMinimumSize(new java.awt.Dimension(150, 22));
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
                ivjPortman.setPreferredSize(new java.awt.Dimension(150, 21));
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
        getPortman().addActionListener(ivjEventHandler);
        getArchLM().addActionListener(ivjEventHandler);

    }

    /**
     * Initialize the class.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initialize() {
        try {
            // user code begin {1}
            // user code end
            setName("CAFPE_diagTests");
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
     * Comment
     */
    private void portman_ActionEvents() {
        getJLabel1().setEnabled(getPortman().isSelected());
        getMaxLagsPort().setEnabled(getPortman().isSelected());
        return;
    }

}