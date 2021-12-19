#!/usr/bin/perl
use Net::POP3;
usage(0) if "-h" eq $ARGV[0]; || "-help" eq $ARGV[0];
usage(1) if 2 != $#ARGV;
($hostname, $account, $password) = @ARGV;
$handle = Net::POP3->new($hostname) or die "Unable to establish a POP3 connection to $hostname.\n";
defined($handle->login($account, $password)) or die "Unable to authenticate ($account, $password) at $hostname.\n";
$message_list = $handle->list or die "Unable to retreive list of avaible mesages.\n";
foreach $item (keys %$message_list) {
$header = $handle->top($item);
print @$header;
}

sub usage {
print "Use this utility as 'pop_check HOSTNAME ACCOUNT PASSWORD',\n";
print " or 'pop_check -help', to see this message.\n";
exit($_[0]);
}

