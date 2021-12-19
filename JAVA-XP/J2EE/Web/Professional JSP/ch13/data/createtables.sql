create table county (
countynumber integer not null auto_increment,
countyname varchar(32),
state varchar(32),
primary key(countynumber));

create table candidate (
candidatenumber integer not null auto_increment,
firstname varchar(32) not null,
lastname varchar(32) not null,
politicalparty varchar(32) not null,
primary key(candidatenumber));

create table voterregistration (
ssn integer not null,
firstname varchar(32) not null,
lastname varchar(32) not null,
countynumber integer not null,
primary key(SSN),
foreign key(countynumber) references county);


create table votes (
votenumber integer not null auto_increment,
candidatenumber integer,
countynumber integer,
primary key(votenumber),
foreign key(candidatenumber) references candidate,
foreign key(countynumber) references county);
