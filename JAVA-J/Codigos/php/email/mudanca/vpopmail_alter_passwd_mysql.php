<?
/*
Formulario de mudanca de senha para Solucao vpopmail + mysql + qmail
VPOPMAIL ALTER PASSWD Versao 0.1
Desenvolvido por Patrick Brandao < tol83@bol.com.br >

Esse codigo e livre desde que seja copiado ou distribuido mantendo o nome
do author, e-mail, agradecimentos e esse aviso! Quaisquer alteracao para
distribuicao devera ser reportada ao autor por e-mail

Agradecimentos a :
    Jesus Cristo
	Digno e's de tomar o livro, e de abrir os seus selos; porque foste morto,
	e com o teu sangue compraste para Deus homens de toda a tribo, e lingua,
	e povo, e nacao. E para o nosso Deus os fizeste reis e sacerdotes; e eles
	reinarao sobre a terra.
							    Apocalipse 5:9
    .
*/

// parametros de configuracao
	$db_user = "root";
	$db_pass = "Senha_dificil_de_quebrar!-Vc_nao_acha?:-)";
	$db_host = "localhost";
	$db_mail = "vpopmail";
	$db_mail_table = "vpopmail";
	
// arquivo de log
	$arquivo_log = "/var/log/vpopmail_alter_passwd.log";
	// # touch /var/log/vpopmail_alter_passwd.log
	// # chown userapache.groupapache vpopmail_alter_passwd.log
	// # chmod 700 vpopmail_alter_passwd.log
// ----------------
// ---------------------------

// funcoes
	// grupos de caracters
	$c_tipo_email = "zaqwsxcderfvbgtyhnmjuikolp1234567890-_.@";
	$c_tipo_login = "zaqwsxcderfvbgtyhnmjuikolp1234567890-_.";
	$c_tipo_sql_invalido = "%=<>!'";
	
	function limpar($str, $permitido) {
		$retorno = "";
		$str = "¨" . $str;
		$permitido = "¨" . $permitido;
		for ($i = 1; $i < strlen($str); $i++) {
			$c = substr($str,$i,1); // instr
			$r = strrpos($permitido,$c); // mid
			if($r != 0){
				$retorno .= $c;
			}
		}
		return($retorno);
	}

	function retirar($str, $proibido) {
		$retorno = "";
		$str = "¨" . $str;
		$proibido = "¨" . $proibido;
		for ($i = 1; $i < strlen($str); $i++) {
			$c = substr($str,$i,1); // instr
			$r = strrpos($proibido,$c); // mid
			if($r == 0){
				$retorno .= $c;
			}
		}
		return($retorno);
	}

    function perl_crypt($p_password, $p_salt) {
		$n_salt = str_replace('$', '\$', $p_salt);
		$c_cmd = "perl -e 'use Digest::MD5; printf crypt(\"$p_password\", \"$n_salt\");'";
		$r_cmd = shell_exec($c_cmd);
		$r_cmd = trim($r_cmd);
		return($r_cmd);
    }
// ---------------------------

