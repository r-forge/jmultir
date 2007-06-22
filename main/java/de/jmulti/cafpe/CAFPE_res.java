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

package de.jmulti.cafpe;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import de.jmulti.tools.KernDensPanel;
import de.jmulti.tools.PlotAddResPanel;
import de.jmulti.tools.SpectrumPanel;

/**
 * Panel that binds the components of the residual analysis together.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */

final class CAFPE_res extends JPanel {
    private static final Logger log = Logger.getLogger(CAFPE_res.class);

    private JTabbedPane ivjJTabbedPane1 = null;

    private SpectrumPanel ivjSpectrum = null;

    private KernDensPanel ivjKernelDensity = null;

    private CAFPE_diagTests ivjCAFPE_diagTests = null;

    private PlotAddResPanel ivjPlotAddResids = null;

    /**
     * CAFPE_res constructor comment.
     */
    public CAFPE_res() {
        super();
        initialize();
    }

    /**
     * Return the CAFPE_diagTests property value.
     * 
     * @return program.frame.nonparam.CAFPE_diagTests
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private CAFPE_diagTests getCAFPE_diagTests() {
        if (ivjCAFPE_diagTests == null) {
            try {
                ivjCAFPE_diagTests = new de.jmulti.cafpe.CAFPE_diagTests();
                ivjCAFPE_diagTests.setName("CAFPE_diagTests");
                ivjCAFPE_diagTests.setResiduals(CAFPE_constants.RESID_EST);
                ivjCAFPE_diagTests.setVolatRes(false);
                ivjCAFPE_diagTests.setXsadj(CAFPE_constants.XSADJ);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjCAFPE_diagTests;
    }

    /**
     * Return the JTabbedPane1 property value.
     * 
     * @return javax.swing.JTabbedPane
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JTabbedPane getJTabbedPane1() {
        if (ivjJTabbedPane1 == null) {
            try {
                ivjJTabbedPane1 = new javax.swing.JTabbedPane();
                ivjJTabbedPane1.setName("JTabbedPane1");
                ivjJTabbedPane1.insertTab("Diagnostic Tests", null,
                        getCAFPE_diagTests(), null, 0);
                ivjJTabbedPane1.insertTab("Plot/Add Resids", null,
                        getPlotAddResids(), null, 1);
                ivjJTabbedPane1.insertTab("Spectrum", null, getSpectrum(),
                        null, 2);
                ivjJTabbedPane1.insertTab("Kernel Density Est.", null,
                        getKernelDensity(), null, 3);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJTabbedPane1;
    }

    /**
     * Return the KernelDensity property value.
     * 
     * @return de.jmulti.tools.KernDensPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private de.jmulti.tools.KernDensPanel getKernelDensity() {
        if (ivjKernelDensity == null) {
            try {
                ivjKernelDensity = new de.jmulti.tools.KernDensPanel();
                ivjKernelDensity.setName("KernelDensity");
                // user code begin {1}
                ivjKernelDensity.setResids(CAFPE_constants.RESID_EST.name);
                ivjKernelDensity
                        .setNameResids(CAFPE_constants.NAME_RESIDS.name);
                ivjKernelDensity.setDRange(CAFPE_constants.T1.name);

                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjKernelDensity;
    }

    /**
     * Return the PlotAddResids property value.
     * 
     * @return de.jmulti.tools.PlotAddResPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private de.jmulti.tools.PlotAddResPanel getPlotAddResids() {
        if (ivjPlotAddResids == null) {
            try {
                ivjPlotAddResids = new de.jmulti.tools.PlotAddResPanel();
                ivjPlotAddResids.setName("PlotAddResids");
                // user code begin {1}
                ivjPlotAddResids.setResids(CAFPE_constants.RESID_EST.name);
                ivjPlotAddResids
                        .setNameResids(CAFPE_constants.NAME_RESIDS.name);
                ivjPlotAddResids.setDRange(CAFPE_constants.T1.name);
                ivjPlotAddResids.setUnivariateOnly(true);


                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjPlotAddResids;
    }

    /**
     * Return the Spectrum property value.
     * 
     * @return de.jmulti.tools.SpectrumPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private de.jmulti.tools.SpectrumPanel getSpectrum() {
        if (ivjSpectrum == null) {
            try {
                ivjSpectrum = new de.jmulti.tools.SpectrumPanel();
                ivjSpectrum.setName("Spectrum");
                // user code begin {1}
                ivjSpectrum.setResids(CAFPE_constants.RESID_EST.name);
                ivjSpectrum.setNameResids(CAFPE_constants.NAME_RESIDS.name);
                ivjSpectrum.setDRange(CAFPE_constants.T1.name);

                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSpectrum;
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
            setName("CAFPE_res");
            setLayout(new BorderLayout());
            setSize(637, 423);
            add(getJTabbedPane1(), BorderLayout.CENTER);
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}
        TitledBorder title = new TitledBorder(new BevelBorder(
                BevelBorder.LOWERED), "Check Residuals of Mean Estimation",
                TitledBorder.RIGHT, TitledBorder.TOP);
        setBorder(title);

        // user code end
    }
}