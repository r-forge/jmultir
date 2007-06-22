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

package de.jmulti.initanal;

import javax.swing.JPanel;

import org.apache.log4j.Logger;

/**
 * A Panel with a CardLayout for different unit root tests.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
final class UnitRootTestContainer extends JPanel {
    private static final Logger log = Logger
            .getLogger(UnitRootTestContainer.class);

    private ADFPanel ivjADFPanel = null;

    private HegyPanel ivjHegyPanel = null;

    private KPSSPanel ivjKPSSPanel = null;

    private SPPanel ivjSPPanel = null;

    private StructBreakPanel ivjStructBreakPanel = null;

    /**
     * UnitRootTestContainer constructor comment.
     */
    public UnitRootTestContainer() {
        super();
        initialize();
    }

    /**
     * Return the ADFPanel property value.
     * 
     * @return program.frame.prelim.ADFPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private ADFPanel getADFPanel() {
        if (ivjADFPanel == null) {
            try {
                ivjADFPanel = new de.jmulti.initanal.ADFPanel();
                ivjADFPanel.setName("ADFPanel");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjADFPanel;
    }

    /**
     * Return the HegyPanel property value.
     * 
     * @return program.frame.prelim.HegyPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private HegyPanel getHegyPanel() {
        if (ivjHegyPanel == null) {
            try {
                ivjHegyPanel = new de.jmulti.initanal.HegyPanel();
                ivjHegyPanel.setName("HegyPanel");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjHegyPanel;
    }

    /**
     * Return the KPSSPanel property value.
     * 
     * @return program.frame.prelim.KPSSPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private KPSSPanel getKPSSPanel() {
        if (ivjKPSSPanel == null) {
            try {
                ivjKPSSPanel = new de.jmulti.initanal.KPSSPanel();
                ivjKPSSPanel.setName("KPSSPanel");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjKPSSPanel;
    }

    /**
     * Return the SPPanel property value.
     * 
     * @return program.frame.prelim.SPPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private SPPanel getSPPanel() {
        if (ivjSPPanel == null) {
            try {
                ivjSPPanel = new de.jmulti.initanal.SPPanel();
                ivjSPPanel.setName("SPPanel");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSPPanel;
    }

    /**
     * Return the StructBreakPanel property value.
     * 
     * @return program.frame.prelim.StructBreakPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private StructBreakPanel getStructBreakPanel() {
        if (ivjStructBreakPanel == null) {
            try {
                ivjStructBreakPanel = new de.jmulti.initanal.StructBreakPanel();
                ivjStructBreakPanel.setName("StructBreakPanel");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjStructBreakPanel;
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
            setName("UnitRootTestContainer");
            setLayout(new java.awt.CardLayout());
            setSize(520, 350);
            add(getADFPanel(), getADFPanel().getName());
            add(getKPSSPanel(), getKPSSPanel().getName());
            add(getSPPanel(), getSPPanel().getName());
            add(getHegyPanel(), getHegyPanel().getName());
            add(getStructBreakPanel(), getStructBreakPanel().getName());
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}
        // user code end
    }

    /**
     * Perform the selectionChanged method.
     */
    public void selectionChanged() {
        /* Perform the selectionChanged method. */
        getStructBreakPanel().selectionChanged();

    }
}