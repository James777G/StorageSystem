
create database if not exists ItemBase;

use ItemBase;


create table if not exists DateTransactionTable(
        ItemID int not null unique auto_increment,
        ItemName varchar(50) unique not null,
        AddUnit int not null,
        RemoveUnit int not null,
        CurrentUnit int not null,
        RecordTIme DATETIME not null
);

insert into DateTransactionTable(ItemName, AddUnit,RemoveUnit,CurrentUnit,RecordTIme) values
                ('Thatch', 10,3,17,now()),
                ('Wood', 10,20,30,now()),
                ('Stone', 30,50,10,now()),
                ('metal', 50,45,55,now()),
                ('metal ingot', 100,90,110,now()),
                ( 'rare mushroom', 40,10,70,now()),
                ('rare flower', 45,45,45,now());
