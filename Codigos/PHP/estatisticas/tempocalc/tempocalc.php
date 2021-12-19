<?php 
// BY SK15 
// http://www.BuscaWeb.clic3.net 
function getmicrotime(){ 
list($sec, $usec) = explode(" ",microtime()); 
return ($sec + $usec); 
} 
$time_start = getmicrotime(); 

/* 

Seus Códigos AQUI !!!! 

*/ 

$time_end = getmicrotime(); 
$time = $time_end - $time_start; 
$texto=printf ("Gerado em <b>%.5f</b> segundos",$time); // Onde esta o "5" e o numero de digitos 
?> 