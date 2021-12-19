<html> 
<title>Postagem</title> 
<body> 

<? /* Este script posta todos os dados de um arquivo dbf para uma base de 
dados em mysql */ 

$con = mysql_connect("127.0.0.1\", \"ODBC\",\"\") or die (\"Não foi possível conectar ao banco de dados\"); 

$db = mysql_select_db (\"postar\",$con) or die (\"Não foi possível selecionar o banco de dados\"); 

$dbcon = dbase_open(\'c:\\apache\\Dbf_mysql\\Dados.dbf\',0) or die (\"Não foi possível abrir o arquivo dados.dbf\"); 

$dbrows = dbase_numrecords($dbcon); // as linhas (registros) da tabela variam de 1 até x 
for ($c = 1; $c <= $dbrows; $c++) { 
   $dbreg = dbase_get_record($dbcon,$c); // pega o contéudo da linha (registro) 
   $campo1 = $dbreg[0]; // os campos da tabela variam de 0 a y-1 
   $campo2 = $dbreg[1]; 
   $sql = \"INSERT INTO base (cod, nome) VALUES(\'$campo1\',\'$campo2\')\"; 
   $x = mysql_query ($sql, $con) or die (\"O registro não pode ser inserido no banco de dados dbexemplo<br>\"); 

} 
echo \"<p><b>Importação finalizada!</b></p>\"; 
mysql_close ($con); 
/* FACICOMP - Faculdade de Ciência da Computação de Caratinga 
   Bruno Rodrigues Silva - 16/12/2000 
  */ 
?> 
</body> 
</html> 