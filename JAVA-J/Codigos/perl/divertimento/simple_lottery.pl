#!/usr/bin/perl -w

use strict;
my (%pick, %rand) = ();
my ($plays, $money, $years, $week, $win, $count) = 0;
srand();

print "How many times do you play the lottery every week?\n";
chomp(my $wcheck = <>);
$week = ($wcheck * 52);

print "Please enter 6 numbers for the lottery: \n";
while ($count < 6) {
    $count++;
    print "Enter lottery number $count: ";
    chomp(my $num = <>);
    $count-- and print "That is not a valid input, please enter a number between 1 and 54\n" and next if $num > 54 || !($num =~ /^\d+$/);
    $pick{"$count"} = "$num";
}

## Start our search!
while () {
    $plays++;
    undef $win;
    my $i = 0;
    while ($i <= 6) { $i++; $rand{$i} = int(rand(54)) + 1;}
    foreach my $x (keys (%rand)) {
        foreach my $y (keys(%pick)) {
            $win++, next if $pick{$y} == $rand{$x};
            }
        }
    $money += 1000000 and last if $win == 6;
    $money += 1000 if $win == 5;
    $money += 100 if $win == 4;
    $money += 5 if $win == 3;
    print "We won \$$money dollars so far, in $plays plays\n";
}
print "We won \$$money dollars in $plays plays!\n";

## Figure out how long it took us to hit the jackpot and print findings
if ($plays >= $week) {
    $years = sprintf "%.1f", ($plays / $week);
    print "It took us $years years to hit the jackpot at $wcheck plays a week. We won \$$money dollars!\n";
}
else { print "It took us less than a year to hit the jackpot. We won \$$money dollars!\n"; }

