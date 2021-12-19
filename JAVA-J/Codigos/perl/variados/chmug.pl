#!/usr/bin/perl
# chmug - Change mode, user (owner), and/or group of file(s)
# @(#)chmug.pl  1.11, 95/11/27 09:24:38
# See usage message (in "sub Usage" below) for more details.
# By Tye McQueen, tye@metronet.com, see http://www.metronet.com/~tye/
#Bugs:
# "u+s+x" "intentionally" ignores "u+s" but "u+x+s" and "u+sx" work as "u+xs".
# Unlike BSD, /[ugoa]+-X/ is useful, removing "x" if _not_ a directory _and_
#    some slots _don't_ have "x".
# Unlike BSD, /(^|,)-[rwx]+/ is useful, only removing bits _set_ in umask.
# "chmug -R" may temporilly do "chmod u+rx" to a subdirectory so that it can
#    recurse into it.  When it finishes in that subdirectory, it will restore
#    the previous mode then make any changes to it as requested on the command
#    line.  So if it dies before then, one line of directories may be left
#    with "u+rx" applied to them.  Also, "chmug -R" will not fail on your
#    directories, even if you have denied yourself the right to use them.
# The Usage message doesn't tell you that "chmug -q" suppresses some warnings
#    including the logging of the "temporary" changes described above.
# The Usage messages doesn't tell that you can set the CHMUG environment
#    variable to contain default flags (CHMUG="-v -P", for example).

# Gobal variables:

use strict;             # Complain about any undeclared variables.
my( $Mode, $User, $Group, @Mode );      # Set by &ParseChange.
( my $Self= $0 ) =~ s#^/(.*/)?##;       # This program's name, for errors.
my $ModeBits= "rwxXlstugo";             # Valid chars after /[ugoa]*[-+=]/.
my $Recurse= 0;         # Should we recurse into directories?
##my $ModLink= 0;       # Modify symbolic link rather than file link points to?
my $Debug= 0;           # Log what we do as we go along?
my $Model= "";          # Model file to copy owner/mode settings from (none).
my $First= 1;           # Are we changing our first file?  Else don't rewarn.
my $ActLike= "mug";     # Are we chmug, chmod, chown, or chgrp?
my $Colon= ":";         # Will be ":." if -P used to allow "user.group".
my $Period= ".";        # Will be "" if -P used so ("." can't be in username).
my %Usage= split( /\n/, <<END );        # Our usage message, based on $ActLike:
mug
Usage:  $Self [-RvqP] { [mode][[,]usr][:[grp]] | -f model } file [file...]
mod
Usage:  $Self [-RvqP] { [mode][[,]usr][:[grp]] | -f model } file [file...]
own
Usage:  $Self [-RvqP] { [[,]usr][:[grp]] | -f model } file [file...]
grp
Usage:  $Self [-RvqP] { [:]grp | -f model } file [file...]
END
# Pseudo-declaration "Global( =x, y=, z )" means "x" is a read-only global
# variable, "y" is a write-only global, and "z" is a read/write global.

# Subroutines:  (Program execution begins after "main {" below)

sub WhoAmI {
    if(  $Self =~ m#mod[^/]*$#  ||  $Self =~ m#perm[^/]*$#  ) {
        "mod";
    } elsif(  $Self =~ m#own[^/]*$#  ||  $Self =~ m#use?r[^/]*$#  ) {
        "own";
    } elsif(  $Self =~ m#gr[ou]*p[^/]*$#  ) {
        "grp";
    } else {
        "mug";
    }
}

