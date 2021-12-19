#!/usr/bin/perl -w #Neil H Watson (watson-wilson.ca) on Wed May 8 13:28:51 EDT 2002 #usage: sender -f -t -s -b use strict; use warnings; use MIME::Entity; use Getopt::Std; my ($body, $key, @to, $top); my %opt= ( #set default options f => "x", t => "x", s => "x", b => "x"); getopts("f:t:s:b:", \%opt); # check usage foreach $key (keys %opt){ chomp $opt{$key}; if ($opt{$key} =~ m/^x$/){ die " Error: No $key value entered. Usage: sender -f -t -s -b \n\n"; } } my $from = $opt{f}; my $to = $opt{t}; my $subject = $opt{s}; my $bodyfile = $opt{b}; open BODY, "$bodyfile" or die "Could not open $bodyfile\n"; while (){ $body = $body . $_; } if ($to =~ m/\@/){ # if $to is an address $top = MIME::Entity->build(Type => "text/html", Encoding => "7bit", From => "$from", Bcc => "$to", Subject => "$subject", Data => "$body"); open MAIL, "|/usr/sbin/sendmail -t -oi -oem" or die "Error at /usr/sbin/sendmail $!"; $top->print(\*MAIL); close MAIL; } else { # assume $to is a file open TO, "$to" or die "Could no open from file: $to"; while (){ # get addresses chomp ($_); push (@to, $_); } $to = join ",", @to; # add addresses to one string #print "$to\n"; $top = MIME::Entity->build(Type => "text/html", Encoding => "7bit", From => "$from", Bcc => "$to", Subject => "$subject", Data => "$body"); open MAIL, "|/usr/sbin/sendmail -t -oi -oem" or die "Error at /usr/sbin/sendmail $!"; $top->print(\*MAIL); close MAIL; } 