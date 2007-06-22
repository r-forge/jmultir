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
 * A Panel with a CardLayout for different cointegration tests.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
final class CointTestContainer extends JPanel {
    private static final Logger log = Logger
            .getLogger(CointTestContainer.class);

    private JohansenPanel ivjJohansenPanel = null;

    private SLCointPanel ivjSLCointPanel = null;

    /**
     * CointTestContainer constructor comment.
     */
    public CointTestContainer() {
        super();
        initialize();
    }

    /**
     * Return the JohansenPanel property value.
     * 
     * @return program.frame.prelim.JohansenPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private JohansenPanel getJohansenPanel() {
        if (ivjJohansenPanel == null) {
            try {
                ivjJohansenPanel = new de.jmulti.initanal.JohansenPanel();
                ivjJohansenPanel.setName("JohansenPanel");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJohansenPanel;
    }

    /**
     * Return the LuetkeSaikkPanel property value.
     * 
     * @return program.frame.prelim.LuetkeSaikkPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private SLCointPanel getSLCointPanel() {
        if (ivjSLCointPanel == null) {
            try {
                ivjSLCointPanel = new de.jmulti.initanal.SLCointPanel();
                ivjSLCointPanel.setName("SLCointPanel");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSLCointPanel;
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
            setName("CointTestContainer");
            setLayout(new java.awt.CardLayout());
            setSize(520, 350);
            add(getJohansenPanel(), getJohansenPanel().getName());
            add(getSLCointPanel(), getSLCointPanel().getName());
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}
        // user code end
    }
}