#!/usr/local/bin/perl -w

### Description
#
# Download the imdb.com diff for the specified day (defaults to today)
# and pick the "new movies" to write to a file.

use strict;
use Getopt::Long;
use Date::Manip;
use Net::FTP;
use LWP;
use Compress::Zlib; # Compress::Zlib not used directly, but Archive::Tar
use Archive::Tar;   # doesn't require it properly

### Configuration

use vars qw($ftplogin $ftppass $ftphost $ftpdir
	    $year_threshhold $hard_year_threshhold
	    $fudge_factor $fraction_power);

# ftp stuff
$ftplogin   = 'anonymous';
$ftppass    = 'yourname@yourhost';
$ftphost    = 'ftp.imdb.com';
$ftpdir     = '/pub/interfaces/diffs/';

# the number of years old a move can be and still be considered semi newish
$year_threshhold  = 1;
# automatically junk movies this old -- no matter how cool they are
$hard_year_threshhold = 3;

# the number of "votes" to consider "inconsequential"
# and subtract from all counts to help baseline at '0'
$fudge_factor = 0;

# This is an exponent that will be aplied to the movie_year / this_year
# fraction .. the bigger it is, the bigger a deal the date
# difference is to the score
$fraction_power = 25;

### Options

use vars qw($verbose $date $datemod
	    $opt_remote $opt_local $opt_http
	    $file $outfile $lazy);

# options that default to false
$opt_http = $verbose = 0;
# options that default to true
$lazy = $opt_local = 1;
# options that have values
$date = 'last friday';
$datemod = "0"; # date offset, passed to DateCalc
$outfile = undef; # set to '-' for STDIN
$opt_remote = undef;

GetOptions ('verbose!' => \$verbose,   # verbose
	    'remote!' => \$opt_remote, # get the .tgz file
	    'local!' => \$opt_local,   # untar, parse, gen scores
	    'lazy!' => \$lazy,         # be lazy, do nothing if outfile exist
	    'http!' => \$opt_http,     # use http instead of ftp
	    'date=s' => \$date,        # effective date
	    'datemod=s' => \$datemod,  # modification to the date (if needed)
	    'outfile=s' => \$outfile,  # output file
	    );

$date = DateCalc($date,$datemod);
$file = UnixDate($date, 'diffs-%y%m%d.tar.gz');
$outfile = UnixDate($date, 'output-%y%m%d.txt') unless defined $outfile;

if ($verbose) {
    warn "Verbose Mode\n";
    warn "Effective Date: $date\n";
    warn "Output File: $outfile\n";
}

### Should we even bother?

if ($outfile ne '-' and $lazy and -e $outfile and -s _) {
	warn "Output File exists, aborting.\n" if $verbose;
	exit();
}

### Set $opt_remote if we don't allready know it

if ( ! defined $opt_remote ) {
    # if we don't have explicit instructions, then look for the .tgz file
    # if we've allready got it, don't bother downloading.
    warn "Detectinging [no]remote behavior...\n" if $verbose;
    if (-e $file) {
	warn "  Tar file found, 'noremote' behavior\n" if $verbose;
	$opt_remote = 0;
    } else {
	warn "  Tar file NOT found, 'remote' behavior\n" if $verbose;
	$opt_remote = 1;
    }
}

### Get the Tar File

if ($opt_remote) {
    warn "Downloading file: $file\n" if $verbose;

    # Pick a method for getting hte file

    if ($opt_http) {

	# HTTP support isn't the greatest ...
	#  * won't work if passive mode is neccessary (firewall)
	#  * can't autodetect if file isn't available
	
	my ($ua, $req, $resp, $url);
	$url = "ftp://${ftphost}${ftpdir}${file}";
	
	warn "HTTP Download - $url\n" if $verbose;
	
	$ua = new LWP::UserAgent;
	$req = new HTTP::Request('GET', $url);
	$resp = $ua->request($req, "./$file");

	if ($resp->is_error()) {
	    # this might 
	    die "HTTP Failed: " . $resp->status_line() . "\n";
	}
	
    } else {

	# the GOOD way to get the file
	
	warn "FTP Download\n" if $verbose;
	
	my ($ftp, $status, $mod_date);
	
	$ftp = Net::FTP->new($ftphost, Debug => $verbose);
	die "FTP Failed: $@\n" unless defined $ftp;
	
	$ftp->login($ftplogin, $ftppass)
	    or die "FTP Failed (Login)\n";
	
	$ftp->cwd($ftpdir)
	    or die "FTP Failed: (cd $ftpdir)\n";
	
	$ftp->binary()
	    or die "FTP Failed: (binary)\n";

	$mod_date = $ftp->mdtm($file);
	if (!defined($mod_date)) {
	    # It's not an error for the file to not exist

	    warn "FTP '$file' not found\n" if $verbose;
	    $ftp->quit;
	    exit();
	}
	
	$status = $ftp->get($file);
	die "FTP Failed (get $file)\n" unless defined $status;
	
	$ftp->quit;
	
    }

}

### "Do Stuff" with teh Tar file

