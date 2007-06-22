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

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import org.apache.log4j.Logger;

import com.jstatcom.component.CompSettings;
import com.jstatcom.component.NumSelector;
import com.jstatcom.component.ResultField;
import com.jstatcom.component.StdMessages;
import com.jstatcom.engine.PCall;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.JSCSArray;
import com.jstatcom.model.ModelPanel;
import com.jstatcom.project.OutputData;
import com.jstatcom.project.OutputList;
import com.jstatcom.ts.TSDateRange;
import com.jstatcom.util.FArg;
import com.jstatcom.util.UMatrix;

import de.jmulti.proc.UnivarARCHLMCall;
import de.jmulti.proc.UnivarJBeraCall;

/**
 * A panel to compute and display various statistics.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
public class StatsPanel extends ModelPanel {
    private static final Logger log = Logger.getLogger(StatsPanel.class);

    private NumSelector ivjArchLags = null;

    private JCheckBox ivjArchLM = null;

    private JCheckBox ivjBeraJarque = null;

    private JCheckBox ivjDescript = null;

    private JButton ivjExecute = null;

    private JLabel ivjJLabel2 = null;

    private ResultField ivjResultField = null;

    private JPanel ivjStatsTab = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    class IvjEventHandler implements java.awt.event.ActionListener,
            java.awt.event.ItemListener {

        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == StatsPanel.this.getExecute())
                connEtoC4();
        };

        public void itemStateChanged(java.awt.event.ItemEvent e) {
            if (e.getSource() == StatsPanel.this.getArchLM())
                connPtoP1SetTarget();
            if (e.getSource() == StatsPanel.this.getArchLM())
                connPtoP2SetTarget();
        };
    };

    /**
     * StatsPanel constructor comment.
     */
    public StatsPanel() {
        super();
        initialize();
    }

    /**
     * connEtoC4: (Execute.action. --> StatsPanel.execute_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC4() {
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
     * connPtoP1SetTarget: (ArchLM.selected <-->JLabel2.enabled)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connPtoP1SetTarget() {
        /* Set the target from the source */
        try {
            getJLabel2().setEnabled(getArchLM().isSelected());
            // user code begin {1}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connPtoP2SetTarget: (ArchLM.selected <-->ArchLags.enabled)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connPtoP2SetTarget() {
        /* Set the target from the source */
        try {
            getArchLags().setEnabled(getArchLM().isSelected());
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
    private void execute_ActionEvents() {
        if (global().get(WorkbenchPanel.WB_DATA).isEmpty()) {
            StdMessages
                    .infoNothingSelected("Please select at least one series.");
            return;
        }

        TSDateRange range = global().get(WorkbenchPanel.WB_DRANGE)
                .getJSCDRange().getTSDateRange();

        StringBuffer buffer = new StringBuffer();
        buffer.append("*** " + CompSettings.getDateString() + " ***\n");
        buffer.append(range.format("sample range:", 16) + "\n\n");

        if (!getDescript().isSelected() && !getBeraJarque().isSelected()
                && !getArchLM().isSelected()) {
            StdMessages
                    .infoNothingSelected("Please select at least one option.");
            return;
        }
        JSCNArray jscData = global().get(WorkbenchPanel.WB_DATA).getJSCNArray();
        JSCSArray jscNames = global().get(WorkbenchPanel.WB_DATANAMES)
                .getJSCSArray();

        if (getDescript().isSelected()) {
            double[][] data = jscData.doubleArray();
            double[] means = UMatrix.meanc(data);
            double[] mins = UMatrix.minc(data);
            double[] maxs = UMatrix.maxc(data);
            double[] stds = UMatrix.stdc(data);

            buffer.append("DESCRIPTIVE STATISTICS:\n\n");
            buffer.append(FArg.sprintf("%-15s %-12s %-12s %-12s %-12s\n",
                    new FArg("variable").add("mean").add("min").add("max").add(
                            "std. dev.")));
            for (int i = 0; i < jscNames.rows(); i++) {
                buffer.append(FArg.sprintf(
                        "%- 14s %- 8.5e %- 8.5e %- 8.5e %- 8.5e", new FArg(
                                jscNames.stringAt(i, 0)).add(means[i]).add(
                                mins[i]).add(maxs[i]).add(stds[i])));
                buffer.append("\n");
            }
            buffer.append("\n");
        }
        if (buffer.length() > 0)
            OutputList.getInstance().addOutput(
                    new OutputData("Summary Statistics", buffer.toString()));

        getResultField().append(buffer.toString());

        if (getBeraJarque().isSelected()) {
            PCall job = new UnivarJBeraCall(jscData, jscNames);
            job.setOutHolder(getResultField());
            job.setSymbolTable(local());
            job.setPrintDate(false);
            job.execute();
        }

        if (getArchLM().isSelected()) {
            PCall job = new UnivarARCHLMCall(jscData, jscNames, getArchLags()
                    .getIntNumber());
            job.setOutHolder(getResultField());
            job.setSymbolTable(local());
            job.setPrintDate(false);
            job.execute();
        }

    }

    /**
     * Return the ArchLags property value.
     * 
     * @return com.jstatcom.model.NumberSelector
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private NumSelector getArchLags() {
        if (ivjArchLags == null) {
            try {
                ivjArchLags = new NumSelector();
                ivjArchLags.setName("ArchLags");
                ivjArchLags.setNumber(2.0);
                ivjArchLags.setRangeExpr("[1,100]");
                ivjArchLags.setPreferredSize(new java.awt.Dimension(35, 21));
                ivjArchLags.setMinimumSize(new java.awt.Dimension(35, 21));
                ivjArchLags.setHorizontalAlignment(SwingConstants.LEFT);
                ivjArchLags.setEnabled(false);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjArchLags;
    }

    /**
     * Return the ArchLM property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getArchLM() {
        if (ivjArchLM == null) {
            try {
                ivjArchLM = new javax.swing.JCheckBox();
                ivjArchLM.setName("ArchLM");
                ivjArchLM.setText("ARCH-LM Test");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjArchLM;
    }

    /**
     * Return the BeraJarque property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getBeraJarque() {
        if (ivjBeraJarque == null) {
            try {
                ivjBeraJarque = new javax.swing.JCheckBox();
                ivjBeraJarque.setName("BeraJarque");
                ivjBeraJarque.setText("Jarque-Bera Test");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjBeraJarque;
    }

    /**
     * Return the Descript property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getDescript() {
        if (ivjDescript == null) {
            try {
                ivjDescript = new javax.swing.JCheckBox();
                ivjDescript.setName("Descript");
                ivjDescript.setSelected(true);
                ivjDescript.setText("Descriptive statistics");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjDescript;
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
                ivjExecute.setText("Execute");
                ivjExecute.setPreferredSize(new java.awt.Dimension(120, 27));
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
                ivjJLabel2.setText("lags");
                ivjJLabel2.setEnabled(false);
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
                ivjResultField.setBorder(new BevelBorder(BevelBorder.LOWERED));
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
     * Return the StatsTab property value.
     * 
     * @return javax.swing.JPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JPanel getStatsTab() {
        if (ivjStatsTab == null) {
            try {
                ivjStatsTab = new javax.swing.JPanel();
                ivjStatsTab.setName("StatsTab");
                ivjStatsTab.setPreferredSize(new java.awt.Dimension(200, 100));
                ivjStatsTab.setBorder(new BevelBorder(BevelBorder.LOWERED));
                ivjStatsTab.setLayout(new java.awt.GridBagLayout());

                java.awt.GridBagConstraints constraintsExecute = new java.awt.GridBagConstraints();
                constraintsExecute.gridx = 2;
                constraintsExecute.gridy = 2;
                constraintsExecute.gridheight = 2;
                constraintsExecute.anchor = java.awt.GridBagConstraints.EAST;
                constraintsExecute.weightx = 1.0;
                constraintsExecute.insets = new java.awt.Insets(0, 0, 5, 5);
                getStatsTab().add(getExecute(), constraintsExecute);

                java.awt.GridBagConstraints constraintsDescript = new java.awt.GridBagConstraints();
                constraintsDescript.gridx = 0;
                constraintsDescript.gridy = 0;
                constraintsDescript.anchor = java.awt.GridBagConstraints.SOUTHWEST;
                constraintsDescript.weighty = 1.0;
                constraintsDescript.insets = new java.awt.Insets(5, 5, 0, 0);
                getStatsTab().add(getDescript(), constraintsDescript);

                java.awt.GridBagConstraints constraintsBeraJarque = new java.awt.GridBagConstraints();
                constraintsBeraJarque.gridx = 0;
                constraintsBeraJarque.gridy = 1;
                constraintsBeraJarque.anchor = java.awt.GridBagConstraints.WEST;
                constraintsBeraJarque.insets = new java.awt.Insets(0, 5, 0, 0);
                getStatsTab().add(getBeraJarque(), constraintsBeraJarque);

                java.awt.GridBagConstraints constraintsArchLM = new java.awt.GridBagConstraints();
                constraintsArchLM.gridx = 0;
                constraintsArchLM.gridy = 2;
                constraintsArchLM.anchor = java.awt.GridBagConstraints.WEST;
                constraintsArchLM.insets = new java.awt.Insets(0, 5, 0, 0);
                getStatsTab().add(getArchLM(), constraintsArchLM);

                java.awt.GridBagConstraints constraintsArchLags = new java.awt.GridBagConstraints();
                constraintsArchLags.gridx = 2;
                constraintsArchLags.gridy = 2;
                constraintsArchLags.anchor = java.awt.GridBagConstraints.WEST;
                constraintsArchLags.insets = new java.awt.Insets(0, 5, 0, 0);
                getStatsTab().add(getArchLags(), constraintsArchLags);

                java.awt.GridBagConstraints constraintsJLabel2 = new java.awt.GridBagConstraints();
                constraintsJLabel2.gridx = 1;
                constraintsJLabel2.gridy = 2;
                constraintsJLabel2.anchor = java.awt.GridBagConstraints.WEST;
                constraintsJLabel2.insets = new java.awt.Insets(0, 10, 0, 0);
                getStatsTab().add(getJLabel2(), constraintsJLabel2);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjStatsTab;
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
        getArchLM().addItemListener(ivjEventHandler);
        connPtoP1SetTarget();
        connPtoP2SetTarget();
    }

    /**
     * Initialize the class.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initialize() {
        try {
            // user code begin {1}
            // user code end
            setName("StatsPanel");
            setLayout(new java.awt.BorderLayout());
            setSize(568, 329);
            add(getResultField(), "Center");
            add(getStatsTab(), "North");
            initConnections();
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}
        global().get(WorkbenchPanel.WB_DATA);
        global().get(WorkbenchPanel.WB_DATANAMES);
        global().get(WorkbenchPanel.WB_DRANGE);

        // user code end
    }
}