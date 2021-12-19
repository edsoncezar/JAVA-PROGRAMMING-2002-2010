//Vale lembrar que a função aceita números negativos 
//como argumento, passando então: 

//echo dateadd(5); //soma-se 5 dias a data do sistema 
//echo dateadd(-5,"06/01/1975"; //diminui 5 dias da data passada 


function dateadd($dias,$datahoje = "?") 
{ 
if ($datahoje == "?") 
{ 
  $datahoje = date("d") . "/" . date("m") . "/".date("Y"); 
} 

  if (ereg ("([0-9]{1,2})/([0-9]{1,2})/([0-9]{4})", $datahoje, $sep)) 
  {    
   $dia = $sep[1];    
   $mes = $sep[2];    
   $ano = $sep[3];    
  } 
  else 
  {    
    return "#Erro#";    
  }    

  if ($dias < 0) 
  { 
    $dias = $dias * -1; 
    if($mes == "01" || $mes == "02" || $mes == "04" || $mes == "06" || $mes == "08" || $mes == "09" || $mes == "11"){    
     for ($cont = $dias ; $cont > 0 ; $cont--) 
     {    
     $dia--;    
      if($dia == 00) 
      {   
       $dia = 31;    
       $mes = $mes -1; 
        if($mes == 00) 
        {    
         $mes = 12;    
         $ano = $ano - 1; 
        }    
      }    
     }    
    }    

    if($mes == "05" || $mes == "07" || $mes == "10" || $mes == "12" ) 
    {    
     for ($cont = $dias ; $cont > 0 ; $cont--) 
     {    
      $dia--;    
      if($dia == 00) 
       { 
        $dia = 30;    
        $mes = $mes -1; 
       }    
      }    
    }    

   if($ano % 4 == 0 && $ano%100 != 0) 
   { 
    if($mes == "03") 
     {    
      for ($cont = $dias ; $cont > 0 ; $cont--) 
      {    
        $dia--;    
        if($dia == 00) 
         { 
          $dia = 29;    
          $mes = $mes -1; 
        }    
       }    
     }    
   } 
   else 
   { 
    if($mes == "03" ) 
    {    
      for ($cont = $dias ; $cont > 0 ; $cont--) 
       {    
        $dia--;    
        if($dia == 00) 
         { 
          $dia = 28;    
          $mes = $mes -1; 
         }    
       }    
     }    
   }    
  } 
  else 
  { 
  $i = $dias;    
  for($i = 0;$i<$dias;$i++) 
  {    
    if ($mes == 01 || $mes == 03 || $mes == 05 || $mes == 07 || $mes == 8 || $mes == 10 || $mes == 12) 
    {    
      if($mes == 12 && $dia == 31) 
      {    
        $mes = 01;    
        $ano++;    
        $dia = 00;    
      }    
    if($dia == 31 && $mes != 12) 
    {    
      $mes++;    
      $dia = 00;    
    }    
  } 
  if($mes == 04 || $mes == 06 || $mes == 09 || $mes == 11) 
  {    
    if($dia == 30) 
    {    
      $dia =  00;    
      $mes++;    
    }    
  } 

  if($mes == 02) 
  {    
    if($ano % 4 == 0 && $ano % 100 != 0) 
    { 
      if($dia == 29) 
      {    
        $dia = 00;    
        $mes++;         
      }    
    }    
    else 
    {    
      if($dia == 28) 
      {    
        $dia = 00;    
        $mes++;    
      }    
    }    
  } 
  $dia++;    
  } 
  } 
  if(strlen($dia) == 1){$dia = "0".$dia;}    
  if(strlen($mes) == 1){$mes = "0".$mes;}    
  $nova_data = $dia . "/" . $mes . "/" . $ano ;    
  return $nova_data;    
} 
