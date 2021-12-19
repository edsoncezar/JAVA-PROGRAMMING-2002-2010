#!/usr/bin/perl -ws

use strict;
use vars qw( $h $words $g );

my @words;

usage() if $h;
build_word_list($words);
$g ||= 10;


while (1) {
  my $len = length(my $word = randword());
  my $freq = dissect($word);
  my @state = ("_") x $len;
  my ($left, %guessed) = 10;

  print "The word is $len letters long.\n";

  while (%$freq and $left) {
    printf "%d wrong guess%s left.\n", $left, $left > 1 && "es";
    print @state, "\n";
    print "Guessed: @{[ sort keys %guessed ]}\n";

    print "Next Letter: ";
    my $c = substr <STDIN>, 0, 1;
    print("You already guessed '$c'"), next if $guessed{$c}++;

    if (my $pos = delete $freq->{$c}) { @state[@$pos] = ($c) x @$pos }
    else { $left--, print "'$c' not found" }
  }
  continue { print "\n\n" }

  if (%$freq) { print "Too late -- the word was '$word'." }
  else { printf "You got it in %d guesses!", scalar keys %guessed }

  print "\n\n";
  print "Play again ([y]/n): ";
  last if lc substr(<STDIN>, 0, 1) !~ /^y?$/;
  print "\n\n";
}


sub usage {
  print << "USAGE";
$0 [-h] [-words=file] [-g=num]

  -h           display this usage note
  -words=file  the file to use as a word list (def: .hm-words)
  -g=num       the maximum number of wrong guesses (def: 10)

USAGE
  exit;
}


sub build_word_list {
  for my $file (@_, ".hm-words") {
    next if not $file;
    my $i = 0;
    open WORDLIST, $file or warn("file $file not readable: $!\n"), next;
    chomp, push(@words,$_) while <WORDLIST>;
    close WORDLIST;
    return;
  }
  die "no wordlist found!  make an .hm-words file!\n";
}


# XXX:  slow to splice towards the beginning of a LARGE list
#       to be replaced by a hash/linked-list implementation
sub randword () { splice @words, rand @words, 1 }


sub dissect {
  my ($i, %freq);
  push @{ $freq{$_} }, $i++ for split //, shift;
  return \%freq;
}
