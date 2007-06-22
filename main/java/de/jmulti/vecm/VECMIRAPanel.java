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

import javax.swing.JTabbedPane;

import org.apache.log4j.Logger;

import com.jstatcom.model.ModelPanel;
import com.jstatcom.model.SymbolEvent;
import com.jstatcom.model.SymbolEventTypes;
import com.jstatcom.model.SymbolListener;

import de.jmulti.tools.IRAComputePanel;
import de.jmulti.tools.IRADisplayPanel;
import de.jmulti.tools.ModelTypes;

/**
 * This panel binds the components of the VECM impulse response analysis
 * together. References to configured <code>IRAComputePanel</code> and
 * <code>IRADisplayPanel</code> are held by this component. A symbol table is
 * set for both of these components to have local variable scope of all related
 * data object. A table change listener resets the computed IR if necessary.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public final class VECMIRAPanel extends ModelPanel {
    private static final Logger log = Logger.getLogger(VECMIRAPanel.class);

    private IRAComputePanel ivjIRAComputePanel = null;

    private IRADisplayPanel ivjIRADisplayPanel = null;

    private JTabbedPane ivjJTabbedPane1 = null;

    /**
     * VECMIRAPanel constructor comment.
     */
    public VECMIRAPanel() {
        super();
        initialize();
    }

    /**
     * Return the IRAComputePanel property value.
     * 
     * @return de.jmulti.tools.IRAComputePanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private IRAComputePanel getIRAComputePanel() {
        if (ivjIRAComputePanel == null) {
            try {
                ivjIRAComputePanel = new IRAComputePanel();
                ivjIRAComputePanel.setName("IRAComputePanel");
                ivjIRAComputePanel.setModelType(ModelTypes.VECM);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjIRAComputePanel;
    }

    /**
     * Return the IRADisplayPanel property value.
     * 
     * @return de.jmulti.tools.IRADisplayPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private IRADisplayPanel getIRADisplayPanel() {
        if (ivjIRADisplayPanel == null) {
            try {
                ivjIRADisplayPanel = new IRADisplayPanel();
                ivjIRADisplayPanel.setName("IRADisplayPanel");
                ivjIRADisplayPanel.setModelType(ModelTypes.VECM);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjIRADisplayPanel;
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
                ivjJTabbedPane1.insertTab("Specify IRA", null,
                        getIRAComputePanel(), null, 0);
                ivjJTabbedPane1.insertTab("Display Impulse Responses", null,
                        getIRADisplayPanel(), null, 1);
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
            setName("VECMIRAPanel");
            setLayout(new javax.swing.BoxLayout(this,
                    javax.swing.BoxLayout.X_AXIS));
            setSize(686, 425);
            add(getJTabbedPane1(), getJTabbedPane1().getName());
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}

        // for resetting IRA if model was reestimated
        global().get(VECMConstants.cv_u_hat_Def).addSymbolListener(
                new SymbolListener() {
                    public void valueChanged(SymbolEvent evt) {
                        getIRAComputePanel().reset();
                    }
                }, SymbolEventTypes.EMPTY_STATE);

        // user code end
    }

    public void shown(boolean isShown) {
        getIRAComputePanel().shown(isShown);
    }
}