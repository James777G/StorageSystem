create database if not exists ItemBase;

use ItemBase;


create table if not exists MessageTable(
MessageID  int not null unique auto_increment,
StaffName varchar(20) not null,
Category varchar(10) not null,
Information varchar(80) not null,
MessageTime date not null,
Star int not null default 0

);

insert into MessageTable (StaffName,Category,Information,MessageTime)values
('Tom','Item','NONE', curdate()),
('Amy','Item','ddd',curdate()),
('Smith','Staff','dfrW',curdate()),
('Berry','Item','ddghr',curdate()),
('Peter','Item','dijdd',curdate()),
('Wang','Item','ddted',curdate());

insert into MessageTable (StaffName,Category,Information,MessageTime,Star)values
('Li','Item','iiuyuy',curdate(),1),
('Ming','Item','45654',curdate(),1),
('Tang','Item','845',curdate(),1),
('Liu','Item','gvgfv',curdate(),1);

insert into MessageTable (StaffName,Category,Information,MessageTime)values
('Jess','Item','coffee Needed', curdate()),
('Laura','Item','Sugar',curdate());



