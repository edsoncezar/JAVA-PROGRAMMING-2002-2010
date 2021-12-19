#!/usr/bin/perl -w

use LWP::UserAgent;
use HTTP::Request::Common;

$numofstrips = NumofStrips();

$home = `echo \$HOME`;chop $home;$rcfile = "$home\/.stripnum";

if (-f $rcfile) {
    $stripnum = `cat ~/.stripnum`;
} else {
    print "~/.stripnum does not exist, creating...\n";
    system 'echo 1 > ~/.stripnum';
    $stripnum = `cat ~/.stripnum`;
}

if (-d "$home\/.strips") {
} else {
    print "directory $home\/.strips does not exist, creating...\n";
    system "mkdir $home\/.strips";
}
print "Should only see this once!\n";
mainblock: {
    $url = "http://thebench.org/index.php3?strip=$stripnum";
    $bot = new LWP::UserAgent;
    $bot->agent('Mozilla');
    $response = $bot->request(GET $url);
    $content = $response->content;

    @content = split(/\n/, $content);

  content: for($i=0;$i<$#content;$i++) {
      $content[$i] =~ /.*Strip \#(.*):<.*/;
      if ($1) { $number = $1; last content; }
  }
    if ($number != $stripnum) { 
        print "$stripnum does not seem to exhist\n";
        $numofstrips = NumofStrips();
        print "$numofstrips seems to be total number of strips\n";
        $numofstrips++;
        if ($stripnum == $numofstrips) {
            open (FILE, ">$rcfile") || die;
            print (FILE "$stripnum") || die;
            close FILE;
            die "Got last file exiting\n";
        }
        else {
            $stripnum++;
        }
        print "lets try to get $stripnum\n";
        goto mainblock;
    }
    undef $filetodl;
  searchforfiletodl: for (split "\n", $content) {
        $filetodl = "$1.jpg" if/.*img\/strips\/(.*).jpg/;
        if ($filetodl){
            #print $filetodl;
            last searchforfiletodl;
        }
    }
    $imgurl = "http://thebench.org/img/strips/$filetodl";
    print "Downloading $imgurl\n";
    $response = $bot->request(GET $imgurl);
    $content = $response->content;


    $file = "$home\/.strips\/$number.jpg";
    print "opening $file\n";
    open (FILE, ">$file") || die;
    print (FILE "$content") || die;
    print "success!\n";
    close FILE;
    $stripnum++;
    print "$stripnum\n";
    open (FILE, ">$rcfile") || die;
    print (FILE $stripnum) || die;
    close FILE;
}

goto mainblock;

sub NumofStrips {
    my $url = "http://thebench.org/archive.php3";
    my $bot = new LWP::UserAgent;
    $bot->agent('Mozilla');
    my $response = $bot->request(GET $url);
    my $content = $response->content;
    @content = split(/\/td/, $content);
    searchfornumofstrips: for($i=0;$i<$#content;$i++){
        $content[$i] =~ /<tr><td valign='top'><p>(.*)<\/p></;
        if($1) { $numofstrips = $1; last searchfornumofstrips; }
    }
    return $numofstrips;
}

print "This isnt supposed to happen!\n"; die;
