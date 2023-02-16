# StorageSystem
## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)
* [Background](#background)
* [Glossary](#glossary)

## General info
Warehouse management system to govern your items neatly. Requires Wi-Fi to run locally.

## Technologies
Project is created with:
* Java-SE version: 17.0.2
* MySQL version: 8.0.31
* MyBatis version: 3.5.5
* JavaFX version: 18.0.1


## Setup
To run this project, install:

* MySQL --- [https://dev.mysql.com/downloads/mysql/] (Link to MySQL Installer)
* Download Dbeavers in your perferred IDE marketplace
* Add the path to the bin folder to the path environment variable

## For Mac Users: 
```
export PATH=${PATH}:%path to bin folder%

```
## Initialise your MySQL Server as administrator/sudo for mac users
```
mysqld --initialize-insecure;
mysqld -install;
net start MySQL;
```

## Log into your MySQL Server
```
mysql -u root -p;

```

## Change your MySQL Server Password
```
ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY '%your preferred password%';
flush privileges;

```

## Create new mysql authorised user to allow remote access using IP address


```
Create new 'root'@'%' identified by 'root';
grant all privileges on *.* to 'root'@'%' with grant option;
flush privileges;


```

## View your IPV4 address

```
ipconfig
```
* For mac users please view [https://www.avast.com/c-how-to-find-ip-address#topic-3]


## Background

* 随着公司的规模增长，我们一直没有一个仓库管理系统， 无法去计量仓库里面物品的数量
* 在逐渐引入各类高价值物品的情况下， 为了不造成损失， 所以一个仓库管理系统是必需的
* 这个系统必须可以准确的记载物流， 以及提供历史物流信息以便查询
