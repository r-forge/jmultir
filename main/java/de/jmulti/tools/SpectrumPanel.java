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
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import org.apache.log4j.Logger;

import com.jstatcom.component.NumSelector;
import com.jstatcom.component.ResultField;
import com.jstatcom.component.StdMessages;
import com.jstatcom.engine.PCall;
import com.jstatcom.model.JSCTypeDef;
import com.jstatcom.model.JSCTypes;
import com.jstatcom.model.ModelPanel;
import com.jstatcom.model.Scope;
import com.jstatcom.model.SymbolTable;
import com.jstatcom.ts.TSDateRange;

import de.jmulti.proc.SpectrumCall;

/**
 * A panel for spectral density estimation that can be configured with the names
 * of the symbols of the input data objects.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public final class SpectrumPanel extends ModelPanel {
    private static final Logger log = Logger.getLogger(SpectrumPanel.class);

    private Scope scope = Scope.GLOBAL;

    private JSCTypeDef dRange = new JSCTypeDef("spectrum_drange",
            JSCTypes.DRANGE);

    private JSCTypeDef resids = new JSCTypeDef("spectrum_data", JSCTypes.NARRAY);

    private JSCTypeDef nameResids = new JSCTypeDef("spectrum_datanames",
            JSCTypes.SARRAY);

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private JLabel ivjJLabel31 = null;

    private JCheckBox ivjLogScale = null;

    private JCheckBox ivjMultipleWindows = null;

    private ResultField ivjResultField = null;

    private JCheckBox ivjTextSpectrum = null;

    private NumSelector ivjWindowSize = null;

    private JPanel ivjSpectrumPanel = null;

    private JButton ivjExecuteButton = null;

    class IvjEventHandler implements java.awt.event.ActionListener {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == SpectrumPanel.this.getExecuteButton())
                connEtoC1();
        };
    };

    /**
     * Spectrum constructor comment.
     */
    public SpectrumPanel() {
        super();
        initialize();
    }

    /**
     * connEtoC1: (plotSpectrum.action. -->
     * Spectrum.plotSpectrum_ActionEvents()V)
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
     * Return the plotSpectrum property value.
     * 
     * @return javax.swing.JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JButton getExecuteButton() {
        if (ivjExecuteButton == null) {
            try {
                ivjExecuteButton = new javax.swing.JButton();
                ivjExecuteButton.setName("ExecuteButton");
                ivjExecuteButton.setText("Execute");
                ivjExecuteButton
                        .setMaximumSize(new java.awt.Dimension(140, 27));
                ivjExecuteButton.setPreferredSize(new java.awt.Dimension(135,
                        27));
                ivjExecuteButton
                        .setMinimumSize(new java.awt.Dimension(135, 27));
                ivjExecuteButton.setEnabled(true);
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
     * Return the JLabel31 property value.
     * 
     * @return javax.swing.JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JLabel getJLabel31() {
        if (ivjJLabel31 == null) {
            try {
                ivjJLabel31 = new javax.swing.JLabel();
                ivjJLabel31.setName("JLabel31");
                ivjJLabel31.setPreferredSize(new java.awt.Dimension(115, 21));
                ivjJLabel31.setText("Bartlett window size (1 = periodogram)");
                ivjJLabel31.setMaximumSize(new java.awt.Dimension(115, 21));
                ivjJLabel31.setMinimumSize(new java.awt.Dimension(115, 21));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJLabel31;
    }

    /**
     * Return the LogScale property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getLogScale() {
        if (ivjLogScale == null) {
            try {
                ivjLogScale = new javax.swing.JCheckBox();
                ivjLogScale.setName("LogScale");
                ivjLogScale.setText("Log scale");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjLogScale;
    }

    /**
     * Return the MultipleWindows property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getMultipleWindows() {
        if (ivjMultipleWindows == null) {
            try {
                ivjMultipleWindows = new javax.swing.JCheckBox();
                ivjMultipleWindows.setName("MultipleWindows");
                ivjMultipleWindows.setText("One diagram for each graph");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjMultipleWindows;
    }

    /**
     * Gets the variable name for names of residuals.
     * 
     * @return variable name of data object
     */
    public String getNameResids() {
        if (nameResids == null)
            return null;

        return nameResids.name;
    }

    /**
     * Gets the variable name for the date range.
     * 
     * @return variable name of data object
     */
    public String getDRange() {
        if (dRange == null)
            return null;

        return dRange.name;
    }

    /**
     * Gets the variable name for residuals.
     * 
     * @return variable name of data object
     */
    public String getResids() {
        if (resids == null)
            return null;

        return resids.name;

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
                        .setPreferredSize(new java.awt.Dimension(200, 100));
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
     * Return the SpectrumPanel property value.
     * 
     * @return javax.swing.JPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JPanel getSpectrumPanel() {
        if (ivjSpectrumPanel == null) {
            try {
                ivjSpectrumPanel = new javax.swing.JPanel();
                ivjSpectrumPanel.setName("SpectrumPanel");
                ivjSpectrumPanel.setPreferredSize(new java.awt.Dimension(221,
                        120));
                ivjSpectrumPanel
                        .setBorder(new BevelBorder(BevelBorder.LOWERED));
                ivjSpectrumPanel.setLayout(new java.awt.GridBagLayout());
                ivjSpectrumPanel
                        .setMinimumSize(new java.awt.Dimension(221, 120));

                java.awt.GridBagConstraints constraintsExecuteButton = new java.awt.GridBagConstraints();
                constraintsExecuteButton.gridx = 2;
                constraintsExecuteButton.gridy = 3;
                constraintsExecuteButton.anchor = java.awt.GridBagConstraints.WEST;
                constraintsExecuteButton.insets = new java.awt.Insets(0,200,5,5);
                java.awt.GridBagConstraints constraintsWindowSize = new java.awt.GridBagConstraints();
                constraintsWindowSize.gridx = 0;
                constraintsWindowSize.gridy = 0;
                constraintsWindowSize.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsWindowSize.anchor = java.awt.GridBagConstraints.WEST;
                constraintsWindowSize.weighty = 1.0;
                constraintsWindowSize.insets = new java.awt.Insets(10,10,0,0);
                java.awt.GridBagConstraints constraintsJLabel31 = new java.awt.GridBagConstraints();
                constraintsJLabel31.gridx = 1;
                constraintsJLabel31.gridy = 0;
                constraintsJLabel31.gridwidth = 2;
                constraintsJLabel31.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsJLabel31.anchor = java.awt.GridBagConstraints.WEST;
                constraintsJLabel31.weightx = 1.0;
                constraintsJLabel31.weighty = 1.0;
                constraintsJLabel31.insets = new java.awt.Insets(10,5,0,0);
                ivjSpectrumPanel.add(getExecuteButton(), constraintsExecuteButton);
                ivjSpectrumPanel.add(getWindowSize(), constraintsWindowSize);
                java.awt.GridBagConstraints constraintsMultipleWindows = new java.awt.GridBagConstraints();
                constraintsMultipleWindows.gridx = 0;
                constraintsMultipleWindows.gridy = 2;
                constraintsMultipleWindows.gridwidth = 3;
                constraintsMultipleWindows.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsMultipleWindows.anchor = java.awt.GridBagConstraints.WEST;
                constraintsMultipleWindows.insets = new java.awt.Insets(0,10,0,0);
                ivjSpectrumPanel.add(getJLabel31(), constraintsJLabel31);
                java.awt.GridBagConstraints constraintsLogScale = new java.awt.GridBagConstraints();
                constraintsLogScale.gridx = 0;
                constraintsLogScale.gridy = 3;
                constraintsLogScale.gridwidth = 2;
                constraintsLogScale.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsLogScale.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsLogScale.insets = new java.awt.Insets(0,10,0,0);
                ivjSpectrumPanel.add(getMultipleWindows(), constraintsMultipleWindows);
                java.awt.GridBagConstraints constraintsTextSpectrum = new java.awt.GridBagConstraints();
                constraintsTextSpectrum.gridx = 0;
                constraintsTextSpectrum.gridy = 1;
                constraintsTextSpectrum.gridwidth = 3;
                constraintsTextSpectrum.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsTextSpectrum.anchor = java.awt.GridBagConstraints.WEST;
                constraintsTextSpectrum.insets = new java.awt.Insets(0,10,0,0);
                ivjSpectrumPanel.add(getLogScale(), constraintsLogScale);
                ivjSpectrumPanel.add(getTextSpectrum(), constraintsTextSpectrum);
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
     * Return the TextSpectrum property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getTextSpectrum() {
        if (ivjTextSpectrum == null) {
            try {
                ivjTextSpectrum = new javax.swing.JCheckBox();
                ivjTextSpectrum.setName("TextSpectrum");
                ivjTextSpectrum.setText("Output as text (no graph)");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjTextSpectrum;
    }

    /**
     * Return the WindowSize property value.
     * 
     * @return com.jstatcom.model.NumberSelector
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private NumSelector getWindowSize() {
        if (ivjWindowSize == null) {
            try {
                ivjWindowSize = new NumSelector();
                ivjWindowSize.setName("WindowSize");
                ivjWindowSize.setPreferredSize(new java.awt.Dimension(35, 21));
                ivjWindowSize.setNumber(10.0);
                ivjWindowSize.setMinimumSize(new java.awt.Dimension(35, 1));
                ivjWindowSize.setRangeExpr("[1,100]");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjWindowSize;
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
    }

    /**
     * Initialize the class.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initialize() {
        try {
            // user code begin {1}
            // user code end
            setName("Spectrum");
            setLayout(new java.awt.BorderLayout());
            setSize(587, 334);
            add(getResultField(), "Center");
            add(getSpectrumPanel(), "North");
            initConnections();
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}
        // user code end
    }

    /**
     * Makes call to spectrum.
     */
    private void execute_ActionEvents() {

        SymbolTable sTable = scope.getSymbolTable(this);

        if (sTable.get(resids).isEmpty()) {
            StdMessages
                    .infoNothingSelected("No series available for spectrum.");
            return;
        }

        TSDateRange range = sTable.get(dRange).getJSCDRange().getTSDateRange();

        PCall job = new SpectrumCall(sTable.get(resids).getJSCNArray(), sTable
                .get(nameResids).getJSCSArray(), range, getWindowSize()
                .getIntNumber(), getLogScale().isSelected(), getTextSpectrum()
                .isSelected(), getMultipleWindows().isSelected());

        job.setSymbolTable(local());
        job.setOutHolder(getResultField());
        job.execute();

    }

    /**
     * Sets the variable name for the names of residuals.
     * 
     * @param newNameResids
     */
    public void setNameResids(String newNameResids) {
        if (newNameResids == null)
            nameResids = null;
        else
            nameResids = new JSCTypeDef(newNameResids, JSCTypes.SARRAY);
    }

    /**
     * Sets the variable name for the date range.
     * 
     * @param newDRange
     */
    public void setDRange(String newDRange) {
        if (newDRange == null)
            dRange = null;
        else
            dRange = new JSCTypeDef(newDRange, JSCTypes.DRANGE);
    }

    /**
     * Sets the variable name for the residuals.
     * 
     * @param newResids
     */
    public void setResids(String newResids) {
        if (newResids == null)
            resids = null;
        else
            resids = new JSCTypeDef(newResids, JSCTypes.NARRAY);

    }

    /**
     * @return
     */
    public Scope getScope() {
        return scope;
    }

    /**
     * @param scope
     */
    public void setScope(Scope scope) {
        if (scope == null)
            throw new IllegalArgumentException("Argument was null.");

        this.scope = scope;
    }

}