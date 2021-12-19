#!/usr/local/perl/bin/perl -- # -*-Perl-*-
# NOTE: I always use -w, but I'm not here because there's a bug 
#       in Win32::GUI which constantly spits out warnings.  It's
#       annoying, so I'm not using -w.  Get over it.
#
# monkchat
# Shendal, June 2000
#
# Special thanks to zzamboni who created PerlMonksChat.pm
# Very special thanks to vroom for creating PerlMonks.org
# Oh, and Larry Wall's okay in my book for making perl
#
# Notes:
#  - When I output to the chatterbox window, the script needs
#    to append the output to the end of the buffer.  Currently,
#    Win32::GUI doesn't have a nice way to do this.  Instead,
#    I have to select the end of the buffer and then do a 
#    ReplaceSel.  It's kludgy, but it works.
#
# To-do:
#  - userlist could be double-clickable to get info
#    on selected user (by launching a browser?)
#
# Version history:
# 1.0 6/21/00
#  - Added File->About to show version information
#  - Chatterbox now automatically scrolls on every insert!  *phew*
# 0.9.4 6/20/00
#  - Hitting return now sends whatever is in the input box. I simply
#    changed the Win32::Window to Win32::DialogBox and it worked.
#    Go figure.
#  - Tabbing around the userinfo/password box now works as well.
# 0.9.3 6/20/00
#  - Spawn new process to alleviate gui locking during updates
# 0.9.2 6/16/00
#  - XP progress bar is more accurate: now reports % of way
#    from current level to next
# 0.9.1 6/16/00
#  - fixed private message formatting
#  - text now inserted at bottom of buffer
#  - added /checkoff, /co for checking off private messages
#  - added /msgs to re-print unchecked private messages
#  - sent private messages now appear in chatterbox buffer
#  - added color
# 0.9 6/15/00
#  - initial release
#
use strict;
use Getopt::Long;
use Win32::GUI;
use IPC::Open2;
use Symbol;
use PerlMonksChat;

# Process command-line options
use vars '$opt_server'; # launch in server mode
use vars '$opt_close';  # close dos parent window
use vars '$opt_debug';  # debug
&GetOptions("server","close","debug") or die "Bad options!";

# Since we're communicating line by line, we want everything unbuffered
select STDERR; $|=1;
select STDOUT; $|=1;

# Version info
my($version)     = '1.0';
my($status_idle) = "monkchat version $version is idle";

# Polling itervals (in millisecs)
# set to 0 to disable
my($interval_chat)     = 15000;  # 15 secs
my($interval_xp)       = 30000;  # 30 secs
my($interval_userlist) = 30000;  # 30 secs

# refreshes all caches at the expense of occasional gui locks
# not really needed, unless you REALLY want to be up to date
my($interval_caches)   = 0;

# Colors
my($text_default)  = 0x010101; # black
my($text_private)  = 0x880088; # purple
my($text_username) = 0xFF0000; # blue
my($text_message)  = 0x888800; # aqua
my($text_error)    = 0x0000FF; # red

# perlmonk levels
# the xp xml ticker doesn't return this, so we'll have to hard code it
my(%perlmonk_levels) = (1 => 0,
			2 => 20,
			3 => 50,
			4 => 100,
			5 => 200,
			6 => 500,
			7 => 1000,
			8 => 1600,
			9 => 2300,
			10 => 3000);

# This is the beast that drives everything
my($p); # perlmonkschat object

# user information
my($user,$passwd);

# Server Objects
my($serverProcess); # server process object
my($toServer)   = gensym(); # send data glob to server
my($fromServer) = gensym(); # read data glob from server

# GUI Objects
my($Window);        # The over-all window object
my($Chatfield);     # object that displays all the chat text
my($Userlist);      # userlist listbox
my($UserlistLabel); # displays number of users logged in
my($Inputfield);    # object that allows the user to type their own message
my($SayButton);     # send text button
my($Progress);      # progress bar intended to show xp & next level
my($XPLabel);       # displays XP information on the screen
my($Status);        # well, a status bar
my($userinfo_w);    # userinformation window
my($menu);          # Globally declare menu since it affects some user
                    # Thanks to httptech for this one

