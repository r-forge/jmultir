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

import com.jstatcom.model.JSCData;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.JSCNumber;
import com.jstatcom.model.JSCSArray;
import com.jstatcom.util.UMatrix;

/**
 * Model that represents the global plot settings defined in
 * <code>jmplot.dec</code> for <i>JMulTi </i>. A shared static instance can be
 * used for global plot settings, special versions can be created by
 * subclassing.
 * 
 * @author A. Benkwitz, <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */

public class PlotControlModel {
    /**
     * Shared instance for global settings.
     */
    public static final PlotControlModel SHARED_PLOT_MODEL = new PlotControlModel();

    public final JSCNumber is_legend_default = new JSCNumber(
            "__jm_legend_default");

    public final JSCNArray is_axnum = new JSCNArray("__jm_is_axnum");

    public final JSCNumber is_xlabel = new JSCNumber("__jm_is_xlabel");

    public final JSCNumber is_zlabel = new JSCNumber("__jm_is_zlabel");

    public final JSCNumber is_ylabel = new JSCNumber("__jm_is_ylabel");

    public final JSCNumber height_axis = new JSCNumber("__jm_height_axis");

    public final JSCNumber height_numbers = new JSCNumber("__jm_height_numbers");

    public final JSCNumber height_legend = new JSCNumber("__jm_height_legend");

    public final JSCNumber height_plot_title = new JSCNumber(
            "__jm_height_plot_title");

    public final JSCNumber height_main_title = new JSCNumber(
            "__jm_height_main_title");

    public final JSCNumber show_legend = new JSCNumber("__jm_show_legend");

    public final JSCNumber show_main_title = new JSCNumber(
            "__jm_show_main_title");

    public final JSCNumber show_plot_title = new JSCNumber(
            "__jm_show_plot_title");

    public final JSCNumber show_date = new JSCNumber("__jm_show_date");

    public final JSCNumber legend_x = new JSCNumber("__jm_legend_x");

    public final JSCNumber legend_y = new JSCNumber("__jm_legend_y");

    public final JSCNArray line_type = new JSCNArray("__jm_line_type");

    public final JSCNArray line_color = new JSCNArray("__jm_line_color");

    public final JSCNArray line_width = new JSCNArray("__jm_line_width");

    public final JSCSArray legend_strings = new JSCSArray("__jm_legend_strings");

    private final JSCData[] gdArray = new JSCData[] { is_legend_default,
            is_axnum, is_xlabel, is_zlabel, is_ylabel, height_axis,
            height_numbers, height_legend, height_plot_title,
            height_main_title, show_legend, show_main_title, show_plot_title,
            show_date, legend_x, legend_y, line_type, line_color, line_width,
            legend_strings };

    /**
     * PlotControlModel constructor comment.
     */
    public PlotControlModel() {
        super();
        setDefaultValues();
    }

    /**
     * Gets the array of all data objects containing the plot control settings.
     * 
     * @return a data array that can be written to GAUSS
     */
    public final JSCData[] getDataArray() {
        if (legend_strings.isEmpty())
            legend_strings.setVal(new String[] { "" });

        return gdArray;
    }

    /**
     * Sets all settings back to the defaults. This behaviour may be overwritten
     * by subclasses.
     */
    public void setDefaultValues() {
        height_axis.setVal(0);
        height_legend.setVal(5);
        height_main_title.setVal(0);
        height_numbers.setVal(0);
        height_plot_title.setVal(0);
        legend_x.setVal(0.1);
        legend_y.setVal(0.1);

        line_color.setVal(new double[] { 0 });
        line_type.setVal(new double[] { 0 });
        line_width.setVal(new double[] { 0 });

        show_date.setVal(0);
        show_legend.setVal(1);
        show_main_title.setVal(1);
        show_plot_title.setVal(1);
        is_xlabel.setVal(1);
        is_zlabel.setVal(1);
        is_ylabel.setVal(1);
        is_legend_default.setVal(1);

        //legend_strings.setVal(new String[][] { { "" }
        //});
        is_axnum.setVal(UMatrix.ones(2, 1));

    }
}
