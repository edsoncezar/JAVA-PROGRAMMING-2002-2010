 #!/usr/bin/perl
    use Getopt::Std;
    use POSIX qw(strftime);
    getopts("hf:");
    if ( $opt_h ) {
    print "\nUsage is: showdate \[ -f format \] \[days ago\]\n\n";
    print " - where \"format\" is a vaild POSIX format string\n";
    print " - and \"days ago\" is the number of days ago to show\n\n";
    exit;
    }
    # Return 0 if day arg is not numeric
    if ( $ARGV[0] =~ /\D/ ) {
    print "0";
    exit -1;
    }
    # Set default time format to YYYYMMDD
    if ( $opt_f ) {
    $FORMAT=$opt_f;
    } else {
    $FORMAT="%Y%m%d";
    }
    # Generate formatted string
    if ( ! $ARGV[0] ) {
    printf "%s", strftime($FORMAT, localtime( time ) );
    } else {
    printf "%s", strftime($FORMAT, localtime( time - ( 86400 * $ARGV[0] ) ) );
    }

