#!/usr/bin/perl

##################################################
# pemail
# ======
# Perl Mail Client
#
# (c) 2000 Mike Roessing
# 
# Incorporating changes made by Philip Nelson
# (philip@bojnice.com) to listmsgs()
#################################################

use Mail::POP3Client;
use MIME::Lite;
use Term::ReadKey;
use strict;

my $version = 1.2;
my $servername;
my $pop;
my $username;
my $password;

$SIG{PIPE}= 'IGNORE';

readswitches($version);

serverconnect($servername);



sub readswitches {
	my($version) = @_;

	

	if ((defined($ARGV[0])) && (($ARGV[0] eq "-h") || ($ARGV[0] eq "--help"))) {
		showhelp("command");
		exit;
	} elsif ((defined($ARGV[0])) && (($ARGV[0] eq "-v") || ($ARGV[0] eq "--version"))) {
		print "pemail v$version\n";
		exit;
	} elsif ((defined($ARGV[0])) && (($ARGV[0] eq "-s"))) {
		my $emailto = $ARGV[1];
		sendmsg("command", $emailto);
		exit;
	} elsif ((defined($ARGV[0])) && ($ARGV[0] eq "-P")) {
		$servername = $ARGV[1];
		if ((defined($ARGV[2])) && ($ARGV[2] eq "-u")) {
			if ((!defined($ARGV[3])) || ($ARGV[3] eq "")) {
				print "Please specify a user name with -u.\n";
				exit;
			} else {
				$username = $ARGV[3];
			}
			if ((defined($ARGV[4])) && ($ARGV[4] eq "-p")) {
				if ((!defined($ARGV[5])) || ($ARGV[5] eq "")) {
					print "Please specify a password with -p.\n";
					exit;
				} else {
					$password = $ARGV[5];
				}
			} elsif (defined($ARGV[4])) {
				print "Invalid switch: $ARGV[4]\n";
			}
		} elsif ((defined($ARGV[2])) && ($ARGV[2] eq "-p")) {
			print "Please use -u to specify a username before using -p.\n";
			exit;
		} elsif (defined($ARGV[2])) {
			print "Invalid switch: $ARGV[2]\n";
			exit;
		}
	} else {
		print "You must specify a POP3 server with the -P switch first.\n";
		exit;
	}
}

sub showhelp {
	my($from) = @_;
	if ($from eq "interface") {
        print "pemail: Perl Email: v$version\n";
                print "---------------------------\n";
                print "h                print this help\n";
                print "d                mark a message for deletion\n";
                print "u                unmark any messages marked for deletion\n";
                print "l                list messages in your mailbox (5 at a time)\n";
                print "#                view message #\n";
                print "s                send a message\n";
	        print "q                quit pemail\n";
	        print "\nCommand [h for help]: ";
        } elsif ($from eq "command") {
		print "Usage to view pop3 messages: pemail -P server [-u username [-p password]]\n";
		print "Usage to send a message:     pemail -s [address]\n";
		print "  -h, --help			show this help text\n";
		print "  -P pop3server                  use pop3server as the pop3 server\n";
		print "  -u username			use username as the pop3 login name\n";
		print "  -p password			use password as the pop3 login password\n";
		print "  -s				send an email from the command line\n";
		print "  -v, --version			show version number\n";
		exit;
	}
}

sub serverconnect {
	my($server) = @_;

	if (!defined($username)) {
		print "Please enter your user name: ";
		chomp($username = <STDIN>);
	}
	if (!defined($password)) {
		print "Please enter your password: ";
		ReadMode('noecho');
		chomp($password = ReadLine(0));
		ReadMode('restore');
		print "\n";
	}

	$pop = new Mail::POP3Client(	USER		=> "$username",
					PASSWORD	=> "$password",
					HOST		=> "$server",
					AUTH_MODE	=> "PASS" );

	my $state = $pop->State;
	if ($state eq "TRANSACTION") {
		my $nummsgs = $pop->Count();
		if ($nummsgs > 0) {
			print "You have $nummsgs messages in your mailbox.\n\n";
		} else {
			print "You have no messages in your mailbox.\n\n";
		}
		interface($nummsgs);
	} else {
		print "Could not connect to POP3 server.\nCheck user name and/or password, and make sure the server is available.\n";
	}
}

sub interface {
	my $from;
	my $subject;
	my($nummsgs) = @_;

	print "Command [h for help]: ";
	while (my $answer = <STDIN>) {
		chomp ($answer);
		if ($answer eq "h") {
			showhelp("interface");
		} elsif ($answer eq "d") {
			deletemsg();
		} elsif ($answer eq "u") {
			undelete();
		} elsif ($answer eq "l") {
			listmsgs();
		} elsif ($answer eq "s") {
			sendmsg("interface", "");
		} elsif ($answer eq "q") {
			$pop->Close();
			exit;
		} elsif (($answer > 0) && ($answer < 1000)) {
			viewmsg($answer);
		} else {
			print "$answer is not a valid pemail command.\n\nCommand [h for help]: ";
		}
	}
}

