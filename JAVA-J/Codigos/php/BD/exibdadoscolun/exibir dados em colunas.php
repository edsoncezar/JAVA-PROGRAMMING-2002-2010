<? 

/* 

Mostra os dados da seguinte forma: 

dado1      dado2      dado3 
dado4      dado5      dado6 
dado7      dado8      dado9 

-------------------------------------------------- 

Autor: Pablo Costa 
Email: pablo@cbsp.com.br 


No exemplo abaixo o script espera existir um banco de dados chamado 
cadastro com uma tabela chamada usuarios contendo o campo nome: 

Veja ele em acao no endereco: 
http://www.example.com/grid.php?colunas=3 
ps: vc pode mudar o parametro do numero de colunas 


*/ 


    $hostname = "localhost"; 
    $username = "root"; 
    $password = "senha"; 
    $dbname   = "cadastro"; 

    MYSQL_CONNECT($hostname, $username, $password) OR DIE("Nao pude conectar"); 
    @mysql_select_db( "$dbname") or die( "Nao pude selecionar o banco de dados"); 


    $query = "select nomes from usuarios"; 
    $result = MYSQL_QUERY($query); 


    /* configure aqui numero de colunas */ 
    if(!isset($colunas)) $colunas = 3; 

    ?> 

<table> 
<tr> 
    <? 

    $i=1; 

    while ($row = mysql_fetch_array($result)){ 

        $resto = $i % $colunas; 

        print "\t<td>$row[nome] ($i)</td>\n"; 

        if( $resto == 0) print "\n</tr>\n<tr>\n"; 

        $i++; 

    }    

             if( $resto != 0) print "\n</tr>"; 

    ?> 

</table>           