#!/usr/bin/perl -w 

##  
## pmchat by Nicholas J. Leon ala mr.nick (nicholas@binary9.net) 
##                                    http://www.mrnick.binary9.net 
##
## A text mode client for the Chatter Box of Perl Monks 
## This is not an attempt to be complete, but small and useful 
## Use it or not. No guaranteee, no warranty, blah blah 

## now features a debugging mode! Guaranteed to piss off less
## CB users than before!


my $ID='$Id: pmchat,v 1.65 2001/08/07 01:02:15 nicholas Exp $'; #'

use strict;
use XML::Simple;
use LWP::Simple; 
use LWP::UserAgent; 
use HTTP::Cookies; 
use HTTP::Request::Common; 
use Data::Dumper; 
use Text::Wrap qw($columns wrap); 
use Term::ReadLine; 
use Term::ReadKey qw(GetTerminalSize ReadMode ReadLine); 
use HTML::Parser;
use File::Copy;
use Storable;
use MD5;
use URI::Escape;
use HTML::Parser;
 
$|++; 

my $pm='http://www.perlmonks.org/index.pl'; 
my $cookie="$ENV{HOME}/.pmcookie"; 
my $cffile="$ENV{HOME}/.pmconfig"; 
my %config=( 
            timestamp => 0, 
            colorize => $^O=~/win/i ? 0 : 1, 
	    browser => '/usr/bin/lynx %s',
	    newnodes => 25,
	    updateonlaunch => 0,
	    timeout => 45,
	    away => 0,
	    debug => 0,
	    logfile => 'none',
           ); 
 
my %seenmsg; 
my %seenprv; 
my %xp;
my $ua;
 
## some color stuff (if you want) 
my %colormap= 
  (  
   node => [ "\e[33m", "\e[0m" ], 
   user => [ "\e[1m", "\e[0m" ], 
   code => [ "\e[32m", "\e[0m" ], 
   me => [ "\e[36m", "\e[0m" ], 
   private => [ "\e[35m","\e[0m" ],
   important => [ "\e[1;34m","\e[0m" ],
  ); 

## <readmore>
##############################################################################
##############################################################################

sub writeconfig { 
  store \%config,$cffile;
} 
sub readconfig { 
  %config=(%config,%{ retrieve $cffile }) if -f $cffile;

  ## away is ALWAYS unset
  $config{away}=0;
} 

sub autoupdate {
  my $quiet=shift;
  my $r=$ua->request(GET "http://www.mrnick.binary9.net/pmchat/version");

  if ($r->{_rc} != 200) {
    print "Sorry, update request failed: $r->{_rc}/$r->{_msg}\n";
    return;
  }

  my($ver)=$r->content=~/^([\d\.]+)$/;
  my($this)=$ID=~/,v\s+([\d\.]+)/;
  

  print "This version is $this, the current version is $ver.\n" unless $quiet;

  if ($this >= $ver) {
    print "There is no need to update.\n" unless $quiet;
    return;
  }

  print "A new version is available, $ver.\n";

  $r=$ua->request(GET "http://www.mrnick.binary9.net/pmchat/pmchat");

  if ($r->{_rc} != 200) {
    print "Sorry, update request failed: $r->{_rc}/$r->{_msg}\n";
    return;
  }

  my $tmp=$ENV{TMP} || $ENV{TEMP} || "/tmp";
  my $fn="$tmp/pmchat-$ver";

  unless (open (OUT,">$fn")) {
    print "Unable to save newest version to $fn\n";
    return;
  }

  print OUT $r->content;
  close OUT;

  ## okay, a couple checks here: we can autoupdate IF the following
  ## are true
  if ($^O=~/win32/i) {
    print "Sorry, autoupdate not available for Windows installations.\n";
    print "The newest version has been saved in $fn.\n";
    return;
  }

  ## moving the old version someplace else 
  if (!move($0,"$0.bak")) {
    print "Couldn't move $0 to $0.bak, aborting.\n";
    print "The newest version has been saved in $fn.\n";
    return;
  }
  ## moving the new version to the old's location
  if (!move($fn,$0)) {
    print "Couldn't move $fn to $0, aborting $!.\n";
    move("$0.bak",$0);
    print "The newest version has been saved in $fn.\n";
    return;
  }
  ## okay! Reload!
  chmod 0755,$0;
  reload();
}


