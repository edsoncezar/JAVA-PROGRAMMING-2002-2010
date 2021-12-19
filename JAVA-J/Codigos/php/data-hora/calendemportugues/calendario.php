<?php
/********************************************************************************
*    Classe calend.class.php                                                    *
*                                                                                 *
*     Descriçao : Esta classe imprime um calendário do mes e ano desejado        *
*  os  parâmetros  $mes  e  $ano  são opcionais,  neste caso o método irá        *
*  o calendário do mês atual do servidor.                                        *
*                                                                                *
*                                                                                *
*   Criado Por: Bruno Felipe Cerqueira Silva                                    *
*       E-Mail: bruno@sofolha.com.br                                            *                                                                                *
********************************************************************************/
class calendario {
    var $mes_ext = Array("", "Janeiro", "Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto","Setembro",
                         "Outubro","Novembro","Dezembro");
    function impr_calendar( $mes='', $ano='') {
        $mes = !$mes ? date('m') : $mes;
        $ano = !$ano ? date('Y') : $ano;
        $estiloMes    = "font-family: Verdana, Arial, Helvetica, sans-serif; font-size: 12px; color: #FFFFFF; background-color: #003366;";
        $estiloSemana = "font-family: Verdana, Arial, Helvetica, sans-serif; font-size: 11px; color: #000000; background-color: #CCCCCC;";
        $estiloDia    = "font-family: Verdana, Arial, Helvetica, sans-serif; font-size: 12px; color: #0033FF; background-color: #E6E6E6;";
        $estiloDiaAtual    = "font-family: Verdana, Arial, Helvetica, sans-serif; font-size: 12px; color: #FFFFFF; background-color: #003366;";

        print("<table border='1' align='center' cellpadding='0' cellspacing='0'>
                    <tr>
                      <th colspan='7' style='$estiloMes'>"    . $this->mes_ext[($mes)] . "</th>
                    </tr>
                    <tr style='$estiloSemana'>
                      <th>&nbsp;&nbsp;Dom&nbsp;&nbsp;</th>
                      <th>&nbsp;&nbsp;Seg&nbsp;&nbsp;</th>
                      <th>&nbsp;&nbsp;Ter&nbsp;&nbsp;</th>
                      <th>&nbsp;&nbsp;Qua&nbsp;&nbsp;</th>
                      <th>&nbsp;&nbsp;Qui&nbsp;&nbsp;</th>
                      <th>&nbsp;&nbsp;Sex&nbsp;&nbsp;</th>
                      <th>&nbsp;&nbsp;Sab&nbsp;&nbsp;</th>
                    </tr>");
        $dia = 1;
        while ( $dia <= cal_days_in_month(1, $mes, $ano) ) {
            print("<tr>");
            for ( $i = 0; $i <= 6; $i++ ) {
                if ( $dia <= cal_days_in_month(1, $mes, $ano) ) {
                    if ( date('w', mktime(0,0,0,$mes,$dia,$ano)) == $i ) {
                        $dia = strlen($dia) <= 1 ? 0 . $dia : $dia;
                        $mes = strlen($mes) <= 1 ? 0 . $mes : $mes;
                        if ($dia == date('d')) {
                        print("<td align='center' style='$estiloDiaAtual'>" . $dia++ . "</td>");
                        }
                        else {
                        print("<td align='center' style='$estiloDia'>" . $dia++ . "</td>");
                        }
                    } else
                        print("<td></td>");
                }
            }
            print("</tr>");
        }
        print("</table>");
    }
  }

/*
    Exemplo de Utilização
*/

$objCalendario = new calendario;
$objCalendario->impr_calendar(); //imprime o calendario do mês corrente
?>
