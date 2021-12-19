<? 


$dt2 = date('Y\/m\/d'); 

$dia = substr($dt2, -2); 
$mes = substr($dt2, -5,2); 
$ano = substr($dt2, -10,4); 


$dt = ($mes."-".$dia."-".$ano); 

// É necessário usar o formato MM-DD-AAAA nessa função 
    
list($from_month,$from_day,$from_year) = explode("-",$dt);   

    //número de dias que desejo diminuir 
    $to_day = $from_day - 20; 
    $to_year = $from_year; 
    $to_month = $from_month; 

    if ($to_day < 0) { 

        $to_day = 30 +($to_day); 
        $to_month = $from_month - 1; 
          
        if ($to_month == 0) { 
              
            $to_month = 12; 
            $to_year = $from_year - 1; 

        }              

    } 

    $to = ($to_month."-".$to_day."-".$to_year); 
      
    echo "Data from: "; 
    echo $dt; 
    echo "<br>Data to: "; 
    echo $to; 
          

list($to_month,$to_day,$to_year) = explode("-",$to);   


  $from_date = mktime(0,0,0,$from_month,$from_day,$from_year);   
  $to_date = mktime(0,0,0,$to_month,$to_day,$to_year);   
            

  $days = ($to_date - $from_date)/86400;   
      

    echo "<br><br>A diferença de dias entre duas datas seria: "; 
      

// Adicionado o ceil($days) para garantir que o resultado seja sempre um número inteiro */ 
    echo ceil($days); 


?> 