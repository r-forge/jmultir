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
import com.jstatcom.model.JSCTypeDef;
import com.jstatcom.model.JSCTypes;
import com.jstatcom.util.FArg;
import com.jstatcom.util.UData;

/**
 * This GAUSS command object computes multivariate GARCH(1,1) estimation and
 * stores the results in a symbol table if one is set.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
public final class MGARCHCall extends RPCall {
    public static final JSCTypeDef MGARCH_EST = new JSCTypeDef("MGARCH_EST",
            JSCTypes.NARRAY, "mgarch estimation results");

    public static final JSCTypeDef MGARCH_TNORM = new JSCTypeDef(
            "MGARCH_TNORM", JSCTypes.NARRAY,
            "mgarch t-ratios with normal distribution assumption");

    public static final JSCTypeDef MGARCH_TEXACT = new JSCTypeDef(
            "MGARCH_TEXACT", JSCTypes.NARRAY,
            "mgarch t-ratios calculated exactly");

    public static final JSCTypeDef MGARCH_LOGLIK = new JSCTypeDef(
            "MGARCH_LOGLIK", JSCTypes.NARRAY, "log likelihood");

    public static final JSCTypeDef MGARCH_IVAR = new JSCTypeDef("MGARCH_IVAR",
            JSCTypes.NARRAY, "mgarch estimated variance processes");

    public static final JSCTypeDef MGARCH_RES = new JSCTypeDef("MGARCH_RES",
            JSCTypes.NARRAY, "mgarch residuals");

    public static final JSCTypeDef MGARCH_EIGEN = new JSCTypeDef(
            "MGARCH_EIGEN", JSCTypes.NARRAY,
            "mgarch eigenvalues to check checking covariance stationarity");

    private JSCNArray data = null;

    private JSCSArray names = null;

    private boolean isExactT = false;

    private JSCNArray est;

    private JSCNArray tNorm;

    private JSCNArray tExact;

    private JSCNArray logLik;

    private JSCNArray eigen;

    /**
     * <code>MGARCHCall</code> constructor takes the arguments for the
     * procedure call.
     */
    public MGARCHCall(JSCNArray data, JSCSArray names, boolean isExactT) {

        super();

        setName("MGARCH(1,1) Estimation");

        this.data = data;
        this.isExactT = isExactT;
        this.names = names;

    }

    /**
     * Writes the text output to a string that can be referenced via
     * <code>getOutput</code>.
     */
    protected void finalCode() {

        StringBuffer buffer = output;

        buffer.append("Multivariate GARCH(1,1) estimation for \""
                + UData.stringForArray(names) + "\"\n");

        double[][] g_arch = est.doubleArray();
        double[][] tvalues_norm = tNorm.doubleArray();
        double[][] tvalues_exact = (!tExact.isEmpty()) ? tExact.doubleArray()
                : null;
        double[][] eigvals = eigen.doubleArray();
        if (tvalues_exact != null)
            buffer
                    .append("\nlegend: estimation|(tvalues normal)|(tvalues exact)\n");
        else
            buffer.append("\nlegend: estimation|(tvalues normal)\n");

        /**
         * Gamma_0
         */
        int K = g_arch[0].length;
        buffer.append("\nGamma_0\n");
        for (int i = 0; i < K; i++) {
            // Estimators.
            for (int j = 0; j < g_arch[0].length; j++)
                buffer.append(FArg.sprintf("%- 14.5e", new FArg(g_arch[i][j])));
            // Normal T-Values.
            buffer.append("\n");
            for (int j = 0; j < tvalues_norm[0].length; j++)
                buffer.append(FArg.sprintf(" (%- 10.5f) ", new FArg(
                        tvalues_norm[i][j])));
            // Exact T-Values if available.
            if (tvalues_exact != null) {
                buffer.append("\n");
                for (int j = 0; j < g_arch[0].length; j++)
                    buffer.append(FArg.sprintf(" (%- 10.5f) ", new FArg(
                            tvalues_exact[i][j])));

            }
            buffer.append("\n");
        }
        buffer.append("\nGamma_1\n");
        for (int i = K; i < 2 * K; i++) {
            // Estimators.
            for (int j = 0; j < g_arch[0].length; j++)
                buffer.append(FArg.sprintf("%- 14.5e", new FArg(g_arch[i][j])));
            // Normal T-Values.
            buffer.append("\n");
            for (int j = 0; j < tvalues_norm[0].length; j++)
                buffer.append(FArg.sprintf(" (%- 10.5f) ", new FArg(
                        tvalues_norm[i][j])));
            // Exact T-Values if available.
            if (tvalues_exact != null) {
                buffer.append("\n");
                for (int j = 0; j < g_arch[0].length; j++)
                    buffer.append(FArg.sprintf(" (%- 10.5f) ", new FArg(
                            tvalues_exact[i][j])));

            }
            buffer.append("\n");
        }
        buffer.append("\nBeta_1\n");
        for (int i = 2 * K; i < 3 * K; i++) {
            // Estimators.
            for (int j = 0; j < g_arch[0].length; j++)
                buffer.append(FArg.sprintf("%- 14.5e", new FArg(g_arch[i][j])));
            // Normal T-Values.
            buffer.append("\n");
            for (int j = 0; j < tvalues_norm[0].length; j++)
                buffer.append(FArg.sprintf(" (%- 10.5f) ", new FArg(
                        tvalues_norm[i][j])));
            // Exact T-Values if available.
            if (tvalues_exact != null) {
                buffer.append("\n");
                for (int j = 0; j < g_arch[0].length; j++)
                    buffer.append(FArg.sprintf(" (%- 10.5f) ", new FArg(
                            tvalues_exact[i][j])));

            }
            buffer.append("\n");
        }

        /**
         * Eigenvalues.
         */
        buffer.append("\nmodulus of eigenvalues\n");
        for (int i = 0; i < eigvals.length; i++) {
            buffer.append(FArg.sprintf("%- 14.5e\n", new FArg(eigvals[i][0])));

        }

        // log likelihood
        buffer.append(FArg.sprintf("\nlog likelihood = %- 14.5e", new FArg(
                logLik.doubleAt(0, 0))));

        buffer.append("\n");
    }

    /**
     * @see RPCall
     */
    protected void runCode() {

        if (getSymbolTable() != null) {
            getSymbolTable().get(MGARCH_EST).clear();
            getSymbolTable().get(MGARCH_TNORM).clear();
            getSymbolTable().get(MGARCH_TEXACT).clear();
            getSymbolTable().get(MGARCH_LOGLIK).clear();
            getSymbolTable().get(MGARCH_IVAR).clear();
            getSymbolTable().get(MGARCH_RES).clear();
            getSymbolTable().get(MGARCH_EIGEN).clear();
        }

        if (data.rows() < 10)
            throw new RuntimeException("Not enough observations.");

        engine().load("pgraph", GaussLoadTypes.LIB);
        engine().load("tools", GaussLoadTypes.LIB);
        engine().load("plot", GaussLoadTypes.LIB);
        engine().load("arch", GaussLoadTypes.LIB);

        JSCNArray[] retArgs = new JSCNArray[] {
                (JSCNArray) MGARCH_EST.getInstance(),
                (JSCNArray) MGARCH_TNORM.getInstance(),
                (JSCNArray) MGARCH_TEXACT.getInstance(),
                (JSCNArray) MGARCH_LOGLIK.getInstance(),
                (JSCNArray) MGARCH_IVAR.getInstance(),
                (JSCNArray) MGARCH_RES.getInstance(),
                (JSCNArray) MGARCH_EIGEN.getInstance() };

        engine().call("mgarch_wrapper_arch",
                new JSCData[] { data, new JSCInt("isExactT", isExactT) },
                retArgs);

        if (getSymbolTable() != null)
            getSymbolTable().set(retArgs);

        est = retArgs[0];
        tNorm = retArgs[1];
        tExact = retArgs[2];
        logLik = retArgs[3];
        eigen = retArgs[6];
    }
}