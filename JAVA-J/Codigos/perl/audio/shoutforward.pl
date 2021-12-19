#!/usr/bin/perl

$|=1;

# WolfSkunk ShoutForward
# v0.2 by Kelly "STrRedWolf" Price
# A downsampling restreamer!

### Configuration....
## Where to pull from...
## (Example:  Wolfox Radio)
# $source="http://166.90.143.148:9160/";
$source="http://localhost:8000/166.90.143.148:9160/";

## Where to shout it to.
# Shouting to Icecast goes to the same server,
# but to a Shoutcast server, you need to shout it to server port+1

# Example local icecast server
$destsite='localhost';
$destport=8000;
$pass='put up one yourself';

## Lame Options.
# Default: 16kbps Mono (from sterio)
$lameopt='-b 16 -m m -a';

### Code
use IO::Socket;

print "WSS> Shouting from $source\nto $destsite:$destport...\n";

$out=IO::Socket::INET->new(Proto=>'tcp',
			   PeerAddr=> $destsite,
			   PeerPort=> $destport)
    or die "$!";
$out->autoflush(1);

print $out "$pass\r\n\r\n";
$code=<$out>;
print $code;
die "Got from server $code" if($code !~ /^OK/);
print "WSS> We're in!\n";
# while(<$out>)
# {
#    print "WSS> $_";
#    tr/\r\n//d;
#    last if(/^$/);
# }

print $out "icy-name: [[[WolfFox Radio]]] Charm's Mix Party! Herm Haven Mix-Dance-Techno/Goa\r\n";
print $out "icy-url: http://localhost:8000/\r\n";
print $out "icy-pub: 0\r\n";
print $out "icy-genre: Test\r\n";
print $out "icy-br: 20\r\n";

print $out "\r\n\r\n";

open(IN,"mpg123 -b 2048 -s $source | lame $lameopt -x -s 22.05 - - |");

while(1) { $_=<IN>; print $out $_; }

close $out;
close IN;


