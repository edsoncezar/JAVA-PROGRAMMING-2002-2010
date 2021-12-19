#!/usr/bin/perl -w
#scrabbler.pl - A program to find suitable words while playing Scrabble
#By Collin Winter d_cove@hotmail.com
#
#Options:
#	-f : Means that this is the first turn.
#	     This will skip the request for base letters (see below),
#	     since there will be none.

use strict;

#Replace /usr/share/dict/linux.words with the path to the words file on your system.
open WORDLIST, "/usr/share/dict/linux.words" or die "Unable to open word list: $!\n";
my @wordlist=<WORDLIST>;
close WORDLIST;

#Find out what letters the player has, and make a hash of their frequency
print "Your letters: ";
chomp(my $master_letters=<STDIN>);

#Removes any non-alphacharacters that may have been used to separate the letters;
$master_letters=~s/[^A-Za-z]//g;

my $base_letters;
my %letter_freq;
#If this is the first turn of the game, then there be no base letters to use, so we need to obtain one
if($ARGV[0] eq '-f'){
	$base_letters=substr($master_letters,0,1);
	$master_letters=substr($master_letters,1,length($master_letters)-1);
}

for (split(//,$master_letters)){
	$letter_freq{$_}++;
}

#"Base letters" are tiles that are already out on the board and available for to build words on.
#One base letter will be added at a time to $letters, and valid words looked up for that combination.
if(!$ARGV[0] || $ARGV[0] ne '-f'){
	print "Base letters: ";
	chomp($base_letters=<STDIN>);
	$base_letters=~s/[^A-Za-z]//g;
}

#"Initial omission level" is the number of characters from $letters that maybe left out of a word.
#A value of 0 will return only words that contain all of the characters in $letters.
#Setting it to 1 will return all words that contain every letter and all words with all letters except one, etc
print "Initial omission level: ";
chomp(my $master_om_level=<STDIN>);

my @possible_words;
my @valid_words;

foreach my $base (split(//,$base_letters)){
	my $om_level=$master_om_level;

	#Add the current base letter to the string and to the letter frequency hash
	my $letters=$master_letters.$base;

	$letter_freq{$base}++;
	my $do_it_again=1;

	#Repeat the word-evaluation until a word is found.
	while($do_it_again==1){
		
		#This is done like it is so that $max_length and $min_length can be modified later.
		my $max_length=length($letters)+1;
		my $min_length=length($letters)-$om_level+1;
		
		#Thanks to mt2k for suggesting this kind of for loop.
		for my $i ($min_length .. $max_length){
			push(@possible_words,grep(length($_)==$i,@wordlist));
		}
		
		foreach my $word (@possible_words){
			my %this_freq=%letter_freq;
			my $this_word=$word;
			my $key_count=0;
			my $remaining_oms=$om_level;
		
			#Repeat until either:
			# a) there are no more letters to check for,
			# b) all keys in %letter_freq have been used, or
			# c) the word has too many omissions to be used.
			while((length($this_word)>1) && ($key_count<=(scalar keys %letter_freq)-1) && ($remaining_oms>=0)){
				my $key=(keys %letter_freq)[$key_count];
		
				#If the current letter isn't in the word, then
				#There is one less omission remaining for this word.
				if($this_word!~/$key/i){
					$remaining_oms--;
				}
		
				if(($this_freq{$key} > 0) && ($this_word=~/$key/i)){
			
					if($this_word!~/$key/i){
						$this_freq{$key}--;
					}
			
					while($this_freq{$key}>0){	
						$this_freq{$key}--;
						$this_word=~s/$key//i;
					}
				}
				$key_count++;
			}
			
			if(length($this_word)==1){
				push(@valid_words,$word);
			}
		}
		
		#If no words are found at your current omission level, the search
		#will be automatically enlarged until at least one word is returned.
		print $#valid_words+1," results with $om_level allowed omissions for base \'$base\'";
		
		if(!$valid_words[0]){
			@possible_words=();
			print "; Expanding search...\n";
			$om_level++;
			$max_length=$min_length--;
		}
		if($valid_words[0]){
			print ":\n",@valid_words;
			@valid_words=();
			$do_it_again=0;
		}
	}

	#Remove the current base from the letter frequency hash
	$letter_freq{$base}--;
}
exit;
