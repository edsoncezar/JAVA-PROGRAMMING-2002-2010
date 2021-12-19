#!/usr/bin/perl -w
    ########################################################################
    # A Simple script that Calculates the date of Easter and shows how
    # to calculate future dates of easter.
    ########################################################################
    use POSIX;
    use strict;
    use CGI qw(:standard);
    use CGI::Carp qw(fatalsToBrowser);
    use Time::Local;
    my ($Year2, $Century, $G, $K, $I, $J, $L, $EasterMonth, $EasterDay, $p);
    my ($sec,$min,$hour,$mday,$mon,$year,$wday,$yday,$isdst) = 0;
    ($sec,$min,$hour,$mday,$mon,$year,$wday,$yday,$isdst) = localtime(time);
    my $month_name= 'jan';
    $year += 1900;
    if (param()) {
    	$year = param('y');
    }
    print header;
    print "<b>\n";
    ######################################
    # Print Date of easter This year
    ######################################
    &CalculateEaster();
    print "</b><br><br>\n";
    ######################################
    # Print the next 25 Years of Easter
    ######################################
    print "Next 25 Years<br>\n";
    for ($p = 1; $p <= 25; $p += 1) {
    	&CalculateEaster();
    }
    ######################################
    # Subroutine to Calculate the date of
    # Easter for a given year
    ######################################
    sub CalculateEaster {
    	$Year2 = $year + $p;
    	$Century = int $Year2 / 100;
    	$G = $Year2 % 19;
    	$K = int (($Century - 17) / 25);
    	$I = ($Century - int ($Century / 4) - int (($Century - $K) / 3) + 19 * $G + 15) % 30;
    	$I = $I - (int ($I / 28)) * (1 - (int ($I / 28)) * (int (29 / ($I + 1))) * (int ((21 - $G) / 11)));
    	$J = ($Year2 + int ($Year2 / 4) + $I + 2 - $Century + int ($Century / 4)) % 7;
    	$L = $I - $J;
    	
    	$EasterMonth = 3 + int (($L + 40) / 44);
    	$EasterDay = $L + 28 - 31 * (int ($EasterMonth / 4));
    	
    	$month_name = ("January", "February", "March", 
    							"April", "May", "June", "July", 
    							"August", "September", "October", 
    							"November", "December")[$EasterMonth-1];
    					
    	print "Easter is $month_name $EasterDay, $Year2 <br>\n";
    }

