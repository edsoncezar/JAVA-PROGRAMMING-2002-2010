#!/usr/bin/perl
### WSproxy v0.8
### By Kelly "STrRedWolf" Price

### A "Tell it to" filter/cache web proxy.  Use it with inetd or netpipes.

### Configuration

## URL Grep lists.  Use a regexp to match on URL (no need for http://)

# Banned sites.  Examples of ad sites below.
@adban=(
	"(ads|adcontent)\.gamespy\.com/",
	"/gmcard/",
	"/us\.yimg\.com/a/",
	"UGODirect",
	"UGOPromotions",
	"\.inet1\.com/html-bin/adselect",
	"ad[0-9]+\.focallink\.com",
	"adbureau\.net",
	"adengine\.theglobe\.com",
	"adfarm\.mediaplex\.com/ad/",
	"adforce\.imgis\.com",
	"adimages\.gamespy\.com",
	"ads.+\.focalink\.com(:\d+)?/",
	"ads\.admonitor\.net/",
	"ads\.fp\.sandpiper\.net/",
	"ads\.link4ads\.com/",
	"ads\.washingtonpost\.com",
	"ads\.web\.aol\.com",
	"adserver\.ugo\.com(:80)?/image.ng",
	"advertising\.com",
	"advertizing\.com",
	"banners\.wunderground\.com/",
	"bannervip\.webjump\.com",
	"graphics\.theonion\.com/ad_graphics/",
	"image\.avea\.a\d+\.avenuea\.com/Banners/",
	"image\.avenuea\.com/Banners",
	"image\.ugo\.com/ads",
	"images\.cnn\.com/ads",
	"images\.v3\.com/ads/",
	"img\.adflight\.com/",
	"leader\.linkexchange\.com",
	"members\.tripod\.com/adm/popup/",
	"mp3\.com/RealMedia/ads/",
	"mplayer\.com/graphics/ad_sales/",
	"ms\.akamai\.net/.*/ads/",
	"ngadclient\.hearme\.com/image.ng/",
	"retaildirect\.realmedia\.com",
	"valinux\.com/images/ads/",
	"view\.avenuea\.com/view/burst",
	"www\.adsynergy\.com/cgi-bin/adjuggler",
	"www\.burstnet\.com/cgi-bin/ads",
	"www\.palmgear\.com/banners/",
	"www\.palminfocenter\.com/images/ads/",
	"www\.thefunnypapers\.com/fpcgi/butnet/ads",
	"ad-adex[0-9]+\.flycast\.com/"
	);

@jsban=(
	"\.doubleclick\.net",
	"ad\.preferences\.com/jscript",
	"adserver\.(ugo|ign)\.com",
	"unlikeminerva.com/dropdown.js",
	"js-adex[0-9]+\.flycast\.com/",
	"hitbox\.com/",
	"www\.meixler-tech\.com/",
	"www\.click2net\.com/",
	"\.efront\.com/adserve\.jscript/",
	"party2001\.js"
	);

# Cached sites.  If it doesn't match nocache, and matches cached, it'll
# save any matched URL.
@cached=(
	 "images\.(slashdot\.org|freshmeat\.net|themes\.org)/",
	 "freshmeat\.net/img/",
	 "graphics\.userfriendly\.org/images/",
	 "www\.geocities\.com/strredwolf/stalag/BigPanda\.gif",
	 "www\.(pvponline|newshounds|kevinandkell|brunothebandit|suburbanjungle|theclassm)\.com/images/",
	 "(perlmonks\.org|freshmeat\.net|bearchive\.com|palmgear\.com)/images/",
	 "(howto\.tucows\.com|sluggy\.com|cyantian\.net|gpf-comics\.com)/images/",
	 "(distributed\.net|planetquake\.com|superosity\.com|krazylarry\.com)/images/",
	 "(efax\.com|4\.3\.234\.209/forum|nukees\.com)/images/",
	 "pics\.ebay\.com",
	 "graphics\.theonion\.com/.*images/",
	 "gs\.cdnow\.com/",
	 "www\.palminfocenter\.com/images/[^/]+",
	 "machinima\.com/(backgrounds|images)/",
	 "www(apps)?\.ups\.com/images/",
	 "\.yimg\.com/.*/(space|line|pls)",
	 "\.yimg\.com/i/",
	 "www\.op\.net/~eparkin/doemain/(title|background|buttons)/",
	 "^washingtonpost.com/wp-srv/images/",
	 "(jpager|yog\d*)\.yahoo\.com/",
	 "us.yimg.com/i/(pim|mail)/",
	 "velar\.ctrl-c\.liu\.se/vcl/cgi-bin/.*\.gif",
	 "misterart\.com/pix/",
	 "sourceforge\.net/.*/images/",
	 "supermegatopia\.com/[^/]+\.(gif|css|js)",
	 "supermegatopia\.com/.*/(back|home|next)\.gif",
	 "www\.vistaprint\.com/.*/images/.*\.(gif|jpg|png)",
	 "www\.scottmccloud\.com/.*\.(gif|jpg|png)",
	 "\.barnesandnoble\.com/gresources/",
	 "graphics\.studentadvantage\.com/",
	 "www\.(superosity|brunothebandit)\.com/.*\.gif",
	 "www\.cyantian\.net/(images|buttons)/",
	 "www\.cafepress\.com/cp/?.*/images/"

	);