sub Usage {                             # Print usage message then die:
# Global( =$Self, =@ARGV, =$ModeBits ); # &Usage() shows long usage message.
  local( $_ );
    warn "$Self:  @_\n"   if  @_;       # &Usage("err") shows error text
    warn $Usage{$ActLike}, "\n";        #   then short usage message.
    die qq<(Enter "$Self -H" for long usage message.)\n>   if  @_;
    ( $_= <<END ) =~ s/^#.*\n//mg;      # Grab uncommented lines:
##Usage with possible future enhancements:
##Usg: chmug [-R[k=v[,...]] [-hvqP] {[mode][[,]usr][:[grp]]|-f model} file [...]
"mode" can be a base-8 number or match /^[ugoa]*([-+=][$ModeBits]*)+(,...)*\$/.
"usr" can be username or UID number ("," needed before UID or if after "mode").
#"usr" can be a username or ("," needed before numbers or if "mode").
#If specifying both "mode" and "usr", the "," is required.
"grp" can be a group name or a group ID number (leading ":" always required).
"-f model" duplicates the mode, user, and group of the file "model".
"-R" recursively modifies all files under any directories listed.
"-P" ("period") lets you use "user.group" as well as "user:group".
##"-Rtype=f,mtime+5,user!root" does "find file -type f -mtime +5 ! -user root"
## "-h" changes the user/group of a symbolic link rather than the file that
##      the link points to (you cannot specify a mode with "-h").
"-v" ("verbose") logs changes to STDOUT ("-vv" is more verbose).
"-q" ("quiet") undoes a previous "-v" (in case "-v" is in your default alias).
Specifying "usr:" sets the group to that user's "primary group".
"t" is the sticky bit (invalid after /g[-+=]/ or /o[-+=]/).
"s" is either the set-UID or set-GID bit (invalid after /o[-+=]/).
"l" is mandatory locking ("g+s,g-x" except "-x" implies "-s"; "+s" needs "+x").
"g=u" sets the group bits to what the user bits where before any changes,
"u+g" adds to the user bits what the group bits where before any changes, etc.
/[+=]X/ adds "x" only on files that (now) have an "x" set and on directories,
while "-X" only changes non-directories with at least one "x" _not_ set.
[-+=] not after [ugoa] is like /a[-+=]/ but only sets (clears) bits _not_ set
(unset) in umask ("u-w+r" = "u-w,u+r"; "u-r,+x" = "u-r,a+x" w/ umask on "+x").
For directories, "g-s" and "g+s" are the *only* ways to change that bit.
A base-8 numeric mode has the following meaning:      Bits  User Group Other
(Bits:  "u"=Set-UID  "g"=Set-GID  "t"="Sticky" bit)   ugt   rwx   rwx   rwx
So "4751" is "u=rwxs,g=rx,o=x".                       421   421   421   421
END
  my( $NoMode )=  "own" eq $ActLike  ||  "grp" eq $ActLike;
    s#^"mode".*\n##m                            if  $NoMode;
    s#^"usr".*\n##m                             if  "grp" eq $ActLike;
    s# \("," needed .* "mode"\)##m              if  "own" eq $ActLike;
    s# \(leading "\:" always required\)##m      if  "grp" eq $ActLike;
    if(  "mod" eq $ActLike  ) {
        s#(duplicates the )mode, user, and group( of)#$1mode$2#;
    } elsif(  "own" eq $ActLike  ) {
        s#(duplicates the )mode, user, and group( of)#$1owner and group$2#;
    } elsif(  "grp" eq $ActLike  ) {
        s#(duplicates the )mode, user, and group( of)#$1group$2#;
    }
    s#^.*"usr\:".*\n##m                         if  "grp" eq $ActLike;
    s#^"t".*421\n##ms                           if  $NoMode;
    $_ .= qq<[This is really the "chmug" commmand pretending to be $Self]\n>
                                                unless  "mug" eq $ActLike;
    die $_;
}       # End of &Usage

sub ParseFlags {                # Parse flags given on command line:
# Global( @ARGV, =$ModeBits, $Recurse, $Debug, $ModLink, =$Self );
 my( $arg );    # For POSIX:  "-" => stdin, "--" ends flags, "--x" => reserved
    if(  defined( $ENV{'CHMUG'} )  ) {
      my( @flags )= split( ' ', $ENV{'CHMUG'} );
        @flags= grep(  /^-[^-]/ ? 1
                       : ( warn(qq<$Self:  Invalid flag in CHMUG >,
                                qq<environment variable, "$_", ignored.\n>),
                         0 ),  @flags  );
        unshift( @ARGV, @flags );
    }
    while(  @ARGV  &&  $ARGV[0] =~ /^-[^-]/  ) {        # so /^-[^-]/ is flag.
        # "-x" is mode "a-x" w/ umask applied and so isn't a flag:
        last   if  $ARGV[0] =~ /^-[$ModeBits]+([-+=,.]|$)/o;
        ( $arg= shift(@ARGV) ) =~ s/^-//;       # Get next arg, drop the "-".
        do {                    # While unprocessed flags left in argument:
            if(  $arg =~ s/^H//  ) {            # "-H":
                &Usage();                       # Show long usage message.
            ##} elsif(  $arg =~ s/^E//  ) {     # "-E" (not supported):
                ##&Usage();                     # Show examples and more help.
            } elsif(  $arg =~ s/^R//  ) {       # "-R":
                $Recurse= 1;                    # Recurse into subdirectories.
            } elsif(  $arg =~ s/^P//  ) {       # "-P" ("period"):
                $Colon= ":.";                   # Allow "usr.grp" as "usr:grp".
                $Period= "";                    # Don't allow "." in username.
            } elsif(  $arg =~ s/^v//  ) {       # "-v" ("verbose"):
                $Debug++;                       # Log to STDOUT what we do.
            } elsif(  $arg =~ s/^q//  ) {       # "-q" ("quiet"):
                $Debug= ""   if  --$Debug < 0;  # Log less to STDOUT. [""<"0"]
            ##} elsif(  $arg =~ s/^h//  ) {     # "-h":
            ##    $ModLink= 1;                  # Modify a symbolic link.
            } elsif(  $arg =~ s/^f//  ) {       # "-f model_file":
                $Model= $arg;                   # In case no space after "-f".
                if(  "" eq $Model  ) {          # Maybe "-f model" used:
                    &Usage( "No model file name given after -f" )
                      unless  "" ne ( $Model= shift(@ARGV) );
                }
                $arg= "";               # Don't process file name as flags.
            } else {
                &Usage( "Unknown switch (-$arg)" );
            }
        } while(  "" ne $arg  );        # No more flags in this argument.
    }
    shift( @ARGV )   if  "--" eq $ARGV[0];      # Don't leave "--" as an arg.
}       # End of &ParseFlags

