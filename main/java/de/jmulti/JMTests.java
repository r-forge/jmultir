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

package de.jmulti;

import junit.framework.Test;
import junit.framework.TestSuite;
import de.jmulti.initanal.InitTests;
import de.jmulti.proc.ProcTest;
import de.jmulti.tools.ToolsTest;

/**
 * Test wrapper for all package specific tests in JMulTi.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
public final class JMTests {

    /**
     * Adds test suites for all packages and invokes test run.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(ProcTest.suite());
        suite.addTest(InitTests.suite());
        suite.addTest(ToolsTest.suite());
        return suite;
    }
}