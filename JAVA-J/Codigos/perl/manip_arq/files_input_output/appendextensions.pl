 #!/usr/bin/perl
    # This Perl script appends the extension given by the first argument to
    # all the file names in its directory
    # ex. temp = temp.txt
    print "Appending " . @ARGV[0] . " to all files...\n";
    foreach (glob "*") {
    if ($_ ne substr($0, 2)) {
    rename ($_, $_ . "." . @ARGV[0]);
    }
    }
    print "Done\n";