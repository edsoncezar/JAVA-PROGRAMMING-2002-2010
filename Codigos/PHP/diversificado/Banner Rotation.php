    //**************************************
    //     
    // Name: Banner Rotation
    // Description:Automatic banner rotation
    //     . Randomly selects a banner for display.
    //     by Anton Olsen
    // By: PHP Code Exchange
    //**************************************
    //     
    
    <?php
    /* banner.phtml
    Banner rotation script for PHP3 by Anton Olsen (aolsen@graphweb.com)
    Please feel free to do with this script what you want, all I ask is
    that if you make significant changes, please e-mail them to me.
    I tried to use a number of different methods, the image functions
    of PHP do not appear to understand animated GIFs and the file
    handling features (fopen, fpassthru, and fclose) were causing
    apache to crash on me. I settled on using passthru. Although
    possibly not as portable, it appears to work faster than either
    method mentioned above.
    Assumptions:
    You have a directory for all your banners.
    All banners are GIF files.
    The filenames of the banners all start with banner.
    There are no other files in the directory starting with banner.
    Installation:
    Place this script in the banners directory.
    Place all your banner*gif files in the same directory.
    Add the following HTML code to your web pages :
    <a href="wherever.you.want.com">
    <img src="http://www.yourserver.com/bannerdir/banner.phtml" alt="Random Banner Here" border=0>
    </a>
    */
    /* random( $max integer )
    Returns a random number between 0 and $max-1;
    */
    function random( $max )
    {
    $x = rand();
    $y = getrandmax();
    $r = $x / $y * ($max -1 );
    $r = round( $r++ );
    return $r;
    }
    /* Read the directory, add all "banner*" files with to the array
    */
    $i = 0;
    $d= dir(".");
    while($entry=$d->read())
    if (substr($entry,0,6) == "banner")
    $array[$i++] = $entry;
    $d->close();
    /* pick a banner at random
    */
    $r = random( $i );
    /* Send a no-cache header, and the gif type header, and output the file.
    */
    Header( "Pragma: no-cache" );
    Header( "Expires: Monday 01-Jan-80 12:00:00 GMT" );
    Header( "Content-type: image/gif");
    passthru( "cat $array[$r]" );
    ?>

