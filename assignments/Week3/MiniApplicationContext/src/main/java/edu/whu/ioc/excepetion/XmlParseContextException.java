package edu.whu.ioc.excepetion;

public class XmlParseContextException extends ContextException {

    public XmlParseContextException() {
    }

    public XmlParseContextException(Exception e) {
        super(e);
    }

    public XmlParseContextException(String message) {
        super(message);
    }

}
