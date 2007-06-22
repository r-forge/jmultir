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

package de.jmulti.cafpe;

import java.awt.BorderLayout;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import com.jstatcom.component.NumSelector;
import com.jstatcom.component.ResultField;
import com.jstatcom.component.StdMessages;
import com.jstatcom.engine.PCall;
import com.jstatcom.engine.PCallAdapter;
import com.jstatcom.model.JSCConstants;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.ModelPanel;
import com.jstatcom.model.NumberRange;
import com.jstatcom.model.NumberRangeTypes;
import com.jstatcom.parser.CalcEngine;
import com.jstatcom.parser.ParseException;
import com.jstatcom.table.JSCDataTableScrollPane;
import com.jstatcom.table.JSCNArrayTable;
import com.jstatcom.ts.TSDateRange;

import de.jmulti.proc.CAFPEForecastCall;

/**
 * Panel for 1-step ahead forecast of univariate model.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */

final class CAFPE_forecast extends ModelPanel {
    private static final Logger log = Logger.getLogger(CAFPE_forecast.class);

    private JComboBox estLagBox = null;

    private JPanel ivjJPanel1 = null;

    private ResultField ivjResultField = null;

    private NumSelector ivjCiLevel = null;

    private JCheckBox ivjEditOptLags = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private JButton ivjExecute = null;

    private JSCDataTableScrollPane ivjDataTableScrollPane2 = null;

    private JComboBox ivjGridVector = null;

    private JLabel ivjJLabel3 = null;

    private JLabel ivjJLabel7 = null;

    private JSCNArrayTable ivjScrollPaneTable2 = null;

    private JCheckBox ivjRollOver = null;

    private JRadioButton ivjMod1 = null;

    private JRadioButton ivjMod2 = null;

    private JRadioButton ivjMod3 = null;

    private JTabbedPane ivjJTabbedPane1 = null;

    private NumSelector ivjBwFactorTextField = null;

    private JLabel ivjJLabel = null;

    private javax.swing.JCheckBox jCheckBox = null;

    private NumSelector numberSelector = null;

    private javax.swing.JLabel jLabel = null;