if ($opt_local) {
    warn "Uncompressing $file\n" if $verbose;

    my ($status, $tar);

    $tar = new Archive::Tar;
    $status = $tar->extract_archive($file, 1);
    die "UNTar Failed: " . &Archive::Tar::error() . "\n"
	unless defined $status;

}

### Meat of the Burger ... figure out who's new

if ($opt_local) {
    # Because of the format of the diff.tgz files, there is no easy way of
    # knowing if a movie is completley new or not -- and even if we did,
    # it might not get marked "sci-fi" when it's brand new ... that could
    # come later.
    # So.... what we pay attention to, is all movies that have had "sci-fi"
    # added to their genre's this time, that are either within the past X
    # years OR appear to be new based on the movies.list file (which could
    # mean their title was just edited .. but since the title might have
    # been ANYTHING, anywhere in the diff file -- we'll just cope with that)

    my ($genre_file, $movies_file)
	= ('genres.list', 'movies.list');
    
    my $this_year = UnixDate($date,'%Y');

    # take into account the threshholds
    my $magic_year = $this_year - $year_threshhold;
    my $hard_year  = $this_year - $hard_year_threshhold;
    
    my %movies; # a hash of hashes - key by movie, then file.
    my %years;  # year in the movie title - key by movie.
    my %scores; # absctract score of movies relevancy - key by movie.

    # iterators
    my $title;
    my $file;
    my $line;

    
    warn "Doing GENRE file\n" if $verbose;
    open GENRE, "diffs/$genre_file"
	or die "couldn't open file '$genre_file': $!\n";
    
    while (<GENRE>) {
	next unless m{
	    ^\> \s+              # starts with "> "
		(.*?             # main part of title [$1 = title]
		\((\d+)(/.*?)?\) # year inside parens, might be (1999/I)
		                 # [$2 = year, $3 = crap]
		(\s+\(.*?\))?    # [$4 = crap .. movies might be tv/vids/games]
		)\t+Sci\-Fi$     # must end in Sc-Fi
		}x;
	
	my ($y, $t) = ($2, $1);
	
	# don't even bother if it's really old
	if ($hard_year > $y) {
	    warn "   Ignoring Really Old: '$t'\n" if $verbose;
	    next;
	}
	
	$movies{$t} = { $genre_file => 1 };
	$years{$t} = $y;
    }

    close GENRE;

    warn "Doing MOVIE file\n" if $verbose;
    open MOVIES, "diffs/$movies_file"
	or die "couldn't open movie file '$movies_file': $!";

    while ($line = <MOVIES>) {
	next unless 0 == index $line, '> ';

	foreach $title (keys %movies) {
	    next unless -1 < index $line, $title;

	    # the movies list is really important, treat it like 2 files.
	    $movies{$title}{$movies_file} = 1;
	    $movies{$title}{$movies_file.'dup'} = 1; 
	}
	
    }

    close MOVIES;

    # drop any movie that's "old" and not in the movies file.
    warn "Dropping Oldies...\n" if $verbose;
    foreach $title (keys %movies) {
	if ($magic_year > $years{$title} and
	    1 == scalar keys %{$movies{$title}})
	{
	    delete $years{$title};
	    delete $movies{$title};
	    warn "   Ignoring '$title'\n" if $verbose;
	}
    }

    warn "Looping over all the list files...\n" if $verbose;
    foreach $file (<diffs/*>) {
	next if $file =~ m/$genre_file|$movies_file/o;

	warn " ... $file\n" if $verbose;
	open FILE, $file or die "couldn't open '$file': $!\n";
	
	while ($line = <FILE>) {
	    
	    next unless 0 == index $line, '> ';

	    foreach $title (keys %movies) {
		next unless -1 < index $line, $title;
		
		$movies{$title}{$file} = 1;
	    }
	}
	close FILE;
    }

    warn "Generating Scores\n" if $verbose;
    foreach $title (keys %movies) {
	# the more files it's in, and the later the year, the higher the score.
	# negative scores should be dropped (even if they are "new enough",
	# they aren't "new enough with enough data"

	# basic scoring:  year + files - today
	#$scores{$title} = $years{$title} +
	#    scalar(keys %{$movies{$title}}) - $this_year - $fudge_factor;
	
	warn "  $title\n" if $verbose;

	$scores{$title} = (scalar(keys %{$movies{$title}})
			   - $fudge_factor + $years{$title});
	warn "     Base Score:    $scores{$title}\n" if $verbose;
	
	$scores{$title} *= (($years{$title} / $this_year) ** $fraction_power);
	warn "     Score w/ratio: $scores{$title}\n" if $verbose;

	$scores{$title} -= $this_year;
	warn "     Normalized:    $scores{$title}\n" if $verbose;
	
    }

    warn "Outputing Scores\n" if $verbose;
    my $outhandle = \*STDOUT;
    if ($outfile ne '-') {
	open $outhandle, ">$outfile" or die "Can't open '$outfile': $!";
    }
    
    foreach $title (sort { $scores{$b} <=> $scores{$a} } keys %scores) {
	print $outhandle  "$scores{$title}\t$title\n";
    }
}


