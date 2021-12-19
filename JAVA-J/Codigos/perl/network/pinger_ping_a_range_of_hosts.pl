#! /usr/bin/perl -Tw

use strict;
use vars qw/ $opt_b $opt_n $opt_q $opt_r $opt_s $opt_t /;
use constant TIMEOUT => 1;

use Getopt::Std;
use Socket;
use Net::Ping;

getopts( 'bnrqst:' );

$opt_r   = 1 if $opt_n or $opt_s;
$opt_q   = 1 if $opt_b;
$opt_t ||= TIMEOUT;

die "This script must be installed setuid root.\n" unless $> == 0;

my $first_taint = shift or die "No first IP address given (e.g. 172.17.1.1)\n";
my $last_taint  = shift or die "No last IP address given (e.g. 127)\n";

my ($net,$first) = ($first_taint   =~ /^(\d{1,3}\.\d{1,3}\.\d{1,3})\.(\d{1,3})$/ );
die "Couldn't determine the network part in $first_taint.\n" unless $net;
die "Couldn't determine the host part in $first_taint.\n" unless $first;

my ($last) = ($last_taint =~ /^(\d{1,3})$/ );
die "Couldn't determine the host part in $last_taint.\n" unless $last;

($last, $first) = ($first, $last) if $last < $first;

my $p = Net::Ping->new( 'icmp' );
foreach my $host ( $first..$last )
{
	my $ip = "$net.$host";
	my $hostname = do {
		if( $opt_r and my $resolve = gethostbyaddr( inet_aton($ip), AF_INET )) {
			$resolve =~ s/\..*$// if $opt_s;
			$resolve .= " ($ip)" if $opt_n;
			$resolve;
		}
		else {
			$ip;
		}
	};

	if( $p->ping($ip, $opt_t) ) {
		print $hostname;
		print ' is reachable.' unless $opt_b;
		print "\n";
	}
	elsif( ! $opt_q ) {
		print "$hostname is NOT reachable.\n";
	}
}
$p->close();

=head1 NAME

pinger - ping a range of hosts

=head1 SYNOPSIS

B<pinger> [B<-bnqrs>] [B<-t> timeout] host last

=head1 DESCRIPTION

Ping a range of hosts using ICMP.

=head1 OPTIONS

=over 5

=item B<-b>

Brief. Do not print the "is reachable/is NOT reachable" text.
Probably needs B<-q> as well to be of any use to a program downstream.

=item B<-n>

Numeric. Print the numeric IP address as well as the resolved host name. (Sets B<-r>).

=item B<-q>

Quiet. Only report hosts that are pinged successfully.

=item B<-r>

Resolve. Resolve the numeric address to a FQDN host (implies -q unless -n).

=item B<-s>

Short. Like B<-r>, but resolves the numeric address to a simple host name (stopping at the first dot).

=item B<-t timeout>

Timeout. Set the time out to t seconds (defaults to 1). A low timeout value is used as this
script is destined to be used on internal networks where it is normal to have sub-second
response times. The value may be easily edited in the source to a higher value if required.

=back

=head1 EXAMPLES

C<pinger 192.168.0.1 10>

Pings the machines from 192.168.0.1 to 192.168.0.10. Sample output looks like:

	192.168.0.1 is reachable.
	192.168.0.2 is NOT reachable.
	192.168.0.3 is reachable.
	192.168.0.4 is reachable.
	192.168.0.5 is NOT reachable.
	192.168.0.6 is reachable.
	192.168.0.7 is NOT reachable.
	192.168.0.8 is reachable.
	192.168.0.9 is reachable.
	192.168.0.10 is reachable.

C<pinger -r 192.168.0.1 6>

Pings the machines from 192.168.0.1 to 192.168.0.6 and resolves the hostnames. In the following
example no A record for 192.168.0.3 exists, so the numeric IP address is used instead.

	profane.example.com is reachable.
	slothrop.example.com is NOT reachable.
	192.168.0.3 is reachable.
	godolphin.example.com is reachable.
	stencil.example.com is NOT reachable.
	porpentine.example.com is reachable.

C<pinger -n 192.168.0.1 6>

Like the above, but also includes the numeric IP address alongside the resolved host name.

	profane.example.com (192.168.0.1) is reachable.
	slothrop.example.com (192.168.0.2) is NOT reachable.
	192.168.0.3 is reachable.
	godolphin.example.com (192.168.0.4) is reachable.
	stencil.example.com (192.168.0.5) is NOT reachable.
	porpentine.example.com (192.168.0.6) is reachable.

C<pinger -sq 192.168.0.1 6>

This time, only display the local hostnames without the domain, and only report hosts that are pinged
successfully.

	profane is reachable.
	192.168.0.3 is reachable.
	godolphin is reachable.
	porpentine is reachable.

C<pinger -bq 192.168.0.1 6>

Report the IP addresses of all hosts that are pinged successfully.

	192.168.0.1
	192.168.0.3
	192.168.0.4
	192.168.0.6

=head1 BUGS

Due to the way Net::Ping works, and TCP/IP in general, this script
must be installed setuid root. In light of this constraint, the
script runs in tainted mode. To install the script, run the following
command:

C<chmod 4555 pinger>

The way the behaviour is controlled by the command-line switches is
unnecessarily obfuscated and should be cleaned up. This is a by-product
of the script's evolution. By and large, however, they tend to do
The Right Thing.

=head1 SEE ALSO

L<Net::Ping>

=head1 COPYRIGHT

Copyright 1999-2001 David Landgren.

This script is free software; you can redistribute it and/or modify it
under the same terms as Perl itself.

=head1 AUTHOR

     David "grinder" Landgren
	 eval {join chr(64) => qw[landgren bpinet.com]}

=cut

