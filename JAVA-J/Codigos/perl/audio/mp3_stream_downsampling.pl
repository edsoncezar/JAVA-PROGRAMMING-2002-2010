#!/usr/bin/perl
### WS radio proxy v0.1
### By Kelly "STrRedWolf" Price

### Code!
$ourver="0.1 beta - downsampling";
$d=$hard=$help=$log=$v=0;  #debug

$log=$v=1;

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
print STDERR "$$: pulling $url\n" if($v);

if($site =~ /^([^:]+):(\d+)$/)
{
	$remote=$1; $port=$2;
} else {
	$remote=$site; $port=80;
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


$conn=0; $lc=0; $first=1;
while(<SOCK>)
{
#	print STDERR "$$: Site went bang: $!\n" if($!);

    $l=$_; $lc++;
    tr/\r\n//d;
    last if(/^$/);
    $conn++ if(/^Connection:/);
    $cont=$1 if(/^Content-[lL]ength: (\d+)$/);
    if($first) {
	$cachethis=0 if(/^HTTP.* 4\d+/);
    }

    print $l;


    print STDERR "$$ << $l" if($d);
    print LOG "< $l" if($log);
    $first=0;
}

&err("500 Internal Server Error","Blank document returned.  ($!)")
    unless($lc);

print "Connection: close\r\n" unless($conn);
print $l;

open(OUT,"|lame -b 16 --mp3input - -");

if($cont)
{
    while(1)
    {
	$in=read SOCK, $buf, ($cont < 256 ? $cont : 256 );
	$cont -= $in;
	print OUT $buf;
	# print STDERR "$$ << $buf" if($d);
	print LOG "< $buf\n" if($log);
    }
} else {
    while(<SOCK>)
    {
	print OUT;
	print LOG if($log);
    }
}

close(SOCK);
close(OUT);
print STDERR "$$: Done with $url\n" if($v);


