#!/usr/bin/perl -w
# Really crude "post beautifyer"
# The rationale behind it is to split an unformatted post up into
# text and code, but leaning more to code than to text, as it is
# easier to read monospaced text than to read paragraph-formatted
# code.

# BETA CODE !
# BUGS:
# * Can't properly handle "print <<EOF"-style code ...

use strict; # this is becoming a habit ...
#use Safe;   # kudos to swiftone for the idea and nuance for the Safe module
use Text::Wrap;

my $filename = $ARGV[0] || $0;

my $Content = &readfile( $filename );
my %StartTag = (
  "Code" => '<PRE>',
  "Text" => '<P>',
);
my %EndTag = (
  "Code" => '</PRE>',
  "Text" => '</P>',
);

# A RE that constitutes what a variable name might be ...
my $varRE = q'[\\$\\@\\%\\*](\w[a-zA-Z_0-9]+|[\\$/\|\\@_])';

my $CodeLineBreak = "<FONT color=\"red\">+<BR>+ </FONT>";
#print $Text::Wrap::columns;
$Text::Wrap::columns = 50;

# Fix up the newlines
$Content =~ s/\n\r|\r/\n/g;

# Bail out if the user knows about CODE tags
if ($Content =~ /<CODE>/) {
  print $Content;
  exit;
};

my (@Lines) = split /\n/, $Content;

my $LastLineType = "Text";
my $LineType = "Text";

#my ($Sandbox) = new Safe 'Sandbox';

# Hey, I'm trying to stay on the safe side. If something
# compiles fine but dosen't run, I'm all OK with that !
#$Sandbox->permit_only();

print $StartTag{$LineType};
foreach (@Lines) {
  # For a start, assume that the current line has the same style
  # as this line
  $LineType = $LastLineType;

  # blank lines remain blank lines
  if (/^\s*$/) {
    # keep old state
    }
  # some really safe bets about code lines
  elsif (    /^\s*#/           # A comment line
          || /^\s*["'].*?["']\s*=>/ # A hash entry
          || /^\s*[\{\(]|[\}\)];?/     # a line containing (only) an opening or closing bracket
          || /^\s*if\s*\(/     # an if statement
          || /elsif/           # elsif statements are a dead giveaway
          || /^\s*else\s*\{?\s*/ # a single else statement
          || /\s*[\$\@\%]\w+ [=!~+\-\*]?=/ # assignments
          || /^\s*use\s+\w+(::\w+)*/ # use clauses
          || /^\s*require\s+\w+(::\w+)*/ # require clauses
          || /^\s*sub\s+\w+/
          || /^\s*my\s+\(?\s*$varRE/o
          || /^\s*our\s+\(?\s*$varRE/o
          || /^\s*local\s+$varRE/o
          || /^\s*return\s+\(?\s*$varRE/o
          || /^\s*close\s+[A-Z]+/o
          || /^\s*print\W/
          )
       {
         $LineType = "Code";

         $_ = wrap("","",$_);
         $_ =~ s/\n/$CodeLineBreak/o;
       }
  else {
    # Everything that hasn't been weeded out by now must be normal text ...
    $LineType = "Text"
  }

  print( $EndTag{$LastLineType},$StartTag{$LineType} ) if ($LastLineType ne $LineType);
  print $_, "\n";

  $LastLineType = $LineType;
};
print( $EndTag{$LastLineType} );

sub readfile {
  # Don't put this into production code !
  # It prints user-supplied stuff like the filename ...
  my( $filename ) = @_;
  local $/;
  local *FILE;
  open( FILE, "< $filename" ) or die "Can't read from $filename : $!\n";
  my $Result = <FILE>;
  close FILE;

  return $Result;
};

