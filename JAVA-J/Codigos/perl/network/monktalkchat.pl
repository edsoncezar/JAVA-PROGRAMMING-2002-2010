#!/usr/bin/perl
# monktalk.pl - Talking Perl Monks
# By Joe Stewart
# Mainly just a hack to the sample driver program for PerlMonksChat.pm
# by Diego Zamboni, May 2000.
#
# Use:
#  monktalk.pl -l Username
#      Asks for your password. Stores cookies in ~/.pm-cookie.
#      This is only necessary the first time you use it. After that it uses
#      the cookie automatically.
# Type messages to send
# Type /checkoff <number> to remove personal messages (this is the equivalent
#  of checking on them in the web page).
#
use strict;
use PerlMonksChat;
use IO::Select;
use Text::Wrap qw(wrap);
use Speech::Festival;

$| = 1;
$SIG{'INT'} = \&endit;

my $festival = new Speech::Festival;
conn $festival || warn "Could not connect to festival: $!";

my $user;
my $passwd;

my $usecolor=1;
my $color_username="";
my $color_personalmsg="";
my $color_sysmsg="";
my $color_normal="";

if ($ARGV[0] && $ARGV[0] eq "-l") {
  shift @ARGV;
  $user=shift @ARGV or die "User expected as argument to -l\n";
  print "Password? ";
  $passwd=<STDIN>;
  chomp $passwd;
}

my $period=shift @ARGV || 10;

if ($usecolor) {
  eval "use Term::ANSIColor";
  unless ($@) {
    $color_username=color('BOLD GREEN');
    $color_personalmsg=color('YELLOW BOLD');
    $color_sysmsg=color('RED');
    $color_normal=color('reset');
  }
}

my $p=PerlMonksChat->new();
$p->add_cookies;
if ($user) {
  $p->login($user, $passwd);
}

my $s=IO::Select->new;

$s->add(\*STDIN);
  while (1) {
    my @lines = map { s/^(<[^>]+>)/$color_username$1$color_normal/;
                s/^(\(\d+\)\s*\*.*)$/$color_personalmsg$1$color_normal/;
                wrap("", "\t", "$_\n") } $p->getnewlines(1);
    for (@lines) {
        print;
        &speak_to_me($_);
    }
    my @ready=$s->can_read($period);
    if (@ready) {
      foreach (@ready) {
        my $line=<$_>;
        chomp $line;
        if ($line =~ /^\/?(checkoff|co)\s+/ && (my @ids=($line=~/(\d+)/g))) {
          $p->checkoff(map { 'deletemsg_'.$_ } @ids);
          print $color_sysmsg, "* Done *", $color_normal,"\n";
        }
        elsif ($line =~ /^\s*\/quit\s*$/) {
          exit;
        }
        else {          $p->send($line);
        }
      }
    }
  }

sub speak_to_me($) {
    my $text = shift;
    $text =~ s/^.*?\s+//;
    request $festival "(SayText \"$text\")";
}

sub endit {
    print "Disconnecting from Festival: ";
    disconnect $festival;
    exit;
}
