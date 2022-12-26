create database if not exists ItemBase;

use ItemBase;

create table if not exists ItemTable(
ItemID int not null unique auto_increment,
ItemName varchar(50) unique not null,
Unit int not null,
Description varchar(100)
);



insert into ItemTable(ItemName, Unit) values
('Thatch', 10),
('Wood', 20),
('Stone', 30),
('metal', 50),
('metal ingot', 100),
('rare mushroom', 40),
('rare flower', 45),
('giant honey', 45),
('electronics', 150),
('organic polymer', 150),
('polymer', 200),
('gun powder', 70),
('advanced rifle bullet', 100),
('element shard', 500),
('element', 1000),
('element dust', 10),
('charcoal', 20),
('concealed gas ball', 500);


insert into ItemTable(ItemName, Unit, Description) values
('simple bullet', 30, 'used for pistol'),
('bullet', 50, '');





