# @(#) setpwd.pl - <DESCRIPTION>
#
# Author:
#      Dave Roberts
#
# Synopsis:
#      setpwd.pl
#
# Version
#      $Source: D:/src/perl/scripts/RCS\\setpwd.pl $
#      $Revision: 1.13 $
#      $State: Exp $
#
# Description:
#      <FULL DESCRIPTION>
#
# Options:
#
# Exit Status:
#
# Caveats/Warnings:
#
# See Also:
#
# Files:
#

# Start comments/code here - will not be processed into manual pages
#
#    Copyright © Dave Roberts  2000,2001
#
# Revision history:
#      $Log: setpwd.pl $
#      Revision 1.13  2001/06/05 15:03:57  Dave.Roberts
#      rehacked unix passwd change to properly allow for different OS's
#      checked so far with OSF1 and Solaris
#      also resolved the busy hourglass when changing unix passwords (was disappearing)
#
#      Revision 1.12  2001/06/01 11:41:31  Dave.Roberts
#      added feature to test network connections and authenticate current password
#      combined all password change subroutines
#      made log window less busy through use of colour
#      still needs domain logon to be tested and general testing
#      general busy signal is 'lost' when testing unix accounts - unsure why
#
#      Revision 1.11  2001/05/31 12:23:37  Dave.Roberts
#      selective password resetting now ready for testing
#      some improvements to giving windows appropriate focus
#
#      Revision 1.10  2001/05/28 18:16:01  Dave.Roberts
#      release for prototype testing.  Edit and File functions now coded, print
#      and logging functions still to do
#
#      Revision 1.9  2001/05/26 07:32:18  Dave.Roberts
#      editing and file save/saveas/import/new/open functions completed
#      and ready for testing
#
#      Revision 1.8  2001/05/25 10:12:37  Dave.Roberts
#      chnages to edit window - archived during development
#
#      Revision 1.7  2001/05/24 16:13:25  Dave.Roberts
#      converted to use menu system, but at this stage very much "in development"
#
#      Revision 1.6  2001/05/24 10:42:47  Dave.Roberts
#      added version to manual page, resolved issues around version control - so functionally
#      this is near identical to v 1.5
#
#      Revision 1.5  2001/05/22 15:41:06  Dave.Roberts
#      added help screen, cleaned up GUI presentation
#
#      Revision 1.4  2001/05/21 13:03:09  Dave.Roberts
#      added nt and unix password changes, ec to be proved
#
#      Revision 1.3  2001/04/26 14:08:41  Dave.Roberts
#      added cebj account
#
#      Revision 1.2  2001/04/12 09:43:21  Dave.Roberts
#      added text widget for log info - errors displayed in red.  Works OK - except only
#      updates when chgpasswd change routine completes
#
#      Revision 1.1  2001/04/02 12:51:23  Dave.Roberts
#      Initial revision
#
#******************************************************************************
require 5.002;
use strict;
use Tk;
require Tk::ROText;
require Tk::Tiler;
#----------------------------------------------------------------------------
# These required for changing NT passwords
use Win32::AdminMisc;
use Win32::Lanman;
#----------------------------------------------------------------------------
# These required for changing Unix passwords
use Net::Telnet;
use Net::Ping;
#----------------------------------------------------------------------------
use constant TRUE     => 1;
use constant FALSE    => 0;
use constant NORM     => 0;
use constant ERR      => 1;
use constant BOLD     => 2;
#----------------------------------------------------------------------------
#  Initial variables
#  The %accounts hash has the following syntax...
#  NT Domain: domainname     =>   Account    (An NT Domain Account)
#  NT Workstation: server    =>   Account    (An NT Server defined account)
#  Unix: server              =>   Account    (A Unix server defined account)
my (%accounts);
my ($netTimeOut) = 3;   # Default Network Timeout
my ($oldpwd ,$newpwd1,$newpwd2,$err,$t,$os,$top2);
my ($selection) = @ARGV[0]; #  the named file for file save/open operations
fileOpen($selection) if (defined($selection) && (-f $selection));
my ($lastchange); #  the time of the last change (required to prompt any file/save required on exit)
my(%acswitch);    #  hash that defines passwords to be changed
#----------------------------------------------------------------------------
my $mw = MainWindow->new;
$mw->title('Password Manager');
#----------------------------------------------------------------------------
my $toplevel = $mw->toplevel;
my $menubar = $toplevel->Menu(-type => 'menubar');
$toplevel->configure(-menu => $menubar);
my $f = $menubar->cascade(-label => '~File', -tearoff => 0);
$f->command(-label => 'Open ...',    -command => [\&fOp, 'open']);
$f->command(-label => 'New',         -command => [\&fOp, 'new']);
$f->command(-label => 'Import',      -command => [\&fOp, 'import']);
$f->command(-label => 'Save',        -command => [\&fOp, 'save']);
$f->command(-label => 'Save As ...', -command => [\&fOp, 'saveas']);
$f->separator;
#$f->command(-label => 'Setup ...',   -command => [\&menus_error, 'Setup']);
#$f->command(-label => 'Print ...',   -command => [\&menus_error, 'Print']);
#$f->separator;
$f->command(-label => 'Quit',        -command => [\&on_quit]);     #[$mw => 'bell']);
my $e = $menubar->cascade(-label => '~Edit', -tearoff => 0);
$e->command(-label => 'Accounts',    -command => [\&on_edit]);
$e->command(-label => 'Selection',   -command => [\&on_select]);
my $a = $menubar->cascade(-label => '~Action', -tearoff => 0);
$a->separator;
my $ap = $a->cascade(-label => 'Change Passwords');
$ap->command(-label => 'All',     -command => [\&chgpasswd,0,1,""]);
$ap->command(-label => 'NT',      -command => [\&chgpasswd,0,1,"NT"]);
$ap->command(-label => 'Unix',    -command => [\&chgpasswd,0,1,"Unix"]);
$ap->command(-label => 'Selected',-command => [\&chgpasswd,1,1,""]);
my $ac = $a->cascade(-label => 'Check Connections');
$ac->command(-label => 'All',     -command => [\&chgpasswd,0,0,""]);
$ac->command(-label => 'NT',      -command => [\&chgpasswd,0,0,"NT"]);
$ac->command(-label => 'Unix',    -command => [\&chgpasswd,0,0,"Unix"]);
$ac->command(-label => 'Selected',-command => [\&chgpasswd,1,0,""]);
my $l = $menubar->cascade(-label => '~Logging', -tearoff => 0);
#$l->command(-label => 'On',     -command => [\&menus_error, 'Open']);
#$l->command(-label => 'Off',    -command => [\&menus_error, 'New']);
#$l->command(-label => 'Set LogFile As ...', -command => [\&menus_error, 'Save As']);
#$l->separator;
#$l->command(-label => 'Setup ...',   -command => [\&menus_error, 'Setup']);
#$l->command(-label => 'Print ...',   -command => [\&menus_error, 'Print']);
my $h = $menubar->cascade(-label => '~Help', -tearoff => 0);
$h->command(-label => 'Reference',    -command => [\&on_help]);

