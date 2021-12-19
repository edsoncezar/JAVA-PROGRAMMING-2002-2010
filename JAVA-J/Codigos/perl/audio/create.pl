use strict;
use warnings;
use File::Basename;

my $del_m3u = 1;

my $dir = $ARGV[0] || die "You need to provide a directory";

handle_dir($dir);

exit;

sub handle_dir {
	
	my $dir 	= shift;
	my @songs 	= ();
	
	print "Dir: $dir\n";
	
	opendir DIR, $dir or die "Unable to open dir -- $!";

	foreach my $file (sort readdir DIR) {
		if (-d "$dir/$file") {
			next if $file =~ /^\.+$/;
			handle_dir("$dir/$file")
		} elsif ($file =~ /\.[Mm]3[Uu]$/ && $del_m3u) {
			unlink "$dir/$file" or die "Unable to delete $file -- $!";
		} elsif ($file =~ /\.[Mm][Pp]3$/) {
			push @songs, $file;
		}
	}

	closedir DIR;

	my $playlist = $dir . "/" . basename($dir) . ".m3u";
	
	if (@songs > 0) {		
		open OUT, ">$playlist" or die "Unable to write to $playlist -- $!";	
		print OUT $_, "\n" foreach @songs;	
		close OUT;
	}
	
}


