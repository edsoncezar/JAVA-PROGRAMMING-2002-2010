#!/usr/bin/perl -w 

use Carp            ;
use MIME::Lite      ;
use Data::Dumper    ;
use Mail::Bulkmail  ;
use Parallel::ForkManager ;
use strict ;

# validate the args passsed in
croak "No Session ID or listname provided!!!
Usage: $0 session_id listname\n" if (int @ARGV < 2) ;


# set options
my ($session_id, $listname, $debug) = @ARGV ;

# Set Vars 
my $count           ;

my %content         ;
my $testsend        ;
my $valid_content   ;
my $base_dir    =   "/opt/listman"      ;
my $data_dir    =   "$base_dir/data"    ;
my $list_dir    =   "$data_dir/lists"   ;
my $mail_dir    =   "$data_dir/mail"    ;
my $log_dir     =   "$base_dir/logs"    ;
my $log_file    =   "$log_dir/send.log" ;
my $work_dir    =   "$base_dir/work/$session_id" ;

# Mail Specific Variables 
my @smtp_servers= qw(mail1 mail2 mail3) ;
my $from_header = "\"Foo.com\"<$listname\@foo.com>" ;
my $to_header   = $from_header ;
my @types       = qw(txt html) ;
my $resend      = 60 ; #time in seconds to retry on a failed servr connect

# bulkmail vars
my $envelope = 1 ;
my $domain   = "foo.com" ;
my $ver_header     = 'MyMail 1.0.1' ;
my $envelope_limit = 100 ;

# Mime encodings. 
my %TYPES = (
    csv  => [ 'text/csv', '8bit' ],
    gif  => [ 'image/gif', 'base64' ],
    tiff => [ 'image/tiff', 'base64' ],
    tif  => [ 'image/tiff', 'base64' ],
    jpeg => [ 'image/jpeg', 'base64' ],
    jpg  => [ 'image/jpeg', 'base64' ],
    zip  => [ 'application/zip', 'base64' ],
    gz   => [ 'application/gzip', 'base64' ],
    html => [ 'text/html', '8bit' ],
    htm  => [ 'text/html', '8bit' ],
    pdf  => [ 'application/pdf', 'base64' ],
);

#   Ok defs are over with lets get cracking. 
#-------------------------------------------------------------------------------

# open the log 
open(LOG_FILE, ">> $log_file")  
    or croak "Unable to open log file - Permission issue? - Aborting!\n" ;
log_report("-={ STARTING }=-");

# make sure work dir is there. 
croak "Work direcory: $work_dir does not exist.\n" 
    unless ( -d $work_dir );

# set pidfile
open(PIDOUT,">$work_dir/send_PID.dat") 
    or croak log_report("can't write pidfile $work_dir/send_PID.dat.");
print PIDOUT "$$\n";
close(PIDOUT);

# determine mode and print out the status. 
$testsend = 1 if ( -f "$work_dir/.testsend" );
log_report( "------===={ TEST MODE ENABLED }====------" ) if $testsend ;
log_report("Processing list [$listname] ID:$session_id");


# validate and prep files
log_report("Opening operating files") ;

# load up the subject
log_report("- Loading subject info");
open(SUBJECT_FILE, "<$mail_dir/$listname.sub") 
    or croak log_report( "Error with subject file.\n\tFile:$mail_dir/$listname.sub");

my $subject ;
if ($testsend)
{
    $subject = "TEST - ". <SUBJECT_FILE> ;
} else { $subject = <SUBJECT_FILE> ; }
close(SUBJECT_FILE);
$subject =~ tr/\cM\cJ//d ; # remove all platforms newline

# Get the file body for each type of content. 
log_report( "Loading content" );
foreach my $ext (@types)
{
    if (-f "$mail_dir/$listname.$ext")
    {
        log_report( "\t- $ext") ;
        open FILE, "<$mail_dir/$listname.$ext"
            or croak log_report("couldn't open") ;
        $valid_content = 1 ;
        $content{$ext} .= $_ while <FILE> ;
        close FILE ;
    }
}
croak log_report( "Content not found for list [$listname]!") 
    unless ($valid_content) ;

# * Checks if it should send to the test list or real list
my $list_file;
if ($testsend)
{
    $list_file = "$list_dir/TEST_BASE.LST";
} else { $list_file = "$list_dir/$listname.lst"; }

open(LIST_FILE, "< $list_file")
    or croak log_report("Permission error or file not found opening list file - Aborting!\n");
DEBUG( Dumper(\%content) );

# now we slurp up the list.. loaded in one array then split it up into equal 
# parts based on the number of servers we got. Take each part and sort into 
# an array by domain.. need to watch mem usage. 
my @addys ;
my @keys = qw(name domain);
while (<LIST_FILE>)
{
    chomp;
    my %rec ;
    @rec{@keys} = split /\@/ ; # load addy into list of hashes name/domain as keys..
    push @addys, \%rec ;
}

