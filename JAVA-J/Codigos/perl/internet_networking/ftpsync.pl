#!/usr/bin/perl
use warnings;
use strict;
use Net::FTP;
$|++;    # Autoflush

# Globals - need to tie these  to a dot file!
my $server    = "";		# CHANGE - FQDN (not URI) of ftp server
my $username  = ""; 	# CHANGE - ftp server username
my $pass      = "";		# CHANGE - ftp server password
my $home      = "/home/chorner";	# CHANGE - parent path of $localsite
my $localsite = "$home/webdocs";	# CHANGE - local copy of web site
my $new       = my $changed = my $unchanged = 0;
my $ftp;

#################### SUBROUTINES ###############################

#################################################################
# connect - Connect and authenticate w/ server
sub ftpconnect {
    print "Connecting to $server..";

    # Set up connection
    $ftp = Net::FTP->new( $server, Passive => 1, Debug => 0 ) 
		or die "..could not establish connection to $server: $@";
    print "..authenticating..";

    # Log in...
    $ftp->login( $username, $pass ) or die "..unable to authenticate: $!";
    print "..done!\n";
}

######################################################################
# ftplogout - close the connection
sub ftplogout {
    print "Logging out..";
    $ftp->quit or die "..trouble closing the connection to $server: $!";
    print "..done!\n";
}

#######################################################################
# compare - 
sub compare {
    my @dirlist = `find $localsite`;
    my $rfile;

    for my $file (@dirlist) {
        chomp $file;

        # Translate to remote directory structure
        if ( rdiff($file) ) {

            #  local file is newer - updating! 
            ( $rfile = $file ) =~ s/$home//;

            # This only takes care of files already 
            # existing  both lcl and rmt
            $ftp->put( $file, $rfile );
			print "UPDATED_FILE: $rfile\n";
            $changed++;
        }
        else {

            # local file is older - not updating!
            $unchanged++;
        }
    }
}

####################################################################
# rdiff - compare local and remote mtimes
#         return 1 -> needs updating
#         return 0 -> already updated
# See note below about failing mdtm method
sub rdiff ($) {
    my $file = shift;
    my ( $ltime, $rtime );

    # Get local and remote modification times
    # Test for directories/new files,  they fail the mdtm method
    $ltime = ( stat($file) )[9];
    $file =~ s/$home//;
    if ( !( $rtime = $ftp->mdtm($file) ) ) {

        # mdtm method fails because:
        #      1. rmt file is a directory
        #      2. rmt file doens't exist (either file or dir)
        # Use checknew() to assess and correct this problem
        checknew($file);
        return 0;
    }
    my $diff = $ltime - $rtime;

    # local is older...gotta update!
    if ( $diff > 0 ) {
        return 1;
    }

    # remote is older...no update!
    elsif ( $diff <= 0 ) {
        return 0;
    }

    # At this point something wierd has happened, so 
    # we refuse to update!
    return 0;
}

#######################################################
# checknew -  take care of new lcl files and directories
sub checknew ($) {
    my $localfile = join ( "", $home, @_ );

    if ( -e $localfile && -f $localfile ) {

        # local file is new, rmt file doesn't exist..transfer
        $ftp->put( $localfile, @_ );
		print "ADDED_FILE: @_\n";
        $new++;
		$unchanged--;
        return 0;
    }
    elsif ( -d $localfile && -e $localfile && !( $ftp->cwd(@_) ) ) {

        # remote directory doesn't exist..create
        $ftp->mkdir(@_);
		print "ADDED_DIR: @_\n";
        $new++;
		$unchanged--;
        return 0;
    }
    else {

        # The only reason you are here is that rmt and local dir both exist
        return 1;
    }
}

### MAINS ####
ftpconnect();
compare();
ftplogout();

print
"Changed $changed files, created $new files, and ignored $unchanged files (including directories)\n";




