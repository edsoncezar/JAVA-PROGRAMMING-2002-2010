#!/usr/bin/perl -w -- # -*-Perl-*-
#
# monkchat
# Shendal, September 2000
#
# Thanks to ase for the original Tk port and color enhancements
# Special thanks to zzamboni who created PerlMonksChat.pm
# Very special thanks to vroom for creating PerlMonks.org
# Oh, and Larry Wall's okay in my book for making perl
#
# To-do:
#  - although we have a server process caching information for the
#    client, it still locks up occasionally when the client requests
#    data, and the website is taking a while to return. Probably
#    not going to be able to resolve this until perl on Win32 
#    supports alarm().
#  - closeDOSParent() doesn't work.
#
# Version history:
# 2.2.2 10/31/2002
#  - Halloween update! Fixed a few outstanding issues with GetOptions.
#    Thanks [converter].
#  - Some people are still getting bit by problems with the PerlMonks 
#    modules. The XML parsing in there is broken at worst, and fragile 
#    at best.
#
# 2.2.1 9/22/00
#  - Colorizing system totally revamped - it wasn't reliable enough.
#  - On Win32, now uses the last browser window opened, if available. This
#    also means that on Win32, the browser setting doesn't matter.
#
# 2.2 9/7/00
#  - Added support for <A HREF=foo>bar</A> tags in chatter
#  - Added support for CODE & /CODE tags in chatter
#  - Added support for /tell as an alias for /msg
#  - Using the msg button (or ctrl-enter) with the userlist will
#    now s/ /_/g so that usernames with spaces will work.
#  - printMessage now handles &lt;, &gt;, &#091;, and &#093; correctly
#    by translating them into <, >, [, and ], respectively.
#  - Will now colorize any place the username pops up, so it's easy to 
#    scan the buffer for mentions of your name.
#  - fixed a problem with UNIX & cpan:// tags
#
# 2.1.2 9/5/00
#  - Window now resizable by request from zzamboni. Widgets now act 
#    as you would expect them to.
#
# 2.1.1 9/5/00
#  - Updated to work with PerlMonksChat2, if available. Essentially, this just
#    tells the user whom he's logged in as (or suggests an upgrade).
#  - Fixed a bug where links of the form [foo|bar] or [id://123|bar] didn't work.
#  - GetInfo button was outputting extraneous information
#
# 2.1 9/1/00
#  - windows now don't allow user to resize
#  - text input field larger
#  - updUserlist no longer clobbers selection
#  - added msg button
#  - ctrl-enter now sends message to selected user (clicks msg button)
#  - name completion partially implemented. Only works when the partial
#    word is at the end of the entry.  This may be a feature?
#  - new options allows bad command or not, so it will automatically
#    block things like /msh or /ell, isuing a warning.  Default is to
#    allow these to be posted as normal text (the current behavior).
#
# 2.0.2 8/22/00
#  - now using Tk::ROText so that text in chatterbox window is selectable
#  - fixed a problem where links were not working
#  - pointing at a link now updates the status menu with the node name
#
# 2.0.1 7/13/00
#  - UNIX version now works!
#
# 2.0 7/12/00
#  - initial release
#  - majority of code base taken from the original client, which
#    was intended for use with Win32::GUI.  ase performed the original
#    port to Tk, and I have adapted some of his changes within.
#  - support for clicking on a user name to launch browser
#  - allows user to specify different colors -- saves information for
#    future sessions
#  - nodes mentioned in chat using the [node] syntax will launch a browser
#    with a single click.  Also supports [link|message] syntax by using
#    hidden text. Even supports id: and cpan: links!
#  - userlist launches browser to selected user's home node on double click
#
# 0.9 to 1.0.1
#  - these versions were based on Win32::GUI.  I dumped it in favor of
#    the more portable, more extensible, more documented, more stable Tk.
#    I don't plan on any updates to the old Win32::GUI version.
#
use strict; # Always!
use Getopt::Long;
use IPC::Open2;    # for server process
use Symbol;        # ditto
use PerlMonksChat; # access to perlmonks site

# for preference saving
use SDBM_File;
use Fcntl;

# GUI package
use Tk 8.0;
use Tk::LabEntry;
use Tk::FileSelect;
use Tk::ROText;

# Process command-line options
my($orig_params) = join ' ',@ARGV;
use vars '$opt_server'; # launch in server mode
use vars '$opt_close';  # close dos parent window
use vars '$opt_debug';  # debug
&GetOptions("server" => \$opt_server,
	    "close"  => \$opt_close,
	    "debug"  => \$opt_debug) or die "Bad options!";

# Since we're communicating line by line, we want everything unbuffered
select STDERR; $|++;
select STDOUT; $|++;

# Version info
my($version)     = '2.2.1';
my($status_idle) = "monkchat version $version is idle";

# Polling itervals (in millisecs) - set to 0 to disable
my(%interval) = (chat     => 10000,    # 10 secs
		 xp       => 30000,    # 30 secs
		 userlist => 30000);  # 30 secs
my($pid); # process id of server

