  ## email validation using regex
    print "Enter your email address: ";
    $email = <STDIN>;
    chomp $email;
    if ($email !~ /^[\w\.\-]+\@[\w\.\-]+\.[a-z][a-z]+$/i)
    {
    	print "\n\tINVALID\n\n";
    }
    else
    {
    	print "\n\tGood to go, baby\n\n";
    }

