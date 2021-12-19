#!/usr/bin/perl
    =pod
    =head1 NAME
    autoresponder - A scrtipt for receiving a mail and immediately replying.
    =head1 SYNOPSIS
    autoresponder [options] [filename]
    =head1 DESCRIPTION
    While installing a new mail server or client you typically are sending
    and receiving test mails over and over again. Even worse, you sometimes
    have to do a phonecall and ask someone for sending a mail to you.
    This script will help you in some cases by setting up an email address
    like autoresponder@company.com that will receive email addresses and
    immediately reply it back.
    =head1 INSTALLATION
    Install the prerequisite Perl modules, in particular Graham Barr's
    excellent Mailtools package. L<Mail::Internet(3)>.
    In /etc/mail/aliases or /etc/aliases, put lines like this:
    	autoresponder:	"| /usr/local/bin/autoresponder"
    	owner-autoresponder:	/dev/null
    	autoresponder-owner:	/dev/null
    Then do a "newaliases".
    Edit the autoresponder script and change the reply-to address to
    point back to one of the owner addresses. This should have the
    advantage that you won't see error messages generated by the
    autoresponder.
    =head1 SCRIPT CATEGORIES
    mailstuff
    =head1 PREREQUISITES
    The MailTools package, in particular the Mail::Internet module.
    L<Mail::Internet(3)>.
    =head1 OSNAMES
    any OS using sendmail or a compatible mail server
    =head1 AUTHOR
    	Jochen Wiedmann
    	Am Eisteich 9
    	72555 Metzingen
    	Germany
    	Email: joe@ispsoft.de
    =head1 SEE ALSO
    L<Mail::Internet(3)>, L<aliases(5)>
    =cut
    use strict;
    ############################################################################
    #
    #Configurable section
    #
    ############################################################################
    my $REPLY_TO = 'autoresponder-owner@neckar-alb.de';
    #
    #Use an entry like
    #
    #	autoresponder-owner:	/dev/null
    #
    #to suppress error messages from autoresponders replies.
    #
    ############################################################################
    use Mail::Internet ();
    use Getopt::Long ();
    use vars qw($opt_debug $opt_verbose $opt_help);
    sub Usage() {
    print <<EOF;
    Usage: autoResponder [options] [filename]
    Reads an email from [filename] (default: stdin) and replies to the sender.
    Possible options are:
    --debug Turn on debugging mode. (Suppresses actions)
    --help Print this help message.
    --verboseTurn on verbose mode.
    EOF
    exit 1;
    }
    eval { Getopt::Long::GetOptions('debug', 'verbose', 'help') };
    Usage() if $@ || $opt_help;
    $opt_verbose = 1 if $opt_debug;
    my $fh;
    if (@ARGV) {
    my $file = shift @ARGV;
    open(FILE, "<$file") or die "Failed to open $file: $!";
    $fh = \*FILE;
    print "Reading mail from $file.\n" if $opt_verbose;
    } else {
    $fh = \*STDIN;
    print "Reading mail from STDIN.\n" if $opt_verbose;
    }
    my $msg = Mail::Internet->new($fh, 'Modify' => 0, 'MailFrom' => 'KEEP');
    my @headers = @{$msg->head()->header()};
    my @body = @{$msg->body()};
    my @message = ("\n",
    	"Your mail was received by the autoresponder.\n",
    	"\n",
    	"Your headers have been:\n",
    	@headers,
    	"End of headers\n",
    	"\n",
    	"Your body follows:\n",
    	@body
    	);
    $msg = $msg->reply();
    $msg->body(\@message);
    print("Replying to $REPLY_TO.\n") if $opt_verbose;
    $msg->head()->replace('Reply-To', $REPLY_TO);
    print("Replying message:\n", $msg->as_string()) if $opt_verbose;
    $msg->smtpsend() unless $opt_debug;