# perlmonk levels
# the xp xml ticker doesn't return this, so we'll have to hard code it
my(%perlmonk_levels) = (1  => 0,
			2  => 20,
			3  => 50,
			4  => 100,
			5  => 200,
			6  => 500,
			7  => 1000,
			8  => 1600,
			9  => 2300,
			10 => 3000);

# This is the beast that drives everything
my($p); # perlmonkschat object

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
my($MsgButton);     # send private message button
my($getInfoButton); # get username information button
my($Progress);      # progress bar intended to show xp & next level
my($XPLabel);       # displays XP information on the screen
my($Status);        # well, a status bar
my($userinfo_w);    # user information window
my($choosebrowser_w); # browser information window
my($msg_num) = 0;   # number of private messages
my($menu);          # Globally declare menu since it affects some users
                    # Thanks to httptech for this one
my($prect,$ptext);  # for XP canvas
my($unField);       # username field
my($pwField);       # password field
my($browserfield);  # browser field
my($confField);     # confirmation field
my($browser) = '';  # full path to browser to launch for user information
my($loggedInUser);  # name of user currently logged in

# URL to use for links into perlmonks & cpan
my($perlmonksURL)      = 'http://www.perlmonks.org/index.pl?node=';
my($perlmonksURL_id)   = 'http://www.perlmonks.org/index.pl?node_id=';
my($perlmonksURL_cpan) = 'http://search.cpan.org/search?mode=module&query=';

# OS specific stuff
my(%options) = ();
if ($^O =~ /MSWin32/i) {
    require 5.005;
    $ENV{HOME} = "c:/TEMP" unless ($ENV{HOME});
} else {
    require 5.004_04;
}

# options hash
tie(%options,'SDBM_File',"$ENV{HOME}/.monkchat",O_RDWR|O_CREAT,0640);
my %default_options=(default    => 'black',
		     private    => 'purple',
		     username   => 'blue',
		     message    => 'green',
		     error      => 'red',
		     link       => 'brown',
		     background => 'white',
		     self       => 'orange',
		     badcmds    => 1,
		     browser    => undef,
		     );

#set options to default unless they have alreayd been set
foreach (keys %default_options) {
    unless (defined $_ && defined $options{$_} ) {
	$options{$_} = $default_options{$_};
    }
}

if ($opt_server) {
    # launch as server process
    &runServer;
} else {
    # launch as client process
    &closeDOSParent if ($opt_close);
    &initWindow;
    &initServer;
    &initChat;
    MainLoop();
}

################################################################################
#
# initServer
#
# Initialize the server process
#
sub initServer {
    &Status('Initializing server process...');
    my @flags = ('--server', $opt_debug ? '--debug' : () );
    $serverProcess = open2($fromServer, $toServer, $^X, $0, @flags);
    while (<$fromServer>) {
	chomp;
	if (/server started - logged in as \b(\w.+\w)\b/) {
	    $loggedInUser = $1;
	    printMessage("Server process started successfully ($serverProcess).");
	    printMessage("\nLogged in as $loggedInUser.");
	    last;
	} elsif (/server started/) {
	    printMessage("Server process started successfully ($serverProcess).");
	    printMessage("\nPlease upgrade your version of PerlMonksChat");
	    last;
	} else {
	    printError("Failed to start server process.");
	}
    }
    select $toServer; $|++;
    select STDOUT;
    &Status($status_idle);
}

################################################################################
#
# initChat
#
# Initialize the chat process
#
sub initChat {
    &Status('Initializing client...');
    $Window->repeat($interval{'chat'},\&updChatterbox)   if ($interval{'chat'});
    $Window->repeat($interval{'xp'},\&updXP)             if ($interval{'xp'});
    $Window->repeat($interval{'userlist'},\&updUserlist) if ($interval{'userlist'});
    &updChatterbox; # seed the chatterbox
    &updXP;         # seed the XP info
    &updUserlist;   # seed the Userlist area
    &Status($status_idle);
}

