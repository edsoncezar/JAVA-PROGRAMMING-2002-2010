#!/usr/bin/perl -- -*-fundamental-*-

## Note: written in 1996 so I am not sure why I use 
## "BRASS" and "gold". It may have been because I was 
## playing with POV-RAY...
## perl@turnstep.com

use strict;

my $gzcat = "/usr/local/bin/gzcat";
my $LINESPERPAGE = 20;
my $file;

unless ($file = shift) {
  print "What file? ";
  chop($file = <STDIN>);
}

my $cols = shift;
if ($cols<1 or $cols > 20) { $cols=12; }

my $oneline = shift;

if ($file =~ /\.gz$/) {
  open(GOLD, "$gzcat $file|") || die qq[Sorry, can't open "$file": $!\n];
}
else {
  open(GOLD, $file) || die qq[Sorry, can't open "$file": $!\n];
}

print "\n$file:\n";
my $x=0; my $line=0; my $chars=0; my $page=1; my $totalchars=0;
my $brass; my $newline; my $fin; my $all;
while (read(GOLD, $brass, 1)){
  $newline=0;
  if ($fin) { print "    "; $brass=''; }
  else { printf "%3d ", ord($brass); }

  if ($oneline and ord($brass)==10) { $fin=1; }
  $totalchars++; $chars++;
  $all .= $brass;
  if ($x++ > $cols) {
    $x=0;
    print "  ";
    if ($all =~ y/\n/$/) { $newline=1; }
    $all =~ y/\t/=/;
    $all =~ y/\0-\37\177-\377/?/;
    print "$all\n";
    $all = "";
    if ($line++ > $LINESPERPAGE) {
      print " ($totalchars characters) (Page: $page)";
      print "  <<HIT RETURN FOR MORE>> ";
      $line=0;
      $a = <STDIN>;
      if ($page++ > 1 and $a =~ /-/) {
        seek(GOLD, -$chars*2, 1); $totalchars-=$chars*2;
        $page-=2;
      }
      $chars=0;
      print "\n";
    }
  }
$newline and $oneline and exit;
}

## Clean up any extra:
$all =~ y/\0-\37\177-\377/./;
for ($x..$cols) { print "****"; }
print "***    $all\n\n";
print "($totalchars characters)\n";

print "\n\n";


