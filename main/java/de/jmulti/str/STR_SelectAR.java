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

package de.jmulti.str;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import com.jstatcom.component.NumSelector;
import com.jstatcom.model.JSCDRange;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.JSCSArray;
import com.jstatcom.model.ModelPanel;
import com.jstatcom.model.SymbolEvent;
import com.jstatcom.model.SymbolListener;
import com.jstatcom.ts.TSDateRange;
import com.jstatcom.ts.TSSel;
import com.jstatcom.util.UData;
import com.jstatcom.util.UMatrix;


/**
 * Model select panel and data creation in STR analysis.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */

final class STR_SelectAR extends ModelPanel {

    private static final Logger log = Logger.getLogger(STR_SelectAR.class);

    private boolean recompute = true;

    private JPanel ivjControlPanel = null;

    private TSSel ivjSelector = null;

    private JLabel ivjJLabel1 = null;

    private JLabel ivjJLabel11 = null;

    private NumSelector ivjPz = null;

    private JCheckBox ivjSeasonalDummies = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private NumSelector ivjPy = null;

    class IvjEventHandler implements java.awt.event.ComponentListener,
            java.awt.event.ItemListener {

        public void componentHidden(java.awt.event.ComponentEvent e) {
            if (e.getSource() == STR_SelectAR.this)
                connEtoC1(e);
        };

        public void componentMoved(java.awt.event.ComponentEvent e) {
        };

        public void componentResized(java.awt.event.ComponentEvent e) {
        };

        public void componentShown(java.awt.event.ComponentEvent e) {
        };

        public void itemStateChanged(java.awt.event.ItemEvent e) {
            if (e.getSource() == STR_SelectAR.this.getSeasonalDummies())
                connEtoC2(e);
        };
    };

    /**
     * SelectAR constructor comment.
     */
    public STR_SelectAR() {
        super();
        initialize();
    }

    /**
     * Comment
     */
    private void createNamesThet0() {
        if (!recompute)
            return;

        // Merge Restrictions.
        JSCNArray allRestrictions = new JSCNArray(STR_Constants.STR_allRes.name);
        allRestrictions.appendCols(global().get(STR_Constants.STR_Cy)
                .getJSCNArray());
        allRestrictions.appendCols(global().get(STR_Constants.STR_Cx)
                .getJSCNArray());
        allRestrictions.appendCols(new JSCNArray("append", 0));
        global().get(STR_Constants.STR_allRes).setJSCData(allRestrictions);

        // Count number of variables for names array.
        int m = 0;
        for (int i = 0; i < allRestrictions.cols(); i++)
            m += allRestrictions.intAt(0, i);

        // Build variable names that are possibly constant
        JSCSArray nd = global().get(STR_Constants.STR_Nd).getJSCSArray();
        String[] constnames = new String[m + nd.rows() + 1];

        int k = 0;
        // start with CONST
        constnames[k++] = STR_Constants.CONST;

        // then deterministics
        for (int i = 0; i < nd.rows(); i++)
            constnames[k++] = nd.stringAt(i, 0);

        // get the rest of the variables
        JSCSArray str_transnames = global().get(STR_Constants.STR_transNames)
                .getJSCSArray();

        for (int i = 0; i < allRestrictions.cols(); i++)
            if (allRestrictions.intAt(0, i) > 0) {
                if (i < str_transnames.rows())
                    constnames[k++] = str_transnames.stringAt(i, 0);
            }
        // For nonlintest exclude CONST
        String[] constnameslintest = new String[constnames.length - 1];
        for (int i = 0; i < constnames.length - 1; i++)
            constnameslintest[i] = constnames[i + 1];

        // all variables that are possibly constant
        global().get(STR_Constants.STR_thet0Names).setJSCData(
                new JSCSArray("constnames", constnames));

        global().get(STR_Constants.STR_thet0Names_noConst).setJSCData(
                new JSCSArray("constnameslintest", constnameslintest));

        recompute = false;

    }

