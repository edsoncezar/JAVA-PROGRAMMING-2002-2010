<?
// Criado por Ítalo Marcelo de Oliveira Costa (italomirante@hotmail.com)
// script totalmente free
 $cone=mysql_connect("localhost","usuario","senha");// host, usuario e senha do mysql
 mysql_select_db("base",$cone);//base é o nome da database
 $sql="show tables";
 echo "<center><font face=Verdana size=2><b><u>TABELAS DA BASE DE DADOS X</u></b></font></center><br>";
 $query=mysql_query($sql,$cone) or die("erro");
 echo "<table>";
 while ($registro=mysql_fetch_array($query))
  {
    	echo "<tr><td><font face=Verdana size=2 color=#333300><B>TABELA => $registro[0]</td></tr>";
	$sql="describe $registro[0]";
	$aux=mysql_query($sql,$cone);
       	while ($atributos=mysql_fetch_array($aux))
   		echo "<tr><td><font face=Verdana size=1>$atributos[0]</td><td><font face=Verdana size=1>| $atributos[1]</td><td><font face=Verdana size=1>| $atributos[3]</td></tr>";        
  }
 echo "</table>";
?>