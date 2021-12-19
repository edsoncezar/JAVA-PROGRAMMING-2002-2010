 #! /usr/bin/perl
    # Program to connect to mail server
    # and send mail to a specified user
    # Pray to god this works
    use IO::Socket; #tell perl to use IO *Input Output) Internet Socket
    if (!$ARGV[2]) { #if nothign is entered, start this routine
    print "Program intended to send mail anonymously\n"; #message to user
    print "Usage: perl anonsend.pl [server] [sender] [recepient]\n"; #more message =)
    exit; # finish this routine
    } #end this routine
    # Time to define some variables!
    $server = $ARGV[0]; # what server to use to send the mail
    $sender = $ARGV[1]; # who the e-mail is from
    $recpt = $ARGV[2]; # who the e-mail is to
    { # start new routine 
    print"Type message to send: "; #asks user for message
    $message = <STDIN>; # saves the input as string message
    $remote = IO::Socket::INET->new( PeerAddr => "$server", Proto => "tcp", PeerPort => "25");
    print "Connecting...\n"; # update user with status
    sleep(1);# tells program to stop and wait
    print $remote "HELO computer\n"; # telling mailserver hello
    sleep(1); 
    print "Sending E-Mail Header Information\n"; # update user with status again
    print $remote "MAIL FROM: $sender\r\n"; # Tells server who mail is spoofed from
    sleep(1);
    print $remote "RCPT TO: $recpt\n"; # Tells server who to send the email to
    sleep(1); 
    print $remote "DATA\n"; # Tells server to start process off receiving our email message
    sleep(1);
    print "Sending Message\n"; # Update user of status again
    print $remote "$message\n"; # Sends the string we entered earlier into the server
    sleep(1);
    print $remote ".\n"; # Tells SMTP server to stop receiving data, and send message
    sleep(1);
    print "Anonymous E-Mail Sent!\n"; # tells user program is finished
    exit; # stop this subroutine
    }
