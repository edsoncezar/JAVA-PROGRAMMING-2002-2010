 
    #!/usr/bin/perl -w
    ##############################################################
    ## This is a simple script that shows how to 
    ## create a Replace Subroutine and call it from
    ## a script more easily by supplying 3 parameters
    ## EX: &Replace($FullString, $SearchThis, $ReplaceWithThis);
    ##############################################################
    use strict;
    	
    my $strString = '';
    my $strSearch = '';
    my $strReplace = '';
    my $strFinal = '';
    	
    print 'Enter a string: ';
    chomp ($strString = <STDIN>);
    	
    print 'Enter a Search: ';
    chomp ($strSearch = <STDIN>);
    	
    print 'Enter a Replace: ';
    chomp ($strReplace = <STDIN>);
    	
    $strFinal = &Replace($strString, $strSearch, $strReplace);
    print "$strFinal\n";
    	
    sub Replace {
    	my $strString = shift;
    	my $strSearch = shift;
    	my $strReplace = shift;
    	$strString =~ s/$strSearch/$strReplace/ge;
    	return $strString;
    }
    	
    	
    	
    	
    	
    	
    	

