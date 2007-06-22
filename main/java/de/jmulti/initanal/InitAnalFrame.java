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

import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.apache.log4j.Logger;

import com.jstatcom.model.ModelFrame;

/**
 * The Frame containing the panels of the Initial Analysis.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public final class InitAnalFrame extends ModelFrame {
    private static final Logger log = Logger.getLogger(InitAnalFrame.class);

    private JPanel ivjJInternalFrameContentPane = null;

    private JTabbedPane ivjJTabbedPane = null;

    private WorkbenchPanel ivjUIWorkbenchPanel = null;

    private CointPanel ivjUICointPanel = null;

    private UnitRootPanel ivjUIURTestPanel = null;

    /**
     * DataFrame constructor comment.
     */
    public InitAnalFrame() {
        super();
        initialize();
    }

    /**
     * Return the JInternalFrameContentPane property value.
     * 
     * @return javax.swing.JPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JPanel getJInternalFrameContentPane() {
        if (ivjJInternalFrameContentPane == null) {
            try {
                ivjJInternalFrameContentPane = new javax.swing.JPanel();
                ivjJInternalFrameContentPane
                        .setName("JInternalFrameContentPane");
                ivjJInternalFrameContentPane
                        .setPreferredSize(new java.awt.Dimension(553, 580));
                ivjJInternalFrameContentPane
                        .setLayout(new javax.swing.BoxLayout(
                                getJInternalFrameContentPane(),
                                javax.swing.BoxLayout.X_AXIS));
                ivjJInternalFrameContentPane
                        .setMinimumSize(new java.awt.Dimension(0, 0));
                getJInternalFrameContentPane().add(getJTabbedPane(),
                        getJTabbedPane().getName());
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJInternalFrameContentPane;
    }

    /**
     * Return the JTabbedPane property value.
     * 
     * @return javax.swing.JTabbedPane
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JTabbedPane getJTabbedPane() {
        if (ivjJTabbedPane == null) {
            try {
                ivjJTabbedPane = new javax.swing.JTabbedPane();
                ivjJTabbedPane.setName("JTabbedPane");
                ivjJTabbedPane
                        .setPreferredSize(new java.awt.Dimension(700, 400));
                ivjJTabbedPane.setMinimumSize(new java.awt.Dimension(0, 0));
                ivjJTabbedPane.insertTab("Workbench", null,
                        getUIWorkbenchPanel(), null, 0);
                ivjJTabbedPane.insertTab("Unit Root Tests", null,
                        getUIURTestPanel(), null, 1);
                ivjJTabbedPane.insertTab("Cointegration Tests", null,
                        getUICointPanel(), null, 2);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJTabbedPane;
    }

    /**
     * Return the UICointPanel property value.
     * 
     * @return program.frame.prelim.CointPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private CointPanel getUICointPanel() {
        if (ivjUICointPanel == null) {
            try {
                ivjUICointPanel = new de.jmulti.initanal.CointPanel();
                ivjUICointPanel.setName("UICointPanel");
                ivjUICointPanel.setPreferredSize(new java.awt.Dimension(700,
                        400));
                ivjUICointPanel.setMinimumSize(new java.awt.Dimension(0, 0));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjUICointPanel;
    }

    /**
     * Return the UIURTestPanel property value.
     * 
     * @return program.frame.initanal.UnitRootPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private UnitRootPanel getUIURTestPanel() {
        if (ivjUIURTestPanel == null) {
            try {
                ivjUIURTestPanel = new de.jmulti.initanal.UnitRootPanel();
                ivjUIURTestPanel.setName("UIURTestPanel");
                ivjUIURTestPanel.setPreferredSize(new java.awt.Dimension(700,
                        400));
                ivjUIURTestPanel.setMinimumSize(new java.awt.Dimension(0, 0));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjUIURTestPanel;
    }

    /**
     * Return the UIWorkbenchPanel property value.
     * 
     * @return program.frame.prelim.WorkbenchPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private WorkbenchPanel getUIWorkbenchPanel() {
        if (ivjUIWorkbenchPanel == null) {
            try {
                ivjUIWorkbenchPanel = new de.jmulti.initanal.WorkbenchPanel();
                ivjUIWorkbenchPanel.setName("UIWorkbenchPanel");
                ivjUIWorkbenchPanel.setPreferredSize(new java.awt.Dimension(
                        700, 400));
                ivjUIWorkbenchPanel
                        .setMinimumSize(new java.awt.Dimension(0, 0));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjUIWorkbenchPanel;
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
            setName("DataFrame");
            setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
            setClosable(true);
            setMinimumSize(new java.awt.Dimension(650, 500));
            setSize(750, 500);
            setTitle("Initial Analysis");
            setContentPane(getJInternalFrameContentPane());
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}
        URL url = getClass().getResource("/images/initanal.gif");
        if (url != null)
            setFrameIcon(new ImageIcon(url));

        // user code end
    }
}