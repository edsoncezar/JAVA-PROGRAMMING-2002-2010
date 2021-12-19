<?php 

/* 

Instalação... 

CREATE TABLE ts_contadores ( 
  id         char(15) NOT NULL default '',  # id do contador, este número eu uso de alegre, para especificar o contador eu uso o 'cont_of' 
  id_status    char(15) NOT NULL default '1', # '1' para ativado e '0' para desativado 
  cont        char(15) NOT NULL default '0', # o contador, numero inicial é 0 
  cont_of    char(15) NOT NULL default '',  # contador da onde? no meu caso as páginas são números, 
  PRIMARY KEY  (id) 
) TYPE=MyISAM; 


INSERT INTO ts_contadores VALUES ('10','1','0','1546'); # 'contador de ID: 10','ativado','numero inicial 0','contador da página 1546' 

*/?> 

<?php 

// 
// == Contador com db MySQL =================================================== 
// Autor: Tripa Seca @ phpbrasil.com 
// 
// Versão: v0.1 @ 03/Fev/2002 
//         Desenvolvimento do script em si. 
// 
// Copyright: Modifique a vontade para adaptar a sua necessidade. 
// ============================================================================ 
// 

require("db.php"); //Conecte seu db aqui... 

function ts_contador($pagina,$exibir){ 
  $cont_select = "SELECT * FROM `ts_contadores` WHERE 1 AND `cont_of` LIKE '$pagina' LIMIT 1"; 
  $cont_result = mysql_query($cont_select) or die("Erro Query cont_result"); 
  $cont_campo = mysql_fetch_array($cont_result) or die ("Erro Query cont_campo"); 
  if ($cont_campo["id_status"] == 1){ //<--verefica se o contador está ativado, SIM, acrescentar +1 
    $cont_mais1 = $cont_campo["cont"]+1; 
    $cont_update = "UPDATE ts_contadores SET cont='$cont_mais1' WHERE cont_of LIKE '$pagina' LIMIT 1"; 
    $cont_rec = mysql_query($cont_update) or die("Erro Query cont_rec"); 
    if ($exibir == "s") echo $cont_mais1; //<-- 's' exibe o valor do contador e 'n' não exibe 
  }else{ //<--verefica se o contador está ativado, NÃO, não faz nada, apenas imprimi o valor atual. 
  if ($exibir == "s") echo $cont_campo["cont"];//<-- 's' exibe o valor do contador e 'n' não exibe 
  } 
} 

//Exemplo, 'contador 1546','SIM exibe o resultado' 

ts_contador('1546','s'); 

?> 