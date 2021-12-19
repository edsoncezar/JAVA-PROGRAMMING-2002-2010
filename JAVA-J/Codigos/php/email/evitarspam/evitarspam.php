<?php 
class antiSpam { 
    function codificaEmail($email) { 
        $email = ereg_replace("\@", " <b>arroba</b> ", $email); 
        $email = ereg_replace("\.", " <b>ponto</b> ", $email); 
        return $email; 
    } 

    function descodificaEmail($email) { 
        $email = ereg_replace(" <b>arroba</b> ", "@", $email); 
        $email = ereg_replace(" <b>ponto</b> ", ".", $email); 
        return $email; 
    } 
} 

$antiSpam = new antiSpam; 

/* 
    Para codificar um e-mail: 
    echo $antiSpam->codificaEmail("email@email.com.br"); 

    Para descodificar um e-mail codificado pelo exemplo anterior 
    echo $antiSpam->descodificaEmail("email codificado"); 
*/ 
?> 