#!/usr/bin/perl

print "Apagando fila de mensagens do Exim.......";

if(!system("rm -d -f /var/spool/exim/input/*")){
print "[Ok]\n";
}else{
print "[Erro]";
}