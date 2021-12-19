<HTML>
<? 
$from="seumail@seumail.com.br";
$to = "mail@yahoo.com.br";
$assunto = "Contato Site";
$msg = str_replace("\n","<br>",$msg);
$ip = getenv("REMOTE_ADDR");
$mensagem= "<br>$nome<br>$email<br><br>$msg<br><br> IP = $ip";

if ($enviado == "sim") {
if (mail("$to", $assunto, $mensagem, "from: <$from>\nContent-Type:text/html;charset=iso-8859-1"))
{ $envio = "Mensagem enviada!"; }
}

?>

<STYLE type=text/css>
 
.form_text {color: #006600; border: #006600; background-color: #FFFFFF;  border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px}
</STYLE>
<br>
<TABLE  bgcolor = "#C7E7C7" width ="500" align="center"  >
  <tr bordercolor="#CCFFCC"> 
    <td width ="180" bgcolor ="#ffffff" > 
      <p align="left" > <FONT face="Arial, Helvetica, sans-serif" color="#006600"> 
</font>
</p>

</td>

    <td  height="230" border="0"  align = "center" bgcolor ="#ffffff" width="437"> 
      <br>
 <FORM name="form1" method="post" action="contato.php"> 
      <P align="center"><b><FONT face="Arial, Helvetica, sans-serif" color="#006600"i>Seu 
        Nome:<BR>
        <INPUT name="nome" size="35" class="form_text" >
        <BR>
        Seu Mail:<BR>
        <INPUT name="email" size="35" class="form_text" >
        <BR>
        Mensagem:<BR>
        <TEXTAREA  align ="center" rows="5" name="msg"   cols="20" class="form_text" ></TEXTAREA>
        </font></b><b><FONT face="Arial, Helvetica, sans-serif" color="#006600"> 
        <input type="submit" value="Enviar" name="B1" class="form_text" >
        <INPUT type="hidden" value="sim" name="enviado">
        <? echo "<br>".$envio; ?> </font></b> 
      </td>
	</TABLE>
</HTML>
