  #----------------#
    #Settings#
    #----------------#
    @messages = ("hello","are you doing your work",
    	"stop flirting on email","click OK","perl rules") ;
    $usernameORip = "sd11" ; #this is an IP address or computer name
    $noOfTimes = 5 ; # number of times you want it to loop
    #----------------#
    #Execution#
    #----------------#
    $count = 0 ;
    while ($count < $noOfTimes) {
    	$element = int(rand($#messages)) ; #get a random element
    	$exeStr = "net send $usernameORip \"$messages[$element]\"" ;
    	system($exeStr) ;
    	$count++ ;
    	if ($count < $noOfTimes) {
    		#pause before sending the next one
    		sleep(($element+1)*7) ; 
    	}
    }