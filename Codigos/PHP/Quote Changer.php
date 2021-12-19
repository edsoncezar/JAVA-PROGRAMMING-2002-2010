//**************************************
    //     
    // Name: Quote Changer
    // Description:This is a little script t
    //     hat allows you to put a quote on your pa
    //     ge and easily change it using a PHP scri
    //     pt. I developed this script on Windows N
    //     T but it should work fine on any Linux o
    //     r Unix system. by Brian http://www.evilw
    //     alrus.com
    // By: PHP Code Exchange
    //**************************************
    //     
    
    <?php
    /* 
    Quote Changer Copyright 2000 Brian Ross
    Version 1.0
    brian@music4free.com
    AIM: DeadBrain3
    ICQ: 15273600
    http://brian.zero-gravity.org/php/
    How to install:
    1. Edit the variables below to fit your needs.
    2. Create the datafile that you showed in $path and chomd to it 777.
    3. Use either a PHP or SSI include to insert the quote onto the page you want.
    Example: include("d:\\home\\httpd\\html\\news\\data2\\1.viewh"); (you must put in on in PHP brackets)
    */ 
    $prefix = "<center>";//what you want to appear before the quote line 
    $suffix = "</center>";//what you want to appear after the quote line 
    $password = "change";//the password you need to change the quote 
    $path = "c:\\Brian\\Brain's Pad\\php-bin\\quote.dat";//path to datafile 
    if ($pass != $password) {
    	echo "<html><head><title>DLCount Admin - Enter Password</title></head><body>";
    	echo "<form method=\"post\" action=$PHP_SELF>";
    	echo "Enter your password:<br>";
    	echo "<input type=\"password\" name=\"pass\">";
    	echo "<input type=\"submit\" value=\"continue\">";
    	echo "</form></body></html>";
    }
    else {
    if ($quote . $person != "") {
    $ff = fopen($path,"w");
    fputs($ff,$prefix . stripslashes($quote) . "<br><b>-" . stripslashes($person) . "</b>" . $suffix . "\n");
    fclose($ff);
    print "The Quote Was Successfully Changed To:<br>\n"; //html
    print "<small><b>Quote: </b>" . stripslashes($quote) . "<br><b>Author: </b>" . stripslashes($person) . "\n"; //html
    print "<center><hr width=300 size=1 height=1 noshade color=\"#000000\"><br>\n"; //html
    print "<font face=\"Arial\" size=\"1\" color=\"#000000\">Powered By <a href=\"http://brian.zero-gravity.org/php/\">Quote Changer 1.0</a></font><br>\n"; //html
    }
    else {
    print "<center>\n"; //html
    print "<p><form method=\"POST\" action=\"\"><center><table border=0><tr><td>Quote: </td><td><center><input type=\"text\" name=\"quote\" size=\"30\"><br><font size=\"1\">\"To be or not to be, that is the question.\"</center></font></td></tr><tr><td>Author: </td><td><center><input type=\"text\" name=\"person\" size=\"30\"><br><font size=\"1\">William Shakespeare</font></center></td></tr></table><center><input type=\"hidden\" name=\"pass\" size=\"30\" value=\"" . $password . "\"><input type=\"submit\" value=\"-Change-\" class=\"button\"> <input type=\"reset\" value=\"-Reset-\" class=\"button\"></center></form>\n"; //html
    print "</center>\n"; //html
    print "<center><hr width=300 size=1 height=1 noshade color=\"#000000\"><br>\n"; //html
    print "<font face=\"Arial\" size=\"1\" color=\"#000000\">Powered By <a href=\"http://brian.zero-gravity.org/php/\">Quote Changer 1.0</a></font><br>\n"; //html
    }
    } 
    ?>
