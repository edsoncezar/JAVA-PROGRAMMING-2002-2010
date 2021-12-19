<?php 
$dbname = "nomeBD"; 
mysql_connect("localhost","user","senha") or die ("Conexão não estabelecida");
mysql_select_db($dbname) or die(mysql_error()); 

// Pega a lista de todas as tabelas 
$res = mysql_list_tables($dbname) or die(mysql_error()); 
while ($row = mysql_fetch_row($res))
{
	$table = $row[0];
	mysql_query("DROP TABLE $table"); 
}
?> 