#----------------------------------------------------------------------------

$mw->Label(-text    => 'Old Password')->grid(-row=>1, -col=>1);
$mw->Label(-text    => 'New Password')->grid(-row=>2, -col=>1);
$mw->Label(-text    => 'Confirm New Password')->grid(-row=>3, -col=>1);

$oldpwd  = $mw->Entry(qw/-show * /)->grid(-row=>1, -col=>2);
$newpwd1 = $mw->Entry(qw/-show * /)->grid(-row=>2, -col=>2);
$newpwd2 = $mw->Entry(qw/-show * /)->grid(-row=>3, -col=>2);
$newpwd2->bind("<FocusOut>",\&chkpasswd);

my $logframe = $mw->Frame(-relief=>'sunken',-bd => 1)->grid(-row=>6,-columnspan=>3);
$logframe->Label(-text    => 'Password Change Log:')->pack(-anchor => "nw");
my $t = $logframe->Scrolled(qw/ROText -relief sunken -borderwidth 2 -setgrid true -height 15 -width 50 -scrollbars se -bg SteelBlue1 -wrap none/)
->pack();
my $size = -14;
$mw->fontCreate('C_normal',-family => 'courier', -size => $size);
$mw->fontCreate('C_small',-family => 'courier', -size => int(12*$size/14));
$mw->fontCreate('C_bold',-family => 'courier', -weight => 'bold', -size => $size);
$mw->protocol('WM_DELETE_WINDOW',sub{&on_quit;}); #handle window delete from window manager
#----------------------------------------------------------------------------
sub chkpasswd {
  if ( $newpwd1->get eq $newpwd2->get ) {
    return 0;
    }else{
    $t->insert('end', "new passwords don't match \n",'err1');
    my $err = MainWindow->new;
    &placechild($mw,$err,25,'Error');
    $err->configure (-width => 300,-height => 50);
    $err->Label(-text => 'Password Mismatch',-height => 3,-width => 30)->pack;
    $err->Button(-text => 'OK', -command => sub{$err->destroy})->pack;
    return 1;
  }
}
#----------------------------------------------------------------------------
MainLoop;
exit();
#----------------------------------------------------------------------------
sub on_quit {
  if ( (defined $selection) && ( $lastchange > ((stat($selection))[9]) ) ) {
    my $err = MainWindow->new;
    &placechild($mw,$err,25,'Question');
    $err->configure (-width => 300,-height => 50);
    $err->Label(-text => 'Do you want to save your last changes ?',-height => 3,-width => 40)->pack(qw/side top expand yes -pady 2/);
    $err->Button(-text => 'Yes', -command =>
      sub {
        &fileSave($selection);
        exit();
    } )->pack(qw/side left expand yes -pady 2/);
    $err->Button(-text => 'No', -command => sub{exit();})->pack(qw/side right expand yes -pady 2/);
    }else{
    exit();
  }
}
#----------------------------------------------------------------------------
sub on_help {
  my $top1 = $mw->Toplevel;
  &placechild($mw,$top1,25,"Help");
  my $helptxt=$top1->Scrolled("ROText", -scrollbars => "e", -height => 30, -width =>80,-background => "gray")->pack;my($txt);
  open (MAN,"pod2text $0|");
  while (<MAN>) {$helptxt->insert('end', $_);}
  close MAN;
  $top1->Button(-text => 'close', -command => [$top1 => 'destroy'] )->pack(-side => 'right', -pady => 1);
}
#----------------------------------------------------------------------------
sub on_edit {
  $top2 = $mw->Toplevel;
  &placechild($mw,$top2,25,"Edit");
  my $t = $top2->ROText( -wrap => "word", -width  => 45, -height => 4, -bg => "grey" )->pack(-anchor => "nw");
  $t->insert('end', 'These are the computers and accounts whose passwords are to be managed.  Double click an entry to edit it.  Use Insert or Delete keys to add or remove entries');
  my $list = $top2->Scrolled(qw/Listbox -width 45 -height 16 -setgrid 1 -scrollbars e/);
  $list->pack(qw/-side left -fill y/);
  
  $list->bind('<Double-1>' => sub  { &editform($list->get('active'),$list->index('active'),'edit'); }, );
  $top2->bind('<Delete>' => sub  {
      $list->delete($list->index('active'));
  $lastchange = time;                                }, );
  $top2->bind('<Insert>' => sub  { &editform("NT Domain: Domain => Account",$list->index('active'),'new'); }, );
  foreach (keys %accounts) {
    $list->insert(0, "$_ => $accounts{$_}");
  }
  $top2->Button(-text => 'close', -command =>
    [ sub {
      undef %accounts;# Delete every entry in %accounts
      my $i;
      for ($i=0; $i<=$list->index('end'); $i++) {
        next if ( $list->get($i) eq "" );
        $list->get($i) =~ /(.*) => (\S+)/;
        $accounts{$1} = $2;
      }
      $top2->destroy;
  }] )->pack(-side => 'left');
  $top2->protocol('WM_DELETE_WINDOW',sub{$top2->destroy;}); #handle window delete from window manager
  sub editform {
    my($entry,$index,$func) = @_;
    $entry =~ /^(.*):\s*(\S*)\s*=>\s*(\S*)$/;
    my($eo,$server,$account);
    
    $eo=$1;
    $server = $2;
    $account = $3;
    my $form = $top2->Toplevel;
    &placechild($top2,$form,25,'Edit / Add / Delete Entry');
    my $f = $form->Frame->pack(-fill => 'both');
    my($ls);
    my @opts = ($eo , grep (!/$eo/,("NT Domain","NT Workstation","Unix")));
    my $lo = $f->Label(-text => 'Account Type', -anchor => 'e', -justify => 'right');
    my $opt = $f->Optionmenu(
      -options => [@opts],
      -variable => \$eo,
      -command  => \&set_label
    );
    Tk::grid( $opt, -row => 0, -column => 1,-sticky => 'ew');
    Tk::grid( $lo, -row => 0, -column => 0, -sticky => 'e');
    
    
    if ( "$eo" eq "NT Domain") {
      $ls = $f->Label(-text => 'Domain', -anchor => 'e', -justify => 'right');
      }else{
      $ls = $f->Label(-text => 'Server', -anchor => 'e', -justify => 'right');
    }
    my $es = $f->Entry(qw/-relief sunken -width 40 /);
    Tk::grid( $ls, -row => 1, -column => 0,-sticky => 'ew');
    Tk::grid( $es, -row => 1, -column => 1,-sticky => 'ew');
    $es->insert('end',$server);
    
    sub set_label {
      if ( "$eo" eq "NT Domain") {
        $ls->configure(-text => 'Domain');
        }else{
        $ls->configure(-text => 'Server');
      }
    }
    
    my $la = $f->Label(-text => 'Account', -anchor => 'e', -justify => 'right');
    my $ea = $f->Entry(qw/-relief sunken -width 40 /);
    Tk::grid( $la, -row => 2, -column => 0, -sticky => 'e');
    Tk::grid( $ea, -row => 2, -column => 1,-sticky => 'ew');
    $ea->insert('end',$account);
    
    my $ok = $f->Button(-text => 'OK',-width => 8, -command => [sub {
        $list->insert($index,(sprintf "%s: %s => %s",$eo,$es->get,$ea->get));
        $list->delete($index + 1) if ( $func eq 'edit' );
        $lastchange = time;
        $form->destroy;
        return;
    }], );
    Tk::grid($ok,-row=>0, -column=>2, -sticky => 'e');
    
    my $cl = $f->Button(-text => 'Cancel',-width => 8, -command => [$form => 'destroy'] );
    Tk::grid($cl,-row=>1, -column=>2, -sticky => 'e');
    $form->bind('<Return>' =>
      sub  {
        $form->destroy;
        return (sprintf "%s => %s",$es->get,$ea->get);
      },
    );
  } # end editform
}
#----------------------------------------------------------------------------
sub placechild {
  my ($parent,$child,$gap,$title) = @_;
  my($geom) = $parent->geometry();
  $geom =~ /(\d+)x(\d+)([+-])(\d+)([+-])(\d+)/;
  my ($x,$y);
  $x = $4 + $gap;
  $y = $6 + $gap;
  $child->geometry("$3$x$5$y");
  $child->resizable(0,0);
  $child->title($title);
  $child->focusForce();
}
#----------------------------------------------------------------------------
sub on_select {
  my (%acswitch_mem) = %acswitch;
  my $sel = $mw->Toplevel(-height => 100, -width => 400);
  &placechild($mw,$sel,25,"Select Accounts for Password Reset");
  $sel->Label(-text => "NT Accounts")->grid(-row => 0,-column => 0);
  my $ntframe = $sel->Frame(-relief=>'sunken',-bd => 2)->grid(-row => 1, -column => 0);
  my $nttiler = $ntframe->Scrolled('Tiler',-columns => 1,-height => 80, -width => 150)->pack(-side => "top");
  $ntframe->Button(-text => 'Select All', -width => 12, -command => [\&sel,'^NT',1])->pack(-side => "left");
  $ntframe->Button(-text => 'DeSelect All', -width => 12, -command => [\&sel,'^NT',0])->pack(-side => "right");
  
  $sel->Label(-text => "Unix Accounts")->grid(-row => 0,-column => 1);
  my $uxframe = $sel->Frame(-relief=>'sunken',-bd => 1)->grid(-row => 1, -column => 1);
  my $uxtiler = $uxframe->Scrolled('Tiler',-columns => 1,-height => 80, -width => 150)->pack(-side => "top");
  $uxframe->Button(-text => 'Select All', -width => 12, -command => [\&sel,'^Unix',1])->pack(-side => "left");
  $uxframe->Button(-text => 'DeSelect All', -width => 12, -command => [\&sel,'^Unix',0])->pack(-side => "right");
  
  $uxtiler->pack(qw/-expand yes -fill both/);
  foreach my $key (sort keys %accounts) {
    $key=~/(.*):(.*)/;
    my($type)=$1;
    my($machine)=$2;
    $acswitch{$key} = 1 unless (defined $acswitch{$key});
    if ( $type =~ /^Unix/) {
      $uxtiler->Manage( $uxtiler->Checkbutton(
          -text => "$machine => $accounts{$key}",
          -anchor   => 'w',
          -justify  => 'center',
          -variable => \$acswitch{$key},
      -relief   => 'flat'));
      }elsif( $type =~ /^NT/){
      $nttiler->Manage( $nttiler->Checkbutton(
          -text => "$machine => $accounts{$key}",
          -anchor   => 'w',
          -justify  => 'center',
          -variable => \$acswitch{$key},
      -relief   => 'flat'));
    }
  }
  $nttiler->pack(qw/-expand yes -fill both/);
  $uxtiler->pack(qw/-expand yes -fill both/);
  $sel->Button(-text => 'OK', -width => 8, -command =>
    sub {
      $lastchange = time;
      $sel->destroy;
  } )->grid(-row => 2, -column => 0, -pady => 5);
  $sel->Button(-text => 'Cancel', -width => 8, -command =>
    sub {
      %acswitch = %acswitch_mem;
      $sel->destroy;
  } )->grid(-row => 2, -column => 1, -pady => 5);
  sub sel {
    my($pattern,$set)=@_;
    foreach my $key (keys %acswitch) {
      $acswitch{$key} = $set if ($key =~ /^$pattern/);
    }
  }
}
#----------------------------------------------------------------------------
sub fOp {
  my ($op) = @_;
  if ($op eq 'open') {
    $selection = &fileDialog($mw,'open');
    if (defined($selection)){
      fileOpen($selection);
    }
    }elsif ($op eq 'save') {
    $selection = &fileDialog($mw,'save') unless defined $selection;
    if (defined($selection)) {
      print "Selection ($selection) is defined\n";
      &fileSave($selection);
      }else{
      print "Selection ($selection) not defined\n";
    }
    }elsif ($op eq 'saveas') {
    $selection = &fileDialog($mw,'save') unless defined $selection;
    &fileSave($selection) if (defined($selection));
    }elsif ($op eq 'new') {
    $selection = &fileDialog($mw,'save');
    if (defined($selection)){
      undef %accounts;
      &fileSave($selection);
    }
    }elsif ($op eq 'import') {
    $selection = &fileDialog($mw,'open');
    if (defined($selection)){
      {package Settings;
        my $return;
        unless ( $return = do "$selection" ) {
          warn "couldn't parse $selection: $@" if $@;
          warn "couldn't do $selection: $!" unless defined $return;
          warn "couldn't run $selection" unless $return; #  This should be OK
        }
      }
      $lastchange=time;
      foreach (keys %Settings::accounts) {
        if (
          ( grep (/NT Domain/,$_)) ||
          ( grep (/NT Workstation/,$_)) ||
          ( grep (/Unix/,$_))
          ){
          $accounts{$_} = $Settings::accounts{$_};
          }else{
          print "Invalid account definition $_\n";
        }
      }
    }
  }
  
  sub fileSave {
    my($file) = @_;
    if (open(SAVE,">$file")){
      print SAVE "# configuration file saved by $0\n";
      print SAVE "#\n";
      print SAVE "%accounts = (\n";
        foreach (keys %accounts) {
          print SAVE "\"$_\" => \"$accounts{$_}\",\n";
        }
      print SAVE ");\n";
      print SAVE "%acswitch = (\n";
        foreach (keys %acswitch) {
          print SAVE "\"$_\" => \"$acswitch{$_}\",\n";
        }
      print SAVE ");\n";
      print SAVE "\$netTimeOut = $netTimeOut;\n";   # Network Timeout
      print SAVE "1;\n";
      close SAVE;
      utime($lastchange,$lastchange,$file);
      }else{
      warn "failed to open $file for write\n";
    }
  }
  sub fileOpen {
    my($selection) = @_;
    {package Settings;
      my $return;
      undef %Settings::accounts;
      unless ( $return = do "$selection" ) {
        warn "couldn't parse $selection: $@" if $@;
        warn "couldn't do $selection: $!" unless defined $return;
        warn "couldn't run $selection" unless $return; #  This should be OK
      }
    }
    $lastchange=(stat($selection))[9];  # Last mod time
    undef %accounts;
    foreach (keys %Settings::accounts) {
      if (
        ( grep (/NT Domain/,$_)) ||
        ( grep (/NT Workstation/,$_)) ||
        ( grep (/Unix/,$_))
        ){
        $accounts{$_} = $Settings::accounts{$_};
        }else{
        print "Invalid account definition $_\n";
      }
    }
    undef %acswitch;
    foreach (keys %Settings::acswitch) {
      $acswitch{$_} = $Settings::acswitch{$_};
    }
    $netTimeOut = $Settings::netTimeOut if (defined $Settings::netTimeOut);
  }
}
sub fileDialog {
  my $w = shift;
  my $operation = shift;
  my $types;
  my $file;
#   Type names		Extension(s)	Mac File Type(s)
  my @types =
  (
    ["Perl Scripts",         '.pl',		'TEXT'],
    ["Text files",           [qw/.txt .doc/]],
    ["Text files",           '',             'TEXT'],
    ["C Source Files",	     ['.c', '.h']],
    ["All Source Files",     [qw/.tcl .c .h/]],
    ["Image Files",		       '.gif'],
    ["Image Files",		       ['.jpeg', '.jpg']],
    ["Image Files",   	     '',		[qw/GIFF JPEG/]],
    ["All files",		         '*']
  );
  if ($operation eq 'open') {
    $file = $w->getOpenFile(-filetypes => \@types,-initialfile => 'accounts.pl',-defaultextension => '.pl');
    } else {
    $file = $w->getSaveFile(-filetypes => \@types,-initialfile => 'accounts.pl',-defaultextension => '.pl');
  }
  if (defined $file and $file ne '') {
    return $file;
    }else{
    return undef;
  }
}
#############################################################################
sub chgpasswd {
  my($select,$change,$type) = @_;
  $mw->Busy(-recurse => 1);
#return if (( &chkpasswd ) && ( $change == TRUE ));
  return if ( &chkpasswd );
  my $passwd = $oldpwd->get;
  my $newpwd = $newpwd1->get;
  my ($machine,$domain);
  Account: foreach my $key (keys %accounts) {
    next if ( ( $acswitch{$key} != 1 ) && ($select == TRUE));
#&logit("\n--------------------------------------------------\n",NORM);
    if ( $key =~ /^NT Workstation:\s*(\S+)\s*$/ ) {  #  Logic for NT Workstation (a local NT account one a specific machine)
      next if ($type eq "Unix");
      my($host)=$1;
      $host =~ s/\\/\//g;
      $host =~ s/\///g;
      &logit("\n",NORM);
      &logit("NT Workstation/Server: $host   Account: $accounts{$key}",BOLD);
      &logit("\n",NORM);
      if ($change == TRUE) {
        ntpasswd($host,$accounts{$key},$passwd,$newpwd);
        }else{
        ntwauth($host,$accounts{$key},$passwd);
      }
      }elsif ( $key =~ /^NT Domain:\s*(\S+)\s*$/ ) {  # Logic for NT Domain
      next if ($type eq "Unix");
      my($domain)=$1;
      &logit("\n",NORM);
      &logit("NT Domain: $domain    Account: $accounts{$key}",BOLD);
      &logit("\n",NORM);
      if ( my $host = Win32::AdminMisc::GetPDC( $domain ) ) {
        &logit("  - identified $host as PDC of $domain domain\n",NORM);
        if ($change == TRUE) {
          ntpasswd($host, $accounts{$key},$passwd,$newpwd);
          }else{
          ntdauth($host,$accounts{$key},$passwd);
        }
        }else{
        &logit("  - unable to find PDC of $domain domain\n",ERR);
        next Account;
      }
      }elsif ( $key =~ /^Unix:\s*(\S+)\s*$/ ) {
      next if ($type eq "NT");
      my($host) = $1;
      &logit("\n",NORM);
      &logit("Unix: $host   Account: $accounts{$key}",BOLD);
      &logit("\n",NORM);
      if ( NetChk($host) ) {
        my %passwd;
        my $pd = 0;  # Password debug - send simple messages to console to help debug
        my($telnet) = new Net::Telnet (Host => $host, Timeout => 10, errmode => "return" )|| print "failed to create telnet session\n";
        if ($telnet->open("$host")) {
          if( $telnet->login($accounts{$key},$passwd)) {
            logit("  authenticated\n",NORM);
            if ( $change == FALSE ) {
              $telnet->close;
              next Account;
            }
            $telnet->print("uname -a\n");
## Identify the operating system
            my($prematch, $match) = $telnet->waitfor(-match => '/(OSF1|SunOS)/i'   # Solaris | OSF1
            )
            or do {
              if ($telnet->eof) {
                logit("  read eof waiting for uname -a response\n",ERR);
                logit((sprintf "  %s \n",$telnet->lastline),ERR);
                }else{
                logit((sprintf "  %s \n",$telnet->lastline),ERR);
              }
              $telnet->close;
              next Account;
            };
            if ( $match eq "OSF1" ) {
              logit("  OSF1\n",NORM);
              %passwd = (
                oprompt => '/Old password[: ]*$/i',
                nprompt => '/New password[: ]*$/i',
                confirm => '/Retype new password[: ]*$/i',
                success => '.+>',
                failure => '(Password unchanged|Sorry)',
              );
              }elsif( $match eq 'SunOS' ) {
              logit("  SunOS\n",NORM);
              %passwd = (
                oprompt => '/Enter login password[: ]*$/i',
                nprompt => '/New password[: ]*$/i',
                confirm => '/Re-enter new password[: ]*$/i',
                success => 'successfully changed',
                failure => 'XXXXX',
              );
              }else{
              logit("unable to determine the OS - using default passwd change responses\n",NORM);
              $pd = 1;
              %passwd = (
                oprompt => '/Old password[: ]*$/i',
                nprompt => '/New password[: ]*$/i',
                confirm => '/Retype new password[: ]*$/i',
                success => 'successfully changed',
                failure => 'XXXXX',
              );
            }
            $telnet->print("passwd\n");
## Wait for current password prompt.
            $telnet->waitfor(-match => $passwd{oprompt} )
            or do {
              if ($telnet->eof) {
                logit("  read eof waiting for password prompt\n",ERR);
                }else{
                logit((sprintf "%s  %s \n",$passwd{oprompt},$telnet->lastline),ERR);
              }
              $telnet->close;
              next Account;
            };
            print "got old password prompt\n" if $pd;
            $telnet->print("$passwd\n");                      #  - send old (current) password
## Wait for new password prompt.
            $telnet->waitfor(-match => $passwd{nprompt} )
            or do {
              if ($telnet->eof) {
                logit("read eof waiting for new password prompt\n",ERR);
                }else{
                logit((sprintf "%s \n",$telnet->lastline),ERR);
              }
              $telnet->close;
              next Account;
            };
            print "got new password prompt\n" if $pd;
            $telnet->print("$newpwd\n");                      #  - send new (current) password
## Wait for new password confirmation prompt.
            $telnet->waitfor(-match => $passwd{confirm} )
            or do {
              if ($telnet->eof) {
                logit("  read eof waiting for new password confirmation prompt\n",ERR);
                }else{
                logit((sprintf "  %s \n",$telnet->lastline),ERR);
              }
              $telnet->close;
              next Account;
            };
            
            print "got 2nd new password prompt\n" if $pd;
            $telnet->print("$newpwd\n");                      #  - send new (current) password
## Wait for new password changed OK message
            my($prematch, $match) = $telnet->waitfor(-match => "/($passwd{success}|$passwd{failure})/i" )
            or do {
              if ($telnet->eof) {
                logit("  read eof waiting for password changed OK message\n",ERR);
                }else{
                logit((sprintf "  %s \n",$telnet->lastline),ERR);
              }
              $telnet->close;
              next Account;
            };
            if ( $match =~ /$passwd{success}/i ) {
              logit("  password changed OK\n",NORM);
              }elsif ($match =~ /$passwd{success}/i ) {
              logit("  password change failed \n",NORM);
            }
            }else{
            logit((sprintf "%s \n",$telnet->errmsg),ERR);
          }
          $telnet->close;
          }else{
          logit("  unable to open password server\n",ERR);
        }
        
        }else{
        logit("  server is not on network ( > $netTimeOut secs to respond to icmp ping ) \n",ERR);
      }
    }
  }
  $mw->Unbusy;
}
#----------------------------------------------------------------------------
sub logit {
  my($mess,$z) = @_;
  $t->tag(qw/configure norm -foreground black -font C_normal/);
  $t->tag(qw/configure err1 -foreground red -font C_normal/);
  $t->tag(qw/configure bold -foreground black -background grey -font C_bold/);
  if ( $z == NORM ) {
    $t->insert('end',$mess,'norm');
    }elsif ( $z == ERR ){
    $t->insert('end',$mess,'err1');
    }elsif ( $z == BOLD ){
    $mess = substr("$mess                                                  ",0,50);
    $t->insert('end',$mess,'bold');
  }
  $t->see('end');
  $t->update;
}
#----------------------------------------------------------------------------
sub ntpasswd {
  my ( $Domain , $User, $OldPassword, $NewPassword ) = @_;
  if ( Win32::AdminMisc::UserChangePassword( $Domain , $User, $OldPassword, $NewPassword ) ) {
    &logit("   - changed to new password OK\n",NORM);
    }else{
    my $msg = Win32::FormatMessage(Win32::GetLastError());
    chop $msg;
    &logit("$msg\n",ERR);
  }
}
#----------------------------------------------------------------------------
sub ntdauth {
  my ( $Domain , $User, $Password) = @_;
  if ( AuthenticateUser($Domain, $User, $Password) ) {
    &logit("   - authenticated OK\n",NORM);
    }else{
    my $msg = Win32::FormatMessage(Win32::GetLastError());
    chop $msg;
    &logit("authentication failed - $msg\n",ERR);
  }
}
#----------------------------------------------------------------------------
sub ntwauth {
  my ( $Server , $User, $Password) = @_;
  my $mapped = Win32::Lanman::NetUseDel("\\\\$Server\\ipc\$",&USE_FORCE );
  if ( Win32::Lanman::NetUseAdd({remote => "\\\\$Server\\ipc\$",
        password => "$Password",
        username => "$User",
        domain => "\\\\$Server",
    asg_type => &USE_IPC})){
    &logit("   - authenticated OK\n",NORM);
    Win32::Lanman::NetUseDel("\\\\$Server\\ipc\$",&USE_FORCE ) unless ($mapped);
    }else{
    my $msg = Win32::FormatMessage(Win32::Lanman::GetLastError());
    chop $msg;
    &logit("authentication failed - $msg\n",ERR);
  }
}
#----------------------------------------------------------------------------
sub NetChk ($) {
  my($host) = @_;
  my($p) = Net::Ping->new("icmp",$netTimeOut);
  if ($p->ping($host)) {
    return TRUE;
  }
  return FALSE;
}
#############################################################################

