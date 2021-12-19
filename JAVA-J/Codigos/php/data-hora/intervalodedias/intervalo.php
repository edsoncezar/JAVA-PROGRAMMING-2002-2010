$data_ini = strtotime("07/29/2003"); //data inicial '29 de julho de 2003' 
$hoje = date("m/d/Y"); // data atual 
$foo = strtotime($hoje); // transforma data atual em segundos (eu acho) 
$dias = ($foo - $data_ini)/86400; //calcula intervalo 
echo $dias; //imprime intervalo de dias 