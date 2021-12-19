<?
  /*
    ###################################################
    #Função: DataValida revolution                    #
    #Data: 26/09/2002                                 #
    #Desenvolvedor: MadMax (robbsom@hotmail.com)      #
    #Versão: 2.0                                      #
    #Obs.: Por favor caso seja constatado ou executado#
    # alguma melhoria nesta função, favor me contactar#
    #Caso esteja instalado o PHP4 em seu computador   #
    #favor verificar no PHP.INI  se clausula          #
    # register_globals está como "ON"                 #
    ###################################################
  */

  function DataValida($dt) 
     { 
       @$data=explode("/","$dt");

       // verifica se a data é valida! 
       @$res=checkdate($data[1],$data[0],$data[2]); 
       if ($res == 1)
          {
            echo '<font color = blue><b> A Data informada é valida '. $data[0] . "/" .$data[1] . "/" . $data[2].'</b></font>';
          }
       else 
          { 
            echo '<font color = red><b> A Data informada é Invalida '. $data[0] . "/" .$data[1] . "/" . $data[2].'</b></font>';
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
