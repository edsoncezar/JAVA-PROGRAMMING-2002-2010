=**************************************
    = Name: Map
    = Description:Simple utility that moves 
    =     a character over vaious grid maps
    = By: Joe Creaney
    =
    =This code is copyrighted and has    = limited warranties.Please see http://w
    =     ww.Planet-Source-Code.com/vb/scripts/Sho
    =     wCode.asp?txtCodeId=439&lngWId=6    =for details.    =**************************************
    
    #! usr/bin/perl -w
    use strict;
    my $c;
    my $i=0;
    my $ypos= "0";
    my $xpos= "0";
    my @grid;
    sub grid1 {
    my @gd1=(
    [ qw( . T . . . )],
    [ qw( . . T . . )],
    [ qw( . . . . . )],
    [ qw( . . M . . )],
    [ qw( . M M . . )],
    );
    return @gd1;
    }
    sub grid2 {
    my @gd2=(
    [ qw( . . T . . M . . .)],
    [ qw( . T T T M M . . .)],
    [ qw( T T . . M . . . .)],
    [ qw( T . . M M . . T .)],
    [ qw( . . . . . M T T T)],
    [ qw( T . . . M M T . T)],
    [ qw( . T T . M . T . .)],
    [ qw( . . T T . M . T .)],
    [ qw( T . T . M . M . T)],
    );
    return @gd2;
    }
    sub grid3 {
    my @gd3=(
    [ qw( . . . M . . . . . .)],
    [ qw( . . T T . . . . . .)],
    [ qw( T M . . . . . . T T)],
    [ qw( . T . . M . . . T .)],
    [ qw( T . . M . . . T . .)],
    [ qw( . T M . M . . . T .)],
    [ qw( . . T M . . . T . T)],
    );
    return @gd3;
    }
    sub pgrid {
    my ($xp,$yp,$grd) = @_;
    my $l;
    my $l1;
    my $ter;
    print "Printing Grid \n";
    
    for $l ( 0 .. $#$grd ) {
    for $l1 (0 .. $#{$$grd[$l]}) {
    if ( ( $xp == $l1 ) and ( $yp == $l ) ) {
    print "*";
    } else {
    print "$$grd[$l][$l1]"; 
    }
    }
    print "\n";
    }
    if ($$grd[$yp][$xp] eq "T" ) {
    print "Trees \n";
    }
    if ($$grd[$yp][$xp] eq "M" ) {
    print "Mountians \n";
    }
    }
    sub move {
    my ($mi, $xm, $ym, $grid) = @_;
    my %mov = ( n => [ 0, -1], s => [ 0, 1], e => [1, 0], w => [-1, 0], q => [0, 0]);
    $xm = $xm + $mov{$mi}[0];
    $ym = $ym + $mov{$mi}[1];
    if ($xm > $#$grid)
    {$xm=0;}
    if ($xm < 0)
    {$xm=$#$grid;}
    if ($ym > $#{$grid[$xm]})
    {$ym=0;}
    if ($ym < 0)
    {$ym=$#{$grid[$xm]};}
    return $mi, $xm, $ym;
    	
    	
    }
    while (($i < 1 ) and ($i < 3)) {
    print "Please choose grid 1,2 or 3 \n";
    $i = <STDIN>; chomp $i;
    }
    if ($i == 1) {
    @grid = grid1; }
    if ($i == 2) {
    @grid = grid2; }
    if ($i == 3) {
    @grid = grid3; } 
    pgrid ($xpos,$ypos,\@grid);
    while ($i ne "q") {
    print "Which way:";
    $i=<STDIN>; chomp $i;
    ($i, $xpos, $ypos) = move($i, $xpos, $ypos,\@grid);
    pgrid ($xpos, $ypos,\@grid);
    exit if ($i=~/^q/)
    }
    print "You are done!";

