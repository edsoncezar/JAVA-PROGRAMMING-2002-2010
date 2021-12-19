#!/usr/bin/perl -w

use strict;

my @months = qw(
  January February March April May June July
  August September October November December
);

my ($day, $mon, $year, $dow) = (localtime)[3..6];
my $start_dow = ($dow - ($day - 1)) % 7;

for (1 .. shift || 1) {
  $year++ if $mon != ($mon % 12);
  $mon %= 12;
  my $dim = days_in_month($mon,$year);

  print $months[$mon], ' ', $year + 1900, "\n";
  print "Sun Mon Tue Wed Thu Fri Sat\n";
  for (1 .. $dim + $start_dow) {
    print '    ' and next if ($_-1) < $start_dow;
    printf '%3d', $_ - $start_dow;
    print $_ % 7 ? ' ' : "\n";
  }
  print "\n" x (($dim + $start_dow) % 7 ? 2 : 1);

  $start_dow = ($start_dow + $dim) % 7;
  $mon++;
}

  
BEGIN {
  my @days = (31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);

  sub days_in_month {
    my ($m,$y) = @_;
    my $days = $days[$m];
    $y += 1900;
    $days++ if $m == 1 and $y % 4 == 0 and ($y % 100 != 0 or $y % 400 == 0);
    return $days;
  }
}

