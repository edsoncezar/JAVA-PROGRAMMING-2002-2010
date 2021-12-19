<?php
/**********************************************************
* BD Cunecta
* GPL(GNU General Public License)
* Leandro Soares
*
* V 1.4
* Conecta-se com o banco de dados e executa uma query
* retornando a resposta do banco, exibe erros e os
* envia por e-mail.
*
* ex.
* $conexao = new Bd("banco","usuario","senha");
* $res = $conexao->consultar("SELECT * from tabela",true);
*
* while($data = @mysql_fetch_array($res))
* {
*    echo $data['id']." - ".$data['campo']."<br />";
* }
***********************************************************/

class Bd{

    var $host        = "localhost";
    var $usuario;
    var $senha;
    var $banco;
    var $conexao;
    var $info        = array();
    var $responsavel = "webmaster@host.dom";

    function Bd($banco, $usuario, $senha){

        $this->usuario = $usuario;
        $this->senha   = $senha;
        $this->banco   = $banco;
    }

    function conectar(){

        $this->conexao = mysql_connect($this->host, $this->usuario, $this->senha);
        mysql_select_db($this->banco, $this->conexao);
    }

    function consultar($sql, $erro, $log=false){

        $con = new Bd($this->banco, $this->usuario, $this->senha);
        $con->conectar();

        $resultado = @mysql_query($sql,$con->conexao);

        if(!$resultado){

            echo "<p>Ocorreu um erro ao processar sua requisi&ccedil;&atilde;o, ";
            echo "um e-mail avisando os respons&aacute;veis j&aacute; foi enviado.<br />";
            echo "Tente novamente mais tarde.</p>";

            $bug = "=================================================================\r\n";
            $bug.= "Erro em \r\n".$_SERVER['SERVER_NAME'].$_SERVER['SCRIPT_NAME']."\r\n";
            $bug.= "   |\r\n";
            $bug.= "   +---->Mysql: (".mysql_errno().")".mysql_error()."\r\n";
            $bug.= "      |\r\n";
            $bug.= "      +---->Consulta: ".$sql."\r\n";
            $bug.= "=================================================================\r\n";

            @mail($this->responsavel,"Aviso de Erro",$bug);

            if($erro){

                echo "<p /><blink style=\"color:red;\"><b>ERRO</b></blink>";
                echo " <acronym title=\"".mysql_error()."\" style=\"border-bottom: none;cursor: help;\">";
                echo "(".mysql_errno().")</acronym> <b>".$sql."</b><p />";
            }

            mysql_close();
            return false;
        }
        else{
            if($log==true){
                $abre = @fopen("querylog.log","a");
                $linha = date("Y-m-d h:i")."\t".$sql."\t".$this->usuario."\t".$_SERVER['REMOTE_ADDR'];
                $linha.= "\t".$_SERVER['REMOTE_HOST']."\r\n";

                if($abre){
                    fwrite($abre,$linha);
                    fclose($abre);
                }
            }
        
            $this->info['id']        = mysql_insert_id();
            $this->info['alteradas'] = mysql_affected_rows();

            mysql_close();
            return $resultado;
        }
    }
}
?>
