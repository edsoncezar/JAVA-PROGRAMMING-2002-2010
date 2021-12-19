    //**************************************
    //     
    // Name: RandomDigits
    // Description:This function returns a r
    //     andom string of a specified number of di
    //     gits without leading zeros. For example,
    //     if three digits are specified, the funct
    //     ion will return a random three-digit num
    //     ber from 100 to 999. by Michael A. Smith
    //     
    // By: PHP Code Exchange
    //**************************************
    //     
    
    <?php
    /* RandomDigit 1.0 -- Copyright 1998 by Michael A. Smith -- msmith@dn.net
    This function returns a random string of a specified number of digits
    without leading zeros. For example, if three digits are specified, the
    function will return a random three-digit number from 100 to 999. If you
    specify an eight-digit string you will not get numbers with fewer than
    eight digits, e.g. 376 or 6. */
    function randomdigit($digits) { 
    static $startseed = 0; 
    if (!$startseed) {
    $startseed = (double)microtime()*getrandmax(); 
    srand($startseed);
    }
    $range = 8;
    $start = 1;
    $i = 1;
    while ($i<$digits) {
    $range = $range . 9;
    $start = $start . 0;
    $i++;
    }
    return (rand()%$range+$start); 
    }
    ?>

