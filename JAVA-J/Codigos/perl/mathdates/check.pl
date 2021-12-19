 ##################################################
    #
    # This subroutine simply checks if a date is valid
    # Usage: if (&isdate($datein)) { print "ok!" ; }
    # $datein -> Required - "dd/mm/yyyy" format
    # Returns: 1 # date okay
    #	-1 # day invalid
    #	-2 # month invalid
    #	-3 # year invalid
    #
    ##################################
    sub isDate {
    my ($dd,$mm,$yy) = split(/\//,@_[0]) ;
    # make sure that $yy is 4 digits
    if ($yy =~ /^\d\d$/) {
    if ($yy < 70) {
    # will assume after 70 is in the 1900's
    #u can change this... 
    $yy += 2000 ;
    } else {
    $yy += 1900 ;
    }
    }
    @monthDays = (31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31) ;
    # check for leap year - Thanks Megan :-)
    if ($yy == int($yy/400)*400) {
    $monthDays[1] = 29;
    } elsif ($yy != int($yy/100)*100) {
    if ($yy == int($yy/4)*4) {
    $monthDays[1] = 29;
    }
    }
    return -3 if ($yy !~ /^\d+$/) ;
    return -2 if (!(($mm > 0) && ($mm <= 12))) ;
    return -1 if (!(($dd > 0) && ($dd <= $monthDays[$mm-1]))) ;
    return 1 ;
    }

