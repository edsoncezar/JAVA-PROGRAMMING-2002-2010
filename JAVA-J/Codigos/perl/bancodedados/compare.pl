 ##
    ## Written by John Winger (john@wingeronline.com)
    ##
    ## Use as you wish, but as a courtesy I ask that you give me credit in your code. 
    ##
    print "This script is testing a routine to compare 2 dates.\n\n";
    $dtcompare = "2002-03-04 14:36:15";
    print "Enter a date to compare against $dtcompare: ";
    $userdate = <STDIN>;
    chomp($userdate);
    $comp_results = compareDbDateTime($userdate, $dtcompare);
    if($comp_results == -1) { print "\t$userdate is less than $dtcompare\n\n"}
    elsif ($comp_results == 0) { print "\t$userdate is equal to $dtcompare\n\n" }
    elsif ($comp_results == 1) { print "\t$userdate is greater than $dtcompare\n\n" }
    else{ print "\tError\n\n"}
    sub compareDbDateTime
    {
    	# answers how does date1 compare to date2
    	# (greater than "1", less than "-1", or equal to "0")
    	my ($dt1, $dt2) = @_;
    	
    	my @datetime1;
    	my @datetime2;
    	my $limit = 0;
    	
    	my ($date1, $time1) = split(/ /, $dt1);
    	push(@datetime1, split(/-/, $date1));
    	push(@datetime1, split(/:/, $time1));
    	
    	my ($date2, $time2) = split(/ /, $dt2);
    	push(@datetime2, split(/-/, $date2));
    	push(@datetime2, split(/:/, $time2));
    	
    	# compare up to the lesser number of elements
    	# (like if one datetime only has a date and no time, don't try to compare time)
    	if(@datetime1 == @datetime2) { $limit = @datetime1 }
    	elsif (@datetime1 > @datetime2) { $limit = @datetime2 }
    	elsif (@datetime1 < @datetime2) { $limit = @datetime1 }
    	
    	for (my $i = 0; $i < $limit; $i++)
    	{
    		if ($datetime1[$i] > $datetime2[$i]) { return 1; last; }# date1 greater than date2
    		if ($datetime1[$i] < $datetime2[$i]) { return -1; last; }# date1 less than date2
    	}
    	return 0;# dates are equal
    }