if ($opt_server) {
    # launch as server process
    &runServer;
} else {
    # launch as client process
    # Hide the DOS window that created me, if you want to
    # Warning: This can create a 'zombie' process
    &closeDOSParent if ($opt_close);
    &initWindow;
    &initServer;
    &initChat;
    Win32::GUI::Dialog();
}

################################################################################
#
# initServer
#
# Initialize the server process
#
sub initServer {
    $Status->Text("Initializing server process...");
    my($flags) = '-s';
    $flags .= ' -d' if ($opt_debug);
    $serverProcess = open2($fromServer,$toServer,"$^X","$0","$flags");
    while (<$fromServer>) {
	if (/server started/) {
	    printMessage("Server process started successfully ($serverProcess).");
	    last;
	} else {
	    printMessage("Failed to start server process.");
	}
    }
    select $toServer; $|=1; select STDOUT;
    $Status->Text("$status_idle");
}

################################################################################
#
# initChat
#
# Initialize the chat process
#
sub initChat {
    $Status->Text("Initializing client...");
    $Window->AddTimer("updChatterbox",$interval_chat)   if ($interval_chat);
    $Window->AddTimer("updXP",$interval_xp)             if ($interval_xp);
    $Window->AddTimer("updUserlist",$interval_userlist) if ($interval_userlist);
    $Window->AddTimer("refreshCaches",$interval_caches) if ($interval_caches);
    &updChatterbox_Click; # seed the chatterbox
    &updXP_Click;         # seed the XP info
    &updUserlist_Click;   # seed the Userlist area
    $Status->Text("$status_idle");
}

################################################################################
#
# runServer
#
# Run as server
#
sub runServer {
    select STDERR; $|=1; select STDOUT; $|=1;
    # server-only objects
    my(@chat_cache,%xp_cache,%userlist_cache,@msgs_cache); # caches

    # init perlmonks chat object
    $p = PerlMonksChat->new();
    $p->add_cookies;
    $p->login($user,$passwd) if $user;

    # Seed caches
    &refreshCaches;

    # print advisory information so the gui can continue loading
    print "server started\n";

    while (<STDIN>) {
	s/\r*\n//g;
	print STDERR "SERVER: received \"$_\"\n" if ($opt_debug);
	if (/chat/) {
	    foreach (@chat_cache) { print "$_\n"; }
	    print "MeSsAgE_eNd\n";
	    @chat_cache = (); # flush cache
	} elsif (/xp/) {
	    foreach ('level','xp','xp2nextlevel','votesleft') { print "$xp_cache{$_}\n"; }
	    print "MeSsAgE_eNd\n";
	} elsif (/userlist/) {
	    foreach (sort keys(%userlist_cache)) { print "$_\n"; }
	    print "MeSsAgE_eNd\n";
	} elsif (/msgs/) {
	    my(%msgs) = $p->personal_messages;
	    foreach (sort keys(%msgs)) { print "$msgs{$_}\n"; }
	    print "MeSsAgE_eNd\n";
	} elsif (/^upd/) {
	    &updChatCache     if (/updChatCache/);
	    &updXPCache       if (/updXPCache/);
	    &updUserlistCache if (/updUserlistCache/);
	} elsif (/^refreshCaches/) {
	    &refreshCaches;
	} elsif (s/^SEND://) {
	    s/\r*\n//g;
	    print STDERR "SERVER: Sending \"$_\"\n" if ($opt_debug);
	    $p->send("$_");
	} elsif (/^CO:(.+)$/) {
	    my(@ids) = split /,/,$1;
	    my(%msgs) = $p->personal_messages;
	    $p->checkoff(map { (sort keys %msgs)[$_-1] } @ids);
	} elsif (/^LOGIN (\S+) (\S+)$/) {
	    $p->login($1,$2);
	} else {
	    print STDERR "Un-oh -- shouldn't ever get here: $_\n";
	}
    }
    print STDERR "server exited abnormally\n";

    sub updChatCache     { foreach ($p->getnewlines(1)) { push @chat_cache, $_;  }  }
    sub updXPCache       { %xp_cache       = $p->xp;                }
    sub updUserlistCache { %userlist_cache = $p->users;             }
    sub refreshCaches {
	&updChatCache;
	&updXPCache;
	&updUserlistCache;
    }
}

