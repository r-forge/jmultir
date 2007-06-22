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

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;

import org.apache.log4j.Logger;

import com.jstatcom.component.StdMessages;
import com.jstatcom.engine.PCall;
import com.jstatcom.equation.EqPanel;
import com.jstatcom.equation.EqTermAR;
import com.jstatcom.equation.EqTermDefault;
import com.jstatcom.equation.EqTermLHS;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.ModelPanel;
import com.jstatcom.model.SymbolEvent;
import com.jstatcom.model.SymbolEventTypes;
import com.jstatcom.model.SymbolListener;
import com.jstatcom.table.JSCCellRendererTypes;
import com.jstatcom.table.JSCMouseListenerTypes;
import com.jstatcom.table.JSCPopupTypes;
import com.jstatcom.ts.TSDate;
import com.jstatcom.ts.TSDateRange;
import com.jstatcom.ts.TSDateSelector;
import com.jstatcom.util.UMatrix;

import de.jmulti.proc.RecursCoeffCall;
import de.jmulti.tools.ModelTypes;

/**
 * Panel for recursive estimation of VAR model coefficients.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
final class VARRecursCoeff extends ModelPanel {
    private static final Logger log = Logger.getLogger(VARRecursCoeff.class);

    // Needed to compute indices according to original range.
    private TSDateRange origDateRange = null;

    private JPanel ivjJPanel1 = null;

    private JButton ivjExecute = null;

    private JLabel ivjJLabel1 = null;

    private TSDateSelector ivjStartDate = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private EqTermDefault ivjDeterministicsTerm = null;

    private EqPanel ivjEquationPanel = null;

    private EqTermAR ivjExogenousTerm = null;

    private EqTermAR ivjLaggedEndogenous = null;

    private EqTermLHS ivjLHS = null;

    private JScrollPane ivjJScrollPane1 = null;

    private JLabel ivjJLabel = null;

    private JLabel ivjJLabel2 = null;

    private JLabel ivjJLabel3 = null;

    private JPanel ivjJPanel2 = null;

    private JLabel ivjJLabel31 = null;

    private JRadioButton ivjLagGroupingCheck = null;

    private boolean recompute = true;

    private JRadioButton ivjSeparateWindowCheck = null;

    class IvjEventHandler implements java.awt.event.ActionListener {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == VARRecursCoeff.this.getExecute())
                connEtoC1();
        };
    };

    /**
     * VARRecursCoeff constructor comment.
     */
    public VARRecursCoeff() {
        super();
        initialize();
    }

    /**
     * connEtoC1: (Execute.action. --> VARRecursCoeff.execute_ActionEvents()V)
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
     * Execute recursive estimation.
     */
    private void execute_ActionEvents() {

        JSCNArray test = new JSCNArray("test");
        test.appendCols(global().get(VARConstants.cy_recCoeff_Def)
                .getJSCNArray());
        test.appendCols(global().get(VARConstants.cx_recCoeff_Def)
                .getJSCNArray());
        test.appendCols(global().get(VARConstants.cd_recCoeff_Def)
                .getJSCNArray());

        if (test.rows() == 0) {
            StdMessages.errorSpecification("Not possible for empty model.");
            return;
        }
        if (UMatrix.getNonzeroDoubleCount(test.vec()) == 0) {
            StdMessages
                    .infoNothingSelected("Please select at least one coefficient.");
            return;
        }

        PCall job = new RecursCoeffCall(global(), local(), ModelTypes.VAR,
                origDateRange.indexForDate(getStartDate().getTSDate()),
                getSeparateWindowCheck().isSelected());

        job.execute();
    }

    private EqTermDefault getDeterministicsTerm() {
        if (ivjDeterministicsTerm == null) {
            try {
                ivjDeterministicsTerm = new EqTermDefault();
                ivjDeterministicsTerm.setName("DeterministicsTerm");
                ivjDeterministicsTerm
                        .setRendererCoeff(JSCCellRendererTypes.SUBSET_01);
                ivjDeterministicsTerm.setTablePopup(JSCPopupTypes.CLICK_01);
                ivjDeterministicsTerm
                        .setMouseListenerCoeff(JSCMouseListenerTypes.SUBSET_01);
                ivjDeterministicsTerm
                        .setSymbolNameSubsetRes(VARConstants.Cd_Def.name);
                ivjDeterministicsTerm
                        .setSymbolNameCoeff(VARConstants.cd_recCoeff_Def.name);
                ivjDeterministicsTerm.setBounds(196, 22, 20, 20);
                ivjDeterministicsTerm
                        .setSymbolNameVariables(VARConstants.Nd_Def.name);
                ivjDeterministicsTerm.setCoeffMatRequired(true);
                // user code begin {1}
                ivjDeterministicsTerm
                        .setSymbolNameCoeff(VARConstants.cd_recCoeff_Def.name);
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjDeterministicsTerm;
    }

    private EqPanel getEquationPanel() {
        if (ivjEquationPanel == null) {
            try {
                ivjEquationPanel = new EqPanel();
                ivjEquationPanel.setName("EquationPanel");
                ivjEquationPanel.setBounds(0, 0, 700, 225);
                getEquationPanel().add(getLHS(), getLHS().getName());
                getEquationPanel().add(getLaggedEndogenous(),
                        getLaggedEndogenous().getName());
                getEquationPanel().add(getExogenousTerm(),
                        getExogenousTerm().getName());
                getEquationPanel().add(getDeterministicsTerm(),
                        getDeterministicsTerm().getName());
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjEquationPanel;
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
                ivjExecute.setPreferredSize(new java.awt.Dimension(100, 25));
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

    private EqTermAR getExogenousTerm() {
        if (ivjExogenousTerm == null) {
            try {
                ivjExogenousTerm = new EqTermAR();
                ivjExogenousTerm.setName("ExogenousTerm");
                ivjExogenousTerm
                        .setSymbolNameVariables(VARConstants.Nx_Def.name);
                ivjExogenousTerm
                        .setRendererCoeff(JSCCellRendererTypes.SUBSET_01);
                ivjExogenousTerm.setSymbolNameLags(VARConstants.px_Def.name);
                ivjExogenousTerm.setTablePopup(JSCPopupTypes.CLICK_01);
                ivjExogenousTerm
                        .setMouseListenerCoeff(JSCMouseListenerTypes.SUBSET_01);
                ivjExogenousTerm
                        .setSymbolNameSubsetRes(VARConstants.Cx_Def.name);
                ivjExogenousTerm.setBounds(192, 20, 20, 20);
                // user code begin {1}
                ivjExogenousTerm
                        .setSymbolNameCoeff(VARConstants.cx_recCoeff_Def.name);
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjExogenousTerm;
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
                ivjJLabel.setText("* : Selects parameter for graph ");
                ivjJLabel.setBounds(20, 50, 319, 14);
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
                ivjJLabel1.setPreferredSize(new java.awt.Dimension(100, 21));
                ivjJLabel1.setText("Start date");
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
                ivjJLabel2.setText("0 : Excludes parameter from graph ");
                ivjJLabel2.setBounds(20, 30, 319, 14);
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
                ivjJLabel3.setText("Click on coefficients:");
                ivjJLabel3.setBounds(20, 10, 319, 14);
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
     * Return the JLabel31 property value.
     * 
     * @return javax.swing.JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JLabel getJLabel31() {
        if (ivjJLabel31 == null) {
            try {
                ivjJLabel31 = new javax.swing.JLabel();
                ivjJLabel31.setName("JLabel31");
                ivjJLabel31.setText("RIGHT click on matrices to access menu");
                ivjJLabel31.setBounds(20, 70, 319, 14);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJLabel31;
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
                ivjJPanel1.setPreferredSize(new java.awt.Dimension(0, 150));
                ivjJPanel1.setBorder(new BevelBorder(BevelBorder.LOWERED));
                ivjJPanel1.setLayout(new java.awt.GridBagLayout());

                java.awt.GridBagConstraints constraintsStartDate = new java.awt.GridBagConstraints();
                constraintsStartDate.gridx = 0;
                constraintsStartDate.gridy = 0;
                constraintsStartDate.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsStartDate.insets = new java.awt.Insets(10, 10, 0, 0);
                getJPanel1().add(getStartDate(), constraintsStartDate);

                java.awt.GridBagConstraints constraintsJLabel1 = new java.awt.GridBagConstraints();
                constraintsJLabel1.gridx = 1;
                constraintsJLabel1.gridy = 0;
                constraintsJLabel1.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsJLabel1.insets = new java.awt.Insets(10, 10, 0, 0);
                getJPanel1().add(getJLabel1(), constraintsJLabel1);

                java.awt.GridBagConstraints constraintsExecute = new java.awt.GridBagConstraints();
                constraintsExecute.gridx = 0;
                constraintsExecute.gridy = 3;
                constraintsExecute.gridwidth = 3;
                constraintsExecute.anchor = java.awt.GridBagConstraints.EAST;
                constraintsExecute.weighty = 1.0;
                constraintsExecute.insets = new java.awt.Insets(10, 10, 10, 0);
                getJPanel1().add(getExecute(), constraintsExecute);

                java.awt.GridBagConstraints constraintsJPanel2 = new java.awt.GridBagConstraints();
                constraintsJPanel2.gridx = 3;
                constraintsJPanel2.gridy = 0;
                constraintsJPanel2.gridheight = 3;
                constraintsJPanel2.fill = java.awt.GridBagConstraints.BOTH;
                constraintsJPanel2.weightx = 1.0;
                constraintsJPanel2.weighty = 1.0;
                constraintsJPanel2.insets = new java.awt.Insets(4, 4, 4, 4);
                getJPanel1().add(getJPanel2(), constraintsJPanel2);

                java.awt.GridBagConstraints constraintsSeparateWindowCheck = new java.awt.GridBagConstraints();
                constraintsSeparateWindowCheck.gridx = 0;
                constraintsSeparateWindowCheck.gridy = 1;
                constraintsSeparateWindowCheck.gridwidth = 3;
                constraintsSeparateWindowCheck.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsSeparateWindowCheck.insets = new java.awt.Insets(10,
                        10, 0, 0);
                getJPanel1().add(getSeparateWindowCheck(),
                        constraintsSeparateWindowCheck);

                java.awt.GridBagConstraints constraintsLagGroupingCheck = new java.awt.GridBagConstraints();
                constraintsLagGroupingCheck.gridx = 0;
                constraintsLagGroupingCheck.gridy = 2;
                constraintsLagGroupingCheck.gridwidth = 3;
                constraintsLagGroupingCheck.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsLagGroupingCheck.anchor = java.awt.GridBagConstraints.NORTH;
                constraintsLagGroupingCheck.insets = new java.awt.Insets(5, 10,
                        0, 0);
                getJPanel1().add(getLagGroupingCheck(),
                        constraintsLagGroupingCheck);
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
                ivjJPanel2.setLayout(null);
                getJPanel2().add(getJLabel3(), getJLabel3().getName());
                getJPanel2().add(getJLabel2(), getJLabel2().getName());
                getJPanel2().add(getJLabel(), getJLabel().getName());
                getJPanel2().add(getJLabel31(), getJLabel31().getName());
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
     * Return the JScrollPane1 property value.
     * 
     * @return javax.swing.JScrollPane
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JScrollPane getJScrollPane1() {
        if (ivjJScrollPane1 == null) {
            try {
                ivjJScrollPane1 = new javax.swing.JScrollPane();
                ivjJScrollPane1.setName("JScrollPane1");
                getJScrollPane1().setViewportView(getEquationPanel());
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJScrollPane1;
    }

    private EqTermAR getLaggedEndogenous() {
        if (ivjLaggedEndogenous == null) {
            try {
                ivjLaggedEndogenous = new EqTermAR();
                ivjLaggedEndogenous.setName("LaggedEndogenous");
                ivjLaggedEndogenous
                        .setSymbolNameVariables(VARConstants.Ny_Def.name);
                ivjLaggedEndogenous
                        .setRendererCoeff(JSCCellRendererTypes.SUBSET_01);
                ivjLaggedEndogenous.setSymbolNameLags(VARConstants.py_Def.name);
                ivjLaggedEndogenous.setStartLag(1);
                ivjLaggedEndogenous.setTablePopup(JSCPopupTypes.CLICK_01);
                ivjLaggedEndogenous
                        .setMouseListenerCoeff(JSCMouseListenerTypes.SUBSET_01);
                ivjLaggedEndogenous
                        .setSymbolNameSubsetRes(VARConstants.Cy_Def.name);
                ivjLaggedEndogenous.setBounds(169, 32, 20, 20);
                // user code begin {1}
                ivjLaggedEndogenous
                        .setSymbolNameCoeff(VARConstants.cy_recCoeff_Def.name);
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjLaggedEndogenous;
    }

    /**
     * Return the LagGroupingCheck property value.
     * 
     * @return javax.swing.JRadioButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JRadioButton getLagGroupingCheck() {
        if (ivjLagGroupingCheck == null) {
            try {
                ivjLagGroupingCheck = new javax.swing.JRadioButton();
                ivjLagGroupingCheck.setName("LagGroupingCheck");
                ivjLagGroupingCheck
                        .setText("Group different lags in separate windows");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjLagGroupingCheck;
    }

    private EqTermLHS getLHS() {
        if (ivjLHS == null) {
            try {
                ivjLHS = new EqTermLHS();
                ivjLHS.setName("LHS");
                ivjLHS.setBounds(114, 61, 20, 20);
                ivjLHS.setSymbolNameVariables(VARConstants.Ny_Def.name);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjLHS;
    }

    /**
     * Return the SeparateWindowCheck property value.
     * 
     * @return javax.swing.JRadioButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JRadioButton getSeparateWindowCheck() {
        if (ivjSeparateWindowCheck == null) {
            try {
                ivjSeparateWindowCheck = new javax.swing.JRadioButton();
                ivjSeparateWindowCheck.setName("SeparateWindowCheck");
                ivjSeparateWindowCheck.setSelected(true);
                ivjSeparateWindowCheck.setText("Each coeff in separate window");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSeparateWindowCheck;
    }

    /**
     * Return the StartDate property value.
     * 
     * @return com.jstatcom.ts.TSDateSelector
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private com.jstatcom.ts.TSDateSelector getStartDate() {
        if (ivjStartDate == null) {
            try {
                ivjStartDate = new com.jstatcom.ts.TSDateSelector();
                ivjStartDate.setName("StartDate");
                ivjStartDate.setPreferredSize(new java.awt.Dimension(85, 21));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjStartDate;
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
    }

    /**
     * Initialize the class.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initialize() {
        try {
            // user code begin {1}
            ButtonGroup group = new ButtonGroup();
            group.add(getSeparateWindowCheck());
            group.add(getLagGroupingCheck());
            // user code end
            setName("VARRecursCoeff");
            setLayout(new java.awt.BorderLayout());
            setSize(700, 285);
            add(getJPanel1(), "North");
            add(getJScrollPane1(), "Center");
            initConnections();
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}

        // set recompute to true when covariance changes
        global().get(VARConstants.cv_u_hat_Def).addSymbolListener(
                new SymbolListener() {
                    public void valueChanged(SymbolEvent evt) {
                        recompute = true;
                    }
                }, SymbolEventTypes.EMPTY_STATE);
        // user code end
    }

    public void shown(boolean isShown) {
        if (isShown) {
            // set the date text field and the possible range of dates
            TSDateRange range = global().get(VARConstants.T1_Def)
                    .getJSCDRange().getTSDateRange();
            int nr = global().get(VARConstants.u_hat_Def).getJSCNArray().rows();
            // First possible value : cols(y)*py + cols(x)*(px+1) + cols(d) +
            // cols(y)
            // degfree are one less than 1st possible.
            JSCNArray y = global().get(VARConstants.y_Def).getJSCNArray();
            int degfree = global().get(VARConstants.py_Def).getJSCInt()
                    .intVal()
                    * y.cols()
                    + (global().get(VARConstants.px_Def).getJSCInt().intVal() + 1)
                    * global().get(VARConstants.x_Def).getJSCNArray().cols()
                    + global().get(VARConstants.d_all_Def).getJSCNArray()
                            .cols() + y.cols() - 1;

            // If the index is retrieved, it must be computed relative to the
            // original
            // start date!
            TSDate start = range.lowerBound();
            origDateRange = new TSDateRange(start, nr);
            TSDate firstPossibleDate = start.addPeriods(degfree);
            getStartDate().setEnclosingRange(firstPossibleDate,
                    nr - degfree - 1);

            JSCNArray cy = global().get(VARConstants.Cy_Def).getJSCNArray();
            JSCNArray cx = global().get(VARConstants.Cx_Def).getJSCNArray();
            JSCNArray cd = global().get(VARConstants.Cd_Def).getJSCNArray();

            // check first whether it must be changed, otherwise keep selection
            if (recompute) {
                global().get(VARConstants.cy_recCoeff_Def).setJSCData(
                        new JSCNArray("zeros_cy", new double[cy.rows()][cy
                                .cols()]));

                global().get(VARConstants.cx_recCoeff_Def).setJSCData(
                        new JSCNArray("zeros_cx", new double[cx.rows()][cx
                                .cols()]));

                global().get(VARConstants.cd_recCoeff_Def).setJSCData(
                        new JSCNArray("zeros_cd", new double[cd.rows()][cd
                                .cols()]));

                recompute = false;
            }

        }

    }
}