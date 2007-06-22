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

package de.jmulti.cafpe;

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
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.JSCSArray;
import com.jstatcom.model.JSCTypeDef;
import com.jstatcom.model.ModelPanel;

import de.jmulti.proc.AutocorrCall;
import de.jmulti.proc.UnivarARCHLMCall;
import de.jmulti.proc.UnivarGodfreyCall;
import de.jmulti.proc.UnivarJBeraCall;
import de.jmulti.proc.UnivarPortmanCall;

/**
 * Diagnostic tests for CAFPE analysis residuals. Can be set for residuals from
 * mean and volatility estimation.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */

final class CAFPE_diagTests extends ModelPanel {
    private static final Logger log = Logger.getLogger(CAFPE_diagTests.class);

    private boolean volatRes = false;

    private JSCTypeDef residuals = CAFPE_constants.RESID_EST;

    private JSCTypeDef xsadj = CAFPE_constants.XSADJ;

    private JPanel ivjJPanel1 = null;

    private ResultField ivjResultField = null;

    private JCheckBox ivjArchLM = null;

    private JLabel ivjJLabel1 = null;

    private NumSelector ivjLagsUniArch = null;

    private NumSelector ivjMaxLagsPort = null;

    private JCheckBox ivjNormal = null;

    private JCheckBox ivjPortman = null;

    private JCheckBox ivjAutocorr = null;

    private JCheckBox ivjGodfrey = null;

    private JLabel ivjJLabel2 = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private JButton ivjExecute = null;

    private JLabel ivjJLabel3 = null;

    private NumSelector ivjLagsGodfrey = null;

    private JLabel ivjJLabel4 = null;

    private NumSelector ivjLagsAutocorr = null;

