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

package de.jmulti.arch;

import javax.swing.JPanel;

import org.apache.log4j.Logger;

import com.jstatcom.model.JSCTypeDef;
import com.jstatcom.model.JSCTypes;
import com.jstatcom.ts.TSSel;

/**
 * A panel containing a stand alone ARCH analysis with variable selection
 * included.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public final class StandAloneARCHPanel extends JPanel {
    private static final Logger log = Logger
            .getLogger(StandAloneARCHPanel.class);

    final static JSCTypeDef DRANGE = new JSCTypeDef("ARCH_DRANGE",
            JSCTypes.DRANGE, "date range for univariate arch analysis");

    final static JSCTypeDef DATA = new JSCTypeDef("ARCH_DATA", JSCTypes.NARRAY,
            "data for univariate arch analysis");

    final static JSCTypeDef DATA_NAMES = new JSCTypeDef("ARCH_DATA_NAMES",
            JSCTypes.SARRAY, "data names Ffor univariate arch analysis");

    private ARCHAnalysisPanel ivjARCHAnalysisPanel = null;

    private TSSel ivjSelectPanel = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    class IvjEventHandler implements java.beans.PropertyChangeListener {
        public void propertyChange(java.beans.PropertyChangeEvent evt) {
            if (evt.getSource() == StandAloneARCHPanel.this.getSelectPanel()
                    && (evt.getPropertyName().equals("selectionChanged")))
                connEtoC1(evt);
        };
    };

    /**
     * StartPanel constructor comment.
     */
    public StandAloneARCHPanel() {
        super();
        initialize();
    }

    /**
     * connEtoC1: (SelectPanel.selectionChanged -->
     * StartPanel.selectPanel_SelectionChanged()V)
     * 
     * @param arg1
     *            java.beans.PropertyChangeEvent
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC1(java.beans.PropertyChangeEvent arg1) {
        try {
            // user code begin {1}
            // user code end
            this.selectPanel_SelectionChanged();
            // user code begin {2}
            // user code end
        } catch (java.lang.Exception ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * Return the ARCHAnalysisPanel property value.
     * 
     * @return program.frame.arch.ARCHAnalysisPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    ARCHAnalysisPanel getARCHAnalysisPanel() {
        if (ivjARCHAnalysisPanel == null) {
            try {
                ivjARCHAnalysisPanel = new de.jmulti.arch.ARCHAnalysisPanel();
                ivjARCHAnalysisPanel.setName("ARCHAnalysisPanel");
                // user code begin {1}
                ivjARCHAnalysisPanel.setDateRange(DRANGE.name);
                ivjARCHAnalysisPanel.setNameOfResiduals(DATA.name);
                ivjARCHAnalysisPanel.setResidualNames(DATA_NAMES.name);
                ivjARCHAnalysisPanel.global().get(DRANGE);
                ivjARCHAnalysisPanel.global().get(DATA);
                ivjARCHAnalysisPanel.global().get(DATA_NAMES);
                // user code end
            } catch (java.lang.Exception ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjARCHAnalysisPanel;
    }

    /**
     * Return the SelectPanel property value.
     * 
     * @return TSSelector
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    TSSel getSelectPanel() {
        if (ivjSelectPanel == null) {
            try {
                ivjSelectPanel = new TSSel();
                ivjSelectPanel.setName("SelectPanel");
                ivjSelectPanel.setDeterministicEnabled(false);
                ivjSelectPanel.setOneEndogenousOnly(true);
                ivjSelectPanel.setExogenousEnabled(false);
                ivjSelectPanel
                        .setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
                // user code begin {1}
                ivjSelectPanel.setEndogenousStringsName(DATA_NAMES.name);
                ivjSelectPanel.setDeterministicEnabled(false);
                ivjSelectPanel.setDateRangeName(DRANGE.name);
                ivjSelectPanel.setOneEndogenousOnly(true);
                ivjSelectPanel.setExogenousEnabled(false);
                ivjSelectPanel.setEndogenousDataName(DATA.name);

                // user code end
            } catch (java.lang.Exception ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSelectPanel;
    }

    /**
     * Called whenever the part throws an exception.
     * 
     * @param exception
     *            java.lang.Exception
     */
    private void handleException(java.lang.Exception exception) {

        
        log.error("Unhandled Exception", exception);
    }

    /**
     * Initializes connections
     * 
     * @exception java.lang.Exception
     *                The exception description.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initConnections() throws java.lang.Exception {
        // user code begin {1}
        // user code end
        getSelectPanel().addPropertyChangeListener(ivjEventHandler);
    }

    /**
     * Initialize the class.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initialize() {
        try {
            // user code begin {1}
            // user code end
            setName("StandAloneARCHPanel");
            setLayout(new java.awt.BorderLayout());
            setSize(813, 416);
            add(getARCHAnalysisPanel(), "Center");
            add(getSelectPanel(), "East");
            initConnections();
        } catch (java.lang.Exception ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}
        // To let the Panel enable/disable selection depending
        // in what tab is shown.
        getARCHAnalysisPanel().setSelectPanel(getSelectPanel());

        // user code end
    }

    /**
     * Comment
     */
    private void selectPanel_SelectionChanged() {
        getARCHAnalysisPanel().setDiagnosEnabled(false);
    }
}