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

import java.awt.CardLayout;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.apache.log4j.Logger;

import com.jstatcom.component.AutoEnableMenu;
import com.jstatcom.model.JSCInt;
import com.jstatcom.model.ModelFrame;
import com.jstatcom.model.SymbolEvent;
import com.jstatcom.model.SymbolEventTypes;
import com.jstatcom.model.SymbolListener;
import com.jstatcom.model.SymbolTable;
import com.jstatcom.project.ProjectState;
import com.jstatcom.ts.Selection;


/**
 * The main frame for the STR analysis that binds all panels together.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */

public final class STR_Frame extends ModelFrame {
    private static final Logger log = Logger.getLogger(STR_Frame.class);

    private CardLayout cardLayout = null;

    private JPanel ivjInternalFrameContentPane = null;

    private STR_Diagnos ivjSTR_Diagnos = null;

    private STR_Estimate ivjSTR_Estimate = null;

    private STR_GraphicalAnalysis ivjSTR_GraphicalAnalysis = null;

    private STR_GridSearch ivjSTR_Grid = null;

    private STR_SelectAR ivjSTR_SelectAR = null;

    private STR_SubsetAR ivjSTR_SubsetAR = null;

    private STR_TestNonlinAlt ivjSTR_TestNonlinAlt = null;

    private JMenuBar ivjSTR_FrameJMenuBar = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private JMenuItem ivjSelectMItem = null;

    private JMenuItem ivjSubsetMItem = null;

    private JMenuItem ivjLinTestMItem = null;

    private AutoEnableMenu ivjCheckMenu = null;

    private AutoEnableMenu ivjEstMenu = null;

    private JMenuItem ivjEstMItem = null;

    private JMenuItem ivjGridMItem = null;

    private JMenu ivjSpecMenu = null;

    private JMenuItem ivjDiagnosMItem = null;

    private JMenuItem ivjGraphMItem = null;

