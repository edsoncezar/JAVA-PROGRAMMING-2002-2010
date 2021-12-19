open(AB, "sample.txt");
    open(CD, ">sample_temp.txt");
    $x = <AB>;
    while($x) {
    print CD $x;
    $x = <AB>;
    }
    close AB;
    close CD;

