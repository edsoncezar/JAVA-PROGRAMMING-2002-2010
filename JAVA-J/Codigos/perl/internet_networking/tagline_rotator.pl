#!/usr/bin/perl -w

use strict;

my $debug=0;
my $home='/home/users/somebody';
my $siginfile="$home/sig/sigfile";
my $tearline="  \n";
my $uptime=`uptime`;
my $outfile="$home/\.signature";

open(INFILE,"$siginfile") or die "Can't open $siginfile :$!";
open(OUTFILE,">$outfile") or die "Sorry, but I can't write to $outfile :$!";

my %hash;

while(<INFILE>){
  # build a hash with $. as key and $_ as value
  $hash{$.}=$_;
}

my $rand=rand($.);

my @body;

push (@body, $tearline);
push (@body, qq(  $hash{sprintf("%.0f", ($rand))}));
push (@body, $uptime);

if ($debug){
  print @body;
} else {
  print OUTFILE @body;
}

close INFILE;
close OUTFILE;

