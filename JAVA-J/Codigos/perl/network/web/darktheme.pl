#!/usr/bin/perl

#use CGI ":standard";
use LWP::Simple;

my $this = "http://your.servers.url/yourpath/whatyounamedthis.cgi";
my $meth = $ENV{'REQUEST_METHOD'};
my $quer = $ENV{'QUERY_STRING'};
#my $surl = "http://www.slashdot.org";
my $surl = "";
if ($quer && $meth eq "GET") { $surl = $quer; }
if ($surl =~ /slashdot\.org/) {
    $surl =~ s!slashdot\.org/articles/(\d\d)/(\d\d)/(\d\d)/(\d+)\.shtml!slashdot.org/article.pl?sid=$1/$2/$3/$4&cid=&pid=0&startat=&threshold=2&mode=thread&commentsort=3!i; 
    $surl =~ s!slashdot\.org/interviews/(\d\d)/(\d\d)/(\d\d)/(\d+)\.shtml!slashdot.org/comments.pl?sid=$1%2F$2%2F$3%2F$4&cid=&pid=0&startat=&threshold=2&mode=thread&commentsort=3!i; 
}
my $site = get $surl; 

print "Content-type: text/html\n\n";

$site =~ s/<title>/<title>*pc* /i;
if ($surl =~ /slashdot\.org/i) {
  if ($surl !~ /org\/askslashdot/i) {
    $site =~ s/<BODY bgcolor="#000000" text="#000000" link="#006666" vlink="#000000">/<BODY bgcolor="#000001" text="#4FFFAF" link="#00BBBB" vlink="#4F2F9F">/;
    $site =~ s/<FONT color="#000000">/<FONT color="#A8F8F0">/g;
    $site =~ s/bgcolor="#006666"><IMG/bgcolor="#004444"><IMG/g;
    $site =~ s/CCCCCC"><FONT color="#000000" size=2>/CCCCCC"><FONT color="#B0D0F0" size=2>/g;
    $site =~ s/<B>Results<\/B>/<B><font color="#00BBBB">Results<\/font><\/B>/g;
    $site =~ s/&mode=thread>(.*)<\/A>/&mode=thread><font color="#00BBBB">$1<\/font><\/A>/g;
    $site =~ s/www\.thinkgeek\.com>ThinkGeek<\/A>/www\.thinkgeek\.com><font color="#00BBBB">ThinkGeek<\/font><\/A>/g;
    $site =~ s/    <FONT size=2 face="arial,helvetica"><I>/    <FONT color="#F0F0A0" size=2 face="arial,helvetica"><I>/g;
    $site =~ s/Andover\.Net">Andover\.Net<\/A>/Andover\.Net"><FONT size=1 color="#009999" face="arial,helvetica">Andover\.Net<\/font><\/A>/g;
    $site =~ s/size=3 color="#006666"/size=3 color="#00BBBB"/g;
    $site =~ s/size=1 color="#006666"/size=1 color="#00BBBB"/g;
    $site =~ s/size=4 color="#FFFFFF"/size=4 color="#DEFEEE"/g;
    $site =~ s/<FONT COLOR="#FFFFFF">/<FONT COLOR="#CEFEEE">/g;
    $site =~ s/"#006666"/"#004444"/g; #size=3||1 color=# -> 00BBBB
    $site =~ s/"#FFFFFF"/"#000412"/g; #001204
    $site =~ s/<TD bgcolor="#ffffff"/<TD bgcolor="#120212"/;
    $site =~ s/bgcolor=cccccc>/bgcolor="#521222">/g;
    $site =~ s/bgcolor=ffffff>/bgcolor="#120212">/g;
    $site =~ s/"ffffff"/"120212"/g;
    $site =~ s/"#ffffff"/"#AECEFE"/g;
    $site =~ s/"#CCCCCC"/"#521222"/g; #321202
    $site =~ s/"#000000"/"#C0F0D0"/g; #B0D0F0 F0D0B0 D0FFEF
    $site =~ s!(<a href=")(http://slashdot[^"]*?)(">)!$1$this?$2$3!gi;
    $site =~ s!<a href=http://slashdot\.org/([^>]*)>!<a href="$this?http://slashdot.org/$1">!gi;
    $site =~ s!<a href=/([^>]*)>!<a href="$this?http://slashdot.org/$1">!gi;
#  } else {
    #ask/. stuff only
  }
} elsif ($surl =~ /opensales\.org/i) {
    $site =~ s!white!black!i;
    $site =~ s!bgcolor="#FFFFFF"!bgcolor="#000412"!i;
    $site =~ s!href="/([^"]*)">!href="$this?http://www.opensales.org/$1">!gi;
    $site =~ s!"/images!"http://www.opensales.org/images!gi;
} elsif ($surl =~ /hotmail\.com/i) {
    $site =~ s/#000000/#C0F0D0/g;
} else {
    print <<END_0;
<html><head><title>PipzColourz Index</title></head><body bgcolor="#03070F"
text="#AEFEBE" link="#0000FF" vlink="#D000A0" alink="#FF0000"><center>
<h1>PCndx</h1><br><hr><br>
<h3><a href="$this?http://slashdot.org/">/. slashdot /.</a></h3>
<h3><a href="$this?http://www.opensales.org/">opensales</a></h3>
<h3><a href="http://hotmail.com/">hotmail</a></h3>
</center></body></html>
END_0
}
print $site;
