<html> 
<head> 
<title>Exemplo de Uso de Funções de ODBC</title> 
</head> 

<body> 
<?php 
$conexao = odbc_connect("teste\", \"\", \"\"); 

if ($conexao) { 
  $resultado = odbc_exec($conexao, \"SELECT * FROM list\"); 

  if ($resultado) { 
    echo \"<table border=1>\"; 
    echo \"<tr><td align=center>\"; 
    echo strtoupper(odbc_field_name($resultado, 1)); 
    echo \"</td><td align=center>\"; 
    echo strtoupper(odbc_field_name($resultado, 2)); 
    echo \"</td></tr>\"; 

    while (odbc_fetch_row($resultado)) { 
      echo \"<tr><td>\"; 
      echo odbc_result($resultado, 1); 
      echo \"</td><td>\"; 
      echo odbc_result($resultado, 2); 
      echo \"</td></tr>\"; 
    } 
    echo \"</table>\"; 
  } 
} 
odbc_close($conexao); 
?> 
</body> 
</html> 