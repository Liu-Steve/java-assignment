package edu.whu.ioc.context;

import edu.whu.ioc.excepetion.BeanConstructException;
import edu.whu.ioc.excepetion.BeanNotExistException;
import edu.whu.ioc.excepetion.XmlReadContextException;
import edu.whu.ioc.util.BeanDefinition;
import edu.whu.ioc.util.Property;
import edu.whu.ioc.util.XmlConfigReader;
import org.dom4j.Document;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class XmlContext implements Context {

    protected Class<?> app;

    /**
     * get the start class
     */
    protected void updateStartClass() {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        String appPath = trace[3].getClassName();   //Main
        try {
            app = Class.forName(appPath);
        } catch (ClassNotFoundException e) {
            throw new XmlReadContextException("Refer error class in stack trace.", e);
        }
    }

    /**
     * read the xml
     * @param config xml path
     * @return dom4j document
     */
    protected Document getXmlDocument(String config) {
        Document doc;
        try(InputStream inputStream = app.getResourceAsStream(config)) {
            doc = XmlConfigReader.load(inputStream);
        } catch (IOException e) {
            throw new XmlReadContextException(e);
        }
        return doc;
    }

    /**
     * transform document into definitionMap and construct beans
     * @param doc dom4j document
     */
    protected void updateBeanDocuments(Document doc) {
        List<BeanDefinition> list = XmlConfigReader.documentToBeans(doc);
        for (BeanDefinition definition : list) {
            definitionMap.put(definition.getId(), definition);
        }
    }

    /**
     * construct beans into beans
     */
    protected void constructBeans() {
        for (BeanDefinition definition : definitionMap.values()) {
            beans.put(definition.getId(), constructBean(definition));
        }
    }

    /**
     * inject properties into beans
     */
    protected void injectBeans() {
        for (String key : definitionMap.keySet()) {
            injectBean(beans.get(key), definitionMap.get(key));
        }
    }

    public XmlContext(String config) {

        updateStartClass();
        Document doc = getXmlDocument(config);
        updateBeanDocuments(doc);
        constructBeans();
        injectBeans();

    }

    @Override
    public Object getBean(String id) {
        Object ret = beans.get(id);
        if (ret == null) {
            throw new BeanNotExistException("\"" + id + "\" is not exists");
        }
        return ret;
    }
}
