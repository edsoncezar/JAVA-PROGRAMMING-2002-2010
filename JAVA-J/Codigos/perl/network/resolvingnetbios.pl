 #!/usr/bin/perl -w #
    ##
    # This code uses the nmblookup command line program to #
    # get the NetBIOS Name of a computer with a given IP Address#
    #
    s
    sub GetNBName {
    open (NBSTAT, "nmblookup -A $_[0] |"); # Open pipe to read output
    @Output = <NBSTAT>; # Read output
    close NBSTAT;# Close pipe
    @Filtered = grep(/<00>/, @Output); # Remove all but those containing <00>
    $fil = $Filtered[0];# The first <00> is most likely the NetBIOS Name
    $fil =~ /(.*?)\</; # Extract the NetBIOS Name
    $name = $1;
    $name =~ s/[\s\t]//g;# Remove surrounding whitespace
    return $name;# Return the NetBIOS Name}
    }
    i
    if (!(@ARGV)) {
    print "Usage:\n";
    print "\t$0 <ipaddress>\n";
    print "\tReturns NetBIOS Name of IP Address\n"; }
    }e
    else {
    my $nbname = &GetNBName($ARGV[0]);
    print "NetBIOS Name of $ARGV[0] is $nbname\n"; }
    }

