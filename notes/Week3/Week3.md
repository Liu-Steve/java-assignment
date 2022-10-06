# Week2

## Spring IOC

IOC：反转控制

Beans：组件

## Hello, Spring

## Lifecycle of Beans

scope字段：
singleton 单例模式
prototype 原型模式

对于有状态的bean可能出现线程不安全问题

## Dependency injection

属性 property 注入
构造函数 constructor-arg 注入
集合注入

构造函数注入用于特别重要不可缺少的参数

Autowired自动装配

一般byType
也可选择byName

byType会抛异常
byName不会

## IOC Containers

xml 在类中找配置文件
file system 在文件系统找配置文件

getBean方法的三种写法

### XML配置文件格式

#### 一级标签
1. beans 代表所有的 bean
+ 属性
xmlns 与 xsi 等 包含了 spring xml 的规范

#### 二级标签 
1. bean 代表需要被注入的接口
+ 属性
id 查找该接口的字符串
class 该接口的具体实现
autowire 是否进行自动类型注入 byTybe 表示按类型匹配 byName 表示按名称匹配
scope 是否单例 singleton 单例 prototype 多例

#### 三级标签
1. property 
+ 属性
name 注入属性名，调用相应的 set 方法
value 设置注入简单类型的数据值
ref 设置注入引用类型 bean 的 id

2. constructor-arg
+ 属性
name 注入参数名
value 设置注入简单类型的数据值
ref 设置注入引用类型bean的id

#### 四级标签
1. array 注入数组
子标签 value
2. list 注入 List
子标签 value
3. set 注入 Set
子标签 value
4. map 注入 Map
子标签 entry 属性为 key value
5. props 注入 Properties
子标签 prop 属性为 key

## Spring Annotation

通过注解取代xml配置文件