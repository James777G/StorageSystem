# StorageSystem

## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)

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
Create new '%root%'@'%' identified by '%root%';
grant all privileges on *.* to 'root'@'%' with grant option;
flush privileges;


```
