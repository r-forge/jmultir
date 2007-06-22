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

package de.jmulti.tools;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import com.jstatcom.component.NumSelector;
import com.jstatcom.component.ResultField;
import com.jstatcom.component.TopFrameReference;
import com.jstatcom.engine.PCall;
import com.jstatcom.engine.PCallAdapter;
import com.jstatcom.model.JSCDRange;
import com.jstatcom.model.JSCData;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.JSCSArray;
import com.jstatcom.model.JSCTypeDef;
import com.jstatcom.model.JSCTypes;
import com.jstatcom.model.ModelPanel;
import com.jstatcom.model.Scope;
import com.jstatcom.model.Symbol;
import com.jstatcom.model.SymbolEvent;
import com.jstatcom.model.SymbolListener;
import com.jstatcom.table.JSCDataTableScrollPane;
import com.jstatcom.table.JSCNArrayTable;
import com.jstatcom.table.JSCSArrayTable;
import com.jstatcom.ts.TSDate;
import com.jstatcom.ts.TSDateRange;
import com.jstatcom.ts.TSDateSelector;
import com.jstatcom.util.UData;

import de.jmulti.proc.ForecastCall;
import de.jmulti.var.VARConstants;
import de.jmulti.vecm.VECMConstants;

/**
 * Panel for creating level and undifferenced forecasts from VAR coefficients.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
public class ForecastPanel extends ModelPanel {
    private static final Logger log = Logger.getLogger(ForecastPanel.class);

    // The JSCTypeDef of the variables needed for data display and the call.
    private JSCTypeDef covarName = null;

    private JSCTypeDef ndName = null;

    private JSCTypeDef ndRawName = null;

    private JSCTypeDef detSelName = null;

    private JSCTypeDef nxName = null;

    private JSCTypeDef nyName = null;

    private JSCTypeDef ayName = null;

    private JSCTypeDef yName = null;

    private JSCTypeDef xName = null;

    private JSCTypeDef dName = null;

    private JSCTypeDef dRawName = null;

    private JSCTypeDef drangeName = null;

    private JSCTypeDef pyName = null;

    private JSCTypeDef pxName = null;

    private ModelTypes modelType = ModelTypes.VAR;

    private JSCNArray ytLevels = new JSCNArray("ytLevels");

    private JSCNArray yt_forecast = new JSCNArray("yt_forecast");

    private JSCTypeDef idx_cd2ci = null;

    private JSCTypeDef ad = null;

    // for internal use only
    private static final JSCTypeDef d_forecast_Def = new JSCTypeDef(
            "d_forecast", JSCTypes.NARRAY,
            "deterministics for forecast of levels");

    private static final JSCTypeDef x_forecast_Def = new JSCTypeDef(
            "x_forecast", JSCTypes.NARRAY, "exogenous for forecast of levels");

    private ForecastUndiffConfig fcUndiff = null; // @jve:decl-index=0:visual-constraint="10,420"

    private JTabbedPane ivjForecasting = null;

    private JPanel ivjSpecify = null;

    private JPanel ivjText = null;

    private ResultField ivjResultField = null;

    private JLabel ivjJLabel1 = null;

    private JSCDataTableScrollPane ivjDetScroll = null;

    private JSCNArrayTable ivjDetTable = null;

    private JSCDataTableScrollPane ivjExScroll = null;

    private JSCNArrayTable ivjExTable = null;

    private boolean recompute = true;

    private JButton ivjForecast = null;

    private JLabel ivjJLabel2 = null;

    private JLabel ivjJLabel3 = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private JLabel ivjJLabel = null;

    private JSCDataTableScrollPane ivjNamesPane = null;

    private JSCSArrayTable ivjNamesTable = null;

    private JCheckBox ivjAsymCi = null;

    private JLabel ivjJLabel4 = null;

    private JLabel ivjJLabel5 = null;

    private JButton ivjForecastUndiff = null;

    private JButton ivjUndiffConfig = null;

    private NumSelector ivjCICoverageSelector = null;

    private NumSelector ivjHSelector = null;

    private TSDateSelector ivjStartDateSelector = null;

    class IvjEventHandler implements java.awt.event.ActionListener,
            java.beans.PropertyChangeListener {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == ForecastPanel.this.getForecast())
                connEtoC1();
            if (e.getSource() == ForecastPanel.this.getForecastUndiff())
                connEtoC2();
            if (e.getSource() == ForecastPanel.this.getUndiffConfig())
                connEtoC3();
        };

        public void propertyChange(java.beans.PropertyChangeEvent evt) {
            if (evt.getSource() == ForecastPanel.this.getHSelector()
                    && (evt.getPropertyName().equals("number")))
                connEtoC4(evt);
        };
    };

    /**
     * Forecasting constructor comment.
     */
    public ForecastPanel() {
        super();
        initialize();
    }

    /**
     * Initializes the data used for the model type that has to be set before.
     */
    public final void addNotify() {
        super.addNotify();

        global().get(covarName).addSymbolListener(new SymbolListener() {
            public void valueChanged(SymbolEvent evt) {
                recompute = true;
            }
        });
        global().get(ndName).addSymbolListener(new SymbolListener() {
            public void valueChanged(SymbolEvent evt) {
                local().get(d_forecast_Def).clear();
            }
        });
        global().get(nxName).addSymbolListener(new SymbolListener() {
            public void valueChanged(SymbolEvent evt) {
                local().get(x_forecast_Def).clear();
            }
        });

        getDetScroll().setColumnHeaderSymbolName(ndName.name);
        getExScroll().setColumnHeaderSymbolName(nxName.name);
        getNamesTable().setSymbolName(nyName.name);

    }

    /**
     * This is needed to map the columns in Ad correctly to the matrix of
     * deterministics used for forecasting. When deterministic variables are
     * restricted to the EC term, the Ad matrix from the VAR rep. of the VECM is
     * in different order, the EC restricted terms appear only after the non
     * restricted ones.
     * 
     * 
     * 
     * @return
     */
    private JSCNArray reorderADForVECMwithECRes() {
        Symbol s = global().get(idx_cd2ci);
        if (s == null || s.isEmpty())
            return null;

        double[][] detParams = global().get(ad).getJSCNArray().doubleArray();
        JSCNArray idx = global().get(idx_cd2ci).getJSCNArray();
        int[][] idxAsInt = idx.intArray();
        int notResNum = idx.zeroCount();

        int newIndexNotRes = 0;
        int newIndexRes = 0;

        double[][] reordered = new double[detParams.length][idxAsInt.length];
        for (int i = 0; i < idxAsInt.length; i++) {
            int colIndex = 0; // create a map array from old index to
            // reordered index
            if (idxAsInt[i][0] == 0) // count only not restricted
                colIndex = newIndexNotRes++;
            else
                // count only restricted, index starts after
                // nonrestricted at notResNum
                colIndex = notResNum + newIndexRes++;
            for (int j = 0; j < reordered.length; j++)
                reordered[j][i] = detParams[j][colIndex];
        }

        JSCNArray reorderedMat = new JSCNArray("reorderedDetParams", reordered);
        return reorderedMat;
    }

    /**
     * Compute level forecasts.
     */
    private void executeLevel() {
        JSCNArray selIndex = new JSCNArray("Ny_forecast_selection",
                getNamesTable().getIntSelectionIndex());

        // select all if none is selected
        if (getNamesTable().getSelectedRowCount() == 0)
            for (int i = 0; i < selIndex.rows(); i++)
                selIndex.setValAt(1, i, 0);

        JSCData[] all = UData.mergeTS(global().get(nyName).getJSCSArray(),
                "Forecast_origY", "earliestStart");

        JSCNArray adReordered = null;
        if (modelType == ModelTypes.VECM) {
            // reorder becauseEC restricted dets may change order
            adReordered = reorderADForVECMwithECRes();
        }
        PCall job = new ForecastCall(global(), getHSelector().getIntNumber(),
                selIndex, yt_forecast, null, (JSCNArray) all[0], getDetTable()
                        .getJSCNArray(), getExTable().getJSCNArray(),
                modelType, false, getAsymCi().isSelected(),
                getCICoverageSelector().getNumber() / 100,
                getStartDateSelector().getTSDate(), adReordered);
        job.setSymbolTable(local());
        job.setOutHolder(getResultField());
        job.addPCallListener(new PCallAdapter() {
            public void success() {
                getForecasting().setSelectedIndex(1);
            }
        });

        job.execute();

    }

    /**
     * Compute undifferenced forecasts from 1st differences.
     */
    private void executeUndiff() {
        JSCNArray selIndex = new JSCNArray("Ny_forecast_selection",
                getNamesTable().getIntSelectionIndex());

        // select all if none is selected
        if (getNamesTable().getSelectedRowCount() == 0)
            getNamesTable().getSelectionModel().setSelectionInterval(0,
                    getNamesTable().getRowCount() - 1);

        // PREPARE ORIG YT LEVELS AND THE REST
        TSDateRange range = global().get(drangeName).getJSCDRange()
                .getTSDateRange();

        TSDate startDate = range.upperBound();

        JSCNArray fc_orig_y = ytLevels; // 0 vector to init

        // here we check, whether we can use the selected variables
        JSCNArray selData = getForecUndiffConfig().getYtLevels();
        JSCDRange selDRange = getForecUndiffConfig().getSelDRange();

        if (selData.cols() == global().get(yName).getJSCNArray().cols())
            if (!selDRange.isEmpty()
                    && selDRange.getTSDateRange().upperBound().compareTo(
                            startDate) <= 0) {
                startDate = selDRange.getTSDateRange().lowerBound();
                fc_orig_y = selData;
            }
        JSCNArray selYOrig = getForecUndiffConfig().getSelYLevels();
        if (selYOrig.isEmpty())
            selYOrig = fc_orig_y;
        JSCNArray adReordered = null;
        if (modelType == ModelTypes.VECM) {
            // reorder becauseEC restricted dets may change order
            adReordered = reorderADForVECMwithECRes();
        }
        PCall job = new ForecastCall(global(), getHSelector().getIntNumber(),
                selIndex, yt_forecast, fc_orig_y, selYOrig, getDetTable()
                        .getJSCNArray(), getExTable().getJSCNArray(),
                modelType, true, false,
                getCICoverageSelector().getNumber() / 100, startDate,
                adReordered);
        job.setSymbolTable(local());
        job.setOutHolder(getResultField());
        job.addPCallListener(new PCallAdapter() {
            public void success() {
                getForecasting().setSelectedIndex(1);
            }
        });

        job.execute();

    }

    private void computeDeterministicVariables() {

        local().get(d_forecast_Def).clear();
        JSCNArray detDataAll = global().get(dName).getJSCNArray();

        if (detDataAll.isEmpty()) {
            getDetTable().setEditable(false);
            return;
        }
        getDetTable().setEditable(true);
        int h = getHSelector().getIntNumber();

        // Set the deterministic variables for the forecast
        // but check, whether we have some values, we can use
        JSCSArray ndraw = global().get(ndRawName).getJSCSArray();
        JSCNArray d_forec = local().get(d_forecast_Def).getJSCNArray();
        TSDateRange range = global().get(drangeName).getJSCDRange()
                .getTSDateRange();

        TSDate startDate = range.upperBound().addPeriods(1);
        JSCNArray detSel = global().get(detSelName).getJSCNArray();

        // trend is last
        int lastTrendVal = 0;
        if (detSel.intAt(2, 0) == 1)
            lastTrendVal = detDataAll.intAt(detDataAll.rows() - 1, detDataAll
                    .cols() - 1);

        // find user selected
        JSCNArray detDataSel = global().get(dRawName).getJSCNArray();
        JSCNArray d_forecast1 = ForecastHelper.extrapolateSelData(ndraw,
                detDataSel, d_forec, startDate, h);

        // assemble standards
        JSCNArray d_forecast2 = ForecastHelper.extrapolateStdDets(detSel, range
                .subPeriodicity(), h, startDate, lastTrendVal);

        d_forecast1.appendCols(d_forecast2);

        local().get(d_forecast_Def).setJSCData(d_forecast1);

    }

    private void computeExogenousVariables() {
        JSCNArray x = global().get(xName).getJSCNArray();
        JSCSArray nx = global().get(nxName).getJSCSArray();
        TSDateRange range = global().get(drangeName).getJSCDRange()
                .getTSDateRange();

        if (x.isEmpty()) {
            getExTable().setEditable(false);
            return;
        }
        getExTable().setEditable(true);
        JSCNArray x_forec = local().get(x_forecast_Def).getJSCNArray();
        TSDate startDate = range.upperBound().addPeriods(1);
        int h = getHSelector().getIntNumber();
        // find user selected
        JSCNArray x_forecast = ForecastHelper.extrapolateSelData(nx, x,
                x_forec, startDate, h);

        local().get(x_forecast_Def).setJSCData(x_forecast);

    }

    private void computeVariables() {
        getForecasting().setSelectedIndex(0);
        int h = getHSelector().getIntNumber();
        TSDateRange range = global().get(drangeName).getJSCDRange()
                .getTSDateRange();

        int py = global().get(pyName).getJSCInt().intVal();
        int px = global().get(pxName).getJSCInt().intVal();
        JSCNArray yData = global().get(yName).getJSCNArray();
        JSCNArray xData = global().get(xName).getJSCNArray();
        JSCSArray ny = global().get(nyName).getJSCSArray();

        // for row header
        TSDate endDate = range.upperBound();
        String[] h_index = new String[h];
        for (int i = 0; i < h; i++)
            h_index[i] = endDate.addPeriods(i + 1).toString();
        JSCSArray timeIndex = new JSCSArray("rowHeader", h_index);
        if (global().get(ndName).getJSCSArray().rows() > 0)
            getDetScroll().setRowHeaderData(timeIndex);
        if (global().get(nxName).getJSCSArray().rows() > 0)
            getExScroll().setRowHeaderData(timeIndex);

        computeDeterministicVariables();
        computeExogenousVariables();
        if (recompute)
            getStartDateSelector().setEnclosingRange(range);
        double[] yt = new double[py * yData.cols() + px * xData.cols()];
        int count = 0;
        for (int i = 0; i < py; i++)
            for (int j = 0; j < yData.cols(); j++) {
                yt[count++] = yData.doubleAt(yData.rows() - 1 - i, j);
            }
        for (int i = 0; i < px; i++)
            for (int j = 0; j < xData.cols(); j++) {
                yt[count++] = xData.doubleAt(xData.rows() - 1 - i, j);
            }
        if (modelType == ModelTypes.VAR) {
            if (global().get(ayName).isEmpty()) {
                getAsymCi().setEnabled(false);
                getAsymCi().setSelected(false);
            } else
                getAsymCi().setEnabled(true);
        }
        double[][] yt_levels = new double[1][ny.rows()];

        JSCSArray level_names = new JSCSArray("Ny_LevelNames", ny.stringArray());
        for (int i = 0; i < level_names.rows(); i++) {
            int l = (level_names.stringAt(i, 0).length() > 3) ? 3 : level_names
                    .stringAt(i, 0).length();
            level_names.setValAt(level_names.stringAt(i, 0).substring(0, l)
                    + "-level(t)", i, 0);

        }
        getForecUndiffConfig().setOrigRange(range);
        if (recompute) {
            ytLevels = new JSCNArray("Yt_levels", yt_levels);
            getForecUndiffConfig().setYtLevelsNames(level_names);
            yt_forecast = new JSCNArray("yt_forecast", yt);
        }
        recompute = false;

    }

    /**
     * connEtoC1: (Forecast.action. --> Forecasting.forecast_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC1() {
        try {
            // user code begin {1}
            // user code end
            this.executeLevel();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC2: (ForecastUndiff.action. -->
     * Forecasting.forecastUndiff_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC2() {
        try {
            // user code begin {1}
            // user code end
            this.executeUndiff();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC3: (UndiffConfig.action. -->
     * Forecasting.undiffConfig_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC3() {
        try {
            // user code begin {1}
            // user code end
            this.undiffConfig_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC4: (HSelector.number --> ForecastPanel.computeVariables()V)
     * 
     * @param arg1
     *            java.beans.PropertyChangeEvent
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC4(java.beans.PropertyChangeEvent arg1) {
        try {
            // user code begin {1}
            // user code end
            this.computeVariables();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * Return the AsymCi property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getAsymCi() {
        if (ivjAsymCi == null) {
            try {
                ivjAsymCi = new javax.swing.JCheckBox();
                ivjAsymCi.setName("AsymCi");
                ivjAsymCi.setPreferredSize(new java.awt.Dimension(97, 25));
                ivjAsymCi.setText("Asymptotic CI");
                ivjAsymCi.setMaximumSize(new java.awt.Dimension(97, 25));
                ivjAsymCi.setMinimumSize(new java.awt.Dimension(97, 25));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjAsymCi;
    }

    private NumSelector getCICoverageSelector() {
        if (ivjCICoverageSelector == null) {
            try {
                ivjCICoverageSelector = new NumSelector();
                ivjCICoverageSelector.setName("CICoverageSelector");
                ivjCICoverageSelector.setNumber(95.0);
                ivjCICoverageSelector.setRangeExpr("(0,100)");
                ivjCICoverageSelector.setPreferredSize(new java.awt.Dimension(
                        50, 20));
                ivjCICoverageSelector.setMinimumSize(new java.awt.Dimension(50,
                        20));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjCICoverageSelector;
    }

    private JSCDataTableScrollPane getDetScroll() {
        if (ivjDetScroll == null) {
            try {
                ivjDetScroll = new JSCDataTableScrollPane();
                ivjDetScroll.setName("DetScroll");
                ivjDetScroll.setAutoscrolls(true);
                ivjDetScroll.setMinimumVisibleColumns(1);
                ivjDetScroll.setMaximumVisibleColumns(10);
                ivjDetScroll.setRowHeaderShowing(true);
                ivjDetScroll.setColumnHeaderShowing(true);
                ivjDetScroll.setMaximumVisibleRows(6);
                ivjDetScroll.setMinimumVisibleRows(1);
                getDetScroll().setViewportView(getDetTable());
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjDetScroll;
    }

    private JSCNArrayTable getDetTable() {
        if (ivjDetTable == null) {
            try {
                ivjDetTable = new JSCNArrayTable();
                ivjDetTable.setName("DetTable");
                getDetScroll()
                        .setColumnHeaderView(ivjDetTable.getTableHeader());
                ivjDetTable.setSymbolScope(Scope.LOCAL);
                ivjDetTable.setDynamicColumnWidth(true);
                ivjDetTable.setMaximumColumnWidth(300);
                ivjDetTable.setSymbolName(d_forecast_Def.name);
                ivjDetTable.setBounds(1, 1, 45, 17);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjDetTable;
    }

    private JSCDataTableScrollPane getExScroll() {
        if (ivjExScroll == null) {
            try {
                ivjExScroll = new JSCDataTableScrollPane();
                ivjExScroll.setName("ExScroll");
                ivjExScroll.setAutoscrolls(true);
                ivjExScroll.setMaximumVisibleColumns(10);
                ivjExScroll.setRowHeaderShowing(true);
                ivjExScroll.setColumnHeaderShowing(true);
                ivjExScroll.setMaximumVisibleRows(6);
                getExScroll().setViewportView(getExTable());
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjExScroll;
    }

    private JSCNArrayTable getExTable() {
        if (ivjExTable == null) {
            try {
                ivjExTable = new JSCNArrayTable();
                ivjExTable.setName("ExTable");
                getExScroll().setColumnHeaderView(ivjExTable.getTableHeader());
                ivjExTable.setSymbolScope(Scope.LOCAL);
                ivjExTable.setDynamicColumnWidth(true);
                ivjExTable.setSymbolName(x_forecast_Def.name);
                ivjExTable.setBounds(0, 0, 144, 33);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjExTable;
    }

    /**
     * Return the Forecast property value.
     * 
     * @return javax.swing.JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JButton getForecast() {
        if (ivjForecast == null) {
            try {
                ivjForecast = new javax.swing.JButton();
                ivjForecast.setName("Forecast");
                ivjForecast
                        .setToolTipText("Forecast selected endogenous variables in levels.");
                ivjForecast.setText("Forecast");
                ivjForecast.setMaximumSize(new java.awt.Dimension(85, 25));
                ivjForecast.setPreferredSize(new java.awt.Dimension(100, 25));
                ivjForecast.setMinimumSize(new java.awt.Dimension(100, 25));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjForecast;
    }

    /**
     * Return the Forecasting property value.
     * 
     * @return javax.swing.JTabbedPane
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JTabbedPane getForecasting() {
        if (ivjForecasting == null) {
            try {
                ivjForecasting = new javax.swing.JTabbedPane();
                ivjForecasting.setName("Forecasting");
                ivjForecasting
                        .setPreferredSize(new java.awt.Dimension(700, 186));
                ivjForecasting.setEnabled(true);
                ivjForecasting.insertTab("Specify Forecast", null,
                        getSpecify(), null, 0);
                ivjForecasting.setEnabledAt(0, true);
                ivjForecasting.insertTab("Text (save/print)", null, getText(),
                        null, 1);
                ivjForecasting.setEnabledAt(1, true);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjForecasting;
    }

    /**
     * Return the ForecastUndiff property value.
     * 
     * @return javax.swing.JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JButton getForecastUndiff() {
        if (ivjForecastUndiff == null) {
            try {
                ivjForecastUndiff = new javax.swing.JButton();
                ivjForecastUndiff.setName("ForecastUndiff");
                ivjForecastUndiff
                        .setToolTipText("Forecast 1st undifferenced series.");
                ivjForecastUndiff.setPreferredSize(new java.awt.Dimension(200,
                        25));
                ivjForecastUndiff.setText("Undifferenced Forecast");
                // user code begin {1}
                ivjForecastUndiff
                        .setMinimumSize(new java.awt.Dimension(200, 26));
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjForecastUndiff;
    }

    private NumSelector getHSelector() {
        if (ivjHSelector == null) {
            try {
                ivjHSelector = new NumSelector();
                ivjHSelector.setName("HSelector");
                ivjHSelector.setRangeExpr("[1,200]");
                ivjHSelector.setPreferredSize(new java.awt.Dimension(50, 20));
                ivjHSelector.setMinimumSize(new java.awt.Dimension(50, 20));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjHSelector;
    }

    /**
     * Return the JLabel property value.
     * 
     * @return javax.swing.JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JLabel getJLabel() {
        if (ivjJLabel == null) {
            try {
                ivjJLabel = new javax.swing.JLabel();
                ivjJLabel.setName("JLabel");
                ivjJLabel.setPreferredSize(new java.awt.Dimension(135, 20));
                ivjJLabel.setText("Variables to forecast");
                ivjJLabel.setMinimumSize(new java.awt.Dimension(121, 20));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJLabel;
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
                ivjJLabel1.setPreferredSize(new java.awt.Dimension(90, 20));
                ivjJLabel1.setText("Deterministics");
                ivjJLabel1.setMinimumSize(new java.awt.Dimension(80, 20));
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
                ivjJLabel2.setPreferredSize(new java.awt.Dimension(90, 20));
                ivjJLabel2.setText("Horizon");
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
     * Return the JLabel3 property value.
     * 
     * @return javax.swing.JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JLabel getJLabel3() {
        if (ivjJLabel3 == null) {
            try {
                ivjJLabel3 = new javax.swing.JLabel();
                ivjJLabel3.setName("JLabel3");
                ivjJLabel3.setPreferredSize(new java.awt.Dimension(90, 20));
                ivjJLabel3.setText("Exogenous");
                ivjJLabel3.setMinimumSize(new java.awt.Dimension(80, 20));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJLabel3;
    }

    /**
     * Return the JLabel4 property value.
     * 
     * @return javax.swing.JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JLabel getJLabel4() {
        if (ivjJLabel4 == null) {
            try {
                ivjJLabel4 = new javax.swing.JLabel();
                ivjJLabel4.setName("JLabel4");
                ivjJLabel4.setPreferredSize(new java.awt.Dimension(93, 20));
                ivjJLabel4.setText("CI coverage (%)");
                ivjJLabel4.setMinimumSize(new java.awt.Dimension(105, 20));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJLabel4;
    }

    /**
     * Return the JLabel5 property value.
     * 
     * @return javax.swing.JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JLabel getJLabel5() {
        if (ivjJLabel5 == null) {
            try {
                ivjJLabel5 = new javax.swing.JLabel();
                ivjJLabel5.setName("JLabel5");
                ivjJLabel5.setPreferredSize(new java.awt.Dimension(120, 20));
                ivjJLabel5.setText("Start date of plot (levels)");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJLabel5;
    }

    /**
     * Gets the model type that is currently in use.
     * 
     * @return the type of the model to forecast
     */
    public ModelTypes getModelType() {
        return modelType;
    }

    private JSCDataTableScrollPane getNamesPane() {
        if (ivjNamesPane == null) {
            try {
                ivjNamesPane = new JSCDataTableScrollPane();
                ivjNamesPane.setName("NamesPane");
                ivjNamesPane.setMaximumVisibleColumns(1);
                getNamesPane().setViewportView(getNamesTable());
                // user code begin {1}
                ivjNamesPane.setMinimumVisibleRows(5);
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjNamesPane;
    }

    private JSCSArrayTable getNamesTable() {
        if (ivjNamesTable == null) {
            try {
                ivjNamesTable = new JSCSArrayTable();
                ivjNamesTable.setName("NamesTable");
                getNamesPane().setColumnHeaderView(
                        ivjNamesTable.getTableHeader());
                ivjNamesTable.setDynamicColumnWidth(true);
                ivjNamesTable.setBounds(0, 0, 433, 58);
                ivjNamesTable.setRowSelectionAllowed(true);
                ivjNamesTable.setEditable(false);
                ivjNamesTable.setMinimumColumnWidth(10);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjNamesTable;
    }

    /**
     * Return the ResultField property value.
     * 
     * @return ResultField
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private com.jstatcom.component.ResultField getResultField() {
        if (ivjResultField == null) {
            try {
                ivjResultField = new com.jstatcom.component.ResultField();
                ivjResultField.setName("ResultField");
                // user code begin {1}
                ivjResultField.setBorder(new BevelBorder(BevelBorder.LOWERED));
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjResultField;
    }

    private JPanel getSpecify() {
        if (ivjSpecify == null) {
            try {
                ivjSpecify = new JPanel();
                ivjSpecify.setName("Specify");
                ivjSpecify.setLayout(new java.awt.GridBagLayout());
                ivjSpecify.setVisible(true);

                java.awt.GridBagConstraints constraintsJLabel1 = new java.awt.GridBagConstraints();
                constraintsJLabel1.gridx = 0;
                constraintsJLabel1.gridy = 1;
                constraintsJLabel1.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel1.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsJLabel1.insets = new java.awt.Insets(0, 10, 0, 5);
                getSpecify().add(getJLabel1(), constraintsJLabel1);

                java.awt.GridBagConstraints constraintsDetScroll = new java.awt.GridBagConstraints();
                constraintsDetScroll.gridx = 1;
                constraintsDetScroll.gridy = 1;
                constraintsDetScroll.gridwidth = 7;
                constraintsDetScroll.fill = java.awt.GridBagConstraints.BOTH;
                constraintsDetScroll.weightx = 1.0;
                constraintsDetScroll.weighty = 1.0;
                constraintsDetScroll.insets = new java.awt.Insets(0, 0, 5, 0);
                getSpecify().add(getDetScroll(), constraintsDetScroll);

                java.awt.GridBagConstraints constraintsHSelector = new java.awt.GridBagConstraints();
                constraintsHSelector.gridx = 1;
                constraintsHSelector.gridy = 0;
                constraintsHSelector.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsHSelector.insets = new java.awt.Insets(5, 0, 10, 0);
                getSpecify().add(getHSelector(), constraintsHSelector);

                java.awt.GridBagConstraints constraintsExScroll = new java.awt.GridBagConstraints();
                constraintsExScroll.gridx = 1;
                constraintsExScroll.gridy = 2;
                constraintsExScroll.gridwidth = 7;
                constraintsExScroll.gridheight = 2;
                constraintsExScroll.fill = java.awt.GridBagConstraints.BOTH;
                constraintsExScroll.weightx = 1.0;
                constraintsExScroll.weighty = 1.0;
                getSpecify().add(getExScroll(), constraintsExScroll);

                java.awt.GridBagConstraints constraintsJLabel2 = new java.awt.GridBagConstraints();
                constraintsJLabel2.gridx = 0;
                constraintsJLabel2.gridy = 0;
                constraintsJLabel2.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel2.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsJLabel2.insets = new java.awt.Insets(5, 10, 10, 5);
                getSpecify().add(getJLabel2(), constraintsJLabel2);

                java.awt.GridBagConstraints constraintsJLabel3 = new java.awt.GridBagConstraints();
                constraintsJLabel3.gridx = 0;
                constraintsJLabel3.gridy = 2;
                constraintsJLabel3.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel3.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsJLabel3.insets = new java.awt.Insets(0, 10, 0, 5);
                getSpecify().add(getJLabel3(), constraintsJLabel3);

                java.awt.GridBagConstraints constraintsForecast = new java.awt.GridBagConstraints();
                constraintsForecast.gridx = 2;
                constraintsForecast.gridy = 5;
                constraintsForecast.gridwidth = 2;
                constraintsForecast.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsForecast.anchor = java.awt.GridBagConstraints.WEST;
                constraintsForecast.insets = new java.awt.Insets(5, 0, 5, 5);
                getSpecify().add(getForecast(), constraintsForecast);

                java.awt.GridBagConstraints constraintsJLabel = new java.awt.GridBagConstraints();
                constraintsJLabel.gridx = 4;
                constraintsJLabel.gridy = 0;
                constraintsJLabel.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsJLabel.insets = new java.awt.Insets(5, 20, 0, 5);
                getSpecify().add(getJLabel(), constraintsJLabel);

                java.awt.GridBagConstraints constraintsNamesPane = new java.awt.GridBagConstraints();
                constraintsNamesPane.gridx = 7;
                constraintsNamesPane.gridy = 0;
                constraintsNamesPane.gridwidth = 7;
                constraintsNamesPane.fill = java.awt.GridBagConstraints.BOTH;
                constraintsNamesPane.weightx = 1.0;
                constraintsNamesPane.insets = new java.awt.Insets(5, 0, 0, 0);
                getSpecify().add(getNamesPane(), constraintsNamesPane);

                java.awt.GridBagConstraints constraintsAsymCi = new java.awt.GridBagConstraints();
                constraintsAsymCi.gridx = 0;
                constraintsAsymCi.gridy = 5;
                constraintsAsymCi.gridwidth = 2;
                constraintsAsymCi.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsAsymCi.insets = new java.awt.Insets(5, 10, 5, 0);
                getSpecify().add(getAsymCi(), constraintsAsymCi);

                java.awt.GridBagConstraints constraintsJLabel4 = new java.awt.GridBagConstraints();
                constraintsJLabel4.gridx = 2;
                constraintsJLabel4.gridy = 0;
                constraintsJLabel4.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel4.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsJLabel4.insets = new java.awt.Insets(5, 20, 10, 5);
                getSpecify().add(getJLabel4(), constraintsJLabel4);

                java.awt.GridBagConstraints constraintsCICoverageSelector = new java.awt.GridBagConstraints();
                constraintsCICoverageSelector.gridx = 3;
                constraintsCICoverageSelector.gridy = 0;
                constraintsCICoverageSelector.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsCICoverageSelector.insets = new java.awt.Insets(5,
                        0, 10, 0);
                getSpecify().add(getCICoverageSelector(),
                        constraintsCICoverageSelector);

                java.awt.GridBagConstraints constraintsJLabel5 = new java.awt.GridBagConstraints();
                constraintsJLabel5.gridx = 0;
                constraintsJLabel5.gridy = 4;
                constraintsJLabel5.gridwidth = 2;
                constraintsJLabel5.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel5.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsJLabel5.insets = new java.awt.Insets(5, 10, 0, 5);
                getSpecify().add(getJLabel5(), constraintsJLabel5);

                java.awt.GridBagConstraints constraintsStartDateSelector = new java.awt.GridBagConstraints();
                constraintsStartDateSelector.gridx = 2;
                constraintsStartDateSelector.gridy = 4;
                constraintsStartDateSelector.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsStartDateSelector.insets = new java.awt.Insets(5, 0,
                        0, 0);
                getSpecify().add(getStartDateSelector(),
                        constraintsStartDateSelector);

                java.awt.GridBagConstraints constraintsForecastUndiff = new java.awt.GridBagConstraints();
                constraintsForecastUndiff.gridx = 4;
                constraintsForecastUndiff.gridy = 4;
                constraintsForecastUndiff.gridwidth = 4;
                constraintsForecastUndiff.insets = new java.awt.Insets(5, 5, 0,
                        10);
                getSpecify()
                        .add(getForecastUndiff(), constraintsForecastUndiff);

                java.awt.GridBagConstraints constraintsUndiffConfig = new java.awt.GridBagConstraints();
                constraintsUndiffConfig.gridx = 4;
                constraintsUndiffConfig.gridy = 5;
                constraintsUndiffConfig.gridwidth = 4;
                constraintsUndiffConfig.insets = new java.awt.Insets(5, 5, 5,
                        10);
                getSpecify().add(getUndiffConfig(), constraintsUndiffConfig);
                // user code begin {1}
                getSpecify().setBorder(new BevelBorder(BevelBorder.LOWERED));

                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSpecify;
    }

    /**
     * Return the StartDate property value.
     * 
     * @return com.jstatcom.ts.TSDateSelector
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private com.jstatcom.ts.TSDateSelector getStartDateSelector() {
        if (ivjStartDateSelector == null) {
            try {
                ivjStartDateSelector = new com.jstatcom.ts.TSDateSelector();
                ivjStartDateSelector.setName("StartDateSelector");
                ivjStartDateSelector.setPreferredSize(new java.awt.Dimension(
                        80, 20));
                ivjStartDateSelector.setMinimumSize(new java.awt.Dimension(80,
                        20));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjStartDateSelector;
    }

    /**
     * Return the Text property value.
     * 
     * @return javax.swing.JPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JPanel getText() {
        if (ivjText == null) {
            try {
                ivjText = new javax.swing.JPanel();
                ivjText.setName("Text");
                ivjText.setLayout(new javax.swing.BoxLayout(getText(),
                        javax.swing.BoxLayout.X_AXIS));
                getText().add(getResultField(), getResultField().getName());
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjText;
    }

    /**
     * Return the UndiffConfig property value.
     * 
     * @return javax.swing.JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JButton getUndiffConfig() {
        if (ivjUndiffConfig == null) {
            try {
                ivjUndiffConfig = new javax.swing.JButton();
                ivjUndiffConfig.setName("UndiffConfig");
                ivjUndiffConfig
                        .setToolTipText("Configure the undifferenced forecast.");
                ivjUndiffConfig
                        .setPreferredSize(new java.awt.Dimension(200, 25));
                ivjUndiffConfig.setMinimumSize(new java.awt.Dimension(200, 25));
                ivjUndiffConfig.setText("Configure Undifferenced Forecast");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjUndiffConfig;
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
        getForecast().addActionListener(ivjEventHandler);
        getForecastUndiff().addActionListener(ivjEventHandler);
        getUndiffConfig().addActionListener(ivjEventHandler);
        getHSelector().addPropertyChangeListener(ivjEventHandler);
    }

    /**
     * Initialize the class.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initialize() {
        try {
            // user code begin {1}
            // user code end
            setName("Forecasting");
            setLayout(new javax.swing.BoxLayout(this,
                    javax.swing.BoxLayout.X_AXIS));
            setSize(700, 400);
            add(getForecasting(), getForecasting().getName());
            initConnections();
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}
        // user code end
    }

    /**
     * Sets the model type to be used for forecasts. This determines, which data
     * is used.
     * 
     * @param newModelType
     *            the model type
     * @throws IllegalArgumentException
     *             if unknonw model type
     */
    public void setModelType(ModelTypes newModelType) {
        modelType = newModelType;

        setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED),
                modelType + " Forecasting", TitledBorder.RIGHT,
                TitledBorder.TOP));

        if (modelType == ModelTypes.VAR) {
            getAsymCi().setEnabled(true);
            covarName = VARConstants.cv_u_hat_Def;
            ndName = VARConstants.Nd_Def;
            ndRawName = VARConstants.Nd_raw_Def;
            detSelName = VARConstants.det_sel_Def;
            dRawName = VARConstants.d_raw_Def;
            nxName = VARConstants.Nx_Def;
            nyName = VARConstants.Ny_Def;
            ayName = VARConstants.Ay_Def;
            yName = VARConstants.y_raw_Def;
            xName = VARConstants.x_raw_Def;
            dName = VARConstants.d_all_Def;
            drangeName = VARConstants.T1_Def;
            pyName = VARConstants.py_Def;
            pxName = VARConstants.px_Def;
            return;
        }
        if (modelType == ModelTypes.VECM) {
            getAsymCi().setSelected(false);
            getAsymCi().setEnabled(false);
            covarName = VECMConstants.cv_u_hat_Def;
            ndName = VECMConstants.Nd_Def;
            ndRawName = VECMConstants.Nd_raw_Def;
            detSelName = VECMConstants.det_sel_Def;
            nxName = VECMConstants.Nx_Def;
            nyName = VECMConstants.Ny_Def;
            ayName = VECMConstants.A_Def;
            yName = VECMConstants.y_raw_Def;
            xName = VECMConstants.x_raw_Def;
            dName = VECMConstants.d_Def;
            dRawName = VECMConstants.d_raw_Def;
            drangeName = VECMConstants.T1_Def;
            pyName = VECMConstants.py_Def;
            pxName = VECMConstants.px_Def;
            idx_cd2ci = VECMConstants.idx_cd2ci_Def;
            ad = VECMConstants.C_var_Def;
            return;
        }
        throw new IllegalArgumentException(modelType
                + " is unknown model type.");

    }

    public void shown(boolean isShown) {
        if (isShown && recompute) {

            computeVariables();
        }

    }

    private ForecastUndiffConfig getForecUndiffConfig() {
        if (fcUndiff == null) {
            fcUndiff = new ForecastUndiffConfig(TopFrameReference
                    .getTopFrameRef(), false);
            fcUndiff.setSize(694, 481);
            fcUndiff.setLocationRelativeTo(this);
        }

        return fcUndiff;
    }

    /**
     * Comment
     */
    private void undiffConfig_ActionEvents() {
        getForecUndiffConfig().setVisible(true);
    }
}