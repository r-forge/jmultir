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

import Jama.Matrix;

import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.SymbolTable;
import com.jstatcom.ts.TSDateRange;
import com.jstatcom.util.FArg;
import com.jstatcom.util.UMatrix;

import de.jmulti.tools.ModelTypes;
import de.jmulti.var.VARConstants;
import de.jmulti.vecm.VECMConstants;

/**
 * This command object computes and prints some statistics for a given estimated
 * model.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
public final class ModelStatsCall extends RPCall {

    private ModelTypes modelType = null;

    private double[][] cov = null;

    private double[][] corr = null;

    private double[] infoCrit = new double[4];

    private double[][] lr = null;

    private double logLik = 0;

    private double r2 = 0;

    private double adjr2 = 0;

    private double detCovar = 0;

    private TSDateRange range = null;

    /**
     * <code>ModelStatsCall</code> constructor takes the arguments for the
     * procedure call.
     */
    public ModelStatsCall(SymbolTable gst, ModelTypes modelType) {

        super();

        setSymbolTable(gst);
        setName(modelType + " Statistics");

        this.modelType = modelType;

    }

    /**
     * Writes the text output to a string that can be referenced via
     * <code>getOutput</code>.
     */
    protected void finalCode() {
        StringBuffer buffer = output;
        buffer.append(modelType + " MODEL STATISTICS\n");
        buffer.append(range.format("sample range:", 16) + "\n\n");

        buffer.append(FArg.sprintf("%-20s %- 11.6e \n", new FArg(
                "Log Likelihood:").add(logLik)));
        buffer.append(FArg.sprintf("%-20s %- 11.6e \n", new FArg(
                "Determinant (Cov):").add(detCovar)));
        buffer.append("\n");

        buffer.append("Covariance:  ");
        for (int i = 0; i < cov.length; i++) {
            for (int j = 0; j < cov[0].length; j++) {
                buffer.append(FArg.sprintf("%- 14.6e", new FArg(cov[i][j])));
            }
            buffer.append("\n             ");
        }
        buffer.append("\n");
        buffer.append("Correlation: ");
        for (int i = 0; i < corr.length; i++) {
            for (int j = 0; j < corr[0].length; j++)
                buffer.append(FArg.sprintf("%- 14.6e", new FArg(corr[i][j])));

            buffer.append("\n             ");
        }
        buffer.append("\n");

        if (lr != null && lr.length >= 3) {
            buffer.append(FArg.sprintf("%-34s %- 8.4f \n", new FArg(
                    "LR-test (H1: unrestricted model):").add(lr[0][0])));
            buffer.append(FArg.sprintf("%-34s %- 8.4f \n", new FArg(
                    " p-value(chi^2):").add(lr[1][0])));
            buffer.append(FArg.sprintf("%-34s %- 8.4f \n", new FArg(
                    " degrees of freedom:").add(lr[2][0])));
            buffer.append("\n");
        }

        if (modelType == ModelTypes.VAR) {
            buffer.append(FArg.sprintf("%-13s%- 12.6e\n", new FArg("AIC:")
                    .add(infoCrit[0])));
            buffer.append(FArg.sprintf("%-13s%- 12.6e\n", new FArg("FPE:")
                    .add(infoCrit[1])));
            buffer.append(FArg.sprintf("%-13s%- 12.6e\n", new FArg("SC:")
                    .add(infoCrit[2])));
            buffer.append(FArg.sprintf("%-13s%- 12.6e\n", new FArg("HQ:")
                    .add(infoCrit[3])));

            if (cov.length == 1) {
                buffer.append("\n");
                buffer.append(FArg.sprintf("%-13s%- .4f\n", new FArg("R2:")
                        .add(r2)));
                buffer.append(FArg.sprintf("%-13s%- .4f\n", new FArg(
                        "adjusted R2:").add(adjr2)));

            }
        }
        buffer.append("\n");
    }

    /**
     * @see RPCall
     */
    protected void runCode() {
        JSCNArray res = null;
        double nrOfParam = 0;
        range = null;
        int k = 0;
        SymbolTable gst = getSymbolTable();
        if (modelType == ModelTypes.VAR) {
            cov = gst.get(VARConstants.cv_u_hat_Def).getJSCNArray()
                    .doubleArray();

            res = gst.get(VARConstants.u_hat_Def).getJSCNArray();
            range = gst.get(VARConstants.T1_Def).getJSCDRange()
                    .getTSDateRange();

            k = cov.length;
            JSCNArray allCoeff = new JSCNArray("allCoeff");
            allCoeff.appendCols(gst.get(VARConstants.Ay_Def).getJSCNArray());
            allCoeff.appendCols(gst.get(VARConstants.Ax_Def).getJSCNArray());
            allCoeff.appendCols(gst.get(VARConstants.Ad_Def).getJSCNArray());
            nrOfParam = 0;
            if (allCoeff.cols() > 0) {
                double[] coeff = allCoeff.vec();
                nrOfParam = UMatrix.getNonzeroDoubleCount(coeff);
            }
            if (k == 1) {
                JSCNArray y = gst.get(VARConstants.y_Def).getJSCNArray().copy();
                int[] index = new int[y.rows()];
                for (int i = 0; i < y.rows() - res.rows(); i++)
                    index[i] = 1;
                y.delRowsIf(index);
                Matrix yhat = y.jamaMatrix().minus(res.jamaMatrix());
                int T = res.rows();
                double ymean2 = Math.pow(UMatrix.meanc(y.doubleArray())[0], 2);
                double yhat2sum = yhat.transpose().times(yhat).get(0, 0);
                Matrix yMat = y.jamaMatrix();
                double y2sum = yMat.transpose().times(yMat).get(0, 0);
                r2 = (yhat2sum - T * ymean2) / (y2sum - T * ymean2);
                adjr2 = 1 - ((T - 1) / (T - nrOfParam)) * (1 - r2);
            }

        } else if (modelType == ModelTypes.VECM) {
            cov = gst.get(VECMConstants.cv_u_hat_Def).getJSCNArray()
                    .doubleArray();

            res = gst.get(VECMConstants.u_hat_Def).getJSCNArray();
            range = gst.get(VECMConstants.T1_Def).getJSCDRange()
                    .getTSDateRange();
            k = cov.length;
            lr = gst.get(VECMConstants.lrtest_Def).getJSCNArray().doubleArray();

        } else
            throw new IllegalArgumentException("Invalid model type "
                    + modelType + ".");

        int T = range.numOfObs();
        Matrix covMat = new Matrix(cov);

        // compute covariance divided by T from residuals, because
        // covMat might be divided by T-K in an unrestricted VAR
        if (modelType == ModelTypes.VAR) {
            Matrix covMat_T = res.jamaMatrix().transpose().times(
                    res.jamaMatrix()).times(1.0 / T);
            // Determinant of Covariance from T division.
            detCovar = covMat_T.det();
        } else if (modelType == ModelTypes.VECM)
            // for VECMs take covariance returned from estimation
            detCovar = covMat.det();

        // Log-Likelihood.
        logLik = -T / 2
                * (k * (1 + Math.log(2 * Math.PI)) + Math.log(detCovar));
        // Correlation.
        // use computed covariance again
        double[][] corrMult = UMatrix.eye(k);
        for (int i = 0; i < cov.length; i++)
            corrMult[i][i] = Math.pow(cov[i][i], -0.5);

        Matrix multMat = new Matrix(corrMult);
        corr = multMat.times(covMat).times(multMat).getArray();
        // Just to avoid precision errors.
        for (int i = 0; i < cov.length; i++)
            corr[i][i] = 1;

        // Infocriteria: AIC|FPE|SC|HQ.
        if (modelType == ModelTypes.VAR) {
            lr = gst.get(VARConstants.lrtest_Def).getJSCNArray().doubleArray();

            // AIC
            infoCrit[0] = Math.log(detCovar) + 2 * nrOfParam / T;
            // FPE
            infoCrit[1] = Math.pow(((T + nrOfParam / k) / (T - nrOfParam / k)),
                    k)
                    * detCovar;
            // SC
            infoCrit[2] = Math.log(detCovar) + Math.log(T) / T * nrOfParam;
            // HQ
            infoCrit[3] = Math.log(detCovar) + 2 * Math.log(Math.log(T)) / T
                    * nrOfParam;

        }

    }
}