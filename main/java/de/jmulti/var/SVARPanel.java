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

import java.awt.CardLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import com.jstatcom.model.JSCString;
import com.jstatcom.model.ModelPanel;
import com.jstatcom.model.SymbolEvent;
import com.jstatcom.model.SymbolListener;
import com.jstatcom.model.SymbolTable;

/**
 * The panel to choose from different SVAR model types. It binds all estimation
 * panels together.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
public final class SVARPanel extends ModelPanel {
    private static final Logger log = Logger.getLogger(SVARPanel.class);

    private java.awt.CardLayout cardLayout = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private JComboBox ivjSVARModelCombo = null;

    private JPanel ivjCardLayoutPanel = null;

    private JPanel ivjJPanel1 = null;

    private SVARBlanQuahPanel ivjSVARBlanQuaPanel = null;

    private SVARABModelPanel ivjSVARABModelPanel = null;

    private JLabel ivjLastModelLabel = null;

    class IvjEventHandler implements java.awt.event.ActionListener {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == SVARPanel.this.getSVARModelCombo())
                connEtoC1();
        };
    };

    /**
     * SVARPanel constructor comment.
     */
    public SVARPanel() {
        super();
        initialize();
    }

    /**
     * connEtoC1: (SVARModelCombo.action. -->
     * SVARPanel.sVARModelCombo_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC1() {
        try {
            // user code begin {1}
            // user code end
            this.sVARModelCombo_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * description
     * 
     * @return java.awt.CardLayout
     */
    private CardLayout getCardLayout() {
        if (cardLayout == null)
            cardLayout = (CardLayout) getCardLayoutPanel().getLayout();
        return cardLayout;
    }

    /**
     * Return the JPanel1 property value.
     * 
     * @return javax.swing.JPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JPanel getCardLayoutPanel() {
        if (ivjCardLayoutPanel == null) {
            try {
                ivjCardLayoutPanel = new javax.swing.JPanel();
                ivjCardLayoutPanel.setName("CardLayoutPanel");
                ivjCardLayoutPanel.setPreferredSize(new java.awt.Dimension(300,
                        202));
                ivjCardLayoutPanel.setLayout(new java.awt.CardLayout());
                getCardLayoutPanel().add(getSVARABModelPanel(),
                        getSVARABModelPanel().getName());
                getCardLayoutPanel().add(getSVARBlanQuaPanel(),
                        getSVARBlanQuaPanel().getName());
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjCardLayoutPanel;
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
                ivjJPanel1.setLayout(new javax.swing.BoxLayout(getJPanel1(),
                        javax.swing.BoxLayout.X_AXIS));
                getJPanel1().add(getSVARModelCombo(),
                        getSVARModelCombo().getName());
                // user code begin {1}
                getJPanel1().setBorder(
                        new TitledBorder(new BevelBorder(BevelBorder.LOWERED),
                                "Select SVAR Model Type", TitledBorder.RIGHT,
                                TitledBorder.TOP));
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
     * Return the LastModelLabel property value.
     * 
     * @return javax.swing.JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JLabel getLastModelLabel() {
        if (ivjLastModelLabel == null) {
            try {
                ivjLastModelLabel = new javax.swing.JLabel();
                ivjLastModelLabel.setName("LastModelLabel");
                ivjLastModelLabel.setPreferredSize(new java.awt.Dimension(188,
                        20));
                ivjLastModelLabel
                        .setBorder(new BevelBorder(BevelBorder.LOWERED));
                ivjLastModelLabel.setText(" last SVAR model estimated: none");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjLastModelLabel;
    }

    /**
     * Return the SVAR_ABModelPanel property value.
     * 
     * @return de.jmulti.var.SVARABModelPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private SVARABModelPanel getSVARABModelPanel() {
        if (ivjSVARABModelPanel == null) {
            try {
                ivjSVARABModelPanel = new de.jmulti.var.SVARABModelPanel();
                ivjSVARABModelPanel.setName("SVARABModelPanel");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSVARABModelPanel;
    }

    /**
     * Return the SVARBlanQuaPanel property value.
     * 
     * @return de.jmulti.var.SVARBlanQuahPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private SVARBlanQuahPanel getSVARBlanQuaPanel() {
        if (ivjSVARBlanQuaPanel == null) {
            try {
                ivjSVARBlanQuaPanel = new de.jmulti.var.SVARBlanQuahPanel();
                ivjSVARBlanQuaPanel.setName("SVARBlanQuaPanel");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSVARBlanQuaPanel;
    }

    /**
     * Return the SVARModelCombo property value.
     * 
     * @return javax.swing.JComboBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JComboBox getSVARModelCombo() {
        if (ivjSVARModelCombo == null) {
            try {
                ivjSVARModelCombo = new javax.swing.JComboBox();
                ivjSVARModelCombo.setName("SVARModelCombo");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSVARModelCombo;
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
        getSVARModelCombo().addActionListener(ivjEventHandler);
    }

    /**
     * Initialize the class.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initialize() {
        try {
            // user code begin {1}
            // user code end
            setName("SVARPanel");
            setLayout(new java.awt.BorderLayout());
            setSize(618, 441);
            add(getCardLayoutPanel(), "Center");
            add(getJPanel1(), "North");
            add(getLastModelLabel(), "South");
            initConnections();
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}
        getSVARModelCombo().addItem("SVAR AB Model");
        getSVARModelCombo().addItem("SVAR Blanchard-Quah Model");
        getSVARModelCombo().setSelectedIndex(0);

        // Add Listener to display the last SVAR model that was estimated.
        global().get(SVARConstants.lastModel_Def).addSymbolListener(
                new SymbolListener() {
                    public void valueChanged(SymbolEvent evt) {
                        JSCString lastModel = global().get(
                                SVARConstants.lastModel_Def).getJSCString();
                        String lm = "none";
                        if (!lastModel.isEmpty())
                            lm = lastModel.string();
                        getLastModelLabel().setText(
                                " last SVAR model estimated: " + lm);

                    }
                });

        // user code end
    }

    // Add Listener for shown events.
    public void shown(boolean isShown) {
        getSVARABModelPanel().shown(isShown);
        getSVARBlanQuaPanel().shown(isShown);
    }

    /**
     * Shows the respective SVAR model panel.
     */
    private void sVARModelCombo_ActionEvents() {
        if (getSVARModelCombo().getSelectedIndex() == 0)
            getCardLayout().show(getCardLayoutPanel(),
                    getSVARABModelPanel().getName());
        if (getSVARModelCombo().getSelectedIndex() == 1)
            getCardLayout().show(getCardLayoutPanel(),
                    getSVARBlanQuaPanel().getName());

        return;
    }

    /**
     * Gets the symbol table for the AB model panel. Used by project management.
     * 
     * @return
     */
    SymbolTable getABModelPanelTable() {
        return getSVARABModelPanel().local();
    }

    /**
     * Sets the symbol table for the AB model panel. Used by project management.
     * 
     * @param table
     *            symbol table
     */
    void setABModelPanelTable(SymbolTable table) {
        if (table == null)
            throw new IllegalArgumentException("Argument was null.");
        getSVARABModelPanel().local().setSymbolTable(table);
    }
}