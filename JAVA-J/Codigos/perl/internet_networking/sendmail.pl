 #!/usr/bin/perl -w
    ########################################
    ## This is just a simple script to send
    ## Email with the Net::SMTP module
    ########################################
    use Net::SMTP;
    use strict;
    	
    ########################################
    ## 1. Give the hostname or IP address to 
    ## the SMTP server
    ## 2. Specify the From address ->mail();
    ## 3. Specify the To address
    ## 4. Send the data.
    ## 5. close data send
    ## 6. Quit
    ########################################
    my $smtp = Net::SMTP->new('mailhost');
    $smtp->mail('fromsomebody@hotmail.com');
    $smtp->to('somebody@hotmail.com');
    $smtp->data();
    $smtp->datasend("To: The Persons Name\n");
    $smtp->datasend("\n");
    $smtp->datasend("Hello, I'm sending a test message\n");
    $smtp->datasend("To test out how this Net::SMTP works\n");
    $smtp->dataend();
    $smtp->quit();
    	
    	
