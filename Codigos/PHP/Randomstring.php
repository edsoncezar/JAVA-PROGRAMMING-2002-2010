    //**************************************
    //     
    // Name: Randomstring
    // Description:Generate a random string 
    //     of n characters by Christopher Heschong
    // By: PHP Code Exchange
    //**************************************
    //     
    
    <?
    Function randomstring($len) {
    srand(date("s"));
    while($i<$len) {
    $str.=chr((rand()%26)+97);
    $i++;
    }
    return $str;
    }
    ?>