=cut code to work on

taken from Win32::Lanman docs
sub ntprint{
  
  my $info{type} = &RESOURCETYPE_PRINT;
  my $info{localname} = "lpt1:";
  mt $info{remotename} = "\\\\testserver\\testprinter";
  
  
  
  if(!Win32::Lanman::WNetAddConnection(\%info))
  {
    print "Sorry, something went wrong; error: ";
# get the error code
    print Win32::Lanman::GetLastError();
    exit 1;
  }
  
}
=end


1;
__END__

=head1 NAME

setpwd.pl - perl script that manages multiple passwords

=head1 SYNOPSIS

setpwd.pl [config]

=head1 DESCRIPTION

The setpwd.pl script allows passwords on multiple computers to be
changed from one client.  It is intended to simplify the maintenance
of passwords for many accounts on different operating systems
(Windows NT and Unix)

Account definitions can be entered manually using the Edit menu.  These can
then be saved in a configuration file (accounts.pl by default).

The configuration file is a simple perl script that is executed in a seperate
name space to extract the necessary data. The File/Open, File/Save and
File/Save As menus open and save the configuration file.  File/Import allows
accounts details from a configuration file to be imported into the currently
defined accounts.

The configuration file can also be supplied as an argument when calling the
script.

The Edit/Selection menu allows the selection of the defined accounts for
having passwords changed.  This selection is saved in the configuration file.
This feature allows any combination of the accounts defined to have passwords
reset.

