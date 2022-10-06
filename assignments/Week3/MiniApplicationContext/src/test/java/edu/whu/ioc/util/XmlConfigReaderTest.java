package edu.whu.ioc.util;

import edu.whu.ioc.excepetion.XmlParseContextException;
import edu.whu.ioc.excepetion.XmlReadContextException;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class XmlConfigReaderTest {

    @Test
    void load() {
        assertThrows(XmlReadContextException.class, () -> XmlConfigReader.load(null));
        try(InputStream inputStream = this.getClass().getResourceAsStream("/context.xml")) {
            XmlConfigReader.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void elementToBean() {
        // normal
        Element bean1 = DocumentHelper.createElement("bean");
        bean1.addAttribute("id", "bookDao");
        bean1.addAttribute("class", "edu.whu.example.dao.impl.BookDaoImpl");
        Element property1 = DocumentHelper.createElement("property");
        property1.addAttribute("name", "num");
        property1.addAttribute("value", "123");
        bean1.add(property1);

        BeanDefinition definition = XmlConfigReader.elementToBean(bean1);
        assertEquals("bookDao", definition.getId());
        assertEquals("edu.whu.example.dao.impl.BookDaoImpl", definition.getClassPath());
        Map<String, Property> properties = definition.getProperties();
        Property p = properties.get("num");
        assertEquals("123", p.getPropertyValue());
        
        // no id
        Element bean2 = DocumentHelper.createElement("bean");
        bean2.addAttribute("class", "edu.whu.example.dao.impl.BookDaoImpl");
        assertThrows(XmlParseContextException.class, () -> XmlConfigReader.elementToBean(bean2));

        // no class
        Element bean3 = DocumentHelper.createElement("bean");
        bean3.addAttribute("id", "bookDao");
        assertThrows(XmlParseContextException.class, () -> XmlConfigReader.elementToBean(bean3));

        // property no name
        Element bean4 = DocumentHelper.createElement("bean");
        bean4.addAttribute("id", "bookDao");
        bean4.addAttribute("class", "edu.whu.example.dao.impl.BookDaoImpl");
        Element property2 = DocumentHelper.createElement("property");
        property2.addAttribute("value", "123");
        bean4.add(property2);
        assertThrows(XmlParseContextException.class, () -> XmlConfigReader.elementToBean(bean4));

        // property no value and ref
        Element bean5 = DocumentHelper.createElement("bean");
        bean5.addAttribute("id", "bookDao");
        bean5.addAttribute("class", "edu.whu.example.dao.impl.BookDaoImpl");
        Element property3 = DocumentHelper.createElement("property");
        property3.addAttribute("name", "num");
        bean5.add(property3);
        assertThrows(XmlParseContextException.class, () -> XmlConfigReader.elementToBean(bean5));

        // property no value and ref
        Element bean6 = DocumentHelper.createElement("bean");
        bean6.addAttribute("id", "bookDao");
        bean6.addAttribute("class", "edu.whu.example.dao.impl.BookDaoImpl");
        Element property4 = DocumentHelper.createElement("property");
        property4.addAttribute("name", "num");
        property4.addAttribute("value", "123");
        property4.addAttribute("ref", "refBean");
        bean6.add(property4);
        assertThrows(XmlParseContextException.class, () -> XmlConfigReader.elementToBean(bean6));
    }

    @Test
    void documentToBeans() {
        // normal
        Document doc;
        try(InputStream inputStream = this.getClass().getResourceAsStream("/context.xml")) {
            doc = XmlConfigReader.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<BeanDefinition> list = XmlConfigReader.documentToBeans(doc);
        assertEquals(2, list.size());

        // no beans tag
        try(InputStream inputStream = this.getClass().getResourceAsStream("/empty.xml")) {
            doc = XmlConfigReader.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Document finalDoc = doc;
        assertThrows(XmlParseContextException.class, () -> XmlConfigReader.documentToBeans(finalDoc));
    }
}