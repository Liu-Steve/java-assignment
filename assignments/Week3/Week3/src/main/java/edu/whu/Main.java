package edu.whu;

import edu.whu.dao.BookDao;
import edu.whu.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/applicationContext.xml");
        BookService service = ctx.getBean("bookService", BookService.class);
        service.save();
    }
}