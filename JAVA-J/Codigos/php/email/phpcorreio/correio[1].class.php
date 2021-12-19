<?php
/***********************************************************************************************
* PHPCorreio
* GPL(GNU General Public License)
* Leandro Soares
*
* V 2.0.4
* Classe para enviar e-mail c/ anexos
* ex.:
* $carta = new Correio("eu@correio.com.br","ela@correio.com.br","Bom dia","Como vc esta?");
* $carta->adAnexos("/home/eu/wallpaper.jpg","image/png","wallpaper.png");
* $carta->adAnexos("/home/eu/suicide_solution.mp3","audio/mpeg","suicide_solution.mp3");
* $carta->enviar();
*
**********************************************************************************************/

class Correio
{
    var $de;                       //Remetente (String)
    var $para;                     //Destino (1 destino -> String, Multiplos -> Array)
    var $assunto;                  //Assunto (String)
    var $resposta;                 //E-mail p/ resposta (Default Remetente)
    var $msg;                      //Mensagem do E-mail (String)
    var $relatorio    = false;     //Imprime uma copia do E-mail enviado (Boolean)
    var $confirmacao  = false;     //Exige confirmacao de recebimento (Boolean)
    var $prioridade   = 2;         //prioridade do e-mail (Integer) 1-baixa, 2-normal, 3-urgente
    var $arquivo      = array();   //Caminho do Arquivo anexo (String)
    var $arquivo_nome = array();   //Nome do arquivo (String)
    var $arquivo_tipo = array();   //Tipo do arquivo aka Mime-Type ex. "image/png"  (String)

    function correio($de,$para,$assunto,$mensagem) //Construtor | void correio(String, String ou Array, String, String)
    {
        $this->de       = $de;
        $this->para     = is_array($para) ? implode(",",$para) : $para; //cria string c/ remetentes separados por virgula
        $this->assunto  = $assunto;
        $this->msg      = $mensagem;
        $this->resposta = $de;
    }

    function adAnexos($arquivo,$arquivo_tipo,$arquivo_nome) //Adiciona anexos | boolean adAnexos(String, String, String)
    {
        $n = count($this->arquivo);   //verifica a qunatidade de anexos
        $abre = @fopen($arquivo,"r"); //Abre arquivo retorna falso caso contrario

        if($abre)
        {
            $le = fread($abre,filesize($arquivo));    //Le o arquivo
            $anexo = chunk_split(base64_encode($le)); //Codifica o arquivo (base64) formatando-o conforme o padrao de e-mail

            $this->arquivo[$n] = $anexo;
            $this->arquivo_nome[$n] = $arquivo_nome;
            $this->arquivo_tipo[$n] = $arquivo_tipo;

            fclose($abre);
            return true;
        }
        else
            return false;
    }

    function replyTo($resposta)        //Modifica o endereco p/ resposta | void replyTo(String)
    {
        $this->resposta = $resposta;
    }

    function relatorio()     //Imprime uma copia do E-mail | void relatorio()
    {
        $this->relatorio = $this->relatorio==false ? true : false; //padrao e false (desligado)
    }
    function prioridade($nivel)
    {
        if(is_int($nivel) && $nivel>0 && $nivel<4)
        {
            $this->prioridade = $nivel;
            return true;
        }
        else
            return false;
    }

    function confirmacao()   //Exige uma confirmacao de recebimento | void confirmacao()
    {
        $this->confirmacao = $this->confirmacao==false ? true : false;
    }
    function enviar()                //Envia o e-mail | boolean enviar()
    {
        $cabecalho = "From: ".$this->de."\r\n";               //Cabecalho do E-mail
        $cabecalho.= "Reply-to: ".$this->resposta."\r\n";     //Endereco p/ resposta
        $cabecalho.= "MIME-Version: 1.0\r\n";
        $cabecalho.= "Date: ".date("D, d M Y H:i:s O")."\r\n";             //Data do envio
        $cabecalho.= "X-Priority: ".$this->prioridade."\r\n"; //Prioridade da mensagem
        $cabecalho.= "X-MSMail-Priority: ";                   //Prioridade p/ clientes Microsoft
        $cabecalho.= ($this->prioridade==1 ? "Low" : ($this->prioridade==2 ? "Normal" : "High"))."\r\n";
        
        if($this->confirmacao==true)
            $cabecalho.= "X-Confirm-Reading-To: ".$this->resposta."\r\n";
            
        $cabecalho.= "X-Mailer: PHPCorreio 2.0.4\r\n";
        
        $chave     = md5(uniqid(time()));       //chave p/ identificar as diferentes partes como anexos e textos

        if(count($this->arquivo)>0)             //verifica se ha anexos
        {
            $cabecalho .= "Content-type: multipart/mixed;boundary=\"$chave\"\r\n"; //cabecalho da parte de anexos
            $cabecalho .= "\r\n";
            $cabecalho .= "Esta mensagem nao e suportada pelo seu servidor de e-mails contate: ".$this->resposta;
            $cabecalho .= "\r\n\r\n";

            $anexos .= "--$chave\n";
            for($n=0; $n<count($this->arquivo); $n++)
            {
                $anexos .= "--$chave\n";
                $anexos .= "Content-type: ".$this->arquivo_tipo[$n]."; name=".$this->arquivo_nome[$n];
                $anexos .= "\r\n";
                $anexos .= "Content-transfer-encoding:base64\r\n\r\n"; //especifica o tipo da codificacao dos anexos (base64)
                $anexos .= $this->arquivo[$n]."\r\n\r\n";              //insere o anexo

                if($n+1==count($this->arquivo))
                    $anexos.= "--$chave--";
                else
                    $anexos.= "--$chave\n";
            }
            $corpo .= "--$chave\n";
        }

        if(htmlentities($this->msg)==$this->msg) //Especifica o tipo da mensagem, html ou texto simples
            $corpo .= "Content-type: text/plain;\r\n";
        else
            $corpo .= "Content-type: text/html;\r\n";

        $corpo .= "Content-transfer-encoding: 8bit\r\n\r\n"; //tipo da codificacao da mensagem (8bit)
        $corpo .= $this->msg."\r\n\r\n";

        $enviar = mail($this->para,$this->assunto,$corpo.$anexos,$cabecalho); //envia o e-mail

        if($this->relatorio)
            echo $cabecalho."\r\n".$corpo.$anexos;                            //imprime relatorio

        return $enviar;
    }
}
?>