    class IvjEventHandler implements java.awt.event.ActionListener {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == STR_Frame.this.getSubsetMItem())
                connEtoC2();
            if (e.getSource() == STR_Frame.this.getLinTestMItem())
                connEtoC3();
            if (e.getSource() == STR_Frame.this.getSelectMItem())
                connEtoC1();
            if (e.getSource() == STR_Frame.this.getGridMItem())
                connEtoC4();
            if (e.getSource() == STR_Frame.this.getEstMItem())
                connEtoC5();
            if (e.getSource() == STR_Frame.this.getDiagnosMItem())
                connEtoC6();
            if (e.getSource() == STR_Frame.this.getGraphMItem())
                connEtoC7();
        };
    };

    /**
     * NonParam constructor comment.
     */
    public STR_Frame() {
        super();
        initialize();
    }

    /**
     * connEtoC1: (SelectMItem.action. -->
     * STR_Frame.selectMItem_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC1() {
        try {
            // user code begin {1}
            // user code end
            this.selectMItem_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC2: (SubsetMItem.action. -->
     * STR_Frame.subsetMItem_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC2() {
        try {
            // user code begin {1}
            // user code end
            this.subsetMItem_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC3: (LinTestMItem.action. -->
     * STR_Frame.linTestMItem_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC3() {
        try {
            // user code begin {1}
            // user code end
            this.linTestMItem_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC4: (GridMItem.action. --> STR_Frame.gridMItem_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC4() {
        try {
            // user code begin {1}
            // user code end
            this.gridMItem_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC5: (EstMItem.action. --> STR_Frame.estMItem_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC5() {
        try {
            // user code begin {1}
            // user code end
            this.estMItem_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC6: (DiagnosMItem.action. -->
     * STR_Frame.diagnosMItem_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC6() {
        try {
            // user code begin {1}
            // user code end
            this.diagnosMItem_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC7: (GraphMItem.action. --> STR_Frame.graphMItem_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC7() {
        try {
            // user code begin {1}
            // user code end
            this.graphMItem_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * Comment
     */
    private void diagnosMItem_ActionEvents() {
        getCardLayout().show(getInternalFrameContentPane(),
                getSTR_Diagnos().getName());
    }

    /**
     * Comment
     */
    private void estMItem_ActionEvents() {
        getCardLayout().show(getInternalFrameContentPane(),
                getSTR_Estimate().getName());
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
     * Return the CheckMenu property value.
     * 
     * @return com.jstatcom.component.AutoEnableMenu
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private com.jstatcom.component.AutoEnableMenu getCheckMenu() {
        if (ivjCheckMenu == null) {
            try {
                ivjCheckMenu = new com.jstatcom.component.AutoEnableMenu();
                ivjCheckMenu.setName("CheckMenu");
                ivjCheckMenu.setText("Model Checking");
                ivjCheckMenu.setEnabled(false);
                ivjCheckMenu.add(getDiagnosMItem());
                ivjCheckMenu.add(getGraphMItem());
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjCheckMenu;
    }

    /**
     * Return the DiagnosMItem property value.
     * 
     * @return javax.swing.JMenuItem
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JMenuItem getDiagnosMItem() {
        if (ivjDiagnosMItem == null) {
            try {
                ivjDiagnosMItem = new javax.swing.JMenuItem();
                ivjDiagnosMItem.setName("DiagnosMItem");
                ivjDiagnosMItem.setText("Misspecification Tests");
                ivjDiagnosMItem.setEnabled(false);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjDiagnosMItem;
    }

    /**
     * Return the EstMenu property value.
     * 
     * @return com.jstatcom.component.AutoEnableMenu
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private com.jstatcom.component.AutoEnableMenu getEstMenu() {
        if (ivjEstMenu == null) {
            try {
                ivjEstMenu = new com.jstatcom.component.AutoEnableMenu();
                ivjEstMenu.setName("EstMenu");
                ivjEstMenu.setText("Estimation");
                ivjEstMenu.setEnabled(false);
                ivjEstMenu.add(getGridMItem());
                ivjEstMenu.add(getEstMItem());
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjEstMenu;
    }

    /**
     * Return the EstMItem property value.
     * 
     * @return javax.swing.JMenuItem
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JMenuItem getEstMItem() {
        if (ivjEstMItem == null) {
            try {
                ivjEstMItem = new javax.swing.JMenuItem();
                ivjEstMItem.setName("EstMItem");
                ivjEstMItem.setText("Estimate STR Model");
                ivjEstMItem.setEnabled(false);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjEstMItem;
    }

    private javax.swing.JPanel getInternalFrameContentPane() {
        if (ivjInternalFrameContentPane == null) {
            try {
                ivjInternalFrameContentPane = new javax.swing.JPanel();
                ivjInternalFrameContentPane.setName("InternalFrameContentPane");
                ivjInternalFrameContentPane
                        .setLayout(new java.awt.CardLayout());
                getInternalFrameContentPane().add(getSTR_SelectAR(),
                        getSTR_SelectAR().getName());
                getInternalFrameContentPane().add(getSTR_SubsetAR(),
                        getSTR_SubsetAR().getName());
                getInternalFrameContentPane().add(getSTR_TestNonlinAlt(),
                        getSTR_TestNonlinAlt().getName());
                getInternalFrameContentPane().add(getSTR_Grid(),
                        getSTR_Grid().getName());
                getInternalFrameContentPane().add(getSTR_Estimate(),
                        getSTR_Estimate().getName());
                getInternalFrameContentPane().add(getSTR_Diagnos(),
                        getSTR_Diagnos().getName());
                getInternalFrameContentPane().add(getSTR_GraphicalAnalysis(),
                        getSTR_GraphicalAnalysis().getName());
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjInternalFrameContentPane;
    }

    /**
     * Return the GraphMItem property value.
     * 
     * @return javax.swing.JMenuItem
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JMenuItem getGraphMItem() {
        if (ivjGraphMItem == null) {
            try {
                ivjGraphMItem = new javax.swing.JMenuItem();
                ivjGraphMItem.setName("GraphMItem");
                ivjGraphMItem.setText("Graphical Analysis");
                ivjGraphMItem.setEnabled(false);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjGraphMItem;
    }

    /**
     * Return the GridMItem property value.
     * 
     * @return javax.swing.JMenuItem
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JMenuItem getGridMItem() {
        if (ivjGridMItem == null) {
            try {
                ivjGridMItem = new javax.swing.JMenuItem();
                ivjGridMItem.setName("GridMItem");
                ivjGridMItem.setText("Grid Search for Startvalues");
                ivjGridMItem.setEnabled(false);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjGridMItem;
    }

    /**
     * Return the LinTestMItem property value.
     * 
     * @return javax.swing.JMenuItem
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JMenuItem getLinTestMItem() {
        if (ivjLinTestMItem == null) {
            try {
                ivjLinTestMItem = new javax.swing.JMenuItem();
                ivjLinTestMItem.setName("LinTestMItem");
                ivjLinTestMItem.setText("Test Linear/STR");
                ivjLinTestMItem.setEnabled(false);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjLinTestMItem;
    }

    /**
     * Return the SelectMItem property value.
     * 
     * @return javax.swing.JMenuItem
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JMenuItem getSelectMItem() {
        if (ivjSelectMItem == null) {
            try {
                ivjSelectMItem = new javax.swing.JMenuItem();
                ivjSelectMItem.setName("SelectMItem");
                ivjSelectMItem.setText("Select Model");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSelectMItem;
    }

    /**
     * Return the SpecMenu property value.
     * 
     * @return javax.swing.JMenu
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JMenu getSpecMenu() {
        if (ivjSpecMenu == null) {
            try {
                ivjSpecMenu = new javax.swing.JMenu();
                ivjSpecMenu.setName("SpecMenu");
                ivjSpecMenu.setText("Specification");
                ivjSpecMenu.add(getSelectMItem());
                ivjSpecMenu.add(getSubsetMItem());
                ivjSpecMenu.add(getLinTestMItem());
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSpecMenu;
    }

    /**
     * Return the StrMisspecTests property value.
     * 
     * @return program.frame.str.STR_Diagnos
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private STR_Diagnos getSTR_Diagnos() {
        if (ivjSTR_Diagnos == null) {
            try {
                ivjSTR_Diagnos = new de.jmulti.str.STR_Diagnos();
                ivjSTR_Diagnos.setName("STR_Diagnos");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSTR_Diagnos;
    }

    /**
     * Return the EstimateStar property value.
     * 
     * @return program.frame.str.STR_Estimate
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private STR_Estimate getSTR_Estimate() {
        if (ivjSTR_Estimate == null) {
            try {
                ivjSTR_Estimate = new de.jmulti.str.STR_Estimate();
                ivjSTR_Estimate.setName("STR_Estimate");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSTR_Estimate;
    }

    /**
     * Return the STR_FrameJMenuBar property value.
     * 
     * @return javax.swing.JMenuBar
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JMenuBar getSTR_FrameJMenuBar() {
        if (ivjSTR_FrameJMenuBar == null) {
            try {
                ivjSTR_FrameJMenuBar = new javax.swing.JMenuBar();
                ivjSTR_FrameJMenuBar.setName("STR_FrameJMenuBar");
                ivjSTR_FrameJMenuBar.add(getSpecMenu());
                ivjSTR_FrameJMenuBar.add(getEstMenu());
                ivjSTR_FrameJMenuBar.add(getCheckMenu());
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSTR_FrameJMenuBar;
    }

    /**
     * Return the StrModelCheck property value.
     * 
     * @return program.frame.str.STR_GraphicalAnalysis
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private STR_GraphicalAnalysis getSTR_GraphicalAnalysis() {
        if (ivjSTR_GraphicalAnalysis == null) {
            try {
                ivjSTR_GraphicalAnalysis = new de.jmulti.str.STR_GraphicalAnalysis();
                ivjSTR_GraphicalAnalysis.setName("STR_GraphicalAnalysis");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSTR_GraphicalAnalysis;
    }

    /**
     * Return the StrGrid property value.
     * 
     * @return program.frame.str.STR_GridSearch
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private STR_GridSearch getSTR_Grid() {
        if (ivjSTR_Grid == null) {
            try {
                ivjSTR_Grid = new de.jmulti.str.STR_GridSearch();
                ivjSTR_Grid.setName("STR_Grid");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSTR_Grid;
    }

    /**
     * Return the SelectAR property value.
     * 
     * @return program.frame.str.STR_SelectAR
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private STR_SelectAR getSTR_SelectAR() {
        if (ivjSTR_SelectAR == null) {
            try {
                ivjSTR_SelectAR = new de.jmulti.str.STR_SelectAR();
                ivjSTR_SelectAR.setName("STR_SelectAR");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSTR_SelectAR;
    }

    /**
     * Return the SubsetAR property value.
     * 
     * @return program.frame.str.STR_SubsetAR
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private STR_SubsetAR getSTR_SubsetAR() {
        if (ivjSTR_SubsetAR == null) {
            try {
                ivjSTR_SubsetAR = new de.jmulti.str.STR_SubsetAR();
                ivjSTR_SubsetAR.setName("STR_SubsetAR");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSTR_SubsetAR;
    }

    /**
     * Return the TestNonlinearAlternative property value.
     * 
     * @return program.frame.str.STR_TestNonlinAlt
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private STR_TestNonlinAlt getSTR_TestNonlinAlt() {
        if (ivjSTR_TestNonlinAlt == null) {
            try {
                ivjSTR_TestNonlinAlt = new de.jmulti.str.STR_TestNonlinAlt();
                ivjSTR_TestNonlinAlt.setName("STR_TestNonlinAlt");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSTR_TestNonlinAlt;
    }

    /**
     * Return the SubsetMItem property value.
     * 
     * @return javax.swing.JMenuItem
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JMenuItem getSubsetMItem() {
        if (ivjSubsetMItem == null) {
            try {
                ivjSubsetMItem = new javax.swing.JMenuItem();
                ivjSubsetMItem.setName("SubsetMItem");
                ivjSubsetMItem.setText("Specify Subset Restrictions");
                ivjSubsetMItem.setEnabled(false);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSubsetMItem;
    }

    /**
     * Comment
     */
    private void graphMItem_ActionEvents() {
        getCardLayout().show(getInternalFrameContentPane(),
                getSTR_GraphicalAnalysis().getName());
    }

    /**
     * Comment
     */
    private void gridMItem_ActionEvents() {
        getCardLayout().show(getInternalFrameContentPane(),
                getSTR_Grid().getName());
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
        getSubsetMItem().addActionListener(ivjEventHandler);
        getLinTestMItem().addActionListener(ivjEventHandler);
        getSelectMItem().addActionListener(ivjEventHandler);
        getGridMItem().addActionListener(ivjEventHandler);
        getEstMItem().addActionListener(ivjEventHandler);
        getDiagnosMItem().addActionListener(ivjEventHandler);
        getGraphMItem().addActionListener(ivjEventHandler);
    }

    /**
     * Initialize the class.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initialize() {
        try {
            // user code begin {1}
            // user code end
            setName("STR_Frame");
            setJMenuBar(getSTR_FrameJMenuBar());
            setPreferredSize(new java.awt.Dimension(650, 420));
            setSize(720, 500);
            setTitle("Smooth Transition Regression");
            setContentPane(getInternalFrameContentPane());
            initConnections();
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        // user code begin {2}
        global().get(STR_Constants.STR_endData).addSymbolListener(
                new SymbolListener() {
                    public void valueChanged(SymbolEvent evt) {
                        setDataBoundMenusEnabled(!evt.isSourceEmpty());
                    }
                }, SymbolEventTypes.EMPTY_STATE);

        global().get(STR_Constants.STR_RTZ).addSymbolListener(
                new SymbolListener() {
                    public void valueChanged(SymbolEvent evt) {
                        setResidsBoundMenusEnabled(!evt.isSourceEmpty());
                    }
                }, SymbolEventTypes.EMPTY_STATE);

        // Make sure that the select model panel is displayed first.
        selectMItem_ActionEvents();
        URL url = getClass().getResource("/images/str.gif");
        if (url != null)
            setFrameIcon(new ImageIcon(url));

        // user code end

    }

    /**
     * Comment
     */
    private void linTestMItem_ActionEvents() {
        getCardLayout().show(getInternalFrameContentPane(),
                getSTR_TestNonlinAlt().getName());
    }

    /**
     * Comment
     */
    private void selectMItem_ActionEvents() {
        getCardLayout().show(getInternalFrameContentPane(),
                getSTR_SelectAR().getName());

    }

    /**
     * Set all menus enabled/disabled that depend on the availibility of a
     * selected model. If the menus are disabled, the model selection is
     * displayed.
     * 
     * @param enabled
     *            boolean
     */
    private void setDataBoundMenusEnabled(boolean enabled) {

        if (!enabled)
            selectMItem_ActionEvents();

        getSubsetMItem().setEnabled(enabled);
        getLinTestMItem().setEnabled(enabled);
        getGridMItem().setEnabled(enabled);
        getEstMItem().setEnabled(enabled);

    }

    /**
     * Set all menus enabled/disabled that depend on the availibility of
     * estimation residuals.
     * 
     * @param enabled
     *            boolean
     */
    private void setResidsBoundMenusEnabled(boolean enabled) {

        getDiagnosMItem().setEnabled(enabled);
        getGraphMItem().setEnabled(enabled);
    }

    /**
     * Comment
     */
    private void subsetMItem_ActionEvents() {
        getCardLayout().show(getInternalFrameContentPane(),
                getSTR_SubsetAR().getName());
    }

    /**
     * Sets the state of this module frame from a <code>ProjectState</code>
     * instance.
     */
    public void setState(ProjectState state) {
        if (state instanceof STR_ProjectState) {
            STR_ProjectState strState = (STR_ProjectState) state;

            SymbolTable sglobal = strState.getSymbolTableGlobal();
            Selection sel = strState.getSelection();
            boolean isSeas = (strState.getIsSeas() == null
                    || strState.getIsSeas().isEmpty() || strState.getIsSeas()
                    .intVal() == 0) ? false : true;
            // sets selection and tables
            getCardLayout().show(getInternalFrameContentPane(),
                    getSTR_SelectAR().getName());
            getSTR_SelectAR().getSelector().setSelection(sel);
            getSTR_SelectAR().setSeasSelected(isSeas);
            global().setSymbolTable(sglobal);
            sglobal.clear();

        } else
            throw new IllegalArgumentException(
                    "Argument not a valid project state for this module.");
    }

    /**
     * Gets the state of this module frame as a <code>ProjectState</code>
     * instance.
     */
    public ProjectState getState() {
        STR_ProjectState state = new STR_ProjectState();
        state.setSymbolTableGlobal(global());
        state.setSelection(getSTR_SelectAR().getSelector().getSelection());
        state.setIsSeas(new JSCInt("IsSeasSel", getSTR_SelectAR()
                .isSeasSelected()));
        return state;
    }

}