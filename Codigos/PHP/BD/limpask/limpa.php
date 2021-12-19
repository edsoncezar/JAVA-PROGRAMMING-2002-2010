<?php
// BY SK15
// E-mail: sk15@msn.com
// Site: http://www.BuscaWeb.clic3.net
////////// Começo Config do MYSQL /////////
// Host do Mysql
$host="localhost";
// Usuario do Mysql
$user="root";
// Senha do Mysql
$pass="";
// Banco de Dados
$bd="";
////////// Final Config do MYSQL /////////
#######################################################################################################################################################
// Conecta com o Mysql
mysql_connect("$host","$user","$pass") or die (mysql_error());
// Seleciona o Banco de Dados
mysql_select_db("$bd");
// Mostra a Parte de Cima do HTML
echo "<html>\n<head>\n<title>Limpado o Banco de Dados</title>\n</head>\n<body bgcolor=\"#FFFFFF\">\n\n<p align=\"center\">Limpado o Banco de Dados...";
// Seleciona toda as tabelas
$resultado=mysql_query("SHOW tables");
// Listas as tabelas
while($tabelas=mysql_fetch_array($resultado)){ # Abre 1
// Apaga as tabelas exitentes
mysql_query("DROP TABLE IF EXISTS $tabelas[0]");
} # Fecha 1
// Mostra a Parte de Baixo do HTML
echo " com sucesso !!!</p>\n<br><br>\n<center><h1>...::: BY SK15 ® :::...</h1></center>\n\n</body>\n</html>";
#######################################################################################################################################################
?>