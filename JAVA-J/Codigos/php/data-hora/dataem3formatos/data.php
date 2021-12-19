<?
/*

Script mostra data simples ou por exento, bastando apenas comentar os "echo"s nao desejados.
Script por: Diego de Alcântara e Souza.
Email: insanow@hotmail.com

*/

$dia = date("d");
$dia2 = date("D");
$mes = date("n");
$ano = date("y");
$ano2 = date("Y");

$mesext = array(1 =>"janeiro", "fevereiro", "março", "abril", "maio", "junho", "julho", "agosto", "setembro", "outubro", "novembro", "dezembro");
$diaext = array("Sun" => "Domingo", "Mon" => "Segunda", "Tue" => "Terça", "Wed" => "Quarta", "Thu" => "Quinta", "Fri" => "Sexta", "Sat" => "Sábado");

//echo "$dia/$mes/$ano";// data simples, d/m/a.
echo "$diaext[$dia2], $dia de $mesext[$mes] de $ano2"; // data por exento, exemplo: segunda-feira, 15 de setembro de 2003.
//echo "$dia de $mesext[$mes] de $ano2"; // exemplo : 15 de setembro de 2003
?>
