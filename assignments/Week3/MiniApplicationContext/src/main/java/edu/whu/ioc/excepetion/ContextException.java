package edu.whu.ioc.excepetion;

public class ContextException extends RuntimeException {

    public ContextException() {
    }

    public ContextException(Exception e) {
        super(e);
    }

    public ContextException(String message) {
        super(message);
    }

    public ContextException(String message, Exception e) {
        super(message, e);
    }

}
