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

package de.jmulti.proc;

import com.jstatcom.engine.Engine;
import com.jstatcom.engine.EngineTypes;
import com.jstatcom.engine.PCall;

import de.jmulti.tools.PlotControlModel;

/**
 * Helper class that implements engine for all Gauss calls in JMulTi. engine can
 * easily be change to switch between <code>GRTE</code> and
 * <code>GaussEngine</code> mode.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
public abstract class RPCall extends PCall {

    /*
     * @see com.jstatcom.engine.PCall#engine()
     */
    public Engine engine() {
        return EngineTypes.RSTAT.getEngine();
    }

    /**
     * Sets the global symbols declared in jmplot.dec.
     */
    protected void setGlobalPgraphSettings() {
        setGlobalPgraphSettings(PlotControlModel.SHARED_PLOT_MODEL);
    }

    /**
     * Sets the global symbols declared in jmplot.dec.
     */
    protected void setGlobalPgraphSettings(PlotControlModel plotControlModel) {
        // TODO use par command
        /*
         * JSCData[] args = plotControlModel.getDataArray();
         * engine().load("__jm_legend_default", loadType, new JSCData[] {
         * args[0] }); engine().load("__jm_is_axnum", loadType, new JSCData[] {
         * args[1] }); engine().load("__jm_is_xlabel", loadType, new JSCData[] {
         * args[2] }); engine().load("__jm_is_zlabel", loadType, new JSCData[] {
         * args[3] }); engine().load("__jm_is_ylabel", loadType, new JSCData[] {
         * args[4] }); engine().load("__jm_height_axis", loadType, new JSCData[] {
         * args[5] }); engine().load("__jm_height_numbers", loadType, new
         * JSCData[] { args[6] }); engine() .load("__jm_height_legend",
         * loadType, new JSCData[] { args[7] });
         * engine().load("__jm_height_plot_title", loadType, new JSCData[] {
         * args[8] }); engine().load("__jm_height_main_title", loadType, new
         * JSCData[] { args[9] }); engine().load("__jm_show_legend", loadType,
         * new JSCData[] { args[10] }); engine().load("__jm_show_main_title",
         * loadType, new JSCData[] { args[11] });
         * engine().load("__jm_show_plot_title", loadType, new JSCData[] {
         * args[12] }); engine().load("__jm_show_date", loadType, new JSCData[] {
         * args[13] }); engine().load("__jm_legend_x", loadType, new JSCData[] {
         * args[14] }); engine().load("__jm_legend_y", loadType, new JSCData[] {
         * args[15] }); engine().load("__jm_line_type", loadType, new JSCData[] {
         * args[16] }); engine().load("__jm_line_color", loadType, new JSCData[] {
         * args[17] }); engine().load("__jm_line_width", loadType, new JSCData[] {
         * args[18] }); engine().load("__jm_legend_strings", loadType, new
         * JSCData[] { args[19] });
         */
    }
}