sub ModelFile {         # Grab mode, user, and group of model file.
# Global( =$Model );    # Insert command-line argument to set m, u, and/or g.
  my( $mode, $owner, $group )= (stat($Model))[2,4,5];
    if(  "" eq $mode  ) {
        die "$Self:  Can't stat() model file ($Model): $!\n";
    }
    if(  "mod" eq $ActLike  ) {                 # Acting like "chmod":
        unshift( @ARGV, sprintf("0%o",$mode) ); # Only change modes.
    } elsif(  "own" eq $ActLike  ) {            # Acting like "chown":
        unshift( @ARGV, ",$owner:$group" );     # Only change owners and groups.
    } elsif(  "grp" eq $ActLike  ) {            # Acting like "chgrp":
        unshift( @ARGV, ":$group" );            # Only change groups.
    } else {                                    # Acting like the real "chmug":
        unshift( @ARGV, sprintf("0%o",$mode).",$owner:$group" ); # Change all!
    }
}

sub ParseChange {       # Parse first argument ("[mode][[,]user][:[group]]"):
  my( $change )=        @_;             # Value of first command-line argument.
# Global( $Mode=, @Mode=, $User=, $Group=, =$Self );
  # Update this line if your system allows '-', '/', '$', etc. in usernames:
  my( $l )=             $Period ? "[\\w$Period]" : "\\w"; # Okay char in name.
  my( $number )=        "[0-7]+";       # Numeric file mode
  my( $symbol )=        "(?:a|[ugo]*)(?:[-+=][$ModeBits]*)+";   # Symbolic mode
  my( $mode )=          "(?:$number|(?:$symbol(?:,$symbol)*))"; # Either mode
  my( $user )=          "(?:\\,$l|[a-zA-Z_])$l*"; # User name/number
  my( $group )=         "[$Colon]$l*";                  # Group name/number
  my( @x );                                             # For user/group info
    unless(  ( $Mode, $User, $Group )=          # Break $change into m, u, g.
               $change =~ m#^($mode)?($user)?($group)?$#o  ) {
        &Usage( "Invalid mode/user/group ($change)" );
        # Go ahead, write the code to tell them *what* is invalid about it!
    }
    if(  ! defined($Mode)  ) {  # So "perl -w" won't complain...
        $Mode= "";              #   about "Use of uninitialized value".
    } elsif(  $Mode =~ m#^$number$#o  ) {       # Numeric mode given:
        $Mode= oct($Mode);              # Turn "40" or "040" to 040 (aka 32).
        @Mode= ();                      # Tell subs to use $Mode, not @Mode.
    } elsif(  $Mode ne ""  ) {          # Symbolic mode(s) given:
      my( $mode );                      # Split into single bit-mask ops:
        @Mode= map {                    # First, split on ",".
            if(  m#[-+=].*[-+=]#  ) {   # Then "ug+rw-x" => "ug+rw","ug-x":
              # Split on "nothing followed by -, +, or =":
              my( $who, @what )= split( /(?=[-+=])/, $_ );
                # "+r-x" => $who:"+r",@what:"-x" => $who:"",@what:("+r","-x"):
                ( @what, $who )= ( $who, @what )   if  $who =~ m#^[-+=]#;
                foreach( @what ) {
                    $_= "$who$_";
                }
                @what;
            } else {
                $_;
            }
        } split( /,/, $Mode );          # `First, split on ",".'
        if(  $_= ( grep(/[-+]$/,@Mode) )[0]  ) {        # "o=" OK, "o+" not:
            &Usage( qq<+/- must be followed by one of [$ModeBits]: "$_"> );
        }
    }
    if(  ! defined($User)  ) {  # So "perl -w" won't complain...
        $User= "";              #   about "Use of uninitialized value".
    } elsif(  "" ne $User  ) {  # Convert user name to user ID number:
        $User =~ s#^\,##;       # Trim optional leading "," (a delimiter).
        if(  @x= getpwnam($User)  ) {   # Look up username [even if /^\d+$/]:
            $User= $x[2];               # Found it, change to UID number.
        } elsif(  $User !~ /^\d+$/  ) { # Not a name.  Is it a number?  No:
            die "$Self:  No such user ($User).\n";
        }       # Show back translation in case more than one user w/ that UID:
        print "Using UID=$User(", (getpwuid($User))[0], ").  "  if  1 < $Debug;
    }
    if(  ! defined($Group)  ) { # So "perl -w" won't complain...
        $Group= "";             #   about "Use of uninitialized value".
    } elsif(  "" ne $Group  ) { # Convert group name to group ID number:
        $Group =~ s#^[$Colon]##;# Trim required leading ":" (a delimiter).
        if(  $Group eq ""  ) {  # "user:" means use user's primary group:
            &Usage( qq<":" given ($change) but no user or group> )
              if  "" eq $User;  # "usr:", ":grp", and "usr:grp" OK; ":" BAD.
            $Group= $x[3];      # $User's primary group number.
        } elsif(  @x= getgrnam($Group)  ) {     # Look up group name:
            $Group= $x[2];      # Found it, change to GID number.
        } elsif(  $Group !~ /^\d+$/  ) {        # Is it a Number?  No:
            die "$Self:  No such group ($Group).\n";
        }       # Show back translation in case not just one group w/ that GID:
        print "Using GID=$Group(", (getgrgid($Group))[0], ")."  if  1 < $Debug;
    } else {
        $Group= "";
    }   # So can put "Using UID=...  Using GID=..." on same line:
    print "\n"   if  1 < $Debug  &&  (  $User ne ""  ||  $Group ne ""  );
}       # End of &ParseChange

