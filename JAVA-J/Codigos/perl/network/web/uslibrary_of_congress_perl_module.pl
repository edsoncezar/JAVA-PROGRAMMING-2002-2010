package LOC;

use strict;
use CGI;  
use IO::Socket;

sub z3950_data {
        my $isbn = shift();

        local $/ = undef;
        my $ua = IO::Socket::INET->new(
                Proto    => 'tcp',
                PeerAddr => 'lcweb.loc.gov',
                PeerPort => 'http(80)', );

        print $ua "GET " . z3950_url($isbn) . "\x0d\x0a\x0d\x0a";

        my ($raw_data) = <$ua> =~ /<pre>(.*?)<\/pre>/is;
        return undef unless ( defined($raw_data) );

        my %data = ();
        my $last = 'UNKNOWN';
        foreach my $line ( split(/\n/, $raw_data ) ) {
                chomp( $line );
                $line =~ s/\s+/ /g;
                if ( my ($key, $value) = $line =~ /^([^:]+): (.*)/ ) {
                   $data{$key} .= $value;
                   $last = $key;
                }
                else {
                   $data{$last} .= $line;
                }
        }

        return \%data;
}

sub z3950_html {
        my $isbn = shift();

        my $data = z3950_data( $isbn );
        if ( !defined($data) ) {
                return "<font color='#990000'>z3950_html: Can't get LOC data</font>";
        }

        return "<pre>", join("\n", map { "$_ -> $$data{$_}" } keys( %$data )),
                "</pre>\n";
}

sub z3950_url {
        my $isbn = shift();
        my $sid = undef;

        local $/ = undef;
        my $ua = IO::Socket::INET->new(
                Proto    => 'tcp',
                PeerAddr => 'lcweb.loc.gov',
                PeerPort => 'http(80)', );

        print $ua "GET /cgi-bin/zgate?ACTION=INIT\&FORM_HOST_PORT=".
                "/prod/www/data/z3950/locils.html,z3950.loc.gov,7090".
                "\x0d\x0a\x0d\x0a";

        ($sid) = <$ua> =~ /NAME="SESSION_ID"\s+VALUE="(\d+)"/i;

        return undef unless ( defined($sid) );

        return 
                "http://lcweb.loc.gov/cgi-bin/zgate?".
                "ESNAME=F&".
                "ACTION=SEARCH&".
                "DBNAME=VOYAGER&".
                # "MAXRECORDS=20&".
                # "RECSYNTAX=1.2.840.10003.5.10&".
                # "REINIT=" . CGI::escape("/cgi-bin/zgate?ACTION=INIT&FORM_HOST_PORT=/prod/www/data/z3950/locils.html,z3950.loc.gov,7090") . "&" .
                "TERM_1=$isbn&".
                "USE_1=7&".
                "SESSION_ID=$sid".
                "";
}

1;

=pod

=head1 TITLE

LOC.pm - an interface to the Library of Congress' book database

=head1 SYNOPSIS

To redirect from a web page:

        use CGI;
        use LOC;

        my $cgi = new CGI;
        my $isbn = $cgi->param('isbn');
        print $cgi->redirect( LOC::z3950_url($isbn) );

To get the data for a certain book:

        use LOC;

        my $data = LOC::z3950_data( $isbn );
        foreach my $key ( keys(%$data) ) {
                print "$key: $$data{$key}\n";
        }

=head1 DESCRIPTION

The Library of Congress' web-interface to their book database is screwy.
You just can't find the isbn and plug it into a simple url.  No, you
need to initialize a session first, and then plug the isbn into a simple
url.  Oh well.  So this module initializes a session and redirects you
to the right url.  Or, you can just grab the data from the LOC and present
it in whatever form you want.

=head1 FUNCTIONS

=over 4

=item \%hash z3950_data( $isbn ) 

Given an ISBN, return a reference to a hash with the data downloaded from
the Library of Congress.  The keys to the hash are the data field names.

=item $html z3950_html( $isbn )

Dump out the data from z3950_data as HTML.  Sort of.  It's just plain
text with <pre> tags around it.

=item $url z3950_url( $isbn )

The url that will get the LOC page for this ISBN.

=back

=cut