$erro = "";
$sucesso = "";
$log = "";
// r = ok -> dados submetidos, critica-los
if ($r == "ok"){
	// limpar dados passados de caracter invalidos
	$cmail = limpar(strtolower(trim($mail)), $c_tipo_email);
	$csenha = retirar(strtolower(trim($senha)), $c_tipo_sql_invalido);
	$cnovasenha = retirar(strtolower(trim($novasenha)), $c_tipo_sql_invalido);
	$clembrete = substr(limpar(strtolower(trim($lembrete)), $c_tipo_caracter),0,255);
	// --------------------

	// separar login e dominio
	$cdomain = "";
	$maillogin = "";
	$apos = strpos(" " . $cmail, "@") + 0;
	if ($apos > 1) {
		$sdomain = substr($cmail, $apos);
		$ppos = strpos(" " . $sdomain, ".") + 0;
		if($ppos > 1){
			$cdomain = $sdomain;
			$maillogin = substr($cmail, 0, $apos - 1);
		}
	}
	//----------------------	

	// verificar se os dados passaram pela critica
	if($cmail == "" || $csenha == ""){
		$erro = "Informe o e-mail e a senha atual.";
	}elseif ($cmail != $mail || $cdomain == "" || $maillogin == ""){
		$erro = "Verifique se o campo 'E-mail' foi digitado corretamente. Esse campo suporta apenas caracters de a-z, 0-9, [-_.@] sem espaços. Ex.: contabil_test@goldenlink.com.br";
	}elseif ($csenha != $senha){
		$erro = "Verifique se o campo 'Senha atual' foi digitado corretamente.  Alguns caracters não são permitidos: [ ' \" = % & < > ! ].";
	}elseif ($novasenha != $confirme){
		$erro = "Os campos 'Nova senha' e 'Confirme' não conferem";
	}elseif ($cnovasenha == ""){
		$erro = "Os campos 'Nova senha' e 'Confirme' precisam ser preenchidos";
	}elseif ($cnovasenha != $novasenha){
		$erro = "Os campos 'Nova senha' e 'Confirme' contém caracters proibidos: [ ' \" = % & < > ! ]";
	}elseif ($novasenha == $senha) {
		$erro = "Os campos 'Senha atual' e 'Nova senha' são iguais. Para alterar a senha atual, informe uma nova senha.";
	}
	// ------------------

	// verificar no banco de dados se nao houver nenhum erro ate aqui
	if ($erro == ""){
		// verificar no db
		$link = mysql_pconnect($db_host, $db_user, $db_pass);
		if ($link){
			$db = mysql_select_db($db_mail, $link);
			if ($db){
				$sql = "SELECT pw_name, pw_passwd, pw_domain FROM $db_mail_table WHERE pw_name = '$maillogin' AND pw_domain = '$cdomain' LIMIT 1;";
				$resultado = mysql_query($sql);
				if ($resultado){
					$linha = mysql_fetch_array($resultado);
					// recupera dados do db para comparaçao manual
					$user = $linha[0];
					$pass = $linha[1];
					$dn   = $linha[2];
					// verificacao manual dos dados do db
					// prevençao contra SQL INJECTION, mantenha esse codigo caso
					// retire a critica acima
					if ($user == $maillogin && $user != ""){
						// retirar salt de criptografia e
						// criptografar senha atual e nova senha com ela
						$salt = substr($pass, 0, 9);
						$senhacry = perl_crypt($csenha, $salt); 
						$novasenhacry = perl_crypt($cnovasenha, $salt);
						if ($pass == $senhacry){
							// a senha do banco de dados bate com a senha fornecida, alterar
							$sql = "UPDATE $db_mail_table SET pw_passwd = '$novasenhacry' WHERE pw_name = '$user' AND pw_domain = '$dn' LIMIT 1;";
							$resultado = mysql_query($sql);
							if($resultado){
								$sucesso = "ok";
								// logar mudança de senha
								$datahora = date("d/m/y H:i:s");
								$log = "$datahora : $REMOTE_ADDR : alter_passwd : $user : $csenha -> $cnovasenha\n";
								$link_log = fopen($arquivo_log, "a");
								fwrite($link_log, $log);
								fclose($link_log);
								// -----------------
							}else{
								// nao foi possivel atualiza registro
								$erro = "Erro no servidor. nº 4";
							}

						}else{
							// a senha informada é incorreta, avisar ao usuario
							$erro = "Não foi possível alterar a senha, certifique-se de que o e-mail e a senha atual foram digitados corretamente.";
						}
					}else{
						// e-mail nao encontrado no banco de dados
						$erro = "Não foi possível alterar a senha, certifique-se de que o e-mail e a senha atual foram digitados corretamente.";
					}
				}else{
					// nao foi possivel executar SQL
					$erro = "Erro no servidor. nº 3";
				}
			}else{
				// nao foi possivel abrir db
				$erro = "Erro no servidor. nº 2";
			}
		}else{
			// nao foi possivel conectar-se ao MySQL
			$erro = "Erro no servidor. nº 1";
		}
	}	
	// ---------------
}
?>
<!-- dados para debug -- remova -->
	<pre>DEBUG
	Mail   : <? echo $mail; ?> 
	Senha  : <? echo $senha; ?> 
	Nova   : <? echo $novasenha; ?> 
	confi  : <? echo $confirme; ?> 
	lembr  : <? echo $lembrete; ?> 
	cMail  : <? echo $cmail; ?> 
	cDN    : <? echo $cdomain; ?> 
	cSenha : <? echo $csenha; ?> 
	cNova  : <? echo $cnovasenha; ?> 
	clembr : <? echo $clembrete; ?> 
	erro   : <? echo $erro; ?> 
	msg    : <?
		if ($sucesso == "ok"){
			$msg .= "<b>Senha alterada com sucesso</b>";
		}
		echo $msg;
	?></pre>
<!-- fim debug -->
<form action="index.php" method="post">
<pre>	e-mail:        <input type="text" name="mail" value="<? echo $mail; ?>">
	senha:         <input type="text" name="senha" value="<? echo $senha; ?>">
	nova senha:    <input type="text" name="novasenha" value="<? echo $novasenha; ?>">
	confirme:      <input type="text" name="confirme" value="<? echo $confirme; ?>">
	novo lembrete: <input type="text" name="lembrete" value="<? echo $lembrete; ?>">
	<input type="hidden" value="ok" name="r">
	<input type="submit" value="enviar">
</pre>
</form>
