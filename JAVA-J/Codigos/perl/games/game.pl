 #!C:\Perl\bin\perl.exe
    use CGI qw(:standard);
    		print header;
    		print start_html('Gamble'),
    		h1('Even/Odd Gamble'),
    		h3('Pick even Or Odd'),
    		start_form,
    		radio_group(
    -name => 'evodd',
    -values => ['Even','Odd'],
    -linebreak => 'true'),
    		p,
    		submit,
    		end_form,
    		hr;
    		if (param())
    		{
    			$guess = param('evodd');
    			$number = int(rand(50));
    			if ($number % 2)
    			{
    				print qq!
    				number is odd!;
    				if ( $guess eq "Odd" ) 
    				{
    					print "<br>You Were Right!";
    				}
    				else 
    				{
    					print "<br>You Were Wrong";
    				}		
    			}		
    			else 
    			{
    				print qq!
    				number is even!;
    				if ( $guess eq "Even" ) 
    				{
    					print "<br>You Were Right!";
    				}
    				else 
    				{
    					print "<br>You Were Wrong";
    				}
    		}	
    	}
    		

 