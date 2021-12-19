    //**************************************
    //     
    // Name: Random Quote
    // Description:Displays randome quotes o
    //     n a webpage.
    // By: Todd Williams
    //
    // Inputs:quotes.
    //
    // Returns:quotes.
    //
    //This code is copyrighted and has    // limited warranties.Please see http://
    //     www.Planet-Source-Code.com/xq/ASP/txtCod
    //     eId.313/lngWId.8/qx/vb/scripts/ShowCode.
    //     htm    //for details.    //**************************************
    //     
    
    //QUOTE SCRIPT
    <?php
    srand((double)microtime()*1000000);
    $quotelist = file("quote.txt");
    $nquote = sizeof($quotelist);
    $rquote = rand(0,$nquote-1);
    $quote = $tiplist[$rquote];
    echo $quote;
    ?> 
    //QUOTE TEXT (quote.txt)
    This is quote 1.
    This is quote 2.
    ...
    ...
