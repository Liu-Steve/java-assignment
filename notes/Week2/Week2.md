# Week2

## Stream IO

两种方式
1. 字符输入输出
2. 字节输入输出

数据的读写是异步的，可以使用`xx.flush()`同步

对于数据库连接、文件读写等资源需要手动进行释放，JVM无法帮助gc，或者使用try-with-resource进行资源申请，如`try (OutputStream os = new FileOutputStream("test.txt"))`

对于配置文件，可以使用`PropertiesLoader`进行加载

## 泛型

泛型类（与C++和C#相似）
泛型方法

extend可以限制类型，比直接传Object类型安全

## 反射

利用JVM的ClassLoader获取Class信息，并可以进行对对象的操作，包括`private`和`public`的字段和方法

## 注解

在Spring中大量使用，可以搭配反射进行注解的定义，方便用户使用

## Maven

Maven是一个构建工具（类似CMake）具有依赖管理能力

/src文件夹是代码文件夹
/target文件夹存储编译结果
在/src中
/main存储源代码，其中/resource存储资源文件
/test存储测试代码

pom.xml存储Maven配置

## 单元测试

JUnit为事实上的标准

单元测试应当与代码一起编写

单元测试之间应当保证相互隔离

可以使用BeforeEach和AfterEach注解提取出公共的代码

使用assertThrows进行异常的测试，使用匿名函数或lambda表达式

FIRST原则