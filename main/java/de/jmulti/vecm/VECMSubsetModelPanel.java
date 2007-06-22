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

package de.jmulti.vecm;

import javax.swing.JButton;
import javax.swing.JLabel;
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
import com.jstatcom.model.SymbolEvent;
import com.jstatcom.model.SymbolListener;
import com.jstatcom.table.JSCCellRendererTypes;
import com.jstatcom.table.JSCMouseListenerTypes;
import com.jstatcom.table.JSCPopupTypes;

import de.jmulti.proc.VECMSubsetSearchCall;
import de.jmulti.tools.SubsetSpecPanel;

/**
 * This component provides a user input mask for specifying a VECM subset model.
 * All data objects needed for the estimation are created.
 * 
 * @author A. Benkwitz, <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public final class VECMSubsetModelPanel extends ModelPanel {
    private static final Logger log = Logger
            .getLogger(VECMSubsetModelPanel.class);

    private JPanel ivjJPanel1 = null;

    private JScrollPane ivjJScrollPane1 = null;

    private JButton ivjSearchButton = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private JPanel ivjJPanel2 = null;

    private JButton ivjReset = null;

    private EqPanel ivjEquationPanel = null;

    private EqTermLHS ivjLHS = null;

    private EqTermDefault ivjDeterministicsTerm = null;

    private EqTermDefault ivjECTerm = null;

    private EqTermAR ivjExogenousTerm = null;

    private EqTermAR ivjLaggedEndogenous = null;

    private SubsetSpecPanel ivjSubsetSpecPanel = null;

    private JLabel ivjJLabel1 = null;

    class IvjEventHandler implements java.awt.event.ActionListener {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == VECMSubsetModelPanel.this.getSearchButton())
                connEtoC1();
            if (e.getSource() == VECMSubsetModelPanel.this.getReset())
                connEtoC3();
        };
    };

    /**
     * SubSetPanel constructor comment.
     */
    public VECMSubsetModelPanel() {
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
                ivjDeterministicsTerm.setColumnWidth(25);
                ivjDeterministicsTerm
                        .setMouseListenerCoeff(JSCMouseListenerTypes.SUBSET_012);
                ivjDeterministicsTerm.setBounds(0, 0, 160, 120);
                ivjDeterministicsTerm.setCoeffMatRequired(true);
                // user code begin {1}
                ivjDeterministicsTerm
                        .setSymbolNameVariables(VECMConstants.Nd_vec_Def.name);
                ivjDeterministicsTerm
                        .setSymbolNameCoeff(VECMConstants.S_C_VEC_Def.name);
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjDeterministicsTerm;
    }

    private EqTermDefault getECTerm() {
        if (ivjECTerm == null) {
            try {
                ivjECTerm = new EqTermDefault();
                ivjECTerm.setName("ECTerm");
                ivjECTerm.setRendererCoeff(JSCCellRendererTypes.SUBSET_012);
                ivjECTerm.setTablePopup(JSCPopupTypes.CLICK_012);
                ivjECTerm.setColumnWidth(25);
                ivjECTerm
                        .setMouseListenerCoeff(JSCMouseListenerTypes.SUBSET_012);
                ivjECTerm.setLagIndex(-1);
                ivjECTerm.setMatrixCentered(true);
                ivjECTerm.setBounds(0, 0, 160, 120);
                ivjECTerm.setCoeffMatRequired(true);
                // user code begin {1}
                ivjECTerm.setSymbolNameVariables(VECMConstants.Nec_Def.name);
                ivjECTerm.setSymbolNameCoeff(VECMConstants.S_alpha_Def.name);
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjECTerm;
    }

    private EqPanel getEquationPanel() {
        if (ivjEquationPanel == null) {
            try {
                ivjEquationPanel = new EqPanel();
                ivjEquationPanel.setName("EquationPanel");
                ivjEquationPanel.setBounds(0, 0, 160, 120);
                getEquationPanel().add(getLHS(), getLHS().getName());
                getEquationPanel().add(getECTerm(), getECTerm().getName());
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

    private EqTermAR getExogenousTerm() {
        if (ivjExogenousTerm == null) {
            try {
                ivjExogenousTerm = new EqTermAR();
                ivjExogenousTerm.setName("ExogenousTerm");
                ivjExogenousTerm
                        .setRendererCoeff(JSCCellRendererTypes.SUBSET_012);
                ivjExogenousTerm.setTablePopup(JSCPopupTypes.CLICK_012);
                ivjExogenousTerm.setColumnWidth(25);
                ivjExogenousTerm
                        .setMouseListenerCoeff(JSCMouseListenerTypes.SUBSET_012);
                ivjExogenousTerm.setBounds(0, 0, 160, 120);
                // user code begin {1}
                ivjExogenousTerm.setSymbolNameCoeff(VECMConstants.S_B_Def.name);
                ivjExogenousTerm.setSymbolNameLags(VECMConstants.px_Def.name);
                ivjExogenousTerm
                        .setSymbolNameVariables(VECMConstants.Nx_Def.name);
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
                ivjJLabel1.setPreferredSize(new java.awt.Dimension(551, 20));
                ivjJLabel1
                        .setText(" Please note: Subset restrictions are only taken into account if TWO STAGE estimation is selected (current: JOHANSEN).");
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
                ivjJPanel1.setPreferredSize(new java.awt.Dimension(485, 160));
                ivjJPanel1.setBorder(new javax.swing.border.CompoundBorder());
                ivjJPanel1.setLayout(getJPanel1BorderLayout());
                ivjJPanel1.setMinimumSize(new java.awt.Dimension(485, 160));
                getJPanel1().add(getSubsetSpecPanel(), "Center");
                getJPanel1().add(getJPanel2(), "East");
                getJPanel1().add(getJLabel1(), "South");
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
     * Return the JPanel1BorderLayout property value.
     * 
     * @return java.awt.BorderLayout
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private java.awt.BorderLayout getJPanel1BorderLayout() {
        java.awt.BorderLayout ivjJPanel1BorderLayout = null;
        try {
            /* Create part */
            ivjJPanel1BorderLayout = new java.awt.BorderLayout();
            ivjJPanel1BorderLayout.setVgap(0);
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        ;
        return ivjJPanel1BorderLayout;
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
                        .setRendererCoeff(JSCCellRendererTypes.SUBSET_012);
                ivjLaggedEndogenous.setStartLag(1);
                ivjLaggedEndogenous.setTablePopup(JSCPopupTypes.CLICK_012);
                ivjLaggedEndogenous.setColumnWidth(25);
                ivjLaggedEndogenous
                        .setMouseListenerCoeff(JSCMouseListenerTypes.SUBSET_012);
                ivjLaggedEndogenous.setBounds(0, 0, 160, 120);
                // user code begin {1}
                ivjLaggedEndogenous
                        .setSymbolNameVariables(VECMConstants.Ndy_Def.name);
                ivjLaggedEndogenous
                        .setSymbolNameLags(VECMConstants.pdy_Def.name);
                ivjLaggedEndogenous
                        .setSymbolNameCoeff(VECMConstants.S_G_Def.name);
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
                ivjLHS.setRendererCoeff(JSCCellRendererTypes.SUBSET_01_GRAY);
                ivjLHS.setTablePopup(null);
                ivjLHS.setColumnWidth(25);
                ivjLHS.setMouseListenerCoeff(null);
                ivjLHS.setSymbolNameCoeff(VECMConstants.S_G0_Def.name);
                ivjLHS.setBounds(0, 0, 160, 120);
                ivjLHS.setSymbolNameVariables(VECMConstants.Ndy_Def.name);
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
                ivjReset.setMargin(new java.awt.Insets(2, 10, 2, 10));
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
            setName("VECMSubSetPanel");
            setLayout(new java.awt.BorderLayout());
            setSize(700, 250);
            add(getJPanel1(), "North");
            add(getJScrollPane1(), "Center");
            initConnections();
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}

        // Remove estimation results only if subset restrictions are relevant
        // (Two Stage).
        SymbolListener listener = new SymbolListener() {
            public void valueChanged(SymbolEvent evt) {
                if (global().get(VECMConstants.estimationStrategy_Def)
                        .getJSCInt().intVal() == VECMConstants.TWO_STAGE)
                    VECMDataManagerObs.getInstance().removeData();
            }
        };
        global().get(VECMConstants.S_alpha_Def).addSymbolListener(listener);
        global().get(VECMConstants.S_B_Def).addSymbolListener(listener);
        global().get(VECMConstants.S_C_VEC_Def).addSymbolListener(listener);
        global().get(VECMConstants.S_G_Def).addSymbolListener(listener);

        // user code end
    }

    /**
     * Comment
     */
    private void reset_ActionEvents() {
        VECMDataManagerSubsetModel.getInstance().resetAll();

    }

    /**
     * Initiates the call.
     */
    private void searchButton_ActionEvents() {
        if (global().get(VECMConstants.Ny_Def).getJSCSArray().rows() < 2) {
            StdMessages
                    .infoNothingSelected("Please select at least 2 endogenous variables.");
            return;
        }

        boolean reducedForm = true;
        JSCNArray g0 = global().get(VECMConstants.S_G0_Def).getJSCNArray();
        if (!g0.isEmpty()) {
            for (int i = 0; i < g0.rows() && reducedForm; i++)
                for (int j = 0; j < g0.cols() && reducedForm; j++)
                    reducedForm = reducedForm && (i == j) ? g0.doubleAt(i, j) == 1.0
                            : g0.doubleAt(i, j) == 0.0;
        }

        if (!reducedForm) {
            StdMessages
                    .infoGeneral("Cannot perform search for models in structural form.");
            return;
        }

        PCall job = new VECMSubsetSearchCall(global(), getSubsetSpecPanel()
                .getStrategy(), getSubsetSpecPanel().getCriterion(),
                getSubsetSpecPanel().getThreshold());
        job.execute();
    }

    public void shown(boolean isShown) {

        if (isShown) {
            int estStrat = global().get(VECMConstants.estimationStrategy_Def)
                    .getJSCInt().intVal();
            String estString = "TWO STAGE";
            if (estStrat == VECMConstants.TWO_STAGE)
                getJLabel1()
                        .setText(
                                " Please note: Subset restrictions are only taken into account if TWO STAGE estimation is selected (current: TWO STAGE).");
            else if (estStrat == VECMConstants.S2S) {
                estString = "S2S";
                getJLabel1()
                        .setText(
                                " Please note: Subset restrictions are only taken into account if TWO STAGE estimation is selected (current: S2S).");
            } else if (estStrat == VECMConstants.JOHANSEN) {
                estString = "JOHANSEN";
                getJLabel1()
                        .setText(
                                " Please note: Subset restrictions are only taken into account if TWO STAGE estimation is selected (current: JOHANSEN).");
            } else {
                estString = "UNKNOWN";
                getJLabel1().setText("UNKNOWN ESTIMATION STRATEGY!");
            }
            if (estStrat != VECMConstants.TWO_STAGE) {
                StringBuffer msg = new StringBuffer();
                msg.append("Please note:\n\n");
                msg
                        .append("Subset restrictions are only taken into account for estimation\n");
                msg
                        .append("if the TWO STAGE estimation procedure is selected.\n");
                msg.append("The current estimation method is " + estString
                        + ".\n");
                msg
                        .append("But it is still possible to apply a search routine.");

                StdMessages.infoGeneral(msg.toString());
            }

        }

    }
}