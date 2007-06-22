/*
 * Copyright (C) 2006 Markus Kraetzig
 * Created on 10.07.2006
 */
package de.jmulti.arima;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import com.jstatcom.component.NumSelector;
import com.jstatcom.component.ResultField;
import com.jstatcom.engine.PCall;
import com.jstatcom.engine.PCallAdapter;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.JSCSArray;
import com.jstatcom.model.JSCTypeDef;
import com.jstatcom.model.JSCTypes;
import com.jstatcom.model.ModelPanel;
import com.jstatcom.model.Scope;
import com.jstatcom.table.JSCDataTableScrollPane;
import com.jstatcom.table.JSCNArrayTable;
import com.jstatcom.ts.TS;
import com.jstatcom.ts.TSDate;
import com.jstatcom.ts.TSDateRange;
import com.jstatcom.ts.TSDateSelector;
import com.jstatcom.ts.TSHolder;

import de.jmulti.proc.ARIMAForecastCall;
import de.jmulti.tools.ForecastHelper;

public class ForecastPanel extends ModelPanel {
    // for internal use only
    private static final JSCTypeDef d_forecast_Def = new JSCTypeDef(
            "d_forecast", JSCTypes.NARRAY, "deterministics for forecast");

    private JPanel jPanel = null;

    private JScrollPane jScrollPane = null;

    private ResultField resultField = null;

    private JLabel jLabel = null;

    private NumSelector hSelector = null;

    private JLabel jLabel1 = null;

    private NumSelector ciSelector = null;

    private JButton okButton = null;

    private JCheckBox graphCheckBox = null;

    private TSDateSelector startDateSelector = null;

    private JLabel jLabel2 = null;

    private JTabbedPane jTabbedPane = null;

    private JSCDataTableScrollPane detsScrollPane = null;

    private JSCNArrayTable detsTable = null;

    private JLabel jLabel3 = null;

    /**
     * This method initializes
     * 
     */
    public ForecastPanel() {
        super();
        setName("ForecastPanel");
        initialize();
    }

    /**
     * This method initializes this
     * 
     */
    private void initialize() {
        this.setLayout(new BorderLayout());
        this.setSize(new java.awt.Dimension(708, 390));
        this
                .setBorder(javax.swing.BorderFactory
                        .createTitledBorder(
                                javax.swing.BorderFactory
                                        .createBevelBorder(javax.swing.border.BevelBorder.LOWERED),
                                "Forecasting",
                                javax.swing.border.TitledBorder.RIGHT,
                                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                                new java.awt.Font("Dialog",
                                        java.awt.Font.PLAIN, 10),
                                new java.awt.Color(51, 51, 51)));

        this.add(getJTabbedPane(), java.awt.BorderLayout.CENTER);
    }