@nocache=(
	  "mycomix\.toonscape\.com/images/db",
	  "www\.palminfocenter\.com/images/.*/",
	  "us\.yimg\.com/a",
	  "www\.cyantian\.net/images/today",
	  "www\.(superosity|brunothebandit)\.com/comics/"
	  );

%pcached=(
	  "velar\.ctrl-c\.liu\.se/vcl/Artists/New/.thumbnail/" => 3,
	  "g\.akamai\.net/" => 1,
	  "\.akamai\.net/.*/i/" => 1,
	  "yerf\.com/[^/]+/data/.*\.(gif|jpg|png)" => 2,
	  "\.keen(spot|space)\.com/images/" => 2,
	  "www.\(superosity|brunothebandit)\.com/.*\.gif" => 3,
	  "www\.keenspace\.com/forums/.*\.gif" => 3,
	  "amtrak\.com/images/" => 3,
	  "www\.noradsanta\.org/images/"=>3
	  );

# Where to put our info.  It'll put the files under this directory.
# The URL (w/o http://) will serve as a directory for this.
$info="/home/tygris/.wsproxy";
# The cache will be put here under $info/cache
# a delay db will be put here too.

# Wait time in seconds for a lock to die.
$deadlock=120; # two minutes

### Code!
$ourver="0.8 beta - filtering/caching";
$d=$hard=$help=$log=$v=0;  #debug

foreach $j (@ARGV)
{
    $d++ if($j eq "-d");
    $v++ if($j eq "-v");
    $hard++ if($j eq "-a");
    $help++ if($j eq "-h");
    $log++ if($j eq "-l");
    $rand++ if($j eq "-f");
    $limit++ if($j eq "-r");
    $cflush++ if($j eq "-c");
}

if($help)
{
    print STDERR <<EOF;
$0 options:
    -d  debug
    -v  verbose
    -a  hard fail on banned URLs
    -l  log interaction
    -f  futz around with pulling images a bit
    -r  try to rate limit (not implemented)
    -c  Flush the TempCache and exit.
EOF
    exit 1;
}

sub flushcache
{
    # Find files...
    @d=(); $i=0;
    open(IN,"find $info/pcache/. -type f -print |") || die "Find died: $!";
    while(<IN>)
    {
	chop; $f=$_; $a=-M $f;
	foreach $j (keys %pcached)
	{
	    $d[$i++]=$f if($f =~ /$j/ && $a > $pcached{$j});
	}
    }
    close(IN);
    foreach $j (@d)
    {
	print "Deleting $j\n" if($d); 
	unlink $j;
    }

    # Clean out empty directories.
    $i=1;
    while($i>0)
    {
	@d=(); $i=0;
	open(IN,"find $info/pcache/. -type d -a -empty -print |") || die "Find died: $!";
	while(<IN>)
	{
	    chop; $d[$i++]=$_;
	}
	close(IN);
	foreach $j (@d)
	{
	    print "Flushing $j\n" if($d);
	    rmdir $j;
	}
    }
    system "touch $info/pcache/stamp";
    exit 0;
}
&flushcache if($cflush);

