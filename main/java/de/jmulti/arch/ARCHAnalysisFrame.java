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

import java.awt.CardLayout;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.apache.log4j.Logger;

import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.ModelFrame;
import com.jstatcom.model.SymbolTable;
import com.jstatcom.project.ProjectState;
import com.jstatcom.ts.Selection;

/**
 * An internal frame containing the univariate and multivariate ARCH analysis
 * panels.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
public final class ARCHAnalysisFrame extends ModelFrame {
    private static final Logger log = Logger.getLogger(ARCHAnalysisFrame.class);

    private java.awt.CardLayout cardLayout = null;

    private JPanel ivjInternalFrameContentPane = null;

    private JMenuBar ivjARCHAnalysisFrameJMenuBar = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private JMenuItem ivjUnivarMenuItem = null;

    private JMenuItem ivjMultivarMenuItem = null;

    private StandAloneARCHPanel ivjARCHPanel = null;

    private StandAloneMultARCHPanel ivjMultARCHPanel = null;

    private JMenu ivjSelectMenu = null;

    class IvjEventHandler implements java.awt.event.ActionListener {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == ARCHAnalysisFrame.this.getUnivarMenuItem())
                connEtoC1();
            if (e.getSource() == ARCHAnalysisFrame.this.getMultivarMenuItem())
                connEtoC2();
        };
    };

    /**
     * ARCHAnalysisFrame constructor comment.
     */
    public ARCHAnalysisFrame() {
        super();
        initialize();
    }

    /**
     * connEtoC1: (UnivarMenuItem.action. -->
     * ARCHAnalysisFrame.univarMenuItem_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC1() {
        try {
            // user code begin {1}
            // user code end
            this.univarMenuItem_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (java.lang.Exception ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC2: (MultivarMenuItem.action. -->
     * ARCHAnalysisFrame.multivarMenuItem_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC2() {
        try {
            // user code begin {1}
            // user code end
            this.multivarMenuItem_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (java.lang.Exception ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * Return the ARCHAnalysisFrameJMenuBar property value.
     * 
     * @return javax.swing.JMenuBar
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JMenuBar getARCHAnalysisFrameJMenuBar() {
        if (ivjARCHAnalysisFrameJMenuBar == null) {
            try {
                ivjARCHAnalysisFrameJMenuBar = new javax.swing.JMenuBar();
                ivjARCHAnalysisFrameJMenuBar
                        .setName("ARCHAnalysisFrameJMenuBar");
                ivjARCHAnalysisFrameJMenuBar.add(getSelectMenu());
                // user code begin {1}
                // user code end
            } catch (java.lang.Exception ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjARCHAnalysisFrameJMenuBar;
    }

    /**
     * Return the StartPanel property value.
     * 
     * @return program.frame.arch.StandAloneARCHPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private StandAloneARCHPanel getARCHPanel() {
        if (ivjARCHPanel == null) {
            try {
                ivjARCHPanel = new de.jmulti.arch.StandAloneARCHPanel();
                ivjARCHPanel.setName("ARCHPanel");
                // user code begin {1}
                // user code end
            } catch (java.lang.Exception ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjARCHPanel;
    }

    /**
     * description
     * 
     * @return java.awt.CardLayout
     */
    private java.awt.CardLayout getCardLayout() {
        if (cardLayout == null)
            cardLayout = (CardLayout) getInternalFrameContentPane().getLayout();
        return cardLayout;
    }

    /**
     * Return the InternalFrameContentPane property value.
     * 
     * @return javax.swing.JPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JPanel getInternalFrameContentPane() {
        if (ivjInternalFrameContentPane == null) {
            try {
                ivjInternalFrameContentPane = new javax.swing.JPanel();
                ivjInternalFrameContentPane.setName("InternalFrameContentPane");
                ivjInternalFrameContentPane
                        .setLayout(new java.awt.CardLayout());
                getInternalFrameContentPane().add(getARCHPanel(),
                        getARCHPanel().getName());
                getInternalFrameContentPane().add(getMultARCHPanel(),
                        getMultARCHPanel().getName());
                // user code begin {1}
                // user code end
            } catch (java.lang.Exception ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjInternalFrameContentPane;
    }

    /**
     * Return the MultARCHPanel property value.
     * 
     * @return program.frame.arch.StandAloneMultARCHPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private StandAloneMultARCHPanel getMultARCHPanel() {
        if (ivjMultARCHPanel == null) {
            try {
                ivjMultARCHPanel = new de.jmulti.arch.StandAloneMultARCHPanel();
                ivjMultARCHPanel.setName("MultARCHPanel");
                // user code begin {1}
                // user code end
            } catch (java.lang.Exception ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjMultARCHPanel;
    }

    /**
     * Return the MultivarMenuItem property value.
     * 
     * @return javax.swing.JMenuItem
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JMenuItem getMultivarMenuItem() {
        if (ivjMultivarMenuItem == null) {
            try {
                ivjMultivarMenuItem = new javax.swing.JMenuItem();
                ivjMultivarMenuItem.setName("MultivarMenuItem");
                ivjMultivarMenuItem.setText("Multivariate GARCH Analysis");
                // user code begin {1}
                // user code end
            } catch (java.lang.Exception ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjMultivarMenuItem;
    }

    /**
     * Return the SelectionMenu property value.
     * 
     * @return javax.swing.JMenu
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JMenu getSelectMenu() {
        if (ivjSelectMenu == null) {
            try {
                ivjSelectMenu = new javax.swing.JMenu();
                ivjSelectMenu.setName("SelectMenu");
                ivjSelectMenu.setText("General Specification");
                ivjSelectMenu.add(getUnivarMenuItem());
                ivjSelectMenu.add(getMultivarMenuItem());
                // user code begin {1}
                // user code end
            } catch (java.lang.Exception ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSelectMenu;
    }

    /**
     * Return the UnivarMenuItem property value.
     * 
     * @return javax.swing.JMenuItem
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JMenuItem getUnivarMenuItem() {
        if (ivjUnivarMenuItem == null) {
            try {
                ivjUnivarMenuItem = new javax.swing.JMenuItem();
                ivjUnivarMenuItem.setName("UnivarMenuItem");
                ivjUnivarMenuItem.setText("Univariate ARCH Analysis");
                // user code begin {1}
                // user code end
            } catch (java.lang.Exception ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjUnivarMenuItem;
    }

    /**
     * Called whenever the part throws an exception.
     * 
     * @param exception
     *            java.lang.Exception
     */
    private void handleException(java.lang.Exception exception) {

        log.error("Unhandled Exception", exception);
    }

    /**
     * Initializes connections
     * 
     * @exception java.lang.Exception
     *                The exception description.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initConnections() throws java.lang.Exception {
        // user code begin {1}
        // user code end
        getUnivarMenuItem().addActionListener(ivjEventHandler);
        getMultivarMenuItem().addActionListener(ivjEventHandler);
    }

    /**
     * Initialize the class.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initialize() {
        try {
            // user code begin {1}
            // user code end
            setName("ARCHAnalysisFrame");
            setJMenuBar(getARCHAnalysisFrameJMenuBar());
            setSize(700, 420);
            setMinimumSize(new java.awt.Dimension(700, 420));
            setTitle("ARCH Analysis");
            setContentPane(getInternalFrameContentPane());
            initConnections();
        } catch (java.lang.Exception ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}
        setSize(700, 500);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        URL url = getClass().getResource("/images/arch.gif");
        if (url != null)
            setFrameIcon(new ImageIcon(url));
        // user code end
    }

    /**
     * Comment
     */
    private void multivarMenuItem_ActionEvents() {
        getCardLayout().show(getInternalFrameContentPane(),
                getMultARCHPanel().getName());
        return;
    }

    /**
     * Comment
     */
    private void univarMenuItem_ActionEvents() {
        getCardLayout().show(getInternalFrameContentPane(),
                getARCHPanel().getName());
        return;
    }

    /**
     * Sets the state of this module frame from a <code>ProjectState</code>
     * instance.
     */
    public void setState(ProjectState state) {
        if (state instanceof ARCHProjectState) {
            ARCHProjectState archState = (ARCHProjectState) state;

            SymbolTable sglobal = archState.getSymbolTableGlobal();
            SymbolTable smult = archState.getSymbolTableMult();
            SymbolTable suni = archState.getSymbolTableUni();
            Selection multSel = archState.getSelectionMult();
            Selection uniSel = archState.getSelectionUni();
            JSCNArray uniModelSel = archState.getUniModelSel();

            univarMenuItem_ActionEvents();
            // sets selection and tables
            getARCHPanel().getSelectPanel().setSelection(uniSel);
            getMultARCHPanel().getSelectPanel().setSelection(multSel);

            global().setSymbolTable(sglobal);
            getARCHPanel().getARCHAnalysisPanel().local().setSymbolTable(suni);
            getMultARCHPanel().getMultARCHAnalysisPanel().local()
                    .setSymbolTable(smult);

            getARCHPanel().getARCHAnalysisPanel().setUniModelSel(uniModelSel);

            sglobal.clear();
            smult.clear();
            suni.clear();
        } else
            throw new IllegalArgumentException(
                    "Argument not a valid project state for this module.");
    }

    /**
     * Gets the state of this module frame as a <code>ProjectState</code>
     * instance.
     */
    public ProjectState getState() {
        ARCHProjectState state = new ARCHProjectState();

        // store only empty symbol tables
        state.setSymbolTableGlobal(new SymbolTable(global().NAME));
        // get selection and symbol tables from both panels
        state.setSymbolTableUni(new SymbolTable(getARCHPanel()
                .getARCHAnalysisPanel().local().NAME));
        state.setSelectionUni(getARCHPanel().getSelectPanel().getSelection());
        state.setUniModelSel(getARCHPanel().getARCHAnalysisPanel()
                .getUniModelSel());

        state.setSymbolTableMult(new SymbolTable(getMultARCHPanel()
                .getMultARCHAnalysisPanel().local().NAME));
        state.setSelectionMult(getMultARCHPanel().getSelectPanel()
                .getSelection());
        return state;
    }

}