#!/usr/bin/perl -w

# ftpgp.pl
# pod at tail

use strict;
use Net::FTP;

my %file = (
   source => 'ftpgp.in',
   dest   => 'ftpgp.out',
   log    => 'ftpgp.log',
   );
my %parm = (
   ftphost => '',
   ftpuser => '',
   ftppass => '',
   );

open (LOG, ">>$file{log}")  or die "Error opening $file{log}: $!";
LogIt('noexit', "\n  Started run        ");
print LOG
"    Sourcefile         $file{source}
    Destination file   $file{dest}
    Destination server $parm{ftphost}
    Destination user   $parm{ftpuser}
    Log file           $file{log}
    Upload script      $0
    Perl               $]
    Net::FTP           $Net::FTP::VERSION
    Local OS           $^O\n",
   ;
unless (-r $file{source} && -T _) {
   LogIt('exit',
      "Error with source file $file{source}.\nIs not readable or ASCII.\n"
      );
   }

my $ftp = Net::FTP->new($parm{ftphost}) or
   LogIt('exit', "Error connecting.\nNetwork or server problem?\n");
$ftp->login($parm{ftpuser}, $parm{ftppass}) or 
   LogIt('exit', "Error logging in.\nHas ID or password changed?\n");
$ftp->ascii();
$ftp->put($file{source}, $file{dest}) or
   LogIt('exit', "Error uploading.\nDisk space or permissions problems?\n");
$ftp->quit() or 
   LogIt('exit', "Error disconnecting.\n???\n");

LogIt('noexit', "  Finished FTP put   ");
close LOG                   or die "Error closing $file{log}: $!";


###########################################################################
# Print message with date+timestamp to logfile.
# Abort program if instructed to.
sub LogIt {
   my $exit = $_[0];
   my $msg  = $_[1];
   print LOG "$msg";
   my($sec,$min,$hour,$mday,$mon,$year,$wday,$yday,$isdst) = localtime(time);
   print LOG $year+1900,"-",$mon+1,"-$mday   $hour:$min:$sec\n";
      ;
   exit if ($exit eq 'exit' );
   }
###########################################################################


=head1 Title

 ftpgp.pl

=head1 Description

 Non-interactive script to upload a file to specified FTP server.
 Intended to be run repeatedly from (cron|at|scheduler).
 Logs success or failure with timestamp and Perl+module versions.
 Would be trivial to tweak for multiple files.
 Source, dest, and log files should usually be specified by full filespec.

=head1 Updated

 2001-06-21   19:35 CDT
   Simplify with localtime but not loading Time::localtime module.
 2001-06-20   17:40 CDT
   Add error runs to example logfile.
   Add read+text filetests for source file.
   Combine ErrAndExit, LogMessage, and MyTime into LogIt() with passed param.
 2001-06-19   22:15 CDT
   Post to PerlMonks.
   Initial working code.

=head1 Todos

 Variableize $ftp->(ascii|binary).
 Variableize $ftp->(get|put).
 Debug error only seen with ActivePerl:
   "uninitialized value in concatenation (.)
    at D:/Perl/site/lib/Net/Config.pm line 44."
 Employ Net::FTP's own error handling.

=head1 Tested

 ActivePerl      5.61,     Win2kPro
 Perl            5.006001, Cygwin 1.3.2,  Win2kPro
 Perl            5.00503,  Debian 2.2r3
 Net::FTP        2.56
 Time::localtime 1.01

 Error handling for:
   unreadable or nonexistant sourcefile
   binary (non-ASCII) sourcefile
   unreachable host
   bad ID
   wrong password
   no write perms at target

=head1 Example runlog

  Started run        21:55:1   6-19-2001
    Sourcefile         C:\Data\ftpgp.in
    Destination file   /incoming/ftpgp.out
    Destination server ozark.bbq
    Destination user   clem
    Log file           C:\data\ftpgp.log
    Upload script      C:\Documents and Settings\clem\Perl\ftpgp.pl
    Perl               5.006
    Net::FTP           2.56
    Local OS           MSWin32
  Finished FTP put   21:55:1   6-19-2001

 Error with source file ftpgp.in.
 Is not readable or ASCII.
 2001-6-21   19:37:43

 Error connecting.
 Network or server problem?
 2001-6-20 15:53:29

 Error logging in.
 Has ID or password changed?
 2001-6-20 15:53:49

 Error uploading.
 Disk space or permissions problems?
 2001-6-20 16:3:53

=head1 Author

 ybiC

=head1 Credits

 Thanks to Kevman, grinder, jeffa, and damian1301 for feedback.
 Oh yeah, and to some guy named vroom.
 
=cut


