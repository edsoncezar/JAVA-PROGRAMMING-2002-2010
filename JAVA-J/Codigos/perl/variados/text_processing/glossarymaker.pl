#!/usr/bin/perl

# gutenberg_ftp.pl LIST OF LASTNAMES
#
#  Will download all the book titles under each author on the list 
#  of names into a local archive. It must be run from the directory
#  where the archive resides.
#
#  Example:
#
#  % mkdir archive
#  % gutenberg_ftp.pl Conan\ Doyle Conrad Gogol Darwin
#
#  After running these commands archive will contain one sub directory 
#  for each author, and each of these will contain all the books for that
#  author on Project Gutenberg.

use LWP::UserAgent;
use URI::URL;
use HTML::TokeParser;

$ua = new LWP::UserAgent;
$ua->agent("efetepeador");

#$ua->proxy([ 'ftp' ], 'http://www-dms.esd.sgi.com:8080/');  

# Create a new request.
my $req = new HTTP::Request GET => 
    "ftp://sailor.gutenberg.org/pub/gutenberg/by-author.html";
my $res = $ua->request($req, '/tmp/efete');
if(! $res->is_success) {
    print STDOUT "Failure to connect to server: " . $res->message;
}

$prsr = HTML::TokeParser->new('/tmp/efete');

while (my $token = $prsr->get_tag("a")) {
    my $url = $token->[1]{href} || "-";
    my $text = $prsr->get_trimmed_text("/a");

    foreach (@ARGV) {
      if($text =~ /$_/i) {
	print "found $_\n";
	push @{$authorURLs{$_}}, ($url);
      }       
    }
#    print "$url\t$text\n";
} 

AUTHOR:
foreach $author (keys %authorURLs) {
  print "Retrieving author $author ";
  for (@{$authorURLs{$author}}) { 
    print "URL $_ contains: \n";
    # Create a new request.
    my $authReq = new HTTP::Request GET => 
      "ftp://sailor.gutenberg.org/pub/gutenberg/$_";
    my $authRes = $ua->request($authReq, '/tmp/authefete');
    if(! $authRes->is_success) {
      print STDOUT "Failure to connect to server: " . $authRes->message;
    }
    
    $authPrsr = HTML::TokeParser->new('/tmp/authefete');

    #substitute space for underscore if needed    
    $author =~ s/ /_/g;

    if(! opendir AUTH_DIR, "archive/$author") {
      `mkdir archive/$author`;
    } 
    elsif(! opendir AUTH_DIR, "archive/$author") {
      print STDOUT "couldn't create directory: archive/$author\n";
      next AUTHOR;
    }

  RETRIEVING:    
    while (my $authToken = $authPrsr->get_tag("a")) {
      my $bookUrl = $authToken->[1]{href} || "-";
      my $bookText = $authPrsr->get_trimmed_text("/a");      
      if($bookUrl =~ /\/([^\/]+\.gz)\b/) {
	$book = $1;
	#check for file presence before attempting download
	rewinddir AUTH_DIR;
	while($dirEntry = readdir(AUTH_DIR)) {
	  if($dirEntry =~ /$book/) { 
	    print "$book already in local repository\n"; 
	    next RETRIEVING;
	  } 
	}
	print "downloading into archive/$author/$book ..\n";
	#pause for one minute
        sleep(60);
	my $bookReq = HTTP::Request->new("GET", $bookUrl);
	my $bookRes = $ua->request($bookReq, "archive/$author/$book");
	if(! $bookRes->is_success) {
	  print STDOUT "Failure to connect to server: " . $bookRes->message .
	    "\n";
	}
      }
    }
  }  
}





#-------------------------------------------------------------------------

#!/usr/local/bin/perl
#
# indexer.pl  
#
#  Will generate a DB database file containing a histogram of word 
#  frequencies of the book archive created by the gutenberg_ftp.pl
#  program. 
#
#  To use it just run it from the directory where the 'archive' directory
#  was created. It will generate two files, one of them called index.db
#  containing the histogram and the other called indexedFiles.db containing
#  the names of the files indexed so far (this last one allows us to add 
#  books to the archive and index them without analizing again the ones we 
#  already had).
#
#  Note that this script is very innefficient and requires a good deal of
#  free memory on your system to run. A new version should use MySQL instead
#  of DB files to speed it up.

require 5;
use DB_File;    # Access DB databases
use Fcntl;      # Needed for above...
use File::Find; # Directory searching
undef $/; # Don't obey line boundaries
$currentKey = 0;

# Single database version:
#    Stores file entries in index.db as <NULL><ASCII file number>
#    The leading NULL prevents any word entries from colliding.
############################################################################
#unlink("index.db");         # Delete old index.db
tie(%index,'DB_File',"index.db", 
    O_RDWR | O_CREAT, 0644, $DB_File::DB_BTREE) ;

tie(%indexedFiles,'DB_File',"indexedFiles.db", 
    O_RDWR | O_CREAT, 0644, $DB_File::DB_BTREE) ;

foreach $filename (keys %indexedFiles) {
}

$wordCount = 0;

find(\&IndexFile,"archive");       # Index all of the files

#show findings here

print "..indexed $wordCount words\n";


