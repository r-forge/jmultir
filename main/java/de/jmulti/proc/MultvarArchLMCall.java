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
 * This GAUSS command object computes the multivariate test for ARCH and stores
 * the result in a symbol table if one is set.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
public final class MultvarArchLMCall extends RPCall {
    public static final JSCTypeDef MARCH_RESULT = new JSCTypeDef(
            "MARCH_RESULT", JSCTypes.NARRAY,
            "result of multivariate ARCH-LM test: stat|p-val chi2|df");

    private JSCNArray data = null;

    private int archLags = 0;

    private JSCNArray archRes = null;

    /**
     * <code>MultvarArchLMCall</code> constructor takes the arguments for the
     * procedure call.
     */
    public MultvarArchLMCall(JSCNArray data, int archLags) {

        super();

        setName("Multivariate ARCH-LM Test");

        this.data = data;
        this.archLags = archLags;
    }

    /**
     * Writes the text output to a string that can be referenced via
     * <code>getOutput</code>.
     */
    protected void finalCode() {

        StringBuffer buffer = output;

        double multArch[][] = archRes.doubleArray();
        buffer.append("MULTIVARIATE ARCH-LM TEST with " + archLags + " lags\n");
        buffer.append("\n");
        if (multArch[0][0] < 0) {
            buffer
                    .append("Insufficient data for test, try reducing the number of lags.\n");
        } else {
            buffer.append(FArg.sprintf("%-24s %- 8.4f \n", new FArg(
                    "VARCHLM test statistic:").add(multArch[0][0])));
            buffer.append(FArg.sprintf("%-24s %- 8.4f \n", new FArg(
                    " p-value(chi^2):").add(multArch[1][0])));
            buffer.append(FArg.sprintf("%-24s %- 8.4f \n", new FArg(
                    " degrees of freedom:").add(multArch[2][0])));
        }
        buffer.append("\n");
    }

    /**
     * @see RPCall
     */
    protected void runCode() {
        if (getSymbolTable() != null)
            getSymbolTable().get(MARCH_RESULT).clear();

        if (data.rows() < 2)
            throw new RuntimeException("Not enough observations");

        engine().load("diag", GaussLoadTypes.LIB);
        engine().load("fil", GaussLoadTypes.LIB);

        archRes = (JSCNArray) MARCH_RESULT.getInstance();

        engine().call("archlm_resanal",
                new JSCData[] { data, new JSCInt("archLags", archLags) },
                new JSCData[] { archRes });

        if (getSymbolTable() != null)
            getSymbolTable().set(archRes);

    }
}