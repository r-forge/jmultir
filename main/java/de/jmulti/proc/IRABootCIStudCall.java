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

import com.jstatcom.engine.gauss.GaussLoadTypes;
import com.jstatcom.model.JSCData;
import com.jstatcom.model.JSCInt;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.JSCNumber;
import com.jstatcom.model.JSCSArray;
import com.jstatcom.model.JSCString;
import com.jstatcom.model.JSCTypeDef;
import com.jstatcom.model.JSCTypes;
import com.jstatcom.model.SymbolTable;
import com.jstatcom.util.FArg;

import de.jmulti.tools.IRACIManager;
import de.jmulti.tools.ModelTypes;
import de.jmulti.var.SVARConstants;
import de.jmulti.vecm.SVECConstants;
import de.jmulti.vecm.VECMConstants;

/**
 * This GAUSS command object computes the bootstrapped Studentized Hall
 * confidence intervals for the impulse response analysis according to the
 * parametrization and the specified model.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public final class IRABootCIStudCall extends RPCall {
    public static JSCTypeDef FE_BOOT = new JSCTypeDef("FE_BOOT",
            JSCTypes.NARRAY,
            "all bootstrapped forecast error IAs from the last run:\n"
                    + "number of bootstrap replications x K^2* (horizon + 1)\n"
                    + "each row has all IA series one after the other");

    public static JSCTypeDef FE_BOOT_STD = new JSCTypeDef(
            "FE_BOOT_STD",
            JSCTypes.NARRAY,
            "all bootstrapped std deviations of the forecast error IAs from the last run:\n"
                    + "number of bootstrap replications x K^2 * (horizon + 1)\n"
                    + "each row has all std dev(IA) series one after the other");

    public static JSCTypeDef ORTH_BOOT = new JSCTypeDef("ORTH_BOOT",
            JSCTypes.NARRAY,
            "all bootstrapped orthogonalized IAs from the last run:\n"
                    + "number of bootstrap replications x K^2 * (horizon + 1)\n"
                    + "each row has all IA series one after the other");

    public static JSCTypeDef ORTH_BOOT_STD = new JSCTypeDef(
            "ORTH_BOOT_STD",
            JSCTypes.NARRAY,
            "all bootstrapped  std deviations of the orthogonalized IAs from the last run:\n"
                    + "number of bootstrap replications x K^2 * (horizon + 1)\n"
                    + "each row has all std dev(IA) series one after the other");

    private int periods = 20;

    private int numberOfRep = 100;

    private int stdNumberOfRep = 100;

    private double coverage = 0.950;

    private boolean isSeed = false;

    private int seed = 1;

    private boolean stdIsSeed = false;

    private int stdSeed = 1;

    private SymbolTable gst = null;

    private ModelTypes modelType = ModelTypes.VAR;

    /**
     * IRABootCIStudCall constructor comment.
     */
    public IRABootCIStudCall(SymbolTable symbolTable, int periods,
            int numberOfRep, double coverage, boolean isSeed, int seed,
            int stdNumberOfRep, boolean stdIsSeed, int stdSeed,
            ModelTypes modelType) {

        super();

        this.gst = symbolTable;

        setName("Bootstrap Studentized Hall CIs");

        this.periods = periods;
        this.numberOfRep = numberOfRep;
        this.coverage = coverage;
        this.isSeed = isSeed;
        this.seed = seed;
        this.modelType = modelType;
        this.stdNumberOfRep = stdNumberOfRep;
        this.stdIsSeed = stdIsSeed;
        this.stdSeed = stdSeed;
    }

    /**
     * Gets a descriptive string for the specified bootstrap CI. The string
     * contains all selected parameters and can therefore be used as a key to
     * identify all bootstrapped CIs for a given model.
     * 
     * @return string with the name for the CI to bootstrap
     */
    private String getCIName() {

        // Generate names for the confidence intervals.
        StringBuffer studNameCI = new StringBuffer();

        String coverageString = FArg.sprintf("%.0f", new FArg(100 * coverage));

        studNameCI.append(coverageString + "% Studentized Hall CI (B="
                + numberOfRep + " h=" + periods);

        if (isSeed)
            studNameCI.append(", seed=" + seed);

        studNameCI.append("; St.Error: B=" + stdNumberOfRep);

        if (stdIsSeed)
            studNameCI.append(", seed=" + stdSeed);

        studNameCI.append(")");

        return studNameCI.toString();
    }

    /**
     * @see ProcCall
     */
    protected void runCode() {

        engine().load("var", GaussLoadTypes.LIB);
        engine().load("vec", GaussLoadTypes.LIB);

        if (modelType == ModelTypes.VAR || modelType == ModelTypes.VECM) {
            runVARVECCode();
            return;
        }

        if (modelType == ModelTypes.SVAR) {
            String lastModel = gst
                    .get(SVARConstants.lastModel_Def).getJSCString().string();
            if (lastModel.equalsIgnoreCase(SVARConstants.AB_Model))
                runSVARABCode();
            else if (lastModel.equalsIgnoreCase(SVARConstants.BQ_Model))
                runSVARBQCode();
            return;
        }

        if (modelType == ModelTypes.SVEC) {
            runSVECCode();
            return;
        }

    }

    /**
     * @see ProcCall
     */
    private void runSVARABCode() {
        seed = isSeed ? seed : 0;
        stdSeed = stdIsSeed ? stdSeed : 0;

        JSCNArray varBuffer = new JSCNArray("varBuffer");

        VAREstimationCall.setVarBuffer(engine(), gst, varBuffer);

        // Put SVAR model information necessary for IR bootstrap in buffer.
        engine().call(
                "vml_vput",
                new JSCData[] { varBuffer,
                        gst.get(SVARConstants.resMatrixAB_Def).getJSCData(),
                        new JSCString("info_key", "resMat") },
                new JSCData[] { varBuffer });
        engine().call(
                "vml_vput",
                new JSCData[] { varBuffer,
                        gst.get(SVARConstants.startValVector_Def).getJSCData(),
                        new JSCString("info_key", "startVal") },
                new JSCData[] { varBuffer });
        engine().call(
                "vml_vput",
                new JSCData[] { varBuffer,
                        gst.get(SVARConstants.relParamCh_Def).getJSCData(),
                        new JSCString("info_key", "relParCh") },
                new JSCData[] { varBuffer });
        engine().call(
                "vml_vput",
                new JSCData[] { varBuffer,
                        gst.get(SVARConstants.relLikCh_Def).getJSCData(),
                        new JSCString("info_key", "relLikCh") },
                new JSCData[] { varBuffer });
        engine().call(
                "vml_vput",
                new JSCData[] { varBuffer,
                        gst.get(SVARConstants.maxIter_Def).getJSCData(),
                        new JSCString("info_key", "maxIter") },
                new JSCData[] { varBuffer });
        engine()
                .call(
                        "vml_vput",
                        new JSCData[] {
                                varBuffer,
                                gst.get(SVARConstants.svarABModelType_Def)
                                        .getJSCData(),
                                new JSCString("info_key", "sabtype") },
                        new JSCData[] { varBuffer });
        engine().call(
                "vml_vput",
                new JSCData[] { varBuffer,
                        gst.get(SVARConstants.normConst_Def).getJSCData(),
                        new JSCString("info_key", "normCon") },
                new JSCData[] { varBuffer });
        engine().call(
                "vml_vput",
                new JSCData[] { varBuffer,
                        gst.get(SVARConstants.lastModel_Def).getJSCData(),
                        new JSCString("info_key", "svar") },
                new JSCData[] { varBuffer });

        JSCNArray[] retArgs = getRtnArgs();

        engine().call(
                "var__IRA_CI_ST",
                new JSCData[] { varBuffer,
                        new JSCInt("numberOfRep", numberOfRep),
                        new JSCInt("stdNumberOfRep", stdNumberOfRep),
                        new JSCInt("seed", seed),
                        new JSCInt("stdSeed", stdSeed),
                        new JSCInt("periods", periods),
                        new JSCNumber("coverage", coverage) }, retArgs);

        IRACIManager manager = IRACIManager.getInstance();

        // Store in CI Manager
        // STUD_LOWER|STUD_UPPER
        JSCNArray[] gdStud = new JSCNArray[] { retArgs[4], retArgs[5] };
        manager.addCI(getCIName(), modelType, gdStud);

        setLocalResults(retArgs, true);

    }

    /**
     * @see ProcCall
     */
    private void runSVARBQCode() {
        seed = isSeed ? seed : 0;
        stdSeed = stdIsSeed ? stdSeed : 0;

        JSCNArray varBuffer = new JSCNArray("varBuffer");

        VAREstimationCall.setVarBuffer(engine(), gst, varBuffer);

        engine().call(
                "vml_vput",
                new JSCData[] { varBuffer,
                        gst.get(SVARConstants.lastModel_Def).getJSCData(),
                        new JSCString("info_key", "svar") },
                new JSCData[] { varBuffer });

        JSCNArray[] retArgs = getRtnArgs();

        engine().call(
                "var__IRA_CI_ST",
                new JSCData[] { varBuffer,
                        new JSCInt("numberOfRep", numberOfRep),
                        new JSCInt("stdNumberOfRep", stdNumberOfRep),
                        new JSCInt("seed", seed),
                        new JSCInt("stdSeed", stdSeed),
                        new JSCInt("periods", periods),
                        new JSCNumber("coverage", coverage) }, retArgs);

        IRACIManager manager = IRACIManager.getInstance();

        // Store in CI Manager
        // STUD_LOWER|STUD_UPPER
        JSCNArray[] gdStud = new JSCNArray[] { retArgs[4], retArgs[5] };
        manager.addCI(getCIName(), modelType, gdStud);

        setLocalResults(retArgs, true);

    }

    /**
     * @see ProcCall
     */
    private void runSVECCode() {
        seed = isSeed ? seed : 0;

        int estStrat = gst.get(VECMConstants.estimationStrategy_Def)
                .getJSCInt().intVal();

        JSCNArray vecBuffer = new JSCNArray("vecmBuffer");

        VECMEstimationCall.setVecBuffer(engine(), gst, vecBuffer, estStrat);

        // Set the EC estimation to fixed.
        engine().call(
                "vml_vdel",
                new JSCData[] {
                        vecBuffer,
                        new JSCSArray("toDelete", new String[] { "em", "r_est",
                                "cir", "idx_equa" }) },
                new JSCData[] { vecBuffer });

        engine().call(
                "vml_SetCointRelation",
                new JSCData[] { vecBuffer,
                        gst.get(VECMConstants.beta_Def).getJSCData(),
                        gst.get(VECMConstants.beta_d_Def).getJSCData() },
                new JSCData[] { vecBuffer });

        // Put SVEC model information necessary for IR bootstrap in buffer.

        engine().call(
                "vml_vput",
                new JSCData[] { vecBuffer,
                        gst.get(SVECConstants.resB_Def).getJSCData(),
                        new JSCString("info_key", "resB") },
                new JSCData[] { vecBuffer });
        engine().call(
                "vml_vput",
                new JSCData[] { vecBuffer,
                        gst.get(SVECConstants.resC_Def).getJSCData(),
                        new JSCString("info_key", "resC") },
                new JSCData[] { vecBuffer });
        engine().call(
                "vml_vput",
                new JSCData[] { vecBuffer,
                        gst.get(SVECConstants.startValVector_Def).getJSCData(),
                        new JSCString("info_key", "startVal") },
                new JSCData[] { vecBuffer });
        engine().call(
                "vml_vput",
                new JSCData[] { vecBuffer,
                        gst.get(SVECConstants.relParamCh_Def).getJSCData(),
                        new JSCString("info_key", "relParCh") },
                new JSCData[] { vecBuffer });
        engine().call(
                "vml_vput",
                new JSCData[] { vecBuffer,
                        gst.get(SVECConstants.relLikCh_Def).getJSCData(),
                        new JSCString("info_key", "relLikCh") },
                new JSCData[] { vecBuffer });
        engine().call(
                "vml_vput",
                new JSCData[] { vecBuffer,
                        gst.get(SVECConstants.maxIter_Def).getJSCData(),
                        new JSCString("info_key", "maxIter") },
                new JSCData[] { vecBuffer });
        engine().call(
                "vml_vput",
                new JSCData[] { vecBuffer,
                        gst.get(SVECConstants.normConst_Def).getJSCData(),
                        new JSCString("info_key", "normCon") },
                new JSCData[] { vecBuffer });
        engine().call(
                "vml_vput",
                new JSCData[] { vecBuffer, new JSCString("modelName", "SVEC"),
                        new JSCString("info_key", "svar") },
                new JSCData[] { vecBuffer });

        JSCNArray[] retArgs = getRtnArgs();

        engine().call(
                "var__IRA_CI_ST",
                new JSCData[] { vecBuffer,
                        new JSCInt("numberOfRep", numberOfRep),
                        new JSCInt("stdNumberOfRep", stdNumberOfRep),
                        new JSCInt("seed", seed),
                        new JSCInt("stdSeed", stdSeed),
                        new JSCInt("periods", periods),
                        new JSCNumber("coverage", coverage) }, retArgs);

        IRACIManager manager = IRACIManager.getInstance();

        // Store in CI Manager
        // STUD_ORTH_LOWER|STUD_ORTH_UPPER
        JSCNArray[] gdStud = new JSCNArray[] { retArgs[4], retArgs[5] };
        manager.addCI(getCIName(), modelType, gdStud);
        setLocalResults(retArgs, true);

    }

    /**
     * @see ProcCall
     */
    private void runVARVECCode() {
        seed = isSeed ? seed : 0;
        stdSeed = stdIsSeed ? stdSeed : 0;

        // VAR model buffer.
        JSCNArray varBuffer = new JSCNArray("varBuffer");
        // VAR model buffer.
        if (modelType == ModelTypes.VAR) {
            VAREstimationCall.setVarBuffer(engine(), gst, varBuffer);

        } else { // VEC model buffer.
            int estStrat = gst.get(VECMConstants.estimationStrategy_Def)
                    .getJSCInt().intVal();

            VECMEstimationCall.setVecBuffer(engine(), gst, varBuffer, estStrat);

        }

        JSCNArray[] retArgs = getRtnArgs();

        engine().call(
                "var__IRA_CI_ST",
                new JSCData[] { varBuffer,
                        new JSCInt("numberOfRep", numberOfRep),
                        new JSCInt("stdNumberOfRep", stdNumberOfRep),
                        new JSCInt("seed", seed),
                        new JSCInt("stdSeed", stdSeed),
                        new JSCInt("periods", periods),
                        new JSCNumber("coverage", coverage) }, retArgs);

        IRACIManager manager = IRACIManager.getInstance();

        // Store in CI Manager
        // STUD_FERR_LOWER|STUD_FERR_UPPER|STUD_ORTH_LOWER|STUD_ORTH_UPPER
        JSCNArray[] gdStud = new JSCNArray[] { retArgs[1], retArgs[2],
                retArgs[4], retArgs[5] };

        manager.addCI(getCIName(), modelType, gdStud);
        setLocalResults(retArgs, false);

    }

    /**
     * @return
     */
    private JSCNArray[] getRtnArgs() {
        JSCNArray[] retArgs = new JSCNArray[] { new JSCNArray("__rt_vec0"),
                new JSCNArray("__rt_vec1"), new JSCNArray("__rt_vec2"),
                new JSCNArray("__rt_vec3"), new JSCNArray("__rt_vec4"),
                new JSCNArray("__rt_vec5"), new JSCNArray("__rt_vec6"),
                new JSCNArray("__rt_vec7"), (JSCNArray) FE_BOOT.getInstance(),
                (JSCNArray) FE_BOOT_STD.getInstance(),
                (JSCNArray) ORTH_BOOT.getInstance(),
                (JSCNArray) ORTH_BOOT_STD.getInstance() };
        return retArgs;
    }

    /**
     * @param retArgs
     */
    private void setLocalResults(JSCNArray[] retArgs, boolean isSVARVEC) {
        SymbolTable local = getSymbolTable();
        if (local != null) {
            if (!isSVARVEC) {
                local.get(FE_BOOT).setJSCData(retArgs[8]);
                local.get(FE_BOOT_STD).setJSCData(retArgs[9]);
            }
            local.get(ORTH_BOOT).setJSCData(retArgs[10]);
            local.get(ORTH_BOOT_STD).setJSCData(retArgs[11]);
        }
    }
}