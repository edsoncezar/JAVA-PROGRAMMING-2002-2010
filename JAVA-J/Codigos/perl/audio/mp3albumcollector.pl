#!/usr/bin/perl -w
#
# Cheezy method to create HTML lists for every mp3 album in a directory
#

# FEATURES:
# . Reads ID3 v1 and v2 tags
# . Configurable output format
# . Simple to use
#
# TODO:
# . Implement unsynchronisation for the ID3v2 tags
# . Maybe make "smart album creation" optional to also include "lonely" tracks
#
# NOTE:
# This is a "live" updated version that should be able
# to replace variables even in multi-line templates, but
# I haven't tested that part yet (shame on me, I know) -
# so if this code dosen't work, put the stuff in the template
# on one line again.

# These modules should be in every (standard) distribution of Perl
use File::Spec;                                # So that it even works on Macs ...
use File::Basename;
use IO::File;
use Getopt::Long;

$Version = "MP3Album v0.82 - corion\@informatik.uni-frankfurt.de";

$optVerbosity = 2;                                # Talk about many things
$optTemplateName = "main::DATA";                # Use the built-in template
$optFilespec = "*.mp3";

%Options = ( "verbosity" => \$optVerbosity,
             "template" => \$optTemplateName,
             "filespec" => \$optFilespec,
             "help" => \$optHelp,
           );

GetOptions( \%Options, "template=s", "verbosity=i", "filespec=s", "help|h|?") or die "Use --help for help\n";

die <<end_of_help
$Version
SYNTAX: mp3album.pl [options] [directories ...] [files ...]

Mp3album creates a file from the ID3 tag information found in the files.
See http://www.id3.org for more information about the ID3 format.
Currently mp3album only supports complete albums, that is, albums
that contain a track with track number 1. All other tracks with
the same album title are then pulled together with this one,
regardless of where they actually reside.

Directories will not be searched recursively. The default directory is
the current directory.

--verbosity=i                Set verbosity of output (0-2)
--template=s                Set the name of the template file (see __DATA__ section
                        of the source code for format)
--filespec=s                Set the filespec (default is *.mp3)
                        This string must be quoted from shell expansion under
                        Unix shells !
--help                        Display this screen

This program is complete freeware without any license. If you want to make
the ID3 code into a module, feel free to and maybe mail me the results.
end_of_help
if $optHelp;

@optDirectories = ();
@Files = ();

# Now sort everything left on the command line into either files or directories
while ($tmp = shift) {
  if (-f $tmp) {
    push @Files, $tmp;
  } elsif (-d _) {                                # Some (smart?) optimization to save one system call
    push @optDirectories, $tmp;
  } else {
    print "ERROR: \"$tmp\" is neither a directory nor a file";
  };
};

if ($#optDirectories + $#Files == -2) {
  push @optDirectories, File::Spec->curdir;
};

# The hash will contain all template data
%Template = ();

