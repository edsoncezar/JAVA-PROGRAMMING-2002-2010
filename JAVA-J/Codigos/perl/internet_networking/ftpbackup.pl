#!/usr/bin/perl
#
# Entries in the file and host list should be seperated with
# a new row. spaces or other signs will make the script fail.
# You can use comments in the host and file lists if you put
# a '#' sign in the beginning of the line.
#
use Net::FTP;
use strict;

my $usage = "$0 filelist hostlist username password directory\n";
my $filelist = $ARGV[0] or die "$usage";
my $hostlist = $ARGV[1] or die "$usage";
my $username = $ARGV[2] or die "$usage";
my $password = $ARGV[3] or die "$usage";
my $home = $ARGV[4] or die "$usage";
my $debug = 5;
our $nr1 = 0;

open(FilesToGet, $filelist)
or die "can't open filelist $filelist: $!\n";

open(HostsToGet, $hostlist)
or die "can't open hostlist $hostlist: $!\n";

my @hostarr = <HostsToGet>;
chomp @hostarr;
my $hostarrnr = @hostarr;

my @filearr = <FilesToGet>;
chomp @filearr;
my $filearrnr = @filearr;

@hostarr = grep !/^#/, @hostarr;
@filearr = grep !/^#/, @filearr;

my $filearrnr = @filearr;
my $hostarrnr = @hostarr;

print "\nHosts: $hostarrnr\n";
print "Files: " . ($filearrnr * $hostarrnr) . "\n\n";

sub ftpbackup {
	if (my $pid = fork) {
		print STDERR "Connecting to $hostarr[$nr1]\n" if $debug > 3;
		my $ftp = Net::FTP->new($hostarr[$nr1]);
		$ftp->login($username, $password);
		$ftp->cwd($home);
		for (my $nr2 = 0; $nr2 < $filearrnr; $nr2++) {
			print STDERR "Getting $filearr[$nr2]\n" if $debug > 3;
			$ftp->get($filearr[$nr2],("$hostarr[$nr1]." . $filearr[$nr2]));
		}
		$ftp->quit;
		exit;
	}
}

for ($nr1 = 0; $nr1 < $hostarrnr; $nr1++) {
	ftpbackup();
}

