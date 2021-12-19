#!/usr/bin/perl -w

use strict;

my (@lines, @clean, $sig);
my $allrules = "allrules";
my $pigsigs = "pigsigs";
my $delimiter = ",";

open ALLRULES, $allrules || die "Could not open file: $1\n";
while (<ALLRULES>) {
push (@lines, $_);
}

foreach $sig (@lines) {
if ($sig =~ /^#/) {
next;
}
if ($sig =~ (m/(\".*?\")/) ) {
push (@clean,($1, $delimiter));
}
if ($sig =~ (m/(sid.*?;)/) ) {
push (@clean,($1, $delimiter));
}
if ($sig =~ (m/(rev:.*?;)/) ) {
push (@clean,($1, "\n"));
}
}

foreach (@clean) {
s/\"|sid:|rev:|;//g;
open (PIGSIGS, ">>$pigsigs");
print PIGSIGS $_;
}
close (PIGSIGS);

