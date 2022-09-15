# Week1

Java基础内容

参考教程：[菜鸟教程](https://www.runoob.com/java/java-tutorial.html)

笔记主要记载Java与C#的不同点

## Java基础语法

1. 源文件名应当与public类名相同，不同会导致编译错误
2. 方法名应当以小写字母开头
3. 所有的Java程序由`public static void main(String[] args)`方法开始执行（兼容`public static void main(String args[])`）
4. Java函数不支持默认参数，只能通过重载实现类似的效果
5. Java函数调用均为值传递

## Java对象与类

每个类都有构造方法。如果没有显式地为类定义构造方法，Java 编译器将会为该类提供一个默认构造方法

## Java基本数据类型

1. Java的数据类型包括内置数据类型与引用数据类型，与C#不同，布尔值的名称为`boolean`，与C++不同，`long`表示64位长整数，`char`为UTF-16编码
2. 奇怪的特性：Java对应整形有相应的包装`Integer`，Java会创建`IntegerCache`存储-128至127所有的`Integer`对象，当装箱的数字在此范围内时会自动将`IntegerCache`内事先分配好的`Integer`对象地址赋给引用，否则在堆中创建一个新的`Integer`对象，此特性会影响上一条`Integer`的比较结果

## Java修饰符

1. 当子类与基类不在同一包中时，那么在子类中，子类实例可以访问其从基类继承而来的protected方法，而不能访问基类实例的protected方法
2. synchronized关键字声明的方法同一时间只能被一个线程访问
3. volatile修饰的成员变量在每次被线程访问时，都强制从共享内存中重新读取该成员变量的值。而且，当成员变量发生变化时，会强制线程将变化值回写到共享内存

## Java运算符

1. instanceof运算符用于操作对象实例，检查该对象是否是一个特定类型
2. Java的`==`符号用于比较引用类型时只能比较地址是否相同，即使是`String`类型也是如此

## Java String 类

1. 一旦创建了String对象，那它的值就无法改变
2. String对象分配在堆内，当不断连接String时会反复占用拷贝全部字符串后释放空间，相当耗时
3. 需要可以修改的字符串，可以使用线程不安全的StringBuilder和线程安全的StringBuffer

