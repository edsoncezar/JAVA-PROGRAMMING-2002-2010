#!/usr/bin/perl
use strict;

# Usage variables
my $execname;   # Name of script
my $usage;      # Usage info

# Configuration variables
my $badport;    # List of ports to give extra warning about
my $lastdeny;   # Last DENY entry from the last run
my $blankrep;   # If TRUE, send a report even if nothing happened
my $ignoreport; # Comma separated list of ports to ignore
my $mailto;     # Address to mail report to

# Parts of the DENY message
my $destport;   # Destination port in DENY message
my $sourceaddr; # Source address in DENY message

# Variables used for creating report
my $lenaddr;    # Length of an address
my $lencount;   # Length of a port count
my $lenport;    # Length of a port
my $port;       # A single port from %addr hash
my $addr;       # A single address from %addr hash

# Other variables
my $i;          # Index
my $numdenies;  # Total number of deny entries since last run
my $msgfiles;   # All "message" files in /var/log, in reverse order

# Lists and hashes
my @denies;     # List of DENY entries
my %addr;       # All source addresses that have been found
my %badport;    # "Bad" ports, and number of times found
my %ignoreport; # Ports to ignore, and number of times found
my %ports;      # All ports for a given address

# Initialize variables
$numdenies =  0;
$execname  = $0;
$execname  =~ s/^.+\/(.+)$/\1/;
$usage     = "
Usage: $execname

  The \"$execname\" script checks for DENY entries in
  /var/log/messages* and mails a report with any new entries since
  the last run to the address specified in /etc/deniesrc.  This file
  also has an optional list of destination ports to ignore, \"bad\"
  ports to flag in the report (such as ports used by Back Orifice
  and the ilk), and other parameters:

    LASTDENY:   The last DENY entry from the last run.  This is used
                to determine where to start for the next run.  Do
                not edit this unless you know what you are doing.
    BLANKREP:   If this is \"True\", mail a report even if there are
                no new DENY entries.  If this is \"False\", only
                mail a report if there are new DENY entries.
    IGNOREPORT: List of ports to ignore.  The format is
                \"68, 113, 137\".
    BADPORT:    List of ports to give special attention to.  The
                format is \"27374, 31337\".
    MAILTO:     Address to mail the report to.  For example
                \"root\".

  Richard Griswold, griswold\@acm.org  -  25 JUL 2000
";

# Get settings
( $lastdeny, $blankrep, $ignoreport, $badport, $mailto ) =
  &getSettings();

# Get list of DENY messages
$msgfiles = join ' ', sort { $b cmp $a } split /\n/,
            `ls /var/log/messages*`;
@denies   = split /\n/, `grep -h DENY $msgfiles`;

# Check if any denies
if ( $#denies == -1 ) {
  if ( $blankrep eq "TRUE" ) {
    &mailBlank( $mailto, $ignoreport, $badport, $lastdeny );
  }
  exit;
}

# Build ignore and bad port hashes
%badport    = &createHash( $badport    );
%ignoreport = &createHash( $ignoreport );

# Not run previously if $lastdeny is zero
if ( $lastdeny eq "NONE" ) {
  $i = 0;
} else {
  # Find $lastdeny in @denies
  for ( $i = 0; $i <= $#denies; $i++ ) {
    if ( $denies[$i] eq $lastdeny ) {
      last;
    }
  }

  # Adjust index
  if ( $i < $#denies ) {       # Go to next entry, if there is one
    $i++;
  } elsif ( $i == $#denies ) { # No new entries if at end of list
    if ( $blankrep eq "TRUE" ) {
      &mailBlank( $mailto, $ignoreport, $badport, $lastdeny );
    }
    exit;
  } else {               # Past end of list, so all entries are new
    $i = 0;
  }
}

