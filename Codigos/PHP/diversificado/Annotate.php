//**************************************
    //     
    // Name: Annotate
    // Description:Very simple module to let
    //     your users post their comments on your W
    //     eb pages. Does not require SQL. Does req
    //     uire a writeable directory. by Steve Yel
    //     vington
    // By: PHP Code Exchange
    //**************************************
    //     
    
    <?
    /*
    annotate.php3 
    This is a module that can be placed on any php3 page to allow users to add
    their comments. The comments are stored in a file in the current directory,
    whose name is constructed by adding ".comment" to the calling page's name,
    and merged into the calling page dynamically. (The calling page is not
    modified.)
    I wrote this because I wanted a simple way to add this functionality to my
    pages without requiring that mySQL be available.
    In the message input, blank lines are converted to paragraph tags. No other
    conversions are applied. If you don't want your users to be able to input
    html, uncomment the "strip_tags" line.
    Note that the directory must be writable by the web server.
    Put this module in some convenient location and then embed it in your pages
    like so:
    require("/some/full/path/annotate.php3");
    or, relative to the docroot:
    require($DOCUMENT_ROOT . "/relativepath/php3");
    Steve Yelvington <steve@yelvington.com>
    */
    if ($message)
    	{
    	/* uncomment the next two lines to strip out html from input */
    	/* $name = strip_tags($name); */
    	/* $message = strip_tags($message); */
    	$message = ereg_replace("\r\n\r\n", "\n<P>", $message);
    	$date = date("l, F j Y, h:i a");
    	$message = "<B>$name </B> -- $date<P> $message <BR><HR>";
    	$fp = fopen (basename($PHP_SELF) . ".comment", "a");
    	fwrite ($fp, $message);
    	fclose ($fp);
    	}
    @readfile(basename(($PHP_SELF . ".comment")));
    ?>
    <FORM method="post">
    <b>Your name:</b><BR><INPUT name="name" type="text" size="55"><BR>
    <b>Your comment:</b><BR><TEXTAREA name="message" rows=10 cols=55 wrap=virtual>
    </TEXTAREA><BR>
    <INPUT name="submit" type="submit" value="Post your comments">
    </FORM>

