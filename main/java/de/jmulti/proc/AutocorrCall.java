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
import com.jstatcom.model.JSCSArray;
import com.jstatcom.model.JSCString;
import com.jstatcom.model.JSCTypeDef;
import com.jstatcom.model.JSCTypes;
import com.jstatcom.util.FArg;
import com.jstatcom.util.UData;
import com.jstatcom.util.UStringArray;

/**
 * This GAUSS command object computes and plots the autocorrelation of one or
 * more series.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
public final class AutocorrCall extends RPCall {
    public static final JSCTypeDef AUTOCORR_RESULT = new JSCTypeDef(
            "AUTOCORR_RESULT", JSCTypes.NARRAY,
            "Autocorrelations up to specified lag order for each variable in a col.");

    public static final JSCTypeDef AUTOCORR_PORT = new JSCTypeDef(
            "AUTOCORR_PORT",
            JSCTypes.NARRAY,
            "Results of Portmanteau tests: bp~prob_chi~lb~prob_chilb for each variable in a row.");

    private JSCNArray data = null;

    private JSCSArray names = null;

    private boolean isSquared = false;

    private boolean isTextOutput = false;

    private int lags = 0;

    private JSCNArray portRes = null;

    private JSCNArray outRes = null;

    private boolean isDisplayPort = true;

    /**
     * <code>AutocorrCall</code> constructor takes the arguments for the
     * procedure call.
     */
    public AutocorrCall(JSCNArray data, JSCSArray names, int lags,
            boolean isSquared, boolean isTextOutput, boolean isDisplayPort) {

        super();

        setName("Autocorrelation of Series");

        this.data = data;
        this.names = names;
        this.lags = lags;
        this.isSquared = isSquared;
        this.isTextOutput = isTextOutput;
        this.isDisplayPort = isDisplayPort;
    }

    /**
     * Writes the text output to a string that can be referenced via
     * <code>getOutput</code>.
     */
    protected void finalCode() {

        if (!isTextOutput)
            return;

        StringBuffer buffer = output;

        String namArray[] = names.getCol(0);
        if (isSquared)
            namArray = UStringArray.appendSuffix(namArray, "^2");

        double[][] out = outRes.doubleArray();

        buffer.append("AUTOCORRELATION FUNCTION (p <= " + lags + ")\n");

        buffer.append(FArg.sprintf("\n%- 8s", new FArg(" ")));
        for (int i = 0; i < namArray.length; i++)
            buffer.append(FArg.sprintf("%- 16s", new FArg(namArray[i])));

        if (isDisplayPort) {
            double[][] port = portRes.doubleArray();
            buffer.append(FArg.sprintf("\n\n%-7s", new FArg("port")));
            for (int i = 0; i < namArray.length; i++)
                buffer.append(FArg.sprintf("%- 16.4f", new FArg(port[i][0])));
            buffer.append(FArg.sprintf("\n%-7s", new FArg("p-val")));

            for (int i = 0; i < namArray.length; i++)
                buffer.append(FArg.sprintf("%- 16.4f", new FArg(port[i][1])));
            buffer.append(FArg.sprintf("\n%-7s", new FArg("L&B")));

            for (int i = 0; i < namArray.length; i++)
                buffer.append(FArg.sprintf("%- 16.4f", new FArg(port[i][2])));
            buffer.append(FArg.sprintf("\n%-7s", new FArg("p-val")));

            for (int i = 0; i < namArray.length; i++)
                buffer.append(FArg.sprintf("%- 16.4f", new FArg(port[i][3])));
        }
        buffer.append(FArg.sprintf("\n\n%- 8s", new FArg("lag")));
        for (int i = 0; i < namArray.length; i++)
            buffer.append(FArg
                    .sprintf("%- 7s %- 8s", new FArg("ac").add("pac")));

        for (int i = 0; i < out.length; i++) {
            buffer.append(FArg.sprintf("\n%- 7i", new FArg(i + 1)));
            for (int j = 0; j < out[0].length; j += 2)
                buffer.append(FArg.sprintf("%- 5.4f %- 8.4f", new FArg(
                        out[i][j]).add(out[i][j + 1])));
        }
        ;
        buffer.append("\n");
    }

    /**
     * @see gauss.GaussProcCall
     */
    protected void runCode() {
        StringBuffer exec = new StringBuffer();
        if (getSymbolTable() != null) {
            getSymbolTable().get(AUTOCORR_RESULT).clear();
            if (isTextOutput)
                getSymbolTable().get(AUTOCORR_PORT).clear();
        }

        if (data.rows() < 2 * lags)
            throw new RuntimeException("Not enough observations.");

        JSCSArray nam = names;
        exec.append(AUTOCORR_RESULT + "={};" + AUTOCORR_PORT + "={};\n");
        if (isSquared) {
            data = new JSCNArray(data.name() + "_squared", data.getPow(2));
            nam = UData.appendSuffix(names, "^2", names.name());
        }

        engine().load("pgraph", GaussLoadTypes.LIB);
        engine().load("tools", GaussLoadTypes.LIB);
        engine().load("plot", GaussLoadTypes.LIB);
        engine().load("fil", GaussLoadTypes.LIB);
        engine().load("diag", GaussLoadTypes.LIB);

        // set config data
        if (!isTextOutput)
            setGlobalPgraphSettings();

        outRes = (JSCNArray) AUTOCORR_RESULT.getInstance();

        for (int i = 0; i < data.cols(); i++) {
            JSCNArray result = new JSCNArray("result");

            // compute autocorr function and graph optionally
            engine().call(
                    "autocorr_descript",
                    new JSCData[] { new JSCNArray("data", data.getCol(i)),
                            new JSCInt("lags", lags),
                            new JSCInt("isGraph", !isTextOutput),
                            new JSCString("name", nam.stringAt(i, 0)) },
                    new JSCData[] { result });

            outRes.appendCols(result);

            if (isTextOutput) {
                portRes = (JSCNArray) AUTOCORR_PORT.getInstance();
                // portmanteau
                engine().call(
                        "portman_diagnos",
                        new JSCData[] { new JSCNArray("data", data.getCol(i)),
                                new JSCInt("lags", lags),
                                new JSCInt("correction", 0) },
                        new JSCData[] { result });

                portRes.appendRows(result);
            }

        }

        if (getSymbolTable() != null) {
            getSymbolTable().set(outRes);
            if (isTextOutput)
                getSymbolTable().set(portRes);
        }
    }
}