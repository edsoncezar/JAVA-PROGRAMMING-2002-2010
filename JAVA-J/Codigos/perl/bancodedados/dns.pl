 #!/usr/bin/perl -w
    #################################################
    ## This is an Example of how to Connect to an
    ## Access Database using a DSN-less Connection
    #################################################
    use POSIX;
    use strict;
    use CGI qw(:standard);
    use CGI::Carp qw(fatalsToBrowser);
    use DBI;
    print header;
    	
    #########################################
    ## Create the Connection String 
    ## To use a DSN just replace the string
    ## with the DSN name.
    #########################################
    my $DSN = 'driver=Microsoft Access Driver (*.mdb);dbq=C:\Perl\DBI\perl.mdb';
    my $dbh = DBI->connect("dbi:ODBC:$DSN", '','') 
    	or die "$DBI::errstr\n"; 
    	
    ###############################
    ## Generate SQL Statement
    ###############################	
    my $sql = <<"EndOfSQL";
    SELECT
    ID, LastName, FirstName
    FROM
    tblContacts
    WHERE
    ID = 1
    EndOfSQL
    	
    ###############################
    ## Prepare and execute the 
    ## SQL Statement
    ###############################
    my $loadHandle = $dbh->prepare($sql);
    $loadHandle->execute;
    
    #########################################
    ## Close the connection when finished:
    #########################################
    $dbh->disconnect;
    	
    	
    	
    	

