------------------- fragmento HTML --------------------- 
<form name="nomedoformulario"> 
Estado: 
<select size="1" name="campo_estado" onChange="reloadIFrame();"> 
<option value="1" >AC</option> 
<option value="2" >AL</option> 
<option value="3" >AM</option> 
<option value="4" >AP</option> 
<option value="5" >BA</option> 
<option value="6" >CE</option> 
<option value="7" >DF</option> 
<option value="8" >ES</option> 
<option value="9" >GO</option> 
<option value="10" >MA</option> 
<option value="11" >MG</option> 
<option value="12" >MS</option> 
<option value="13" >MT</option> 
<option value="14" >PA</option> 
<option value="15" >PB</option> 
<option value="16" >PE</option> 
<option value="17" >PI</option> 
<option value="18" >PR</option> 
<option value="19" >RJ</option> 
<option value="20" >RN</option> 
<option value="21" >RO</option> 
<option value="22" >RR</option> 
<option value="23" >RS</option> 
<option value="24" >SC</option> 
<option value="25" >SE</option> 
<option value="26" >SP</option> 
<option value="27" >TO</option> 
</select> 
<script language=JavaScript> 
function reloadIFrame() { 
    var estado_id = nomedoformulario.campo_estado.value; 
    window.open("cidades.php?estado_id=" + estado_id,"cidades"); 
} 
</script> 
<iframe frameborder="no" name="cidades" src="cidades.php" width="300" height="52" border="0" marginwidth="0" marginheight="0" scrolling="no"> 
</iframe> 
</form> 
------------------- fragmento HTML --------------------- 

Vc então cria um script chamado cidades.php para listar as cidades que existem de acordo com o estado que o usuário selecionar 
----------------- cidades.php -------------- 
<select name="campo_cidade"> 
<?php 
if($estado_id != '') 
{ 
    //  ..... conexão com o DB blá blá blá .....// 
    $sql = "SELECT * FROM tabela WHERE estado_id='$estado_id' ORDER BY nome_cidade"; 
    $sql_result = mysql_query($sql); 
    while ($row = mysql_fetch_array($sql_result)) 
    { 
        echo '<option value="'.$row['cidade_id'].'">'.$row['nome_cidade'].'</option>'; 
    } 
} 
?> 
</select> 
-------------------------------------------- 
