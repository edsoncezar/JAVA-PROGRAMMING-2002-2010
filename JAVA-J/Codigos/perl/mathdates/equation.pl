  #!C:\perl\bin\perl
    if(@ARGV[0] eq "")
    {
    		print ("\n");
    		print ("syntax: perl equation.pl 'equation' \n");
    		print ("'equation' must be in form letter/number/operator letter/number/operator \n");
    		print ("eg for 4x + y syntax would be perl equation.pl 4 x + y \n");
    }
    else
    {
    	print ("@ARGV \n");
    	print ("specify a file to save as: ");
    	$filename = <STDIN>;
    	$COUNT = 0;
    	open(OUTFILE, ">file.pl");
    	print OUTFILE ('#!C:\perl\bin\perl');
    	print OUTFILE ("\n");
    	print OUTFILE ('# ');
    	print OUTFILE ("$file Created by equation.pl automatic perl script generator \n\n\n");
    	
    	$break = 0;
    	$varcount = 0;
    	while (@ARGV[$COUNT] ne "" && $break != 1)
    	{
    		if (@ARGV[$COUNT] =~ /[a-z A-Z]/)
    		{
    			print ("letter\n");
    			print OUTFILE ('print ("');
    			print OUTFILE ("@ARGV[$COUNT] input:");
    			print OUTFILE (' ");');
    			print OUTFILE ("\n");
    			print OUTFILE ('$');
    			print OUTFILE ("@ARGV[$COUNT] = ");
    			print OUTFILE ('<STDIN>;');
    			print OUTFILE ("\n");
    			$varcount++;
    	
    		}
    		elsif( @ARGV[$COUNT] =~ /\d/)
    		{
    			print ("digit\n");
    		}
    		elsif ( @ARGV[$COUNT] eq "+" || @ARGV[$COUNT] eq "-" || @ARGV[$COUNT] eq "*" || @ARGV[$COUNT] eq "%" || @ARGV[$COUNT] eq "/" || @ARGV[$COUNT] eq "^")
    		{
    			if ($COUNT ==0)
    			{
    				$break = 1;
    				print ("error! math operator can not begin equation\n");
    			}
    		
    			else{}
    			print ("math operator\n");
    		}
    		
    			
    		else
    		{
    			print ("error\n");
    		}
    	$COUNT++;
    	}
    	print OUTFILE ('$result = ');
    	$COUNT = 0;
    	while (@ARGV[$COUNT] ne "" && $break != 1)
    	{
    		if (@ARGV[$COUNT] =~ /[a-z A-Z]/)
    		{
    			print OUTFILE ('$');
    			print OUTFILE ("@ARGV[$COUNT] ");
    		}
    		elsif (@ARGV[$COUNT] eq "+")
    		{
    			print OUTFILE (" + ");
    		}
    		elsif (@ARGV[$COUNT] eq "*")
    		{
    			print OUTFILE (" * ");
    		}
    		
    		elsif (@ARGV[$COUNT] eq "-")
    		{
    			print OUTFILE (" - ");
    		}
    		elsif (@ARGV[$COUNT] eq "/")
    		{
    			print OUTFILE (' / ');
    		}
    		
    		elsif( @ARGV[$COUNT] =~ /\d/)
    		{
    			print OUTFILE ("@ARGV[$COUNT] ");
    		}
    	
    		elsif (@ARGV[$COUNT] eq "(" )
    		{
    			print OUTFILE (' ( ');
    		}
    		elsif (@ARGV[$COUNT] eq ")" )
    		{
    			print OUTFILE (' ) ');
    		}
    			
    		elsif (@ARGV[$COUNT] eq "^")
    		{
    			print OUTFILE (' ** ');
    		}	
    	
    		elsif (@ARGV[$COUNT] eq "%")
    		{
    			print OUTFILE (' % ');
    		}
    	
    		else {}
    		$COUNT++;
    	}
    	print OUTFILE ("; \n");
    	print OUTFILE ('print ("result is $result \n");');
    	print OUTFILE ("\n");
    }

