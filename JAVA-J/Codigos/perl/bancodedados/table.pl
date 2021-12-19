  #!/usr/bin/perl -w
    ###################################################
    ## This is a simple script that will create a 
    ## new table in a Access Database. This is to show
    ## how to connect to an Access Database thru Perl
    ## by using DBI and the ADO driver. You can also
    ## connect by using ODBC, by replacing where you 
    ## ADO. dbi:ADO:$DSN
    ###################################################
    use POSIX;
    use strict;
    use CGI qw(:standard);
    use CGI::Carp qw(fatalsToBrowser);
    use DBI;
    use Time::Local
    print header; 
    	
    ######################################
    ## Set DSN-Less Connection
    ######################################
    my $DSN = 'driver=Microsoft Access Driver (*.mdb);dbq=C:\WINDOWS\perl.mdb';
    my $dbh = DBI->connect("dbi:ADO:$DSN", '','') 
    	or die "$DBI::errstr\n"; 
    		
    ######################################
    ## Generate the SQL Statement
    ######################################
    my $sql = <<"EndOfSQL";
    CREATE TABLE tblContacts2 (
    	ID				COUNTER,
    	LastName		CHAR(40),
    	FirstName	CHAR(40),
    	MiddleName CHAR(20);
    	HomePhone	CHAR(40),
    	WorkPhone	CHAR(40),
    	CellPhone CHAR(40);
    	BirthDay CHAR(20);
    	Fax			CHAR(40),
    	Email			CHAR(40),
    	Address1		CHAR(40),
    	Address2		CHAR(40),
    	City			CHAR(30),
    	State			CHAR(10),
    	ZipCode		CHAR(20),
    CONSTRAINT ID_PK PRIMARY KEY(ID)	
    )
    EndOfSQL
    	
    	
    ################################
    ## Execute the SQL Statement
    ################################
    $dbh->do($sql) 
    	or die "Execution problem: $DBI::errstr";
    print "Table was Created";
    	
    ######################################
    ## Close the connection when finished:
    ######################################
    $dbh->disconnect;
    	
    	
    	
    	