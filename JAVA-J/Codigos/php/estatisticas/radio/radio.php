<? 

/* 

Enquete 0.4.1 - Com RADIO BOXES 
Paulo Vitto Ruthes - 2001 

-- ALTERAÇÕES -- 

+ Funcionabilidade do script. 
+ Erros corrigidos. 
+ Funcionalidade da tabela. 


---------------- 

Para utilização crie em uma DB de MySQL uma tabela 
create table enquete (opcao INT(2) auto_increment primary key, descricao VARCHAR(50), votos INT(4)); 
Isso deve resolver. 

Um exemplo da enquete está no final do arquivo já como parte do PHP 

Para utilização da enquete com imagem de gráfico crie um gif de por exemplo 100x2 pixels. 
E coloque-o no mesmo local do script, ou adicione-o em uma DB. 
E "descomente" tudo que estiver logo abaixo de: 

// Para utilização com imagem com gráfico 

*/ 


if ($escolha != "") { 

     
    // Variáveis a serem alteradas 
     
    $db = "nitio";        // Altere para o nome de sua DB 
    $radios = 6;        // Altere para o número de RADIOS em sua enquete 
    $mysql_host = "localhost";    // Altere para o host do MySQL 
    $mysql_senha = "";    // Altere para a senha do MySQL 
    $mysql_user = "";    // Altere para o usuário do MySQL 
         
    // Para utilização com imagem com gráfico 
         
    // $imagem = "grafico.gif"; 
             
         
    // NÃO ALTERE MAIS NADA 
     
    $radios++; 
     
    $conexao = mysql_connect($mysql_host,$mysql_user,$mysql_senha); 

    for ($valor=1;$valor<$radios;$valor++) { 
         
        $consulta[$valor] = "SELECT * FROM enquete where opcao=$valor"; 

    } 
     
    mysql_select_db($db); 

    $resultado_votos = mysql_query($consulta[$escolha]); 
     
    while ($linha = mysql_fetch_row($resultado_votos)) { 
     
        $voto = $linha[2]; 
         
    } 
         
    $new_votos = $voto + 1; 
     
    $update = "UPDATE enquete SET votos='$new_votos' where opcao='$escolha'"; 
     
    mysql_query($update); 
     
    for ($valor=1;$valor<$radios;$valor++) { 
             
        $resultado[$valor] = mysql_query($consulta[$valor]); 
    } 
     
    // $linha[0] = Opção 
    // $linha[1] = Descrição 
    // $linha[2] = Votos 
     
    for ($valor=1;$valor<$radios;$valor++) { 
         
        while ($linha = @mysql_fetch_row($resultado[$valor])) { 
             
            $opcao[$valor] = $linha[1]; 
            $votos[$valor] = $linha[2]; 
            $total_votos += $linha[2]; 
             
        } 
         
         

    }             


    echo "<table width='280' border=1 cellspacing=0 cellpadding=0n"; 
    echo "bordercolorright=#ffffff bordercolordark=#ffffff BORDERCOLOR=#000000>n"; 
    echo "<tr>n"; 
    echo "<td bgcolor='#c0c0c0'><font face='Verdana' size='2'><b>Opção</td>n"; 
    echo "<td bgcolor='#c0c0c0'><font face='Verdana' size='2'><b>Votos</td>n"; 
    echo "<td bgcolor='#c0c0c0'><font face='Verdana' size='2'><b>%</td>n"; 
     
    // Para utilização com imagem com gráfico 
     
    // echo "<td bgcolor='#c0c0c0'><font face='Verdana' size='2'><b>Gráfico</td>n"; 
     
    echo "</tr>n"; 

    for ($valor=1;$valor<$radios;$valor++) { 
     
        $porcent[$valor] = $votos[$valor] * 100 / $total_votos; 
        $porcent[$valor] = number_format($porcent[$valor],2); 
     
     
        echo "<tr><td bgcolor='#c0c0c0'><font face='Verdana' size='1'><b>$opcao[$valor]</td>n"; 
        echo "<td bgcolor='#dcdcdc' align='center'><font color='#FF0000' face='Verdana' size='1'>$votos[$valor]</td>n"; 
        echo "<td bgcolor='#dcdcdc' align='center'><font color='#0000FF' face='Verdana' size='1'>$porcent[$valor]%</td>n"; 
         
        // Para utilização com imagem para gráfico 
     
        // $width_img[$valor] = (int) $porcent[$valor]; 
        // echo "<td valign='center' bgcolor=#dcdcdc><img src='$imagem' height='10' width='$width_img[$valor]'></td>n"; 
         
         
        echo "</tr>"; 
     
     
    } 
     
    echo "<tr><td bgcolor='#c0c0c0'><font face='Verdana' size='2'><b>TOTAL</td>n"; 
    echo "<td bgcolor='#dcdcdc' align='center'><font color='#FF0000' face='Verdana' size='2'>$total_votos</td>n"; 
    echo "<td bgcolor='#dcdcdc' align='center'><font color='#0000FF' face='Verdana' size='1'>&nbsp;&nbsp;</td>n"; 
     
    // Para utilização com imagem para gráfico 
         
    // echo "<td valign='center' bgcolor=#dcdcdc><font face='Verdana' size='1'>&nbsp;</td>n"; 
             
    echo "</tr>"; 
     
    echo "</table>n"; 
         

}     

else { 

?> 
<FORM ACTION="<? $PHP_SELF ?>" METHOD=POST> 
    <INPUT TYPE="radio" NAME="escolha" VALUE="1">LinuX<BR> 
    <INPUT TYPE="radio" NAME="escolha" VALUE="2">Windows<BR> 
    <INPUT TYPE="radio" NAME="escolha" VALUE="3">BeOS<BR> 
    <INPUT TYPE="radio" NAME="escolha" VALUE="4">OS/2<BR> 
    <INPUT TYPE="radio" NAME="escolha" VALUE="5">Mac OS<BR> 
    <INPUT TYPE="radio" NAME="escolha" VALUE="6">Outro<BR> 
<INPUT TYPE="Submit" value="Submeter"> 
</FORM> 
<? 
} 
?> 
