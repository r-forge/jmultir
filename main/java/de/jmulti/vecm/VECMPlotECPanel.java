package de.jmulti.vecm;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import com.jstatcom.component.ResultField;
import com.jstatcom.engine.PCall;
import com.jstatcom.engine.PCallAdapter;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.JSCSArray;
import com.jstatcom.model.ModelPanel;
import com.jstatcom.ts.TSDate;

import de.jmulti.proc.ComputeECTermCall;
import de.jmulti.proc.PlotTSCall;

/**
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
public final class VECMPlotECPanel extends ModelPanel {

    private JPanel jPanel = null;

    private ResultField resultField = null;

    private JButton okButton = null;

    private JRadioButton ec1CheckBox = null;

    private JRadioButton ec2CheckBox1 = null;

    private JRadioButton ec3CheckBox = null;

    private JRadioButton ec4CheckBox2 = null;

    private JCheckBox graphicsCheckBox = null;

    private JCheckBox multGraphCheckBox = null;

    /**
     * This method initializes
     *  
     */
    public VECMPlotECPanel() {
        super();
        initialize();
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
        this.setSize(580, 335);
        setName("PlotECPanel");
        TitledBorder title = new TitledBorder(new BevelBorder(
                BevelBorder.LOWERED), "Plot EC Term", TitledBorder.RIGHT,
                TitledBorder.TOP);
        this.setLayout(new BorderLayout());
        this.setBorder(title);

        this.add(getJPanel(), java.awt.BorderLayout.NORTH);
        this.add(getResultField(), java.awt.BorderLayout.CENTER);
        ButtonGroup ec = new ButtonGroup();
        ec.add(getEc1CheckBox());
        ec.add(getEc2CheckBox());
        ec.add(getEc3CheckBox());
        ec.add(getEc4CheckBox());
    }

    /**
     * This method initializes jPanel
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJPanel() {
        if (jPanel == null) {
            GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
            GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
            GridBagConstraints gridBagConstraints31 = new GridBagConstraints();
            GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
            GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
            GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
            GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
            GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
            jPanel = new JPanel();
            jPanel.setLayout(new GridBagLayout());
            jPanel.setPreferredSize(new java.awt.Dimension(10, 150));
            gridBagConstraints1.gridx = 4;
            gridBagConstraints1.gridy = 4;
            gridBagConstraints1.insets = new java.awt.Insets(0, 0, 10, 20);
            gridBagConstraints1.weightx = 0.0D;
            gridBagConstraints1.weighty = 1.0D;
            gridBagConstraints1.anchor = java.awt.GridBagConstraints.SOUTHEAST;
            gridBagConstraints2.gridx = 0;
            gridBagConstraints2.gridy = 1;
            gridBagConstraints2.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints2.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints2.insets = new java.awt.Insets(5, 10, 0, 0);
            gridBagConstraints2.gridwidth = 2;
            gridBagConstraints2.weightx = 0.0D;
            gridBagConstraints3.gridx = 0;
            gridBagConstraints3.gridy = 2;
            gridBagConstraints3.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints3.insets = new java.awt.Insets(5, 10, 0, 0);
            gridBagConstraints3.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints3.gridwidth = 2;
            gridBagConstraints4.gridx = 0;
            gridBagConstraints4.gridy = 3;
            gridBagConstraints4.insets = new java.awt.Insets(5, 10, 0, 0);
            gridBagConstraints4.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints4.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints4.gridwidth = 2;
            gridBagConstraints5.gridx = 0;
            gridBagConstraints5.gridy = 4;
            gridBagConstraints5.insets = new java.awt.Insets(5, 10, 0, 0);
            gridBagConstraints5.anchor = java.awt.GridBagConstraints.NORTHWEST;
            gridBagConstraints5.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints5.weightx = 0.0D;
            gridBagConstraints11.gridx = 3;
            gridBagConstraints11.gridy = 1;
            gridBagConstraints11.gridwidth = 2;
            gridBagConstraints11.insets = new java.awt.Insets(0, 20, 0, 0);
            gridBagConstraints11.weightx = 1.0D;
            gridBagConstraints11.anchor = java.awt.GridBagConstraints.CENTER;
            gridBagConstraints11.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints21.gridx = 4;
            gridBagConstraints21.gridy = 2;
            gridBagConstraints21.anchor = java.awt.GridBagConstraints.WEST;
            gridBagConstraints21.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints21.gridwidth = 2;
            gridBagConstraints21.insets = new java.awt.Insets(5, 20, 0, 0);
            gridBagConstraints31.gridx = 0;
            gridBagConstraints31.gridy = 0;
            gridBagConstraints31.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints31.insets = new java.awt.Insets(0, 10, 0, 0);
            gridBagConstraints31.gridwidth = 5;
            jPanel.add(getOkButton(), gridBagConstraints1);
            jPanel.add(getEc1CheckBox(), gridBagConstraints2);
            jPanel.add(getEc3CheckBox(), gridBagConstraints4);
            jPanel.add(getEc4CheckBox(), gridBagConstraints5);
            jPanel.add(getEc2CheckBox(), gridBagConstraints3);
            jPanel.add(getGraphicsCheckBox(), gridBagConstraints11);
            jPanel.add(getMultGraphCheckBox(), gridBagConstraints21);
        }
        return jPanel;
    }

    /**
     * This method initializes resultField
     * 
     * @return com.jstatcom.component.ResultField
     */
    private ResultField getResultField() {
        if (resultField == null) {
            resultField = new ResultField();
        }
        return resultField;
    }

    /**
     * This method initializes jButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getOkButton() {
        if (okButton == null) {
            okButton = new JButton();
            okButton.setText("Execute");
            okButton.setPreferredSize(new java.awt.Dimension(120, 26));

            // Invokes the call.
            okButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    int sel = getEc1CheckBox().isSelected() ? 0 : 0;
                    sel = getEc2CheckBox().isSelected() ? 1 : sel;
                    sel = getEc3CheckBox().isSelected() ? 2 : sel;
                    sel = getEc4CheckBox().isSelected() ? 3 : sel;
                    final int selRepIndex = sel;
                    getResultField().clear();
                    PCall job = new ComputeECTermCall(global(), selRepIndex);
                    job.setSymbolTable(local());
                    job.setOutHolder(getResultField());
                    if (getGraphicsCheckBox().isSelected())
                        job.addPCallListener(new PCallAdapter() {
                            public void success() {
                                TSDate start = global().get(
                                        VECMConstants.T1_raw_Def)
                                        .getJSCDRange().getTSDateRange()
                                        .lowerBound();
                                JSCNArray data = local().get(
                                        ComputeECTermCall.EC_TERM)
                                        .getJSCNArray();
                                    
                                int[] ind = new int[data.cols()];
                                int rank = ind.length / 4;
                                
                                for (int i = selRepIndex * rank; i < (selRepIndex + 1)
                                        * rank; i++)
                                    ind[i] = 1;

                                data = new JSCNArray("data", data
                                        .selColsIf(ind));
                                // assemble names for plot
                                String[] nam = new String[rank];
                                for (int i = 0; i < nam.length; i++)
                                    nam[i] = "eq(" + (i + 1) + ")";

                                JSCSArray names = new JSCSArray("names", nam);
                                PCall job = new PlotTSCall(data, start, names,
                                        getMultGraphCheckBox().isSelected(),
                                        false);
                                job.execute();
                            }
                        });

                    job.execute();
                }
            });
        }
        return okButton;
    }

    /**
     * This method initializes jCheckBox
     * 
     * @return javax.swing.JCheckBox
     */
    private JRadioButton getEc1CheckBox() {
        if (ec1CheckBox == null) {
            ec1CheckBox = new JRadioButton();
            ec1CheckBox.setText("beta*Y(t-1)");
            ec1CheckBox.setSelected(true);
        }
        return ec1CheckBox;
    }

    /**
     * This method initializes jCheckBox1
     * 
     * @return javax.swing.JCheckBox
     */
    private JRadioButton getEc2CheckBox() {
        if (ec2CheckBox1 == null) {
            ec2CheckBox1 = new JRadioButton();
            ec2CheckBox1.setSelected(false);
            ec2CheckBox1.setText("beta*Y(t-1)+beta_d*D(t-1)");
        }
        return ec2CheckBox1;
    }

    /**
     * This method initializes jCheckBox
     * 
     * @return javax.swing.JCheckBox
     */
    private JRadioButton getEc3CheckBox() {
        if (ec3CheckBox == null) {
            ec3CheckBox = new JRadioButton();
            ec3CheckBox.setSelected(false);
            ec3CheckBox.setText("beta*Y(t-1)*M");
        }
        return ec3CheckBox;
    }

    /**
     * This method initializes jCheckBox2
     * 
     * @return javax.swing.JCheckBox
     */
    private JRadioButton getEc4CheckBox() {
        if (ec4CheckBox2 == null) {
            ec4CheckBox2 = new JRadioButton();
            ec4CheckBox2.setSelected(false);
            ec4CheckBox2.setText("beta*Y(t-1)*M+beta_d*D(t-1)*M");
        }
        return ec4CheckBox2;
    }

    /**
     * This method initializes jCheckBox
     * 
     * @return javax.swing.JCheckBox
     */
    private JCheckBox getGraphicsCheckBox() {
        if (graphicsCheckBox == null) {
            graphicsCheckBox = new JCheckBox();
            graphicsCheckBox.setPreferredSize(new java.awt.Dimension(200, 24));
            graphicsCheckBox.setText("Display Graphics");
            graphicsCheckBox.setSelected(true);
            graphicsCheckBox.addItemListener(new ItemListener() {
                public void itemStateChanged(java.awt.event.ItemEvent e) {
                    getMultGraphCheckBox().setEnabled(
                            getGraphicsCheckBox().isSelected());
                }
            });
        }
        return graphicsCheckBox;
    }

    /**
     * This method initializes jCheckBox1
     * 
     * @return javax.swing.JCheckBox
     */
    private JCheckBox getMultGraphCheckBox() {
        if (multGraphCheckBox == null) {
            multGraphCheckBox = new JCheckBox();
            multGraphCheckBox.setPreferredSize(new java.awt.Dimension(21, 24));
            multGraphCheckBox.setText("One diagram for each graph");
            multGraphCheckBox.setSelected(true);
        }
        return multGraphCheckBox;
    }
} //  @jve:decl-index=0:visual-constraint="10,10"
