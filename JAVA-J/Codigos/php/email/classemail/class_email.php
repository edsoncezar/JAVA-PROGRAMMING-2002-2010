<?
/*
Tipo : classe
Nome : Email
Data : 10/08/2003
Autor: Wonder Alexandre Luz Alves
Desc : Esta class para envio de email
*/
class Email {
		var $titulo;
		var $msg; // Corpo do email
		var $de; //email de(from)
		var $para; //lista de email para
		var $cc; //lista dos email cc
		var $bcc; //lista dos email bcc
		var $replyTo; //email reply to
		var $mime = "HTML";
		var $headers;	
		
        /** Construtor da class 
         * @desc Contrutor da class 
		*/
        function Email ($Titulo=0, $message=0 ,$to=0 , $De_Email=0 , $De_nome=0,$BCC=0,$CC=0){
            if($replyTo) $this->replyTo = $replyTo;
            if($Titulo) $this->titulo = $Titulo;
            if($message) $this->msg = $message;
            if($to) $this->Email_para($to);
            if($BCC) $this->Email_bcc($BCC);
            if($CC) $this->Email_cc($CC);
            if(($De_nome) && ($to))
        		$this->de = $De_nome . " <$De_Email>";
        	elseif($to) 	
        		$this->de = $De_Email;
		}
		
		function Email_para($toList){
			if(is_array($toList)) 
            	$this->para = join($toList,",");
            else
                $this->para = $toList;
		}
		
		function Email_cc($ccList){
			if(is_array($ccList) && sizeof($ccList))
            	$this->cc = join($ccList,",");
            elseif($ccList) 
                $this->cc = $ccList;
		}
		
		function Email_bcc($bccList){
			if(is_array($bccList) && sizeof($bccList))
            	$this->bcc = join($bccList,",");
            elseif($bccList) 
                $this->bcc = $bccList;
		}
         
        function enviar() {
			$this->headers = "From: " . $this->de . "\n";
			if($this->mime=="HTML") $this->headers .= "Content-type: text/html; charset=us-ascii \n";
			if($this->mime=="HTML") $this->headers .= "Content-transfer-encoding: 8bits \n\n"; 
            if ($this->replyTo ) $this->headers .= "Reply-To: " . $this->replyTo . "\n";
            if ($this->cc) $this->headers .= "Cc: " . $this->cc . "\n";
            if ($this->bcc) $this->headers .= "Bcc: " . $this->bcc . "\n";      
            return mail ( $this->para, $this->titulo, $this->msg, $this->headers );
        }

        /**
	 	* email::valida()
	 	* Função que verifica se o e-mail é valido ou não
	 	* @param $str
	 	* @return int
	 	*/
		function valida($email){
			if (ereg("^[_a-z0-9-]+(\.[_a-z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]+)+$", $email))
				return 1;
	 		return 0;
		}	
        
}
 
?>
