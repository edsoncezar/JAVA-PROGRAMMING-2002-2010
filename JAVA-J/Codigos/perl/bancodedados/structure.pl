 #!/usr/bin/perl
    use Data::Dumper; # this module is used to store the hashtable in a way that can be recovered with the 'do' statement
    # read the content of "hash.txt" to a variable (this variable will contain a reference to the hashtable)
    $h= do "hash.txt";
    # store the reference content into an hashtable
    %hash=%{$h};
    # prints the content of the hashtabele
    print "previous inserted values:\n";
    print "----------------------\n";
    for (keys %hash)
    {
    	print "$_ => $hash{$_}\n";
    }
    	
    print "----------------------\n";
    # reads new key and value
    print "new key:";
    $key=<STDIN>;
    chomp($key);
    print "value for \'$key\':";
    $value=<STDIN>;
    chomp $value;
    # stores the key and the value in the hashtable
    $hash{$key}=$value;
    # stores the hashtable in the file
    open(A,">hash.txt");
    print A Dumper \%hash; # don't forget to put the backslash before the hashtable, otherwise it won't work
    close(A);
    ## DON'T FORGET TO VOTE

