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
import com.jstatcom.model.JSCSArray;
import com.jstatcom.model.JSCTypeDef;
import com.jstatcom.model.ModelPanel;

import de.jmulti.proc.LMTestCall;
import de.jmulti.proc.MultvarArchLMCall;
import de.jmulti.proc.MultvarNonNormCall;
import de.jmulti.proc.MultvarPortmanCall;
import de.jmulti.proc.UnivarARCHLMCall;
import de.jmulti.proc.UnivarJBeraCall;
import de.jmulti.var.VARConstants;
import de.jmulti.vecm.VECMConstants;

/**
 * A panel providing access to various potentially multivariate diagnostic tests
 * to be used by VAR and VECM models.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public final class MultDiagTests extends ModelPanel {
    private static final Logger log = Logger.getLogger(MultDiagTests.class);

    private ModelTypes modelType = null;

    private JSCTypeDef resids = null;

    private JSCTypeDef residNames = null;

    private JSCTypeDef pyLags = null;

    private JCheckBox ivjArchLMmult = null;

    private JCheckBox ivjArchLMuni = null;

    private JButton ivjExecute = null;

    private JLabel ivjJLabel = null;

    private JLabel ivjJLabel1 = null;

    private JLabel ivjJLabel2 = null;

    private JLabel ivjJLabel6 = null;

    private NumSelector ivjLagsUniArch = null;

    private JCheckBox ivjLMAutocorr = null;

    private NumSelector ivjLMLags = null;

    private NumSelector ivjMaxLagsPort = null;

    private NumSelector ivjMultArchLags = null;

    private JCheckBox ivjNonnormality = null;

    private JCheckBox ivjPortmanteau = null;

    private ResultField ivjResultField = null;

    private JPanel ivjTests = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    class IvjEventHandler implements java.awt.event.ActionListener,
            java.awt.event.ItemListener {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == MultDiagTests.this.getExecute())
                connEtoC1();
        };

        public void itemStateChanged(java.awt.event.ItemEvent e) {
            if (e.getSource() == MultDiagTests.this.getPortmanteau())
                connPtoP1SetTarget();
            if (e.getSource() == MultDiagTests.this.getPortmanteau())
                connPtoP2SetTarget();
            if (e.getSource() == MultDiagTests.this.getArchLMmult())
                connPtoP3SetTarget();
            if (e.getSource() == MultDiagTests.this.getArchLMmult())
                connPtoP4SetTarget();
            if (e.getSource() == MultDiagTests.this.getArchLMuni())
                connPtoP5SetTarget();
            if (e.getSource() == MultDiagTests.this.getArchLMuni())
                connPtoP6SetTarget();
            if (e.getSource() == MultDiagTests.this.getLMAutocorr())
                connPtoP7SetTarget();
            if (e.getSource() == MultDiagTests.this.getLMAutocorr())
                connPtoP8SetTarget();
        };
    };

    /**
     * MultivariateDiagnosticTests constructor comment.
     */
    public MultDiagTests() {
        super();
        initialize();
    }

    /**
     * connEtoC1: (Execute.action. --> MultDiagTests.execute_ActionEvents()V)
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
     * connPtoP1SetTarget: (Portmanteau.selected <--> JLabel1.enabled)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connPtoP1SetTarget() {
        /* Set the target from the source */
        try {
            getJLabel1().setEnabled(getPortmanteau().isSelected());
            // user code begin {1}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connPtoP2SetTarget: (Portmanteau.selected <--> MaxLagsPort.enabled)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connPtoP2SetTarget() {
        /* Set the target from the source */
        try {
            getMaxLagsPort().setEnabled(getPortmanteau().isSelected());
            // user code begin {1}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connPtoP3SetTarget: (ArchLMmult.selected <--> JLabel2.enabled)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connPtoP3SetTarget() {
        /* Set the target from the source */
        try {
            getJLabel2().setEnabled(getArchLMmult().isSelected());
            // user code begin {1}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connPtoP4SetTarget: (ArchLMmult.selected <--> MultArchLags.enabled)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connPtoP4SetTarget() {
        /* Set the target from the source */
        try {
            getMultArchLags().setEnabled(getArchLMmult().isSelected());
            // user code begin {1}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connPtoP5SetTarget: (ArchLMuni.selected <--> JLabel.enabled)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connPtoP5SetTarget() {
        /* Set the target from the source */
        try {
            getJLabel().setEnabled(getArchLMuni().isSelected());
            // user code begin {1}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connPtoP6SetTarget: (ArchLMuni.selected <--> LagsUniArch.enabled)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connPtoP6SetTarget() {
        /* Set the target from the source */
        try {
            getLagsUniArch().setEnabled(getArchLMuni().isSelected());
            // user code begin {1}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connPtoP7SetTarget: (LMAutocorr.selected <--> JLabel6.enabled)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connPtoP7SetTarget() {
        /* Set the target from the source */
        try {
            getJLabel6().setEnabled(getLMAutocorr().isSelected());
            // user code begin {1}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connPtoP8SetTarget: (LMAutocorr.selected <--> LMLags.enabled)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connPtoP8SetTarget() {
        /* Set the target from the source */
        try {
            getLMLags().setEnabled(getLMAutocorr().isSelected());
            // user code begin {1}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * Makes call to selected tests.
     */
    private void execute_ActionEvents() {
        if (!getLMAutocorr().isSelected() && !getArchLMmult().isSelected()
                && !getArchLMuni().isSelected()
                && !getPortmanteau().isSelected()
                && !getNonnormality().isSelected()) {
            StdMessages.infoNothingSelected("Please select an option");
            return;
        }
        JSCNArray res = global().get(resids).getJSCNArray();
        JSCSArray resNames = global().get(residNames).getJSCSArray();
        int py = global().get(pyLags).getJSCInt().intVal();

        // Portmanteau test.
        if (getPortmanteau().isSelected()) {
            PCall job = new MultvarPortmanCall(global(), local(), modelType,
                    getMaxLagsPort().getIntNumber());
            job.setOutHolder(getResultField());
            job.execute();
        }
        // LM Test
        if (getLMAutocorr().isSelected()) {
            PCall job = new LMTestCall(global(), local(), modelType,
                    getLMLags().getIntNumber());
            job.setOutHolder(getResultField());
            job.execute();
        }

        // Nonnormality.
        if (getNonnormality().isSelected()) {
            PCall jobM = new MultvarNonNormCall(res, py);
            jobM.setOutHolder(getResultField());
            jobM.setSymbolTable(local());
            jobM.execute();

            PCall job = new UnivarJBeraCall(res, resNames);
            job.setOutHolder(getResultField());
            job.setSymbolTable(local());
            job.execute();
        }
        // Univariate ARCH-LM.
        if (getArchLMuni().isSelected()) {
            PCall job = new UnivarARCHLMCall(res, resNames, getLagsUniArch()
                    .getIntNumber());
            job.setOutHolder(getResultField());
            job.setSymbolTable(local());
            job.execute();
        }
        // Multivariate ARCH-LM.
        if (getArchLMmult().isSelected()) {
            PCall job = new MultvarArchLMCall(res, getMultArchLags()
                    .getIntNumber());
            job.setOutHolder(getResultField());
            job.setSymbolTable(local());
            job.execute();
        }

    }

    /**
     * Return the ArchLMmult property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getArchLMmult() {
        if (ivjArchLMmult == null) {
            try {
                ivjArchLMmult = new javax.swing.JCheckBox();
                ivjArchLMmult.setName("ArchLMmult");
                ivjArchLMmult.setSelected(true);
                ivjArchLMmult.setText("Multivariate ARCH-LM");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjArchLMmult;
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
                ivjExecute.setPreferredSize(new java.awt.Dimension(120, 27));
                ivjExecute.setText("Execute");
                ivjExecute.setMinimumSize(new java.awt.Dimension(120, 27));
                ivjExecute.setMaximumSize(new java.awt.Dimension(120, 27));
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
                ivjJLabel.setMinimumSize(new java.awt.Dimension(24, 21));
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
                ivjJLabel6.setPreferredSize(new java.awt.Dimension(30, 21));
                ivjJLabel6.setText("Lags");
                ivjJLabel6.setMinimumSize(new java.awt.Dimension(24, 21));
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

    private NumSelector getLagsUniArch() {
        if (ivjLagsUniArch == null) {
            try {
                ivjLagsUniArch = new NumSelector();
                ivjLagsUniArch.setName("LagsUniArch");
                ivjLagsUniArch.setPreferredSize(new java.awt.Dimension(55, 21));
                ivjLagsUniArch.setNumber(16.0);
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

    /**
     * Return the LMAutocorr property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getLMAutocorr() {
        if (ivjLMAutocorr == null) {
            try {
                ivjLMAutocorr = new javax.swing.JCheckBox();
                ivjLMAutocorr.setName("LMAutocorr");
                ivjLMAutocorr.setSelected(true);
                ivjLMAutocorr.setText("LM tests for autocorr.");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjLMAutocorr;
    }

    private NumSelector getLMLags() {
        if (ivjLMLags == null) {
            try {
                ivjLMLags = new NumSelector();
                ivjLMLags.setName("LMLags");
                ivjLMLags.setPreferredSize(new java.awt.Dimension(55, 21));
                ivjLMLags.setNumber(5.0);
                ivjLMLags.setMinimumSize(new java.awt.Dimension(55, 21));
                ivjLMLags.setRangeExpr("[1,20]");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjLMLags;
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
     * Gets the model type currently in use.
     * 
     * @return model type
     */
    public ModelTypes getModelType() {
        return modelType;
    }

    private NumSelector getMultArchLags() {
        if (ivjMultArchLags == null) {
            try {
                ivjMultArchLags = new NumSelector();
                ivjMultArchLags.setName("MultArchLags");
                ivjMultArchLags
                        .setPreferredSize(new java.awt.Dimension(55, 21));
                ivjMultArchLags.setNumber(5.0);
                ivjMultArchLags.setMinimumSize(new java.awt.Dimension(55, 21));
                ivjMultArchLags.setRangeExpr("[1,20]");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjMultArchLags;
    }

    /**
     * Return the Nonnormality property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getNonnormality() {
        if (ivjNonnormality == null) {
            try {
                ivjNonnormality = new javax.swing.JCheckBox();
                ivjNonnormality.setName("Nonnormality");
                ivjNonnormality.setSelected(true);
                ivjNonnormality
                        .setPreferredSize(new java.awt.Dimension(150, 25));
                ivjNonnormality.setText("Tests for nonnormality");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjNonnormality;
    }

    /**
     * Return the Portmanteau property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getPortmanteau() {
        if (ivjPortmanteau == null) {
            try {
                ivjPortmanteau = new javax.swing.JCheckBox();
                ivjPortmanteau.setName("Portmanteau");
                ivjPortmanteau.setSelected(true);
                ivjPortmanteau.setText("Portmanteau test");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjPortmanteau;
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
                ivjResultField
                        .setPreferredSize(new java.awt.Dimension(200, 100));
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
     * Return the Tests property value.
     * 
     * @return javax.swing.JPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JPanel getTests() {
        if (ivjTests == null) {
            try {
                ivjTests = new javax.swing.JPanel();
                ivjTests.setName("Tests");
                ivjTests.setBorder(new BevelBorder(BevelBorder.LOWERED));
                ivjTests.setLayout(new java.awt.GridBagLayout());
                ivjTests.setPreferredSize(new java.awt.Dimension(550, 100));

                java.awt.GridBagConstraints constraintsPortmanteau = new java.awt.GridBagConstraints();
                constraintsPortmanteau.gridx = 0;
                constraintsPortmanteau.gridy = 0;
                constraintsPortmanteau.anchor = java.awt.GridBagConstraints.SOUTHWEST;
                constraintsPortmanteau.insets = new java.awt.Insets(5, 5, 0, 0);
                java.awt.GridBagConstraints constraintsJLabel1 = new java.awt.GridBagConstraints();
                constraintsJLabel1.gridx = 1;
                constraintsJLabel1.gridy = 0;
                constraintsJLabel1.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel1.anchor = java.awt.GridBagConstraints.SOUTHWEST;
                constraintsJLabel1.insets = new java.awt.Insets(5, 20, 0, 10);
                getTests().add(getJLabel1(), constraintsJLabel1);

                java.awt.GridBagConstraints constraintsNonnormality = new java.awt.GridBagConstraints();
                constraintsNonnormality.gridx = 3;
                constraintsNonnormality.gridy = 1;
                constraintsNonnormality.anchor = java.awt.GridBagConstraints.WEST;
                constraintsNonnormality.insets = new java.awt.Insets(0, 20, 0,
                        0);
                java.awt.GridBagConstraints constraintsMaxLagsPort = new java.awt.GridBagConstraints();
                constraintsMaxLagsPort.gridx = 2;
                constraintsMaxLagsPort.gridy = 0;
                constraintsMaxLagsPort.anchor = java.awt.GridBagConstraints.SOUTHWEST;
                constraintsMaxLagsPort.insets = new java.awt.Insets(5, 0, 0, 0);
                getTests().add(getMaxLagsPort(), constraintsMaxLagsPort);

                java.awt.GridBagConstraints constraintsExecute = new java.awt.GridBagConstraints();
                constraintsExecute.gridx = 3;
                constraintsExecute.gridy = 2;
                constraintsExecute.gridwidth = 3;
                constraintsExecute.anchor = java.awt.GridBagConstraints.SOUTHEAST;
                constraintsExecute.weightx = 1.0;
                constraintsExecute.weighty = 1.0;
                constraintsExecute.insets = new java.awt.Insets(0, 20, 5, 10);
                getTests().add(getExecute(), constraintsExecute);

                java.awt.GridBagConstraints constraintsArchLMuni = new java.awt.GridBagConstraints();
                constraintsArchLMuni.gridx = 0;
                constraintsArchLMuni.gridy = 2;
                constraintsArchLMuni.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsArchLMuni.insets = new java.awt.Insets(0, 5, 5, 0);
                java.awt.GridBagConstraints constraintsJLabel = new java.awt.GridBagConstraints();
                constraintsJLabel.gridx = 1;
                constraintsJLabel.gridy = 2;
                constraintsJLabel.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsJLabel.insets = new java.awt.Insets(0, 20, 5, 10);
                getTests().add(getJLabel(), constraintsJLabel);

                java.awt.GridBagConstraints constraintsLagsUniArch = new java.awt.GridBagConstraints();
                constraintsLagsUniArch.gridx = 2;
                constraintsLagsUniArch.gridy = 2;
                constraintsLagsUniArch.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsLagsUniArch.insets = new java.awt.Insets(0, 0, 5, 0);
                getTests().add(getLagsUniArch(), constraintsLagsUniArch);

                java.awt.GridBagConstraints constraintsArchLMmult = new java.awt.GridBagConstraints();
                constraintsArchLMmult.gridx = 0;
                constraintsArchLMmult.gridy = 1;
                constraintsArchLMmult.anchor = java.awt.GridBagConstraints.WEST;
                constraintsArchLMmult.insets = new java.awt.Insets(0, 5, 0, 0);
                java.awt.GridBagConstraints constraintsJLabel2 = new java.awt.GridBagConstraints();
                constraintsJLabel2.gridx = 1;
                constraintsJLabel2.gridy = 1;
                constraintsJLabel2.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel2.insets = new java.awt.Insets(0, 20, 0, 10);
                getTests().add(getJLabel2(), constraintsJLabel2);

                java.awt.GridBagConstraints constraintsMultArchLags = new java.awt.GridBagConstraints();
                constraintsMultArchLags.gridx = 2;
                constraintsMultArchLags.gridy = 1;
                constraintsMultArchLags.anchor = java.awt.GridBagConstraints.WEST;
                getTests().add(getMultArchLags(), constraintsMultArchLags);

                java.awt.GridBagConstraints constraintsLMAutocorr = new java.awt.GridBagConstraints();
                constraintsLMAutocorr.gridx = 3;
                constraintsLMAutocorr.gridy = 0;
                constraintsLMAutocorr.anchor = java.awt.GridBagConstraints.WEST;
                constraintsLMAutocorr.insets = new java.awt.Insets(5, 20, 0, 0);
                java.awt.GridBagConstraints constraintsJLabel6 = new java.awt.GridBagConstraints();
                constraintsJLabel6.gridx = 4;
                constraintsJLabel6.gridy = 0;
                constraintsJLabel6.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel6.insets = new java.awt.Insets(5, 20, 0, 10);
                getTests().add(getJLabel6(), constraintsJLabel6);

                java.awt.GridBagConstraints constraintsLMLags = new java.awt.GridBagConstraints();
                constraintsNonnormality.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsLMAutocorr.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsLMLags.gridx = 5;
                constraintsLMLags.gridy = 0;
                constraintsLMLags.anchor = java.awt.GridBagConstraints.WEST;
                constraintsLMLags.insets = new java.awt.Insets(5, 0, 0, 0);
                constraintsPortmanteau.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsArchLMuni.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsArchLMmult.fill = java.awt.GridBagConstraints.HORIZONTAL;
                ivjTests.add(getPortmanteau(), constraintsPortmanteau);
                ivjTests.add(getNonnormality(), constraintsNonnormality);
                ivjTests.add(getArchLMuni(), constraintsArchLMuni);
                ivjTests.add(getArchLMmult(), constraintsArchLMmult);
                ivjTests.add(getLMAutocorr(), constraintsLMAutocorr);
                getTests().add(getLMLags(), constraintsLMLags);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjTests;
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
        getPortmanteau().addItemListener(ivjEventHandler);
        getArchLMmult().addItemListener(ivjEventHandler);
        getArchLMuni().addItemListener(ivjEventHandler);
        getLMAutocorr().addItemListener(ivjEventHandler);
        getExecute().addActionListener(ivjEventHandler);
        connPtoP1SetTarget();
        connPtoP2SetTarget();
        connPtoP3SetTarget();
        connPtoP4SetTarget();
        connPtoP5SetTarget();
        connPtoP6SetTarget();
        connPtoP7SetTarget();
        connPtoP8SetTarget();
    }

    /**
     * Initialize the class.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initialize() {
        try {
            // user code begin {1}
            // user code end
            setName("MultivariateDiagnosticTests");
            setLayout(new java.awt.BorderLayout());
            setSize(584, 324);
            add(getTests(), "North");
            add(getResultField(), "Center");
            initConnections();
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}
        // user code end
    }

    /**
     * Sets the model type to be used for this component.
     * 
     * @param newModelType
     *            <code>VAR</code> or <code>VECM</code>
     */
    public void setModelType(ModelTypes newModelType) {
        modelType = newModelType;

        if (modelType == ModelTypes.VAR) {
            resids = VARConstants.u_hat_Def;
            residNames = VARConstants.Nu_Def;
            pyLags = VARConstants.py_Def;
        } else if (modelType == ModelTypes.VECM) {
            resids = VECMConstants.u_hat_Def;
            residNames = VECMConstants.Nu_Def;
            pyLags = VECMConstants.py_Def;
        } else
            throw new IllegalArgumentException(newModelType
                    + " is unknown model type.");

    }
}