sub BitOp {     # Return $mode but with $mask bits set (cleared if "-" eq $op):
  my( $mode, $op, $mask )= @_;
    if(  "-" eq $op  ) {
        $mode & ~ $mask;        # Clear bits of $mode that are set in $mask.
    } else {
        $mode | $mask;          # Set bits of $mode that are set in $mask.
    }
}       # End of &BitOp

sub SetBit {    # Apply a single "(a|[ugo]*)[-+=][$ModeBits]" to $mode:
  my( $mode, $bit, $where, $top, $start, $who, $op, $change, $file )= @_;
# Global( =$Self );
    if(  "r" eq $bit  ) {                       # Set/clear "read" bit(s):
        $mode= &BitOp( $mode, $op, $where << 2 );       # "4" bit(s)
    } elsif(  "w" eq $bit  ) {                  # Set/clear "write" bit(s):
        $mode= &BitOp( $mode, $op, $where << 1 );       # "2" bit(s)
    } elsif(  "x" eq $bit  ) {                  # Set/clear "execute" bit(s):
        $mode= &BitOp( $mode, $op, $where );            # "1" bit(s)
        if(  "-" eq $op  ) {                    # -x implies -s:
            if(  0 != ( $mode & $top )  &&  $change !~ /s/  ) {
                warn qq<$Self:  Note, "$who$op$change" removed "s" mode bit>,
                     qq< along with "x" ($file).\n>   unless  "" eq $Debug;
                $mode= &BitOp( $mode, $op, $top );
            }
        }
    } elsif(  "X" eq $bit  ) {          # Set/clear "execute" bit(s) sometimes:
        # Set "x"s if started with an "x" set or is a directory;
        # Clear "x"s if not a directory and started with an "x" unset:
        if(  "-" ne $op  &&  ( -d _ || 0 != ( $mode & 0111 ) )
         ||  "-" eq $op  &&  ( ! -d _ && 0111 != ( $mode & 0111 ) )  ) {
            $mode= &BitOp( $mode, $op, $where );
            if(  "-" eq $op  ) {                        # -x implies -s:
                if(  0 != ( $mode & $top )  &&  $change !~ /s/  ) {
                    warn qq<$Self:  Note, "$who$op$change" removed "s" mode>,
                         qq< bit along with "x" ($file).\n>
                      unless  "" eq $Debug;
                    $mode= &BitOp( $mode, $op, $top );
                }
            }
        }
    } elsif(  "s" eq $bit  ) {          # Set/clear set-UID/set-GID bit(s):
        if(  0 == $top  ) {             # Must be /o[-+=]s/ or something:
            warn qq<$Self:  Warning, "s" ignored after "$who" >,
                 qq<($who$op$change)\n>   if  $First;   # Don't repeat warning.
        } elsif(  "-" ne $op  &&  $change !~ /x/        # +s,=s requires +x,=x:
         &&  (1*$where) != ( $mode & (1*$where) )  ) {  # Unless "x" was set.
            warn qq<$Self:  Note, no "x" access so set-ID bits not set >,
                 qq<by "$who$op$change" ($file).\n>   unless  "" eq $Debug;
        } else {
            $mode= &BitOp( $mode, $op, $top );
        }
    } elsif(  "t" eq $bit  ) {                  # Set/clear "sticky" bit:
        if(  $who =~ /^[go]+$/  ) {
            warn qq<$Self:  Warning, "t" ignored after "$who" >,
                 qq<($who$op$change)\n>   if  $First;   # Don't repeat warning.
        } else {
            $mode= &BitOp( $mode, $op, 01000 );
        }
    } elsif(  "l" eq $bit  ) {          # Set/clear mandatory record locking:
        ##if(  $who =~ /^[go]+$/  ) {
            ##warn qq<$Self:  Warning, "l" ignored after "$who" >,
            ##     "($who$op$change)\n"   if  $First;   # Don't repeat warning.
        ##} els # UnixWare chmod(1) allows this so I will too.
        if(  "-" eq $op  ) {                            # "-l" always works:
            $mode &= ~ 02000   if  02000 == ( 02010 & $mode );
        } elsif(  0 != ( 00010 & $mode )  ) {           # Group exec allowed:
            warn qq<$Self:  Warning, "l" ignored ($who$op$change) since "g+x">,
                 qq< set ($file).\n>   unless "" eq $Debug;# so can't set "+l".
        } else {
            $mode &= ~ 02010;   $mode |= 02000;         # Set "+l".
        }
    } elsif(  "u" eq $bit  ) {                  # Apply previous "u" bits:
        $mode= &BitOp(  $mode,  $op,  $where * ( 7 & ($start>>6) )  );
        # Should /u[-+=]g/ really change "u+s" based on "g+s"???
        if(  0 != ( 04000 & $start )  ) {       # "u+s" was set...
            $mode= &BitOp( $mode, $op, $top );  # so modify "s" bit(s).
        }
    } elsif(  "g" eq $bit  ) {                  # Apply previous "g" bits:
        $mode= &BitOp(  $mode,  $op,  $where * ( 7 & ($start>>3) )  );
        # Should /g[-+=]u/ really change "u+s" based on "g+s"???
        if(  0 != ( 02000 & $start )  ) {       # "g+s" was set...
            $mode= &BitOp( $mode, $op, $top );  # so modify "s" bit(s).
        }
    } elsif(  "o" eq $bit  ) {                  # Apply previous "o" bits:
        $mode= &BitOp(  $mode,  $op,  $where * ( 7 & ($start) )  );
    } else {                                    # Unreachable code:
        die "$Self:  Imposible mode letter ($bit)";
    }
    $mode;      # Return modified value of mode bits.
}       # End of &SetBit

