#!/usr/bin/perl

# Send2VCL - v0.2
use Socket;

# setup
$f=$ARGV[0]; $type="application/octet-stream";
$dir=$ARGV[1]; $dir="." unless($dir);
$type="image/gif" if($f=~/\.gif$/i);
$type="image/jpeg" if($f=~/\.jpg/i);
print STDERR "--- Sending $f to VCL/$dir\n";
$fi=$f; $fi =~ s#^.+/([^/]+)$#$1#;

# Connect
$iaddr   = inet_aton("velar.ctrl-c.liu.se") ||
    die "Can't resolve VCL: $!";
$paddr   = sockaddr_in(80, $iaddr);

$proto   = getprotobyname('tcp');
socket(SOCK, PF_INET, SOCK_STREAM, $proto)  ||
    die "Can't set up connection to VCL: $!";
connect(SOCK, $paddr)    ||
    die "Can't connect to VCL: $!";

# Autoflush everything.
$old=select(SOCK); $|=1; select($old);

# Print the request
print SOCK <<EOF;
POST /artist-admin/upload.cgi HTTP/1.0
Referer: http://velar.ctrl-c.liu.se/artist-admin/admin.cgi
User-Agent: RedWolf VCL Art Admin v0.2 (send2vcl)
Host: velar.ctrl-c.liu.se
Accept: image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, image/png, */*
Accept-Language: en
Accept-Charset: iso-8859-1,*,utf-8
Authorization: Basic nothing-really-here-really
Content-type: multipart/form-data; boundary=---VCLADMIN
EOF

open(OUT,">/tmp/vcl.$$") || die "/tmp/vcl.$$: $!";
print OUT "-----VCLADMIN\r\n";
print OUT "Content-Disposition: form-data; name=\"uploaddir\"\r\n\r\n";
print OUT "$dir\r\n";
print OUT "-----VCLADMIN\r\n";
print OUT "Content-Disposition: form-data; name=\"/Artists/Kelly-Price\"; filename=\"$fi\"\r\n";
print OUT "Content-type: $type\r\n\r\n";

open(IN,"<$f") || die "Can't open $f: $!";
while(<IN>)
{
    print OUT;
}
print OUT "\r\n-----VCLADMIN--\r\n";
close(OUT);
$len=-s "/tmp/vcl.$$";

print SOCK "Content-Length: $len\r\n\r\n";
open(IN,"</tmp/vcl.$$") || die "Painfully!!! $!";
while(<IN>)
{
    print SOCK;
}

while(<SOCK>)
{
    print;
}

