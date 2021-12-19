<? 
/* 
Função Semana 
Autor : Marcelo Raposo 
Data : 10/09/2001 
Universidade do Estado da Bahia 
Versão : 1b 
*/ 
function conversor($dataentra){ 
             $datasentra=explode("-",$dataentra); 
             $indice = 3; 
             while($indice != -1){ 
                $datass[$indice] = $datasentra[$indice]; 
                $indice--; 
             } 
             $datasaida=implode("/",$datass); 
             return $datasaida; 
          } 


          $diadasemana = date("w"); 

          $dia = date("d"); 

          $mes = date("m"); 

          $ano = date("Y"); 

          $datadasemana = date ("Y-m-d", mktime (0,0,0,$mes,$dia,$ano)); 

          $index = 0; 
            
          while($diadasemana != -1){ 

             switch($diadasemana){ 

                case"0": $diasemana = "Domingo";       break; 
                case"1": $diasemana = "Segunda-Feira"; break; 
                case"2": $diasemana = "Terça-Feira";   break; 
                case"3": $diasemana = "Quarta-Feira";  break; 
                case"4": $diasemana = "Quinta-Feira";  break; 
                case"5": $diasemana = "Sexta-Feira";   break; 
                case"6": $diasemana = "Sábado";        break; 

             } 
        
             $arraydata[$index] = $datadasemana; $index++; 

             print $diasemana."\t".conversor($datadasemana)."<br><br>"; 

             $diadasemana--; 
     
             $dia = $dia - 1; 

             $datadasemana = date ("Y-m-d", mktime (0,0,0,$mes,$dia,$ano)); 
        
        }   
          
        $indexy = $index - 1; 

        $saidaarray = $arraydata[$indexy]; $saidaarray2 = $arraydata[0];   
          
        $diafinais = $dia + 7; 
          
        $saidaarray2 = date ("Y-m-d", mktime (0,0,0,$mes,$diafinais,$ano)); 

        print "Semana de ".conversor($saidaarray)." até ".conversor($saidaarray2); 

?> 