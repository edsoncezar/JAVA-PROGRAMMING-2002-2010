    //**************************************
    //     
    // Name: Case insensitive alphabetical a
    //     rray sorter
    // Description:This function is to be us
    //     ed with phps' built in usort(). It will 
    //     sort an array alphabetically and case in
    //     sensitively, something I found the norma
    //     l sort() function didn't do. An example 
    //     of usage: usort($array, 'isort'); Natura
    //     lly the function should be included or d
    //     efined in your code. By Richard Heyes.
    // By: PHP Code Exchange
    //**************************************
    //     
    
    <?php
    function isort($a,$b){
    if(ord(substr(strtolower($a),0,1)) == ord(substr(strtolower($b),0,1))) return 0;
    return (ord(substr(strtolower($a),0,1)) < ord(substr(strtolower($b),0,1))) ? -1 : 1;
    }
    ?>

 