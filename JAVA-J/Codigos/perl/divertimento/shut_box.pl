#! Perl -w
# shutthebox.pl -- classic game
use strict;
my($roll1, $roll2, $c1, @c2, @opts, $choice, $score, %pegs);
my $cnum="0";
my $endgame="0";
srand;
sub assign {
	foreach my $num ("1".."12") {
		$pegs{$num} = 1;
	}
}
sub show_board {
	print "\n\n";
	foreach my $num ("1".."12") {
		if ($pegs{$num}) {
			print "$num ";
		} else {
			print "# ";
		}
	}
	print "\n";
}
sub roll {
	print "Roll was: ";
	$roll1 = 1 + int rand(6);
	$roll2 = 1 + int rand(6);
	print "$roll1 and $roll2";
}
sub find_combos {
	$c1 = $roll1 + $roll2;
	@c2 = ($roll1, $roll2);
}
sub process_combos {
	$cnum = "0";
	@opts = undef;
	print "\nOptions: \n";
	if ($pegs{$c1}) {
		print "\t".++$cnum.") Put down $c1\n";
		$opts[$cnum] = $c1;
	}
	unless ($c2[0] == $c2[1]) {
		if ($pegs{$c2[0]} && $pegs{$c2[1]}) {
			print "\t".++$cnum.") Put down $c2[0] and $c2[1]\n";
			$opts[$cnum] = "13";
		}
	}
	foreach("1".."12"){
		unless (defined($pegs{$_})) {
			$endgame++;
		}
	}
	if ($endgame == 12){
		print "YOU WON!";
		exit;
	}
	unless($opts[1]) {
		print "\tnone\nYOU LOSE!\n";
		foreach my $left ("1".."12") {
			if ($pegs{$left}) {
				$score += $left;
			}
		}
		print "\tScore: $score (lower is best)";
		exit;
	}
	print "Which option? ";
	chomp($choice=<STDIN>);
	if ($opts[$choice] == $c1) {
		$pegs{$c1} = 0;
	} elsif ($opts[$choice] == 13 && $choice <= 2) {
		$pegs{$c2[0]} = 0;
		$pegs{$c2[1]} = 0;
	} else {
		print "Invalid option choice!\n";
		process_combos();
	}
}


assign();
while(1) {
	show_board();
	roll();
	find_combos();
	process_combos();
}
