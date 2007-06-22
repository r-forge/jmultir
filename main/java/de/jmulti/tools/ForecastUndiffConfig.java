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

package de.jmulti.tools;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.BevelBorder;

import org.apache.log4j.Logger;

import com.jstatcom.component.StdMessages;
import com.jstatcom.model.JSCDRange;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.JSCSArray;
import com.jstatcom.model.JSCTypeDef;
import com.jstatcom.model.JSCTypes;
import com.jstatcom.model.ModelPanel;
import com.jstatcom.model.SymbolTable;
import com.jstatcom.table.JSCDataTableScrollPane;
import com.jstatcom.table.JSCNArrayTable;
import com.jstatcom.ts.TSDateRange;
import com.jstatcom.ts.TSSel;

/**
 * A dialog for configuring the undifferenced forecast from the 1st differences.
 * The user can add the levels of the variables for time t.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
final class ForecastUndiffConfig extends JDialog {
    private static final Logger log = Logger
            .getLogger(ForecastUndiffConfig.class);

    private JSCTypeDef selNamesDef = new JSCTypeDef("selNames",
            JSCTypes.SARRAY, "selected names for undifferenced forecast");

    private JSCTypeDef selDRangeDef = new JSCTypeDef("selDRange",
            JSCTypes.DRANGE, "selected date range undifferenced forecast");

    private JSCTypeDef selDataDef = new JSCTypeDef("selDataDef",
            JSCTypes.NARRAY, "selected series undifferenced forecast");

    private final JSCNArray ytLevels = new JSCNArray("ytLevels_forecundiff");

    private JSCNArray selYLevels = new JSCNArray("selytLevels_forecundiff");

    private TSDateRange origRange = null;

    private ModelPanel modelPanel = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private JPanel ivjJPanel1 = null;

    private JPanel ivjJPanel2 = null;

    private JButton ivjOK = null;

    private TSSel ivjSelectPanel = null;

    private JPanel ivjJPanel3 = null;

    private JTextPane ivjJTextPane1 = null;

    private JSCDataTableScrollPane ivjDataTableScrollPane1 = null;

    private JSCNArrayTable ivjScrollPaneTable = null;

    class IvjEventHandler implements java.awt.event.ActionListener,
            java.beans.PropertyChangeListener {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == ForecastUndiffConfig.this.getOK())
                connEtoC1();
        };

        public void propertyChange(java.beans.PropertyChangeEvent evt) {
            if (evt.getSource() == ForecastUndiffConfig.this.getSelectPanel()
                    && (evt.getPropertyName().equals("selectionChanged")))
                connEtoC2(evt);
        };
    };

    /**
     * ForecastUndiffConfig constructor comment.
     */
    public ForecastUndiffConfig() {
        super();
        initialize();
    }

    /**
     * ForecastUndiffConfig constructor comment.
     * 
     * @param owner
     *            java.awt.Frame
     * @param modal
     *            boolean
     */
    public ForecastUndiffConfig(java.awt.Frame owner, boolean modal) {
        super(owner, modal);
        initialize();
    }

    /**
     * connEtoC1: (OK.action. --> ForecastUndiffConfig.oK_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC1() {
        try {
            // user code begin {1}
            // user code end
            this.oK_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC2: (SelectPanel.selectionChanged -->
     * ForecastUndiffConfig.selectPanel_SelectionChanged()V)
     * 
     * @param arg1
     *            java.beans.PropertyChangeEvent
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC2(java.beans.PropertyChangeEvent arg1) {
        try {
            // user code begin {1}
            // user code end
            this.selectPanel_SelectionChanged();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    private JSCDataTableScrollPane getDataTableScrollPane1() {
        if (ivjDataTableScrollPane1 == null) {
            try {
                ivjDataTableScrollPane1 = new JSCDataTableScrollPane();
                ivjDataTableScrollPane1.setName("DataTableScrollPane1");
                ivjDataTableScrollPane1.setColumnHeaderShowing(true);
                getDataTableScrollPane1().setViewportView(getScrollPaneTable());
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjDataTableScrollPane1;
    }

    private ModelPanel getJDialogContentPane() {
        if (modelPanel == null) {
            try {
                modelPanel = new ModelPanel();
                modelPanel.setName("JDialogContentPane");
                modelPanel.setLayout(new java.awt.BorderLayout());
                getJDialogContentPane().add(getJPanel1(), "Center");
                getJDialogContentPane().add(getJPanel2(), "South");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return modelPanel;
    }

    /**
     * Return the JPanel1 property value.
     * 
     * @return javax.swing.JPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JPanel getJPanel1() {
        if (ivjJPanel1 == null) {
            try {
                ivjJPanel1 = new javax.swing.JPanel();
                ivjJPanel1.setName("JPanel1");
                ivjJPanel1.setBorder(new BevelBorder(BevelBorder.LOWERED));
                ivjJPanel1.setLayout(new java.awt.GridBagLayout());

                java.awt.GridBagConstraints constraintsSelectPanel = new java.awt.GridBagConstraints();
                constraintsSelectPanel.gridx = 1;
                constraintsSelectPanel.gridy = 0;
                constraintsSelectPanel.gridheight = 2;
                constraintsSelectPanel.fill = java.awt.GridBagConstraints.BOTH;
                constraintsSelectPanel.anchor = java.awt.GridBagConstraints.NORTHEAST;
                getJPanel1().add(getSelectPanel(), constraintsSelectPanel);

                java.awt.GridBagConstraints constraintsJPanel3 = new java.awt.GridBagConstraints();
                constraintsJPanel3.gridx = 0;
                constraintsJPanel3.gridy = 0;
                constraintsJPanel3.fill = java.awt.GridBagConstraints.BOTH;
                constraintsJPanel3.weightx = 1.0;
                constraintsJPanel3.weighty = 1.0;
                constraintsJPanel3.insets = new java.awt.Insets(10, 0, 0, 0);
                getJPanel1().add(getJPanel3(), constraintsJPanel3);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJPanel1;
    }

    /**
     * Return the JPanel2 property value.
     * 
     * @return javax.swing.JPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JPanel getJPanel2() {
        if (ivjJPanel2 == null) {
            try {
                ivjJPanel2 = new javax.swing.JPanel();
                ivjJPanel2.setName("JPanel2");
                ivjJPanel2.setPreferredSize(new java.awt.Dimension(0, 50));
                ivjJPanel2.setLayout(new java.awt.FlowLayout());
                ivjJPanel2.setMinimumSize(new java.awt.Dimension(0, 50));
                ivjJPanel2.setMaximumSize(new java.awt.Dimension(0, 0));
                getJPanel2().add(getOK(), getOK().getName());
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJPanel2;
    }

    /**
     * Return the JPanel3 property value.
     * 
     * @return javax.swing.JPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JPanel getJPanel3() {
        if (ivjJPanel3 == null) {
            try {
                ivjJPanel3 = new javax.swing.JPanel();
                ivjJPanel3.setName("JPanel3");
                ivjJPanel3.setLayout(new java.awt.GridBagLayout());

                java.awt.GridBagConstraints constraintsJTextPane1 = new java.awt.GridBagConstraints();
                constraintsJTextPane1.gridx = 0;
                constraintsJTextPane1.gridy = 0;
                constraintsJTextPane1.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJTextPane1.weightx = 1.0;
                constraintsJTextPane1.insets = new java.awt.Insets(0, 10, 0, 10);
                getJPanel3().add(getJTextPane1(), constraintsJTextPane1);

                java.awt.GridBagConstraints constraintsGaussDataTableScrollPane1 = new java.awt.GridBagConstraints();
                constraintsGaussDataTableScrollPane1.gridx = 0;
                constraintsGaussDataTableScrollPane1.gridy = 1;
                constraintsGaussDataTableScrollPane1.fill = java.awt.GridBagConstraints.BOTH;
                constraintsGaussDataTableScrollPane1.weightx = 1.0;
                constraintsGaussDataTableScrollPane1.weighty = 1.0;
                constraintsGaussDataTableScrollPane1.insets = new java.awt.Insets(
                        15, 10, 0, 10);
                getJPanel3().add(getDataTableScrollPane1(),
                        constraintsGaussDataTableScrollPane1);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJPanel3;
    }

    /**
     * Return the JTextPane1 property value.
     * 
     * @return javax.swing.JTextPane
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JTextPane getJTextPane1() {
        if (ivjJTextPane1 == null) {
            try {
                ivjJTextPane1 = new javax.swing.JTextPane();
                ivjJTextPane1.setName("JTextPane1");
                ivjJTextPane1
                        .setText("Please note:\nTo get the level forecasts from the estimation in 1st differences, we need the value of y(t) in levels. Select the original undifferenced  series in the correct order. Start and end date affect the plot of the original series. You can also type in y(t) directly.");
                ivjJTextPane1.setEditable(false);
                ivjJTextPane1.setEnabled(false);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJTextPane1;
    }

    /**
     * Return the OK property value.
     * 
     * @return javax.swing.JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JButton getOK() {
        if (ivjOK == null) {
            try {
                ivjOK = new javax.swing.JButton();
                ivjOK.setName("OK");
                ivjOK.setPreferredSize(new java.awt.Dimension(85, 25));
                ivjOK.setText("OK");
                ivjOK.setMinimumSize(new java.awt.Dimension(85, 25));
                ivjOK.setMaximumSize(new java.awt.Dimension(100, 25));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjOK;
    }

    private JSCNArrayTable getScrollPaneTable() {
        if (ivjScrollPaneTable == null) {
            try {
                ivjScrollPaneTable = new JSCNArrayTable();
                ivjScrollPaneTable.setName("ScrollPaneTable");
                getDataTableScrollPane1().setColumnHeaderView(
                        ivjScrollPaneTable.getTableHeader());
                ivjScrollPaneTable.setPrecision(4);
                ivjScrollPaneTable.setBounds(0, 0, 142, 102);
                ivjScrollPaneTable.setJSCData(ytLevels);
                ivjScrollPaneTable.setEditable(true);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjScrollPaneTable;
    }

    private TSSel getSelectPanel() {
        if (ivjSelectPanel == null) {
            try {
                ivjSelectPanel = new TSSel();
                ivjSelectPanel.setName("SelectPanel");
                ivjSelectPanel.setEndogenousStringsName(selNamesDef.name);
                ivjSelectPanel.setDeterministicEnabled(false);
                ivjSelectPanel.setDateRangeName(selDRangeDef.name);
                ivjSelectPanel.setEndogenousDataName(selDataDef.name);
                ivjSelectPanel.setExogenousEnabled(false);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSelectPanel;
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
        getOK().addActionListener(ivjEventHandler);
        getSelectPanel().addPropertyChangeListener(ivjEventHandler);
    }

    /**
     * Initialize the class.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initialize() {
        try {
            // user code begin {1}
            // user code end
            setName("ForecastUndiffConfig");
            setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
            setSize(612, 486);
            setTitle("Configure undifferenced Forecast");
            setContentPane(getJDialogContentPane());
            initConnections();
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}
        // user code end
    }

    /**
     * Comment
     */
    private void oK_ActionEvents() {
        setVisible(false);
        return;
    }

    /**
     * Comment
     */
    private void selectPanel_SelectionChanged() {
        /* compute the index, that has observation t */
        SymbolTable sTable = modelPanel.global();
        TSDateRange selRange = sTable.get(selDRangeDef).getJSCDRange()
                .getTSDateRange();

        // null selection
        if (selRange == null)
            return;

        int index = selRange.lowerBound().compareTo(origRange.upperBound());

        if (index >= 0) {
            selYLevels = sTable.get(selDataDef).getJSCNArray();
            if (selYLevels.cols() < ytLevels.cols())
                selYLevels.appendCols(new JSCNArray("toAppend",
                        new double[selYLevels.rows()][ytLevels.cols()
                                - selYLevels.cols()]));
            else if (selYLevels.cols() > ytLevels.cols()) {
                int[] ind = new int[selYLevels.cols()];
                for (int i = 0; i < ind.length; i++)
                    if (i >= ytLevels.cols())
                        ind[i] = 1;
                selYLevels.delColsIf(ind);
            }

            ytLevels.setVal(selYLevels.getRows(index, index));

        } else {
            StdMessages
                    .errorSpecification("Selected series do not fit to series used in the estimation.\nSelect another series or type y(t) manually.");
            return;
        }

    }

    /**
     * Sets the original date range to check.
     * 
     * @param range
     */
    public void setOrigRange(TSDateRange range) {
        origRange = range;
    }

    /**
     * Sets the levels names.
     * 
     * @param levelsNames
     */
    public void setYtLevelsNames(JSCSArray levelsNames) {
        getDataTableScrollPane1().setColumnHeaderData(levelsNames);
        ytLevels.setVal(new double[1][levelsNames.rows()]);
    }

    /**
     * Gets the selected yt row.
     * 
     * @return <code>JSCNArray</code>
     */
    public JSCNArray getYtLevels() {
        return ytLevels;
    }

    /**
     * Gets the selected date range.
     * 
     * @return <code>JSCDRange</code>
     */
    public JSCDRange getSelDRange() {
        return modelPanel.global().get(selDRangeDef).getJSCDRange();
    }

    /**
     * Gets the selected yt's.
     * 
     * @return <code>JSCNArray</code>
     */
    public JSCNArray getSelYLevels() {
        return selYLevels;
    }

}
