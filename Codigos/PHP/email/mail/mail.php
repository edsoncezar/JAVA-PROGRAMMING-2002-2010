<?php
$texto="
<html>
<body>
         <img src=\"cid:part1\"> 
	 <font size=6> SEU CODIGO </FONT> 
</body>
</html>
";

	  $nome="/xxx/xxx/sua_imagem.jpg";
          $arquivo=fopen($nome,"r");
          $contents = fread($arquivo, filesize($nome));
          $encoded_attach = chunk_split(base64_encode($contents));

          fclose($arquivo);

          $mailheaders = "From: quem_esta_enviando@xxxxxx.xxx\n";
          $mailheaders .= "MIME-version: 1.0\n";
          $mailheaders .= "Content-type: multipart/related;";
          $mailheaders .= "boundary=\"limite\"\n";


          $msg_body = "--limite\n";
          $msg_body .= "Content-type: text/html; charset=iso-8859-1\n";
          $msg_body .= "$texto";
          $msg_body .= "--limite\n";
          $msg_body .= "Content-type: image/jpeg; name=sua_imagem.jpg\n";
          $msg_body .= "Content-Transfer-Encoding: BASE64\n";
          $msg_body .= "Content-ID: <part1>\n";
          $msg_body .= "Content-disposition: inline; filename=sua_imagem.jpg\n\n";
          $msg_body .= "$encoded_attach\n";
          $msg_body .= "--limite--\n";
        
          mail("destino@xxxxxx.xxx","Assunto",$msg_body, $mailheaders);
          
      ?>  
