  print ("\n\nEnter the distance to be converted:");
    $originaldist = <STDIN>;
    chop ($originaldist);
    $miles = $originaldist * 0.6214;
    $kilometers = $originaldist * 1.609;
    print ("\n", $originaldist, " kilometers = ", $miles, " miles\n");
    print ("\n",$originaldist, " miles = ", $kilometers, " kilometers\n");

