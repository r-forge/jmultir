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

package de.jmulti.vecm;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import com.jstatcom.component.ResultField;
import com.jstatcom.component.StdMessages;
import com.jstatcom.engine.PCall;
import com.jstatcom.engine.PCallAdapter;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.JSCSArray;
import com.jstatcom.model.ModelPanel;
import com.jstatcom.util.FArg;
import com.jstatcom.util.UData;

import de.jmulti.proc.BetaResWaldCall;

/**
 * Panel for specifying the restrictions on the cointegration space.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
public final class VECMWaldBetaPanel extends ModelPanel {
    private static final Logger log = Logger.getLogger(VECMWaldBetaPanel.class);

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private JButton ivjExecuteButton = null;

    private JPanel ivjJPanel1 = null;

    private ResultField ivjResultField = null;

    private VECMBetaResSpecPanel ivjBetaResSpecPanel = null;

    private JTabbedPane ivjJTabbedPane1 = null;

    private JButton ivjSetResButton = null;

    class IvjEventHandler implements java.awt.event.ActionListener {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == VECMWaldBetaPanel.this.getExecuteButton())
                connEtoC2();
            if (e.getSource() == VECMWaldBetaPanel.this.getSetResButton())
                connEtoC1();
        };
    };

    /**
     * VECMWaldBetaPanel constructor comment.
     */
    public VECMWaldBetaPanel() {
        super();
        initialize();
    }

    /**
     * Call test procedure.
     */
    private boolean checkRank() {
        JSCNArray resMat = getBetaResSpecPanel().getResMat();
        int rank = resMat.rank();
        if (rank < resMat.rows()) {
            StdMessages
                    .errorSpecification("R contains linearly dependent restrictions (rank = "
                            + rank + ").\nPlease change your specification.");
            return false;
        }
        return true;

    }

    /**
     * connEtoC1: (SetResButton.action. -->
     * VECMWaldBetaPanel.setResButton_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC1() {
        try {
            // user code begin {1}
            // user code end
            this.setResButton_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC2: (ExecuteButton.action. -->
     * VECMWaldBetaPanel.executeButton_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC2() {
        try {
            // user code begin {1}
            // user code end
            this.executeButton_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * Call test procedure.
     */
    private void executeButton_ActionEvents() {
        JSCNArray resMat = getBetaResSpecPanel().getResMat();
        if (!checkRank())
            return;

        PCall job = new BetaResWaldCall(global(), resMat, getBetaResSpecPanel()
                .getRMat());
        job.setOutHolder(getResultField());
        job.addPCallListener(new PCallAdapter() {
            public void success() {
                getJTabbedPane1().setSelectedIndex(1);
            }

        });
        job.execute();

    }

    /**
     * Return the BetaResSpecPanel property value.
     * 
     * @return de.jmulti.vecm.VECMBetaResSpecPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private VECMBetaResSpecPanel getBetaResSpecPanel() {
        if (ivjBetaResSpecPanel == null) {
            try {
                ivjBetaResSpecPanel = new de.jmulti.vecm.VECMBetaResSpecPanel();
                ivjBetaResSpecPanel.setName("BetaResSpecPanel");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjBetaResSpecPanel;
    }

    /**
     * Return the ExecuteButton property value.
     * 
     * @return javax.swing.JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JButton getExecuteButton() {
        if (ivjExecuteButton == null) {
            try {
                ivjExecuteButton = new javax.swing.JButton();
                ivjExecuteButton.setName("ExecuteButton");
                ivjExecuteButton.setPreferredSize(new java.awt.Dimension(200,
                        25));
                ivjExecuteButton.setText("Execute Wald Test (with JOH ML)");
                ivjExecuteButton
                        .setMinimumSize(new java.awt.Dimension(240, 25));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjExecuteButton;
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
                ivjJPanel1.setPreferredSize(new java.awt.Dimension(0, 200));
                ivjJPanel1.setLayout(new java.awt.GridBagLayout());

                java.awt.GridBagConstraints constraintsExecuteButton = new java.awt.GridBagConstraints();
                constraintsExecuteButton.gridx = 1;
                constraintsExecuteButton.gridy = 1;
                constraintsExecuteButton.weightx = 1.0;
                constraintsExecuteButton.insets = new java.awt.Insets(5, 0, 5,
                        0);
                getJPanel1().add(getExecuteButton(), constraintsExecuteButton);

                java.awt.GridBagConstraints constraintsBetaResSpecPanel = new java.awt.GridBagConstraints();
                constraintsBetaResSpecPanel.gridx = 0;
                constraintsBetaResSpecPanel.gridy = 0;
                constraintsBetaResSpecPanel.gridwidth = 2;
                constraintsBetaResSpecPanel.fill = java.awt.GridBagConstraints.BOTH;
                constraintsBetaResSpecPanel.weightx = 1.0;
                constraintsBetaResSpecPanel.weighty = 1.0;
                getJPanel1().add(getBetaResSpecPanel(),
                        constraintsBetaResSpecPanel);

                java.awt.GridBagConstraints constraintsSetResButton = new java.awt.GridBagConstraints();
                constraintsSetResButton.gridx = 0;
                constraintsSetResButton.gridy = 1;
                constraintsSetResButton.weightx = 1.0;
                constraintsSetResButton.insets = new java.awt.Insets(5, 0, 5, 0);
                getJPanel1().add(getSetResButton(), constraintsSetResButton);
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
                ivjJTabbedPane1.insertTab("Specify Wald Test", null,
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
     * Return the ResultField property value.
     * 
     * @return com.jstatcom.component.ResultField
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
     * Return the SetResButton property value.
     * 
     * @return javax.swing.JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JButton getSetResButton() {
        if (ivjSetResButton == null) {
            try {
                ivjSetResButton = new javax.swing.JButton();
                ivjSetResButton.setName("SetResButton");
                ivjSetResButton
                        .setPreferredSize(new java.awt.Dimension(230, 25));
                ivjSetResButton.setText("Set Restrictions for Estimation");
                ivjSetResButton.setMinimumSize(new java.awt.Dimension(230, 25));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSetResButton;
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
        getExecuteButton().addActionListener(ivjEventHandler);
        getSetResButton().addActionListener(ivjEventHandler);
    }

    /**
     * Initialize the class.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initialize() {
        try {
            // user code begin {1}
            // user code end
            setName("VECMWaldBetaPanel");
            setLayout(new BorderLayout());
            setSize(550, 423);
            add(getJTabbedPane1(), BorderLayout.CENTER);
            initConnections();
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}
        TitledBorder title = new TitledBorder(new BevelBorder(
                BevelBorder.LOWERED),
                "Specify Restrictions on Cointegration Space",
                TitledBorder.RIGHT, TitledBorder.TOP);
        getJPanel1().setBorder(title);

        // user code end
    }

    /**
     * Sets the restrictions for estimation.
     */
    private void setResButton_ActionEvents() {
        if (!checkRank())
            return;

        int estStrat = global().get(VECMConstants.estimationStrategy_Def)
                .getJSCInt().intVal();
        int firstStageS2S = global().get(VECMConstants.firstStage_byS2S_Def)
                .getJSCInt().intVal();
        if (!(estStrat == VECMConstants.S2S || (estStrat == VECMConstants.TWO_STAGE && firstStageS2S == 1))) {
            StringBuffer msg = new StringBuffer();
            msg.append("Please note:\n\n");
            msg
                    .append("Restrictions on beta can only be estimated with S2S.\n");
            msg.append("This strategy is currently not selected, therefore\n");
            msg
                    .append("restrictions on beta will be ignored during estimation.\n");
            msg.append("But it is still possible to do the Wald test.");

            StdMessages.infoGeneral(msg.toString());
        }

        JSCNArray[] extRes = UData.imp2ExpRes(
                getBetaResSpecPanel().getResMat(), getBetaResSpecPanel()
                        .getRMat());
        global().get(VECMConstants.resHmat_Def).setJSCData(extRes[0]);
        global().get(VECMConstants.resFreeParamIndex_Def).setJSCData(extRes[1]);
        global().get(VECMConstants.resH_Def).setJSCData(extRes[2]);

        /* Reset estimation if restrictions are used */

        if (estStrat == VECMConstants.S2S
                || estStrat == VECMConstants.TWO_STAGE)
            VECMDataManagerObs.getInstance().removeData();

        StringBuffer buffer = new StringBuffer();
        JSCSArray betaNames = getBetaResSpecPanel().getBetaNam();
        ArrayList<String> etaNames = new ArrayList<String>();
        for (int i = 0; i < betaNames.rows(); i++) {
            if (extRes[1].intAt(i, 0) == 1)
                etaNames.add(betaNames.stringAt(i, 0));
        }
        String[] eta = etaNames.toArray(new String[0]);

        buffer
                .append("Explicit representation of restrictions: vec(beta(k-r))=Hmat*eta+h; eta are free parameters to estimate\n");
        for (int i = 0; i < extRes[0].rows(); i++) {
            for (int j = 0; j < extRes[0].cols(); j++) {

                buffer.append(FArg.sprintf("%- 8.4f", new FArg(extRes[0]
                        .doubleAt(i, j))));

            }
            buffer.append(FArg.sprintf("  %10s %- 8.4f\n", new FArg(
                    (i < eta.length) ? eta[i] : "").add(extRes[2]
                    .doubleAt(i, 0))));

        }
        // In case there are no free parameters at all.
        if (extRes[0].rows() < 1)
            for (int i = 0; i < extRes[2].rows(); i++) {
                buffer.append(FArg.sprintf("%- 8.4f\n", new FArg(extRes[2]
                        .doubleAt(i, 0))));

            }
        buffer.append("\n");
        getResultField().append(buffer.toString());
        getJTabbedPane1().setSelectedIndex(1);
    }

    /**
     * Called when the component is shown or hidden.
     */
    public void shown(boolean isShown) {

        if (isShown)
            getBetaResSpecPanel().update();

    }
}