    /**
     * connEtoC1:
     * (SelectAR.component.componentShown(java.awt.event.ComponentEvent) -->
     * SelectAR.selectAR_ComponentShown(Ljava.awt.event.ComponentEvent;)V)
     * 
     * @param arg1
     *            java.awt.event.ComponentEvent
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC1(java.awt.event.ComponentEvent arg1) {
        try {
            // user code begin {1}
            // user code end
            this.selectAR_ComponentHidden();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC2:
     * (SeasonalDummies.item.itemStateChanged(java.awt.event.ItemEvent) -->
     * SelectAR.seasonalDummies_ItemStateChanged()V)
     * 
     * @param arg1
     *            java.awt.event.ItemEvent
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC2(java.awt.event.ItemEvent arg1) {
        try {
            // user code begin {1}
            // user code end
            this.seasonalDummies_ItemStateChanged();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * Creates all data objects needed for specification of an STR model.
     */
    private void createModelData() {
        if (!recompute)
            return;

        global().get(STR_Constants.STR_D).clear();
        global().get(STR_Constants.STR_Y).clear();
        global().get(STR_Constants.STR_X).clear();

        global().get(STR_Constants.STR_detData).setJSCData(
                global().get(STR_Constants.STR_detData_raw).getJSCData());

        // Set the CONST data object
        global().get(STR_Constants.STR_CONSTNAME).setJSCData(
                new JSCSArray("toset", STR_Constants.CONST));

        global().get(STR_Constants.STR_Nd).setJSCData(
                global().get(STR_Constants.STR_Nd_raw).getJSCData());

        int py = global().get(STR_Constants.STR_py).getJSCInt().intVal();
        int pz = global().get(STR_Constants.STR_pz).getJSCInt().intVal();

        TSDateRange range = global().get(STR_Constants.STR_drange)
                .getJSCDRange().getTSDateRange();

        int lags = Math.max(py, pz);
        TSDateRange T1 = range.addPeriodsToStart(lags);

        global().get(STR_Constants.STR_T1).setJSCData(
                new JSCDRange("toset", T1));

        if (getSeasonalDummies().isSelected()) {
            JSCNArray dummies = new JSCNArray("dummies", range.createSeasDum(
                    false, false));

            // Append dummies to det data.
            global().get(STR_Constants.STR_detData).getJSCNArray().appendCols(
                    dummies);
            for (int i = 0; i < dummies.cols(); i++)
                global().get(STR_Constants.STR_Nd).getJSCSArray().appendRows(
                        new JSCSArray("toset", "S" + (i + 1)));

        }
        // create the names of possible transition variables
        JSCSArray nx = global().get(STR_Constants.STR_Nx).getJSCSArray();
        String[] xnames = new String[py + nx.rows() * (pz + 1)];
        int j = 0;

        // append lagged Ys
        for (int i = 0; i < py; i++)
            xnames[j++] = global().get(STR_Constants.STR_Ny).getJSCSArray()
                    .stringAt(0, 0)
                    + "(t-" + (i + 1) + ")";
        // append lagged Xs
        for (int k = 0; k < (pz + 1); k++)
            for (int i = 0; i < nx.rows(); i++) {
                if (k > 0)
                    xnames[j++] = nx.stringAt(i, 0) + "(t-" + k + ")";
                else
                    xnames[j++] = nx.stringAt(i, 0) + "(t)";
            }

        // all possible transition variables without trend
        global().get(STR_Constants.STR_transNames_noTrend).setJSCData(
                new JSCSArray("xnames", xnames));

        JSCSArray toSet = new JSCSArray(STR_Constants.STR_transNames.name,
                xnames);
        // all possible transition variables with trend
        toSet.appendRows(new JSCSArray("trend", STR_Constants.TREND));
        global().get(STR_Constants.STR_transNames).setJSCData(toSet);

        // Create subset matrizes
        global().get(STR_Constants.STR_Cy).setJSCData(
                new JSCNArray("cy", UMatrix.ones(1, py)));

        global().get(STR_Constants.STR_Cx).setJSCData(
                new JSCNArray("cy", UMatrix.ones(1, nx.rows() * (pz + 1))));

        // create the X matrix
        JSCNArray[] yz = UData.createYZMat(global().get(
                STR_Constants.STR_detData).getJSCNArray(), global().get(
                STR_Constants.STR_endData).getJSCNArray(), global().get(
                STR_Constants.STR_exData).getJSCNArray(), py, pz,
                STR_Constants.STR_Y.name, STR_Constants.STR_X.name);

        if (yz[1].cols() > 0) {
            double[][] trend_transposed = UMatrix.transpose(UMatrix
                    .toDoubleMatrix(UMatrix.seqa(1, 1, yz[1].cols())));
            yz[1].appendRows(new JSCNArray("trend", trend_transposed));

        } else { // here we have just a const for a smooth trend!
            double[][] trend_transposed = UMatrix.transpose(UMatrix
                    .toDoubleMatrix(UMatrix.seqa(1, 1, global().get(
                            STR_Constants.STR_endData).getJSCNArray().rows())));
            global().get(STR_Constants.STR_X).setJSCData(
                    new JSCNArray("trend", trend_transposed));
        }

        global().get(STR_Constants.STR_Y).setJSCData(yz[0]);

        // Set deterministics
        JSCNArray detData = global().get(STR_Constants.STR_detData)
                .getJSCNArray();
        if (detData.cols() > 0)
            global().get(STR_Constants.STR_D).setJSCData(
                    new JSCNArray(STR_Constants.STR_D.name, yz[1].getRows(0,
                            detData.cols() - 1)));

        // Set X without deterministics
        if (yz[1].rows() > 0)
            global().get(STR_Constants.STR_X).setJSCData(
                    new JSCNArray(STR_Constants.STR_X.name, yz[1].getRows(
                            detData.cols(), yz[1].rows() - 1)));

        createNamesThet0();
        recompute = false;
    }

