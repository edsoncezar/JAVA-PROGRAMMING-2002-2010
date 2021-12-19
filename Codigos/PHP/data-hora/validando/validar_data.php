<?
/*
validar_data.php

Script que contém a função validar(), usada para verificar se uma
data é válida ou não. Por exemplo, se o usuário informar 31/02, 
o programa irá acusar que a data não é válida.

Programado por:
Fábio Berbert de Paula <fabio@vivaolinux.com.br>

Rio de Janeiro, 15 de Agosto de 2003
*/
?>
<html>
<body>
<form>
<?
// construindo o select do dia
echo "<select name=\"dia\">\n";
for ($i = 1; $i <= 31; $i++) {
   $i = sprintf("%02d", $i); // preencher com zero À esquerda
   echo "\t<option value=$i>$i</option>\n";
}
echo "</select>";

// construindo o select do mês
echo "<select name=\"mes\">\n";
for ($i = 1; $i <= 12; $i++) {
   $i = sprintf("%02d", $i); // preencher com zero À esquerda
   echo "\t<option value=$i>$i</option>\n";
}
echo "</select>";

// construindo o select do ano
echo "<select name=\"ano\">\n";
for ($i = 2003; $i <= 2010; $i++)
   echo "\t<option value=$i>$i</option>\n";
echo "</select>";
?>
<input type=submit value="OK">
<br><br>
<?
if ($dia) { // significa que o formulário foi submetido
    // vamos validar a data
   if (!validar($dia, $mes, $ano)) 
      echo "Resultado: <font color=red>data inválida!</font><br>";
   else 
      echo "Resultado: data OK!<br>";
}

// função usada para validar o ano
function validar($dia , $mes, $ano) {
if ( (($ano % 4) == 0) && ($mes == 2) && ($dia > 29) )
  // se o mês for fevereiro e o ano for bissexto, dia não pode
  // ser maior que 29
  return 0;
else if ( (($ano % 4) > 0) && ($mes == 2) && ($dia > 28) )
  // se o mês for fevereiro e o ano não for bissexto, dia não pode
  // ser maior que 28
  return 0;
else if( (($mes == 4) || ($mes == 6) || ($mes == 9) || ($mes == 11) ) && ($dia == 31))
  // se o mês for Abril, Junho, Setembro ou Novembro, dia não pode ser 31
  return 0;
else
  return 1;
}
?>
