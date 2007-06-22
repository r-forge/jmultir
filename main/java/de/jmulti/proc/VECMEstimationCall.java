/*
 This file is part of the econometric software package JMulTi.
 Copyright (C) 2000-2005  Alexander Bankwitz, Markus Kraetzig

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

import com.jstatcom.engine.Engine;
import com.jstatcom.engine.gauss.GaussLoadTypes;
import com.jstatcom.io.FileSupport;
import com.jstatcom.model.JSCData;
import com.jstatcom.model.JSCInt;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.JSCSArray;
import com.jstatcom.model.JSCString;
import com.jstatcom.model.JSCTypeDef;
import com.jstatcom.model.SymbolTable;
import com.jstatcom.ts.TSDateRange;
import com.jstatcom.util.FArg;
import com.jstatcom.util.UData;
import com.jstatcom.util.UMatrix;
import com.jstatcom.util.UString;

import de.jmulti.vecm.VECMConstants;

/**
 * This GAUSS command object carries out a VECM estimation according to the
 * parametrization. The results of this call are stored to the symbol table if
 * one has been set. The variable names defined in <code>VECMConstants</code>
 * are used for the arguments of the call as well as for storing the results.
 * The call estimates either by Johanson, S2S (Lütkepohl) or Two Stage.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
public final class VECMEstimationCall extends RPCall {

    // Input variables.

    private JSCSArray Ny = null;

    private JSCSArray Nx = null;

    private JSCSArray Nd = null;

    private int py = 0;

    private int px = 0;

    private int r = 0;

    private int estimationStrategy = VECMConstants.JOHANSEN;

    private String fileNameVAR = "";

    private String fileNameVEC = "";

    /**
     * Contains all variables that should be read back after the estimation is
     * done.
     */
    public static final JSCTypeDef[] outputs = new JSCTypeDef[] {
            VECMConstants.G0_Def, VECMConstants.G_Def, VECMConstants.alpha_Def,
            VECMConstants.beta_Def, VECMConstants.beta_d_Def,
            VECMConstants.B_Def, VECMConstants.C_vec_Def, VECMConstants.A0_Def,
            VECMConstants.A_Def, VECMConstants.C_var_Def,
            VECMConstants.SD_G0_Def, VECMConstants.SD_G_Def,
            VECMConstants.SD_alpha_Def, VECMConstants.SD_beta_Def,
            VECMConstants.SD_beta_d_Def, VECMConstants.SD_B_Def,
            VECMConstants.SD_C_vec_Def, VECMConstants.SD_A0_Def,
            VECMConstants.SD_A_Def, VECMConstants.SD_C_var_Def,
            VECMConstants.TV_G0_Def, VECMConstants.TV_G_Def,
            VECMConstants.TV_alpha_Def, VECMConstants.TV_beta_Def,
            VECMConstants.TV_beta_d_Def, VECMConstants.TV_B_Def,
            VECMConstants.TV_C_vec_Def, VECMConstants.TV_A0_Def,
            VECMConstants.TV_A_Def, VECMConstants.TV_C_var_Def,
            VECMConstants.eigv_modulus_Def, VECMConstants.u_hat_Def,
            VECMConstants.cv_u_hat_Def, VECMConstants.estimationMethod_Def,
            VECMConstants.lrtest_Def };

    /**
     * VECMEstimationCall constructor comment.
     * 
     * @param estimationStrategy
     *            Johanson, S2S, or Two Stage as defined in
     *            <code>VECMConstants</code>
     * @throws IllegalArgumentException
     *             if <code>estimationStrategy</code> is invalid
     */
    public VECMEstimationCall(SymbolTable symbolTable, String fileNameVEC,
            String fileNameVAR, int estimationStrategy) {
        super();

        if (estimationStrategy != VECMConstants.JOHANSEN
                && estimationStrategy != VECMConstants.S2S
                && estimationStrategy != VECMConstants.TWO_STAGE)
            throw new IllegalArgumentException(estimationStrategy
                    + " is not of type Johanson, S2S or Tow Stage.");

        this.estimationStrategy = estimationStrategy;

        setSymbolTable(symbolTable);

        setName("VECM Estimation");

        Ny = symbolTable.get(VECMConstants.Ny_Def).getJSCSArray();
        Nx = symbolTable.get(VECMConstants.Nx_Def).getJSCSArray();
        Nd = symbolTable.get(VECMConstants.Nd_Def).getJSCSArray();

        py = symbolTable.get(VECMConstants.py_Def).getJSCInt().intVal();
        px = symbolTable.get(VECMConstants.px_Def).getJSCInt().intVal();
        r = symbolTable.get(VECMConstants.cointRank_Def).getJSCInt().intVal();

        this.fileNameVAR = fileNameVAR;
        this.fileNameVEC = fileNameVEC;

    }

    /**
     * Sets the Gauss Buffer to <code>bufferToSet</code>.
     * 
     * @param engine
     *            the engine to use
     * @param sTable
     *            table to get input data from <code>VECMConstants</code>
     * @param bufferToSet
     *            initialized data object that is set to GAUSS buffer contents
     */
    public static void setVecBuffer(Engine engine, SymbolTable sTable,
            JSCNArray bufferToSet, int strategy) {

        int py = sTable.get(VECMConstants.py_Def).getJSCInt().intVal();
        int px = sTable.get(VECMConstants.px_Def).getJSCInt().intVal();

        int r = sTable.get(VECMConstants.cointRank_Def).getJSCInt().intVal();
        engine.call(getVECMBufferProc(strategy), getVECMBufferArgs(sTable, py,
                px, r, strategy), new JSCData[] { bufferToSet });

    }

    /**
     * Gets the name of the GAUSS procedure for the given estimation strategy.
     * The procedure should be called with <code>getVECMBufferArgs</code> as
     * argument list.
     * 
     * @param strategy
     *            the estimation strategy to retrieve the necessary data for,
     *            defined in VECMConstants
     * @return procedure call
     */
    private static String getVECMBufferProc(int strategy) {
        if (strategy == VECMConstants.JOHANSEN)
            return "vec_CreateJohansenVECM";

        // proc(1) =
        // vec_CreateS2SVECM(y,x,d,py,px,Ny,Nx,Nd,r,mx_cd2cr,hmat,freeparamindex,h);
        if (strategy == VECMConstants.S2S)
            return "vec_CreateS2SVECM";

        // proc(1) = vec_CreateTwoStageVECM(y,x,d,py,px,Ny,Nx,Nd,r,mx_cd2cr,
        // firstStage_equaIdx,firstStageByVECMEquation,firstStageByJohansen,firstStage_crJoh,
        // beta_x,beta_d_x,sndstage_method,S_G0,S_G,S_alpha,S_beta,S_beta_d,S_B,S_C_VEC);
        if (strategy == VECMConstants.TWO_STAGE)
            return "vec_CreateTwoStageVECM";

        throw new IllegalArgumentException(strategy
                + " is not of type Johansen, S2S or Two Stage.");
    }

    /**
     * Helper function that creates the GAUSS command to create a VECM Johanson
     * estimation data buffer as defined in the <code>var / vec</code>
     * libraries of <i>JMulTi </i>. It uses <code>VECMConstants</code>.
     * <p>
     * <code>
     *  vec_CreateJohansenVECM(y,x,d,py,px,Ny,Nx,Nd,r,idx_cd2ci)
     * </code>.
     * 
     * @param sTable
     *            symbol table with data defined in <code>VECMConstants</code>
     * @param py
     *            endogenous VAR lags
     * @param px
     *            exogenous lags
     * @param r
     *            number of cointegration relations
     * @param strategy
     *            the estimation strategy to retrieve the necessary data for,
     *            defined in VECMConstants
     * @return the GAUSS argument array
     * @throws IllegalArgumentException
     *             <code>  if (strategy != VECMConstants.JOHANSON
     *                                          && strategy != VECMConstants.S2S
     *                                          && strategy != VECMConstants.TWO_STAGE)
     *                                  </code>
     */
    private static JSCData[] getVECMBufferArgs(SymbolTable sTable, int py,
            int px, int r, int strategy) {

        // proc(1) = vec_CreateJohansenVECM(y,x,d,py,px,Ny,Nx,Nd,r,mx_cd2cr);
        if (strategy == VECMConstants.JOHANSEN)
            return new JSCData[] { sTable.getJSCData(VECMConstants.y_Def),
                    sTable.getJSCData(VECMConstants.x_Def),
                    sTable.getJSCData(VECMConstants.d_Def),
                    new JSCInt("py", py), new JSCInt("px", px),
                    sTable.getJSCData(VECMConstants.Ny_Def),
                    sTable.getJSCData(VECMConstants.Nx_Def),
                    sTable.getJSCData(VECMConstants.Nd_Def),
                    new JSCInt("r", r),
                    sTable.getJSCData(VECMConstants.idx_cd2ci_Def), };

        // proc(1) =
        // vec_CreateS2SVECM(y,x,d,py,px,Ny,Nx,Nd,r,mx_cd2cr,hmat,freeparamindex,h);
        if (strategy == VECMConstants.S2S)
            return new JSCData[] { sTable.getJSCData(VECMConstants.y_Def),
                    sTable.getJSCData(VECMConstants.x_Def),
                    sTable.getJSCData(VECMConstants.d_Def),
                    new JSCInt("py", py), new JSCInt("px", px),
                    sTable.getJSCData(VECMConstants.Ny_Def),
                    sTable.getJSCData(VECMConstants.Nx_Def),
                    sTable.getJSCData(VECMConstants.Nd_Def),
                    new JSCInt("r", r),
                    sTable.getJSCData(VECMConstants.idx_cd2ci_Def),
                    sTable.getJSCData(VECMConstants.resHmat_Def),
                    sTable.getJSCData(VECMConstants.resFreeParamIndex_Def),
                    sTable.getJSCData(VECMConstants.resH_Def) };

        // proc(1) = vec_CreateTwoStageVECM(y,x,d,py,px,Ny,Nx,Nd,r,mx_cd2cr,
        // firstStage_equaIdx,firstStageByVECMEquation,firstStageByJohansen,firstStage_crJoh,
        // beta_x,beta_d_x,sndstage_method,S_G0,S_G,S_alpha,S_beta,S_beta_d,S_B,S_C_VEC);
        if (strategy == VECMConstants.TWO_STAGE) {
            JSCNArray beta_x = (JSCNArray) sTable
                    .getJSCData(VECMConstants.beta_x_Def);
            JSCNArray beta_d_x = (JSCNArray) sTable
                    .getJSCData(VECMConstants.beta_d_x_Def);

            if (beta_x != null && beta_d_x != null) {
                int nonzeros = beta_x.rows() * beta_x.cols()
                        - beta_x.zeroCount();
                if (nonzeros < 2) { // first condition
                    beta_x = (JSCNArray) VECMConstants.beta_x_Def.getInstance();
                    beta_d_x = (JSCNArray) VECMConstants.beta_d_x_Def
                            .getInstance();
                } else // 2nd condition
                if (!beta_d_x.isEmpty()
                        && beta_d_x.zeroCount() == beta_d_x.rows()
                                * beta_d_x.cols()) {
                    beta_x = (JSCNArray) VECMConstants.beta_x_Def.getInstance();
                    beta_d_x = (JSCNArray) VECMConstants.beta_d_x_Def
                            .getInstance();
                }
            } else {
                // both must be non null
                beta_x = (JSCNArray) VECMConstants.beta_x_Def.getInstance();
                beta_d_x = (JSCNArray) VECMConstants.beta_d_x_Def.getInstance();
            }
            JSCInt firstbySE = (JSCInt) sTable
                    .getJSCData(VECMConstants.firstStage_bySingleEquation_Def);
            JSCInt firstbyJoh = (JSCInt) sTable
                    .getJSCData(VECMConstants.firstStage_byJoh_Def);
            JSCInt firstbyS2S = (JSCInt) sTable
                    .getJSCData(VECMConstants.firstStage_byS2S_Def);
            if ((beta_x == null || beta_x.isEmpty())
                    && (beta_d_x == null || beta_d_x.isEmpty())) {
                if ((firstbySE.isEmpty() || firstbySE.intVal() == 0)
                        && (firstbyJoh.isEmpty() || firstbyJoh.intVal() == 0)
                        && (firstbyS2S.isEmpty() || firstbyS2S.intVal() == 0)) {
                    firstbyJoh.setInt(1); // set Johansen as default if
                    // nothing else was set

                }
            }

            return new JSCData[] { sTable.getJSCData(VECMConstants.y_Def),
                    sTable.getJSCData(VECMConstants.x_Def),
                    sTable.getJSCData(VECMConstants.d_Def),
                    new JSCInt("py", py), new JSCInt("px", px),
                    sTable.getJSCData(VECMConstants.Ny_Def),
                    sTable.getJSCData(VECMConstants.Nx_Def),
                    sTable.getJSCData(VECMConstants.Nd_Def),
                    new JSCInt("r", r),
                    sTable.getJSCData(VECMConstants.idx_cd2ci_Def),
                    sTable.getJSCData(VECMConstants.firstStage_equaIdx_Def),
                    firstbySE, firstbyJoh, firstbyS2S,
                    sTable.getJSCData(VECMConstants.firstStage_crJoh_Def),
                    beta_x, beta_d_x,
                    sTable.getJSCData(VECMConstants.secondStage_method_Def),
                    sTable.getJSCData(VECMConstants.S_G0_Def),
                    sTable.getJSCData(VECMConstants.S_G_Def),
                    sTable.getJSCData(VECMConstants.S_alpha_Def),
                    sTable.getJSCData(VECMConstants.S_beta_Def),
                    sTable.getJSCData(VECMConstants.S_beta_d_Def),
                    sTable.getJSCData(VECMConstants.S_B_Def),
                    sTable.getJSCData(VECMConstants.S_C_VEC_Def),
                    sTable.getJSCData(VECMConstants.resHmat_Def),
                    sTable.getJSCData(VECMConstants.resFreeParamIndex_Def),
                    sTable.getJSCData(VECMConstants.resH_Def) };
        }

        throw new IllegalArgumentException(strategy
                + " is not of type Johansen, S2S or Two Stage.");
    }

    protected void finalCode() {

        SymbolTable gst = getSymbolTable();

        String end = UData.stringForArray(Ny);
        String ex = UData.stringForArray(Nx);
        String det = UData.stringForArray(Nd);

        StringBuffer buffer = output;
        buffer.append("VEC REPRESENTATION\n");

        buffer.append(FArg.sprintf("%-25s %s \n", new FArg(
                "endogenous variables:").add(end)));
        buffer.append(FArg.sprintf("%-25s %s \n", new FArg(
                "exogenous variables:").add(ex)));
        buffer.append(FArg.sprintf("%-25s %s \n", new FArg(
                "deterministic variables:").add(det)));

        buffer.append(FArg.sprintf("%-25s %i \n", new FArg(
                "endogenous lags (diffs):").add(py - 1)));
        buffer.append(FArg.sprintf("%-25s %i \n", new FArg("exogenous lags:")
                .add(px)));
        TSDateRange range = gst.get(VECMConstants.T1_Def).getJSCDRange()
                .getTSDateRange();
        buffer.append(range.format("sample range:", 26) + "\n");
        buffer.append(FArg.sprintf("%-25s %s \n", "estimation procedure:", gst
                .get(VECMConstants.estimationMethod_Def).getJSCString()
                .string()
                + ""));

        buffer.append(FileSupport.getInstance().readTextFile(fileNameVEC)
                + "\n\n");
        new File(fileNameVEC).delete();

        buffer.append("VAR REPRESENTATION\n");

        buffer
                .append("\nmodulus of the eigenvalues of the reverse characteristic polynomial:\n");
        buffer.append("|z| = ( ");
        JSCNArray eigv_mod = gst.get(VECMConstants.eigv_modulus_Def)
                .getJSCNArray();
        for (int i = 0; i < eigv_mod.rows(); i++) {
            buffer.append(FArg.sprintf("%-10.4f ", new FArg(eigv_mod.doubleAt(
                    i, 0))));

        }
        buffer.append(")\n");

        buffer.append("\n");
        buffer.append(FileSupport.getInstance().readTextFile(fileNameVAR));
        new File(fileNameVAR).delete();

    }

    /**
     * @see RPCall
     */
    protected void runCode() {

        SymbolTable sTable = getSymbolTable();

        if (sTable != null)
            for (int i = 0; i < outputs.length; i++)
                sTable.get(outputs[i]).clear();

        engine().load("var", GaussLoadTypes.LIB);
        engine().load("vec", GaussLoadTypes.LIB);

        JSCNArray vecmBuffer = new JSCNArray("vecmBuffer");
        JSCData[] estArgs = getVECMBufferArgs(sTable, py, px, r,
                estimationStrategy);
        engine().call(getVECMBufferProc(estimationStrategy), estArgs,
                new JSCData[] { vecmBuffer });

        engine().call("var_EstimateModel", new JSCData[] { vecmBuffer },
                new JSCData[] { vecmBuffer });

        engine().call("var_ComputeModelStatistics",
                new JSCData[] { vecmBuffer }, new JSCData[] { vecmBuffer });

        JSCData[] retArgsEst = new JSCData[] {
                VECMConstants.G0_Def.getInstance(),
                VECMConstants.G_Def.getInstance(),
                VECMConstants.alpha_Def.getInstance(),
                VECMConstants.beta_Def.getInstance(),
                VECMConstants.beta_d_Def.getInstance(),
                VECMConstants.B_Def.getInstance(),
                VECMConstants.C_vec_Def.getInstance(),
                new JSCNArray("emptyArg"), VECMConstants.A0_Def.getInstance(),
                VECMConstants.A_Def.getInstance(),
                VECMConstants.C_var_Def.getInstance(),
                new JSCNArray("emptyArg") };

        engine().call("vec_GetCoefficients", new JSCData[] { vecmBuffer },
                retArgsEst);

        if (sTable != null)
            sTable.set(retArgsEst);

        JSCData[] retArgsStdErr = new JSCData[] {
                VECMConstants.SD_G0_Def.getInstance(),
                VECMConstants.SD_G_Def.getInstance(),
                VECMConstants.SD_alpha_Def.getInstance(),
                VECMConstants.SD_beta_Def.getInstance(),
                VECMConstants.SD_beta_d_Def.getInstance(),
                VECMConstants.SD_B_Def.getInstance(),
                VECMConstants.SD_C_vec_Def.getInstance(),
                new JSCNArray("emptyArg"),
                VECMConstants.SD_A0_Def.getInstance(),
                VECMConstants.SD_A_Def.getInstance(),
                VECMConstants.SD_C_var_Def.getInstance(),
                new JSCNArray("emptyArg") };

        engine().call("vec_GetStandardErrors", new JSCData[] { vecmBuffer },
                retArgsStdErr);

        if (sTable != null)
            sTable.set(retArgsStdErr);

        JSCData[] retArgsTVal = new JSCData[] {
                VECMConstants.TV_G0_Def.getInstance(),
                VECMConstants.TV_G_Def.getInstance(),
                VECMConstants.TV_alpha_Def.getInstance(),
                VECMConstants.TV_beta_Def.getInstance(),
                VECMConstants.TV_beta_d_Def.getInstance(),
                VECMConstants.TV_B_Def.getInstance(),
                VECMConstants.TV_C_vec_Def.getInstance(),
                new JSCNArray("emptyArg"),
                VECMConstants.TV_A0_Def.getInstance(),
                VECMConstants.TV_A_Def.getInstance(),
                VECMConstants.TV_C_var_Def.getInstance(),
                new JSCNArray("emptyArg") };

        engine().call("vec_GetT_Values", new JSCData[] { vecmBuffer },
                retArgsTVal);

        if (sTable != null)
            sTable.set(retArgsTVal);

        JSCString estMeth = (JSCString) VECMConstants.estimationMethod_Def
                .getInstance();
        engine().call(
                "vml_vread",
                new JSCData[] { vecmBuffer,
                        new JSCString("em_exe_key", "em_exe") },
                new JSCData[] { estMeth });
        String[] s = estMeth.string().split("[^a-zA-Z0-9_,.=\\s]");
        estMeth.setVal(s[0].trim());
        if (sTable != null)
            sTable.set(estMeth);

        JSCNArray cv_u_hat = (JSCNArray) VECMConstants.cv_u_hat_Def
                .getInstance();
        engine()
                .call(
                        "vml_vread",
                        new JSCData[] { vecmBuffer,
                                new JSCString("cvRes_key", "cvRes") },
                        new JSCData[] { cv_u_hat });
        if (sTable != null)
            sTable.set(cv_u_hat);

        JSCNArray u_hat = (JSCNArray) VECMConstants.u_hat_Def.getInstance();
        engine().call("vml_vread",
                new JSCData[] { vecmBuffer, new JSCString("u_key", "u") },
                new JSCData[] { u_hat });
        if (sTable != null)
            sTable.set(u_hat);

        JSCNArray eigv_mod = (JSCNArray) VECMConstants.eigv_modulus_Def
                .getInstance();
        engine().call("var_eigenvalues",
                new JSCData[] { sTable.getJSCData(VECMConstants.A_Def) },
                new JSCData[] { eigv_mod });
        if (sTable != null)
            sTable.set(eigv_mod);

        String varFile = UString.replaceAllSubStrings(fileNameVAR.toString(),
                "\\", "/");
        String vecFile = UString.replaceAllSubStrings(fileNameVEC.toString(),
                "\\", "/");

        // file output
        engine().call("var__ShowLegend",
                new JSCData[] { new JSCString("vecFile", vecFile) }, null);
        engine()
                .call(
                        "var__ShowVEC_Coefficients",
                        new JSCData[] { vecmBuffer,
                                new JSCString("vecFile", vecFile) }, null);

        engine()
                .call(
                        "var__ShowVAR_Coefficients",
                        new JSCData[] { vecmBuffer,
                                new JSCString("varFile", varFile) }, null);

        // do LR test if feasible
        if (estimationStrategy == VECMConstants.TWO_STAGE
                && sTable.getJSCData(VECMConstants.S_G0_Def).isEmpty()) {
            int restrictions = 0;
            JSCNArray S_G = (JSCNArray) sTable
                    .getJSCData(VECMConstants.S_G_Def);
            JSCNArray S_alpha = (JSCNArray) sTable
                    .getJSCData(VECMConstants.S_alpha_Def);
            JSCNArray S_B = (JSCNArray) sTable
                    .getJSCData(VECMConstants.S_B_Def);
            JSCNArray S_C_VEC = (JSCNArray) sTable
                    .getJSCData(VECMConstants.S_C_VEC_Def);

            restrictions += S_G.zeroCount() + S_alpha.zeroCount()
                    + S_B.zeroCount() + S_C_VEC.zeroCount();

            // a restricted model was indeed estimated
            if (restrictions > 0) {
                // replace zero restrictions in original arguments with ones
                estArgs[19] = new JSCNArray("S_G", UMatrix.ones(S_G.rows(), S_G
                        .cols()));
                estArgs[20] = new JSCNArray("S_alpha", UMatrix.ones(S_alpha
                        .rows(), S_alpha.cols()));
                estArgs[23] = new JSCNArray("S_B", UMatrix.ones(S_B.rows(), S_B
                        .cols()));
                estArgs[24] = new JSCNArray("S_C_VEC", UMatrix.ones(S_C_VEC
                        .rows(), S_C_VEC.cols()));

                engine().call(getVECMBufferProc(estimationStrategy), estArgs,
                        new JSCData[] { vecmBuffer });

                JSCNArray lr = (JSCNArray) VECMConstants.lrtest_Def
                        .getInstance();
                engine().call(
                        "vec_computeLRStat",
                        new JSCData[] { vecmBuffer, cv_u_hat,
                                new JSCInt("resnum", restrictions) },
                        new JSCData[] { lr });
                if (sTable != null)
                    sTable.set(lr);

            }

        }

    }
}