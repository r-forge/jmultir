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
package de.jmulti.arima;

import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import org.apache.log4j.Logger;

import com.jstatcom.component.AutoEnableMenu;
import com.jstatcom.component.CardDisplayPanel;
import com.jstatcom.component.CardPanelAction;
import com.jstatcom.model.JSCInt;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.ModelFrame;
import com.jstatcom.model.SymbolEventTypes;
import com.jstatcom.model.SymbolListener;
import com.jstatcom.project.ProjectState;

import de.jmulti.arch.ARCHAnalysisPanel;

/**
 * Holds the ARIMA analysis.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
public final class ARIMAFrame extends ModelFrame {
    private static final Logger log = Logger.getLogger(ARIMAFrame.class);

    private JMenuBar arimaMenuBar = null;

    private AutoEnableMenu specMenu = null;

    private AutoEnableMenu estMenu = null;

    private AutoEnableMenu forecastMenu = null;

    private AutoEnableMenu modelCheckMenu = null;

    private CardDisplayPanel cardDisplayPanel = new CardDisplayPanel();

    private final CardPanelAction action_archAnal = new CardPanelAction(
            "de.jmulti.arch.ARCHAnalysisPanel", cardDisplayPanel, false,
            "ARCH Analysis of Residuals");

    private final CardPanelAction action_SpecPanel = new CardPanelAction(
            "de.jmulti.arima.SpecPanel", cardDisplayPanel, true, "Select Model");

    private final CardPanelAction action_EstPanel = new CardPanelAction(
            "de.jmulti.arima.EstPanel", cardDisplayPanel, false,
            "Estimated Model");

    private final CardPanelAction action_ForecastPanel = new CardPanelAction(
            "de.jmulti.arima.ForecastPanel", cardDisplayPanel, false,
            "Forecasting");

    private final CardPanelAction action_modelCheckingPanel = new CardPanelAction(
            "de.jmulti.arima.ModelCheckPanel", cardDisplayPanel, false,
            "Residual Analysis");

    /**
     * This method initializes
     * 
     */
    public ARIMAFrame() {
        super();
        initialize();

        global().get(ARIMAConstants.END_DATA).addSymbolListener(
                new SymbolListener() {
                    public void valueChanged(com.jstatcom.model.SymbolEvent evt) {
                        action_EstPanel.setEnabled(!evt.isSourceEmpty());
                    };
                }, SymbolEventTypes.EMPTY_STATE);

        global().get(ARIMAConstants.EST_RESIDS).addSymbolListener(
                new SymbolListener() {
                    public void valueChanged(com.jstatcom.model.SymbolEvent evt) {
                        action_ForecastPanel.setEnabled(!evt.isSourceEmpty());
                        action_modelCheckingPanel.setEnabled(!evt
                                .isSourceEmpty());
                        action_archAnal.setEnabled(!evt.isSourceEmpty());
                    };
                }, SymbolEventTypes.EMPTY_STATE);

        global().get(ARIMAConstants.EST_RESIDS_NAME).getJSCSArray().setVal(
                new String[] { "resids" });

    }

    /**
     * This method initializes this
     * 
     */
    private void initialize() {
        this.setSize(new java.awt.Dimension(615, 475));
        this.setContentPane(getCardDisplayPanel());
        this.setJMenuBar(getArimaMenuBar());
        this.setTitle("ARIMA Analysis (univariate)");

        URL url = getClass().getResource("/images/arima.gif");
        if (url != null)
            setFrameIcon(new ImageIcon(url));

        action_SpecPanel.actionPerformed(null);

    }

    /**
     * This method initializes jJMenuBar
     * 
     * @return javax.swing.JMenuBar
     */
    private JMenuBar getArimaMenuBar() {
        if (arimaMenuBar == null) {
            arimaMenuBar = new JMenuBar();
            arimaMenuBar.add(getSpecMenu());
            arimaMenuBar.add(getEstMenu());
            arimaMenuBar.add(getModelCheckMenu());
            arimaMenuBar.add(getForecastMenu());
        }
        return arimaMenuBar;
    }

    /**
     * This method initializes jMenu
     * 
     * @return javax.swing.JMenu
     */
    private JMenu getSpecMenu() {
        if (specMenu == null) {
            specMenu = new AutoEnableMenu();
            specMenu.setText("Specification");
            specMenu.add(action_SpecPanel);
        }
        return specMenu;
    }

    /**
     * This method initializes cardDisplayPanel
     * 
     * @return com.jstatcom.component.CardDisplayPanel
     */
    private CardDisplayPanel getCardDisplayPanel() {
        if (cardDisplayPanel == null) {
            cardDisplayPanel = new CardDisplayPanel();
        }
        return cardDisplayPanel;
    }

    /**
     * This method initializes jMenu1
     * 
     * @return javax.swing.JMenu
     */
    private JMenu getEstMenu() {
        if (estMenu == null) {
            estMenu = new AutoEnableMenu();
            estMenu.setText("Estimation");
            estMenu.add(action_EstPanel);

        }
        return estMenu;
    }

    private JMenu getForecastMenu() {
        if (forecastMenu == null) {
            forecastMenu = new AutoEnableMenu();
            forecastMenu.setText("Forecasting");
            forecastMenu.add(action_ForecastPanel);

        }
        return forecastMenu;
    }

    private JMenu getModelCheckMenu() {
        if (modelCheckMenu == null) {
            modelCheckMenu = new AutoEnableMenu();
            modelCheckMenu.setText("Model Checking");
            modelCheckMenu.add(action_modelCheckingPanel);
            modelCheckMenu.add(action_archAnal);

            if (action_archAnal.getComponent() instanceof ARCHAnalysisPanel) {
                ARCHAnalysisPanel arch = (ARCHAnalysisPanel) action_archAnal
                        .getComponent();
                arch.setNameOfResiduals(ARIMAConstants.EST_RESIDS.name);
                arch.setResidualNames(ARIMAConstants.EST_RESIDS_NAME.name);
                arch.setDateRange(ARIMAConstants.DRANGE_ADJUSTED.name);
            }

        }
        return modelCheckMenu;
    }

    public void setState(ProjectState state) {
        if (state == null) {
            log.warn("Project state was null.");
            return;
        }
        SpecPanel specPanel = (SpecPanel) action_SpecPanel.getComponent();
        ARIMAProjectState arimaState = (ARIMAProjectState) state;
        specPanel.getTSSel().setSelection(arimaState.getSelection());

        if (arimaState.getP() != null)
            global().get(ARIMAConstants.P).setJSCData(arimaState.getP());
        if (arimaState.getQ() != null)
            global().get(ARIMAConstants.Q).setJSCData(arimaState.getQ());
        if (arimaState.getD() != null && !arimaState.getD().isEmpty()) {
            int d = arimaState.getD().intVal();
            if (d < 0)
                d = 0;
            specPanel.getDComboBox().setSelectedIndex(d);
            global().get(ARIMAConstants.D).setJSCData(arimaState.getD());
        }
        JSCNArray detSel = arimaState.getDetSel();
        if (detSel != null && detSel.rows() == 3) {
            specPanel.getConstCheckBox().setSelected(detSel.intAt(0, 0) != 0);
            specPanel.getSeasCheckBox().setSelected(detSel.intAt(1, 0) != 0);
            specPanel.getTrendCheckBox().setSelected(detSel.intAt(2, 0) != 0);

            if (specPanel.getSeasCheckBox().isSelected()
                    || specPanel.getTrendCheckBox().isSelected()){
                specPanel.getConstCheckBox().setSelected(true);
                specPanel.getConstCheckBox().setEnabled(false);
            }
            else
                specPanel.getConstCheckBox().setEnabled(true);
            
            global().get(ARIMAConstants.DET_SEL).setJSCData(
                    arimaState.getDetSel());

        }

        if (arimaState.getH_hr() != null && !arimaState.getH_hr().isEmpty())
            specPanel.getHSelector().setNumber(arimaState.getH_hr().intVal());
        if (arimaState.getPqmax_hr() != null
                && !arimaState.getPqmax_hr().isEmpty())
            specPanel.getPqMaxSelector().setNumber(
                    arimaState.getPqmax_hr().intVal());

    }

    public ProjectState getState() {
        ARIMAProjectState state = new ARIMAProjectState();
        SpecPanel specPanel = (SpecPanel) action_SpecPanel.getComponent();

        state.setSelection(specPanel.getTSSel().getSelection());
        JSCNArray selection = new JSCNArray("DET_SEL", new int[] {
                specPanel.getConstCheckBox().isSelected() ? 1 : 0,
                specPanel.getSeasCheckBox().isSelected() ? 1 : 0,
                specPanel.getTrendCheckBox().isSelected() ? 1 : 0 });

        state.setDetSel(selection);
        state.setP(global().get(ARIMAConstants.P).getJSCInt());
        state.setQ(global().get(ARIMAConstants.Q).getJSCInt());
        int d = specPanel.getDComboBox().getSelectedIndex();
        if (d < 0)
            d = 0;
        state.setD(new JSCInt("D", d));

        // for Hannan-Rissanen

        int pqMax = specPanel.getPqMaxSelector().getIntNumber();
        state.setPqmax_hr(new JSCInt("pq_max", pqMax));

        int h_hr = specPanel.getHSelector().getIntNumber();
        state.setH_hr(new JSCInt("h_hr", h_hr));

        return state;
    }

}
