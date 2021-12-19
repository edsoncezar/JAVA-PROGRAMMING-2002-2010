#!/usr/local/bin/perl -w
#-*-perl-*-
#

=pod

=head1 NAME

B<ping> -- send ICMP ECHO_REQUEST packets to network hosts

=cut

use strict;
use Net::Ping;
use Getopt::Std;
use Sys::Hostname;
use vars        qw ( %Option %Statistics );
use Time::HiRes qw ( gettimeofday tv_interval );

my ($VERSION)          = '$Revision: 1.0 $' =~ /([.\d]+)/;
my $warnings           = 0;

                                        # print statistics on ^C if supported
#$SIG{INT}              = \&PingStatistics;

$SIG {__WARN__}        = sub {          # Print a usuage message on an unknown
    if ( substr (                       # option.  Borrowed from abigail.
		 $_ [0], 
		 0, 
		 14
		) eq "Unknown option" ) { die "Usage" };
    require File::Basename;
    $0                 = File::Basename::basename ( $0 );
    $warnings          = 1;
    warn "$0: @_";
};

$SIG {__DIE__} = sub {
    require File::Basename;
    $0                 = File::Basename::basename ( $0 );
    if ( substr ( 
		 $_ [0], 
		 0,  
		 5
		) eq "Usage" ) {
        die <<EOF;
$0 (Perl bin utils) $VERSION
$0 [ -aAnq ] [ -c count ] [-i wait ] [ -p protocol ] [ -s packetsize ]
     [ -w maxwait ] host
EOF
    }
    elsif ( substr ( 
		    $_ [0], 
		    0,  
		    6
		   ) eq "Exited" ) {
        die <<EOF;
$0: Exited on ^C
EOF
    }
    die "$0: @_";
};

=pod

=head1 SYNOPSIS

B<ping> [ -QaAnq ] [ -c count ] [ -i wait ] [ -p protocol ] [ -s packetsize ] 
     [ -w maxwait ] I<host>

=head1 DESCRIPTION

B<ping> tests whether a remote host can be reached from your computer.
This simple function is extremely useful as the first step in testing
network connections, B<ping> sends a packet to the destination host
with a timestamp.  The destination host sends the packet back.
B<ping> calculates the time difference and displays the data.

This test is independent of any application in which the original
problem may have been detected. B<ping> allows you to determine whether further
testing should be directed toward the network connection or the
application. If B<ping> shows that packets can travel to the remote
system and back, the isse may be application related. If packets can't
make the round trip, the network may be at fault.  Test further.

The options are as follows:

=head2 OPTIONS

=head3 note

This B<ping> implementation does support ~strange~ behavior some other
B<ping>'s exhibit due to answering the question "Where on the command
line should the destination host be listed?"  Some, like Solaris and
Win32, answer this quandry by not caring if the host is the zeroth
(first, if you like) option or the last option.  We don't care either,
and accomplish this zest for life by performing a quick
'begin-with-hyphen?' test on the primary argument.  If no hyphen,
assume it is the destination host and the remainder are options.  IF
we do see a hyphen, assume the last argument is the destination host
and gather all the prior stuff as options.

Please don't think you can stick the destination host anywhere.  If it
is not at the start or end, it will be ignored and the syntax error
will appear.

=cut

my (                                    # set up excessive flexibility
    $IpAddress,                         # a couple of testers commented that
    $HostName,                          # allowing the destination host as the
    $LocalHost                          # zeroth or else last argument is a 
   );                                   # 'good thing'

die "Usage"
  unless $ARGV[0];
                                        # some 'ping' implementations allow
                                        # destination host at the beginning
unless ( $ARGV[0]      =~ /^-/ ) {      # or end of the options at the shell
    $IpAddress         = shift @ARGV;   # so, if the first option does not
}                                       # start w/ '-', assume its a hostname

getopts (                               # get our options
	 'aAc:i:np:qQs:w:',              # see the pod doc below for details
	 \%Option                       # store them in our hash
	);

$SIG{'INT'}  = \&PingStatistics;
$IpAddress             ||= $ARGV[0];    # get $ARGV[0] after 'getopts' if we
$HostName                = $IpAddress;  # didn't get it before
$LocalHost               = hostname;    # we see the '-n' option for beauty

