#!/usr/bin/perl -w
# Frequency Analyzer by Will Stockwell (waldo@cyberspace.org)
# Big Willy on perlmonks.org
# Thanks to japhy for the split //, lc business
# Thanks to Adam for the $i catch

my %letters;

while (<STDIN>) {
        $letters{$_}++ for split //, lc;
}

foreach (sort keys %letters) {
        if($_ eq "\n") { 
                print "\\n";
        } elsif($_ eq ' ') {
                print "<space>";
        } elsif($_ eq "\t") {
                print "\\t";
        } else {
                print "$_";
        }
        print " = $letters{$_}\n";
}
 

