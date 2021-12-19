#!/usr/bin/perl -w
use strict;

my ($miss, $guesses, $guess, @build, $tries, @guessed, @wordbank);

@wordbank=qw(cubicle scramble deduction envelope century rediculous); #as many words as you like.
my $selected=rand(scalar(@wordbank));
my (@word)=split(//, $wordbank[$selected]);
@build=("_") x scalar(@word);
$_=join('',@word);
$miss=0; $guesses="0-<--<"; $tries=0;



print "Welcome to HangPerl Hangman ver 0.56... mentained by: Nimster\n";
print "Word has "; print scalar(@word); print " letters\n";
print "Your hangman: "; print $guesses; print "\n";
print "Good luck! Start guessin`!\n";

while (1) 
  {
  last if $miss==length($guesses);
  last if join('',@build) eq join('',@word);
  $guess=<STDIN>; chomp $guess;
  $guess=lc(substr($guess,0,1));
  $tries+=1;
  if (m/$guess/) 
    {
    print "right!\n";
    for(my $index=0; $index<scalar(@word); $index++) 
      {
        $build[$index]=$word[$index] if ($word[$index] eq $guess);  
      }
    
    foreach my $letter (@build) 
      {
      print "$letter ";
      }
    print "\n";
    }
  else 
    {
    $miss++;
    print substr($guesses, $miss, length($guesses)); print "\n";
    }
  }
print "It took you $tries tries ($miss misses) and ";
if ($miss < length($guesses)) { print "You made it!\n"; }
else { print "You failed.\n"; }

#it can be made much more effective easily (elimination of @word/$currword which are basically the same, same with some more variables, so changes in the algorythm, etc. However, this is my first try at a real, kinda full perl program. Soon to be ported to CGI, then maybe I'll add DBM highscores, etc.
