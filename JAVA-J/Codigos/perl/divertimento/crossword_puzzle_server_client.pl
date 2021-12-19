#!/usr/bin/perl -w use strict; # I wrote this to generate a file, not be a cgi script, but you could... # use CGI.pm; open GRID, '>../html/grid.html' or die $!; select GRID; print "\n"; print "
\n"; my $cellwidth = '20'; # pixel or percent my $cellheight = '4'; # pixels only my $counter = 0; sub Solid { return " \n"; } sub Open { return "\n"; } sub Numbered { ++$counter; return "\n"; print "\n
"; print ""; #### #! /usr/bin/perl -w require ("cgi-lib.pl"); $success=0; print "Content-type: text/html\n\n"; @my_query = split(/&/,$ENV{'QUERY_STRING'}); foreach (@my_query) { @key_value = split(/=/,$_); push @solved, $key_value[1]; } $solved_str = join('',@solved); if ($solved_str eq "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefgh ijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghij klmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcd") { $success=1; } if ($success) { print << 'End_Of_Text'; 
Hooray! You've solved the puzzle!
Congrats. Go out, have a beer. Eat some salami. End_Of_Text } else { print << 'End_Of_Text'; 
Sorry, try again.
One of your answers is incorrect. Click back and try again. End_Of_Text } 