##############################################################################
##############################################################################
sub xml {
  my $r=shift;
  my $xml=$r->content;
  $xml=~ tr/\x80-\xff/\?/;
  $xml;
}

sub colorize {
  my $txt=shift;
  my $type=shift;

  return $txt unless $config{colorize};

  "$colormap{$type}[0]$txt$colormap{$type}[1]";
}

my %usermap;
my @colors=(31..36,41..46);

sub user {
  ## see if this user has b
  colorize(shift,"user");
}
sub imp {
  colorize(shift,"important");
}  
sub content {
  my $txt=shift;

  return $txt unless $config{colorize};

  unless ($txt=~s/\<code\>(.*)\<\/code\>/$colormap{code}[0]$1$colormap{code}[1]/mig) {
    $txt=~s/\[([^\]]+)\]/$colormap{node}[0]$1$colormap{node}[1]/g;
  }

  $txt;
}
##############################################################################
##############################################################################

sub cookie {
  $ua->cookie_jar(HTTP::Cookies->new());
  $ua->cookie_jar->load($cookie);
}

sub login {
  my $user; 
  my $pass; 
  
  ## fixed <> to <STDIN> via merlyn
  print "Enter your username: "; chomp($user=<STDIN>); 
  ReadMode 2;
  print "Enter your password: "; chomp($pass=<STDIN>); 
  ReadMode 0;
  print "\n";
  
  $ua->cookie_jar(HTTP::Cookies->new(file => $cookie, 
				     ignore_discard => 1, 
				     autosave => 1, 
				    ) 
		 ); 
  
  my $r=$ua->request( POST ($pm,[  
				 op=> 'login',  
				 user=> $user,  
				 passwd => $pass, 
				 expires => '+1y',  
				 node_id => '16046'  
				])); 

  if ($r->{_rc} != 200) {
    print "Sorry, login request failed: $r->{_rc}/$r->{_msg}\n";
    return;
  }

}

sub xp { 
    my $r=$ua->request(GET("$pm?node_id=16046")); 

    if ($r->{_rc} != 200) {
      print "Sorry, XP request failed: $r->{_rc}/$r->{_msg}\n";
      return;
    }

    my $xml=XMLin(xml($r));

    $config{xp}=$xml->{XP}->{xp} unless defined $config{xp};
    $config{level}=$xml->{XP}->{level} unless defined $config{level};


    print "\nYou are logged in as ".user($xml->{INFO}->{foruser}).".\n"; 
    print "You are level $xml->{XP}->{level} ($xml->{XP}->{xp} XP).\n"; 
    if ($xml->{XP}->{level} > $config{level}) {
      print imp "You have gained a level!\n";
    }
    print "You have $xml->{XP}->{xp2nextlevel} XP left until the next level.\n"; 

    if ($xml->{XP}->{xp} > $config{xp}) {
      print imp "You have gained ".($xml->{XP}->{xp} - $config{xp})." experience!\n";
    }
    elsif ($xml->{XP}->{xp} < $config{xp}) { 
      print imp "You have lost ".($xml->{XP}->{xp} - $config{xp})." experience!\n"; 
    }                               

    ($config{xp},$config{level})=($xml->{XP}->{xp},$xml->{XP}->{level});

    print "\n"; 
  } 
 
