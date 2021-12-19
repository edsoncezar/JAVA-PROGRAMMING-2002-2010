  #!/usr/bin/perl -w
    use strict;
    use CGI;
    my $query = CGI->new;
    my %form = %{$query->Vars};
    use CGI qw/:standard/;
    print header,
    start_html('EVS'),
    start_form,
    "What's your email address? ",textfield('usermail'),p,
    submit,
    end_form,
    hr;
    my $chars;
    my $adminmail = "admin\@test.com";
    my $sendmail = "/usr/lib/sendmail";
    my $site = "http://www.site.com/cgi-bin/evs.pl";
    my $accountID = $query->url_param('accountID');
    my $accountAD = $query->url_param('accountAD');
    my $storedID = "stored.txt" ;
    my $addresses = "addresses.txt";
    my @chars = ( "A" .. "Z", "a" .. "z", 0 .. 9, qw(! $ % ^ & * ) );
    my $ID;
    ;
    if ($accountID && $accountAD) {
    open (ADDRESSES, ">>$addresses") or die "Cannot open addresses.txt for writing";
    print ADDRESSES "$accountAD\n";
    close (ADDRESSES);
    print "SUCCESS! Your email address has been added to the system!\n";
    }
    elsif ($form{'usermail'}) {
    # READING OF FILE
    open(STORED, "< $storedID") or die "Cannot access stored.txt for reading";
    my @used = <STORED>;
    chomp(@used);
    do { $ID = join '', map { $chars[ rand @chars ] } 1..17; } 
    while grep {$_ eq $ID} @used;
    print "An email has been sent to $form{'usermail'} for verification. The link must be activated for your account to 
    be listed\n";
    close(STORED);
    }
    # WRITING TO FILE
    open(STORED, ">> $storedID") or die "Cannot access stored.txt for writing";
    print STORED "$ID\n";
    close(STORED);
    # my $dbase = "$form{'usermail'}::$ID";
    # my @dbase = split /::/, "$dbase\n";
    # print "@dbase\n";
    my $accountID = $ID;
    my $accountAD = "$form{'usermail'}";
    open (MAIL, "|$sendmail -t") or die "Cannot access mail";
    print MAIL "To: my $form{'usermail'}\n";
    print MAIL "From: $adminmail\n";
    print MAIL "Subject: Verify your Email Address\n\n";
    print MAIL "$site/1.pl?accountID=$accountID&accountAD=$accountAD\n\n";
    print MAIL "If the link above is not active copy and paste it in your broswer.";
    close (MAIL);
