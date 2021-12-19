<?php 

// Declarando dados de hora, ano, mes, minuto e dia 

$dia = date(d); 
$ano = date(Y); 
$mes = date(m); 
$hora = date(H); 
$minuto = date(i); 

// Reconhecendo se é dia, tarde, noite ou madrugada 

if ($hora >= 0 && $hora < 6) { 
$sauda = "Boa madrugada"; 
} elseif ($hora >= 6 && $hora < 12){ 
$sauda = "Bom dia"; 
} elseif ($hora >= 12 && $hora < 18) { 
$sauda = "Boa tarde"; 
}else { 
$sauda = "Boa noite"; } 

// Mostrando a mensagem 

echo "<font face=\"tahoma\" size=\"2\" color=\"#0066cc\"><b>$sauda, hoje é dia $dia do mes $mes do ano de $ano, agora sao $hora:$minuto</font></b>"; 

?> 