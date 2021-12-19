#!/usr/bin/perl -w
use strict;
open(SENDMAIL, "/var/adm/syslog/mail.log");

my %pairs;
my %from;

while (my $line = <SENDMAIL>) {
 chomp $line;
 my @fields = split(" ", $line);
 next if ( $fields[6] !~ /to=/ && $fields[6] !~ /from=/ );
 if ( $fields[6] =~ /from=/ ) {
  $from{$fields[5]} = "\L$fields[6]";
 }
 else {
  $pairs{"$from{$fields[5]} \L$fields[6]"}++;
 }
}
close(SENDMAIL);

foreach my $key (sort {$pairs{$b} <=> $pairs{$a}} (keys(%pairs))) {
 print "$pairs{$key} $key\n";
}

