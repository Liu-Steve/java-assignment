package org.week2;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

public class Main {
    private static final String propertyPath = "/myapp.properties";

    public static class ReadPropertyException extends RuntimeException {
        public ReadPropertyException(String msg) {
            super(msg);
        }
    }

    public static class ExecuteAnnotationException extends RuntimeException {
        public ExecuteAnnotationException(String msg) {
            super(msg);
        }
    }

    protected static String readProperty(String fileName) throws ReadPropertyException {
        Properties properties = new Properties();
        String bootstrapClass;
        try (InputStream in = Main.class.getResourceAsStream(fileName)) {
            if (in == null) {
                throw new ReadPropertyException("Open file failed");
            }
            properties.load(in);
            bootstrapClass = properties.getProperty("bootstrapClass");
        } catch (IOException e) {
            throw new ReadPropertyException(e.getMessage());
        }

        if (bootstrapClass == null) {
            throw new ReadPropertyException("No property called \"bootstrapClass\" exists");
        }

        System.out.println(" - Load properties success!");
        System.out.println("bootstrapClass = " + bootstrapClass);
        return bootstrapClass;
    }

    protected static int executeAnnotation(String bootstrapClass) throws ExecuteAnnotationException {
        int initMethodRet = 0;
        try {
            Class myClass = Class.forName(bootstrapClass);
            Object obj = myClass.newInstance();
            Method[] methods = myClass.getDeclaredMethods();
            boolean execute = false;
            for (Method method : methods) {
                if (method.isAnnotationPresent(InitMethod.class)) {
                    method.setAccessible(true);
                    Object ret = method.invoke(obj);
                    initMethodRet = (int) ret;
                    execute = true;
                }
            }
            if (!execute) {
                throw new ExecuteAnnotationException("No such annotation");
            }
        } catch (ClassNotFoundException e) {
            throw new ExecuteAnnotationException("Class Not Found");
        } catch (InstantiationException e) {
            throw new ExecuteAnnotationException("Can not create an instance");
        } catch (IllegalAccessException e) {
            throw new ExecuteAnnotationException("Illegal Access");
        } catch (InvocationTargetException e) {
            throw new ExecuteAnnotationException("Invoke method failed");
        } catch (IllegalArgumentException e) {
            throw new ExecuteAnnotationException("No argument should exist");
        } catch (ClassCastException e) {
            throw new ExecuteAnnotationException("Return type is not int");
        }

        System.out.println(" - Execute init method success!");
        return initMethodRet;
    }

    public static void main(String[] args) {
        String bootstrapClass;
        try {
            bootstrapClass = readProperty(propertyPath);
            int initMethodRet = executeAnnotation(bootstrapClass);
            System.out.println("被注解的方法返回整形" + initMethodRet);
        } catch (ReadPropertyException e) {
            System.err.println(" - Load properties failed!");
            System.err.println(e.getMessage());
        } catch (ExecuteAnnotationException e) {
            System.err.println(" - Execute init method failed!");
            System.err.println(e.getMessage());
        }
    }
}