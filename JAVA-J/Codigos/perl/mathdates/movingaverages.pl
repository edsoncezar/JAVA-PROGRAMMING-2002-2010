print "Please enter the initial values separated by spaces: ";
    chomp($inval = <STDIN>);#Get the initial values
    @data = split(/ /,$inval);#Split them on the spaces
    unless($nval eq 'exit'){#Unless we want to quit 
    print "Average of ";
    for $item(@data){
    	print $item.",";#Print the current data set
    }
    print " : ".average(@data)."\n"; #Print the average
    shift @data;#Remove the unwanted value
    print "Next Value: ";
    chomp($nval = <STDIN>);
    unless($nval eq 'exit'){
    	push(@data,$nval); #Add the new value
    	}
    }
    sub average {
    	@avd = @_;
    	$els = 0;
    	$total = 0;
    	for $element(@avd){
    		$total += $element;#Add them all together
    		$els++;#Increment the counter
    	}
    	$av = $total / $els;#Calculate the mean value
    	return $av;#Return it
    }

