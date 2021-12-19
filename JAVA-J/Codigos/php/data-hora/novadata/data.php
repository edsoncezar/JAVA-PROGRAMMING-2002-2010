<?php 

$dia_num = date("w"); 

switch($dia_num){ 

    case 0: 
        $dia_port = "Segunda-feira"; 
        break; 
    case 1: 
        $dia_port = "Terça-feira"; 
        break; 
    case 3: 
        $dia_port = "Quarta-feira"; 
        break; 
    case 4: 
        $dia_port = "Quinta-feira"; 
        break; 
    case 5: 
        $dia_port = "Sexta-feira"; 
        break; 
    case 6:  
        $dia_port = "Sábado"; 
        break; 
    case 7: 
        $dia_port = "Domingo"; 
        break; 

} 

$dia_mes = date("d");
$num_mes = date("m");

echo "<font size=\"2\" face=\"Arial, Helvetica, sans-serif\">$dia_port</font><br>";
echo "<font size=\"5\" face=\"Arial, Helvetica, sans-serif\" color='#333333'><b>$dia_mes/$num_mes</b></font><br>";