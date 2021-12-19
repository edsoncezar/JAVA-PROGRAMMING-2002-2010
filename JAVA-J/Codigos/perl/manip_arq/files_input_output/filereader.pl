 print "\n\nPerl File Reader by Struct Software\n";
    print "-----------------------------------\n\n";
    print "Enter a file name:\n";
    $fname=<STDIN>;
    chomp($fname);
    print "\n";
    if (-f $fname){ #if a file
    open FILE, "<$fname"|| die $!;
    @fa = <FILE>; #makes file to an array
    close FILE;}
    foreach $fa(@fa){#shows the array in print
    print "$fa";}
    print "\n\n\n";

