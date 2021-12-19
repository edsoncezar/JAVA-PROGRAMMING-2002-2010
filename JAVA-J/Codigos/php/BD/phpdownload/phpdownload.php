<?
/*
Criando ambiente no MySQL:

mysql> create database phpdownload;
mysql> connect phpdownload;
mysql> CREATE TABLE contador (
    -> arquivo VARCHAR(100) PRIMARY KEY NOT NULL,
    -> hits    INT DEFAULT 1
    -> );

mysql> GRANT ALL PRIVILEGES ON phpdownload.* TO phpdown@localhost
    -> IDENTIFIED BY 'uaubaby' WITH GRANT OPTION;
mysql> flush privileges;
*/

chdir('./publico'); // entrar no diretório público

if (!is_file($arquivo)) { // se o arquivo não é arquivo ...
    echo "Erro: arquivo $file não encontrado !";
    exit; // ... aborte o programa
}

$con = mysql_connect('localhost','phpdown','uaubaby');
mysql_select_db('phpdownload');

$consulta = "SELECT arquivo,hits FROM contador WHERE arquivo='$arquivo'";
$res = mysql_query($consulta,$con);

/*
  certificar se o arquivo já foi clicado alguma vez. Se o arquivo ainda
  não consta em nossa tabela, vamos inserir um novo registro, caso contrário
  vamos somar um hit ao registro existente
*/

if ($saida = mysql_fetch_array($res)) { // registro existe
    $hit = ++$saida[1]; // some 1 (um) ao número de cliques
    $update = "UPDATE contador SET hits=$hit WHERE arquivo='$arquivo'";
    mysql_query($update,$con); // atualize o registro
} else {
    $insert = "INSERT INTO contador (arquivo) VALUES ('$arquivo')";
    mysql_query($insert,$con); // insira o novo arquivo na tabela. O valor padrão
                             // de hit é 1 (um)
}

$tamanho = filesize($arquivo); // pega o tamanho do arquivo em bytes 

// enviar os cabeçalhos HTTP para o browser
header("Content-Type: application/save"); 
header("Content-Length: $tamanho");
header("Content-Disposition: attachment; filename=$arquivo"); 
header("Content-Transfer-Encoding: binary"); 

// abrir e enviar o arquivo
$fp = fopen("$arquivo", "r"); 
fpassthru($fp); 
fclose($fp); 
?>
