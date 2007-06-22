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

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import com.jstatcom.component.StdMessages;
import com.jstatcom.engine.PCall;
import com.jstatcom.equation.EqPanel;
import com.jstatcom.equation.EqTermAR;
import com.jstatcom.equation.EqTermDefault;
import com.jstatcom.equation.EqTermLHS;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.ModelPanel;
import com.jstatcom.table.JSCCellRendererTypes;
import com.jstatcom.table.JSCMouseListenerTypes;
import com.jstatcom.table.JSCPopupTypes;
import com.jstatcom.util.UMatrix;

import de.jmulti.proc.VARSubsetSearchCall;
import de.jmulti.tools.SubsetSpecPanel;

/**
 * Panel to perform automatic or manual setting of subset restrictions for VAR.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public final class VARSubSetPanel extends ModelPanel {
    private static final Logger log = Logger.getLogger(VARSubSetPanel.class);

    private JPanel ivjJPanel1 = null;

    private JScrollPane ivjJScrollPane1 = null;

    private JButton ivjSearchButton = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private JButton ivjReset = null;

    private EqTermDefault ivjDeterministicsTerm = null;

    private EqPanel ivjEquationPanel = null;

    private EqTermAR ivjExogenousTerm = null;

    private EqTermAR ivjLaggedEndogenous = null;

    private EqTermLHS ivjLHS = null;

    private SubsetSpecPanel ivjSubsetSpecPanel = null;

    private JPanel ivjJPanel2 = null;

    class IvjEventHandler implements java.awt.event.ActionListener {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == VARSubSetPanel.this.getSearchButton())
                connEtoC1();
            if (e.getSource() == VARSubSetPanel.this.getReset())
                connEtoC3();
        };
    };

    /**
     * SubSetPanel constructor comment.
     */
    public VARSubSetPanel() {
        super();
        initialize();
    }

    /**
     * connEtoC1: (SearchButton.action. -->
     * SubSetPanel.searchButton_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC1() {
        try {
            // user code begin {1}
            // user code end
            this.searchButton_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC3: (Reset.action. --> SubSetPanel.reset_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC3() {
        try {
            // user code begin {1}
            // user code end
            this.reset_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    private EqTermDefault getDeterministicsTerm() {
        if (ivjDeterministicsTerm == null) {
            try {
                ivjDeterministicsTerm = new EqTermDefault();
                ivjDeterministicsTerm.setName("DeterministicsTerm");
                ivjDeterministicsTerm
                        .setRendererCoeff(JSCCellRendererTypes.SUBSET_012);
                ivjDeterministicsTerm.setTablePopup(JSCPopupTypes.CLICK_012);
                ivjDeterministicsTerm
                        .setMouseListenerCoeff(JSCMouseListenerTypes.SUBSET_012);
                ivjDeterministicsTerm
                        .setSymbolNameCoeff(VARConstants.Cd_Def.name);
                ivjDeterministicsTerm.setBounds(196, 22, 20, 20);
                ivjDeterministicsTerm
                        .setSymbolNameVariables(VARConstants.Nd_Def.name);
                ivjDeterministicsTerm.setCoeffMatRequired(true);
                // user code begin {1}
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
                getEquationPanel().setBounds(0, 0, 528, 196);
                getEquationPanel().add(getLHS(), getLHS().getName());
                getEquationPanel().add(getLaggedEndogenous(),
                        getLaggedEndogenous().getName());
                getEquationPanel().add(getExogenousTerm(),
                        getExogenousTerm().getName());
                getEquationPanel().add(getDeterministicsTerm(),
                        getDeterministicsTerm().getName());

            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjEquationPanel;
    }

    private EqTermAR getExogenousTerm() {
        if (ivjExogenousTerm == null) {
            try {
                ivjExogenousTerm = new EqTermAR();
                ivjExogenousTerm.setName("ExogenousTerm");
                ivjExogenousTerm
                        .setSymbolNameVariables(VARConstants.Nx_Def.name);
                ivjExogenousTerm
                        .setRendererCoeff(JSCCellRendererTypes.SUBSET_012);
                ivjExogenousTerm.setSymbolNameLags(VARConstants.px_Def.name);
                ivjExogenousTerm.setTablePopup(JSCPopupTypes.CLICK_012);
                ivjExogenousTerm
                        .setMouseListenerCoeff(JSCMouseListenerTypes.SUBSET_012);
                ivjExogenousTerm.setBounds(192, 20, 20, 20);
                ivjExogenousTerm.setSymbolNameCoeff(VARConstants.Cx_Def.name);
                // user code begin {1}
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
                ivjJPanel1.setPreferredSize(new java.awt.Dimension(485, 140));
                ivjJPanel1.setBorder(new javax.swing.border.CompoundBorder());
                ivjJPanel1.setLayout(new java.awt.BorderLayout());
                ivjJPanel1.setMinimumSize(new java.awt.Dimension(485, 140));
                getJPanel1().add(getSubsetSpecPanel(), "Center");
                getJPanel1().add(getJPanel2(), "East");
                // user code begin {1}
                TitledBorder title = new TitledBorder(new BevelBorder(
                        BevelBorder.LOWERED), "Specify Subset Restrictions",
                        TitledBorder.RIGHT, TitledBorder.TOP);
                getJPanel1().setBorder(title);

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
                ivjJPanel2.setPreferredSize(new java.awt.Dimension(150, 0));
                ivjJPanel2.setLayout(getJPanel2FlowLayout());
                getJPanel2().add(getReset(), getReset().getName());
                getJPanel2()
                        .add(getSearchButton(), getSearchButton().getName());
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
     * Return the JPanel2FlowLayout property value.
     * 
     * @return java.awt.FlowLayout
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private java.awt.FlowLayout getJPanel2FlowLayout() {
        java.awt.FlowLayout ivjJPanel2FlowLayout = null;
        try {
            /* Create part */
            ivjJPanel2FlowLayout = new java.awt.FlowLayout();
            ivjJPanel2FlowLayout.setVgap(20);
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        ;
        return ivjJPanel2FlowLayout;
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
                getJScrollPane1().setBorder(
                        new BevelBorder(BevelBorder.LOWERED));
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
                        .setRendererCoeff(JSCCellRendererTypes.SUBSET_012);
                ivjLaggedEndogenous.setSymbolNameLags(VARConstants.py_Def.name);
                ivjLaggedEndogenous.setStartLag(1);
                ivjLaggedEndogenous.setTablePopup(JSCPopupTypes.CLICK_012);
                ivjLaggedEndogenous
                        .setMouseListenerCoeff(JSCMouseListenerTypes.SUBSET_012);
                ivjLaggedEndogenous.setBounds(169, 32, 20, 20);
                ivjLaggedEndogenous
                        .setSymbolNameCoeff(VARConstants.Cy_Def.name);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjLaggedEndogenous;
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
     * Return the Reset property value.
     * 
     * @return javax.swing.JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JButton getReset() {
        if (ivjReset == null) {
            try {
                ivjReset = new javax.swing.JButton();
                ivjReset.setName("Reset");
                ivjReset.setPreferredSize(new java.awt.Dimension(135, 27));
                ivjReset.setText("Reset Restrictions");
                ivjReset.setMargin(new java.awt.Insets(2, 5, 2, 5));
                ivjReset.setMinimumSize(new java.awt.Dimension(135, 27));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjReset;
    }

    /**
     * Return the SearchButton property value.
     * 
     * @return javax.swing.JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JButton getSearchButton() {
        if (ivjSearchButton == null) {
            try {
                ivjSearchButton = new javax.swing.JButton();
                ivjSearchButton.setName("SearchButton");
                ivjSearchButton
                        .setPreferredSize(new java.awt.Dimension(135, 27));
                ivjSearchButton.setText("Search Restrictions");
                ivjSearchButton.setMargin(new java.awt.Insets(2, 5, 2, 5));
                ivjSearchButton.setMinimumSize(new java.awt.Dimension(135, 27));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSearchButton;
    }

    /**
     * Return the SubsetSpecPanel property value.
     * 
     * @return de.jmulti.tools.SubsetSpecPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private SubsetSpecPanel getSubsetSpecPanel() {
        if (ivjSubsetSpecPanel == null) {
            try {
                ivjSubsetSpecPanel = new SubsetSpecPanel();
                ivjSubsetSpecPanel.setName("SubsetSpecPanel");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSubsetSpecPanel;
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
        getSearchButton().addActionListener(ivjEventHandler);
        getReset().addActionListener(ivjEventHandler);
    }

    /**
     * Initialize the class.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initialize() {
        try {
            // user code begin {1}

            // user code end
            setName("SubSetPanel");
            setLayout(new java.awt.BorderLayout());
            setSize(761, 372);
            add(getJPanel1(), "North");
            add(getJScrollPane1(), "Center");
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
    private void reset_ActionEvents() {

        JSCNArray cd = global().get(VARConstants.Cd_Def).getJSCNArray();
        JSCNArray cx = global().get(VARConstants.Cx_Def).getJSCNArray();
        JSCNArray cy = global().get(VARConstants.Cy_Def).getJSCNArray();

        if (cd.cols() > 0)
            cd.setVal(UMatrix.ones(cd.rows(), cd.cols()));
        if (cx.cols() > 0)
            cx.setVal(UMatrix.ones(cx.rows(), cx.cols()));

        if (cy.cols() > 0)
            cy.setVal(UMatrix.ones(cy.rows(), cy.cols()));
    }

    /**
     * Comment
     */
    private void searchButton_ActionEvents() {
        if (global().get(VARConstants.Cy_Def).isEmpty()
                && global().get(VARConstants.Cx_Def).isEmpty()
                && global().get(VARConstants.Cd_Def).isEmpty()) {
            StdMessages.infoNothingSelected("Nothing to search.");
            return;
        }
        PCall job = new VARSubsetSearchCall(global(), getSubsetSpecPanel()
                .getStrategy(), getSubsetSpecPanel().getCriterion(),
                getSubsetSpecPanel().getThreshold());

        job.execute();
    }
}