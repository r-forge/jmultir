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
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.SymbolTable;
import com.jstatcom.util.FArg;

import de.jmulti.vecm.VECMConstants;


/**
 * Makes the call to the Wald test for restrictions on the cointegrating space
 * for a VEC model (beta). It depends on the class <code>VECMConstants</code>
 * as well as on <code>VECMEstimationCall</code>. The estimation routine that
 * is used is the Johanson procedure. Exogenous variables as well as subset
 * constraints are ignored.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
public final class BetaResWaldCall extends RPCall {
    private JSCNArray resMat = null;

    private JSCNArray r = null;

    private JSCNArray waldResult = null;

    /**
     * BetaResWaldCall constructor comment.
     */
    public BetaResWaldCall(SymbolTable gst, JSCNArray resMat, JSCNArray r) {
        super();

        setSymbolTable(gst);
        setName("Wald Test for Beta Restrictions");

        this.resMat = resMat;
        this.r = r;
    }

    protected void finalCode() {

        StringBuffer buffer = output;
        buffer
                .append("WALD TEST FOR BETA RESTRICTIONS (using Johansen ML estimator)\n");
        buffer.append("R*vec(beta'(K-r))=r; displaying R and r:\n");
        for (int i = 0; i < resMat.rows(); i++) {
            for (int j = 0; j < resMat.cols(); j++) {

                buffer.append(FArg.sprintf("%- 8.4f", new FArg(resMat.doubleAt(
                        i, j))));

            }
            buffer.append(FArg.sprintf("         %- 8.4f\n", new FArg(r
                    .doubleAt(i, 0))));

        }
        buffer.append("\n");
        buffer.append(FArg.sprintf("%-24s %- 8.4f \n", new FArg(
                "test statistic:").add(waldResult.doubleAt(0, 0))));
        buffer.append(FArg.sprintf("%-24s %- 8.4f \n", new FArg(" p-value:")
                .add(waldResult.doubleAt(1, 0))));
        buffer.append(FArg.sprintf("%-24s %- 8.4f \n", new FArg(
                " degrees of freedom:").add(waldResult.doubleAt(2, 0))));
        buffer.append("\n");
    }

    /**
     * @see ProcCall
     */
    protected void runCode() {

        SymbolTable sTable = getSymbolTable();

        engine().load("var", GaussLoadTypes.LIB);
        engine().load("vec", GaussLoadTypes.LIB);

        JSCNArray vecmBuffer = new JSCNArray("vecmBuffer");

        // gets the buffer name and the buffer data from static
        // VECEstCall method

        VECMEstimationCall.setVecBuffer(engine(), sTable, vecmBuffer,
                VECMConstants.JOHANSEN);

        waldResult = new JSCNArray("wald_result");

        engine().call("waldtest_cointspace_vec",
                new JSCData[] { vecmBuffer, resMat, r },
                new JSCData[] { waldResult });

    }
}