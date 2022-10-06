package edu.whu.ioc.context;

import edu.whu.ioc.excepetion.BeanConstructException;
import edu.whu.ioc.excepetion.BeanInjectException;
import edu.whu.ioc.util.BeanDefinition;
import edu.whu.ioc.util.Property;
import edu.whu.ioc.util.StringUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Map;

public interface Context {

    Map<String, Object> beans = new Hashtable<>();
    Map<String, BeanDefinition> definitionMap = new Hashtable<>();

    /**
     * get bean with id
     * @param id bean id
     * @return bean object
     */
    Object getBean(String id);

    /**
     * construct a bean by definition without inject
     * @param definition BeanDefinition
     * @return bean without inject
     */
    default Object constructBean(BeanDefinition definition) {
        Object object;
        try {
            Class<?> beanClass = Class.forName(definition.getClassPath());
            Constructor<?> constructor = beanClass.getConstructor();
            object = constructor.newInstance();
            return object;
        } catch (ClassNotFoundException e) {
            throw new BeanConstructException(definition.getClassPath() + " not found", e);
        } catch (NoSuchMethodException e) {
            throw new BeanConstructException(definition.getClassPath() + " has no valid constructor", e);
        } catch (InvocationTargetException e) {
            throw new BeanConstructException("Constructor invoked failed", e);
        } catch (InstantiationException e) {
            throw new BeanConstructException("Fail to create new instance", e);
        } catch (IllegalAccessException e) {
            throw new BeanConstructException("Constructor might not be public", e);
        }
    }

    /**
     * inject a bean with definition
     * @param obj bean
     * @param definition BeanDefinition
     */
    default void injectBean(Object obj, BeanDefinition definition) {
        Map<String, Property> propertyMap = definition.getProperties();
        for (Property property : propertyMap.values()) {
            String propertyName = property.getPropertyName();
            if (property.getRef() == null) {
                // simple type
                String value = property.getPropertyValue();
                injectSimpleType(obj, propertyName, value);
            } else {
                // reference type
                String id = property.getRef();
                injectReferenceType(obj, propertyName, id);
            }
        }
    }

    /**
     * inject one simple type into bean
     * @param obj bean
     * @param propertyName simple type property name
     * @param value simple type value
     */
    default void injectSimpleType(Object obj, String propertyName, String value) {
        String setFunctionName;
        try{
            setFunctionName = "set" + StringUtil.upperCaseFirst(propertyName);
        } catch (StringUtil.TransformStringException e) {
            throw new BeanInjectException("Property is an empty string", e);
        }
        boolean injectSuccess = false;
        for (Class<?> type : StringUtil.simpleTypes) {
            //get setter
            Method setFunction;
            try {
                setFunction = obj.getClass().getMethod(setFunctionName, type);
            } catch (NoSuchMethodException e) {
                continue;
            }

            //inject
            try {
                switch (type.getName()) {
                    case "boolean":
                        boolean bool = StringUtil.toBoolean(value);
                        setFunction.invoke(obj, bool);
                        break;

                    case "char":
                        char ch = StringUtil.toChar(value);
                        setFunction.invoke(obj, ch);
                        break;

                    case "long":
                        long l = StringUtil.toLong(value);
                        setFunction.invoke(obj, l);
                        break;

                    case "int":
                        int i = (int) StringUtil.toLong(value);
                        setFunction.invoke(obj, i);
                        break;

                    case "short":
                        short s = (short) StringUtil.toLong(value);
                        setFunction.invoke(obj, s);
                        break;

                    case "byte":
                        byte b = (byte) StringUtil.toLong(value);
                        setFunction.invoke(obj, b);
                        break;

                    case "double":
                        double d = StringUtil.toDouble(value);
                        setFunction.invoke(obj, d);
                        break;

                    case "float":
                        float f = (float) StringUtil.toDouble(value);
                        setFunction.invoke(obj, f);
                        break;

                    default:
                        setFunction.invoke(obj, value);
                        break;

                }
            } catch (StringUtil.TransformStringException e) {
                continue;
            } catch (InvocationTargetException e) {
                throw new BeanInjectException("Setter invoked failed", e);
            } catch (IllegalAccessException e) {
                throw new BeanInjectException("Setter might not be public", e);
            }

            injectSuccess = true;
            break;
        }
        if (!injectSuccess) {
            throw new BeanInjectException("No available setter for the property \"" + propertyName + "\"");
        }
    }

    /**
     * inject one reference type into bean
     * @param obj bean
     * @param propertyName reference type property name
     * @param id reference type id
     */
    default void injectReferenceType(Object obj, String propertyName, String id) {
        String setFunctionName;
        try{
            setFunctionName = "set" + StringUtil.upperCaseFirst(propertyName);
        } catch (StringUtil.TransformStringException e) {
            throw new BeanInjectException("Property is an empty string", e);
        }
        Object refer = beans.get(id);
        if (refer == null)
            throw new BeanInjectException("No bean which id is \"" + id + "\"");
        Class<?>[] interfaces = refer.getClass().getInterfaces();
        //get setter
        Method setFunction = null;
        for (Class<?> it : interfaces) {
            try {
                setFunction = obj.getClass().getMethod(setFunctionName, it);
            } catch (NoSuchMethodException e) {
                continue;
            }
            break;
        }
        if (setFunction == null) {
            throw new BeanInjectException("No available setter for the property \"" + propertyName + "\"");
        }

        //inject
        try {
            setFunction.invoke(obj, refer);
        } catch (IllegalAccessException e) {
            throw new BeanInjectException("Setter might not be public", e);
        } catch (InvocationTargetException e) {
            throw new BeanInjectException("Setter invoked failed", e);
        }
    }
}