The Action/Change Passwords sub-menu allows All, NT only, Unix only or selected
passwords to be changed.

The Action/Check Connection sub-menu allows All, NT only, Unix only or selected
network connection and passwords to be verified.  NT passwords are verified by
connecting to the IPC$ share of the PDC (for a domain) or workstation.

When changing passwords the log are will display machine names in black on grey
(bold).  General messages are displayed in black on blue, and errors in red on
blue.

Error messages may be a little cryptic and meaningless - it just depends what
information can be gathered from the operating system.

This has been moderatly tested on NT 4.0 (SP 6a), Win 2000,  OSF1 and Solaris 8

=head1 AUTHOR

Dave Roberts

=head1 SUPPORT

This is $Revision: 1.13 $

You can send bug reports and suggestions for improvements on this module
to me at DaveRoberts@iname.com. However, I can't promise to offer
any other support for this script.

=head1 COPYRIGHT

This module is Copyright Dave Roberts © 2000, 2001. All rights reserved.

This script is free software; you can redistribute it and/or modify it
under the same terms as Perl itself. This script is distributed in the
hope that it will be useful, but WITHOUT ANY WARRANTY; without even
the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
PURPOSE. The copyright holder of this script can not be held liable
for any general, special, incidental or consequential damages arising
out of the use of the script.

=head1 TO DO

=over 4

=item 1

Save the hash's of accounts and computers in an ini file - so no editing
of setpwd.pl is required. completed 28 May 2001

=item 2

Evaluate a safe way to record the last succesful password change for each
account/computer in a file - that is itself encrypted.  This would allow the
time od the last succesful (and unsuccesful) password change to be recorded,
as well as the passwords themselves.

=item 3

Add a feature that will allow selective accounts to be changed (perhaps a radio
button for each account?).  This over and above the menu options for changing NT,
Unix etc. completed 31 May 2001

=item 4

add password strength checking.  This is to try and prevent the problems associated
with new passwords being rejected by the policies of individual computers and domains.
At least this could include password length, numbers of specific character types etc.
Failure would generate a warning - but would still allow password changes to be attempted.

=item 5

add a way to change root under unix - on servers that do not allow a root login
over the network.

=back
=cut

