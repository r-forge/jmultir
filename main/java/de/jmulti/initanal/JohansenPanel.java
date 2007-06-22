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

import java.awt.GridBagConstraints;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import com.jstatcom.component.CheckBoxList;
import com.jstatcom.component.NumSelector;
import com.jstatcom.component.ResultField;
import com.jstatcom.component.StdMessages;
import com.jstatcom.engine.PCall;
import com.jstatcom.model.JSCDRange;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.JSCSArray;
import com.jstatcom.model.ModelPanel;
import com.jstatcom.model.SymbolEvent;
import com.jstatcom.model.SymbolListener;
import com.jstatcom.ts.TSDate;
import com.jstatcom.ts.TSDateRange;
import com.jstatcom.util.UData;

import de.jmulti.proc.InfoCritCall;
import de.jmulti.proc.JohCointTestCall;

/**
 * Panel to perform the Johansen Trace test for cointegration.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
public final class JohansenPanel extends ModelPanel {
    private static final Logger log = Logger.getLogger(JohansenPanel.class);

    private JohBreakSpecDialog breakSpecDialog = null;

    private ResultField ivjResultField = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private JLabel ivjJLabel2 = null;

    private NumSelector ivjLagLength = null;

    private JRadioButton ivjConst = null;

    private JRadioButton ivjConstAndTrend = null;

    private JLabel ivjJLabel21 = null;

    private NumSelector ivjMaxLagLength = null;

    private JRadioButton ivjOrthConstAndTrend = null;

    private JCheckBox ivjSeasonalDummies = null;

    private JScrollPane ivjJScrollPane1 = null;

    private CheckBoxList ivjDummyCheckList = null;

    private JButton ivjExecute = null;

    private JButton jButton = null;

    class IvjEventHandler implements java.awt.event.ActionListener {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == JohansenPanel.this.getExecute())
                connEtoC1();
        };
    };

    /**
     * JohansenPanel constructor comment.
     */
    public JohansenPanel() {
        super();
        initialize();
    }

    /**
     * connEtoC1: (Execute.action. --> JohansenPanel.execute_ActionEvents()V)
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
     * Comment
     */
    private void execute_ActionEvents() {
        JSCNArray data = global().get(CointPanel.COINT_ENDDATA).getJSCNArray();
        if (!data.isEmpty()) {
            int k = data.cols();
            if (getOrthConstAndTrend().isSelected() && k < 2) {
                StdMessages
                        .infoNothingSelected("Please select at least two endogenous series for this model.");
                return;
            }
        } else {
            StdMessages.infoNothingSelected(UnitRootPanel.NO_VAR_MSG);
            return;
        }
        TSDateRange range = global().get(CointPanel.COINT_DRANGE)
                .getJSCDRange().getTSDateRange();

        int period = range.subPeriodicity();
        if (period == 1 && getSeasonalDummies().isSelected()) {
            StdMessages.errorSpecification(UnitRootPanel.NO_SEAS_MSG);
            return;
        }

        // do not allow user specified shifts or trend breaks, break spec panel
        // should be used instead
        JSCNArray userDet = global().get(CointPanel.COINT_DET).getJSCNArray();
        JSCNArray[] ind = UData.splitDeterministics(userDet, false);
        if (!ind[2].isEmpty() || !ind[3].isEmpty()) {
            StdMessages
                    .errorSpecification("There are user defined deterministic variables that appear to be a shift or a trend break.\n"
                            + "Please deselect them and use the break specification panel instead.");
            return;
        }

        JSCNArray breaks = new JSCNArray("breaks");
        JSCNArray condDummies = new JSCNArray("condDummies");

        TSDate[] breakDates = getBreakSpecDialog().getBreakDates();

        // check that break dates are in the allowed range
        int levelsLags = getLagLength().getIntNumber();
        TSDateRange newRange = new TSDateRange(range.lowerBound().addPeriods(
                levelsLags + 2), range.upperBound().addPeriods(-levelsLags - 2));
        for (int i = 0; i < breakDates.length; i++)
            if (newRange.encloses(breakDates[i]) != null) {
                StdMessages
                        .errorSpecification("The break date "
                                + breakDates[i]
                                + " is not in the allowed range "
                                + newRange
                                + ".\nThis range depends on the specified number of lags.");
                return;
            }
        if (breakDates.length == 2)
            // break dates are ordered
            if (breakDates[1].compareTo(breakDates[0]) < levelsLags + 2) {
                StdMessages
                        .errorSpecification("The two break dates are too close for the given number of lags.\n"
                                + "Please change the break specification.");
                return;
            }

        // Checks are ok, prepare call.
        int testState = 1;
        if (getConst().isSelected())
            testState = 1;
        if (getConstAndTrend().isSelected())
            testState = 2;
        if (getOrthConstAndTrend().isSelected())
            testState = 3;

        if (breakDates.length > 0) { // if breaks are specified
            breaks = JohCointTestCall.createBreaks(breakDates, range,
                    testState, getBreakSpecDialog().isTrendBreak());
            if (!breaks.isEmpty())
                condDummies = JohCointTestCall.createCondDummies(breakDates,
                        range, levelsLags);
        }

        // create deterministics for information criteria computation
        JSCNArray dets = UData.createDeterministic(range, userDet, true,
                getSeasonalDummies().isSelected(), true, getConstAndTrend()
                        .isSelected()
                        || getOrthConstAndTrend().isSelected(), "dets");
        dets.appendCols(condDummies);
        dets.appendCols(breaks);

        // Check rank of lag truncated deterministics, because dummies might
        // have a break in the lag range, causing a singular regressor,
        // check only up to test lags, not information criteria max lags
        if (!dets.isEmpty())
            if (!UData.hasFullColumnRank(dets, levelsLags)) {
                StdMessages
                        .errorSpecification("Matrix of deterministic variables is singular after truncating "
                                + levelsLags
                                + " lags.\nPlease check the dummy specification.");
                return;
            }

        int[] intSel = getDummyCheckList().getIntSelectionStatus();
        JSCSArray detNames = global().get(CointPanel.COINT_DETNAMES)
                .getJSCSArray().copy();
        JSCNArray userDets = global().get(CointPanel.COINT_DET).getJSCNArray()
                .copy();

        userDets.appendCols(condDummies);
        userDets.appendCols(breaks);

        int[] ecSelection = intSel;

        // append restrictions to EC term for breaks according to spec
        if (!breaks.isEmpty()) {
            ecSelection = JohCointTestCall.getECSelection(intSel, breakDates,
                    testState, getBreakSpecDialog().isTrendBreak(), levelsLags);

            // assemble names for deterministics used for breaks
            String[] breakdumNames = JohCointTestCall.getBreakDumNames(
                    breakDates, testState, getBreakSpecDialog().isTrendBreak(),
                    levelsLags);
            detNames.appendRows(new JSCSArray("breakdumNames", breakdumNames));
        }

        // Johansen Test call.
        PCall job = new JohCointTestCall(data, global().get(
                CointPanel.COINT_ENDNAMES).getJSCSArray(), userDets, detNames,
                getSeasonalDummies().isSelected(), levelsLags, testState,
                range, ecSelection, breakDates, getBreakSpecDialog()
                        .isTrendBreak());
        job.setSymbolTable(local());
        job.setOutHolder(getResultField());
        job.execute();

        // Infocriteria call.
        PCall jobInfo = new InfoCritCall(getMaxLagLength().getIntNumber(),
                null, null, data, dets, range.lowerBound(), 0);
        jobInfo.setOutHolder(getResultField());
        jobInfo.setPrintDate(false);
        job.add(jobInfo);

    }

    /**
     * Return the Const property value.
     * 
     * @return javax.swing.JRadioButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JRadioButton getConst() {
        if (ivjConst == null) {
            try {
                ivjConst = new javax.swing.JRadioButton();
                ivjConst.setName("Const");
                ivjConst.setSelected(true);
                ivjConst.setText("Constant");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjConst;
    }

    /**
     * Return the ConstAndTrend property value.
     * 
     * @return javax.swing.JRadioButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JRadioButton getConstAndTrend() {
        if (ivjConstAndTrend == null) {
            try {
                ivjConstAndTrend = new javax.swing.JRadioButton();
                ivjConstAndTrend.setName("ConstAndTrend");
                ivjConstAndTrend.setText("Constant and trend");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjConstAndTrend;
    }

    /**
     * Return the DummyCheckList property value.
     * 
     * @return CheckBoxList
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private CheckBoxList getDummyCheckList() {
        if (ivjDummyCheckList == null) {
            try {
                ivjDummyCheckList = new CheckBoxList();
                ivjDummyCheckList.setName("DummyCheckList");
                ivjDummyCheckList.setBorderTitle("Set Restricted to Long Run");
                ivjDummyCheckList.setBounds(0, 0, 160, 120);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjDummyCheckList;
    }

    /**
     * Return the JButton1 property value.
     * 
     * @return javax.swing.JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JButton getExecute() {
        if (ivjExecute == null) {
            try {
                ivjExecute = new javax.swing.JButton();
                ivjExecute.setName("Execute");
                ivjExecute.setText("Execute Test");
                ivjExecute.setMaximumSize(new java.awt.Dimension(135, 27));
                ivjExecute.setPreferredSize(new java.awt.Dimension(135, 27));
                ivjExecute.setMinimumSize(new java.awt.Dimension(135, 27));
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

    /**
     * Return the JLabel2 property value.
     * 
     * @return javax.swing.JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JLabel getJLabel2() {
        if (ivjJLabel2 == null) {
            try {
                ivjJLabel2 = new javax.swing.JLabel();
                ivjJLabel2.setName("JLabel2");
                ivjJLabel2.setPreferredSize(new java.awt.Dimension(85, 20));
                ivjJLabel2.setText("Number of lags");
                ivjJLabel2.setMaximumSize(new java.awt.Dimension(85, 20));
                ivjJLabel2.setMinimumSize(new java.awt.Dimension(85, 20));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJLabel2;
    }

    /**
     * Return the JLabel21 property value.
     * 
     * @return javax.swing.JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JLabel getJLabel21() {
        if (ivjJLabel21 == null) {
            try {
                ivjJLabel21 = new javax.swing.JLabel();
                ivjJLabel21.setName("JLabel21");
                ivjJLabel21.setPreferredSize(new java.awt.Dimension(130, 20));
                ivjJLabel21.setText("Max number of lags");
                ivjJLabel21.setMaximumSize(new java.awt.Dimension(100, 20));
                ivjJLabel21.setMinimumSize(new java.awt.Dimension(130, 20));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJLabel21;
    }

    /**
     * Return the JScrollPane1 property value.
     * 
     * @return javax.swing.JScrollPane
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JScrollPane getJScrollPane1() {
        if (ivjJScrollPane1 == null) {
            try {
                ivjJScrollPane1 = new javax.swing.JScrollPane();
                ivjJScrollPane1.setName("JScrollPane1");
                ivjJScrollPane1.setPreferredSize(new java.awt.Dimension(160,
                        131));
                ivjJScrollPane1.setMinimumSize(new java.awt.Dimension(160, 22));
                getJScrollPane1().setViewportView(getDummyCheckList());
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJScrollPane1;
    }

    /**
     * Return the LagLength property value.
     * 
     * @return com.jstatcom.model.NumberSelector
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private NumSelector getLagLength() {
        if (ivjLagLength == null) {
            try {
                ivjLagLength = new NumSelector();
                ivjLagLength.setName("LagLength");
                ivjLagLength.setPreferredSize(new java.awt.Dimension(25, 20));
                ivjLagLength.setNumber(2.0);
                ivjLagLength.setMinimumSize(new java.awt.Dimension(25, 20));
                ivjLagLength.setRangeExpr("[1,100]");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjLagLength;
    }

    /**
     * Return the MaxLagLength property value.
     * 
     * @return com.jstatcom.model.NumberSelector
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private NumSelector getMaxLagLength() {
        if (ivjMaxLagLength == null) {
            try {
                ivjMaxLagLength = new NumSelector();
                ivjMaxLagLength.setName("MaxLagLength");
                ivjMaxLagLength.setNumber(10.0);
                ivjMaxLagLength.setText("10");
                ivjMaxLagLength.setRangeExpr("[1,100]");
                ivjMaxLagLength
                        .setPreferredSize(new java.awt.Dimension(25, 20));
                ivjMaxLagLength.setMinimumSize(new java.awt.Dimension(25, 20));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjMaxLagLength;
    }

    /**
     * Return the OrthConstAndTrend property value.
     * 
     * @return javax.swing.JRadioButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JRadioButton getOrthConstAndTrend() {
        if (ivjOrthConstAndTrend == null) {
            try {
                ivjOrthConstAndTrend = new javax.swing.JRadioButton();
                ivjOrthConstAndTrend.setName("OrthConstAndTrend");
                ivjOrthConstAndTrend.setText("Orthogonal trend");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjOrthConstAndTrend;
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
                ivjResultField
                        .setPreferredSize(new java.awt.Dimension(300, 350));
                ivjResultField.setMinimumSize(new java.awt.Dimension(300, 350));
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
                ivjSeasonalDummies.setText("Seasonal dummies");
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
    }

    /**
     * Initialize the class.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initialize() {
        try {
            // user code begin {1}
            GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
            TitledBorder title = new TitledBorder(new BevelBorder(
                    BevelBorder.LOWERED), "Johansen Trace Test",
                    TitledBorder.RIGHT, TitledBorder.TOP);
            this.setBorder(title);
            ButtonGroup selectGroup = new javax.swing.ButtonGroup();
            selectGroup.add(getConst());
            selectGroup.add(getConstAndTrend());
            selectGroup.add(getOrthConstAndTrend());

            // user code end
            setName("JohansenPanel");
            setPreferredSize(new java.awt.Dimension(485, 350));
            setLayout(new java.awt.GridBagLayout());
            setSize(485, 350);
            setMinimumSize(new java.awt.Dimension(485, 350));

            java.awt.GridBagConstraints constraintsResultField = new java.awt.GridBagConstraints();
            constraintsResultField.gridx = 0;
            constraintsResultField.gridy = 0;
            constraintsResultField.gridheight = 9;
            constraintsResultField.fill = java.awt.GridBagConstraints.BOTH;
            constraintsResultField.weightx = 1.0;
            constraintsResultField.weighty = 1.0;
            constraintsResultField.insets = new java.awt.Insets(0, 0, 5, 5);
            add(getResultField(), constraintsResultField);

            java.awt.GridBagConstraints constraintsLagLength = new java.awt.GridBagConstraints();
            constraintsLagLength.gridx = 1;
            constraintsLagLength.gridy = 4;
            constraintsLagLength.anchor = java.awt.GridBagConstraints.WEST;
            constraintsLagLength.insets = new java.awt.Insets(0, 0, 5, 0);
            add(getLagLength(), constraintsLagLength);

            java.awt.GridBagConstraints constraintsJLabel2 = new java.awt.GridBagConstraints();
            constraintsJLabel2.gridx = 2;
            constraintsJLabel2.gridy = 4;
            constraintsJLabel2.fill = java.awt.GridBagConstraints.HORIZONTAL;
            constraintsJLabel2.anchor = java.awt.GridBagConstraints.WEST;
            constraintsJLabel2.insets = new java.awt.Insets(0, 5, 5, 0);
            add(getJLabel2(), constraintsJLabel2);

            java.awt.GridBagConstraints constraintsExecute = new java.awt.GridBagConstraints();
            constraintsExecute.gridx = 1;
            constraintsExecute.gridy = 8;
            constraintsExecute.gridwidth = 2;
            constraintsExecute.fill = java.awt.GridBagConstraints.HORIZONTAL;
            constraintsExecute.anchor = java.awt.GridBagConstraints.SOUTHEAST;
            constraintsExecute.insets = new java.awt.Insets(0, 0, 5, 5);
            add(getExecute(), constraintsExecute);

            java.awt.GridBagConstraints constraintsConst = new java.awt.GridBagConstraints();
            constraintsConst.gridx = 1;
            constraintsConst.gridy = 0;
            constraintsConst.gridwidth = 2;
            constraintsConst.fill = java.awt.GridBagConstraints.HORIZONTAL;
            constraintsConst.anchor = java.awt.GridBagConstraints.WEST;
            add(getConst(), constraintsConst);

            java.awt.GridBagConstraints constraintsConstAndTrend = new java.awt.GridBagConstraints();
            constraintsConstAndTrend.gridx = 1;
            constraintsConstAndTrend.gridy = 1;
            constraintsConstAndTrend.gridwidth = 2;
            constraintsConstAndTrend.fill = java.awt.GridBagConstraints.HORIZONTAL;
            constraintsConstAndTrend.anchor = java.awt.GridBagConstraints.WEST;
            add(getConstAndTrend(), constraintsConstAndTrend);

            java.awt.GridBagConstraints constraintsOrthConstAndTrend = new java.awt.GridBagConstraints();
            constraintsOrthConstAndTrend.gridx = 1;
            constraintsOrthConstAndTrend.gridy = 2;
            constraintsOrthConstAndTrend.gridwidth = 2;
            constraintsOrthConstAndTrend.fill = java.awt.GridBagConstraints.HORIZONTAL;
            constraintsOrthConstAndTrend.anchor = java.awt.GridBagConstraints.WEST;
            constraintsOrthConstAndTrend.insets = new java.awt.Insets(0, 0, 5,
                    0);
            add(getOrthConstAndTrend(), constraintsOrthConstAndTrend);

            java.awt.GridBagConstraints constraintsMaxLagLength = new java.awt.GridBagConstraints();
            constraintsMaxLagLength.gridx = 1;
            constraintsMaxLagLength.gridy = 5;
            constraintsMaxLagLength.anchor = java.awt.GridBagConstraints.WEST;
            constraintsMaxLagLength.insets = new java.awt.Insets(0, 0, 10, 0);
            add(getMaxLagLength(), constraintsMaxLagLength);

            java.awt.GridBagConstraints constraintsJLabel21 = new java.awt.GridBagConstraints();
            constraintsJLabel21.gridx = 2;
            constraintsJLabel21.gridy = 5;
            constraintsJLabel21.fill = java.awt.GridBagConstraints.HORIZONTAL;
            constraintsJLabel21.anchor = java.awt.GridBagConstraints.WEST;
            constraintsJLabel21.insets = new java.awt.Insets(0, 5, 10, 0);
            add(getJLabel21(), constraintsJLabel21);

            java.awt.GridBagConstraints constraintsSeasonalDummies = new java.awt.GridBagConstraints();
            constraintsSeasonalDummies.gridx = 1;
            constraintsSeasonalDummies.gridy = 6;
            constraintsSeasonalDummies.gridwidth = 2;
            constraintsSeasonalDummies.fill = java.awt.GridBagConstraints.HORIZONTAL;
            constraintsSeasonalDummies.anchor = java.awt.GridBagConstraints.WEST;
            constraintsSeasonalDummies.insets = new java.awt.Insets(0, 0, 5, 0);
            add(getSeasonalDummies(), constraintsSeasonalDummies);

            java.awt.GridBagConstraints constraintsJScrollPane1 = new java.awt.GridBagConstraints();
            constraintsJScrollPane1.gridx = 1;
            constraintsJScrollPane1.gridy = 7;
            constraintsJScrollPane1.gridwidth = 2;
            constraintsJScrollPane1.fill = java.awt.GridBagConstraints.BOTH;
            constraintsJScrollPane1.anchor = java.awt.GridBagConstraints.WEST;
            constraintsJScrollPane1.weighty = 1.0;
            constraintsJScrollPane1.insets = new java.awt.Insets(0, 0, 5, 5);
            gridBagConstraints1.gridx = 1;
            gridBagConstraints1.gridy = 3;
            gridBagConstraints1.gridwidth = 2;
            gridBagConstraints1.insets = new java.awt.Insets(0, 0, 5, 5);
            gridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
            add(getJScrollPane1(), constraintsJScrollPane1);
            this.add(getBreakSpecButton(), gridBagConstraints1);
            initConnections();
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}
        getDummyCheckList().setSymbolName(CointPanel.COINT_DETNAMES.name);
        global().get(CointPanel.COINT_DET);
        global().get(CointPanel.COINT_DETNAMES);
        global().get(CointPanel.COINT_DRANGE);
        global().get(CointPanel.COINT_ENDDATA);
        global().get(CointPanel.COINT_ENDNAMES);

        global().get(CointPanel.COINT_DRANGE).addSymbolListener(
                new SymbolListener() {
                    public void valueChanged(SymbolEvent evt) {
                        getBreakSpecDialog().reset();
                        getBreakSpecButton().setText("Set Breaks (none)");
                    }
                });
        // user code end
    }

    /**
     * This method initializes jButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getBreakSpecButton() {
        if (jButton == null) {
            jButton = new JButton();
            jButton.setPreferredSize(new java.awt.Dimension(34, 27));
            jButton.setText("Set Breaks (none)");
            jButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    JSCDRange range = global().get(CointPanel.COINT_DRANGE)
                            .getJSCDRange();
                    if (range == null || range.isEmpty()) {
                        StdMessages
                                .infoNothingSelected("Please select some variables before.");
                        return;
                    }

                    getBreakSpecDialog().setDateRange(range.getTSDateRange(),
                            getLagLength().getIntNumber());
                    getBreakSpecDialog().setVisible(true);
                    TSDate[] breaks = getBreakSpecDialog().getBreakDates();
                    if (breaks.length == 0)
                        jButton.setText("Set Breaks (none)");
                    if (breaks.length == 1)
                        jButton.setText("Set Breaks (1)");
                    if (breaks.length == 2)
                        jButton.setText("Set Breaks (2)");
                }
            });
        }
        return jButton;
    }

    /**
     * @return
     */
    private JohBreakSpecDialog getBreakSpecDialog() {
        if (breakSpecDialog == null) {
            breakSpecDialog = new JohBreakSpecDialog();
            breakSpecDialog.setLocationRelativeTo(this);
        }
        return breakSpecDialog;
    }
}