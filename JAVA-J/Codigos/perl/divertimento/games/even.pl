 =**************************************
    = Name: Even Odd Guessing Game
    = Description:If you have seen other scr
    =     ipts by me, you will have noticed that i
    =     like to make games. This Script is a Eve
    =     n Or Odd Guessing game, it is quite simp
    =     le. Once again, i hope you enjoy!
    = By: Jason DeLuca
    =
    =This code is copyrighted and has    = limited warranties.Please see http://w
    =     ww.Planet-Source-Code.com/vb/scripts/Sho
    =     wCode.asp?txtCodeId=382&lngWId=6    =for details.    =**************************************
    
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
    		

