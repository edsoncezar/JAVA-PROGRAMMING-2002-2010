    //**************************************
    //     
    // Name: Print Array
    // Description:Print Array is most usefu
    //     l as a debugging function, when you want
    //     to see the exact structure contained wit
    //     hin a variable. By Scott Parish
    // By: PHP Code Exchange
    //**************************************
    //     
    
    <?php
    /* FIRETAIL-print_array
    * Copyright (C) 1999 Scott Parish
    *
    * This library is free software; you can redistribute it and/or
    * modify it under the terms of the GNU Library General Public
    * License as published by the Free Software Foundation; either
    * version 2 of the License, or (at your option) any later version.
    *
    * This library is distributed in the hope that it will be useful,
    * but WITHOUT ANY WARRANTY; without even the implied warranty of
    * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
    * Library General Public License for more details.
    *
    * You should have received a copy of the GNU Library General Public
    * License along with this library; if not, write to the
    * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
    * Boston, MA 02111-1307, USA.
    */
    //This is are two little functions that 
    //     I made to quickly debug my arrays
    //while making php scripts. You can pass
    //     these things any variable (both
    //arrays and non arrays), and it will pr
    //     int out everything that is in it.
    //It will even print out embedded arrays
    //     , so if you have arrays in arrays
    //in arrays, it will print all that out 
    //     in a consice enough format that you
    //will know exactly what is in that vari
    //     able.
    //
    //You should get output with "key => 
    //     val", which should look something like
    //the following:
    //
    // admin =>
    // user=> 1
    // screwball => 2
    // admin => 4
    // . . .
    // is=>
    // student worker => 1
    // hacker => 2
    // nerd=> 4
    // admin => 8
    // . . .
    // 
    //call it like the following (where $tes
    //     tVar is a variable of you choice)
    //
    // print_array($testVar);
    //
    //if your array contains any html code, 
    //     you may want to call it instead as:
    //
    // print_array($testVar,"<xmp>","&
    //     lt;/xmp>");
    // 
    //Should be very easy to throw in your c
    //     ode and use...hope you find it useful
    //Scott Parish <sRparish@bigfoot.com&
    //     gt; 1998/02/24
    
    if(!$GLOBALS["PRINT_ARRAY"]) {
    $GLOBALS["PRINT_ARRAY"]=true;
    function print_array($a,$btag="",$etag="") {
    if(is_array($a)) {
    printf("<table cellpadding=0 cellspacing=0>");
    while(list($one,$two)=each($a)) {
    printf("\n<tr valign=baseline><td>$btag$one$etag</td><td>".
    " $btag=>$etag</td>".
    "<td align=right> %s</td></tr>\n"
    ,sprint_array($two,$btag,$etag));
    }
    printf("</table>");
    } 
    else {
    printf("%s%s%s",$btag,$a,$etag);
    } 
    }
    
    
    function sprint_array($a,$btag="",$etag="") {
    if(is_array($a)) {
    $out=sprintf("<table cellpadding=0 cellspacing=0>");
    while(list($one,$two)=each($a)) {
    $out .= sprintf("\n<tr valign=baseline><td>$btag$one$etag</td><td>".
    " $btag=>$etag</td>".
    "<td align=right> %s</td></tr>\n"
    ,sprint_array($two,$btag,$etag));
    }
    $out .= "</table>";
    return $out;
    }
    else {
    return sprintf("%s%s%s",$btag,$a,$etag);
    }
    }
    }
    ?>

