<?php

//
// == DB Date Cast ============================================================
// Autor: Fosfozol 33g (fosfozol@yahoo.com.br)
//
// Versão: v1.0 @ 21/Ago/2003
//         Desenvolvimento do script em si.
//
// Oque é isso?: Transforme automaticamente datas de bancos de
//               dados MySQL e PostGreSQL para o formato popular
//               brasileiro.
//
// Copyright: GNU General Public License
//            Modifique a vontade para adaptar a sua necessidade.
// ============================================================================
//

  function dbdatecast($date, $db = 'pgsql') {
      $style = (strlen($date) <= 10) ? 'date' : 'timestamp';

      //echo '<!-- formatando data "'.$date.'" com o estilo "'.$style.' '.$db.'" -->';

      switch ($db) {
          case 'pgsql' :

            if($style == 'date') {
                ereg("([0-9]{4})-([0-9]{2})-([0-9]{2})", $date, $splitdate);
                list (,$y,$m,$d) = $splitdate;
                return "$d/$m/$y";
            }else{
                $date = substr($date,0,19);
                ereg("([0-9]{4})-([0-9]{2})-([0-9]{2}) ([0-9]{2}):([0-9]{2}):([0-9]{2})", $date, $splitdate);
                list (,$y,$m,$d,$h,$i,$s) = $splitdate;
                return "$d/$m/$y $h:$i:$s";
            }

          break;
          case 'mysql' :

            if($style == 'date') {
                ereg("([0-9]{4})([0-9]{2})([0-9]{2})", $date, $splitdate);
                list (,$y,$m,$d) = $splitdate;
                return "$d/$m/$y";
            }else{
                ereg("([0-9]{4})([0-9]{2})([0-9]{2})([0-9]{2})([0-9]{2})([0-9]{2})", $date, $splitdate);
                list (,$y,$m,$d,$h,$i,$s) = $splitdate;
                return "$d/$m/$y $h:$i:$s";
            }

          break;
      }

      return $date;
  }

?>
Saída timestamp do MySQL: de '20030821171139' para '<b><?= dbdatecast('20030821171139','mysql'); ?></b>'<br><br>
Saída timestamp do PgSQL: de '2003-08-21 17:11:39 -0300' para '<b><?= dbdatecast('2003-08-21 17:11:39 -0300'); ?></b>'<br><br>
Saída timestamp do PgSQL: de '2003-08-21 17:11:39' para '<b><?= dbdatecast('2003-08-21 17:11:39'); ?></b>'<br><br>
Saída date do MySQL: de '20030821' para '<b><?= dbdatecast('20030821','mysql'); ?></b>'<br><br>
Saída date do PgSQL: de '2003-08-21' para '<b><?= dbdatecast('2003-08-21'); ?></b>'<br><br>
