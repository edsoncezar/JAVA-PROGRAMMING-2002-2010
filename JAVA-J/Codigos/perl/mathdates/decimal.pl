 #!perl
    #Get arguments from command line
    ($dec)=@ARGV;
    #Check for invalid info
    unless($dec) {
    print "\n\rUsage: $0 <decimal number> \n\r";
    exit();
    }
    # print decimal
    print "decimal number is: $dec\n\r";
    # convert to hex and print results
    $hex = dec2hex($dec);
    print "hex number is: $hex\n\r";
    exit;
    sub dec2hex {
    # parameter passed to
    # the subfunction
    my $decnum = $_[0];
    # the final hex number
    my $hexnum;
    my $tempval;
    while ($decnum != 0) {
    # get the remainder (modulus function)
    # by dividing by 16
    $tempval = $decnum % 16;
    # convert to the appropriate letter
    # if the value is greater than 9
    if ($tempval > 9) {
    $tempval = chr($tempval + 55);
    }
    # 'concatenate' the number to 
    # what we have so far in what will
    # be the final variable
    $hexnum = $tempval . $hexnum ;
    # new actually divide by 16, and 
    # keep the integer value of the 
    # answer
    $decnum = int($decnum / 16); 
    # if we cant divide by 16, this is the
    # last step
    if ($decnum < 16) {
    # convert to letters again..
    if ($decnum > 9) {
    $decnum = chr($decnum + 55);
    }
    
    # add this onto the final answer.. 
    # reset decnum variable to zero so loop
    # will exit
    $hexnum = $decnum . $hexnum; 
    $decnum = 0 
    }
    }
    return $hexnum;
    } # end sub

