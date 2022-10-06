package edu.whu.ioc.util;

import edu.whu.ioc.excepetion.XmlParseContextException;
import edu.whu.ioc.excepetion.XmlReadContextException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class XmlConfigReader {

    /**
     * Load xml document to dom4j document element.
     *
     * @param in InputStream of the xml file
     * @return dom4j document
     * @throws XmlReadContextException DocumentException in dom4j
     */
    public static Document load(InputStream in) throws XmlReadContextException {
        SAXReader reader = new SAXReader();
        Document document;
        try {
            document = reader.read(in);
        } catch (DocumentException e) {
            throw new XmlReadContextException(e);
        }
        return document;
    }

    /**
     * transform dom4j element to BeanDefinition
     *
     * @param element dom4j element
     * @return BeanDefinition
     * @throws XmlParseContextException some properties are needed
     */
    protected static BeanDefinition elementToBean(Element element) throws XmlParseContextException {
        BeanDefinition define = new BeanDefinition();

        //parse attribute value
        String id = element.attributeValue("id");
        String classPath = element.attributeValue("class");
        define.setId(id);
        define.setClassPath(classPath);

        //a bean should have id && class tag
        if (id == null) {
            throw new XmlParseContextException("Id not found in this bean");
        }
        if (classPath == null) {
            throw new XmlParseContextException("Class not found in this bean");
        }

        //parse properties
        List<Element> list = element.elements("property");
        for (Element e : list) {
            String name = e.attributeValue("name");
            String value = e.attributeValue("value");
            String ref = e.attributeValue("ref");

            //a property should have name && (value || ref) tag
            if (name == null) {
                throw new XmlParseContextException("Name tag not found in " + id);
            }
            if (value == null && ref == null) {
                throw new XmlParseContextException("Value and ref tag both not found in " + id);
            }
            if (value != null && ref != null) {
                throw new XmlParseContextException("Value and ref tag both found in " + id);
            }

            Property property = new Property();
            property.setPropertyName(name);
            property.setPropertyValue(value);
            property.setRef(ref);
            Map<String, Property> properties = define.getProperties();
            properties.put(name, property);
        }

        return define;
    }

    /**
     * transform dom4j document to List of BeanDefinition
     *
     * @param doc dom4j document
     * @return List
     * @throws XmlParseContextException some tags are needed
     */
    public static List<BeanDefinition> documentToBeans(Document doc) throws XmlParseContextException {
        List<BeanDefinition> beans = new ArrayList<>();
        Element element = doc.getRootElement();

        //beans should in the document
        if (!"beans".equals(element.getName())) {
            throw new XmlParseContextException("Beans tag is not found in the document");
        }

        List<Element> list = element.elements("bean");
        for (Element e : list) {
            BeanDefinition define = elementToBean(e);
            beans.add(define);
        }
        return beans;
    }

}
