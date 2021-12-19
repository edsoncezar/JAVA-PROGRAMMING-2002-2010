#!/usr/bin/perl -w
    #######################################################################
    ## This is a simple calculator script written in Perl
    ## 
    ##
    #######################################################################
    use strict;
    use CGI qw(:standard);
    use CGI::Carp qw(fatalsToBrowser);
    	
    	
    #######################################################################
    ## Path to the perl script.
    my $formname = "calculator.pl";
    #######################################################################
    	
    my $buffer='';
    my $value = '';
    my @pairs=();
    my $pair='';
    my $name='';
    my ($num1, $num2, $operator) = '';
    my %FORM=();
    	
    print "Content-type: text/html\n\n\n";
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
    	
    $num1 = $FORM{number1};
    $num2 = $FORM{number2};
    $operator = $FORM{operation};
    	
    my %opermatch = ('add','+','subtract','-','multiply','*','power','^','divide','/');
    	
    if ($FORM{status} eq 'calculate') {
    	if (ErrorCheck() == 2) {
    		my $answer = GetAnswer();
    		print <<_ENDTABLE;
    		 <BR>
    		 <div align="center"><center>
    		 <table border="0" cellspacing="1" width="25%" bgcolor="#C0C0C0">
    		 <tr>
    		 	 <th colspan="5" bgcolor="#FFFFFF">Answer</th>
    		 </tr>
    		 <tr>
    		 <td width="20%">$num1</td>
    		 <td width="20%">$opermatch{$operator}</td>
    		 <td width="20%">$num2</td>
    		 <td width="20%">=</td>
    		 <td width="20%">$answer</td>
    		 </tr>
    		 </table>
    		 </center></div>
    _ENDTABLE
    	# print "Answer is $answer";
    	}
    	drawCalc();
    }
    else { 
    	drawCalc(); 
    }
    	
    print " ";
    ### end of main body of program ###
    sub GetAnswer(){
    	my $answer;
    	if ($operator eq 'add') { 
    		$answer = $num1 + $num2; 
    	}
    	elsif ($operator eq 'subtract') { 
    		$answer = $num1 - $num2; 
    	}
    	elsif ($operator eq 'multiply') { 
    		$answer = $num1 * $num2; 
    	}
    	elsif ($operator eq 'power') { 
    		$answer = PwrFunc(); 
    	}
    	else { # default is division
    		$answer = $num1 / $num2; 
    	}
    	return $answer;
    }
    	
    sub PwrFunc()
    {
    	my $total = 1; 
    	my $count;
    	
    	if ($num2 < 0) {
    		return 0; 
    	} 
    	
    	if ($num2 == 0) { 
    		return 1; 
    	} 
    	
    	for ($count = $num2; $count > 0; $count--){ 
    		$total *= $num1; 
    	}
    	
    	return $total;
    }
    	
    sub drawCalc() {
    	 print <<__HTML__;
    	 <br><br><br>
    	 < form method="post" action="$formname">
    	 < input type="hidden" name="status" value="calculate">
    	 <div align="center"><center>
    	 <table border="0" cellspacing="1" cellpadding="0" width="40%" bgcolor="#COCOCO">
    	 	<tr>
    	 		<td>
    	 		 <table border="0" cellspacing="0" cellpadding="5" width="100%" bgcolor="#COCOCO">
    	 		 <tr>
    	 			<th colspan="4" bgcolor="#FFFFFF">Calculator</th>
    	 </tr>
    	 <tr>
    	 			<td width="25%">< input type="text" name="number1" size="5" maxlength="5"></td>
    	 			<td width="25%"><select name="operation" size="1">
    				<option selected value="add">+</option>
    				<option value="subtract">-</option>
    				<option value="multiply">*</option>
    				<option value="divide">/</option>
    				<option value="power">^</option>
    				</select></td>
    				<td width="25%">< input type="text" name="number2" size="5" maxlength="5"></td>
    				<td width="25%">< input type="submit" value="Enter"></td>
    	 		 </tr>
    	 		 </table>
    	 		</td>
    	 </tr>
    	 </table>
    	 </center></div>
    	 </form>
    __HTML__
    }
    	
    sub ErrorCheck() {
    	if ($num1 eq "" || $num2 eq "") { 
    		ErrorMsg(1); 
    		return 1; 
    	}
    	elsif ($num2 == 0 && $operator eq "divide") { 
    		ErrorMsg(2); 
    		return 1; 
    }
    elsif ($num1 < -999 || $num1 > 999 || $num2 < -999 || $num2 >999){ 
    	ErrorMsg(3); 
    	return 1; 
    	}
    	else { 
    		return 2; 
    	} #no error
    }
    	
    sub ErrorMsg() {
    	my ($errornum) = @_;
    	my @Errors = ('space filler', 'Missing operand(s)', 'Divide by Zero', 'Improper input values');
    	print "<center><br><h3>Error: $Errors[$errornum]</h3></center>";
    }
    	
    	