#!/usr/bin/perl -w
# showpic logger

# Setup configuration
# $home - filesystem path to append to images
# $logfile - full path to the logfile you want to use
# $ips - $ip addresses you want to ignore in the log
# %entries - hash of labels => regexes for log

my $home = '/my_path_to/public_html';
my $logfile = '/my_path_to/access_log';
my $ips = '127.0.0.1';
my %entries = (
        'home' => 'comatose/index.html$|comatose/$',
        'resume' => 'resume'
        );

use strict;
use Socket;

# Open the pic or DIE!
my $pic = $home . $ENV{PATH_INFO};
open PIC, "$pic" or die "Could not open $pic: $!";

# Get the size and MIME type
my $type = '';
my $size = -s $pic;

if ($pic =~ /gif$/) {
        $type = 'gif';
} elsif ($pic =~ /jpg$/) {
        $type = 'jpeg';
} else {
        die "Unknown image type";
}

print "Content-type: image/$type\n";
print "Content-length: $size\n\n";

print while (<PIC>);

# Now make the log entry
my ($sec, $min, $hour, $mday, undef, $year, undef, undef) =
                localtime($^T);
my $date = ('Sun','Mon','Tue','Wed','Thu','Fri',
	'Sat')[(localtime)[6]] . ' ';
$date .= ('Jan','Feb','Mar','Apr','May','Jun','Jul','Aug',
	'Sep','Oct','Nov','Dec')[(localtime)[4]];

# Adjust timestamp to suit taste
$year += 1900;
foreach (($mday, $sec, $min, $hour)) {
        $_ = '0' . $_ if ($_ < 10);
}

$date .= " $mday $hour:$min:$sec " . $year;

unless ($ENV{REMOTE_ADDR} =~ /$ips/) {
        my $hostname;
        $hostname = 
		gethostbyaddr(inet_aton($ENV{REMOTE_ADDR}),
		AF_INET) or $hostname = $ENV{REMOTE_ADDR};
        my $data = "$date $hostname";
        open LOG, ">>$logfile" or last;
        flock LOG, 2;
        foreach (keys %entries) {
          print LOG "$data $_\n" if 
		($ENV{HTTP_REFERER} =~ /$entries{$_}/);
        }
        close LOG;
}
