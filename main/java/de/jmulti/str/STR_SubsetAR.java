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

package de.jmulti.str;

import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import com.jstatcom.equation.EqPanel;
import com.jstatcom.equation.EqTermAR;
import com.jstatcom.equation.EqTermDefault;
import com.jstatcom.equation.EqTermLHS;
import com.jstatcom.model.ModelPanel;
import com.jstatcom.table.JSCCellRendererTypes;
import com.jstatcom.table.JSCMouseListenerTypes;


/**
 * Panel to specify subset restrictions in STR model.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */

final class STR_SubsetAR extends ModelPanel {
    private static final Logger log = Logger.getLogger(STR_SubsetAR.class);

    private EqPanel ivjEquationPanel = null;

    private EqTermDefault ivjConst = null;

    private EqTermLHS ivjLHS_y = null;

    private EqTermAR ivjEndogenousLagged = null;

    private EqTermAR ivjExogenousLagged = null;

    private EqTermDefault ivjDeterministic = null;

    private JScrollPane ivjJScrollPane1 = null;

    /**
     * SubsetAR constructor comment.
     */
    public STR_SubsetAR() {
        super();
        initialize();
    }

    private EqTermDefault getConst() {
        if (ivjConst == null) {
            try {
                ivjConst = new EqTermDefault();
                ivjConst.setName("Const");
                ivjConst.setShowingTimeIndex(false);
                ivjConst.setTablePopup(null);
                ivjConst.setBounds(44, 7, 20, 20);
                ivjConst
                        .setSymbolNameVariables(STR_Constants.STR_CONSTNAME.name);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjConst;
    }

    private EqTermDefault getDeterministic() {
        if (ivjDeterministic == null) {
            try {
                ivjDeterministic = new EqTermDefault();
                ivjDeterministic.setName("Deterministic");
                ivjDeterministic.setShowingTimeIndex(false);
                ivjDeterministic.setTablePopup(null);
                ivjDeterministic.setBounds(117, 15, 20, 20);
                ivjDeterministic
                        .setSymbolNameVariables(STR_Constants.STR_Nd.name);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjDeterministic;
    }

    private EqTermAR getEndogenousLagged() {
        if (ivjEndogenousLagged == null) {
            try {
                ivjEndogenousLagged = new EqTermAR();
                ivjEndogenousLagged.setName("EndogenousLagged");
                ivjEndogenousLagged
                        .setSymbolNameVariables(STR_Constants.STR_Ny.name);
                ivjEndogenousLagged
                        .setRendererCoeff(JSCCellRendererTypes.SUBSET_01);
                ivjEndogenousLagged.setStartLag(1);
                ivjEndogenousLagged
                        .setSymbolNameLags(STR_Constants.STR_py.name);
                ivjEndogenousLagged.setTablePopup(null);
                ivjEndogenousLagged
                        .setMouseListenerCoeff(JSCMouseListenerTypes.SUBSET_01);
                ivjEndogenousLagged.setBounds(62, 11, 20, 20);
                ivjEndogenousLagged.setEditable(false);
                ivjEndogenousLagged
                        .setSymbolNameCoeff(STR_Constants.STR_Cy.name);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjEndogenousLagged;
    }

    private EqPanel getEquationPanel() {
        if (ivjEquationPanel == null) {
            try {
                ivjEquationPanel = new EqPanel();
                ivjEquationPanel.setName("EquationPanel");
                ivjEquationPanel.setBounds(0, 0, 10, 10);
                getEquationPanel().add(getLHS_y(), getLHS_y().getName());
                getEquationPanel().add(getConst(), getConst().getName());
                getEquationPanel().add(getEndogenousLagged(),
                        getEndogenousLagged().getName());
                getEquationPanel().add(getExogenousLagged(),
                        getExogenousLagged().getName());
                getEquationPanel().add(getDeterministic(),
                        getDeterministic().getName());
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

    private EqTermAR getExogenousLagged() {
        if (ivjExogenousLagged == null) {
            try {
                ivjExogenousLagged = new EqTermAR();
                ivjExogenousLagged.setName("ExogenousLagged");
                ivjExogenousLagged
                        .setSymbolNameVariables(STR_Constants.STR_Nx.name);
                ivjExogenousLagged
                        .setRendererCoeff(JSCCellRendererTypes.SUBSET_01);
                ivjExogenousLagged.setSymbolNameLags(STR_Constants.STR_pz.name);
                ivjExogenousLagged.setTablePopup(null);
                ivjExogenousLagged
                        .setMouseListenerCoeff(JSCMouseListenerTypes.SUBSET_01);
                ivjExogenousLagged.setBounds(91, 12, 20, 20);
                ivjExogenousLagged.setEditable(false);
                ivjExogenousLagged
                        .setSymbolNameCoeff(STR_Constants.STR_Cx.name);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjExogenousLagged;
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

    private EqTermLHS getLHS_y() {
        if (ivjLHS_y == null) {
            try {
                ivjLHS_y = new EqTermLHS();
                ivjLHS_y.setName("LHS_y");
                ivjLHS_y.setTablePopup(null);
                ivjLHS_y.setBounds(25, 33, 20, 20);
                ivjLHS_y.setSymbolNameVariables(STR_Constants.STR_Ny.name);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjLHS_y;
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
     * Initialize the class.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initialize() {
        try {
            // user code begin {1}
            // user code end
            setName("SubsetAR");
            setLayout(new javax.swing.BoxLayout(this,
                    javax.swing.BoxLayout.X_AXIS));
            setSize(589, 338);
            add(getJScrollPane1(), getJScrollPane1().getName());
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}
        TitledBorder title = new TitledBorder(new BevelBorder(
                BevelBorder.LOWERED),
                "Specify Subset Restrictions for AR Part", TitledBorder.RIGHT,
                TitledBorder.TOP);
        this.setBorder(title);

        // user code end
    }
}