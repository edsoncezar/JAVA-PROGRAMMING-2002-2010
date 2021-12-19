<? 
$conect = mysql_connect("hostdobd","usuario","senha"); 

?> 
<select class="select" name="datapes"> 

<option value="">Todos os dias</option> 

<? 

    $querydata = mysql_db_query("bd","SELECT data_tirada FROM fotos GROUP BY data_tirada ORDER BY data_tirada DESC", $link); 

    $afetadata = mysql_num_rows($querydata); 

    while($vetordata = mysql_fetch_array($querydata)){ 
       $datacerta = $vetordata["data_tirada"]; 
     
       ?><option value="<? print $datacerta; ?>"> <? print $datacerta; ?></option><?    

    } 

?> 
</select> 