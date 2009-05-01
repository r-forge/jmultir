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

package de.jmulti.str;

import javolution37.javolution.xml.XmlElement;
import javolution37.javolution.xml.XmlFormat;

import com.jstatcom.model.JSCInt;
import com.jstatcom.model.SymbolTable;
import com.jstatcom.project.ProjectState;
import com.jstatcom.ts.Selection;

/**
 * Represents the persistent state for the STR module.
 * 
 * @author <a href="mailto:mk@mk-home.de">Markus Kraetzig </a>
 */
public final class STR_ProjectState implements ProjectState {
	private SymbolTable symbolTableGlobal = null;

	private Selection selection = null;

	private JSCInt isSeas = null;

	/**
	 * XML format field for (de)serialization.
	 */
	public static final XmlFormat<STR_ProjectState> STRProjectState_XML = new XmlFormat<STR_ProjectState>(
			STR_ProjectState.class) {
		public void format(STR_ProjectState data, XmlElement xml) {
			xml.add(data.symbolTableGlobal);
			xml.add(data.selection);
			xml.add(data.isSeas);
		}

		public STR_ProjectState parse(XmlElement xml) {
			STR_ProjectState state = new STR_ProjectState();
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
				JSCInt sel = (JSCInt) xml.getNext();
				if (sel != null)
					state.setIsSeas(sel);
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
		return STR_Frame.class.getName();
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
	public Selection getSelection() {
		return selection;
	}

	/**
	 * @param selection
	 */
	public void setSelection(Selection selectionUni) {
		if (selectionUni == null)
			throw new IllegalArgumentException("Argument was null.");

		this.selection = selectionUni;
	}

	/**
	 * @return
	 */
	public JSCInt getIsSeas() {
		return isSeas;
	}

	/**
	 * @param isSeas
	 */
	public void setIsSeas(JSCInt isSeas) {
		if (isSeas == null)
			throw new IllegalArgumentException("Argument was null.");

		this.isSeas = isSeas;
	}
}
