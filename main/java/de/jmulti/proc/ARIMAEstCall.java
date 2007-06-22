/*
 * Copyright (C) 2006 Markus Kraetzig
 * Created on 10.07.2006
 */
package de.jmulti.proc;

import java.io.File;
import java.io.IOException;

import com.jstatcom.engine.gauss.GaussLoadTypes;
import com.jstatcom.io.FileSupport;
import com.jstatcom.model.JSCData;
import com.jstatcom.model.JSCInt;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.JSCSArray;
import com.jstatcom.model.JSCString;
import com.jstatcom.model.JSCTypeDef;
import com.jstatcom.model.JSCTypes;
import com.jstatcom.model.SymbolTable;
import com.jstatcom.ts.TSDateRange;
import com.jstatcom.util.UString;

import de.jmulti.arima.ARIMAConstants;

public class ARIMAEstCall extends RPCall {

    private File outFile = null;

    private int p = 0;

    private int q = 0;

    private int d = 0;

    private String yName = null;

    private JSCNArray y = null;

    private JSCNArray detData = null;

    private JSCSArray detNames = null;

    private JSCNArray b0 = null;

    // results
    private JSCNArray b = null;

    private JSCNArray ll = null;

    private JSCNArray e = null;

    private JSCNArray covb = null;

    private JSCNArray infoCrits = null;

    private SymbolTable global = null;

    private SymbolTable local = null;

    private TSDateRange range;

    public final static JSCTypeDef INFO_CRITS = new JSCTypeDef("INFO_CRITS",
            JSCTypes.NARRAY, "AIC|SBC from estimation");

    public final static JSCTypeDef LOG_LIK = new JSCTypeDef("LOG_LIK",
            JSCTypes.NARRAY, "log-likelihood from estimation");

    public ARIMAEstCall(int p, int q, int d, JSCNArray y, JSCNArray detData,
            JSCSArray detNames, JSCNArray b0, String yName, TSDateRange range,
            SymbolTable global, SymbolTable local) {

        setName("ARIMA Estimation");

        this.p = p;
        this.q = q;
        this.d = d;
        this.y = y;
        this.b0 = b0;
        this.yName = yName;
        this.global = global;
        this.local = local;
        this.detData = detData;
        this.detNames = detNames;
        this.range = range;

    }

    @Override
    protected void runCode() {
        if (global != null) {
            global.get(ARIMAConstants.EST_PARAMS).clear();
            global.get(ARIMAConstants.COV_EST_PARAMS).clear();
            global.get(ARIMAConstants.EST_RESIDS).clear();
            global.get(ARIMAConstants.DRANGE_ADJUSTED).getJSCDRange();
        }
        if (local != null) {
            local.get(INFO_CRITS).clear();
            local.get(LOG_LIK).clear();
        }
        try {
            outFile = File.createTempFile("ARIMA", "OUT");
        } catch (IOException e1) {
            throw new RuntimeException(
                    "Could not generate temp file for output", e1);
        }
        JSCString fileName = new JSCString("outFileName", UString
                .replaceAllSubStrings(outFile.getAbsolutePath(), "\\", "/"));

     
        
        if (b0 == null || b0.isEmpty())
            b0 = new JSCNArray("b0", 0);

        b = new JSCNArray("b");
        ll = new JSCNArray("ll");
        e = new JSCNArray("e");
        covb = new JSCNArray("covb");
        infoCrits = new JSCNArray("infoCrits");
        JSCData[] rtns = new JSCData[] { b, ll, e, covb, infoCrits };

        engine().load("arima, uni", GaussLoadTypes.LIB);

        JSCSArray allNames = new JSCSArray("allNames", yName);
        allNames.appendRows(detNames);

        JSCData[] inArgs = new JSCData[] { b0, y, new JSCInt("p", p),
                new JSCInt("d", d), new JSCInt("q", q), detData, fileName,
                allNames };

        engine().call("arima_uni", inArgs, rtns);

        if (global != null) {
            global.get(ARIMAConstants.EST_PARAMS).setJSCData(b);
            global.get(ARIMAConstants.COV_EST_PARAMS).setJSCData(covb);
            global.get(ARIMAConstants.EST_RESIDS).setJSCData(e);
        }
        if (local != null) {
            local.get(INFO_CRITS).setJSCData(infoCrits);
            local.get(LOG_LIK).setJSCData(ll);
        }

    }

    @Override
    protected void finalCode() {
        int toAdd = range.numOfObs() - e.rows();
        TSDateRange r = range.addPeriodsToStart(toAdd);
        if (global != null)
            global.get(ARIMAConstants.DRANGE_ADJUSTED).getJSCDRange().setVal(r);

        output.append(r.format("sample range:", 20) + "\n");

        output.append(FileSupport.getInstance().readTextFile(
                outFile.getAbsolutePath()));

    }

    @Override
    protected void cleanUpAfterCall() {
        outFile.delete();
    }
}
