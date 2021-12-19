# MySQL-Front Dump 2.5
#
# Host: localhost   Database: loja
# --------------------------------------------------------
# Server version 3.23.54-nt


#
# Table structure for table 'produtos'
#

CREATE TABLE produtos (
  codigo int(3) unsigned NOT NULL auto_increment,
  descricao varchar(40) NOT NULL default '0',
  prateleira int(3) default '0',
  preco float default '0',
  fabricante varchar(25) default '0',
  PRIMARY KEY  (codigo)
) TYPE=MyISAM;



#
# Dumping data for table 'produtos'
#

INSERT INTO produtos VALUES("1", "Skol lata", "1", "12", "SKOL");
INSERT INTO produtos VALUES("2", "Brahma Lata", "2", "13", "BRAHMA");
INSERT INTO produtos VALUES("3", "Antartica Lata", "3", "11.5", "ANTARTICA");
INSERT INTO produtos VALUES("4", "Guaraná PET 2", "4", "8.8", "ANTARTICA");
INSERT INTO produtos VALUES("5", "Soda Limonada", "4", "4.5", "ANTARTICA");
INSERT INTO produtos VALUES("6", "Skol Beats", "1", "14.23", "SKOL");
INSERT INTO produtos VALUES("8", "Brahma Extra", "3", "12", "Brahma");
