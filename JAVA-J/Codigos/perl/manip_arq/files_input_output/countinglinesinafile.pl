 # $ENV{HOME} = your home directory
    open(FP, "$ENV{HOME}/perl/sample.dat");
    while(<FP>) { }
    print "Number of lines -> $.\n";
    # That's it.
    # $. -> the current input line number of the last
    #filehandle that was read. Reset only when
    #the filehandle is closed explicitly.

