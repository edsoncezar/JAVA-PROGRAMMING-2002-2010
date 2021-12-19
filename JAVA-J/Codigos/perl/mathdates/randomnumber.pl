 #!/usr/bin/perl -w
    ###############################################
    ## A random Number generator list.
    ## This script will generato a list of
    ## random numbers that are not repeated
    ## in the list. Very useful in generating
    ## lotto numbers or anything else that
    ## you can not have repetitive numbers
    ###############################################
    use strict;
    use integer;
    ########################################
    ## Configuration:
    ## @Numbers = a valid range of numbers
    ## $Limit= a count of how many random
    ##to return. This includes 0, so if
    ##you put 20, 21 numbers will be 
    ##returned in the list.
    ########################################
    my @Numbers = 1..49;
    my $Limit = 20; 
    my @list = ();
    print "\n****************************************************\n";
    print "***** This is a Random number generator script *****\n";
    print "****************************************************\n\n\n";
    for(my $i = 0; $i <= $Limit; $i++) {
    	my $intRand = int(rand(@Numbers))+1;
    #########################################
    ## Add the first number to the list, and
    ## then compare all remaining numbers 
    ## with those numbers already in the list
    #########################################
    if ($i == 0) {
    	$list[$i] = $intRand;
    }
    else {
    		for (my $j = 0; $j<$i; $j++) {
    			while ($intRand == $list[$j]) {
    				#########################################
    				## The random number has already been 
    				## added to the list. Generate a new
    				## Random number and set the $j counter
    				## to -1 so it starts over to see if the
    				## newly generated number is in the list
    				#########################################
    				$intRand = int(rand(@Numbers))+1;
    				$j = -1;
    			}
    			#########################################
    			## We just found another random number
    			## that has not been added to the list 
    			## yet, so we are adding it in now.
    			#########################################
    			$list[$i] = $intRand;
    		}	
    	}	
    }
    print "****************************************************\n";
    print "** This is the list of ". ($Limit+1) ." random numbers chosen\n";
    print "** by the system in the order they were chosen\n";
    print "****************************************************\n\n";
    for (my $i = 0; $i <=$Limit; $i++) {
    	print "$list[$i] ";
    }
    print "\n\n\n";
    ##########################################
    ## Now Sort the Numeric list of numbers
    ##########################################
    my @SortedList = sort {$a <=> $b} @list;
    print "****************************************************\n";
    print "** This is the Sorted list of ". ($Limit+1) ." random\n";
    print "** numbers chosen by the system\n";
    print "****************************************************\n\n";
    for (my $i = 0; $i <=$Limit; $i++) {
    	print "$SortedList[$i] ";
    }
    print "\n\n\n";

