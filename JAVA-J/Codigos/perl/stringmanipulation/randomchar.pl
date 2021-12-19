 #!/usr/bin/perl
    print "content-type: text/html\n\n";
    @char = ("A" .. "Z", "a" .. "z", "0" .. "9");
    $chars = \@char;
    $length = @char;
    $count=0;
    while($count <15){ #set the number here to the amount of charactors needed
    $num = rand($length);
    $password = $password . $$chars[$num];
    $count++;
    }
    print $password; #this is the randomly created string do what you want with this.
