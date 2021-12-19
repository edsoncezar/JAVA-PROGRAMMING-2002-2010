 #!/usr/bin/perl -w
    #
    # Coded By Damian Myerscough
    #
    #
    print"Welcome To My Phone Book.\n\n";
    sleep(2);
    main();
    sub main{
    system("clear");
    print"1) Add Phone Number\n2) Add Address\n3) Exit\n4) Make a new Phone Book.\n\n";
    print"Enter: ";
    $Select = <STDIN>;
    chop($Select);
    
    if($Select == 1){
    print"Please Enter There Name: ";
    $Name = <STDIN>;
    chop($Name);
    print"Enter There Phone Number: ";
    $Number = <STDIN>;
    chop($Number);
    print"Adding To Phone Book..\n";
    sleep(1);
    if(open(LOGFILE, ">>phone.log")){
    print LOGFILE ("+=========================================+\n");
    print LOGFILE ("Name: $Name \n");
    print LOGFILE ("Phone Number: $Number\n");
    print LOGFILE ("+=========================================+\n\n\n");
    close(LOGFILE); 
    print("Added Information.\n");
    sleep(2); 
    main();
    }
    }elsif($Select == 4){
    print"You only Need to Make this once..\n";
    system("echo >> phone.log");
    }elsif($Select == 2){
    print"Please Enter There Name: ";
    $Name2 = <STDIN>;
    chop($Name2);
    print"Enter There House Number: ";
    $HouseNumber = <STDIN>;
    chop($HouseNumber); 
    print"Enter There Phone Number: ";
    $PhoneNumber2 = <STDIN>;
    chop($PhoneNumber2);
    print"Enter There Town/City: ";
    $TownCity = <STDIN>;
    chop($TownCity);
    if (open(LOGFILE, ">>phone.log")){
    print LOGFILE ("+=========================================+\n");
    print LOGFILE ("Name: $Name2 \n");
    print LOGFILE ("House Number: $HouseNumber \n");
    print LOGFILE ("Phone Number: $PhoneNumber2 \n");
    print LOGFILE ("Town/City: $TownCity \n\n");
    print LOGFILE ("+=========================================+\n\n");
    close(LOGFILE)
    }
    print"Adding Information..\n";
    sleep(1);
    print"Information Added.\n"; 
    sleep(1);
    main();
    }elsif($Select == 3){
    print"Exiting..\n";
    sleep(1);
    }
    }

