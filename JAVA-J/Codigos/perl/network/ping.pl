#!/usr/bin/perl
    print <<HTMLHead;
    Content-type:text/html
    <html><head><title>Test Page</title></head>
    <h2><center>Test Ping Page</center></h2>
    <p>
    HTMLHead
    system("ping 208.185.226.87 > pingit.txt");
    open(input,"pingit.txt");
    @lines=<input>;
    foreach $i (@lines) {
    print $i;
    }
    print <<HTMLFoot;
    </body></html>
    HTMLFoot
    exit;