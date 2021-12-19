<?php
#################################################
#                                               #
# Script modificado em 22/12/03 por Diogo Gomes #
# Usar esta função é fácil, você define o valor #
# inicial e final no seu menu. Para definir o   #
# inicial, você segue o padrão 1 para 1901 e 50 #
# para 1950 ou 102 para 2002.                   #
# Para o valor final, ele se enquadra a partir  #
# do 0 para 2000 e 2 para 2002.                 #
#                                               #
#################################################
function box($inicio,$fim){
$ano = $fim;
$ano = $ano+100;
$box="<select name=year>\n";
for ($x=$inicio;$x<=$ano;$x++){
    if ($x<=99){
        if ($x<10){
            $y = 190;
        }else{
            $y = 19;
        }
    }else{
        $x -= 100;
        if($x<10){
            $y = 200;
        }else{
            $y = 20;
        }
        $a++;
    }
    $box.="<option value=$y$x>$y$x</option>\n";

if ($a){
	$x += 100;
    $a = 0;
}

}
$box.="</select>";
return $box;
}
$atual=date("y");
echo box('2',$atual);
echo "<br />";
echo box('50','0');
?>