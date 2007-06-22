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

package de.jmulti.cafpe;

import java.awt.CardLayout;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.apache.log4j.Logger;

import com.jstatcom.component.AutoEnableMenu;
import com.jstatcom.model.JSCDRange;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.JSCSArray;
import com.jstatcom.model.JSCString;
import com.jstatcom.model.ModelFrame;
import com.jstatcom.model.SymbolEvent;
import com.jstatcom.model.SymbolEventTypes;
import com.jstatcom.model.SymbolListener;
import com.jstatcom.model.SymbolTable;
import com.jstatcom.project.ProjectState;
import com.jstatcom.ts.Selection;
import com.jstatcom.ts.TSDateRange;


/**
 * The main frame for the nonparametric analysis with CAFPE.
 * <p>
 * Implementation note: <br>
 * All data listeners are initialized in <code>initialize</code>.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */

public final class CAFPE_NonParamFrame extends ModelFrame {
    private static final Logger log = Logger
            .getLogger(CAFPE_NonParamFrame.class);

    private CardLayout cardLayout = null;

    private JPanel ivjInternalFrameContentPane = null;

    private JMenuBar ivjNonParamJMenuBar = null;

    private JMenuItem ivjSelectModelItem = null;

    IvjEventHandler ivjEventHandler = new IvjEventHandler();

    private CAFPE_est ivjCAFPE_est = null;

    private CAFPE_sel ivjCAFPE_sel = null;

    private JMenuItem ivjResAnalItem = null;

    private CAFPE_res ivjCAFPE_res = null;

    private AutoEnableMenu ivjEstimation = null;

    private AutoEnableMenu ivjModelCheck = null;

    private AutoEnableMenu ivjSelect = null;

    private AutoEnableMenu ivjForecasting = null;

    private JMenuItem ivjForecastItem = null;

    private AutoEnableMenu ivjVolatility = null;

    private JMenuItem ivjLagSelVolatItem = null;

    private JMenuItem ivjEstVolatItem = null;

    private JMenuItem ivjResVolatItem = null;

    private CAFPE_forecast ivjCAFPE_forecast = null;

    private CAFPE_volatest ivjCAFPE_volatest = null;

    private CAFPE_volatsel ivjCAFPE_volatsel = null;

    private CAFPE_volatres ivjCAFPE_volatres = null;

    private JMenuItem ivjEstimateItem = null;

    class IvjEventHandler implements java.awt.event.ActionListener,
            java.awt.event.ComponentListener {
        public void actionPerformed(java.awt.event.ActionEvent e) {
            if (e.getSource() == CAFPE_NonParamFrame.this.getSelectModelItem())
                connEtoC1();
            if (e.getSource() == CAFPE_NonParamFrame.this.getEstimateItem())
                connEtoC2();
            if (e.getSource() == CAFPE_NonParamFrame.this.getResAnalItem())
                connEtoC3();
            if (e.getSource() == CAFPE_NonParamFrame.this.getResVolatItem())
                connEtoC7();
            if (e.getSource() == CAFPE_NonParamFrame.this.getLagSelVolatItem())
                connEtoC5();
            if (e.getSource() == CAFPE_NonParamFrame.this.getEstVolatItem())
                connEtoC6();
            if (e.getSource() == CAFPE_NonParamFrame.this.getForecastItem())
                connEtoC4();
        };

        public void componentHidden(java.awt.event.ComponentEvent e) {
            if (e.getSource() == CAFPE_NonParamFrame.this.getCAFPE_volatsel())
                connPtoP1SetTarget();
        };

        public void componentMoved(java.awt.event.ComponentEvent e) {
        };

        public void componentResized(java.awt.event.ComponentEvent e) {
        };

        public void componentShown(java.awt.event.ComponentEvent e) {
        };
    };

    /**
     * NonParam constructor comment.
     */
    public CAFPE_NonParamFrame() {
        super();
        initialize();
    }

