<?
/* Validaзгo de e-mail by Felipe Lopes v.2.0 - 2001
** php4@mailbr.com.br
** php.phpgo.com
** para usar o script, basta incluн-lo na pбgina que se quer fazer
** a validaзгo do e-mail com o seguinte cуdigo:
** require("verifica_email.php");
** e entгo chamar a funзгo da seguinte forma:
** verifica_email($variavel_q_contem_o_email)
** essa funзгo retornarб true se o e-mail estiver correto e, se nгo estiver, false.
*/

function verifica_email($email){
if (ereg("^([0-9,a-z,A-Z]+)([.,_]([0-9,a-z,A-Z]+))*[@]([0-9,a-z,A-Z]+)([.,_,-]([0-9,a-z,A-Z]+))*[.]([0-9,a-z,A-Z]){2}([0-9,a-z,A-Z])?$", $email)){
return true;
}else{
    return false;
}

}

?>