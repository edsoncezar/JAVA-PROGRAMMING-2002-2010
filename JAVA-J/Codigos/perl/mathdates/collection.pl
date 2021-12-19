 <input type="hidden" name="status" value="allfactors"><div align="center"><center><table
    border="0" cellpadding="12" cellspacing="1" width="80%">
    <tr>
    <td width="76%"><div align="center"><center><h3>List all factors of a number</h3>
    </center></div><div align="center"><center><p>Number to factor  <input type="text"
    name="num" size="10"></td>
    <td width="24%" align="center"><input TYPE="submit" VALUE="Compute"> <input type="reset"
    value="Reset"></td>
    </tr>
    </table>
    </center></div>
    </form>
    <form METHOD="POST" ACTION="math.pl">
    <input type="hidden" name="status" value="lcm"><div align="center"><center><table
    border="0" cellpadding="12" cellspacing="1" width="80%">
    <tr>
    <td width="76%"><div align="center"><center><h3>Find the lcm of 2 numbers</h3>
    </center></div><div align="center"><center><p>First number  <input type="text"
    name="num1" size="10"></p>
    </center></div><div align="center"><center><p>Second number  <input type="text"
    name="num2" size="10"></td>
    <td width="24%" align="center"><input TYPE="submit" VALUE="Compute"> <input type="reset"
    value="Reset"></td>
    </tr>
    </table>
    </center></div>
    </form>
    <form METHOD="POST" ACTION="math.pl">
    <input type="hidden" name="status" value="prime"><div align="center"><center><table
    border="0" cellpadding="12" cellspacing="1" width="80%">
    <tr>
    <td width="76%" align="center"><div align="center"><center><h3>Check whether or not a
    number is prime</h3>
    </center></div><div align="center"><center><p>Number to check  <input type="text"
    name="num" size="10"></td>
    <td width="24%" align="center"><input TYPE="submit" VALUE="Compute"> <input type="reset"
    value="Reset"></td>
    </tr>
    </table>
    </center></div>
    </form>
    <form METHOD="POST" ACTION="math.pl">
    <input type="hidden" name="status" value="fibonacci"><div align="center"><center><table
    border="0" cellpadding="12" cellspacing="1" width="80%">
    <tr>
    <td width="76%" align="center"><div align="center"><center><h3>List  numbers of the
    Fibonacci sequence</h3>
    </center></div><div align="center"><center><p>Number of elements to list   <input
    type="text" name="num" size="10"></td>
    <td width="24%" align="center"><input TYPE="submit" VALUE="Compute"> <input type="reset"
    value="Reset"></td>
    </tr>
    </table>
    </center></div>
    </form>
    =
    =This code is copyrighted and has    = limited warranties.Please see http://w
    =     ww.Planet-Source-Code.com/vb/scripts/Sho
    =     wCode.asp?txtCodeId=509&lngWId=6    =for details.    =**************************************
    
    #!/usr/bin/perl -w
    #######################################################################
    ## 
    #######################################################################
    	
    use strict;
    use CGI qw(:standard);
    use CGI::Carp qw(fatalsToBrowser);
    print "HTTP/1.0 200 OK\n";
    print "Content-type: text/html\n\n";
    	
    		
    #######################################################################
    ## User configurable variables start here
    ## When lists are made, alternating colors looks nice
    ## so $color1 and $color2 are used for this
    my $color1 = "\#0000FF";
    my $color2 = "\#CC3210";
    #######################################################################
    my ($value, $name, $pair,$buffer, $FORM, $action, $z)='';
    my @pairs=();
    my %FORM=();
    	
    if ($ENV{'REQUEST_METHOD'} eq "POST") {
    	read(STDIN, $buffer, $ENV{'CONTENT_LENGTH'});
    }
    	
    @pairs = split(/&/, $buffer);
    foreach $pair (@pairs) {
    	($name, $value) = split(/=/, $pair);
    	$value =~ tr/+/ /;
    	$value =~ s/%([a-fA-F0-9][a-fA-F0-9])/pack("c", hex($1))/eg;
    	$value =~ s/^( +)//;
    	$value =~ s/( +)$//;
    	$value =~ s/(\t|\r|\n)//g;
    	$FORM{$name} = $value;
    } # end of foreach
    	
    $action = $FORM{status};
    #print " $action";
    	
    if ($action eq 'allfactors'){ 
    	getAllfactors(); 
    }
    elsif ($action eq 'lcm'){ 
    	getLcm(); 
    }
    elsif ($action eq 'prime'){
    getPrime(); 
    }
    else { 
    	if ($action eq 'fibonacci') {
    		getFibonacci(); 
    	} 
    }
    	
    	
    ##########################################
    ## Sub: getAllfactors
    ## Description: This sub gets all Factors 
    ## for a Number
    ##########################################
    sub getAllfactors() {
    	my $z = 0;
    	my $x = $FORM{num};
    	my $y = $x;
    	my $clr;
    	
    	print "<br><center>";
    	print "<h3><u>All factors of $x</u></h3><br>";
    	while ($y > 0) {
    		if ($x % $y == 0) {
    			if ($z % 2 == 0){ 
    				$clr = $color1; 
    			}
    			else { 
    				$clr = $color2; 
    			}
    			
    	 		print "<font color=\"$clr\"> $y </font>";
    	 		$z++ ;
    	 		
    	 		if ($z % 5 == 0){ 
    	 			print "<BR>"; 
    	 		}
    	 	}
    		$y-- ;
    	}
    	print "</center>";
    }
    	
    	
    ##########################################
    ## Sub: getLcm
    ## Description: This sub gets the lcm
    ## between 2 numbers
    ##########################################
    sub getLcm() {
    	my $x = $FORM{num1};
    	my $y = $FORM{num2};
    	my $temp,$z;
    	
    	print "<br><center>";
    	print "<h3>The lcm of <font color=\"$color1\"> $x </font> and <font color=\"$color1\"> $y </font> is ";
    	
    	if ($x < $y) {
    		$temp = $y;
    		$y = $x;
    		$x = $temp;
    	}
    	
    	$z = $y;
    	$temp = 1;
    	while ($z % $x) {
    		$z = $y * $temp;
    		$temp++ ;
    	}
    	
    	print "<font color=\"$color2\"> $z </font></h3></center>";
    }
    	
    	
    ##########################################
    ## Sub: getPrime
    ## Description: This sub lets you know if
    ## a number is a prime or not.
    ##########################################
    sub getPrime() {
    	use integer;
    	
    	my $x = $FORM{num};
    	my $answer;
    	
    	print "<br><center>";
    	print "<h3>The number <font color=\"$color1\"> $x </font> is ";
    	
    	if ($x < 2) {
    		print "<font color=\"$color2\"> not prime </font></h3></center>";
    	}
    	else {
    		my $y = $x/2;
    		my $flag = 0; ## flag is 0 until num is not prime
    		
    		while ($y > 1 && $flag == 0) {
    		if ($x % $y == 0) { 
    			$flag++ ; 
    		}
    		$y-- ;
    	 	}
    	 	
    		if ($flag){ 
    			print "<font color=\"$color2\"> not prime</font></h3></center>"; 
    		}
    		else { 
    			print "<font color=\"$color2\"> prime </font></h3></center>"; 
    		}
    	}
    }
    	
    	
    ##########################################
    ## Sub: getFibonacci
    ## Description: This sub gets the first 
    ## (number) Fibonacci Numbers.
    ##########################################
    sub getFibonacci() {
    	my $x = 0;
    	my $y = 1;
    	my $limit = $FORM{num};
    	my $count = 0;
    	my $temp;
    	my $clr;
    	
    	print "<br><center>";
    	print "<h3><u>The first $limit Fibonacci numbers</u></h3><br>";
    	
    	print "<table cellpadding=\"5\"><tr>";
    	while ($limit > $count) {
    		$count++ ;
    		if ($count % 2 == 0){ 
    			$clr = $color1; 
    		}
    		else { 
    			$clr = $color2; 
    		}
    		print "<td><font color=\"$clr\">$y</font></td>";
    		if ($count % 5 == 0) { 
    		print "</tr><tr>"; 
    	}
    		$temp = $y;
    		$y += $x;
    		$x = $temp;
    	}
    	print "</tr></table></center>";
    }
    ### End of file ###
    	