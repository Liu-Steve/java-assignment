package edu.whu;

import org.week2.InitMethod;

public class Test2Class {

    public Test2Class(int value) {

    }

    @InitMethod
    public int init() {
        int ret = 2022;
        System.out.println("edu.whu.Test2Class.init()方法被执行了");
        System.out.println("edu.whu.Test2Class.init()方法返回整形" + ret);
        return ret;
    }

}
