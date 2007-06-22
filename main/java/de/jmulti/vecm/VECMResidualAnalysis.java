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

import javax.swing.JTabbedPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import com.jstatcom.model.ModelPanel;

import de.jmulti.tools.CorrPanel;
import de.jmulti.tools.KernDensPanel;
import de.jmulti.tools.ModelTypes;
import de.jmulti.tools.MultDiagTests;
import de.jmulti.tools.PlotAddResPanel;
import de.jmulti.tools.SpectrumPanel;

/**
 * Binds the residual analysis together.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public final class VECMResidualAnalysis extends ModelPanel {
    private static final Logger log = Logger
            .getLogger(VECMResidualAnalysis.class);

    private JTabbedPane ivjJTabbedPane1 = null;

    private MultDiagTests ivjMultivariateDiagnosticTests = null;

    private CorrPanel ivjCorrelation = null;

    private KernDensPanel ivjKernelDensity = null;

    private PlotAddResPanel ivjPlotAddResids = null;

    private SpectrumPanel ivjSpectrum = null;

    /**
     * ResidualAnalysis constructor comment.
     */
    public VECMResidualAnalysis() {
        super();
        initialize();
    }

    /**
     * Return the Correlation property value.
     * 
     * @return de.jmulti.tools.CorrPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private CorrPanel getCorrelation() {
        if (ivjCorrelation == null) {
            try {
                ivjCorrelation = new CorrPanel();
                ivjCorrelation.setName("Correlation");
                // user code begin {1}
                ivjCorrelation.setModelType(ModelTypes.VECM);
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjCorrelation;
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
                        getMultivariateDiagnosticTests(), null, 0);
                ivjJTabbedPane1.insertTab("Plot/Add", null, getPlotAddResids(),
                        null, 1);
                ivjJTabbedPane1.insertTab("Correlation", null,
                        getCorrelation(), null, 2);
                ivjJTabbedPane1.insertTab("Spectrum", null, getSpectrum(),
                        null, 3);
                ivjJTabbedPane1.insertTab("Kernel Density", null,
                        getKernelDensity(), null, 4);
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
                ivjKernelDensity.setResids(VECMConstants.u_hat_Def.name);
                ivjKernelDensity.setNameResids(VECMConstants.Nu_Def.name);
                ivjKernelDensity.setDRange(VECMConstants.T1_Def.name);

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
     * Return the MultivariateDiagnosticTests property value.
     * 
     * @return de.jmulti.tools.MultDiagTests
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private MultDiagTests getMultivariateDiagnosticTests() {
        if (ivjMultivariateDiagnosticTests == null) {
            try {
                ivjMultivariateDiagnosticTests = new MultDiagTests();
                ivjMultivariateDiagnosticTests
                        .setName("MultivariateDiagnosticTests");
                // user code begin {1}
                ivjMultivariateDiagnosticTests.setModelType(ModelTypes.VECM);
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjMultivariateDiagnosticTests;
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
                ivjPlotAddResids.setResids(VECMConstants.u_hat_Def.name);
                ivjPlotAddResids.setNameResids(VECMConstants.Nu_Def.name);
                ivjPlotAddResids.setDRange(VECMConstants.T1_Def.name);

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
                ivjSpectrum.setResids(VECMConstants.u_hat_Def.name);
                ivjSpectrum.setNameResids(VECMConstants.Nu_Def.name);
                ivjSpectrum.setDRange(VECMConstants.T1_Def.name);

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
            TitledBorder title = new TitledBorder(new BevelBorder(
                    BevelBorder.LOWERED), "Residual Analysis",
                    TitledBorder.RIGHT, TitledBorder.TOP);
            this.setBorder(title);
            // user code end
            setName("ResidualAnalysis");
            setLayout(new javax.swing.BoxLayout(this,
                    javax.swing.BoxLayout.X_AXIS));
            setSize(572, 321);
            add(getJTabbedPane1(), getJTabbedPane1().getName());
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}
        // user code end
    }
}