Use
ItemBase;

CREATE table if not exists codeTable(
id int not null unique auto_increment,
username varchar(20) not null,
code int not null
);