    /**
     * Return the ControlPanel property value.
     * 
     * @return javax.swing.JPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JPanel getControlPanel() {
        if (ivjControlPanel == null) {
            try {
                ivjControlPanel = new javax.swing.JPanel();
                ivjControlPanel.setName("ControlPanel");
                ivjControlPanel.setLayout(new java.awt.GridBagLayout());

                java.awt.GridBagConstraints constraintsPy = new java.awt.GridBagConstraints();
                constraintsPy.gridx = 1;
                constraintsPy.gridy = 0;
                constraintsPy.anchor = java.awt.GridBagConstraints.WEST;
                constraintsPy.weightx = 1.0;
                constraintsPy.insets = new java.awt.Insets(50, 10, 0, 0);
                getControlPanel().add(getPy(), constraintsPy);

                java.awt.GridBagConstraints constraintsJLabel1 = new java.awt.GridBagConstraints();
                constraintsJLabel1.gridx = 0;
                constraintsJLabel1.gridy = 0;
                constraintsJLabel1.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel1.anchor = java.awt.GridBagConstraints.SOUTHWEST;
                constraintsJLabel1.insets = new java.awt.Insets(50, 20, 0, 0);
                getControlPanel().add(getJLabel1(), constraintsJLabel1);

                java.awt.GridBagConstraints constraintsJLabel11 = new java.awt.GridBagConstraints();
                constraintsJLabel11.gridx = 0;
                constraintsJLabel11.gridy = 1;
                constraintsJLabel11.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel11.anchor = java.awt.GridBagConstraints.WEST;
                constraintsJLabel11.insets = new java.awt.Insets(10, 20, 0, 0);
                getControlPanel().add(getJLabel11(), constraintsJLabel11);

                java.awt.GridBagConstraints constraintsPz = new java.awt.GridBagConstraints();
                constraintsPz.gridx = 1;
                constraintsPz.gridy = 1;
                constraintsPz.anchor = java.awt.GridBagConstraints.WEST;
                constraintsPz.weightx = 1.0;
                constraintsPz.insets = new java.awt.Insets(10, 10, 0, 0);
                getControlPanel().add(getPz(), constraintsPz);

                java.awt.GridBagConstraints constraintsSeasonalDummies = new java.awt.GridBagConstraints();
                constraintsSeasonalDummies.gridx = 0;
                constraintsSeasonalDummies.gridy = 2;
                constraintsSeasonalDummies.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsSeasonalDummies.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsSeasonalDummies.insets = new java.awt.Insets(20, 20,
                        0, 0);
                constraintsSeasonalDummies.weighty = 1.0D;
                ivjControlPanel.add(getSeasonalDummies(),
                        constraintsSeasonalDummies);
                TitledBorder title = new TitledBorder(new BevelBorder(
                        BevelBorder.LOWERED), "Specify Linear AR Part",
                        TitledBorder.RIGHT, TitledBorder.TOP);
                getControlPanel().setBorder(title);

                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjControlPanel;
    }

    /**
     * Return the JLabel1 property value.
     * 
     * @return javax.swing.JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JLabel getJLabel1() {
        if (ivjJLabel1 == null) {
            try {
                ivjJLabel1 = new javax.swing.JLabel();
                ivjJLabel1.setName("JLabel1");
                ivjJLabel1.setPreferredSize(new java.awt.Dimension(101, 21));
                ivjJLabel1.setText("Lags y (maximum)");
                ivjJLabel1.setMaximumSize(new java.awt.Dimension(101, 21));
                ivjJLabel1.setMinimumSize(new java.awt.Dimension(101, 21));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJLabel1;
    }

    /**
     * Return the JLabel11 property value.
     * 
     * @return javax.swing.JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JLabel getJLabel11() {
        if (ivjJLabel11 == null) {
            try {
                ivjJLabel11 = new javax.swing.JLabel();
                ivjJLabel11.setName("JLabel11");
                ivjJLabel11.setPreferredSize(new java.awt.Dimension(101, 21));
                ivjJLabel11.setText("Lags x (maximum)");
                ivjJLabel11.setMinimumSize(new java.awt.Dimension(101, 21));
                ivjJLabel11.setMaximumSize(new java.awt.Dimension(101, 21));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJLabel11;
    }

    private NumSelector getPy() {
        if (ivjPy == null) {
            try {
                ivjPy = new NumSelector();
                ivjPy.setNumber(2.0);
                ivjPy.setMaximumSize(new java.awt.Dimension(55, 21));
                ivjPy.setRangeExpr("[0,20]");
                ivjPy.setSymbolName(STR_Constants.STR_py.name);
                ivjPy.setPreferredSize(new java.awt.Dimension(55, 21));
                ivjPy.setIntType(true);
                ivjPy.setEnabled(false);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjPy;
    }

    private NumSelector getPz() {
        if (ivjPz == null) {
            try {
                ivjPz = new NumSelector();
                ivjPz.setMaximumSize(new java.awt.Dimension(55, 21));
                ivjPz.setRangeExpr("[0,20]");
                ivjPz.setSymbolName(STR_Constants.STR_pz.name);
                ivjPz.setPreferredSize(new java.awt.Dimension(55, 21));
                ivjPz.setIntType(true);
                ivjPz.setEnabled(false);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjPz;
    }

    /**
     * Return the SeasonalDummies property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getSeasonalDummies() {
        if (ivjSeasonalDummies == null) {
            try {
                ivjSeasonalDummies = new javax.swing.JCheckBox();
                ivjSeasonalDummies.setName("SeasonalDummies");
                ivjSeasonalDummies.setText("Add seasonal dummies");
                ivjSeasonalDummies.setEnabled(false);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSeasonalDummies;
    }

    TSSel getSelector() {
        if (ivjSelector == null) {
            try {
                ivjSelector = new TSSel();
                ivjSelector.setName("Selector");
                ivjSelector.setDateRangeName(STR_Constants.STR_drange.name);
                ivjSelector.setEndogenousStringsName(STR_Constants.STR_Ny.name);
                ivjSelector.setExogenousDataName(STR_Constants.STR_exData.name);
                ivjSelector
                        .setDeterministicStringsName(STR_Constants.STR_Nd_raw.name);
                ivjSelector
                        .setDeterministicDataName(STR_Constants.STR_detData_raw.name);
                ivjSelector.setOneEndogenousOnly(true);
                ivjSelector
                        .setEndogenousDataName(STR_Constants.STR_endData.name);
                ivjSelector.setExogenousStringsName(STR_Constants.STR_Nx.name);
                // user code begin {1}
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
     * Called whenever the part throws an exception.
     * 
     * @param exception
     *            Exception
     */
    private void handleException(Throwable exception) {

        log.error("Unhandled Exception", exception);
    }