=pod

=over

=item -a

Audible.  Include a bell (ASCII 0x07) character in the output when 
any packet is received.

=item -A

Audible.  Include a bell (ASCII 0x07) character in the output when 
any packet is not received.

=item -c count

Stop after sending (and receiving) count ECHO_RESPONSE packets.  By
default, B<ping> will go continuously until interrupted.  On most
systems, the statistics will display on a SIGINT (CTRL-C or ^C).  But
on systems where SIGINT cannot be trapped (Win32, for example) will
simply end.

=item -i wait

Wait I<wait> miliseconds between sending each packet.  The default is
to wait for 1000 ms (one second) between each packet.  The wait time
may be fractional.

=item -n

Numeric output only.  No attempt will be made to lookup symbolic names
for host addresses.  This option cannot be performed unless the IP
address is offered on the command line and will fail otherwise.

=item -p protocol

B<ping> with I<protocol>.  Valid protocols are C<icmp>, C<tcp>, or
C<udp> for pings.  C<tcp> is unavailable on systems without I<alarm>
functionality.  C<icmp> is the default.

=item -Q     

Somewhat quiet output.  Don't display ICMP error messages that
are in response to our query messages.

=item -q     

Quick and very quiet like the default Solaris B<ping> response stating 
up or down only.
print ReadKey(0) , "\n";

=item -s packetsize

Specifies the number of data bytes to be sent.  The default is 64.

=item -w maxwait

Specifies the number of miliseconds to wait for a response to a packet
before transmitting the next one.  The default is 10000 ms (ten seconds).

=back

=head2 ERRORS

I<unknown host>

The destination host's name cannot be resolved by name service into an
IP address.

I<destination host unreachable>

The local system does not have a route to the remote system or the
remote system did not respond.  The remote host may be down.  The
remote host may be incorrectly configured.  A gateway or curcuit
between the local host and the remote host may be down.  Test further.

=cut

die "Usage" unless $IpAddress;          # syntax on no hostname
die "Usage" if (                        # makes no sense to ping by name
		$Option{'n'}            # yet not allow an IP lookup
		and $IpAddress =~ /[A-Za-z]/o
	       );

=pod

=head1 EXAMPLES

In the following example, B<ping> checks the network status of a
host. To check that www.yahoo.com can be reached from almond, we send
five 64-byte packets with the following command:

 C:\> ping -c 5 www.perl.com
 PING www.perl.com (208.201.239.50) from 192.168.2.101: 64 bytes of data.
 64 bytes from 208.201.239.50: icmp_seq=0, time=100.00 ms
 64 bytes from 208.201.239.50: icmp_seq=1, time=110.00 ms
 64 bytes from 208.201.239.50: icmp_seq=2, time=100.00 ms
 64 bytes from 208.201.239.50: icmp_seq=3, time=110.00 ms
 64 bytes from 208.201.239.50: icmp_seq=4, time=101.00 ms

 --- www.perl.com ping Statistics ---
 5 packets transmitted, 5 packets received, 0% packet loss
 round-trip (ms): min/avg/max = 100.00/104.20/110.00

The sequence in which the packets are arriving, as shown by the ICMP
sequence number (C<icmp_seq>) displayed for each packet will show
dropped packets if the numbering is out of sequence..

How long it takes a packet to make the round trip is displayed in
milliseconds after the string C<time=>.  The lower the number the
better.  Note that slow connections, like dialup or VPN, cause high
response numbers.

The percentage of packets lost is displayed in a summary line at the end
of the B<ping> output with the minimum, average, and maximum round-trip
times.

The following is an example of the quick ping:

 C:\> ping www.perl.com -p
 www.perl.com is alive.

This method simply checks if a host is up or not without the default
output's verbosity.  This is similar to the default action of Solaris'
B<ping>.

=head1 ENVIRONMENT

The working of B<ping> is not influenced by any environment variables.

However, tcp pings cannot function on systems (like Win32) with no I<alarm>
function.

=cut

