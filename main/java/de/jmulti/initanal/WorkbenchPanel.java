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

package de.jmulti.initanal;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.apache.log4j.Logger;

import com.jstatcom.model.JSCTypeDef;
import com.jstatcom.model.JSCTypes;
import com.jstatcom.ts.TSSel;

import de.jmulti.tools.KernDensPanel;
import de.jmulti.tools.SpectrumPanel;

/**
 * The workbench contains various panels that provide
 * functionality to investigate basic time series properties. 
 *
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
final class WorkbenchPanel extends JPanel {
    private static final Logger log = Logger.getLogger(WorkbenchPanel.class);
	static final JSCTypeDef WB_DRANGE =
		new JSCTypeDef(
			"WB_DRANGE",
			JSCTypes.DRANGE,
			"date range for workbench");
	static final JSCTypeDef WB_DATA =
		new JSCTypeDef("WB_DATA", JSCTypes.NARRAY, "data for workbench");
	static final JSCTypeDef WB_DATANAMES =
		new JSCTypeDef(
			"WB_DATANAMES",
			JSCTypes.SARRAY,
			"names of series for workbench");
	private JTabbedPane ivjJTabbedPane1 = null;
	private AutocorrPanel ivjAutocorrPanel = null;
	private PlotPanel ivjPlotPanel = null;
	private StatsPanel ivjStatsPanel = null;
	private CrossPlotPanel ivjCrossPlotPanel = null;
	private SpectrumPanel ivjSpectrumPanel = null;
	private HPFilterPanel ivjHPFiltPanel = null;
	private KernDensPanel ivjKernelDensity = null;
	private TSSel ivjSelector = null;

	/**
	 * UIViewData constructor comment.
	 */
	public WorkbenchPanel() {
		super();
		initialize();
	}

	/**
	 * Return the AutocorrPanel property value.
	 * @return AutocorrPanel
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private AutocorrPanel getAutocorrPanel() {
		if (ivjAutocorrPanel == null) {
			try {
				ivjAutocorrPanel = new de.jmulti.initanal.AutocorrPanel();
				ivjAutocorrPanel.setName("AutocorrPanel");
				// user code begin {1}
				// user code end
			} catch (Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjAutocorrPanel;
	}

	/**
	 * Return the CrossPlotPanel property value.
	 * @return CrossPlotPanel
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private CrossPlotPanel getCrossPlotPanel() {
		if (ivjCrossPlotPanel == null) {
			try {
				ivjCrossPlotPanel = new de.jmulti.initanal.CrossPlotPanel();
				ivjCrossPlotPanel.setName("CrossPlotPanel");
				// user code begin {1}
				// user code end
			} catch (Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjCrossPlotPanel;
	}

	/**
	 * Return the HPFilterPanel property value.
	 * @return HPFilterPanel
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private HPFilterPanel getHPFiltPanel() {
		if (ivjHPFiltPanel == null) {
			try {
				ivjHPFiltPanel = new de.jmulti.initanal.HPFilterPanel();
				ivjHPFiltPanel.setName("HPFiltPanel");
				// user code begin {1}
				// user code end
			} catch (Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjHPFiltPanel;
	}

	/**
	 * Return the JTabbedPane1 property value.
	 * @return javax.swing.JTabbedPane
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private javax.swing.JTabbedPane getJTabbedPane1() {
		if (ivjJTabbedPane1 == null) {
			try {
				ivjJTabbedPane1 = new javax.swing.JTabbedPane();
				ivjJTabbedPane1.setName("JTabbedPane1");
				ivjJTabbedPane1.insertTab(
					"Plot",
					null,
					getPlotPanel(),
					null,
					0);
				ivjJTabbedPane1.insertTab(
					"Tests/Stats",
					null,
					getStatsPanel(),
					null,
					1);
				ivjJTabbedPane1.insertTab(
					"Autocorr.",
					null,
					getAutocorrPanel(),
					null,
					2);
				ivjJTabbedPane1.insertTab(
					"Spectrum",
					null,
					getSpectrumPanel(),
					null,
					3);
				ivjJTabbedPane1.insertTab(
					"Kernel Density",
					null,
					getKernelDensity(),
					null,
					4);
				ivjJTabbedPane1.insertTab(
					"Filter",
					null,
					getHPFiltPanel(),
					null,
					5);
				ivjJTabbedPane1.insertTab(
					"Crossplots",
					null,
					getCrossPlotPanel(),
					null,
					6);
				// user code begin {1}
				// user code end
			} catch (Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjJTabbedPane1;
	}

	/**
	 * Return the KernelDensity property value.
	 * @return de.jmulti.tools.KernDensPanel
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private de.jmulti.tools.KernDensPanel getKernelDensity() {
		if (ivjKernelDensity == null) {
			try {
				ivjKernelDensity = new de.jmulti.tools.KernDensPanel();
				ivjKernelDensity.setName("KernelDensity");
				// user code begin {1}
				ivjKernelDensity.setResids(WB_DATA.name);
				ivjKernelDensity.setNameResids(WB_DATANAMES.name);
				ivjKernelDensity.setDRange(WB_DRANGE.name);

				// user code end
			} catch (Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjKernelDensity;
	}

	/**
	 * Return the PlotPanel property value.
	 * @return PlotPanel
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private PlotPanel getPlotPanel() {
		if (ivjPlotPanel == null) {
			try {
				ivjPlotPanel = new de.jmulti.initanal.PlotPanel();
				ivjPlotPanel.setName("PlotPanel");
				// user code begin {1}
				// user code end
			} catch (Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjPlotPanel;
	}

	/**
	 * Return the PlotSelector property value.
	 * @return TSSelector
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private TSSel getSelector() {
		if (ivjSelector == null) {
			try {
				ivjSelector = new TSSel();
				ivjSelector.setName("Selector");
				// user code begin {1}
				ivjSelector.setAllDataName(WB_DATA.name);
				ivjSelector.setDateRangeName(WB_DRANGE.name);
				ivjSelector.setAllStringsName(WB_DATANAMES.name);

				// user code end
			} catch (Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjSelector;
	}

	/**
	 * Return the SpectrumPanel property value.
	 * @return de.jmulti.tools.SpectrumPanel
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private de.jmulti.tools.SpectrumPanel getSpectrumPanel() {
		if (ivjSpectrumPanel == null) {
			try {
				ivjSpectrumPanel = new de.jmulti.tools.SpectrumPanel();
				ivjSpectrumPanel.setName("SpectrumPanel");
				// user code begin {1}
				ivjSpectrumPanel.setResids(WB_DATA.name);
				ivjSpectrumPanel.setNameResids(WB_DATANAMES.name);
				ivjSpectrumPanel.setDRange(WB_DRANGE.name);

				// user code end
			} catch (Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjSpectrumPanel;
	}

	/**
	 * Return the StatsPanel property value.
	 * @return StatsPanel
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private StatsPanel getStatsPanel() {
		if (ivjStatsPanel == null) {
			try {
				ivjStatsPanel = new de.jmulti.initanal.StatsPanel();
				ivjStatsPanel.setName("StatsPanel");
				// user code begin {1}
				// user code end
			} catch (Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return ivjStatsPanel;
	}

	/**
	 * Called whenever the part throws an exception.
	 * @param exception Exception
	 */
	private void handleException(Throwable exception) {

        log.error("Unhandled Exception", exception);
	}

	/**
	 * Initialize the class.
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private void initialize() {
		try {
			// user code begin {1}
			// user code end
			setName("WorkBenchPanel");
			setPreferredSize(new java.awt.Dimension(700, 400));
			setLayout(new java.awt.GridBagLayout());
			setSize(700, 400);
			setMinimumSize(new java.awt.Dimension(700, 400));

			java.awt.GridBagConstraints constraintsSelector =
				new java.awt.GridBagConstraints();
			constraintsSelector.gridx = 1;
			constraintsSelector.gridy = 0;
			constraintsSelector.fill = java.awt.GridBagConstraints.BOTH;
			constraintsSelector.weighty = 1.0;
			add(getSelector(), constraintsSelector);

			java.awt.GridBagConstraints constraintsJTabbedPane1 =
				new java.awt.GridBagConstraints();
			constraintsJTabbedPane1.gridx = 0;
			constraintsJTabbedPane1.gridy = 0;
			constraintsJTabbedPane1.fill = java.awt.GridBagConstraints.BOTH;
			constraintsJTabbedPane1.weightx = 1.0;
			constraintsJTabbedPane1.weighty = 1.0;
			add(getJTabbedPane1(), constraintsJTabbedPane1);
		} catch (Throwable ivjExc) {
			handleException(ivjExc);
		}
		// user code begin {2}
	
		// user code end
	}
}