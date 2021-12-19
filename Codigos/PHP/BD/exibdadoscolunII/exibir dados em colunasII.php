<? 

/* 

Mostra os dados da seguinte forma: 

dado1      dado4      dado7 
dado2      dado5      dado8 
dado3      dado6      dado9 

-------------------------------------------------- 

Autor: Pablo Costa 
Email: pablo@cbsp.com.br 


No exemplo abaixo o script espera existir um banco de dados chamado 
cadastro com uma tabela chamada usuarios contendo o campo nome: 

Veja ele em acao no endereco: 
http://www.example.com/grid.php?colunas=3 
ps: vc pode mudar o parametro do numero de colunas 


Pode haver outras maneiras mais elegantes de se 
fazer isto, mas quando fiz saiu assim :-) 


*/ 


    $hostname = "localhost"; 
    $username = "root"; 
    $password = "senha"; 
    $dbname   = "cadastro"; 

    MYSQL_CONNECT($hostname, $username, $password) OR DIE("Nao pude conectar"); 
    @mysql_select_db( "$dbname") or die( "Nao pude selecionar o banco de dados"); 

    $query = "select nomes from usuarios"; 
    $result = MYSQL_QUERY($query); 


    if(!isset($colunas)) $colunas = 3; 

    $i=1; 
    while ($row = mysql_fetch_array($result)){ 
        $data[$i] = $row["nome"]; 
        $i++; 
    } 

    $usado = array();; 

    $salto = intval(count($data)/$colunas); 

    $test  = $colunas * $salto; 
    $total = count($data); 


    if ($test < $total){ 
        while( ($colunas * $salto) < $total ){ $salto++; } 
    }                                                              
    function printtd($ponto){ 
        global $data,$usado, $salto, $colunas; 
        for($c = 0 ; $c < $colunas; $c++ ){ 
            if(!empty($data[$ponto])) print "\t<td>$data[$ponto] ($ponto)</td>\n"; 
            $ponto +=  $salto; 
            $usado[$ponto] = $ponto;; 
        } 
    } 
    ?> 

<table> 
<tr> 
    <? 

    for($j = 1; $j < $i; $j++){ 

        if(empty($usado[$j])){ 
            printtd($j); 
            print "\n</tr>\n<tr>\n"; 
        } 
    } 

    $resto = $j % $colunas; 
    if( $resto != 0) print "\n</tr>"; 

    ?> 

</table>    
