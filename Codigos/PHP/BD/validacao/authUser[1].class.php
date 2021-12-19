<?php
/******************************************************************************************************
Classe:    		Authentix
Versуo:			1.02
Autor:     		Gustavo Sampaio Villa
E-mail:    		php@sitework.com.br
Site:      		www.sitework.com.br

Descriчуo: 		O grande objetivo dessa classe щ facilitar a validaчуo de usuсrios
		   		para acesso р сreas restritas.
		   		Nуo щ necessсrio criar tabelas especэficas no banco de dados,
		   		nem mesmo fazer alteraчѕes no Script.
		   
Fluxograma:		Para a utilizaчуo dessa classe щ necessсrio que vocъ utilize um
				formulсrio com dados a serem preenchidos na ordem:
				Identificaчуo do usuсrio, Senha e um botуo de submit que enviarс os dados a
				outra rotina chamada autenticaчуo. O script armazena as informaчѕes e verifica se o usuсrio
				existe. Retorna True caso o usuсrio tenha sido autenticado e false caso nуo.
				
Descriчуo: 		authUser($BDLogin, $BDSenha, $BDTabela, $LoginInformado, $senhaInformada);
				$BDLogin: Nome do campo do banco de dados que serс usado para identificar o usuсrio
				$BDSenha: Nome do campo do banco de dados que contщm a senha do usuсrio
				$BDTabela: Tabela do banco de dados onde esses campos estуo contidos
				$LoginInformado: String contendo o login informado pelo usuсrio
				$senhaInformada: String contendo a senha informada pelo usuсrio
				
Exemplo: 		$authUsr = new authUser("inst_Email", "inst_Senha", "tbl_Usuarios", $_POST["frmEmail"], $_POST["frmSenha"]);

******************************************************************************************************/

class authUser {
	
	var $bdUsr, $bdPwd, $bdTbl, $frmUsr, $frmPwd;


	/*
	authUser($bdUser, $bdPwd, $bdTbl, $frmUsr, $frmPwd)
	Mщtodo construtor que armazena as informaчѕes passadas
	*/
	function authUser($bdUser, $bdPwd, $bdTbl, $frmUsr, $frmPwd){
		$this->bdUsr  = $bdUser;
		$this->bdPwd  = $bdPwd;
		$this->bdTbl  = $bdTbl;
		$this->frmUsr = $frmUsr;
		$this->frmPwd = $frmPwd;
	}

	/*
	bool verifica()
	Mщtodo que faz a verificaчуo se o login informado e a senha existem no banco de dados.
	Caso afirmativo e se nunhuma sessуo ainda foi criada, ele autentica o usuсrio.
	Retorna True caso o usuсrio tenha sido aprovado ou False caso tenha sido rejeitado.
	*/
	function verifica(){
		session_start();
		
		if (!isset($HTTP_SESSION_VARS["usuario"])){
			$sql   = "select ". $this->bdUsr ." from ". $this->bdTbl ." where ". $this->bdUsr ."='". $this->frmUsr ."' and ". $this->bdPwd ."='". $this->frmPwd ."'";
			$query = mysql_query($sql);
			$usuario = mysql_fetch_row($query);
			$count = mysql_num_rows($query);
			
			if ($count == 1){
				session_register("usuario");
				return(true);
			} else {
				return(false);
			}
		} else {
			return(true);
		}
	}	
}
?>