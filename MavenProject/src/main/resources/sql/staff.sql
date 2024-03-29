use ItemBase;

create table if not exists StaffTable
(
    staffID      int                           not null unique auto_increment,
    staffName    varchar(100)                   not null,
    status       enum('ACTIVE', 'INACTIVE')    not null,
    otherInfo    varchar(200)
);