  #!/usr/bin/perl -w
    ###############################################
    # This script is a number spelling script.
    # it will spell out numbers that are entered.
    # The range of numbers is 0 - 10,000
    ###############################################
    	
    $num = 0; # Raw number
    $exit = ""; # Exit program
    %numbers = (
    	0 => 'Zero',
    	1 => 'One',
    	2 => 'Two',
    	3 => 'Three',
    	4 => 'Four',
    	5 => 'Five',
    	6 => 'Six',
    	7 => 'Seven',
    	8 => 'Eight',
    	9 => 'Nine',
    	10 => 'Ten',
    	11 => 'Eleven',
    	12 => 'Twelve',
    	13 => 'Thirteen',
    	14 => 'Fourteen',
    	15 => 'Fifteen',
    	16 => 'Sixteen',
    	17 => 'Seventeen',
    	18 => 'Eighteen',
    	19 => 'Nineteen',
    	20 => 'Twenty',
    	30 => 'Thirty',
    	40 => 'Forty',
    	50 => 'Fifty',
    	60 => 'Sixty',
    	70 => 'Seventy',
    	80 => 'Eighty',
    	90 => 'Ninety',
    	100 => 'Hundred',
    	1000 => 'Thousand',
    );
    	
    while ($exit ne "n") {
    	while () {
    		print 'Enter the number you want to spell: ';
    		chomp($num = <STDIN>);
    		if ($num !~/\d/ or $num =~/\D/) {
    			print "No strings. 0 through 10000 please.\n";
    			next;
    		}
    		if ($num > 10000) {
    			print "Too big. 0 through 10000 please.\n";
    			next;
    		}
    		if ($num < 0) {
    			print "No negative numbers. 0 through 10000 please.\n";
    			next;
    		}		
    		last;
    	}
    	
    	print "Number $num is: ";
    	
    	if ($num >= 1000 && $num <= 10000) {
    		&SpellThousands();
    	}
    	elsif ($num > 100) {
    		&SpellHundreds();
    	}
    	elsif ($num > 20) {
    		&SpellTens();
    	}
    	else {
    		print $numbers{$num};
    	}
    	
    	print "\n";
    	
    	while () {
    		print "\n";
    		print 'Try another number (y/n)?: ';
    		chomp ($exit = <STDIN>);
    		$exit = lc $exit;
    		if ($exit ne 'y' && $exit ne 'n') {
    			print "y or n, please\n";
    		}
    		else {
    			last;
    		}
    	}	
    }	
    	
    	
    ###############################################
    # Subroutine to Spell out the tens digit
    # spells number from 10 To 90
    ###############################################
    sub SpellTens {
    	if ($num > 20 && $num < 30) {
    		$num = $num - 20;
    		print "$numbers{20}-";
    		print $numbers{$num};
    	}
    	elsif ($num > 30 && $num < 40) {
    		$num = $num - 30;
    		print "$numbers{30}-";
    		print $numbers{$num};
    	}
    	elsif ($num > 40 && $num < 50) {
    		$num = $num - 40;
    		print "$numbers{40}-";
    		print $numbers{$num};
    	}
    	elsif ($num > 50 && $num < 60) {
    		$num = $num - 50;
    		print "$numbers{50}-";
    		print $numbers{$num };
    	}
    	elsif ($num > 60 && $num < 70) {
    		$num = $num - 60;
    		print "$numbers{60}-";
    		print $numbers{$num};
    	}
    	elsif ($num > 70 && $num < 80) {
    		$num = $num - 70;
    		print "$numbers{70}-";
    		print $numbers{$num};
    	}
    	elsif ($num > 80 && $num < 90) {
    		$num = $num - 80;
    		print "$numbers{80}-";
    		print $numbers{$num};
    	}
    	elsif ($num > 90 && $num < 100) {
    		$num = $num - 90;
    		print "$numbers{90}-";
    		print $numbers{$num};
    	}
    	elsif ($num > 0) {
    		print $numbers{$num};
    	}
    }
    	
    	
    ###############################################
    # Subroutine to Spell out the hundreds digit
    # spells number from 100 To 900
    ###############################################
    sub SpellHundreds {
    	if ($num >= 100 && $num < 200) {
    		$num = $num - 100;
    		print "$numbers{1} $numbers{100} ";
    		&SpellTens();
    	}
    	elsif ($num >= 200 && $num < 300) {
    		$num = $num - 200;
    		print "$numbers{2} $numbers{100} ";
    		&SpellTens();
    	}
    	elsif ($num >= 300 && $num < 400) {
    		$num = $num - 300;
    		print "$numbers{2} $numbers{100} ";
    		&SpellTens();
    	}
    	elsif ($num >= 400 && $num < 500) {
    		$num = $num - 400;
    		print "$numbers{4} $numbers{100} ";
    		&SpellTens();
    	}
    	elsif ($num >= 500 && $num < 600) {
    		$num = $num - 500;
    		print "$numbers{5} $numbers{100} ";
    		&SpellTens();
    	}
    	elsif ($num >= 600 && $num < 700) {
    		$num = $num - 600;
    		print "$numbers{6} $numbers{100} ";
    		&SpellTens();
    	}
    	elsif ($num >= 700 && $num < 800) {
    		$num = $num - 700;
    		print "$numbers{7} $numbers{100} ";
    		&SpellTens();
    	}
    	elsif ($num >= 800 && $num < 900) {
    		$num = $num - 800;
    		print "$numbers{8} $numbers{100} ";
    		&SpellTens();
    	}
    	elsif ($num >= 900 && $num < 1000) {
    		$num = $num - 900;
    		print "$numbers{9} $numbers{100} ";
    		&SpellTens();
    	}
    	elsif ($num > 0) {
    		print $numbers{$num};
    	}	
    }
    	
    	
    ###############################################
    # Subroutine to Spell out the thousands digit
    # spells number from 1,000 To 10,000
    ###############################################
    sub SpellThousands {
    	if ($num >= 1000 && $num < 2000) {
    		$num = $num - 1000;
    		print "$numbers{1} $numbers{1000} ";
    		&SpellHundreds();
    	}
    	elsif ($num >= 2000 && $num < 3000) {
    		$num = $num - 2000;
    		print "$numbers{2} $numbers{1000} ";
    		&SpellHundreds();
    	}
    	elsif ($num >= 3000 && $num < 4000) {
    		$num = $num - 3000;
    		print "$numbers{2} $numbers{1000} ";
    		&SpellHundreds();
    	}
    	elsif ($num >= 4000 && $num < 5000) {
    		$num = $num - 4000;
    		print "$numbers{4} $numbers{1000} ";
    		&SpellHundreds();
    	}
    	elsif ($num >= 5000 && $num < 6000) {
    		$num = $num - 5000;
    		print "$numbers{5} $numbers{1000} ";
    		&SpellHundreds();
    	}
    	elsif ($num >= 6000 && $num < 7000) {
    		$num = $num - 6000;
    		print "$numbers{6} $numbers{1000} ";
    		&SpellHundreds();
    	}
    	elsif ($num >= 7000 && $num < 8000) {
    		$num = $num - 7000;
    		print "$numbers{7} $numbers{1000} ";
    		&SpellHundreds();
    	}
    	elsif ($num >= 8000 && $num < 9000) {
    		$num = $num - 8000;
    		print "$numbers{8} $numbers{1000} ";
    		&SpellHundreds();
    	}
    	elsif ($num >= 9000 && $num < 10000) {
    		$num = $num - 9000;
    		print "$numbers{9} $numbers{1000} ";
    		&SpellHundreds();
    	}
    	elsif ($num >= 10000 && $num < 11000) {
    		$num = $num - 10000;
    		print "$numbers{10} $numbers{1000} ";
    		&SpellHundreds();
    	}
    	elsif ($num > 0) {
    		print $numbers{$num};
    	}		
    }
    	
    	
    	
    	