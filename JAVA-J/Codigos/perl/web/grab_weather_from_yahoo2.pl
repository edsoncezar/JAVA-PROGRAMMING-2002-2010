#!/usr/bin/perl

use IO::Socket;

if ($#ARGV < 0) { die "usage: weather.pl <zipcode | city>\n"; }

$server = 'search.weather.yahoo.com';
$serverPort = '80';
$get = '/weather/query.cgi?q=';

$zipcode = $ARGV[0];

$remote = new IO::Socket::INET (
                  Proto=>'tcp',
                  PeerAddr=>$server,
                  PeerPort=>$serverPort,
                  Reuse=>1 ) or die $!;

$remote -> autoflush(1);

print $remote "GET $get"."$zipcode HTTP/1.0\n\n";

while ( $location = <$remote>) {
  if ( $location =~ /Location:/ ) {
    $redir = $location;
    $redir =~ s/Location: //;
  }
}

close $remote;

if ($redir eq '') {
  print "Sorry, unable to gather weather data for $zipcode\n";
  exit;
}

@temp = split(/\//, $redir);
$server = $temp[2];
$get = "\/"."$temp[3]"."\/"."$temp[4]";

$remote = new IO::Socket::INET (
                  Proto=>'tcp',
                  PeerAddr=>$server,
                  PeerPort=>$serverPort,
                  Reuse=>1 ) or die $!;

$remote -> autoflush(1);

print $remote "GET $get HTTP/1.0\n\n";

while ( $raw = <$remote>) {

  if ($raw =~ /Appar Temp:/) {
    $temp = $raw;
  } elsif ($raw =~ /Humidity:/) {
    $humidity = $raw;
  } elsif ($raw =~ /Wind:/) {
    $wind = $raw;
  } elsif (($raw =~ /^<font size=\"-2\" face=\"arial\">.+<\/font><\/td>$/) && ($condFound ne 1)) {
    $conditions = $raw;
    $condFound = 1;
  } elsif ($raw =~ / Forecast<\/title>/) {
    $city = $raw;
  }

}

close $remote;

$conditions =~ s/^<font size=\"-2\" face=\"arial\">//;
$conditions =~ s/<\/font><\/td>$//;
chomp $conditions;

$temp =~ s/<tr><td><font size=-1>//;
$temp =~ s/<\/font><\/td>//g;
$temp =~ s/<td><font size=-1>//;
$temp =~ s/<\/tr>//;
$temp =~ s/:/: /;
$temp =~ s/Appar //;
$temp =~ s/&#176\;/ degrees/;
chomp $temp;

$humidity =~ s/<tr><td><font size=-1>//;
$humidity =~ s/<\/font><\/td>//g;
$humidity =~ s/<td><font size=-1>//;
$humidity =~ s/<\/tr>//;
$humidity =~ s/:/: /;
$humidity =~ s/% /%/;
chomp $humidity;

$wind =~ s/<tr><td><font size=-1>//;
$wind =~ s/<\/font><\/td>//g;
$wind =~ s/<td><font size=-1>//;
$wind =~ s/<\/tr>//;
$wind =~ s/&nbsp\;/ /;
$wind =~ s/:/: /;
chomp $wind;

$city =~ s/<title>//;
$city =~ s/<\/title>//;
$city =~ s/Weather Forecast//;
chomp $city;

print "Weather for $city(( $temp $humidity ) ( $conditions $wind ))\n";
