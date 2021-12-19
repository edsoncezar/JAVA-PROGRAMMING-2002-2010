#!/usr/bin/perl

#############################################
################Sem queda 1.0################
#############################################
#   Checa o Nivel de Processamento do CPU.  #
# E o reinicia em caso de sobrecarregamento #
# As ações do mesmo são enviadas por e-mail #
#     para o Administrador do Sistema.      #
# 					    #
#    Recomendo que o script seja agendado   #
#         na crontab do Sistema             #
#############################################
#Escrito por Diego Silva(djspeed@bol.com.br)#
#############################################

# E-mail do Administrador do Sistema
$emailadmin = "djspeed\@bol.com.br";
# não tire a \ antes do @

# Load Average Máximo
$loadaverage = "10.0";

# Reiniciar o Sistema quando o load average máximo for atingido?
# 1 para sim e 0 para não
$reiniciar = "1";

# Path do SendMail
$sendmail = "/usr/sbin/sendmail";


#############################################
###########Não Mexa Em mais Nada#############
#############################################

print "=================================\n";
print "==========Sem Queda V1.0=========\n";
print "=================================\n";
print "Verificando o Sistema....\n\n";

($buf1,$buf2,$buf3)=&get_uptime_info;
print "Load Average Maximo: $loadaverage\n";
print "Load Average Atual : $buf2\n\n";
$hostname = qx/hostname/;

if($buf2 < $loadaverage) {
print "Sistema trabalhando normalmente\n";

print "=================================\n";
print "Sistema Escrito por Diego A. Silva\n";
print "E-mail de Contato: djspeed\@bol.com.br\n";
print "=================================\n"
}else{
print "Sistema sobrecarregado....\n";
print "Enviando e-mail ao administrador...\n";

open(MAIL,"|$sendmail -t");
print MAIL "To: $emailadmin\n";
print MAIL "From: $emailadmin\n";
print MAIL "Subject: Sistema Sobrecarregado [Sem Queda 1.0]\n\n";
print MAIL "Caro Administrador,\n";
print MAIL "O Sistema de $hosname está sobrecarregado.\n";
print MAIL "Trabalhando atualmente com o Load Average de $buf2.";
if($reiniciar eq"1"){
print MAIL "O Sistema está sendo reiniciado.\n";
}
print MAIL "\nSem Queda V1.0";
close (MAIL);


print "Reiniciando o Sistema...";
system("reboot");
}

#############################################

sub get_uptime_info {
my ($uptime,$load,$users,$res,@buf,$buf);

  if ($^O=~ /win/i) { 
    $uptime=$load=$users='<small>ERRO</small>';
  } else {

    eval {$res=`uptime`;};

    if (($@) || ($res eq '') ) {
      $uptime=$load=$users='<small><font color=red>Nao foi possivel detectar</font></small>';
    } else {
      chomp($res);

      if ($res=~ s/\,*\s*load\s*averages*\s*\:*\s*(.*)//i) {
        $buf=$1;
        $buf=~ s/^\s+//;
        @buf=split(/,*\s+/,$buf);
        $load="$buf[0]";
      } else {
        $load='<small>Não foi possivel detectar</small>';
      }
      
      if ($res=~ s/\,*\s*(\d+)\s+user\(*s*\)*//i) {
        $users=$1;
      } else {
        $users='<small><font color=red>Nao foi possivel detectar</font></small>';
      }


      if ($res=~ /up\s*\:*\s*(.*)\,*/) {
        $uptime=$1;
        $uptime=~ s/day/day/igs;
        $uptime=~ s/min/minute/igs;

        $uptime=~ s/(\d+)\:(\d+)/$1 hour\(s\) $2 minute(s)/igs;
      } else {
        $uptime='<small>Nao foi possivel detectar</small>';
      }
    }
  }
  return ($uptime,$load,$users);
}

#############################################