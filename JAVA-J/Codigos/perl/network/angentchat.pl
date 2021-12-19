#!/usr/local/bin/perl -w
#
# AgentChat program
# by t0mas@netlords.net
#
# Version 
# 0.01    Initial release
# 0.02    Changed character loading and added XP checks
# 0.03    Added Babelfish support


# Modules
use strict;
use Win32::OLE;
use Win32::OLE::Variant;
use PerlMonksChat;

# User set vars
use vars qw($user $passwd $sound $checkXP @Characters %Monks);
use vars qw($debug $translate);

####################################################################
# Config section start

# Username and password for PerlMonks (comment out for AM)

$user="your_monk_id";
$passwd="your_monk_passwd";

# Comment out if you don't want xp checks... 

$checkXP="Yup";

# Comment out if you prefer to have a quiet office... 

$sound="Yup";

# Use babelfish to traslate the chat from English to other language
# Babelfish supports French, German, Italian, Portuguese, and 
# Spanish. You must have a text-to-speech engine installed that 
# supports the language of your choise, or the Agents will remain 
# silent (the ballon text will show anyway)
#
# Comment out if you prefer English... 

#$translate='German';

# Debug mode (Offline mode for testing...)
# Comment out if you want real live chat... 

#$debug="Yup";

# Add entries to array to load all the characters you want
# (A fine source of new characters is the MS Agent Ring:
# http://www.msagentring.org/agentchars.html)

@Characters = (
	# Name and animation file (full path or file name if, file is
	# in the default directory, usually %winDir%\msagent\chars)
	{name => 'Merlin', file => 'merlin.acs',},
	{name => 'Peedy',  file => 'peedy.acs',},
	{name => 'Genie',  file => 'genie.acs',},
	{name => 'Robby',  file => 'robby.acs',},
);

# Add entries to hash to make a given monk always pop up as the 
# same character

%Monks = (
	#Monk			# Character to use
	root		=>	'Genie',  # Because he's everywhere
	merlyn		=>	'Merlin', # Because he's a wizard (P+++++)
	chromatic	=>	'Robby',  # Because he shines like chrome
	Corion		=>	'Peedy',  # Because he likes Peedy better... 
);

# Config section end
####################################################################

# Non user vars
use vars qw(@newchat $i $Char $RandomChar $Action $monk %CharNums);
use vars qw($Agent $SpeechLine $PMChat $personalMsg $chatLine);
use vars qw($xpCnt $oldXP $xpChange %Languages $VERSION);

# Version
$VERSION='0.03';

# Languages hash (map language to speech engine)
%Languages = (
	English => 0x0409,
	French => 0x040C, 
	German => 0x0407, 
	Italian => 0x0410, 
	Portuguese => 0x0816, 
	Spanish => 0x0C0A,
);

# Require WWW::Babelfish if we shall translate
if ($translate) {
	if (! eval "require WWW::Babelfish") {
		die "It seems like the Babelfish " .
			"has slipped out of your ear!";
	}
}

# Initialize OLE
Win32::OLE->Initialize(Win32::OLE::COINIT_MULTITHREADED);

# Create a new agent OLE object
$Agent = Win32::OLE->new('Agent.Control.2')
	or die Win32::OLE->LastError();
$Agent->{Connected} = Variant(VT_BOOL, 1);

# Loop all defined Characters
for $i (0..$#Characters) {
	# Load characters
	$Agent->Characters->Load($Characters[$i]{name},
		$Characters[$i]{file})
	or die Win32::OLE->LastError();

	# Insert refs in hash
	$Characters[$i]{agent} = $Agent->Characters(
		$Characters[$i]{name});

	# Turn off sound effects if we don't want sound
	$Characters[$i]{agent}->{SoundEffectsOn} = Variant(VT_BOOL, 0)
		unless ($sound);

	# Set language
	if ($translate && $Languages{$translate}) {
		$Characters[$i]{agent}->{LanguageID}=$Languages{$translate} 
			if defined $sound;
	} else {
		$Characters[$i]{agent}->{LanguageID}=$Languages{English} 
			if defined $sound;
	}

	# Add to Num hash 
	$CharNums{$Characters[$i]{name}} = $i;
}

