use ItemBase;


create table if not exists TransactionTable
(
    ID          int                          not null unique auto_increment,
    ItemName    varchar(200)                  not null,
    StaffName   varchar(100)                  NOT NULL,
    Status      enum('TAKEN', 'RESTOCK')     not null,
    Unit        int                          not null,
    TransactionTime  varchar(50)             not null,
    Purpose     varchar(300)
);

INSERT INTO TransactionTable(ItemName, StaffName, Status, Unit, TransactionTime, Purpose)
    values('Thatch','James','TAKEN',50,'2022-11-03','Used for thatch house');

SET @auto_id = 0;
update TransactionTable
set ID = (@auto_id := @auto_id + 1)
 where 1=1;
ALTER TABLE TransactionTable AUTO_INCREMENT = 1;