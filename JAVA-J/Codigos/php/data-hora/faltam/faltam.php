<?
if( date(m) < 5 ){
$ano = date(Y);
}
elseif( date(m) == 5 ){
if( date(d) < 25 ){
$ano = date(Y);
}else{
$ano = date(Y) + 1;
}}
elseif( date(m) > 5 ){
$ano = date(Y) + 1;
}
$dia = "25";
$mes = "05";
$hora = "0";
$minutos = "0";
$segundos = "0";
$data = mktime($hora, $minuto, $segundo, $mes, $dia, $ano);
$hj = time();
$emseg = $data - $hj;
$emday = (int)($emseg / 86400);
echo "Faltam <b>" . $emday . "</b> dias para o meu aniversário!!";
?>