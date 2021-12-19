 #!/usr/bin/perl
    use warnings;
    use strict;
    # This program asks the user for choose a file and will then change all HTML tags into lower case
    # Written by Tyler Cruz
    my $filename; # File to be parsed
    my @contents; # Contents of file
    print "Please type the name of the file to parse (with extention)\n>";
    $filename = <STDIN>; #Get user input
    #open the file and store the contents in variable
    open (FH, "$filename") || die "Error opening filename";
    @contents = <FH>;
    close FH;
    #write to file
    open (FH, ">$filename");
    #test each line
    my $i;
    foreach $i (@contents) {
    $i =~ s/<(.*?)>/<\L$1>/g;
    print FH $i;
    }
    close FH
    print "\n\nDone!"

