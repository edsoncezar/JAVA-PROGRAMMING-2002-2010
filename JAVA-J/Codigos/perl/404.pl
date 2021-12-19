#!/usr/bin/perl 
print "Content-type: text/html\n\n"; 
 
$referer = "$ENV{'HTTP_REFERER'}"; 
$date    = `date +%m/%d/%y`; 
chop ($date); 
 
open( FILE, ">>404.list" ); 
print FILE "[$date] Broken link on: $referer\n"; 
close FILE; 
 
print <<CHECK; 
<html><head><title>Error 404: File Not Found</title></head> 
<body> 
<font size=2 face=Verdana,Arial,Helvetica> 
 
<br><br> 
 
<font color=#FF0000> 
<b><blink>Error 404</blink> - File not found ...</b> 
</font> 
 
<br><br> 
<br><br> 
 
<blockquote> 
<b>Sorry, That URL does not exist on this server! <br> 
Please check over the spelling to make sure it's not a typo ...</b> 
 
<br><br> 
 
The page you came from [$referer] has been recorded and will be 
checked for errors. 
 
<br><br> 
 
<form> 
<input type=button value="Go Back" onclick=window.history.go(-1)> 
</form> 
</blockquote> 
</b> 
CHECK 
1;