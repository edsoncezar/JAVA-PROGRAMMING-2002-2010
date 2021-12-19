# MySQL-Front Dump 2.5
#
# Host: localhost   Database: noticias
# --------------------------------------------------------
# Server version 3.23.54-nt


#
# Table structure for table 'artigos'
#

CREATE TABLE artigos (
  codigo int(3) unsigned NOT NULL auto_increment,
  assunto varchar(50) default '0',
  conteudo varchar(200) default '0',
  prioridade int(10) unsigned default NULL,
  PRIMARY KEY  (codigo)
) TYPE=MyISAM;



#
# Dumping data for table 'artigos'
#

INSERT INTO artigos VALUES("1", "Flagrante de extorsão", "Auditora do INSS tenta obter R$ 1,5 mi e é presa", "1");
INSERT INTO artigos VALUES("2", "Rio de Janeiro", "Rosinha vai insistir na ocupação de morros", "2");
INSERT INTO artigos VALUES("3", "UOL Mídia Global", "EUA impedem investigação da ONU no Iraque, sem dicas", "8");
INSERT INTO artigos VALUES("4", "Folha Online", "Tubarão ataca adolescente na praia de Copacabana", "7");
INSERT INTO artigos VALUES("5", "Argentina", "Disputa eleitoral vai ter fim na Justiça", "5");
INSERT INTO artigos VALUES("6", "Eliana", "Programe o fim de semana de toda a família e filhos com dicas da apresentadora", "3");
INSERT INTO artigos VALUES("7", "Caras", "Feliz com novo amor, Adriane Galisteu planeja ter filhos", "6");
INSERT INTO artigos VALUES("8", "Folha de S.Paulo", "Lula tenta enquadrar PT para garantir reforma", "4");
INSERT INTO artigos VALUES("9", "Conteudo", "O fim do dia", "3");
