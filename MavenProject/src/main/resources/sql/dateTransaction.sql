
create database if not exists ItemBase;

use ItemBase;


create table if not exists DateTransactionTable(
ItemID int not null unique auto_increment,
ItemName varchar(50) unique not null,
AddUnit int not null,
RemoveUnit int not null,
CurrentUnit int not null,
RecordTime varchar(30) not null
);

ALTER TABLE DateTransactionTable AUTO_INCREMENT = 1;



insert into DateTransactionTable(ItemName, AddUnit,RemoveUnit,CurrentUnit,RecordTime) values
('Thatch', 10,3,17,'2022-12-21 :23:00:34'),
('Wood', 10,20,30,'2022-12-22 :23:00:34'),
('Stone', 30,50,10,'2022-12-23 :23:00:34'),
('metal', 50,45,55,'2022-12-24 :23:00:34'),
('metal ingot', 100,90,110,'2022-12-25 :23:00:34'),
( 'rare mushroom', 40,10,70,'2022-12-26 :23:00:34'),
('rare flower', 45,45,45,'2022-12-27 :23:00:34');


#used when the id number is wrong
SET @auto_id = 0;
UPDATE DateTransactionTable SET ItemID = (@auto_id := @auto_id + 1) where 1=1;
ALTER TABLE DateTransactionTable AUTO_INCREMENT = 1;