untie(%index);                      # Close database
untie(%indexedFiles);                      # Close database
###########################################################################
sub IndexFile {  
  #only gzipped files
  $fName = $_;

  if(!/\.gz/) {return; }
  
  if(!exists $indexedFiles{$File::Find::name}) {    
    if(open TEXTO, "gunzip -c $fName |") {
      $indexedFiles{$File::Find::name} = true;
    } else {
      print "couldn't fork for gunzip: $!\n";
    }

    print "indexing $File::Find::name\n";

    my($text) = <TEXTO>;        # Read entire output
    $wordCount += &IndexWords($text);    
  } else {
    print "already indexed: $File::Find::name\n";
  }
}
###########################################################################
sub IndexWords {
    my($words) = @_;
    my(%worduniq);          # for unique-ifying word list
    # Split text into Array of words
    my(@words) = split(/[^a-zA-Z0-9\xc0-\xff\+\/\_]+/, lc $words);
    @words = grep { s/^[^a-zA-Z0-9\xc0-\xff]+//; $_ }  # Strip leading punct
             grep { length > 1 }                   # Must be > 1 char long
             grep { /[a-zA-Z\xc0-\xff]/ }       # alpha chars only
	     grep { !/[0-9\x00-\x01f]/ }           # No nums or ctrl chars
             @words;

    #count, remove duplicates
    @words = grep{ $numWords{$_}++ == 0 } @words;         # Remove duplicates

    foreach (@words) {
        my($a) = $index{$_};
        $a += $numWords{$_};
        $index{$_} = $a;
    }

    return scalar(@words);    # Return count of words indexed
}



#-------------------------------------------------------------


#!/usr/local/bin/perl

# glossary_maker.pl BOOK_FILE
#
# Will take a book from the archive created by the gutenberg_ftp.pl 
# script and will look at the word count for each of its words on the 
# histogram of word frequencies created by indexer.pl. Starting with the 
# less frequent words it will prompt the user to choose which ones to
# include on the glossary. When the user stops choosing words the program
# will query a web dictionary and print the definition of all the chosen
# words to STDOUT.

require 5;
use DB_File;    # Access DB databases
use Fcntl;      # Needed for above...
use File::Find; # Directory searching
use Term::ReadKey;

require HTML::TreeBuilder;
require HTML::FormatText;
use LWP::UserAgent;

$|=1;     # Asynchronous I/O.
undef $/; # Don't obey line boundaries

$gzFile = $ARGV[0] || die "usage: indagador.pl gzippped_file\n";

open TEXTO, "gunzip -c $gzFile |" ||  die "couldn't fork for gunzip: $!\n";

my($text) = <TEXTO>;     

#%bookWords = &IndexWords($text);   
$bookIndexRef = &IndexWords($text);   

tie(%index,'DB_File',"index.db",    # Open DB file
    O_RDONLY, 0, $DB_File::DB_BTREE) or die "no abre: $!";

$occurs = 1;

print "n to not include\ny to include\nb to o back one\nq to start querying\n\n";

ReadMode 4; # Turn off controls keys

REVISANDO: while(true) {
  print "-- Occuring $occurs times: \n";
  print ($occurs == 1) ? " ---\n":"s ---\n";

 SALIR_ANTES:
  foreach $palabrita (sort keys %$bookIndexRef) {   
    if($index{$palabrita} == $occurs) {
      print " $palabrita [n]: ";

      while (not defined ($key = ReadKey(-1))) {}

      print "$key\n";
      if ($key =~ /b|B/) {
	ReadMode 0;
	print("which one did you miss? ");
	$palabrita = read STDIN, $cars, 128;
	push @elegidas, $palabrita;
	ReadMode 4;
      }

      if ($key =~ /y|Y/) {
	push @elegidas, $palabrita;
      }
      elsif ($key =~ /q|Q/) {
	last REVISANDO;
      }

      if($contador++ == 10) {
	$contador = 0;
	last SALIR_ANTES;
      }

    }
  }  
  $occurs++;
}

# Reset tty mode
ReadMode 0;


$ua = new LWP::UserAgent;
$ua->agent("Diccionario");
# $ua->proxy([ 'http' ], 'http://www-dms.esd.sgi.com:8080/');

for (@elegidas) {
  #submit query -- one every five or so seconds

  print"Elegida: $_\n";

  $elegida = $_;

  QueryDict($elegida, $ua);

}

untie(%index);                      # Close database



sub QueryDict {
  my ($palabrita, $agent) = @_;
  
  #
  # Create a new request.
  #
  
  my $req = new HTTP::Request GET => "http://work.ucsd.edu:5141/cgi-bin/http_webster?isindex=$palabrita&method=exact";
  
  #
  # Pass Request to the user agent and get a response back.
  #
  my $res = $agent->request($req);
  
  #
  # Print outcome of the response.
  #
  if(! $res->is_success) {
    print "Failure to connect to server: " . $res->message;
  } else {


    $html = $res->content;

    $p = HTML::TreeBuilder->new;
    $p->parse($html);
    $formatter = HTML::FormatText->new(leftmargin => 0, rightmargin => 60);
    $result = $formatter->format($p);
    
    @parrafos = split /^\s+/m, $result;
    
    for($x=2; !($parrafos[$x] =~ /^From WordNet/) and $x < 10; $x++) {
      print $parrafos[$x]."\n";
    }    
  }
}

sub IndexWords {
    my($words) = @_;
    my(%worduniq);          # for unique-ifying word list
    # Split text into Array of words
    my(@words) = split(/[^a-zA-Z0-9\xc0-\xff\+\/\_]+/, lc $words);
    @words = grep { s/^[^a-zA-Z0-9\xc0-\xff]+//; $_ }  # Strip leading punct
             grep { length > 1 }                   # Must be > 1 char long
             grep { /[a-zA-Z\xc0-\xff]/ }       # alpha chars only
	     grep { !/[0-9\x00-\x01f]/ }           # No nums or ctrl chars
             @words;

    #count, remove duplicates
    @words = grep{ $numWords{$_}++ == 0 } @words;         # Remove duplicates

    foreach (@words) {
        my($a) = $bookIndex{$_};
        $a += $numWords{$_};
        $bookIndex{$_} = $a;
    }

    print "indexed " , scalar(@words) . "\n";
    
    return (\%bookIndex);    # Return count of words indexed
}