################################################################################
#
# getFromServer
#
# Interface for client to get information from server
#
sub getFromServer {
    my($what) = shift;
    my(@result) = ();
    print STDERR "CLIENT: requesting $what\n" if ($opt_debug);
    print $toServer "$what\n";
    while (<$fromServer>) {
	last if (/MeSsAgE_eNd/);
	s/\r?\n//g;
	push @result, $_;
    }
    print STDERR "CLIENT: received @result\n" if ($opt_debug);
    return @result;
}

################################################################################
#
# initWindow
#
# Initialize the GUI window
#
sub initWindow {
    $menu = Win32::GUI::MakeMenu (
        "&File" => "File",
          ">  &About" => "About",
          ">  E&xit"  => "Exit",
	"&Update" => "Update",
          ">  Force update on &chatterbox"  => "updChatterbox",
          ">  Force update on &XP"          => "updXP",
          ">  Force update on &userlist"    => "updUserlist",
          ">  -"                            => 0,
          ">  Change username and password" => "updUsername",
	);
    $Window = new Win32::GUI::DialogBox(
	-title  => "Perlmonks Chat",
	-left   => 100,
	-top    => 100,
	-width  => 600,
	-height => 400,
	-name   => "Window",
	-style  => WS_MINIMIZEBOX | WS_CAPTION | WS_SYSMENU,
	-menu   => $menu,
	);
    $Chatfield = $Window->AddRichEdit(
	-name     => "Chatfield",
	-left     => 5,
	-top      => 5,
	-text     => "",
	-width    => $Window->ScaleWidth-105,
	-height   => $Window->ScaleHeight-70,
	-readonly => 1,
	-style    => WS_CHILD | WS_VISIBLE | ES_AUTOVSCROLL | WS_VSCROLL
		     | ES_LEFT | ES_MULTILINE | ES_READONLY,
	-exstyle  => WS_EX_CLIENTEDGE,
        );
    $Userlist = $Window->AddListbox(
        -name     => "Userlist",
        -text     => "Userlist",
        -left     => $Window->ScaleWidth-100,
        -top      => 5,
        -width    => 95,
        -height   => $Window->ScaleHeight-65,
        -multisel => 0,
        -sort     => 1,
        );
    $UserlistLabel = $Window->AddLabel(
        -text     => "Getting userlist...",
        -sunken   => 1,
        -name     => "UserlistLabel",
        -left     => $Window->ScaleWidth-100,
        -top      => $Window->ScaleHeight-86,
        -width    => 95,
        -height   => 21,
        );
    $Inputfield = $Window->AddTextfield(
	-name     => "input",
	-left     => 5,
        -top      => $Window->ScaleHeight-60,
	-text     => "",
        -width    => $Window->ScaleWidth-50,
        -height   => 22,
        -foreground => [0,0,0],
        -background => [255,255,255],
	-tabstop  => 1,
        );
    $Inputfield->SetFocus;
    $SayButton = $Window->AddButton(
	-name     => "Say",
        -left     => $Window->ScaleWidth-40,
        -top      => $Window->ScaleHeight-60,
        -width    => 35,
        -height   => 22,
        -text     => "Say",
	-tabstop  => 1,
	-default  => 1,
	-ok       => 1,
        );
    $Progress = $Window->AddProgressBar(
        -name     => "Progress",
        -left     => $Window->ScaleWidth/2,
        -top      => $Window->ScaleHeight-34,
        -width    => ($Window->ScaleWidth/2)-5,
        -height   => 10,
        -smooth   => 1,
        );
    $XPLabel = $Window->AddLabel(
        -text     => "Getting XP info...",
        -sunken   => 1,
        -name     => "XPLabel",
        -left     => $Window->ScaleWidth/2,
        -top      => $Window->ScaleHeight-22,
        -width    => ($Window->ScaleWidth/2)-5,
        -height   => 20,
        );
    $Status = $Window->AddLabel(
        -name     => "Status",
        -text     => "$status_idle",
	-sunken   => 1,
        -left     => 5,
        -top      => $Window->ScaleHeight-22,
        -width    => ($Window->ScaleWidth/2)-10,
        -height   => 20,
        );
    $Window->Show();
}

################################################################################
#
# Window_Terminate
#
# What to do when the user closes the window
#
sub Window_Terminate { 
    if (kill('KILL',$serverProcess)) {
	print "Successfully shutdown server.\n";
    } else {
	print "An error occurred shutting down server ($serverProcess).\n";
    }
    return -1;
}

