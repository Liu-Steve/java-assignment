# Week4

## 1 Web App Basic

协议 HTTP

传统Web App
初始请求与后续请求都返回HTML文件
缺点：
速度慢
服务端决定跳转逻辑，复杂性高
前后端代码混合，不利于合作开发

SPA
只有初始传输HTML页面
后续访问Rest API进行更新

## 2 Spring MVC

Model View Controller

SSM = SpringMVC + Spring + MyBatis

SpringBoot整合了Spring和SpringMVC
特点：
程序独立运行
内嵌中间件
包含Starter类
抛弃XML配置

SpringBoot配置
src/main/resources/application.properties
或src/main/resources/application.yaml (推荐) 

## 3 Restful API

REST风格
数据和方法都可以用URI访问
请求 = 动词 + 宾语
HTTP Method
GET POST PUT PATCH DELETE 
HTTP CODE
2XX成功
4XX客户端有错
5XX服务端有错

## 4 Swagger

API 文档生成

## 5 Vue + Rest API

Element UI View层
Vue MVVM 框架