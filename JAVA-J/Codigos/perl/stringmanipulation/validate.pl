# This program will validate a date
    # It will print an error message if the month is not valid
    # It will also print an error message if the day is not valid
    # It checks the string to make sure it is in the right format
    #
    # Define the hash and the arrays
    %daynum = ();
    @monthlist = 
    ('january','february','march','april','may','june','july','august','september','october','no
    vember','december');
    @daylist = (31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
    # Initialize daylist with the values of the array
    for ($i=0; $i<12; $i++)
    {
    	$daynum{$monthlist[$i]} = $daylist[$i];
    } 
    # Define variable for continuation of while loop - set to true
    $h = 1;
    # start while loop for the verification process
    while ($h == 1){
    	$y = 1;
    	# Define variable for checking month - set to 0
    	$g = 0;
    	while($y == 1)
    	{
    		# make sure february has 28 days - might have been changed from prev
    		if ($daynum{$monthlist[1]} != 28) { $daynum{$monthlist[1]} = 28; }
    		# The user enters a string
    		print "\nEnter a date: month(word) day year. Please follow this 
    format\n";
    		print "with a space between each one. Please do not abbreviate: \n";
    		$_ = <STDIN>;
    		# check if string is too long: 
    		@too_long = split;
    		if($#too_long != 2) { print "\nPlease try again. String is too long or 
    short.\n"; }
    		else { $y = 0; }
    	}
    	# split the value entered into three parts
    	($month,$day,$year) = split;
    	# lowercase the month
    	$month =~ tr/A-Z/a-z/;
    	# check to see if year has problems
    	if (($year%4 == 0) && ($year%100 == 0) && ($year%400 == 0)){
    	 	$daynum{$monthlist[1]} = 29;
    	}
    	elsif (($year%4 == 0) && ($year%100 != 0)){
    		$daynum{$monthlist[1]} = 29;
    	}
    	for ($i=0; $i<12; $i++) # check the month
    	{
    		if ( @monthlist[$i] eq $month)
    		{
    			if ($day > 0 && ($daynum{$monthlist[$i]} >= $day)){
    				print "\nGood job! You entered a valid month and day.\n";
    				$g = 2;
    				$h = 0;
    			}
    			else {
    				$g = 1; # use g to show that the month was correct - day 
    wrong
    			}
    		}
    	}
    	if ($g == 1)
    	{
    		print "\nYou entered a valid month, but not a valid day.\n";
    		print "\nPlease try again!\n";
    	}
    	elsif ($g == 0) 
    	{
    		print "\nYou were unable to enter a valid month.";
    		print "\nPlease try again!\n\n";
    	}
    }
    exit(0);
 