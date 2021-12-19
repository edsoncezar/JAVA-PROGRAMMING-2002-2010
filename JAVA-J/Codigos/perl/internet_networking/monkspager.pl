eval 'exec perl -x $0 ${1+"$@"}' # -*-perl-*-
  if 0;
#!perl -w
#
# "Pager" for PerlMonks
# Gets personal messages and displays or mails them.
#
# Diego Zamboni, September 10, 2000
#
# $Id: pmpager.pl,v 1.1.2.2 2000/09/10 17:44:20 zamboni Exp $
#

use strict;
use PerlMonks::Chat;
use Text::Wrap qw(wrap);
use Getopt::Long;
use Mail::Mailer;
use SDBM_File;
use Fcntl;

sub usage {
  return <<EOM;
Usage: $0 [options]
    --login user    -l user    User to login as. Only needed once, it
                               gets stored in a cookie
    --password pwd  -p pwd     Password to use (otherwise ask)
    --mailto addr   -m addr    Mail results (print to stdout by default)
    --checkoff      -c         Check off new messages automatically
    --ignorecache   -i         Print all messages, even if they have
                               been seen before. By default it only shows
			       new messages
    --help          -h         This message
EOM
}

my $p=PerlMonks::Chat->new();
$p->add_cookies();

# Config options
my $user;
my $mailto;
my $passwd;
my $checkoff;
my $nocache;
my $help;

# Get command-line args
GetOptions('login|l=s' => \$user,
	   'mailto|m=s' => \$mailto,
	   'password|p=s' => \$passwd,
	   'checkoff|c'   => \$checkoff,
	   'ignorecache|i'  => \$nocache,
	   'help|h'        => \$help,
	  )
  or die usage();

warn(usage()), exit if $help;

if ($user) {
  unless ($passwd) {
    $|=1;
    print "Password? ";
    $passwd=<STDIN>;
  }
  # Login
  $p->login($user, $passwd)
    or die "Login failure: $@\n";
}

# Open the cache.
my %cache;
tie(%cache, 'SDBM_File', "$ENV{HOME}/.pmpager", O_RDWR|O_CREAT,0640);

my $body="";
my @ids=();

# Get personal messages
my %msgs=$p->personal_messages;
foreach my $k (keys %msgs) {
  next if (!$nocache && exists($cache{$k}));
  $cache{$k}=1 unless $nocache;
  push @ids, $k;
  $body.=wrap("", "\t", $msgs{$k})."\n";
}
untie %cache;

# Print or send the messages
if ($mailto) {
  if ($body) {
    my $mailer=Mail::Mailer->new();
    $mailer->open({From    => $mailto,
		   To      => $mailto,
		   Subject => "Your new PerlMonks personal messages"
		  })
      or do {
	# This is so that if you execute this through crontab and
	# it fails, maybe you'll get your messages anyway.
	warn "Can't open Mail::Mailer object.\n";
	warn "Your new personal messages are:\n$body";
	die "Terminating.\n";
      };
    print $mailer $body;
    if ($checkoff) {
      print $mailer "\nThese messages have been checked off.\n";
    }
    $mailer->close();
  }
}
else {
  if ($body) {
    print "Your new PerlMonks personal messages:\n$body";
    if ($checkoff) {
      print "\nThese messages have been checked off.\n";
    }
  }
  else {
    print "No new personal messages.\n";
  }
}

# Check off the messages if necessary. We do this last so that
# if anything fails, the messages are not checked off.
$p->checkoff(@ids) if @ids && $checkoff;
