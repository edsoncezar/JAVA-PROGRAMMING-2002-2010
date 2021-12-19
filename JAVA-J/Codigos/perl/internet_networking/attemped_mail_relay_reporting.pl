#!/usr/bin/perl -wT

use IO::Socket::INET;
use Mail::Mailer;
use Parse::Syslog;

use strict;


my %mail = (
    'To'        =>  'rob@cowsnet.com.au',
    'From'      =>  'root@cowsnet.com.au',
    'Server'    =>  '127.0.0.1'
);

my %hosts;
my $syslog = Parse::Syslog->new('/var/log/mail.log', arrayref => 1);
while (my $line = $syslog->next) {
    next unless $line->[2] =~ /^sendmail$/i;
    next unless $line->[4] =~ /ruleset=check_(rcpt|relay)/i;
    my ($relay) = $line->[4] =~ m/relay=\[?([\w\d\.\-\@]+)\]?/i;
    next unless defined $relay;
    push @{$hosts{$relay}}, $line;
}

foreach my $host (keys %hosts) {
    my $whois = eval {
        my $sock = IO::Socket::INET->new(
            PeerAddr    =>  "whois.geektools.com",
            PeerPort    =>  43,
            Timeout     =>  30
        ) || die $!;
        $sock->print("$host\r\n");
        my @response = <$sock>;
        $sock->close;
        return join "", @response;
    };
    my $smtp = Mail::Mailer->new("smtp", Server => $mail{'Server'});
    $smtp->open({
        'To'        =>  $mail{'To'},
        'From'      =>  $mail{'From'},
        'Subject'   =>  "[MAIL ADMIN] Attempted mail relay from $host"
    });
    print $smtp $whois, "\n";
    foreach my $line (@{$hosts{$host}}) {
        my $time = localtime($line->[0]);
        print $smtp
            $time, " ",
            $line->[1], " ",
            $line->[2], "[", $line->[3], "]: ",
            $line->[4], "\n\n";
    }
    $smtp->close;
}

exit 0;

