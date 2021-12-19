#!/usr/local/bin/perl
    $, = ' '; $\ = "\n";
    if($ARGV[0] =~ '-v') { $vu++; shift; }
    die "Usage: solv fun x0 x1\n" if ($#ARGV != 2);
    $fun = $ARGV[0];
    $x0 = $ARGV[1];
    $x1 = $ARGV[2];
    $n = 20;
    $x = $x0; $y0 = eval($fun);
    print $x0, $y0 if $vu;
    while($x0 != $x1 && --$n>0) {
    $x = $x1; $y1 = eval($fun);
    print $x1, $y1 if $vu;
    last if $y0 == $y1;
    $x2 = ($y1*$x0-$y0*$x1)/($y1-$y0);
    $x0 = $x1; $y0 = $y1; $x1 = $x2;
    }
    print $x1;
    print 'No convergence!' if $n <=0;