# Do some startup animation, using first character [0]
$SpeechLine=&addSpeechTags("Welcome to Agent Chat $VERSION");
$Characters[0]{agent}->Show;
$Characters[0]{agent}->Play("Greet");
$Characters[0]{agent}->Speak($SpeechLine);
sleep(5);
$Characters[0]{agent}->Hide;
sleep(5);

# Create a PM chat object
$PMChat=PerlMonksChat->new();
$PMChat->add_cookies;

# Login
if ($user && $passwd) {
	$PMChat->login($user, $passwd);
}

# Set xpCnt to 4 to get XP on first run
$xpCnt=4;

# Eternal loop (use ctrl+c to exit program)
while (1) {

	# Get new chat lines	 
	@newchat=($debug)?&debugMessages():$PMChat->getnewlines();

	# Check for xp changes every 60 seconds
	if ($checkXP && ($user && $passwd) && (++$xpCnt == 5)) {

		# Call sub
		$xpChange=&getXP();

		# Put first in @newchat if any changes
		unshift(@newchat,$xpChange) if ($xpChange);

		# Reset getXp counter
		$xpCnt=0;
	}

	# Somebody said something
	if (@newchat) {

		# Random character
		$RandomChar=int(rand(@Characters));

		# Speak each line
		foreach $chatLine (@newchat){

			# Reset personal message flag
			$personalMsg=0;	

			# Find out who said what
			if ($chatLine=~/^<([^>]+)>/) {
				# /msg message
				$monk=$1;	
			} elsif ($chatLine=~/^([^*][^\s]+)/){
				# /me message (doesn't handle spaces in names...)
				$monk=$1;	
			} elsif ($chatLine=~/^\*\s*(.+)\ssays/){
				# Personal message (* nnn says )
				$monk=$1;
				$personalMsg=1;
			} elsif ($chatLine=~/^\*/){
				# The Xp info (* You gained...) is considered personal
				$monk="root";
				$personalMsg=1;
			} else {
				$monk="_FIXME_PLEASE_";	
			}

			# Find out if monk has a predefined character
			if ($Monks{$monk}) {
				# Character for monk
				$Char=$CharNums{$Monks{$monk}};
			} else {
				# Character for other monks
				$Char=$RandomChar;
			}

			# Hide any visible character and wait for the to hide
			# (Agents run multi-threaded...)
			for $i (0..$#Characters) {
				if ($Characters[$i]{agent}->Visible &&
					$Characters[$i]{name} ne 
					$Characters[$Char]{name}) {

					# Hide character and make new character wait
					$Action=$Characters[$i]{agent}->Hide;
					$Characters[$Char]{agent}->Wait($Action);
				}
			}

			# Format for speech and maybe translate (kind of...)
			$SpeechLine=&addSpeechTags($chatLine);

			# Show character if not visible
			if (! $Characters[$Char]{agent}->Visible) {
				$Characters[$Char]{agent}->Show;
			} else {
				$Characters[$Char]{agent}->Play("RestPose");
			}

			#
			# Play some animations:
			#  

			# 1. Based on $personalMsg flag
			$Characters[$Char]{agent}->Play("Announce")       
				if ($personalMsg==1);

			# 2. Based on emoticon (smiley) at end of lines
			$Characters[$Char]{agent}->Play("Sad")       
				if ($chatLine=~/['8:;][-~^]*[(cC{]$/);   # :-( 
			$Characters[$Char]{agent}->Play("Pleased")   
				if ($chatLine=~/['8:;][-~^]*[)D>Pb}]$/); # :-) 
			$Characters[$Char]{agent}->Play("Confused")  
				if ($chatLine=~/['8:;][-~^]*[\/]$/);     # :-/ 
			$Characters[$Char]{agent}->Play("Uncertain") 
				if ($chatLine=~/['8:;][-~^]*[I]$/);      # :-I 
			$Characters[$Char]{agent}->Play("Alert")     
				if ($chatLine=~/['8:;][-~^]*[O]$/);      # :-O 
			$Characters[$Char]{agent}->Play("Surprised") 
				if ($chatLine=~/['8:;][-~^]*[o]$/);      # :-o

			# 3. Based on acronym
			$Characters[$Char]{agent}->Play("Pleased") 
				if ($chatLine=~/^lol$/i);     # Laughing Out Loud
			$Characters[$Char]{agent}->Play("Pleased") 
				if ($chatLine=~/^rotfl$/i);   # Rolling On The Floor 
								   # Laughing 

			# Say message
			$Characters[$Char]{agent}->Speak($SpeechLine);

			# Log to STDOUT
			print "$Characters[$Char]{name} ($monk): $chatLine\n";

			# Pause to wait for user to read message
			sleep(5);
		}

		# Keep character onscreen awhile after speaking
		sleep(5);

		# Hide last character 
		$Characters[$Char]{agent}->Hide;
	}

	# Sleep a while (10 seconds to make vroom happy :)
	sleep(10);
}

####################################################################
sub getXP {

	#
	# Get users XP
	#

	my $xpMsg;
	my $xpDiff;

	# Not in debug mode
	return undef if ($debug);

	# Get xp from PM
	my %XP=$PMChat->xp;

	# Check if we've got something
	if (%XP) {

		# Check for first time use
		if ($oldXP) {

			# Find out diffrence (if any)
			$xpDiff=$XP{xp}-$oldXP;
			if ($xpDiff>0) {
				# Somtimes you win...
	    		$xpMsg = 
"* You gained $xpDiff experience point(s). :)";
			} elsif ($xpDiff<0) {
				# somtimes you lose...
	    		$xpMsg =
"* Ack! You lost $xpDiff experience point(s). :(";
			} else {
				# No changes
				$xpMsg = undef;
			}
		} else {
			# Print xp info first time
	    		$xpMsg =
"* You have $XP{xp} XP points and $XP{xp2nextlevel} points to go until" .
" the next level. You have $XP{votesleft} votes left today."; 
		}

		# save oldXP
		$oldXP=$XP{xp};

	} else {
		$xpMsg = 
"* Could not get your XP nodelet. :I";
	}

	# Return xp message
	return $xpMsg;
}

####################################################################
sub addSpeechTags {

	#
	# A try to make message speakable.
	#

	# Receive text as parameter
	my $textToTag = shift || "";

	# Var to hold the babelfish object and the translated text
	my $fish;
	my $tempText;

	# Remove first * from personal messages
	$textToTag =~ s/^\*\s*//; 

	# Change <monk> msg ---> monk says msg
	$textToTag =~ s/^<([^>]+)>/$1 says/;

	# Remove emoticon (smiley) at end of lines
	$textToTag =~ s/['8:;][-~^]*[)(cC{D>\/Pb}IOo]$//; 

	# Try to translate
	if ($translate) {
		if ($ENV{HTTP_proxy}) {
			$fish = new WWW::Babelfish(
				'agent' => 'Mozilla/8.0',
				'proxy' => $ENV{HTTP_proxy});
		} else {
			$fish = new WWW::Babelfish(
				'agent' => 'Mozilla/8.0');
		}

		if (defined($fish)) {
			$tempText = $fish->translate(
				'source' => 'English',
				'destination' => $translate,
				'text' => $textToTag,
				'delimiter' => "\n\t");

			$textToTag=$tempText if defined($tempText);
		}
	}

	# Emphasizes _word_ types of words
	$textToTag =~ s/\s_([^_\s]+)_\s/ \\Emp\\$1 /;

	# Change chr to pronounsables 
	# TODO - Think this over a bit first...
#	$textToTag =~ s/-/\\MAP="minus"="-"\\/;
#	$textToTag =~ s/\+/\\MAP="plus"="\+"\\/;
#	$textToTag =~ s/\*/\\MAP="times"="\*"\\/;
#	$textToTag =~ s/\//\\MAP="divided by"="\/"\\/;
#	$textToTag =~ s/\$/\\MAP="dollar"="\$"\\/;
#	$textToTag =~ s/_/\\MAP="underline"="_"\\/;
#	$textToTag =~ s/{/\\MAP="left curly bracket"="{"\\/;
#	$textToTag =~ s/}/\\MAP="right curly bracket"="}"\\/;
#	$textToTag =~ s/[/\\MAP="left square bracket"="["\\/;
#	$textToTag =~ s/]/\\MAP="right square bracket"="]"\\/;

	# Return tagged text
	return $textToTag;

}

####################################################################
sub debugMessages {

	#
	# A list of common? phrases from the chatterbox
	#

	# Return list
	return ("merlyn wanders off to do some teaching",
			"<KM> guten morgen folks",
			"kudra is just a sugar mouse in the rain...",
			"<merlyn> {grin}",
			"<le> hi KM"
			); 

}

####################################################################
__END__


=head1 NAME

AgentChat - Chatterbox client using Microsoft Agents

=head1 DESCRIPTION

AgentChat is a chatterbox client. It uses small animated characters 
to communicate the contents of the chatterbox to the astonished 
user.These characters, called Agents, are ActiveX components that 
can be controled by the OLE interface.

WARNING. This software works only on an operating system called 
Microsoft Windows (namly the 95,98,NT, and 2000 flavours). Besides 
this obscure OS you will need to download and install Agents from 
the Microsoft Agent Home at 
http://msdn.microsoft.com/workshop/imedia/agent/default.asp. 
And of cause Perl.

I shamelessly admit that I use a Microsoft OS (MOS). As a MOS user 
I am used to limitations and I know how to reboot my computer. Any 
given RealOS(tm) let the user expand his desktop to a number of 
virtual screens and thus being able to run many space-consuming 
applications. You can have your IDE in one screen, a word processor
in another and your chatterbox client in a third.

As A MOS user I am forced to minimize my IDE to document in my word 
processor and the mimimize my word processor to follow the chat at 
PerlMonks. Thus a MOS user spends half of his/her days minimizing 
and maximizing windows. The other half they chat at PerlMonks.

As any Boss can imagine, having your workers minimizing and 
maximizing all day long, have severe impact on productivity. This 
situation must be delt with with the highest priority. An easy 
solution would be to change the OS to one of those 
virtual-multi-screened-super-stable-free-of-cost OS'es, but that 
would be such a waste of invested funds, as people actually have to 
BUY and pay MONEY for a MOS. So the problem needed to be solved in 
a different way...

AgentChat solves one of the minimize/maximize problems, the 
catterbox one.

Given that you have sucessfully installed a MOS, Microsoft Agent 
and Perl, the fun can begin. Head over to the Config section at 
the top of the program and follow the instructions there to 
configure AgentChat. After you are done, start it up and follow 
the action...

The first thing that's supposed to happen is that a wizard pops up 
on your screen, bows and welcomes you to AgentChat. He will then 
dissapear. A few seconds later another character (or the same one , 
depending on your configuration) pops up and reads the catterbox 
messages. When it's done it will too dissapear, just to pop up again
when a new message has been posted to the chatterbox. You can 
configure AgentChat to output the messages using syntetic speech 
and/or text boxes like in the comics. All this automagically.

As the characters pop up, say a message and dissapears they sort of 
maximize/minimize themeselves, thus releaving the MOS user of one 
heavy task during his/her work hours. 

The MOS users will now only have to do their maximize/minimize 
routines when they want activly participate in chats, using a 
chatterbox of their choise to enter chat messages. This situation 
is addressed in section FUTURE DEVELOPEMT.

Have Fun :) 

=head1 SEE ALSO

L<Win32::OLE>, L<Win32::OLE::Variant>, L<PerlMonksChat>

=head1 BUGS

Probably a few...  

=head1 FUTURE DEVELOPMENT

=over 4

=item *

Input possibilities, either by speech or keyboard

=back

=head1 DISCLAIMER

I do not guarantee B<ANYTHING> with this package. If you use it you
are doing so B<AT YOUR OWN RISK>! I may or may not support this
depending on my time schedule...

=head1 AUTHOR

t0mas@netlords.net

=head1 COPYRIGHT

Copyright 2000, t0mas@netlords.net

This package is free software; you can redistribute it and/or
modify it under the same terms as Perl itself.

