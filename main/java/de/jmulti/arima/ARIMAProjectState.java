/*
 * Copyright (C) 2006 Markus Kraetzig
 * Created on 14.07.2006
 */
package de.jmulti.arima;

import javolution.xml.XmlElement;
import javolution.xml.XmlFormat;

import com.jstatcom.model.JSCInt;
import com.jstatcom.model.JSCNArray;
import com.jstatcom.project.ProjectState;
import com.jstatcom.ts.Selection;

public class ARIMAProjectState implements ProjectState {

    private Selection selection = null;

    private JSCNArray detSel = null;

    private JSCInt p = null;

    private JSCInt q = null;

    private JSCInt d = null;

    private JSCInt h_hr = null;

    private JSCInt pqmax_hr = null;

    /**
     * XML format field for (de)serialization.
     */
    public static final XmlFormat<ARIMAProjectState> ARIMAProjectState_XML = new XmlFormat<ARIMAProjectState>(
            ARIMAProjectState.class) {
        public void format(ARIMAProjectState data, XmlElement xml) {
            xml.add(data.selection);
            xml.add(data.detSel);
            xml.add(data.p);
            xml.add(data.q);
            xml.add(data.d);
            xml.add(data.h_hr);
            xml.add(data.pqmax_hr);

        }

        private void readToEnd(XmlElement xml) {
            while (xml.hasNext())
                xml.getNext();
        }

        public ARIMAProjectState parse(XmlElement xml) {
            ARIMAProjectState state = new ARIMAProjectState();
            if (xml.hasNext()) {
                Object o = xml.getNext();
                if (o instanceof Selection) {
                    Selection sel = (Selection) o;
                    state.setSelection(sel);
                } else {
                    readToEnd(xml);
                    return state;
                }

            }
       
            if (xml.hasNext()) {
                Object o = xml.getNext();
                if (o instanceof JSCNArray) {
                    JSCNArray sel = (JSCNArray) o;
                    state.setDetSel(sel);
                } else {
                    readToEnd(xml);
                    return state;
                }

            }
            if (xml.hasNext()) {
                Object o = xml.getNext();
                if (o instanceof JSCInt) {
                    JSCInt sel = (JSCInt) o;
                    state.setP(sel);
                } else {
                    readToEnd(xml);
                    return state;
                }
            }
            if (xml.hasNext()) {
                Object o = xml.getNext();
                if (o instanceof JSCInt) {
                    JSCInt sel = (JSCInt) o;
                    state.setQ(sel);
                } else {
                    readToEnd(xml);
                    return state;
                }
            }
            if (xml.hasNext()) {
                Object o = xml.getNext();
                if (o instanceof JSCInt) {
                    JSCInt sel = (JSCInt) o;
                    state.setD(sel);
                } else {
                    readToEnd(xml);
                    return state;
                }
            }
            if (xml.hasNext()) {
                Object o = xml.getNext();
                if (o instanceof JSCInt) {
                    JSCInt sel = (JSCInt) o;
                    state.setH_hr(sel);
                } else {
                    readToEnd(xml);
                    return state;
                }
            }
            if (xml.hasNext()) {
                Object o = xml.getNext();
                if (o instanceof JSCInt) {
                    JSCInt sel = (JSCInt) o;
                    state.setPqmax_hr(sel);
                } else {
                    readToEnd(xml);
                    return state;
                }
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
        return ARIMAFrame.class.getName();
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
        this.selection = selectionUni;
    }

    public JSCInt getD() {
        return d;
    }

    public void setD(JSCInt d) {
        this.d = d;
    }

    public JSCInt getP() {
        return p;
    }

    public void setP(JSCInt p) {
        this.p = p;
    }

    public JSCInt getQ() {
        return q;
    }

    public void setQ(JSCInt q) {
        this.q = q;
    }

    public JSCInt getH_hr() {
        return h_hr;
    }

    public void setH_hr(JSCInt h_hr) {
        this.h_hr = h_hr;
    }

    public JSCInt getPqmax_hr() {
        return pqmax_hr;
    }

    public void setPqmax_hr(JSCInt pqmax_hr) {
        this.pqmax_hr = pqmax_hr;
    }

    /**
     * @return detSel.
     */
    public JSCNArray getDetSel() {
        return detSel;
    }

    /**
     * @param detSel
     *            detSel
     */
    public void setDetSel(JSCNArray detSel) {
        this.detSel = detSel;
    }

}