sub who { 
  my $r=$ua->request(GET("$pm?node_id=15851"));

  if ($r->{_rc} != 200) {
    print "Sorry, who request failed: $r->{_rc}/$r->{_msg}\n";
    return;
  }
  
  my $ref=XMLin(xml($r),forcearray=>1); 
 
  print "\nUsers current online (";
  print $#{$ref->{user}} + 1;
  print "):\n";

  print wrap "\t","\t",map { $_->{username}." " } @{$ref->{user}};

  print "\n";
} 
 
sub newnodes { 
  my $r=$ua->request(GET("$pm?node_id=30175")); 
  if ($r->{_rc} != 200) {
    print "Sorry, newnodes request failed: $r->{_rc}/$r->{_msg}\n";
    return;
  }
  my $ref=XMLin(xml($r),forcearray=>1); 
  my $cnt=1; 
  my %users=map { ($_->{node_id},$_->{content}) } @{$ref->{AUTHOR}}; 
  
  print "\nNew Nodes:\n";
  
  if ($ref->{NODE}) {
    for my $x (sort { $b->{createtime} <=> $a->{createtime} } @{$ref->{NODE}}) { 
      print wrap "\t","\t\t", 
      sprintf("%d. [%d] %s by %s (%s)\n",$cnt,
	      $x->{node_id},$x->{content},
	      user(defined $users{$x->{author_user}} ? $users{$x->{author_user}}:"Anonymous Monk"),
	      $x->{nodetype});
      last if $cnt++==$config{newnodes}; 
    } 
  }
  print "\n";
  
} 

sub nodeinfo {
  my $r=$ua->request(GET "$pm?node_id=32704");
  if ($r->{_rc} != 200) {
    print "Sorry, node info failed: $r->{_rc}/$r->{_msg}\n";
    return;
  }
  my $ref=XMLin(xml($r),forcearray=>1); 

  $config{nodes}=$ref->{NODE} unless defined $config{nodes};

  if (defined $ref->{NODE}) { 
    for my $id (keys %{$ref->{NODE}}) { 
      $config{nodes}->{$id}->{reputation}=0 if ! defined $config{nodes}->{$id}->{reputation};

      my $ch=$ref->{NODE}->{$id}->{reputation}-$config{nodes}->{$id}->{reputation};

      if ($ch) {
	print wrap "\t","\t\t","$ref->{NODE}->{$id}->{content} ($id) has ";
	print imp (($ch>0?"gained":"lost")." $ch ");
	print "reputation!\n";

	$config{nodes}->{$id}->{reputation}=$ref->{NODE}->{$id}->{reputation};
      }
    }
    print "\n";
  }
}

sub getnode {
  my $id=shift;

  system(sprintf($config{browser},"$pm?node_id=$id"));
}

sub quit {
  writeconfig;
  exit;
}

sub set {
  my $args=shift;

  if ($args) {
    if ($args=~/([^\s]+)\s+(.+)$/) {
      $config{$1}=$2;
      print "\t$1 is now $2\n";
    }
    elsif ($args=~/([^\s+]+)$/) {
      print "\t$1 is $config{$1}\n";
    }
  }
  else {
    for my $k (sort keys %config) {
      next if ref $config{$k};
      printf "\t%-15s %s\n",$k,$config{$k};
    }
  }
}

sub reload {
  print "Reloading $0...\n";
  writeconfig;
  exec $0;
}

sub away {
  my $args=shift;

  print wrap '','',"You are now away. Checking your XP or sending a message will negate this.\n";

  $config{away}=1;
}

sub logfile {
  my $buff=shift;

  if ($config{logfile} && $config{logfile} ne '0' && $config{logfile} ne 'none') {
    if (!open(OUT,">>$config{logfile}")) {
      warn "Couldn't open log file '$config{logfile}': $!\n";
      return;
    }
    print OUT $buff,"\n";
    close OUT;
  }
}

##############################################################################
##############################################################################