sub ModePart {  # Apply $change to $mode bits, returning new $mode ($start...
  my( $start, $mode, $change, $file )= @_;      # is mode before any changes).
  my( $who, $op )= ( $change =~ m#^(a|[ugo]*)([-+=])# ); # Get first 2 parts.
    $change =~ s###;            # Leave only 3rd part in $change.
  my( $where, $top )= ( 0111, 06000 );  # Defaults for "a=..." and "=...".
  # The bits that may change are those set in $top | 7*$where.
  my( $mask )= $mode;   # Save $mode before change so can apply umask to change.
    if(  "a" ne $who  &&  "" ne $who  ) {       # Must be /[ugo]+/:
        $where= $top= 0;                        # Start w/ 0 then "or" in bits.
        if(  $who =~ /u/  )                     # Change user mode bits:
            {   $where |= 0100;   $top |= 04000;   }
        if(  $who =~ /g/  )                     # Change group mode bits:
            {   $where |= 0010;   $top |= 02000;   }
        if(  $who =~ /o/  )                     # Change other mode bits:
            {   $where |= 0001;   }
    }   # Else use default $where and $top from declaration.
    if(  "=" eq $op  ) {        # Clear unmentioned bits:
        $mode &= ~ (7*$where);  # Clear all u, g, and/or o "rwx" mode bits.
        if(  -d _  ) {          # For directories...
            $mode &= ~ ( $top & ~02000 );       # don't clear "g=s" bits.
        } else {                # For non-directories...
            $mode &= ~ ($top);          # clear "u=s" and/or "g=s" bits.
        }
    }
    { my( $bit );                       # For each character past /[-+=]/:
        foreach $bit (  split( //, $change )  ) {
            $mode= &SetBit( $mode, $bit, $where, $top, $start,  # Change...
              $who, $op, $change, $file );      # bits of $mode based on char.
        }
    }
    if(  "" eq $who  ) {        # Apply "umask" to these mode bit changes:
      my( $umask )= umask;      # Since umask doesn't mention ug=s...
        $umask |= 04000   if  0 != ( 0100 & $umask );   # Do u-s if doing u-x.
        $umask |= 02000   if  0 != ( 0010 & $umask );   # Do g-s if doing g-x.
        # Find bits turned on[off] in the $mask=>$mode transition
        # then turn off[on] any of these that are also set[clear] in umask:
        $mode &= ~ ( $mode & ~$mask & $umask ); # (The "turn off bits" part.)
        $mode |= ( ~$mode & $mask & ~$umask );  # (The "turn on bits" part.)
    }
    $mode;      # Return modified mode bits.
}       # End of &ModePart

sub SetMode {   # Apply all "mode" changes to one file:
  my( $file, $mode )= @_;       # File to change and its current mode bits.
# Global( =@Mode, =$Mode, =$Self );
    if(  ! @Mode  ) {           # Simple numeric mode specified (not symbolic):
      my( $gid )= 02000 & $mode; # Check current "g+s" (for directories).
        $mode &= ~ 07777;       # Clear out all of the bits.
        $mode |= $Mode;         # Set the bits as requested.
        if(  -d _  )            # Except "g+s" on directories stays same:
            {   $mode &= ~ 02000;   $mode |= $gid;   }
    } else {                    # Symbolic mode change(s) given:
      my( $start )= $mode;      # Record "initial" mode for "g+u", etc.
      my( $change );
        foreach $change ( @Mode ) {     # Apply each comma-separated part:
            $mode= &ModePart( $start, $mode, $change, $file );
        }
    }
    chmod( $mode, $file )  ||  warn "$Self:  ",
      "Can't change mode (", sprintf("0%o",$mode), ") on file ($file): $!\n";
  my( $end )= 07777 & (stat($file))[2]; # Fetch actual mode after change.
    $mode &= 07777;             # Check for mode bit changes that chmod()...
    if(  $end != $mode  ) {     # silently ignored and announce them:
        if(  $_= $end & ~$mode  ) {
            warn "$Self:  Bits that chmod(2) would not turn off: ",
              sprintf( "%05.5o", $end & ~$mode ), " ($file)\n"
              unless  "" eq $Debug;
        }
        if(  $_= $mode & ~$end  ) {
            warn "$Self:  Bits that chmod(2) would not turn on:  ",
              sprintf( "%05.5o", $mode & ~$end ), " ($file)\n"
              unless  "" eq $Debug;
        }
    }
    $First= 0; # We've changed a file; don't repeat some warnings on next file.
}       # End of &SetMode

sub ModeStr {   # Take numeric file mode bits, return "rwxrwxrwx" string:
  my( $mode )= @_;      # Numeric mode (an integer, not an octal string).
  my( $str )= "";       # Start building the string that represents it.
    $str .= ( 0400 & $mode ) ? "r" : "-";
    $str .= ( 0200 & $mode ) ? "w" : "-";
    if(  00100 == ( 04100 & $mode )  ) {                $str .= "x";
    } elsif(  00000 == ( 04100 & $mode )  ) {           $str .= "-";
    } elsif(  04100 == ( 04100 & $mode )  ) {           $str .= "s";
    } else {                                            $str .= "S";
    }
    $str .= ( 0040 & $mode ) ? "r" : "-";
    $str .= ( 0020 & $mode ) ? "w" : "-";
    if(  00010 == ( 02010 & $mode )  ) {                $str .= "x";
    } elsif(  00000 == ( 02010 & $mode )  ) {           $str .= "-";
    } elsif(  02010 == ( 02010 & $mode )  ) {           $str .= "s";
    } else {                                            $str .= "l";
    }
    $str .= ( 0004 & $mode ) ? "r" : "-";
    $str .= ( 0002 & $mode ) ? "w" : "-";
    if(  00001 == ( 01001 & $mode )  ) {                $str .= "x";
    } elsif(  00000 == ( 01001 & $mode )  ) {           $str .= "-";
    } elsif(  01001 == ( 01001 & $mode )  ) {           $str .= "t";
    } else {                                            $str .= "T";
    }
    $str;       # Return constructed string.
}       # End of &ModeStr

sub LogChange { # Describe how a file's mode, user, and group were changed:
  # For example:  ":1=:other -> :3=:sys;  changed.group"  or
  # ".-x.w.r.- => rwsr-x--x; 101:1=tye:other -> 0:3=root:sys;  file.name"
  my( $file, $Omode, $Ouser, $Ogrp )= @_;       # Filename, previous attributes.
  my( $Nmode, $Nuser, $Ngrp )= (stat($file))[2,4,5];    # Get new attributes.
  my( $notice )= "";                    # Notice string that will be displayed.
    if(  "" ne $Ouser  ) {
        # Describe any changes to user and/or group ownership:
        $notice .= (getpwuid($Ouser))[0]                if  $Ouser != $Nuser;
        $notice .= ":".(getgrgid($Ogrp))[0]             if  $Ogrp != $Ngrp;
        $notice .= "="                                  if  "" ne $notice;
        $notice .= $Ouser                               if  $Ouser != $Nuser;
        $notice .= ":".$Ogrp                            if  $Ogrp != $Ngrp;
        $notice .= " -> "                               if  "" ne $notice;
        $notice .= "$Nuser"                             if  $Ouser != $Nuser;
        $notice .= ":$Ngrp"                             if  $Ogrp != $Ngrp;
        $notice .= "="                                  if  "" ne $notice;
        $notice .= (getpwuid($Nuser))[0]                if  $Ouser != $Nuser;
        $notice .= ":".(getgrgid($Ngrp))[0]             if  $Ogrp != $Ngrp;
        $notice .= "; "                                 if  "" ne $notice;
    }
    if(  $Omode != $Nmode  ) {      # Describe changes to mode (permissions):
      my( $Ostr, $Nstr )= ( &ModeStr($Omode), &ModeStr($Nmode) );
        foreach (  0 .. length($Ostr)-1  ) {    # Turn unchanged bits of...
            substr($Ostr,$_,1)= "."             # old mode string into "."s.
              if  substr($Ostr,$_,1) eq substr($Nstr,$_,1);
        }
        $notice= "$Ostr => $Nstr; $notice";
    }
    $notice= "(no change) "   if  "" eq $notice  &&  1 < $Debug;
    $notice= "Temporary:  " . $notice   if  "" eq $Ouser;
    print "$notice $file\n"   unless  "" eq $notice;
}       # End of &LogChange

sub ChangeFile {        # Change mode, user, and/or group owner of one file:
  my( $file )= @_;      # Name of file to change.
# Global( =$ModLink, =$User, =$Group, =$Mode, =$Self, =$Debug );
  ##Local( $ModLink );
    ##$ModLink=  $ModLink  &&  -l $file;
    ##if(  ! $ModLink  &&  ! -e $file  ) { ##}
    if(  ! -e $file  ) {
        warn "$Self:  Can't find file ($file): $!\n";
    } else {
      my( $mode, $user, $group )= (stat(_))[2,4,5];
        # If I'm not root and am changing owner, chmod while I still own it:
        if(  0 != $>  &&  "" ne $User  ) {
            &SetMode( $file, $mode )   if  "" ne $Mode;
        }
        ##if(  $ModLink  ) {
        ##    lchown( $user, $group, $file );
        ##} els
        if(  "" ne $User  ||  "" ne $Group  ) {         # Need to call chown():
            chown(  "" ne $User ? $User : $user,        # User or group might...
                    "" ne $Group ? $Group : $group,  $file  )   # stay the same.
             ||  warn "$Self:  Can't change owner/group of file ($file): $!\n";
        }
        # If don't have to chmod first, chmod last so "+s" will take effect:
        if(  0 == $>  ||  "" eq $User  ) {      # Exact opposite of above test.
            &SetMode( $file, $mode )   if  "" ne $Mode;
        }                       # Tell the user what we changed, if interested:
        &LogChange( $file, $mode, $user, $group )   if  $Debug;
    }
}       # End of &ChangeFile

sub MungFile {  # Modify file and possibly recurse into subdirectories.
  my( $file )= @_;      # Name of file to change.
# Global( =$Self, =$Debug, =$Recurse );
    if(  ! $Recurse  ||  -l $file  ||  ! -d _  ) {
        &ChangeFile( $file );
    } else {                            # Recurse into subdirectory:
      my( $dir )= $file;
      my( $omode )= "";
      my( @dirs );
        if(  0 != $>  &&  ! -x _  ||  ! -r _  ) {
            $omode= (stat(_))[2];               # So can we undo this later.
            # "chmod u+rx" so can recurse into this subdirectory:
            if(  chmod( 0500 | $omode, $dir )  &&  "" ne $Debug  ) {
                &LogChange( $dir, $omode, "", "" );
            }
        }
        if(  ! opendir( DIR, $dir )  ) {
            warn "$Self:  Can't read subdirectory "
               . "to recurse into ($dir): $!\n";
        } else {
            while(  defined( $file= readdir(DIR) )  ) { # So "sub/0/dir" OK.
                if(  -l "$dir/$file"  ||  ! -d _  ) {
                    &ChangeFile( "$dir/$file" );
                } elsif(  $file !~ m#^\.{1,2}$#  ) {
                    push( @dirs, $file );
                }       # Don't recurse into "." nor ".." (infinite loop).
            }
            closedir( DIR );
            while(  @dirs  ) {
                &MungFile( "$dir/" . pop(@dirs) );
            }
        }
        chmod( $omode, $dir )   if  "" ne $omode;
        &ChangeFile( $dir );
    }
}       # End of &MungFile

# sub main {    # Program execution starts here:

$ActLike= &WhoAmI;      # Returns "mug", "mod", "own", or "grp".
&ParseFlags;            # Parse command-line flags (set globals, adjust @ARGV).
&ModelFile   if  "" ne $Model;  # Copy owner/mode from model (unshifts @ARGV).
if(  @ARGV < 2  ) {     # "chmug change file" is minimum invokation:
    &Usage( "Too few arguments" );      # Give helpful usage message.
}
if(  "own" eq $ActLike  &&  "," ne substr($ARGV[0],0,1)  ) {    # Be "chown":
    $ARGV[0]= ",$ARGV[0]";      # "chown usr file" -> "chmug ,usr file"
} elsif(  "grp" eq $ActLike  &&  ":" ne substr($ARGV[0],0,1)  ) { # Be "chgrp":
    $ARGV[0]= ":$ARGV[0]";      # "chgrp grp file" -> "chmug :grp file"
}
&ParseChange( shift(@ARGV) );   # Parse mode/user/group arg into globals.
##if(  $ModLink  &&  "" ne $Mode  ) {
##    die "$Self:  Do not specify mode ($Mode) when using -h.\n"
##}
{ my( $file );
    foreach $file ( @ARGV ) {   # Change each file listed:
        &MungFile( $file );     # (possibly recursing into directories)
    }
}
# }     # end of "main"

__END__
Technical notes:

If run as chmod (ie. if file name contains "mod"),
        "-f model" only changes mode bits (not user or group).
If run as chown (ie file name matches /own/ or /use?r/),
        can't specify a mode (just "[[,]usr][:[grp]]")
        and "-f model" only changes user and group (not mode bits).
If run as chgrp (ie file name matches /gr[ou]*p/),
        can't specify a mode nor a user (just "[:][grp]")
        and "-f model" only changes group (not mode bits nor user).

Should /u[-+=]g/ really change "u+s" based on "g+s"???

/[-+=][ugo]/ uses what the mode bits where on the file _before any changes
were made_ while all other operations (/[-+=]X/, /[+=]s/) that look at the
"current" mode bits, use the mode bits _after_ applying whatever changes
appear before (to the left of) that operation.  So "+s" always works in
"g+xs", "g+x+s", "g+x,g+s" because it can see the preceeding "+x".  For
convenience, /[+=]s/ can also see an "x" that follows it as part of the
same operation (no [-+=] between the "s" and the "x") so the "s" will always
work in "g+sx" because it can see the nearby "+x" but "+s" will fail in
"g+s+x" and "g+s,g+x" if the file was "g-x" at that point.

Examples:
    "chmug =" = "chmug -rwx" turn off all bits set in umask


