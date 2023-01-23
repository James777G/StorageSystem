use ItemBase;


create table if not exists TransactionTable
(
    ID          int                          not null unique auto_increment,
    ItemName    varchar(50)                  not null,
    StaffName   varchar(50)                  NOT NULL,
    Status      enum('TAKEN', 'RESTOCK')     not null,
    Unit        int                          not null,
    TransactionTime  varchar(30)             not null,
    Purpose     varchar(60)
);

INSERT INTO TransactionTable(ItemName, StaffName, Status, Unit, TransactionTime, Purpose)
    values('Thatch','James','TAKEN',50,'2022-11-03','Used for thatch house');

SET @auto_id = 0;
update TransactionTable
set ID = (@auto_id := @auto_id + 1)
 where 1=1;
ALTER TABLE TransactionTable AUTO_INCREMENT = 1;