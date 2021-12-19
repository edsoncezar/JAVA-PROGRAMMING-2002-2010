//**************************************
    //     
    // Name: Attrition Latest Defacements
    // Description:Gets the latest defacemen
    //     ts from www.attrition.org, make use of l
    //     ooping & regular epressions (<- hard)
    //     
    // By: Demian Net
    //
    //This code is copyrighted and has    // limited warranties.Please see http://
    //     www.Planet-Source-Code.com/xq/ASP/txtCod
    //     eId.213/lngWId.8/qx/vb/scripts/ShowCode.
    //     htm    //for details.    //**************************************
    //     
    
    <?
    // Atrrition Latest Defacements
    // Writer: Max - Demian Net
    // E-Mail: Max@Wackowoh.com
    // WebSite: http://www.DemianNet.com htt
    //     p://www.Wackowoh.com
    // Language: PHP
    // Usage:
    // include("attrition.php");
    // or
    // require "attrition.php";
    // Variables:
    $number = "10"; // The Number of Latest Defacements You Want To Retrieve
    // None of This Needs To Be Edited
    $buffer = implode("", file("http://www.attrition.org/mirror/attrition/index.html"));
    preg_match_all("|(<a href[^>]+>.*</[^>]+>+[^ ]+)|U",$buffer,$out,PREG_PATTERN_ORDER);
    $i = 0;
    echo "<base href='http://www.attrition.org/mirror/attrition/'>\n";
    for ($i = 14; $i < $number + 14; $i++){
    echo "{$out[1][$i]}<br>\n";
    }
    ?>
