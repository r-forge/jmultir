package de.jmulti.initanal;

import com.jstatcom.model.JSCNArray;
import com.jstatcom.ts.TSDate;
import com.jstatcom.ts.TSDateRange;

import de.jmulti.proc.JohCointTestCall;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unittests for initial analysis.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
public class InitTests extends TestCase {

    public static Test suite() {
        return new TestSuite(InitTests.class);
    }

    public void testJohPanel() {
        TSDate break1 = new TSDate(1960, 1, 4);
        TSDate break2 = new TSDate(1970, 4, 4);
        TSDateRange range = TSDateRange.valueOf("[1955 Q1, 1990 Q2]");
        JSCNArray dum = JohCointTestCall.createCondDummies(new TSDate[] { break1,
                break2 }, range, 4);
        assertEquals(dum.intAt(range.indexForDate(break1), 0), 1);
        assertEquals(dum.intAt(range.indexForDate(break1) + 1, 1), 1);
        assertEquals(dum.intAt(range.indexForDate(break1) + 2, 2), 1);
        assertEquals(dum.intAt(range.indexForDate(break1) + 3, 3), 1);

        assertEquals(dum.intAt(range.indexForDate(break2), 4), 1);
        assertEquals(dum.intAt(range.indexForDate(break2) + 1, 5), 1);
        assertEquals(dum.intAt(range.indexForDate(break2) + 2, 6), 1);
        assertEquals(dum.intAt(range.indexForDate(break2) + 3, 7), 1);

    }
}
