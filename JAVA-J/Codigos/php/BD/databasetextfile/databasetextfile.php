<?php 
# defina aqui, o nome do arquivo q vc irá usar como banco de dados 
$Arquivo="meu_bd.txt"; 

# a funcao file() le o arquivo 
$arquivo=file($Arquivo); 

# le linha por linha do arquivo 
foreach($arquivo as $linha) { 
  # diz que o banco de dados está neste formato: 
  # nome|email 
  # $nome, é a variável contendo nome 
  # | separa as strings, $email contém o email 
  list($nome,$email)=explode("|", $linha); 

  # imprime o nome linkado ao email 
  print "<a href=\"mailto:$email\">$nome</a><br>\n"; 
} 
?> 


<? /* código sem os comentarios */ ?> 

<? 
$Arquivo="meu_bd.txt"; 
$arquivo=file($Arquivo); 
foreach($arquivo as $linha) { 
  list($nome,$email)=explode("|", $linha); 
  print "<a href=\"mailto:$email\">$nome</a><br>\n"; 
} 
?> 