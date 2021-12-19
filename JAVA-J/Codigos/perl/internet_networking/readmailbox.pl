#!/usr/bin/perl -w
    # Checks to see how many new mail messages are in the inbox.
    # Change the value of $mailbox to your Unix mail spool.
    use strict;
    my $old_messages= 0;
    my $total_messages = 0;
    my $new_messages= 0;
    my $headers= 0;
    my $mailbox= '/your/path/to/mailbox/here';
    my $VERSION = 1.1;
    open(MAILBOX, "<$mailbox") or die "Couldn't open $mailbox for reading: $!\n";
    MESSAGE:
    while (<MAILBOX>) {
    	if (/^From /) { #Start of the header block
    		$headers = 1;
    	} elsif (/^$/) { #End of the header block
    		$headers = 0;
    	}
    	if ($headers == 0) {
    		next MESSAGE;
    	}
    	if (/^From: /) { #One From: header per message
    		$total_messages++;
    	} elsif (/^Status: /) { #Only the read messages have the Status: header
    		$old_messages++;
    	}
    }
    close(MAILBOX);
    $new_messages = $total_messages - $old_messages;
    print "There are $new_messages new message(s), and $total_messages total message(s) in your inbox.\n";
    __END__
    =head1 NAME
    chk_mail - Checks the number of new and total messages in your Unix mailbox
    =head1 DESCRIPTION
    When using the Unix shell, and using Pine or elm (or possibly others, it has been tested with Pine and elm), this will let you
    know how many new messages you have in your mailbox, and how many total. Most people place a call to this in their .profile or
    .login scripts, to let them know if they should check their mail.
    =head1 README
    When using the Unix shell, and using Pine or elm (or possibly others, it has been tested with Pine and elm), this will let you
    know how many new messages you have in your mailbox, and how many total. Most people place a call to this in their .profile or
    .login scripts, to let them know if they should check their mail. This script uses no Perl modules. Enhancements can be made
    if people see a need.
    =head1 PREREQUISITES
    None.
    =head1 COREQUISITES
    None.
    =pod OSNAMES
    Any Unix-like only
    =pod SCRIPT CATEGORIES
    Mail
    =cut

