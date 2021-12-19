<?php
function is_email_valid($email) { 
  if(eregi("^[a-z0-9\._-]+@+[a-z0-9\._-]+\.+[a-z]{2,3}$", $email)) return TRUE; 
  else return FALSE; 
}
?>
Exemplo:
<?php
$email = "numsei@naosabe.com.br"; 
if (is_email_valid($email)) print "o E-mail é válido"; 
else print "o E-mail é válido";
?>