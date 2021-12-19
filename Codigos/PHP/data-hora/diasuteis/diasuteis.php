<?php 
/* vim: set expandtab tabstop=4 shiftwidth=4: */ 
// +----------------------------------------------------------------------+ 
// | PHP version 4 - SAM Reports!                                         | 
// +----------------------------------------------------------------------+ 
// | Copyright (c) 1997-2003 The PHP Group                                | 
// +----------------------------------------------------------------------+ 
// | This source file is subject to version 2.0 of the PHP license,       | 
// | that is bundled with this package in the file LICENSE, and is        | 
// | available through the world-wide-web at                              | 
// | http://www.php.net/license/2_02.txt.                                 | 
// | If you did not receive a copy of the PHP license and are unable to   | 
// | obtain it through the world-wide-web, please send a note to          | 
// | license@php.net so we can mail you a copy immediately.               | 
// +----------------------------------------------------------------------+ 
// | Authors: Marcelo Pereira Fonseca da Silva <marcelopfs@yahoo.com.br>  | 
// +----------------------------------------------------------------------+ 
// $ Id : 0.1 

/** 
* Script criado para contar quantos dias 
* úteis que há no mês atual 
* 
* Não inclui suporte para feriados 
* sinta-se livre para adicionar este feature 
* 
*/ 


$mes_mk = htmlentities($_GET['mes']); 
$ano_mk = htmlentities($_GET['ano']); 

$transforma_mes = array ("1" => "Janeiro","2" => "Fevereiro","3" => "Março","4" => "Abril","5" => "Maio","6" => "Junho","7" => "Julho","8" => "Agosto","9" => "Setembro","10" => "Outubro","11" => "Novembro","12" => "Dezembro"); 

for ($x=0;$x<date("t", time());$x++) { 
    if (strtolower(date("l", mktime(0,0,0,$mes_mk,$x,$ano_mk))) != 'saturday' && 
    strtolower(date("l", mktime(0,0,0,$mes_mk,$x,$ano_mk))) != 'sunday') 
        $all++; 
} 

echo "<font face=verdana size=1><b> 
      Total de </b>".$all." <b> 
      dias úteis no mês 
      de ".$transforma_mes[$mes_mk]."</b></font>"; 


?> 