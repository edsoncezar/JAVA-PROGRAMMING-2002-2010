#!/usr/bin/perl -w

# scribbled by CBAS [ mailto:cbas@screaming3d.com ]
# 30/12/2000
#
# "One more kick to get me started ..."
#   - Republica

use strict;

use subs qw {
	doDir
	doFile
	freakOut
};

use vars qw {
	$from
	$into
	$recur
	$renamed
	$directories
	$argument
};

freakOut and exit unless @ARGV >= 4;

$renamed		= 0;
$directories	= 0;
$from			= shift @ARGV;
$into			= shift @ARGV;
$recur			= shift @ARGV;

print <<EOL;
Renaming files from "foo$from" to "foo$into" ...
EOL

for $argument ( @ARGV ) {
	print "WARNING: \"$argument\" could not be found as a directory.\n"
		unless -d $argument and doDir ( $argument );
}

print <<EOL;
Renamed $renamed file(s) in $directories directories.
EOL

sub doDir {
	my ( $dir, $foo, @fools );
	$dir = shift;
	opendir DIR, $dir or return 0;
	@fools = readdir DIR;
	closedir DIR;
	shift @fools; shift @fools;
	foreach $foo ( @fools ) {
		if ( $recur and -d "$dir/$foo" ) {
			doDir ( "$dir/$foo" );
		} elsif ( -f "$dir/$foo" and $foo =~ /$from$/ ) {
			doFile ( "$dir/$foo" );
		}
	}
	$directories++;
	return 1;
}

sub doFile {
	my ( $file, $temp );
	$file = shift;
	$temp = $file;
	$file =~ s/(.*)$from$/$1$into/;
	if ( rename $temp, $file ) {
		$renamed++
	} else {
		print "couldn't rename $temp: $!"
	}
}

sub freakOut {
	print <<EOL;
Usage:
	$0 from into recur dir [dir [dir ... ]

Where:
	from
		the file extension you want to change
	into
		to what you want to change from
	recur
		renaming will happen recursively when set to anything but 0
	dir
		directory that will be traversed by the script

Example:
	$0 .MP3 .mp3 1 .
EOL
}

