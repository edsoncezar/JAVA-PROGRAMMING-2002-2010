#!/usr/bin/php -q 
<? 
// Configura os dados de acesso ao banco de dados 
$mysql_dados['usuario'] = "usuario"; 
$mysql_dados['senha']   = "senha"; 
$mysql_dados['host']    = "localhost"; 
$mysql_dados['banco']   = "bancodados"; 
// Configura DELIMITADORES do arquivo 
$delimita = ";"; 
// 
if(!$argv[3]) 
{ 
print "\nUso: $argv[0] arquivo tabela remove[s/n] \n(EX: $argv[0] clientes.txt clientes s (limpa a tabela clientes se existir))\n"; 
        exit; 
} 
print "\n"; 
print "Iniciando transferência para o banco de dados."; 
print "\nDependendo do tamanho do mesmo e da velocidade de sua máquina isso pode demorar alguns minutos.\n\n"; 
// Conectando no MySQL 
$conecta_mysql = @mysql_connect("$mysql_dados[host]","$mysql_dados[usuario]","$mysql_dados[senha]") OR DIE ("Erro 1 - MySQL"); 
@mysql_select_db("dadoscamarajunior") or die ("Erro 2 - MySQL"); 
$arquivo= file("$argv[1]"); 
$qtdecampos = explode("$delimita",$arquivo[0]); 
$qtdecampos = count($qtdecampos); 
// Verificando se a quantidade de campos concide com o banco 
$fields = @mysql_list_fields("dadoscamarajunior", "$argv[2]", $conecta_mysql); 
$mqc = @mysql_num_fields($fields); 
if($mqc != $qtdecampos) 
{ 
        print "\nErro:\nQuantidade de campos no arquivo não corresponde a quantidade de campos no banco de dados, ou a tabela no banco de dados não existe.\n"; 
        exit; 
} 
// Se tabela existir remove o conteúdo caso selecionar opcao s 
if($argv[3]=="s") 
{ 
$remove_sql="DELETE FROM $argv[2]"; 
mysql_query($remove_sql); 
} 
// 
$ac = count($arquivo); 
unset($sql); 
for($x=0;$x<$ac;$x++) 
{ 
$valor_campo = explode("$delimita",$arquivo[$x]); 
$valor_campo[0] = str_replace("\"","",$valor_campo[0]); 
$valor_campo[0] = str_replace('\'','',$valor_campo[0]); 
        $sql = "INSERT INTO $argv[2] VALUES('$valor_campo[0]'"; 
        for($y=1;$y<$qtdecampos;$y++) 
        { 
                $valor_campo[$y] = str_replace("\"","",$valor_campo[$y]); 
                $valor_campo[$y] = str_replace('\'','',$valor_campo[$y]); 
                $sql .= ",'$valor_campo[$y]'"; 
        } 
        $sql .= ");"; 
mysql_query($sql) or die("Erro 3-Mysql"); 
} 
mysql_close($conecta_mysql); 
print "Dados inseridos com êxito\n\n"; 
?> 