# The other end.  Clear any cache when it's due.
$pcf=-M "$info/pcache/stamp";

if($pcf >= 1)
{
    print STDERR "$$: Forking a flush...\n" if($v);

    unless(fork)
    {
	exec "$0 -c";
    }
}

# Requirements
use Socket;
$|=1;

# Error subroutine.  If we run into trouble, we do it here.
sub err {
	my ($e,$r)=@_;	

	# Quickly suck in some junk just in case...
	# BUG:  It just delays netscape somehow.
        # while(<>) {;}
	
	print STDERR "$$: $e -- $r\n" if($v);

	print "HTTP/1.0 $e\r\n";
	print "Content-Type: text/html\r\n";
	print "Connection: close\r\n\r\n";
	print "<html><head><title>$e</title></head><body>\r\n";
	print "<h1>$e</h1><BR><P>";
	print "WSProxy cannot fulfill your request -- $r<P>$req<P>\n";
	print "<hr><br><i>WSProxy version $ourver<br></I>\r\n";
	exit 0;

}

sub nothing {
    print STDERR "$$: Sending nothing\n" if($v);

    print "HTTP/1.0 200 OK\r\n";
    print "Content-Type: text/html\r\n";
    print "Connection: close\r\n\r\n";
    print "// Nothing. \r\n";
    print "\r\n";
    exit 0;
}

sub blank {
	print STDERR "$$: Sending blank\n" if($v);

	print "HTTP/1.0 200 OK\nConnection: close\n";
	print "Content-type: image/gif\n\n",
		pack "H*", "47494638396101000100800000ffffff" .
			"00000021f90401000000002c00000000" .
			"010001000002024401003b";
}


# Generic file send routine.
sub sendthis {
    my ($file)=@_;
    my ($type);

    $_=$file;
    $type="text/html";
    $type="image/gif" if /\.gif$/ ;
    $type="image/jpg" if /\.jpg$/ ;
    $type="image/png" if /\.png$/ ;

    while( -e "$file.LOCK" )
    { 
	unlink "$file.LOCK" if(-M "$file.LOCK" > $deadlink);
	sleep 3;
    }

    open(IN,"<$file") || &err("500 Internal Server Error","Cannot open cached file ($!)");

    print STDERR "$$: sending $file\n" if($v);
    
    print "HTTP/1.0 200 OK\r\n";
    print "Content-Type: $type\r\n";
    print "Connection: close\r\n\r\n";
    while(<IN>)
    {
	print;
    }
    exit 0;
}

# Open up a logfile.
if($log)
{
    $log='' unless(open(LOG,">/tmp/wsproxy.$$"));
}

# Read the first line of the request and pharze it.
$_=$req=<STDIN>; tr/\n\r//d;
exit 0 if(/^$/);

m#^(GET|POST|HEAD) http://([^/]+)(/.*) (HTTP\S+)$#i;
$type=$1; $site=$2; $page=$3; $ver=$4;

print LOG "> $req" if($log);

$url=$site.$page;

#search banned
foreach $j (@adban)
{
    if($url =~ /$j/)
    {
	&err("400 Bad Request","Site $site is banned.") if($hard);
	print STDERR "$$: $url banned.\n";
	&blank;
    }
}

foreach $j (@jsban)
{
    if($url =~ /$j/)
    {
	&err("400 Bad Request","Site $site is banned.") if($hard);
	print STDERR "$$: $url banned.\n";
	&nothing;
    }
}

#search cached
$cachethis=0;
unless($url =~ /\?/)
{
    foreach $j (@nocache)
    {
	$really++ if($url =~ /$j/);
    }
    unless($really)
    {
	# Peramently cache?
	foreach $j (@cached)
	{
	    if($url =~ /$j/)
	    {
		$file="$info/cache/$url";
		&sendthis($file) if (-e $file && -s $file);
		$cachethis=$file;
		print STDERR "$$: Caching $url\n" if($v);
	    }
	}

	# Temp cache.
	if(!$cachethis)
	{
	    foreach $j (keys (%pcached))
	    {
		next if($url !~/$j/);
		next unless($pcached{$j});
		$file="$info/pcache/$url";

		$s=-s $file;
		$m=-M $file;
		if($s && $m < 3) {
		    &sendthis($file);
		}
		unlink $file if($s);
		$cachethis=$file;
		print STDERR "$$: TempCaching $url\n" if($v);
	    }
	}
    }
}

