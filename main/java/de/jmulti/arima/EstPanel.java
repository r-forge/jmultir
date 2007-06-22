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

package de.jmulti.arima;

import java.awt.BorderLayout;

import com.jstatcom.component.ResultField;
import com.jstatcom.engine.PCall;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.ModelPanel;
import com.jstatcom.ts.TSDateRange;

import de.jmulti.proc.ARIMAEstCall;

/**
 * Holds estimation output for ARIMA model.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
public final class EstPanel extends ModelPanel {

    private ResultField resultField = null;

    /**
     * This method initializes
     * 
     */
    public EstPanel() {
        super();
        setName("EstimationPanel");
        initialize();
    }

    /**
     * This method initializes this
     * 
     */
    private void initialize() {
        this.setLayout(new BorderLayout());
        this.setSize(new java.awt.Dimension(543, 398));
        this
                .setBorder(javax.swing.BorderFactory
                        .createTitledBorder(
                                javax.swing.BorderFactory
                                        .createBevelBorder(javax.swing.border.BevelBorder.LOWERED),
                                "Estimated Model",
                                javax.swing.border.TitledBorder.RIGHT,
                                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                                new java.awt.Font("Dialog",
                                        java.awt.Font.PLAIN, 10),
                                new java.awt.Color(51, 51, 51)));
        this.add(getResultField(), java.awt.BorderLayout.CENTER);

    }

    /**
     * This method initializes resultField
     * 
     * @return com.jstatcom.component.ResultField
     */
    private ResultField getResultField() {
        if (resultField == null) {
            resultField = new ResultField();
        }
        return resultField;
    }

    @Override
    public void shown(boolean isShown) {
        if (!isShown)
            return;

        getResultField().clear();

        TSDateRange range = global().get(ARIMAConstants.DRANGE).getJSCDRange()
                .getTSDateRange();

        JSCNArray dets = global().get(ARIMAConstants.DET_DATA_ALL)
                .getJSCNArray();
        if (!dets.isEmpty() && dets.rank() < dets.cols()) {
            getResultField()
                    .setText(
                            "Matrix of fixed regressors has not full column rank. Please change your selection.");
            return;
        }
        int p = global().get(ARIMAConstants.P).getJSCInt().getInt();
        int q = global().get(ARIMAConstants.Q).getJSCInt().getInt();
        if (dets.isEmpty() && p == 0 && q == 0) {
            getResultField().setText("There is nothing to estimate.");
            return;
        }

        PCall job = new ARIMAEstCall(p, q, global().get(ARIMAConstants.D)
                .getJSCInt().getInt(), global().get(ARIMAConstants.END_DATA)
                .getJSCNArray(), dets, global().get(
                ARIMAConstants.DET_NAMES_ALL).getJSCSArray(), global().get(
                ARIMAConstants.START_VALS).getJSCNArray(), global().get(
                ARIMAConstants.END_NAME).getJSCSArray().stringAt(0, 0), range,
                global(), local());

        job.setOutHolder(getResultField());
        job.execute();
    }
} // @jve:decl-index=0:visual-constraint="10,10"
