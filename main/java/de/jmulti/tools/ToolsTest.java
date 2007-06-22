/*
 * Copyright (C) 2006 Markus Kraetzig
 * Created on 27.09.2006
 */
package de.jmulti.tools;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.JSCSArray;
import com.jstatcom.ts.TS;
import com.jstatcom.ts.TSDate;
import com.jstatcom.ts.TSHolder;

public class ToolsTest extends TestCase {
    public static Test suite() {
        return new TestSuite(ToolsTest.class);
    }

    public void testForecastHelper() {

        double[] data = new double[] { 2, 3, 4, 5, 6, 7 };
        TS ts = new TS(data, "a", TSDate.valueOf("1970 Q1"));
        TSHolder.getInstance().addTS(ts);
        JSCNArray selData = new JSCNArray("data", new double[] { 2, 3, 4, });
        JSCNArray forecData = ForecastHelper.extrapolateSelData(new JSCSArray(
                "names", "a"), selData, null, TSDate.valueOf("1970 Q4"), 3);

        assertEquals(forecData.intAt(0, 0), 5);
        assertEquals(forecData.intAt(1, 0), 6);
        assertEquals(forecData.intAt(2, 0), 7);

    }
}
