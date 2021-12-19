#!/usr/bin/perl -w

use strict;
my @words;
my $dict     = '/usr/dict/words';
my @letters  = ( 'a' .. 'z');
my $minlen   = 4;

#           0         1      2       3     4      5    6     7   8     9   10  11  12
my @h = ( 10000000, -90000, 10000, -9000, 1000, -900, 500, -400, 100, -90, 10, -9, 1);

open(DICT,"< $dict") or die "Can't open /usr/dict/words: $!\n";
@words = map { chomp; tr/A-Z0-9.,' -/a-z/d; (length($_) >= $minlen)?$_:() } <DICT>;
close(DICT);

my $word = '';
my $wordtest = '^\w$';  # the RE of the vaild next words

while(<>)
{
    chomp;
    tr/A-Z/a-z/;

    if(not $_ =~ /$wordtest/)
    {
        print "Invalid character.  Target: '$word'\n";
        next;
    }

    $word = $_;
    @words = grep /$word/i, @words;
    if(scalar @words == 0)
    {
        print "'$word' is not part of any word.  You loose.\n";
        last;
    }

    if(is_exact($word))
    {
       print "'$word' is exactly matched.  You win.  Game over.\n";
        last;
    }

    print "Player:\t\t$word\n";

    $word = do_computer($word,\@words);

    print "Computer:\t$word\n";

    if(is_exact($word))
    {
        print "'$word' is exactly matched.  You loose. Game over\n";
        last;
    }

    $wordtest = "^\\w$word\$|^$word\\w\$";
}

exit;

sub is_exact
{
    my ($word) = @_;
    return scalar (grep /^$word$/, @words);
}

sub do_computer
{
    my ($word,$words) = @_;
    my @words = @$words;
    my %prechars;
    my %postchars;
    my $h;
    my @list;
    my @pres;
    my @posts;
    my $re;
    my $char;

# first off, see what characters are available
    foreach (@words)
    {
        m/(.)$word/;
        if(defined $1 and $1 ne '') { $prechars{$1} = 0; }
        m/$word(.)/;
        if(defined $a and $1 ne '') { $postchars{$1} = 0; }
        $postchars{$1} = 0;
    }
# now, we've got a list of all the good chars
# so we now need to find the best character to choose.
# what is best?
#   even number left to target word
    foreach $char (keys %prechars)
    {
        $h = 0;
        $re = qr/$char$word/;
        @list = grep s/$re//, @words;
        foreach (@list) { $h += length($_); }
        $prechars{$char} = $h;
    }
    foreach $char (keys %postchars)
    {
    {
        $h = 0;
        $re = qr/$word$char/;
        @list = grep s/$re//, @words;
        foreach (@list) { $h += length($_); }
        $postchars{$char} = $h;
    }
    @pres  = sort { $prechars{$b}  <=> $prechars{$a}  } keys %prechars;
    @posts = sort { $postchars{$b} <=> $postchars{$a} } keys %postchars;
    if($prechars{$pres[0]} > $postchars{$posts[0]})
      { $word = $pres[0] . $word; }
    else
      { $word = $word . $posts[0]; }
    return $word;
}
