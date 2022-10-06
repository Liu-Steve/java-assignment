package edu.whu.ioc.excepetion;

public class BeanConstructException extends ContextException{

    public BeanConstructException() {}

    public BeanConstructException(String message) {
        super(message);
    }

    public BeanConstructException(String message, Exception e) {
        super(message, e);
    }

}
