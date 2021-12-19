    //**************************************
    //     
    // Name: Binary pattern match
    // Description:This is a function that w
    //     ill return the filetype of a file. It wi
    //     ll perform a binary pattern match of the
    //     contents of the file. An array defines t
    //     he filetype and associated pattern
    // By: PHP Code Exchange
    //**************************************
    //     
    
    <?
    function findfiletype($filename) {
    /*
    The array with the filetype and pattern, separate with a semi-colon. 
    Each pair in the pattern represents a single byte in the input file.
    The question mark is used to match a single digit but should be used on
    both digits in the byte pair. Originally made to find the filetype of
    an input file uploaded using the POST method, which explains the "none"
    comparison.
    - Petter Nilsen <pettern@thule.no>,
    */
    $types = array(
    "zip;$504B",
    "lha;$????2D6C68",
    "gif;$47494638??",
    "jpg;$????????????4A464946",
    "exe;$4D5A",
    "bmp;$424D"
    );
    $len = 0;
    $match = 0;
    $ext = "";
    if($filename == "none") {
    return($ext);
    }
    $fh = fopen($filename, "r");
    if($fh) {
    $tmpBuf = fread($fh, 250);
    if(strlen($tmpBuf) == 250) {
    for($iOffset = 0; $types[$iOffset]; $iOffset += 1) {
    list($ext,$pattern,$junk) = explode( ";",$types[$iOffset]);
    $len = strlen($pattern);
    if($pattern[0] == '$') {
    for($n = 1; $n < $len; $n += 2) {
    $lowval = 0; $highval = 0;
    if($pattern[$n] == '?' || $pattern[$n + 1] == '?')
    continue;
    $highval = Ord($pattern[$n]) - 48;
    if($highval > 9) {
    $highval -= 7;
    }
    $lowval = Ord($pattern[$n + 1]) - 48;
    if($lowval > 9) {
    $lowval -= 7;
    }
    if(Ord($tmpBuf[($n - 1) >> 1]) == (($highval << 4) + $lowval)) {
    $match = 1;
    }
    else {
    $match = 0;
    break;
    }
    }
    if($match) {
    break;
    }
    }
    }
    }
    if(!$match) {
    $ext = "";
    }
    fclose($fh);
    }
    return ($ext);
    }
    ?>

