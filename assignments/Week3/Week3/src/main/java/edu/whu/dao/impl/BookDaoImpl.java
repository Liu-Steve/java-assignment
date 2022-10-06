package edu.whu.dao.impl;

import edu.whu.dao.BookDao;
import org.springframework.stereotype.Component;

@Component("bookDao")
public class BookDaoImpl implements BookDao {

    @Override
    public void save() {
        System.out.println("Book Dao Impl saving...");
    }

}
