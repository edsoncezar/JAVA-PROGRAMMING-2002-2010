#!/usr/bin/perl
#
# Take a mail message on STDIN or as a file argument and parse out all
# IP addresses in Received: headers.  These are then looked up in the
# Realtime Black Hole spam filter (or similar service) and if any are
# found then the programs prints a 1 to stdout otherwise it returns a
# 0
#
# Use this in a procmail .procmailrc file like this to filter all
# spam into a mailbox
#
# ISSPAM=`/path/to/rbl-test.pl`
#
# :0
# * ISSPAM ?? [1-9]
# {
#     # Add a spam detected header
#     :0 fw
#     | formail -A "X-Spam: $ISSPAM"
#
#     :0:
#     spambox
# }
#
# Takes -d to print extra stuff when debugging
#
# by Nick Craig-Wood <ncw@axis.demon.co.uk>

use strict;
use Net::DNS;
use Getopt::Std;
$^W=1;                          # use this instead of -w to silence warnings from Net::DNS

# You can put other servers in here as desired
#
# The commented out ones are too vicious for my taste - experiment by
# all means!

my @servers = (
    'blackholes.mail-abuse.org',# reported spammers
#   'dialups.mail-abuse.org',   # dialup users
    'relays.mail-abuse.org',    # open relays
#   'inputs.orbs.org',          # single stage relay filtering 
#   'outputs.orbs.org',         # immediate filtering of multihop relays.
#   'delayed-outputs.orbs.org', # delayed immediate filtering of multihop relays.
);

# Ip addresses we should ignore here - the private ones
my $IGNORE = qr{
    ^(?:
        (?: 1 \. ) |
        (?: 10 \. ) |
        (?: 172 \. (?:1[6-9]|2\d|3[01]) \. ) |
        (?: 192 \. 168 \. ) |
        (?: 0 \. 0 \. 0 \. 0 $ ) |
        (?: 127 \. 0 \. 0 \. 1 $ )
    )
}x;

my $opt = {};
getopts("d", $opt);
my $DEBUG = $opt->{d};

# Parse the header out of the email joining continued header lines as
# necessary and stopping at the end of the header

my $header = "";
while (<>)
{
    chomp;
    last if $_ eq "";           # end of header
    $header .= "\n" unless /^\s+/;
    $header .= $_;
}
$header .= "\n";

# Parse the IP addresses out of the header

my $octet = qr{(?:\d|(?:[1-9]|1\d|2[0-4])\d|25[0-5])};
my $ip_addr = qr{$octet\.$octet\.$octet\.$octet};
my %ips;

while ($header =~ m/^Received:\s*(.*)$/mg)
{
    my $received = $1;
    while ($received =~ /\b($ip_addr)(?=\b)/og)
    {
        my $ip = $1;
        if ($ip =~ /$IGNORE/)
        {
            print "Ignoring ip '$ip'\n" if $DEBUG;
        }
        else
        {
            print "Found ip: '$ip'\n" if $DEBUG;
            $ips{$ip}++;
        }
    }
}

# Now test the ip addresses

my @blocked = query_ip_addresses(sort keys %ips);
if (@blocked)
{
    print "Blacklisted IP addresses found\n" if $DEBUG;
    print join(", ", @blocked), "\n";
}
else
{
    print "No bad IPs found - all OK\n" if $DEBUG;
    print "0\n";
}

exit(0);


############################################################
# Query the list of IP addresses in parallel
# This speeds up the checker greatly
############################################################

sub query_ip_addresses
{
   my (@ip_addresses) = @_;
   my ($RETRIES) = 2;           # try sending each packet this many times
   my ($TIMEOUT) = 5;           # max time for all queries to come back
   my ($DTIMEOUT) = 0.1;        # time to poll for each query
   my ($retry_interval) = $TIMEOUT / $RETRIES;
   my ($i);
   my (@sock);
   my @blocked;

   # Produce a list of input names to look up
   my (@input) = map
   {
       my $revip = join(".", reverse split /\./, $_);
       map { "$revip.$_." } @servers;
   } @ip_addresses;
   my (@desc) = map
   {
       my $ip = $_;
       map { "$ip in $_" } @servers;
   } @ip_addresses;
   print "querying:\n", map { "  $_\n" } @input if $DEBUG;

   my ($dns) = new Net::DNS::Resolver;
   $dns->recurse(1);            # Do recurse
   $dns->dnsrch(0);             # Ignore the search list
   $dns->defnames(0);           # Don't append stuff to the end if no trailing .
   #$dns->debug(1);

   # Create the background queries
   @sock = map { $dns->bgsend($_, "ANY") } @input;

   my ($retry_at) = $retry_interval;
   for (my $timeout = 0; $timeout < $TIMEOUT && scalar(grep { $_ } @sock); $timeout += $DTIMEOUT)
   {
       print "{TRY}\n" if $DEBUG;
       select(undef, undef, undef, $DTIMEOUT); # sleep for a short time

       if ($timeout > $retry_at)
       {
           # destroy the sockets and remake them
           for ($i = 0; $i < @sock; $i++)
           {
               next unless $sock[$i];
               $sock[$i] = undef;       # destroy socket
               $sock[$i] = $dns->bgsend($input[$i], "ANY");
               print "{RETRY $input[$i]}\n" if $DEBUG;
           }
           $retry_at += $retry_interval; # reset the retry timeer
       }

       for ($i = 0; $i < @sock; $i++)
       {
           my $sock = $sock[$i];
           my $input = $input[$i];
           next unless $sock && $dns->bgisready($sock);
           my $query = $dns->bgread($sock);
           $sock[$i] = undef;   # destroy the socket

           if ($query)
           {
               print "$input[$i] answer received\n" if $DEBUG;
               foreach my $rr ($query->answer)
               {
                   next unless $rr->type eq "A";
                   print "**** Blacklisted IP found $desc[$i] (", $rr->type, " => ", $rr->address ,")\n" if $DEBUG;
                   push @blocked, $desc[$i];
               }
           }
           else
           {
               print "query failed: ", $dns->errorstring, "\n" if $DEBUG;
           }
       }
   }
   
   # destroy any unused sockets - these are timeouts
   for ($i = 0; $i < @sock; $i++)
   {
       next unless $sock[$i];
       print "Timeout on: $input[$i]\n" if $DEBUG;
       $sock[$i] = undef;       # destroy socket
   }
   
   return @blocked;
}

