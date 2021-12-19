   #!/usr/bin/perl
    use IO::Socket;
    my ($line, $port, $sock, @servers);
    my $VERSION='1.0';
    ($server = $ARGV[0]) || &usage;
    $begin = ($ARGV[1] || 0);
    for ($port=$begin;$port<=65000;$port++)	{
    	$sock = IO::Socket::INET->new(PeerAddr => $server,
    				 	PeerPort => $port,
    				 	Proto => 'tcp');
    	if ($sock)	{
    		print "Connected on port $port\n";
    	} else {
    		# print "$port failed\n";
    	}
    } # End for
    sub usage	{
    	print "Usage: portscan hostname [start at port number]\n";
    	exit(0);
    }
    =head1 NAME
    portscan - Scans a host on TCP ports to determine what is listening
    =head1 DESCRIPTION
    Determines on which TCP ports a host is listening for incoming connections.
    Useful for determining what services are running on a server.
    =head1 PREREQUISITE
    uses IO::Socket
    =head1 COREQUISITE
    None
    =head1 README
    Determines on which TCP ports a host is listening for incoming connections.
    Useful for determining what services are running on a server.
    =pod OSNAMES
    MSWin32, Unix
    =pod SCRIPT CATEGORIES
    Networking
    =cut

