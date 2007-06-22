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

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.apache.log4j.Logger;

import com.jstatcom.component.Card;

import de.jmulti.tools.ChowTestPanel;
import de.jmulti.tools.ModelTypes;

/**
 * Panel that binds components for VECM stability analysis together.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public final class VECMStabilityPanel extends JPanel implements Card {
    private static final Logger log = Logger
            .getLogger(VECMStabilityPanel.class);

    private JTabbedPane ivjJTabbedPane1 = null;

    private ChowTestPanel ivjChowTestPanel = null;

    private VECMRecEigenPanel ivjVECMRecEigenPanel = null;

    private VECMRecursCoeff ivjVECMRecursCoeff = null;

    /**
     * StabilityPane constructor comment.
     */
    public VECMStabilityPanel() {
        super();
        initialize();
    }

    /**
     * Return the ChowTestPanel property value.
     * 
     * @return de.jmulti.tools.ChowTestPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private ChowTestPanel getChowTestPanel() {
        if (ivjChowTestPanel == null) {
            try {
                ivjChowTestPanel = new ChowTestPanel();
                ivjChowTestPanel.setName("ChowTestPanel");
                ivjChowTestPanel.setModelType(ModelTypes.VECM);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjChowTestPanel;
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
                ivjJTabbedPane1.insertTab("Chow Test", null,
                        getChowTestPanel(), null, 0);
                ivjJTabbedPane1.insertTab("Recursive Eigenvalues", null,
                        getVECMRecEigenPanel(), null, 1);
                ivjJTabbedPane1.insertTab("Recursive Coefficients", null,
                        getVECMRecursCoeff(), null, 2);
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
     * Return the VECMRecEigenPanel property value.
     * 
     * @return VECMRecEigenPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private VECMRecEigenPanel getVECMRecEigenPanel() {
        if (ivjVECMRecEigenPanel == null) {
            try {
                ivjVECMRecEigenPanel = new de.jmulti.vecm.VECMRecEigenPanel();
                ivjVECMRecEigenPanel.setName("VECMRecEigenPanel");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjVECMRecEigenPanel;
    }

    /**
     * Return the VECMRecursCoeff property value.
     * 
     * @return VECMRecursCoeff
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private VECMRecursCoeff getVECMRecursCoeff() {
        if (ivjVECMRecursCoeff == null) {
            try {
                ivjVECMRecursCoeff = new de.jmulti.vecm.VECMRecursCoeff();
                ivjVECMRecursCoeff.setName("VECMRecursCoeff");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjVECMRecursCoeff;
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
            setName("StabilityPane");
            setLayout(new javax.swing.BoxLayout(this,
                    javax.swing.BoxLayout.X_AXIS));
            setSize(633, 366);
            add(getJTabbedPane1(), getJTabbedPane1().getName());
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}

        // user code end
    }

    /**
     * Show components notification.
     * 
     * @param isShown
     *            boolean
     */
    public void shown(boolean isShown) {

        getChowTestPanel().shown(isShown);
        getVECMRecEigenPanel().shown(isShown);
        getVECMRecursCoeff().shown(isShown);

    }
}