    /**
     * Initializes connections
     * 
     * @exception Exception
     *                The exception description.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initConnections() throws Exception {
        // user code begin {1}
        // user code end
        this.addComponentListener(ivjEventHandler);
        getSeasonalDummies().addItemListener(ivjEventHandler);
    }

    /**
     * Initialize the class.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initialize() {
        try {
            // user code begin {1}
            // user code end
            setName("SelectAR");
            setLayout(new java.awt.BorderLayout());
            setSize(570, 394);
            add(getSelector(), "East");
            add(getControlPanel(), "Center");
            initConnections();
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}

        // selector changed, clear, enable and set recompute,
        // can only happen from within this panel
        getSelector().addPropertyChangeListener("selectionChanged",
                new PropertyChangeListener() {

                    public void propertyChange(PropertyChangeEvent arg0) {
                        recompute = true;
                        boolean endEmpy = global().get(
                                STR_Constants.STR_endData).isEmpty();
                        getPy().setEnabled(!endEmpy);
                        getSeasonalDummies().setEnabled(!endEmpy);
                        getPz().setEnabled(
                                !global().get(STR_Constants.STR_exData)
                                        .isEmpty());
                        global().get(STR_Constants.STR_RTZ).clear();

                    }
                });

        // listens to changes in restrictions, creates names
        // will happen from outside this panel
        global().get(STR_Constants.STR_Cy).addSymbolListener(
                new SymbolListener() {

                    public void valueChanged(SymbolEvent evt) {
                        recompute = true;
                        global().get(STR_Constants.STR_RTZ).clear();
                        createNamesThet0();
                    }
                });

        // listens to changes in restrictions, creates names
        // will happen from outside this panel
        global().get(STR_Constants.STR_Cx).addSymbolListener(
                new SymbolListener() {

                    public void valueChanged(SymbolEvent evt) {
                        recompute = true;
                        global().get(STR_Constants.STR_RTZ).clear();
                        createNamesThet0();
                    }
                });

        // lags changed, can only happen from this panel
        // data will be recomputed on hide
        global().get(STR_Constants.STR_py).addSymbolListener(
                new SymbolListener() {

                    public void valueChanged(SymbolEvent evt) {
                        recompute = true;
                        global().get(STR_Constants.STR_RTZ).clear();
                    }
                });

        // lags changed, can only happen from this panel
        // data will be recomputed on hide
        global().get(STR_Constants.STR_pz).addSymbolListener(
                new SymbolListener() {

                    public void valueChanged(SymbolEvent evt) {
                        recompute = true;
                        global().get(STR_Constants.STR_RTZ).clear();
                    }
                });
        // user code end
    }

    /**
     * Comment
     */
    private void seasonalDummies_ItemStateChanged() {
        recompute = true;
    }

    /**
     * Comment
     */
    private void selectAR_ComponentHidden() {
        createModelData();
    }

    boolean isSeasSelected() {
        return getSeasonalDummies().isSelected();
    }

    void setSeasSelected(boolean isSel) {
        getSeasonalDummies().setSelected(isSel);
    }
}