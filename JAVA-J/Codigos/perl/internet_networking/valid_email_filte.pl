#!/usr/bin/perl -w

#checks for valid email address
#usage validemail <file containing email addresses>
#creates to files: goodmails and badmails

use warnings;
use strict;
use Email::Valid::Loose;
use Net::DNS;

my $resolver=Net::DNS::Resolver->new();

my $addrfile = $ARGV[0];
my ($is_valid, $host, $ip, @goodaddr, @badaddr, $x, $record, @mx);

#custom words that make emails invalid to you
my @custom = qw(
 nospam
 postmaster
 webmaster
);
  

open (EMAILS, "$addrfile");

OUTER: while (<EMAILS>){
 $is_valid="";
 $_ =~ s/\015//;
 chomp $_;

 foreach $x (@custom){
  if (m/$x/){
   push (@badaddr, $_);
   next OUTER;
  }
 }

 #if email is invalid move on
 if (!defined(Email::Valid::Loose->address($_))){
  push (@badaddr, $_);
  next;
 }

 #if email is valid get hostname
 $is_valid = Email::Valid::Loose->address($_);
 if ($is_valid =~ m/\@(.*)$/) {
  $host = $1;
 }
 
 #check for mx record
 @mx=mx($resolver, $host);

 if (@mx) {
  push (@goodaddr, $_); #address is good
  }else{
  push (@badaddr, $_); #address is bad
 }


}
close (EMAILS);

#warning! I will delete existing files as I open them!

open (BADADDR, ">badmails") || die;
foreach $x (@badaddr){
 print BADADDR "$x\n";
}
close (BADADDR);

open (GOODADDR, ">goodmails") || die;
foreach $x (@goodaddr){
 print GOODADDR "$x\n";
}
close (GOODADDR);

