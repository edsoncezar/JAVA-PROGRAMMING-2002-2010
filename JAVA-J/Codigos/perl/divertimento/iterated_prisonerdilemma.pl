#!/usr/local/bin/perl -w 

use strict;

srand(time);

my $iters = 10;

my $player1 = \&player_random;
#my $player1 = \&player_good;
#my $player1 = \&player_tit_for_tat;

#my $player2 = \&player_random;
#my $player2 = \&player_good;
my $player2 = \&player_tit_for_tat;

my (@record1, @record2, $score1, $score2, $total_score1, $total_score2);

for (my $n = 0; $n < $iters; ++$n)
{
    my $ret1 = $player1->(\@record1, \@record2);
    my $ret2 = $player2->(\@record2, \@record1);

    ($score1, $score2) = calc_score($ret1, $ret2);

    $total_score1 += $score1;
    $total_score2 += $score2;

    push(@record1, $ret1);
    push(@record2, $ret2);
}

print_records(\@record1, \@record2);
print "Player1: $total_score1\nPlayer2: $total_score2\n";

#########################################
#
# Subroutines
#
#########################################


sub print_records
{
    my @record1 = @{$_[0]};
    my @record2 = @{$_[1]};

    for (my $n = 0; $n < @record1; ++$n)
    {
	print "$record1[$n] $record2[$n]\n";
    }
}

# H - hold out (be good)
# T - testify (betray)
#
sub calc_score
{
    my $move1 = $_[0];
    my $move2 = $_[1];
    my ($score1, $score2);
    
 SWITCH:
    {
	if ($move1 eq "T" && $move2 eq "T")
	{
	    $score1 = 4;
	    $score2 = 4;
	    last SWITCH;
	}
	if ($move1 eq "T" && $move2 eq "H")
	{
	    $score1 = 0;
	    $score2 = 5;
	    last SWITCH;
	}
	if ($move1 eq "H" && $move2 eq "T")
	{
	    $score1 = 5;
	    $score2 = 0;
	    last SWITCH;
	}
	if ($move1 eq "H" && $move2 eq "H")
	{
	    $score1 = 2;
	    $score2 = 2;
	    last SWITCH;
	}
    }

    return ($score1, $score2);
}


#
# Various players
#
# Arguments: player's past record and opponent's
#            past record
#
# Return: H or T
#

sub player_nice
{
    return "H";
}


sub player_random
{
    return "H" if rand() < 0.5;
    return "T";
}

sub player_tit_for_tat 
{
    my @my_record = @{$_[0]};
    my @his_record = @{$_[1]};

    return "H" unless @my_record;
    return $his_record[$#his_record];
}
