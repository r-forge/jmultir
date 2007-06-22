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

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import org.apache.log4j.Logger;

import com.jstatcom.component.ResultField;
import com.jstatcom.engine.PCall;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.ModelPanel;
import com.jstatcom.ts.TSDate;
import com.jstatcom.ts.TSDateRange;
import com.jstatcom.ts.TSDateSelector;

import de.jmulti.proc.VECMRecEigenCall;

/**
 * Recursive eigenvalue computation for VECM models.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public final class VECMRecEigenPanel extends ModelPanel {
    private static final Logger log = Logger.getLogger(VECMRecEigenPanel.class);

    // Needed to compute indices according to original range.
    private TSDateRange origDateRange = null;

    private JButton ivjExecute = null;

    private JLabel ivjJLabel11 = null;

    private JPanel ivjJPanel1 = null;

    private ResultField ivjResultField = null;

    private TSDateSelector ivjStartDate = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private JComboBox ivjConcCombo = null;

    private JComboBox ivjRecursCICombo = null;

    private JLabel ivjJLabel = null;

    private JLabel ivjJLabel2 = null;

    private JLabel ivjJLabel3 = null;

    private JComboBox ivjTauSignCombo = null;

    class IvjEventHandler implements java.awt.event.ActionListener {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == VECMRecEigenPanel.this.getExecute())
                connEtoC1();
        };
    };

    /**
     * VECMRecEigenPanel constructor comment.
     */
    public VECMRecEigenPanel() {
        super();
        initialize();
    }

    /**
     * connEtoC1: (Execute.action. -->
     * VECMRecEigenPanel.execute_ActionEvents()V)
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
     * Execute recursive eigenvalue computation.
     */
    private void execute_ActionEvents() {
        // Index for GAUSS starting from 1, therefore + 1. Index is computed
        // from original range.
        int startPoint = origDateRange.indexForDate(getStartDate().getTSDate());

        double tauSign = 0.95;
        String index = getTauSignCombo().getSelectedItem().toString();
        if (index.equals("10%"))
            tauSign = 0.90;
        else if (index.equals("1%"))
            tauSign = 0.99;

        int estMethod = getConcCombo().getSelectedIndex();
        int ciMethod = getRecursCICombo().getSelectedIndex();

        PCall job = new VECMRecEigenCall(global(), local(), startPoint,
                estMethod, ciMethod, tauSign);
        job.execute();
    }

    /**
     * Return the SignificanceCombo property value.
     * 
     * @return javax.swing.JComboBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JComboBox getConcCombo() {
        if (ivjConcCombo == null) {
            try {
                ivjConcCombo = new javax.swing.JComboBox();
                ivjConcCombo.setName("ConcCombo");
                ivjConcCombo.setPreferredSize(new java.awt.Dimension(200, 23));
                ivjConcCombo.setMinimumSize(new java.awt.Dimension(100, 23));
                // user code begin {1}
                ivjConcCombo.addItem("using concentrated likelihood func.");
                ivjConcCombo.addItem("recursively estimate all parameters");
                ivjConcCombo.setSelectedIndex(0);
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjConcCombo;
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
                ivjExecute.setPreferredSize(new java.awt.Dimension(105, 25));
                ivjExecute.setText("Execute");
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
                ivjJLabel.setPreferredSize(new java.awt.Dimension(160, 21));
                ivjJLabel.setText("Likelihood function estimation");
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
                ivjJLabel11.setPreferredSize(new java.awt.Dimension(100, 21));
                ivjJLabel11.setText("Start date");
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
                ivjJLabel2.setPreferredSize(new java.awt.Dimension(160, 21));
                ivjJLabel2.setText("Eigenvalue CI estimation");
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
                ivjJLabel3.setPreferredSize(new java.awt.Dimension(180, 21));
                ivjJLabel3.setText("Sign. level for Tau-stability tests");
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
                ivjJPanel1.setBorder(new BevelBorder(BevelBorder.LOWERED));
                ivjJPanel1.setLayout(new java.awt.GridBagLayout());

                java.awt.GridBagConstraints constraintsExecute = new java.awt.GridBagConstraints();
                constraintsExecute.gridx = 2;
                constraintsExecute.gridy = 4;
                constraintsExecute.gridwidth = 3;
                constraintsExecute.anchor = java.awt.GridBagConstraints.SOUTHEAST;
                constraintsExecute.weightx = 1.0;
                constraintsExecute.weighty = 1.0;
                constraintsExecute.insets = new java.awt.Insets(0, 10, 10, 10);
                getJPanel1().add(getExecute(), constraintsExecute);

                java.awt.GridBagConstraints constraintsStartDate = new java.awt.GridBagConstraints();
                constraintsStartDate.gridx = 0;
                constraintsStartDate.gridy = 0;
                constraintsStartDate.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsStartDate.insets = new java.awt.Insets(10, 10, 0, 0);
                getJPanel1().add(getStartDate(), constraintsStartDate);

                java.awt.GridBagConstraints constraintsJLabel11 = new java.awt.GridBagConstraints();
                constraintsJLabel11.gridx = 1;
                constraintsJLabel11.gridy = 0;
                constraintsJLabel11.anchor = java.awt.GridBagConstraints.WEST;
                constraintsJLabel11.insets = new java.awt.Insets(10, 5, 0, 0);
                getJPanel1().add(getJLabel11(), constraintsJLabel11);

                java.awt.GridBagConstraints constraintsConcCombo = new java.awt.GridBagConstraints();
                constraintsConcCombo.gridx = 4;
                constraintsConcCombo.gridy = 1;
                constraintsConcCombo.gridwidth = 5;
                constraintsConcCombo.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsConcCombo.anchor = java.awt.GridBagConstraints.WEST;
                constraintsConcCombo.insets = new java.awt.Insets(10, 10, 0, 10);
                getJPanel1().add(getConcCombo(), constraintsConcCombo);

                java.awt.GridBagConstraints constraintsRecursCICombo = new java.awt.GridBagConstraints();
                constraintsRecursCICombo.gridx = 4;
                constraintsRecursCICombo.gridy = 3;
                constraintsRecursCICombo.gridwidth = 5;
                constraintsRecursCICombo.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsRecursCICombo.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsRecursCICombo.insets = new java.awt.Insets(10, 10,
                        0, 10);
                getJPanel1().add(getRecursCICombo(), constraintsRecursCICombo);

                java.awt.GridBagConstraints constraintsJLabel = new java.awt.GridBagConstraints();
                constraintsJLabel.gridx = 4;
                constraintsJLabel.gridy = 0;
                constraintsJLabel.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel.anchor = java.awt.GridBagConstraints.WEST;
                constraintsJLabel.insets = new java.awt.Insets(10, 10, 0, 10);
                getJPanel1().add(getJLabel(), constraintsJLabel);

                java.awt.GridBagConstraints constraintsJLabel2 = new java.awt.GridBagConstraints();
                constraintsJLabel2.gridx = 4;
                constraintsJLabel2.gridy = 2;
                constraintsJLabel2.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel2.anchor = java.awt.GridBagConstraints.WEST;
                constraintsJLabel2.insets = new java.awt.Insets(10, 10, 0, 10);
                getJPanel1().add(getJLabel2(), constraintsJLabel2);

                java.awt.GridBagConstraints constraintsTauSignCombo = new java.awt.GridBagConstraints();
                constraintsTauSignCombo.gridx = 0;
                constraintsTauSignCombo.gridy = 1;
                constraintsTauSignCombo.gridwidth = 2;
                constraintsTauSignCombo.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsTauSignCombo.insets = new java.awt.Insets(10, 10, 0,
                        0);
                getJPanel1().add(getTauSignCombo(), constraintsTauSignCombo);

                java.awt.GridBagConstraints constraintsJLabel3 = new java.awt.GridBagConstraints();
                constraintsJLabel3.gridx = 1;
                constraintsJLabel3.gridy = 1;
                constraintsJLabel3.anchor = java.awt.GridBagConstraints.WEST;
                constraintsJLabel3.insets = new java.awt.Insets(10, 5, 0, 0);
                getJPanel1().add(getJLabel3(), constraintsJLabel3);
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
     * Return the RecursCICombo property value.
     * 
     * @return javax.swing.JComboBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JComboBox getRecursCICombo() {
        if (ivjRecursCICombo == null) {
            try {
                ivjRecursCICombo = new javax.swing.JComboBox();
                ivjRecursCICombo.setName("RecursCICombo");
                ivjRecursCICombo.setPreferredSize(new java.awt.Dimension(200,
                        23));
                ivjRecursCICombo
                        .setMinimumSize(new java.awt.Dimension(100, 23));
                // user code begin {1}
                ivjRecursCICombo
                        .addItem("full sample estimates of standard errors");
                ivjRecursCICombo
                        .addItem("recursive sample estimates of standard errors");
                ivjRecursCICombo.setSelectedIndex(0);
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjRecursCICombo;
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
     * Return the StartDate property value.
     * 
     * @return com.jstatcom.ts.TSDateSelector
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private com.jstatcom.ts.TSDateSelector getStartDate() {
        if (ivjStartDate == null) {
            try {
                ivjStartDate = new com.jstatcom.ts.TSDateSelector();
                ivjStartDate.setName("StartDate");
                ivjStartDate.setPreferredSize(new java.awt.Dimension(100, 21));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjStartDate;
    }

    /**
     * Return the TauSignCombo property value.
     * 
     * @return javax.swing.JComboBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JComboBox getTauSignCombo() {
        if (ivjTauSignCombo == null) {
            try {
                ivjTauSignCombo = new javax.swing.JComboBox();
                ivjTauSignCombo.setName("TauSignCombo");
                ivjTauSignCombo
                        .setPreferredSize(new java.awt.Dimension(100, 23));
                ivjTauSignCombo.setMinimumSize(new java.awt.Dimension(100, 23));
                // user code begin {1}
                ivjTauSignCombo.addItem("1%");
                ivjTauSignCombo.addItem("5%");
                ivjTauSignCombo.addItem("10%");
                ivjTauSignCombo.setSelectedIndex(0);

                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjTauSignCombo;
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
            // user code end
            setName("VECMRecEigenPanel");
            setLayout(new java.awt.BorderLayout());
            setSize(591, 375);
            add(getJPanel1(), "North");
            add(getResultField(), "Center");
            initConnections();
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}
        // user code end
    }

    public void shown(boolean isShown) {
        if (isShown) {
            // set the date text field and the possible range of dates
            TSDateRange range = global().get(VECMConstants.T1_Def)
                    .getJSCDRange().getTSDateRange();
            int nr = global().get(VECMConstants.u_hat_Def).getJSCNArray()
                    .rows();

            // First possible value : cols(y)*(py+1) + cols(d) + cols(y)
            // degfree are one less than 1st possible.
            JSCNArray y = global().get(VECMConstants.y_Def).getJSCNArray();
            int degfree = (global().get(VECMConstants.py_Def).getJSCInt()
                    .intVal() + 1)
                    * y.cols()
                    + global().get(VECMConstants.d_Def).getJSCNArray().cols()
                    + y.cols() - 1;

            // If the index is retrieved, it must be computed relative to the
            // original
            // start date!
            TSDate start = range.lowerBound();
            origDateRange = range;
            TSDate firstPossibleDate = start.addPeriods(degfree);
            getStartDate().setEnclosingRange(firstPossibleDate,
                    nr - degfree - 1);
        }

    }
}