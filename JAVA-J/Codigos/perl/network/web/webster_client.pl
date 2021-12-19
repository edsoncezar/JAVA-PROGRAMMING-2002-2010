#!/usr/bin/perl -w

use HTML::TreeBuilder;
use HTML::FormatText;
use LWP::UserAgent;

my $ua = new LWP::UserAgent;
$ua->agent("Dictionary");

#
# Uncomment if behind a firewall
#
#$ua->proxy([ 'http' ], 'http://www-dms.esd.sgi.com:8080/');

#
# Create a new request.
#
my $req = new HTTP::Request GET => "http://work.ucsd.edu:5141/cgi-bin/http_webster?isindex=$ARGV[0]&method=exact";

#
# Pass Request to the user agent and get a response back.
#
my $res = $ua->request($req);

#
# Print outcome of the response.
#
if(! $res->is_success) {
  print "Failure to connect to server: " . $res->message;
} else {
  my $html = $res->content;
  my $p = HTML::TreeBuilder->new;
  $p->parse($html);
  my $formatter = HTML::FormatText->new(leftmargin => 0, rightmargin => 60);
  my $result = $formatter->format($p);
  
  my @paragraphs = split /^\s+/m, $result;

  #
  # Print only the paragraphs where the word definition is
  #
  for(my $x=2; !($paragraphs[$x] =~ /^From WordNet/) and $x < 10; $x++) {
    print $paragraphs[$x]."\n";
  } 
}

