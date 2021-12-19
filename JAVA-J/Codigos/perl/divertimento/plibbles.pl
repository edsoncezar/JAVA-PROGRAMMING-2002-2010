#!/usr/bin/perl -w

use strict; 
use Curses; 

srand( time() ^ ($$ + ($$ << 15)) );

my $score = 0;
my @worm;
for (my $i=0; $i < 8; $i++) {
    push @worm, { y => 12,
                  x => 9-$i };
}

my $w = Curses->new();

noecho();

my $maxy;
my $maxx;
$w->getmaxyx($maxy, $maxx);
$w->clear;
my $t = $w->subwin(1, $maxx, 0, 0);
my $o = $w->subwin($maxy-1, $maxx, 1, 0);
my $growing;
my %prize;

$t->addstr(0, 1, "Plibbles: A worm game in Perl");
$t->addstr(0, $maxx-8-length($score), "Score: $score");
$t->refresh;

$o->box('|', '-');

draw_worm(@worm);
$o->move($worm[0]{y}, $worm[0]{x});
prize();
$o->addstr($prize{y}, $prize{x}, $prize{prize});
$o->refresh();

while(1) 
{
    my @oldworm;
    for (my $wormlen = @worm - 1; $wormlen >= 0; $wormlen--) {
        $oldworm[$wormlen]{y} = $worm[$wormlen]{y};
        $oldworm[$wormlen]{x} = $worm[$wormlen]{x};
    }
    for (my $wormlen = @worm - 1; $wormlen > 0; $wormlen--) {
        $worm[$wormlen]{y} = $worm[$wormlen-1]{y};
        $worm[$wormlen]{x} = $worm[$wormlen-1]{x};
    }
    my $key = $o->getch();
    if ($key eq 'h') { 
        $worm[0]{x}--;
    } elsif ($key eq 'j') {
        $worm[0]{y}++;
    } elsif ($key eq 'k') {
        $worm[0]{y}--;
    } elsif ($key eq 'l') {
        $worm[0]{x}++;
    } else {
        @worm = @oldworm;
        $o->clear();
        $o->box('|', '-');
        draw_worm(@worm);
        $o->move($worm[0]{y}, $worm[0]{x});
        next;
    }
    if ($growing != 0) {
        push @worm, $oldworm[scalar(@oldworm)];
        $growing--;
    }
    $o->move($worm[0]{y}, $worm[0]{x});
    my $char = $o->inch();
    if ($char =~ /\d/) {
        $growing += $char;
        $score += $char;
        $t->addstr(0, $maxx-8-length($score), "Score: $score");
        $t->refresh();
        prize();
    }
    elsif ($char ne ' ') { 
        crash(); 
    } 
    $o->clear();
    $o->box('|', '-');
    $o->addstr($prize{y}, $prize{x}, $prize{prize});
    draw_worm(@worm);
    $o->move($worm[0]{y}, $worm[0]{x});
    $o->refresh();
}
endwin();

sub draw_worm {
    my @worm = @_;
    my $head = shift @worm;
    $o->addstr($head->{y}, $head->{x}, '@');
    foreach my $segment (@worm) {
        $o->addstr($segment->{y}, $segment->{x}, 'o');
    }
}

sub crash {
    endwin;
    print "\nWell, you ran into something and the game is over.\n";
    print "Your final score was $score\n\n";
    exit;
}

sub prize {
    my ($a, $b);
    do {
        $a = rand($maxy-3) + 2;
        $b = rand($maxx-2) + 1;
        $o->move($a, $b);
    } while($o->inch ne ' ');
    my $v = int(rand(9) + 1);
    %prize = ( y => $a, x => $b, prize => $v );
}
