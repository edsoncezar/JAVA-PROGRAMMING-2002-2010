<?
//Feito por Cristiano Barbosa www.criso.com.br
error_reporting(0);

$hs=date("H:i");
$dataatual=date("d/m/Y");
$data2=date("Y-m-d");

?>
<html>
<head>
<title></title>
<body class="textog" bgcolor="#FFFFFF" >
<?

if(!empty($email)){

$headers .= "MIME-Version: 1.0\nContent-Type: text/html; charset=iso-8859-1\n";
$headers .="From: Teste email.php <teste@teste.com.br>\r\n";

mail($email,"Teste envio PHP : $dataatual - $hs", "$msgcliente","$headers");

$msg="Foi enviado um email para : $email";
}
?>

<form name=form1  method=post action=testemail.php>
<table border=1>
<tr>
<td colspan=2> Teste para envio de email : <? echo $msg ?></td>
</tr>
<tr>
<td>Email destino:</td><td><input type=text name=email></td>
</tr>
<tr>
<td></td><td><input type=submit value=enviar></td>
</tr>
</table>
</form>
</body></html>
