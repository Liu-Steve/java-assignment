package edu.whu.ioc.excepetion;

public class BeanInjectException extends ContextException{

    public BeanInjectException() {}

    public BeanInjectException(String message) {
        super(message);
    }

    public BeanInjectException(String message, Exception e) {
        super(message, e);
    }

}
