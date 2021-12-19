<? 
function mail_html($destinatario,$origem, $titulo, $mensagem) 
{ 

    $headers ="Content-Type: text/html; charset=iso-8859-1\n"; 
    $headers.="From: $origem\n"; 
    mail("$destinatario", "$titulo", "$mensagem", "$headers"); 

} 
?> 