IIS.pm

# File: iis.pm version 1.03
# Description: Perl Module for the ck.pl script
# Written by: Joseph Oaks
# Date written: 14 May 2001
# Last revised: 09 Jul 2001

package IIS;
use IO::Socket;
use POSIX;
use IO::Select;
use LWP::UserAgent;
use HTTP::Request;

=head1 NAME

INET - perl module used for the starting and stopping of Microsoft IIS

=head1 SYNOPSIS

inet.pl

=head1 DESCRIPTION

In some installations of IIS and third party application, IIS will continue
to increase its memory use to the point where it will stop responding. This
module came about as a way to start and stop the IIS and to verify that said
services are running.

=head1 VARIABLES

Be sure to change your environment setting to match your system.
Example...
$server = '127.0.0.1';
$url = '/your homepage';
$port = '80';
$timeout = '15';

=head1 AUTHOR

Joseph Oaks E<lt>F<joseph_oaks@non.hp.com>E<gt>

=cut

$server = '127.0.0.1';
$url = '/computing/printcentral/eprise';
$full_url = 'http://127.0.0.1/computing/printcentral/eprise';
$port = '80';
$timeout = '15';
$date = strftime('%m/%d/%Y %H:%M:%S', localtime);

sub make {
my $connect;

  print "In make() \n";
  my $ua = new LWP::UserAgent;
  $ua->timeout($timeout);
  
  print "UA created with timeout $timeout \n";

  $request = new HTTP::Request('GET', $full_url);
  print "Created request \n";
  $connect = $ua->simple_request($request); 
  print "Got response: ".$connect->content."\n";

 
  return $connect->content;
  }

sub stop {
  my $str1 = `net stop iisadmin /y`;
  return $str1;
}

sub start {
  $str2 = `net start w3svc /y`;
  sleep 10;
  return $str2; 
}

sub pid {
  $str3 = `ps -ef | grep -v grep | grep inetinfo`;
#  print $str3, "\n"; 
  @arr = split /\s+/, $str3;

   return $arr[2]; 
  }

============================================================
inet.pl

# File: inet.pl version 2.0
# Description: Restart Script for IIS
# Written by: Joseph Oaks & Praveen Sinha
# Date written: 20 Apr 2001
# Last revised: 09 Aug 2001

use POSIX;
use IO::Socket;
use IIS;

$| = 1;
open FILE, ">>c:\inet_log.log";
while (1) {
  my $time = strftime('%m/%d/%Y %H:%M:%S', localtime);
  my $uptime = `c:/uptime.exe`;

print FILE "$time\n";
print FILE "$uptime\n";

####################
# IIS Stop Section #
####################

my $line = `ps -ef | grep -v grep | grep inetinfo`;
@arr = split /\s+/, $line;
my $PID = $arr[2];
print FILE "INETINFO running on $PID ...\n";
  if ($arr[2] =~ /\d+/) {
  print FILE "-- Attempting to stop WWW Services...\n";
  my $str = `net stop iisadmin /y`;
  print FILE "$str\n";
    if ($str =~ /\s+successfully.\s+/i) {
    print FILE "-- INETINFO Server has shutdown Successfully.\n\n";
    } else {
      print FILE "INETINFO $PID did not stop on its own...\n\n";
      `kill $PID`;
      print FILE "INETINFO Server has been killed!\n\n"; 
   }
  }

 
#####################################
# Check if Dr. Watson Error Section #
#####################################

my $dr = undef;
my $drpid = `ps -ef | grep -v grep | grep drwtsn32`;
@arr2 = split /\s+/, $drpid;
$dr = $arr2[2];
  if ($dr eq '') {
  print FILE "-- No Dr. Watson errors...\n\n";
  } else {
  if ($arr2[2] =~ /\d+/) {
    print FILE "-- Dr. Watson error exists... \n\n";
    print FILE "   Killing $dr \n"; 
     `kill $dr`;
 }
}


#####################
# IIS Start Section #
#####################

my $NEWPID = undef;
  if ($NEWPID eq '') {
  print FILE "-- Atempting to start W3SVC Web Server!\n";
  my $str2 = `net start w3svc /y`;
  print FILE "$str2\n";
  if ($str2 =~ /\s+successfully.\s+/i) {
  print FILE "   WWW Services have been started.\n";
  }

  my $line1 = `ps -ef | grep -v grep | grep inetinfo`;
  @arr1 = split /\s+/, $line1;
  $NEWPID = $arr1[2];
  print FILE "-- INETINFO is now running with new PID = $NEWPID.\n\n";
  }
  
#####################
# Web Check Section #
#####################

$connect = &IIS::make;
   if ($connect =~ /\s+eprise\s+/is) {
     close FILE;
     exit(1);
 }
}

============================================================
ck.pl

# File: ck.pl version 1.01
# Discription: Restart script for IIS / Eprise Web server
# Written by: Joseph Oaks & Praveen Sinha
# Date written: 14 May 2001
# Last revised: 29 Jun 2001


use POSIX;
use IIS;
use IO::Socket;

$| = 1;
while (1) {

$chk_iis = &IIS::pid;

print "IIS is running with a pid of: ", $chk_iis, "\n\n";

  if ($chk_iis eq '') {
    `c:/inet.pl`;
    sleep 60;

  } else {

print "Checking if the Web Content...\n\n";
$connect = &IIS::make;

  if ($connect eq '') {
    `c:/inet.pl`;
    sleep 60;
  } else {
  sleep 300;  
}
 # exit(1);
  close FILE;
}
} 

