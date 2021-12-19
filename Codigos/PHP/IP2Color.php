//**************************************
    //     
    // Name: IP2Color
    // Description:A couple of functions tha
    //     t convert an IP address into its color c
    //     ode and not-color-code. Useful when view
    //     ing an apache log with a mysql result gr
    //     ouped by IP
    // By: Fabien HADDADI
    //
    // Inputs:IP address
    //
    // Returns:formatted HTML with a colored
    //     IP address on a contrasted background
    //
    // Assumes:A color is defined by the thr
    //     ee components Red Green & Blue, to defin
    //     e the color of IP address w.x.y.z, I tak
    //     e w for red, x for green and y for blue,
    //     the 'not-color' is the base color transl
    //     ated by 128
    //
    // Side Effects:Some IPs produce a not s
    //     o contrasted background, because the col
    //     oring function could be improved
    //
    //This code is copyrighted and has    // limited warranties.Please see http://
    //     www.Planet-Source-Code.com/xq/ASP/txtCod
    //     eId.319/lngWId.8/qx/vb/scripts/ShowCode.
    //     htm    //for details.    //**************************************
    //     
    
    <? 
    /***********************************/ 
    /*this function converts $ipaddr into a color string*/ 
    function IP2Color($ipaddr) { 
    $pieces=explode(".",$ipaddr); 
    $color=""; 
    for($i=0;$i<3;$i++) { 
    if(($pieces[$i]>=0) && ($pieces[$i]<=255)) { 
    $color.=dechex($pieces[$i]); 
    } 
    } 
    $color=substr($color."000000",0,6); 
    return("#".strtoupper($color)); 
    } 
    /***********************************/ 
    /*this function converts $ipaddr 
    to anti color string*/ 
    function IP2NotColor($ipaddr) { 
    $pieces=explode(".",$ipaddr); 
    $color=""; 
    for($i=0;$i<3;$i++) { 
    if(($pieces[$i]>=0) && ($pieces[$i]<=255)) { 
    $color.=dechex((128+$pieces[$i]) % 255); 
    } 
    } 
    $color=substr($color."000000",0,6); 
    return("#".strtoupper($color)); 
    } 
    ======== 
    Example: 
    ======== 
    $IP=getenv("REMOTE_ADDR"); 
    print "<table><tr>\n"; 
    print "<td bgcolor='".IP2Color($IP)."'><font face='Arial,Courier' 
    color='".IP2NotColor($IP)."'>$IP</font></td>\n"; 
    print "</tr></table>\n"; 
    ?> 

