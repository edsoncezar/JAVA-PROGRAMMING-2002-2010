<? 
/////////////////////////////////////// 
//// DESENVOLVIDOR POR ROBERT_RSC    /// 
////                                /// 
////      roberto@nutecs.com        /// 
////                                /// 
/////////////////////////////////////// 
//// 
////Este script foi desenvolvido por mim e é para uso livre 
////Qualquer adaptação ou melhoria será bem vinda desde que 
////se respeite os créditos originais 
//////////////////////////////////////////////////////////////////// 
//// 
////Função para pontuação financeira de qualquer número 
////boa para algumas aplicações de integração de comércio eletrônico 
////com agências bancárias onde os valores devem ser passados ora em 
////centavos EX:10,00 (1000) ora em reais (R$ 10,00) 
///////////////////////////////////////////////////////////////////// 
?> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<HTML> 
<HEAD> 
<TITLE>Função para pontuação financeira!</TITLE> 
<META NAME="Generator" CONTENT="PHPSCRIPT"> 
<META NAME="Robert_RSC - roberto@nutecs.com" CONTENT=""> 
<META NAME="Keywords" CONTENT=""> 
<META NAME="Description" CONTENT=""> 
</HEAD> 

<BODY> 
<FORM METHOD=POST ACTION="<?$PHP_SELF?>"> 
<INPUT TYPE="text" NAME="numero" SIZE="20"><BR> 
<INPUT TYPE="submit" VALUE="FORMATAR VALOR"> 
<BR> 
<BR> 
<BR> 

<?IF($numero != ""){ 
$numero2 = pontuar("$numero"); 
ECHO "<B>"."VALOR ORIGINAL = ".$numero."</B><br><BR>"; 
ECHO "<B>"."VALOR FORMATADO = ".$numero2."</B>"; 
}?> 

</FORM> 
</BODY> 
</HTML> 

<?function pontuar($valor){ 
if($valor < 0){ 
    $neg_array = "$valor"; 
    $neg_tmp_array = explode("-",$neg_array); 
    $valor = $neg_tmp_array[1]; 
    $md = "neg";} 
$limpa = str_replace(".","", $valor); 
$limpa2 = str_replace(",","", $limpa); 
$valor = "$limpa2"; 
$size = strlen($limpa); 
if($size == 2){$vt_v = $valor; $valor = "00".$vt_v;} 
if($size == 1){$vt_v = $valor; $valor = "000".$vt_v;} 
$size = strlen($valor); 
if($size > 2){$str_mod = $size - 3; $param = $size -2; $m = 3; $ctrl_str = $str_mod; $mi = 1; 
$sub_count = 1; $count = 0; 
while($count <= $str_mod){$n_array = $valor[$ctrl_str]; 
if($count == 0){$var_temp = $n_array;} 
else 
{if($sub_count == 3){ 
if($ctrl_str == 0){$nvar_temp = $n_array.$var_temp; $var_temp = $nvar_temp; $sub_count = 0;} 
else 
{$nvar_temp = ".".$n_array.$var_temp; $var_temp = $nvar_temp; $sub_count = 0;}} 
else 
{$nvar_temp = $n_array.$var_temp; $var_temp = $nvar_temp;} 
} 
$count++; $sub_count++; $ctrl_str--;} 
$moeda = "R$ "; 
$c1 = $valor[$str_mod+1].$valor[$str_mod+2]; 
if($md == "neg"){ 
$valor_final = $moeda."-".$var_temp.",".$c1;} else {// PARA REMOVER O R$ REMOVA A VAR ($moeda) 
$valor_final = $moeda.$var_temp.",".$c1;}// PARA REMOVER O R$ REMOVA A VAR ($moeda) 
$var_temp = $valor_final;} 
return $valor_final;} 
?> 