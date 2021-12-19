#!/usr/bin/perl

my $login = shift;
my $senha = shift;
if (!$login || !$senha) {
  print "Informe login/senha !\nUse $0 login senha\n\n";
  exit;
}

my $passwd = "/etc/shadow";
my $saida = `grep $login $passwd`;
if ($saida !~ /[A-Z]/i) {
  print "Usuário inexistente !\n";
  exit;
}

my $shadow = "";
open(R,$passwd);
while (<R>) {
  if ( $_ =~ /^$login:/ ) {
    my ($user,$pass,$resto) = split(/:/,$saida,3);
    my $newpass = crypt($senha,substr $user,0,2);
    print "crypt($senha," . substr $user,0,2 . ")\n";
    $shadow .= $user . ':' . $newpass . ':' . $resto;
  } else {
    $shadow .= $_;
  }
} # fim while
close(R);

open(W,"> $passwd");
print W $shadow;
close(W);

