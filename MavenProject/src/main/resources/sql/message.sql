create database if not exists ItemBase;

use ItemBase;


create table if not exists MessageTable(
MessageID  int not null unique auto_increment,
StaffName varchar(20) not null,
Category varchar(10) not null,
Information varchar(80) not null,
MessageTime date not null,
Star int not null default 0

);
