<?
########################################################################################
#	- Script Desenvolvido por: Olavo Alexandrino									   #
#	- E-mail: oalexandrino@hotmail.com											       #
#	- Versão: 1.00																	   #
# 	- Versão Original: Diego Mascarenhas 											   #
#	  (http://www.phpbrasil.com/articles/article.php/pagerRow/0/id/245)				   #
########################################################################################


// Consulta
$busca = "SELECT valor FROM tabela"; 

// Números de Registros por página
$registrosPorPagina = 10; 

// Validação para não exibir a página 0
if (empty($pagina)):    $pc = 1; 	else:    $pc = $pagina; endif;

// Retorna os "$registrosPorPagina" até o número passado por "$pagina"
$inicio = $pc - 1; 
$inicio = $inicio * $registrosPorPagina; 

// Pesquisa da página atual
$limite = mysql_query("$busca LIMIT $inicio,$registrosPorPagina"); 

// Número total de registros da tabela
$totalRegistros = mysql_num_rows(mysql_query("$busca")); 

// verifica o número total de páginas 
$numPaginas = $totalRegistros / $registrosPorPagina; 

// Visualização dos dados da Tabela
while (list($valor)=mysql_fetch_row($limite)) 
{ 
    echo "Valor: $valor<br>"; 
} 

// Botões anterior e próximo
$anterior = $pc - 1; 
$proximo =  $pc + 1; 
if ($pc>1):     		echo " <a href='?pagina=$anterior'><< Anterior</a> "; 	endif;
if ($pc<$numPaginas):	echo " <a href='?pagina=$proximo'>Próxima >></a>"; 		endif;


echo "<br><br><br>";

// Links para todas as páginas
if (($totalRegistros%$registrosPorPagina!=0)):
	while($totalRegistros%$registrosPorPagina!=0){$totalRegistros++;}
endif;
echo "<strong>Páginas</strong><br>";
for ($a=1;$a<=$totalRegistros;$a++)
{
	if ($a%$registrosPorPagina==0):
		$link = $a;
		$link /= $registrosPorPagina;	
		if ($link!=$pagina):
			echo " <a href='?pagina=$link".$link."'>$link</a>&nbsp;";
		else:
			echo " ::<strong>$link</strong>::&nbsp;";		
		endif;
		$aux++;
	endif;
}
?>
