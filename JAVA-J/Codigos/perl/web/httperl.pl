#!/usr/bin/perl
# ---------------------------------------------------------
# httperl.pl (htt[p]erl)
# 
# Script em Perl usado para simular um servidor web, muito
# bom para quem deseja entender melhor o protocolo de 
# comunica��o entre browser e servidor
#
# 
# Programado por F�bio Berbert de Paula 
# <fabio@vivaolinux.com.br>
#
# Rio de Janeiro, 06 de Janeiro de 2003
# ---------------------------------------------------------

$| = 1;

# criar socket na porta 80 do servidor
use IO::Socket;
my $sock = new IO::Socket::INET (
     LocalPort => '80',
     Type      => SOCK_STREAM,
     Proto     => 'tcp',
     Listen    => 10
);
die "N�o consegui iniciar o servidor: $!\n" unless $sock;
print "Servidor iniciado ...\n";

# definir o cabe�alho de retorno b�sico
my $retorno = "
HTTP/1.1 200 OK
Server: htt[p]erl/1.0 (GNU/Linux)
Connection: close
Content-Type: text/html

<html>
<body>
Uma cortesia de <a href=\"http://www.vivaolinux.com.br\">Viva o Linux</a>.
</body>
</html>
";

# aceitar conex�es TCP
while (my $new_sock = $sock->accept()) {
   while($linha = <$new_sock>) {
      # imprimir o cabe�alho recebido pelo browser do usu�rio
      print $linha;

      # se linha vazia, ent�o o browser envio c�mbio, vamos respond�-lo
      if ($linha!~/[A-Za-z0-9]/) {
         print $new_sock $retorno;
         # fechar conex�o com o cliente
         $new_sock = '';
      }
   }
   close(W);
}
close($sock);
