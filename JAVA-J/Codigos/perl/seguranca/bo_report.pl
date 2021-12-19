#!/usr/bin/perl
#
# envia avisos de tentativa de ataques BO
#
$sysadmin = "email\@doroot.com.br";
#
$kiddie = $ARGV[0] || die "Sem IP especificado";
chomp ($kiddie);
#
open (MAIL, "|/usr/lib/sendmail -t $sysadmin") || die "Não pode enviar mail para $sysadmin";
print MAIL "From: <root\@your.system>\nTo: <$sysadmin>\nSubject: BO
Attempt\n\n";
print MAIL "Back Orifice tentando conexão de $kiddie\n\n";
$nslookup = `nslookup $kiddie`;
print MAIL "$nslookup\n\n";
$traceroute = `traceroute $kiddie`;
print MAIL "$traceroute\n\n";
$log = `tail /var/log/fakebo.log`;
print MAIL "$log\n\n";
close (MAIL);