sub showmessage {
  my $msg=shift;
  my $type=shift || '';
  
  for my $k (keys %$msg) {
    $msg->{$k}=~s/^\s+|\s+$//g
  }

  print "\r";

  my $content=$msg->{content};
  
  if ($type eq 'private') {
    print wrap('',"\t",
	       ($config{timestamp}?sprintf "%02d:%02d:%02d/",(unpack("A8A2A2A2",$msg->{time}))[1..3]:'').
	       colorize("$msg->{author} says $msg->{content}","private").
	       "\n");
    logfile (($config{timestamp}?sprintf "%02d:%02d:%02d/",(unpack("A8A2A2A2",$msg->{time}))[1..3]:'').
      "$msg->{author} says $msg->{content}");
  }
  else {
    if ($msg->{content}=~s/^\/me\s+//) {
      print wrap('',"\t",
		 ($config{timestamp}?sprintf "%02d:%02d:%02d/",(unpack("A8A2A2A2",$msg->{time}))[1..3]:'').
		 colorize("$msg->{author} $msg->{content}","me"),
		 "\n");
      logfile (($config{timestamp}?sprintf "%02d:%02d:%02d/",(unpack("A8A2A2A2",$msg->{time}))[1..3]:'').
	"$msg->{author} $msg->{content}");
    }
    else {
    
      print wrap('',"\t",
		 ($config{timestamp}?sprintf "%02d:%02d:%02d/",(unpack("A8A2A2A2",$msg->{time}))[1..3]:'').
		 colorize($msg->{author},"user").
		 ": ".
		 content($msg->{content}).
		 "\n");
      logfile (($config{timestamp}?sprintf "%02d:%02d:%02d/",(unpack("A8A2A2A2",$msg->{time}))[1..3]:'').
	"$msg->{author}: $msg->{content}");
    }
  }
}
	     

sub getmessages { 
  my $r;
  ## alright, something wacky here. If $config{away} is true, then
  ## don't use the users cookie to grab the list
  if ($config{away}) {
    my $nua=LWP::UserAgent->new;
    $nua->agent("pmchat-mrnick-anon"); 
    $r=$nua->request(GET("$pm?node_id=15834"));  
  }
  else {
    $r=$ua->request(GET("$pm?node_id=15834")); 
  }
  if ($r->{_rc} != 200) {
    print "Sorry, message request failed: $r->{_rc}/$r->{_msg}\n";
    return;
  }

  ## we'll cheese-out here ... for XML::Simple
  my $xml=xml($r);

  my $ref=XMLin(uri_escape($xml,"\x80-\xff"), forcearray=>1 ); 
  
  if (defined $ref->{message}) { 
    for my $mess (@{$ref->{message}}) { 
      ## ignore this message if we've already printed it out 
      next if $seenmsg{"$mess->{user_id}:$mess->{time}"}++; 

      showmessage $mess; 
    } 
  } 
  else { 
    ## if there is nothing in the list, reset ours 
    undef %seenmsg; 
  } 
} 

sub getprivatemessages { 
  my $r=$ua->request(GET("$pm?node_id=15848")); 

  if ($r->{_rc} != 200) {
    print "Sorry, private message request failed: $r->{_rc}/$r->{_msg}\n";
    return;
  }
  my $ref=XMLin(xml($r),forcearray=>1); 
  
  if (defined $ref->{message}) { 
    for my $mess (@{$ref->{message}}) { 
      ## ignore this message if we've already printed it out 
      next if $seenprv{"$mess->{user_id}:$mess->{time}"}++; 
 
      showmessage $mess,"private"; 
    } 
  } 
  else { 
    undef %seenprv; 
  } 
} 

sub postmessage { 
  my $junk=shift;
  my $msg=shift; 

  if ($config{debug}) {
    print ">> $msg\n";
    return;
  }

  my $req=POST ($pm,[ 
                     op=>'message', 
                     message=>$msg, 
                     node_id=>'16046', 
                    ]); 
  
  my $r=$ua->request($req); 

  if ($r->{_rc} != 200) {
    print "Sorry, post message failed: $r->{_rc}/$r->{_msg}\n";
    return;
  }

} 

