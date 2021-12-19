<?PHP 

# ---------- Autor ----------------------------------> 
# 
# Script Desenvolvido por Anderson Brito em 18/12/2001 
# Nicks: Unixer, Anderson-RJ ou WebServ 
# ICQ: 43003071 
# Email: anderson-rj@linuxbr.com.br ou anderson@linuxclub.com.br 
# Site: http://www.linuxclub.com.br 
# ---------- Copy Right -----------------------------> 
# 
# Este Script pode ser usado e modificado onde você quiser!     
# Só quero que quando coloque este script em seu site ou em outros sites! 
# coloquem o meu nome corretamente e respeite a minha autoria deste! 
# Eu quero ajudar e acabam roubando o meu script e colocando o próprio 
# nome e nick! O que eu vi acontecer com o meu script de Saudações ao usuário 
# que trata de Bom-dia Boa Tarde Boa noite de acordo com o horário! 
# Um Grande Abraço a Todos! Anderson! 
# 
# ----------- Sobre o Script -----------------------> 
# 
# Script de Login de Session! 
# A session é registrada somente enquanto o usuário esta conectado ao site ou esta com o 
# navegador aberto apos isso terá que se Logar novamente usando o seu nome de 
# logim e sua senha! 
# antes cadastrados no formulário de cadastramento! E armazenado no banco de 
# dados ou arquivo no servido! 
# Este script trás um exemplo de como fazer um session de login usando banco de dados mysql 
# Qualquer duvida envie um e-mail! 
# 
# ----------------------------------------------------> 

if ($submit){ /* Se o formulário for enviado ele entra nesta parte do script senão ele ira mostrar o          
                        formulário novamente até ser enviado corretamente!! */ 


if ((!$usuario) || (!$senha)){ 

        $erro = "Desculpe! Mas você deixou algum campo em branco no formulário."; 
        
    }else{ 
         
        $bd_host = "host_do_banco_de_dados";      // Endereço do servidor mySQL pode ser o localhost 
        $bd_user = "usuario_do_bd";                  // Seu Login no mySQL 
        $bd_pass = "senhado_do_bd";               // Sua Senha no mySQL 
        $bd_bd = "nome_do_banco_de_dados";        // Nome do Banco de Dados 

        /* Conecta ao Banco de Dados */ 
        $conectar = mysql_connect($bd_host, $bd_user, $bd_pass) or die (mysql_error()); 

        /* Seleciona o Banco de dados na conexão do BD */ 
        mysql_select_db($bd_bd, $conectar); 

        /* Verifica se existe usuários com aquela senha digitada!! */ 
        $sql = mysql_query("SELECT * FROM usuarios WHERE usuario='$usuario' and senha='$senha'"); 

        /* Conta o numero de usuários com aquela senha */ 
        $total = mysql_num_rows($sql); 

        if ($total == 1){   /* Caso o numero de usuário com aquela senha for igual a 1 entra neste if */ 

            session_start("usuarios"); /* A session_start deve estar antes de qualquer codigo senao dará erro !!                               e ele pode ter qualquer nome  */ 
             
            session_register("usuario","senha"); /* Registra o nome de usuário e a senha caso esteja correto */ 

            header("Location: ./site_protegido.php"); /* Redireciona o usuário para o php protegido */ 

            exit;  /* Finaliza este script aqui */ 

         
        /* 
            No Topo do PHP protegido que o usuário será redirecionado deve-se colocar isto para evitar engraçadinhos querendo pular a proteção! 
             
            if(!(session_is_registered("usr") AND session_is_registered("pass"))){   
                
               echo "Area somente para usuários cadastrados!"; 
             
            }else{ 

               # Mostra o conteudo do site para o usuário !!!     

            } 
            
        */ 


        }else{ 
             
            $erro = "Desculpe! Mas você não é cadastrado ou esqueceu sua senha !! Cadastre-se Já!!."; 

        } 
    } 
} 
if($erro){ /* Se existir algum erro ele entra neste if e mostra o erro antes de mostrar o formulário novamente */ 
    echo $erro; 
} 
?> 
  <form name="form" action="login_em_session.php" method="POST" > 
  <p>Usuario: 
    <input type="text" name="usuario"> 
    Senha: 
    <input type="text" name="senha"> 
   <input type="submit" name="submit" value="ok"> 
  </p> 
  </form> 