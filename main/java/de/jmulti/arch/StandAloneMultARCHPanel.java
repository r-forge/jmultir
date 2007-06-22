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
 * A panel containing a stand alone multivariate GARCH analysis with variable
 * selection included. Models with dimension 2 to 4 can be estimated.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public final class StandAloneMultARCHPanel extends JPanel {
    private static final Logger log = Logger
            .getLogger(StandAloneMultARCHPanel.class);

    final static JSCTypeDef DRANGE = new JSCTypeDef("MGARCH_DRANGE",
            JSCTypes.DRANGE, "date range for multivariate arch analysis");

    final static JSCTypeDef DATA = new JSCTypeDef("MGARCH_DATA",
            JSCTypes.NARRAY, "data for multivariate arch analysis");

    final static JSCTypeDef DATA_NAMES = new JSCTypeDef("MGARCH_DATA_NAMES",
            JSCTypes.SARRAY, "data names for multivariate arch analysis");

    private TSSel ivjSelectPanel = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private MultARCHAnalysisPanel ivjMultARCHAnalysisPanel = null;

    class IvjEventHandler implements java.beans.PropertyChangeListener {
        public void propertyChange(java.beans.PropertyChangeEvent evt) {
            if (evt.getSource() == StandAloneMultARCHPanel.this
                    .getSelectPanel()
                    && (evt.getPropertyName().equals("selectionChanged")))
                connEtoC1(evt);
        };
    };

    /**
     * StartPanel constructor comment.
     */
    public StandAloneMultARCHPanel() {
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
     * Return the MultARCHAnalysisPanel property value.
     * 
     * @return program.frame.arch.MultARCHAnalysisPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    MultARCHAnalysisPanel getMultARCHAnalysisPanel() {
        if (ivjMultARCHAnalysisPanel == null) {
            try {
                ivjMultARCHAnalysisPanel = new de.jmulti.arch.MultARCHAnalysisPanel();
                ivjMultARCHAnalysisPanel.setName("MultARCHAnalysisPanel");
                // user code begin {1}
                ivjMultARCHAnalysisPanel.setDateRange(DRANGE.name);
                ivjMultARCHAnalysisPanel.setNameOfResiduals(DATA.name);
                ivjMultARCHAnalysisPanel.setResidualNames(DATA_NAMES.name);
                ivjMultARCHAnalysisPanel.global().get(DRANGE);
                ivjMultARCHAnalysisPanel.global().get(DATA);
                ivjMultARCHAnalysisPanel.global().get(DATA_NAMES);
                // user code end
            } catch (java.lang.Exception ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjMultARCHAnalysisPanel;
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
                ivjSelectPanel.setOneEndogenousOnly(false);
                ivjSelectPanel.setExogenousEnabled(false);
                ivjSelectPanel
                        .setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
                // user code begin {1}
                ivjSelectPanel.setDateRangeName(DRANGE.name);
                ivjSelectPanel.setEndogenousStringsName(DATA_NAMES.name);
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
            setName("StandAloneMultARCHPanel");
            setLayout(new java.awt.BorderLayout());
            setSize(813, 416);
            add(getMultARCHAnalysisPanel(), "Center");
            add(getSelectPanel(), "East");
            initConnections();
        } catch (java.lang.Exception ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}
        // To let the Panel enable/disable selection depending
        // in what tab is shown.
        getMultARCHAnalysisPanel().setSelectPanel(getSelectPanel());
        // user code end
    }

    /**
     * Comment
     */
    private void selectPanel_SelectionChanged() {
        getMultARCHAnalysisPanel().setDiagnosEnabled(false);

    }
}