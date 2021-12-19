=**************************************
    = Name: Lotto Numbers
    = Description:Genarates Numbers for the 
    =     Lotto
    = By: Damian myerscough
    =
    = Inputs:Non
    =
    = Returns:Winning Numbers ;)
    =
    =This code is copyrighted and has    = limited warranties.Please see http://w
    =     ww.Planet-Source-Code.com/vb/scripts/Sho
    =     wCode.asp?txtCodeId=512&lngWId=6    =for details.    =**************************************
    
    #!/usr/bin/perl -w
    #
    # Lotto Numbers
    #
    #
    @Numbers = 1..49;
    print"The Lotto Number are.\n";
    for($i = 1; $i <= 6; $i++)
    {
    $Damo = int(rand(@Numbers));
    print"The $i Ball is: $Damo \n";
    }

