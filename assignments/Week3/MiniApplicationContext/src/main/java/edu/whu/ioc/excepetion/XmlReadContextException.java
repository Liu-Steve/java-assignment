package edu.whu.ioc.excepetion;

public class XmlReadContextException extends ContextException {

    public XmlReadContextException() {
    }

    public XmlReadContextException(Exception e) {
        super(e);
    }

    public XmlReadContextException(String message) {
        super(message);
    }

    public XmlReadContextException(String s, Exception e) {
        super(s, e);
    }
}
