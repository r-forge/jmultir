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

package de.jmulti.vecm;

import java.awt.event.ActionEvent;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.WindowConstants;

import org.apache.log4j.Logger;

import com.jstatcom.component.AutoEnableMenu;
import com.jstatcom.component.CardDisplayPanel;
import com.jstatcom.component.CardPanelAction;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.ModelFrame;
import com.jstatcom.model.SymbolTable;
import com.jstatcom.project.ProjectState;
import com.jstatcom.ts.Selection;

import de.jmulti.arch.MultARCHAnalysisPanel;
import de.jmulti.tools.CausalityPanel;
import de.jmulti.tools.FEVDPanel;
import de.jmulti.tools.ForecastPanel;
import de.jmulti.tools.ModelTypes;

/**
 * VECM binds all components for the VECM analysis together. It also manages the
 * data listeners that enable/disable menus according to the available results.
 * This class is a SINGLETON and should be referenced via the
 * <code>getInstance</code> method.
 * 
 * @author A. Benkwitz, <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
public final class VECM {
    private static final Logger log = Logger.getLogger(VECM.class);

    // The variable for the instance.
    private static final VECM vecm = new VECM();

    private CardDisplayPanel cardPanel = null;

    final CardPanelAction action_ecPlot = new CardPanelAction(
            "de.jmulti.vecm.VECMPlotECPanel", getCardPanel(), false,
            "Plot EC Term");

    final CardPanelAction action_stabAnal = new CardPanelAction(
            "de.jmulti.vecm.VECMStabilityPanel", getCardPanel(), false,
            "Stability Analysis");

    final CardPanelAction action_ModelSpecification = new CardPanelAction(
            "de.jmulti.vecm.VECMSpecifyModelPanel", getCardPanel(), true,
            "Specify VEC Model");

    final CardPanelAction action_subsetRestrictionsSecondStage = new CardPanelAction(
            "de.jmulti.vecm.VECMSubsetModelPanel", getCardPanel(), false,
            "Specify Subset Model");

    final CardPanelAction action_waldTestBeta = new CardPanelAction(
            "de.jmulti.vecm.VECMWaldBetaPanel", getCardPanel(), false,
            "Specify and Test Restrictions on Beta");

    final CardPanelAction action_estimatedCoefficients = new CardPanelAction(
            "de.jmulti.vecm.VECMEstCoeffPanel", getCardPanel(), false,
            "Estimation Results");

    final CardPanelAction action_residualAnalysis = new CardPanelAction(
            "de.jmulti.vecm.VECMResidualAnalysis", getCardPanel(), false,
            "Residual Analysis");

    final CardPanelAction action_impulseResponseAnalysis = new CardPanelAction(
            "de.jmulti.vecm.VECMIRAPanel", getCardPanel(), false,
            "Impulse Response Analysis");

    final CardPanelAction action_forecastErrorVarianceDecomposition = new CardPanelAction(
            "de.jmulti.tools.FEVDPanel", getCardPanel(), false,
            "FEV Decomposition");

    final CardPanelAction action_causalityTests = new CardPanelAction(
            "de.jmulti.tools.CausalityPanel", getCardPanel(), false,
            "Causality Tests");

    final CardPanelAction action_Forecasting = new CardPanelAction(
            "de.jmulti.tools.ForecastPanel", getCardPanel(), false,
            "Forecast Variables");

    final CardPanelAction action_svec = new CardPanelAction(
            "de.jmulti.vecm.SVECModelPanel", getCardPanel(), false,
            "Estimate SVEC Model");

    // FEVD SVEC Panel needs to be configured.
    final CardPanelAction action_svecFEVD = new CardPanelAction(
            "de.jmulti.tools.FEVDPanel", getCardPanel(), false, "SVEC FEVD");

    final CardPanelAction action_svecIRA = new CardPanelAction(
            "de.jmulti.vecm.SVECIRAPanel", getCardPanel(), false, "SVEC IRA");

    final CardPanelAction action_multArchAnal = new CardPanelAction(
            "de.jmulti.arch.MultARCHAnalysisPanel", getCardPanel(), false,
            "Multivariate GARCH Analysis of Residuals");

    private ModelFrame internalFrame = null;

    private JMenuBar menuBar = null;

    private AutoEnableMenu menu_specification = null;

    private AutoEnableMenu menu_estimation = null;

    private AutoEnableMenu menu_modelChecking = null;

    private AutoEnableMenu menu_structuralAnalysis = null;

    private AutoEnableMenu menu_forecasting = null;

    private AutoEnableMenu menu_svec = null;

    /**
     * Constructor hidden.
     */
    private VECM() {
        super();

    }

    /**
     * Insert the method's description here. Creation date: (12.12.01 13:27:27)
     * 
     * @return CardDisplayPanel
     */
    private CardDisplayPanel getCardPanel() {
        if (cardPanel == null) {
            cardPanel = new CardDisplayPanel();

        }
        return cardPanel;
    }

    /**
     * Gets the global symbol table.
     * 
     * @return global symbol table
     */
    public SymbolTable getSymbolTable() {
        if (internalFrame != null)
            return internalFrame.global();
        return null;
    }

    /**
     * Returns the SINGLETON instance of VECM.
     * 
     * @return pVECM reference to an instance
     */
    public static VECM getInstance() {
        return vecm;
    }

    /**
     * Initializes the internal frame for VECM analysis.
     * 
     * @return javax.swing.JInternalFrame the initialized instance
     */
    public static ModelFrame getInternalFrame() {
        if (getInstance().internalFrame == null) {

            getInstance().internalFrame = new ModelFrame("VECM Analysis") {
                public String getID() {
                    return "de.jmulti.vecm.VECM";
                }

                /**
                 * Sets the state of this module frame from a
                 * <code>ProjectState</code> instance.
                 */
                public void setState(ProjectState state) {
                    if (state instanceof VECMProjectState) {
                        VECMProjectState vecState = (VECMProjectState) state;

                        SymbolTable table = vecState.getSymbolTableGlobal();
                        Selection sel = vecState.getSelection();
                        JSCNArray detSel = vecState.getDetSelection();
                        SymbolTable svecTable = vecState.getSymbolTableSVEC();
                        JSCNArray ecSel = vecState.getEcSelection();
                        JSCNArray stratSel = vecState.getStratSelection();

                        VECMSpecifyModelPanel specPanel = (VECMSpecifyModelPanel) getInstance().action_ModelSpecification
                                .getComponent();
                        SVECModelPanel svecPanel = (SVECModelPanel) getInstance().action_svec
                                .getComponent();

                        // set spec panel
                        getInstance().action_ModelSpecification
                                .actionPerformed(new ActionEvent(getInstance(),
                                        0, "show selection panel"));
                        try {
                            specPanel.getLagsVECMPanel().getVECMEstStratPanel()
                                    .setProjectLoadOnGoing(true);
                            // set selection
                            if (sel != null)
                                specPanel.getSelectPanel().setSelection(sel);

                            // set table
                            if (table != null) {
                                table.get(VECMConstants.cv_u_hat_Def).clear();
                                global().setSymbolTable(table);
                            }

                            // set deterministics
                            if (detSel != null && !detSel.isEmpty())
                                specPanel.getLagsVECMPanel().setDetSelection(
                                        detSel);

                            // set ec selection
                            if (ecSel != null && !ecSel.isEmpty())
                                specPanel.getLagsVECMPanel().setECSelection(
                                        ecSel);

                            // set strategy
                            if (stratSel != null && !stratSel.isEmpty())
                                specPanel.getLagsVECMPanel()
                                        .getVECMEstStratPanel()
                                        .setStratSelection(stratSel);

                            // svec table
                            if (svecTable != null) {
                                svecTable.get(SVECConstants.matB_Def).clear();
                                svecPanel.local().setSymbolTable(svecTable);
                            }

                        } catch (Throwable ex) {
                            log.error("Problem in setting VECM state", ex);
                        } finally {
                            specPanel.getLagsVECMPanel().getVECMEstStratPanel()
                                    .setProjectLoadOnGoing(false);
                        }
                        table.clear();
                        svecTable.clear();
                        specPanel.getLagsVECMPanel().selectionChanged();
                    } else
                        throw new IllegalArgumentException(
                                "Argument not a valid project state for this module.");
                }

                /**
                 * Gets the state of this module frame as a
                 * <code>ProjectState</code> instance.
                 */
                public ProjectState getState() {
                    VECMProjectState state = new VECMProjectState();

                    VECMSpecifyModelPanel specPanel = (VECMSpecifyModelPanel) getInstance().action_ModelSpecification
                            .getComponent();
                    SVECModelPanel svecPanel = (SVECModelPanel) getInstance().action_svec
                            .getComponent();

                    state.setSelection(specPanel.getSelectPanel()
                            .getSelection());
                    state.setSymbolTableGlobal(global());
                    state.setDetSelection(specPanel.getLagsVECMPanel()
                            .getDetSelection());
                    state.setSymbolTableSVEC(svecPanel.local());
                    state.setEcSelection(specPanel.getLagsVECMPanel()
                            .getECSelection());
                    state.setStratSelection(specPanel.getLagsVECMPanel()
                            .getVECMEstStratPanel().getStratSelection());

                    return state;
                }
            };
            getInstance().internalFrame.setName(getInstance().internalFrame
                    .getTitle());
            getInstance().internalFrame.setContentPane(getInstance()
                    .getCardPanel());
            getInstance().internalFrame.setJMenuBar(getInstance().getMenuBar());
            getInstance().internalFrame.setSize(800, 500);
            getInstance().internalFrame.setMinimumSize(new java.awt.Dimension(
                    800, 500));
            getInstance().internalFrame
                    .setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

            getInstance().internalFrame.setLocation(0, 0);
            URL url = getInstance().getClass().getResource("/images/vec.gif");
            if (url != null)
                getInstance().internalFrame.setFrameIcon(new ImageIcon(url));

            // Brings the selection panel to the front.
            getInstance().action_ModelSpecification
                    .actionPerformed(new ActionEvent(getInstance(), 0,
                            "show selection panel"));

            // Make sure that the data managers are initialized
            getInstance().initDataManagers();
        }
        return getInstance().internalFrame;
    }

    /**
     * Insert the method's description here. Creation date: (06.10.00 10:19:10)
     * 
     * @return javax.swing.JMenu
     */
    private JMenu getMenu_Estimation() {
        if (menu_estimation == null) {
            menu_estimation = new AutoEnableMenu("Estimation");
            menu_estimation.setMnemonic('E');
            menu_estimation.add(action_estimatedCoefficients);
        }
        return menu_estimation;
    }

    /**
     * Insert the method's description here. Creation date: (06.10.00 10:19:10)
     * 
     * @return javax.swing.JMenu
     */
    private JMenu getMenu_Forecasting() {
        if (menu_forecasting == null) {
            menu_forecasting = new AutoEnableMenu("Forecasting");
            menu_forecasting.setMnemonic('F');

            menu_forecasting.add(action_Forecasting);
            ForecastPanel forec = (ForecastPanel) action_Forecasting
                    .getComponent();
            forec.setModelType(ModelTypes.VECM);

        }
        return menu_forecasting;
    }

    /**
     * Insert the method's description here. Creation date: (06.10.00 10:19:10)
     * 
     * @return javax.swing.JMenu
     */
    private JMenu getMenu_ModelChecking() {
        if (menu_modelChecking == null) {
            menu_modelChecking = new AutoEnableMenu("Model Checking");
            menu_modelChecking.setMnemonic('M');
            menu_modelChecking.add(action_ecPlot);
            menu_modelChecking.add(action_residualAnalysis);
            menu_modelChecking.add(action_stabAnal);
            menu_modelChecking.add(action_multArchAnal);
            if (action_multArchAnal.getComponent() instanceof MultARCHAnalysisPanel) {
                MultARCHAnalysisPanel multArch = (MultARCHAnalysisPanel) action_multArchAnal
                        .getComponent();
                multArch.setNameOfResiduals(VECMConstants.u_hat_Def.name);
                multArch.setResidualNames(VECMConstants.Nu_Def.name);
                multArch.setDateRange(VECMConstants.T1_Def.name);
            }
        }
        return menu_modelChecking;
    }

    /**
     * Insert the method's description here. Creation date: (06.10.00 10:19:10)
     * 
     * @return javax.swing.JMenu
     */
    private JMenu getMenu_Specification() {
        if (menu_specification == null) {
            menu_specification = new AutoEnableMenu("Specification");
            menu_specification.setMnemonic('S');
            menu_specification.add(action_ModelSpecification);
            menu_specification.add(action_subsetRestrictionsSecondStage);
            menu_specification.add(action_waldTestBeta);

        }
        return menu_specification;
    }

    /**
     * Insert the method's description here. Creation date: (06.10.00 10:19:10)
     * 
     * @return javax.swing.JMenu
     */
    private JMenu getMenu_StructuralAnalysis() {
        if (menu_structuralAnalysis == null) {
            menu_structuralAnalysis = new AutoEnableMenu("Structural Analysis");
            menu_structuralAnalysis.setMnemonic('t');

            menu_structuralAnalysis.add(action_causalityTests);
            CausalityPanel caus = (CausalityPanel) action_causalityTests
                    .getComponent();
            caus.setModelType(ModelTypes.VECM);

            menu_structuralAnalysis.add(action_impulseResponseAnalysis);
            FEVDPanel fevd = (FEVDPanel) action_forecastErrorVarianceDecomposition
                    .getComponent();
            fevd.setCoeffName(VECMConstants.A_Def.name);
            fevd.setCovarName(VECMConstants.cv_u_hat_Def.name);
            fevd.setModelType(ModelTypes.VECM);
            menu_structuralAnalysis
                    .add(action_forecastErrorVarianceDecomposition);
        }
        return menu_structuralAnalysis;
    }

    /**
     * Insert the method's description here. Creation date: (06.10.00 10:19:10)
     * 
     * @return javax.swing.JMenu
     */
    private JMenu getMenu_SVEC() {
        if (menu_svec == null) {
            menu_svec = new AutoEnableMenu("SVEC");
            menu_svec.add(action_svec);

            menu_svec.add(action_svecIRA);

            FEVDPanel fevd = (FEVDPanel) action_svecFEVD.getComponent();
            fevd.setStructMatrixName(SVECConstants.matB_Def.name);
            fevd.setModelType(ModelTypes.SVEC);
            fevd.setCoeffName(VECMConstants.A_Def.name);
            fevd.setCovarName(VECMConstants.cv_u_hat_Def.name);
            menu_svec.add(action_svecFEVD);
        }
        return menu_svec;
    }

    /**
     * Insert the method's description here. Creation date: (06.10.00 10:16:54)
     * 
     * @return javax.swing.JMenuBar
     */
    private JMenuBar getMenuBar() {
        if (menuBar == null) {
            menuBar = new JMenuBar();
            menuBar.add(getMenu_Specification());
            menuBar.add(getMenu_Estimation());
            menuBar.add(getMenu_ModelChecking());
            menuBar.add(getMenu_StructuralAnalysis());
            menuBar.add(getMenu_SVEC());
            menuBar.add(getMenu_Forecasting());
        }
        return menuBar;
    }

    /**
     * This initializes all data managers such that they register themselves to
     * their respective data objects.
     * 
     */
    private void initDataManagers() {

        VECMDataManagerEnableMenus.getInstance();
        VECMDataManagerObs.getInstance();
        VECMDataManagerSubsetModel.getInstance();

    }
}