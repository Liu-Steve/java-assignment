package edu.whu;

import org.week2.InitMethod;

public class MyClass {

    @InitMethod
    public int init() {
        int ret = 2022;
        System.out.println("edu.whu.MyClass.init()方法被执行了");
        System.out.println("edu.whu.MyClass.init()方法返回整形" + ret);
        return ret;
    }

}
