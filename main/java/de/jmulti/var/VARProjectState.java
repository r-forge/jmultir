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

package de.jmulti.var;

import javolution37.javolution.xml.XmlElement;
import javolution37.javolution.xml.XmlFormat;

import com.jstatcom.model.JSCNArray;
import com.jstatcom.model.SymbolTable;
import com.jstatcom.project.ProjectState;
import com.jstatcom.ts.Selection;

/**
 * Represents a VAR state. Each state instance holds a symbol table with all
 * global symbols of a VAR frame, together with the current selection of the
 * <code>TSSel</code> component.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
public final class VARProjectState implements ProjectState {

	private SymbolTable symbolTableGlobal = null;

	private SymbolTable symbolTableSVAR = null;

	private Selection selection = null;

	private JSCNArray detSelection = null;

	/**
	 * XML format field for (de)serialization.
	 */
	public static final XmlFormat<VARProjectState> VARProjectState_XML = new XmlFormat<VARProjectState>(
			VARProjectState.class) {
		public void format(VARProjectState data, XmlElement xml) {
			xml.add(data.symbolTableGlobal);
			xml.add(data.selection);
			xml.add(data.detSelection);
			xml.add(data.symbolTableSVAR);
		}

		public VARProjectState parse(XmlElement xml) {
			VARProjectState state = new VARProjectState();
			if (xml.hasNext()) {
				SymbolTable table = (SymbolTable) xml.getNext();
				if (table != null)
					state.setSymbolTableGlobal(table);
			}
			if (xml.hasNext()) {
				Selection sel = (Selection) xml.getNext();
				if (sel != null)
					state.setSelection(sel);
			}
			if (xml.hasNext()) {
				JSCNArray detSel = (JSCNArray) xml.getNext();
				if (detSel != null)
					state.setDetSelection(detSel);
			}
			if (xml.hasNext()) {
				SymbolTable svarTable = (SymbolTable) xml.getNext();
				if (svarTable != null)
					state.setSymbolTableSVAR(svarTable);
			}
			return state;
		}
	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jstatcom.project.ProjectState#getHandlerClassName()
	 */
	public String getHandlerID() {
		return VAR.class.getName();
	}

	/**
	 * Gets the global symbol table stored in this state.
	 * 
	 * @return symbol table with global symbols
	 */
	public SymbolTable getSymbolTableGlobal() {
		return symbolTableGlobal;
	}

	/**
	 * Sets the global symbol table to be stored in this state. The argument is
	 * not copied but just the reference is used for performance reasons.
	 * 
	 * @param symbolTableGlobal
	 *            reference to the current global table of the VAR module
	 * @throws IllegalArgumentException
	 *             <code>if (symbolTableGlobal == null)</code>
	 */
	public void setSymbolTableGlobal(SymbolTable symbolTableGlobal) {
		if (symbolTableGlobal == null)
			throw new IllegalArgumentException("Argument was null.");
		this.symbolTableGlobal = symbolTableGlobal;
	}

	/**
	 * Gets the selection instance that represents the selection state of the
	 * underlying <code>TSSel</code> component.
	 * 
	 * @return the selection state for a <code>TSSel</code> object
	 */
	public Selection getSelection() {
		return selection;
	}

	/**
	 * Sets the selection instance to be stored in this state object.
	 * 
	 * @param selection
	 *            <code>TSSel</code> selection state
	 * @throws IllegalArgumentException
	 *             <code>if (selection == null)</code>
	 */
	public void setSelection(Selection selection) {
		if (selection == null)
			throw new IllegalArgumentException("Argument was null.");
		this.selection = selection;
	}

	/**
	 * Gets the 3x1 array indicating the selection of: constant-trend-seasonal
	 * dummies.
	 * 
	 * @return selection array
	 */
	public JSCNArray getDetSelection() {
		return detSelection;
	}

	/**
	 * Sets the 3x1 array indicating the selection of: constant-trend-seasonal
	 * dummies.
	 * 
	 * @param detSelection
	 *            selection array
	 * @throws IllegalArgumentException
	 *             <code>if (detSelection == null)</code>
	 */
	public void setDetSelection(JSCNArray detSelection) {
		if (detSelection == null)
			throw new IllegalArgumentException("Argument was null.");
		this.detSelection = detSelection;
	}

	/**
	 * Gets the SVAR symbol table stored in this state.
	 * 
	 * @return symbol table with SVAR symbols
	 */
	public SymbolTable getSymbolTableSVAR() {
		return symbolTableSVAR;
	}

	/**
	 * Sets the SVAR symbol table to be stored in this state. The argument is
	 * not copied but just the reference is used for performance reasons.
	 * 
	 * @param symbolTableSVAR
	 *            reference to the current global table of the VAR module
	 * @throws IllegalArgumentException
	 *             <code>if (symbolTableSVAR == null)</code>
	 */
	public void setSymbolTableSVAR(SymbolTable symbolTableSVAR) {
		if (symbolTableSVAR == null)
			throw new IllegalArgumentException("Argument was null.");
		this.symbolTableSVAR = symbolTableSVAR;
	}
}