die "\"$optTemplateName\" not found." if (!(-e $optTemplateName|| ($optTemplateName eq "main::DATA")));
if ($optTemplateName ne "main::DATA") { open DATA, "<" . $optTemplateName or die "Can't open \"$optTemplateName\" : $!\n" };
$SectionName = "";
while (<DATA>) {

  # Strip comments and empty lines from template
  next if (/^#/ || /^$/);

  if (/^%(.+)%$/) {
    $SectionName = lc($1);
    $Template{$SectionName} = "";
  } else {
    $Template{$SectionName} .= $_;
  };
};

# Strip <CR> from output filename template
chomp $Template{"outfile"};

%Data = ();                        # Filename -> file data map
@Albums = ();                        # Holds the data of the first file of all 
                                     # found albums (albums are found by looking for tracks with track number 1)

# The ID3v2 tags also get mapped to friendly names for compatibility with the
# mp3 shell extension
%TagNames = (
  # ID3 v2.2 tags
  "TRK" => "track",
  "TT2" => "title",
  "TP1" => "artist",
  "TAL" => "album",
  "COM" => "comment",
  "TCO" => "genre",
  "TYE" => "year",

  # ID3 v2.3 tags
  "TRCK" => "track",
  "TIT2" => "title",
  "TPE1" => "artist",
  "TALB" => "album",
  "COMM" => "comment",
  "TCON" => "genre",
);

# Type information for the different tags
%ID3v22TagTypes = (
  "TRK" => \&UnpackNumericString,
  "TT2" => \&UnpackString,
  "TP1" => \&UnpackString,
  "TAL" => \&UnpackString,
  "TSI" => \&UnpackNumericString,
  "TCO" => \&UnpackString,
  "COM" => \&UnpackComment,
);

%ID3v23TagTypes = (
  "TRCK" => \&UnpackNumericString,
  "TIT2" => \&UnpackString,
  "TPE1" => \&UnpackString,
  "TALB" => \&UnpackString,
  "TSIZ" => \&UnpackNumericString,
  "TCON" => \&UnpackString,
  "COMM" => \&UnpackComment,
);

# Bitrate information for mp3 decoding
@BitRates = (0, 32000, 40000, 48000, 56000, 64000, 80000, 96000, 112000, 128000, 160000, 192000, 224000, 256000, 320000, 0);

#$tmp = MP3Info( "diefan~1.mp3" );
#if (ref $tmp) {
#  foreach $key (sort keys %$tmp) {
#    print "$key:" . $tmp->{$key} . "\n";
#  };
#} else {
#  print $tmp;
#};
#exit;

# Convert the glob filespec into something suitable for a RE match
$optFilespec =~ s/([.+\/\[\]\(\)\'$^~])/\\$1/g;
$optFilespec =~ s/\*/\.\*/g;

print "$Version\n";
print "Reading ";
foreach $Directory (@optDirectories) {
  print $Directory . ", ";

  opendir DIR, $Directory or die "Can't read '$Directory' : $!\n";
  my @Contents = readdir( DIR );
  foreach $Entry (@Contents) {
    my $Name = File::Spec->catfile( $Directory, $Entry );
    if (-f $Name) {
      push( @Files, $Name ) if ($Name =~ /$optFilespec/i);
    };
  };
  closedir DIR;
};

print( ($#Files + 1). " file(s), reading ID3 info" );
foreach $File (@Files) {
  my( $tmp ) = MP3Info( $File );
  if ( ref $tmp ) {
    $Data{ $File } = $tmp;
  } else {
    # $tmp contains a (non fatal) error message instead of data on the file
    print "\n" . $tmp if ($optVerbosity > 1);
  };
};
undef @Files;

# Extract all (first tracks of) Albums
@Albums = grep { (($_->{"TRCK"}||0) == 1) } (values %Data);

if ($#Albums >= 0) {
  print ", " . ($#Albums + 1) . " album(s) found.\n";
} else {
  print ", no albums found, aborting.";
  exit;
};

# Now process each album
foreach $Album (@Albums) {
  print "Processing " . $Album->{"album"} . " (". $Album->{"artist"} . ")";

  # Find all tracks with a defined album title and a title equal to the current title
  @AlbumFiles = grep { (($_->{"album"}||"") eq $Album->{"album"}) } values %Data;

  print ", " . ($#AlbumFiles + 1) . " tracks";

  # Calculate total time
  $TotalTime = 0;
  foreach $Track (@AlbumFiles) {
    $TotalTime += $Track->{"sectime"};
  };

  # And update necessary variables
  foreach $Track (@AlbumFiles) {
    $Track->{"sectottime"} = $TotalTime;
    $Track->{"tottime"} = PlayTime( $TotalTime );
    $Track->{"count"} = $#AlbumFiles + 1;
  };

  # Sort all the tracks by track number
  @AlbumFiles = sort { $a->{"track"} <=> $b->{"track"} } @AlbumFiles;

  # Output an error if there are duplicate or missing track numbers
  foreach $TrackNum (1..$#AlbumFiles+1) {
    if ($AlbumFiles[ $TrackNum-1 ]->{"track"} != $TrackNum ) {
      print "\nWARNING: Missing/duplicate track(s) found";
      last;
    };
  };

  # Open the output file
  $Indexname = ReplaceVars( $AlbumFiles[0], $Template{"outfile"} );
  open( INDEX, "> $Indexname") or die "\nERROR creating \"$Indexname\" : $!\n";

  # And print out the stuff
  print INDEX ReplaceVars( $AlbumFiles[0], $Template{"header"} );
  foreach $Info (@AlbumFiles) {
    print INDEX ReplaceVars( $Info, $Template{"track"} );
  };
  print INDEX ReplaceVars( $AlbumFiles[0], $Template{"footer"} );

  close INDEX;
  # Done with this album
  print ", done.\n";
};

# Only boring stuff below here
exit;

# Replaces all "$()" with entries from the hash ref
sub ReplaceVars( $$ ) {
  my ($Result, $Varname);
  my $Ref = shift;
  my $Right = shift;

  $Result = "";
  while ($Right =~ /\$\(([a-zA-Z0-9]+)\)/sm ) {
    $Result .= $`;
    $Varname = lc($1);
    $Right = $';
    if (exists $Ref->{$Varname} ) {
      $Varname = $Ref->{$Varname};
    } else {
      print "\nWARNING: \"" . $Ref->{"filename"}. "\" : Undefined variable \"\$($Varname)\"" if ($optVerbosity);
      $Varname = "";
    };
    $Result .= "$Varname";

  };
  $Result .= $Right;

  return $Result;
};

sub TwoDigits( $ ) {
  my $Result = shift;
  if (length( $Result ) == 1) {
    $Result = "0$Result";
  };
  return "$Result";
};

sub PlayTime( $ ) {
  my $Time = shift;
  my $Result;
  my ($hour, $min, $sec) = (0,0,0);
  if ($Time) {
    $sec = $Time % 60;
    $min = int($Time / 60) % 60;
    $hour = int( $Time / 3600 );

    $Result = &TwoDigits( $min ) . ":" . &TwoDigits( $sec );
    if ($hour) {
      $Result = $hour . ":" . $Result;
    };
  };

  # Strip leading zero
  $Result =~ s/^0//;

  return $Result;
};

# Decodes a 28-bit number
sub DecodeNum( $ ) {
  my( $In ) = shift;
  @Bits = split //, $In;
  my $Result = 0;
  foreach $Bit (@Bits) {
    $Result = $Result * 128 + ord( $Bit );
  };
  return $Result;
};

# Checks if a string is ASCII and unpacks it (no unicode support here)
sub UnpackString( $ ) {
  my $String = shift;
  if ($String =~ /^\00(.*)$/) {
    $String = $1;
  } else {
    die "\nUnicode string encountered ($String). Unicode is not supported yet.\n";
  };
  return $String;
};

sub UnpackNumericString( $ ) {
  my $String = shift;
  $String = UnpackString( $String );

  my @Digits = split( //, $String );
  $String = 0;
  foreach $Digit (@Digits) {
    $String = $String * 10 + ord( $Digit ) - ord( "0" );
  };
  return $String;
};

# Unpacks a comment record
sub UnpackComment( $ ) {
  my $String = shift;
  $String = UnpackString( $String );

  $String =~ s/^...[^\x00]*\x00//;

  return $String;
};

# Reads information about the mp3 stream from the file
# If the result is undef, everything is OK
# If the result is defined, something went wrong and the result is the error message
sub ReadStreamInfo( $ ) {
  my $Hash = shift;
  my $Result = undef;

  my $Frame;

  # Get some statistics about the file
  my ( @Filedata ) = stat( MP3FILE ) or die "Can't stat() \"" . $Hash->{"filename"} . "\" : $!\n";

  # Get some data from the mp3 stream
  $Hash->{"filesize"} = $Filedata[7];
  $Hash->{"filesizek"} = int($Filedata[7] / 1042);
  $Hash->{"filesizem"} = int($Filedata[7] / (1042*1024));

  # Now calculate the bitrate and thus the playlength of the track
  seek( MP3FILE, $Hash->{"streamstart"}, 0 );

  # Synchronize to the next 0xFF after the header
  read( MP3FILE, $Frame, 1) or die "reading from $Filename : $!\n";
  while (ord( $Frame ) != 255) {
    read( MP3FILE, $Frame, 1) or die "reading from $Filename : $!\n";
  };
  read( MP3FILE, $Frame, 1) or die "reading from $Filename : $!\n";
  read( MP3FILE, $Frame, 1) or die "reading from $Filename : $!\n";

  my ($Bitrate) = $BitRates[ord( $Frame ) >> 4];

  if ($Bitrate) {
    $Hash->{"bitrate"} = $Bitrate;
    $Hash->{"sectime"} = 0;
    $Hash->{"sectime"} = int((($Filedata[7]-($Hash->{"streamstart"}+10)) * 8) / $Bitrate) if ($Bitrate);
    $Hash->{"time"} = PlayTime( $Hash->{"sectime"} );
  } else {
    $Result = "ERROR: Unknown bitrate in " . $Hash->{"filename"};
  };

  return $Result;
};

# Extracts information from a ID3v1 file
# Returns nothing
sub ReadID3v1Tags( $$ ) {
  my $Hash = shift;
  my $ID3Tag = shift;

  # Enter some known data into the hash
  $Hash->{"tagversion"} = "ID3v1.0";
  $Hash->{"taglength"} = 128;
  $Hash->{"tagoffset"} = tell( MP3FILE ) - $Hash->{"taglength"};
  $Hash->{"streamstart"} = 0;

  my ($tagTAG, $tagTitle, $tagArtist, $tagAlbum, $tagYear, $tagComment, $tagGenre) = unpack( "a3A30A30A30A4a30A", $ID3Tag );

  # Fix for the ID3v1 extension, where the track number is stored in the last two bytes
  # of the comment field :
  if ($tagComment=~ s/\00([\x01-\x63])$//sm) {
    $Hash->{"TRCK"} = ord( $1 );
  };

  # Strip trailing stuff
  foreach $Tag (\$tagTitle,\$tagArtist,\$tagAlbum,\$tagComment,\$tagGenre) {
    $$Tag =~ s/[\00 ]+$//sm;
  };

  $Hash->{"TIT2"} = "$tagTitle";
  $Hash->{"TPE1"} = "$tagArtist";
  $Hash->{"TALB"} = "$tagAlbum";
  $Hash->{"COMM"} = "$tagComment";
  $Hash->{"TCON"} = "(" . ord( $tagGenre ) . ")";
};

# Extracts information from an ID3 v2.2 file (obsolete format - do not write this format)
sub ReadID3v22Tags( $$ ) {
  my $Hash = shift;
  my $ID3Header = shift;

  # Enter some known data into the hash
  $Hash->{"tagversion"} = "ID3v2.2";
  $Hash->{"tagoffset"} = 0;

  $ID3Header =~ /^ID3\02\00.(....)$/sm;
  my ($HeaderSize) = DecodeNum( $1 );
  my ($StreamStart) = $HeaderSize + 10;
  my ($Frame);

  # Set up the remaining stuff vor a ID3v2 tag
  $Hash->{"taglength"} = $HeaderSize;
  $Hash->{"streamstart"} = $StreamStart;

  while ($HeaderSize > 0) {
    read( MP3FILE, $Frame, 6 ) or die "\nError reading from \"" . $Hash->{"name"} . "\" : $!\n";
    my( $Tag, $Size ) = unpack( "a3a3", $Frame );
    # Sanity check for the tag
    if ($Tag =~ /^[a-zA-Z0-9][a-zA-Z0-9][a-zA-Z0-9]/) {
      my( $Buffer );
      $Size = DecodeNum( $Size );
      read( MP3FILE, $Buffer, $Size ) or die "Error reading data from \"" . $Hash->{"name"} . "\" ($Size bytes): $!\n";

      if (exists $ID3v22TagTypes{$Tag}) {
        my $Decoder = $ID3v22TagTypes{$Tag};
        $Buffer = &$Decoder( $Buffer );
      };
      # Only store a tag if it wasn't there before (because of corrupt tags !)
      $Hash->{$Tag} = $Buffer unless $Hash->{$Tag};
      $HeaderSize += -(6+$Size);
    } else {
      last;
    };
  };
};

# Extracts information from a ID3v2 file
# Returns nothing
sub ReadID3v23Tags( $$ ) {
  my $Hash = shift;
  my $ID3Header = shift;

  # Enter some known data into the hash
  $Hash->{"tagversion"} = "ID3v2.3";
  $Hash->{"tagoffset"} = 0;

  $ID3Header =~ /^ID3...(....)$/sm;
  my ($HeaderSize) = DecodeNum( $1 );
  my ($StreamStart) = $HeaderSize + 10;
  my ($Frame);

  # Set up the remaining stuff vor a ID3v2 tag
  $Hash->{"taglength"} = $HeaderSize;
  $Hash->{"streamstart"} = $StreamStart;

  while ($HeaderSize > 0) {
    read( MP3FILE, $Frame, 10 ) or die "\nError reading from \"" . $Hash->{"name"} . "\" : $!\n";
    my( $Tag, $Size, $Flags ) = unpack( "a4a4a2", $Frame );
    if ($Tag =~ /^[a-zA-Z0-9][a-zA-Z0-9][a-zA-Z0-9][a-zA-Z0-9]/) {
      my( $Buffer );
      $Size = DecodeNum( $Size );
      read( MP3FILE, $Buffer, $Size ) or die "Error reading data from \"" . $Hash->{"name"} . "\" ($Size bytes): $!\n";

      if (exists $ID3v23TagTypes{$Tag}) {
        my $Decoder = $ID3v23TagTypes{$Tag};
        $Buffer = &$Decoder( $Buffer );
      };
      # Only store a tag if it wasn't there before (because of corrupt tags !)
      $Hash->{$Tag} = $Buffer unless $Hash->{$Tag};
      $HeaderSize += -(10+$Size);
    } else {
      last;
    };
  };
};

# Returns a hash reference with the information about the file
sub MP3Info( $ ) {
  my $Filename = shift;
  my %Hash = ( "filename" => basename( "$Filename" ),
               "path" => dirname("$Filename"),
               "name" => "$Filename",
             );
  my $Result = \%Hash;

  my( $ID3Header );

  open( MP3FILE, "<" . $Filename ) or die "\nError opening $Filename : $!\n";
  binmode( MP3FILE );
  read( MP3FILE, $ID3Header, 10 )  or die "\nError reading from $Filename : $!\n";

  if ($ID3Header =~ /^ID3\03......$/sm) {
    ReadID3v23Tags( \%Hash, $ID3Header );
  } elsif ($ID3Header =~ /^ID3\02......$/sm) {
    ReadID3v22Tags( \%Hash, $ID3Header );
    #$Result = "Unknown ID3v2 version in \"$Filename\"";
  } else {
    seek( MP3FILE, -128, 2 ) or die "\nError seeking in $Filename : $!\n";
    read( MP3FILE, $ID3Header, 128 ) or die "\nError reading from $Filename : $!\n";
    if ($ID3Header =~ /^TAG/s) {
      ReadID3v1Tags( \%Hash, $ID3Header );
    } else {
      $Result = "ERROR: \"$Filename\" has no ID3 tag";
    };
  };

  # If no error occurred until now, read information about the mp3 stream
  # and fix up some of the tag names
  if (ref $Result) {
    my $tmp = ReadStreamInfo( \%Hash );

    if ($tmp) {
      $Result = $tmp;
    } else {
      # Now duplicate some of the names
      foreach $Tag (keys %TagNames) {
        if (exists $Hash{$Tag}) {
          $Hash{$TagNames{$Tag}} = "$Hash{$Tag}";
          #print "$Tag : " . $TagNames{$Tag} . " : ". $Hash{$TagNames{$Tag}} . "\n";
        };
      };
      if (exists $Hash{"TRCK"}) {
        $Hash{"track2"} = TwoDigits( $Hash{"TRCK"} );
      };
    };
  };

  close MP3FILE or die "closing $Filename : $!\n";

  return $Result;
};


__DATA__
# The built-in HTML template for the album
# The name (and location) of the generated file
#
# To use your own template, either modify this one or copy everything from below __DATA__
# into another file and start mp3album.pl with the "--template filename" switch
#

%outfile%
#html/$(artist) - $(album).html
$(artist) - $(album).html

%header%
# Created from the data of the file with track number 1
<HTML><HEAD><TITLE>$(artist) - $(album) (MP3 CD $(comment) - $(genre))</TITLE></HEAD>
<body bgcolor="#000000" link="#000000" vlink="#000000" leftmargin="20" topmargin="0" text="#000000">
<div align="right"><A href="../index.html"><img src="../Thumbs/back.gif" alt="Back" border="0" width="50" height="50"></a></div>
<div align="center"><center>
<br>
<br><font SIZE="2" FACE="Arial" COLOR="#FFCC33">
<h2>$(artist) - $(album)</h2></font>
<font SIZE="2" FACE="Arial" COLOR="#000000">
<a href="../$(artist) - $(album).m3u">
  <img src="../Covers/$(artist) - $(album).jpg" width="300" height="300" alt="Play \'$(artist) - $(album)\'">
</a>
<br>
<br>
<table BGCOLOR="#000000" CELLSPACING="5" width="80%" cellpadding="5"><TBODY>

%track%
# Created for each file in the album
<tr>
  <td width="30" BGCOLOR="#FFCC33" align="right">$(track)
  <td BGCOLOR="#FFCC33"><a href="../$(filename)">$(title)</a>
  <td BGCOLOR="#FFCC33" width="41" align="right">$(time)</tr>

%footer%
# Footer, generated from the file with track number 1
<tr>
  <td>
  <td align="right"><font SIZE="3" FACE="Arial" COLOR="#FFCC33">Total Time</font></td>
  <td width="41"><font SIZE="3" FACE="Arial" COLOR="#FFCC33">$(tottime)</font></td>
</tr></TBODY></table></font>
<div align="right">
  <br>
  <br>
  <A HREF="../index.html"><font SIZE="3" FACE="Arial" COLOR="#FFCC33">Back to the index...</font></A>
</div>
</center>
</div>
</body></html>

