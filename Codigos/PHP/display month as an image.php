    //**************************************
    //     
    // Name: display month as an image
    // Description:This simply displays the 
    //     current month as an image
    // By: i hendry
    //
    // Returns:An image of the current moont
    //     h
    //
    // Assumes:The images in this example ar
    //     e in the same directory as the script . 
    //     If images are placed in a sub-directory 
    //     you have to alter the script. The images
    //     can be downloaded from http://www.mywebr
    //     esources.co.uk/php/monthimages.zip
    //
    //This code is copyrighted and has    // limited warranties.Please see http://
    //     www.Planet-Source-Code.com/xq/ASP/txtCod
    //     eId.338/lngWId.8/qx/vb/scripts/ShowCode.
    //     htm    //for details.    //**************************************
    //     
    
    <?php
    //images are availabe from 
    //http://www.mywebresources.co.uk/php/mo
    //     nthimages.zip
    //or you can use your own
    //example at http://www.mywebresources.c
    //     o.uk/php/monthpic.php
    //get month in textual format . i.e Jan 
    //     , Feb
    $month = date("M");
    //display month as an image
    echo "<img src = \"$month.gif\">";
    ?>