    /**
     * connEtoC1: (SelectModelItem.action. -->
     * NonParam.selectModelItem_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC1() {
        try {
            // user code begin {1}
            // user code end
            this.selectModelItem_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC2: (EstimateItem.action. -->
     * NonParam.estimateItem_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC2() {
        try {
            // user code begin {1}
            // user code end
            this.estimateItem_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC3: (ResAnalItem.action. --> NonParam.resAnalItem_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC3() {
        try {
            // user code begin {1}
            // user code end
            this.resAnalItem_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC4: (ForecastItem.action. -->
     * NonParam.forecastItem_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC4() {
        try {
            // user code begin {1}
            // user code end
            this.forecastItem_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC5: (VolatItem.action. --> NonParam.volatItem_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC5() {
        try {
            // user code begin {1}
            // user code end
            this.lagsSelVolatItem_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC6: (EstVolatItem.action. -->
     * NonParam.estVolatItem_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC6() {
        try {
            // user code begin {1}
            // user code end
            this.estVolatItem_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connEtoC7: (ResVolatItem.action. -->
     * NonParam.resVolatItem_ActionEvents()V)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connEtoC7() {
        try {
            // user code begin {1}
            // user code end
            this.resVolatItem_ActionEvents();
            // user code begin {2}
            // user code end
        } catch (Throwable ivjExc) {
            // user code begin {3}
            // user code end
            handleException(ivjExc);
        }
    }

    /**
     * connPtoP1SetTarget: (CAFPE_volatsel.whiteNoiseSelected <-->
     * CAFPE_volatest.wnCheckSelected)
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private void connPtoP1SetTarget() {
        /* Set the target from the source */
        try {
            getCAFPE_volatest().setWnCheckSelected(
                    getCAFPE_volatsel().getWhiteNoiseSelected());
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
    private void estimateItem_ActionEvents() {
        getCardLayout().show(getInternalFrameContentPane(),
                getCAFPE_est().getName());

        return;
    }

    /**
     * Comment
     */
    private void estVolatItem_ActionEvents() {
        getCardLayout().show(getInternalFrameContentPane(),
                getCAFPE_volatest().getName());

        return;
    }

    /**
     * Comment
     */
    private void forecastItem_ActionEvents() {
        getCardLayout().show(getInternalFrameContentPane(),
                getCAFPE_forecast().getName());

        return;
    }

    /**
     * Return the EstimateCAFPE property value.
     * 
     * @return program.frame.nonparam.CAFPE_est
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private CAFPE_est getCAFPE_est() {
        if (ivjCAFPE_est == null) {
            try {
                ivjCAFPE_est = new de.jmulti.cafpe.CAFPE_est();
                ivjCAFPE_est.setName("CAFPE_est");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjCAFPE_est;
    }

    /**
     * Return the CAFPE_forecast property value.
     * 
     * @return program.frame.nonparam.CAFPE_forecast
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private CAFPE_forecast getCAFPE_forecast() {
        if (ivjCAFPE_forecast == null) {
            try {
                ivjCAFPE_forecast = new de.jmulti.cafpe.CAFPE_forecast();
                ivjCAFPE_forecast.setName("CAFPE_forecast");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjCAFPE_forecast;
    }

    /**
     * Return the CAFPE_res property value.
     * 
     * @return program.frame.nonparam.CAFPE_res
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private CAFPE_res getCAFPE_res() {
        if (ivjCAFPE_res == null) {
            try {
                ivjCAFPE_res = new de.jmulti.cafpe.CAFPE_res();
                ivjCAFPE_res.setName("CAFPE_res");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjCAFPE_res;
    }

    /**
     * Return the CAFPE property value.
     * 
     * @return program.frame.nonparam.CAFPE_sel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private CAFPE_sel getCAFPE_sel() {
        if (ivjCAFPE_sel == null) {
            try {
                ivjCAFPE_sel = new de.jmulti.cafpe.CAFPE_sel();
                ivjCAFPE_sel.setName("CAFPE_sel");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjCAFPE_sel;
    }

    /**
     * Return the CAFPE_volatest property value.
     * 
     * @return program.frame.nonparam.CAFPE_volatest
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private CAFPE_volatest getCAFPE_volatest() {
        if (ivjCAFPE_volatest == null) {
            try {
                ivjCAFPE_volatest = new de.jmulti.cafpe.CAFPE_volatest();
                ivjCAFPE_volatest.setName("CAFPE_volatest");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjCAFPE_volatest;
    }

    /**
     * Return the CAFPE_volatres property value.
     * 
     * @return program.frame.nonparam.CAFPE_volatres
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private CAFPE_volatres getCAFPE_volatres() {
        if (ivjCAFPE_volatres == null) {
            try {
                ivjCAFPE_volatres = new de.jmulti.cafpe.CAFPE_volatres();
                ivjCAFPE_volatres.setName("CAFPE_volatres");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjCAFPE_volatres;
    }

    /**
     * Return the CAFPE_volatsel property value.
     * 
     * @return program.frame.nonparam.CAFPE_volatsel
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private CAFPE_volatsel getCAFPE_volatsel() {
        if (ivjCAFPE_volatsel == null) {
            try {
                ivjCAFPE_volatsel = new de.jmulti.cafpe.CAFPE_volatsel();
                ivjCAFPE_volatsel.setName("CAFPE_volatsel");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjCAFPE_volatsel;
    }

    /**
     * description
     * 
     * @return java.awt.CardLayout
     */
    private java.awt.CardLayout getCardLayout() {
        if (cardLayout == null)
            cardLayout = (CardLayout) getInternalFrameContentPane().getLayout();
        return cardLayout;
    }

    /**
     * Return the EstimateItem property value.
     * 
     * @return javax.swing.JMenuItem
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JMenuItem getEstimateItem() {
        if (ivjEstimateItem == null) {
            try {
                ivjEstimateItem = new javax.swing.JMenuItem();
                ivjEstimateItem.setName("EstimateItem");
                ivjEstimateItem.setText("Estimate Model");
                ivjEstimateItem.setEnabled(false);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjEstimateItem;
    }

    /**
     * Return the Estimation property value.
     * 
     * @return com.jstatcom.component.AutoEnableMenu
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private com.jstatcom.component.AutoEnableMenu getEstimation() {
        if (ivjEstimation == null) {
            try {
                ivjEstimation = new com.jstatcom.component.AutoEnableMenu();
                ivjEstimation.setName("Estimation");
                ivjEstimation.setText("Estimation");
                ivjEstimation.setEnabled(false);
                ivjEstimation.add(getEstimateItem());
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjEstimation;
    }

    /**
     * Return the EstVolatItem property value.
     * 
     * @return javax.swing.JMenuItem
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JMenuItem getEstVolatItem() {
        if (ivjEstVolatItem == null) {
            try {
                ivjEstVolatItem = new javax.swing.JMenuItem();
                ivjEstVolatItem.setName("EstVolatItem");
                ivjEstVolatItem.setText("Estimate Volatility");
                ivjEstVolatItem.setEnabled(false);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjEstVolatItem;
    }

    /**
     * Return the Forecasting property value.
     * 
     * @return com.jstatcom.component.AutoEnableMenu
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private com.jstatcom.component.AutoEnableMenu getForecasting() {
        if (ivjForecasting == null) {
            try {
                ivjForecasting = new com.jstatcom.component.AutoEnableMenu();
                ivjForecasting.setName("Forecasting");
                ivjForecasting.setText("Forecasting");
                ivjForecasting.setEnabled(false);
                ivjForecasting.add(getForecastItem());
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjForecasting;
    }

    /**
     * Return the ForecastItem property value.
     * 
     * @return javax.swing.JMenuItem
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JMenuItem getForecastItem() {
        if (ivjForecastItem == null) {
            try {
                ivjForecastItem = new javax.swing.JMenuItem();
                ivjForecastItem.setName("ForecastItem");
                ivjForecastItem.setText("One-step ahead Forecast");
                ivjForecastItem.setEnabled(false);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjForecastItem;
    }

    private javax.swing.JPanel getInternalFrameContentPane() {
        if (ivjInternalFrameContentPane == null) {
            try {
                ivjInternalFrameContentPane = new javax.swing.JPanel();
                ivjInternalFrameContentPane.setName("InternalFrameContentPane");
                ivjInternalFrameContentPane
                        .setLayout(new java.awt.CardLayout());
                getInternalFrameContentPane().add(getCAFPE_sel(),
                        getCAFPE_sel().getName());
                getInternalFrameContentPane().add(getCAFPE_est(),
                        getCAFPE_est().getName());
                getInternalFrameContentPane().add(getCAFPE_res(),
                        getCAFPE_res().getName());
                getInternalFrameContentPane().add(getCAFPE_volatest(),
                        getCAFPE_volatest().getName());
                getInternalFrameContentPane().add(getCAFPE_volatsel(),
                        getCAFPE_volatsel().getName());
                getInternalFrameContentPane().add(getCAFPE_forecast(),
                        getCAFPE_forecast().getName());
                getInternalFrameContentPane().add(getCAFPE_volatres(),
                        getCAFPE_volatres().getName());
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjInternalFrameContentPane;
    }

    /**
     * Return the VolatItem property value.
     * 
     * @return javax.swing.JMenuItem
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JMenuItem getLagSelVolatItem() {
        if (ivjLagSelVolatItem == null) {
            try {
                ivjLagSelVolatItem = new javax.swing.JMenuItem();
                ivjLagSelVolatItem.setName("LagSelVolatItem");
                ivjLagSelVolatItem.setText("Volatility Lag Selection");
                ivjLagSelVolatItem.setEnabled(false);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjLagSelVolatItem;
    }

    /**
     * Return the ModelCheck property value.
     * 
     * @return com.jstatcom.component.AutoEnableMenu
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private com.jstatcom.component.AutoEnableMenu getModelCheck() {
        if (ivjModelCheck == null) {
            try {
                ivjModelCheck = new com.jstatcom.component.AutoEnableMenu();
                ivjModelCheck.setName("ModelCheck");
                ivjModelCheck.setText("Model Checking");
                ivjModelCheck.setEnabled(false);
                ivjModelCheck.add(getResAnalItem());
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjModelCheck;
    }

    /**
     * Return the NonParamJMenuBar property value.
     * 
     * @return javax.swing.JMenuBar
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JMenuBar getNonParamJMenuBar() {
        if (ivjNonParamJMenuBar == null) {
            try {
                ivjNonParamJMenuBar = new javax.swing.JMenuBar();
                ivjNonParamJMenuBar.setName("NonParamJMenuBar");
                ivjNonParamJMenuBar.add(getSelect());
                ivjNonParamJMenuBar.add(getEstimation());
                ivjNonParamJMenuBar.add(getModelCheck());
                ivjNonParamJMenuBar.add(getVolatility());
                ivjNonParamJMenuBar.add(getForecasting());
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjNonParamJMenuBar;
    }

    /**
     * Return the ResAnalItem property value.
     * 
     * @return javax.swing.JMenuItem
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JMenuItem getResAnalItem() {
        if (ivjResAnalItem == null) {
            try {
                ivjResAnalItem = new javax.swing.JMenuItem();
                ivjResAnalItem.setName("ResAnalItem");
                ivjResAnalItem.setText("Residual Analysis");
                ivjResAnalItem.setEnabled(false);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjResAnalItem;
    }

    /**
     * Return the ResVolatItem property value.
     * 
     * @return javax.swing.JMenuItem
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JMenuItem getResVolatItem() {
        if (ivjResVolatItem == null) {
            try {
                ivjResVolatItem = new javax.swing.JMenuItem();
                ivjResVolatItem.setName("ResVolatItem");
                ivjResVolatItem.setText("Volatility Residual Analysis");
                ivjResVolatItem.setEnabled(false);
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjResVolatItem;
    }

    /**
     * Return the Select property value.
     * 
     * @return com.jstatcom.component.AutoEnableMenu
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private com.jstatcom.component.AutoEnableMenu getSelect() {
        if (ivjSelect == null) {
            try {
                ivjSelect = new com.jstatcom.component.AutoEnableMenu();
                ivjSelect.setName("Select");
                ivjSelect.setText("Select Model");
                ivjSelect.add(getSelectModelItem());
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSelect;
    }

    /**
     * Return the SelectModelItem property value.
     * 
     * @return javax.swing.JMenuItem
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private javax.swing.JMenuItem getSelectModelItem() {
        if (ivjSelectModelItem == null) {
            try {
                ivjSelectModelItem = new javax.swing.JMenuItem();
                ivjSelectModelItem.setName("SelectModelItem");
                ivjSelectModelItem.setText("Select Model");
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjSelectModelItem;
    }

    /**
     * Return the Volatility property value.
     * 
     * @return com.jstatcom.component.AutoEnableMenu
     */
    /* WARNING: THIS METHOD WILL BE REGENERATED. */
    private com.jstatcom.component.AutoEnableMenu getVolatility() {
        if (ivjVolatility == null) {
            try {
                ivjVolatility = new com.jstatcom.component.AutoEnableMenu();
                ivjVolatility.setName("Volatility");
                ivjVolatility.setText("Volatility Analysis");
                ivjVolatility.setEnabled(false);
                ivjVolatility.add(getLagSelVolatItem());
                ivjVolatility.add(getEstVolatItem());
                ivjVolatility.add(getResVolatItem());
                // user code begin {1}
                // user code end
            } catch (Throwable ivjExc) {
                // user code begin {2}
                // user code end
                handleException(ivjExc);
            }
        }
        return ivjVolatility;
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
        getCAFPE_volatsel().addComponentListener(ivjEventHandler);
        getSelectModelItem().addActionListener(ivjEventHandler);
        getEstimateItem().addActionListener(ivjEventHandler);
        getResAnalItem().addActionListener(ivjEventHandler);
        getResVolatItem().addActionListener(ivjEventHandler);
        getLagSelVolatItem().addActionListener(ivjEventHandler);
        getEstVolatItem().addActionListener(ivjEventHandler);
        getForecastItem().addActionListener(ivjEventHandler);
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
            setName("NonParam");
            setJMenuBar(getNonParamJMenuBar());
            setPreferredSize(new java.awt.Dimension(800, 500));
            setSize(720, 500);
            setMinimumSize(new java.awt.Dimension(800, 500));
            setTitle("Nonparametric Time Series (univariate)");
            setContentPane(getInternalFrameContentPane());
            initConnections();
        } catch (Throwable ivjExc) {
            handleException(ivjExc);
        }
        // user code begin {2}
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        // enable Estimate Panel /Forecast Panel, when OPT_LAGS is there
        global().get(CAFPE_constants.OPT_LAGS).addSymbolListener(
                new SymbolListener() {
                    public void valueChanged(SymbolEvent evt) {
                        JSCString selCrit = global().get(
                                CAFPE_constants.SELCRIT).getJSCString();
                        if (!evt.isSourceEmpty() && !selCrit.isEmpty()) {
                            getForecastItem().setEnabled(true);
                            if (selCrit.string().startsWith("lq"))
                                getEstimateItem().setEnabled(true);

                        } else { // empty
                            getEstimateItem().setEnabled(false);
                            getForecastItem().setEnabled(false);
                        }
                    }
                }, SymbolEventTypes.EMPTY_STATE);

        // enable ResAnal, when residuals are there
        global().get(CAFPE_constants.RESID_EST).addSymbolListener(
                new SymbolListener() {
                    public void valueChanged(SymbolEvent evt) {
                        getResAnalItem().setEnabled(!evt.isSourceEmpty());

                        if (!evt.isSourceEmpty()) {
                            TSDateRange range = global().get(
                                    CAFPE_constants.DRANGE).getJSCDRange()
                                    .getTSDateRange();
                            JSCNArray res = evt.getSource().getJSCNArray();
                            TSDateRange estRange = range
                                    .addPeriodsToStart(range.numOfObs()
                                            - res.rows());
                            global().get(CAFPE_constants.T1).setJSCData(
                                    new JSCDRange("estRange", estRange));

                        }
                    }
                }, SymbolEventTypes.EMPTY_STATE);

        // enable Volatitily Estimation, when OPT_LAGS_VOLAT are there
        global().get(CAFPE_constants.OPT_LAGS_VOLAT).addSymbolListener(
                new SymbolListener() {
                    public void valueChanged(SymbolEvent evt) {
                        getEstVolatItem().setEnabled(!evt.isSourceEmpty());
                    }
                }, SymbolEventTypes.EMPTY_STATE);

        // enable ResAnal, when residuals_volat are there
        global().get(CAFPE_constants.RESID_EST_VOLAT).addSymbolListener(
                new SymbolListener() {
                    public void valueChanged(SymbolEvent evt) {
                        getResVolatItem().setEnabled(!evt.isSourceEmpty());

                        if (!evt.isSourceEmpty()) {
                            TSDateRange range = global().get(
                                    CAFPE_constants.DRANGE).getJSCDRange()
                                    .getTSDateRange();
                            JSCNArray res = evt.getSource().getJSCNArray();
                            TSDateRange estRange = range
                                    .addPeriodsToStart(range.numOfObs()
                                            - res.rows());
                            global().get(CAFPE_constants.T1_volat).setJSCData(
                                    new JSCDRange("estRange", estRange));

                        }
                    }
                }, SymbolEventTypes.EMPTY_STATE);

        // enable volatility lag selection when data are there
        global().get(CAFPE_constants.Y).addSymbolListener(new SymbolListener() {
            public void valueChanged(SymbolEvent evt) {
                getLagSelVolatItem().setEnabled(!evt.isSourceEmpty());
            }
        }, SymbolEventTypes.EMPTY_STATE);

        global().get(CAFPE_constants.NAME_RESIDS).setJSCData(
                new JSCSArray("name", "resids"));
        global().get(CAFPE_constants.NAME_RESIDS_VOLAT).setJSCData(
                new JSCSArray("name", "resids_volat"));
        getCAFPE_forecast().setEstLagBox(getCAFPE_est().getIvjGridVector());

        URL url = getClass().getResource("/images/cafpe.gif");
        if (url != null)
            setFrameIcon(new ImageIcon(url));

        // user code end
    }

