/*
 This file is part of the econometric software package JMulTi.
 Copyright (C) 2005  Markus Kraetzig

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

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.Arrays;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import com.jstatcom.component.StdMessages;
import com.jstatcom.component.TopFrameReference;

import com.jstatcom.ts.TSDate;
import com.jstatcom.ts.TSDateRange;
import com.jstatcom.ts.TSDateSelector;

import javax.swing.JRadioButton;
import javax.swing.JLabel;

/**
 * Dialog to specify up to 2 breaks in levels and trends for the Johansen Trace
 * test.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
public final class JohBreakSpecDialog extends JDialog {

    private TSDate[] breakDates = new TSDate[0];

    private JPanel jPanel = null;

    private JPanel jPanel1 = null;

    private JPanel jPanel2 = null;

    private JButton okButton = null;

    private JButton cancelButton = null;

    private TSDateSelector break1 = null;

    private TSDateSelector break2 = null;

    private JRadioButton levelsRadio = null;

    private JRadioButton trendRadio = null;

    private JRadioButton noBreaksRadio = null;

    private JLabel jLabel = null;

    private JLabel jLabel1 = null;

    private JRadioButton oneBreakRadio = null;

    private JRadioButton twoBreaksRadio = null;

    private JLabel jLabel2 = null;

    private JLabel jLabel3 = null;

    /**
     * This method initializes
     * 
     */
    public JohBreakSpecDialog() {
        super(TopFrameReference.getTopFrameRef());
        initialize();
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
        this.setResizable(false);
        this.setModal(true);
        this.setContentPane(getJPanel());
        this.setTitle("Specify Breaks in Levels/Trend");
        this.setSize(450, 330);

        ButtonGroup b1 = new ButtonGroup();
        b1.add(levelsRadio);
        b1.add(trendRadio);

        ButtonGroup b2 = new ButtonGroup();
        b2.add(noBreaksRadio);
        b2.add(oneBreakRadio);
        b2.add(twoBreaksRadio);
    }

    /**
     * This method initializes jPanel
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJPanel() {
        if (jPanel == null) {
            jPanel = new JPanel();
            jPanel.setLayout(new BorderLayout());
            jPanel.add(getJPanel1(), java.awt.BorderLayout.CENTER);
            jPanel.add(getJPanel2(), java.awt.BorderLayout.SOUTH);
        }
        return jPanel;
    }

    /**
     * This method initializes jPanel1
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJPanel1() {
        if (jPanel1 == null) {
            jLabel3 = new JLabel();
            jLabel2 = new JLabel();
            jLabel1 = new JLabel();
            jLabel = new JLabel();
            jPanel1 = new JPanel();
            jPanel1.setLayout(null);
            jPanel1.setBorder(javax.swing.BorderFactory
                    .createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
            jPanel1.setEnabled(false);
            jLabel
                    .setText("Break type (breaks are ignored for \"Orthogonal trend\")");
            jLabel.setLocation(20, 10);
            jLabel.setSize(383, 20);
            jLabel1.setText("Number of Breaks");
            jLabel1.setLocation(20, 110);
            jLabel1.setSize(124, 25);
            jLabel2.setText("1st break date");
            jLabel2.setLocation(310, 170);
            jLabel2.setSize(94, 20);
            jLabel2.setEnabled(false);
            jLabel3.setText("2nd break date");
            jLabel3.setLocation(310, 200);
            jLabel3.setSize(99, 20);
            jLabel3.setEnabled(false);
            jPanel1.add(getBreak1(), null);
            jPanel1.add(getBreak2(), null);
            jPanel1.add(getLevelsRadio(), null);
            jPanel1.add(getTrendRadio(), null);
            jPanel1.add(getNoBreaksRadio(), null);
            jPanel1.add(jLabel, null);
            jPanel1.add(jLabel1, null);
            jPanel1.add(getOneBreakRadio(), null);
            jPanel1.add(getTwoBreaksRadio(), null);
            jPanel1.add(jLabel2, null);
            jPanel1.add(jLabel3, null);
        }
        return jPanel1;
    }

    /**
     * This method initializes jPanel2
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJPanel2() {
        if (jPanel2 == null) {
            FlowLayout flowLayout2 = new FlowLayout();
            jPanel2 = new JPanel();
            jPanel2.setLayout(flowLayout2);
            jPanel2.setPreferredSize(new java.awt.Dimension(10, 50));
            flowLayout2.setVgap(10);
            flowLayout2.setHgap(100);
            jPanel2.add(getOkButton(), null);
            jPanel2.add(getCancelButton(), null);
        }
        return jPanel2;
    }

    /**
     * This method initializes okButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getOkButton() {
        if (okButton == null) {
            okButton = new JButton();
            okButton.setText("OK");
            okButton.setPreferredSize(new java.awt.Dimension(100, 26));
            okButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    // do checks and set indices
                    if (getNoBreaksRadio().isSelected())
                        breakDates = new TSDate[0];

                    else if (getOneBreakRadio().isSelected())
                        breakDates = new TSDate[] { getBreak1().getTSDate() };

                    else if (getTwoBreaksRadio().isSelected()) {
                        if (getBreak1().getTSDate().equals(
                                getBreak2().getTSDate())) {
                            StdMessages
                                    .errorInput("Please choose different dates for the two breaks or select just one.");
                            return;
                        }

                        // update break indices
                        breakDates = new TSDate[] { getBreak1().getTSDate(),
                                getBreak2().getTSDate() };
                    }
                    setVisible(false);
                }
            });
        }
        return okButton;
    }

    /**
     * This method initializes cancelButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getCancelButton() {
        if (cancelButton == null) {
            cancelButton = new JButton();
            cancelButton.setText("Cancel");
            cancelButton.setPreferredSize(new java.awt.Dimension(100, 26));
            cancelButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    // nothing to do, do not update break indices
                    setVisible(false);
                }
            });
        }
        return cancelButton;
    }

    /**
     * This method initializes levelBreak1
     * 
     * @return com.jstatcom.ts.levelBreak1
     */
    private TSDateSelector getBreak1() {
        if (break1 == null) {
            break1 = new TSDateSelector();
            break1.setLocation(190, 170);
            break1.setSize(110, 20);
            break1.setEnabled(false);
        }
        return break1;
    }

    /**
     * This method initializes levelBreak2
     * 
     * @return com.jstatcom.ts.TSDateSelector
     */
    private TSDateSelector getBreak2() {
        if (break2 == null) {
            break2 = new TSDateSelector();
            break2.setLocation(190, 200);
            break2.setSize(110, 20);
            break2.setEnabled(false);
        }
        return break2;
    }

    /**
     * This method initializes jRadioButton
     * 
     * @return javax.swing.JRadioButton
     */
    private JRadioButton getLevelsRadio() {
        if (levelsRadio == null) {
            levelsRadio = new JRadioButton();
            levelsRadio
                    .setText("Break(s) in levels only (for \"Constant\" and \"Constant and trend\")");
            levelsRadio.setSelected(true);
            levelsRadio.setSize(383, 21);
            levelsRadio.setLocation(30, 40);
        }
        return levelsRadio;
    }

    /**
     * This method initializes jRadioButton
     * 
     * @return javax.swing.JRadioButton
     */
    private JRadioButton getTrendRadio() {
        if (trendRadio == null) {
            trendRadio = new JRadioButton();
            trendRadio
                    .setText("Break(s) in levels and trend jointly (only used for \"Constant and trend\" )");
            trendRadio.setSize(400, 21);
            trendRadio.setLocation(30, 70);
        }
        return trendRadio;
    }

    /**
     * This method initializes jRadioButton1
     * 
     * @return javax.swing.JRadioButton
     */
    private JRadioButton getNoBreaksRadio() {
        if (noBreaksRadio == null) {
            noBreaksRadio = new JRadioButton();
            noBreaksRadio.setText("None");
            noBreaksRadio.setSelected(true);
            noBreaksRadio.setSize(188, 21);
            noBreaksRadio.setLocation(30, 140);
            noBreaksRadio
                    .addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent e) {
                            getBreak1().setEnabled(false);
                            getBreak2().setEnabled(false);
                            jLabel2.setEnabled(false);
                            jLabel3.setEnabled(false);
                        }
                    });
        }
        return noBreaksRadio;
    }

    /**
     * This method initializes jRadioButton2
     * 
     * @return javax.swing.JRadioButton
     */
    private JRadioButton getOneBreakRadio() {
        if (oneBreakRadio == null) {
            oneBreakRadio = new JRadioButton();
            oneBreakRadio.setBounds(30, 170, 163, 21);
            oneBreakRadio.setText("One break");
            oneBreakRadio
                    .addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent e) {
                            getBreak1().setEnabled(true);
                            getBreak2().setEnabled(false);
                            jLabel2.setEnabled(true);
                            jLabel3.setEnabled(false);
                        }
                    });
        }
        return oneBreakRadio;
    }

    /**
     * This method initializes jRadioButton3
     * 
     * @return javax.swing.JRadioButton
     */
    private JRadioButton getTwoBreaksRadio() {
        if (twoBreaksRadio == null) {
            twoBreaksRadio = new JRadioButton();
            twoBreaksRadio.setText("Two breaks");
            twoBreaksRadio.setSize(163, 21);
            twoBreaksRadio.setLocation(30, 200);
            twoBreaksRadio
                    .addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent e) {
                            getBreak1().setEnabled(true);
                            getBreak2().setEnabled(true);
                            jLabel2.setEnabled(true);
                            jLabel3.setEnabled(true);
                        }
                    });
        }
        return twoBreaksRadio;
    }

    /**
     * Sets the allowed range for the two date selecting text fields. It adds
     * the number of lags + 1 to avoid a shift starting from the first
     * observation in the sample range.
     * 
     * @param range
     *            the original number of lags
     * @param levelsLags
     *            the number of lags in levels
     * @throws IllegalArgumentException
     *             <code>if (range == null || levelsLags < 0)</code>
     */
    public void setDateRange(TSDateRange range, int levelsLags) {
        if (range == null || levelsLags < 0)
            throw new IllegalArgumentException(
                    "range == null || levelsLags  < 0");
        TSDateRange newRange = new TSDateRange(range.lowerBound().addPeriods(
                levelsLags + 2), range.upperBound().addPeriods(-levelsLags - 1));
        getBreak1().setEnclosingRange(newRange);
        getBreak2().setEnclosingRange(newRange);
    }

    /**
     * Gets dates with breaks. This array also holds information whether none,
     * 1, or 2 breaks are specified. The returned dates are sorted in descending
     * order.
     * 
     * @return 0x0, 1x1, or 2x1 int array, 1st break, 2nd break
     */
    public TSDate[] getBreakDates() {
        Arrays.sort(breakDates);
        return breakDates;

    }

    /**
     * Gets whether there is a break in levels and trend or just in levels.
     * 
     * @return <code>true</code> break in trend and levels; <code>false</code>
     *         break only in levels
     */
    public boolean isTrendBreak() {
        return getTrendRadio().isSelected();
    }

    /**
     * Sets selection to none and sets break indices to empty.
     * 
     */
    public void reset() {
        breakDates = new TSDate[0];
        getNoBreaksRadio().setSelected(true);
    }
} // @jve:decl-index=0:visual-constraint="10,10"
