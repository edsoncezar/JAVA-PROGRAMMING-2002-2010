<html> 
<head> 
<title>Send Mail</title> 
</head> 

<body> 

<table border="1" width="480"> 
<form method="post" action="enviar.php"> 
<tr> 
  <td>nome</td> 
  <td><input type="text" name="nome"></td> 
</tr> 
<tr> 
  <td>Assunto</td> 
  <td><input type="text" name="assunto"></td> 
</tr> 
<tr> 
  <td>seu e-mail</td> 
  <td><input type="text" name="remetente"></td> 
</tr> 
<tr> 
  <td>Menssagem</td> 
  <td><input type="text" name="menssagem" size="20"></td> 
</tr> 
<tr> 
  <td><input type="submit" name="Submit" value="Enviar"></td> 
  <td>Eviar formulario</td> 
</tr> 
</form> 
</table> 

</body> 
</html> 

============================= enviar.php ============================= 

<html> 
<head> 
<title>TESTE</title> 
</head> 
<body> 

<? 
$assunto = $_POST["assunto"]; 
$menssagem = $_POST["menssagem"]; 
$remetente = $_POST["remetente"]; 
$nome = $_POST["nome"]; 

if($assunto != "" && $menssagem != "" && $remetente != "" && $nome != ""){ 

    if (ereg("([$,#,!,%,*,(,),&,=,/,\,|,^,`,~,:,;,<,>])", $remetente) || strpos($remetente, "@") == 0){ 
          echo "<script language='JavaScript'>"; 
          echo "alert('O campo e-mail esta incorreto!"; 
          echo "Ou esta faltando @');"; 
          echo "</script>"; 

    } 
    else{ 
          echo "Seu e-mail é válido!<br>"; 
          mail("nando.lessa@zipmail.com.br",$assunto,"<b>Nome: $nome</b><br><br>$menssagem","From: $remetente \nContent-type: text/html\n"); 
          echo "E-mail enviado com successo!<br>"; 
          echo "De: <b>$remetente</b><br><b>Para:</b> nando.lessa@zipmail.com.br"; 
    } 
} 
else{ 

tex(); 
echo "<script language='JavaScript'>"; 
if($nome == ""){ 
    echo "alert('O campo Nome não pode estar vazio');"; 
} 
if($remetente == ""){ 
    echo "alert('O campo E-mail não pode estar vazio');"; 
} 
if (ereg("([$,#,!,%,*,(,),&,=,/,\,|,^,`,~,:,;,<,>])", $remetente)){ 
    echo "alert('O campo e-mail esta incorreto!');"; 
} 
if($assunto == ""){ 
    echo "alert('O campo Assunto não pode estar vazio');"; 
} 
if($menssagem == ""){ 
    echo "alert('O campo Menssagem não pode estar vazio');"; 
} 
echo "</script>"; 
} 

############## 
#Functions 
############## 
function tex(){ 
echo "<table border=1 width=100%>"; 
echo "<tr>"; 
echo "<td>Algo esta errado! Clique aqui para <a href=javascript:history.back()>voltar</a></td>"; 
echo "</tr>"; 
echo "</table>"; 
} 

?> 
</body> 
</html> 