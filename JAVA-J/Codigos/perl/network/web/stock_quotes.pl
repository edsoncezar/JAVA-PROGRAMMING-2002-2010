#!/usr/bin/perl -w
# Steven Rutter <scribe@ziplip.com>

use IO::Socket;
use strict;

if ($#ARGV < 0) { die "usage: $0 <symbol>\n" }

my $symbol = $ARGV[0];
my $server = 'quote.yahoo.com';
my $serverPort = '80';
my $get = "/d/quotes.cgi?s=$symbol&f=sl1d1t1c1ohgv&e=.csv";

my $remote = new IO::Socket::INET (
                  Proto=>'tcp',
                  PeerAddr=>$server,
                  PeerPort=>$serverPort,
                  Reuse=>1 ) or die $!;

$remote -> autoflush(1);

print $remote "GET $get HTTP/1.0\n\n";

my $quote;

while ( my $raw = <$remote>) {

  # "USVOE.OB",2.75,"4/12/2000","4:00PM",-0.3125,3.0625,3.160000,2.65625,538100
  # symb, price, lastTrade, lastTradeTime, change, high, low, volume

  if ( $raw =~ /\"/ ) {
    $quote = $raw;
  }
}

close $remote;


$quote =~ s/\"//g;
$quote =~ s/^M//g;
chomp $quote;
my @qarray = split(/,/, $quote);

print "(( $qarray[0] $qarray[1]  $qarray[4] ) ( $qarray[7] -- $qarray[6] ) ( Vol: $qarray[8] ))\n";

