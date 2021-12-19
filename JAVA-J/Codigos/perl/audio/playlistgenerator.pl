#!c:\perl\bin\perl.exe -w
#
# WinAMP M3U Sorted Playlist Generator version .0.1 ("Bob") December 2001
# <xunker@pyxidis.org>
#
use strict;

use File::Find;
use MP3::Info;

my $Debug = 0;
my $usage = "listmaker.pl <source dir> <output file> [<verbose>]\n";

my ($source_path, $output_filename, $verbose) = @ARGV;
die $usage unless (($source_path) && ($output_filename));

die "that path doesn't exist, hombre" unless (-e $source_path);

unlink $output_filename if (-e $output_filename);

my @files; my %shortname;

sub addFile {
	$shortname{$File::Find::name} = $_;
	return unless -f;
	return unless /\.mp3$/;
	push @files, $File::Find::name;
	print '.' if $verbose;
}
print "\n" if $verbose;

find (\&addFile, $source_path);

@files = sort {uc($a) cmp uc($b)} @files;

open FILE, ">$output_filename"
	or die "could not open $output_filename for writing: $!";
	
print FILE "#EXTM3U\n";
my $counter = 1;  my $max = scalar (@files);
foreach my $file (@files) {
	my $tag = get_mp3tag($file);
	my $info = get_mp3info($file);
	my $pair;
	if (($tag->{ARTIST}) && ($tag->{TITLE})) {
		$pair = $tag->{ARTIST} . ' - ' . $tag->{TITLE};
	} else {
		$pair = $shortname{$file};
	}
	print FILE "#EXTINF:"
		. int ($info->{SECS})
		. ","
		. $pair
		. "\n";
	$file =~ s/\//\\/g; # this is for WinAMP/MS-DOS;
                            # by default, File::Find returns
                            # filenames with the forward
                            # (proper) slash, but I use the
                            # program in Windows, so...
                            # If you use a real OS, remove.
	print FILE "$file\n";
	
	print "$counter of $max: $pair\n" if $verbose;
	$counter++;
}

close FILE;

