use ItemBase;

create table if not exists RegulatoryTable
(
    ID     int     not null unique auto_increment,
    ItemName    varchar(100)    unique      not null,
    ItemAmount int not null
);