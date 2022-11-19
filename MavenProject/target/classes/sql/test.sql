show databases;

create database if not exists newtable;

use newtable;

select database();

drop table if exists Trial;

create table if not exists Trial(
ID int not null unique auto_increment,
Name varchar(20) not null,
Price int not null default 500
);

insert into Trial(Name, Price) Values
('James', 1000),
('Peter', 2000),
('James', 3000),
('kylyn', 4000),
('Xu', 5000);

DESC Trial;
Alter table Trial rename to newbase;

show create table newbase;

Alter table newbase add primary key(ID);

Alter table newbase modify column ID INT auto_increment

create table if not exists mapping (
LOLID int unique auto_increment not null,
ID int unique 
);

Insert into mapping (ID) values
(1),
(2),
(3),
(4),
(5);

Alter Table newbase add foreign key (ID) references mapping(LOLID);

desc newbase;

show create table newbase;

select * from newtable.newbase;

select ID from newbase;
select name from newbase;

select name as "名前" from newbase;

select name from newbase where ID = 3;

select ID, name from newbase where ID > 3 and ID < 5;

update newbase set price = price + 1000;

update newbase set price = price + 500, name = 'James Gong' where ID = 1;

update newbase set price = price + 2000 where ID = 5;

