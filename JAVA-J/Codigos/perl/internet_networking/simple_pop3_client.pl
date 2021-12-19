#!/usr/bin/perl -w

use Net::POP3;

# Connect to pop3 server
my $pop3 = Net::POP3->new("10.10.10.10") || die "Error : Couldn't log on to server";

# Login to pop3 server
my $Num_Message = $pop3->login("dummy", "dummy");

my $Messages = $pop3->list();

my ($MsgId, $MsgDate, $MsgFrom, $MsgTo, $MsgCc, $MsgSub);
my ($MsgAttach, $MsgSize, $MsgHeader, $MsgHeadFlg, $MsgBody);

foreach $MsgNo (keys %$Messages)
{
  my $MsgContent = $pop3->get($MsgNo);
  my $count = 0;
  $MsgHeadFlg = 0;
  $MsgBody = "";
  print "Message No : $MsgNo\n";

  $MsgSize = $pop3->list($MsgNo);
  print "Message Size : $MsgSize Bytes\n";
  
  # Process message data
  while()
  {

    # Exit if last line of mail
    if ($count >= scalar(@$MsgContent))
    {
      print "$MsgBody";
      last;
    }

    # Check if end of mail header
    if (@$MsgContent[$count] =~ /^\n/)
    {
      $MsgHeadFlg = 1;
    }

    # Proceed if message header not processed
    if (not $MsgHeadFlg)
    {

      # Split the line 
      my @LineContent = split /: /, @$MsgContent[$count];

      # Check Header Info
      SWITCH:
      {
        # Get message date
        $LineContent[0] =~ /Date/i && do
                                       {
                                         $MsgDate = $LineContent[1];
                                         print "Date : $MsgDate";
                                         last SWITCH;
                                       };

        # Get message id
        $LineContent[0] =~ /Message-ID/i && do
                                       {
                                         $MsgId = $LineContent[1];
                                         print "Message ID : $MsgId";
                                         last SWITCH;
                                       };

        # Get message from
        $LineContent[0] =~ /From/i && do
                                      {
                                        $MsgFrom = $LineContent[1];
                                        print "From : $MsgFrom";
                                        last SWITCH;
                                      };

        # Get message to
        $LineContent[0] =~ /To/i && do
                                      {
                                        $MsgTo = $LineContent[1];
                                        print "To : $MsgTo";
                                        last SWITCH;
                                      };

        # Get message cc
        $LineContent[0] =~ /Cc/i && do
                                      {
                                        $MsgCc = $LineContent[1];
                                        print "Cc : $MsgCc";
                                        last SWITCH;
                                      };


        # Get message subject
        $LineContent[0] =~ /Subject/i && do
                                      {
                                        $MsgSub = $LineContent[1];
                                        print "Subject : $MsgSub";
                                        last SWITCH;
                                      };
      }
    }
    else
    {
      # Process message body
      $MsgBody .= @$MsgContent[$count];
    }

    $count++;
  }
}

# Disconnect from pop3 server
$pop3->quit();