if (                                    # alarm() fails on some systems, so
    $Option{'p'}                        # 0. See if -p was keyed at the shell
    and $Option{'p'} eq "tcp"           # 1. Did it have 'tcp' as an arg?
    and ! eval { alarm 1 }              # 2. eval 'alarm' for kosher-ness
   ) {
    print $0 , " protocol " , $Option{'p'} , " is invalid on " , $^O;
    print " due to a possibly unimplemented alarm() function\n";
    die "Usage";                        # die if we don't support 'alarm'
}
                                        # --- Defaults ---
$Option{'c'}         ||= "-1";          # ping continuously
$Option{'i'}         ||= "1000";        # wait 1 second between pings
$Option{'s'}         ||= "64";          # send 64 bytes per packet
$Option{'w'}         ||= "10000";       # wait 10 seconds for a response
$Option{'p'}         ||= "icmp";        # icmp for ping

$Statistics{'i'}       = "0";           # stats in a global hash in case of
$Statistics{'loss'}    = "0";           # a trapped SIGINT so PingStatistics()
$Statistics{'max'}     = "0";           # can get at it without passing args

(
 $HostName,                             # pass 
 $IpAddress,                            # and return real values
 $LocalHost                             # for source and destination
)                      = ResolveName ( 
				      $IpAddress,
				      $LocalHost
				     )
  unless $Option{'n'};                  # unless we see '-n'

                                        # setup the 'ping' with protocol,
                                        # waittime (remember I work in ms
                                        # tho Net::Ping takes seconds) divided
                                        # by 1000, and packet size
my $Ping               = Net::Ping->new (
					 $Option{'p'},
					 $Option{'w'} / 1000,
					 $Option{'s'}
					)
                                        # sometimes you must be root-like
  or die "Cannot ping.  Are you priviledged?\n";

$Statistics{'i'}                = 0;
QuickPing() 
  if $Option{'q'};                      # run this subroutine and die if '-q'
FullPing();                             # otherwise do a full ping display
PingStatistics();                       # and give us stats if we provide '-c';

sub FullPing {
    my $sum                     = "0";  # tally times for stats
                                        # print the header
    print uc $0 , " " , $HostName , " (" , $IpAddress , ") from " , $LocalHost;
    print ": ", $Option{'s'} , " bytes of data.\n";
                                        # ping until we hit 'count'
    until ( $Statistics{'i'}   == $Option{'c'} ) {
                                        # timestamp
	my $time0               = [gettimeofday];
	if ( $Ping->ping ( $IpAddress ) ) {
	                                # math for round-trip
	    my $elapsed         = tv_interval ( $time0 ) * 1000;
                                        # print the packet's info
	    print  "\a"
	      if $Option{'a'};
	    print  $Option{'s'} , " bytes from $IpAddress: ";
	    print  $Option{'p'} , "_seq=" , $Statistics{'i'} , ", time=";
	    printf "%.2f ms\n", $elapsed;
                                        # if we returned slowest, save
	    $Statistics{'max'}  = $elapsed
	      unless $elapsed < $Statistics{'max'};
                                        # if we returned fastest, save
	    $Statistics{'min'}  = $elapsed
	      if ( 
		  ! $Statistics{'min'}  # especially if this is the first 
		  or $elapsed < $Statistics{'min'} 
		 );
                                        # tally times
	    $Statistics{'sum'} += $elapsed;
	}
	else {
	    $Statistics{'loss'}++;      # if we don't return, tally that
	    # and print a message for comfort
	    print  "\a"
	      if $Option{'A'};
	    print "From " , $LocalHost , ": destination host unreachable\n"
	      unless $Option{'Q'};
	}
	$Statistics{'i'}++;
	sleep $Option{'i'} / 1000;      # sleep before going again
    }
    $Ping->close();                     # if we're done, close the ping
}

sub QuickPing {                         # is remote up or down only
    print $HostName , " is alive.\n" 
      if $Ping->ping( $IpAddress )
      or die $HostName , " is unreachable\n";
    $Ping->close();                     # if we're done, close the ping
    exit 1;
}

