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

package de.jmulti.arch;

import javolution.xml.XmlElement;
import javolution.xml.XmlFormat;

import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.SymbolTable;
import com.jstatcom.project.ProjectState;
import com.jstatcom.ts.Selection;

/**
 * Represents the persistent state for the ARCH module.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
public final class ARCHProjectState implements ProjectState {
    private SymbolTable symbolTableGlobal = null;

    private SymbolTable symbolTableUni = null;

    private SymbolTable symbolTableMult = null;

    private Selection selectionMult = null;

    private Selection selectionUni = null;

    private JSCNArray uniModelSel = null;

    /**
     * XML format field for (de)serialization.
     */
    public static final XmlFormat<ARCHProjectState> ARCHProjectState_XML = new XmlFormat<ARCHProjectState>(
            ARCHProjectState.class) {
        public void format(ARCHProjectState data, XmlElement xml) {
            xml.add(data.symbolTableGlobal);
            xml.add(data.symbolTableUni);
            xml.add(data.symbolTableMult);
            xml.add(data.selectionUni);
            xml.add(data.selectionMult);
            xml.add(data.uniModelSel);
        }

        public ARCHProjectState parse(XmlElement xml) {
            ARCHProjectState state = new ARCHProjectState();
            if (xml.hasNext()) {
                SymbolTable table = (SymbolTable) xml.getNext();
                if (table != null)
                    state.setSymbolTableGlobal(table);
            }
            if (xml.hasNext()) {
                SymbolTable table = (SymbolTable) xml.getNext();
                if (table != null)
                    state.setSymbolTableUni(table);
            }
            if (xml.hasNext()) {
                SymbolTable table = (SymbolTable) xml.getNext();
                if (table != null)
                    state.setSymbolTableMult(table);
            }
            if (xml.hasNext()) {
                Selection sel = (Selection) xml.getNext();
                if (sel != null)
                    state.setSelectionUni(sel);
            }
            if (xml.hasNext()) {
                Selection sel = (Selection) xml.getNext();
                if (sel != null)
                    state.setSelectionMult(sel);
            }
            if (xml.hasNext()) {
                JSCNArray sel = (JSCNArray) xml.getNext();
                if (sel != null)
                    state.setUniModelSel(sel);
            }

            return state;
        }
    };

    /*
     * (non-Javadoc)
     * 
     * @see com.jstatcom.project.ProjectState#getHandlerID()
     */
    public String getHandlerID() {
        return ARCHAnalysisFrame.class.getName();
    }

    /**
     * @return
     */
    public SymbolTable getSymbolTableGlobal() {
        return symbolTableGlobal;
    }

    /**
     * @param symbolTableGlobal
     */
    public void setSymbolTableGlobal(SymbolTable symbolTableGlobal) {
        if (symbolTableGlobal == null)
            throw new IllegalArgumentException("Argument was null.");

        this.symbolTableGlobal = symbolTableGlobal;
    }

    /**
     * @return
     */
    public SymbolTable getSymbolTableMult() {
        return symbolTableMult;
    }

    /**
     * @param symbolTableMult
     */
    public void setSymbolTableMult(SymbolTable symbolTableMult) {
        if (symbolTableMult == null)
            throw new IllegalArgumentException("Argument was null.");

        this.symbolTableMult = symbolTableMult;
    }

    /**
     * @return
     */
    public SymbolTable getSymbolTableUni() {
        return symbolTableUni;
    }

    /**
     * @param symbolTableUni
     */
    public void setSymbolTableUni(SymbolTable symbolTableUni) {
        if (symbolTableUni == null)
            throw new IllegalArgumentException("Argument was null.");

        this.symbolTableUni = symbolTableUni;
    }

    /**
     * @return
     */
    public Selection getSelectionMult() {
        return selectionMult;
    }

    /**
     * @param selectionMult
     */
    public void setSelectionMult(Selection selectionMult) {
        if (selectionMult == null)
            throw new IllegalArgumentException("Argument was null.");

        this.selectionMult = selectionMult;
    }

    /**
     * @return
     */
    public Selection getSelectionUni() {
        return selectionUni;
    }

    /**
     * @param selectionUni
     */
    public void setSelectionUni(Selection selectionUni) {
        if (selectionUni == null)
            throw new IllegalArgumentException("Argument was null.");

        this.selectionUni = selectionUni;
    }

    /**
     * @return
     */
    public JSCNArray getUniModelSel() {
        return uniModelSel;
    }

    /**
     * @param uniModelSel
     */
    public void setUniModelSel(JSCNArray uniModelSel) {
        if (uniModelSel == null)
            throw new IllegalArgumentException("Argument was null.");

        this.uniModelSel = uniModelSel;
    }
}
