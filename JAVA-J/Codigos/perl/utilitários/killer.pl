#!/usr/bin/perl -w
use strict;
die "Usage:  $0 regex\n"   unless  1 == @ARGV;
for my $proc (
    map {(split(' '))[1]} grep /$ARGV[0]/, `ps -ef`
) {
    if(  ! kill(-9,$proc)  ) {
        warn "Can't kill PID $proc: $!\n";
    } else {
        print "Killed PID $proc.\n";
    }
}