sub help {
  print <<EOT
The following commands are available:
    /away         :: Sets pmchat to anonymously pull Chatterbox messages. The
                     effect is that you will not appear in Other Users unless
                     you send a message or check your XP.
    /help         :: Shows this message
    /getnode ID   :: Retrieves the passed node and launches your user
                     configurable browser ("browser") to view that node.
    /newnodes     :: Displays a list of the newest nodes (of all types)
                     posted. The number of nodes displayed is limited by
                     the "newnodes" user configurable variable.
    /nodeinfo     :: Displays changes in reputation for your nodes.
    /reload       :: UNIX ONLY. Restarts pmchat.
    /set          :: Displays a list of all the user configurable
                     variables and their values.
    /set X Y      :: Sets the user configurable variable X to
                     value Y.
    /update       :: Checks for a new version of pmchat, and if it
                     exists, download it.
                     This WILL overwrite your current version.
    /quit         :: Exits pmchat
    /who          :: Shows a list of all users currently online
    /xp           :: Shows your current experience and level.
EOT
  ;
}

##############################################################################
##############################################################################
my $old;
my $term=new Term::ReadLine 'pmchat';

sub getlineUnix {
  my $message;

  eval {
    local $SIG{ALRM}=sub { 
      $old=$readline::line; 
      die 
    };
    
    ## I don't use the version of readline from ReadKey (that includes a timeout)
    ## because this version stores the interrupted (what was already typed when the
    ## alarm() went off) text in a variable. I need that so I can restuff it 
    ## back in.

    alarm($config{timeout}) unless $^O=~/win32/i;
    $message=$term->readline("(Talk) ",$old);
    $old=$readline::line='';
    alarm(0) unless $^O=~/win32/i;
  };    

  $message;
}

sub getlineWin32 {
  ## sorry, non-blocking reads are not supported on Windows, it appears
  print "(Talk) ";
  chomp($_=<STDIN>);
  $_;
}

## initialize our user agent
$ua=LWP::UserAgent->new || die "Couldn't init UserAgent: $!\n";
$ua->agent("pmchat-mrnick"); 

## trap ^C's
## for clean exit
$SIG{INT}=sub { 
  writeconfig;
  exit 
};

## load up our config defaults
readconfig;

## for text wrapping
$columns=(Term::ReadKey::GetTerminalSize)[0] || $ENV{COLS} || $ENV{COLUMNS} || 80;

if (-e $cookie) {
  cookie;
}
else {
  login;
}

my($this)=$ID=~/,v\s+([\d\.]+)/;

print "This is pmchat version $this.\n";

if ($config{updateonlaunch}) {
  autoupdate(1);
}
else {
  print "Consider checking for a new version with /update.\n";
}

xp();
nodeinfo();
print "Type /help for help.\n";
who();
newnodes();
getprivatemessages;
getmessages();

## testing, please ignore
my %cmdmap=(
	    '/me' => \&postmessage,
	    '/msg' => \&postmessage,

	    '/away', => \&away,
	    '/who' => \&who,
	    '/quit' => \&quit,
	    '/set' => \&set,
	    '/new\s*nodes' => \&newnodes,
	    '/xp' => \&xp,
	    '/getnode' => \&getnode,
	    '/help' => \&help,
	    '/reload' => \&reload,
	    '/update' => \&autoupdate,
	    '/nodeinfo' => \&nodeinfo,
	   );


while (1) {
  my $message;
  
  getprivatemessages unless $config{away};
  getmessages;
  
  if ($^O=~/win32/i) {
    $message=getlineWin32;
  }
  else {
    $message=getlineUnix;
  }
  
  if (defined $message) {
    if ($message=~/^\//) {
      foreach (keys %cmdmap) {
	if ($message=~/^$_\s*(.*)/) {
	  &{$cmdmap{$_}}($1,$message);
	  last;
	}
      }
    }
    else {
      postmessage undef,$message;
    }
  }
}
