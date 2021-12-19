    //**************************************
    //     
    // Name: Count Down
    // Description:Gives a date/time count d
    //     own between current date/time and future
    //     date/time.
    // By: Tom Gard
    //
    // Inputs:current date
    future date
    //
    // Returns:count of:
    days
    hours
    minutes
    seconds
    //
    // Assumes:I am new to learning php and 
    //     was looking for some code that I could p
    //     ut a count down type of thing on my webs
    //     ite. After searching around and not real
    //     ly finding what I was looking for I came
    //     up with this code. It works pretty well 
    //     I guess. I'd appreciate any feedback, pe
    //     rhaps there is a better way to get a cou
    //     nt down, if so send me a clue. Thanks
    //
    //This code is copyrighted and has    // limited warranties.Please see http://
    //     www.Planet-Source-Code.com/xq/ASP/txtCod
    //     eId.250/lngWId.8/qx/vb/scripts/ShowCode.
    //     htm    //for details.    //**************************************
    //     
    
    <?php
    //Count Down of Days:Hours:Minutes:Secon
    //     ds
    //by T.Gard (tom_g@execpc.com) written 0
    //     1/09/2001
    //
    //Date/Time counting down too.
    $datetime=strtotime( "2001-01-27 11:00" );
    //Retrieve the current date/time 
    $date2=strtotime("NOW"); 
    if ($datetime < $date2) {
    print "The Event is here!! or over with!! <br> ".$date2." is greater than ".$datetime."<br>";
    } else {
    //List total time by type
    //Seconds
    echo "total seconds remaining: ".(($datetime-$date2)). "<br>";
    $holdtotsec=(($datetime-$date2));
    //Minutes
    echo "total minutes remaining: ".(($datetime-$date2)/60). "<br>";
    $holdtotmin=(($datetime-$date2)/60);
    //Hours
    echo "total hours remaining: ".(($datetime-$date2)/3600). "<br>";
    $holdtothr=(($datetime-$date2)/3600); 
    //
    //Days - a day is a 24 hour period
    //This gives days remaining
    $holdtotday=intval(($datetime-$date2)/86400);
    echo "total days remaining: ".$holdtotday. "<br>"; 
    //
    //Find hours remaining - get days in hou
    //     rs and sub from tothr
    $holdhr=intval($holdtothr-($holdtotday*24));	
    echo "hours remaining: ".($holdhr). "<br>";
    //
    //Find minutes remaining - get days and 
    //     hours in minutes and sub from totmin
    $holdmr=intval($holdtotmin-(($holdhr*60)+($holdtotday*1440))); 
    echo "minutes remaining: ".($holdmr). "<br>";
    //
    //Find seconds remaining - get days hour
    //     s minutes in seconds and sub from totsec
    //     
    $holdsr=intval($holdtotsec-(($holdhr*3600)+($holdmr*60)+(86400*$holdtotday))); 
    echo "seconds remaining: ".($holdsr). "<br>";
    }
    ?>
