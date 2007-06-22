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

package de.jmulti;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import org.apache.log4j.Logger;

import com.jstatcom.component.TopFrame;
import com.jstatcom.project.ProjectManager;
import com.jstatcom.ts.DefaultTSListPopup;

import de.jmulti.tools.PlotControlDialog;
import de.jmulti.tools.PlotControlModel;
import de.jmulti.tools.TSPlotAction;

/**
 * Main frame of the JMulTi application.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public final class JMultiR extends TopFrame {
    private static final Logger log = Logger.getLogger(JMultiR.class);

    private PlotControlDialog plotDialog = null;

    /**
     * Not to be invoked except from <code>main</code>.
     */
    private JMultiR() {
        super();
    }

    private PlotControlDialog getPlotDialog() {
        if (plotDialog == null) {
            plotDialog = PlotControlDialog.valueOfSimple();
            plotDialog.setLocationRelativeTo(this);
            plotDialog.setPlotControlModel(PlotControlModel.SHARED_PLOT_MODEL);
        }
        return plotDialog;

    }

    /**
     * Gets the old GAUSS symbol control frame.
     * 
     * @return TS calculator frame
     * 
     * protected JFrame getSymbolControlFrame() { if (symbolFrame == null) {
     * symbolFrame = new JFrame("Symbol Control"); gauss.SymbolControl sControl =
     * new gauss.SymbolControl(); symbolFrame.getContentPane().add(sControl);
     * symbolFrame.setSize(600, 400); symbolFrame.setLocationRelativeTo(this);
     * URL url = getClass().getResource("/images/scontrol.gif"); if (url !=
     * null) symbolFrame.setIconImage(new ImageIcon(url).getImage()); } return
     * symbolFrame; }
     */

    /**
     * Overwrites supermethod.
     */
    protected void extra() {

        JMenuItem item = new JMenuItem("Global Graphics Settings");
        item.addActionListener(new AbstractAction(
                "Global Graphics Settings") {
            public void actionPerformed(ActionEvent evt) {
                getPlotDialog().setVisible(true);
            }
        });
        getControlMenu().add(item, 0);
        getControlMenu().add(new JSeparator(), 1);
        ProjectManager.getInstance().addPackagePrefix("jmulti", "de.jmulti");

        DefaultTSListPopup.getSharedInstance().addTSActionAt(
                TSPlotAction.TSPLOT_GAUSS, 2);

    }

    /**
     * Gets the old GAUSS status bar. The default returns
     * <code>DefaultPCallControl.getInstance</code>.
     * 
     * @return panel to be used as status bar
     * 
     * protected JPanel getStatusBar() { return
     * gauss.DefaultPCallControl.getInstance(); }
     */

    /**
     * Starts the application.
     * 
     * @param args
     *            an array of command-line arguments
     */
    public static void main(String[] args) {
        try {

            JMultiR jmFrame = new JMultiR();
            // call skeleton
            jmFrame.skeleton();

        } catch (Throwable exception) {
            log.error("Exception in main()", exception);
        }
    }
}
