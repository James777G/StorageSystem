
create database if not exists ItemBase;

use ItemBase;


create table if not exists DateTransactionTable(
ItemID int not null unique auto_increment,
ItemName varchar(50) unique not null,
AddUnit int not null,
RemoveUnit int not null,
CurrentUnit int not null,
RecordTime DATETiME not null
);

insert into DateTransactionTable(ItemName, AddUnit,RemoveUnit,CurrentUnit,RecordTime) values
('Thatch', 10,3,17,now()),
('Wood', 10,20,30,now()),
('Stone', 30,50,10,now()),
('metal', 50,45,55,now()),
('metal ingot', 100,90,110,now()),
( 'rare mushroom', 40,10,70,now()),
('rare flower', 45,45,45,now());


insert into DateTransactionTable(ItemName, AddUnit,RemoveUnit,CurrentUnit,RecordTime) values
('giant honey', 45,10,80,now()),
('electronics', 150,100,200,now()),
('organic polymer', 150,200,100,now()),
('polymer', 200,300,100,now()),
('gun powder', 70,90,50,'2022-12-23 12:56:02');







