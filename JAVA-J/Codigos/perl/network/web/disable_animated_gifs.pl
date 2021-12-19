#!/usr/bin/perl
#
# 21 MAY 1999:  Created
# 29 OCT 1999:  Added ability to disable blinking text
#               Rewrote argument fetch code
#               Used strict package
# 19 SEP 2000:  Cleanup

use strict;

my $execname;      # Name of this script
my $usage;         # Usage information

my $ag;            # Animated GIF action
my $bt;            # Blinking text action

my $arg;           # An argument

$execname   = $0;
$execname   =~ s/^.+\///;
$usage      = "
Usage: $execname [-ae|-ad] [-be|-bd] <--> file(s) ...
  -ae      Enable animated GIFs
  -ad      Disable animated GIFs
  -be      Enable blinking text
  -bd      Disable blinking text
  --       All other arguments are files
  file(s)  Netscape executable(s)

  Richard Griswold, 21 MAY 1999

";

# Fetch arguments
while ( $#ARGV >= 0 ) {
  $arg = shift ( @ARGV );

  if    ( ( $arg eq "-ae" ) or ( $arg eq "-ad" ) ) { $ag = $arg; }
  elsif ( ( $arg eq "-be" ) or ( $arg eq "-bd" ) ) { $bt = $arg; }
  elsif ( $arg eq "--"  ) { last; }    # Rest of args are files
  else  { unshift @ARGV, $arg; last; } # Rest of args are files
}
if ( not $ag and not $bt ) {
  die "You must specify at least one action\n$usage";
}
if ( $#ARGV < 0 ) {
  die "You must specify at least one file\n$usage";
}

# Check access on each file
foreach $arg ( @ARGV ) {
  if ( !( -e $arg) ) {
    die "File \"$arg\" does not exist.\n$usage";
  }
  if ( !( -f $arg) ) {
    die "File \"$arg\" is not a plain file.\n$usage";
  }
  if ( !( -w $arg) ) {
    die "You do not have write access to file \"$arg\".\n$usage";
  }
}

# Perform action
$arg = join ' ', @ARGV;
if ( $ag eq "-ae" ) {
  `perl -pi -e 's/ANIMEXTZ1.0/ANIMEXTS1.0/' $arg`;
  `perl -pi -e 's/NOTSCAPE2.0/NETSCAPE2.0/' $arg`;
  print "Animated GIFs are enabled in file(s) \"$arg\"\n";
} elsif ( $ag eq "-ad" ) {
  `perl -pi -e 's/ANIMEXTS1.0/ANIMEXTZ1.0/' $arg`;
  `perl -pi -e 's/NETSCAPE2.0/NOTSCAPE2.0/' $arg`;
  print "Animated GIFs are disabled in file(s) \"$arg\"\n";
}
if ( $bt eq "-be" ) {
  `perl -pi -e 's/blynk/blink/' $arg`;
  print "Blinking text is enabled in file(s) \"$arg\"\n";
} elsif ( $bt eq "-bd" ) {
  `perl -pi -e 's/blink/blynk/' $arg`;
  print "Blinking text is disabled in file(s) \"$arg\"\n";
}
