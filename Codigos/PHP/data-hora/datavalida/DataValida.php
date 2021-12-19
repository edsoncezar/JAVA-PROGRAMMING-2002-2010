<?
  /*
    ###################################################
    #Função: DataValida                               #
    #Data: 10/09/2002                                 #
    #Desenvolvedor: MadMax (robbsom@hotmail.com)      #
    #Versão: 1.0                                      #
    #Obs.: Por favor caso seja constatado ou executado#
    # alguma melhoria nesta função, favor me contactar#
    #Caso esteja instalado o PHP4 em seu computador   #
    #favor verificar no PHP.INI  se clausula          #
    # register_globals está como "ON"                 #
    ###################################################
  */

  function DataValida($dt) 
     { 
         $anovalido = false; $mesvalido = false; $diavalido = false;
         $formata = explode ("/", $dt);
         if ((is_numeric(@$formata[2])) and (@$formata[2] > 1900))
            {
              $anovalido = true;
              if ((is_numeric($formata[1])) and ($formata[1] <= 12))
                 {
                   $mesvalido = true;
                   if (is_numeric($formata[0]))
                      {   
                        switch($formata[1])
                           {  
                             case 01:
                                    if ($formata[0] <= 31)
                                        {
                                        $diavalido = true;}
                                         break;
                             case 02:
                                    if ($formata[0] <= 29)
                                        {
                                        $diavalido = true;}
                                        break;
                             case 03:
                                    if ($formata[0] <= 31)
                                        {$diavalido = true;}
                                        break; 
                             case 04:
                                    if ($formata[0] <= 30)
                                        {$diavalido = true;}
                                        break;                                                                                 
                             case 05:
                                    if ($formata[0] <= 31)
                                        {$diavalido = true;}
                                        break; 
                             case 06:
                                    if ($formata[0] <= 30)
                                        {$diavalido = true;}
                                        break; 
                             case 07:
                                    if ($formata[0] <= 31)
                                        {$diavalido = true;}
                                        break; 
                             case 08:
                                    if ($formata[0] <= 31)
                                        {$diavalido = true;}
                                        break; 
                             case 09:
                                    if ($formata[0] <= 30)
                                        {$diavalido = true;}
                                        break;
                             case 10:
                                    if ($formata[0] <= 31)
                                        {$diavalido = true;}
                                        break; 
                             case 11:
                                    if ($formata[0] <= 30)
                                        {$diavalido = true;}
                                        break; 
                             case 12:
                                    if ($formata[0] <= 31)
                                        {$diavalido = true;}
                                        break;  
                           } 
                    
                        }
                   }
              }
         if (($anovalido == true) && ($mesvalido == true) && ($diavalido == true))
               {
                 echo '<font color = blue><b> A Data informada é valida '. $formata[0] . "/" .$formata[1] . "/" . $formata[2].'</b></font>';
               }
         else
               {
                 echo "<font color = red><b> A data informada é invalida, Verifique!! </b></font>";
               }
     }
?>

<?if (@$acao =='verificar')
     {
       if (!($data))
          {
            echo '<b>Favor informar uma data no formato dd/mm/aaaa </b>';
            exit;
          }
       else
          {   
            DataValida($data);
          }
     }    
?>
     
<html>
<head><title> Função de Verificação de Datas [by MadMax] </title></head>     
<body>
<form method="POST" action="<?echo $PHP_SELF;?>?acao=verificar">
Informe a Data(dd/mm/aaaa)<BR>
<input type="text" size="20" name ='data'>
<br>
<input type="submit" value="Verificar Data" >
</form>
</body>
</html>
