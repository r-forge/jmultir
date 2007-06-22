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
import com.jstatcom.model.JSCTypeDef;
import com.jstatcom.model.JSCTypes;
import com.jstatcom.util.FArg;

/**
 * This GAUSS command object computes the univariate Portmanteau test and stores
 * the result in a symbol table if one is set.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public final class UnivarPortmanCall extends RPCall {
    public static final JSCTypeDef PORT_RESULT = new JSCTypeDef("PORT_RESULT",
            JSCTypes.NARRAY,
            "Results of Portmanteau test: bp~prob_chi~lb~prob_chilb");

    private int lags = 0;

    private int correction = 0;

    private JSCNArray data = null;

    private JSCNArray portRes = null;

    /**
     * <code>UnivarPortmanCall</code> constructor takes the arguments for the
     * procedure call.
     */
    public UnivarPortmanCall(JSCNArray data, int lags, int correction) {

        super();

        setName("Univariate Portmanteau Test");

        this.lags = lags;
        this.data = data;
        this.correction = correction;

    }

    /**
     * Writes the text output to a string that can be referenced via
     * <code>getOutput</code>.
     */
    protected void finalCode() {

        StringBuffer buffer = output;

        buffer.append("PORTMANTEAU TEST with " + lags + " lags\n\n");
        buffer.append(FArg.sprintf("%-25s %- 13.4f\n", new FArg("Portmanteau:")
                .add(portRes.doubleAt(0, 0))));
        buffer.append(FArg.sprintf("%-25s %- 13.4f\n", new FArg(
                " p-Value (Chi^2):").add(portRes.doubleAt(0, 1))));
        buffer.append(FArg.sprintf("%-25s %- 13.4f\n", new FArg("Ljung & Box:")
                .add(portRes.doubleAt(0, 2))));
        buffer.append(FArg.sprintf("%-25s %- 13.4f\n", new FArg(
                " p-Value (Chi^2):").add(portRes.doubleAt(0, 3))));

        buffer.append("\n");
    }

    /**
     * @see PCall
     */
    protected void runCode() {

        if (getSymbolTable() != null)
            getSymbolTable().get(PORT_RESULT).clear();

        if (data.rows() < 2 * lags)
            throw new RuntimeException("Not enough observations.");

        portRes = (JSCNArray) PORT_RESULT.getInstance();

        engine().load("fil", GaussLoadTypes.LIB);
        engine().load("diag", GaussLoadTypes.LIB);

        engine().call(
                "portman_diagnos",
                new JSCData[] { data, new JSCInt("lags", lags),
                        new JSCInt("correction", correction) },
                new JSCData[] { portRes });

        if (getSymbolTable() != null)
            getSymbolTable().set(portRes);

    }
}