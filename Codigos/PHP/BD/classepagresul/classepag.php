/* 
     Exemplo de uso: 
/* 

function Mostrar() 
require "browse.php"; 
    $b = new Browse(); 
    $b->SetHeader("<table>"); 
    $b->SetFooter("</table>"); 
    $b->SetTr("<tr><td>%r[0]</td><td>%r[1]</td></tr>"); 
    $b->SetSelect("SELECT id, nome FROM users"); 
    $b->SetMax(20); // quantos por página 
    $b->go(); 
} 
