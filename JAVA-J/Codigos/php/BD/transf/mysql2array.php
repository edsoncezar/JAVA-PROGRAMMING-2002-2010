<?php

function mysql2array($result)
{
    $retorno = array();
    while ($dados = mysql_fetch_assoc($result)) {
        array_push($retorno, $dados);
    }
    
    return $retorno;
}


/** Exemplo de utilizaчуo
$con = mysql_connect("localhost", "root", "root") or die("falha na conexуo: ".mysql_error());
mysql_select_db("mysql") or die("falha na seleчуo do banco: ".mysql_error());

//Forma de utilizaчуo 
$sql = "select * from user"; 

$result = mysql_query($sql) or die("falha na consulta: ".mysql_error());

$mysql_array  = mysql2array($result);

echo "<pre>";
print_r($mysql_array);
*/
?>