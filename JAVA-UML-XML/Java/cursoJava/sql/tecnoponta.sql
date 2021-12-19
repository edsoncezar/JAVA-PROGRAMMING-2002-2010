# MySQL-Front Dump 2.5
#
# Host: localhost   Database: tecnoponta
# --------------------------------------------------------
# Server version 3.23.54-nt


#
# Table structure for table 'usuarios'
#

CREATE TABLE usuarios (
  codigo tinyint(3) unsigned NOT NULL auto_increment,
  nome varchar(20) default '0',
  senha varchar(15) default '0',
  PRIMARY KEY  (codigo)
) TYPE=MyISAM;



#
# Dumping data for table 'usuarios'
#

INSERT INTO usuarios VALUES("1", "ivan", "111111");
INSERT INTO usuarios VALUES("2", "jr", "222222");
INSERT INTO usuarios VALUES("3", "loco", "333333");
INSERT INTO usuarios VALUES("4", "jaqueline", "444444");
INSERT INTO usuarios VALUES("5", "beth", "555555");
INSERT INTO usuarios VALUES("6", "andreia", "777777");
