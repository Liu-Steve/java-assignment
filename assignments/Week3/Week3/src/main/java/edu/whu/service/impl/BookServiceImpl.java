package edu.whu.service.impl;

import edu.whu.dao.BookDao;
import edu.whu.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("bookService")
public class BookServiceImpl implements BookService {

    @Autowired
    public void setDao(BookDao dao) {
        this.dao = dao;
    }

    private BookDao dao;

    @Override
    public void save() {
        System.out.println("Book Service Impl saving...");
        dao.save();
    }

}
