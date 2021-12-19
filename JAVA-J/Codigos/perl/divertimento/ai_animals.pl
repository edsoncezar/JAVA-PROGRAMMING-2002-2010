#!/usr/bin/perl -Tw
use strict;
use AnyDBM_File;
use Fcntl; 

my $dbfile = "animals";
tie (my %brain, "AnyDBM_File", $dbfile, O_CREAT|O_RDWR, 0600) or die "Can't open $dbfile: $!\n";
&create_new unless keys %brain;

my $loop;
until ($loop) {
 print "Hello, my name is AI!  I love animals and guessing games.\n";
 print "I have currently learned " . &animals_learned . " animals.\n";
 print "\nPlease think of an animal and I will try to guess it - press enter when ready\n";
 <>;
 my $x = my $y = my $found = 1;
 while ($found) {
  my $entry = my $response = my $animal = my $question = my $answer = 0;
  ($entry = $brain{$x,$y}) =~ s/(^[QG]): //;
  if ( $1 eq "Q" ) {
   until ( $response =~ /^y/i || $response =~ /^n/i ) {
    print "$entry\n";
    chomp ($response = <>);
   }
   if ( $response =~ /^y/i ) {   
    $y = $y * 2 - 1;
    ++$x;
   }
   elsif ($response =~ /^n/i) {
    $y *= 2;
    ++$x;
   }
  }
  elsif ( $1 eq "G" ) {
   $found = 0;
   until ( $response =~ /^y/i || $response =~ /^n/i ) {
    print "Is your animal a(n) $entry?\n";
    chomp ($response = <>);
   }
   if ( $response =~ /^y/i ) {
    print "\nYeah, I got it right!\n";
   }
   elsif ($response =~ /^n/i) {
    until ( $animal ) {
     print "OK, I give up, what animal were you thinking of?\n";
     chomp ($animal = <>);
    }
    $animal =~ s/a[n]* //i;
    until ( $question ) {
     print "\nEnter a yes/no question to tell the difference between a(n) $animal and a(n) $entry?\n";
     chomp ($question = <>);
    }
    $question .= "?" unless $question =~ /^.*\?$/;
    until ( $answer =~ /^y/i || $answer =~ /^n/i ) {
     print "\nWhat would the correct answer be for a(n) $animal?\n";
     chomp ($answer = <>);
    }
    if ( $answer =~ /^y/i ) {
     $brain{$x + 1,$y * 2 - 1} = "G: " . $animal;
     $brain{$x + 1,$y * 2} = "G: " . $entry;    
    }
    elsif ( $answer =~ /^n/i ) {
     $brain{$x + 1,$y * 2} = "G: " . $animal;
     $brain{$x + 1,$y * 2 - 1} = "G: " . $entry;   
    }
    $brain{$x,$y} = "Q: " . $question;
   }
   $response = "";
   until ( $response =~ /^y/i || $response =~ /^n/i ) {
    print "Would you like to play again?\n";
    chomp ($response = <>);
   }
   $loop = 1 if $response =~ /^n/i;
  }
 }
}
untie %brain;

sub animals_learned {
 return grep /^G: / , %brain;
}

sub create_new {
 $brain{1,1} = "Q: Does your animal live in the water";
 $brain{2,1} = "Q: Is your animal a mammal?";
 $brain{2,2} = "Q: Can your animal fly?";
 $brain{3,1} = "Q: Does your animal get caught in Tuna nets?";
 $brain{3,2} = "Q: Does your animal have tentacles?";
 $brain{3,3} = "Q: Is your animal nocturnal?";
 $brain{3,4} = "Q: Can your animal be domesticated as a house pet?";
 $brain{4,1} = "G: dolphin";
 $brain{4,2} = "G: whale";
 $brain{4,3} = "G: octapus";
 $brain{4,4} = "G: shark";
 $brain{4,5} = "G: bat";
 $brain{4,6} = "G: parrot";
 $brain{4,7} = "G: dog";
 $brain{4,8} = "G: lion";
}