################################################################################
#
# Timers
#
# Checks for new message or updates info on timers
#
sub updChatterbox_Timer { &updChatterbox_Click; print $toServer "updChatCache\n";     }
sub updXP_Timer         { &updXP_Click;         print $toServer "updXPCache\n";       }
sub updUserlist_Timer   { &updUserlist_Click;   print $toServer "updUserlistCache\n"; }
sub refreshCaches_Timer { print $toServer "refreshCaches\n"; }

################################################################################
#
# Say_Click
#
# What to do when the user clicks the say button
#
sub Say_Click {
    $Status->Text("Sending data...");
    my($text) = $Inputfield->Text();
    $Inputfield->Text("");
    $text =~ s/\r*\n//g;
    if ($text =~ /^\s*\/msg\s+(\S+)\s*(.+)$/i) {
	print $toServer "SEND:$text\n";
	printMessage("Sent private msg to $1: $2");
    } elsif ($text =~ /^\/?(checkoff|co)\s+/ && (my @ids=($text=~/(\d+)/g))) {
	my($list) = join ',',@ids;
	print $toServer "CO:" . join ',',@ids . "\n";
	printMessage("* Checked off private msgs");
    } elsif ($text =~ /^\s*\/msgs\s*$/) {
	my(@msgs) = &getFromServer('msgs');
	printMessage("* No personal messages") unless @msgs;
	my($msg_num) = 1;
	foreach (@msgs) {
	    printMessage("($msg_num) $_",$text_private);
	    $msg_num++;
	}
    } else {
	print $toServer "SEND:$text\n";
    }
    $Status->Text("$status_idle");
}

################################################################################
#
# Exit_Click
#
# What to do when the user clicks the exit menu option
#
sub Exit_Click { 
    if (kill('KILL',$serverProcess)) {
	print "Successfully shutdown server.\n";
    } else {
	print "An error occurred shutting down server ($serverProcess).\n";
    }
    exit(0);
}

################################################################################
#
# About_Click
#
# What to do when the user clicks the about menu option
#
sub About_Click {
    my($gui_ver) = $Window->Version();
    printMessage("monkchat version $version");
    printMessage("Win32::GUI version $gui_ver");
    printMessage("by Shendal, copyleft 2000");
}

################################################################################
#
# updChatterbox_Click;
#
# Checks for new chat messages
#
sub updChatterbox_Click {
    $Status->Text("Checking for new chat messages...");
    my($msg_num) = 1;
    foreach (&getFromServer('chat')) {
	$Chatfield->Select(999999,999999); # See note above on this kludge
	if (s/^\(\d+\)/\($msg_num\)/) { 
	    $msg_num++;
	    printMessage("$_",$text_private);
	} elsif (s/^<(\S+)>//) {
	    printuser($1);
	    printMessage("$_",$text_default);
	} else {
	    printMessage("$_",$text_default);
	}
    }
    $Status->Text("$status_idle");

    sub printuser {
	my($user) = shift;
	printMessage('<',$text_default,1);
	printMessage("$user",$text_username,1);
	printMessage('>',$text_default,1);
    }
}

################################################################################
#
# updXP_Click
#
# Find user's current XP level and what the next level will be
#
sub updXP_Click {
    $Status->Text("Checking for new XP information...");
    my($level,$xp,$xp2next,$votesleft) = &getFromServer('xp');
    if ($level =~ /^\s*$/) {
	$XPLabel->Text("Error accessing your XP node");
    } else {
	my($position) = int(( ($xp-$perlmonk_levels{$level}) /
			      ($xp-$perlmonk_levels{$level}+$xp2next)) * 100) ;
	$Window->Progress->SetPos($position);
	my($XPLabelStr) = "Level: $level, XP: $xp, "
	    . "To next: $xp2next ($position%), Votes left: $votesleft";
	$XPLabel->Text("$XPLabelStr");
    }
    $Status->Text("$status_idle");
}

################################################################################
#
# updUserlist_Click
#
# Updates the userlist listbox
#
sub updUserlist_Click {
    $Status->Text("Checking userlist...");
    $Userlist->Clear;
    my($num_users) = 0;
    foreach (&getFromServer('userlist')) {
	$Userlist->AddString("$_");
	$num_users++;
    }
    $UserlistLabel->Text("# Users: $num_users");
    printError("Ack!  Noone's logged in!") unless $num_users;
    $Status->Text("$status_idle");
}

