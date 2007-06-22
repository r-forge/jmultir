/*
 This file is part of the econometric software package JMulTi.
 Copyright (C) 2000-2005  Alexander Bankwitz, Markus Kraetzig

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

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import com.jstatcom.component.CompSettings;
import com.jstatcom.component.NumSelector;

/**
 * Provides a user input mask for configuring the bootstrap for Efron/Hall
 * Percentile confidence intervals for the impulse response analysis. The
 * selected values can be accessed via the <code>get</code> methods.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public final class IRABootCIPercPanel extends JPanel {
    private static final Logger log = Logger
            .getLogger(IRABootCIPercPanel.class);

    private NumSelector ivjCoverageSelector = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private JLabel ivjJLabel1 = null;

    private JLabel ivjJLabel2 = null;

    private NumSelector ivjNumSelector = null;

    private JCheckBox ivjSeedCheck = null;

    private NumSelector ivjSeedSelector = null;

    class IvjEventHandler implements java.awt.event.ItemListener {
        public void itemStateChanged(java.awt.event.ItemEvent e) {
            if (e.getSource() == IRABootCIPercPanel.this.getSeedCheck())
                connPtoP1SetTarget();
        };
    };

    /**
     * IRABootCIPercPanel constructor comment.
     */
    public IRABootCIPercPanel() {
        super();
        initialize();
    }

    private void connPtoP1SetTarget() {
        /* Set the target from the source */
        try {
            getSeedSelector().setEnabled(getSeedCheck().isSelected());
            // user code begin {1}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * Gets the selected number of bootstrap replications.
     * 
     * @return the number of replications
     */
    public int getBootRepNum() {
        return getNumSelector().getIntNumber();
    }

    /**
     * Gets the selected coverage of the bootstrapped confidence intervals.
     * 
     * @return a <code>double</code> between 0 an 1
     */
    public double getCICoverage() {
        return getCoverageSelector().getNumber();
    }

    private NumSelector getCoverageSelector() {
        if (ivjCoverageSelector == null) {
            try {
                ivjCoverageSelector = new NumSelector();
                ivjCoverageSelector.setName("CoverageSelector");
                ivjCoverageSelector.setNumber(0.95);
                ivjCoverageSelector.setPrecision(2);
                ivjCoverageSelector.setRangeExpr("(0,1)");
                ivjCoverageSelector.setBounds(250, 20, 85, 20);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjCoverageSelector;
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
                ivjJLabel1.setText("CI coverage (0 < c < 1)");
                ivjJLabel1.setBounds(10, 20, 165, 20);
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
                ivjJLabel2.setText("Number of bootstrap replications");
                ivjJLabel2.setBounds(10, 45, 187, 20);
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

    private NumSelector getNumSelector() {
        if (ivjNumSelector == null) {
            try {
                ivjNumSelector = new NumSelector();
                ivjNumSelector.setName("NumSelector");
                ivjNumSelector.setNumber(100.0);
                ivjNumSelector.setRangeExpr("[40,+infinity)");
                ivjNumSelector.setBounds(250, 45, 85, 20);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjNumSelector;
    }

    /**
     * Gets the selected seed.
     * 
     * @return the <code>int</code> representing the seed
     */
    public int getSeed() {
        return getSeedSelector().getIntNumber();
    }

    /**
     * Return the CheckBox_Seed property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getSeedCheck() {
        if (ivjSeedCheck == null) {
            try {
                ivjSeedCheck = new javax.swing.JCheckBox();
                ivjSeedCheck.setName("SeedCheck");
                ivjSeedCheck.setText("Use this seed (0 < s < 2,147,483,647) ");
                ivjSeedCheck.setBounds(10, 70, 235, 20);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSeedCheck;
    }

    private NumSelector getSeedSelector() {
        if (ivjSeedSelector == null) {
            try {
                ivjSeedSelector = new NumSelector();
                ivjSeedSelector.setName("SeedSelector");
                ivjSeedSelector.setNumber(1.0);
                ivjSeedSelector.setPrecision(0);
                ivjSeedSelector.setRangeExpr("(0,2.147483647E9)");
                ivjSeedSelector.setBounds(250, 70, 85, 20);
                ivjSeedSelector.setEnabled(false);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSeedSelector;
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
        getSeedCheck().addItemListener(ivjEventHandler);
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
            setName("IRABootCIPercPanel");
            setPreferredSize(new java.awt.Dimension(360, 100));
            setLayout(null);
            setSize(360, 100);
            setMinimumSize(new java.awt.Dimension(360, 100));
            add(getCoverageSelector(), getCoverageSelector().getName());
            add(getNumSelector(), getNumSelector().getName());
            add(getSeedSelector(), getSeedSelector().getName());
            add(getSeedCheck(), getSeedCheck().getName());
            add(getJLabel2(), getJLabel2().getName());
            add(getJLabel1(), getJLabel1().getName());
            initConnections();
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}
        TitledBorder title = new TitledBorder(new EtchedBorder(),
                "Configure Percentile CI Bootstrap", TitledBorder.RIGHT,
                TitledBorder.TOP);
        title.setTitleFont(CompSettings.smallFontDefault);
        this.setBorder(title);
        // user code end
    }

    /**
     * Gets whether seed should be used.
     * 
     * @return <code>true</code> if seed is used
     */
    public boolean isSeed() {
        return getSeedCheck().isSelected();
    }
}
