package edu.whu.example.service.impl;

import edu.whu.example.dao.BookDao;
import edu.whu.example.service.BookService;

//@Component("bookService")
public class BookServiceImpl implements BookService {

//    @Autowired
    public void setDao(BookDao dao) {
        this.dao = dao;
    }

    private BookDao dao;

    @Override
    public int save() {
        System.out.println("Book Service Impl saving...");
        dao.save();
        return dao.getNum();
    }

}
