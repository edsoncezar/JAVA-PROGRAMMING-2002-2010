<? 

/* 

Enquete 0.5.3 - Com check boxes 
Paulo Vitto Ruthes - 2000 

Para utilização crie em uma DB de MySQL uma tabela 
create table enquete (opcao char(2) primary key, descr char(50), votos char(4)); 
Isso deve resolver. 

Um exemplo da enquete está no final do arquivo já como parte do PHP 

Para utilização da enquete com imagem de gráfico crie um gif de por exemplo 100x2 pixels. 
E coloque-o no mesmo local do script, ou adicione-o em uma DB. 
E "descomente" tudo que estiver logo abaixo de: 

// Para utilização com imagem com gráfico 

*/ 

if ($escolha != "") { 
     
    // Variáveis a serem alteradas 
     
    $host = "localhost";    // endereço do servidor onde está rodando o MySQL 
        $user = "";        // usuário do MySQL 
        $pass = "";     // senha do usuário do MySQL 
        $dbas = "";        // banco de dados (database) utilizada 
    $ques = "Quais desses bancos de dados você já utilizou?";    // pergunta da enquete 
    $chek = "5";        // numero de opções na enquete 

    // Para utilização com imagem com gráfico 

    $imagem = "aqua.gif";    // imagem do gráfico 
     
    // FIM 
     
    // Começo do Script para requisitar os dados e alterá-los 
     
    $cheks = $chek + 1; 
    $conexao = @mysql_connect($host, $user, $pass) or die("ops.. ehhehe.. num deu muuuito certo..."); 
    mysql_select_db($dbas, $conexao); 

    for ($i=0;$i<sizeof($escolha);$i++) { 
     
        $id = $escolha[$i]; 
        $consulta[$i] = "SELECT * FROM enquete WHERE opcao='$id'"; 
        $resultado[$i] = mysql_query($consulta[$i], $conexao); 
        $objetos[$i] = mysql_fetch_object($resultado[$i]); 
        $vot[$i] = $objetos[$i]->votos; 
        $vot_m[$i] = $vot[$i] + 1; 
         
        $update[$i] = "UPDATE enquete SET votos='$vot_m[$i]' WHERE opcao='$id'"; 
        mysql_query($update[$i], $conexao); 
         
    } 
     

    for ($a=1;$a<$cheks;$a++) { 
     
        $consulta[$a] = "SELECT * FROM enquete WHERE opcao='$a'"; 
        $resultado[$a] = mysql_query($consulta[$a], $conexao); 
        $objetos[$a] = mysql_fetch_object($resultado[$a]); 
         
        $vot[$a] = $objetos[$a]->votos; 
        $opc[$a] = $objetos[$a]->opcao; 
        $des[$a] = $objetos[$a]->descr; 
         
        $total_votos += $vot[$a]; 

    } 
     
    for ($b=1;$b<$cheks;$b++) { 
     
        $por[$b] = $vot[$b] * 100 / $total_votos; 
        $por[$b] = number_format($por[$b],2); 
    } 
     
    echo "<table width=\"200\" align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"1\" bgcolor=\"#000000\">  <tr align=\"center\"><td align=\"center\"><font face=\"Verdana\" size=\"-1\" color=\"#FFFFFF\"><b>Enquete do Mês</b></font></td></tr>\n"; 

    echo "<tr align=\"center\"><td><center><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"2\" align=\"center\" bgcolor=\"#FFFFFF\" height=\"60\"><tr valign=\"middle\"><td colspan=\"2\" height=\"40\"><font face=\"Verdana\" size=\"-2\"><b>$ques</b></font></td></tr>\n"; 

    echo "<tr align=\"right\" valign=\"bottom\"><td colspan=\"2\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"1\" height=\"100%\">\n"; 


    for ($c=1;$c<$cheks;$c++) { 
     
        // Para utilização com imagem com gráfico 

        $width_img[$c] = $vot[$c] * 195 / $total_votos; 
        $width_img[$c] = (int) $width_img[$c]; 
         

        echo "<tr bgcolor=\"#FFFFFF\">\n"; 
         
        echo "<td nowrap height=\"22\"><font face=\"Verdana\" size=\"-2\">$des[$c]</font></td>\n"; 

        echo "<td nowrap height=\"22\">&nbsp;&nbsp;</td>\n"; 
         
        // Para utilização com imagem com gráfico 

        echo "<td nowrap height=\"22\"><img src=\"$imagem\" width=\"$width_img[$c]\" height=\"10\"></td>\n"; 
         
        echo "<td height=\"22\">&nbsp;&nbsp;</td>\n"; 
         
        echo "<td nowrap height=\"22\"><font face=\"Verdana\" size=\"-2\">$por[$c]%</font></td>\n"; 
         
        echo "<td nowrap height=\"22\">&nbsp;&nbsp;</td>\n"; 
         
        echo "<td nowrap height=\"22\"><font face=\"Verdana\" size=\"-2\">$vot[$c] votos</td></tr>\n"; 
         
    } 
     
    echo "</table>\n</td></tr></table></td></tr><tr><td align=\"center\"><font face=\"Verdana\" size=\"1\" color=\"#ffffff\"><b>Total de votos: </b>$total_votos</td></tr></table></body></html>"; 


} 


else { 
?> 
<form action="<? echo $PHP_SELF; ?>" method=post> 
<input type="checkbox" name="escolha[]" value="1">MySQL<br> 
<input type="checkbox" name="escolha[]" value="2">msSQL<br> 
<input type="checkbox" name="escolha[]" value="3">ODBC<br> 
<input type="checkbox" name="escolha[]" value="4">PostgreSQL<br> 
<input type="checkbox" name="escolha[]" value="5">Nenhum<br> 
<input type="submit"><br> 
</form> 
<? 

} 

?> 

     
