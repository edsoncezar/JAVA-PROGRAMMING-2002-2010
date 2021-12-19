# MySQL-Front Dump 2.5
#
# Host: localhost   Database: controle
# --------------------------------------------------------
# Server version 3.23.54-nt


#
# Table structure for table 'usuarios'
#

CREATE TABLE usuarios (
  codigo int(3) unsigned NOT NULL auto_increment,
  nome varchar(15) default '0',
  senha varchar(10) default '0',
  PRIMARY KEY  (codigo)
) TYPE=MyISAM;



#
# Dumping data for table 'usuarios'
#

INSERT INTO usuarios VALUES("1", "rogerio", "123");
INSERT INTO usuarios VALUES("2", "lacerda", "456");
