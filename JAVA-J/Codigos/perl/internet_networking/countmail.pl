    #!/opt/gnu/bin/perl -w
    # ---------------------------------------------------------------------------
    # NAME
    #	countmail - greps the sendmail log file and counts messages 
    #		to and from a particular address
    #
    # SYNOPSIS
    #	countmail [-m]
    #
    # OPTIONS
    #	-m Send report to a mailing list
    #	(with no options, report is *only* sent to the test recipients)
    #
    # NOTE
    #	This script is intended to run as a batch job,
    #	It's good for getting a daily count of mail traffic
    #	for a particular address. It counts mail messages sent
    #	to a recipient on the day before. 
    #
    #	I tried to take into consideration as many error
    #	conditions as possible. It only counts successfully
    #	sent messages. Queued attempts are not counted. 
    #	Also, mail from the mailer-daemon (i.e., bounces)
    #	are also ignored. 
    #
    #	Limitation: since this script basically ignores any day
    #	but yesterday, it probably will not count messages which stay 
    #	in the queue from one day to the next. 
    #
    # $Id: countmail,v 1.7 1999/12/24 06:29:35 john Exp john $
    # ---------------------------------------------------------------------------
    =head1 NAME
    countmail - greps the sendmail log file and counts messages 
    to and from a particular address
    =head1 SCRIPT CATEGORIES
    UNIX/System_administration
    Mail
    =head1 SYNOPSIS
    C<countmail [-m] >
    =head1 README
    This script scans the current mail log and several recent
    mail log archives, and searches for messages sent to or from
    a particular address.It counts them up and mails a report
    with an inbound count and an outbound count. 
    =head1 DESCRIPTION
    This script scans the current mail log and several recent
    mail log archives, and searches for messages sent to or from
    a particular address.It counts them up and mails a report
    with an inbound count and an outbound count. 
    This script is intended to run as a batch job,
    It's good for getting a daily count of mail traffic
    for a particular address. It counts mail messages sent
    to a recipient on the day before. 
    I tried to take into consideration as many error
    conditions as possible. It only counts successfully
    sent messages. Queued attempts are not counted. 
    Also, mail from the mailer-daemon (i.e., bounces)
    are also ignored. 
    Limitation: since this script basically ignores any day
    but yesterday, it probably will not count messages which stay 
    in the queue from one day to the next. 
    =head1 PREREQUISITES
    This script requres C<Date::Manip>, as well as C<MIME::Entity>. 
    C<MIME::Entity> requires the C<MailTools> bundle, which itself requires 
    C<MIME::Base64>. (If I remember correctly... they're all nifty modules, 
    just install all of them.)
    =head1 COPYRIGHT
    Copyright (c) 1998,1999 John Nolan <jpnolan@sonic.net>. All rights reserved.
    This program is free software. You may modify and/or distribute it
    under the same terms as Perl itself. This copyright notice
    must remain attached to the file.
    =head1 REVISION
    $Id: countmail,v 1.7 1999/12/24 06:29:35 john Exp john $
    =cut
    # ---------------------------------------------------------------------------
    # CONFIGURATION - adjust these values for your setup
    # The log files we want to examine. $maillog will be read directly,
    # but the files in @maillog_archives will be gunzipped before
    # they are read. 
    #
    my $logdir = '/var/log';
    my $maillog= 'maillog';
    my @maillog_archives= qw( maillog.2.gz maillog.1.gz maillog.0.gz );
    # The target address. Make sure that the syntax here matches
    # what your versions of sendmail & syslogd actually record. 
    #
    my $target = 'nobody@nosuch.oops';
    my $ingrep = "to=$target";
    my $outgrep= "from=<$target>";
    my $sendmail= '/usr/lib/sendmail';
    my $gunzip = '/usr/local/bin/gunzip';
    # These should be comma-delimited lists
    #
    my $err_recipient = 'nobody@nosuch.oops';
    my $ok_recipients = 'nobody@nosuch.oops';
    # ---------------------------------------------------------------------------
    # You shouldn't need to modify anything below
    # this line, unless you want to hack the script itself. 
    # ---------------------------------------------------------------------------
    require 5;
    use Date::Manip;
    use MIME::Entity;
    use Getopt::Std;
    use strict;
    use vars qw( $opt_m );
    getopts('m');
    # ---------------------------------------------------------------------------
    # Initialize some global variables
    my $daemongrep = 'from=<>';
    my $yesterday = &UnixDate(&DateCalc("today", "- 1 day"), "%d");
    my $inbound_count = 0;
    my $outbound_count = 0;
    my $datestring = &UnixDate(&ParseDate("yesterday"), "%m/%d %a");
    my ($day,$msg,$address,$line);
    my (%was_seen_outbound);
    my (%was_seen_inbound);
    my (%from_daemon);
    # ---------------------------------------------------------------------------
    # MAIN LOGIC
    # ---------------------------------------------------------------------------
    # --------------------------------
    # Read in the log files
    foreach my $archive (@maillog_archives) {
    	open (LOGFILE, "$gunzip < $logdir/$archive |") or 
    		warn "Problem piping from $logdir/$archive : $!";
    	process_line($_) while defined ($_ = <LOGFILE>) ;
    }
    open (LOGFILE, "< $logdir/$maillog") or 
    	warn "Problem opening file $logdir/$maillog for reading: $!";
    process_line($_) while defined ($_ = <LOGFILE>) ;
    close(LOGFILE);
    # --------------------------------
    # Format counts, subject header, recipients
    $inbound_count= commify($inbound_count);
    $outbound_count = commify($outbound_count);
    my ($subject,$recipients);
    $subject= "Mail count ($target) $datestring: $inbound_count in - $outbound_count out";
    if ($opt_m) {
    	$subject= $subject;
    	$recipients = $ok_recipients;
    } else {
    	$subject = "(Test mail) $subject";
    	$recipients = $err_recipient;
    }
    # --------------------------------
    # Compose mail message, Attach HTML docs to message
    #
    my $mesgbody = sprintf <<EOM;
    This message is generated automatically each day. 
    Please send any questions or complaints to $err_recipient
    EOM
    # --------------------------------
    # Create mail object, attach messages, and send it off
    #
    my $mimedoc = build MIME::Entity
    	Type => "multipart/mixed",
    	-From=> "Mail counter <$err_recipient>",
    	-To => "$recipients",
    	-Subject => "$subject";
    attach $mimedoc Data=>$mesgbody;
    open MAIL, "| $sendmail -t -i"
    	or die "Problem piping to $sendmail: $!";
    $mimedoc->print(\*MAIL);
    # ---------------------------------------------------------------------------
    # END of main logic
    # ---------------------------------------------------------------------------
    # ---------------------------------------------------------------------------
    # This function is called for each line of the log files. 
    # It takes into account the problem of queued messages
    # (i.e., it only counts successful attempts to send a message). 
    #
    # This function probably will not count messages that spend 
    # one or more midnights in the queue. 
    #
    # Each mail message can generate 4 or more lines in a log file. 
    #
    # Use the hashes %was_seen_outbound, %was_seen_inbound,
    # and %from_daemon (which are keyed on message serial number)
    # to record various facts about a message and remember them
    # when we process future lines. (For example, if a message 
    # was sent by a daemon, then we can't count it.) 
    #
    #
    sub process_line {
    	chomp($line = shift);
    	# Grab the 2nd, 6th and 7th field of each line. 
    	# $msg contains the message serial number. 
    	#
    	($day,$msg,$address) = (split /\s+/, $line)[1,5..6];
    	# Skip this line unless it was written yesterday
    	#
    	return unless $day == $yesterday;
    	# Note any messages which were sent by a daemon
    	#
    	if ($address =~ /$daemongrep/o) {
    		$from_daemon{ $msg } = 1 ;
    	}
    	# Note that a message was logged as inbound
    	#
    	if ($address =~ /$ingrep/o) {
    		$was_seen_inbound{ $msg } = 1 ;
    	}
    	# Now we look for a line indicating that 
    	# that this particular message was actually sent. 
    	# If we find one, count it. 
    	#
    	if ($line =~ /stat=Sent/ and defined $was_seen_inbound{ $msg } ) {
    		# But DON'T count it if it was sent by a daemon
    		#
    		unless( defined $from_daemon{ $msg } ) {
    			$inbound_count++;
    		} else {
    			undef $from_daemon{ $msg };
    		}
    		undef $was_seen_inbound{ $msg };
    	}
    	# Note that a message was logged as outbound
    	#
    	if ($address =~ /$outgrep/o) {
    		$was_seen_outbound{ $msg } = 1 ;
    	}
    	# Now we actually count it if we find a line indicating
    	# that this particular message was actually sent. 
    	# If we find one, count it. 
    	#
    	if ($line =~ /stat=Sent/ and defined $was_seen_outbound{ $msg } ) {
    		$outbound_count++;
    		undef $was_seen_outbound{ $msg };
    	}
    }
    # ---------------------------------------------------------------------------
    # Regex to comma-delimit numbers (Perl FAQ 5)
    #
    sub commify {
    	local $_ = shift;
    	1 while s/^(-?\d+)(\d{3})/$1,$2/;
    	return $_;
    }

