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
import com.jstatcom.model.JSCNumber;
import com.jstatcom.model.JSCString;
import com.jstatcom.model.JSCTypeDef;
import com.jstatcom.model.JSCTypes;
import com.jstatcom.ts.TSDateRange;
import com.jstatcom.util.FArg;

/**
 * This GAUSS command object computes the Hegy test and
 * stores the result in a symbol table if one is set.
 *
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public final class HegyTestCall extends RPCall {
	public static final JSCTypeDef HEGY_EST =
		new JSCTypeDef(
			"HEGY_EST",
			JSCTypes.NARRAY,
			"Hegy test estimation results: est~t-values");
	public static final JSCTypeDef HEGY_RESIDS =
		new JSCTypeDef(
			"HEGY_RESIDS",
			JSCTypes.NARRAY,
			"Hegy test: residuals from test regression");
	public static final JSCTypeDef HEGY_SIGMA =
		new JSCTypeDef(
			"HEGY_SIGMA",
			JSCTypes.NUMBER,
			"Hegy test residual covariance");
	public static final JSCTypeDef HEGY_STAT =
		new JSCTypeDef(
			"HEGY_STAT",
			JSCTypes.NARRAY,
			"Hegy test statistics:\nF34|F56|F78|F910|F1112|F1_12|F2_12 or F34|F234|F1234");
	public static final JSCTypeDef HEGY_CRITVAL =
		new JSCTypeDef(
			"HEGY_CRITVAL",
			JSCTypes.NARRAY,
			"Hegy test: critical values: 1% 5% 10% according to statistics");

	private int lags = 0;
	private JSCNArray y = null;
	private JSCString yName = null;
	private TSDateRange range = null;
	private int testState = 0;

	private JSCNArray hegy_est = null;
	private JSCNArray hegy_stats = null;
	private JSCNArray hegy_crit = null;
	private JSCNumber hegy_sigma = null;

	/**
	 * <code>HegyTestCall</code> constructor takes the arguments for the procedure call.
	 *
	 * @param testState
	 * <ul>
	 * <li>1 -- no intercept,no dummies, notimetrend
	 * <li>2 -- intercept,no dummies, notimetrend
	 * <li>3 -- intercept, no dummies, time trend
	 * <li>4 -- intercept,dummies, timetrend
	 * <li>5 -- intercept,dummies, no timetrend
	 * </ul>
	 */
	public HegyTestCall(
		JSCNArray y,
		JSCString yName,
		TSDateRange range,
		int lags,
		int testState) {

		super();

		setName("HEGY Test");

		this.lags = lags;
		this.range = range;
		this.y = y;
		this.yName = yName;
		this.testState = testState;

	}

	/**
	 * UStrings HEGY test results.
	 */
	private void addRegressionResults(int testState) {
		StringBuffer buffer = output;

		//HEGY_EST, HEGY_SIGMA, HEGY_RESIDS, HEGY_STAT, HEGY_CRITVAL
		double[][] estimates = hegy_est.doubleArray();
		double[][] testStats = hegy_stats.doubleArray();
		double[][] critVals = hegy_crit.doubleArray();
		double sigma = hegy_sigma.doubleVal();

		String critNames[] = null;
		int period = range.lowerBound().subPeriodicity();
		if (period == 4)
			critNames =
				new String[] { "t(pi1)", "t(pi2)", "F34", "F234", "F1234" };
		if (period == 12)
			critNames =
				new String[] {
					"t(pi1)",
					"t(pi2)",
					"F34",
					"F56",
					"F78",
					"F910",
					"F1112",
					"F1-12",
					"F2-12" };

		//b and t-stats b
		buffer.append("regression results:\n");
		buffer.append("---------------------------------------\n");
		buffer.append(
			FArg.sprintf(
				"%-13s %-13s %-13s\n",
				new FArg("variable").add("coefficient").add("t-statistic")));
		buffer.append("---------------------------------------\n");
		int start = 0;
		switch (testState) {
			case 1 :
				start = 0;
				break;
			case 2 :
				start = 1;
				buffer.append(
					FArg.sprintf(
						"%-13s %- 13.4f %- 13.4f\n",
						new FArg("const").add(estimates[0][0]).add(
							estimates[0][1])));
				break;
			case 3 :
				start = 2;
				buffer.append(
					FArg.sprintf(
						"%-13s %- 13.4f %- 13.4f\n",
						new FArg("const").add(estimates[0][0]).add(
							estimates[0][1])));
				buffer.append(
					FArg.sprintf(
						"%-13s %- 13.4f %- 13.4f\n",
						new FArg("trend").add(estimates[1][0]).add(
							estimates[1][1])));
				break;
			case 4 :
				start = 5;
				for (int a = 0; a < period; a++)
					buffer.append(
						FArg.sprintf(
							"%-13s %- 13.4f %- 13.4f\n",
							new FArg("SD" + (a + 1)).add(estimates[a][0]).add(
								estimates[a][1])));
				buffer.append(
					FArg.sprintf(
						"%-13s %- 13.4f %- 13.4f\n",
						new FArg("trend").add(estimates[4][0]).add(
							estimates[4][1])));
				break;
			case 5 :
				start = 4;
				for (int a = 0; a < period; a++)
					buffer.append(
						FArg.sprintf(
							"%-13s %- 13.4f %- 13.4f\n",
							new FArg("SD" + (a + 1)).add(estimates[a][0]).add(
								estimates[a][1])));
				break;
			default :
				start = 0;
		}
		int countStart = start;
		double tpi[] = new double[2];
		tpi[0] = estimates[start][1];
		tpi[1] = estimates[start + 1][1];
		for (int a = start; a < estimates.length - lags; a++) {
			buffer.append(
				FArg.sprintf(
					"%-13s %- 13.4f %- 13.4f\n",
					new FArg("pi" + (a - countStart + 1)).add(
						estimates[a][0]).add(
						estimates[a][1])));
			start = a;
		}
		for (int a = start + 1; a < estimates.length; a++)
			buffer.append(
				FArg.sprintf(
					"%-13s %- 13.4f %- 13.4f\n",
					new FArg("AR" + (a - start)).add(estimates[a][0]).add(
						estimates[a][1])));
		buffer.append(
			FArg.sprintf("%-13s % .4f\n", new FArg("sigma:").add(sigma)));

		buffer.append(
			"\ncritical values\nreference: P.H. Franses and B. Hobijn (1997)\nNumbers from all the tables in\n\"Critical values for unit root tests in seasonal time series\",\nJournal of Applied Statistics 24: 25-46\nTaylor & Francis Ltd.,www.tandf.co.uk/journals\n");
		buffer.append("---------------------------------------\n");
		buffer.append(
			FArg.sprintf(
				"%-10s %-10s %-10s %-10s \n",
				new FArg("statistic").add(" 1%").add(" 5%").add(" 10%")));
		for (int i = 0; i < critVals.length; i++)
			buffer.append(
				FArg.sprintf(
					"%-10s %- 10.2f %- 10.2f %- 10.2f\n",
					new FArg(critNames[i]).add(critVals[i][0]).add(
						critVals[i][1]).add(
						critVals[i][2])));
		buffer.append("\nvalues of test statistics: \n");
		for (int i = 0; i < testStats.length + 2; i++)
			if (i < 2)
				buffer.append(
					FArg.sprintf(
						"%-15s % .4f\n",
						new FArg(critNames[i] + ":").add(tpi[i])));
			else
				buffer.append(
					FArg.sprintf(
						"%-15s % .4f\n",
						new FArg(critNames[i] + ":").add(testStats[i - 2][0])));

	}

	/**
	 * Writes the text output to a string that can be
	 * referenced via <code>getOutput</code>.
	 */
	protected void finalCode() {

		StringBuffer buffer = output;
		buffer.append(
			FArg.sprintf(
				"%-25s %s \n",
				new FArg("HEGY Test for series:").add(yName.string())));
		buffer.append(
			range.addPeriodsToStart(
				lags + range.lowerBound().subPeriodicity()).format(
				"sample range:",
				26)
				+ "\n");
		buffer.append(
			FArg.sprintf(
				"%-25s %i \n",
				new FArg("lagged differences:").add(lags)));

		if (testState == 1)
			buffer.append("no intercept, no time trend\n");

		if (testState == 2)
			buffer.append("intercept, no time trend\n");

		if (testState == 3)
			buffer.append("intercept, time trend\n");

		if (testState == 4)
			buffer.append("intercept, time trend, seasonal dummies\n");

		if (testState == 5)
			buffer.append("intercept, seasonal dummies, no time trend\n");

		// Add Results.
		addRegressionResults(testState);
		buffer.append("\n");
	}

	/**
	 * @see RPCall
	 */
	protected void runCode() {

		if (getSymbolTable() != null){
			getSymbolTable().get(HEGY_RESIDS).clear();
			getSymbolTable().get(HEGY_EST).clear();
			getSymbolTable().get(HEGY_CRITVAL).clear();
			getSymbolTable().get(HEGY_STAT).clear();
			getSymbolTable().get(HEGY_SIGMA).clear();
		}

		int period = range.lowerBound().subPeriodicity();
		if (y.rows() < 2 * (period + lags))
			throw new RuntimeException(
				"Not enough observations in sample " + range + ".");

		engine().load("ur", GaussLoadTypes.LIB);

		hegy_est = (JSCNArray) HEGY_EST.getInstance();
		hegy_crit = (JSCNArray) HEGY_CRITVAL.getInstance();
		hegy_stats = (JSCNArray) HEGY_STAT.getInstance();
		JSCNArray hegy_resids = (JSCNArray) HEGY_RESIDS.getInstance();
		hegy_sigma = (JSCNumber) HEGY_SIGMA.getInstance();

		String procName = "hegy4_hegy";
		if (period == 12)
			procName = "hegy12_hegy";

		if (period != 12 && period != 4)
			throw new RuntimeException(
				"HEGY test not implemented for periodicity " + period + ".");

		engine().call(
			procName,
			new JSCData[] {
				y,
				new JSCInt("lags", lags),
				new JSCInt("teststate", testState)},
			new JSCData[] {
				new JSCInt("obs"),
				hegy_est,
				hegy_sigma,
				hegy_resids,
				hegy_stats,
				hegy_crit });

		if (getSymbolTable() != null) {
			getSymbolTable().set(hegy_est);
			getSymbolTable().set(hegy_crit);
			getSymbolTable().set(hegy_stats);
			getSymbolTable().set(hegy_resids);
			getSymbolTable().set(hegy_sigma);
		}
	}
}