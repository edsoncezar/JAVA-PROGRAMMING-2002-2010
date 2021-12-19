  #!/bin/perl
    use Mysql;
    use strict;
    	
    	
    my host = '';## MySQL Host
    my $user = ''## Your username
    my $password = ''; ## Your password
    my $database = ''; ## Your Database name you want
    	
    my $dbh = Msql->connect($host, undef, $user, $password);
    	
    ### Create database.
    my $rc = $dbh->createdb($database);
    $dbh->selectdb($database);
    	
    	
    my $sql_statement = <<"EndOfSQL";
    CREATE TABLE TableContacts (
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
    	
    my $sth = $dbh->query($sql_statement);
    	
    	
    	
    	
    	
    	
    	