    /**
     * This method initializes jPanel
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJPanel() {
        if (jPanel == null) {
            GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
            gridBagConstraints13.gridx = 0;
            gridBagConstraints13.gridwidth = 4;
            gridBagConstraints13.insets = new java.awt.Insets(15, 20, 10, 0);
            gridBagConstraints13.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints13.gridy = 2;
            jLabel3 = new JLabel();
            jLabel3
                    .setText("Fixed regressors in forecast period (table may be edited)");
            jLabel3.setPreferredSize(new java.awt.Dimension(200, 20));
            GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
            gridBagConstraints4.fill = java.awt.GridBagConstraints.BOTH;
            gridBagConstraints4.gridy = 3;
            gridBagConstraints4.weightx = 1.0;
            gridBagConstraints4.weighty = 1.0;
            gridBagConstraints4.gridwidth = 4;
            gridBagConstraints4.insets = new java.awt.Insets(0, 20, 0, 20);
            gridBagConstraints4.gridx = 0;
            GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
            gridBagConstraints31.gridx = 3;
            gridBagConstraints31.insets = new java.awt.Insets(10, 10, 0, 0);
            gridBagConstraints31.weightx = 0.0D;
            gridBagConstraints31.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints31.gridy = 1;
            jLabel2 = new JLabel();
            jLabel2.setText("Select start date of plot");
            jLabel2.setPreferredSize(new java.awt.Dimension(132, 20));
            GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
            gridBagConstraints21.fill = java.awt.GridBagConstraints.NONE;
            gridBagConstraints21.gridy = 1;
            gridBagConstraints21.weightx = 0.0D;
            gridBagConstraints21.insets = new java.awt.Insets(10, 40, 0, 0);
            gridBagConstraints21.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints21.gridx = 2;
            GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
            gridBagConstraints12.gridx = 2;
            gridBagConstraints12.insets = new java.awt.Insets(10, 40, 0, 0);
            gridBagConstraints12.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints12.gridwidth = 2;
            gridBagConstraints12.gridy = 0;
            GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
            gridBagConstraints3.gridx = 0;
            gridBagConstraints3.weightx = 0.0D;
            gridBagConstraints3.weighty = 0.0D;
            gridBagConstraints3.anchor = java.awt.GridBagConstraints.SOUTH;
            gridBagConstraints3.insets = new java.awt.Insets(10, 10, 15, 10);
            gridBagConstraints3.gridwidth = 4;
            gridBagConstraints3.gridy = 4;
            GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
            gridBagConstraints2.fill = java.awt.GridBagConstraints.NONE;
            gridBagConstraints2.gridy = 1;
            gridBagConstraints2.weightx = 0.0D;
            gridBagConstraints2.insets = new java.awt.Insets(10, 20, 0, 0);
            gridBagConstraints2.anchor = java.awt.GridBagConstraints.NORTHEAST;
            gridBagConstraints2.gridx = 0;
            GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
            gridBagConstraints11.gridx = 1;
            gridBagConstraints11.weightx = 0.0D;
            gridBagConstraints11.weighty = 0.0D;
            gridBagConstraints11.insets = new java.awt.Insets(10, 10, 0, 0);
            gridBagConstraints11.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints11.anchor = java.awt.GridBagConstraints.NORTH;
            gridBagConstraints11.gridy = 1;
            jLabel1 = new JLabel();
            jLabel1.setText("Confidence level");
            jLabel1.setPreferredSize(new java.awt.Dimension(38, 20));
            GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
            gridBagConstraints1.fill = java.awt.GridBagConstraints.NONE;
            gridBagConstraints1.gridy = 0;
            gridBagConstraints1.weightx = 0.0D;
            gridBagConstraints1.insets = new java.awt.Insets(10, 20, 0, 0);
            gridBagConstraints1.gridx = 0;
            GridBagConstraints gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
            gridBagConstraints.gridy = 0;
            gridBagConstraints.weightx = 0.0D;
            gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints.weighty = 0.0D;
            gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
            gridBagConstraints.gridx = 1;
            jLabel = new JLabel();
            jLabel.setText("Forecast horizon");
            jLabel.setPreferredSize(new java.awt.Dimension(95, 20));
            jPanel = new JPanel();
            jPanel.setLayout(new GridBagLayout());
            jPanel.setPreferredSize(new java.awt.Dimension(10, 120));
            jPanel.add(jLabel, gridBagConstraints);
            jPanel.add(getHSelector(), gridBagConstraints1);
            jPanel.add(jLabel1, gridBagConstraints11);
            jPanel.add(getCiSelector(), gridBagConstraints2);
            jPanel.add(getOkButton(), gridBagConstraints3);
            jPanel.add(getGraphCheckBox(), gridBagConstraints12);
            jPanel.add(getStartDateSelector(), gridBagConstraints21);
            jPanel.add(jLabel2, gridBagConstraints31);
            jPanel.add(getDetsScrollPane(), gridBagConstraints4);
            jPanel.add(jLabel3, gridBagConstraints13);
        }
        return jPanel;
    }

    /**
     * This method initializes jScrollPane
     * 
     * @return javax.swing.JScrollPane
     */
    private JScrollPane getJScrollPane() {
        if (jScrollPane == null) {
            jScrollPane = new JScrollPane();
            jScrollPane.setViewportView(getResultField());
        }
        return jScrollPane;
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

    /**
     * This method initializes hSelector
     * 
     * @return com.jstatcom.component.NumSelector
     */
    private NumSelector getHSelector() {
        if (hSelector == null) {
            hSelector = new NumSelector();
            hSelector.setPreferredSize(new java.awt.Dimension(100, 20));
            hSelector.setMinimumSize(new java.awt.Dimension(100, 20));
            hSelector.setIntType(true);
            hSelector.setRangeExpr("[1,1000]");
            hSelector.addPropertyChangeListener("number",
                    new PropertyChangeListener() {
                        public void propertyChange(PropertyChangeEvent evt) {
                            computeDetsForeForecast();

                        }
                    });
        }
        return hSelector;
    }

    /**
     * This method initializes ciSelector
     * 
     * @return com.jstatcom.component.NumSelector
     */
    private NumSelector getCiSelector() {
        if (ciSelector == null) {
            ciSelector = new NumSelector();
            ciSelector.setPreferredSize(new java.awt.Dimension(100, 20));
            ciSelector.setMinimumSize(new java.awt.Dimension(100, 20));
            ciSelector.setPrecision(2);
            ciSelector.setNumber(0.95D);
            ciSelector.setRangeExpr("(0,1)");
        }
        return ciSelector;
    }

    /**
     * This method initializes okButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getOkButton() {
        if (okButton == null) {
            okButton = new JButton();
            okButton.setPreferredSize(new java.awt.Dimension(120, 25));
            okButton.setMinimumSize(new java.awt.Dimension(120, 25));

            okButton.setText("Forecast");
            okButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    int p = global().get(ARIMAConstants.P).getJSCInt().intVal();
                    int q = global().get(ARIMAConstants.Q).getJSCInt().intVal();
                    int d = global().get(ARIMAConstants.D).getJSCInt().intVal();
                    JSCNArray y = global().get(ARIMAConstants.END_DATA)
                            .getJSCNArray();
                    JSCNArray resids = global().get(ARIMAConstants.EST_RESIDS)
                            .getJSCNArray();

                    JSCNArray detsForForec = getDetsTable().getJSCNArray();

                    // need also last p observations of fixed regressors
                    if (p > 0 && !detsForForec.isEmpty()) {
                        JSCNArray detDataAll = global().get(
                                ARIMAConstants.DET_DATA_ALL).getJSCNArray();
                        int end = detDataAll.rows() - 1;
                        double[][] detDataPre = detDataAll.getRows(end - p + 1,
                                end);
                        JSCNArray tmp = detsForForec;
                        detsForForec = new JSCNArray("detsForForec", detDataPre);
                        detsForForec.appendRows(tmp);
                    }

                    JSCNArray b = global().get(ARIMAConstants.EST_PARAMS)
                            .getJSCNArray();
                    String yName = global().get(ARIMAConstants.END_NAME)
                            .getJSCSArray().stringAt(0, 0);
                    double ciLevel = getCiSelector().getNumber();
                    int horizon = getHSelector().getIntNumber();
                    TSDateRange range = global().get(ARIMAConstants.DRANGE)
                            .getJSCDRange().getTSDateRange();
                    TSDate startPlot = getStartDateSelector().getTSDate();
                    boolean isPlot = getGraphCheckBox().isSelected();
                    PCall job = new ARIMAForecastCall(p, q, d, y, resids,
                            detsForForec, b, yName, ciLevel, horizon, range,
                            startPlot, isPlot, local());

                    getResultField().clear();
                    job.setOutHolder(getResultField());
                    job.addPCallListener(new PCallAdapter() {
                        @Override
                        public void success() {
                            getJTabbedPane().setSelectedIndex(1);
                        }
                    });
                    job.execute();

                }

            });

        }
        return okButton;
    }

    /**
     * This method initializes graphCheckBox
     * 
     * @return javax.swing.JCheckBox
     */
    private JCheckBox getGraphCheckBox() {
        if (graphCheckBox == null) {
            graphCheckBox = new JCheckBox();
            graphCheckBox.setText("Show graph");
            graphCheckBox.setSelected(true);
            graphCheckBox
                    .addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent e) {

                            boolean isSel = getGraphCheckBox().isSelected();
                            getStartDateSelector().setEnabled(isSel);
                            jLabel2.setEnabled(isSel);
                        }
                    });
        }
        return graphCheckBox;
    }

    /**
     * This method initializes startDateSelector
     * 
     * @return com.jstatcom.ts.TSDateSelector
     */
    private TSDateSelector getStartDateSelector() {
        if (startDateSelector == null) {
            startDateSelector = new TSDateSelector();
            startDateSelector.setPreferredSize(new java.awt.Dimension(100, 20));
            startDateSelector.setMinimumSize(new java.awt.Dimension(100, 20));

        }
        return startDateSelector;
    }

    @Override
    public void shown(boolean isShown) {
        if (!isShown)
            return;

        // get range
        TSDateRange estRange = global().get(ARIMAConstants.DRANGE)
                .getJSCDRange().getTSDateRange();
        String yName = global().get(ARIMAConstants.END_NAME).getJSCSArray()
                .stringAt(0, 0);
        TS origTS = TSHolder.getInstance().getTS(yName);
        TSDate earliestStart = origTS.start();
        TSDate latestStart = estRange.upperBound();
        getStartDateSelector().setEnclosingRange(
                new TSDateRange(earliestStart, latestStart));

        computeDetsForeForecast();
        getJTabbedPane().setSelectedIndex(0);
    }

    /**
     * This method initializes jTabbedPane
     * 
     * @return javax.swing.JTabbedPane
     */
    private JTabbedPane getJTabbedPane() {
        if (jTabbedPane == null) {
            jTabbedPane = new JTabbedPane();
            jTabbedPane.addTab("Specify Forecast", null, getJPanel(), null);
            jTabbedPane.addTab("Results", null, getJScrollPane(), null);
        }
        return jTabbedPane;
    }

    /**
     * This method initializes detsScrollPane
     * 
     * @return com.jstatcom.table.JSCDataTableScrollPane
     */
    private JSCDataTableScrollPane getDetsScrollPane() {
        if (detsScrollPane == null) {
            detsScrollPane = new JSCDataTableScrollPane();
            detsScrollPane.setColumnHeaderShowing(true);
            detsScrollPane.setViewportView(getDetsTable());
            detsScrollPane.setRowHeaderShowing(true);
            detsScrollPane.setMinimumVisibleColumns(1);
            detsScrollPane.setMaximumVisibleColumns(10);
            detsScrollPane.setMaximumVisibleRows(20);
            detsScrollPane.setMinimumVisibleRows(1);

        }
        return detsScrollPane;
    }

    /**
     * This method initializes detsTable
     * 
     * @return com.jstatcom.table.JSCNArrayTable
     */
    private JSCNArrayTable getDetsTable() {
        if (detsTable == null) {
            detsTable = new JSCNArrayTable();
            detsTable.setEditable(true);
            detsTable.setSymbolScope(Scope.LOCAL);
            detsTable.setDynamicColumnWidth(true);
            detsTable.setMaximumColumnWidth(300);
            detsTable.setSymbolName(d_forecast_Def.name);
            getDetsScrollPane().setColumnHeaderView(detsTable.getTableHeader());

        }
        return detsTable;
    }

    private void computeDetsForeForecast() {
        local().get(d_forecast_Def).clear();
        JSCNArray detDataAll = global().get(ARIMAConstants.DET_DATA_ALL)
                .getJSCNArray();

        if (detDataAll.isEmpty()) {
            getDetsTable().setEditable(false);
            getDetsScrollPane().setRowHeaderData(new JSCSArray("empty"));
            getDetsScrollPane().setColumnHeaderData(new JSCSArray("empty"));
            jLabel3.setVisible(false);
            return;
        }
        jLabel3.setVisible(true);

        getDetsTable().setEditable(true);

        int h = getHSelector().getIntNumber();

        // Set the deterministic variables for the forecast
        // but check, whether we have some values, we can use
        JSCSArray ndraw = global().get(ARIMAConstants.DET_NAMES_SEL)
                .getJSCSArray();
        JSCNArray d_forec = local().get(d_forecast_Def).getJSCNArray();

        TSDateRange range = global().get(ARIMAConstants.DRANGE).getJSCDRange()
                .getTSDateRange();

        TSDate startDate = range.upperBound().addPeriods(1);
        JSCNArray detSel = global().get(ARIMAConstants.DET_SEL).getJSCNArray();

        // trend is last of standard dets
        int lastTrendVal = 0;
        if (detSel.intAt(2, 0) == 1)
            lastTrendVal = detDataAll.intAt(detDataAll.rows() - 1, detDataAll
                    .cols()
                    - 1 - ndraw.rows());

        // assemble standards
        JSCNArray d_forecast1 = ForecastHelper.extrapolateStdDets(detSel, range
                .subPeriodicity(), h, startDate, lastTrendVal);

        // find user selected
        JSCNArray detDataSel = global().get(ARIMAConstants.DET_DATA_SEL)
                .getJSCNArray();
        JSCNArray d_forecast2 = ForecastHelper.extrapolateSelData(ndraw,
                detDataSel, d_forec, startDate, h);

        d_forecast1.appendCols(d_forecast2);

        local().get(d_forecast_Def).setJSCData(d_forecast1);

        // table headers
        // for row header
        TSDate endDate = range.upperBound();
        String[] h_index = new String[h];
        for (int i = 0; i < h; i++)
            h_index[i] = endDate.addPeriods(i + 1).toString();
        JSCSArray timeIndex = new JSCSArray("rowHeader", h_index);
        if (!d_forecast1.isEmpty()) {
            JSCSArray nd = global().get(ARIMAConstants.DET_NAMES_ALL)
                    .getJSCSArray();
            getDetsScrollPane().setRowHeaderData(timeIndex);
            getDetsScrollPane().setColumnHeaderData(nd);
        }

    }

} // @jve:decl-index=0:visual-constraint="10,10"
