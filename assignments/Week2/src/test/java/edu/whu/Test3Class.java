package edu.whu;

import org.week2.InitMethod;

public class Test3Class {

    @InitMethod
    public String init() {
        String ret = "2022";
        System.out.println("edu.whu.Test3Class.init()方法被执行了");
        System.out.println("edu.whu.Test3Class.init()方法返回整形" + ret);
        return ret;
    }

}
