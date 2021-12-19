<?php 
// 
// == Minutos para Hora/Minutos - muntohor() ================================== 
// Autor: Tripa Seca (fosfozol@yahoo.com.br) 
// 
// Versão: v0.1 @ 09/Set/2002 
//         Desenvolvimento do script em si. 
// 
// Oque é isso?: Converte um monte de minutos para o formato 0h00. 
//               Limitação: Não converte em dias. 
// 
// Copyright: GNU General Public License 
//            Modifique a vontade para adaptar a sua necessidade. 
// ============================================================================ 
// 


function mintohor($mins) { 
  $h = floor($mins/60); 
  $m = $mins % 60; 
  if($m < 10) $m = "0".$m; 
  return ($h."h".$m); 
} 

?> 
Exemplos:<br> 
100 min - <?= mintohor(100) ?><br> 
589 min - <?= mintohor(589) ?><br> 
32 min - <?= mintohor(32) ?><br> 
111 min - <?= mintohor(11) ?><br> 
