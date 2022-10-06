package edu.whu.ioc.util;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class BeanDefinition {

    protected String id;
    protected String classPath;
    protected final Map<String, Property> properties = new Hashtable<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassPath() {
        return classPath;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    public Map<String, Property> getProperties() {
        return properties;
    }

}
