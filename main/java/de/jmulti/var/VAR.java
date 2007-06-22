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

package de.jmulti.var;

import java.awt.event.ActionEvent;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.WindowConstants;

import com.jstatcom.component.AutoEnableMenu;
import com.jstatcom.component.CardDisplayPanel;
import com.jstatcom.component.CardPanelAction;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.ModelFrame;
import com.jstatcom.model.SymbolTable;
import com.jstatcom.project.ProjectState;
import com.jstatcom.ts.Selection;

import de.jmulti.arch.ARCHAnalysisPanel;
import de.jmulti.arch.MultARCHAnalysisPanel;
import de.jmulti.tools.FEVDPanel;
import de.jmulti.tools.ForecastPanel;
import de.jmulti.tools.ModelTypes;

/**
 * This component binds the VAR analysis together. It is a SINGLETON and should
 * be referenced via the <code>getInstance</code> method.
 * 
 * @author A. Benkwitz, <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
public final class VAR {

    /**
     * The variable for the instance.
     */
    private static final VAR var = new VAR();

    private final CardDisplayPanel contentPane = new CardDisplayPanel();

    final CardPanelAction action_specifyModel = new CardPanelAction(
            "de.jmulti.var.VARSpecifyModelPanel", contentPane, true,
            "Specify VAR Model");

    final CardPanelAction action_setRestrictions = new CardPanelAction(
            "de.jmulti.var.VARSubSetPanel", contentPane, false,
            "Subset Restrictions");

    final CardPanelAction action_estimatedCoefficients = new CardPanelAction(
            "de.jmulti.var.VAREstCoeffPanel", contentPane, false,
            "Estimated Model");

    //
    final CardPanelAction action_residualAnalysis = new CardPanelAction(
            "de.jmulti.var.VARResidualAnalysis", contentPane, false,
            "Residual Analysis");

    //
    final CardPanelAction action_forecasting = new CardPanelAction(
            "de.jmulti.tools.ForecastPanel", contentPane, false,
            "Forecast Variables");

    //
    final CardPanelAction action_causalityTests = new CardPanelAction(
            "de.jmulti.tools.CausalityPanel", contentPane, false,
            "Causality Tests");

    final CardPanelAction action_impulseResponseAnalysis = new CardPanelAction(
            "de.jmulti.var.VARIRAPanel", contentPane, false,
            "Impulse Response Analysis");

    // FEVD VAR Panel needs to be configured.
    final CardPanelAction action_forecastErrorVarianceDecomposition = new CardPanelAction(
            "de.jmulti.tools.FEVDPanel", contentPane, false,
            "FEV Decomposition");

    final CardPanelAction action_stabAnal = new CardPanelAction(
            "de.jmulti.var.VARStabilityPanel", contentPane, false,
            "Stability Analysis");

    final CardPanelAction action_archAnal = new CardPanelAction(
            "de.jmulti.arch.ARCHAnalysisPanel", contentPane, false,
            "Univariate ARCH Analysis of Residuals");

    final CardPanelAction action_multArchAnal = new CardPanelAction(
            "de.jmulti.arch.MultARCHAnalysisPanel", contentPane, false,
            "Multivariate GARCH Analysis of Residuals");

    final CardPanelAction action_svarEstimation = new CardPanelAction(
            "de.jmulti.var.SVARPanel", contentPane, false, "SVAR Estimation");

    final CardPanelAction action_svarIRA = new CardPanelAction(
            "de.jmulti.var.SVARIRAPanel", contentPane, false, "SVAR IRA");

    // FEVD SVAR Panel needs to be configured.
    final CardPanelAction action_svarFEV = new CardPanelAction(
            "de.jmulti.tools.FEVDPanel", contentPane, false, "SVAR FEVD");

    private ModelFrame internalFrame = null;

    private JMenuBar menuBar = null;

    private AutoEnableMenu menu_specification = null;

    private AutoEnableMenu menu_estimation = null;

    private AutoEnableMenu menu_modelChecking = null;

    private AutoEnableMenu menu_structuralAnalysis = null;

    private AutoEnableMenu menu_forecasting = null;

    private AutoEnableMenu menu_svar = null;

    /**
     * Constructor hidden.
     */
    private VAR() {
        super();
    }

    /**
     * Returns the SINGLETON instance of VAR.
     * 
     * @return VAR reference to an instance
     */
    public static VAR getInstance() {
        return var;
    }

    /**
     * Initializes the internal frame for VAR analysis.
     * 
     * @return javax.swing.JInternalFrame the initialized instance
     */
    public static ModelFrame getInternalFrame() {
        if (getInstance().internalFrame == null) {
            // subclass with project management
            getInstance().internalFrame = new ModelFrame("VAR Analysis") {
                public String getID() {
                    return "de.jmulti.var.VAR";
                }

                /**
                 * Sets the state of this module frame from a
                 * <code>ProjectState</code> instance.
                 */
                public void setState(ProjectState state) {
                    if (state instanceof VARProjectState) {
                        VARProjectState varState = (VARProjectState) state;

                        SymbolTable table = varState.getSymbolTableGlobal();
                        Selection sel = varState.getSelection();
                        JSCNArray detSel = varState.getDetSelection();
                        SymbolTable svarTable = varState.getSymbolTableSVAR();

                        VARSpecifyModelPanel specPanel = (VARSpecifyModelPanel) getInstance().action_specifyModel
                                .getComponent();
                        SVARPanel svarPanel = (SVARPanel) getInstance().action_svarEstimation
                                .getComponent();

                        // set specify model panel
                        getInstance().action_specifyModel
                                .actionPerformed(new ActionEvent(getInstance(),
                                        0, "show selection panel"));

                        // selection panel
                        if (sel != null)
                            specPanel.getSelectPanel().setSelection(sel);

                        // symbol table
                        if (table != null) {
                            table.get(VARConstants.cv_u_hat_Def).clear();
                            global().setSymbolTable(table);
                        }
                        // deterministic selection
                        if (detSel != null)
                            specPanel.setDetSelection(detSel);

                        //  svar table
                        if (svarTable != null) {
                            svarTable.get(SVARConstants.svar_A0_Def).clear();
                            svarPanel.setABModelPanelTable(svarTable);
                        }
                        table.clear();
                        svarTable.clear();
                        specPanel.getLagSpecPanel().selectionChanged();

                    } else
                        throw new IllegalArgumentException(
                                "Argument not a valid project state for this module.");
                }

                /**
                 * Gets the state of this module frame as a
                 * <code>ProjectState</code> instance.
                 */
                public ProjectState getState() {
                    VARProjectState state = new VARProjectState();
                    VARSpecifyModelPanel specPanel = (VARSpecifyModelPanel) getInstance().action_specifyModel
                            .getComponent();
                    SVARPanel svarPanel = (SVARPanel) getInstance().action_svarEstimation
                            .getComponent();

                    state.setSymbolTableGlobal(global());
                    state.setSelection(specPanel.getSelectPanel()
                            .getSelection());
                    state.setDetSelection(specPanel.getDetSelection());
                    state.setSymbolTableSVAR(svarPanel.getABModelPanelTable());

                    return state;
                }
            };
            getInstance().internalFrame.setName(getInstance().internalFrame
                    .getTitle());
            getInstance().internalFrame.setJMenuBar(getInstance().getMenuBar());
            getInstance().internalFrame
                    .setContentPane(getInstance().contentPane);
            getInstance().internalFrame.setSize(800, 500);
            getInstance().internalFrame.setMinimumSize(new java.awt.Dimension(
                    800, 500));
            getInstance().internalFrame
                    .setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

            getInstance().internalFrame.setLocation(0, 0);
            URL url = getInstance().getClass().getResource("/images/var.gif");
            if (url != null)
                getInstance().internalFrame.setFrameIcon(new ImageIcon(url));

            getInstance().action_specifyModel.actionPerformed(new ActionEvent(
                    getInstance(), 0, "show selection panel"));

            // To make sure it is initialized for enabling the menus.
            VARDataManager.getInstance();

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
            menu_forecasting.add(action_forecasting);
            ForecastPanel forec = (ForecastPanel) action_forecasting
                    .getComponent();
            forec.setModelType(ModelTypes.VAR);

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
            menu_modelChecking.add(action_residualAnalysis);
            menu_modelChecking.add(action_stabAnal);
            menu_modelChecking.add(action_archAnal);
            menu_modelChecking.add(action_multArchAnal);

            if (action_archAnal.getComponent() instanceof ARCHAnalysisPanel) {
                ARCHAnalysisPanel arch = (ARCHAnalysisPanel) action_archAnal
                        .getComponent();
                arch.setNameOfResiduals(VARConstants.u_hat_Def.name);
                arch.setResidualNames(VARConstants.Nu_Def.name);
                arch.setDateRange(VARConstants.T1_Def.name);
            }
            if (action_multArchAnal.getComponent() instanceof MultARCHAnalysisPanel) {
                MultARCHAnalysisPanel multArch = (MultARCHAnalysisPanel) action_multArchAnal
                        .getComponent();
                multArch.setNameOfResiduals(VARConstants.u_hat_Def.name);
                multArch.setResidualNames(VARConstants.Nu_Def.name);
                multArch.setDateRange(VARConstants.T1_Def.name);
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
            menu_specification.add(action_specifyModel);
            menu_specification.add(action_setRestrictions);
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
            // Default Type is already VAR
            menu_structuralAnalysis.add(action_causalityTests);
            menu_structuralAnalysis.add(action_impulseResponseAnalysis);
            // Default Type is already VAR
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
    private JMenu getMenu_SVAR() {
        if (menu_svar == null) {
            menu_svar = new AutoEnableMenu("SVAR");

            menu_svar.add(action_svarEstimation);
            menu_svar.add(action_svarIRA);

            FEVDPanel fevd = (FEVDPanel) action_svarFEV.getComponent();
            fevd.setStructMatrixName(SVARConstants.svar_A0_Def.name);
            fevd.setModelType(ModelTypes.SVAR);
            menu_svar.add(action_svarFEV);

        }
        return menu_svar;
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
            menuBar.add(getMenu_SVAR());
            menuBar.add(getMenu_Forecasting());
        }
        return menuBar;
    }

    /**
     * Gets the global symbol table used for this model frame.
     * 
     * @return the global symbol table
     */
    public SymbolTable getSymbolTable() {
        return getInternalFrame().global();
    }
}