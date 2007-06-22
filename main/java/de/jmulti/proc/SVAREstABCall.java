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

import java.io.File;

import com.jstatcom.engine.gauss.GaussLoadTypes;
import com.jstatcom.io.FileSupport;
import com.jstatcom.model.JSCData;
import com.jstatcom.model.JSCInt;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.JSCNumber;
import com.jstatcom.model.JSCString;
import com.jstatcom.model.SymbolTable;
import com.jstatcom.util.UString;

import de.jmulti.var.SVARConstants;

/**
 * This GAUSS command object estimates an SVAR AB model according to the
 * parametrization. The result of this call is stored to the symbol table with
 * the names defined in <code>SVARConstants</code> if one has been set.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
public final class SVAREstABCall extends RPCall {

    private JSCNArray mSigmaU = null;

    private int T = 0;

    private JSCNArray gamma_mat = null;

    private JSCNArray r_mat = null;

    private double eps1_tol = 10E-6;

    private double eps2_tol = 10E-10;

    private double fixStart = 0.1;

    private int maxIterations = 500;

    private int startValueMethod = 1;

    private boolean useDecomp = true;

    private int maxRetries = 10;

    private String fileName = "result.txt";

    /**
     * <code>SVAREstAB</code> constructor takes the arguments for the
     * procedure call.
     */
    public SVAREstABCall(JSCNArray mSigmaU, int T, JSCNArray gamma_mat,
            JSCNArray r_mat, double eps1_tol, double eps2_tol, double fixStart,
            int maxIterations, int startValueMethod, boolean useDecomp,
            int maxRetries, String fileName) {

        // INPUT CHECKING SHOULD BE DONE!
        setName("SVAR AB Model Estimation");

        this.mSigmaU = mSigmaU;
        this.T = T;
        this.gamma_mat = gamma_mat;
        this.r_mat = r_mat;
        this.eps1_tol = eps1_tol;
        this.eps2_tol = eps2_tol;
        this.fixStart = fixStart;
        this.maxIterations = maxIterations;
        this.startValueMethod = startValueMethod;
        this.useDecomp = useDecomp;
        this.maxRetries = maxRetries;
        this.fileName = fileName;

    }

    /**
     * Writes the text output to a string that can be referenced via
     * <code>getOutput</code>.
     */
    protected void finalCode() {
        output.append(FileSupport.getInstance().readTextFile(fileName));
        new File(fileName).delete();
    }

    /**
     * @see RPCall
     */
    protected void runCode() {

        // {minvAB,mA,mB,mS,vs,vg_bs,model}

        SymbolTable sTable = getSymbolTable();

        if (sTable != null) {
            sTable.get(SVARConstants.svar_A0_Def).clear();
            sTable.get(SVARConstants.aMatAB_Def).clear();
            sTable.get(SVARConstants.bMatAB_Def).clear();
            sTable.get(SVARConstants.resMatrixAB_Def).clear();
            sTable.get(SVARConstants.normConst_Def).clear();
            sTable.get(SVARConstants.startValVector_Def).clear();
            sTable.get(SVARConstants.svarABModelType_Def).clear();
        }

        engine().load("var", GaussLoadTypes.LIB);

        JSCData svar_A0_Def = SVARConstants.svar_A0_Def.getInstance();
        JSCData aMatAB_Def = SVARConstants.aMatAB_Def.getInstance();
        JSCData bMatAB_Def = SVARConstants.bMatAB_Def.getInstance();
        JSCData resMatrixAB_Def = SVARConstants.resMatrixAB_Def.getInstance();
        JSCData normConst_Def = SVARConstants.normConst_Def.getInstance();
        JSCData startValVector_Def = SVARConstants.startValVector_Def
                .getInstance();
        JSCData svarABModelType_Def = SVARConstants.svarABModelType_Def
                .getInstance();
        JSCData svar_A_std_Def = SVARConstants.svar_A_STD_Def.getInstance();
        JSCData svar_B_std_Def = SVARConstants.svar_B_STD_Def.getInstance();

        JSCData[] retArgs = new JSCData[] { svar_A0_Def, aMatAB_Def,
                bMatAB_Def, resMatrixAB_Def, normConst_Def, startValVector_Def,
                svarABModelType_Def, svar_A_std_Def, svar_B_std_Def };

        engine().call(
                "svar_wrapper_var",
                new JSCData[] {
                        mSigmaU,
                        new JSCInt("T", T),
                        gamma_mat,
                        r_mat,
                        new JSCNumber("relParamCh", eps1_tol),
                        new JSCNumber("relLikCh", eps2_tol),
                        new JSCInt("maxIterations", maxIterations),
                        new JSCInt("startValueMethod", startValueMethod),
                        new JSCNumber("fixStart", fixStart),
                        new JSCNumber("vStartUser", 0),
                        new JSCInt("useDecomp", useDecomp),
                        new JSCInt("maxRetries", maxRetries),
                        new JSCString("outFileName", UString
                                .replaceAllSubStrings(fileName, "\\", "/")) },
                retArgs);

        if (sTable != null) {
            sTable.get(SVARConstants.lastModel_Def).setJSCData(
                    new JSCString("toSet", SVARConstants.AB_Model));

            sTable.set(retArgs);
            sTable.get(SVARConstants.relParamCh_Def).setJSCData(
                    new JSCNumber("toSet", eps1_tol));
            sTable.get(SVARConstants.relLikCh_Def).setJSCData(
                    new JSCNumber("toSet", eps2_tol));
            sTable.get(SVARConstants.maxIter_Def).setJSCData(
                    new JSCInt("toSet", maxIterations));

        }

    }
}