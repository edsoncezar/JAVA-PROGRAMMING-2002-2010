<?
/*
   Remove as duplicidades de um banco de dados MySQL!
   Nesse caso eu usei o campo EMAIL pra identificar as duplicidades,
   vocъ pode adaptar esse cѓdigo pras suas necessidades.

   Щ importante vocъ fazer um backup dos seus dados antes! :P.
*/

// Configuraчѕes para conexуo com Banco de Dados
$host = "seu_host_aqui";
$user = "usuсrio";
$pass = "senha";
$base = "base dos dados";
$tabela = "tabela";


// Conecta ao DB
$mysql = mysql_connect($host, $user, $pass) or die("Erro ao conectar ao banco de dados!");


// Seleciona base de dados
$mydb = mysql_select_db($base, $mysql) or die ("Erro ao acessar o banco de dados!");


// Encontrando duplicidades
$query = "SELECT DISTINCT 'email' FROM $tabela"; # Atenчуo ao campo email aqui!
$dados = mysql_query($query) or die("Erro QUERY SQL 1");

while ($campo = mysql_fetch_array($dados)) {

	$quant = mysql_query("SELECT * FROM $tabela where email='". $campo['email'] ."'") or die ("Erro QUERY SQL 2"); # Atenчуo ao campo email novamente!
	$num = mysql_num_rows($quant);

	if ($num > "1") {

		$del = mysql_query("DELETE FROM $tabela WHERE email='". $campo['email'] ."'"); #Campo email novamente! :)
		$add = mysql_query("INSERT INTO $tabela VALUES ('". $campo['email'] ."')");
		$cont++;

	}

}

print "Foram encontradas $cont duplicidades!\n";

?>