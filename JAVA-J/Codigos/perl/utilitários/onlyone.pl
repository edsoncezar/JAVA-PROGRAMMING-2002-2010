#!/usr/bin/perl

use strict;
use Sys::Syslog;
use English;

my ($temp); # holds temporary data
my ($mytty); # holds my tty
my ($ttytype); # holds either tty or pts
my ($ipaddr); # holds my ip addr
my ($childpid); # holds the pid of a child process
my (@w_listing); # array for output of w command

delete @ENV{qw(IFS CDPATH ENV BASH_ENV)};
$ENV{PATH} = "/bin:/usr/bin";

open (FROMKID, "-|") or exec ("who", "-m") or system_log ("Can't run who: $!");
if (eof FROMKID) {exit 1;}
while (<FROMKID>) {
  if (/tty(.*?)\s/) {
    $mytty = "tty" . $1;
    $ttytype = "tty";
  }
  elsif (/pts(.*?)\s/) {
    $mytty = "pts" . $1;
    $ttytype = "pts";
  }
}
close FROMKID;

if (defined($mytty)) {
  open (FROMKID, "-|") or exec ("w", "-s") or system_log ("Can't run w: $!");
  if (eof FROMKID) {exit 1;}
  while (<FROMKID>) {

    my ($w_idx); # subsript for @w_listing;
			
    $w_idx = @w_listing;
    $w_listing[$w_idx] = $_;

  }
  close FROMKID;

  foreach (@w_listing) {
    if (/$mytty\s.*?\b(.*?)\s/) {
      $ipaddr = $1;
    }
  }

  if (defined($ipaddr)) {

    foreach (@w_listing) {

      my ($checktty); # tty to check for d3 process

      if (/$ttytype(.*?)\s.*?$ipaddr/) {
        next if (($ttytype . $1) eq $mytty); # we don't want to kill ourselves!
				
        $checktty = $1;

        open (FROMKID, "-|") or exec ("ps", "awwww") or system_log ("Can't run ps: $!");
        if (eof FROMKID) {exit 1;}
        while (<FROMKID>) {

          foreach (<FROMKID>) {
            if (/^ *(\d*?)\s.*?\b$checktty\s.*?d3.*?VIDEO/) {

              my ($killresult);

              $killresult = kill 15, $1;
              if ($killresult) {
                if (kill 0, $1) {
                  $killresult = kill 1, $1;
                }
              }
              if ($killresult) {
                system_log ('PID %d has been killed successfully for %s', $1, $ipaddr);
              } else {
                system_log ('Attempt to kill PID %d for %s failed', $1, $ipaddr);
                print "You are unable to login because there is already a D3 process\n";
                print "running from your IP address.\n\n";
                print "Please call Example Systems for assistance.\n\n";
                print "Press <ENTER> to continue...";
                my $hit_enter = <STDIN>;
                exit 1;
              }
            }
          }
        }
        close FROMKID;

      }
    }

  } else {
    print "I cannot determine my IP address.\n\n";
    print "Please call Example Systems.\n\n";
    print "Press <ENTER> to continue...";
    system_log ("Cannot determine IP address");
    my $hit_enter = <STDIN>;
  }
} else {
  print "I cannot determine my tty.\n\n";
  print "Please call Example Systems.\n\n";
  print "Press <ENTER> to continue...";
  system_log ("Cannot determine tty");
  my $hit_enter = <STDIN>;
}

sub system_log {
  Sys::Syslog::setlogsock ('unix');
  openlog ('onlyone', 'pid','user');     # open a channel to the syslogd
  syslog ('info', @_);
  closelog();
}

