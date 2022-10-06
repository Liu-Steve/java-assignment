package edu.whu.example.dao.impl;

import edu.whu.example.dao.BookDao;

//@Component("bookDao")
public class BookDaoImpl implements BookDao {

    protected int num;

    @Override
    public int getNum() {
        return num;
    }

    @Override
    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public void save() {
        System.out.println("Book Dao Impl saving...");
    }

}