# Run through all entries since $lastdeny
for ( ; $i <= $#denies; $i++, $numdenies++ ) {
  # Get destination port for this DENY entry
  $destport =  $denies[$i];
  $destport =~ s/^.+:[0-9]+ [0-9\.]+:([0-9]+) .+$/$1/;

  # If $destport is one to ignore, increment count
  # and skip to next port
  if ( $ignoreport{$destport} ne '' ) {
    $ignoreport{$destport}++;
    next;
  }

  # If $destport is a bad port, increment count
  if ( $badport{$destport} ne '' ) {
    $badport{$destport}++;
  }

  # Get source address
  $sourceaddr =  $denies[$i];
  $sourceaddr =~ s/^.+ ([0-9\.]+):[0-9]+ [0-9\.]+.+$/$1/;

  # Add port to list for this address
  $addr{$sourceaddr}{$destport}++;  # A hash of hashes :)
}

# Write last entry to /etc/deniesrc
&writeEntry( $denies[$#denies] );

# ---------- Print report ----------

# Heading
open PROC, "| mail -s\"Denies report for ".localtime()."\" $mailto";

# Total number of DENY entries
print PROC
  "There were $numdenies new entries since the last run\n\n";

# Ignored ports
$lenport = 4; $lencount = 5;
foreach ( keys %ignoreport ) {
  $i = length $_;              if ( $i>$lenport  ) { $lenport =$i; }
  $i = length $ignoreport{$_}; if ( $i>$lencount ) { $lencount=$i; }
}
print PROC "Ignored ports:" . ' ' x ( $lenport  - 3 ) .
           "Port"           . ' ' x ( $lencount - 2 ) . "Times\n";
foreach ( sort {$a <=> $b} ( keys %ignoreport ) ) {
  print PROC
    ' ' x ( 15 + $lenport  - length $_ )         . $_ .
    ' ' x (  3 + $lencount - length $ignoreport{$_} ) .
    "$ignoreport{$_}\n";
}
print PROC "\n";

# "Bad" ports
$lenport = 4; $lencount = 5;
foreach ( keys %badport ) {
  $i = length $_;           if ( $i>$lenport  ) { $lenport  = $i; }
  $i = length $badport{$_}; if ( $i>$lencount ) { $lencount = $i; }
}
print PROC "Bad ports:" . ' ' x ( $lenport  + 1 ) .
           "Port"       . ' ' x ( $lencount - 2 ) . "Times\n";
foreach ( sort {$a <=> $b} ( keys %badport ) ) {
  print PROC ' ' x ( 15 + $lenport  - length $_ ) .
        $_ . ' ' x (  3 + $lencount - length $badport{$_} ) .
        "$badport{$_}\n";
}
print PROC "\n";

# Non-ignored addresses and ports
$lenaddr = 7; $lenport = 4; $lencount = 5;
foreach ( keys %addr ) {
  $i = length $_; if ( $i > $lenaddr  ) { $lenaddr = $i; }
  foreach $port ( keys %{ $addr{$_} } ) {
    $i = length $port;            if ( $i>$lenport  ){$lenport =$i;}
    $i = length $addr{$_}{$port}; if ( $i>$lencount ){$lencount=$i;}
  }
}
print PROC "Address" . ' ' x ( $lenaddr + $lenport - 8 ) .
           "Port"    . ' ' x ( $lencount - 2 ) . "Times\n";
foreach ( sort sortAddr ( keys %addr ) ) {
  $addr = $_;
  foreach $port ( sort {$a <=> $b} ( keys %{ $addr{$_} } ) ) {
    print PROC $addr .
      ' ' x ( 3 + $lenaddr + $lenport -
        length( $addr ) - length $port ) .
      $port . ' ' x ( 3 + $lencount - length $addr{$_}{$port} ) .
      "$addr{$_}{$port}\n";
    # Blank out address for subsequent ports
    $addr = ' ' x length $addr;
  }
  print PROC "\n";
}

# Settings
print PROC "\nSettings:\n";
print PROC "  Ignored ports:      $ignoreport\n";
print PROC "  Bad ports:          $badport\n";
print PROC "  Mail blank reports: $blankrep\n";
print PROC "  Mail report to:     $mailto\n";
print PROC "  Last DENY entry:    $lastdeny\n\n";

# Raw DENY entries
print PROC "\nRaw DENY entries:\n";
for ( $i = $#denies - $numdenies + 1; $i <= $#denies; $i++ ) {
  print PROC "  $denies[$i]\n";
}

print PROC "--- End of report ---\n";

#--------------
# getSettings |
#-------------------------------------------------------------------
# Get settings from /etc/deniesrc file
#-------------------------------------------------------------------
sub getSettings {
  my $ignoreport;  # List of ports to ignore
  my $badport;     # List of ports to give extra warning about
  my $lastdeny;    # Last DENY entry from the last run
  my $blankrep;    # If TRUE, send a report even if nothing happened
  my $mailto;      # Address to mail report to

  # Defaults
  $blankrep = "TRUE";
  $ignoreport = $badport = $lastdeny = $mailto = "NONE";

  # Create RC file if necessary
  if ( not -e "/etc/deniesrc" ) {
    &createRCFile();
    $mailto = "root";
    return $lastdeny, $blankrep, $ignoreport, $badport, $mailto;
  }

  # Check if we can read and write RC file
  -r "/etc/deniesrc" or die "Cannot read /etc/deniesrc\n";
  -w "/etc/deniesrc" or die "Cannot write /etc/deniesrc\n";

  # Open RC file
  open( RCFILE, "</etc/deniesrc" ) or
    die "Cannot open resource file /etc/deniesrc for reading\n";

  # Get options
  foreach ( <RCFILE> ) {
    chop;
    if ( ( $_ =~ /^ *#.+$/ ) or ( $_ eq "" ) ) {
      next;
    } elsif ( $_ =~ /^LASTDENY:/ ) {
      $lastdeny =  $_;
      $lastdeny =~ s/^LASTDENY: +(.+)$/$1/;
    } elsif ( $_ =~ /^BLANKREP:/ ) {
      $blankrep =  $_;
      $blankrep =~ s/^BLANKREP: +(.+)$/$1/;
    } elsif ( $_ =~ /^IGNOREPORT:/ ) {
      $ignoreport =  $_;
      $ignoreport =~ s/^IGNOREPORT: +(.+[0-9]+) *$/$1/;
    } elsif ( $_ =~ /^BADPORT:/ ) {
      $badport =  $_;
      $badport =~ s/^BADPORT: +(.+[0-9]+) *$/$1/;
    } elsif ( $_ =~ /^MAILTO:/ ) {
      $mailto =  $_;
      $mailto =~ s/^MAILTO: +(.+)$/$1/;
    } else {
      print
        "Line \"$_\" is not valid in /etc/deniesrc.  Ignoring.\n";
    }
  }

  close RCFILE;

  # Validate options
  if ( $blankrep ne "NONE" ) {
    if ( ( uc( $blankrep ) ne "TRUE"  ) and
         ( uc( $blankrep ) ne "FALSE" ) ) {
      print
   "Keyword \"$blankrep\" is not valid for option \"BLANKREP:\".\n";
      print "  Ignoring.\n";
    }
    $blankrep = uc $blankrep;
  }

  if ( $ignoreport ne "NONE" ) {
    if ( &validPortList( $ignoreport ) < 0 ) {
      print
  "List \"$ignoreport\" is not valid for option \"IGNOREPORT:\".\n";
      print "  Ignoring.\n";
      $ignoreport = "";
    }
  }

  if ( $badport ne "NONE" ) {
    if ( &validPortList( $badport ) < 0 ) {
      print
        "List \"$badport\" is not valid for option \"BADPORT:\".\n";
      print "  Ignoring.\n";
      $badport = "";
    }
  }

  if ( $mailto eq "NONE" ) {
    $mailto = "root";
  }

  return $lastdeny, $blankrep, $ignoreport, $badport, $mailto;
}

#---------------
# createRCFile |
#-------------------------------------------------------------------
# Create the /etc/deniesrc file
#-------------------------------------------------------------------
sub createRCFile {
  open FILE ,">/etc/deniesrc" or
    die "Cannot create /etc/deniesrc file\n";

  print FILE
   "# RC file for \"denies\" script\n\n";
  print FILE
   "# NOTE:  Do not edit the following line:\n";
  print FILE
   "LASTDENY:\n\n";
  print FILE
   "# Set this line to \"False\" if you do not want to get blank\n";
  print FILE
   "# reports (reports when there are no DENY entries).\n";
  print FILE
   "BLANKREP:   True\n\n";
  print FILE
   "# Put the list of ports to ignore on this line.\n";
  print FILE
   "# Use the format \"68, 113, 137\".\n";
  print FILE
   "IGNOREPORT: NONE\n\n";
  print FILE
   "# Put the list of \"bad\" ports on this line.  These are \n";
  print FILE
   "# ports you want extra notification about, such as the \n";
  print FILE
   "# Back Orifice 31337 port.  Use the same format as above.\n";
  print FILE
   "BADPORT:    NONE\n\n";
  print FILE
   "# Put the address you want to send the report to here.\n";
  print FILE
   "MAILTO:     root\n";

  close FILE;
  chmod 0600, "/etc/deniesrc";
}

#----------------
# validPortList |
#-------------------------------------------------------------------
# Check if a port list is in a valid format (ie 10, 31, 138, ...)
#-------------------------------------------------------------------
sub validPortList {
  foreach ( split /, /, shift ) {
    if ( $_ !~ /^[0-9]+$/ ) {
      return -1;
    }
  }

  return 0;
}

#------------
# mailBlank |
#-------------------------------------------------------------------
# Mail a blank report
#-------------------------------------------------------------------
sub mailBlank {
  my $mailto     = shift;
  my $ignoreport = shift;
  my $badport    = shift;
  my $lastdeny   = shift;

  open PROC,
    "| mail -s\"Denies report for ".localtime()."\" $mailto";
  print PROC "There were no new entries for since the last run\n\n";
  print PROC "Settings:\n";
  print PROC "  Ignored ports:      $ignoreport\n";
  print PROC "  Bad ports:          $badport\n";
  print PROC "  Mail blank reports: $blankrep\n";
  print PROC "  Mail report to:     $mailto\n";
  print PROC "  Last DENY entry:    $lastdeny\n\n";
  print PROC "--- End of report ---\n";
  close PROC;
}

#-------------
# createHash |
#-------------------------------------------------------------------
# Create a hash from a comma separated list
#-------------------------------------------------------------------
sub createHash {
  my $list = shift;

  $list =~ s/, /, 0, /g;
  $list .= ", 0";

  return split /, /, $list;
}

#-------------
# writeEntry |
#-------------------------------------------------------------------
# Write last DENY entry inot /etc/deniesrc
#-------------------------------------------------------------------
sub writeEntry {
  my $lastdeny = shift;
  my @rcfile;

  open FILE, "</etc/deniesrc" or
    die "Cannot open resource file /etc/deniesrc for reading\n";

  foreach ( <FILE> ) {
    if ( $_ =~ /^LASTDENY:/ ) {
      $_ =~ s/^(LASTDENY:)$/$1   /;        # Put in spaces if none
      $_ =~ s/^(LASTDENY: +).*$/$1$lastdeny/;
    }
    push @rcfile, $_;
  }
  close FILE;

  open FILE ,">/etc/deniesrc" or
    die "Cannot open resource file /etc/deniesrc for writing\n";
  foreach ( @rcfile ) {
    print FILE $_;
  }
  close FILE;
}

#-----------
# sortAddr |
#-------------------------------------------------------------------
# Sort IP addresses
#-------------------------------------------------------------------
sub sortAddr {
  my @a = split /\./, $a;
  my @b = split /\./, $b;
  my $i;

  for ( $i = 0; $i < 4; $i++ ) {
    if ( $a[$i] < $b[$i] ) {
      return -1;
    } elsif ( $a[$i] > $b[$i] ) {
      return 1;
    }
  }
  return 0;
}

