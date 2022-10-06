package edu.whu.ioc.context;

import edu.whu.example.dao.BookDao;
import edu.whu.example.service.BookService;
import edu.whu.ioc.excepetion.BeanNotExistException;
import edu.whu.ioc.excepetion.XmlReadContextException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class XmlContextTest {

    XmlContext ctx;

    @BeforeEach
    void setUp() {
        ctx = new XmlContext("/context.xml");
    }

    @Test
    void updateStartClass() {
        assertEquals(this.getClass(), ctx.app);
    }

    @Test
    void getXmlDocument() {
        assertDoesNotThrow(() -> ctx.getXmlDocument("/context.xml"));
        assertDoesNotThrow(() -> ctx.getXmlDocument("/empty.xml"));
        assertThrows(XmlReadContextException.class, () -> ctx.getXmlDocument("/"));
    }

    @Test
    void getBean() {
        BookDao dao = (BookDao) ctx.getBean("bookDao");
        assertEquals(123, dao.getNum());
        BookService service = (BookService) ctx.getBean("bookService");
        assertEquals(123, service.save());
        assertThrows(BeanNotExistException.class, () -> ctx.getBean("noSuchBean"));
    }
}