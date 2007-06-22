/*
 * Copyright (C) 2006 Markus Kraetzig
 * Created on 10.07.2006
 */
package de.jmulti.arima;

import javax.swing.BoxLayout;
import javax.swing.JTabbedPane;

import com.jstatcom.model.ModelPanel;

import de.jmulti.tools.KernDensPanel;
import de.jmulti.tools.PlotAddResPanel;
import de.jmulti.tools.SpectrumPanel;
import de.jmulti.tools.UnivarAutocorrPanel;

public class ModelCheckPanel extends ModelPanel {

    private JTabbedPane jTabbedPane = null;

    private KernDensPanel kernDensPanel = null;

    private SpectrumPanel spectrumPanel = null;

    private UnivarAutocorrPanel autocorrPanel = null;

    private PlotAddResPanel plotAddResPanel = null;

    private DiagTestsPanel diagTestsPanel = null;

    /**
     * This method initializes
     * 
     */
    public ModelCheckPanel() {
        super();
        initialize();
    }

    /**
     * This method initializes this
     * 
     */
    private void initialize() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setSize(new java.awt.Dimension(595,302));
        this.add(getJTabbedPane(), null);

    }

    /**
     * This method initializes jTabbedPane
     * 
     * @return javax.swing.JTabbedPane
     */
    private JTabbedPane getJTabbedPane() {
        if (jTabbedPane == null) {
            jTabbedPane = new JTabbedPane();
            jTabbedPane.addTab("Diagnostic Tests", null, getDiagTestsPanel(),
                    null);
            jTabbedPane.addTab("Plot/Add Resids", null, getPlotAddResPanel(),
                    null);
            jTabbedPane.addTab("Autocorrelations", null, getAutocorrPanel(),
                    null);
            jTabbedPane.addTab("Spectrum", null, getSpectrumPanel(), null);
            jTabbedPane
                    .addTab("Kernel Density", null, getKernDensPanel(), null);

        }
        return jTabbedPane;
    }

    /**
     * This method initializes kernDensPanel
     * 
     * @return de.jmulti.tools.KernDensPanel
     */
    private KernDensPanel getKernDensPanel() {
        if (kernDensPanel == null) {
            kernDensPanel = new KernDensPanel();
            kernDensPanel.setResids(ARIMAConstants.EST_RESIDS.name);
            kernDensPanel.setNameResids(ARIMAConstants.EST_RESIDS_NAME.name);
            kernDensPanel.setDRange(ARIMAConstants.DRANGE_ADJUSTED.name);

        }
        return kernDensPanel;
    }

    /**
     * This method initializes spectrumPanel
     * 
     * @return de.jmulti.tools.SpectrumPanel
     */
    private SpectrumPanel getSpectrumPanel() {
        if (spectrumPanel == null) {
            spectrumPanel = new SpectrumPanel();
            spectrumPanel.setResids(ARIMAConstants.EST_RESIDS.name);
            spectrumPanel.setNameResids(ARIMAConstants.EST_RESIDS_NAME.name);
            spectrumPanel.setDRange(ARIMAConstants.DRANGE_ADJUSTED.name);

        }
        return spectrumPanel;
    }

    /**
     * This method initializes autocorrPanel
     * 
     * @return de.jmulti.arima.AutocorrPanel
     */
    private UnivarAutocorrPanel getAutocorrPanel() {
        if (autocorrPanel == null) {
            autocorrPanel = new UnivarAutocorrPanel();
            autocorrPanel.setResids(ARIMAConstants.EST_RESIDS.name);
        }
        return autocorrPanel;
    }

    /**
     * This method initializes plotAddResPanel
     * 
     * @return de.jmulti.tools.PlotAddResPanel
     */
    private PlotAddResPanel getPlotAddResPanel() {
        if (plotAddResPanel == null) {
            plotAddResPanel = new PlotAddResPanel();
            plotAddResPanel.setResids(ARIMAConstants.EST_RESIDS.name);
            plotAddResPanel.setNameResids(ARIMAConstants.EST_RESIDS_NAME.name);
            plotAddResPanel.setDRange(ARIMAConstants.DRANGE_ADJUSTED.name);
            plotAddResPanel.setUnivariateOnly(true);

        }
        return plotAddResPanel;
    }

    /**
     * This method initializes diagTestsPanel
     * 
     * @return de.jmulti.arima.DiagTestsPanel
     */
    private DiagTestsPanel getDiagTestsPanel() {
        if (diagTestsPanel == null) {
            diagTestsPanel = new DiagTestsPanel();
        }
        return diagTestsPanel;
    }

} // @jve:decl-index=0:visual-constraint="10,10"
