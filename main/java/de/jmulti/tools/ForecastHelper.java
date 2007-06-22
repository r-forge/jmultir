/*
 * Copyright (C) 2006 Markus Kraetzig
 * Created on 17.07.2006
 */
package de.jmulti.tools;

import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.JSCSArray;
import com.jstatcom.ts.TS;
import com.jstatcom.ts.TSDate;
import com.jstatcom.ts.TSDateRange;
import com.jstatcom.ts.TSHolder;
import com.jstatcom.util.UMatrix;

public class ForecastHelper {

    public static JSCNArray extrapolateSelData(JSCSArray selNames,
            JSCNArray selData, JSCNArray dataFromTable, TSDate forecStartDate,
            int h) {
        double[][] d_forecast = new double[h][selNames.rows()];
        TSHolder data = TSHolder.getInstance();
        for (int j = 0; j < selNames.rows(); j++) {
            // Check whether there are still values in the series we can use
            TS series = data.getTS(selNames.stringAt(j, 0));
            TSDate start = series.start();
            int T = series.numOfObs();
            int nr = start.compareTo(forecStartDate);
            int to_use_for_forecast = T - nr - 1;
            // Check what kind of variable it is

            // the default (includes impulse/shift dummies
            // {0 0 a 0} or {a a 0 0 0})
            double value = 0;

            double last = selData.doubleAt(selData.rows() - 1, j);
            double preLast = selData.doubleAt(selData.rows() - 2, j);
            double prePreLast = selData.doubleAt(selData.rows() - 3, j);

            // Looks like a shift {0 0 0 a a a} - use last value
            if (last != 0 && last == preLast)
                value = selData.doubleAt(selData.rows() - 1, j);
            // Looks like some other non dummy variable - use last value
            else if (last != preLast && last != prePreLast
                    && preLast != prePreLast)
                value = selData.doubleAt(selData.rows() - 1, j);

            for (int k = 0; k < h; k++) {
                if (to_use_for_forecast >= 0) {
                    d_forecast[k][j] = series.valueAt(T - 1
                            - to_use_for_forecast--);
                    value = d_forecast[k][j];
                    if (to_use_for_forecast < 0 && k > 0)
                        if (d_forecast[k - 1][j] == 0)
                            value = 0;

                } else {
                    if (dataFromTable.rows() > k && dataFromTable.cols() > j)
                        d_forecast[k][j] = dataFromTable.doubleAt(k, j);
                    else
                        d_forecast[k][j] = value;
                }

            }
        }

        return new JSCNArray("d_forecast", d_forecast);
    }

    public static JSCNArray extrapolateStdDets(JSCNArray detSel,
            int periodicity, int h, TSDate start, int lastTrendVal) {

        boolean centered = false;
        JSCNArray dForecastData = new JSCNArray("d_forecast");
        int cols = 0;
        // const
        if (detSel.intAt(0, 0) == 1)
            cols++;
        // seasdum
        if (detSel.intAt(1, 0) == 1) {
            cols += (periodicity - 1);
            // only for VECM: centered or standard
            if (detSel.rows() == 4)
                centered = detSel.intAt(3, 0) == 0;
        }
        // trend
        if (detSel.intAt(2, 0) == 1)
            cols++;

        if (cols == 0)
            return dForecastData;

        // Intercept
        if (detSel.intAt(0, 0) == 1)
            dForecastData
                    .appendCols(new JSCNArray("const", UMatrix.ones(h, 1)));

        // Seasonal dummies, assume not centered and not all
        if (detSel.intAt(1, 0) == 1) {

            TSDateRange forecastRange = new TSDateRange(start, h);
            // in case of VECM need centered
            double[][] seas = forecastRange.createSeasDum(centered, false);
            dForecastData.appendCols(new JSCNArray("seas", seas));
        }
        // Trend
        if (detSel.intAt(2, 0) == 1)
            dForecastData.appendCols(new JSCNArray("trend", UMatrix.seqa(
                    lastTrendVal + 1, 1, h)));

        return dForecastData;
    }
}
