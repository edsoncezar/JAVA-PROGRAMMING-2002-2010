#!/usr/bin/perl -w
    #######################################################
    ## This is an updated version of my IsDate Function 
    ## that validates an input date to see if its a 
    ## valid date. This will check 7 different 
    ## Date/Time formats.
    #######################################################
    	
    use strict;
    use POSIX;
    use Time::Local;
    	
    my $InputDate = '';
    my $Test = '';
    my $Choice = "Y";
    	
    while ($Choice ne "n" && $Choice ne "N") { ;
    	print 'Enter a Date to Validate: ';
    	chomp ($InputDate = <STDIN>);
    	$Test = &IsDate($InputDate);
    	if ($Test ==1) {
    		print "Valid Date\n\n";
    	}
    	else {
    		print "Invalid Date\n\n";
    	}
    	
    	print "Would you like to Test another Date/Time? (Y/N)";
    	chomp ($Choice = <STDIN>);
    		
    }
    	
    ##################################################################
    ## Sub Name: IsDate.
    ## Description: This sub validates the input to check to see if
    ## the input is a valid date. It checks for leap year and the 
    ## number of days in each month.
    ## Example Formats: 
    ## 1. 10/20/2003 = mm/dd/yyyy
    ## 2. 20/10/2003 = dd/mm/yyyy
    ## 3. 10-20-2003 = mm-dd-yyyy
    ## 4. 20-10-2003 = dd-mm-yyyy
    ## 5. 2003-10-20 = yyyy-mm-dd
    ## 6. Thursday, June 26, 2003
    ## 7. Sat Oct 25 14:23:17 2003
    ## Returns:
    ## 1 - True
    ## 0 - False
    ##################################################################
    sub IsDate {
    	my $InputString = shift;
    	my $strDays = "Sunday|Monday|Tuesday|Wednesday|Thursday|Friday|Saturday";
    	my $strAbbrDays = "Sun|Mon|Tue|Thu|Fri|Sat";	
    	my $strMons = "January|February|March|April|May|June|July|
    						August|September|October|November|December";
    	my $strAbbrMons = "Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec";
    	my @arrMonths = ("January","February","March","April","May","June","July",
    	 "August","September","October","November","December");
    	my @arrAbbrMonths = ("Jan","Feb","Mar","Apr","May","Jun",
    								"Jul","Aug","Sep","Oct","Nov","Dec");
    	my @arrDays = ("Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday");
    	my $strDay = '';
    	my $intMonth = -1;
    my ($sec,$min,$hour,$day,$month,$year,$wday,$mhour) = 0;
    my ($DateTime, $WeekDayName, $i, $strTheMonth) = 0;
    
    
    	### 10/20/2003
    	if ($InputString =~ /^(\d{1,2})(\/|-)(\d{1,2})\2(\d{4})$/) {
    		if (($1 < 1 || $1 > 12) && ($3 < 1 || $3 > 12)) {
    			return 0;
    		}
    	
    		if (($3 < 1 || $3 > 31) && ($3 < 1 || $3 > 31)) {
    			return 0;
    		}
    	
    		if ((($1==4 || $1==6 || $1==9 || $1==11) && $3==31) || (($3==4 || $3==6 || $3==9 || $3==11) && $1==31)) {
    			return 0;
    		}
    	
    		if ($1 == 2) {
    			if ($3>29 || ($3==29 && !($4 % 4 == 0 && ($4 % 100 != 0 || $4 % 400 == 0)))) {
    				return 0;
    			}
    		}
    		return 1;
    	}
    	### 2003-10-20
    	elsif ($InputString =~ /^(\d{4})(\-)(\d{1,2})\2(\d{1,2})$/) {
    		if ($3 < 1 || $3 > 12) {
    			return 0;
    		}
    	
    		if ($4 < 1 || $4 > 31) {
    			return 0;
    		}
    	
    		if (($3==4 || $3==6 || $3==9 || $3==11) && $4==31) {
    			return 0;
    		}
    	
    		if ($3 == 2) {
    			if ($4>29 || ($4==29 && !($1 % 4 == 0 && ($1 % 100 != 0 || $1 % 400 == 0)))) {
    				return 0;
    			}
    		}
    		return 1;
    	}
    	### Thursday, June 26, 2003
    	elsif ($InputString =~ /^($strDays), ($strMons) (\d{1,2}), (\d{4})$/i) {
    		### The Input has a valid Date Format, now its time to Validate
    		### the inputted to date to see is a Valid Date
    	
    		$strTheMonth = $2;
    		foreach (@arrMonths) {
    			if ($_ eq ucfirst(lc($strTheMonth))) {
    				$intMonth = $i;
    				last;
    			}	
    			$i++;
    		}
    		
    		if ($intMonth > 11 || $intMonth < 0) { 
    			return 0;
    		}
    		
    		### If the days in the month for April, June, September, November equals 31
    		### then the input date is invalid.
    		if (($intMonth==4 || $intMonth==6 || $intMonth==9 || $intMonth==11) && $3==31) {
    			return 0;
    		}
    		
    		### If Leap Year then allow the days in the month of february to be 29, else only
    		### allow the number of days to be 28.
    		if ($intMonth == 1) {
    			if ($3>29 || ($3==29 && !($4 % 4 == 0 && ($4 % 100 != 0 || $4 % 400 == 0)))) {;
    				return 0;
    			}
    		}
    		
    		### Convert the DateTime to Seconds so you can use Perl's localtime function.
    		### Use Perl's localtime function to get the week day to verify if the date is valid.
    		$DateTime = timelocal(0,0,0, $3, $intMonth, $4);
    		($sec,$min,$hour,$day,$month,$year,$wday) = localtime($DateTime);
    		
    		### Set the WeekDay Name from the $wday(0-6) from localtime.
    		$WeekDayName = ("Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday")[$wday];
    	
    		### If the Inputted WeekDay name isn't the same as what was returned from Perl's
    		### localtime function then the input date is invalid.
    		#$strDay = $1;
    		my $strDay = ucfirst(lc($1));
    	
    		if ($strDay ne $WeekDayName) {
    			return 0;
    		}
    		
    		return 1;
    	}
    	### Sat Oct 25 14:23:17 2003
    	elsif ($InputString =~ /^($strAbbrDays) ($strAbbrMons) (\d{1,2}) (\d{2}):(\d{2}):(\d{2}) (\d{4})$/i) {
    		### The Input has a valid Date Format, now its time to Validate
    		### the inputted to date to see is a Valid Date
    		### Code to Validate the Date String
    		
    		$strTheMonth = $2;
    		foreach (@arrAbbrMonths) {
    			if ($_ eq ucfirst(lc($strTheMonth))) {
    				$intMonth = $i;
    				last;
    			}	
    			$i++;
    		}
    		
    		if ($intMonth > 11 || $intMonth < 0) { 
    			return 0;
    		}
    		
    		### If the days in the month for April, June, September, November equals 31
    		### then the input date is invalid.
    		if (($intMonth==4 || $intMonth==6 || $intMonth==9 || $intMonth==11) && $3==31) {
    			return 0;
    		}
    		
    		### If Leap Year then allow the days in the month of february to be 29, else only
    		### allow the number of days to be 28.
    		if ($intMonth == 1) {
    			if ($3>29 || ($3==29 && !($7 % 4 == 0 && ($7 % 100 != 0 || $7 % 400 == 0)))) {
    				return 0;
    			}
    		}
    		
    		if ($4>=24 || $4<0 || $5>=60 || $5<0 || $6>=60 || $6<0) {
    			return 0;
    		}
    		
    		### Convert the DateTime to Seconds so you can use Perl's localtime function.
    		### Use Perl's localtime function to get the week day to verify if the date is valid.
    		$DateTime = timelocal($6, $5, $4, $3, $intMonth, $7);
    		($sec,$min,$hour,$day,$month,$year,$wday) = localtime($DateTime);
    		
    		### Set the WeekDay Name from the $wday(0-6) from localtime.
    		$WeekDayName = ("Sun","Mon","Tue","Wed","Thu","Fri","Sat")[$wday];
    	
    		### If the Inputted WeekDay name isn't the same as what was returned from Perl's
    		### localtime function then the input date is invalid.
    		#$strDay = $1;
    		my $strDay = ucfirst(lc($1));
    	
    		if ($strDay ne $WeekDayName) {
    			return 0;
    		}
    		
    		return 1;
    	}
    	else {
    		return 0;
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
    	
    	
    	
    	
    	
    	

