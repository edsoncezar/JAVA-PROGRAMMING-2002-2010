#!/usr/bin/perl
# ---------------------------------------------------------
# httperl.pl (htt[p]erl)
# 
# Script em Perl usado para simular um servidor web, muito
# bom para quem deseja entender melhor o protocolo de 
# comunicação entre browser e servidor
#
# 
# Programado por Fábio Berbert de Paula 
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
die "Não consegui iniciar o servidor: $!\n" unless $sock;
print "Servidor iniciado ...\n";

# definir o cabeçalho de retorno básico
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

# aceitar conexões TCP
while (my $new_sock = $sock->accept()) {
   while($linha = <$new_sock>) {
      # imprimir o cabeçalho recebido pelo browser do usuário
      print $linha;

      # se linha vazia, então o browser envio câmbio, vamos respondê-lo
      if ($linha!~/[A-Za-z0-9]/) {
         print $new_sock $retorno;
         # fechar conexão com o cliente
         $new_sock = '';
      }
   }
   close(W);
}
close($sock);
