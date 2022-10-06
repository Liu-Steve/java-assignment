package edu.whu.ioc.context;

import edu.whu.example.dao.BookDao;
import edu.whu.example.dao.impl.BookDaoImpl;
import edu.whu.example.test.TestInject;
import edu.whu.ioc.excepetion.BeanConstructException;
import edu.whu.ioc.excepetion.BeanInjectException;
import edu.whu.ioc.util.BeanDefinition;
import edu.whu.ioc.util.Property;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ContextTest {

    Context ctx;

    @BeforeEach
    void setUp() {
        ctx = id -> null;
    }

    @Test
    void constructBean() {
        // normal
        BeanDefinition definition1 = new BeanDefinition();
        definition1.setClassPath("edu.whu.example.dao.impl.BookDaoImpl");
        assertDoesNotThrow(() -> ctx.constructBean(definition1));

        // no such class
        BeanDefinition definition2 = new BeanDefinition();
        definition2.setClassPath("no.such.class");
        assertThrows(BeanConstructException.class, () -> ctx.constructBean(definition2));
    }

    @Test
    void injectBean() {
        // simple property name is empty
        BeanDefinition definition1 = new BeanDefinition();
        definition1.setClassPath("edu.whu.example.dao.impl.BookDaoImpl");
        Map<String, Property> map1 = definition1.getProperties();
        Property property1 = new Property();
        property1.setPropertyName("");
        property1.setPropertyValue("123");
        map1.put("p1", property1);
        BookDao dao1 = new BookDaoImpl();
        assertThrows(BeanInjectException.class, () -> ctx.injectBean(dao1, definition1));

        // reference property name is empty
        BeanDefinition definition2 = new BeanDefinition();
        definition2.setClassPath("edu.whu.example.dao.impl.BookDaoImpl");
        Map<String, Property> map2 = definition2.getProperties();
        Property property2 = new Property();
        property2.setPropertyName("");
        property2.setRef("123");
        map2.put("p2", property2);
        BookDao dao2 = new BookDaoImpl();
        assertThrows(BeanInjectException.class, () -> ctx.injectBean(dao2, definition1));
    }

    @Test
    void injectSimpleType() {
        // test all simple type
        TestInject test = new TestInject();
        assertDoesNotThrow(() -> ctx.injectSimpleType(test, "p1", "true"));
        assertDoesNotThrow(() -> ctx.injectSimpleType(test, "p2", "T"));
        assertDoesNotThrow(() -> ctx.injectSimpleType(test, "p3", "2147483648"));
        assertDoesNotThrow(() -> ctx.injectSimpleType(test, "p4", "65536"));
        assertDoesNotThrow(() -> ctx.injectSimpleType(test, "p5", "256"));
        assertDoesNotThrow(() -> ctx.injectSimpleType(test, "p6", "0"));
        assertDoesNotThrow(() -> ctx.injectSimpleType(test, "p7", "3.1415926"));
        assertDoesNotThrow(() -> ctx.injectSimpleType(test, "p8", "3.14"));
        assertDoesNotThrow(() -> ctx.injectSimpleType(test, "p9", "Hello World!"));
        assertThrows(BeanInjectException.class, () -> ctx.injectSimpleType(test, "p10", "456"));
        assertTrue(test.isP1());
        assertEquals('T', test.getP2());
        assertEquals(2147483648L, test.getP3());
        assertEquals(65536, test.getP4());
        assertEquals(256, test.getP5());
        assertEquals(0, test.getP6());
        assertEquals(3.1415926, test.getP7());
        assertEquals(3.14f, test.getP8());
        assertEquals("Hello World!", test.getP9());
    }

    @Test
    void injectReferenceType() {
        // test reference type
        TestInject test = new TestInject();
        assertThrows(BeanInjectException.class, () -> ctx.injectReferenceType(test, "", "1"));
    }
}