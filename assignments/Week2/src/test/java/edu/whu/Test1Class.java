package edu.whu;

import org.week2.InitMethod;

public class Test1Class {

    @InitMethod
    public int init(int value) {
        int ret = 2022;
        System.out.println("edu.whu.Test1Class.init()方法被执行了");
        System.out.println("edu.whu.Test1Class.init()方法返回整形" + ret);
        return ret;
    }

}
