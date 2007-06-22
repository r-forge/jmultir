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

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.BevelBorder;

import org.apache.log4j.Logger;

import com.jstatcom.component.NumSelector;
import com.jstatcom.component.StdMessages;
import com.jstatcom.engine.PCall;
import com.jstatcom.engine.PCallAdapter;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.ModelPanel;
import com.jstatcom.ts.TS;
import com.jstatcom.ts.TSHolder;
import com.jstatcom.util.UMatrix;

import de.jmulti.proc.HPFilterCall;

/**
 * A panel providing the functionality to split a timeseries into its trend and
 * cyclical component by using the HP filter. The panel needs the name of the
 * dataobject containing the names of the selected time series. It looks for the
 * original series in the TSHolder to use the full series.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public final class HPFilterPanel extends ModelPanel {
    private static final Logger log = Logger.getLogger(HPFilterPanel.class);

    private static final String suffixTrendComponent = "_hptrend";

    private static final String suffixCyclicalComponent = "_hpcyclic";

    private JButton ivjOK = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private JLabel ivjJLabel1 = null;

    private NumSelector ivjLambdaTextField = null;

    private JCheckBox ivjAddCyclic = null;

    private JCheckBox ivjAddHPTrend = null;

    private JLabel ivjJLabel2 = null;

    class IvjEventHandler implements java.awt.event.ActionListener {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == HPFilterPanel.this.getOK())
                connEtoC1();
        };
    };

    /**
     * HPFilterPanel constructor comment.
     */
    public HPFilterPanel() {
        super();
        initialize();
    }

    /**
     * connEtoC1: (OK.action. --> HPFilterPanel.ok_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC1() {
        try {
            // user code begin {1}
            // user code end
            this.oK_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * Return the AddCyclic property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getAddCyclic() {
        if (ivjAddCyclic == null) {
            try {
                ivjAddCyclic = new javax.swing.JCheckBox();
                ivjAddCyclic.setName("AddCyclic");
                ivjAddCyclic.setSelected(true);
                ivjAddCyclic.setText("Add cyclical component");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjAddCyclic;
    }

    /**
     * Return the AddHPTrend property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getAddHPTrend() {
        if (ivjAddHPTrend == null) {
            try {
                ivjAddHPTrend = new javax.swing.JCheckBox();
                ivjAddHPTrend.setName("AddHPTrend");
                ivjAddHPTrend.setSelected(true);
                ivjAddHPTrend.setText("Add trend component");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjAddHPTrend;
    }

    /**
     * Return the JLabel1 property value.
     * 
     * @return javax.swing.JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JLabel getJLabel1() {
        if (ivjJLabel1 == null) {
            try {
                ivjJLabel1 = new javax.swing.JLabel();
                ivjJLabel1.setName("JLabel1");
                ivjJLabel1.setText("Hodrick-Prescott Filter");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJLabel1;
    }

    /**
     * Return the JLabel2 property value.
     * 
     * @return javax.swing.JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JLabel getJLabel2() {
        if (ivjJLabel2 == null) {
            try {
                ivjJLabel2 = new javax.swing.JLabel();
                ivjJLabel2.setName("JLabel2");
                ivjJLabel2.setText("Lambda");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJLabel2;
    }

    /**
     * Return the LambdaTextField property value.
     * 
     * @return com.jstatcom.model.NumberSelector
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private NumSelector getLambdaTextField() {
        if (ivjLambdaTextField == null) {
            try {
                ivjLambdaTextField = new NumSelector();
                ivjLambdaTextField.setName("LambdaTextField");
                ivjLambdaTextField.setPreferredSize(new java.awt.Dimension(80,
                        20));
                ivjLambdaTextField.setNumber(1600.0);
                ivjLambdaTextField.setRangeExpr("(0, Infinity)");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjLambdaTextField;
    }

    /**
     * Return the OK property value.
     * 
     * @return javax.swing.JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JButton getOK() {
        if (ivjOK == null) {
            try {
                ivjOK = new javax.swing.JButton();
                ivjOK.setName("OK");
                ivjOK.setPreferredSize(new java.awt.Dimension(120, 27));
                ivjOK.setText("Execute Filter");
                ivjOK.setMaximumSize(new java.awt.Dimension(80, 27));
                ivjOK.setMinimumSize(new java.awt.Dimension(80, 27));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjOK;
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
     * Initializes connections
     * 
     * @exception Exception
     *                The exception description.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initConnections() throws Exception {
        // user code begin {1}
        // user code end
        getOK().addActionListener(ivjEventHandler);
    }

    /**
     * Initialize the class.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initialize() {
        try {
            // user code begin {1}
            // user code end
            setName("HPFilterPanel");
            setPreferredSize(new java.awt.Dimension(210, 180));
            setBorder(new BevelBorder(BevelBorder.LOWERED));
            setLayout(new java.awt.GridBagLayout());
            setSize(517, 226);
            setMinimumSize(new java.awt.Dimension(210, 180));

            java.awt.GridBagConstraints constraintsJLabel1 = new java.awt.GridBagConstraints();
            constraintsJLabel1.gridx = 0;
            constraintsJLabel1.gridy = 0;
            constraintsJLabel1.gridwidth = 2;
            constraintsJLabel1.fill = java.awt.GridBagConstraints.HORIZONTAL;
            constraintsJLabel1.insets = new java.awt.Insets(10, 10, 0, 0);
            add(getJLabel1(), constraintsJLabel1);

            java.awt.GridBagConstraints constraintsJLabel2 = new java.awt.GridBagConstraints();
            constraintsJLabel2.gridx = 0;
            constraintsJLabel2.gridy = 1;
            constraintsJLabel2.fill = java.awt.GridBagConstraints.HORIZONTAL;
            constraintsJLabel2.insets = new java.awt.Insets(15, 10, 0, 0);
            add(getJLabel2(), constraintsJLabel2);

            java.awt.GridBagConstraints constraintsLambdaTextField = new java.awt.GridBagConstraints();
            constraintsLambdaTextField.gridx = 1;
            constraintsLambdaTextField.gridy = 1;
            constraintsLambdaTextField.insets = new java.awt.Insets(15, 10, 0,
                    0);
            add(getLambdaTextField(), constraintsLambdaTextField);

            java.awt.GridBagConstraints constraintsAddHPTrend = new java.awt.GridBagConstraints();
            constraintsAddHPTrend.gridx = 0;
            constraintsAddHPTrend.gridy = 2;
            constraintsAddHPTrend.gridwidth = 2;
            constraintsAddHPTrend.fill = java.awt.GridBagConstraints.HORIZONTAL;
            constraintsAddHPTrend.insets = new java.awt.Insets(5, 10, 0, 0);
            add(getAddHPTrend(), constraintsAddHPTrend);

            java.awt.GridBagConstraints constraintsAddCyclic = new java.awt.GridBagConstraints();
            constraintsAddCyclic.gridx = 0;
            constraintsAddCyclic.gridy = 3;
            constraintsAddCyclic.gridwidth = 2;
            constraintsAddCyclic.fill = java.awt.GridBagConstraints.HORIZONTAL;
            constraintsAddCyclic.anchor = java.awt.GridBagConstraints.NORTH;
            constraintsAddCyclic.weighty = 1.0;
            constraintsAddCyclic.insets = new java.awt.Insets(5, 10, 0, 0);
            add(getAddCyclic(), constraintsAddCyclic);

            java.awt.GridBagConstraints constraintsOK = new java.awt.GridBagConstraints();
            constraintsOK.gridx = 2;
            constraintsOK.gridy = 3;
            constraintsOK.anchor = java.awt.GridBagConstraints.NORTHEAST;
            constraintsOK.weightx = 1.0;
            constraintsOK.weighty = 1.0;
            constraintsOK.insets = new java.awt.Insets(0, 0, 0, 20);
            add(getOK(), constraintsOK);
            initConnections();
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}
        // user code end
    }

    /**
     * OK Button pressed, start filter for each timeseries in the names data
     * object.
     */
    private void oK_ActionEvents() {
        if (global().get(WorkbenchPanel.WB_DATA).isEmpty()) {
            StdMessages
                    .infoNothingSelected("Please select at least one series.");
            return;
        }

        TSHolder tsHolder = TSHolder.getInstance();
        String[][] seriesNames = global().get(WorkbenchPanel.WB_DATANAMES)
                .getJSCSArray().stringArray();

        for (int i = 0; i < seriesNames.length; i++) {
            // Read the series from the datamap and start HP filter.
            final String name = seriesNames[i][0];
            final TS series = tsHolder.getTS(name);
            if (series == null)
                throw new RuntimeException("TimeSeries " + name
                        + " cannot be read.");

            // To ensure GAUSS compatibilty of names (reserved words)
            JSCNArray dataSeries = new JSCNArray("series_" + (i + 1), series
                    .values());
            PCall job = new HPFilterCall(dataSeries, getLambdaTextField()
                    .getIntNumber());
            job.setSymbolTable(local());

            // Add series if job finished (happens in EventDispatchingThread).
            job.addPCallListener(new PCallAdapter() {
                public void success() {
                    double[][] hpResult = local().get(
                            HPFilterCall.HPFILTER_RESULT).getJSCNArray()
                            .doubleArray();
                    TSHolder data = TSHolder.getInstance();

                    if (getAddHPTrend().isSelected()) {
                        double[] hpTrendComponent = UMatrix.getDoubleCol(
                                hpResult, 0);
                        TS hpTrend = new TS(hpTrendComponent, name
                                + suffixTrendComponent, series.start());
                        hpTrend.setTSType(series.type());
                        int accept = data.addTS(hpTrend);
                        if (accept == JOptionPane.CANCEL_OPTION)
                            return;
                    }
                    if (getAddCyclic().isSelected()) {
                        double[] hpCyclicalComponent = UMatrix.getDoubleCol(
                                hpResult, 1);
                        TS hpCyclic = new TS(hpCyclicalComponent, name
                                + suffixCyclicalComponent, series.start());
                        hpCyclic.setTSType(series.type());
                        int accept = data.addTS(hpCyclic);
                        if (accept == JOptionPane.CANCEL_OPTION)
                            return;
                    }
                };
            });
            job.execute();
        }

    }
}