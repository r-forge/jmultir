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

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.apache.log4j.Logger;

import com.jstatcom.component.Card;

import de.jmulti.tools.ChowTestPanel;
import de.jmulti.tools.ModelTypes;

/**
 * Panel that binds components for VAR stability analysis together.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public final class VARStabilityPanel extends JPanel implements Card {
    private static final Logger log = Logger.getLogger(VARStabilityPanel.class);

    private JTabbedPane ivjJTabbedPane1 = null;

    private ChowTestPanel ivjChowTestPanel = null;

    private VARCusumPanel ivjCusumPanel = null;

    private VARRecursCoeff ivjVARRecursCoeff = null;

    private VARRecResPanel ivjRecursiveResiduals = null;

    /**
     * StabilityPane constructor comment.
     */
    public VARStabilityPanel() {
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
                ivjChowTestPanel.setModelType(ModelTypes.VAR);
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
     * Return the CusumPanel property value.
     * 
     * @return VARCusumPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private VARCusumPanel getCusumPanel() {
        if (ivjCusumPanel == null) {
            try {
                ivjCusumPanel = new de.jmulti.var.VARCusumPanel();
                ivjCusumPanel.setName("CusumPanel");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjCusumPanel;
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
                ivjJTabbedPane1.insertTab("Cusum", null, getCusumPanel(), null,
                        1);
                ivjJTabbedPane1.insertTab("Recursive Coefficients", null,
                        getVARRecursCoeff(), null, 2);
                ivjJTabbedPane1.insertTab("Recursive Residuals", null,
                        getRecursiveResiduals(), null, 3);
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
     * Return the RecursiveResiduals property value.
     * 
     * @return de.jmulti.var.VARRecResPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private VARRecResPanel getRecursiveResiduals() {
        if (ivjRecursiveResiduals == null) {
            try {
                ivjRecursiveResiduals = new de.jmulti.var.VARRecResPanel();
                ivjRecursiveResiduals.setName("RecursiveResiduals");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjRecursiveResiduals;
    }

    /**
     * Return the VARRecursCoeff property value.
     * 
     * @return VARRecursCoeff
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private VARRecursCoeff getVARRecursCoeff() {
        if (ivjVARRecursCoeff == null) {
            try {
                ivjVARRecursCoeff = new de.jmulti.var.VARRecursCoeff();
                ivjVARRecursCoeff.setName("VARRecursCoeff");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjVARRecursCoeff;
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

    public void shown(boolean isShown) {
        getChowTestPanel().shown(isShown);
        getVARRecursCoeff().shown(isShown);

    }
}