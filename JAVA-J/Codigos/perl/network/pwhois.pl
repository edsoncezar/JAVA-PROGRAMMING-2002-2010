 #!/usr/bin/perl -w
    # Go to a whois host and make a query, following a redirect
    # if it can.
    #
    # This steals network code from a web proxy perl script I wrote.
    #
    use strict;
    BEGIN { $ENV{PATH} = '/bin:/usr/bin' }
    use vars qw($EOL $tcpproto $id $host $dont $query $defhost $nsihost $VERSION);
    use Socket;
    use Carp;
    $VERSION = '1.0';
    $EOL = "\cm\cj";
    $tcpproto = getprotobyname('tcp');
    $dont = 0;
    $host = '';
    $defhost = 'rs.internic.net';
    $nsihost = 'whois.networksolutions.com';
    $id = $0;
    $id =~ s:.*/::;
    sub THEEND {
    my $signame = (shift or '(unknown)');
    die "Got SIG$signame ... exiting\n";
    } # &THEEND
    $SIG{INT} = 'main::THEEND';
    $SIG{TERM} = 'main::THEEND';
    while(defined($ARGV[0]) and substr($ARGV[0], 0, 1) eq '-') {
    if (($ARGV[0] eq '-h') or ($ARGV[0] eq '--host')) {
    shift;
    if ($#ARGV >= 0 and substr($ARGV[0], 0, 1) ne '-') {
    $host = shift;
    } else {
    print STDERR "$id: -h (--host) requires an argument\n";
    usage(2);
    }
    } elsif (($ARGV[0] eq '-d') or ($ARGV[0] eq '--dont-follow')) {
    shift;
    $dont = 1;
    } elsif ($ARGV[0] eq '--help') {
    &usage(0);
    } else {
    print STDERR "$0: $ARGV[0] not a recognized option\n";
    &usage(2);
    }
    }
    $query = shift;
    if (!defined($query)) {
    print STDERR "No query found\n";
    usage(2);
    } elsif (!$host) {
    if ($query =~ m'.@.') {
    $host = $nsihost;
    } else {
    $host = $defhost;
    }
    }
    my $nport = 43;
    my $ans;
    my $notdone = 1;
    while ($notdone) {
    print "[$host]\n";
    # Fetch the page
    $ans = &grab($host, $nport, \$query, \$EOL);
    if (!$dont and $ans =~ /\s+Whois\s+Server[:\s]+((?:[\w-]+\.)+\w+)\s/) {
    $host = $1; 
    } else {
    print $ans;
    $notdone = 0;
    }
    }
    exit(0);
    #####################################################
    # Grab an html page. Needs a remote hostname, a port number
    # a first line request (eg "GET / HTTP/1.0"), and the remainder
    # of the request (empty string if HTTP/0.9).
    sub grab ($$$$) {
    my ($remote, $port, $request, $heads) = @_;
    my ($iaddr, $paddr, $line);
    my $out = '';
    my $len;
    my $rc;
    if (!($iaddr = inet_aton($remote))) { 
    return &err404('unknown',"no host: $remote");
    }
    $paddr= sockaddr_in($port, $iaddr);
    if (!socket(SOCK, PF_INET, SOCK_STREAM, $tcpproto)) {
    return &err404('unknown', "socket: $!");
    }
    if (!connect(SOCK, $paddr)) {
    return &err404('unknown', "connect: $!");
    }
    $len = length($$request);
    $rc = syswrite(SOCK, $$request, $len);
    if ($rc != $len) {
    warn("request write to $remote was short ($rc != $len)\n");
    } else {
    $len = length($$heads);
    $rc = syswrite(SOCK, $$heads, $len);
    warn("heads write to $remote was short ($rc != $len)\n")
    	if ($rc != length($$heads));
    }
    while ($line = &saferead()) {
    $out .= $line;
    last if ($line =~ /^\015?\012?$/);
    }
    if ($out =~ /\nContent-Length:\s+(\d+)/) {
    read(SOCK,$out,$1,length($out));
    } else {
    # Back to line by line mode.
    while (defined($line = <SOCK>)) {
    $out .= $line;
    }
    }
    close (SOCK)|| die "close: $!";
    return $out;
    } # end &grab
    #####################################################
    # Attempt to read a line safely from SOCK filehandle.
    sub saferead () {
    my $line;
    eval {
    	local$SIG{ALRM} = sub { die 'timeout!' };
    	alarm 15;
    	$line = <SOCK>;
    	alarm 0;
    };
    if ($@ and $@ !~ /timeout!/) {warn("during socket read: $@\n")}
    return $line;
    } # end &saferead 
    #####################################################
    # Print an error message
    sub err404 ($$) {
    my $server_mess= shift;
    my $internal_mess = shift;
    print STDERR "An error occured: $internal_mess\n";
    if ($server_mess ne 'unknown') {
    print STDERR "Error continued: $server_mess\n";
    }
    return '';
    } # end &err404 
    #####################################################
    # Print a usage message. Exits with the number passed in.
    sub usage ($) {
    my $exit = shift;
    print <<"EndUsage";
    $0 usage:
    pwhois [options] query
    Options:
    	-d --dont-follow	don't follow redirects
    	-h --host 	 host	use host for the query
    Goes to a whois host and make a query, following a redirect
    if it can do so and hasn't been told not to.
    EndUsage
    exit($exit);
    } # end &usage 
    __END__
    =head1 NAME
    pwhois - make whois requests, following redirections
    =head1 README
    pwhois - make whois requests, following redirections
    =head1 DESCRIPTION
    pwhois goes to a whois host and make a query, following a redirect
    if it finds one unless instructed not to do so.
    =head1 USAGE
    	pwhois [options] query
    Options:
    =over 4
    =item *
    -d
    --dont-follow
    don't follow redirects
    =item *
    -h	host
    --host	host
    	
    use host for the query
    =back
    If a host is not specified, one of two defaults is used. For
    queries that have an at sign (@) with text on either side of
    it, whois.networksolutions.com is used. Otherwise rs.internic.net.
    When following redirects, pwhois will look for a 'Whois Server:'
    line in the whois output to know where to next go.
    =head1 PREREQUISITES
    The C<Socket> and C<Carp> modules are used.
    The C<strict>, C<vars>, and C<integer> pragma modules are also used.
    =head1 COREQUISITES
    No optional CPAN modules needed.
    =head1 OSNAMES
    All, hopefully.
    =head1 SEE ALSO
    L<whois>(1)
    =head1 COPYRIGHT
    Copyright 2000 by Eli the Bearded / Benjamin Elijah Griffin.
    Released under the same license(s) as Perl.
    =head1 AUTHOR
    Eli the Bearded originally wrote this to tool to deal with the
    redirections rs.internic.net suddenly started giving to whois
    requests.
    =head1 CAVEATS
    No protocol specification was examined in writing this, rather
    the behavior of L<whois>(1) was merely examined. This could mean
    that there are cases I don't handle write. Feel free to send
    patches or bugs to E<lt>eli+cpan@panix.comE<gt>.
    =head1 CATEGORIES
    =pod SCRIPT CATEGORIES
    Networking
    =cut

