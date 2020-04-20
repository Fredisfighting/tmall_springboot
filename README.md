# 用Springboot实现一个仿天猫项目

## 个人服务器演示地址

http://116.62.11.33:8080/

## 介绍

从[How2J的Java教程](http://how2j.cn/)模仿学习，主要为了巩固Java的知识点和框架的使用。 

## 部署

由于项目是基于springboot框架，通过打包成war的形式通过tomcat访问

## 技术简述

### 前后端技术

| 功能       | 使用       |
| ---------- | ---------- |
| 核心框架   | springboot |
| 数据库     | mysql      |
| 持久层框架 | hibernate  |
| JS         | vue+jquery |
| 安全框架   | Shiro      |
| 模板引擎   | Thymeleaf  |

## 数据库表结构

数据共九张表 分别为:

- user：用户表
- category：分类表，如大衣，冰箱
- product：产品表，每个分类有多个产品
- productImage:产品图片表
- property：属性表，存放属性信息，如颜色，重量，品牌，厂商，型号等
- propertyvalue：属性值表，红色，90kg，海尔等等
- order：订单表
- orderitem：订单项表，一个订单有多个订单项
- review:评价表，用户在商品下的评价