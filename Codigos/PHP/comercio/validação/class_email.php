<?php
/*
 * Classe para validaзгo de e-mail
 * Copyright (c) Sebastiгo Farias Jъnior 2002
 * Belйm/PA
 * E-mail: overond@yahoo.com
 * Licenзa GNU
 * Modo de usar:
 * $oEmail = new email;
 * if ($oEmail->valida($str)) {
 * echo "e-mail valido";
 * }
 * else{
 * echo "e-mail invalido";
 * }
 */
class email{

/**
 * email::valida()
 * Funзгo que verifica se o e-mail й valido ou nгo
 * @param $str
 * @return 
 */
function valida($str){
if (ereg("^[_a-zA-Z0-9-]+(\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)+$", $str)){
return 1;
 }
else {
 return 0;
 }
}
}
?>