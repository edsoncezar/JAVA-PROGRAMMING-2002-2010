#!/usr/bin/perl -w

use strict;
use LWP::Simple;

my (@numbers, %normals, %powers);

my $content;

unless (defined ($content = get('http://www.powerball.com/results/pbhist.txt'))) {
	die "Cannot get PB history.\n";
}

@numbers = split /\n/, $content;

my @data;

foreach my $line (@numbers) {
	next if ($line =~ /^!/);
	@data = split(/\s/, $line);
	shift @data;        # throw away the date
	$powers{pop @data}++;

	foreach (@data) {
		$normals{$_}++;
	}
}

print "Normal Pick Rate:\n\n";

my @norm_sort = sort { $normals{$a} <=> $normals{$b} } keys %normals;

foreach (@norm_sort) {
	print "$_ :\t($normals{$_})\t", "*" x $normals{$_}, "\n";
}

print "\nPower Pick Rate:\n\n";

my @power_sort = sort { $powers{$a} <=> $powers{$b} } keys %powers;

foreach (@power_sort) {
	print "$_ :\t($powers{$_})\t", "*" x $powers{$_}, "\n";
}
print "\nNormal Picks:\t";

print join(" ", sort (@norm_sort[0 .. 11])), "\n";

print "\nPower Picks:\t";

print join(" ", sort (@power_sort[0 .. 3])), "\n";

print "\nDisclaimer:\n\tThis is not statistically accurate, except in that the drawings are
guaranteed.\nThis is just a quick frequency analysis making no pretenses as to predictive
accuracy.\n";
