#!/usr/bin/perl -wT
use strict;

my $dictfile = '/usr/share/dict/linux.words';

use vars qw/$regex1 $regex2 @list1 @list2 $word1 $word2 @array $match $expr/;
my $cols  = 2;
my $max   = -1;

$regex1 = &build_regex( '1' );
$regex2 = &build_regex( '2' );

open ( FILE1, "< $dictfile")
  or die("Could not open FILE1, $dictfile\n");

while (<FILE1>) {
  chomp;
  push @list1, $_;
}
close FILE1;

@list2 = @list1;

$expr = &build_expr( $regex1, $regex2 );
print "\nPlease wait a moment...\n";
eval $expr;

unless ($array[0]) {
	print "\n\nNo matches!\n";
	exit;
}

print "\n\nThe results are,\n\n";
$_ > $max && ($max = $_) for map {length} @array;
while (@array) {
    print join " " => map {sprintf "%-${max}s" => $_}
                           splice @array => 0, $cols;
    print "\n";
}
exit;

### SUBS

sub build_regex {
	my $this = shift;
	my ($regex, $total, $other, $position);
	my $count = 1;
	if ($this == 1) {
		$other = 2;
	}
	else {
		$other = 1;
	}
	
	print "\nHow any letters does word $this have?\n";
	$total = <STDIN>;
	chomp $total;
	unless ($total =~ /^([0-9]+)$/) {
		die("Incorrect input! - $total\n");
	}
	$total = $1;
	
	print "What position does it meet word $other at?\n";
	$position = <STDIN>;
	chomp $position;
	unless ($position =~ /^([0-9]+)$/) {
		die("Incorrect Input! - $position\n");
	}
	$position = $1;
	print "\n";
	
	while ($count <= $total) {
		if (($this == 1) && ($position == $count)) {
			$regex .= '(\w)';
		}
		elsif (($this == 2) && ($position == $count)) {
			$regex .= '${match}';
		}
		else {
			my $suffix = &build_suffix( $count );
			
			print "What is the ${count}${suffix} letter?\n";
			if ($count == 1) {
				print "Hit <RETURN> if you don't know.\n";
			}
			my $input = <STDIN>;
			chomp $input;
			unless (($input =~ /^([a-zA-Z])$/) | ($input =~ /^()$/)) {
				die("Incorrect input! - $input\n");
			}
			$input = $1;
			
			if ($input eq '') {
				$regex .= '\w';
			}
			else {
				$regex .= $input;
			}
		}
		
		$count ++;
	}
	
	return $regex;
}

sub build_suffix {
	my $number = shift;
	if ($number =~ /([0-9])$/) {
		$number = $1;
	}
	else {
		die("Could not build number suffix!\n");
	}
	
	if ($number == 1) {
		return 'st';
	}
	elsif ($number == 2) {
		return 'nd';
	}
	elsif ($number == 3) {
		return 'rd';
	}
	else {
		return 'th';
	}
}

sub build_expr {
	my $string1 = shift;
	my $string2 = shift;
	
	my $build = "
foreach \$word1 (\@list1) {
  if (\$word1 =~ /^$string1\$/i) {
  	\$match = \$1;
    foreach \$word2 (\@list2) {
    	if (\$word2 =~ /^$string2\$/i) {
    		push \@array, \$word1;
    		push \@array, \$word2;
    	}
    }
  }
}";

	return $build;
}
