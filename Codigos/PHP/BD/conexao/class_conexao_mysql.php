<?
/**
 * Tipo : classe
 * Nome : conexao_mysql
 * Data : 10/06/2003
 * Autor: Wonder Alexandre Luz Alves
 * Desc : Esta classes tem a maioria das funções mais utilizada do mysql
*/

class conexao_mysql{
	var $banco = ""; //nome do banco de dados
	var $servidor = "localhost"; //nome do servidor de banco de dados
	var $password = ""; //password do banco
	var $user = "root";	//usuario do banco
	var $link_id = ""; //link para resultado da consulta
	var $MYSQL_ERRNO = ""; //variavel para tratamento de erros, nº de error
	var $MYSQL_ERROR = ""; //variavel para tratamento de erros, nome do error
	var $rows="";
	
	/**
	 * @return void
	 * @desc Contrutor da class
	*/
	function  conexao_mysql(){
		$this->connect(); 
	}
	
	/**
	 * @return void 
	 * @desc retorna o id da conecao
	*/
	function connect(){ 	
	   $this->link_id = mysql_connect($this->servidor,$this->user,$this->password);
	   if(!$this->link_id) {
	      $this->MYSQL_ERRNO = 0;
	      $this->MYSQL_ERROR = "Conexão Falhou $this->servidor.";
	      $this->message_error();
	   }
	   else if(empty($this->banco) && !mysql_select_db($this->banco)) {
	      $this->MYSQL_ERRNO = mysql_errno();
	      $this->MYSQL_ERROR = mysql_error();
	      $this->message_error();
	   }
	   else if(!empty($this->banco) && !mysql_select_db($this->banco)) {
	      $this->MYSQL_ERRNO = mysql_errno();
	      $this->MYSQL_ERROR = mysql_error();
	      $this->message_error();
	   }
	}
	
	function result($link, $row, $mix){
		return mysql_result($link,$row,$mix);
	}
	
	/** 
	 * @return int
	 * @desc retorna true se a conecao foi fechada com sucesso
	 */
	function close(){
		return mysql_close($this->link_id);
	}
	
	/** 
	  * @return int
	  * @desc retorna o resultado da consulta 
	*/
	function query($query){
		if($result = mysql_query($query,$this->link_id)){
			$this->rows = @mysql_num_rows($result);
			return $result;
		}
		else 	
			die($query);//$this->sql_error();
		
	}
	/**
	 * @return int
	 * @param result point
	 * @desc retorna o id do ultimo insert
	 */
	function Id(){
		return  mysql_insert_id();
	}	
	
	/** 
	  * @return void
	  * @param url string
	  * @desc mostra um error na sql atraves de uma popup e direciona p/ url desejada ou volta se nao especificada
	 */
	 function message_error($url='')
	 {
	 	echo "<script>alert('Erro: ". $this->sql_error() ."');</script>";
	 	if($url) 
	 		echo "<script>location.self='$url';";
	 	else 
	 		echo "<script>history.back();</script>";
	 }	
	 
	/** 
	  * @return string
	  * @desc retorna o numero e o tipo de error da consulta anterior
	 */
	 function sql_error() {
	   if(empty($this->MYSQL_ERROR)) {
	      $this->MYSQL_ERRNO = mysql_errno();
	      $this->MYSQL_ERROR = mysql_error();
	   }
	   echo "<script>alert('Error $this->MYSQL_ERRNO: $this->MYSQL_ERROR');</script>";
	   exit;
	}

}

?>
