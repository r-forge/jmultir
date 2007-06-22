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

package de.jmulti.proc;

import com.jstatcom.engine.gauss.GaussLoadTypes;
import com.jstatcom.model.JSCData;
import com.jstatcom.model.JSCInt;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.JSCNumber;
import com.jstatcom.model.JSCString;
import com.jstatcom.ts.TSDateRange;
import com.jstatcom.util.FArg;

/**
 * This GAUSS command object computes the cross plots between two series.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
public final class CrossPlotCall extends RPCall {

    private JSCNArray xData = null;

    private JSCNArray yData = null;

    private String xName = null;

    private String yName = null;

    private TSDateRange range = null;

    private int kernel = 0;

    private boolean isOLS = false;

    private boolean isNadaraya = false;

    private JSCNArray cross_b = null;

    private JSCNArray cross_std = null;

    private JSCNumber cross_sigma = null;

    private JSCNumber cross_rsq = null;

    /**
     * <code>CrossPlotCall</code> constructor takes the arguments for the
     * procedure call.
     * 
     * @param kernel
     *            <ul>
     *            <li>0 - Gaussian kernel
     *            <li>1 - biweight kernel
     *            <li>2 - rectangular kernel
     *            <li>3 - triangular kernel
     *            <li>4 - Epanechnikov kernel
     *            </ul>
     */
    public CrossPlotCall(JSCNArray xData, JSCNArray yData, String xName,
            String yName, TSDateRange range, boolean isOLS, boolean isNadaraya,
            int kernel) {

        super();

        setName("Crossplots");

        this.range = range;
        this.xData = xData;
        this.yData = yData;
        this.xName = xName;
        this.yName = yName;
        this.isOLS = isOLS;
        this.isNadaraya = isNadaraya;
        this.kernel = kernel;

    }

    /**
     * Writes the text output to a string that can be referenced via
     * <code>getOutput</code>.
     */
    protected void finalCode() {
        if (!isOLS)
            return;
        if (cross_sigma.isEmpty())
            return;
        //"b", "stderr", "sigma", "rsq"
        StringBuffer buffer = output;
        double[][] b = cross_b.doubleArray();
        double[][] std = cross_std.doubleArray();
        double sigma = cross_sigma.doubleVal();
        double rsq = cross_rsq.doubleVal();
        buffer.append("OLS ESTIMATION\n");
        buffer.append(range.format("sample range:", 16) + "\n");
        buffer.append(FArg.sprintf("%-15s %s\n", new FArg("dependent:")
                .add(yName)));
        buffer.append(FArg.sprintf("%-15s %s\n\n", new FArg("independent:")
                .add(xName)));
        buffer.append(FArg.sprintf("%-s = %- 8.4f + %- 8.4f*%-s \n", new FArg(
                yName).add(b[0][0]).add(b[1][0]).add(xName)));
        if (sigma > 0) {
            buffer.append(FArg.sprintf(
                    "t-values      =  { %- 8.4f  %- 8.4f }\n", new FArg(b[0][0]
                            / std[0][0]).add(b[1][0] / std[1][0])));
            buffer.append(FArg.sprintf("sigma         = %- 8.4f \n", new FArg(
                    sigma)));
        }
        buffer
                .append(FArg.sprintf("R-squared     = %- 8.4f \n",
                        new FArg(rsq)));
        buffer.append("\n");
    }

    /**
     * @see RPCall
     */
    protected void runCode() {

        if (yData.rows() < 2 || xData.rows() < 2)
            throw new RuntimeException("Not enough observations.");

        engine().load("pgraph", GaussLoadTypes.LIB);
        engine().load("tools", GaussLoadTypes.LIB);
        engine().load("plot", GaussLoadTypes.LIB);
        engine().load("fil", GaussLoadTypes.LIB);
        engine().load("diag", GaussLoadTypes.LIB);

        // set config data
        setGlobalPgraphSettings();

        cross_b = new JSCNArray("cross_b");
        cross_std = new JSCNArray("cross_std");
        cross_rsq = new JSCNumber("cross_rsq");
        cross_sigma = new JSCNumber("cross_sigma");

        engine().call(
                "crossplot_diagnos",
                new JSCData[] { xData, yData, new JSCString("xName", xName),
                        new JSCString("yName", yName),
                        new JSCInt("isOLS", isOLS),
                        new JSCInt("isNW", isNadaraya),
                        new JSCInt("kernel", kernel) },
                new JSCData[] { cross_b, cross_std, cross_rsq, cross_sigma });

    }
}