    class IvjEventHandler implements java.awt.event.ActionListener {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == CAFPE_diagTests.this.getExecute())
                connEtoC1();
            if (e.getSource() == CAFPE_diagTests.this.getPortman())
                connEtoC2();
            if (e.getSource() == CAFPE_diagTests.this.getArchLM())
                connEtoC3();
            if (e.getSource() == CAFPE_diagTests.this.getGodfrey())
                connEtoC4();
            if (e.getSource() == CAFPE_diagTests.this.getAutocorr())
                connEtoC5();
        };
    };

    /**
     * CAFPE_diagTests constructor comment.
     */
    public CAFPE_diagTests() {
        super();
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
     * Comment
     */
    private void autocorr_ActionEvents() {
        getJLabel4().setEnabled(getAutocorr().isSelected());
        getLagsAutocorr().setEnabled(getAutocorr().isSelected());
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
     * connEtoC4: (Godfrey.action. --> CAFPE_diagTests.godfrey_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC4() {
        try {
            // user code begin {1}
            // user code end
            this.godfrey_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC5: (Autocorr.action. -->
     * CAFPE_diagTests.autocorr_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC5() {
        try {
            // user code begin {1}
            // user code end
            this.autocorr_ActionEvents();
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
                && !getGodfrey().isSelected() && !getAutocorr().isSelected()
                && !getNormal().isSelected()) {
            StdMessages.infoNothingSelected("Please select an option.");
            return;
        }

        JSCNArray res = global().get(residuals).getJSCNArray();

        if (getArchLM().isSelected()) {

            // ARCH.
            PCall job = new UnivarARCHLMCall(res, null, getLagsUniArch()
                    .getIntNumber());

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

        // Portman.
        if (getPortman().isSelected()) {
            PCall job = new UnivarPortmanCall(res, getMaxLagsPort()
                    .getIntNumber(), 0);
            job.setOutHolder(getResultField());
            job.setSymbolTable(local());
            job.execute();
        }

        // Autocorrelation of Resids.
        if (getAutocorr().isSelected()) {
            String name;
            if (isVolatRes())
                name = "CAFPE volat. residuals ("
                        + global().get(CAFPE_constants.Y_NAME).getJSCString()
                                .string() + ")";
            else
                name = "CAFPE residuals ("
                        + global().get(CAFPE_constants.Y_NAME).getJSCString()
                                .string() + ")";

            PCall job = new AutocorrCall(res, new JSCSArray("plotname", name),
                    getLagsAutocorr().getIntNumber(), false, false, false);
            job.setSymbolTable(local());
            job.execute();
        }

        // Breusch-Godfrey test
        if (getGodfrey().isSelected()) {
            PCall job = new UnivarGodfreyCall(res, global().get(xsadj)
                    .getJSCNArray(), getLagsGodfrey().getIntNumber());
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
                ivjAutocorr.setPreferredSize(new java.awt.Dimension(150, 21));
                ivjAutocorr.setText("Plot autocorrelation");
                ivjAutocorr.setMinimumSize(new java.awt.Dimension(150, 22));
                ivjAutocorr.setMaximumSize(new java.awt.Dimension(200, 22));
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
     * Return the Godfrey property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getGodfrey() {
        if (ivjGodfrey == null) {
            try {
                ivjGodfrey = new javax.swing.JCheckBox();
                ivjGodfrey.setName("Godfrey");
                ivjGodfrey.setSelected(true);
                ivjGodfrey.setPreferredSize(new java.awt.Dimension(150, 21));
                ivjGodfrey.setText("Godfrey test");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjGodfrey;
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
                ivjJLabel3.setPreferredSize(new java.awt.Dimension(30, 21));
                ivjJLabel3.setText("Lags");
                ivjJLabel3.setMinimumSize(new java.awt.Dimension(24, 21));
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
                ivjJLabel4.setPreferredSize(new java.awt.Dimension(30, 21));
                ivjJLabel4.setText("Lags");
                ivjJLabel4.setMinimumSize(new java.awt.Dimension(24, 21));
                ivjJLabel4.setEnabled(false);
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
                constraintsNormal.gridx = 3;
                constraintsNormal.gridy = 0;
                constraintsNormal.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsNormal.insets = new java.awt.Insets(10, 40, 0, 0);
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

                java.awt.GridBagConstraints constraintsGodfrey = new java.awt.GridBagConstraints();
                constraintsGodfrey.gridx = 0;
                constraintsGodfrey.gridy = 2;
                constraintsGodfrey.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsGodfrey.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsGodfrey.insets = new java.awt.Insets(5, 10, 0, 0);
                getJPanel1().add(getGodfrey(), constraintsGodfrey);

                java.awt.GridBagConstraints constraintsJLabel3 = new java.awt.GridBagConstraints();
                constraintsJLabel3.gridx = 1;
                constraintsJLabel3.gridy = 2;
                constraintsJLabel3.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel3.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsJLabel3.insets = new java.awt.Insets(5, 10, 0, 0);
                getJPanel1().add(getJLabel3(), constraintsJLabel3);

                java.awt.GridBagConstraints constraintsLagsGodfrey = new java.awt.GridBagConstraints();
                constraintsLagsGodfrey.gridx = 2;
                constraintsLagsGodfrey.gridy = 2;
                constraintsLagsGodfrey.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsLagsGodfrey.insets = new java.awt.Insets(5, 5, 0, 0);
                getJPanel1().add(getLagsGodfrey(), constraintsLagsGodfrey);

                java.awt.GridBagConstraints constraintsAutocorr = new java.awt.GridBagConstraints();
                constraintsAutocorr.gridx = 3;
                constraintsAutocorr.gridy = 1;
                constraintsAutocorr.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsAutocorr.anchor = java.awt.GridBagConstraints.NORTH;
                constraintsAutocorr.insets = new java.awt.Insets(5, 40, 0, 0);
                getJPanel1().add(getAutocorr(), constraintsAutocorr);

                java.awt.GridBagConstraints constraintsExecute = new java.awt.GridBagConstraints();
                constraintsExecute.gridx = 3;
                constraintsExecute.gridy = 2;
                constraintsExecute.anchor = java.awt.GridBagConstraints.SOUTHWEST;
                constraintsExecute.weighty = 1.0;
                constraintsExecute.insets = new java.awt.Insets(0, 40, 10, 10);
                getJPanel1().add(getExecute(), constraintsExecute);

                java.awt.GridBagConstraints constraintsJLabel4 = new java.awt.GridBagConstraints();
                constraintsJLabel4.gridx = 4;
                constraintsJLabel4.gridy = 1;
                constraintsJLabel4.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel4.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsJLabel4.insets = new java.awt.Insets(5, 10, 0, 0);
                getJPanel1().add(getJLabel4(), constraintsJLabel4);

                java.awt.GridBagConstraints constraintsLagsAutocorr = new java.awt.GridBagConstraints();
                constraintsLagsAutocorr.gridx = 5;
                constraintsLagsAutocorr.gridy = 1;
                constraintsLagsAutocorr.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsLagsAutocorr.weightx = 1.0;
                constraintsLagsAutocorr.insets = new java.awt.Insets(5, 5, 0, 0);
                constraintsNormal.gridwidth = 3;
                ivjJPanel1.add(getNormal(), constraintsNormal);
                getJPanel1().add(getLagsAutocorr(), constraintsLagsAutocorr);
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
                ivjLagsAutocorr.setHorizontalAlignment(SwingConstants.RIGHT);
                ivjLagsAutocorr.setEnabled(false);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjLagsAutocorr;
    }

    private NumSelector getLagsGodfrey() {
        if (ivjLagsGodfrey == null) {
            try {
                ivjLagsGodfrey = new NumSelector();
                ivjLagsGodfrey.setName("LagsGodfrey");
                ivjLagsGodfrey.setPreferredSize(new java.awt.Dimension(55, 21));
                ivjLagsGodfrey.setNumber(4.0);
                ivjLagsGodfrey.setMinimumSize(new java.awt.Dimension(55, 21));
                ivjLagsGodfrey.setRangeExpr("[1,100]");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjLagsGodfrey;
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
     * Comment
     */
    private void godfrey_ActionEvents() {
        getJLabel3().setEnabled(getGodfrey().isSelected());
        getLagsGodfrey().setEnabled(getGodfrey().isSelected());

        return;
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
        getGodfrey().addActionListener(ivjEventHandler);
        getAutocorr().addActionListener(ivjEventHandler);
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
     * description
     * 
     * @return boolean
     */
    public boolean isVolatRes() {
        return volatRes;
    }

    /**
     * Comment
     */
    private void portman_ActionEvents() {
        getJLabel1().setEnabled(getPortman().isSelected());
        getMaxLagsPort().setEnabled(getPortman().isSelected());
        return;
    }

    /**
     * Insert the method's description here. Creation date: (17.04.2002
     * 18:02:24)
     * 
     * @param newResiduals
     *            String
     */
    public void setResiduals(JSCTypeDef newResiduals) {
        residuals = newResiduals;
    }

    /**
     * If true, Jarcque Bera test is disabnled and hidden, because it makes no
     * sense in volatility analysis.
     * 
     * @param newVolatRes
     *            boolean
     */
    public void setVolatRes(boolean newVolatRes) {
        volatRes = newVolatRes;
        getNormal().setSelected(!newVolatRes);
        getNormal().setVisible(!newVolatRes);
    }

    /**
     * Insert the method's description here. Creation date: (17.04.2002
     * 18:02:24)
     * 
     * @param newXsadj
     *            String
     */
    public void setXsadj(JSCTypeDef newXsadj) {
        xsadj = newXsadj;
    }
}