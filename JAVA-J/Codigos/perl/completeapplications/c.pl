
   
    #!/usr/bin/perl -w
    #
    # The Username can Be your name BUT THE PASSWD is: Damo
    #
    system("clear");
    print"Welcome To Damian Myerscough Data Base..\n";
    sleep(2);
    print"Please Enter Your Name: ";
    $Name = <STDIN>;
    chop($Name);
    print"Please Enter your Password: ";
    $Damo = <STDIN>;
    chop($Damo);
    $TimeDate = localtime();
    $Encryption = crypt($Damo, "123456789ABCDEF");
    print"Encryption Done Testing match...\n";
    if ($Encryption eq '12JdzCuGFwA5o'){
    main();
    }else{
    for(open(LOGFILE, ">>login.log")){
    print LOGFILE ("+============================================================+\n");
    print LOGFILE ("Name of Person who tryed to login: $Name \n");
    print LOGFILE ("The Time and Date was: $TimeDate \n");
    print LOGFILE ("+============================================================+\n\n\n");
    close(LOGFILE);
    print"Login Failed..\n";
    }
    }
    sub main(){
    for(open(LOGFILE, ">>login.log")){
    print LOGFILE ("+============================================================+\n");
    print LOGFILE ("Sucessfull login by: $Name\n");
    print LOGFILE ("The Time and Date was: $TimeDate \n");
    print LOGFILE ("+============================================================+\n\n\n");
    close(LOGFILE);
    }
    system("clear");
    print"Welcome $Name to This DataBase\n\n\n";
    print"Main Menu\n";
    print"=========\n\n";
    print"1) Add a Car To The DataBase.\n";
    print"2) Add a Address to The DataBase. \n";
    print"3) Add ID to the Database. \n";
    print"4) Read List of Cars. \n";
    print"5) Read The ID's. \n";
    print"6) Read addresses. \n";
    print"7) Exit. \n\n";
    
    print"Enter the Number you would like to view: ";
    $NUMBER = <STDIN>;
    chop($NUMBER);
    if($NUMBER == 1){
    One();
    }elsif($NUMBER == 2){
    Two();
    }elsif($NUMBER == 3){
    three();
    }elsif($NUMBER == 4){
    four();
    }elsif($NUMBER == 5){
    five();
    }elsif($NUMBER == 6){
    six();
    }elsif($NUMBER == 7){
    print"Thanks For trying out my DataBase..\n";
    }else{
    print"Command uknown..\n";
    sleep(2);
    main();
    } 
    }
    sub One(){
    print"Car DataBase.\n\n";
    sleep(1);
    $Time22 = localtime();
    print"Please Enter your type of Car: ";
    $CarName = <STDIN>;
    chop($CarName);
    print"What Year was your car Made: ";
    $CarAge = <STDIN>;
    chop($CarAge);
    print"How many Miles has it done: ";
    $CarMiles = <STDIN>;
    chop($CarMiles);
    print"Enter a ONE line comment about the car: ";
    $WhatCar = <STDIN>;
    chop($WhatCar);
    
    for(open(LOGFILE, ">>car.log")){
    print LOGFILE ("+=========================================================================+\n");
    print LOGFILE ("The Car Type is: $CarName \n");
    print LOGFILE ("The Car was Made in: $CarAge \n");
    print LOGFILE ("The Car Has Done: $CarMiles miles \n"); 
    print LOGFILE ("This Car was added at: $Time22 \n");
    print LOGFILE ("Comment about the car: $WhatCar \n");
    print LOGFILE ("+=========================================================================+\n\n");
    close(LOGFILE);
    print"Added Information to car.log \n";
    sleep(2);
    main();
    }
    }
    sub Two(){
    print"Address DataBase.\n";
    print"Enter Your street: ";
    $Street = <STDIN>;
    chop($Street);
    print"Enter your house Number: ";
    $HouseNumber = <STDIN>;
    chop($HouseNumber);
    print"Enter zip/post code: ";
    $ZipCode = <STDIN>;
    chop($ZipCode);
    print"Enter your Phone Number: ";
    $Phone = <STDIN>;
    chop($Phone);
    print"Enter your full Name: ";
    $MrName = <STDIN>;
    chop($MrName);
    for(open(LOGFILE, ">>address.log")){
    print LOGFILE ("+========================================================+\n");
    print LOGFILE ("The Street is: $Street \n");
    print LOGFILE ("The House Number is: $HouseNumber \n");
    print LOGFILE ("The zip/postal code is: $ZipCode \n");
    print LOGFILE ("There Phone Number is: $Phone \n");
    print LOGFILE ("There Name is: $MrName \n");
    print LOGFILE ("+========================================================+\n\n");
    close(LOGFILE);
    print"Adding information to address.log \n";
    sleep(2);
    main();
    }
    }
    sub three(){
    print"Add ID to the DataBase.\n\n";
    sleep(1);
    print"Enter There Name: ";
    $IDNAME = <STDIN>;
    chop($IDNAME);
    print"Enter Credit Card Number: ";
    $IDCREDIT = <STDIN>;
    chop($IDCREDIT);
    print"Enter Phone Number: ";
    $IDPHONE = <STDIN>;
    chop($IDPHONE);
    print"Enter Were your street: ";
    $IDSTREET = <STDIN>;
    chop($IDSTREET);
    print"Enter your zip\postal code: ";
    $IDPOST = <STDIN>;
    chop($IDPOST); 
    for(open(LOGFILE, ">>id.log")){
    print LOGFILE ("+============================================================+\n");
    print LOGFILE ("Name is: $IDNAME \n");
    print LOGFILE ("Credit card is: $IDCREDIT \n");
    print LOGFILE ("Phone Number is: $IDPHONE \n");
    print LOGFILE ("There Street is: $IDSTREET \n");
    print LOGFILE ("There zip/postal code is: $IDPOST \n");
    print LOGFILE ("+============================================================+\n\n");
    close(LOGFILE);
    print"Adding information to id.log \n";
    sleep(2);
    main();
    }
    }
    sub four(){
    print"Read car list.\n";
    sleep(1);
    open(LOGFILE, "car.log") || die "Could Not open file.\n";
    @Login_read = <LOGFILE>;
    print"@Login_read \n";
    
    }
    sub five(){
    print"Reading ID's.\n";
    sleep(1);
    open(LOGFILE, "id.log") || die "Could not open file.\n";
    @Array_Read = <LOGFILE>;
    print"@Array_Read \n";
    }
    sub six(){
    print"Reading addresses.\n";
    sleep(1);
    open(LOGFILE, "address.log") || die "Could Not open file.\n";
    @Array_Text2 = <LOGFILE>;
    print"@Array_Text2 \n";
    }

