<?

    ##########################################
    #      //EXIBE REGISTRO RANDOMICO  \\    #
    #      //      MySQL               \\    #
    ##########################################
    # Código Gerado por Tadeu Pires Pasetto  #
    ##########################################
    #         t.pasetto@bol.com.br           #
    ##########################################
    #Código Livre desde que os Créditos sejam#
    #   mantidos. Também podem ser feitas    #
    #alterações para  melhor funcionabildade #
    ##########################################
/*
Exibe um registro aleatório de uma tabela no banco de dados
MySQL, utilizando o LIMIT
*/

##################################################
/*VARIÁVEIS PARA CONFIGURAÇÃO*/
$user = ""; // usuario
$pass = "";//senha
$bd = ""; // Banco de Dados
$tabela = ""; // tabela de origem dos dados
$cond = ""; // o WHERE, caso queira exibir apenas um tipo de registro entre com a condição
//$cond = " WHERE data=".date("Y-m-d"); // iniciando com espaço " "
##################################################
$con = mysql_connect("localhost", "$user", "$pass");
$query = "SELECT * FROM ".$tabela;
$query .= $cond;
$resultado = mysql_db_query("$bd",$query,$con);
$num = mysql_num_rows($resultado);
$num --;
$reg = rand(0,$num);

$query .= " LIMIT ".$reg.",1";
$resultado = mysql_db_query("$bd",$query,$con);
$arr = mysql_fetch_array($resultado);

// Agora com o array "$arr" você exibe o rsultado como quiser
echo($arr['msg']); // imprime apenas o campo msg

?>

