use ItemBase;

create table if not exists EmailTable
(
    ID     int     not null unique auto_increment,
    emailAddress    varchar(100)    unique      not null
);