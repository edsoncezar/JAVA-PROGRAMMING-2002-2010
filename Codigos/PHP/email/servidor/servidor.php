<?php 
/* 
##################################################################### 
##################################################################### 
## Data : 01/09/2003 - PHP 
## Autor : Thiago Zilli Sarmento - EMAIL : thiago@3dzs.com 
##                                   EMAIL : internet@feob.br 
## 
## Nome do Programa : Estudo de consulta Email. 
## 
## Have Fun. ;) 
##################################################################### 
##################################################################### 
*/ 


$server = "email.seuprovedor.com.br"; 
$user = "usaconta@seuprovedor.com.br"; 
$pass = "suasenha"; 

//Coneccao com o server... 
$conn = @imap_open("\{$server:110/pop3}INBOX",$user,$pass) or die("Conecção Falhou!"); 

//Chamando o Headers... 
$headers = @imap_headers($conn) or die("Não existe emails!"); 

//Verifica Quantidade de emails em sua caixa postal... 
$numEmails = sizeof($headers); 

//Mostra conteudo... 
echo "Você tem <b>$numEmails</b> mensagens em sua caixa de correio.<br><br>"; 

//Loop para listar somente o titulo descrito... 
for ($i=1; $i<$numEmails+1; $i++){ 

$mailHeader = @imap_headerinfo($conn, $i); 
$from = $mailHeader->fromaddress; 
$subject = strip_tags($mailHeader->subject); 
$date = $mailHeader->date; 

echo "Email de : $from , titulo $subject, data $date<br>"; 

} 

//Fecha conecccao com o server... 
imap_close($conn); 

?> 
