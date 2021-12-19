    //**************************************
    //     
    // Name: Asociative array to global vari
    //     ables
    // Description:By Ondra Zizka. Takes an 
    //     asociative array and defines variables n
    //     amed by arr's field names, opt. suffixes
    //     and preffixes. arr["hi"]=="there"; <b
    //     r> arr2vars($arr, "say_", "_1"); <
    //     br> //-> $say_hi_1=="there" <br
    //     >
    // By: PHP Code Exchange
    //**************************************
    //     
    
    <?
    function arr2vars($arr, $pref="", $suff=""){
    while (list($xkey, $val) = each($arr)){
    //global ${$pref.$xkey.$suff};
    	eval("global \$$pref$xkey$suff;");
    	${$pref.$xkey.$suff} = $val;
    }
    }
    /* use:
    $arr = Array("ahoj"=>"lidi", "jakse"=>"mate");
    arr2vars($arr);
    echo $ahoj;
    */
    ?>

