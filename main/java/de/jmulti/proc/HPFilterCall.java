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
import com.jstatcom.model.JSCInt;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.JSCTypeDef;
import com.jstatcom.model.JSCTypes;

/**
 * This GAUSS command object computes Hodrick-Prescott filter.
 *
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public final class HPFilterCall extends RPCall {
	public static final JSCTypeDef HPFILTER_RESULT =
		new JSCTypeDef(
			"HPFILTER_RESULT",
			JSCTypes.NARRAY,
			"HP filtered components:  trend component~cyclical component");

	private JSCNArray data = null;
	private int lambda = 0;

	/**
	 * <code>HPFilterCall</code> constructor takes the arguments for the procedure call.
	 *
	 */
	public HPFilterCall(JSCNArray data, int lambda) {

		super();

		setName("HP-Filter");

		this.data = data;
		this.lambda = lambda;

	}

	/**
	 * Writes the text output to a string that can be
	 * referenced via <code>getOutput</code>.
	 */
	protected void finalCode() {

	}

	/**
	 * @see RPCall
	 */
	protected void runCode() {

		if (getSymbolTable() != null)
			getSymbolTable().get(HPFILTER_RESULT).clear();

		if (data.rows() < 2)
			throw new RuntimeException("Not enough observations");

		if (getSymbolTable() != null)
			getSymbolTable().get(HPFILTER_RESULT).clear();

		engine().load("fil", GaussLoadTypes.LIB);

		JSCData hpResult = HPFILTER_RESULT.getInstance();

		engine().call(
			"hodpres_filter",
			new JSCData[] { data, new JSCInt("lambda", lambda)},
			new JSCData[] { hpResult });
		
		if (getSymbolTable() != null)
			getSymbolTable().set(hpResult);

	}
}