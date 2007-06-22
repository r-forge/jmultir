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

package de.jmulti.arch;

import java.awt.BorderLayout;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import com.jstatcom.component.ResultField;
import com.jstatcom.component.StdMessages;
import com.jstatcom.engine.PCall;
import com.jstatcom.engine.PCallAdapter;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.JSCSArray;
import com.jstatcom.model.JSCTypeDef;
import com.jstatcom.model.JSCTypes;
import com.jstatcom.model.ModelPanel;
import com.jstatcom.model.Scope;
import com.jstatcom.ts.TSDateRange;
import com.jstatcom.ts.TSSel;

import de.jmulti.proc.GARCHCall;
import de.jmulti.tools.ModelTypes;

/**
 * A panel providing access to various methods for estimating ARCH, GARCH and
 * TGARCH models with Gaussian, GED or t-distributed errors. It relies on the
 * availibility of the <code>arch</code> GAUSS library as well as the GAUSS
 * software. The name of the variable containing the residuals can be changed,
 * the default is <code>u_hat</code>.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
public final class ARCHAnalysisPanel extends ModelPanel {
    private static final Logger log = Logger.getLogger(ARCHAnalysisPanel.class);

    final static JSCTypeDef ivarName = GARCHCall.GARCH_IVAR;

    final static JSCTypeDef resName = GARCHCall.GARCH_RES;

    final static JSCTypeDef paramVector = GARCHCall.GARCH_PARAM;

    final static JSCTypeDef modelName = new JSCTypeDef("UNIARCH_model",
            JSCTypes.STRING, "arch model type");

    final static JSCTypeDef resStrings = new JSCTypeDef("UNIARCH_resStrings",
            JSCTypes.SARRAY, "residual names from ARCH estimation");

    final static JSCTypeDef pName = new JSCTypeDef("UNIARCH_p", JSCTypes.INT,
            "p lags for GARCH model");

    final static JSCTypeDef qName = new JSCTypeDef("UNIARCH_q", JSCTypes.INT,
            "q lags for GARCH model");

    private JSCTypeDef resDef = new JSCTypeDef("u_hat", JSCTypes.NARRAY);

    private JSCTypeDef dateRange = new JSCTypeDef("DRANGE", JSCTypes.DRANGE);

    private JSCTypeDef resNamesDef = null;

    private final ComboBoxModel archQLagsComboModel = new DefaultComboBoxModel(
            new Object[] { "1", "2", "3", "4", "5" });

    private final ComboBoxModel qLagsComboModel = new DefaultComboBoxModel(
            new Object[] { "1", "2" });

    private TSSel selectPanel = null;

    private JPanel ivjARCHSpecificationPanel = null;

    private JPanel ivjTextOutPanel = null;

    private ResultField ivjResultField = null;

    private JComboBox ivjErrorDistCombo = null;

    private JLabel ivjJLabel1 = null;

    private JLabel ivjJLabel2 = null;

    private JLabel ivjJLabel3 = null;

    private JLabel ivjJLabel4 = null;

    private JComboBox ivjModelCombo = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private JButton ivjExecute = null;

    private JComboBox ivjPLagCombo = null;

    private JComboBox ivjQLagCombo = null;

    private ARCH_diagTests ivjARCH_diagTests = null;

    private JTabbedPane ivjJTabbedPaneARCH = null;

    private de.jmulti.tools.KernDensPanel ivjKernelDensity = null;

    class IvjEventHandler implements java.awt.event.ActionListener,
            java.awt.event.ComponentListener {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == ARCHAnalysisPanel.this.getExecute())
                connEtoC1();
            if (e.getSource() == ARCHAnalysisPanel.this.getModelCombo())
                connEtoC2();
        };

        public void componentHidden(java.awt.event.ComponentEvent e) {
            if (e.getSource() == ARCHAnalysisPanel.this
                    .getARCHSpecificationPanel())
                connEtoC4(e);
        };

        public void componentMoved(java.awt.event.ComponentEvent e) {
        };

        public void componentResized(java.awt.event.ComponentEvent e) {
        };

        public void componentShown(java.awt.event.ComponentEvent e) {
            if (e.getSource() == ARCHAnalysisPanel.this
                    .getARCHSpecificationPanel())
                connEtoC3(e);
        };
    };

    /**
     * ARCHAnalysisPanel constructor comment.
     */
    public ARCHAnalysisPanel() {
        super();
        initialize();
    }

    /**
     * Disables the selectPanel if component is hidden and a selectPanel is
     * specified.
     */
    private void aRCHSpecificationPanel_ComponentHidden() {
        if (selectPanel != null)
            selectPanel.setVisible(false);

    }

    /**
     * Shows the selectPanel if component is shown and a selectPanel is
     * specified.
     */
    private void aRCHSpecificationPanel_ComponentShown() {
        if (selectPanel != null)
            selectPanel.setVisible(true);

        return;
    }

    /**
     * connEtoC1: (Execute.action. -->
     * ARCHAnalysisPanel.execute_ActionEvents()V)
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
     * connEtoC2: (ModelCombo.action. -->
     * ARCHAnalysisPanel.modelCombo_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC2() {
        try {
            // user code begin {1}
            // user code end
            this.modelCombo_ActionEvents();
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
     * (ARCHSpecificationPanel.component.componentShown(java.awt.event.ComponentEvent)
     * --> ARCHAnalysisPanel.aRCHSpecificationPanel_ComponentShown()V)
     * 
     * @param arg1
     *            java.awt.event.ComponentEvent
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC3(java.awt.event.ComponentEvent arg1) {
        try {
            // user code begin {1}
            // user code end
            this.aRCHSpecificationPanel_ComponentShown();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC4:
     * (ARCHSpecificationPanel.component.componentHidden(java.awt.event.ComponentEvent)
     * --> ARCHAnalysisPanel.aRCHSpecificationPanel_ComponentHidden()V)
     * 
     * @param arg1
     *            java.awt.event.ComponentEvent
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC4(java.awt.event.ComponentEvent arg1) {
        try {
            // user code begin {1}
            // user code end
            this.aRCHSpecificationPanel_ComponentHidden();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * Execute ARCH/GARCH/TGARCH estimation according to selection.
     */
    private void execute_ActionEvents() {
        JSCSArray res = global().get(resNamesDef).getJSCSArray();
        if (res.rows() != 1) {
            StdMessages
                    .errorSpecification("Please select one endogenous variable.");
            return;
        }

        TSDateRange range = global().get(dateRange).getJSCDRange()
                .getTSDateRange();

        int p = getPLagCombo().getSelectedIndex() + 1;
        int q = getQLagCombo().getSelectedIndex() + 1;

        ModelTypes type = null;
        // ARCH
        if (getModelCombo().getSelectedIndex() == 0) {
            type = ModelTypes.ARCH;
            p = 0;
        } else
        // GARCH
        if (getModelCombo().getSelectedIndex() == 1)
            type = ModelTypes.GARCH;
        else
        // TGARCH
        if (getModelCombo().getSelectedIndex() == 2)
            type = ModelTypes.TGARCH;
        else
            // Should not happen
            throw new RuntimeException("Invalid ARCH Model selection.");

        // for kenerl density -> global to upper
        local().get(dateRange).getJSCDRange().setVal(range);

        local().get(resStrings)
                .setJSCData(new JSCSArray(resStrings.name, "xi"));
        local().get(modelName).getJSCString().setVal(type + "");
        local().get(qName).getJSCInt().setVal(q);
        local().get(pName).getJSCInt().setVal(p);

        PCall job = new GARCHCall(global().get(resDef).getJSCNArray(), global()
                .get(resNamesDef).getJSCString(), type, q, p,
                getErrorDistCombo().getSelectedIndex());
        job.addPCallListener(new PCallAdapter() {
            public void success() {

                setDiagnosEnabled(true);
                getJTabbedPaneARCH().setSelectedIndex(1);
            }
        });

        job.setSymbolTable(local());
        job.setOutHolder(getResultField());
        job.execute();

    }

    /**
     * Return the ARCH_diagTests property value.
     * 
     * @return program.frame.arch.ARCH_diagTests
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private ARCH_diagTests getARCH_diagTests() {
        if (ivjARCH_diagTests == null) {
            try {
                ivjARCH_diagTests = new de.jmulti.arch.ARCH_diagTests();
                ivjARCH_diagTests.setName("ARCH_diagTests");
                // user code begin {1}
                ivjARCH_diagTests.setDateRangeDef(dateRange);
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjARCH_diagTests;
    }

    /**
     * Return the ARCHSpecificationPanel property value.
     * 
     * @return javax.swing.JPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JPanel getARCHSpecificationPanel() {
        if (ivjARCHSpecificationPanel == null) {
            try {
                ivjARCHSpecificationPanel = new javax.swing.JPanel();
                ivjARCHSpecificationPanel.setName("ARCHSpecificationPanel");
                ivjARCHSpecificationPanel.setBorder(new BevelBorder(
                        BevelBorder.LOWERED));
                ivjARCHSpecificationPanel
                        .setLayout(new java.awt.GridBagLayout());
                ivjARCHSpecificationPanel
                        .setMinimumSize(new java.awt.Dimension(110, 237));

                java.awt.GridBagConstraints constraintsJLabel1 = new java.awt.GridBagConstraints();
                constraintsJLabel1.gridx = 0;
                constraintsJLabel1.gridy = 0;
                constraintsJLabel1.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel1.insets = new java.awt.Insets(20, 20, 0, 0);
                getARCHSpecificationPanel().add(getJLabel1(),
                        constraintsJLabel1);

                java.awt.GridBagConstraints constraintsJLabel2 = new java.awt.GridBagConstraints();
                constraintsJLabel2.gridx = 0;
                constraintsJLabel2.gridy = 1;
                constraintsJLabel2.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel2.insets = new java.awt.Insets(20, 20, 0, 0);
                getARCHSpecificationPanel().add(getJLabel2(),
                        constraintsJLabel2);

                java.awt.GridBagConstraints constraintsJLabel3 = new java.awt.GridBagConstraints();
                constraintsJLabel3.gridx = 0;
                constraintsJLabel3.gridy = 3;
                constraintsJLabel3.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel3.insets = new java.awt.Insets(20, 20, 0, 0);
                getARCHSpecificationPanel().add(getJLabel3(),
                        constraintsJLabel3);

                java.awt.GridBagConstraints constraintsJLabel4 = new java.awt.GridBagConstraints();
                constraintsJLabel4.gridx = 0;
                constraintsJLabel4.gridy = 2;
                constraintsJLabel4.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel4.insets = new java.awt.Insets(20, 20, 0, 0);
                getARCHSpecificationPanel().add(getJLabel4(),
                        constraintsJLabel4);

                java.awt.GridBagConstraints constraintsModelCombo = new java.awt.GridBagConstraints();
                constraintsModelCombo.gridx = 1;
                constraintsModelCombo.gridy = 0;
                constraintsModelCombo.anchor = java.awt.GridBagConstraints.WEST;             
                constraintsModelCombo.fill = java.awt.GridBagConstraints.NONE;
                constraintsModelCombo.weightx = 1.0;
                constraintsModelCombo.insets = new java.awt.Insets(20, 10, 0,
                        10);
                getARCHSpecificationPanel().add(getModelCombo(),
                        constraintsModelCombo);

                java.awt.GridBagConstraints constraintsErrorDistCombo = new java.awt.GridBagConstraints();
                constraintsErrorDistCombo.gridx = 1;
                constraintsErrorDistCombo.gridy = 1;
                constraintsErrorDistCombo.anchor = java.awt.GridBagConstraints.WEST;
                constraintsErrorDistCombo.fill = java.awt.GridBagConstraints.NONE;
                constraintsErrorDistCombo.weightx = 1.0;
                constraintsErrorDistCombo.insets = new java.awt.Insets(20, 10,
                        0, 10);
                getARCHSpecificationPanel().add(getErrorDistCombo(),
                        constraintsErrorDistCombo);

                java.awt.GridBagConstraints constraintsPLagCombo = new java.awt.GridBagConstraints();
                constraintsPLagCombo.gridx = 1;
                constraintsPLagCombo.gridy = 3;
                constraintsPLagCombo.anchor = java.awt.GridBagConstraints.WEST;
                constraintsPLagCombo.insets = new java.awt.Insets(20, 10, 0, 20);
                getARCHSpecificationPanel().add(getPLagCombo(),
                        constraintsPLagCombo);

                java.awt.GridBagConstraints constraintsQLagCombo = new java.awt.GridBagConstraints();
                constraintsQLagCombo.gridx = 1;
                constraintsQLagCombo.gridy = 2;
                constraintsQLagCombo.anchor = java.awt.GridBagConstraints.WEST;
                constraintsQLagCombo.insets = new java.awt.Insets(20, 10, 0, 20);
                getARCHSpecificationPanel().add(getQLagCombo(),
                        constraintsQLagCombo);

                java.awt.GridBagConstraints constraintsExecute = new java.awt.GridBagConstraints();
                constraintsExecute.gridx = 0;
                constraintsExecute.gridy = 4;
                constraintsExecute.gridwidth = 2;
                constraintsExecute.anchor = java.awt.GridBagConstraints.NORTH;
                constraintsExecute.weighty = 1.0;
                constraintsExecute.insets = new java.awt.Insets(40, 0, 0, 0);
                getARCHSpecificationPanel().add(getExecute(),
                        constraintsExecute);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjARCHSpecificationPanel;
    }

    /**
     * Return the ErrorDistCombo property value.
     * 
     * @return javax.swing.JComboBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JComboBox getErrorDistCombo() {
        if (ivjErrorDistCombo == null) {
            try {
                ivjErrorDistCombo = new javax.swing.JComboBox();
                ivjErrorDistCombo.setName("ErrorDistCombo");
                ivjErrorDistCombo.setPreferredSize(new java.awt.Dimension(250,
                        23));
                ivjErrorDistCombo
                        .setMinimumSize(new java.awt.Dimension(50, 23));
                // user code begin {1}
                ivjErrorDistCombo.addItem("Gaussian errors");
                ivjErrorDistCombo.addItem("GED distributed errors");
                ivjErrorDistCombo.addItem("t-distributed errors");
                ivjErrorDistCombo.setSelectedIndex(0);
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjErrorDistCombo;
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
                ivjExecute.setPreferredSize(new java.awt.Dimension(130, 25));
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
                ivjJLabel1.setPreferredSize(new java.awt.Dimension(150, 23));
                ivjJLabel1.setText("Select model");
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
                ivjJLabel2.setPreferredSize(new java.awt.Dimension(150, 23));
                ivjJLabel2.setText("Select error distribution");
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
                ivjJLabel3.setPreferredSize(new java.awt.Dimension(150, 23));
                ivjJLabel3.setText("Lags in variances");
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
                ivjJLabel4.setPreferredSize(new java.awt.Dimension(150, 23));
                ivjJLabel4.setText("ARCH order");
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
     * Return the JTabbedPane1 property value.
     * 
     * @return javax.swing.JTabbedPane
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JTabbedPane getJTabbedPaneARCH() {
        if (ivjJTabbedPaneARCH == null) {
            try {
                ivjJTabbedPaneARCH = new javax.swing.JTabbedPane();
                ivjJTabbedPaneARCH.setName("JTabbedPaneARCH");
                ivjJTabbedPaneARCH.setMinimumSize(new java.awt.Dimension(200,
                        263));
                ivjJTabbedPaneARCH.insertTab("Specify Model", null,
                        getARCHSpecificationPanel(), null, 0);
                ivjJTabbedPaneARCH.insertTab("Text (save/print)", null,
                        getTextOutPanel(), null, 1);
                ivjJTabbedPaneARCH.insertTab("Diagnostics", null,
                        getARCH_diagTests(), null, 2);
                ivjJTabbedPaneARCH.setEnabledAt(2, false);
                ivjJTabbedPaneARCH.insertTab("Kernel Density", null,
                        getKernelDensity(), null, 3);
                ivjJTabbedPaneARCH.setEnabledAt(3, false);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJTabbedPaneARCH;
    }

    /**
     * Return the KernelDensity property value.
     * 
     * @return de.jmulti.tools.KernDensPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private de.jmulti.tools.KernDensPanel getKernelDensity() {
        if (ivjKernelDensity == null) {
            try {
                ivjKernelDensity = new de.jmulti.tools.KernDensPanel();
                ivjKernelDensity.setName("KernelDensity");
                // user code begin {1}
                ivjKernelDensity.setResids(resName.name);
                ivjKernelDensity.setNameResids(resStrings.name);
                ivjKernelDensity.setDRange(dateRange.name);
                ivjKernelDensity.setScope(Scope.UPPER);
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjKernelDensity;
    }

    /**
     * Return the ModelCombo property value.
     * 
     * @return javax.swing.JComboBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JComboBox getModelCombo() {
        if (ivjModelCombo == null) {
            try {
                ivjModelCombo = new javax.swing.JComboBox();
                ivjModelCombo.setName("ModelCombo");
                ivjModelCombo.setPreferredSize(new java.awt.Dimension(250, 23));
                ivjModelCombo.setMinimumSize(new java.awt.Dimension(50, 23));
                // user code begin {1}
                ivjModelCombo.addItem("ARCH model");
                ivjModelCombo.addItem("GARCH model");
                ivjModelCombo.addItem("TGARCH model");
                ivjModelCombo.setSelectedIndex(0);
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjModelCombo;
    }

    /**
     * Gets the nameOfResiduals property (String) value.
     * 
     * @return The nameOfResiduals property value.
     * @see #setNameOfResiduals
     */
    public String getNameOfResiduals() {
        if (resDef == null)
            return null;

        return resDef.name;
    }

    /**
     * Return the PLagCombo property value.
     * 
     * @return javax.swing.JComboBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JComboBox getPLagCombo() {
        if (ivjPLagCombo == null) {
            try {
                ivjPLagCombo = new javax.swing.JComboBox();
                ivjPLagCombo.setName("PLagCombo");
                ivjPLagCombo.setPreferredSize(new java.awt.Dimension(60, 23));
                ivjPLagCombo.setEnabled(false);
                // user code begin {1}
                ivjPLagCombo.addItem("1");
                ivjPLagCombo.addItem("2");
                ivjPLagCombo.setSelectedIndex(0);
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjPLagCombo;
    }

    /**
     * Return the QLagCombo property value.
     * 
     * @return javax.swing.JComboBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JComboBox getQLagCombo() {
        if (ivjQLagCombo == null) {
            try {
                ivjQLagCombo = new javax.swing.JComboBox();
                ivjQLagCombo.setName("QLagCombo");
                ivjQLagCombo.setPreferredSize(new java.awt.Dimension(60, 23));
                // user code begin {1}
                ivjQLagCombo.setModel(archQLagsComboModel);
                ivjQLagCombo.setSelectedIndex(0);
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjQLagCombo;
    }

    /**
     * Gets the residualNames property (String) value.
     * 
     * @return The residualNames property value.
     * @see #setResidualNames
     */
    public String getResidualNames() {
        if (resNamesDef == null)
            return null;

        return resNamesDef.name;
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
     * Gets the dateRange property (String) value.
     * 
     * @return The dateRange property value.
     * @see #setDateRangeName
     */
    public String getDateRangeName() {
        if (dateRange == null)
            return null;

        return dateRange.name;
    }

    /**
     * Return the TextOutPanel property value.
     * 
     * @return javax.swing.JPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JPanel getTextOutPanel() {
        if (ivjTextOutPanel == null) {
            try {
                ivjTextOutPanel = new javax.swing.JPanel();
                ivjTextOutPanel.setName("TextOutPanel");
                ivjTextOutPanel.setLayout(new BorderLayout());
                getTextOutPanel().add(getResultField(), BorderLayout.CENTER);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjTextOutPanel;
    }

    /**
     * Called whenever the part throws an exception.
     * 
     * @param exception
     *            Exception
     */
    private void handleException(Throwable exception) {

        
        log.error("Unhandled Exception.", exception);
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
        getModelCombo().addActionListener(ivjEventHandler);
        getARCHSpecificationPanel().addComponentListener(ivjEventHandler);
    }

    /**
     * Initialize the class.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initialize() {
        try {
            // user code begin {1}
            // user code end
            setName("ARCHAnalysisPanel");
            setLayout(new javax.swing.BoxLayout(this,
                    javax.swing.BoxLayout.X_AXIS));
            setSize(578, 349);
            add(getJTabbedPaneARCH(), getJTabbedPaneARCH().getName());
            initConnections();
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}
        TitledBorder title = new TitledBorder(new BevelBorder(
                BevelBorder.LOWERED), "Univariate ARCH Analysis",
                TitledBorder.RIGHT, TitledBorder.TOP);
        this.setBorder(title);

        // user code end
    }

    /**
     * Enable/Disable PLagCombo according to selection.
     */
    private void modelCombo_ActionEvents() {
        // Index 0 is ARCH model, which does not have p parameter
        getPLagCombo().setEnabled(getModelCombo().getSelectedIndex() != 0);
        if (getModelCombo().getSelectedIndex() == 0)
            getQLagCombo().setModel(archQLagsComboModel);
        else
            getQLagCombo().setModel(qLagsComboModel);
        return;
    }

    /**
     * Enables/Disables the diagnostic panel.
     * 
     * @param enabled
     *            boolean
     */
    void setDiagnosEnabled(boolean enabled) {
        getJTabbedPaneARCH().setEnabledAt(2, enabled);
        getJTabbedPaneARCH().setEnabledAt(3, enabled);
    }

    /**
     * Sets the name of the data object containing actual the series under
     * analysis.
     * 
     * @param nameOfResiduals
     *            The new value for the property.
     * @see #getNameOfResiduals
     */
    public void setNameOfResiduals(String nameOfResiduals) {
        if (nameOfResiduals == null)
            resDef = null;
        else
            resDef = new JSCTypeDef(nameOfResiduals, JSCTypes.NARRAY);

    }

    /**
     * Sets the name of the data object containing the names of the series under
     * analysis.
     * 
     * @param residualNames
     *            The new value for the property.
     * @see #getResidualNames
     */
    public void setResidualNames(String residualNames) {
        if (residualNames == null)
            resNamesDef = null;
        else
            resNamesDef = new JSCTypeDef(residualNames, JSCTypes.SARRAY);
    }

    /**
     * Sets the instance of the select panel for disabling/enabling it from
     * within this component.
     * 
     * @param newSelectPanel
     *            the time series selector
     */
    void setSelectPanel(TSSel newSelectPanel) {
        selectPanel = newSelectPanel;
    }

    /**
     * Sets the name of the data object containing the date range of the series
     * under analysis.
     * 
     * @param rangeName
     *            The new value for the property.
     */
    public void setDateRange(String rangeName) {
        if (rangeName == null)
            dateRange = null;
        else
            dateRange = new JSCTypeDef(rangeName, JSCTypes.DRANGE);
        ivjKernelDensity.setDRange(rangeName);
        ivjARCH_diagTests.setDateRangeDef(dateRange);

    }

    /**
     * Sets the model selection combos from an index array
     * 
     * @param sel
     */
    void setUniModelSel(JSCNArray sel) {
        if (sel == null || sel.isEmpty() || sel.rows() < 4)
            return;
        getModelCombo().setSelectedIndex(sel.intAt(0, 0));
        getErrorDistCombo().setSelectedIndex(sel.intAt(1, 0));
        getPLagCombo().setSelectedIndex(sel.intAt(2, 0));
        getQLagCombo().setSelectedIndex(sel.intAt(3, 0));
    }

    /**
     * Gets the model selection as an index array.
     * 
     * @return
     */
    JSCNArray getUniModelSel() {

        return new JSCNArray("ModelSelection", new int[] {
                getModelCombo().getSelectedIndex(),
                getErrorDistCombo().getSelectedIndex(),
                getPLagCombo().getSelectedIndex(),
                getQLagCombo().getSelectedIndex() });
    }
}