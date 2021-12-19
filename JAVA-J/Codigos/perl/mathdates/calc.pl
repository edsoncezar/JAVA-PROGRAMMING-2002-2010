 #!/usr/bin/perl -w
    ##########################################
    ## Daylight Savings Time in the US and EU
    ##########################################
    use strict;
    my ($intDay, $Year) = 0;
    my ($Country, $BeginEnd) = '';
    $intDay = &DaylightSavings(2003, 'US', 'Begin');
    print "************************************************\n";
    print "*** Calculate Daylight-Saving Time ***\n";
    print "************************************************\n";
    print "1. Year - Must be 4 Digits\n";
    print "2. Country - Must be US or EU\n";
    print "3. Begin/End - Must be Begin or End; 1 or 2\n";
    print "************************************************\n\n";
    print "1. Enter the Year: ";
    chomp ($Year = <STDIN>);
    print "2. Choose US or EU: ";
    chomp ($Country = uc(<STDIN>));
    print "3. Choose Begin or End: ";
    chomp ($BeginEnd = ucfirst(lc(<STDIN>)));
    $intDay = &DaylightSavings($Year, $Country, $BeginEnd);
    if ($intDay == 0) {
    	print "An Invalid Calculation was entered. Make sure\n";
    	print "A valid 4 Digit Year was used, along with US or EU\n";
    	print "was chosen, and the Begin or End was chosen\n";
    }
    if ($Country eq 'US') {
    	if ($BeginEnd eq 'Begin' || $BeginEnd eq 1) {
    		print "\nDaylight-Savings Begins on: ";
    		print "Sunday, April $intDay, $Year\n";
    	}
    	elsif ($BeginEnd eq 'End' || $BeginEnd eq 2) {
    		print "\nDaylight-Savings Ends on: ";
    		print "Sunday, October $intDay, $Year\n";
    	}
    }
    elsif ($Country eq 'EU') {
    	if ($BeginEnd eq 'Begin' || $BeginEnd eq 1) {
    		print "\nDaylight-Savings Begins on: ";
    		print "Sunday, March $intDay, $Year\n";
    	}
    	elsif ($BeginEnd eq 'End' || $BeginEnd eq 2) {
    		print "\nDaylight-Savings Ends on: ";
    		print "Sunday, October $intDay, $Year\n";
    	}
    }
    print "\n\n";
    #####################################################
    ## Sub: DaylightSavings
    ## Description: This sub calculates Daylight Savings
    ##Based on the Year, Country, Begin(1) or End(2) 
    ##that is passed to the subroutine.
    ## Examples:
    ##$intDay = &DaylightSavings(2003, 'US', 'Begin');
    ##$intDay = &DaylightSavings(2003, 'EU', '1');
    #####################################################
    sub DaylightSavings {
    	my $intYear = shift;
    	my $strCountry = shift;
    	my $strBeginEnd = shift;
    	my $intDay = 0;	
    	if ($strCountry eq 'US') {
    		if ($strBeginEnd eq "Begin" || $strBeginEnd eq 1) {
    			$intDay = ((2+6*$intYear-int($intYear/4)) % 7+1);
    		}
    		elsif ($BeginEnd eq "End" || $strBeginEnd eq 2) {
    		$intDay = (31-($intYear*5/4+1) % 7);
    	}
    	else {
    		$intDay = 0;
    	}
    }
    elsif ($strCountry eq 'EU') {
    	if ($strBeginEnd eq "Begin" || $strBeginEnd eq 1) {
    		$intDay = (31 - (5*$intYear/4 + 4) % 7);
    	}
    	elsif ($strBeginEnd eq "End" || $strBeginEnd eq 2) {
    		$intDay = (31 - (5*$intYear/4 + 1) % 7);
    	}
    	else {
    		$intDay = 0;
    	}
    	}
    	else {
    		$intDay = 0;
    	}
    	### Return the Day
    	return $intDay;
    }

