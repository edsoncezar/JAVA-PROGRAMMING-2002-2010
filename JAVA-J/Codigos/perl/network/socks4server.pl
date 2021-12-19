#!/usr/bin/perl

$|=1; # Run this with Faucet.

#use strict;
use IO::Socket;
use Net::hostent;
use bytes;

my $remote=$ARGV[0];
my $buf, $byte, @head, $up, $user, $err, $kidpid, $sock, $op, $server;
my $destip, $destport, $i;

### Read in first bytes of the Socks header.

read(STDIN,$buf,4);
@head=unpack("CCn",$buf);
read(STDIN,$buf,4);
$ip=inet_ntoa($buf);

$user='';
while(read(STDIN,$buf,1)) {
  last unless(ord $buf);
  $user .= $buf;
}

### Do some insanity checking.
$err=91;  $op=-1; # Assume Not OK...
$destip=pack("N",0); $destport=0;

if($head[0] == 4) { # SOCKS 4
  if($head[1] == 1) { # CONNECT
    $sock=IO::Socket::INET->new(Proto=>"tcp",
				PeerAddr=>$ip,
				PeerPort=>$head[2]);
    if($sock) {
      $sock->autoflush(1);
      $err=90;
    }
  } elsif($head[1] == 2) { # BIND
    $destport=$$; $destip=inet_aton($remote);
    $server=IO::Socket::INET->new(Proto=>'tcp',
				  LocalPort=>$$,
				  LocalAddr=>$remote,
				  Listen=>1,
				  Reuse=>1);
    print pack("CCn",0,90,$destport).$destip;
    if($sock=$server->accept()) {
      $sock->autoflush(1);
      if($sock->peerhost eq $ip) {
	$err=90;
      } else {
	close $sock;
      }
    }
    close $server;
  }
}

print pack("CCn",0,$err,$destport).$destip;
exit if($err>90);

die "can't fork: $!" unless defined($kidpid=fork());
if($kidpid) {
  while(read($sock,$byte,1)){
    print STDOUT $byte;
  }
  kill("TERM", $kidpid);
} else {
  while (read(STDIN,$byte,1)) {
    print $sock $byte;
  }
}