################################################################################
#
# runServer
#
# Run as server
#
sub runServer {
    # server-only objects
    my(@chat_cache,%xp_cache,%userlist_cache,@msgs_cache); # caches

    local $_;

    # init perlmonks chat object
    $p = PerlMonksChat->new();
    $p->add_cookies;

    # Seed caches
    foreach ($p->getnewlines(1)) { push @chat_cache, $_; }
    %xp_cache       = $p->xp;
    %userlist_cache = $p->users;

    # print advisory information so the gui can continue loading
    if ($xp_cache{'user'}) {
	print "server started - logged in as $xp_cache{user}\n";
    } else {
	print "server started\n";
    }

    while (<STDIN>) {
	s/\r?\n//g;
	print STDERR "SERVER: received \"$_\"\n" if ($opt_debug);
	if (/^chat$/) {
	    foreach (@chat_cache) { next unless /\S/; print "$_\n"; }
	    print "MeSsAgE_eNd\n";
	    @chat_cache = (); # flush cache
	    foreach ($p->getnewlines(1)) { push @chat_cache, $_; }
	} elsif (/^xp$/) {
	    if (defined $xp_cache{'level'}) {
		foreach ('level','xp','xp2nextlevel','votesleft') { print "$xp_cache{$_}\n"; }
	    }
	    print "MeSsAgE_eNd\n";
	    %xp_cache       = $p->xp;
	} elsif (/^userlist$/) {
	    foreach (sort { lc($a) cmp lc($b) } keys(%userlist_cache)) { print "$_\n"; }
	    print "MeSsAgE_eNd\n";
	    %userlist_cache = $p->users;
	} elsif (/^msgs$/) {
	    my(%msgs) = $p->personal_messages;
	    foreach (sort keys(%msgs)) { print "$msgs{$_}\n"; }
	    print "MeSsAgE_eNd\n";
	} elsif (/^upd\S+$/) {
	    if (/updChatCache/) {
		foreach ($p->getnewlines(1)) { push @chat_cache, $_; }
	    }
	    %xp_cache       = $p->xp    if (/updXPCache/);
	    %userlist_cache = $p->users if (/updUserlistCache/);
	} elsif (s/^SEND://) {
	    print STDERR "SERVER: Sending \"$_\"\n" if ($opt_debug);
	    $p->send("$_");
	} elsif (/^CO:(.+)$/) {
	    my(@ids) = split /,/,$1;
	    my(%msgs) = $p->personal_messages;
	    $p->checkoff(map { (sort keys %msgs)[$_-1] } @ids);
	} elsif (/^LOGIN (\S+) (\S+)$/) {
	    $p->login($1,$2);
	    print "Logged in as " . $p->current_user . "\n";
	    print "MeSsAgE_eNd\n";
	} else {
	    print STDERR "Un-oh -- shouldn't ever get here: $_\n";
	}
    }
    print STDERR "server exited abnormally\n";
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
    print STDERR "Initializing Tk window...\n" if ($opt_debug);
    $Window = MainWindow->new(-title  => "Perlmonks Chat");
    my($menu) = $Window->Menu;
    $Window->configure (-menu => $menu);

    # build menubar
    my($file_menu)    = $menu->cascade(-label   => '~File',
				       -tearoff => 0);
    my($update_menu)  = $menu->cascade(-label   => '~Update',
				       -tearoff => 0);
    my($options_menu) = $menu->cascade(-label   => '~Options',
				       -tearoff => 0);
    # file menu
    $file_menu->command(-label     => 'About',
			-underline => 1,
			-command   => \&About,
			);
    $file_menu->command(-label     => 'Exit',
			-underline => 1,
			-command   => \&Exit,
			);
    # update menu
    $update_menu->command(-label     => 'Chatterbox',
			  -underline => 0,
			  -command   => \&updChatterbox,
			  );
    $update_menu->command(-label     => 'XP',
			  -underline => 0,
			  -command   => \&updXP,
			  );
    $update_menu->command(-label     => 'Userlist',
			  -underline => 0,
			  -command   => \&updUserlist,
			  );
    $update_menu->separator();
    $update_menu->command(-label     => 'Username and Password',
			  -underline => 0,
			  -command   => \&updUsername,
			  );

    # options menu
    $options_menu->command(-label     => 'Chat Background',
			   -underline => 0,
			   -command   => sub { $Chatfield->configure
						   (-bg=>$Window->chooseColor
						    (-initialcolor=>$Chatfield->cget
						     (-bg),
						     -title => "Background Color"))
						   }
			   );
    $options_menu->command(-label     => 'Default text',
			   -underline => 0,
			   -command   => sub { $Chatfield->tagConfigure
						   ('default',-foreground=>$Window->chooseColor
						    (-initialcolor=>$Chatfield->tagCget
						     ('default',-foreground),
						     -title => "Default Text Color"));
					   }
			   );
    $options_menu->command(-label     => 'Private text',
			   -underline => 0,
			   -command   => sub { $Chatfield->tagConfigure
						   ('private',-foreground=>$Window->chooseColor
						    (-initialcolor=>$Chatfield->tagCget
						     ('private',-foreground),
						     -title => "Received Private /msg Text Color"));
					   }
			   );
    $options_menu->command(-label     => 'Username text',
			   -underline => 0,
			   -command   => sub { $Chatfield->tagConfigure
						   ('username',-foreground=>$Window->chooseColor
						    (-initialcolor=>$Chatfield->tagCget
						     ('username',-foreground),
						     -title => "Username Text Color"));
					   }
			   );
    $options_menu->command(-label     => 'Message text',
			   -underline => 0,
			   -command   => sub { $Chatfield->tagConfigure
						('message',-foreground=>$Window->chooseColor
						 (-initialcolor=>$Chatfield->tagCget
						  ('message',-foreground),
						  -title => "Sent Private /msg Text Color"));
					}
			   );
    $options_menu->command(-label     => 'Error text',
			   -underline => 0,
			   -command   => sub { $Chatfield->tagConfigure
						   ('error',-foreground=>$Window->chooseColor
						    (-initialcolor=>$Chatfield->tagCget
						     ('error',-foreground),
						     -title => "Error Text Color"));
					   }
			   );
    $options_menu->command(-label     => 'Link text',
			   -underline => 0,
			   -command   => sub { $Chatfield->tagConfigure
						   ('link',-foreground=>$Window->chooseColor
						    (-initialcolor=>$Chatfield->tagCget
						     ('link',-foreground),
						     -title => "Link Text Color"));
					}
			   );
    $options_menu->command(-label     => 'My nickname text',
			   -underline => 0,
			   -command   => sub { $Chatfield->tagConfigure
						   ('self',-foreground=>$Window->chooseColor
						    (-initialcolor=>$Chatfield->tagCget
						     ('self',-foreground),
						     -title => "My Nickname Color"));
					}
			   );
    $options_menu->separator();
    $options_menu->command(-label     => 'Save color settings',
			   -underline => 0,
			   -command   => \&save_settings);
    $options_menu->command(-label     => 'Reset to default colors',
			   -underline => 0,
			   -command   => \&reset_settings);
    $options_menu->separator();
    $options_menu->checkbutton(-label       => 'Allow bad commands',
			       -onvalue     => 1,
			       -offvalue    => 0,
			       -indicatoron => 1,
			       -underline   => 0,
			       -variable    => \$options{badcmds});
    $options_menu->command(-label     => 'Choose browser',
			   -underline => 7,
			   -command   => \&chooseBrowser);
    
    # create window frames
    my($uframe) =$Window->Frame()->pack(-side   => 'top',
					-fill   => 'both',
					-expand => 1
					);
    my($lframe) =$uframe->Frame()->pack(-side   => 'left',
					-fill   => 'both',
					-expand => 1);
    my($rframe) =$uframe->Frame()->pack(-side   => 'right',
					-fill   => 'y');
    my($dframe) =$Window->Frame()->pack(-side   =>'top',
					-fill   => 'x');

    # chatfield
    $Chatfield = $lframe->Scrolled("ROText",
				   -width  => 20,
				   -height => 2,
				   -bg     => $options{'background'},
				   -wrap   => 'word',
				   -relief => 'sunken',
				   -scrollbars => 'osoe',
				   )->pack(-side   => 'top',
					   -fill   => 'both',
					   -expand => 1);

    # userlist			
    $Userlist = $rframe->Scrolled("Listbox",
				  -width      => 12,
				  -scrollbars => 'osoe',
				  -selectmode => 'single',
				  )->pack(-side   =>'top',
					  -fill   => 'y',
					  -expand => 1,
					  -padx   => 2);
    $UserlistLabel = $rframe->Label(-text     => "Getting userlist...",
				    -relief   => "sunken",
				    )->pack(-side=>'top',-fill=>'x',-padx=>2,-pady=>2);
    $Userlist->bind("<Double-Button-1>",\&getInfo);
    $Userlist->bind("<Return>",\&getInfo);
    
    # input field
    $Inputfield = $lframe->Entry()->pack(-side   => 'left',
					 -fill   => 'x',
					 -expand => 1,
					 -pady   => 2);
    $Inputfield->bind("<Return>", \&Say_Click);
    $Inputfield->bind("<Control-Return>", \&Msg_Click);
    $Inputfield->bind("<Tab>", \&completeName);

    # say button
    $SayButton = $lframe->Button(-text     => "Say",
				 -command  => \&Say_Click,
				 -height   => 1,
				 -takefocus=> 0,
				 )->pack(-side=>'left',-padx=>2,-pady=>2);
    # msg button
    $MsgButton = $lframe->Button(-text     => "Msg",
				 -command  => \&Msg_Click,
				 -height   => 1,
				 -takefocus=> 0,
				 )->pack(-side=>'left',-padx=>2,-pady=>2);
    # getInfo button
    $getInfoButton = $lframe->Button(-text     => "Get Info",
				     -command  => \&getInfo,
				     -height   => 1,
				     -takefocus=> 0,
				     )->pack(-side=>'left',-padx=>2,-pady=>2);

    # status label
    $Status = $dframe->Label(-text   => $status_idle,
			     -relief => 'sunken',
			     -width  => 40,
			     )->pack(-side   => 'left',
				     -fill   => 'x',
				     -expand => 1);
    
    # XP status bar
    $Progress = $dframe->Canvas(-height      => 16,
				-width       => 301,
				-relief      => 'sunken',
				-borderwidth => 2,
				-takefocus   => 0
				)->pack(-side => 'left',
					-padx => 2);
    $prect = $Progress->createRectangle(0,0,300,16,-fill=>'red',-outline=>'red');
    $ptext = $Progress->createText(150,10,-text=>'Getting XP info...');

    # link text & cursor changes
    foreach (keys %options) {
	next if /(browser|badcmds)/;
	$Chatfield->tagConfigure($_,-foreground=>$options{$_});
    }
    $Chatfield->tagConfigure('italic',-font=>'fontitalic');
    $Chatfield->tagConfigure('hidden_link',-state=>'hidden');
    $Chatfield->tagBind('link',"<Button-1>",sub { &LaunchBrowser(&getLink); });
    $Chatfield->tagBind('link',"<Enter>",sub {
	&Status('Node: ' . &getLink);
	$Chatfield->configure(-cursor=>'hand1'); });
    $Chatfield->tagBind('link',"<Leave>",sub { 
	&Status($status_idle);
	$Chatfield->configure(-cursor=>'arrow'); });
    $Chatfield->tagBind('username',"<Button-1>",sub { &LaunchBrowser(&getLink); });
    $Chatfield->tagBind('username',"<Enter>",sub {
	&Status('Node: ' . &getLink);
	$Chatfield->configure(-cursor=>'hand1'); });
    $Chatfield->tagBind('username',"<Leave>",sub {
	&Status($status_idle);
	$Chatfield->configure(-cursor=>'arrow'); });

    # Set initial focus
    $Inputfield->focus;
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
# Say_Click
#
# What to do when the user clicks the say button
#
sub Say_Click {
    &Status('Communicating with website...');
    my($text) = $Inputfield->get();
    $Inputfield->delete(0,'end');
    $text =~ s/\r*\n//g;
    if ($text =~ /^\s*\/(msg|tell)\s+(\S+)\s*(.+)$/i) {
	print $toServer "SEND:$text\n";
	printMessage("\nSent private msg to $2: $3");
    } elsif ($text =~ /^\/?(checkoff|co)\s+/ && (my @ids=($text=~/(\d+)/g))) {
	$msg_num = $msg_num - scalar(@ids);
	my($list) = join ',',@ids;
	print $toServer "CO:$list\n";
	printMessage("\n* Checked off private msgs");
    } elsif ($text =~ /^\s*\/msgs\s*$/) {
	$msg_num = 0;
	my(@msgs) = &getFromServer('msgs');
	printMessage("\n* No personal messages") unless @msgs;
	foreach (@msgs) { &printChat("$_"); }
    } elsif ($text =~ /^\/me /) {
	print $toServer "SEND:$text\n";
    } elsif ($text =~ /^\// && ! $options{badcmds}) {
	printError("\nBad command: $text");
    } else {
	print $toServer "SEND:$text\n";
    }
    &Status($status_idle);
}

################################################################################
#
# Msg_Click
#
# What to do when the user clicks the msg button
#
sub Msg_Click {
    &Status('Sending data...');
    my($index) = $Userlist->curselection;
    if ($index eq "") {
	printError("No user selected");
    } else {
	my($username) = $Userlist->get($index);
	my($text) = $Inputfield->get();
	if ($text) {
	    $Inputfield->delete(0,'end');
	    $text =~ s/\r*\n//g;
	    printMessage("\nSent private msg to $username: $text");
	    $username =~ s/ /_/g; # must sub _ for spaces
	    $text = '/msg ' . $username . " $text";
	    print $toServer "SEND:$text\n";
	}
    }
    &Status($status_idle);
}

################################################################################
#
# completeName
#
# completes the word where the current insertion point is by looking through
# the userlist
#
sub completeName {
    my($text) = $Inputfield->get();
    return unless ($text);
    if ($text =~ /(\[*)\b(\w+)$/) {
	my($brace,$word) = ($1,$2);
	foreach ($Userlist->get(0,'end')) {
	    if (s/^$word//i) {
		$_ .= ']' if ($brace);
		$Inputfield->insert('end',$_);
		last;
	    }
	}
    }
    $Inputfield->break;
}

################################################################################
#
# getInfo
#
# Get information on selected user
#
sub getInfo {
    my($index) = $Userlist->curselection;
    if ($index eq "") {
	printError("No user selected");
    } else {
	my($username) = $Userlist->get($index);
	&LaunchBrowser("$username");
    }
}

################################################################################
#
# getLink
#
# Return the node name which is currently being pointed at by the cursor
#
sub getLink {
    my($text) = shift;
    my(%ranges) = ($text->tagRanges('link'),$text->tagRanges('username'));
    my($current_row,$current_col) = $text->index('current') =~ /^(\d+)\.(\d+)$/;
    foreach (keys %ranges) {
	my($start_row,$start_col) = /^(\d+)\.(\d+)$/;
	my($end_row,$end_col)     = $ranges{$_} =~ /^(\d+)\.(\d+)$/;
	if (&isBetween($current_row,$start_row,$end_row) &&
	    &isBetween($current_col,$start_col,$end_col)) {
	    my($value) = $text->get("$start_row.$start_col","$end_row.$end_col");
	    my(%hidden_link_ranges) = $text->tagRanges('hidden_link');
	    foreach (keys %hidden_link_ranges) {
		if ($hidden_link_ranges{$_} eq "$start_row.$start_col") {
		    $value = $text->get($_,$hidden_link_ranges{$_});
		}
	    }
	    return $value;
	}
    }
}
sub isBetween {
    my($val,$a,$b) = @_;
    return 1 if (($val >= $a) && ($val <= $b));
}

################################################################################
#
# LaunchBrowser
#
# Launch a browser to look at a particular node on perlmonks
#
sub LaunchBrowser {
    unless ($^O =~ /MSWin32/i || defined $options{'browser'}) {
	printError("No browser defined");
	printError("Use Options->Choose browser menu to define one.");
	return -1;
    }
    my($node) = @_;
    my($url);
    my($browser) = $options{'browser'};
    printMessage("\n*Launching browser for node $node...");
    if    ($node =~ s/^id:\/\///)   { $url = $perlmonksURL_id   . $node; }
    elsif ($node =~ s/^node_id=//)  { $url = $perlmonksURL_id   . $node; }
    elsif ($node =~ s/^cpan:\/\///) { $url = $perlmonksURL_cpan . $node; }
    elsif ($node =~ /^http:/)       { $url = $node;                      }
    else                            { $url = $perlmonksURL      . $node; }
    if ($^O =~ /MSWin32/i) {
	my($process);
	$browser =~ s/\\/\\\\/g;
	$browser =~ s/\"//g;
	$browser =~ /\\(\S+)$/;
	my($pgm) = $1;
	eval '
	    use Win32::Process;
	    Win32::Process::Create($process,
                                   "$ENV{SYSTEMROOT}\\\\system32\\\\cmd.exe",
                                   "cmd.exe /c start $url",
                                   0,DETACHED_PROCESS,".") ||
	      printError("Unable to launch browser: " . Win32::FormatMessage(Win32::GetLastError()));
	';
    } else {
	eval '
	    my($pid) = fork;
            $url =~ s/\s/+/g;
	    if ($pid == 0) {
		exec "$browser \'$url\'";
	    }';
    }
}

################################################################################
#
# Exit
#
# What to do when the user clicks the exit menu option
#
sub Exit {
    &Status("Killing server process ($serverProcess)...");
    if (kill('KILL',$serverProcess)) {
	print "Successfully shutdown server.\n";
    } else {
	print "An error occurred shutting down server ($serverProcess).\n";
    }
    exit(0);
}

################################################################################
#
# About
#
# What to do when the user clicks the about menu option
#
sub About {
    my($perl_ver) = $];
    $perl_ver =~ s/^(\d+)\.0+(\d+)$/$1.$2/;
    my($tk_ver) = $Tk::VERSION;
    $tk_ver =~ s/^(\d)(\d+)\.\S+$/$1.$2/;
    my($browser_short) = $options{'browser'};
    $browser_short =~ s/\\+/\\/g;
    printMessage("\nmonkchat version $version");
    printMessage("\nPerl version $perl_ver");
    printMessage("\nTk version $tk_ver");
    printMessage("\nBrowser: $browser_short") if ($browser_short);
    printMessage("\nby ");
    printMessage('Shendal','link');
    printMessage(", copyleft 2000");
}

################################################################################
#
# updChatterbox
#
# Checks for new chat messages
#
sub updChatterbox {
    &Status('Checking for new chat messages...');
    foreach (&getFromServer('chat')) { &printChat("$_"); }
    &Status($status_idle);
}

################################################################################
#
# printChat
#
# Print to chatterbox with proper format and colors
#
sub printChat {
    local($_) = shift;
    chomp;
    return unless ($_);
    my($color) = shift || 'default';
    my($ret)   = shift;
    $ret = 1 unless (defined $ret);
    LINE: {
	# special case for needed a return: and all is quiet
	if (/^and all is quiet\.\.\.$/) {
	    printMessage('and all is quiet...','default',1);
	    last LINE;
	}
	# private messages
	if (s/^\(\d+\) \* (.+) says // || s/^\* (.+) says //) {
	    printMessage("*** (" . ++$msg_num . ") ",'private',1);
	    printMessage("$1",'username');
	    printMessage(' says ','private');
	    $ret = 0;
	    redo LINE;
	}
	# usernames
	if ($ret && s/^<(.+?)>//) {
	    printMessage('<', 'default',1);
	    printMessage("$1",'username');
	    printMessage('>', 'default');
	    $ret = 0;
	    redo LINE;
	}
	# CODE blah /CODE
	if (s/^(.*?)\<CODE\>(.*?)<\/CODE>//i) {
	    my($text,$code) = ($1,$2);
	    printChat("$text",$color,$ret);
	    printMessage("$code",'code');
	    $ret = 0;
	    redo LINE;
	}
	# [blah]
	if (s/^([^\[]*?)\[(.+?)\]//) {
	    my($text,$link) = ($1,$2);
	    printChat("$text",$color,$ret);
	    if ($link =~ s/^(.+)\|(.+)$/$2/) { printMessage("$1",'hidden_link'); }
	    printMessage("$link",'link');
	    $ret = 0;
	    redo LINE;
	}
	# <A HREF=foo>bar</A>
	if (s/^(.*?)<A\s+HREF=(\S+)>(.+)<\/A>//i) {
	    my($text,$link,$linktext) = ($1,$2,$3);
	    printChat("$text",$color,$ret);
	    $link =~ s/[\"\']//g;
	    printMessage("$link",'hidden_link');
	    printMessage("$linktext",'link');
	    $ret = 0;
	    redo LINE;
	}
	# colorize logged in user's name
	if (defined $loggedInUser && s/^(.*?)$loggedInUser//i) {
	    printChat("$1",$color,$ret) if ($1);
	    printMessage("$loggedInUser",'self',$ret);
	    $ret = 0;
	    redo LINE;
	}
	# everything else just print
	printMessage("$_",$color,$ret);
    }
}

################################################################################
#
# updXP
#
# Find user's current XP level and what the next level will be
#
sub updXP {
    &Status('Checking for new XP information...');
    my($level,$xp,$xp2next,$votesleft) = &getFromServer('xp');
    if (!defined $level || $level =~ /^\s*$/) {
	$Progress->delete($ptext);
	$ptext=$Progress->createText(150,10,-text=>"Unable to obtain your XP info");
    } else {
	my($position) = int(( ($xp-$perlmonk_levels{$level}) /
			      ($xp-$perlmonk_levels{$level}+$xp2next)) * 100) ;
	$Progress->delete($prect);
	$prect=$Progress->createRectangle(0,0,$position*3-1,20,
					  -fill    => 'green',
					  -outline => 'green');
	my($XPLabelStr) = "Level: $level, XP: $xp, "
	    . "To next: $xp2next ($position%), Votes left: $votesleft";
	$Progress->delete($ptext);
	$ptext=$Progress->createText(150,10,-text=>$XPLabelStr);
    }
    &Status($status_idle);
}

################################################################################
#
# updUserlist
#
# Updates the userlist listbox
#
sub updUserlist {
    &Status('Checking userlist...');
    my($oldindex) = $Userlist->curselection || "";
    if ($oldindex ne "") { $oldindex = $Userlist->get($oldindex); }
    $Userlist->delete(0,'end');
    my($num_users) = 0;
    foreach (&getFromServer('userlist')) {
	$Userlist->insert('end',"$_");
	$num_users++;
	if (defined $oldindex && $_ eq $oldindex) { $Userlist->selectionSet('end'); }
    }
    $UserlistLabel->configure(-text => "# Users: $num_users");
    printError("Ack!  No one's logged in!") unless $num_users;
    &Status($status_idle);
}

################################################################################
#
# updUsername
#
# Updates the username/password cookie
#
sub updUsername {

    &Status("Updating user information...");

    if (!$userinfo_w) {
	$userinfo_w = $Window->Toplevel(-takefocus=>1,
					-title  => "Update user info");
	# don't allow any resizing of the window
	$userinfo_w->bind('<Configure>' => sub {
	    my($xe) = $userinfo_w->XEvent;
	    $userinfo_w->maxsize($xe->w, $xe->h);
	    $userinfo_w->minsize($xe->w, $xe->h);
	});
	$userinfo_w->withdraw();
	$userinfo_w->transient($Window);

	# setup frames
	my $frame1 =$userinfo_w->Frame()->pack(-side=>'top');
	my $frame2 =$userinfo_w->Frame()->pack(-side=>'top');
	my $frame3 =$userinfo_w->Frame()->pack(-side=>'top');
	my $frame4 =$userinfo_w->Frame()->pack(-side=>'bottom');

	$frame1->Label(
		       -text   => 'Username:',
		       -width  => 20,
		       )->pack(-side=>'left',-fill=>'x');
	$unField = $frame1->LabEntry(
				     -width  => 25,
				     )->pack;
	$frame2->Label(
		       -text   => 'Password:',
		       -width  => 20,
		       )->pack(-side=>'left',-fill=>'x');	
	$pwField = $frame2->LabEntry(
 					 -width    => 25,
 					 -show => '*',
					 )->pack;
	$frame3->Label(
			   -text   => 'Confirm:',
			   -width  => 20,
			   )->pack(-side=>'left',-fill=>'x');
	$confField = $frame3->LabEntry(
 					   -width    => 25,
 					   -show => '*',
					   )->pack;
	
	$frame4->Button (
			 -text     => "Ok",
			 -command=> \&Ok_Click
			 )->pack(-side => 'right',-padx=>5,-pady=>2);
	$frame4->Button(
			    -text     => "Cancel",
			    -command=> sub { $userinfo_w->grabRelease;
					     $userinfo_w->withdraw;
					 }
			    )->pack(-side =>'right',-padx=>5,-pady=>2);
    }
    
    $userinfo_w->Popup;
    $unField->focus;
    $userinfo_w->protocol('WM_DELETE_WINDOW',sub {;}); #handle window 'x' button
    $userinfo_w->grabGlobal;
    
    &Status($status_idle);
}

sub Ok_Click { 
    my ($un,$pw,$co) = ($unField->get,$pwField->get,$confField->get);
    unless (defined $un && defined $pw && defined $co) {
	printError("All fields required. Nothing changed.");
	$userinfo_w->grabRelease;
	$userinfo_w->withdraw;
	return;
    }
    if ($pw ne $co) {
	printError("Password and confirmation did not match. Nothing changed.");
	$userinfo_w->grabRelease;
	$userinfo_w->withdraw;
    } else {
	local($_) = &getFromServer("LOGIN $un $pw");
	if (/^Logged in as (.+)$/) {
	    printMessage("\nLogged in as $1");
	} else {
	    printError("\nLogin failed.");
	}
	$userinfo_w->grabRelease;
	$userinfo_w->withdraw;
     }
}

################################################################################
#
# chooseBrowser
#
# Prompts the user to select the browser executable
#
sub chooseBrowser {
    &Status("Updating browser information...");
    if (!$choosebrowser_w) {
	$choosebrowser_w = $Window->Toplevel(-takefocus=>1,
					     -title  => "Choose browser executable");
	$choosebrowser_w->withdraw();
	$choosebrowser_w->transient($Window);

	$browserfield = $choosebrowser_w->LabEntry(
 					 -label => "Executable:",
 					 -width  => 40,
 					 -labelPack => [-side => 'left' ]
					 )->pack;
	$choosebrowser_w->Button(
			    -text     => "Browse",
			    -command=> sub { my($fsref) = $choosebrowser_w->FileSelect;
					     my($file)  = $fsref->Show;
					     $browserfield->configure(-textvariable=>\$file);
					 }
			    )->pack(-side =>'left',-padx=>5,-pady=>2);
	$choosebrowser_w->Button (
			     -text     => "Ok",
			     -command=> \&chooseBrowser_ok
			     )->pack(-side => 'right',-padx=>5,-pady=>2);
	$choosebrowser_w->Button(
			    -text     => "Cancel",
			    -command=> sub { $choosebrowser_w->grabRelease;
					     $choosebrowser_w->withdraw;
					 }
			    )->pack(-side =>'right',-padx=>5,-pady=>2);
    }
    
    $browserfield->configure(-textvariable=>\$options{'browser'}) if (defined $options{'browser'});
    $choosebrowser_w->Popup;
    $browserfield->focus;
    $choosebrowser_w->protocol('WM_DELETE_WINDOW',sub {;}); #handle window 'x' button
    $choosebrowser_w->grabGlobal;
    
    &Status("$status_idle");
}

sub chooseBrowser_ok { 
    my($potential_browser) = $browserfield->get;
    $potential_browser =~ s/\//\\/g if ($^O =~ /Win32/i);
    unless ($potential_browser) {
	printError("No browser specified.  Nothing changed.");
	$choosebrowser_w->grabRelease;
	$choosebrowser_w->withdraw;
	return;
    }
    if (! -e "$potential_browser") {
	printError("Could not locate browser executable.");
    } else {
	$options{'browser'} = $potential_browser;
     }
	$choosebrowser_w->grabRelease;
	$choosebrowser_w->withdraw;
}

################################################################################
#
# printMessage and printError
#
# Prints an error or message to the chatterbox
#
sub printMessage {
    local($_)  = shift;
    my($color) = shift || 'message';
    my($ret)   = shift || 0;

    # translate escape sequences
    s/\&lt\;/</g;     # <
    s/\&gt\;/>/g;     # >
    s/\&\#091\;/\[/g; # [
    s/\&\#093\;/\]/g; # ]

    # print initial return if requested
    $Chatfield->insert('end',"\n",$color) if ($ret);

    # print rest of message
    $Chatfield->insert('end',$_,$color);
    $Chatfield->see('end');
}
sub printError {
    my($error) = shift;
    $error =~ s/\r*\n//g;
    printMessage("ERROR: $error",'error',1);
}

################################################################################
#
# status
#
# change status line to say something else
#
sub Status {
    my($msg) = shift;
    $Status->configure(-text => $msg);
    $Status->update;
}

################################################################################
#
# save_settings
#
# save color settings
#
sub save_settings {
    for my $option (keys %options) {
	next if $option =~ /browser/;
	$options{$option}=$Chatfield->tagCget($option,-foreground) unless $option eq 'background';
    }
    $options{'background'}=$Chatfield->cget(-bg);
}

################################################################################
#
# reset_settings
#
# reset color settings
#
sub reset_settings {
    foreach(keys %default_options) {
	$Chatfield->tagConfigure($_,-foreground=>$options{$_}) unless $_ eq 'background';
    }
    $Chatfield->configure(-bg => $default_options{'background'});
    save_settings;
}

################################################################################
#
# closeDOSParent
#
# Closes the dos prompt that created this process
#
sub closeDOSParent {
    # This just simply doesn't work.  I'm not sure why.
    return;
    return if ($opt_debug); # debugging info goes to STDOUT!
    if ($^O =~ /MSWin32/i) {
	my($process);
	my($program) = $^X;
	$orig_params =~ s/\-c\S*\b//g;
	my($pgm) = "perl $0 $orig_params";
	eval '
	    use Win32::Process;
	  Win32::Process::Create($process,"$program","$pgm",0,DETACHED_PROCESS,".") ||
	      die Win32::FormatMessage(Win32::GetLastError()) . "\n";
	';
	exit;
    }
}
