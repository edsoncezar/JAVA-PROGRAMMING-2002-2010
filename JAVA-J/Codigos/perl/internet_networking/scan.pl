 use strict;
    use warnings;
    use Socket;
    #--------- Variables you may need to configure are here ---------
    # $path2log is the directory path to your apache logs
    my $path2Log = "/var/log/httpd/";
    # $path2save is where you want to store the ban list
    # the ban list is stored as follows <ip address/hostname> <number of explit atempts>
    my $path2save = "/home/tj/fw_data/fw_banned";
    # $path2ipchains is the full path name to the ipchains executable
    my $path2ipchains = "/sbin/";
    # $trys is the number of explit atempts before the user is banned
    my $trys = 5;
    #--------- DO NOT EDIT PAST HERE UNLESS YOU KNOW WHAT YOUR DOING ---------
    opendir(DIR, $path2Log) or die("Unable to open $path2Log: $!");
    my @hackArray;
    my @ipArray;
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
    if (index($urlArray[0], ".exe", 0) > 0) {
    $hackArray[$c] = $ipArray[0];
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
    if ($old eq $_) {
    $z++; 
    next; 
    }
    $old = $_;
    if ($z >= $trys) {
    open(banIn, $path2save) or die("Cannot open $path2Log\n");
    while (my $line = <banIn>) {
    my @checkArray = split(" ", $line);
    if ($_ eq $checkArray[0]){
    $me = "tj";
    }
    }
    if ($me eq "tj") {
    $me = "none";
    } else {
    open(banOut, ">>$path2save") or die("Cannot open $path2save: $!\n");
    print(banOut "$_ $z\n");
    close(banOut) or die("Cannot close $path2save: $!\n");
    $iaddr = gethostbyname($_);
    if ($iaddr) {$peer_addr = inet_ntoa($iaddr)};
    my $shell = $path2ipchains . 'ipchains -A input -p tcp -s ' . $peer_addr . ' -d 0/0 80 -j DENY';
    system $shell;
    } 
    close(banIn) or die("Cannot close $path2save: $!\n");
    }
    $z = 1;
    }

