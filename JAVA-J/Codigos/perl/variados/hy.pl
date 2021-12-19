#!/usr/bin/perl
    #By HypA
    #Greetz to everyone who idles on irc.digital-root.com
    use IO::Socket;
    $sock = IO::Socket::INET->new(
    PeerAddr => 'irc.digital-root.com', 
    PeerPort => 6667, 
    Proto => 'tcp' ) or die "could not make the connection";
    while($line = <$sock>){
    print $line;
    if($line =~ /(NOTICE AUTH).*(checking ident)/i){
    print $sock "NICK pmub\nUSER bot 0 0 :bot\n";
    last;
    }
    }
    while($line = <$sock>){
    print $line;
    #use next line if the server asks for a ping
    if($line =~ /^PING/){
    print $sock "PONG :" . (split(/ :/, $line))[1];
    }
    if($line =~ /(376|422)/i){
    print $sock "NICKSERV :identify nick_password\n";
    last;
    }
    }
    sleep 3;
    print $sock "JOIN #digichat\n";
    while ($line = <$sock>) {
    ($command, $text) = split(/ :/, $line);#$text is the stuff from the ping or the text from the server
    if ($command eq 'PING'){
    #while there is a line break - many different ways to do this
    while ( (index($text,"\r") >= 0) || (index($text,"\n") >= 0) ){ chop($text); }
    print $sock "PONG $text\n";
    next;
    }
    #done with ping handling
    ($nick,$type,$channel) = split(/ /, $line); #split by spaces
    ($nick,$hostname) = split(/!/, $nick); #split by ! to get nick and hostname seperate
    $nick =~ s/://; #remove :'s
    $text =~ s/://;
    #get rid of all line breaks. Again, many different way of doing this.
    $/ = "\r\n";
    while($text =~ m#$/$#){ chomp($text); }
    if($channel eq '#digichat'){
    print "<$nick> $text";
    				 if($text =~ /hi bot/gi){
    print $sock "PRIVMSG #digichat :hi $nick\n";
    		
    				}elsif($text =~ /shut up bot/gi){
    							print $sock "PRIVMSG #digichat :no, you shut up\n";
    }elsif($text =~ /if you don't/gi){
    							print $sock "PRIVMSG #digichat :please don't threaten people $nick\n";
    }elsif($text =~ /code/gi){
    							print $sock "PRIVMSG #digichat :did you know I coded myself $nick?\n";
    				}elsif($text =~ /slaps bot/gi){
    							print $sock "PRIVMSG #digichat :please refrain with slapping me with trouts $nick or I shall slap you with a very spikey porcupine.\n";
    				}elsif($text =~ /hacking links/gi){
    							print $sock "PRIVMSG #digichat :Here, digital-root.com, progenic.com, spymodem.com, brain-hack.org, google.com, apple.com, yahoo.com.\n";
    }elsif($text =~ /graphic links/gi){
    							print $sock "PRIVMSG #digichat :http://tutorialforums.com/, xanthic.com, the webmachine.com.\n";
    }
    {
    if($text =~ /!op hypa/gi){
    print $sock "MODE #digichat +o HypA\n";
    }
    }
    }
    }

