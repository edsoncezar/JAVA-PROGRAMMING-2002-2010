<?php 
// 
// == Função para subtração e adição de datas ============================================= 
// Autor: Fernando Barchi Finotti - fbarchi@ig.com.br - UIN# 38189361 
// Modificado por: Thiago Filler
// Data: 02 Dez 01 
// Update : Fernando Barchi Finotti 
// Data: 15/09/2003 
// Obs. Bug quando o mes era 09, não trocava de mes, apenas era somado  os dias
// LOG Fernando Barchi : Problema foi corrigido.
// ============================================================================ 
// 

function voltadata($dias,$datahoje){ 

// Desmembra Data ------------------------------------------------------------- 

  if (ereg ("([0-9]{1,2})/([0-9]{1,2})/([0-9]{4})", $datahoje, $sep)) { 
  $dia = $sep[1]; 
  $mes = $sep[2]; 
  $ano = $sep[3]; 
  } else { 
    echo "<b>Formato Inválido de Data - $datahoje</b><br>"; 
  } 

// Meses que o antecessor tem 31 dias ----------------------------------------- 

  if($mes == "01" || $mes == "02" || $mes == "04" || $mes == "06" || $mes == "08" || $mes == "09" || $mes == "11"){ 
    for ($cont = $dias ; $cont > 0 ; $cont--){ 
    $dia--; 
      if($dia == 00){ // Volta o dia para dia 31 . 
      $dia = 31; 
      $mes = $mes -1; // Diminui um mês se o dia zerou . 
        if($mes == 00){ 
        $mes = 12; 
        $ano = $ano - 1; // Se for Janeiro e subtrair 1 , vai para o ano anterior no mês de dezembro. 
        } 
      } 
    } 
  } 

// Meses que o antecessor tem 30 dias ----------------------------------------- 

  if($mes == "05" || $mes == "07" || $mes == "10" || $mes == "12" ){ 
    for ($cont = $dias ; $cont > 0 ; $cont--){ 
    $dia--; 
      if($dia == 00){ // Volta o dia para dia 30 . 
      $dia = 30; 
      $mes = $mes -1; // Diminui um mês se o dia zerou . 
      } 
    } 
  } 

// Mês que o antecessor é fevereiro ------------------------------------------- 
  if($ano % 4 == 0 && $ano%100 != 0){ // se for bissexto 
    if($mes == "03" ){ 
      for ($cont = $dias ; $cont > 0 ; $cont--){ 
      $dia--; 
        if($dia == 00){ // Volta o dia para dia 30 . 
        $dia = 29; 
        $mes = $mes -1; // Diminui um mês se o dia zerou . 
        } 
      } 
    } 
  }//fecha se bissexto... 
  else{ // se não for bissexto 
    if($mes == "03" ){ 
      for ($cont = $dias ; $cont > 0 ; $cont--){ 
        $dia--; 
        if($dia == 00){ // Volta o dia para dia 30 . 
          $dia = 28; 
          $mes = $mes -1; // Diminui um mês se o dia zerou . 
        } 
      } 
    } 
  } 

// Confirma Saída de 2 dígitos ------------------------------------------------ 

  if(strlen($dia) == 1){$dia = "0".$dia;} 
  if(strlen($mes) == 1){$mes = "0".$mes;} 

// Monta Saída ---------------------------------------------------------------- 

  $nova_data = $dia."/".$mes."/".$ano ; 

  print $nova_data; 
} //fecha função 

// 
// == Função para adição de datas ============================================= 
// Autor: Fernando Barchi Finotti - fbarchi@ig.com.br 
// Modificado por: Tripa Seca @ phpbrasil.com 
// Data: 02 Dez 01 
// ============================================================================ 
// 

function somadata($dias,$datahoje){ 

// Desmembra Data ------------------------------------------------------------- 

  if (ereg ("([0-9]{1,2})/([0-9]{1,2})/([0-9]{4})", $datahoje, $sep)) { 
  $dia = $sep[1]; 
  $mes = $sep[2]; 
  $ano = $sep[3]; 
  } else { 
    echo "<b>Formato Inválido de Data - $datahoje</b><br>"; 
  } 

  $i = $dias; 

  for($i = 0;$i<$dias;$i++){ 

    if ($mes == "01" || $mes == "03" || $mes == "05" || $mes == "07" || $mes == "08" || $mes == "10" || $mes == "12"){
      if($mes == 12 && $dia == 31){ 
        $mes = 01; 
        $ano++; 
        $dia = 00; 
      } 
    if($dia == 31 && $mes != 12){ 
      $mes++; 
      $dia = 00; 
    } 
  }//fecha if geral 

  if($mes == "04" || $mes == "06" || $mes == "09" || $mes == "11"){ 
    if($dia == 30){ 
      $dia = 00; 
      $mes++; 
    } 
  }//fecha if geral 

  if($mes == "02"){ 
    if($ano % 4 == 0 && $ano % 100 != 0){ //ano bissexto 
      if($dia == 29){ 
        $dia = 00; 
        $mes++;      
      } 
    } 
    else{ 
      if($dia == 28){ 
        $dia = 00; 
        $mes++; 
      } 
    } 
  }//FECHA IF DO MÊS 2 

  $dia++; 

  }//fecha o for() 

// Confirma Saída de 2 dígitos ------------------------------------------------ 

  if(strlen($dia) == 1){$dia = "0".$dia;}; 
  if(strlen($mes) == 1){$mes = "0".$mes;}; 

// Monta Saída ---------------------------------------------------------------- 

$nova_data = $dia."/".$mes."/".$ano;

print $nova_data;

}//fecha a funçâo data 
?>
<?php 
//Exemplo: 

echo "Volta Data: "; 
voltadata("7","05/02/2001"); 
echo "<br><br>"; 
echo "Soma Data: "; 
somadata("15","05/02/2001"); 
?> 