<?php

#Simples Script para imprimir o Dia da Semana, Dia do M�s, M�s e Ano.
#by Destinus Dark.
echo "<style>\n";
echo ".fonte\n";
echo "{\n";
echo "font-family: Verdana; font-size:8pt; font-weight : bold;\n";
echo "}\n";
echo "</style>\n";

$DiaSemana = date(D);
 $DiaMes    = date(d);
  $Mes       = date(M);
   $Ano       = date(Y);
$Dia[Sun] = "Domingo";
 $Dia[Mon] = "Segunda-Feira";
  $Dia[Tue] = "Ter�a-Feira";
   $Dia[Wed] = "Quarta-Feira";
    $Dia[Thu] = "Quinta-Feira";
     $Dia[Fri] = "Sexta-Feira";
      $Dia[Sat] = "S�bado";
$Meses[Jan] = "Janeiro";
 $Meses[Feb] = "Fevereiro";
  $Meses[Mar] = "Mar�o";
   $Meses[Apr] = "Abril";
    $Meses[May] = "Maio";
     $Meses[Jun] = "Junho";
      $Meses[Jul] = "Julho";
       $Meses[Aug] = "Agosto";
        $Meses[Sep] = "Setembro";
         $Meses[Oct] = "Outubro";
          $Meses[Nov] = "Novembro";
           $Meses[Dec] = "Dezembro";

$DataReal = "<span class = \"fonte\">$Dia[$DiaSemana], $DiaMes de $Meses[$Mes] de $Ano.</span>";

echo $DataReal;

?>

