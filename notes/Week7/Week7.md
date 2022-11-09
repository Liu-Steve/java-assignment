# Week 7

## 1 AOP

SoC 关注点分离

模块化思想、单一职责原则

横切关注点：如安全检查、事务、异常处理、日志
面向对象难以解决这些关注点代码重复与代码缠绕的问题

由此提出面向切面编程 AOP

Advice 横向关注点
Joinpoint 植入点
Pointcut 匹配表达式找到对应 Joinpoint
Weaving 植入

## 2 Hello AOP

## 3 原理

JDK Proxy 或 CGLib Proxy 原理

## 4 PointCuts

execution 匹配方法
within 匹配对象
this 按声明匹配对象
target 按实际匹配对象
@annotation 按注解匹配
@args 按参数匹配
@target 按注解标注的类的对象匹配
bean 按类名匹配

*匹配1个单词
..匹配0个到多个单词

可以使用 && 等连接

## 5 Advice

注解
@Before
@After
@AfterReturning
@Around
@AfterThrowing

有先后顺序

1. Around_前半部分 (不proceed后面都不会执行)
2. Before
3. 程序主体
4. AfterReturning
5. After (不被异常打断)
6. Around_后半部分

示例：统计程序运行时间

## 6 全局异常处理