# now sort entries by domain
@addys = sort { lc $a->{'domain'} cmp lc $b->{'domain'}}@addys ;
my @list_addresses ;

# push sorted entries int array of addys..
foreach  (@addys) { push @list_addresses, "$$_{'name'}\@$$_{'domain'}" } ;
undef @addys ;  # need to save mem :-)

# now split the lists. 
my $total       = int(@list_addresses) ;
my $total_servers = int(@smtp_servers)  ;
my $listsize = int($total / $total_servers) ;

# figure out the list numbers 
my $send_num = 1;
my %sends;
foreach my $server (@smtp_servers)
{
    if ($send_num != $total_servers )
    {
        # pop $listsize off out @list_addresses 
        @{$sends{$server}} = splice(@list_addresses, 0, $listsize) ;
    }
    else
    {
        # put the remailder in the last list. 
        $sends{$server} = \@list_addresses ;
    }
    $send_num++;
}
log_report( "* Loaded $total Adresses into $total_servers lists of $listsize adresse*") ;
log_report( "Creating the Bulkmail objects" );

# Forkmanager object and callback definitions
my $pm = new Parallel::ForkManager( $total_servers )
    or croak log_report("Odd couln't make Fork manager object Exiting") ;

# when a childe spawns
$pm->run_on_start(
    sub {
        my ($pid,$ident)=@_;
        log_report("Parent Starting Child $pid Sending to $ident");
    });

# when a childe finishes
$pm->run_on_finish(
    sub {
        my ($pid, $exit_code, $ident, $error) = @_;
        log_report( "Child at $pid completed on $ident with code:$exit_code");
    });

# while we wait.. 
$pm->run_on_wait( sub{log_report( "Waiting for children ..." );} );

# setup the message body as multipart mime..
# can/should put this in a loop.. for each .ext we can have a type.. 
# this way can send whatever.. 
my $msg = MIME::Lite->new( 
            Type =>'multipart/mixed',
            Datestamp => 0
          );

foreach my $key (sort keys %content)
{
    my $type = $TYPES{ lc $key } || [ 'text/plain', '8bit' ];
    $msg->attach(
        Type => $type->[0],
        Encoding => $type->[1],
        Data => $content{$key}
    );
}
my $body = $msg->as_string ;
DEBUG( Dumper($msg)) ;

DEBUG( $body );
# force the To/From on the message manually. 
# its an issue with how Bulkmail deals with adresses.. 
# and it is annoying Mail::Bulkmail pukes on FOO <foo@dom.com> 
# seems to add a <> around whole thing.. and the mail servers
# barf on it.. 
$body = "From: $from_header\nTo: $to_header\n".$body ;
DEBUG( $body );

# Loop fork  and send 
# Setup the bulkmail objects, and fork to send.. 
my %bm ;
foreach my $server (@smtp_servers)
{
    $bm{$server} = Mail::Bulkmail->new(
        Subject         => $subject,
        Message         => $body,
        LIST            => $sends{$server},
        Smtp            => $server,
        'X-Newsletter'  => $ver_header,
        Domain          => $domain,
        BAD             => "$log_dir/$server-bad.log",
        ERRFILE         => "$log_dir/$server-error.log",
        use_envelope    => $envelope,
        envelope_limit  => $envelope_limit,
        HFM             => 1,# make BM read the msg for headers 
        log_full_line   => 1,
        Trusting        => 0
    ) or croak log_report($bm{$server}->error) ;


    DEBUG( Dumper($bm{$server}) );
    # Forks and returns the pid for the child:
    my $pid = $pm->start($server) and next;

    # childe work in here
    my $error_code = 0 ;  # reset this everytime
    my $retval = $bm{$server}->connect ; # see if we can connect
    my $retcount = 0 ;
    while (!$retval )
    {
        log_report( "FAILD TO CONNECT TO $server ".$bm{$server}->error ) ;
        sleep $resend ; $retcount++;
        log_report("Attempting to connect to $server. Attempt: $retcount");
        $retval = $bm{$server}->connect  ;
    }
    log_report( "Child Connected to $server.. Sending" ) ;

    # SEND ALREADY! 
    $retval = $bm{$server}->bulkmail ;
    if ( !$retval )
    {
        log_report( $bm{$server}->error );
        $error_code = 1;
    }
    $pm->finish($pid,$retval,$server,$error_code,0) ;
}

# wait for children to finish . 
$pm->wait_all_children;
log_report("-={ DONE }=-");

# simple print to the logfile
# also prints to STDout
sub log_report
{
   my $text = shift;
   my $test_text = "";
   my $date = scalar localtime();
   $test_text =  "TEST MODE: " if $testsend ;
   print LOG_FILE "$date $test_text [$listname]  $text\n";
   print "$date $test_text [$listname]  $text\n";
}

# simple sub to use if debug.. 
sub DEBUG
{
   my $bug = shift;
   print STDOUT "$bug\n" if $debug ;
}


