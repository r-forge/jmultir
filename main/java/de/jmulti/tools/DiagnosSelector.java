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
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import org.apache.log4j.Logger;

import com.jstatcom.component.NumSelector;
import com.jstatcom.component.TopFrameReference;

/**
 * A panel for specifying some univariate diagnostic tests. The specified
 * settings can be accessed via methods after the dialog has been shown. It is
 * recommended, although not necessary, that the dialog is shared an therefore
 * created only once.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public final class DiagnosSelector extends JDialog {
    private static final Logger log = Logger.getLogger(DiagnosSelector.class);

    private boolean isArch = false;

    private boolean isBJ = false;

    private boolean isPlotAuto = false;

    private boolean isPlotRes = false;

    private boolean isPortMan = false;

    private boolean isResStd = false;

    private int archLMLags = 2;

    private int portLags = 12;

    private int autoCorrLags = 36;

    private static DiagnosSelector sharedInstance = null;

    private JCheckBox ivjArch = null;

    private JPanel ivjJDialogContentPane = null;

    private JPanel ivjJPanel1 = null;

    private JCheckBox ivjPortman = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private JButton ivjOK = null;

    private NumSelector ivjArchLags = null;

    private JLabel ivjJLabel1 = null;

    private JLabel ivjJLabel2 = null;

    private NumSelector ivjPortmanLags = null;

    private JCheckBox ivjBeraJarque = null;

    private JCheckBox ivjPlotResiduals = null;

    private JPanel ivjJPanel2 = null;

    private JCheckBox ivjResidsStandardized = null;

    private NumSelector ivjAutocorrLags = null;

    private JLabel ivjJLabel4 = null;

    private JCheckBox ivjPlotAutoCorrelation = null;

    private JButton ivjCancelButton = null;

    class IvjEventHandler implements java.awt.event.ActionListener {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == DiagnosSelector.this.getOK())
                connEtoC1();
            if (e.getSource() == DiagnosSelector.this.getPlotResiduals())
                connEtoC2();
            if (e.getSource() == DiagnosSelector.this.getArch())
                connEtoC3();
            if (e.getSource() == DiagnosSelector.this.getPortman())
                connEtoC4();
            if (e.getSource() == DiagnosSelector.this.getPlotAutoCorrelation())
                connEtoC5();
            if (e.getSource() == DiagnosSelector.this.getCancelButton())
                connEtoC6();
        };
    };

    /**
     * DiagnosticTests constructor comment.
     */
    public DiagnosSelector() {
        super();
        initialize();
    }

    /**
     * DiagnosticTests constructor comment.
     * 
     * @param owner
     *            java.awt.Frame
     * @param modal
     *            boolean
     */
    public DiagnosSelector(java.awt.Frame owner, boolean modal) {
        super(owner, modal);
        initialize();
        setLocationRelativeTo(owner);
    }

    /**
     * Comment
     */
    private void arch_ActionEvents() {
        getArchLags().setEnabled(getArch().isSelected());
        return;
    }

    /**
     * Cancels without setting numbers.
     */
    private void cancelButton_ActionEvents() {
        setVisible(false);
    }

    /**
     * connEtoC1: (OK.action. --> DiagnosticTests.oK_ActionEvents()V)
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
     * connEtoC2: (PlotResiduals.action. -->
     * DiagnosticTests.plotResiduals_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC2() {
        try {
            // user code begin {1}
            // user code end
            this.plotResiduals_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC3: (Arch.action. --> DiagnosticTests.arch_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC3() {
        try {
            // user code begin {1}
            // user code end
            this.arch_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC4: (Portman.action. --> DiagnosticTests.portman_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC4() {
        try {
            // user code begin {1}
            // user code end
            this.portman_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC5: (PlotAutoCorrelation.action. -->
     * DiagnosticTests.plotAutoCorrelation_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC5() {
        try {
            // user code begin {1}
            // user code end
            this.plotAutoCorrelation_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC6: (CancelButton.action. -->
     * UnivariateDiagnosSelector.cancelButton_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC6() {
        try {
            // user code begin {1}
            // user code end
            this.cancelButton_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * Return the Arch property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getArch() {
        if (ivjArch == null) {
            try {
                ivjArch = new javax.swing.JCheckBox();
                ivjArch.setName("Arch");
                ivjArch.setSelected(false);
                ivjArch.setText("ARCH-LM test");
                ivjArch.setBounds(10, 55, 137, 25);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjArch;
    }

    private NumSelector getArchLags() {
        if (ivjArchLags == null) {
            try {
                ivjArchLags = new NumSelector();
                ivjArchLags.setName("ArchLags");
                ivjArchLags.setNumber(2.0);
                ivjArchLags.setBounds(150, 55, 30, 25);
                ivjArchLags.setEnabled(false);
                ivjArchLags.setRangeExpr("[1,100]");
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
     * Insert the method's description here. Creation date: (30.08.00 08:27:15)
     * 
     * @return double
     */
    public int getArchLMLags() {
        return archLMLags;
    }

    /**
     * Insert the method's description here. Creation date: (30.08.00 08:27:15)
     * 
     * @return double
     */
    public int getAutoCorrelationLags() {
        return autoCorrLags;
    }

    private NumSelector getAutocorrLags() {
        if (ivjAutocorrLags == null) {
            try {
                ivjAutocorrLags = new NumSelector();
                ivjAutocorrLags.setName("AutocorrLags");
                ivjAutocorrLags.setNumber(36.0);
                ivjAutocorrLags.setBounds(150, 175, 30, 25);
                ivjAutocorrLags.setEnabled(false);
                ivjAutocorrLags.setRangeExpr("[2,100]");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjAutocorrLags;
    }

    /**
     * Return the JCheckBox3 property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getBeraJarque() {
        if (ivjBeraJarque == null) {
            try {
                ivjBeraJarque = new javax.swing.JCheckBox();
                ivjBeraJarque.setName("BeraJarque");
                ivjBeraJarque.setSelected(false);
                ivjBeraJarque.setText("Jarque-Bera test");
                ivjBeraJarque.setBounds(10, 95, 137, 25);
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
     * Return the CancelButton property value.
     * 
     * @return javax.swing.JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JButton getCancelButton() {
        if (ivjCancelButton == null) {
            try {
                ivjCancelButton = new javax.swing.JButton();
                ivjCancelButton.setName("CancelButton");
                ivjCancelButton
                        .setPreferredSize(new java.awt.Dimension(85, 25));
                ivjCancelButton.setText("Cancel");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjCancelButton;
    }

    /**
     * Return the JDialogContentPane property value.
     * 
     * @return javax.swing.JPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JPanel getJDialogContentPane() {
        if (ivjJDialogContentPane == null) {
            try {
                ivjJDialogContentPane = new javax.swing.JPanel();
                ivjJDialogContentPane.setName("JDialogContentPane");
                ivjJDialogContentPane.setLayout(new java.awt.BorderLayout());
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
        return ivjJDialogContentPane;
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
                ivjJLabel1.setText("Number of lags");
                ivjJLabel1.setBounds(185, 15, 90, 25);
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
                ivjJLabel2.setText("Number of lags");
                ivjJLabel2.setBounds(185, 55, 90, 25);
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
     * Return the JLabel4 property value.
     * 
     * @return javax.swing.JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JLabel getJLabel4() {
        if (ivjJLabel4 == null) {
            try {
                ivjJLabel4 = new javax.swing.JLabel();
                ivjJLabel4.setName("JLabel4");
                ivjJLabel4.setText("Lags to include");
                ivjJLabel4.setBounds(185, 175, 90, 25);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJLabel4;
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
                ivjJPanel1.setPreferredSize(new java.awt.Dimension(410, 180));
                ivjJPanel1.setBorder(new BevelBorder(BevelBorder.LOWERED));
                ivjJPanel1.setLayout(null);
                ivjJPanel1.setMinimumSize(new java.awt.Dimension(410, 180));
                getJPanel1().add(getPortman(), getPortman().getName());
                getJPanel1().add(getArch(), getArch().getName());
                getJPanel1().add(getBeraJarque(), getBeraJarque().getName());
                getJPanel1().add(getPortmanLags(), getPortmanLags().getName());
                getJPanel1().add(getArchLags(), getArchLags().getName());
                getJPanel1().add(getJLabel1(), getJLabel1().getName());
                getJPanel1().add(getJLabel2(), getJLabel2().getName());
                getJPanel1().add(getPlotResiduals(),
                        getPlotResiduals().getName());
                getJPanel1().add(getResidsStandardized(),
                        getResidsStandardized().getName());
                getJPanel1().add(getPlotAutoCorrelation(),
                        getPlotAutoCorrelation().getName());
                getJPanel1()
                        .add(getAutocorrLags(), getAutocorrLags().getName());
                getJPanel1().add(getJLabel4(), getJLabel4().getName());
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
                ivjJPanel2.setLayout(getJPanel2FlowLayout());
                ivjJPanel2.setAlignmentY(java.awt.Component.CENTER_ALIGNMENT);
                ivjJPanel2.setPreferredSize(new java.awt.Dimension(410, 50));
                ivjJPanel2.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
                ivjJPanel2.setMinimumSize(new java.awt.Dimension(410, 50));
                getJPanel2().add(getOK(), getOK().getName());
                getJPanel2()
                        .add(getCancelButton(), getCancelButton().getName());
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
     * Return the JPanel2FlowLayout property value.
     * 
     * @return java.awt.FlowLayout
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private java.awt.FlowLayout getJPanel2FlowLayout() {
        java.awt.FlowLayout ivjJPanel2FlowLayout = null;
        try {
            /* Create part */
            ivjJPanel2FlowLayout = new java.awt.FlowLayout();
            ivjJPanel2FlowLayout.setVgap(10);
            ivjJPanel2FlowLayout.setHgap(50);
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        ;
        return ivjJPanel2FlowLayout;
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
                ivjOK.setText("OK");
                ivjOK.setPreferredSize(new java.awt.Dimension(85, 25));
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

    /**
     * Return the PlotAutoCorrelation property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getPlotAutoCorrelation() {
        if (ivjPlotAutoCorrelation == null) {
            try {
                ivjPlotAutoCorrelation = new javax.swing.JCheckBox();
                ivjPlotAutoCorrelation.setName("PlotAutoCorrelation");
                ivjPlotAutoCorrelation.setText("Plot autocorrelation ");
                ivjPlotAutoCorrelation.setBounds(10, 175, 137, 25);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjPlotAutoCorrelation;
    }

    /**
     * Return the JCheckBox1 property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getPlotResiduals() {
        if (ivjPlotResiduals == null) {
            try {
                ivjPlotResiduals = new javax.swing.JCheckBox();
                ivjPlotResiduals.setName("PlotResiduals");
                ivjPlotResiduals.setText("Plot residuals");
                ivjPlotResiduals.setBounds(10, 135, 137, 25);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjPlotResiduals;
    }

    /**
     * Return the Portman property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getPortman() {
        if (ivjPortman == null) {
            try {
                ivjPortman = new javax.swing.JCheckBox();
                ivjPortman.setName("Portman");
                ivjPortman.setSelected(false);
                ivjPortman.setText("Portmanteau test");
                ivjPortman.setBounds(10, 15, 137, 25);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjPortman;
    }

    private NumSelector getPortmanLags() {
        if (ivjPortmanLags == null) {
            try {
                ivjPortmanLags = new NumSelector();
                ivjPortmanLags.setName("PortmanLags");
                ivjPortmanLags.setNumber(12.0);
                ivjPortmanLags.setBounds(150, 15, 30, 25);
                ivjPortmanLags.setEnabled(false);
                ivjPortmanLags.setRangeExpr("[1,100]");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjPortmanLags;
    }

    /**
     * Insert the method's description here. Creation date: (30.08.00 08:27:15)
     * 
     * @return double
     */
    public int getPortmanteauLags() {
        return portLags;
    }

    /**
     * Return the ResidsStandardized property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getResidsStandardized() {
        if (ivjResidsStandardized == null) {
            try {
                ivjResidsStandardized = new javax.swing.JCheckBox();
                ivjResidsStandardized.setName("ResidsStandardized");
                ivjResidsStandardized.setText("Standardize residuals");
                ivjResidsStandardized.setBounds(150, 135, 159, 25);
                ivjResidsStandardized.setEnabled(false);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjResidsStandardized;
    }

    /**
     * Gets an initialized Singleton instance of this dialog that can be shared.
     * 
     * @return shared instance
     */
    public static DiagnosSelector getSharedInstance() {
        if (sharedInstance == null)
            sharedInstance = new DiagnosSelector(TopFrameReference
                    .getTopFrameRef(), true);
        return sharedInstance;
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
        getPlotResiduals().addActionListener(ivjEventHandler);
        getArch().addActionListener(ivjEventHandler);
        getPortman().addActionListener(ivjEventHandler);
        getPlotAutoCorrelation().addActionListener(ivjEventHandler);
        getCancelButton().addActionListener(ivjEventHandler);
    }

    /**
     * Initialize the class.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initialize() {
        try {
            // user code begin {1}
            // user code end
            setName("DiagnosticTests");
            setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
            setResizable(false);
            setSize(352, 300);
            setModal(true);
            setTitle("Configure Residual Analysis");
            setContentPane(getJDialogContentPane());
            initConnections();
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}
        // user code end
    }

    /**
     * Insert the method's description here. Creation date: (30.08.00 08:23:44)
     * 
     * @return boolean
     */
    public boolean isArchLM() {
        return isArch;
    }

    /**
     * Insert the method's description here. Creation date: (30.08.00 08:23:44)
     * 
     * @return boolean
     */
    public boolean isJarqueBera() {
        return isBJ;
    }

    /**
     * Insert the method's description here. Creation date: (30.08.00 08:23:44)
     * 
     * @return boolean
     */
    public boolean isPlotAutoCorrelation() {
        return isPlotAuto;
    }

    /**
     * Insert the method's description here. Creation date: (30.08.00 08:23:44)
     * 
     * @return boolean
     */
    public boolean isPlotResiduals() {
        return isPlotRes;
    }

    /**
     * Insert the method's description here. Creation date: (30.08.00 08:23:44)
     * 
     * @return boolean
     */
    public boolean isPortMan() {
        return isPortMan;
    }

    /**
     * Insert the method's description here. Creation date: (30.08.00 08:23:44)
     * 
     * @return boolean
     */
    public boolean isResidsStandardized() {
        return isResStd;
    }

    /**
     * Comment
     */
    private void oK_ActionEvents() {

        isArch = getArch().isSelected();

        isBJ = getBeraJarque().isSelected();
        isPortMan = getPortman().isSelected();
        isPlotAuto = getPlotAutoCorrelation().isSelected();
        isPlotRes = getPlotResiduals().isSelected();
        isResStd = getResidsStandardized().isSelected();

        archLMLags = getArchLags().getIntNumber();
        portLags = getPortmanLags().getIntNumber();
        autoCorrLags = getAutocorrLags().getIntNumber();

        this.setVisible(false);

        return;
    }

    /**
     * Comment
     */
    private void plotAutoCorrelation_ActionEvents() {
        getAutocorrLags().setEnabled(getPlotAutoCorrelation().isSelected());
        return;
    }

    /**
     * Comment
     */
    private void plotResiduals_ActionEvents() {
        getResidsStandardized().setEnabled(getPlotResiduals().isSelected());
        return;
    }

    /**
     * Comment
     */
    private void portman_ActionEvents() {
        getPortmanLags().setEnabled(getPortman().isSelected());
        return;
    }

    /**
     * Overrides method to insert current settings before showing the dialog.
     * 
     * @param isVisible
     */
    public void setVisible(boolean isVisible) {
        getArch().setSelected(isArch);
        getBeraJarque().setSelected(isBJ);
        getPortman().setSelected(isPortMan);
        getPlotAutoCorrelation().setSelected(isPlotAuto);
        getPlotResiduals().setSelected(isPlotRes);
        getResidsStandardized().setSelected(isResStd);

        getArchLags().setNumber(archLMLags);
        getPortmanLags().setNumber(portLags);
        getAutocorrLags().setNumber(autoCorrLags);
        getArchLags().setEnabled(isArch);
        getPortmanLags().setEnabled(isPortMan);
        getAutocorrLags().setEnabled(isPlotAuto);

        super.setVisible(isVisible);

    }
}
