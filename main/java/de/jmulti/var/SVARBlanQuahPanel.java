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

package de.jmulti.var;

import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.BevelBorder;

import org.apache.log4j.Logger;

import com.jstatcom.component.NumSelector;
import com.jstatcom.component.ResultField;
import com.jstatcom.engine.PCall;
import com.jstatcom.engine.PCallAdapter;
import com.jstatcom.model.JSCConstants;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.ModelPanel;
import com.jstatcom.model.Scope;
import com.jstatcom.table.JSCCellRendererTypes;
import com.jstatcom.table.JSCDataTableScrollPane;
import com.jstatcom.table.JSCNArrayTable;
import com.jstatcom.util.UMatrix;

import de.jmulti.proc.SVARBootStdBlanQuahCall;
import de.jmulti.proc.SVAREstBlanQuahCall;

/**
 * Panel for performing the SVAR estimation for a Blanchard-Quah model. It also
 * provides an interface to bootstrap the std. errors. This also computed the
 * confidence intervals.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
public final class SVARBlanQuahPanel extends ModelPanel {
    private static final Logger log = Logger.getLogger(SVARBlanQuahPanel.class);

    private static final String OUT_FILE_EST = "svar_bq_model_est.out";

    private JTabbedPane ivjJTabbedPane1 = null;

    private ResultField ivjResultField = null;

    private JPanel ivjSpecTab = null;

    private JButton ivjEstimateButton = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private NumSelector ivjBootRepSelector = null;

    private JButton ivjBootstrapButton = null;

    private JLabel ivjJLabel1 = null;

    private JSCNArrayTable ivjDataTable = null;

    private JSCDataTableScrollPane ivjDataTableScrollPane = null;

    private JLabel ivjJLabel2 = null;

    private JCheckBox ivjSeedCheck = null;

    private NumSelector ivjSeedSelector = null;

    class IvjEventHandler implements java.awt.event.ActionListener,
            java.awt.event.ItemListener {

        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == SVARBlanQuahPanel.this.getEstimateButton())
                connEtoC1();
            if (e.getSource() == SVARBlanQuahPanel.this.getBootstrapButton())
                connEtoC2();
        };

        public void itemStateChanged(java.awt.event.ItemEvent e) {
            if (e.getSource() == SVARBlanQuahPanel.this.getSeedCheck())
                connPtoP1SetTarget();
        };
    };

    /**
     * SVARBlanQuaPanel constructor comment.
     */
    public SVARBlanQuahPanel() {
        super();
        initialize();
    }

    /**
     * Estimate and bootstrap std. errors.
     */
    private void bootstrapButton_ActionEvents() {

        String outFile = JSCConstants.getSystemTemp() + File.separator
                + OUT_FILE_EST;

        getResultField().clear();

        PCall job = new SVARBootStdBlanQuahCall(global(), getBootRepSelector()
                .getIntNumber(), getSeedSelector().getIntNumber(),
                getSeedCheck().isSelected(), outFile);

        job.addPCallListener(new PCallAdapter() {

            public void success() {
                // set result tab
                getJTabbedPane1().setSelectedIndex(1);
            }
        });

        job.setOutHolder(getResultField());
        job.execute();

    }

    /**
     * connEtoC1: (EstimateButton.action. -->
     * SVARBlanQuaPanel.estimateButton_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC1() {
        try {
            // user code begin {1}
            // user code end
            this.estimateButton_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC2: (BootstrapButton.action. -->
     * SVARBlanQuaPanel.bootstrapButton_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC2() {
        try {
            // user code begin {1}
            // user code end
            this.bootstrapButton_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connPtoP1SetTarget: (SeedCheck.selected <-->NumberSelector.enabled)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connPtoP1SetTarget() {
        /* Set the target from the source */
        try {
            getSeedSelector().setEnabled(getSeedCheck().isSelected());
            // user code begin {1}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * Estimate Model.
     */
    private void estimateButton_ActionEvents() {
        String outFile = JSCConstants.getSystemTemp() + File.separator
                + OUT_FILE_EST;
        getResultField().clear();

        PCall job = new SVAREstBlanQuahCall(global().get(VARConstants.Ay_Def)
                .getJSCNArray(), global().get(VARConstants.cv_u_hat_Def)
                .getJSCNArray(), outFile);

        job.addPCallListener(new PCallAdapter() {

            public void success() {
                // set result tab
                getJTabbedPane1().setSelectedIndex(1);
            }
        });
        job.setSymbolTable(global());
        job.setOutHolder(getResultField());
        job.execute();

    }

    private NumSelector getBootRepSelector() {
        if (ivjBootRepSelector == null) {
            try {
                ivjBootRepSelector = new NumSelector();
                ivjBootRepSelector.setName("BootRepSelector");
                ivjBootRepSelector.setPreferredSize(new java.awt.Dimension(100,
                        20));
                ivjBootRepSelector.setNumber(100.0);
                ivjBootRepSelector.setMinimumSize(new java.awt.Dimension(100,
                        20));
                ivjBootRepSelector.setRangeExpr("[50,Infinity)");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjBootRepSelector;
    }

    /**
     * Return the BootstrapButton property value.
     * 
     * @return javax.swing.JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JButton getBootstrapButton() {
        if (ivjBootstrapButton == null) {
            try {
                ivjBootstrapButton = new javax.swing.JButton();
                ivjBootstrapButton.setName("BootstrapButton");
                ivjBootstrapButton
                        .setToolTipText("Estimates with bootrapping std. errors.");
                ivjBootstrapButton.setText("Estimate with Boot. Std. Errors");
                ivjBootstrapButton.setPreferredSize(new java.awt.Dimension(200,
                        25));
                ivjBootstrapButton.setMinimumSize(new java.awt.Dimension(180,
                        25));
                ivjBootstrapButton.setMargin(new java.awt.Insets(2, 5, 2, 5));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjBootstrapButton;
    }

    private JSCNArrayTable getDataTable() {
        if (ivjDataTable == null) {
            try {
                ivjDataTable = new JSCNArrayTable();
                ivjDataTable.setName("DataTable");
                getDataTableScrollPane().setColumnHeaderView(
                        ivjDataTable.getTableHeader());
                ivjDataTable.setSymbolScope(Scope.LOCAL);
                ivjDataTable
                        .setCellRenderer(JSCCellRendererTypes.SUBSET_01M1NEGINF);
                ivjDataTable.setTablePopup(null);
                ivjDataTable.setBounds(0, 0, 147, 107);
                ivjDataTable.setEditable(false);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjDataTable;
    }

    private JSCDataTableScrollPane getDataTableScrollPane() {
        if (ivjDataTableScrollPane == null) {
            try {
                ivjDataTableScrollPane = new JSCDataTableScrollPane();
                ivjDataTableScrollPane.setName("DataTableScrollPane");
                ivjDataTableScrollPane.setColumnHeaderShowing(true);
                ivjDataTableScrollPane.setMinimumVisibleColumns(5);
                ivjDataTableScrollPane.setMinimumVisibleRows(5);
                getDataTableScrollPane().setViewportView(getDataTable());
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjDataTableScrollPane;
    }

    /**
     * Return the EstimateButton property value.
     * 
     * @return javax.swing.JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JButton getEstimateButton() {
        if (ivjEstimateButton == null) {
            try {
                ivjEstimateButton = new javax.swing.JButton();
                ivjEstimateButton.setName("EstimateButton");
                ivjEstimateButton
                        .setToolTipText("Estimates without bootrapping std. errors.");
                ivjEstimateButton.setPreferredSize(new java.awt.Dimension(200,
                        25));
                ivjEstimateButton.setText("Point Estimates Only");
                ivjEstimateButton
                        .setMinimumSize(new java.awt.Dimension(180, 25));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjEstimateButton;
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
                ivjJLabel1.setPreferredSize(new java.awt.Dimension(245, 20));
                ivjJLabel1
                        .setText("Matrix of long run restrictions (not editable)");
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
                ivjJLabel2.setPreferredSize(new java.awt.Dimension(190, 20));
                ivjJLabel2.setText("Number of bootstrap replications");
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
     * Return the JTabbedPane1 property value.
     * 
     * @return javax.swing.JTabbedPane
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JTabbedPane getJTabbedPane1() {
        if (ivjJTabbedPane1 == null) {
            try {
                ivjJTabbedPane1 = new javax.swing.JTabbedPane();
                ivjJTabbedPane1.setName("JTabbedPane1");
                ivjJTabbedPane1.insertTab("Estimate Blanchard-Quah Model",
                        null, getSpecTab(), null, 0);
                ivjJTabbedPane1.insertTab("Output (save/print)", null,
                        getResultField(), null, 1);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJTabbedPane1;
    }

    /**
     * Return the ResultField property value.
     * 
     * @return com.jstatcom.component.ResultField
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
     * Return the SeedCheck property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getSeedCheck() {
        if (ivjSeedCheck == null) {
            try {
                ivjSeedCheck = new javax.swing.JCheckBox();
                ivjSeedCheck.setName("SeedCheck");
                ivjSeedCheck.setText("Use this seed (0 < s < 2,147,483,647) ");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSeedCheck;
    }

    private NumSelector getSeedSelector() {
        if (ivjSeedSelector == null) {
            try {
                ivjSeedSelector = new NumSelector();
                ivjSeedSelector.setName("SeedSelector");
                ivjSeedSelector.setNumber(1.0);
                ivjSeedSelector.setRangeExpr("(0,2.147483647E9)");
                ivjSeedSelector
                        .setPreferredSize(new java.awt.Dimension(100, 20));
                ivjSeedSelector.setMinimumSize(new java.awt.Dimension(100, 20));
                ivjSeedSelector.setEnabled(false);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSeedSelector;
    }

    /**
     * Return the SpecTab property value.
     * 
     * @return javax.swing.JPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JPanel getSpecTab() {
        if (ivjSpecTab == null) {
            try {
                ivjSpecTab = new javax.swing.JPanel();
                ivjSpecTab.setName("SpecTab");
                ivjSpecTab.setBorder(new BevelBorder(BevelBorder.LOWERED));
                ivjSpecTab.setLayout(new java.awt.GridBagLayout());

                java.awt.GridBagConstraints constraintsEstimateButton = new java.awt.GridBagConstraints();
                constraintsEstimateButton.gridx = 2;
                constraintsEstimateButton.gridy = 2;
                constraintsEstimateButton.anchor = java.awt.GridBagConstraints.EAST;
                constraintsEstimateButton.insets = new java.awt.Insets(5, 10,
                        5, 10);
                getSpecTab()
                        .add(getEstimateButton(), constraintsEstimateButton);

                java.awt.GridBagConstraints constraintsBootstrapButton = new java.awt.GridBagConstraints();
                constraintsBootstrapButton.gridx = 2;
                constraintsBootstrapButton.gridy = 3;
                constraintsBootstrapButton.gridheight = 2;
                constraintsBootstrapButton.anchor = java.awt.GridBagConstraints.EAST;
                constraintsBootstrapButton.insets = new java.awt.Insets(10, 10,
                        10, 10);
                getSpecTab().add(getBootstrapButton(),
                        constraintsBootstrapButton);

                java.awt.GridBagConstraints constraintsBootRepSelector = new java.awt.GridBagConstraints();
                constraintsBootRepSelector.gridx = 1;
                constraintsBootRepSelector.gridy = 3;
                constraintsBootRepSelector.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsBootRepSelector.insets = new java.awt.Insets(10, 5,
                        0, 0);
                getSpecTab().add(getBootRepSelector(),
                        constraintsBootRepSelector);

                java.awt.GridBagConstraints constraintsJLabel1 = new java.awt.GridBagConstraints();
                constraintsJLabel1.gridx = 0;
                constraintsJLabel1.gridy = 0;
                constraintsJLabel1.gridwidth = 2;
                constraintsJLabel1.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel1.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsJLabel1.insets = new java.awt.Insets(10, 10, 0, 0);
                getSpecTab().add(getJLabel1(), constraintsJLabel1);

                java.awt.GridBagConstraints constraintsDataTableScrollPane = new java.awt.GridBagConstraints();
                constraintsDataTableScrollPane.gridx = 0;
                constraintsDataTableScrollPane.gridy = 1;
                constraintsDataTableScrollPane.gridwidth = 3;
                constraintsDataTableScrollPane.fill = java.awt.GridBagConstraints.BOTH;
                constraintsDataTableScrollPane.weightx = 1.0;
                constraintsDataTableScrollPane.weighty = 1.0;
                constraintsDataTableScrollPane.insets = new java.awt.Insets(5,
                        10, 0, 0);
                getSpecTab().add(getDataTableScrollPane(),
                        constraintsDataTableScrollPane);

                java.awt.GridBagConstraints constraintsJLabel2 = new java.awt.GridBagConstraints();
                constraintsJLabel2.gridx = 0;
                constraintsJLabel2.gridy = 3;
                constraintsJLabel2.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsJLabel2.insets = new java.awt.Insets(10, 10, 0, 0);
                getSpecTab().add(getJLabel2(), constraintsJLabel2);

                java.awt.GridBagConstraints constraintsSeedCheck = new java.awt.GridBagConstraints();
                constraintsSeedCheck.gridx = 0;
                constraintsSeedCheck.gridy = 4;
                constraintsSeedCheck.insets = new java.awt.Insets(5, 10, 5, 0);
                getSpecTab().add(getSeedCheck(), constraintsSeedCheck);

                java.awt.GridBagConstraints constraintsSeedSelector = new java.awt.GridBagConstraints();
                constraintsSeedSelector.gridx = 1;
                constraintsSeedSelector.gridy = 4;
                constraintsSeedSelector.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsSeedSelector.insets = new java.awt.Insets(5, 5, 0, 0);
                getSpecTab().add(getSeedSelector(), constraintsSeedSelector);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSpecTab;
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
        getEstimateButton().addActionListener(ivjEventHandler);
        getBootstrapButton().addActionListener(ivjEventHandler);
        getSeedCheck().addItemListener(ivjEventHandler);
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
            setName("SVARBlanQuaPanel");
            setLayout(new javax.swing.BoxLayout(this,
                    javax.swing.BoxLayout.X_AXIS));
            setSize(620, 357);
            add(getJTabbedPane1(), getJTabbedPane1().getName());
            initConnections();
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}

        // Just to display the matrix of long run restrictions.
        getDataTableScrollPane().setColumnHeaderSymbolName(
                VARConstants.Ny_Def.name);
        // user code end
    }

    public void shown(boolean isShown) {
        if (isShown) {
            int k = global().get(VARConstants.y_Def).getJSCNArray().cols();
            getDataTable().setJSCData(
                    new JSCNArray("BQres", UMatrix.lowerTringular(
                            Double.NEGATIVE_INFINITY, k)));

        }

    }
}