print STDERR "$$: pulling $url\n" if($v);

if($site =~ /^([^:]+):(\d+)$/)
{
	$remote=$1; $port=$2;
} else {
	$remote=$site; $port=80;
}

# Futz around if it's a graphic file.  One attempt to limit Netscape's spammish
# tendencies
if($rand && $page =~ /\.(gif|jpg|png)$/i && !$cachethis)
{
    print STDERR "$$: Delaying...\n";
    sleep (int (rand 2) + 1);
}

# Call out and strangle someone
$iaddr   = inet_aton($remote) ;
# print STDERR "$$:  $remote -> $iaddr\n" if($v);
&err("400 Bad Request","Site $remote can't be resolved ($!).")
    unless($iaddr);
$paddr   = sockaddr_in($port, $iaddr);

$proto   = getprotobyname('tcp');
socket(SOCK, PF_INET, SOCK_STREAM, $proto)  || &err("500 Internal Server Error","Something went wrong trying to connect to $remote ($!).");
connect(SOCK, $paddr)    || &err("500 Internal Server Error","Cannot connect to $remote ($!)");

$old=select(SOCK); $|=1; select($old);

print SOCK "$type $page $ver\r\n"; # Xmission's webserver is funky

$lo=0;
while(<STDIN>)
{
    $ok=1;
    $l=$_;
    tr/\r\n//d;
    $lo=2 if(/^$/);
    $cont=$1 if(/^Content-[lL]ength: (\d+)$/);
    $nocache++ if(/^Pragma: no-cache/);
    $ok=0 if(/^Proxy-Connection:/);
    print SOCK "Connection: close$l" if($lo);
    print SOCK "$l" if($ok);
    print STDERR "$$ >>> $l" if($ok && $d);
    print LOG "> $l" if($ok && $log);
    last if($lo);
}

if($cont)
{
    while($cont)
    {
	$in=read STDIN, $buf, ($cont < 256 ? $cont : 256 );
	$cont -= $in;
	print SOCK $buf;
	print STDERR "$$ >>> $buf" if($d);
	print LOG "> $buf\n" if($log);
    }
}
print STDERR "$$ XXX\n" if($d);


$conn=0; $lc=0;
while(<SOCK>)
{
#	print STDERR "$$: Site went bang: $!\n" if($!);

    $l=$_; $lc++;
    tr/\r\n//d;
    last if(/^$/);
    $conn++ if(/^Connection:/);
    $cont=$1 if(/^Content-[lL]ength: (\d+)$/);
    print $l;
#    print STDERR "$$: Client went bang: $!\n" if($!);
    print STDERR "$$ << $l" if($d);
    print LOG "< $l" if($log);
}

&err("500 Internal Server Error","Blank document returned.  ($!)")
    unless($lc);

print "Connection: close\r\n" unless($conn);
print $l;

if($cachethis)
{
    $file=$cachethis;
    $file=~m#^(.*/)[^/]+$#; $dir=$1;

    print STDERR "$$: Saving $url\n" if($v);

    if(! -e $dir)
    {
	system "mkdir -p $dir";
    }
    
    while(-e "$file.LOCK")
    {
	# Hmmm... already being pulled.
        unlink "$file.LOCK" if(-M "$file.LOCK" > $deadlink);
	sleep 3;

    }
	

    if(open(OUT,">$file.LOCK"))
    {
	print OUT "$$\n";
	close(OUT);
	$cachethis=0 unless( open(OUT,">$file") );
	unlink "$file.lock" unless($cachethis);
    }
}

if($cont)
{
    while($cont)
    {
	$in=read SOCK, $buf, ($cont < 256 ? $cont : 256 );
	$cont -= $in;
	print $buf;
	print OUT $buf if($cachethis);
	# print STDERR "$$ << $buf" if($d);
	print LOG "< $buf\n" if($log);
    }
} else {
    while(<SOCK>)
    {
	print;
	print OUT if($cachethis);
	print LOG if($log);
    }
}

close(SOCK);
unlink "$file.LOCK" if($cachethis);
close(OUT);
print STDERR "$$: Done with $url\n" if($v);

