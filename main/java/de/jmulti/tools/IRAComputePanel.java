/*
 This file is part of the econometric software package JMulTi.
 Copyright (C) 2000-2005  Alexander Bankwitz, Markus Kraetzig

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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import com.jstatcom.component.NumSelector;
import com.jstatcom.engine.PCall;
import com.jstatcom.engine.PCallAdapter;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.JSCSArray;
import com.jstatcom.model.JSCTypeDef;
import com.jstatcom.model.JSCTypes;
import com.jstatcom.model.ModelPanel;
import com.jstatcom.model.Scope;
import com.jstatcom.table.JSCDataTableScrollPane;
import com.jstatcom.table.JSCSArrayTable;

import de.jmulti.proc.IRABootCIPercCall;
import de.jmulti.proc.IRABootCIStudCall;
import de.jmulti.proc.IRAFuncCall;
import de.jmulti.var.SVARConstants;
import de.jmulti.var.VARConstants;
import de.jmulti.vecm.SVECConstants;
import de.jmulti.vecm.VECMConstants;

/**
 * 
 * Configurable panel for specification and execution of a Impulse Response
 * Analysis for different models. It computes the IR functions and can be used
 * to bootstrap confidence intervals as well.
 * 
 * @author A. Benkwitz, <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public final class IRAComputePanel extends ModelPanel {
    private static final Logger log = Logger.getLogger(IRAComputePanel.class);

    // CI names, shared between display and compute panel.
    public static final JSCTypeDef CI_NAMES = new JSCTypeDef("ci_names",
            JSCTypes.SARRAY, "names of computed CIs");

    private JPanel ivjCardPanel = null;

    private JButton ivjComputeBootCIButton = null;

    private JPanel ivjJPanel1 = null;

    private JLabel ivjJLabel1 = null;

    private NumSelector ivjPeriodSelector = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private ModelTypes modelType = ModelTypes.VAR;

    private JComboBox ivjCIChooser = null;

    private IRABootCIPercPanel ivjIRABootCIPercPanel = null;

    private IRABootCIStudPanel ivjIRABootCIStudPanel = null;

    private JSCDataTableScrollPane ivjCITableScrollPane = null;

    private JSCSArrayTable ivjComputedCITable = null;

    private JButton ivjClearCIButton = null;

    class IvjEventHandler implements java.awt.event.ActionListener,
            java.beans.PropertyChangeListener {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == IRAComputePanel.this.getComputeBootCIButton())
                connEtoC1();
            if (e.getSource() == IRAComputePanel.this.getCIChooser())
                connEtoC3();
            if (e.getSource() == IRAComputePanel.this.getClearCIButton())
                connEtoC4();
        };

        public void propertyChange(java.beans.PropertyChangeEvent evt) {
            if (evt.getSource() == IRAComputePanel.this.getPeriodSelector()
                    && (evt.getPropertyName().equals("number")))
                connEtoC2(evt);
        };
    };

    /**
     * IRAComputePanel constructor comment.
     */
    public IRAComputePanel() {
        super();
        initialize();
    }

    /**
     * Sets the bootstrap CI method.
     */
    private void cIChooser_ActionEvents() {
        if (getCIChooser().getSelectedIndex() == 0)
            ((java.awt.CardLayout) getCardPanel().getLayout()).show(
                    getCardPanel(), getIRABootCIPercPanel().getName());

        if (getCIChooser().getSelectedIndex() == 1)
            ((java.awt.CardLayout) getCardPanel().getLayout()).show(
                    getCardPanel(), getIRABootCIStudPanel().getName());
    }

    /**
     * Clears the CIs from the manager.
     */
    private void clearCI() {
        upper().get(CI_NAMES).clear();
        IRACIManager.getInstance().clear(modelType);
        local().clear();
    }

    /**
     * Initiates computation of bootstrapped CIs.
     */
    private void computeBootCI() {

        int periods = getPeriodSelector().getIntNumber();

        // Efron & Hall bootstrap CI is selected.
        if (getCIChooser().getSelectedIndex() == 0) {
            int bootRep = getIRABootCIPercPanel().getBootRepNum();
            boolean isSeed = getIRABootCIPercPanel().isSeed();
            int seed = getIRABootCIPercPanel().getSeed();
            double coverage = getIRABootCIPercPanel().getCICoverage();

            PCall job = new IRABootCIPercCall(global(), periods, bootRep,
                    coverage, isSeed, seed, modelType);
            // Add the CI names if the call has finished.
            job.addPCallListener(new PCallAdapter() {
                public void success() {
                    upper().get(CI_NAMES).setJSCData(
                            new JSCSArray("toSet", IRACIManager.getInstance()
                                    .getCIKeys(modelType)));
                }
            });
            job.setSymbolTable(local());
            job.execute();
        }

        // Studentized bootstrap CI is selected.
        if (getCIChooser().getSelectedIndex() == 1) {
            int bootRep = getIRABootCIStudPanel().getBootRepNum();
            int stdBootRep = getIRABootCIStudPanel().getStdBootRepNum();
            boolean isSeed = getIRABootCIStudPanel().isSeed();
            boolean stdIsSeed = getIRABootCIStudPanel().isStdSeed();
            int seed = getIRABootCIStudPanel().getSeed();
            int stdSeed = getIRABootCIStudPanel().getStdSeed();
            double coverage = getIRABootCIStudPanel().getCICoverage();

            PCall job = new IRABootCIStudCall(global(), periods, bootRep,
                    coverage, isSeed, seed, stdBootRep, stdIsSeed, stdSeed,
                    modelType);
            // Add the CI names if the call has finished.
            job.addPCallListener(new PCallAdapter() {
                public void success() {
                    upper().get(CI_NAMES).setJSCData(
                            new JSCSArray("toSet", IRACIManager.getInstance()
                                    .getCIKeys(modelType)));

                }
            });
            job.setSymbolTable(local());
            job.execute();
        }

    }

    /**
     * Initiates computation of of the IR function, depends on model type.
     * 
     */
    private void computeIRFunction() {

        // Delete CI's if period is bigger than computed CI's
        // Delete Keys

        JSCNArray yCoeff = null;
        JSCNArray cv_u_hat = null;
        JSCNArray structMatrix = null;
        int period = getPeriodSelector().getIntNumber();

        // collect data according to model.
        if (modelType == ModelTypes.VAR) {
            yCoeff = global().get(VARConstants.Ay_Def).getJSCNArray();
            cv_u_hat = global().get(VARConstants.cv_u_hat_Def).getJSCNArray();
            structMatrix = new JSCNArray("structMat");

        }
        ;
        if (modelType == ModelTypes.SVAR) {
            yCoeff = global().get(VARConstants.Ay_Def).getJSCNArray();
            cv_u_hat = new JSCNArray("cv_u_hat");
            structMatrix = global().get(SVARConstants.svar_A0_Def)
                    .getJSCNArray();
        }
        ;
        if (modelType == ModelTypes.VECM) {
            yCoeff = global().get(VECMConstants.A_Def).getJSCNArray();
            cv_u_hat = global().get(VECMConstants.cv_u_hat_Def).getJSCNArray();
            structMatrix = global().get(VECMConstants.A0_Def).getJSCNArray();
        }
        ;
        if (modelType == ModelTypes.SVEC) {
            yCoeff = global().get(VECMConstants.A_Def).getJSCNArray();
            cv_u_hat = global().get(VECMConstants.cv_u_hat_Def).getJSCNArray();
            structMatrix = global().get(SVECConstants.matB_Def).getJSCNArray();
        }
        ;

        PCall job = new IRAFuncCall(period, yCoeff, structMatrix, cv_u_hat,
                modelType);

        job.setSymbolTable(upper());
        job.execute();

    }

    /**
     * connEtoC1: (ComputeBootCIButton.action. -->
     * IRAComputePanel.computeBootCIButton_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC1() {
        try {
            // user code begin {1}
            // user code end
            this.computeBootCI();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC2: (PeriodSelector.number -->
     * IRAComputePanel.computeIRFunction()V)
     * 
     * @param arg1
     *            java.beans.PropertyChangeEvent
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC2(java.beans.PropertyChangeEvent arg1) {
        try {
            // user code begin {1}
            // user code end
            this.computeIRFunction();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC3: (CIChooser.action. -->
     * IRAComputePanel.cIChooser_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC3() {
        try {
            // user code begin {1}
            // user code end
            this.cIChooser_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC4: (ClearCIButton.action. --> IRAComputePanel.clearCI()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC4() {
        try {
            // user code begin {1}
            // user code end
            this.clearCI();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * Return the CardPanel property value.
     * 
     * @return javax.swing.JPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JPanel getCardPanel() {
        if (ivjCardPanel == null) {
            try {
                ivjCardPanel = new javax.swing.JPanel();
                ivjCardPanel.setName("CardPanel");
                ivjCardPanel.setPreferredSize(new java.awt.Dimension(360, 100));
                ivjCardPanel.setLayout(new java.awt.CardLayout());
                getCardPanel().add(getIRABootCIPercPanel(),
                        getIRABootCIPercPanel().getName());
                getCardPanel().add(getIRABootCIStudPanel(),
                        getIRABootCIStudPanel().getName());
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjCardPanel;
    }

    /**
     * Return the BootMethodCombo property value.
     * 
     * @return javax.swing.JComboBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JComboBox getCIChooser() {
        if (ivjCIChooser == null) {
            try {
                ivjCIChooser = new javax.swing.JComboBox();
                ivjCIChooser.setName("CIChooser");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjCIChooser;
    }

    private JSCDataTableScrollPane getCITableScrollPane() {
        if (ivjCITableScrollPane == null) {
            try {
                ivjCITableScrollPane = new JSCDataTableScrollPane();
                ivjCITableScrollPane.setName("CITableScrollPane");
                ivjCITableScrollPane.setColumnHeaderShowing(true);
                ivjCITableScrollPane.setMaximumVisibleRows(12);
                ivjCITableScrollPane.setMinimumVisibleRows(1);
                String ivjLocal47columnHeaderStringData[] = { "Computed Confidence Intervals" };
                ivjCITableScrollPane
                        .setColumnHeaderStringData(ivjLocal47columnHeaderStringData);
                getCITableScrollPane().setViewportView(getComputedCITable());
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjCITableScrollPane;
    }

    /**
     * Return the ClearCIButton property value.
     * 
     * @return javax.swing.JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JButton getClearCIButton() {
        if (ivjClearCIButton == null) {
            try {
                ivjClearCIButton = new javax.swing.JButton();
                ivjClearCIButton.setName("ClearCIButton");
                ivjClearCIButton.setText("Remove Computed CIs");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjClearCIButton;
    }

    /**
     * Return the ComputeBootCIButton property value.
     * 
     * @return javax.swing.JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JButton getComputeBootCIButton() {
        if (ivjComputeBootCIButton == null) {
            try {
                ivjComputeBootCIButton = new javax.swing.JButton();
                ivjComputeBootCIButton.setName("ComputeBootCIButton");
                ivjComputeBootCIButton
                        .setText("Bootstrap Confidence Intervals");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjComputeBootCIButton;
    }

    private JSCSArrayTable getComputedCITable() {
        if (ivjComputedCITable == null) {
            try {
                ivjComputedCITable = new JSCSArrayTable();
                ivjComputedCITable.setName("ComputedCITable");
                getCITableScrollPane().setColumnHeaderView(
                        ivjComputedCITable.getTableHeader());
                ivjComputedCITable.setSymbolScope(Scope.UPPER);
                ivjComputedCITable.setDynamicColumnWidth(true);
                ivjComputedCITable.setMaximumColumnWidth(300);
                ivjComputedCITable.setBounds(0, 0, 45, 17);
                ivjComputedCITable.setEditable(false);
                ivjComputedCITable.setMinimumColumnWidth(200);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjComputedCITable;
    }

    /**
     * Return the IRABootCIPercPanel property value.
     * 
     * @return de.jmulti.tools.IRABootCIPercPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private IRABootCIPercPanel getIRABootCIPercPanel() {
        if (ivjIRABootCIPercPanel == null) {
            try {
                ivjIRABootCIPercPanel = new IRABootCIPercPanel();
                ivjIRABootCIPercPanel.setName("IRABootCIPercPanel");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjIRABootCIPercPanel;
    }

    /**
     * Return the IRABootCIStudPanel property value.
     * 
     * @return de.jmulti.tools.IRABootCIStudPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private IRABootCIStudPanel getIRABootCIStudPanel() {
        if (ivjIRABootCIStudPanel == null) {
            try {
                ivjIRABootCIStudPanel = new IRABootCIStudPanel();
                ivjIRABootCIStudPanel.setName("IRABootCIStudPanel");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjIRABootCIStudPanel;
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
                ivjJLabel1.setPreferredSize(new java.awt.Dimension(104, 20));
                ivjJLabel1.setText("Number of periods");
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
                ivjJPanel1.setBorder(new javax.swing.border.EtchedBorder());
                ivjJPanel1.setLayout(new java.awt.GridBagLayout());

                java.awt.GridBagConstraints constraintsCITableScrollPane = new java.awt.GridBagConstraints();
                constraintsCITableScrollPane.gridx = 0;
                constraintsCITableScrollPane.gridy = 1;
                constraintsCITableScrollPane.gridwidth = 2;
                constraintsCITableScrollPane.fill = java.awt.GridBagConstraints.BOTH;
                constraintsCITableScrollPane.weightx = 1.0;
                constraintsCITableScrollPane.weighty = 1.0;
                constraintsCITableScrollPane.insets = new java.awt.Insets(10,
                        5, 5, 5);
                getJPanel1().add(getCITableScrollPane(),
                        constraintsCITableScrollPane);

                java.awt.GridBagConstraints constraintsPeriodSelector = new java.awt.GridBagConstraints();
                constraintsPeriodSelector.gridx = 1;
                constraintsPeriodSelector.gridy = 0;
                constraintsPeriodSelector.anchor = java.awt.GridBagConstraints.WEST;
                constraintsPeriodSelector.insets = new java.awt.Insets(10, 10,
                        0, 20);
                java.awt.GridBagConstraints constraintsJLabel1 = new java.awt.GridBagConstraints();
                constraintsJLabel1.gridx = 0;
                constraintsJLabel1.gridy = 0;
                constraintsJLabel1.insets = new java.awt.Insets(10, 5, 0, 0);
                getJPanel1().add(getJLabel1(), constraintsJLabel1);

                java.awt.GridBagConstraints constraintsClearCIButton = new java.awt.GridBagConstraints();
                constraintsClearCIButton.gridx = 0;
                constraintsClearCIButton.gridy = 2;
                constraintsClearCIButton.gridwidth = 2;
                constraintsClearCIButton.insets = new java.awt.Insets(5, 5, 5,
                        5);
                constraintsPeriodSelector.fill = java.awt.GridBagConstraints.NONE;
                ivjJPanel1.add(getPeriodSelector(), constraintsPeriodSelector);
                getJPanel1().add(getClearCIButton(), constraintsClearCIButton);
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
     * Gets the model type that defines, how the computations are carried out.
     * 
     * @return the current model type
     */
    public ModelTypes getModelType() {
        return modelType;
    }

    private NumSelector getPeriodSelector() {
        if (ivjPeriodSelector == null) {
            try {
                ivjPeriodSelector = new NumSelector();
                ivjPeriodSelector.setName("PeriodSelector");
                ivjPeriodSelector.setNumber(20.0);
                ivjPeriodSelector.setRangeExpr("[1,200]");
                ivjPeriodSelector.setPreferredSize(new java.awt.Dimension(60,
                        20));
                // user code begin {1}
                ivjPeriodSelector
                        .setMinimumSize(new java.awt.Dimension(60, 20));
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjPeriodSelector;
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
        getComputeBootCIButton().addActionListener(ivjEventHandler);
        getPeriodSelector().addPropertyChangeListener(ivjEventHandler);
        getCIChooser().addActionListener(ivjEventHandler);
        getClearCIButton().addActionListener(ivjEventHandler);
    }

    /**
     * Initialize the class.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initialize() {
        try {
            // user code begin {1}
            // user code end
            setName("IRAComputePanel");
            setLayout(new java.awt.GridBagLayout());
            setSize(659, 380);

            java.awt.GridBagConstraints constraintsJPanel1 = new java.awt.GridBagConstraints();
            constraintsJPanel1.gridx = 1;
            constraintsJPanel1.gridy = 1;
            constraintsJPanel1.gridheight = 3;
            constraintsJPanel1.fill = java.awt.GridBagConstraints.BOTH;
            constraintsJPanel1.weightx = 1.0;
            constraintsJPanel1.weighty = 1.0;
            constraintsJPanel1.insets = new java.awt.Insets(0, 0, 5, 0);
            add(getJPanel1(), constraintsJPanel1);

            java.awt.GridBagConstraints constraintsCardPanel = new java.awt.GridBagConstraints();
            constraintsCardPanel.gridx = 2;
            constraintsCardPanel.gridy = 2;
            constraintsCardPanel.fill = java.awt.GridBagConstraints.BOTH;
            constraintsCardPanel.weighty = 1.0;
            add(getCardPanel(), constraintsCardPanel);

            java.awt.GridBagConstraints constraintsCIChooser = new java.awt.GridBagConstraints();
            constraintsCIChooser.gridx = 2;
            constraintsCIChooser.gridy = 1;
            constraintsCIChooser.fill = java.awt.GridBagConstraints.HORIZONTAL;
            constraintsCIChooser.insets = new java.awt.Insets(0, 5, 5, 5);
            add(getCIChooser(), constraintsCIChooser);

            java.awt.GridBagConstraints constraintsComputeBootCIButton = new java.awt.GridBagConstraints();
            constraintsComputeBootCIButton.gridx = 2;
            constraintsComputeBootCIButton.gridy = 3;
            constraintsComputeBootCIButton.fill = java.awt.GridBagConstraints.HORIZONTAL;
            constraintsComputeBootCIButton.insets = new java.awt.Insets(5, 5,
                    5, 5);
            add(getComputeBootCIButton(), constraintsComputeBootCIButton);
            initConnections();
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}
        getCIChooser().addItem("Efron & Hall Bootstrap Percentile CI");
        getCIChooser().addItem("Studentized Hall Bootstrap CI");
        setModelType(ModelTypes.VAR);
        getComputedCITable().setSymbolName(CI_NAMES.name);
        // user code end
    }

    /**
     * Evaluates <code>CardSuccessionPanel.CARD_DISPLAYED</code> property
     * change events.
     */
    public void shown(boolean isShown) {
        if (isShown)
            computeIRFunction();

    }

    /**
     * Deletes the computed IR function and the CI names. This does depend on
     * the model for the part of the CIs, because they are stored in the
     * <code>IRACIManager</code>. This needs to be called by the panel
     * binding the IRA together for the respective model.
     */
    public void reset() {
        upper().get(IRAFuncCall.IR_FERR).clear();
        upper().get(IRAFuncCall.IR_ORTH).clear();
        upper().get(IRAFuncCall.IR_SVAR).clear();
        clearCI();
    }

    /**
     * Sets the model type that defines, how the computations are carried out.
     * 
     * @param newModelType
     *            the model type to be used
     * @throws IllegalArgumentException
     *             <code>if (newModelType == null)</code>
     */
    public void setModelType(ModelTypes newModelType) {
        if (newModelType == null)
            throw new IllegalArgumentException("Model type was null.");
        modelType = newModelType;
        TitledBorder title = new TitledBorder(new BevelBorder(
                BevelBorder.LOWERED), "Configure " + modelType
                + " Impulse Response Analysis", TitledBorder.RIGHT,
                TitledBorder.TOP);
        this.setBorder(title);

    }
}