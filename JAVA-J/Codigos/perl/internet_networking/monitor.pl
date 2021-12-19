#!usr/bin/perl -w
    use strict;
    use IO::Socket;
      ##############################################################################
    # DESCRIPTION:
    # Monitors port availability and restarts process if port isn't available.
    ##############################################################################
    # USAGE:
    # 	pmtr <host> <remote port> <application path>
    ##############################################################################
    my $host_ip 	= $ARGV[0] || &usage;
    my $host_port= $ARGV[1] || &usage;
    my $applicationpth = $ARGV[2] || &usage;
    my $sock = new IO::Socket::INET(
    	PeerAddr => $host_ip,
    	PeerPort => $host_port,
    Proto => "tcp");
    			
    if(!$sock){
    	my $restart = system($applicationpth);
    	exit(0);
    }
    exit(0);
    sub usage{
    	print "usage: pmtr <host_ip> <host_port> <application_path>\n";
    	print "\tHOST_IP= The IP address of the remote host to monitor\n";
    	print "\tHOST_PORT = The remote port to monitor\n";
    	print "\tAPPLICATION_PATH= Path to application to restart if failure\n\n";
    	exit(0);
    } 	