    /**
     * Comment
     */
    private void lagsSelVolatItem_ActionEvents() {
        getCardLayout().show(getInternalFrameContentPane(),
                getCAFPE_volatsel().getName());
    }

    /**
     * Comment
     */
    private void resAnalItem_ActionEvents() {
        getCardLayout().show(getInternalFrameContentPane(),
                getCAFPE_res().getName());

        return;
    }

    /**
     * Comment
     */
    private void resVolatItem_ActionEvents() {
        getCardLayout().show(getInternalFrameContentPane(),
                getCAFPE_volatres().getName());

        return;
    }

    /**
     * Comment
     */
    private void selectModelItem_ActionEvents() {
        getCardLayout().show(getInternalFrameContentPane(),
                getCAFPE_sel().getName());
        return;
    }

    /**
     * Sets the state of this module frame from a <code>ProjectState</code>
     * instance.
     */
    public void setState(ProjectState state) {
        if (state instanceof CAFPE_ProjectState) {
            CAFPE_ProjectState cafpeState = (CAFPE_ProjectState) state;

            SymbolTable sglobal = cafpeState.getSymbolTableGlobal();
            Selection sel = cafpeState.getSelection();
            JSCNArray mSel = cafpeState.getMSel();
            getCardLayout().show(getInternalFrameContentPane(),
                    getCAFPE_sel().getName());
            getCAFPE_sel().getCAFPESelect().setSelection(sel);
            getCAFPE_sel().setMSel(mSel);
            global().setSymbolTable(sglobal);
            sglobal.clear();

        } else
            throw new IllegalArgumentException(
                    "Argument not a valid project state for this module.");
    }

    /**
     * Gets the state of this module frame as a <code>ProjectState</code>
     * instance.
     */
    public ProjectState getState() {
        CAFPE_ProjectState state = new CAFPE_ProjectState();
        state.setSymbolTableGlobal(global());
        state.setSelection(getCAFPE_sel().getCAFPESelect().getSelection());
        state.setMSel(getCAFPE_sel().getMSel());
        return state;
    }

}