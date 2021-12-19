#!usr/bin/perl -w
$wizard=50;
$wizardl=1;
$gold=5;
$damage=2;
print"Welcome to Wizard!  Type your wizard's name...";
$name=<STDIN>;
print "Welcome $name to Wizard.  You are now in the Merlins castle.  You are surrounded  by other mages and disscussion flows back\n";
print "and forth in the room.   Suddenly Merlin stands up in front of all and says, I need a powerful man to go out and slay all the monsters that have been plauging my land.\n";
print "Which one of you can raise to the challenge.  Slowly all those in the room rise, including yourself.\n\n";
print "Good, good merlin utters.  Then be off.\n\n\n";
sub die{
if($wizard==0){
print "Thanks for playing";
die;
}
sub toad{
my $toad=int (rand 4)+1;
if($toad<=$wizardl){
print "The goblins have been zapped into toads!\n";
$gold=$gold+3;
print "good job you got 3 gold!\n";
&freedale
}elsif($toad>$wizardl){
print "your spell has failed! -2 health\n";
$wizard=$wizard-2;
&battle;
}
	}
sub battle{
my $goblin=12;
print "What do you want to do...press 1 for spells or 2 for combat\n";
$choice=<STDIN>;
if($choice==1){
	&toad;
}elsif($choice==2){
do{
$goblin=$goblin-$damage;
$wizard=$wizard-2;
print "wizard health: $wizard\t";
print "goblin health: $goblin\n";
}until($goblin==0 || $wizard==0);
if($wizard>0){
print "good job you got 3 gold!\n";
$gold=$gold+3;
&freedale;
}elsif($wizard<=0){
 &die;
}
	}
		}

sub forest {
	print "The forest is dark and smells of old mold and rotton fish.  The foul waters have turned this once peaceful place in to a\n"; 	print "dark evil place.\n";
	my $gobbo=int (rand 6)+1;
			if ($gobbo>=5){
		print "goblins attack\n";
		&battle;
		}elsif($gobbo!=5 || $gobbo!=6){
		print "There are no monsters here now.\n";
		&freedale
		}
	}

sub mountain {
	print "Its pretty cold up here, but from this altitude you can see the whole town below you.\n"; 
	print "Although it is clear that you are not the first one here!\n";
	my $gobbo=int (rand 6)+1;
			if ($gobbo>=4){
		print "goblins attack\n";
		&battle;
		}elsif($gobbo!=4 || $gobbo!=5 || $gobbo!=6){
		print "There are no monsters here now.\n";
		&freedale
		}
	}

sub dungeon {
	print "There is a heavy feeling in the air, it's very dark and musty down here.\n"; 
	print "You can see clear signs that goblins have been here, and maybe worse things!\n";
	my $gobbo=int (rand 6)+1;
			if ($gobbo>=3){
		print "goblins attack\n";
		&battle;
		}elsif($gobbo!=3 || $gobbo!=4 || $gobbo!=5 || $gobbo!=6){
		print "There are no monsters here now.\n";
		&freedale
		}
	}
sub shop {
	print "Hello welcome to freedale market may I help you?\n";
	print "type 1 for healing..............................................10 gold\n";
	print "type 2 for daggers.............................................15 gold\n";
	print "type 3 for magic wands.....................................30 gold\n";
	$shop=<STDIN>;
	if($shop==1){
		if($gold<10){
		print "you don't have enough gold\n";
		&freedale;
		}
	print "Thank you\n";
	$gold=$gold-10;
	$wizard=50;
	&freedale;
		
	}elsif($shop==2){
		if($gold<15){
		print "you don't have enough gold\n";
		&freedale;
		}
	print "thank you\n";
	$gold=$gold-15;
	$damage=3;
	&freedale;
	}elsif($shop==3){
		if($gold<15){
		print "you don't have enough gold\n";
		&freedale;
		}
	print "thank you\n";
	$gold=$gold-30;
	$wizardl=$wizardl+1;
	}
}
sub stat{
	print "health:\t$wizard\n";
	print "magic level:\t$wizardl\n";
	print "gold:\t$gold\n";
	&freedale
}
sub freedale{
print "you are in freedale\n";
print "where do you want to go\n";
print "type 1 for the forest, 2 for the mountain, or 3  for the dungeon\n 4 for the shop or 5 to view stats.\n ";
$room=<STDIN>;
if($room==1){
&forest;
}elsif($room==2){
&mountain;
}elsif($room==3){
&dungeon;
}elsif($room==4){
&shop
}elsif($room==5){
&stat
}
}
}
&freedale
