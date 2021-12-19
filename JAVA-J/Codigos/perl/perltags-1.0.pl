#!/usr/bin/perl -w

#perltags - tag perlfiles
#Daniel Bretoi, Jan 2001  daniel@netwalk.org
##############################################################################

##############################################################################
=pod
=head1 perltags

=item DESCRIPTION:

perltags - makes tags files for perl. A tags file gives the locations 
of subs in a group of files.  Each line of the tags file contains the 
sub name, the file in which it is defined, and a search pattern for 
the object definition, separated by white-space.

=item OPTIONS:

        -r      recursive tagging
        -a      append to tags file
        -v      verbose mode
        -h      help
 
=item INPUT:

A regex of the files to match for tagging. 
Something like \.cgi$ for all files ending with .cgi

=item OUTPUT:

Creates or appends to a tags file in the current directory.

=item NOTES:

Made by Daniel Bretoi <daniel@netwalk.org>

Feel free to send any improvements or suggestions to the author.

=item SCRIPT CATEGORIES

CPAN/Administrative
Perl/Programming

=cut
###############################################################################
use strict;
use Cwd;
use File::Find;

unless ($ARGV[0]) { &usage_end; }

my $prune=1;
my ($warn,$verbose);

for (@ARGV) {
   if (/^-(.*)/) {
      my $qp = $1;
      if ($qp =~ /[^ravh]/) { print "invalid switch -$qp\n\n"; &usage_end; }
      if ($qp =~ /h/) { &usage_end;exit; } 
      if ($qp =~ /r/) { $prune=0; } 
      if ($qp =~ /a/) { open TAGS,"| sort >>tags"; } 
      if ($qp =~ /v/) { $verbose=1; } 
   }
}

unless (fileno TAGS) { open TAGS,"| sort >tags"; }

find(\&Wanted,".");

sub Wanted {


	my $file = $_;

	if ((-d $file) && ($file !~ /^\.$/)) { $File::Find::prune=$prune; }

	for (@ARGV) {
   	if (/^-(.*)/) { next; }
		if ($file =~ /$_/) {
			if (-d $file) {next;}
			if ($verbose) {print "Tagging $file\n";}
			if (-r $file) {open FH,"<$file";} else {$warn=1;}
			$file = cwd . "/$file";
			if (fileno FH) { for (<FH>) { print TAGS "$1\t$file\t/^$&/;\"\n" if /^sub\s+([\w_\d]+).*/; }}
		}
	}
}

if ($warn) { print "Some of these files were not readable.\nThose files have not been tagged.\n";}
	close TAGS;
	close FH;

sub usage_end {
   print "Usage: $0 [options] <files> \n";
   print "Where <files> is stated as a regexp\n";
   print "\tOptions:\n";
   print "\t-r\trecursive tagging\n";
   print "\t-a\tappend to tags file\n";
   print "\t-v\tverbose mode\n";
   print "\t-h\tthis help\n\n";
   print "Example:\n";
   print "$0 \\.cgi\$\n";
   print "Tags all .cgi files recursivly.\n";
   exit;
}
