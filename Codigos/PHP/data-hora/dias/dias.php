<? 
function diassemanas(){ 
$verificames = 1; 

while($verificames != date("m")){ 

   switch($verificames){ 

      case"01": $adicionames = 31; break; 
      case"02": if(date("Y")%4 == 0){ $adicionames = 29; }else{ $adicionames = 28; } break; 
      case"03": $adicionames = 31; break; 
      case"04": $adicionames = 30; break; 
      case"05": $adicionames = 31; break; 
      case"06": $adicionames = 30; break; 
      case"07": $adicionames = 31; break; 
      case"08": $adicionames = 31; break; 
      case"09": $adicionames = 30; break; 
      case"10": $adicionames = 31; break; 
      case"11": $adicionames = 30; break; 
      case"12": $adicionames = 31; break; 

   } 

   $diasmes = $diasmes + $adicionames; 

   $verificames++; 
} 

$difdia = date ("d", mktime (0,0,0,date("m"),date("d"),date("Y"))) - date("d", mktime (0,0,0,01,01,2001)); 

$diasmes = $diasmes + $difdia; 

print "já se passaram ".$diasmes." dias do ano de ".date("Y")." e estamos na semana ".ceil($diasmes / 7)." deste mesmo ano<br>"; 
} 

?> 