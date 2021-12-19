 #! /usr/local/bin/perl
    #
    # prints the times table of a specified number
    #
    print "Display which number's table? ";
    $timing = <stdin>;
    chop $timing;
    print "The $timing times table: \n";
    for $num(1 .. 12){
    	$times = $num * $timing;
    	$res = $times ."\n";
    	print $num ." x $timing = " .$res;
    }

