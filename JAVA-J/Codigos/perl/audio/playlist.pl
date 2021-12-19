#!/usr/bin/perl -w

# mp3playlist.pl by Viking

use strict;

if ($ARGV[0]) {
        &parsedir($ARGV[0]);
} else {
        print "mp3playlist.pl by Viking\n";
        print "usage: mp3playlist.pl <dir>\n";
}

sub parsedir {

        # get current directory
        my $currentdir = $_[0];

        # create dir listing
        opendir DIR, $currentdir;
        my @dirlist = readdir DIR;
        close DIR;

        # loop thru dir listing
        for (@dirlist) {

                # ignore "." and ".."
                if (!(/^\.{1,2}$/)) {

                        # get file mode
                        my $mode = (stat "$currentdir/$_")[2];

                        # if directory recurse routine with new directory
                        if ($mode =~ /^1/) {
                                &parsedir("$currentdir/$_");
                        # if mp3 file print path and name
                        } elsif (/\.mp3$/) {
                                print "$currentdir/$_\n";
                        }
                }
        }
}

