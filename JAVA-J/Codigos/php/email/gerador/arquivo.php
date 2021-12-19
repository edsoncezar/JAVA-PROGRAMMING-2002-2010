<?php 
/* 
$to_name    nome do destinatário 
$to_email   email do destinatário 
$from_name  nome do remetente 
$from_email email do remetente 
$charset    o CHARSET que você deseja utilizar no email 
$formato    formatação do email, opções text/plain (texto puro) e text/html (html) 

*/ 
function criar_mailheader($to_name,$to_email,$from_name,$from_email,$charset='iso-8859-1',$formato='text/plain') { 

    $headers  = "MIME-Version: 1.0\r\n"; 
    $headers .= "Content-Type: $formato; charset=$charset\r\n"; 
    $headers .= "From: \"". acessorio_hex4email($from_name,$charset) ."\" <".$from_email.">\r\n"; 
    $headers .= "Reply-To: \"". acessorio_hex4email($from_name,$charset) ."\" <".$from_email.">\r\n"; 
    $headers .= "To: \"". acessorio_hex4email($to_name,$charset) ."\" <".$to_email.">\r\n"; 
    $headers .= "Return-Path: <$from_email>\r\n";        // Return path for errors 
    $headers .= "Return-Receipt-To: <$from_email>\r\n";        // Return path for errors 
    $headers .= "X-Sender: <$from_email>\r\n";  
    $headers .= "X-Priority: 3\r\n";                         // 1-Urgent message! 2-very 3-normal 
    $headers .= "X-MSMail-Priority: Normal\r\n"; 
    $headers .= "X-Mailer: PHP/ ".phpversion()."\r\n";     // mailer 
    return $headers; 
} 
function acessorio_hex4email($string,$charset='iso-8859-1')  
{ 
    $string = bin2hex($string); 
    $encoded = chunk_split($string, 2, '=');  
    $encoded = preg_replace("/=$/",'',$encoded);  
    $string="=?$charset?Q?=".$encoded."?=";  
    return $string;  
} 

// EXEMPLO 
$headers = criar_mailheader('José Silva','jose@dasilva.net','Destinário','destinat@rio.com'); 
mail('jose@dasilva.net','Assunto','corpo da mensagem',$headers) 



?>