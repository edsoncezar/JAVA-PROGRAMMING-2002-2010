<?
/*
* Este programa é de livre utilização e distribuição.
* Se você realizar alguma modificação interessante,
* deixe-me saber!
*
* JAMAIS remova os créditos do Autor,
* nem de pessoas que modificaram este script
*/

/*
* MS-Sql Query Executor Versão 0.1
* Desenvolvido por
* Auro Florentino <auro@merco.net>
* Release 0.2 - 13 de Fevereiro de 2003
* São Paulo, Brasil
*/

/*
* MS-Sql Query Executor
* Longe de ser um substituto ao Enterprise Manager da M$,
* o MS-Sql Query Executor é um script bastante simples,
* que pode ser deixado junto ao servidor WEB,
* e permite que você realize as mais diversas consultas
* SQL ao servidor da M$, via PHP
*/

/*
* Para utilizá-lo, certifique-se de:
* Alterar a senha de administrador para realizar o seu login
* Preencher pelo menos 1 array com os dados de seu banco de dados
* Você pode cadastrar e utilizar quantos bancos de dados quiser
* Se quiser, pode alterar o tempo de expiração da sessão entre consultas.
*/

$admin_pass = "admin"; // Senha do Usuário

$sessao_expira = "3600"; // Tempo de expiração de sessão... Default de 1H (3600 segundos)

/*

$database[0] = array(
	0 => "192.168.0.1",//Host
	1 => "sa",//User
	2 => "",//Pass
	3 => "DataBase",//DataBase
);

$database[1] = array(
	0 => "192.168.0.2",//Host
	1 => "sa",//User
	2 => "",//Pass
	3 => "DataBase2",//DataBase
);

*/


// Não mexa daqui pra frente, a menos que VSOQEF!!!
// E se for mexer, me manda um e-mail....
// A comunidade agradece!!!

function form_login($erro="") {
	$FORM = "<form method=\"post\">
	<input name=\"passwd\" type=\"password\">
	<input type=\"submit\" value=\"Login\">
	</form>" . $erro;
	echo $FORM;

}

IF ((!($admin_pass)) || ($admin_pass == "admin")) {
	echo "ERRO: Não esqueça de alterar a senha de administrador!!!";
	exit;
}

IF (!(is_array($database))) {
	echo "ERRO: Nenhuma base de dados foi cadastrada!!!";
	exit;
}

session_start();

$hora_atual = time();
$hora_expira = $hora_atual - $sessao_expira;

IF (($_SESSION['timestamp']) < $hora_expira ) {
	$_SESSION['logado'] = false;
	$erro_expira = "Sua Sessão Expirou!!!";
}

$_SESSION['timestamp'] = time();

IF ($_POST['passwd']) {
	IF ($_POST['passwd'] == $admin_pass) {
		$_SESSION['logado'] = 1;
	} ELSE {
		$_SESSION['logado'] = false;
		$erro = "Senha Incorreta!!!";
		form_login($erro);
		exit;
	}
}

IF (!($_SESSION['logado'])) {
	form_login($erro_expira);
	exit;
}

function conecta($base="0") {
	global $database;
	$conecta = @mssql_connect($database[$base][0],$database[$base][1],$database[$base][2]);
	$seleciona = @mssql_select_db($database[$base][3]);
	return $conecta;
}

function fecha($links=array()) {
	$tamanho = sizeof($links);
		FOR ($i=0; $i < $tamanho; $i++) {
			@mssql_close($links[$i]);
		}
	return true;
}

function rows_affected($link){
	$result = @mssql_query("SELECT @@ROWCOUNT",$link);
	list($affected) = @mssql_fetch_row($result);
	return $affected;
}

