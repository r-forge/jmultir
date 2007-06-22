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

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;

import org.apache.log4j.Logger;

import com.jstatcom.component.NumSelector;
import com.jstatcom.component.TopFrameReference;
import com.jstatcom.util.UMatrix;

/**
 * Dialog that contains plot settings input mask. It allows to adjust all
 * variables of a <code>PlotControlModel</code> that can be used with the
 * GAUSS engine to specify plot settings used in <i>JMulTi </i>.
 * 
 * @author A. Benkwitz, <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
public final class PlotControlDialog extends javax.swing.JDialog {
    private static final Logger log = Logger.getLogger(PlotControlDialog.class);

    public static final String[] GAUSS_LINESTYLES = new String[] { "dashed",
            "dotted", "short dashes", "closely spaced dots", "dots and dashes",
            "solid" };

    public static final String[] GAUSS_COLORS = new String[] { "Black", "Blue",
            "Green", "Cyan", "Red", "Magenta", "Brown", "Grey", "Dark Grey",
            "Light Blue", "Light Green", "Light Cyan", "Light Red",
            "Light Magenta", "Yellow", "White" };

    public static final String[] GAUSS_SHADINGS = new String[] { "no shading",
            "dots ", "vertical cross-hatch",
            "diagonal lines with positive slope",
            "diagonal lines with negative slope", "diagonal cross-hatch",
            "solid" };

    public static final List<String> SHADING_LIST = new ArrayList<String>();

    public static final List<String> COLOR_LIST = new ArrayList<String>();

    public static final List<String> LINESTYLES_LIST = new ArrayList<String>();
    static {
        for (String val : GAUSS_LINESTYLES)
            LINESTYLES_LIST.add(val);
        for (String val : GAUSS_COLORS)
            COLOR_LIST.add(val);
        for (String val : GAUSS_SHADINGS)
            SHADING_LIST.add(val);
    }

    private String[] legend_strings;

    private double[] line_types = null;

    private double[] line_color = null;

    private double[] line_width = null;

    private int line_index_displayed = 0;

    private PlotControlModel plotControlModel = new PlotControlModel();

    private javax.swing.JButton ivjCancelButton = null;

    private javax.swing.JButton ivjDefaultsButton = null;

    private javax.swing.JPanel ivjJPanel1 = null;

    private javax.swing.JButton ivjOKButton = null;

    private javax.swing.JCheckBox ivjCheckBoxShowDate = null;

    private javax.swing.JCheckBox ivjCheckBoxShowLegend = null;

    private javax.swing.JCheckBox ivjCheckBoxShowMainTitle = null;

    private javax.swing.JCheckBox ivjCheckBoxShowPlotTitle = null;

    private javax.swing.JComboBox ivjComboBoxLineColor = null;

    private javax.swing.JComboBox ivjComboBoxLineType = null;

    private javax.swing.JComboBox ivjComboBoxMainLines = null;

    private javax.swing.JLabel ivjJLabel = null;

    private javax.swing.JLabel ivjJLabel1 = null;

    private javax.swing.JLabel ivjJLabel10 = null;

    private javax.swing.JLabel ivjJLabel11 = null;

    private javax.swing.JLabel ivjJLabel12 = null;

    private javax.swing.JLabel ivjJLabel13 = null;

    private javax.swing.JLabel ivjJLabel14 = null;

    private javax.swing.JLabel ivjJLabel16 = null;

    private javax.swing.JLabel ivjJLabel2 = null;

    private javax.swing.JLabel ivjJLabel3 = null;

    private javax.swing.JLabel ivjJLabel4 = null;

    private javax.swing.JLabel ivjJLabel5 = null;

    private javax.swing.JLabel ivjJLabel6 = null;

    private javax.swing.JLabel ivjJLabel7 = null;

    private javax.swing.JLabel ivjJLabel8 = null;

    private javax.swing.JLabel ivjJLabel9 = null;

    private javax.swing.JTabbedPane ivjJTabbedPane1 = null;

    private javax.swing.JSlider ivjSliderHeightAxisLabels = null;

    private javax.swing.JSlider ivjSliderHeightAxisNumbers = null;

    private javax.swing.JSlider ivjSliderHeightLegend = null;

    private javax.swing.JSlider ivjSliderHeightMainTitle = null;

    private javax.swing.JSlider ivjSliderHeightPlotTitle = null;

    private javax.swing.JSlider ivjSliderLineWidth = null;

    private javax.swing.JTextField ivjTextFieldLineString = null;

    private NumSelector ivjTextFieldXPositionLegend = null;

    private NumSelector ivjTextFieldYPositionLegend = null;

    private javax.swing.JPanel ivjAxes = null;

    private javax.swing.JPanel ivjLegend = null;

    private javax.swing.JPanel ivjMainLines = null;

    private javax.swing.JPanel ivjTitlePage = null;

    private javax.swing.JPanel ivjPlotControlDialogContentPane1 = null;

    private javax.swing.JCheckBox ivjLegendDefaultCheck = null;

    private javax.swing.JCheckBox ivjDisplayXCheck = null;

    private javax.swing.JCheckBox ivjDisplayYCheck = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private javax.swing.JCheckBox jDisplayZCheck = null;

    class IvjEventHandler implements java.awt.event.ActionListener,
            java.awt.event.ItemListener {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == PlotControlDialog.this.getOKButton())
                connEtoC2();
            if (e.getSource() == PlotControlDialog.this.getCancelButton())
                connEtoC3();
            if (e.getSource() == PlotControlDialog.this.getDefaultsButton())
                connEtoC4();
            if (e.getSource() == PlotControlDialog.this.getComboBoxMainLines())
                connEtoC5();
        };

        public void itemStateChanged(java.awt.event.ItemEvent e) {
            if (e.getSource() == PlotControlDialog.this.getLegendDefaultCheck())
                connEtoC1(e);
        };
    };

    /**
     * PlotControlDialog constructor comment.
     */
    public PlotControlDialog() {
        super(TopFrameReference.getTopFrameRef(), true);
        initialize();
    }

    /**
     * Discards all changes and hides dialog.
     */
    private void cancelButton_ActionEvents() {
        setPlotControlModel(plotControlModel);
        setVisible(false);
    }

    /**
     * connEtoC1:
     * (LegendDefaultCheck.item.itemStateChanged(java.awt.event.ItemEvent) -->
     * PlotControlDialog.legendDefaultCheck_ItemStateChanged()V)
     * 
     * @param arg1
     *            java.awt.event.ItemEvent
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC1(java.awt.event.ItemEvent arg1) {
        try {
            // user code begin {1}
            // user code end
            this.legendDefaultCheck_ItemStateChanged();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC2: (CancelButton.action. -->
     * PlotControlDialog.cancelButton_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC2() {
        try {
            // user code begin {1}
            // user code end
            this.oKButton_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC3: (DefaultsButton.action. -->
     * PlotControlDialog.defaultsButton_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC3() {
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
     * connEtoC4: (ComboBoxMainLines.action. -->
     * PlotControlDialog.displayNewIndex()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC4() {
        try {
            // user code begin {1}
            // user code end
            this.defaultsButton_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC5: (ComboBoxMainLines.action. -->
     * PlotControlDialog.displayNewIndex()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC5() {
        try {
            // user code begin {1}
            // user code end
            this.displayNewIndex();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * Sets defaults to current control model and adjusts settings.
     */
    private void defaultsButton_ActionEvents() {
        plotControlModel.setDefaultValues();
        setPlotControlModel(plotControlModel);
    }

    /**
     * Comment
     */
    private void displayNewIndex() {

        int i = getComboBoxMainLines().getSelectedIndex();
        if (line_index_displayed >= 0) {
            // save current settings intermediate
            saveAttributesForLine(line_index_displayed);
        }
        line_index_displayed = i;
        if (i >= 0) {
            getTextFieldLineString().setText(legend_strings[i]);
            getTextFieldLineString().setCaretPosition(0);
            getComboBoxLineColor().setSelectedIndex((int) line_color[i]);
            getComboBoxLineType().setSelectedIndex((int) line_types[i] - 1);
            getSliderLineWidth().setValue((int) line_width[i]);
        } else {
            getTextFieldLineString().setText("");
        }

    }

    /**
     * Return the Page property value.
     * 
     * @return javax.swing.JPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JPanel getAxes() {
        if (ivjAxes == null) {
            try {
                ivjAxes = new javax.swing.JPanel();
                ivjAxes.setName("Axes");
                ivjAxes.setLayout(new java.awt.GridBagLayout());
                ivjAxes.setVisible(false);
                ivjAxes.setMaximumSize(new java.awt.Dimension(2147483647,
                        2147483647));
                ivjAxes.setMinimumSize(new java.awt.Dimension(371, 220));

                java.awt.GridBagConstraints constraintsJLabel1 = new java.awt.GridBagConstraints();
                constraintsJLabel1.gridx = 0;
                constraintsJLabel1.gridy = 0;
                constraintsJLabel1.gridwidth = 3;
                constraintsJLabel1.anchor = java.awt.GridBagConstraints.WEST;
                constraintsJLabel1.insets = new java.awt.Insets(10, 10, 0, 0);
                java.awt.GridBagConstraints constraintsJLabel2 = new java.awt.GridBagConstraints();
                constraintsJLabel2.gridx = 0;
                constraintsJLabel2.gridy = 3;
                constraintsJLabel2.gridwidth = 3;
                constraintsJLabel2.anchor = java.awt.GridBagConstraints.WEST;
                constraintsJLabel2.insets = new java.awt.Insets(10, 10, 0, 0);
                java.awt.GridBagConstraints constraintsJLabel3 = new java.awt.GridBagConstraints();
                constraintsJLabel3.gridx = 0;
                constraintsJLabel3.gridy = 5;
                constraintsJLabel3.anchor = java.awt.GridBagConstraints.WEST;
                constraintsJLabel3.insets = new java.awt.Insets(15, 10, 0, 0);
                java.awt.GridBagConstraints constraintsJLabel14 = new java.awt.GridBagConstraints();
                constraintsJLabel14.gridx = 0;
                constraintsJLabel14.gridy = 1;
                constraintsJLabel14.gridwidth = 3;
                constraintsJLabel14.anchor = java.awt.GridBagConstraints.WEST;
                constraintsJLabel14.weightx = 1.0;
                constraintsJLabel14.insets = new java.awt.Insets(5, 10, 0, 0);
                java.awt.GridBagConstraints constraintsSliderHeightAxisLabels = new java.awt.GridBagConstraints();
                constraintsSliderHeightAxisLabels.gridx = 0;
                constraintsSliderHeightAxisLabels.gridy = 4;
                constraintsSliderHeightAxisLabels.gridwidth = 3;
                constraintsSliderHeightAxisLabels.anchor = java.awt.GridBagConstraints.WEST;
                constraintsSliderHeightAxisLabels.insets = new java.awt.Insets(
                        5, 10, 0, 30);
                java.awt.GridBagConstraints constraintsSliderHeightAxisNumbers = new java.awt.GridBagConstraints();
                constraintsSliderHeightAxisNumbers.gridx = 0;
                constraintsSliderHeightAxisNumbers.gridy = 6;
                constraintsSliderHeightAxisNumbers.gridwidth = 3;
                constraintsSliderHeightAxisNumbers.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsSliderHeightAxisNumbers.weighty = 1.0;
                constraintsSliderHeightAxisNumbers.insets = new java.awt.Insets(
                        5, 10, 0, 30);
                java.awt.GridBagConstraints constraintsDisplayXCheck = new java.awt.GridBagConstraints();
                constraintsDisplayXCheck.gridx = 0;
                constraintsDisplayXCheck.gridy = 2;
                constraintsDisplayXCheck.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsDisplayXCheck.insets = new java.awt.Insets(10, 10,
                        0, 0);
                java.awt.GridBagConstraints constraintsDisplayYCheck = new java.awt.GridBagConstraints();
                java.awt.GridBagConstraints consGridBagConstraints1 = new java.awt.GridBagConstraints();
                consGridBagConstraints1.gridy = 2;
                consGridBagConstraints1.gridx = 2;
                consGridBagConstraints1.insets = new java.awt.Insets(10, 10, 0,
                        0);
                consGridBagConstraints1.anchor = java.awt.GridBagConstraints.WEST;
                consGridBagConstraints1.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsDisplayYCheck.gridx = 1;
                constraintsDisplayYCheck.gridy = 2;
                constraintsDisplayYCheck.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsDisplayYCheck.insets = new java.awt.Insets(10, 10,
                        0, 0);
                constraintsDisplayYCheck.anchor = java.awt.GridBagConstraints.WEST;
                constraintsJLabel3.gridwidth = 3;
                constraintsDisplayXCheck.anchor = java.awt.GridBagConstraints.WEST;
                ivjAxes.add(getJLabel1(), constraintsJLabel1);
                ivjAxes.add(getJLabel2(), constraintsJLabel2);
                ivjAxes.add(getJLabel3(), constraintsJLabel3);
                ivjAxes.add(getJLabel14(), constraintsJLabel14);
                ivjAxes.add(getSliderHeightAxisLabels(),
                        constraintsSliderHeightAxisLabels);
                ivjAxes.add(getSliderHeightAxisNumbers(),
                        constraintsSliderHeightAxisNumbers);
                ivjAxes.add(getDisplayXCheck(), constraintsDisplayXCheck);
                ivjAxes.add(getDisplayYCheck(), constraintsDisplayYCheck);
                ivjAxes.add(getJDisplayZCheck(), consGridBagConstraints1);
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjAxes;
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
                ivjCancelButton.setText("Cancel");
                ivjCancelButton
                        .setPreferredSize(new java.awt.Dimension(100, 25));
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
     * Return the CheckBoxShowDate property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getCheckBoxShowDate() {
        if (ivjCheckBoxShowDate == null) {
            try {
                ivjCheckBoxShowDate = new javax.swing.JCheckBox();
                ivjCheckBoxShowDate.setName("CheckBoxShowDate");
                ivjCheckBoxShowDate.setText("Show date");
                ivjCheckBoxShowDate.setMaximumSize(new java.awt.Dimension(85,
                        22));
                ivjCheckBoxShowDate.setMinimumSize(new java.awt.Dimension(85,
                        22));
                ivjCheckBoxShowDate.setActionCommand("Show date");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjCheckBoxShowDate;
    }

    /**
     * Return the CheckBoxShowLegend property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getCheckBoxShowLegend() {
        if (ivjCheckBoxShowLegend == null) {
            try {
                ivjCheckBoxShowLegend = new javax.swing.JCheckBox();
                ivjCheckBoxShowLegend.setName("CheckBoxShowLegend");
                ivjCheckBoxShowLegend.setText("Show legend");
                ivjCheckBoxShowLegend.setMaximumSize(new java.awt.Dimension(98,
                        22));
                ivjCheckBoxShowLegend.setMinimumSize(new java.awt.Dimension(98,
                        22));
                ivjCheckBoxShowLegend.setActionCommand("Show legend");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjCheckBoxShowLegend;
    }

    /**
     * Return the CheckBoxShowMainTitle property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getCheckBoxShowMainTitle() {
        if (ivjCheckBoxShowMainTitle == null) {
            try {
                ivjCheckBoxShowMainTitle = new javax.swing.JCheckBox();
                ivjCheckBoxShowMainTitle.setName("CheckBoxShowMainTitle");
                ivjCheckBoxShowMainTitle.setText("Show main title");
                ivjCheckBoxShowMainTitle.setMaximumSize(new java.awt.Dimension(
                        112, 22));
                ivjCheckBoxShowMainTitle.setMinimumSize(new java.awt.Dimension(
                        112, 22));
                ivjCheckBoxShowMainTitle.setActionCommand("Show main title");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjCheckBoxShowMainTitle;
    }

    /**
     * Return the CheckBoxShowPlotTitle property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getCheckBoxShowPlotTitle() {
        if (ivjCheckBoxShowPlotTitle == null) {
            try {
                ivjCheckBoxShowPlotTitle = new javax.swing.JCheckBox();
                ivjCheckBoxShowPlotTitle.setName("CheckBoxShowPlotTitle");
                ivjCheckBoxShowPlotTitle.setText("Show plot title");
                ivjCheckBoxShowPlotTitle.setMaximumSize(new java.awt.Dimension(
                        105, 22));
                ivjCheckBoxShowPlotTitle.setMinimumSize(new java.awt.Dimension(
                        105, 22));
                ivjCheckBoxShowPlotTitle.setActionCommand("Show plot title");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjCheckBoxShowPlotTitle;
    }

    /**
     * Return the ComboBoxLineColor property value.
     * 
     * @return javax.swing.JComboBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JComboBox getComboBoxLineColor() {
        if (ivjComboBoxLineColor == null) {
            try {
                javax.swing.plaf.metal.MetalComboBoxEditor.UIResource ivjLocalEditor1;
                ivjLocalEditor1 = new javax.swing.plaf.metal.MetalComboBoxEditor.UIResource();
                ivjLocalEditor1.setItem("Black");
                javax.swing.plaf.basic.BasicComboBoxRenderer.UIResource ivjLocalRenderer1;
                ivjLocalRenderer1 = new javax.swing.plaf.basic.BasicComboBoxRenderer.UIResource();
                ivjLocalRenderer1.setName("LocalRenderer1");
                ivjLocalRenderer1.setText("White");
                ivjLocalRenderer1
                        .setMaximumSize(new java.awt.Dimension(35, 16));
                ivjLocalRenderer1
                        .setMinimumSize(new java.awt.Dimension(35, 16));
                ivjLocalRenderer1.setForeground(new java.awt.Color(0, 0, 0));
                ivjComboBoxLineColor = new javax.swing.JComboBox();
                ivjComboBoxLineColor.setName("ComboBoxLineColor");
                ivjComboBoxLineColor.setEditor(ivjLocalEditor1);
                ivjComboBoxLineColor.setRenderer(ivjLocalRenderer1);
                ivjComboBoxLineColor.setSelectedItem("Black");
                ivjComboBoxLineColor.setMinimumSize(new java.awt.Dimension(108,
                        23));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjComboBoxLineColor;
    }

    /**
     * Return the ComboBoxLineType property value.
     * 
     * @return javax.swing.JComboBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JComboBox getComboBoxLineType() {
        if (ivjComboBoxLineType == null) {
            try {
                javax.swing.plaf.metal.MetalComboBoxEditor.UIResource ivjLocalEditor;
                ivjLocalEditor = new javax.swing.plaf.metal.MetalComboBoxEditor.UIResource();
                ivjLocalEditor.setItem("dashed");
                javax.swing.plaf.basic.BasicComboBoxRenderer.UIResource ivjLocalRenderer;
                ivjLocalRenderer = new javax.swing.plaf.basic.BasicComboBoxRenderer.UIResource();
                ivjLocalRenderer.setName("LocalRenderer");
                ivjLocalRenderer.setText("solid");
                ivjLocalRenderer.setMaximumSize(new java.awt.Dimension(29, 16));
                ivjLocalRenderer.setMinimumSize(new java.awt.Dimension(29, 16));
                ivjLocalRenderer.setForeground(new java.awt.Color(0, 0, 0));
                ivjComboBoxLineType = new javax.swing.JComboBox();
                ivjComboBoxLineType.setName("ComboBoxLineType");
                ivjComboBoxLineType.setEditor(ivjLocalEditor);
                ivjComboBoxLineType.setRenderer(ivjLocalRenderer);
                ivjComboBoxLineType.setSelectedItem("dashed");
                ivjComboBoxLineType.setMinimumSize(new java.awt.Dimension(141,
                        23));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjComboBoxLineType;
    }

    /**
     * Return the ComboBoxMainLines property value.
     * 
     * @return javax.swing.JComboBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JComboBox getComboBoxMainLines() {
        if (ivjComboBoxMainLines == null) {
            try {
                ivjComboBoxMainLines = new javax.swing.JComboBox();
                ivjComboBoxMainLines.setName("ComboBoxMainLines");
                ivjComboBoxMainLines
                        .setEditor(new javax.swing.plaf.metal.MetalComboBoxEditor.UIResource());
                ivjComboBoxMainLines
                        .setRenderer(new javax.swing.plaf.basic.BasicComboBoxRenderer.UIResource());
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjComboBoxMainLines;
    }

    /**
     * Return the DefaultsButton property value.
     * 
     * @return javax.swing.JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JButton getDefaultsButton() {
        if (ivjDefaultsButton == null) {
            try {
                ivjDefaultsButton = new javax.swing.JButton();
                ivjDefaultsButton.setName("DefaultsButton");
                ivjDefaultsButton.setText("Defaults");
                ivjDefaultsButton.setPreferredSize(new java.awt.Dimension(100,
                        25));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjDefaultsButton;
    }

    /**
     * Return the JCheckBox2 property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getDisplayXCheck() {
        if (ivjDisplayXCheck == null) {
            try {
                ivjDisplayXCheck = new javax.swing.JCheckBox();
                ivjDisplayXCheck.setName("DisplayXCheck");
                ivjDisplayXCheck.setText("Show x-label");
                ivjDisplayXCheck
                        .setMaximumSize(new java.awt.Dimension(176, 22));
                ivjDisplayXCheck.setActionCommand("Show x-label");
                ivjDisplayXCheck.setSelected(true);
                ivjDisplayXCheck
                        .setMinimumSize(new java.awt.Dimension(176, 22));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjDisplayXCheck;
    }

    /**
     * Return the JCheckBox3 property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getDisplayYCheck() {
        if (ivjDisplayYCheck == null) {
            try {
                ivjDisplayYCheck = new javax.swing.JCheckBox();
                ivjDisplayYCheck.setName("DisplayYCheck");
                ivjDisplayYCheck.setText("Show y-label");
                ivjDisplayYCheck
                        .setMaximumSize(new java.awt.Dimension(175, 22));
                ivjDisplayYCheck.setActionCommand("Show y-label and numbers");
                ivjDisplayYCheck.setSelected(true);
                ivjDisplayYCheck
                        .setMinimumSize(new java.awt.Dimension(175, 22));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjDisplayYCheck;
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
                ivjJLabel.setText("y-coordinate of lower left corner");
                ivjJLabel.setEnabled(false);
                ivjJLabel.setMinimumSize(new java.awt.Dimension(182, 14));
                ivjJLabel.setMaximumSize(new java.awt.Dimension(182, 14));
                // user code begin {1}
                ivjJLabel.setEnabled(false);
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
                ivjJLabel1.setText("Set height in points (pt).");
                ivjJLabel1.setMaximumSize(new java.awt.Dimension(133, 14));
                ivjJLabel1.setMinimumSize(new java.awt.Dimension(133, 14));
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
     * Return the JLabel10 property value.
     * 
     * @return javax.swing.JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JLabel getJLabel10() {
        if (ivjJLabel10 == null) {
            try {
                ivjJLabel10 = new javax.swing.JLabel();
                ivjJLabel10.setName("JLabel10");
                ivjJLabel10.setText("Line type:");
                ivjJLabel10.setMaximumSize(new java.awt.Dimension(54, 14));
                ivjJLabel10.setMinimumSize(new java.awt.Dimension(54, 14));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJLabel10;
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
                ivjJLabel11.setText("Line color:");
                ivjJLabel11.setMaximumSize(new java.awt.Dimension(59, 14));
                ivjJLabel11.setMinimumSize(new java.awt.Dimension(59, 14));
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
     * Return the JLabel12 property value.
     * 
     * @return javax.swing.JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JLabel getJLabel12() {
        if (ivjJLabel12 == null) {
            try {
                ivjJLabel12 = new javax.swing.JLabel();
                ivjJLabel12.setName("JLabel12");
                ivjJLabel12.setText("Line width:");
                ivjJLabel12.setMaximumSize(new java.awt.Dimension(61, 14));
                ivjJLabel12.setMinimumSize(new java.awt.Dimension(61, 14));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJLabel12;
    }

    /**
     * Return the JLabel13 property value.
     * 
     * @return javax.swing.JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JLabel getJLabel13() {
        if (ivjJLabel13 == null) {
            try {
                ivjJLabel13 = new javax.swing.JLabel();
                ivjJLabel13.setName("JLabel13");
                ivjJLabel13.setText("Legend string:");
                ivjJLabel13.setMaximumSize(new java.awt.Dimension(81, 14));
                ivjJLabel13.setMinimumSize(new java.awt.Dimension(81, 14));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJLabel13;
    }

    /**
     * Return the JLabel14 property value.
     * 
     * @return javax.swing.JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JLabel getJLabel14() {
        if (ivjJLabel14 == null) {
            try {
                ivjJLabel14 = new javax.swing.JLabel();
                ivjJLabel14.setName("JLabel14");
                ivjJLabel14
                        .setText("If height=0, GAUSS computes a default value (~10pt).");
                ivjJLabel14.setMaximumSize(new java.awt.Dimension(296, 14));
                ivjJLabel14.setMinimumSize(new java.awt.Dimension(296, 14));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJLabel14;
    }

    /**
     * Return the JLabel16 property value.
     * 
     * @return javax.swing.JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JLabel getJLabel16() {
        if (ivjJLabel16 == null) {
            try {
                ivjJLabel16 = new javax.swing.JLabel();
                ivjJLabel16.setName("JLabel16");
                ivjJLabel16
                        .setText("(If height=0, GAUSS computes a default value (~10pt))");
                ivjJLabel16.setMaximumSize(new java.awt.Dimension(301, 14));
                ivjJLabel16.setMinimumSize(new java.awt.Dimension(301, 14));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJLabel16;
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
                ivjJLabel2.setText("Axis labels, height in pt:");
                ivjJLabel2.setMaximumSize(new java.awt.Dimension(133, 14));
                ivjJLabel2.setMinimumSize(new java.awt.Dimension(133, 14));
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
                ivjJLabel3.setText("Axis numbers, height in pt:");
                ivjJLabel3.setMaximumSize(new java.awt.Dimension(150, 14));
                ivjJLabel3.setMinimumSize(new java.awt.Dimension(150, 14));
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
                ivjJLabel4
                        .setText("Magnification (see also 'Axes|Axis Numbers'):");
                ivjJLabel4.setMaximumSize(new java.awt.Dimension(259, 14));
                ivjJLabel4.setMinimumSize(new java.awt.Dimension(259, 14));
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
     * Return the JLabel5 property value.
     * 
     * @return javax.swing.JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JLabel getJLabel5() {
        if (ivjJLabel5 == null) {
            try {
                ivjJLabel5 = new javax.swing.JLabel();
                ivjJLabel5.setName("JLabel5");
                ivjJLabel5.setText("x-coordinate of lower left corner");
                ivjJLabel5.setEnabled(false);
                ivjJLabel5.setMinimumSize(new java.awt.Dimension(183, 14));
                ivjJLabel5.setMaximumSize(new java.awt.Dimension(183, 14));
                // user code begin {1}
                ivjJLabel5.setEnabled(false);
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJLabel5;
    }

    /**
     * Return the JLabel6 property value.
     * 
     * @return javax.swing.JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JLabel getJLabel6() {
        if (ivjJLabel6 == null) {
            try {
                ivjJLabel6 = new javax.swing.JLabel();
                ivjJLabel6.setName("JLabel6");
                ivjJLabel6
                        .setText("The legend can get a different position (in inches): ");
                ivjJLabel6.setEnabled(false);
                ivjJLabel6.setMinimumSize(new java.awt.Dimension(284, 14));
                ivjJLabel6.setMaximumSize(new java.awt.Dimension(284, 14));
                // user code begin {1}
                ivjJLabel6.setEnabled(false);
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJLabel6;
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
                ivjJLabel7.setText("Main title height in points:");
                ivjJLabel7.setMaximumSize(new java.awt.Dimension(143, 14));
                ivjJLabel7.setMinimumSize(new java.awt.Dimension(143, 14));
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
     * Return the JLabel8 property value.
     * 
     * @return javax.swing.JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JLabel getJLabel8() {
        if (ivjJLabel8 == null) {
            try {
                ivjJLabel8 = new javax.swing.JLabel();
                ivjJLabel8.setName("JLabel8");
                ivjJLabel8.setText("Plot title height in points:");
                ivjJLabel8.setMaximumSize(new java.awt.Dimension(138, 14));
                ivjJLabel8.setMinimumSize(new java.awt.Dimension(138, 14));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJLabel8;
    }

    /**
     * Return the JLabel9 property value.
     * 
     * @return javax.swing.JLabel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JLabel getJLabel9() {
        if (ivjJLabel9 == null) {
            try {
                ivjJLabel9 = new javax.swing.JLabel();
                ivjJLabel9.setName("JLabel9");
                ivjJLabel9
                        .setText("Select a specific main line and set attributes:");
                ivjJLabel9.setMaximumSize(new java.awt.Dimension(254, 14));
                ivjJLabel9.setMinimumSize(new java.awt.Dimension(254, 14));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjJLabel9;
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
                ivjJPanel1.setLayout(getJPanel1FlowLayout());
                ivjJPanel1.setMinimumSize(new java.awt.Dimension(285, 45));
                getJPanel1().add(getOKButton(), getOKButton().getName());
                getJPanel1()
                        .add(getCancelButton(), getCancelButton().getName());
                getJPanel1().add(getDefaultsButton(),
                        getDefaultsButton().getName());
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
     * Return the JPanel1FlowLayout property value.
     * 
     * @return java.awt.FlowLayout
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private java.awt.FlowLayout getJPanel1FlowLayout() {
        java.awt.FlowLayout ivjJPanel1FlowLayout = null;
        try {
            /* Create part */
            ivjJPanel1FlowLayout = new java.awt.FlowLayout();
            ivjJPanel1FlowLayout.setAlignment(java.awt.FlowLayout.CENTER);
            ivjJPanel1FlowLayout.setVgap(10);
            ivjJPanel1FlowLayout.setHgap(20);
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        ;
        return ivjJPanel1FlowLayout;
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
                ivjJTabbedPane1
                        .setBorder(new javax.swing.border.BevelBorder(0));
                ivjJTabbedPane1
                        .setMinimumSize(new java.awt.Dimension(442, 250));
                ivjJTabbedPane1.insertTab("Main Lines", null, getMainLines(),
                        "", 0);
                ivjJTabbedPane1.setEnabledAt(0, true);
                ivjJTabbedPane1.insertTab("Title", null, getTitlePage(), "", 1);
                ivjJTabbedPane1.setEnabledAt(1, true);
                ivjJTabbedPane1.insertTab("Legend", null, getLegend(), "", 2);
                ivjJTabbedPane1.setEnabledAt(2, true);
                ivjJTabbedPane1.insertTab("Axes", null, getAxes(), "", 3);
                ivjJTabbedPane1.setEnabledAt(3, true);
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
     * Return the JPanel11 property value.
     * 
     * @return javax.swing.JPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JPanel getLegend() {
        if (ivjLegend == null) {
            try {
                ivjLegend = new javax.swing.JPanel();
                ivjLegend.setName("Legend");
                ivjLegend.setLayout(new java.awt.GridBagLayout());
                ivjLegend.setVisible(false);
                ivjLegend.setMaximumSize(new java.awt.Dimension(2147483647,
                        2147483647));
                ivjLegend.setMinimumSize(new java.awt.Dimension(294, 208));

                java.awt.GridBagConstraints constraintsCheckBoxShowLegend = new java.awt.GridBagConstraints();
                constraintsCheckBoxShowLegend.gridx = 0;
                constraintsCheckBoxShowLegend.gridy = 0;
                constraintsCheckBoxShowLegend.gridwidth = 2;
                constraintsCheckBoxShowLegend.anchor = java.awt.GridBagConstraints.WEST;
                constraintsCheckBoxShowLegend.insets = new java.awt.Insets(10,
                        10, 0, 0);
                getLegend().add(getCheckBoxShowLegend(),
                        constraintsCheckBoxShowLegend);

                java.awt.GridBagConstraints constraintsJLabel4 = new java.awt.GridBagConstraints();
                constraintsJLabel4.gridx = 0;
                constraintsJLabel4.gridy = 1;
                constraintsJLabel4.gridwidth = 2;
                constraintsJLabel4.anchor = java.awt.GridBagConstraints.WEST;
                constraintsJLabel4.insets = new java.awt.Insets(5, 10, 0, 0);
                getLegend().add(getJLabel4(), constraintsJLabel4);

                java.awt.GridBagConstraints constraintsJLabel5 = new java.awt.GridBagConstraints();
                constraintsJLabel5.gridx = 0;
                constraintsJLabel5.gridy = 5;
                constraintsJLabel5.anchor = java.awt.GridBagConstraints.WEST;
                constraintsJLabel5.insets = new java.awt.Insets(5, 10, 0, 0);
                getLegend().add(getJLabel5(), constraintsJLabel5);

                java.awt.GridBagConstraints constraintsJLabel = new java.awt.GridBagConstraints();
                constraintsJLabel.gridx = 0;
                constraintsJLabel.gridy = 6;
                constraintsJLabel.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsJLabel.insets = new java.awt.Insets(5, 10, 0, 0);
                getLegend().add(getJLabel(), constraintsJLabel);

                java.awt.GridBagConstraints constraintsTextFieldXPositionLegend = new java.awt.GridBagConstraints();
                constraintsTextFieldXPositionLegend.gridx = 1;
                constraintsTextFieldXPositionLegend.gridy = 5;
                constraintsTextFieldXPositionLegend.anchor = java.awt.GridBagConstraints.WEST;
                constraintsTextFieldXPositionLegend.insets = new java.awt.Insets(
                        5, 10, 0, 0);
                getLegend().add(getTextFieldXPositionLegend(),
                        constraintsTextFieldXPositionLegend);

                java.awt.GridBagConstraints constraintsTextFieldYPositionLegend = new java.awt.GridBagConstraints();
                constraintsTextFieldYPositionLegend.gridx = 1;
                constraintsTextFieldYPositionLegend.gridy = 6;
                constraintsTextFieldYPositionLegend.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsTextFieldYPositionLegend.weightx = 1.0;
                constraintsTextFieldYPositionLegend.weighty = 1.0;
                constraintsTextFieldYPositionLegend.insets = new java.awt.Insets(
                        5, 10, 0, 0);
                getLegend().add(getTextFieldYPositionLegend(),
                        constraintsTextFieldYPositionLegend);

                java.awt.GridBagConstraints constraintsSliderHeightLegend = new java.awt.GridBagConstraints();
                constraintsSliderHeightLegend.gridx = 0;
                constraintsSliderHeightLegend.gridy = 2;
                constraintsSliderHeightLegend.gridwidth = 2;
                constraintsSliderHeightLegend.anchor = java.awt.GridBagConstraints.WEST;
                constraintsSliderHeightLegend.insets = new java.awt.Insets(5,
                        10, 0, 10);
                getLegend().add(getSliderHeightLegend(),
                        constraintsSliderHeightLegend);

                java.awt.GridBagConstraints constraintsJLabel6 = new java.awt.GridBagConstraints();
                constraintsJLabel6.gridx = 0;
                constraintsJLabel6.gridy = 4;
                constraintsJLabel6.gridwidth = 2;
                constraintsJLabel6.anchor = java.awt.GridBagConstraints.WEST;
                constraintsJLabel6.insets = new java.awt.Insets(15, 10, 0, 0);
                getLegend().add(getJLabel6(), constraintsJLabel6);

                java.awt.GridBagConstraints constraintsLegendDefaultCheck = new java.awt.GridBagConstraints();
                constraintsLegendDefaultCheck.gridx = 0;
                constraintsLegendDefaultCheck.gridy = 3;
                constraintsLegendDefaultCheck.gridwidth = 2;
                constraintsLegendDefaultCheck.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsLegendDefaultCheck.insets = new java.awt.Insets(10,
                        10, 0, 0);
                getLegend().add(getLegendDefaultCheck(),
                        constraintsLegendDefaultCheck);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjLegend;
    }

    /**
     * Return the JCheckBox1 property value.
     * 
     * @return javax.swing.JCheckBox
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JCheckBox getLegendDefaultCheck() {
        if (ivjLegendDefaultCheck == null) {
            try {
                ivjLegendDefaultCheck = new javax.swing.JCheckBox();
                ivjLegendDefaultCheck.setName("LegendDefaultCheck");
                ivjLegendDefaultCheck
                        .setText("Display legend at default position");
                ivjLegendDefaultCheck.setMaximumSize(new java.awt.Dimension(
                        211, 22));
                ivjLegendDefaultCheck
                        .setActionCommand("Display legend at default position");
                ivjLegendDefaultCheck.setSelected(true);
                ivjLegendDefaultCheck.setMinimumSize(new java.awt.Dimension(
                        211, 22));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjLegendDefaultCheck;
    }

    /**
     * Return the JPanel3 property value.
     * 
     * @return javax.swing.JPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JPanel getMainLines() {
        if (ivjMainLines == null) {
            try {
                ivjMainLines = new javax.swing.JPanel();
                ivjMainLines.setName("MainLines");
                ivjMainLines.setLayout(new java.awt.GridBagLayout());
                ivjMainLines.setVisible(false);
                ivjMainLines.setMaximumSize(new java.awt.Dimension(2147483647,
                        2147483647));
                ivjMainLines.setMinimumSize(new java.awt.Dimension(264, 204));

                java.awt.GridBagConstraints constraintsComboBoxMainLines = new java.awt.GridBagConstraints();
                constraintsComboBoxMainLines.gridx = 0;
                constraintsComboBoxMainLines.gridy = 1;
                constraintsComboBoxMainLines.gridwidth = 3;
                constraintsComboBoxMainLines.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsComboBoxMainLines.weightx = 1.0;
                constraintsComboBoxMainLines.insets = new java.awt.Insets(10,
                        10, 0, 10);
                getMainLines().add(getComboBoxMainLines(),
                        constraintsComboBoxMainLines);

                java.awt.GridBagConstraints constraintsJLabel9 = new java.awt.GridBagConstraints();
                constraintsJLabel9.gridx = 0;
                constraintsJLabel9.gridy = 0;
                constraintsJLabel9.gridwidth = 2;
                constraintsJLabel9.anchor = java.awt.GridBagConstraints.WEST;
                constraintsJLabel9.insets = new java.awt.Insets(10, 10, 0, 0);
                getMainLines().add(getJLabel9(), constraintsJLabel9);

                java.awt.GridBagConstraints constraintsJLabel10 = new java.awt.GridBagConstraints();
                constraintsJLabel10.gridx = 0;
                constraintsJLabel10.gridy = 3;
                constraintsJLabel10.anchor = java.awt.GridBagConstraints.WEST;
                constraintsJLabel10.insets = new java.awt.Insets(10, 10, 0, 0);
                getMainLines().add(getJLabel10(), constraintsJLabel10);

                java.awt.GridBagConstraints constraintsJLabel11 = new java.awt.GridBagConstraints();
                constraintsJLabel11.gridx = 0;
                constraintsJLabel11.gridy = 4;
                constraintsJLabel11.anchor = java.awt.GridBagConstraints.WEST;
                constraintsJLabel11.insets = new java.awt.Insets(10, 10, 0, 0);
                getMainLines().add(getJLabel11(), constraintsJLabel11);

                java.awt.GridBagConstraints constraintsJLabel12 = new java.awt.GridBagConstraints();
                constraintsJLabel12.gridx = 0;
                constraintsJLabel12.gridy = 5;
                constraintsJLabel12.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsJLabel12.insets = new java.awt.Insets(10, 10, 0, 0);
                getMainLines().add(getJLabel12(), constraintsJLabel12);

                java.awt.GridBagConstraints constraintsComboBoxLineType = new java.awt.GridBagConstraints();
                constraintsComboBoxLineType.gridx = 1;
                constraintsComboBoxLineType.gridy = 3;
                constraintsComboBoxLineType.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsComboBoxLineType.insets = new java.awt.Insets(10,
                        10, 0, 0);
                getMainLines().add(getComboBoxLineType(),
                        constraintsComboBoxLineType);

                java.awt.GridBagConstraints constraintsComboBoxLineColor = new java.awt.GridBagConstraints();
                constraintsComboBoxLineColor.gridx = 1;
                constraintsComboBoxLineColor.gridy = 4;
                constraintsComboBoxLineColor.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsComboBoxLineColor.insets = new java.awt.Insets(10,
                        10, 0, 0);
                getMainLines().add(getComboBoxLineColor(),
                        constraintsComboBoxLineColor);

                java.awt.GridBagConstraints constraintsJLabel13 = new java.awt.GridBagConstraints();
                constraintsJLabel13.gridx = 0;
                constraintsJLabel13.gridy = 2;
                constraintsJLabel13.anchor = java.awt.GridBagConstraints.WEST;
                constraintsJLabel13.insets = new java.awt.Insets(10, 10, 0, 0);
                getMainLines().add(getJLabel13(), constraintsJLabel13);

                java.awt.GridBagConstraints constraintsTextFieldLineString = new java.awt.GridBagConstraints();
                constraintsTextFieldLineString.gridx = 1;
                constraintsTextFieldLineString.gridy = 2;
                constraintsTextFieldLineString.gridwidth = 2;
                constraintsTextFieldLineString.fill = java.awt.GridBagConstraints.HORIZONTAL;
                constraintsTextFieldLineString.insets = new java.awt.Insets(10,
                        10, 0, 10);
                getMainLines().add(getTextFieldLineString(),
                        constraintsTextFieldLineString);

                java.awt.GridBagConstraints constraintsSliderLineWidth = new java.awt.GridBagConstraints();
                constraintsSliderLineWidth.gridx = 1;
                constraintsSliderLineWidth.gridy = 5;
                constraintsSliderLineWidth.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsSliderLineWidth.weighty = 1.0;
                constraintsSliderLineWidth.insets = new java.awt.Insets(10, 10,
                        0, 0);
                getMainLines().add(getSliderLineWidth(),
                        constraintsSliderLineWidth);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjMainLines;
    }

    /**
     * Return the OKButton property value.
     * 
     * @return javax.swing.JButton
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JButton getOKButton() {
        if (ivjOKButton == null) {
            try {
                ivjOKButton = new javax.swing.JButton();
                ivjOKButton.setName("OKButton");
                ivjOKButton.setText("OK");
                ivjOKButton.setPreferredSize(new java.awt.Dimension(100, 25));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjOKButton;
    }

    /**
     * Return the PlotControlDialogContentPane1 property value.
     * 
     * @return javax.swing.JPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JPanel getPlotControlDialogContentPane1() {
        if (ivjPlotControlDialogContentPane1 == null) {
            try {
                ivjPlotControlDialogContentPane1 = new javax.swing.JPanel();
                ivjPlotControlDialogContentPane1
                        .setName("PlotControlDialogContentPane1");
                ivjPlotControlDialogContentPane1
                        .setLayout(getPlotControlDialogContentPane1BorderLayout());
                ivjPlotControlDialogContentPane1
                        .setMaximumSize(new java.awt.Dimension(2147483647,
                                2147483647));
                ivjPlotControlDialogContentPane1
                        .setMinimumSize(new java.awt.Dimension(442, 295));
                ivjPlotControlDialogContentPane1.setBounds(0, 0, 0, 0);
                getPlotControlDialogContentPane1().add(getJPanel1(), "South");
                getPlotControlDialogContentPane1().add(getJTabbedPane1(),
                        "Center");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjPlotControlDialogContentPane1;
    }

    /**
     * Return the PlotControlDialogContentPane1BorderLayout property value.
     * 
     * @return java.awt.BorderLayout
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private java.awt.BorderLayout getPlotControlDialogContentPane1BorderLayout() {
        java.awt.BorderLayout ivjPlotControlDialogContentPane1BorderLayout = null;
        try {
            /* Create part */
            ivjPlotControlDialogContentPane1BorderLayout = new java.awt.BorderLayout();
            ivjPlotControlDialogContentPane1BorderLayout.setVgap(0);
            ivjPlotControlDialogContentPane1BorderLayout.setHgap(0);
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        ;
        return ivjPlotControlDialogContentPane1BorderLayout;
    }

    /**
     * Gets the plot control model for the underlying input mask.
     * 
     * @return the current model with the selected settings
     */
    public PlotControlModel getPlotControlModel() {
        return plotControlModel;
    }

    /**
     * Return the SliderHeightAxisLabels property value.
     * 
     * @return javax.swing.JSlider
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JSlider getSliderHeightAxisLabels() {
        if (ivjSliderHeightAxisLabels == null) {
            try {
                ivjSliderHeightAxisLabels = new javax.swing.JSlider();
                ivjSliderHeightAxisLabels.setName("SliderHeightAxisLabels");
                ivjSliderHeightAxisLabels.setPaintLabels(true);
                ivjSliderHeightAxisLabels
                        .setMaximumSize(new java.awt.Dimension(32767, 41));
                ivjSliderHeightAxisLabels.setPaintTicks(true);
                ivjSliderHeightAxisLabels.setValue(0);
                ivjSliderHeightAxisLabels.setMajorTickSpacing(20);
                ivjSliderHeightAxisLabels.setFont(new java.awt.Font("Dialog",
                        0, 12));
                ivjSliderHeightAxisLabels.setSnapToTicks(true);
                ivjSliderHeightAxisLabels.setMinorTickSpacing(1);
                ivjSliderHeightAxisLabels
                        .setMinimumSize(new java.awt.Dimension(36, 41));
                // user code begin {1}
                ivjSliderHeightAxisLabels
                        .setPreferredSize(new java.awt.Dimension(300, 43));
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSliderHeightAxisLabels;
    }

    /**
     * Return the SliderHeightAxisNumbers property value.
     * 
     * @return javax.swing.JSlider
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JSlider getSliderHeightAxisNumbers() {
        if (ivjSliderHeightAxisNumbers == null) {
            try {
                ivjSliderHeightAxisNumbers = new javax.swing.JSlider();
                ivjSliderHeightAxisNumbers.setName("SliderHeightAxisNumbers");
                ivjSliderHeightAxisNumbers.setPaintLabels(true);
                ivjSliderHeightAxisNumbers
                        .setMaximumSize(new java.awt.Dimension(32767, 41));
                ivjSliderHeightAxisNumbers.setPaintTicks(true);
                ivjSliderHeightAxisNumbers.setValue(0);
                ivjSliderHeightAxisNumbers.setMajorTickSpacing(20);
                ivjSliderHeightAxisNumbers.setFont(new java.awt.Font("Dialog",
                        0, 12));
                ivjSliderHeightAxisNumbers.setSnapToTicks(true);
                ivjSliderHeightAxisNumbers.setMinorTickSpacing(1);
                ivjSliderHeightAxisNumbers
                        .setMinimumSize(new java.awt.Dimension(36, 41));
                // user code begin {1}
                ivjSliderHeightAxisNumbers
                        .setPreferredSize(new java.awt.Dimension(300, 43));
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSliderHeightAxisNumbers;
    }

    /**
     * Return the SliderHeightLegend property value.
     * 
     * @return javax.swing.JSlider
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JSlider getSliderHeightLegend() {
        if (ivjSliderHeightLegend == null) {
            try {
                ivjSliderHeightLegend = new javax.swing.JSlider();
                ivjSliderHeightLegend.setName("SliderHeightLegend");
                ivjSliderHeightLegend.setPaintLabels(true);
                ivjSliderHeightLegend.setMaximumSize(new java.awt.Dimension(
                        32767, 41));
                ivjSliderHeightLegend.setPaintTicks(true);
                ivjSliderHeightLegend.setValue(5);
                ivjSliderHeightLegend.setMajorTickSpacing(1);
                ivjSliderHeightLegend
                        .setFont(new java.awt.Font("Dialog", 0, 12));
                ivjSliderHeightLegend.setSnapToTicks(true);
                ivjSliderHeightLegend.setMaximum(9);
                ivjSliderHeightLegend.setMinimum(1);
                ivjSliderHeightLegend.setMinimumSize(new java.awt.Dimension(36,
                        41));
                // user code begin {1}
                ivjSliderHeightLegend.setPreferredSize(new java.awt.Dimension(
                        300, 43));
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSliderHeightLegend;
    }

    /**
     * Return the SliderHeightMainTitle property value.
     * 
     * @return javax.swing.JSlider
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JSlider getSliderHeightMainTitle() {
        if (ivjSliderHeightMainTitle == null) {
            try {
                ivjSliderHeightMainTitle = new javax.swing.JSlider();
                ivjSliderHeightMainTitle.setName("SliderHeightMainTitle");
                ivjSliderHeightMainTitle.setPaintLabels(true);
                ivjSliderHeightMainTitle.setMaximumSize(new java.awt.Dimension(
                        32767, 41));
                ivjSliderHeightMainTitle.setPaintTicks(true);
                ivjSliderHeightMainTitle.setValue(0);
                ivjSliderHeightMainTitle.setMajorTickSpacing(20);
                ivjSliderHeightMainTitle.setFont(new java.awt.Font("Dialog", 0,
                        12));
                ivjSliderHeightMainTitle.setSnapToTicks(true);
                ivjSliderHeightMainTitle.setMinorTickSpacing(1);
                ivjSliderHeightMainTitle.setMinimumSize(new java.awt.Dimension(
                        36, 41));
                // user code begin {1}
                ivjSliderHeightMainTitle
                        .setPreferredSize(new java.awt.Dimension(300, 43));
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSliderHeightMainTitle;
    }

    /**
     * Return the SliderHeightPlotTitle property value.
     * 
     * @return javax.swing.JSlider
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JSlider getSliderHeightPlotTitle() {
        if (ivjSliderHeightPlotTitle == null) {
            try {
                ivjSliderHeightPlotTitle = new javax.swing.JSlider();
                ivjSliderHeightPlotTitle.setName("SliderHeightPlotTitle");
                ivjSliderHeightPlotTitle.setPaintLabels(true);
                ivjSliderHeightPlotTitle.setMaximumSize(new java.awt.Dimension(
                        32767, 41));
                ivjSliderHeightPlotTitle.setPaintTicks(true);
                ivjSliderHeightPlotTitle.setValue(0);
                ivjSliderHeightPlotTitle.setMajorTickSpacing(20);
                ivjSliderHeightPlotTitle.setFont(new java.awt.Font("Dialog", 0,
                        12));
                ivjSliderHeightPlotTitle.setSnapToTicks(true);
                ivjSliderHeightPlotTitle.setMinorTickSpacing(1);
                ivjSliderHeightPlotTitle.setMinimumSize(new java.awt.Dimension(
                        36, 41));
                // user code begin {1}
                ivjSliderHeightPlotTitle
                        .setPreferredSize(new java.awt.Dimension(300, 43));
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSliderHeightPlotTitle;
    }

    /**
     * Return the SliderLineWidth property value.
     * 
     * @return javax.swing.JSlider
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JSlider getSliderLineWidth() {
        if (ivjSliderLineWidth == null) {
            try {
                ivjSliderLineWidth = new javax.swing.JSlider();
                ivjSliderLineWidth.setName("SliderLineWidth");
                ivjSliderLineWidth.setPaintLabels(true);
                ivjSliderLineWidth.setMaximumSize(new java.awt.Dimension(32767,
                        41));
                ivjSliderLineWidth.setPaintTicks(true);
                ivjSliderLineWidth.setValue(0);
                ivjSliderLineWidth.setMajorTickSpacing(10);
                ivjSliderLineWidth.setFont(new java.awt.Font("Dialog", 0, 12));
                ivjSliderLineWidth.setSnapToTicks(true);
                ivjSliderLineWidth.setMaximum(50);
                ivjSliderLineWidth.setMinorTickSpacing(1);
                ivjSliderLineWidth
                        .setMinimumSize(new java.awt.Dimension(36, 41));
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSliderLineWidth;
    }

    /**
     * Return the TextFieldLineString property value.
     * 
     * @return javax.swing.JTextField
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JTextField getTextFieldLineString() {
        if (ivjTextFieldLineString == null) {
            try {
                ivjTextFieldLineString = new javax.swing.JTextField();
                ivjTextFieldLineString.setName("TextFieldLineString");
                ivjTextFieldLineString
                        .setHighlighter(new javax.swing.plaf.basic.BasicTextUI.BasicHighlighter());
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjTextFieldLineString;
    }

    /**
     * Return the TextFieldXPositionLegend property value.
     * 
     * @return com.jstatcom.model.NumberSelector
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private NumSelector getTextFieldXPositionLegend() {
        if (ivjTextFieldXPositionLegend == null) {
            try {
                ivjTextFieldXPositionLegend = new NumSelector();
                ivjTextFieldXPositionLegend.setName("TextFieldXPositionLegend");
                ivjTextFieldXPositionLegend.setNumber(0.1);
                ivjTextFieldXPositionLegend.setPrecision(1);
                ivjTextFieldXPositionLegend
                        .setHighlighter(new javax.swing.plaf.basic.BasicTextUI.BasicHighlighter());
                ivjTextFieldXPositionLegend.setText("0.1");
                ivjTextFieldXPositionLegend.setRangeExpr("[-1000.0, 1000.0]");
                ivjTextFieldXPositionLegend.setColumns(7);
                ivjTextFieldXPositionLegend.setEnabled(false);
                // user code begin {1}
                ivjTextFieldXPositionLegend.setEnabled(false);
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjTextFieldXPositionLegend;
    }

    /**
     * Return the TextFieldYPositionLegend property value.
     * 
     * @return com.jstatcom.model.NumberSelector
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private NumSelector getTextFieldYPositionLegend() {
        if (ivjTextFieldYPositionLegend == null) {
            try {
                ivjTextFieldYPositionLegend = new NumSelector();
                ivjTextFieldYPositionLegend.setName("TextFieldYPositionLegend");
                ivjTextFieldYPositionLegend.setNumber(0.1);
                ivjTextFieldYPositionLegend.setPrecision(1);
                ivjTextFieldYPositionLegend
                        .setHighlighter(new javax.swing.plaf.basic.BasicTextUI.BasicHighlighter());
                ivjTextFieldYPositionLegend.setText("0.1");
                ivjTextFieldYPositionLegend.setRangeExpr("[-1000.0, 1000.0]");
                ivjTextFieldYPositionLegend.setColumns(7);
                ivjTextFieldYPositionLegend.setEnabled(false);
                // user code begin {1}
                ivjTextFieldYPositionLegend.setEnabled(false);
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjTextFieldYPositionLegend;
    }

    /**
     * Return the JPanel2 property value.
     * 
     * @return javax.swing.JPanel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JPanel getTitlePage() {
        if (ivjTitlePage == null) {
            try {
                ivjTitlePage = new javax.swing.JPanel();
                ivjTitlePage.setName("TitlePage");
                ivjTitlePage.setLayout(new java.awt.GridBagLayout());
                ivjTitlePage.setVisible(false);
                ivjTitlePage.setMaximumSize(new java.awt.Dimension(2147483647,
                        2147483647));
                ivjTitlePage.setMinimumSize(new java.awt.Dimension(433, 207));

                java.awt.GridBagConstraints constraintsCheckBoxShowDate = new java.awt.GridBagConstraints();
                constraintsCheckBoxShowDate.gridx = 0;
                constraintsCheckBoxShowDate.gridy = 0;
                constraintsCheckBoxShowDate.anchor = java.awt.GridBagConstraints.WEST;
                constraintsCheckBoxShowDate.insets = new java.awt.Insets(10,
                        10, 0, 0);
                getTitlePage().add(getCheckBoxShowDate(),
                        constraintsCheckBoxShowDate);

                java.awt.GridBagConstraints constraintsCheckBoxShowMainTitle = new java.awt.GridBagConstraints();
                constraintsCheckBoxShowMainTitle.gridx = 0;
                constraintsCheckBoxShowMainTitle.gridy = 1;
                constraintsCheckBoxShowMainTitle.anchor = java.awt.GridBagConstraints.WEST;
                constraintsCheckBoxShowMainTitle.insets = new java.awt.Insets(
                        10, 10, 0, 0);
                getTitlePage().add(getCheckBoxShowMainTitle(),
                        constraintsCheckBoxShowMainTitle);

                java.awt.GridBagConstraints constraintsCheckBoxShowPlotTitle = new java.awt.GridBagConstraints();
                constraintsCheckBoxShowPlotTitle.gridx = 0;
                constraintsCheckBoxShowPlotTitle.gridy = 3;
                constraintsCheckBoxShowPlotTitle.anchor = java.awt.GridBagConstraints.WEST;
                constraintsCheckBoxShowPlotTitle.insets = new java.awt.Insets(
                        10, 10, 0, 0);
                getTitlePage().add(getCheckBoxShowPlotTitle(),
                        constraintsCheckBoxShowPlotTitle);

                java.awt.GridBagConstraints constraintsJLabel7 = new java.awt.GridBagConstraints();
                constraintsJLabel7.gridx = 1;
                constraintsJLabel7.gridy = 1;
                constraintsJLabel7.anchor = java.awt.GridBagConstraints.WEST;
                constraintsJLabel7.insets = new java.awt.Insets(10, 10, 0, 0);
                getTitlePage().add(getJLabel7(), constraintsJLabel7);

                java.awt.GridBagConstraints constraintsJLabel8 = new java.awt.GridBagConstraints();
                constraintsJLabel8.gridx = 1;
                constraintsJLabel8.gridy = 3;
                constraintsJLabel8.anchor = java.awt.GridBagConstraints.WEST;
                constraintsJLabel8.insets = new java.awt.Insets(10, 10, 0, 0);
                getTitlePage().add(getJLabel8(), constraintsJLabel8);

                java.awt.GridBagConstraints constraintsSliderHeightMainTitle = new java.awt.GridBagConstraints();
                constraintsSliderHeightMainTitle.gridx = 1;
                constraintsSliderHeightMainTitle.gridy = 2;
                constraintsSliderHeightMainTitle.anchor = java.awt.GridBagConstraints.WEST;
                constraintsSliderHeightMainTitle.insets = new java.awt.Insets(
                        5, 10, 0, 10);
                getTitlePage().add(getSliderHeightMainTitle(),
                        constraintsSliderHeightMainTitle);

                java.awt.GridBagConstraints constraintsSliderHeightPlotTitle = new java.awt.GridBagConstraints();
                constraintsSliderHeightPlotTitle.gridx = 1;
                constraintsSliderHeightPlotTitle.gridy = 4;
                constraintsSliderHeightPlotTitle.anchor = java.awt.GridBagConstraints.NORTHWEST;
                constraintsSliderHeightPlotTitle.weightx = 1.0;
                constraintsSliderHeightPlotTitle.weighty = 1.0;
                constraintsSliderHeightPlotTitle.insets = new java.awt.Insets(
                        5, 10, 0, 10);
                getTitlePage().add(getSliderHeightPlotTitle(),
                        constraintsSliderHeightPlotTitle);

                java.awt.GridBagConstraints constraintsJLabel16 = new java.awt.GridBagConstraints();
                constraintsJLabel16.gridx = 1;
                constraintsJLabel16.gridy = 5;
                constraintsJLabel16.gridwidth = 2;
                constraintsJLabel16.anchor = java.awt.GridBagConstraints.WEST;
                constraintsJLabel16.insets = new java.awt.Insets(0, 10, 5, 0);
                getTitlePage().add(getJLabel16(), constraintsJLabel16);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjTitlePage;
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
     * Converts inch to a point.
     * 
     * @return double
     * @param pt
     *            int
     */
    private int inch2pt(double inch) {
        return (int) (72.27 * inch);
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
        getLegendDefaultCheck().addItemListener(ivjEventHandler);
        getOKButton().addActionListener(ivjEventHandler);
        getCancelButton().addActionListener(ivjEventHandler);
        getDefaultsButton().addActionListener(ivjEventHandler);
        getComboBoxMainLines().addActionListener(ivjEventHandler);
    }

    /**
     * Initialize the class.
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void initialize() {
        try {
            // user code begin {1}
            // user code end
            setName("PlotControlDialog");
            setDefaultCloseOperation(2);
            setTitle("Plot Settings for GAUSS");
            setModal(true);
            setBounds(new java.awt.Rectangle(0, 0, 546, 363));
            setSize(546, 363);
            setResizable(false);
            setContentPane(getPlotControlDialogContentPane1());
            initConnections();
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}
        getComboBoxLineType().setModel(
                new DefaultComboBoxModel(GAUSS_LINESTYLES));

        getComboBoxLineColor().setModel(new DefaultComboBoxModel(GAUSS_COLORS));

        // user code end
    }

    /**
     * Comment
     */
    private void legendDefaultCheck_ItemStateChanged() {
        boolean isEn = !getLegendDefaultCheck().isSelected();
        getJLabel6().setEnabled(isEn);
        getJLabel5().setEnabled(isEn);
        getJLabel().setEnabled(isEn);
        getTextFieldXPositionLegend().setEnabled(isEn);
        getTextFieldYPositionLegend().setEnabled(isEn);
    }

    /**
     * Stores all changes in <code>plotControlModel</code> and hides dialog.
     */
    private void oKButton_ActionEvents() {
        plotControlModel.height_axis.setVal(pt2inch(getSliderHeightAxisLabels()
                .getValue()));
        plotControlModel.height_numbers
                .setVal(pt2inch(getSliderHeightAxisNumbers().getValue()));

        plotControlModel.show_date
                .setVal((getCheckBoxShowDate().isSelected() ? 1 : 0));
        plotControlModel.show_main_title.setVal((getCheckBoxShowMainTitle()
                .isSelected() ? 1 : 0));
        plotControlModel.show_plot_title.setVal((getCheckBoxShowPlotTitle()
                .isSelected() ? 1 : 0));
        plotControlModel.height_main_title
                .setVal((pt2inch(getSliderHeightMainTitle().getValue())));
        plotControlModel.height_plot_title
                .setVal((pt2inch(getSliderHeightPlotTitle().getValue())));

        if (getComboBoxMainLines().getItemCount() > 0)
            saveAttributesForLine(getComboBoxMainLines().getSelectedIndex());

        plotControlModel.legend_strings.setVal(legend_strings);

        plotControlModel.line_type.setVal(line_types);
        plotControlModel.line_width.setVal(line_width);
        plotControlModel.line_color.setVal(line_color);

        int isX = getDisplayXCheck().isSelected() ? 1 : 0;
        int isY = getDisplayYCheck().isSelected() ? 1 : 0;
        int isZ = getJDisplayZCheck().isSelected() ? 1 : 0;

        // legend settings
        int isLD = getLegendDefaultCheck().isSelected() ? 1 : 0;
        plotControlModel.is_legend_default.setVal(isLD);

        plotControlModel.show_legend.setVal((getCheckBoxShowLegend()
                .isSelected() ? 1 : 0));
        plotControlModel.height_legend.setVal(getSliderHeightLegend()
                .getValue());

        // legend position or default position 0.1;0.1 if selected
        plotControlModel.legend_x
                .setVal((isLD == 0) ? getTextFieldXPositionLegend().getNumber()
                        : 0.1);
        plotControlModel.legend_y
                .setVal((isLD == 0) ? getTextFieldYPositionLegend().getNumber()
                        : 0.1);

        plotControlModel.is_axnum.setVal(new double[][] { { isX }, { isY } });
        plotControlModel.is_xlabel.setVal(isX);
        plotControlModel.is_zlabel.setVal(isZ);

        plotControlModel.is_ylabel.setVal(isY);
        setVisible(false);
    }

    /**
     * Converts a point to an inch.
     * 
     * @return double
     * @param pt
     *            int
     */
    private double pt2inch(int pt) {
        return pt / 72.27;
    }

    /**
     * Comment
     */
    private void saveAttributesForLine(int idx) {
        if (idx >= 0) {
            // save current settings intermediate
            if (legend_strings != null && idx < legend_strings.length) {
                legend_strings[idx] = getTextFieldLineString().getText();
            }
            if (line_color != null && idx < line_color.length) {
                line_color[idx] = getComboBoxLineColor().getSelectedIndex();
            }
            if (line_types != null && idx < line_types.length) {
                line_types[idx] = getComboBoxLineType().getSelectedIndex() + 1;
            }
            if (line_width != null && idx < line_width.length) {
                line_width[idx] = getSliderLineWidth().getValue();
            }
        }
    }

    /**
     * Sets the plot control model for the underlying input mask.
     * 
     * @param newPlotControlModel
     *            a plot control model
     * @throws IllegalArgumentException
     *             <code>if (newPlotControlModel == null)</code>
     */
    public void setPlotControlModel(PlotControlModel newPlotControlModel) {
        if (newPlotControlModel == null)
            throw new IllegalArgumentException("PlotControlModel is null.");

        plotControlModel = newPlotControlModel;

        getSliderHeightAxisLabels().setValue(
                inch2pt(plotControlModel.height_axis.doubleVal()));
        getSliderHeightAxisNumbers().setValue(
                inch2pt(plotControlModel.height_numbers.doubleVal()));

        getCheckBoxShowLegend().setSelected(
                plotControlModel.show_legend.doubleVal() != 0.0);
        getSliderHeightLegend().setValue(
                plotControlModel.height_legend.intVal());
        getTextFieldXPositionLegend().setNumber(
                plotControlModel.legend_x.doubleVal());
        getTextFieldYPositionLegend().setNumber(
                plotControlModel.legend_y.doubleVal());

        getCheckBoxShowDate().setSelected(
                plotControlModel.show_date.doubleVal() != 0.0);
        getCheckBoxShowMainTitle().setSelected(
                plotControlModel.show_main_title.doubleVal() != 0.0);
        getCheckBoxShowPlotTitle().setSelected(
                plotControlModel.show_plot_title.doubleVal() != 0.0);
        getSliderHeightMainTitle().setValue(
                inch2pt(plotControlModel.height_main_title.doubleVal()));
        getSliderHeightPlotTitle().setValue(
                inch2pt(plotControlModel.height_plot_title.doubleVal()));

        // check whether the main lines are set
        boolean isLinesSet = !plotControlModel.legend_strings.isEmpty();
        line_width = null;
        legend_strings = null;
        line_color = null;
        line_types = null;
        if (isLinesSet) {
            int prevIndex = getComboBoxMainLines().getSelectedIndex();
            line_width = UMatrix.getDoubleCol(plotControlModel.line_width
                    .doubleArray(), 0);
            line_types = UMatrix.getDoubleCol(plotControlModel.line_type
                    .doubleArray(), 0);
            line_color = UMatrix.getDoubleCol(plotControlModel.line_color
                    .doubleArray(), 0);

            legend_strings = new String[plotControlModel.legend_strings.rows()];
            for (int i = 0; i < legend_strings.length; i++)
                legend_strings[i] = plotControlModel.legend_strings.stringAt(i,
                        0);

            // due to item state changed, the method displayNewIndex is invoked
            // which sets the controls to the correct values
            getComboBoxMainLines().setModel(
                    new DefaultComboBoxModel(legend_strings));
            if (prevIndex < getComboBoxMainLines().getItemCount()
                    && prevIndex > -1)
                getComboBoxMainLines().setSelectedIndex(prevIndex);
            else
                getComboBoxMainLines().setSelectedIndex(0);

        }

        line_index_displayed = -1;
        getDisplayXCheck().setSelected(plotControlModel.is_xlabel.intVal() > 0);
        getDisplayYCheck().setSelected(plotControlModel.is_ylabel.intVal() > 0);
        getJDisplayZCheck()
                .setSelected(plotControlModel.is_zlabel.intVal() > 0);
        getLegendDefaultCheck().setSelected(
                plotControlModel.is_legend_default.intVal() > 0);

    }

    /**
     * Sets this dialog to a slightly simple version, not displaying all
     * options.
     * 
     * @param isSimple
     *            <code>true</code> if simpler
     */
    public void setSimpleVersion(boolean isSimple) {

        getMainLines().setVisible(!isSimple);

    }

    /**
     * Overwrites supermethod to sync display with model when shown.
     * 
     * @param visible
     *            only synced if <code>true</code>
     */
    public void setVisible(boolean visible) {
        if (visible)
            setPlotControlModel(plotControlModel);
        super.setVisible(visible);

    }

    /**
     * Gets an instance of this class with simple to adjust features.
     * 
     * @return simple instance
     */
    public static PlotControlDialog valueOfSimple() {
        PlotControlDialog dialog = new PlotControlDialog();
        dialog.getJTabbedPane1().remove(0);
        return dialog;
    }

    /**
     * This method initializes jDisplayZCheck
     * 
     * @return javax.swing.JCheckBox
     */
    private javax.swing.JCheckBox getJDisplayZCheck() {
        if (jDisplayZCheck == null) {
            jDisplayZCheck = new javax.swing.JCheckBox();
            jDisplayZCheck.setText("Show z-label");
            jDisplayZCheck.setSelected(true);
        }
        return jDisplayZCheck;
    }
}