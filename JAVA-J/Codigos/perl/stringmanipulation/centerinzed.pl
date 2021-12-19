 print "Centerized Your String\n";
    print "Written by Ram2i (ram2i\@hotmail.com)\n\n";
    ############################################
    # stty -a : find your current settings
    # >filename : save the o/p of the stty cmd into a file
    ############################################
    system "stty -a >$ENV{HOME}/temp.dat";
    sysopen(FILE, "$ENV{HOME}/temp.dat", O_RDONLY) or die "$!\n";
    while(<FILE>) 
    {
    # assign to $line if "rows" label found
    $line = $_ if($_ =~ m/rows/);
    }
    # split and put into an array
    @line = split(/;/, $line);
    # trim the str first, then put into new array
    for($i=0; $i<$#line; $i++) 
    {
    $data[$i] = trim($line[$i]);
    }
    for($i=0; $i<$#line; $i++) {
    $post = rindex($data[$i], "="); #backward search, last index
    $data[$i] = trim(substr($data[$i], $post+1, length($data[$i])));
    }
    ############################################
    # data[0] = rows
    # data[1] = columns
    # data[2] = ypixels
    # data[3] = xpixels
    ############################################
    print "TYPE STRING: ";
    chomp($str = <STDIN>);
    print "\n\n";
    print "OUTPUT:\n";
    print "-" x $data[1], "\n";
    print " " x (($data[1] - length($str))/2 ),"$str\n";
    print "-" x $data[1], "\n";
    print "\n\nWallahhh!! It is centered!!!";
    close FILE;
    unlink "$ENV{HOME}/temp.dat";
    ############################################
    # trimming blanks from string
    ############################################
    sub trim 
    {
    my $str = $_[0];
    $str =~ s/^\s+//;
    $str =~ s/\s+$//;
    
    return $str;
    }

