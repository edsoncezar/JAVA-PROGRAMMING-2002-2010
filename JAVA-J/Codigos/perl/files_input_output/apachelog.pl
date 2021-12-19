 #C:\Perl\Bin\Perl.exe 
    # 
    #Apache Log Manager 
    # 
    print "Apache Log Manager\n"; 
    print "ds \[x\] - Find logs related to date \[x\] \n"; 
    print "is \[x\] - Find logs related to ip \[x\]\n"; 
    print "rs \[x\] - Find logs related to request \[x\]\n"; 
    print "ss \[x\] - Find logs with string \[x\] in them\n"; 
    &getui; 
    sub getui 
    { 
    print "Search type:"; 
    $stype = <STDIN>; 
    print "Search criteria:"; 
    $scrit = <STDIN>; 
    chop $stype; 
    chop $scrit; 
    DoSearch($stype,$scrit); 
    } 
    sub DoSearch 
    { 
    $sc = substr($_[0],0,2); 
    print "$sc\n"; 
    if ($sc eq 'ds') 
    { 
    datesearch($_[1]);#Search for hits on specified Date 
    } 
    elsif ($sc eq 'is') 
    { 
    ipsearch($_[1]);#Search for hits by specified IP address 
    } 
    elsif ($sc eq 'rs') 
    { 
    requestsearch($_[1]);#Search for hits using specified request 
    } 
    else 
    { 
    stringsearch($_[1]);#No search defined-Generic search for specified string 
    } 
    } 
    sub datesearch 
    { 
    $sc = $_[0]; 
    $count = 0; 
    open (LOG,'C:\webserv\apache\logs\access.log'); #Replace with your log path 
    @log = <LOG>; 
    close(LOG); 
    foreach $entry(@log) 
    { 
    $dbegin = index($entry,'['); 
    $dend = $dbegin + 12; 
    $data = substr($entry,$dbegin + 1,$dend); 
    if ($data =~ /$sc/) 
    { 
    chop $entry; 
    print "$entry\n"; 
    $count = $count +1; 
    } 
    } 
    print "$count entries\n"; 
    } 
    sub ipsearch 
    { 
    $sc = $_[0]; 
    $count = 0; 
    open (LOG,'C:\webserv\apache\logs\access.log'); #Replace with your log path 
    @log = <LOG>; 
    close(LOG); 
    foreach $entry(@log) 
    { 
    $iend = index($entry,' '); 
    $data = substr($entry,0,$dend -1); 
    if ($data =~ /$sc/) 
    { 
    chop $entry; 
    print "$entry\n"; 
    $count = $count +1; 
    } 
    } 
    print "$count entries\n"; 
    } 
    sub requestsearch 
    { 
    $sc = $_[0]; 
    $count = 0; 
    open (LOG,'C:\webserv\apache\logs\access.log'); #Replace with your log path 
    @log = <LOG>; 
    close(LOG); 
    foreach $entry(@log) 
    { 
    $rbegin = index($entry,"\""); 
    $rend = rindex($entry,"\" "); 
    $data = substr($entry,$dbegin + 1,$dend -1); 
    if ($data =~ /$sc/) 
    { 
    chop $entry; 
    print "$entry\n"; 
    $count = $count +1; 
    } 
    } 
    print "$count entries\n"; 
    } 
    sub stringsearch 
    { 
    $sc = $_[0]; 
    $count = 0; 
    open (LOG,'C:\webserv\apache\logs\access.log'); #Replace with your log path 
    @log = <LOG>; 
    close(LOG); 
    foreach $entry(@log) 
    { 
    if ($entry =~ /$sc/) 
    { 
    chop $entry; 
    print "$entry\n"; 
    $count = $count +1; 
    }
    } 
    print "$count entries\n"; 
    }

