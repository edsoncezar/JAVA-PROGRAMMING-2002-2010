package CoversProject::Client;

$VERSION   = '0.01';

use strict;
use warnings;
use Carp;
use Frontier::Client;

my $default = 'http://covers.wiw.org:80/RPC.php';

sub ServerName { ${$_[0]}->{'url'}; }

sub Default { $default }

sub new {
  my( $class, %arg ) = @_;
  my $self = Frontier::Client->new( url => $arg{server_name} || $default );
  bless \$self, $class;
}

my %method = map { $_ => "covers.$_" } qw( CoveredBy Longest Chain Covered Bands URL );
$method{$_} = "system.$_" for qw( methodSignature listMethods methodHelp );

sub AUTOLOAD {
  my $m = (split '::', our $AUTOLOAD)[-1];
  my $ret = exists $method{$m} ? ${$_[0]}->call( $method{$m}, grep { defined } $_[1] )
                               : croak "Undefined method $m called";
  ref $ret eq 'ARRAY' ? @$ret : $ret;
}

1;


=pod

=head1 NAME

CoversProject::Client - Perl library to access the covers project (http://covers.wiw.org/).

=head1 SYNOPSIS

  use CGI ':standard';
  use CoversProject::Client;

  my ($server, @results, $query);

  $server = CoversProject::Client->new();
  @results = $server->Bands();

  print header, start_html;
  print ol(li(\@results));
  print end_html;

=head1 DESCRIPTION

This module makes use of Ken MacLeod's XML-RPC module (Frontier::Client) to provide
easy access to methods provided by Chris Heschong's Covers Project (http://covers.wiw.org/).

=head1 METHODS

=head2 new()

Call new() to create a new XML-RPC client object.

  my $server = CoversProject::Client->new();

It will use http://covers.wiw.org:80/RPC.php as the default server. You can supply your own
if you wish by using the server_name parameter.

  my $server = CoversProject::Client->new( server_name => 'http://my.own.server.com:80/RPC.php' );

=head2 ServerName()

Simply returns the server your client is connected to.

  my $current_server = $server->ServerName();

=head2 Default()

Returns the default server your client can connect to.

  my $default_server = $server->Default();

=head2 Covered()

Returns an array of hashrefs containing songs originally performed by the supplied argument that have
been covered by other artists.

  my @results = $server->Covered('Cake');
  foreach (@results) {
    print $_->{'artist'} . ' - ' . $_->{'song'} . '<br />';
  }

=head2 CoveredBy()

Returns an array of hashrefs containing songs that the supplied argument has covered.

  my @results = $server->CoveredBy('Cake');
  foreach (@results) {
    print $_->{'artist'} . ' - ' . $_->{'song'} . '<br />';
  }

=head2 Bands()

Returns an array containing all of the bands the server knows about.

  my @results = $server->Bands();
  foreach (@results) {
    print $_ . '<br />';
  }

=head2 URL()

Returns the Covers Project URL for a given artist.

  my $url = $server->URL('Cake');

=head2 Chain()

Returns an array of hashrefs containing songs in a cover chain started by the supplied argument.

  my @results = $server->Chain('Cake');
  foreach (@results) {
    print $_->{'artist'} . ' - ' . $_->{'song'} . ' was covered by ' . $_->{'coveredBy'} . '<br />';
  }

=head2 Longest()

Returns an array of hashrefs containing the chain at position (argument). Use position 0 for the longest chain.

  my @results = $server->Longest(0);
  foreach (@results) {
    print $_->{'artist'} . ' - ' . $_->{'song'} . ' was covered by ' . $_->{'coveredBy'} . '<br />';
  }

=head2 listMethods()

This method lists all the methods that the covers project server knows how to dispatch.

  my @methods = $server->listMethods();
  foreach (@methods) {
    print $_ . '<br />';
  }

=head2 methodHelp()

Returns help text if defined for the method passed, otherwise returns an empty string.

  my $help = $server->methodHelp('covers.URL');

=head2 methodSignature()

Returns an array of known signatures for the method name passed.

  my @sigs = $server->methodSignature('covers.Chain');
  foreach (@sigs) {
    print @$_[0] . ' covers.Chain(' . @$_[1] . ')';
  }

=head1 BUGS

If you have any questions, comments, bug reports or feature suggestions, 
email them to Brian Cassidy <brian@alternation.net>.

=head1 CREDITS

This module was originally written by Brian Cassidy (http://www.alternation.net/)
and was then rewritten and optimized by Ray Brinzer (http://www.brinzer.net/).

Many thanks to Ken MacLeod and his Frontier::Client module. Without it, this
would not have been possible.

Thanks also to Chris Heschong (http://chris.wiw.org/) for his most excellent covers
project (http://covers.wiw.org/) and its XML-RPC interface (http://covers.wiw.org/xmlrpc.php).

=head1 LICENSE

This library is free software; you can redistribute it and/or modify it as you
wish.

=cut
