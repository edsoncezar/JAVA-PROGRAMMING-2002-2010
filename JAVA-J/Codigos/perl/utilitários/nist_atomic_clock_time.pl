#!/usr/bin/perl -w

use strict;
use LWP::UserAgent;

my $uri = "http://www.time.gov/timezone.cgi?Eastern/d/-5/java";

my $agent = new LWP::UserAgent;
my $req = HTTP::Request->new(GET => $uri);
my $res = $agent->request($req);

if ( !$res->is_success ) {
    die "HTTP Request Failed: Error ".$res->code;
}

# The line that has the date and time is
# a javascript declaration that looks like
# var NISTSendTime = new Date( "May 10, 2000 22:37:41");
#  Yes, this regex could be done better.
if ( $res->content =~ /NISTSendTime = new Date\(\s*\"(.*?)"\);/ ) {
    print "NIST Date/Time: $1\n";
}

