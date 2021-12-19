    //**************************************
    //     
    // Name: Linux Fortune
    // Description:Very primitive include to
    //     show Linux fortune, with an option to sh
    //     ow offensive, plus disk space usage, and
    //     , optional process listing. by Van
    // By: PHP Code Exchange
    //**************************************
    //     
    
    <html> 
    <head> 
    <% 
    /* 2000-06-02 22:01:34: Van; vanboers@dedserius.com 
    http://www.dedserius.com 
    --fortune.php3 v.0.0.1 
    A little PHP3 module for Linux that shows fortune, disk-space usage and 
    processes (if you enable them) 
    $ps_array("cpu","ruser","pid","tty","time","command"); 
    */ 
    %> 
    <title>Fortune on <%=$SERVER_NAME%></title> 
    <table width="100%"> 
    </head> 
    <body bgcolor="white"> 
    <td width=5> 
    </td> 
    <td> 
    <h2> Brought to you by Linux Fortune on <u><% echo "$SERVER_NAME!"%></u></h2> 
    <form action="./fortune.php3" method="get"> 
    <input type="hidden" name="curpage" value="fortune"> 
    <input type="hidden" name="message" value="fortune.php3"> 
    <select name="whichcmd"> 
    <option selected value="<%=$whichcmd%>">Show me a Fortune</option> 
    <option value="FortuneOffensive">Show me an Offensive Fortune</option> 
    <%/*<option value="Who">Who's on the server?</option>*/%> 
    <option value="ProcessList">Show Processes</option> 
    <option value="DF">How are the disks doing?</option> 
    <input type="submit" value="Post"> 
    </select> 
    <input type="hidden" name="chosewhat" value="Fortune"> 
    </form> 
    <% 
    switch ($whichcmd) 
    { 
    case "Fortune": 
    $fortune = exec("/usr/games/fortune -a",$fortunearray); 
    break; 
    case "OffensiveFortune": 
    $fortune = exec("/usr/games/fortune -o",$fortunearray); 
    break; 
    case "Who": 
    $fortune = exec("w", $fortunearray); 
    break; 
    case "DF": 
    $fortune = exec("df -hv | sed '/ /s// /g' | sort -n +1", $fortunearray); 
    $numcols=5; 
    break; 
    case "ProcessList": 
    //Enable the following and comment out t
    //     he one below it, if you're either brave 
    //     
    //or sttupid. >:) The formatting is k
    //     inda broken, but, I haven't done much wi
    //     th it. 
    /*$fortune = exec("ps -eo '%C%u%p%y%x%a' --width 200 | sort -n +1 -r", $fortunearray);*/ 
    $fortune = exec("/bin/echo 'Hi There, >:); Yeah, right!'", $fortunearray); 
    $numcols=5; 
    break; 
    default: 
    $fortune = exec("/usr/games/fortune -a", $fortunearray); 
    break; 
    } 
    $i = 0; 
    $j = count($fortunearray); 
    if ( ($whichcmd != "DF" ) && ($whichcmd != "ProcessList")) 
    { 
    while ( $i < $j ) 
    { 
    echo "<font face=>\n"; 
    echo "$fortunearray[$i]<br>\n"; 
    echo "</font>\n"; 
    $i++; 
    } 
    } else { 
    %> 
    <table border=1> 
    <% 
    while ( $i < $j ) 
    { 
    echo "\t<tr>\n"; 
    $tabarray=split(" ",$fortunearray[$i],$numcols); 
    for ($k=0; $k < $numcols; $k++) 
    { 
    echo "\t\t<td><b>$tabarray[$k] </b></td>\n"; 
    } 
    echo "\t</tr>\n"; 
    $i++; 
    } 
    echo "\n</table>\n"; 
    } 
    %> 
    <% 
    echo "<br><br><hr>\n"; 
    $w = exec("/usr/bin/uptime",$uptime); 
    $e = 0; 
    $f = count($uptime); 
    echo "<b><u><font size=+1>$HostName</u></b> Up Time:</font><br>\n"; 
    echo "==============================================================<br>\n"; 
    while ( $e < $f ) 
    { 
    echo "$uptime[$e]<br>\n"; 
    $e ++; 
    } 
    echo "==============================================================<br>\n"; 
    echo "\n"; 
    %> 
    </td> 
    <td width=5> 
    </td> 
    </table> 
    </body> 
    </html>
