<?PHP 

/************************************************************ 
*                                                            * 
*  Exemplo de paginação de resultados com Oracle 8i            * 
*                                                            * 
*  Autor: Ernani Joppert Pontes Martins                        * 
*                                                            * 
*  Data: 10/05/2001                                            * 
*                                                            * 
*  Este código ensina como usar arrays multidimensionais,    * 
*  usando-as para obter o número de registros e colunas        * 
*  sendo assim tornando a paginação de resultados simples.    * 
*                                                              * 
*  Variáveis recebidas:                                     * 
*                                                              * 
*  $start, mostra de onde os registros devem começar a         * 
*  aparecer.                                                * 
*                                                              * 
************************************************************/ 

// Verificação de Variáveis 
if ((!is_numeric($start)) || (strlen($start) <= 0)) 
{ 
    $start = 1; // No começo deve ser definida como 1. 
} 

if ((!is_numeric($offset)) || (strlen($offset) <= 0)) 
{ 
    $offset = 10; // Coloque aqui o limite de resultados por página. 
} 

// Conectando-se com o Banco de Dados. 
$conn = OCILogon("scott","tiger","arvore") or die ("Não foi possível logar-se na Base de Dados."); 

// Montando a query SQL. 
$sql = "SELECT nome, cidade FROM vw_paginacao"; 

// Analisando a query SQL. 
$sql_statement = OCIParse($conn, $sql) or die ("Falha na passagem de cláusula SQL."); 

// Executando a query SQL. 
OCIExecute($sql_statement) or die ("Não foi possível executar a cláusula SQL."); 

// Armazenando a quantidade de colunas do Select. 
$num_columns = OCINumCols($sql_statement, $conn); 

// Atribuindo a quantidade de registros pra 0. 
$row_num = 0; 

// Condição de laço para quando existem registros no Banco de Dados. 
while (OCIFetch($sql_statement)) 
{ 
    $row_num++; //incrementa-se a quantidade de registros. 
    for ($i=1; $i <= $num_columns; $i++) 
    { 
        $aresults[$row_num][$i] = OCIResult($sql_statement, $i); //armazena o resultado da coluna atual em uma array multidimensional. 
    } 
} 

// Libera a query SQL da memória. 
OCIFreeStatement($sql_statement); 

// Desconecta-se do Banco de Dados. 
OCILogoff($conn); 

// Atribui a quantidade total de registros em $rows e colunas em $cols. 
$rows = $row_num; 
$cols = $num_columns; 

// Condição de limitação da exibição dos resultados. 
// $stop recebe um número onde deve ser o limite dos registros. 
if ($rows > ($offset + $start)) 
{ 
    $stop = ($offset + ($start - 1)); 
} 
else 
{ 
    $stop = $rows; 
} 

// Legenda inicial. 
echo "Mostrando " . $offset . " registros a partir do registro " . $start . "<p>"; 

// Condição de exibição dos registros na tela. 
// se acabaram os registros ele não mostra mais nada. 
for($rloop = $start; $rloop <= $stop; $rloop++) 
{ 
    for($cloop = 1; $cloop <= $cols; $cloop++) 
    { 
        $resultado = $aresults[$rloop][$cloop] . " "; 
        if(!empty($resultado)) echo $resultado; 
    } 
    echo "<br>"; 
} 

echo "<p>"; 

// Condições para mostrar na tela os próximos registros e os registros anteriores. 
if ($start > 1) 
{ 
    // Mostra os registros anteriores em forma de link. 
    $back = $start - $offset; 
    echo "<A HREF=\"getrows.php?start=$back\">Anteriores $offset</A>"; 
} 

echo "&nbsp;"; 

if ($stop < $rows) 
{ 
    // Mostra os próximos registros em forma de link. 
    $go = $start + $offset; 
    echo "<A HREF=\"getrows.php?start=$go\">Próximos $offset</A>"; 
} 

echo "<p>"; 

// Mostra o total de registros. 
echo "Total de Registros : <b>" . $rows . "</b>"; 
?> 