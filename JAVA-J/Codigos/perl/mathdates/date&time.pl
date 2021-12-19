$m = "AM";
    $date_time = localtime($^T);
    $vtime = substr($date_time, 11, 8);
    $vday = substr($date_time, 8, 2);
    $vmonth = substr($date_time, 4, 3);
    $vyear = substr($date_time, 20, 4);
    @timeArray = split(/:/, $vtime);
    if($timeArray[0] >= 12) {
    $m = "PM";
    if($timeArray[0] > 12) {
    $timeArray[0] -= 12;
    }
    }
    $vtime = join(":", @timeArray);
    print "DATE : $vday $vmonth $vyear\n";
    print "TIME : $vtime $m\n";