IF (($_POST['query']) && ($_POST['query'] != "")) {

	$_POST['query'] = stripslashes($_POST['query']);
	$link[0] = conecta($_POST['database']);
	$result = mssql_query($_POST['query'],$link[0]);
	$foi = @rows_affected($link[0]);

		IF ($foi != 0) {
			$RETORNO = $foi . " registro(s) afetado(s).";
		} ELSE {
			$RETORNO = "Nenhum resultado encontrado";
		}

		while ($array_completa = @mssql_fetch_array($result)) {
			$i = 1;

			while(list($campo,$valor) = each($array_completa)) {

					IF (($i % 2) == 0) {
						$nova[$campo] = $valor;
					}

				$i++;

			}

			$array[] = $nova;
			$nova = array();

		}

		FOR ($i=0; $i<sizeof($array); $i++) {

			$TD = "";

				while(list($campo,$valor) = each($array[$i])) {

					IF (empty($valor)) {
						$valor = "&nbsp;";
					}

					IF ($i==0) {
						$TH .= "<TH CLASS=\"query\">" . $campo . "</TH>\n";
					}

					$TD .= "<TD CLASS=\"query\">" . $valor . "</TD>\n";

				}

			$TDS .= "<TR CLASS=\"query\">\n" . $TD . "</TR>\n";

		}

		IF (is_array($array)) {

			$RETORNO = "<TABLE CLASS=\"query\" BORDER=\"0\" CELLSPACING=\"1\" CELLPADDING=\"3\" WIDTH=\"95%\">\n";
			$RETORNO .= "<TR CLASS=\"query\">\n" . $TH . "</TR>\n";
			$RETORNO .= $TDS;
			$RETORNO .= "</TABLE>";

		}

}

$TEXTAREA_QUERY = htmlentities($_POST['query']);

IF (!($TEXTAREA_QUERY)) {

$TEXTAREA_QUERY = "/*
sp_help
*/";

}

$SELECT_DATABASE = "<select name=\"database\" CLASS=\"query\">\n";

	FOR ($i=0; $i<(sizeof($database)); $i++) {

		IF (($_POST['database']) == $i) {
			$database_selected = " SELECTED";
		} ELSE {
			$database_selected = "";
		}

		$SELECT_DATABASE .= "<option value=\"" . $i . "\"" . $database_selected . ">" . strtoupper($database[$i][3]) . "</option>\n";

	}

$SELECT_DATABASE .= "</select>\n";

fecha($link);

$PROJECT = "MS-Sql Query Executor - versão 0.2";
$LICENSE = "<a class='query' href='mailto:auro@merco.net?subject=" . $PROJECT . "'>&copy; 2003 - Auro Florentino</a>";

?><html>
<head>
<title><?=$PROJECT;?></title>
<meta http-equiv="expires" content="Thu, 6 Jan 2000 12:00:00 GMT">
<meta http-equiv="pragma" content="no-cache">
<style type="text/css">
h1.query { font-family: Verdana,sans-serif; font-size: 18pt; color: #000000; }
input.query { font-family: Verdana,sans-serif; font-size: 14pt; background-color: #9999CC; color: #000000; }
select.query { font-family: Verdana,sans-serif; font-size: 14pt; background-color: #9999CC; color: #000000; }
textarea.query { font-family: Verdana,sans-serif; font-size: 14pt; background-color: #9999CC; color: #000000; }
table.query, tr.query { background-color: #000000; }
th.query { font-family: Verdana,sans-serif; font-size: 12pt; background-color: #9999CC; color: #000000; }
td.query { font-family: Verdana,sans-serif; font-size: 10pt; background-color: #CCCCFF; color: #000000; }
a.query, a:link.query, a:visited.query { font-family: Verdana,sans-serif; font-size: 12pt; color: #000000; text-decoration: none; }
a:hover.query { font-family: Verdana,sans-serif; font-size: 12pt; color: #000000; text-decoration: none; font-weight: bold; }
</style>
</head>
<body topmargin="0" leftmargin="0" marginwidth="0" marginheight="0" bgcolor="#C0C0C0">
<form method="post">
<div align="center"><center>
<H1 CLASS="query"><?=$PROJECT;?></H1>
<textarea class="query" name="query" rows="10" cols="80">
<?=$TEXTAREA_QUERY;?>
</textarea>
<BR>
<BR>
<?=$SELECT_DATABASE;?>
&nbsp;&nbsp;&nbsp;
<input class="query" type="submit" value="Executar Pesquisa">
<BR>
<BR>
<?=$RETORNO;?>
<BR>
<BR>
<HR>
<?=$LICENSE;?>
</center></div>
</form>
</body>
</html>