################################################################################
#
# updUsername_Click
#
# Updates the username/password cookie
#
sub updUsername_Click {
    $Status->Text("Updating user information...");
    my($unField,$pwField,$confField);
    if (!$userinfo_w) {
	$userinfo_w = new Win32::GUI::DialogBox(
	    -title  => "Update user info",
	    -left   => 200,
	    -top    => 200,
	    -width  => 250,
	    -height => 150,
	    -name   => "userinfo_w",
	    -style  => WS_CAPTION,
	    );
	$unField = $userinfo_w->AddTextfield(
            -name   => "username",
            -prompt => "Username:",
            -left   => 5,
            -top    => 5,
	    -height => 22,
            -width  => 150,
	    -tabstop=> 1,
            );
	$pwField = $userinfo_w->AddTextfield(
            -name     => "password",
            -prompt   => "Password:",
            -left     => 5,
            -top      => 32,
    	    -height   => 22,
            -width    => 150,
            -password => 1,
	    -tabstop=> 1,
            );
        $confField = $userinfo_w->AddTextfield(
            -name     => "confirm",
            -prompt   => "Confirm:",
            -left     => 5,
            -top      => 56,
	    -height   => 22,
            -width    => 150,
            -password => 1,
	    -tabstop=> 1,
            );
        my($cancelButton) = $userinfo_w->AddButton (
            -name     => "Cancel",
            -text     => "Cancel",
            -left     => 5,
            -top      => 83,
            -height   => 30,
            -width    => ($userinfo_w->ScaleWidth/2)-5,
	    -tabstop=> 1,
            );
        my($okButton) = $userinfo_w->AddButton (
            -name     => "Ok",
            -text     => "Ok",
            -left     => ($userinfo_w->ScaleWidth/2)+5,
            -top      => 83,
            -height   => 30,
            -width    => ($userinfo_w->ScaleWidth/2)-5,
	    -tabstop=> 1,
            );
	$unField->SetFocus;
    }
    $userinfo_w->Show();
    $Status->Text("$status_idle");

    sub userinfo_w_Terminate { return -1; }
    sub Cancel_Click { $userinfo_w->Hide; }
    sub Ok_Click { 
	unless ($unField->Text && $pwField->Text && $confField->Text) {
	    printError("All fields required. Nothing changed.");
	    $userinfo_w->Hide;
	    return;
	}
	if ($pwField->Text ne $confField->Text) {
	    printError("Password and confirmation did not match. Nothing changed.");
	    $userinfo_w->Hide;
	} else {
	    print $toServer 'LOGIN ' . $unField->Text . ' ' . $pwField->Text . "\n";
	    printMessage("Logged in as " . $unField->Text);
	    $userinfo_w->Hide;
	}
    }
}

################################################################################
#
# Userlist_DblClick
#
# What to do when a user double-clicks someone in the user list
#
sub Userlist_DblClick {
    printMessage("Detected double click in userlist!");
    my($selected) = $Userlist->GetString($Userlist->SelectedItem);
    if ($selected) {
	printMessage("Launch browser to look at user $selected.");
    }
}


################################################################################
#
# printMessage and printError
#
# Prints an error or message to the chatterbox
#
sub printMessage {
    my($msg) = shift;
    my($color) = shift || $text_message;
    my($omit_return) = shift;
    $msg .= "\n" unless $omit_return;
    $Chatfield->SetFocus();
    $Chatfield->Select(999999,999999); # See hack message above
    setColor($color);
    $Chatfield->ReplaceSel("$msg",1);
    setColor($text_default);
    $Inputfield->SetFocus();
}
sub printError {
    my($error) = shift;
    printMessage("ERROR: $error","$text_error")
}

################################################################################
#
# setColor
#
# sets text color for chatterbox
#
sub setColor {
    my($color) = shift;
    $Chatfield->SetCharFormat(-color => "$color");
}

################################################################################
#
# closeDOSParent
#
# Closes the dos prompt that created this process
#
sub closeDOSParent {
    my($DOShwnd, $DOShinstance) = Win32::GUI::GetPerlWindow();
    # either close it or hide it, but it's gonna be around when you quit this
    # program!
    Win32::GUI::CloseWindow($DOShwnd);
    Win32::GUI::Hide($DOShwnd);
}
