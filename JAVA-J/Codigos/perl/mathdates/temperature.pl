  #!/usr/bin/perl -w
    ############################################
    ## This is a simple Temperature converter
    ## that will convert Fahrenheit to Celsius
    ## and Celsius to Fahrenheit.
    ############################################
    use strict;
    my $fahr = 0;
    my $cel = 0;
    my $choice = 0;
    my $input = 0;
    	
    print "\n";
    print "*********************************************\n";
    print "*** This is a Temperature Converter ***\n";
    print "*********************************************\n";
    print "1. Celsius to Fahrenheit.\n";
    print "2. Fahrenheit to Celsius. \n";
    print "3. Exit\n";
    print "*********************************************\n\n";
    print "Enter a choice (1-3): ";
    	
    chomp ($choice = <STDIN>);
    if(&IsNumeric($choice) == 0) {
    	$choice = 0;
    }
    	
    while ($choice != 3) {
    	###################################
    	## Do conversion from C to F
    	###################################
    	if ($choice == 1) {
    		print "\nEnter a Temperature: ";
    		chomp ($cel = <STDIN>);
    		$fahr = ($cel * (9 / 5)) + 32;
    		
    		###############################
    		## Format to one decimal
    		###############################
    		$fahr = sprintf("%.1f", $fahr);
    		
    		print "$cel degrees Celsius = ";
    		print "$fahr degrees Fahrenheit\n";
    	}
    	
    	###################################
    	## Do conversion from F to C
    	###################################
    	elsif ($choice == 2) {
    		print "\nEnter a Temperature: ";
    		chomp ($fahr = <STDIN>);	
    		$cel = ($fahr - 32) * 5 / 9;
    		
    		###############################
    		## Format to one decimal
    		###############################
    		$cel = sprintf("%.1f", $cel);
    		
    		print "$fahr degrees Fahrenheit = ";
    		print "$cel degrees Celsius\n";
    	}
    	
    	###################################
    	## Display Error Message
    	###################################
    	else {
    		print "\nYou entered and invalid choice please choose a choice from the menu.\n\n";
    	}
    	
    	print "\nEnter a Choice (1-3): ";
    	chomp ($choice = <STDIN>);
    	
    	if(&IsNumeric($choice) == 0) {
    		$choice = 0;
    	}
    }
    	
    ##################################################################
    ## Sub Name: IsNumeric.
    ## Description: This sub validates the input to check to see if
    ## the input is a Numeric value
    ## Example: 100, 1,000, $10.00, and 14.00 are valid inputs.
    ##################################################################
    sub IsNumeric {
    	my $InputString = shift;
    	
    	if ($InputString !~ /^[0-9|.|,]*$/) {
    		return 0;
    	}
    	else {
    		return 1;	
    	}	
    }
    	
    	
    	
    	
    	
    	

