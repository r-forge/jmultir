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

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.jstatcom.component.SystemOutHolder;
import com.jstatcom.engine.PCall;
import com.jstatcom.model.JSCConstants;
import com.jstatcom.model.JSCDRange;
import com.jstatcom.model.JSCInt;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.JSCSArray;
import com.jstatcom.model.JSCString;
import com.jstatcom.model.SymbolTable;
import com.jstatcom.ts.TSDate;
import com.jstatcom.ts.TSDateRange;
import com.jstatcom.util.UMatrix;

import de.jmulti.arima.ARIMAConstants;
import de.jmulti.tools.IRACIManager;
import de.jmulti.tools.ModelTypes;
import de.jmulti.var.SVARConstants;
import de.jmulti.var.VARConstants;
import de.jmulti.vecm.VECMConstants;

/**
 * This is a set of tests for the proc package. It makes use of the junit
 * framework.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
public class ProcTest extends TestCase {

    /**
     * GaussTest constructor comment.
     * 
     * @param name
     *            String
     */
    public ProcTest(String name) {
        super(name);
    }

    @Override
    protected void setUp() throws Exception {
        JSCConstants.DEBUG = true;
    }

    /**
     * Here a TestSuite is created, which automatically executes all methods
     * that start with test* by using a reflection mechanism.
     * 
     * @return Test
     */
    public static Test suite() {
        return new TestSuite(ProcTest.class);
    }

    /**
     * 
     */
    public void testCIManager() {
        IRACIManager m = IRACIManager.getInstance();
        m.addCI("test1", ModelTypes.VAR, new JSCNArray[] {
                new JSCNArray("test"), new JSCNArray("test"),
                new JSCNArray("test"), new JSCNArray("test") });
        m.addCI("test2", ModelTypes.VAR, new JSCNArray[] {
                new JSCNArray("test"), new JSCNArray("test"),
                new JSCNArray("test"), new JSCNArray("test") });
        assertEquals(m.getCIKeys(ModelTypes.VAR)[1], "test2");
        assertEquals(m.getCIKeys(ModelTypes.VAR)[0], "test1");
    }

    /**
     * 
     */
    public void testTSPlot() {
        if (true)
            return;
        for (int i = 0; i < 20; i++) {
            PCall job = new PlotTSCall(new JSCNArray("test", UMatrix.ones(100,
                    1)), new TSDate(), new JSCSArray("test1", "sd"), false,
                    false);
            job.execute();
        }
        try {
            Thread.sleep(3000);
        } catch (Throwable e) {
        }
    }

    /**
     * 
     */
    public void testUnivarGodfrey() {
        SymbolTable sTable = new SymbolTable("test");
        PCall job = new UnivarGodfreyCall(new JSCNArray("res", UMatrix.rndu(
                100, 1)), new JSCNArray("orig", UMatrix.rndu(100, 1)), 4);
        job.setSymbolTable(sTable);
        job.run();

        assertEquals(sTable.get(UnivarGodfreyCall.GODFREY_EST).getJSCNArray()
                .rows(), 5);
        assertEquals(sTable.get(UnivarGodfreyCall.GODFREY_RESULT)
                .getJSCNArray().cols(), 4);
        job.engine().shutdown();
    }

    /**
     * Tests the TS name check.
     */
    public void testS2S() {
        SymbolTable table = new SymbolTable("s2stest", false);
        JSCNArray y = JSCNArray.valueOf(
                new File("jmtestdata/blanqua.dat"), "y");

        table.get(VECMConstants.y_Def).setJSCData(y);
        table.get(VECMConstants.x_Def).setJSCData(new JSCNArray("x"));
        table.get(VECMConstants.d_Def).setJSCData(
                new JSCNArray("d", UMatrix.ones(y.rows(), 1)));

        table.get(VECMConstants.px_Def).setJSCData(new JSCInt("px", 0));
        table.get(VECMConstants.py_Def).setJSCData(new JSCInt("py", 2));
        table.get(VECMConstants.cointRank_Def).setJSCData(new JSCInt("r", 1));
        table.get(VECMConstants.T1_Def).setJSCData(
                new JSCDRange("range", new TSDateRange()));
        table.get(VECMConstants.Nx_Def).setJSCData(new JSCSArray("nx"));
        table.get(VECMConstants.Ny_Def).setJSCData(
                new JSCSArray("ny", new String[] { "a", "b" }));
        table.get(VECMConstants.Nd_Def).setJSCData(
                new JSCSArray("nd", new String[] { "CONST" }));

        PCall job = new VECMEstimationCall(table, "varfile", "vecfile",
                VECMConstants.S2S);
        job.setOutHolder(new SystemOutHolder());
        job.run();
        JSCNArray G = table.get(VECMConstants.G_Def).getJSCNArray();
        assertEquals(G.doubleAt(0, 0), -0.0474, 0.00005);
        assertEquals(G.doubleAt(1, 0), 0.0299, 0.00005);
        assertEquals(G.doubleAt(0, 1), -0.9019, 0.00005);
        assertEquals(G.doubleAt(1, 1), 0.4170, 0.00005);

        JSCNArray beta = table.get(VECMConstants.beta_Def).getJSCNArray();
        assertEquals(beta.doubleAt(0, 0), 1, 0);
        assertEquals(beta.doubleAt(0, 1), -0.0538, 0.00005);
        job.engine().shutdown();
    }

    public void testSVAR_AB() {
        JSCNArray sigma = JSCNArray.valueOf(new File(
                "jmtestdata/svar_canad_sigma.dat"), "sigma");
        JSCNArray aMatRes = new JSCNArray("ARes", new double[][] { { 1, 0 },
                { 0, 1 } });
        JSCNArray bMatRes = new JSCNArray("BRes", new double[][] {
                { Double.NEGATIVE_INFINITY, 0 },
                { 0, Double.NEGATIVE_INFINITY } });
        PCall job = new SVAREstABCall(sigma, 81, aMatRes, bMatRes, 1.0E-10,
                1.0E-6, 0.1, 500, 1, true, 10, JSCConstants.getSystemTemp()
                        + "/svarABtest");
        SymbolTable abTest = new SymbolTable("abtest", false);
        job.setOutHolder(new SystemOutHolder());
        job.setSymbolTable(abTest);
        job.run();
        JSCNArray a0 = abTest.get(SVARConstants.svar_A0_Def).getJSCNArray();
        assertEquals(a0.doubleAt(0, 0), 0.3592, 0.00005);

        // test run with different restrictions
        aMatRes = new JSCNArray("ARes", new double[][] {
                { 1, Double.NEGATIVE_INFINITY }, { 0, 1 } });
        job = new SVAREstABCall(sigma, 81, aMatRes, bMatRes, 1.0E-10, 1.0E-6,
                0.1, 500, 1, true, 10, JSCConstants.getSystemTemp()
                        + "/svarABtest");
        abTest = new SymbolTable("abtest", false);
        job.setOutHolder(new SystemOutHolder());
        job.setSymbolTable(abTest);
        job.run();
        a0 = abTest.get(SVARConstants.svar_A0_Def).getJSCNArray();
        assertEquals(a0.doubleAt(0, 1), -0.0047, 0.00005);
        JSCNArray a_std = abTest.get(SVARConstants.svar_A_STD_Def)
                .getJSCNArray();
        assertEquals(a_std.doubleAt(0, 1), 0.0575, 0.00005);
        job.engine().shutdown();
    }

    public void testARCH_LM() {
        JSCNArray y = JSCNArray.valueOf(
                new File("jmtestdata/blanqua.dat"), "y");
        PCall job = new MultvarArchLMCall(y, 5);
        SymbolTable archTest = new SymbolTable("archtest", false);
        job.setOutHolder(new SystemOutHolder());
        job.setSymbolTable(archTest);
        job.run();
        JSCNArray result = archTest.get(MultvarArchLMCall.MARCH_RESULT)
                .getJSCNArray();
        assertEquals(result.doubleAt(0, 0), 203.9488, 0.00005);

        // 2nd test run
        job = new MultvarArchLMCall(y, 15);
        archTest = new SymbolTable("archtest", false);
        job.setOutHolder(new SystemOutHolder());
        job.setSymbolTable(archTest);
        job.run();
        result = archTest.get(MultvarArchLMCall.MARCH_RESULT).getJSCNArray();
        assertEquals(result.doubleAt(0, 0), 235.1235, 0.00005);
        job.engine().shutdown();
    }

    public void testUnivarJB() {
        JSCNArray y = JSCNArray.valueOf(
                new File("jmtestdata/blanqua.dat"), "y");
        PCall job = new UnivarJBeraCall(y, new JSCSArray("names", new String[] {
                "a", "b" }));
        job.setOutHolder(new SystemOutHolder());
        SymbolTable table = new SymbolTable("jb", false);
        job.setSymbolTable(table);

        job.run();
        JSCNArray result = table.get(UnivarJBeraCall.JBERA_RESULT)
                .getJSCNArray();
        assertEquals(result.doubleAt(0, 0), 1.5615, 0.00005);
        assertEquals(result.doubleAt(0, 1), 0.4581, 0.00005);
        assertEquals(result.doubleAt(1, 3), 2.7742, 0.00005);
        job.engine().shutdown();

    }

    public void testSLCoint() {
        JSCNArray y = JSCNArray.valueOf(new File("jmtestdata/canad.dat"),
                "y");
        JSCNArray det = JSCNArray.valueOf(new File(
                "jmtestdata/dummies.dat"), "d");
        PCall job = new SLCointTestCall(y, new JSCSArray("ynam", new String[] {
                "a", "b", "c" }), det, new JSCSArray("names", new String[] {
                "d1", "d2", "d3", "d4" }), true, 2, 1, new TSDateRange(
                new TSDate(1960, 1, 4), y.rows()));

        job.setOutHolder(new SystemOutHolder());
        SymbolTable table = new SymbolTable("sl", false);
        job.setSymbolTable(table);

        job.run();
        JSCNArray result = table.get(SLCointTestCall.SL_RESULT).getJSCNArray();

        assertEquals(result.doubleAt(0, 0), 27.3088, 0.00005);
        assertEquals(result.doubleAt(0, 1), 21.7594, 0.00005);
        assertEquals(result.doubleAt(2, 4), 0.8918, 0.00005);

        job.engine().shutdown();

    }

    public void testJohCointOneBreak() {
        JSCNArray y = JSCNArray.valueOf(new File(
                "jmtestdata/wheatflour.dat"), "y");
        JSCNArray det = new JSCNArray("d");
        TSDate[] breakDate = new TSDate[] { new TSDate(1929, 5, 12) };
        TSDateRange range = new TSDateRange(new TSDate(1924, 1, 12),
                new TSDate(1937, 4, 12));

        JSCNArray condDummies = JohCointTestCall.createCondDummies(breakDate,
                range, 1);
        JSCNArray breaks = JohCointTestCall.createBreaks(breakDate, range, 2,
                true);
        det.appendCols(condDummies);
        det.appendCols(breaks);
        int[] ecSelection = JohCointTestCall.getECSelection(new int[0],
                breakDate, 2, true, 1);
        String[] breakdumNames = JohCointTestCall.getBreakDumNames(breakDate,
                2, true, 1);
        JSCSArray detNames = new JSCSArray("names", breakdumNames);
        PCall job = new JohCointTestCall(y, new JSCSArray("ynam", new String[] {
                "logwar", "logwis" }), det, detNames, false, 1, 2,
                new TSDateRange(new TSDate(1924, 1, 12), y.rows()),
                ecSelection, breakDate, true);

        job.setOutHolder(new SystemOutHolder());
        SymbolTable table = new SymbolTable("sl", false);
        job.setSymbolTable(table);

        job.run();
        JSCNArray result = table.get(JohCointTestCall.JOH_RESULT)
                .getJSCNArray();

        assertEquals(result.doubleAt(0, 0), 48.7219, 0.00005);
        assertEquals(result.doubleAt(1, 0), 7.1248, 0.00005);
        assertEquals(result.doubleAt(0, 1), 34.191980741883704, 0.00005);
        assertEquals(result.doubleAt(1, 1), 16.6519, 0.00005);
        assertEquals(result.doubleAt(1, 4), 0.8069, 0.00005);
        assertEquals(result.doubleAt(0, 4), 0.0017733984260503144, 0.00005);

        job.engine().shutdown();
    }

    public void testJohCointTwoBreaks() {
        JSCNArray y = JSCNArray.valueOf(new File(
                "jmtestdata/wheatflour.dat"), "y");
        JSCNArray det = new JSCNArray("d");
        TSDate[] breakDate = new TSDate[] { new TSDate(1933, 7, 12),
                new TSDate(1929, 5, 12) };
        TSDateRange range = new TSDateRange(new TSDate(1924, 1, 12),
                new TSDate(1937, 4, 12));

        JSCNArray condDummies = JohCointTestCall.createCondDummies(breakDate,
                range, 3);
        JSCNArray breaks = JohCointTestCall.createBreaks(breakDate, range, 2,
                true);
        det.appendCols(condDummies);
        det.appendCols(breaks);
        int[] ecSelection = JohCointTestCall.getECSelection(new int[0],
                breakDate, 2, true, 3);
        String[] breakdumNames = JohCointTestCall.getBreakDumNames(breakDate,
                2, true, 3);
        JSCSArray detNames = new JSCSArray("names", breakdumNames);
        PCall job = new JohCointTestCall(y, new JSCSArray("ynam", new String[] {
                "logwar", "logwis" }), det, detNames, false, 3, 2,
                new TSDateRange(new TSDate(1924, 1, 12), y.rows()),
                ecSelection, breakDate, true);

        job.setOutHolder(new SystemOutHolder());
        SymbolTable table = new SymbolTable("sl", false);
        job.setSymbolTable(table);

        job.run();
        JSCNArray result = table.get(JohCointTestCall.JOH_RESULT)
                .getJSCNArray();

        assertEquals(result.doubleAt(0, 0), 42.87176756146717, 0.00005);
        assertEquals(result.doubleAt(1, 0), 14.328447321545664, 0.00005);
        assertEquals(result.doubleAt(0, 1), 45.15179423828366, 0.00005);
        assertEquals(result.doubleAt(1, 1), 22.554128461453363, 0.00005);
        assertEquals(result.doubleAt(1, 4), 0.5647643184522826, 0.00005);
        assertEquals(result.doubleAt(0, 4), 0.15295084985450935, 0.00005);

        job.engine().shutdown();
    }

    public void testInfoCrit() {
        JSCNArray y = JSCNArray.valueOf(new File("jmtestdata/canad.dat"),
                "y");
        JSCNArray det = JSCNArray.valueOf(new File(
                "jmtestdata/dummies.dat"), "d");
        JSCNArray dumI = new JSCNArray("dummyI", new double[det.rows()]);
        dumI.setValAt(1, 2, 0);
        det.appendCols(dumI);

        PCall job = new InfoCritCall(10, new JSCSArray("ynam", new String[] {
                "a", "b", "c" }), new JSCSArray("names", new String[] { "d1",
                "d2", "d3", "d4", "ii" }), y, det, new TSDate(1960, 1, 4), 0);

        job.setOutHolder(new SystemOutHolder());
        SymbolTable table = new SymbolTable("sl", false);
        job.setSymbolTable(table);
        job.run();
        JSCNArray result = table.get(InfoCritCall.INFO_VALS).getJSCNArray();

        assertEquals(result.rows(), 3);
        assertEquals(result.doubleAt(2, 3), -3.0156, 0.00005);

        job.engine().shutdown();

    }

    public void testFEVD() {
        JSCNArray coeff = JSCNArray.valueOf(new File(
                "jmtestdata/var_canad_coeff.dat"), "coeff");

        JSCNArray sigma = JSCNArray.valueOf(new File(
                "jmtestdata/var_canad_sigma.dat"), "sigma");

        FEVDCall job = new FEVDCall(20, new JSCSArray("ynam", new String[] {
                "a", "b", "c" }), new JSCNArray("selection", new int[] { 1, 1,
                1 }), coeff, sigma, null, ModelTypes.VAR, null, false);

        SymbolTable table = new SymbolTable("fevd", false);
        job.setOutHolder(new SystemOutHolder());
        job.setSymbolTable(table);
        job.run();
        JSCNArray result = table.get(FEVDCall.FEVD_RESULT).getJSCNArray();

        assertEquals(result.doubleAt(0, 0), 1.0000, 0.00005);
        assertEquals(result.doubleAt(0, 4), 0.9944, 0.00005);
        assertEquals(result.doubleAt(19, 8), 0.7315, 0.00005);

        job.engine().shutdown();
    }

    /**
     * 
     */
    public void testVAR() {
        SymbolTable table = new SymbolTable("vartest", false);
        JSCNArray y = JSCNArray.valueOf(
                new File("jmtestdata/blanqua.dat"), "y");

        table.get(VARConstants.y_Def).setJSCData(y);
        table.get(VARConstants.x_Def).setJSCData(new JSCNArray("x"));
        table.get(VARConstants.d_all_Def).setJSCData(
                new JSCNArray("d", UMatrix.ones(y.rows(), 1)));

        table.get(VARConstants.px_Def).setJSCData(new JSCInt("px", 0));
        table.get(VARConstants.py_Def).setJSCData(new JSCInt("py", 2));
        table.get(VARConstants.T1_Def).setJSCData(
                new JSCDRange("range", new TSDateRange()));
        table.get(VARConstants.Nx_Def).setJSCData(new JSCSArray("nx"));
        table.get(VARConstants.Ny_Def).setJSCData(
                new JSCSArray("ny", new String[] { "a", "b" }));
        table.get(VARConstants.Nd_Def).setJSCData(
                new JSCSArray("nd", new String[] { "CONST" }));

        PCall job = new VAREstimationCall(table, "varfile");
        job.setOutHolder(new SystemOutHolder());
        job.run();
        JSCNArray A = table.get(VARConstants.Ay_Def).getJSCNArray();
        JSCNArray cvu = table.get(VARConstants.cv_u_hat_Def).getJSCNArray();

        assertEquals(A.doubleAt(0, 0), 0.0690, 0.00005);
        assertEquals(A.doubleAt(1, 0), -0.1192, 0.00005);
        assertEquals(A.doubleAt(0, 1), -0.6153, 0.00005);
        assertEquals(A.doubleAt(1, 1), 1.3335, 0.00005);

        assertEquals(cvu.doubleAt(0, 0), 0.9240, 0.00005);
        assertEquals(cvu.doubleAt(0, 1), -0.2022, 0.00005);
        assertEquals(cvu.doubleAt(1, 1), 0.1029, 0.00005);

        job.engine().shutdown();
    }

    /**
     * 
     */
    public void testVARIRABootSubset() {
        SymbolTable table = new SymbolTable("vartest", false);
        JSCNArray y = JSCNArray.valueOf(
                new File("jmtestdata/blanqua.dat"), "y");

        table.get(VARConstants.y_Def).setJSCData(y);
        table.get(VARConstants.d_all_Def).setJSCData(
                new JSCNArray("d", UMatrix.ones(y.rows(), 1)));

        table.get(VARConstants.px_Def).setJSCData(new JSCInt("px", 0));
        table.get(VARConstants.py_Def).setJSCData(new JSCInt("py", 1));
        table.get(VARConstants.T1_Def).setJSCData(
                new JSCDRange("range", new TSDateRange()));
        table.get(VARConstants.Ny_Def).setJSCData(
                new JSCSArray("ny", new String[] { "a", "b" }));
        table.get(VARConstants.Nd_Def).setJSCData(
                new JSCSArray("nd", new String[] { "CONST" }));
        table.get(VARConstants.Cy_Def).setJSCData(
                new JSCNArray("Cy_Def", new double[][] { { 0, 0 }, { 1, 1 } }));
        table.get(VARConstants.Cd_Def).setJSCData(
                new JSCNArray("Cd_Def", new double[][] { { 1 }, { 1 } }));

        PCall job = new VAREstimationCall(table, "varfile");
        job.setOutHolder(new SystemOutHolder());
        job.run();
        JSCNArray A = table.get(VARConstants.Ay_Def).getJSCNArray();
        JSCNArray cvu = table.get(VARConstants.cv_u_hat_Def).getJSCNArray();

        assertEquals(A.doubleAt(0, 0), 0, 0);
        assertEquals(A.doubleAt(1, 0), -0.1687, 0.00005);
        assertEquals(A.doubleAt(0, 1), 0, 0);
        assertEquals(A.doubleAt(1, 1), 1.0023, 0.00005);

        assertEquals(cvu.doubleAt(0, 0), 1.2156, 0.00005);
        assertEquals(cvu.doubleAt(0, 1), -0.2940, 0.00005);
        assertEquals(cvu.doubleAt(1, 1), 0.1331, 0.00005);
        IRACIManager.getInstance().clear(ModelTypes.VAR);
        PCall job1 = new IRABootCIPercCall(table, 20, 100, 0.95, true, 1,
                ModelTypes.VAR);
        job1.run();

        String name = IRACIManager.getInstance().getCIKeys(ModelTypes.VAR)[1];
        JSCNArray[] bootIRA = IRACIManager.getInstance().getCI(name,
                ModelTypes.VAR, true);

        assertEquals(bootIRA[0].doubleAt(0, 0), 0.9869, 0.0005);
        assertEquals(bootIRA[0].doubleAt(0, 1), 0.0, 0.0005);
        assertEquals(bootIRA[0].doubleAt(0, 2), -0.3311, 0.0005);
        assertEquals(bootIRA[0].doubleAt(0, 3), 0.2232, 0.0005);
        assertEquals(bootIRA[0].doubleAt(1, 1), 0.0, 0.0005);
        assertEquals(bootIRA[0].doubleAt(2, 2), -0.5540, 0.0005);

        assertEquals(bootIRA[1].doubleAt(0, 0), 1.2511, 0.0005);
        assertEquals(bootIRA[1].doubleAt(0, 1), 0.0, 0.0005);
        assertEquals(bootIRA[1].doubleAt(0, 2), -0.2128, 0.0005);
        assertEquals(bootIRA[1].doubleAt(0, 3), 0.2767, 0.0005);
        assertEquals(bootIRA[1].doubleAt(1, 1), 0.0, 0.0005);
        assertEquals(bootIRA[1].doubleAt(2, 2), -0.3826, 0.0005);

        job.engine().shutdown();
    }

    /**
     * 
     */
    public void testVARExo() {
        SymbolTable table = new SymbolTable("vartest", false);
        JSCNArray y = JSCNArray.valueOf(
                new File("jmtestdata/blanqua.dat"), "y");

        table.get(VARConstants.y_Def).getJSCNArray().setVal(y.getCol(0));
        double[] x = new double[y.rows() - 1];
        for (int i = 0; i < x.length; i++) {
            x[i] = y.doubleAt(i, 1);
        }
        table.get(VARConstants.x_Def).getJSCNArray().setVal(x);
        table.get(VARConstants.d_all_Def).setJSCData(
                new JSCNArray("d", UMatrix.ones(y.rows(), 1)));

        table.get(VARConstants.px_Def).setJSCData(new JSCInt("px", 1));
        table.get(VARConstants.py_Def).setJSCData(new JSCInt("py", 2));
        table.get(VARConstants.T1_Def).setJSCData(
                new JSCDRange("range", new TSDateRange()));
        table.get(VARConstants.Nx_Def).setJSCData(
                new JSCSArray("nx", new String[] { "b" }));
        table.get(VARConstants.Ny_Def).setJSCData(
                new JSCSArray("ny", new String[] { "a" }));
        table.get(VARConstants.Nd_Def).setJSCData(
                new JSCSArray("nd", new String[] { "CONST" }));

        PCall job = new VAREstimationCall(table, "varfile");
        job.setOutHolder(new SystemOutHolder());
        job.run();
        JSCNArray A = table.get(VARConstants.Ay_Def).getJSCNArray();
        JSCNArray B = table.get(VARConstants.Ax_Def).getJSCNArray();
        JSCNArray cvu = table.get(VARConstants.cv_u_hat_Def).getJSCNArray();

        assertEquals(A.doubleAt(0, 0), 0.0690, 0.00005);
        assertEquals(A.doubleAt(0, 1), 0.0474, 0.00005);

        assertEquals(B.doubleAt(0, 0), -0.6153, 0.00005);
        assertEquals(B.doubleAt(0, 1), 0.9019, 0.00005);

        assertEquals(cvu.doubleAt(0, 0), 0.9240, 0.00005);
        SymbolTable chowtable = new SymbolTable("chow", false);
        PCall call = new ChowTestCall(table, chowtable, new TSDateRange(),
                ModelTypes.VAR, 20, 15, 20, 2, false);
        call.setOutHolder(new SystemOutHolder());
        call.run();
        JSCNArray chow1 = chowtable.get(ChowTestCall.CHOW_BP_ALL)
                .getJSCNArray();
        JSCNArray chow2 = chowtable.get(ChowTestCall.CHOW_FC_ALL)
                .getJSCNArray();
        JSCNArray chow3 = chowtable.get(ChowTestCall.CHOW_SS_ALL)
                .getJSCNArray();
        assertEquals(chow1.doubleAt(0, 0), 11.0091, 0.0005);
        assertEquals(chow2.doubleAt(0, 0), 0.6907, 0.0005);
        assertEquals(chow3.doubleAt(0, 0), 10.9896, 0.0005);

        job.engine().shutdown();
    }

    /**
     * 
     */
    public void testVARSubsetSearchExo() {
        SymbolTable table = new SymbolTable("vartest", false);
        JSCNArray y = JSCNArray.valueOf(
                new File("jmtestdata/blanqua.dat"), "y");

        table.get(VARConstants.y_raw_Def).getJSCNArray().setVal(y.getCol(0));
        table.get(VARConstants.x_raw_Def).getJSCNArray().setVal(y.getCol(1));

        table.get(VARConstants.d_all_Def).setJSCData(
                new JSCNArray("d", UMatrix.ones(y.rows(), 1)));

        table.get(VARConstants.px_Def).setJSCData(new JSCInt("px", 1));
        table.get(VARConstants.py_Def).setJSCData(new JSCInt("py", 2));
        table.get(VARConstants.T1_Def).setJSCData(
                new JSCDRange("range", new TSDateRange()));
        table.get(VARConstants.Nx_Def).setJSCData(
                new JSCSArray("nx", new String[] { "b" }));
        table.get(VARConstants.Ny_Def).setJSCData(
                new JSCSArray("ny", new String[] { "a" }));
        table.get(VARConstants.Nd_Def).setJSCData(
                new JSCSArray("nd", new String[] { "CONST" }));

        table.get(VARConstants.Cy_Def).getJSCNArray()
                .setVal(UMatrix.ones(1, 2));
        table.get(VARConstants.Cx_Def).getJSCNArray()
                .setVal(UMatrix.ones(1, 2));
        table.get(VARConstants.Cd_Def).getJSCNArray()
                .setVal(UMatrix.ones(1, 1));

        PCall job = new VARSubsetSearchCall(table, 3, 1, 0);
        job.setOutHolder(new SystemOutHolder());
        job.run();
        JSCNArray Cy = table.get(VARConstants.Cy_Def).getJSCNArray();
        JSCNArray Cx = table.get(VARConstants.Cx_Def).getJSCNArray();
        JSCNArray Cd = table.get(VARConstants.Cd_Def).getJSCNArray();

        assertEquals(Cy.doubleAt(0, 0), 1, 0);
        assertEquals(Cy.doubleAt(0, 1), 0, 0);
        assertEquals(Cx.doubleAt(0, 0), 1, 0);
        assertEquals(Cx.doubleAt(0, 1), 1, 0);
        assertEquals(Cd.doubleAt(0, 0), 0, 0);

        job.engine().shutdown();
    }

    /**
     * 
     */
    public void testTwoStageWithRes_3SLS() {
        SymbolTable table = new SymbolTable("2stagetest", false);
        JSCNArray y = JSCNArray.valueOf(
                new File("jmtestdata/blanqua.dat"), "y");

        table.get(VECMConstants.y_Def).setJSCData(y);
        table.get(VECMConstants.x_Def).setJSCData(new JSCNArray("x"));
        table.get(VECMConstants.d_Def).setJSCData(
                new JSCNArray("d", UMatrix.ones(y.rows(), 1)));
        table.get(VECMConstants.firstStage_byJoh_Def).setJSCData(
                new JSCInt("isJoh", 1));
        table.get(VECMConstants.firstStage_byS2S_Def).setJSCData(
                new JSCInt("isS2S", 0));

        table.get(VECMConstants.firstStage_crJoh_Def).setJSCData(
                new JSCInt("crJoh", 1));

        table.get(VECMConstants.secondStage_method_Def).setJSCData(
                new JSCString("stage2", "3SLS"));

        table.get(VECMConstants.S_alpha_Def).setJSCData(
                (new JSCNArray("S_alpha", new double[] { 0, 1 })));
        table.get(VECMConstants.S_G_Def).setJSCData(
                (new JSCNArray("S_G", UMatrix.ones(2, 4))));
        table.get(VECMConstants.S_C_VEC_Def).setJSCData(
                (new JSCNArray("S_C_VEC", UMatrix.ones(2, 1))));

        table.get(VECMConstants.px_Def).setJSCData(new JSCInt("px", 0));
        table.get(VECMConstants.py_Def).setJSCData(new JSCInt("py", 2));
        table.get(VECMConstants.cointRank_Def).setJSCData(new JSCInt("r", 1));
        table.get(VECMConstants.T1_Def).setJSCData(
                new JSCDRange("range", new TSDateRange()));
        table.get(VECMConstants.Nx_Def).setJSCData(new JSCSArray("nx"));
        table.get(VECMConstants.Ny_Def).setJSCData(
                new JSCSArray("ny", new String[] { "a", "b" }));
        table.get(VECMConstants.Nd_Def).setJSCData(
                new JSCSArray("nd", new String[] { "CONST" }));
        table.get(VECMConstants.estimationStrategy_Def).setJSCData(
                new JSCInt("estStrat", VECMConstants.TWO_STAGE));

        PCall job = new VECMEstimationCall(table, "varfile", "vecfile",
                VECMConstants.TWO_STAGE);
        job.setOutHolder(new SystemOutHolder());
        job.run();
        JSCNArray G = table.get(VECMConstants.G_Def).getJSCNArray();

        assertEquals(G.doubleAt(0, 0), -0.3714, 0.0005);
        assertEquals(G.doubleAt(0, 1), 0.7582, 0.0005);
        assertEquals(G.doubleAt(1, 0), 0.0815, 0.0005);
        assertEquals(G.doubleAt(1, 1), 0.072, 0.0005);

        JSCNArray beta = table.get(VECMConstants.beta_Def).getJSCNArray();
        assertEquals(beta.doubleAt(0, 0), 1, 0);
        assertEquals(beta.doubleAt(0, 1), -0.0556, 0.0005);

        JSCNArray lr = table.get(VECMConstants.lrtest_Def).getJSCNArray();
        assertEquals(lr.doubleAt(0, 0), 30.2263, 0.0005);

        SymbolTable chowtable = new SymbolTable("chow", false);
        PCall call = new ChowTestCall(table, chowtable, new TSDateRange(),
                ModelTypes.VECM, 20, 15, 20, 2, false);
        call.setOutHolder(new SystemOutHolder());
        call.run();
        JSCNArray chow1 = chowtable.get(ChowTestCall.CHOW_BP_ALL)
                .getJSCNArray();
        JSCNArray chow2 = chowtable.get(ChowTestCall.CHOW_FC_ALL)
                .getJSCNArray();
        JSCNArray chow3 = chowtable.get(ChowTestCall.CHOW_SS_ALL)
                .getJSCNArray();
        assertEquals(chow1.doubleAt(0, 0), 14.2789, 0.0005);
        assertEquals(chow2.doubleAt(0, 0), 0.3140, 0.0005);
        assertEquals(chow3.doubleAt(0, 0), 6.7251, 0.0005);

        job.engine().shutdown();
    }

    /**
     * 
     */
    public void testTwoStageWithRes_OLS() {
        SymbolTable table = new SymbolTable("2stagetest", false);
        JSCNArray y = JSCNArray.valueOf(
                new File("jmtestdata/blanqua.dat"), "y");

        table.get(VECMConstants.y_Def).setJSCData(y);
        table.get(VECMConstants.x_Def).setJSCData(new JSCNArray("x"));
        table.get(VECMConstants.d_Def).setJSCData(
                new JSCNArray("d", UMatrix.ones(y.rows(), 1)));
        table.get(VECMConstants.firstStage_byJoh_Def).setJSCData(
                new JSCInt("isJoh", 1));
        table.get(VECMConstants.firstStage_byS2S_Def).setJSCData(
                new JSCInt("isS2S", 0));

        table.get(VECMConstants.firstStage_crJoh_Def).setJSCData(
                new JSCInt("crJoh", 1));

        table.get(VECMConstants.secondStage_method_Def).setJSCData(
                new JSCString("stage2", "OLS"));

        table.get(VECMConstants.S_alpha_Def).setJSCData(
                (new JSCNArray("S_alpha", new double[] { 0, 1 })));
        table.get(VECMConstants.S_G_Def).setJSCData(
                (new JSCNArray("S_G", UMatrix.ones(2, 4))));
        table.get(VECMConstants.S_C_VEC_Def).setJSCData(
                (new JSCNArray("S_C_VEC", UMatrix.ones(2, 1))));

        table.get(VECMConstants.px_Def).setJSCData(new JSCInt("px", 0));
        table.get(VECMConstants.py_Def).setJSCData(new JSCInt("py", 2));
        table.get(VECMConstants.cointRank_Def).setJSCData(new JSCInt("r", 1));
        table.get(VECMConstants.T1_Def).setJSCData(
                new JSCDRange("range", new TSDateRange()));
        table.get(VECMConstants.Nx_Def).setJSCData(new JSCSArray("nx"));
        table.get(VECMConstants.Ny_Def).setJSCData(
                new JSCSArray("ny", new String[] { "a", "b" }));
        table.get(VECMConstants.Nd_Def).setJSCData(
                new JSCSArray("nd", new String[] { "CONST" }));
        table.get(VECMConstants.estimationStrategy_Def).setJSCData(
                new JSCInt("estStrat", VECMConstants.TWO_STAGE));

        PCall job = new VECMEstimationCall(table, "varfile", "vecfile",
                VECMConstants.TWO_STAGE);
        job.setOutHolder(new SystemOutHolder());
        job.run();
        JSCNArray G = table.get(VECMConstants.G_Def).getJSCNArray();

        assertEquals(G.doubleAt(0, 0), -0.3714, 0.0005);
        assertEquals(G.doubleAt(0, 1), 0.7582, 0.0005);
        assertEquals(G.doubleAt(1, 0), 0.007, 0.005);
        assertEquals(G.doubleAt(1, 1), 0.371, 0.0005);

        JSCNArray beta = table.get(VECMConstants.beta_Def).getJSCNArray();
        assertEquals(beta.doubleAt(0, 0), 1, 0);
        assertEquals(beta.doubleAt(0, 1), -0.0556, 0.0005);

        JSCNArray lr = table.get(VECMConstants.lrtest_Def).getJSCNArray();
        assertEquals(lr.doubleAt(0, 0), 52.3355, 0.0005);
        job.engine().shutdown();
    }

    /**
     * 
     */
    public void testTwoStageWithRes_GLS() {
        SymbolTable table = new SymbolTable("2stagetest", false);
        JSCNArray y = JSCNArray.valueOf(
                new File("jmtestdata/blanqua.dat"), "y");

        table.get(VECMConstants.y_Def).setJSCData(y);
        table.get(VECMConstants.x_Def).setJSCData(new JSCNArray("x"));
        table.get(VECMConstants.d_Def).setJSCData(
                new JSCNArray("d", UMatrix.ones(y.rows(), 1)));
        table.get(VECMConstants.firstStage_byJoh_Def).setJSCData(
                new JSCInt("isJoh", 1));
        table.get(VECMConstants.firstStage_byS2S_Def).setJSCData(
                new JSCInt("isS2S", 0));

        table.get(VECMConstants.firstStage_crJoh_Def).setJSCData(
                new JSCInt("crJoh", 1));

        table.get(VECMConstants.secondStage_method_Def).setJSCData(
                new JSCString("stage2", "GLS"));

        table.get(VECMConstants.S_alpha_Def).setJSCData(
                (new JSCNArray("S_alpha", new double[] { 0, 1 })));
        table.get(VECMConstants.S_G_Def).setJSCData(
                (new JSCNArray("S_G", UMatrix.ones(2, 4))));
        table.get(VECMConstants.S_C_VEC_Def).setJSCData(
                (new JSCNArray("S_C_VEC", UMatrix.ones(2, 1))));

        table.get(VECMConstants.px_Def).setJSCData(new JSCInt("px", 0));
        table.get(VECMConstants.py_Def).setJSCData(new JSCInt("py", 2));
        table.get(VECMConstants.cointRank_Def).setJSCData(new JSCInt("r", 1));
        table.get(VECMConstants.T1_Def).setJSCData(
                new JSCDRange("range", new TSDateRange()));
        table.get(VECMConstants.Nx_Def).setJSCData(new JSCSArray("nx"));
        table.get(VECMConstants.Ny_Def).setJSCData(
                new JSCSArray("ny", new String[] { "a", "b" }));
        table.get(VECMConstants.Nd_Def).setJSCData(
                new JSCSArray("nd", new String[] { "CONST" }));
        table.get(VECMConstants.estimationStrategy_Def).setJSCData(
                new JSCInt("estStrat", VECMConstants.TWO_STAGE));

        PCall job = new VECMEstimationCall(table, "varfile", "vecfile",
                VECMConstants.TWO_STAGE);
        job.setOutHolder(new SystemOutHolder());
        job.run();
        JSCNArray G = table.get(VECMConstants.G_Def).getJSCNArray();

        assertEquals(G.doubleAt(0, 0), -0.3714, 0.0005);
        assertEquals(G.doubleAt(0, 1), 0.758, 0.005);
        assertEquals(G.doubleAt(1, 0), 0.097, 0.005);
        assertEquals(G.doubleAt(1, 1), 0.010, 0.005);

        JSCNArray beta = table.get(VECMConstants.beta_Def).getJSCNArray();
        assertEquals(beta.doubleAt(0, 0), 1, 0);
        assertEquals(beta.doubleAt(0, 1), -0.0556, 0.0005);

        JSCNArray lr = table.get(VECMConstants.lrtest_Def).getJSCNArray();
        assertEquals(lr.doubleAt(0, 0), 29.5066, 0.0005);
        job.engine().shutdown();
    }

    public void testARIMA() {
        SymbolTable table1 = new SymbolTable("global", false);
        SymbolTable table2 = new SymbolTable("local", false);

        JSCNArray y = JSCNArray.valueOf(
                new File("jmtestdata/blanqua.dat"), "y");
        y = new JSCNArray("y", y.getCol(1));

        int p = 3;
        int q = 2;
        int d = 1;
        PCall job = new ARIMAEstCall(p, q, d, y, new JSCNArray("dets", UMatrix
                .ones(y.rows(), 1)), new JSCSArray("detNames", "CONST1"), null,
                "test", new TSDateRange(), table1, table2);

        job.run();

        JSCNArray result = table1.get(ARIMAConstants.EST_PARAMS).getJSCNArray();
        assertEquals(result.doubleAt(0, 0), 0.70845697, 0.000005);

        job = new ARIMAHanRissPCall(10, 5, 1, y, new JSCNArray("dets", UMatrix
                .ones(y.rows(), 1)), "test", new TSDateRange());
        job.setSymbolTable(table1);
        job.run();
        result = table1.get(ARIMAHanRissPCall.LAGS_OPT).getJSCNArray();
        assertEquals(result.intAt(0, 0), 5);

        job = new ARIMAForecastCall(p, q, d, y, table1.get(
                ARIMAConstants.EST_RESIDS).getJSCNArray(), new JSCNArray(
                "dets", UMatrix.ones(30 + p, 1)), table1.get(
                ARIMAConstants.EST_PARAMS).getJSCNArray(), "test", 0.95, 30,
                new TSDateRange(), null, false, table2);

        job.run();
        result = table2.get(ARIMAForecastCall.FORECAST).getJSCNArray();
        assertEquals(result.doubleAt(2, 1), -0.2747, 0.00005);

        job.engine().shutdown();

    }
}