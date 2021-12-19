<?php

$Connection = mysql_connect( "localhost", "user","password");    

if ( !$Connection )
	exit(" Revise seu procedimento => " . mysql_error()); 
    
    
mysql_select_db("dbproduto");  

?>

<html>

<head>
	
	<title>Exemplo de Combos</title>   
      
</head>


<form name="combo" method="post" action="combo.php"> 


<table>    
            <td WIDTH="12%" <b>Produto</b>
    
                <select tabindex="19"  name="combo_produto" style width:250px >
	       
                    <option value="-1">Todos</option>
            
                    <?php combo_produto ( -1 ); ?> <!--Chama pela function-->
         
                </select>	
    
            </td> 
    
</table>

</html>

<?php 

function combo_produto ( $idproduto )  

{   

    global $Connection;

        $sSql  = " SELECT idproduto, produto ";
        $sSql .=   " FROM tbproduto";
        $sSql .=  " ORDER BY produto ";       

        $Result = mysql_query( $sSql, $Connection ); 

        if ( !$Result )
        exit( " Erro : ao Procurar Dados dos produtos = > " . mysql_error());
    
        while( $aResult = mysql_fetch_array( $Result ) )
        
        {
         
            if ( $idproduto == $aResult["idproduto"] )
            print "<option value=\"" . $aResult["idproduto"]  . "\" selected>" . $aResult["produto"] . "</option>\n";
            else
            print "<option value=\"" . $aResult["idproduto"]  . "\" >" . $aResult["produto"] . "</option>\n"; 

        } 

}        

?>