    class IvjEventHandler implements java.awt.event.ActionListener,
            java.awt.event.ComponentListener, java.awt.event.ItemListener {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == CAFPE_forecast.this.getExecute())
                connEtoC1();
            if (e.getSource() == CAFPE_forecast.this.getEditOptLags())
                connEtoC2();
        };

        public void componentHidden(java.awt.event.ComponentEvent e) {
        };

        public void componentMoved(java.awt.event.ComponentEvent e) {
        };

        public void componentResized(java.awt.event.ComponentEvent e) {
        };

        public void componentShown(java.awt.event.ComponentEvent e) {
            if (e.getSource() == CAFPE_forecast.this)
                connEtoC5(e);
        };

        public void itemStateChanged(java.awt.event.ItemEvent e) {
            if (e.getSource() == CAFPE_forecast.this.getEditOptLags())
                connPtoP1SetTarget();
            if (e.getSource() == CAFPE_forecast.this.getRollOver())
                connEtoC4();
            if (e.getSource() == CAFPE_forecast.this.getOneStep())
                oneStep_ActionEvents();

        };
    };

    /**
     * CAFPE_forecast constructor comment.
     */
    public CAFPE_forecast() {
        super();
        initialize();
    }

    /**
     * connEtoC1: (Execute.action. --> CAFPE_forecast.execute_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC1() {
        try {
            // user code begin {1}
            // user code end
            this.execute_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC2: (EditOptLags.action. -->
     * CAFPE_forecast.editOptLags_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC2() {
        try {
            // user code begin {1}
            // user code end
            this.editOptLags_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC4: (RollOver.action. --> CAFPE_forecast.rollOver_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC4() {
        try {
            // user code begin {1}
            // user code end
            this.rollOver_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC5:
     * (JTabbedPane1.component.componentShown(java.awt.event.ComponentEvent) -->
     * CAFPE_forecast.this_componentShown(Ljava.awt.event.ComponentEvent;)V)
     * 
     * @param arg1
     *            java.awt.event.ComponentEvent
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC5(java.awt.event.ComponentEvent arg1) {
        try {
            // user code begin {1}
            // user code end
            this.this_componentShown(arg1);
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connPtoP1SetTarget: (EditOptLags.selected <--> ScrollPaneTable2.visible)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connPtoP1SetTarget() {
        /* Set the target from the source */
        try {
            getScrollPaneTable2().setVisible(getEditOptLags().isSelected());
            // user code begin {1}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * Comment
     */
    private void editOptLags_ActionEvents() {
        getScrollPaneTable2().setVisible(getEditOptLags().isSelected());
    }

    /**
     * Execute forecast.
     */
    private void execute_ActionEvents() {
        if (getGridVector().getItemCount() == 0) {
            StdMessages.errorSpecification("The lag selection is empty.");
            return;
        }
        int modLag = 0;
        if (getMod2().isSelected())
            modLag = 1;
        if (getMod3().isSelected())
            modLag = 2;

        final File outFile = new File(JSCConstants.getSystemTemp()
                + File.separator + CAFPE_constants.OUT_FILE_FORECAST);
        String lags_grid = getGridVector().getSelectedItem().toString();
        CalcEngine parser = new CalcEngine();
        double[] lagsGridArray;

        try { // use times series parser for expressions like "1|2|4"
            parser.parseString("d = " + lags_grid);
            lagsGridArray = parser.getVariable("d");
        } catch (ParseException ex) {
            throw new RuntimeException(ex.toString());
        }

        JSCNArray lagsGrid = new JSCNArray("CAFPE_FOR_LAGSGRID", lagsGridArray);
        TSDateRange range = global().get(CAFPE_constants.DRANGE).getJSCDRange()
                .getTSDateRange();

        PCall job = new CAFPEForecastCall(global().get(CAFPE_constants.Y)
                .getJSCNArray(), global().get(CAFPE_constants.Y_NAME)
                .getJSCString().string(), global().get(CAFPE_constants.MAX_LAG)
                .getJSCInt().intVal(), global().get(CAFPE_constants.D_MAX)
                .getJSCInt().intVal(), range.lowerBound(), lagsGrid, outFile,
                global().get(CAFPE_constants.MOD_METH).getJSCString().string(),
                global().get(CAFPE_constants.SEARCH_METH).getJSCString()
                        .string(), global().get(CAFPE_constants.SELCRIT)
                        .getJSCString().string(), global().get(
                        CAFPE_constants.STARTSTRAT).getJSCString().string(),
                global().get(CAFPE_constants.IS_STAND).getJSCInt().isNonZero(),
                modLag, getInitFrac().getNumber(), getCiLevel().getNumber(),
                getRollOver().isSelected(), getBwFactor().getNumber());
        job.setOutHolder(getResultField());
        job.addPCallListener(new PCallAdapter() {
            public void success() {
                getJTabbedPane1().setSelectedIndex(1);
            }

            public void finished(PCall pCall) {
                outFile.delete();
            }
        });
        job.execute();
    }

    private NumSelector getBwFactor() {
        if (ivjBwFactorTextField == null) {
            try {
                ivjBwFactorTextField = new NumSelector();
                ivjBwFactorTextField.setName("BwFactorTextField");
                ivjBwFactorTextField.setPrecision(2);
                ivjBwFactorTextField.setRangeExpr("(0, Infinity)");
                ivjBwFactorTextField.setPreferredSize(new java.awt.Dimension(
                        55, 20));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjBwFactorTextField;
    }

    private NumSelector getCiLevel() {
        if (ivjCiLevel == null) {
            try {
                ivjCiLevel = new NumSelector();
                ivjCiLevel.setName("CiLevel");
                ivjCiLevel.setNumber(0.95);
                ivjCiLevel.setPrecision(2);
                ivjCiLevel.setRangeExpr("(0,1)");
                ivjCiLevel.setPreferredSize(new java.awt.Dimension(55, 20));
                ivjCiLevel.setMinimumSize(new java.awt.Dimension(55, 20));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjCiLevel;
    }

    /**
     * Return the EditOptLags property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getEditOptLags() {
        if (ivjEditOptLags == null) {
            try {
                ivjEditOptLags = new javax.swing.JCheckBox();
                ivjEditOptLags.setName("EditOptLags");
                ivjEditOptLags
                        .setPreferredSize(new java.awt.Dimension(250, 22));
                ivjEditOptLags.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
                ivjEditOptLags
                        .setText("Edit sets of lags obtained from lag selection");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjEditOptLags;
    }

    /**
     * Return the Execute property value.
     * 
     * @return javax.swing.JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JButton getExecute() {
        if (ivjExecute == null) {
            try {
                ivjExecute = new javax.swing.JButton();
                ivjExecute.setName("Execute");
                ivjExecute.setPreferredSize(new java.awt.Dimension(150, 25));
                ivjExecute.setText("Execute Forecast");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjExecute;
    }

    private JSCDataTableScrollPane getDataTableScrollPane2() {
        if (ivjDataTableScrollPane2 == null) {
            try {
                ivjDataTableScrollPane2 = new JSCDataTableScrollPane();
                ivjDataTableScrollPane2.setName("DataTableScrollPane2");
                ivjDataTableScrollPane2.setMinimumVisibleColumns(4);
                ivjDataTableScrollPane2.setMaximumVisibleColumns(4);
                ivjDataTableScrollPane2
                        .setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
                ivjDataTableScrollPane2.setMinimumVisibleRows(3);
                ivjDataTableScrollPane2.setMaximumVisibleRows(3);
                getDataTableScrollPane2()
                        .setViewportView(getScrollPaneTable2());
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjDataTableScrollPane2;
    }

    /**
     * Return the GridVector property value.
     * 
     * @return javax.swing.JComboBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JComboBox getGridVector() {
        if (ivjGridVector == null) {
            try {
                ivjGridVector = new javax.swing.JComboBox();
                ivjGridVector.setName("GridVector");
                ivjGridVector.setPreferredSize(new java.awt.Dimension(250, 23));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjGridVector;
    }

    /**
     * Return the JLabel property value.
     * 
     * @return javax.swing.JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JLabel getJLabel() {
        if (ivjJLabel == null) {
            try {
                ivjJLabel = new javax.swing.JLabel();
                ivjJLabel.setName("JLabel");
                ivjJLabel.setPreferredSize(new java.awt.Dimension(240, 20));
                ivjJLabel.setText("Factor to multiply plug-in bandwidth with");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJLabel;
    }

    /**
     * Return the JLabel3 property value.
     * 
     * @return javax.swing.JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JLabel getJLabel3() {
        if (ivjJLabel3 == null) {
            try {
                ivjJLabel3 = new javax.swing.JLabel();
                ivjJLabel3.setName("JLabel3");
                ivjJLabel3.setPreferredSize(new java.awt.Dimension(55, 20));
                ivjJLabel3.setText("Select set of lags for forecasting");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJLabel3;
    }

    /**
     * Return the JLabel7 property value.
     * 
     * @return javax.swing.JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JLabel getJLabel7() {
        if (ivjJLabel7 == null) {
            try {
                ivjJLabel7 = new javax.swing.JLabel();
                ivjJLabel7.setName("JLabel7");
                ivjJLabel7.setPreferredSize(new java.awt.Dimension(100, 20));
                ivjJLabel7.setText("Level of prediction interval");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJLabel7;
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
                ivjJPanel1.setPreferredSize(new java.awt.Dimension(0, 170));
                ivjJPanel1.setLayout(new java.awt.GridBagLayout());

                java.awt.GridBagConstraints constraintsGridVector = new java.awt.GridBagConstraints();
                constraintsGridVector.gridx = 0;
                constraintsGridVector.gridy = 1;
                constraintsGridVector.gridwidth = 2;
                constraintsGridVector.gridheight = 2;
                constraintsGridVector.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsGridVector.anchor = java.awt.GridBagConstraints.WEST;
                constraintsGridVector.insets = new java.awt.Insets(0, 10, 0, 0);
                java.awt.GridBagConstraints constraintsJLabel3 = new java.awt.GridBagConstraints();
                constraintsJLabel3.gridx = 0;
                constraintsJLabel3.gridy = 0;
                constraintsJLabel3.gridwidth = 2;
                constraintsJLabel3.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel3.anchor = java.awt.GridBagConstraints.WEST;
                constraintsJLabel3.insets = new java.awt.Insets(0, 10, 0, 0);
                getJPanel1().add(getJLabel3(), constraintsJLabel3);

                java.awt.GridBagConstraints constraintsEditOptLags = new java.awt.GridBagConstraints();
                constraintsEditOptLags.gridx = 2;
                constraintsEditOptLags.gridy = 0;
                constraintsEditOptLags.gridwidth = 3;
                constraintsEditOptLags.gridheight = 2;
                constraintsEditOptLags.fill = java.awt.GridBagConstraints.BOTH;
                constraintsEditOptLags.insets = new java.awt.Insets(0, 20, 0, 0);
                java.awt.GridBagConstraints constraintsGaussDataTableScrollPane2 = new java.awt.GridBagConstraints();
                constraintsGaussDataTableScrollPane2.gridx = 3;
                constraintsGaussDataTableScrollPane2.gridy = 2;
                constraintsGaussDataTableScrollPane2.gridheight = 3;
                constraintsGaussDataTableScrollPane2.fill = java.awt.GridBagConstraints.BOTH;
                constraintsGaussDataTableScrollPane2.weightx = 1.0;
                constraintsGaussDataTableScrollPane2.insets = new java.awt.Insets(
                        5, 20, 0, 0);
                java.awt.GridBagConstraints constraintsJLabel7 = new java.awt.GridBagConstraints();
                constraintsJLabel7.gridx = 4;
                constraintsJLabel7.gridy = 7;
                constraintsJLabel7.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel7.anchor = java.awt.GridBagConstraints.WEST;
                constraintsJLabel7.insets = new java.awt.Insets(5, 5, 0, 0);
                java.awt.GridBagConstraints constraintsCiLevel = new java.awt.GridBagConstraints();
                constraintsCiLevel.gridx = 3;
                constraintsCiLevel.gridy = 7;
                constraintsCiLevel.anchor = java.awt.GridBagConstraints.WEST;
                constraintsCiLevel.insets = new java.awt.Insets(5, 40, 0, 0);
                java.awt.GridBagConstraints constraintsExecute = new java.awt.GridBagConstraints();
                constraintsExecute.gridx = 4;
                constraintsExecute.gridy = 14;
                constraintsExecute.gridwidth = 4;
                constraintsExecute.anchor = java.awt.GridBagConstraints.SOUTH;
                constraintsExecute.weighty = 1.0;
                constraintsExecute.insets = new java.awt.Insets(0, 10, 10, 10);
                java.awt.GridBagConstraints constraintsRollOver = new java.awt.GridBagConstraints();
                constraintsRollOver.gridx = 0;
                constraintsRollOver.gridy = 6;
                constraintsRollOver.gridwidth = 2;
                constraintsRollOver.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsRollOver.anchor = java.awt.GridBagConstraints.WEST;
                constraintsRollOver.insets = new java.awt.Insets(15, 10, 0, 0);
                java.awt.GridBagConstraints constraintsMod1 = new java.awt.GridBagConstraints();
                constraintsMod1.gridx = 0;
                constraintsMod1.gridy = 8;
                constraintsMod1.gridwidth = 2;
                constraintsMod1.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsMod1.insets = new java.awt.Insets(5, 20, 0, 0);
                java.awt.GridBagConstraints constraintsMod3 = new java.awt.GridBagConstraints();
                constraintsMod3.gridx = 0;
                constraintsMod3.gridy = 10;
                constraintsMod3.gridwidth = 5;
                constraintsMod3.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsMod3.insets = new java.awt.Insets(5, 20, 0, 0);
                java.awt.GridBagConstraints constraintsMod2 = new java.awt.GridBagConstraints();
                constraintsMod2.gridx = 0;
                constraintsMod2.gridy = 9;
                constraintsMod2.gridwidth = 5;
                constraintsMod2.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsMod2.insets = new java.awt.Insets(5, 20, 0, 0);
                java.awt.GridBagConstraints constraintsBwFactorTextField = new java.awt.GridBagConstraints();
                constraintsBwFactorTextField.gridx = 0;
                constraintsBwFactorTextField.gridy = 3;
                constraintsBwFactorTextField.anchor = java.awt.GridBagConstraints.WEST;
                constraintsBwFactorTextField.insets = new java.awt.Insets(5,
                        10, 0, 0);
                java.awt.GridBagConstraints constraintsJLabel = new java.awt.GridBagConstraints();
                java.awt.GridBagConstraints consGridBagConstraints1 = new java.awt.GridBagConstraints();
                java.awt.GridBagConstraints consGridBagConstraints2 = new java.awt.GridBagConstraints();
                java.awt.GridBagConstraints consGridBagConstraints4 = new java.awt.GridBagConstraints();
                consGridBagConstraints2.gridy = 7;
                consGridBagConstraints2.gridx = 1;
                consGridBagConstraints2.fill = java.awt.GridBagConstraints.HORIZONTAL;
                consGridBagConstraints2.insets = new java.awt.Insets(5, 5, 0, 0);
                consGridBagConstraints2.gridwidth = 1;
                consGridBagConstraints1.fill = java.awt.GridBagConstraints.NONE;
                consGridBagConstraints1.weightx = 0.0D;
                consGridBagConstraints1.gridy = 7;
                consGridBagConstraints1.gridx = 0;
                consGridBagConstraints1.insets = new java.awt.Insets(5, 20, 0,
                        0);
                consGridBagConstraints1.anchor = java.awt.GridBagConstraints.WEST;
                constraintsGaussDataTableScrollPane2.gridwidth = 2;
                consGridBagConstraints4.gridy = 6;
                consGridBagConstraints4.gridx = 3;
                consGridBagConstraints4.fill = java.awt.GridBagConstraints.HORIZONTAL;
                consGridBagConstraints4.insets = new java.awt.Insets(15, 20, 0,
                        0);
                constraintsJLabel.gridx = 1;
                constraintsJLabel.gridy = 3;
                constraintsJLabel.gridwidth = 1;
                constraintsJLabel.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel.insets = new java.awt.Insets(5, 5, 0, 0);
                consGridBagConstraints4.gridwidth = 2;
                ivjJPanel1.add(getGridVector(), constraintsGridVector);
                ivjJPanel1.add(getEditOptLags(), constraintsEditOptLags);
                ivjJPanel1.add(getDataTableScrollPane2(),
                        constraintsGaussDataTableScrollPane2);
                ivjJPanel1.add(getJLabel7(), constraintsJLabel7);
                ivjJPanel1.add(getCiLevel(), constraintsCiLevel);
                ivjJPanel1.add(getExecute(), constraintsExecute);
                ivjJPanel1.add(getRollOver(), constraintsRollOver);
                ivjJPanel1.add(getMod1(), constraintsMod1);
                ivjJPanel1.add(getMod3(), constraintsMod3);
                ivjJPanel1.add(getMod2(), constraintsMod2);
                ivjJPanel1.add(getBwFactor(), constraintsBwFactorTextField);
                ivjJPanel1.add(getJLabel(), constraintsJLabel);
                ivjJPanel1.add(getOneStep(), consGridBagConstraints4);
                ivjJPanel1.add(getInitFrac(), consGridBagConstraints1);
                ivjJPanel1.add(getJLabel2(), consGridBagConstraints2);
                TitledBorder title = new TitledBorder(new BevelBorder(
                        BevelBorder.LOWERED),
                        "Specify 1-step ahead Forecast of Model",
                        TitledBorder.RIGHT, TitledBorder.TOP);
                ivjJPanel1.setBorder(title);

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
     * Return the JTabbedPane1 property value.
     * 
     * @return javax.swing.JTabbedPane
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JTabbedPane getJTabbedPane1() {
        if (ivjJTabbedPane1 == null) {
            try {
                ivjJTabbedPane1 = new javax.swing.JTabbedPane();
                ivjJTabbedPane1.setName("JTabbedPane1");
                ivjJTabbedPane1.insertTab("Specify Forecast", null,
                        getJPanel1(), null, 0);
                ivjJTabbedPane1.insertTab("Output (save/print)", null,
                        getResultField(), null, 1);
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
     * Return the Mod1 property value.
     * 
     * @return javax.swing.JRadioButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JRadioButton getMod1() {
        if (ivjMod1 == null) {
            try {
                ivjMod1 = new javax.swing.JRadioButton();
                ivjMod1.setName("Mod1");
                ivjMod1.setSelected(true);
                ivjMod1.setText("Use set of lags specified above");
                ivjMod1.setEnabled(false);
                // user code begin {1}
                ivjMod1.setPreferredSize(new java.awt.Dimension(100, 20));
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjMod1;
    }

    /**
     * Return the Mod2 property value.
     * 
     * @return javax.swing.JRadioButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JRadioButton getMod2() {
        if (ivjMod2 == null) {
            try {
                ivjMod2 = new javax.swing.JRadioButton();
                ivjMod2.setName("Mod2");
                ivjMod2
                        .setText("Conduct lag selection for initial forecast only");
                ivjMod2.setEnabled(false);
                // user code begin {1}
                ivjMod2.setPreferredSize(new java.awt.Dimension(100, 20));
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjMod2;
    }

    /**
     * Return the Mod3 property value.
     * 
     * @return javax.swing.JRadioButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JRadioButton getMod3() {
        if (ivjMod3 == null) {
            try {
                ivjMod3 = new javax.swing.JRadioButton();
                ivjMod3.setName("Mod3");
                ivjMod3.setText("Conduct lag selection for every forecast");
                ivjMod3.setEnabled(false);
                // user code begin {1}
                ivjMod3.setPreferredSize(new java.awt.Dimension(100, 20));
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjMod3;
    }

    /**
     * Return the ResultField property value.
     * 
     * @return ResultField
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private com.jstatcom.component.ResultField getResultField() {
        if (ivjResultField == null) {
            try {
                ivjResultField = new com.jstatcom.component.ResultField();
                ivjResultField.setName("ResultField");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjResultField;
    }

    /**
     * Return the RollOver property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getRollOver() {
        if (ivjRollOver == null) {
            try {
                ivjRollOver = new javax.swing.JCheckBox();
                ivjRollOver.setName("RollOver");
                ivjRollOver.setText("Rolling over, out-of-sample forecasts");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjRollOver;
    }

    private JSCNArrayTable getScrollPaneTable2() {
        if (ivjScrollPaneTable2 == null) {
            try {
                ivjScrollPaneTable2 = new JSCNArrayTable();
                ivjScrollPaneTable2.setName("ScrollPaneTable2");
                getDataTableScrollPane2().setColumnHeaderView(
                        ivjScrollPaneTable2.getTableHeader());
                ivjScrollPaneTable2.setPrecision(0);
                ivjScrollPaneTable2.setTablePopup(null);
                ivjScrollPaneTable2.setVisible(false);
                ivjScrollPaneTable2.setSymbolName("opt_lags");
                ivjScrollPaneTable2.setBounds(0, 0, 147, 107);
                ivjScrollPaneTable2.setEditable(true);
                ivjScrollPaneTable2.setEnabled(true);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjScrollPaneTable2;
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
        getExecute().addActionListener(ivjEventHandler);
        getEditOptLags().addItemListener(ivjEventHandler);
        getEditOptLags().addActionListener(ivjEventHandler);
        getRollOver().addItemListener(ivjEventHandler);
        getOneStep().addItemListener(ivjEventHandler);
        addComponentListener(ivjEventHandler);
        connPtoP1SetTarget();
    }

    /**
     * Initialize the class.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initialize() {
        try {
            // user code begin {1}
            // user code end
            setName("CAFPE_forecast");
            setLayout(new BorderLayout());
            setSize(663, 366);
            add(getJTabbedPane1(), BorderLayout.CENTER);
            initConnections();
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}
        ButtonGroup bg = new ButtonGroup();
        bg.add(getMod1());
        bg.add(getMod2());
        bg.add(getMod3());
        ButtonGroup bg1 = new ButtonGroup();
        bg1.add(getRollOver());
        bg1.add(getOneStep());

        // user code end
    }

    /**
     * Enables/Disables fields according to selection.
     */
    private void rollOver_ActionEvents() {
        boolean set = getRollOver().isSelected();
        getMod1().setEnabled(set);
        getMod2().setEnabled(set);
        getMod3().setEnabled(set);
        getJLabel2().setEnabled(set);
        getInitFrac().setEnabled(set);
        getJLabel2().setEnabled(set);
    }

    /**
     * Enables/Disables fields according to selection.
     */
    private void oneStep_ActionEvents() {
        boolean set = getOneStep().isSelected();
        getCiLevel().setEnabled(set);
        getJLabel7().setEnabled(set);
    }

    /**
     * Insert the method's description here. Creation date: (18.04.2002
     * 15:45:38)
     * 
     * @param newEstLagBox
     *            javax.swing.JComboBox
     */
    public void setEstLagBox(javax.swing.JComboBox newEstLagBox) {
        estLagBox = newEstLagBox;
        getGridVector().setModel(estLagBox.getModel());
    }

    /**
     * Comment
     */
    private void this_componentShown(
            java.awt.event.ComponentEvent componentEvent) {
        getJTabbedPane1().setSelectedIndex(0);
        double upper = global().get(CAFPE_constants.MAX_LAG).getJSCInt()
                .intVal();

        getScrollPaneTable2().setNumberRange(
                new NumberRange(0, upper, NumberRangeTypes.CLOSED));
        return;
    }

    /**
     * This method initializes jCheckBox
     * 
     * @return javax.swing.JCheckBox
     */
    private javax.swing.JCheckBox getOneStep() {
        if (jCheckBox == null) {
            jCheckBox = new javax.swing.JCheckBox();
            jCheckBox.setText("One-step ahead forecast");
            jCheckBox.setSelected(true);
        }
        return jCheckBox;
    }

    /**
     * This method initializes numberSelector
     * 
     * @return com.jstatcom.model.NumberSelector
     */
    private NumSelector getInitFrac() {
        if (numberSelector == null) {
            numberSelector = new NumSelector();
            numberSelector.setPrecision(2);
            numberSelector.setRangeExpr("(0, 1)");
            numberSelector.setPreferredSize(new java.awt.Dimension(50, 20));
            numberSelector.setEnabled(false);
        }
        return numberSelector;
    }

    /**
     * This method initializes jLabel
     * 
     * @return javax.swing.JLabel
     */
    private javax.swing.JLabel getJLabel2() {
        if (jLabel == null) {
            jLabel = new javax.swing.JLabel();
            jLabel.setText("Fraction of series for initial estimation");
            jLabel.setPreferredSize(new java.awt.Dimension(38, 20));
            jLabel.setEnabled(false);
        }
        return jLabel;
    }
}