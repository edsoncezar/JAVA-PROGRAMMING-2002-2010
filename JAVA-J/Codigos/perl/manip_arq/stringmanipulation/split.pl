 #!/usr/bin/perl -w
    ######################################################
    ## This is a simple script that shows how to make
    ## a simple Split subroutine that can be called
    ## more easy from a script by just supplying 2
    ## parameters. EX: &Split($fullString, $splitString);
    ######################################################
    use strict;
    	
    my $strString = '';
    my $strSplit = '';
    my @strFinal = ();
    	
    print 'Enter a string: ';
    chomp ($strString = <STDIN>);
    	
    print 'Enter a string to Split on: ';
    chomp ($strSplit = <STDIN>);
    	
    @strFinal = &Split($strString, $strSplit);
    foreach(@strFinal) {
    	print "$_\n";	
    }
    	
    ###########################################
    ## Subroutine to a string based on another
    ## Character or Character String
    ###########################################
    sub Split {
    	my $strString = shift;
    	my $strSplit = shift;
    	my @words = ();
    	
    	@words = split /$strSplit/, $strString;
    	return @words;
    }
    	