/* 
data longa em portugues - data(); 
funcao criada por kinko (kinko@brasnet.org) 
data: 09/07/03 
*/ 
<? 
function data() { //inicia a funcao 
   $meses = array(1 => 'janeiro','fevereiro','março','abril','maio','junho','julho','agosto','setembro','outubro','novembro','dezembro'); //array com todos os meses (comeca o mes 1) 
   $dias = array('domingo','segunda-feira','terça-feira','quarta-feira','quinta-feira','sexta-feira','sábado'); //array com todos os dias (comeca no dia 0) 
   $h = getdate(); //variavel recebe a data 
   $hoje = $h['mday']; //variavel recebe dia (numerico) 
   $dia = $h['wday'];  //variavel recebe dia 
   $mes = $h['mon']; //variavel recebe mes 
   $ano = $h['year']; //variavel recebe ano 
   return "$dias[$dia], $hoje de $meses[$mes] de $ano"; //retorna dia, dia(numerico) de mes de ano 
} 
?> 