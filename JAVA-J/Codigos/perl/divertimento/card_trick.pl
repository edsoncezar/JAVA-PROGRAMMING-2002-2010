
#!/usr/bin/perl -w

use strict;

my ($column, $row, @cards2);

my @cards = (
	[
		 ["2", chr(4)], ["A", chr(3)], ["5", chr(3)], ["10", chr(6)]
	],
	[
		["Q", chr(5)], ["3", chr(3)], ["A", chr(6)], ["7", chr(5)]
	],
	[
		["K", chr(5)], ["6", chr(4)], ["9", chr(6)], ["J", chr(6)]
	],
	[
		["8", chr(3)], ["Q", chr(4)], ["3", chr(4)], ["10", chr(5)]
	]
);

print "\n\nPick a card from below.";
&display_cards;
while (1) {
	print "Which column is your card in? ";
	$column = <STDIN>;
	chomp($column);

	if ($column =~ m/[1-4]/) { last; }
	else { print "\nPlease pick a number between 1 and 4.\n"; }
}

my %map = (4=>1, 3=>2, 2=>3, 1=>4);

for (my $i = 0; $i <= $#cards; $i++) {
	for (my $j = 0; $j <= $#cards; $j++) {
		$cards2[$i][$j] = $cards[($map{($j+1)}-1)][$i];
	}
}

@cards = @cards2;
&display_cards;

while (1) {
	print "Which column is your card in now? ";
	$row = <STDIN>;
	chomp($row);

	if ($row =~ m/[1-4]/) { last; }
	else { print "\nPlease pick a number between 1 and 4.\n"; }
}

print "\n\nYour card is: $cards[$column-1][$row-1][0]$cards[$column-1][$row-1][1]\n\n";

sub display_cards {

	my ($aref1, $aref2, $i);

	print "\n\n";
	print "\t[1]\t[2]\t[3]\t[4]\n\n";
	for $aref1 (@cards) {
		print "\t";
		for $aref2 (@$aref1) {
			print "@$aref2[0]@$aref2[1]\t";
		}
		print "\n";
	}
	print "\n\n";
}

__DATA__


##</code><code>##

#!/usr/bin/perl -w

use strict;

my ($column, $row, @cards2);

my @cards = (
	[
		 ["2", chr(4)], ["A", chr(3)], ["5", chr(3)], ["10", chr(6)]
	],
	[
		["Q", chr(5)], ["3", chr(3)], ["A", chr(6)], ["7", chr(5)]
	],
	[
		["K", chr(5)], ["6", chr(4)], ["9", chr(6)], ["J", chr(6)]
	],
	[
		["8", chr(3)], ["Q", chr(4)], ["3", chr(4)], ["10", chr(5)]
	]
);

print "\n\nPick a card from below.";
&display_cards;
while (1) {
	print "Which column is your card in (1-4, left-to-right)?  ";
	$column = <STDIN>;
	chomp($column);

	if ($column =~ m/[1-4]/) { last; }
	else { print "\nPlease pick a number between 1 and 4.\n"; }
}

for (my $i = 0; $i <= $#cards; $i++) {
	for (my $j = 0; $j <= $#cards; $j++) {
		$cards2[$i][$j] = $cards[$j][$i];
		$cards2[$j][$i] = $cards[$i][$j];
	}
}
@cards = @cards2;
&display_cards;

while (1) {
	print "Which row is your card in (1-4, bottom-to-top)? ";
	$row = <STDIN>;
	chomp($row);

	if ($row =~ m/[1-4]/) { last; }
	else { print "\nPlease pick a number between 1 and 4.\n"; }
}

print "\n\nYour card is: $cards[$column-1][$row-1][0]$cards[$column-1][$row-1][1]\n\n";

sub display_cards {

	my ($aref1, $aref2, $i);
	$i = 4;

	print "\n\n";
	print "\t[1]\t[2]\t[3]\t[4]\n\n";
	for $aref1 (@cards) {
		print "[$i]\t";
		for $aref2 (@$aref1) {
			print "@$aref2[0]@$aref2[1]\t";
			
		}
		$i -= 1;
		print "\n";
	}
	print "\n\n";
}

__DATA__
