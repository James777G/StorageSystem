create database if not exists ItemBase;

use ItemBase;


create table if not exists DateTransactionTable
(
    ItemID      int                not null unique auto_increment,
    ItemName    varchar(50) unique not null,
    StaffName   varchar(50)        NOT NULL,
    AddUnit     int                not null,
    RemoveUnit  int                not null,
    CurrentUnit int                not null,
    RecordTime  varchar(30)        not null,
    Purpose     varchar(60)        NOT NULL
);

#ALTER TABLE DateTransactionTable AUTO_INCREMENT = 1;


insert into DateTransactionTable(ItemName, StaffName, AddUnit, RemoveUnit, CurrentUnit, RecordTime,Purpose)
values ('Thatch', 'Tom', 0, 10, 17, '2022-12-21 :01:00:34','sell'),
       ('Wood', 'Sam', 0, 20, 30, '2022-12-22 :09:00:34','sell'),
       ('Stone', 'Jack', 0, 50, 10, '2022-11-23 :03:00:34','**--'),
       ('metal', 'Smith', 0, 30, 55, '2022-12-19 :21:00:34','***'),
       ('metal ingot', 'Andy', 0, 50, 110, '2022-01-25 :23:00:34','**'),
       ('rare mushroom', 'YOYO', 40, 0, 70, '2022-08-26 :23:00:34','**'),
       ('rare flower', 'Ipad', 5, 0, 45, '2022-12-27 :22:00:34','**');
insert into DateTransactionTable(ItemName, StaffName, AddUnit, RemoveUnit, CurrentUnit, RecordTime,Purpose)
values ('cup', 'light', 10, 0, 17, '2022-12-29 :02:00:34','**');

insert into DateTransactionTable(ItemName, StaffName, AddUnit, RemoveUnit, CurrentUnit, RecordTime,Purpose)
values ('fan', 'haha', 10, 0, 10, '2022-12-29 :02:01:34','**');

SELECT *
from DateTransactionTable
where AddUnit =0
order by RecordTime desc
limit 0,4;