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

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import com.jstatcom.component.ResultField;
import com.jstatcom.component.StdMessages;
import com.jstatcom.engine.PCall;
import com.jstatcom.engine.PCallAdapter;
import com.jstatcom.model.JSCSArray;
import com.jstatcom.model.JSCTypeDef;
import com.jstatcom.model.JSCTypes;
import com.jstatcom.model.ModelPanel;
import com.jstatcom.model.Scope;
import com.jstatcom.ts.TSDateRange;
import com.jstatcom.ts.TSSel;

import de.jmulti.proc.MGARCHCall;
import de.jmulti.tools.KernDensPanel;

/**
 * A panel providing access to multivariate GARCH estimation for models with
 * dimension 2 to 4. It relies on the availibility of the <code>arch</code>
 * GAUSS library as well as the GAUSS software. The name of the variable
 * containing the residuals can be changed, the default is <code>u_hat</code>.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public final class MultARCHAnalysisPanel extends ModelPanel {
    private static final Logger log = Logger
            .getLogger(MultARCHAnalysisPanel.class);

    final static JSCTypeDef ivarName = MGARCHCall.MGARCH_IVAR;

    public final static JSCTypeDef resName = MGARCHCall.MGARCH_RES;

    final static JSCTypeDef resStrings = new JSCTypeDef("MGARCH_resStrings",
            JSCTypes.SARRAY, "residual names from MGARCH estimation");

    private JSCTypeDef resDef = new JSCTypeDef("u_hat", JSCTypes.NARRAY);

    private JSCTypeDef dateRange = new JSCTypeDef("DRANGE", JSCTypes.DRANGE);

    private JSCTypeDef resNamesDef = null;

    private TSSel selectPanel = null;

    private JPanel ivjTextOutPanel = null;

    private ResultField ivjResultField = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private JButton ivjExecute = null;

    private JTabbedPane ivjJTabbedPaneARCH = null;

    private MultARCH_diagTests ivjMultARCH_diagTests = null;

    private JPanel ivjMultARCHSpecificationPanel = null;

    private JCheckBox ivjEstimateTCheck = null;

    private KernDensPanel ivjKernelDensity = null;

    class IvjEventHandler implements java.awt.event.ActionListener,
            java.awt.event.ComponentListener {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == MultARCHAnalysisPanel.this.getExecute())
                connEtoC1();
        };

        public void componentHidden(java.awt.event.ComponentEvent e) {
            if (e.getSource() == MultARCHAnalysisPanel.this
                    .getMultARCHSpecificationPanel())
                connEtoC4(e);
        };

        public void componentMoved(java.awt.event.ComponentEvent e) {
        };

        public void componentResized(java.awt.event.ComponentEvent e) {
        };

        public void componentShown(java.awt.event.ComponentEvent e) {
            if (e.getSource() == MultARCHAnalysisPanel.this
                    .getMultARCHSpecificationPanel())
                connEtoC3(e);
        };
    };

    /**
     * ARCHAnalysisPanel constructor comment.
     */
    public MultARCHAnalysisPanel() {
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
        return;
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
     * Execute MGARCH estimation.
     */
    private void execute_ActionEvents() {
        JSCSArray resNames = global().get(resNamesDef).getJSCSArray();
        if (resNames.rows() < 2 || resNames.rows() > 4) {
            StdMessages
                    .infoGeneral("Multivariate GARCH(1,1) estimation only implemented for K = 2, 3, 4.");
            return;
        }

        String[] resS = new String[resNames.rows()];
        for (int i = 0; i < resS.length; i++)
            resS[i] = "xi_" + (i + 1);

        TSDateRange range = global().get(dateRange).getJSCDRange()
                .getTSDateRange();

        // for kenerl density -> global to upper
        local().get(dateRange).getJSCDRange().setVal(range);

        local().get(resStrings)
                .setJSCData(new JSCSArray(resStrings.name, resS));

        PCall job = new MGARCHCall(global().get(resDef).getJSCNArray(),
                global().get(resNamesDef).getJSCSArray(), getEstimateTCheck()
                        .isSelected());
        job.addPCallListener(new PCallAdapter() {
            public void success() {
                getJTabbedPaneARCH().setSelectedIndex(1);
                setDiagnosEnabled(true);
            }
        });

        job.setSymbolTable(local());
        job.setOutHolder(getResultField());
        job.execute();

    }

    /**
     * Return the EstimateTCheck property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getEstimateTCheck() {
        if (ivjEstimateTCheck == null) {
            try {
                ivjEstimateTCheck = new javax.swing.JCheckBox();
                ivjEstimateTCheck.setName("EstimateTCheck");
                ivjEstimateTCheck
                        .setText("Estimate exact t-ratios (computationally very intensive)");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjEstimateTCheck;
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
                ivjJTabbedPaneARCH.setMinimumSize(new java.awt.Dimension(171,
                        209));
                ivjJTabbedPaneARCH.insertTab("Specify Model", null,
                        getMultARCHSpecificationPanel(), null, 0);
                ivjJTabbedPaneARCH.insertTab("Text (save/print)", null,
                        getTextOutPanel(), null, 1);
                ivjJTabbedPaneARCH.insertTab("Diagnostics", null,
                        getMultARCH_diagTests(), null, 2);
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
                ivjKernelDensity
                        .setMinimumSize(new java.awt.Dimension(166, 153));
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
     * Return the MultARCH_diagTests property value.
     * 
     * @return program.frame.arch.MultARCH_diagTests
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private MultARCH_diagTests getMultARCH_diagTests() {
        if (ivjMultARCH_diagTests == null) {
            try {
                ivjMultARCH_diagTests = new de.jmulti.arch.MultARCH_diagTests();
                ivjMultARCH_diagTests.setName("MultARCH_diagTests");
                ivjMultARCH_diagTests.setMinimumSize(new java.awt.Dimension(
                        149, 183));
                // user code begin {1}
                ivjMultARCH_diagTests.setDateRangeDef(dateRange);
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjMultARCH_diagTests;
    }

    /**
     * Return the ARCHSpecificationPanel property value.
     * 
     * @return javax.swing.JPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JPanel getMultARCHSpecificationPanel() {
        if (ivjMultARCHSpecificationPanel == null) {
            try {
                ivjMultARCHSpecificationPanel = new javax.swing.JPanel();
                ivjMultARCHSpecificationPanel
                        .setName("MultARCHSpecificationPanel");
                ivjMultARCHSpecificationPanel.setBorder(new BevelBorder(
                        BevelBorder.LOWERED));
                ivjMultARCHSpecificationPanel
                        .setLayout(new java.awt.GridBagLayout());
                ivjMultARCHSpecificationPanel
                        .setMinimumSize(new java.awt.Dimension(146, 107));

                java.awt.GridBagConstraints constraintsExecute = new java.awt.GridBagConstraints();
                constraintsExecute.gridx = 0;
                constraintsExecute.gridy = 1;
                constraintsExecute.gridwidth = 2;
                constraintsExecute.anchor = java.awt.GridBagConstraints.NORTH;
                constraintsExecute.weighty = 1.0;
                constraintsExecute.insets = new java.awt.Insets(40, 0, 0, 0);
                getMultARCHSpecificationPanel().add(getExecute(),
                        constraintsExecute);

                java.awt.GridBagConstraints constraintsEstimateTCheck = new java.awt.GridBagConstraints();
                constraintsEstimateTCheck.gridx = 1;
                constraintsEstimateTCheck.gridy = 0;
                constraintsEstimateTCheck.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsEstimateTCheck.weightx = 1.0;
                constraintsEstimateTCheck.weighty = 1.0;
                constraintsEstimateTCheck.insets = new java.awt.Insets(20, 10,
                        0, 0);
                getMultARCHSpecificationPanel().add(getEstimateTCheck(),
                        constraintsEstimateTCheck);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjMultARCHSpecificationPanel;
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
        getMultARCHSpecificationPanel().addComponentListener(ivjEventHandler);
    }

    /**
     * Initialize the class.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initialize() {
        try {
            // user code begin {1}
            // user code end
            setName("MultARCHAnalysisPanel");
            setLayout(new javax.swing.BoxLayout(this,
                    javax.swing.BoxLayout.X_AXIS));
            setSize(578, 349);
            setMinimumSize(new java.awt.Dimension(271, 209));
            add(getJTabbedPaneARCH(), getJTabbedPaneARCH().getName());
            initConnections();
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}
        TitledBorder title = new TitledBorder(new BevelBorder(
                BevelBorder.LOWERED), "Multivariate GARCH(1,1) Analysis",
                TitledBorder.RIGHT, TitledBorder.TOP);
        this.setBorder(title);
        // user code end
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

        if (enabled)
            getMultARCH_diagTests().setOrigDataName(getNameOfResiduals());

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
        ivjMultARCH_diagTests.setDateRangeDef(dateRange);
    }
}