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

/**
 * This is a typesave enum for the different model types.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig</a>
 */
public final class ModelTypes {
    final String name;

    public static final ModelTypes ARIMA = new ModelTypes("ARIMA");

    /**
     * VAR model type.
     */
    public static final ModelTypes VAR = new ModelTypes("VAR");

    /**
     * SVAR model type within VAR context.
     */
    public static final ModelTypes SVAR = new ModelTypes("SVAR");

    /**
     * VECM model type within a cointegrated framework.
     */
    public static final ModelTypes VECM = new ModelTypes("VECM");

    /**
     * SVAR model type within VEC context.
     */
    public static final ModelTypes SVEC = new ModelTypes("SVEC");

    /**
     * MGARCH model type.
     */
    public static final ModelTypes MGARCH = new ModelTypes("MGARCH");

    /**
     * ARCH model type.
     */
    public static final ModelTypes ARCH = new ModelTypes("ARCH");

    /**
     * GARCH model type.
     */
    public static final ModelTypes GARCH = new ModelTypes("GARCH");

    /**
     * TGARCH model type.
     */
    public static final ModelTypes TGARCH = new ModelTypes("TGARCH");

    protected ModelTypes(String nam) {
        name = nam;
    }

    public String toString() {
        return name;
    }
}