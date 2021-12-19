#!/usr/bin/perl -w

use strict;
use diagnostics;
use AnyDBM_File;
use Fcntl;

sub print_results;
sub create_wordbank;
sub enter_challenge;
sub lowercase;

my ($letter, @letters, $display, @display, $word, @word, $wrong_letters, $clue, $mode, $continue, $guess, $correct_guess);

my $dbfile = "wordbank";
tie (my %wordbank, "AnyDBM_File", $dbfile, O_CREAT|O_RDWR, 0600) or die "
+Can't open $dbfile: $!\n";
&create_wordbank unless keys %wordbank;

print "\nWelcome to Hangman!! Would you care to have a dance with death?\n";
print "\n1. Single Player";
print "\n2. Challenge Mode";
print "\n3. Quit\n";
print "\nYour choice: ";
$mode = <STDIN>;
chomp($mode);

if ($mode ne "1" and $mode ne "2") { exit; }
else { $continue = "y"; }

while ($continue eq "y") {

	my ($max_key, $random_number);	

	if ($mode eq "2" ) {
		&enter_challenge;
	}
	else {
		
		$max_key = (keys(%wordbank)/2);
		$random_number = (rand($max_key)%$max_key) + 1;
		
		$word = $wordbank{$random_number,1};
		$clue = $wordbank{$random_number,2};
	}

	$wrong_letters = 0;
	@letters = ();
	@word = split(//, $word);
	$display = $word;
	$display =~ s/[a-zA-Z]/-/g;
	@display = split(//, $display);
	$correct_guess = 0;

	&print_results;

	while ($wrong_letters < 6 and $correct_guess == 0) {
		
		my ($index, $correct_letter) = (0, 0);

		print "\nLetter, please.";
		print "\nLetter: ";
		$letter = <STDIN>;
		chomp($letter);
		
		$letter = lowercase($letter);

		if (join("", @letters) =~ $letter) {
			print "----------------------------------------------------------";
			print "\nYou already guessed that letter.\n";
			print "----------------------------------------------------------";
			next;
		}

		push(@letters, $letter);

		foreach (@word) {
			if ( lowercase($_) eq $letter ) { 
				$display[$index] = $_;
				$correct_letter = 1;
			}
			$index +=1;
		}

		$display = join("", @display);
		if ($display !~ m/-/) {
			&print_results;
			last; 
		}

		if ($correct_letter == 0) { 
			$wrong_letters += 1;
			print "\nYou guessed wrong! The noose is getting tighter.\n"
		}
		else { print "\nYou guessed right! Still, you can't escape the inevitable.\n"; }
		&print_results;
	}
	
	print "\nGame over.\n";
	if ($wrong_letters != 6) {
		print "Congratulations. You slipped the hangman's noose.";
	}
	else {
		print "Sorry. Looks like the crows will be feasting today.";
	}
	print "\nWould you like to play again?\n";
	
	$continue = <STDIN>;
	chomp($continue);
}

untie %wordbank;

sub print_results {
	
	print "\n\n----------------------------------------------------------";
	print "\nClue: $clue";
	print "\nWord: $display";

	print "\nGuess list: [".join(",", @letters)."]";
        
	print "\n\n";
        print "__________\n";
        print "          |\n";
        print "          |";
	
	print "\n";
	if ($wrong_letters > 0 && $wrong_letters < 6) { print "          ".chr(2).""; }
	elsif ($wrong_letters == 6) { print "          ".chr(233).""; };
	
	print "\n";
	if ($wrong_letters == 2) { print "          ".chr(30).""; }
	if ($wrong_letters == 3) { print "         /".chr(30).""; }
	if ($wrong_letters >= 4) { print "         /".chr(30)."\\"; }
	
	print "\n";
	if ($wrong_letters == 5) { print "         /"; }
	if ($wrong_letters == 6) { print "         / \\"; }
	
	print "\n";
        print "_______________\n";
        print "_______________";
        print "\n\n";

	print "----------------------------------------------------------";

	if ($correct_guess != 1 and $wrong_letters < 6) {
        	print "\n\nCare to wager a guess? Just hit 'Enter' if you don't know.";
        	print "\nGuess: ";
        	$guess = <STDIN>;
        	chomp($guess);
		print "\nGuess: $guess\n";
	}

	if (lowercase($guess) eq lowercase($word)) {
		$correct_guess = 1;
	}
	else { print "\nIncorrect guess. You'll soon be mine.\n"; }
}

sub create_wordbank {
	$wordbank{1,1} = "Larry Wall";
	$wordbank{1,2} = "Creator of Perl.";
	$wordbank{2,1} = "Camel";
	$wordbank{2,2} = "On the cover of 'Programming Perl'.";
}

sub enter_challenge {
	my $x;

	print "\n\n----------------------------------------------------------";
	print "\nPlease enter a word or phrase:\n";
	$word = <STDIN>;
	chomp($word);
	print "\nPlease enter a clue.\n";
	$clue = <STDIN>;
	chomp($clue);
	print "\----------------------------------------------------------\n\n";

	$x = (keys(%wordbank)/2) + 1;
	$wordbank{$x,1} = $word;
	$wordbank{$x,2} = $clue;
}

sub lowercase {
	my $temp = $_[0];
	$temp =~ tr/[A-Z]/[a-z]/;
	return $temp;
}
