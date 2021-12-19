<?
//Feito por Cristiano Barbosa www.criso.com.br

$dia = date('d'); $mes = date('m'); $ano = date('Y'); $semana = date('w'); 

switch ($mes){ 
case 1: $mes = "Janeiro"; break; 
case 2: $mes = "Fevereiro"; break; 
case 3: $mes = "Março"; break; 
case 4: $mes = "Abril"; break; 
case 5: $mes = "Maio"; break; 
case 6: $mes = "Junho"; break; 
case 7: $mes = "Julho"; break; 
case 8: $mes = "Agosto"; break; 
case 9: $mes = "Setembro"; break; 
case 10: $mes = "Outubro"; break; 
case 11: $mes = "Novembro"; break; 
case 12: $mes = "Dezembro"; break; 
} 
$hoje = "<font color =#006633><b>$mes - $ano"; 
echo "<table align = center bgcolor = #C7E7C7 >";
echo "<td bgcolor= #FFFFFF align = center>$hoje<table  bgcolor = #C7E7C7 >";
echo "<td align = center >D</td><td align = center >S</td><td align = center >T</td><td align = center>Q</td><td align =center>Q</td><td align=center >S</td><td align=center>S</td><tr>";

$mes= date('m'); $ano= date('y');
$d = mktime(0,0,0,$mes,1,$ano); $diaSem = date('w',$d);

for ($i = 0; $i< $diaSem; $i++)
{   echo "<td align = center  bgcolor= #FFFFFF> </td>";}

for ($i = 2; $i < 33; $i++){
$linha = date('d',$d);
if ($i > 3){
}
if ($linha == $dia)
	{echo "<td align = center><b>$linha </b> </td>";}
else {echo "<td align = center  bgcolor =#FFFFFF>$linha  </td>";}

if(date('w',$d) ==6){
echo "  </tr> <tr>";
}
$d = mktime(0,0,0, $mes, $i, $ano);
if (date ('d',$d) =="01") {break;}
}
echo "</tr></table></td></font></table>";

?>

