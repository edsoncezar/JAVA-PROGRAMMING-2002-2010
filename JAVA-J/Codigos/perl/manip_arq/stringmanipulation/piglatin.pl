  #!/usr/bin/perl
    use strict;
    use warnings;
    #This script was written by Tyler Cruz
    #You may use this script as long as you keep these comments in tact
    #For more iddy biddy scripts, go to http://www.101h.com/tyler/perl/
    #get user input
    print "Pig Latin Translater. Please enter the sentence to be translated\n> ";
    my $english = <STDIN>;
    #declare the arrays..
    my @vowels = ('a', 'e', 'i', 'o', 'u', 'y', 'A', 'B', 'E', 'I', 'O', 'U', 'Y');
    my @cons = ('b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'z', 'B', 'C', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'X', 'Z');
    #puts each word in an array
    my @split = split (/\s/, $english); 
    #get every word in the sentence...
    foreach my $word (@split) { 
    #checks if each word starts with a vowel and makes the appropriate changes..
    ##
    foreach my $vow (@vowels) {
    if ($vow eq substr($word, 0, 1)) {
    print "$word" . "-hay ";
    }
    }
    ##
    #checks if each word starts with two consecutive vowels and makes the appropriate changes..
    ##
    foreach my $const (@cons) {
    if (substr($word,0,1) eq $const) {
    foreach my $i (@cons) {
    	if (substr($word,1,1) eq $i) {
    print substr($word, 2) . "-" . substr($word, 0, 2) . "ay ";
    }
    }
    }
    }
    ##
    #checks if each word starts with a consonant and makes the appropriate changes..
    ##
    foreach my $const (@cons) {
    if (substr($word,0,1) eq $const) {
    foreach my $i (@vowels) {
    	if (substr($word,1,1) eq $i) {
    print substr($word, 1) . "-" . substr($word, 0, 1) ."ay ";
    }
    }
    }
    }
    ##
    }
    #The End :)
 