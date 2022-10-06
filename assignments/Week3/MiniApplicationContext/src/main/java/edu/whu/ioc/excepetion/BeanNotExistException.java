package edu.whu.ioc.excepetion;

public class BeanNotExistException extends ContextException {

    public BeanNotExistException() {}

    public BeanNotExistException(String message) {
        super(message);
    }

    public BeanNotExistException(String message, Exception e) {
        super(message, e);
    }

}
