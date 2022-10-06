package edu.whu;

import edu.whu.config.BookConfig;
import edu.whu.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainAnnotation {
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(BookConfig.class);
        BookService service = ctx.getBean("bookService", BookService.class);
        service.save();
    }
}
