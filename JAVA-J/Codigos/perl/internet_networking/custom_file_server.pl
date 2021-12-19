#!/usr/bin/perl -w
use IO::Socket;
use strict;
(@ARGV == 1 || @ARGV == 2) || die "usage: $0 port [filename]\n";
my ($port, $filename) = @ARGV;
my ($buffersize, $bytesread, $data, $remote, $hostinfo, $size) = (1024, 0);
my $server = IO::Socket::INET->new(
				   Listen    => SOMAXCONN,
				   LocalPort => $port,    
				   Reuse     => 1,        
				   Proto     => 'tcp' )   
    || die "can't open connection: $!";                   
while (defined($remote = $server->accept)) {              
    $remote->autoflush(1);                                
    $hostinfo = gethostbyaddr($remote->peeraddr, AF_INET);
    print STDERR "[Received connection from ", $hostinfo || $remote->peerhost," on $port]\n";
    $data = <$remote>;
    ($data =~ /^\[FILENAME: "(.+?\.*?)"\]/)                                                        
	? $filename = $1
	    : die "incorrect header format: missing filename\n";
    $data = <$remote>;
    ($data =~ /^\[SIZE: "(\d*)"\]/) || die "incorrect header format: file size not indicated:$!";
    $size = $1;
    (-e $filename)
	? (-w $filename) || die "connot replace file: $filename:$!" 
	    : (-w ".") || die "cannot write to working directory\n";
    open (FILE, ">$filename") || die "can't open output file $filename:$!";
    binmode FILE;
    print STDERR "[Retrieving $filename \($size bytes\)]\n";
    while(read($remote, $data, $buffersize)) { 
	print FILE $data; 
	$bytesread += length($data);
    }
    close(FILE) || die "can't close output file: $filename:$!";
    chmod 0555, $filename || die "cannot make output file readable:$!";
    print STDERR "[Finished receiving $filename, $bytesread bytes read]\n";
}
close($server) || die "can't close network connection on port $port:$!"; 