sub deletemsg {
	print "Enter a message # to delete: ";
	chomp (my $message = <STDIN>);
	$pop->Delete($message);
	print "Message $message marked for deletion.\n\n";
	print "\nCommand [h for help]: ";
}

sub undelete {
	$pop->Reset();
	print "All messages marked for deletion will not be deleted.\n\n";
	print "\nCommand [h for help]: ";
}

sub listmsgs {
	# Get list of message numbers and their size
	# Process each message individually
	my $count = 0;
	foreach my $listline(split /^/m, $pop->List()) {
		my $msg_number ;
		my $msg_size   ;
		my %header_data;
		if ($listline =~ /(\d+)\s+(\d+)/o) {
			$msg_number = $1;
			$msg_size   = $2;

			printf "Message #: %05d\tMessage Size: %10d\n",$msg_number, $msg_size;

			# Load headers into hash
			# (only first lines separated by colon)
			
			%header_data = ();
			foreach my $headline(split /^/m, $pop->Head($msg_number)) {
				if ($headline =~ /(.+):\s(.+)/) {
					$header_data{$1} = $2;
				}
			}
			#	 print map { "$_ => $header_data{$_}\n" } keys %header_data;
			my $from = $header_data{"From"};
			my $subj = $header_data{"Subject"};
			printf "From     : $from\n";
			printf "Subject  : $subj\n";
			printf "=============================================================================\n";
		}
		$count++;
		if ((($count % 5) == 0) && ($count != 0)) {
			print "Hit <enter> to continue (q then <enter> to quit)";
			ReadMode('noecho');
			my $any_key;
			chomp($any_key = ReadLine(0));
			ReadMode('restore');
			if ($any_key eq "q") {
				print "\n";
				last;
			}
			print "\n\n";
		}
        }
	print "\nCommand [h for help]: ";
}

sub viewmsg {
	my($message) = @_;
	my $from;
	my $subject;
	my $body = $pop->Body($message);
	foreach ($pop->Head($message)) {
		if ($_ =~ /^From:\s+/i) {
			$from = $_;
		} elsif ($_ =~ /^Subject:\s+/i) {
			$subject =$_;
		}
	}
	open (MORE,"|more");
	print MORE "\n$from\n$subject\n$body\n";
	close (MORE);
	print "\nCommand [h for help]: ";
}

sub sendmsg {
        my $receiver = undef;
	my($sentfrom, $emailto) = @_;
	if ($emailto ne "") {
		$receiver = $emailto;
	}
	my $i = 0;
	while ( (!defined($receiver)) || ($receiver eq "\n") ) {
		if ( $i > 0 ) {
			print "\nYou must enter at least one recipient.\n";
		} else {
			print "Enter your recipients, entries separated by a comma.\n";
		}
		print "To: ";
		$receiver = <STDIN>;
		$i++;
	}

	print "\nEnter your Cc list, entries separated by a comma (leave blank for none).\nCc: ";
	my $cc = <STDIN>;

	print "\nSubject: ";
	my $subject = <STDIN>;

	print "\nNow type the body of the message, ending it with a <Ctrl>-d on a newline:\n";
	my @message = <STDIN>;

	my $user = `whoami`;
	my $domain = `hostname -d`;

	my $new_message = MIME::Lite->new(
			From    =>"$user\@$domain",
			To      =>"$receiver",
			Cc      =>"$cc",
			Subject =>"$subject",
			Type    =>'TEXT',
			Data    =>" @message");

	print "\nWould you like to attach a file [y/n]? ";
	my $attachans = <STDIN>;
	chomp ($attachans);
	while (($attachans ne "y") && ($attachans ne "n")) {
		print "\n$attachans is not a valid response.  Would you like to attach a file [y/n]? ";
		$attachans = <STDIN>;
		chomp ($attachans);
	}
	while ($attachans ne "n") {
		addattachment($new_message);
		print "\nWould you like to attach another file [y/n]? ";
		$attachans = <STDIN>;
		chomp ($attachans);
		while (($attachans ne "y") && ($attachans ne "n")) {
			print "\n$attachans is not a valid response.  Would you like to attach another file [y/n]? ";
			$attachans = <STDIN>;
			chomp ($attachans);
		}
	}

	$new_message->send;
	if ($sentfrom eq "interface") {
		print "\nMessage sent.\n\nCommand [h for help]: ";
	} else {
		print "\nMessage sent.\n\n";
	}
}

sub addattachment()
{
	my($msg) = @_;
	print "\nPlease enter the full path to the file: ";
	my $file = <STDIN>;
	chomp ($file);
	if (!checkfile($file)) {
		print "File cannot be found: $file\n";
		return 0;
	}
	my @parts = split(/\//,$file);
	my $filename = pop (@parts);
	print "\n$filename has been attached.\n";
	$msg->attach(	Type		=> 'BINARY',
			Path		=> "$file",
			Filename	=> "$filename"
			);
}

sub checkfile()
{
	my($file) = @_;
	my @stuff = stat($file) || return 0;
	return 1;
}

