use ItemBase;
create table if not exists UserTable(
id int not null unique auto_increment,
name varchar(50)  not null,
username varchar(20) unique not null,
password varchar(20) not null,
emailAddress varchar(50)
);

insert into UserTable(name, username, password, emailAddress) values
('James Gong', 'James777G', '123456', 'jamesgong0719@gmail.com'),
('Ethan Duan', 'Gely222', '2002222', ''),
('Kelsey Leng', 'null', '2002222', ''),
('Peter Zhang', 'Benzene', '5201314', ''),
('Peter Wang', 'H2SO4', '54mixieerbulade', ''),
('James Gai', 'Ambush', 'sir', ''),
('Anthony Feng', 'kumiho', 'kidchild', ''),
('Chuanxi', 'Chauency Xu', '8888888', ''),
('Kylyn Wu', 'wusitu', 'EXingDDDD', '');