sub PingStatistics {
                                        # Solaris needed this to trap SIGINT
    $SIG{INT}          = \&PingStatistics
      unless $^O =~ /MSWin/;
                                        # math on %Statistics for display stats
    my $received       = $Statistics{'i'} - $Statistics{'loss'};
    my $stat_loss      = ( $Statistics{'loss'} /  $Statistics{'i'} * 100 );
                                        # print the top stat line
    print  "\n--- " , $HostName , " " , uc $0 , " Statistics ---\n";
    print  $Statistics{'i'} , " packets transmitted, " , $received;
    print  " packets received, " , $stat_loss , "\% packet loss\n";

    exit 
      if $stat_loss == "100";           # if no packets made it forget the rest
    $Statistics{'avg'} = $Statistics{'sum'} / $Statistics{'i'};
                                        # print the bottom stat line
    print  "round-trip (ms): min/avg/max = ";
    printf "%.2f/%.2f/%.2f\n", 
      $Statistics{'min'}, 
	$Statistics{'avg'}, 
	  $Statistics{'max'};
    die "Exited"                        # Solaris needs this too or will
      unless $^O =~ /MSWin/;
    exit;
}                                       # ping without stopping

sub ResolveName {
    require Socket;                     # require versus use in case '-n'
    my (
	$remote,
        $local
       )               = @_;            # gather
    my (
	$name,
	$address,
	$packed
       );
                                        # get our local IP
    $local             = Socket::inet_ntoa ( Socket::inet_aton ( $local ) );
                                        # passed a hostname?
    if ( $remote       =~ /[A-Za-z]/o ) {
	$name          = $remote;
                                        # get the remote IP, packed
	$packed        = Socket::inet_aton ( $name )
                                        # can't with w/o IP, so die
	  or die "unknown host $name\n";
                                        # unpack the IP into dotted quads
	$address       = Socket::inet_ntoa ( $packed )
                                        # ditto
	  or die "unknown host $name\n";
    }
                                        # Dotted quads (most IP addresses)
                                        # fails on IPs like 127.1 and 2130706433
    elsif ( $remote    =~               # sanity check the $remote IP address
	    m{                          # this is so cool, I'm still disecting
        ^  ( \d | [01]?\d\d | 2[0-4]\d | 25[0-5] )
       \.  ( \d | [01]?\d\d | 2[0-4]\d | 25[0-5] )
       \.  ( \d | [01]?\d\d | 2[0-4]\d | 25[0-5] )
       \.  ( \d | [01]?\d\d | 2[0-4]\d | 25[0-5] )
        $                               # from the Perl Cookbook, Recipe 6.23,
    }xo                                 # pages 218-9, as fixed in the 01/00
	  ) {                           # reprint
	$address       = $remote;
                                        # get the remote IP, packed
        $packed        = Socket::inet_aton ( $address );
                                        # unpack the hostname
	$name          = gethostbyaddr ( 
					$packed, 
					Socket::AF_INET() 
				       )
	  or $name     = $address;      # or else work with the IP address only
    }                                   # no sense in dying here
    
    else {                              # die if $remote isn't a valid IP
	die "$remote: invalid hostname or address\n";
    }
    return $name, $address, $local;     # return
}

exit 1;

__END__

=pod

=head1 BUGS

B<ping> suffers from no known bugs at this time.  But see the ENVIRONMENT
section for more detail.

Some environments (like Win32) cannot trap SIGINT (CTRL-C) to display
the summary lines on a break.

Believed to work on Windows NT, Windows 2K, Solaris, Linux, and NetBSD.

=head1 STANDARDS

I might be RFC compliant.  I'm still reading the related RFCs for
icmp.  ~yawn~

=head1 REVISION HISTORY

    ping
    Revision 1.0  2000/06/29 10:34:03  idnopheq
    Initial revision

=head1 AUTHOR

The Perl implementation of B<ping> was written by Dexter Coffin,
I<idnopheq@home.com>.

=head1 COPYRIGHT and LICENSE

This program is copyright by Dexter Coffin 2000.

This program is free and open software. You may use, copy, modify, distribute,
and sell this program (and any modified variants) in any way you wish,
provided you do not restrict others from doing the same.

=head1 SEE ALSO

=head1 NEXT TOPIC

=for html
<a href="os.html">ps</a><p>


=cut

