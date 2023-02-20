use ItemBase;
create table if not exists UserTable(
id int not null unique auto_increment,
name varchar(100)  not null,
username varchar(100) unique not null,
password varchar(100) not null,
emailAddress varchar(100)
);

insert into UserTable(name, username, password, emailAddress) values
('James Gong', 'James777G', '123456', 'jamesgong0719@gmail.com'),
('Ethan Duan', 'Gely222', '2002222', ''),
('Kelsey Leng', 'null', '2002222', ''),
('Peter Zhang', 'Benzene', '5201314', ''),
('Peter Wang', 'H2SO4', '54mixieerbulade', ''),
('James Gai', 'Ambush', 'sir', ''),
('Anthony Feng', 'kumiho', 'kidchild', ''),
('Chuanxi', 'Chauency Xu', 'Hji20010805', 'xuxinhuiqiang@gmail.com'),
('Kylyn Wu', 'wusitu', 'EXingDDDD', '');


