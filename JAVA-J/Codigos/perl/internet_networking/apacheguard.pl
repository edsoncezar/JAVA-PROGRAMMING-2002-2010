  use strict;
    use strict;
    use warnings;
    use Socket;
    # $path2log is the path to your httpd log directory
    my $path2Log = "/var/log/httpd/";
    # $path2save is where you would like to save the database
    my $path2save = "/opt/oApacheGuard/guard.db";
    # $path2ipchains is the full path to the ipchains executable
    my $path2ipchains = "/sbin/";
    # $path2html is the path where you would like the store the html safe output
    # for live logging on your web site. set this to nothing if you dont want to
    # use this feature (I.E. my $path2html = "";)
    my $path2html = "/var/www/html/bin/oApacheGuard.out";
    # $trys is the number of atempts before a ban is placed
    my $trys = 5;
    # ---- DO NOT EDIT PAST HERE UNLESS YOU KNOW WHAT YOU ARE DOING ----
    opendir(DIR, $path2Log) or die("Unable to open $path2Log: $!");
    my @hackArray;
    my @ipArray;
    my @dataArray;
    my $c = 0;
    my $me = "none";
    my $iaddr;
    my $peer_addr;
    foreach(readdir(DIR)) 
    {
    my $file = $_;
    if ($file && /[A-Za-z]/) {
    if ($file =~ /access_log/) {
    open(LOG, $path2Log . $file) or die("Cannot open $file");
    while(my $line = <LOG>)
    {
    my @logArray = split("] " . chr(34) . "GET ", $line);
    my $arrayCount = scalar @logArray;
    if ($logArray[0]) {
    $logArray[0] =~ s/\[//; 
    @ipArray = split(" ", $logArray[0]);
    }
    if ($logArray[1]) {
    my @urlArray = split("HTTP/1.", $logArray[1]);
    if ($urlArray[0]) {
    if (((index($urlArray[0], "root.exe", 0) > 0) || (index($urlArray[0], "cmd.exe", 0) > 0) || index($urlArray[0], ".ida", 0) > 0)) {
    $hackArray[$c] = "IIS Exploit->" . $ipArray[0] . "->" . $urlArray[0];
    $c++;
    }
    }
    } 
    }
    close(LOG) or die("Cannot close $file\n");
    }
    }
    }
    @hackArray = sort (@hackArray);
    my $old = "empty";
    my $z = 1;
    foreach (@hackArray) {
    @dataArray = split("->", $_);
    #print($dataArray[0] . $dataArray[1] . $dataArray[2] . "\n\n");
    if ($old eq $dataArray[1]) {
    $z++; 
    next; 
    }
    $old = $dataArray[1];
    if ($z >= $trys) {
    open(banIn, $path2save) or die("Cannot open $path2save: $!\n");
    while (my $line = <banIn>) {
    my @checkArray = split("->", $line);
    if ($dataArray[1] eq $checkArray[1]){
    $me = "tj";
    }
    }
    if ($me eq "tj") {
    $me = "none";
    } else {
    open(banOut, ">>$path2save") or die("Cannot open $path2save: $!\n");
    $iaddr = gethostbyname($dataArray[1]);
    if ($iaddr) {$peer_addr = inet_ntoa($iaddr)}; 
    print(banOut $dataArray[0] . "->" . $dataArray[1]. "->" . $peer_addr . "->" . $dataArray[2] . "->" . $z . "\n");
    close(banOut) or die("Cannot close $path2save: $!\n");
    my $shell = $path2ipchains . 'ipchains -A input -p tcp -s ' . $peer_addr . ' -d 0/0 80 -j DENY';
    system $shell;
    #print ("$shell\n");
    } 
    close(banIn) or die("Cannot close $path2save: $!\n");
    }
    $z = 1;
    }
    if ($path2html) {
    open(htmlOut, ">$path2html") or die("Cannot open $path2html: $!\n");
    print(htmlOut "<table width='100%' border=0>");
    print(htmlOut "<tr><td><b>IP Address / Hostname</b></td><td><b>Attempts</b></td><td><b>Reason</b></td></tr>");
    open(htmlIn, $path2save) or die("Cannot open $path2save: $!\n");
    while (my $logLine = <htmlIn>) {
    my @itemArray = split("->", $logLine);
    print(htmlOut "<tr><td>" . $itemArray[1] . "</td><td>" . $itemArray[4] . "</td><td>" . $itemArray[0] . "</td></tr>\n");
    }
    close(htmlIn) or die("Cannot close $path2save: $!\n");
    print(htmlOut "<tr><td colspan=3>Last Updated: " . lUpdated() . "</td></tr>");
    print(htmlOut "</table>");
    close(htmlOut) or die("Cannot close $path2html: $!\n");
    }
    sub lUpdated {
    my $ampm = "AM";
    my $datetime = localtime($^T);
    my $otime = substr($datetime, 11, 8);
    my $oday = substr($datetime, 8, 2);
    my $omonth = substr($datetime, 4, 3);
    my $oyear = substr($datetime, 20, 4);
    my @timeArray;
    @timeArray = split(/:/, $otime);
    if($timeArray[0] >= 12) {
    $ampm = "PM";
    if($timeArray[0] > 12) {
    $timeArray[0] -= 12;
    }
    }
    $otime = join(":", @timeArray);
    my $tmp = "$oday $omonth, $oyear $otime $ampm";
    return $tmp;
    }

