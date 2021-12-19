package Apache::TimeOut;
#file Apache/TimeOut.pm
#
#	Author: J. J. Horner
#	Version: 0.21 (06/14/2000)
#	Usage:  see documentation
#	Description:
#		Small mod_perl handler to provide Athentication phase time outs for 
#		sensitive areas, per realm.  Still has a few issues, but nothing too 
#		serious.

use strict;
use warnings;
use Carp;
use Apache::Constants qw(:common);

our $VERSION = '0.01';

sub handler {

        my $current_time = time();

        my $r = shift;
        my $DEBUG = $r->dir_config('TIMEOUT_DEBUG') || carp "DEBUG value not set: $!"; # pulls debug flag from config file 
       	carp "current time = $current_time" if $DEBUG;
        my ($res, $sent_pw) = $r->get_basic_auth_pw;
        carp "Response set - $res: $!" if $DEBUG;
        return $res if $res != OK;  # return not OK status if not OK

	my $time_to_die;
	if ($r->dir_config('TimeLimit') && ($r->dir_config('TimeLimit') < $r->dir_config('DefaultLimit'))) {
	        $time_to_die = $r->dir_config('TimeLimit');
	} else {
		$time_to_die = $r->dir_config('DefaultLimit');
	}
        carp "time limit set to $time_to_die" if $DEBUG;
        return DECLINED if ($r->dir_config('MODE'));  #do nothing if PerlSetVar TimeLimit not set.

        my $user = $r->connection->user;
        my $realm = $r->auth_name();
        $realm =~ s/\s+/_/g;
        my $host = $r->get_remote_host();
        my $time_file = "/usr/local/apache/conf/times/$realm-$host.$user";
        carp "Time file set to $time_file" if $DEBUG;

        if (-e $time_file) {   # if timestamp file exists, check time difference
                my $last_time = (stat($time_file))[9] || carp "Unable to get last modtime from file: $!";
                carp "Last time = $last_time" if $DEBUG;

                if ($time_to_die >=  ($current_time - $last_time)) {
                        open (TIME, ">$time_file");
                        close TIME;
                        return OK;

                } else {  # if time delta greater than TimeLimit
                        $r->note_basic_auth_failure;
                        unlink($time_file) or carp "Can't unlink file: $!";
                        return AUTH_REQUIRED;
                }

        } else {  # previous time delta greater than TimeLimit so file was unlinked
                open (TIME, ">$time_file");
                close TIME;
                return OK;
        }
}

1;
__END__

=head1 NAME

Apache::TimeOut - mod_perl handler to provide time outs on .htaccess protected pages.

=head1 SYNOPSIS

  In httpd.conf file:
	PerlAuthenHandler Apache::TimeOut
	PerlSetVar DefaultLimit \<timeout in seconds\>

  Optional httpd.conf file entry:
	PerlSetVar TIMEOUT_DEBUG 1
	   Turns debugging on to print messages to server error_log

  Optional .htaccess entries: 
	PerlSetVar TimeLimit \<timeout\>
	    or
	PerlSetVar MODE off      #to turn off timeouts

=head1 DESCRIPTION

  Simple mod_perl handler for the AUTHENTICATION phase to set a limit on user inactivity.
  Will provide timeouts to any file under the protection of an .htaccess file, unless the 
  'MODE' option set to anything other than 0 in the .htaccess file.  The 'DefaultLimit' is
  set via the httpd.conf file, and unless the user specified 'TimeLimit' is set and less 
  than the 'DefaultLimit', determines the length of time a user can be inactive.  This 
  handler can be set anywhere an AUTHENTICATION handler can be specified.

=head2 EXPORT

None by default.


=head1 AUTHOR

J. J. Horner jjhorner@bellsouth.net

=head1 SEE ALSO

perl(1).

=cut

