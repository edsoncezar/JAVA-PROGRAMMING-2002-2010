#!/usr/bin/perl -w
#
# Nick's simple calculator by Nick Craig-Wood <ncw@axis.demon.co.uk>
#
# I usually call this program = 
#
# You can use it with command line arguments, eg
#   = 2 + 2
#   = 230.4e3 x 60 x 60 x 24 / 8 / 1024 / 1024 / 1024
# ( Note that 'x' is translated to '*' on the command line to avoid shell
# annoyances )
#
# You can also run it with no arguments in which case it will run a
# calculator shell.  It will give you a bit of help when you run it.
#
#   =
#   Welcome to Nick's Simple calculator
#   Last item is $l, answers stored in $a[..], $n is last entry
#   > 2
#   $a[0]= 2
#   > ($l+2/$l)/2
#   $a[1]= 1.5
#   > ($l+2/$l)/2
#   $a[2]= 1.41666666666667
#   > ($l+2/$l)/2
#   $a[3]= 1.41421568627451
#
# You can type any valid perl you like.
#
# It uses Term::Readline so you can press up arrow to get your
# previous calculation back
#
# Note: it deliberately doesn't 'use strict' to make it a friendlier
# calculator ;-)

use Term::ReadLine;
$args = scalar(@ARGV);

$ENV{"PERL_RL"}="Gnu";

if ($args)
{
    $sum = join(" ", @ARGV);
    $sum =~ s/x/*/g;
    print "$sum = ", eval($sum), "\n";
    die "Error: $@\n" if $@;
    exit;
}

$term = new Term::ReadLine('=');
print "Welcome to Nick's Simple calculator\n";
print "Last item is \$l, answers stored in \$a[..], \$n is last entry\n";
while (defined ($_ = $term->readline("> ")))
{
    chomp;
    push @a, eval($_);
    print "Error: $@" if $@;
    $n = @a-1;
    $l = $a[$n];
    print "\$a[$n]= ", $l, "\n" if defined $l;
}

print "quit\n";
exit;

