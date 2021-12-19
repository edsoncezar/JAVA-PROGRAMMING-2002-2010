#!/usr/bin/perl -w
use IO::Socket;
use strict;

unless (@ARGV >= 3) { die "usage: $0 server port file(s)\n"; }
my ($server, $port, @file_list) = @ARGV;
my ($buffersize, $buffer, $data, $file, $connection) = (1024);

if ($file_list[0] eq '*') {
    @file_list = get_file_list('.');
}
foreach $file (@file_list) {
    my $size = (stat($file))[7];
    my  $retry_count = 0;
    open(FILE, $file);
    binmode FILE; 
    until ($connection = IO::Socket::INET->new(Proto => "tcp",
					       PeerAddr => $server,
					       PeerPort => $port) || (++$retry_count) > 30) { 
    }
    $connection->autoflush(1);
    print STDERR "[Connected to $server:$port]\n";
    print $connection "[FILENAME: \"_$file\"]\012";
    print $connection "[SIZE: \"$size\"]\012";
    while (read(FILE, $data, $buffersize)) {
	print $connection $data;
    }
    close(FILE);
    sleep(1);
}

sub get_file_list {
    my ($dir) = @_;
    my ($file, @file_list);
    opendir(DIR,$dir) || die "cannot open directory: $!";
    readdir(DIR);
    readdir(DIR);
    while (defined( $file = readdir(DIR) )) {
	push(@file_list, $file);
    }
    return @file_list;
}
