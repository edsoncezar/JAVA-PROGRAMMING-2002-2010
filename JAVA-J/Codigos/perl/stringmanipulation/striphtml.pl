 #!/usr/bin/perl
    sub StripHtml {
    @params = @_ ;
    $i=0; $ie=0; $ir=-1; $ps=@params;
    $t=$params[0];
    while($i != -1) {
    $i = index($t,"<",$ir + 1);
    $ie = index($t,">",$i + 1);
    if ($i ne -1 && $ie eq -1) { return $t; }
    if (index($t,"<",$i + 1) < $ie && index($t,"<",$i + 1) != -1) { 
    $ir=index($t,"<",$i + 1)-1;
    } else {
    if ($i == $ir || $i < 0) { return $t; }
    if ($ie < $i) { $ie = $i + 1; }
    $h = substr($t, $i, $ie - $i + 1);
    $g = substr($t, 0, $i);
    $y = substr($t, $ie+1, length($t) - $ie - 1);
    $ip=0;
    for ($a=1; $a<=$ps-1; $a++) {
    if (lc(substr($h, 0, length($params[$a]) + 1)) eq lc("<$params[$a]") || lc(substr($h, 0, length($params[$a]) + 2)) eq lc("</$params[$a]")) { $ip=1; last; }
    }
    if ($ip eq 0) { $t="$g$y"; $ir = $i; } else { $ir = $ie; }
    }
    }
    return $t;
    }
    $buffer = $ENV{'QUERY_STRING'};
    @pairs = split(/&/, $buffer);
    foreach $pair (@pairs) {
    ($name, $value) = split(/=/, $pair);
    # Un-Webify plus signs and %-encoding
    $value =~ tr/+/ /;
    $value =~ s/%0d/ /ig; 
    $value =~ s/%0a//ig; 
    $value =~ s/%([a-fA-F0-9][a-fA-F0-9])/pack("C", hex($1))/eg;
    $FORM{$name} = $value;
    }
    $n = $FORM{'main'};
    print "Content-type: text/html\n\n";
    $r=StripHtml($n,"a","img","b","i","u");
    #will allow links, images, bold, italics and underline tags to go through
    print qq~
    <br>
    $r
    <br><br>
    <form>
    <textarea cols=40 rows=20 name="main">$r</textarea>
    <input type="submit">
    </form>
    ~;
    ##########################
    #you can paste the above code into a test file, upload it, chmod it, and run it to test it out.
    ##########################################
