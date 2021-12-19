=**************************************
    = Name: Simple RPG V1.02
    = Description:To have fun. You have a pl
    =     ayer wander through a maze fight monster
    =     , collect items, gain experience and go 
    =     up levels. For the most current version 
    =     go to my site at annuna.com/perl5
    = By: Joe Creaney
    =
    = Assumes:Nothing realy just make sure t
    =     o check your inventory because your play
    =     er dosn't start using equipment.
    =
    = Side Effects:I think I got all the bug
    =     s out let me know if you find any.
    =
    =This code is copyrighted and has    = limited warranties.Please see http://w
    =     ww.Planet-Source-Code.com/vb/scripts/Sho
    =     wCode.asp?txtCodeId=469&lngWId=6    =for details.    =**************************************
    
    # Simple Role-Playing game
    # By Joe Creaney
    # mail@annuna.com
    #!/usr/bin/perl -w
    package Player;
    use strict;
    sub new { #player object constructor
    	my $player = {
    		name => $_[0],
    		x=> $_[1],
    		y=> $_[2],
    		lv => $_[3],
    		xp => $_[4],
    		hp => $_[5],
    		mhp => $_[6],
    		wpn => $_[7],
    		arm => $_[8],
    		bag => $_[9]
    		};
    bless $player, 'Player'; 
    return $player;
    }
    sub generate { #player generator
    	my $player;
    	my $name;
    	my $x=1;
    	my $y=1;
    	my $lv=1;
    	my $xp=70;
    	my $hp=12;
    	my $mhp = $hp;
    	my $wpn = 'none';
    	my $armor = 'none';
    	my @stuff = (['club','knife'],
    		['leather']);
    	my $bag = \@stuff;
    	
    	print "What is your name:";
    	$name=<STDIN>; chomp $name;
    	$player = new ($name, $x, $y, $lv, $xp, $hp, $mhp, $wpn, $armor, $bag );
    			
    	return $player;
    	}
    sub heal { #heal function
    	my ($play) = @_;
    	if ($play->{hp} < $play->{mhp}) {
    		$play->{hp}++;
    		}
    	return $play;
    	}
    sub pwep { #prints weapons in bag
    	my ($play) = @_;
    	my $lp;
    	for $lp (0 .. $#{$play->{bag}->[0]}) {
    		print "$lp $play->{bag}->[0][$lp] \n";
    		}
    	}
    	
    sub parm { #prints Armor in bag
    	my ($play) = @_;
    	my $lp;
    	for $lp (0 .. $#{$play->{bag}->[1]}) {
    		print "$lp $play->{bag}->[1][$lp]\n";
    		}
    	}
    sub inv { #inventory function
    	my ($play) = @_;
    	my $in;
    	my $in1;
    	print "$play->{name} is using:\n";
    	print "0 using $play->{wpn}\n";
    	print "1 wearing $play->{arm}\n";
    	print "i Inventory all\n";
    	print "Enter:";
    	$in=<STDIN>;chomp $in;
    	if ($in eq "i") { 
    		pwep($play);
    		print "\n";
    		parm($play);
    		}
    	if ($in eq "0") {
    		pwep($play);
    		print "Wich to use:";
    		$in1=<STDIN>; chomp $in1;
    		($play->{wpn}, $play->{bag}[$in][$in1]) = 
    	($play->{bag}->[$in][$in1], $play->{wpn});
    		}
    	if ($in eq "1") {
    		parm($play);
    		print "Wich to use:";
    		$in1=<STDIN>; chomp $in1;
    		($play->{arm}, $play->{bag}[$in][$in1]) = 
    	($play->{bag}->[$in][$in1], $play->{arm});
    		}
    	
    	return $play;
    	}
    sub newlev { #New level function
    	my ($player) = @_;
    	$player->{lv} = $player->{lv} + 1;
    	$player->{hp} = $player->{hp} + int rand (10)+1;
    	$player->{mhp} =$player->{mhp} + $player->{hp}; 
    	print "$player->{name} went up the level $player->{lv}\n";
    	return $player;
    	}
    sub xpad { #adds Experience after a fight
    	my ($player, $mon) = @_;
    	$player->{xp} = $player->{xp} + $mon->{xpv};
    	if ($player->{xp} > 100 ) {
    		$player->newlev;
    		$player->{xp} = 0
    		}
    	print "$player->{name} got $mon->{xpv} for a total of $player->{xp}\n";
    return $player;
    	}
    #****************************************************************************
    package Monster;
    use integer;
    use strict;
    sub new { #mnonstor constructor
    	
    	my $monster = { 
    		name => $_[0],
    		hd => $_[1],
    		hp => $_[2],
    		dam => $_[3],
    		ac => $_[4],
    		xpv => $_[5],
    		x => $_[6],
    		y => $_[7]
    		};
    	bless $monster, 'Monster';
    	return $monster;			
    	}
    sub generate { #Monster generator
    my ($plv, $mx, $my) = @_;
    my $v;
    my $hp;
    my $mons;
    my @mona;
    my $lp;
    my $rm;
    my $x;
    my @ml = ( 
    	[ "Rat",1,0,2,9,10 ],
    	[ "Kobold",1,0,4,9,10 ],
    [ "Orc",1,0,6,8,15 ],
    	[ "Skelliton",1,0,4,9,10 ],
    [ "Gobblin",1,0,7,4,10 ],
    [ "Hobgobblin",2,0,6,6,15 ],
    	[ "Wolf",2,0,6,5,20 ],
    	[ "Ogre",3,0,7,5,25 ],
    	[ "Troll",3,0,8,4,25 ],
    	[ "Giant",3,0,3,4,8,30 ],
    [ "Dragon!",4,0,10,1,100 ],
    );
    # 0 name,1 Hit dice (Level),2 Hit Points,3 Max dammage,4 Armor Class,5 Expewrience value
    $rm = int rand(4) + ($plv - 1) ;
    if ($rm > 10 )
    {$rm = 10; } 
    for $x (0 .. $#{$ml[$rm]}) {
    	$mona[$x] = $ml[$rm][$x];
    }
    $lp=0;
    for $x (1 .. $mona[1]) { #Choose monster.
    $mona[2] += int rand(6) + 1;
    	
    	}
    $mons = new ( @mona, $my, $mx );
    $lp++; 
    return $mons;
    	}
    #**************************************************************************\
    package Treasure; #treasure hold for dropping and picking up.
    my @istore; #where treasure on the floor is strored.
    sub new { #Item constructor
    my $item = {
    	name => $_[0],
    	x => $_[1],
    	y => $_[2],
    tpe => $_[3]
    	};
    bless $item, 'Treasure';
    return $item;	
    }
    sub pickup {
    	my $adder = 0;
    	my ($play, $map) = @_;
    	my $item;
    	
    	$item = getitem ($play);
    	push @{$play->{bag}->[$item->{tpe}]}, $item->{name};
    	killitem ($play);
    	$map->{level}->[$play->{y}][$play->{x}] = ".";
    	return $play, $map;
    	
    }
    sub drop {
    my $drp;
    my $drp1;
    my $inpt;
    my $item;
    my $loop;
    my @tempbag;
    	my ($play, $map) = @_;
    	print "0 Weapons \n";
    	print "1 Armor\n";
    	print "Which to drop:";
    	$drp=<STDIN>; chomp $drp;
    	if ($drp == 0) {
    		$play->Player::pwep();
    		print "Which to drop:";
    		$inpt=<STDIN>;chomp;
    		}
    	if ($drp == 1) {
    		$play->Player::parm();
    		print "Which to drop:";
    		$inpt=<STDIN>;chomp;
    		}
    if ($play->{bag}->[$drp][$inpt] eq 'none') {
    	print "Cant drop nothing!\n";
    	return $play, $map;
    	}
    if ($inpt > $#{$play->{bag}->[$drp]} ) {
    	return $play, $map;
    	}
    $map->{level}->[$play->{y}][$play->{x}] = "T";
    $item = new ($play->{bag}->[$drp][$inpt],
    	$play->{x},
    	$play->{y},
    	$drp);
    putitem ($item);
    for $loop ($drp .. $#{$play->{bag}->[$drp]}) {
    	$play->{bag}->[$drp][$loop] = 
    	$play->{bag}->[$drp][$loop+1];
    	}
    pop @{$play->{bag}->[$drp]};
    	
    return $play, $map;
    }
    sub putitem { #stores items on the floor in global var
    my ($item) = @_;
    my $n = 0 ;
    my $flag = 0;
    do {		
    	if (($istore[$n] == undef) or 
    	 ($istore[$n]->{name} eq " ")) {
    		$istore[$n] = $item;
    		
    		}
    	$n++;
    	} until ($flag = 1);
    }
    sub getitem { #picks up items off the floor and takes them out of the floor list.
    my ($char) = @_;
    my $item;
    my $l;
    for $l (0 .. @istore) {
    		if (( $char->{x} == $istore[$l]->{x} ) || 
    	 	 ( $char->{y} == $istore[$l]->{y} )) {
    			$item = $istore[$l];
    			}
    		}
    	
    	
    return $item;
    }
    sub killitem { #takes items off the storage variable.
    my ($char) = @_;
    my $l;
    	foreach $l (0 .. @istore) {
    		if (( $char->{x} == $istore[$l]->{x} ) and 
    	 	 ( $char->{y} == $istore[$l]->{y} )) {
    			$istore[$l]->{name} = " ";
    			}
    	}
    	
    }
    sub farm { #Armor chooser
    	my ($xpb) = @_;
    my $har;
    my $chs;
    $chs = int(rand(100)) + $xpb;
    	if ($chs > 100) {
    		$har = 'plate';
    		return $har;
    		}
    if ( $chs > 75) {
    $har = 'chain mail';
    		return $har;
    		}
    if ($chs > 40) {
    $har = 'ring mail';
    		return $har;
    		}
    $har = 'leather'; 
    return $har;
    }
    sub fwep { #Weapon Chooser
    my ($xpb) = @_;
    my $hwp;
    my $chs;
    $chs = int(rand(100)) + $xpb;
    	if ($chs > 100) {
    		$hwp = 'Ax'; 
    		return $hwp;
    		}
    if ($chs > 80) {
    		$hwp = 'gladius'; 
    		return $hwp;
    		}
    if ($chs > 60) {
    		$hwp = 'spear';
    		return $hwp;
    		}
    if ($chs > 30) {
    $hwp = 'club';
    		return $hwp;
    		}
    	$hwp = 'knife'; 
    	return $hwp;
    }
    sub gen { #If treasure is chosen weapons or armor.
    my ($play, $monst, $map) = @_;
    my $tnum;
    my $fnd;
    my $item;
    my $find;
    $tnum = int(rand(100))+$monst->{xpv};
    	if ( $tnum > 75 ) {
    		$find = int(rand(2));
    		if ($find == 0) {
    			$fnd = fwep ($monst->{xpv});
    			}
    		if ($find == 1) {
    			$fnd = farm ($monst->{xpv});
    			}
    	$item = new ($fnd, $monst->{x}, $monst->{y}, $find);
    	$map->{level}->[$monst->{y}][$monst->{x}] = "T";
    	putitem ($item);
    	} else {print "Nothing found\n";}
    return ($play, $map);
    }
    #****************************************************************************
    package Monstore; #Where monsters are stored and gotten if the player runs.
    use strict;
    my @mstore;
    sub putmon { 
    my ($mon) = @_;
    my $n = 0;
    my $flag = 0;
    print "* $mon->{name} \n";
    do {		
    	if (($mstore[$n] == undef) or ($mstore[$n]->{name} eq " ")) {
    		$mstore[$n] = $mon;
    		$flag = 1;
    		}
    	$n++;
    	} until ($flag = 1); $n--;
    print "You ran from the $mstore[$n]->{name}\n";
    }
    sub getmon {	
    my ($y, $x) = @_;
    my $l;
    foreach $l (0 .. @mstore) {
    	if (( $x == $mstore[$l]->{x} ) and 
    	 ( $y == $mstore[$l]->{y} )) {
    print "* $mstore[$l]->{name} \n";
    			return $mstore[$l]; 
    				
    			}
    		}
    print "* $mstore[$l]->{name} \n";
    return $mstore[$l]; 
    }
    sub kilmon {
    my ($y, $x) = @_;
    my $l;
    foreach $l (0 .. @mstore) {
    	if (( $x == $mstore[$l]->{x} ) and 
    	 ( $y == $mstore[$l]->{y} )) {
    			$mstore[$l]->{name} = " ";
    			
    			
    			}
    		}
    	}
    #***************************************************************************
    package Map;
    use strict;
    sub new { #Generates the map and makes it an object
    my $map;
    my @map1 = (
    [ qw (# # # # # # # # # # # # # # # # # # # # #)],
    [ qw (# . . . . # . . # . . . # # . . . . . . #)],
    [ qw (# # # # . # . # # . # . # . . . # # # . #)],
    [ qw (# . . . . . . . . . # . # . # . # . # . #)],
    [ qw (# . . # # . # # # . . . # . # . . . . . #)],
    [ qw (# . . # . . . # # . # . # . # # . # # # #)],
    [ qw (# # . # # . . # . . # . . . . . . . . . #)],
    [ qw (# # . . # . . . . . # # # # # . # # # . #)],
    	[ qw (# . . # # # # # # . . . . . . . . # . . #)],
    	[ qw (# . # # # . # # . . # # . # # # . # # . #)],
    	[ qw (# . . . . . # . . # # . . . # . . . # # #)],
    [ qw (# # # # # # # # # # # # # # # X # # # # #)],
    	);
    	$map = {
    		level => \@map1
    		};
    bless $map, 'Map';
    return $map;
    	}
    	
    #*************************************************************************
    package Main;
    #!/usr/bin/perl -w
    use strict;
    use integer;
    my $author = 'Joe Creaney';
    my $version = '1.02';
    my $name;
    my $map;
    my $count;
    my $healcnt;
    my $player;
    my $m = " ";
    my %weapon = ('none' => 1,
    	'knife' => 2, 
    'club' => 4,
    'spear' => 6,
    'gladius'=> 8,
    'ax'=> 10);
    my %armor = ('none'=> 10, 
    'leather'=> 9, 
    'ring mail' => 7,
    'chain mail' => 5, 
    'plate'=> 3 );
    sub helpme {
    	print "Help function:\n";
    	print "n - north\n";
    	print "s - south\n";
    	print "e - east\n";
    	print "w - west\n";
    	print "i - inventory\n";
    	print "d - drop\n";
    	print "p - pick-up\n";
    	print "h - help\n";
    	print "In Combat:\n";
    	print "f - fight\n";
    	print "r - run\n";
    	}
    sub death {
    	print "$_[0]->{name} You are dead GAME OVER";
    	exit;
    	}
    sub thaco {
    	my $gen = @_;
    	my $tmp;
    	if ($gen == 1) {$tmp=19};
    	if ($gen == 2) {$tmp=18};
    	if ($gen == 3) {$tmp=17};
    	if ($gen == 4) {$tmp=16};
    	return $tmp;
    	}
    sub atk {
    	my ($ac, $lv) = @_;
    	my $flag;
    	my $thaco;
    	my $thn;
    	my $d20;
    	$thaco = thaco ($lv);
    	$thn = ($thaco - $ac);
    	$d20 = int rand(20)+1;
    	if ( $d20 > $thn ) {
    	$flag=1;
    	} else {
    		$flag=0;
    		}
    	return $flag;
    	
    	
    	}
    sub monat {
    	my ($play, $mon) =@_;
    	my $dam=0;
    	my $flag;
    	$flag = atk ( $armor{$player->{arm}}, $mon->{hd} );
    	if ($flag == 1) {
    		print "The $mon->{name} hit ";
    		$dam = int rand($mon->{dam})+1;	
    		print "and did $dam points \n";
    		$play->{hp}=$play->{hp} - $dam;
    		} 
    	if ($flag == 0) { 
    		print "The $mon->{name} Missed!\n"; 
    			}
    return $play;
    	
    	}
    sub playat {
    	my ($play, $mon) = @_;
    	my $dam=0;	
    	my $flag;
    	$flag = atk ($mon->{ac}, $player->{level} );
    	if ($flag == 1) {
    		print "You Hit!\n";
    		$dam = int rand($weapon{$play->{wpn}})+1;
    		$mon->{hp} = $mon->{hp} - $dam;
    		print "$dam ponts of dammage\n";
    			}	
    	if ($flag == 0 ) { 
    			print "You missed \n";
    			}
    return $mon;
    	}
    sub fight {
    	my ($play, $monst, $map) = @_;
    	
    	my $in;
    	my $tx;
    	my $ty;
    		
    	while ($monst->{hp} > 0) {
    		print "You see a $monst->{name} \n";
    		dmap ($play, $map);
    		print "f to fight r to run:";
    		$in =<STDIN>; chomp $in;
    		if ($in eq "r") {
    			($play) = monat ($play, $monst);
    			Monstore::putmon ($monst);
    			return $play, $map;
    			}
    		if ($in eq "f") {
    			$monst = playat ($play, $monst);
    			$play = monat ($play, $monst);
    			print "You have $play->{hp} it has $monst->{hp} \n";
    			}
    		if ($play->{hp} < 1 ) {
    		return $play, $map;
    		}
    	} 
    	$tx=$monst->{x};
    	$ty=$monst->{y};
    	$map->{level}->[$ty][$tx] = ".";
    	$play->xpad($monst);
    	($play, $map) = Treasure::gen ($play, $monst, $map);
    return $play, $map;
    	}
    sub move { #movement and movement admin function.
    	my ($mi, $play, $grid) = @_;
    	my $rmon;
    	my $monst;
    	my $item;
    	my $xm = $play->{x};
    	my $ym = $play->{y};
    	my %mov = ( n => [ 0, -1],
    	 s => [ 0, 1], 
    	 e => [ 1, 0], 
    	 w => [ -1, 0],
    	 q => [ 0, 0],
    	 i => [ 0, 0],
    		 p => [ 0, 0],
    		 d => [ 0, 0],	
    		 h => [ 0, 0]);
    $xm = $xm + $mov{$mi}[0];
    $ym = $ym + $mov{$mi}[1];
    	
    	$rmon = int(rand(6))+1;
    if ($grid->{level}->[$ym][$xm] eq "#") { #Check if ran into wall.
    		print "Can't go that way \n";
    return $play; 
    	}
    if ($grid->{level}->[$ym][$xm] eq "m") { #check if monster is on map.
    		($monst) = Monstore::getmon ($ym, $xm);
    		Monstore::kilmon ($ym, $xm);
    		($play, $grid) = fight ($play, $monst, $grid);
    return $play; 
    	}
    if (($mov{$mi}[0] != 0) || ($mov{$mi}[1] != 0)) {
    if ( $rmon == 1 ) { #Check for rnd monsters.
    		$monst = Monster::generate($play->{lv}, $ym, $xm);
    		print "$monst->{name} $monst->{hp} \n";
    		$grid->{level}->[$ym][$xm] = "m";	
    		($play, $grid) = fight ($play, $monst, $grid);
    	return $play;
    	}
    }
    #move player to next space.
    $play->{x} = $xm;
    $play->{y} = $ym;
    if ($grid->{level}->[$ym][$xm] eq "X") { #check to see if found objective.
    	die "You win!!! \n";
    	$mi = "q";
    	return $play;
    	}
    if ($grid->{level}->[$ym][$xm] eq "T") { #if there is an item on the floor.
    	($item) = Treasure::getitem ($play);
    	print "You see $item->{name} on the floor\n"; 
    	
    	}
    if ($mi eq "i") { #inventory choice.
    	$play->inv();
    	return $play;
    	}
    if ($mi eq "d") { # drop items.
    	($play, $grid) = Treasure::drop ($play, $grid);
    	return $play;
    	}
    if ($mi eq "p") { #to pick up items from floor.
    	if ($grid->{level}->[$ym][$xm] eq "T") {
    		 ($play, $grid) = Treasure::pickup ($play, $grid);
    			} else {
    			print "Nothing to pick up\n";
    			}
    	return $play;
    	}
    if ($mi eq "h") { #hepl!
    	helpme;
    	return $play;
    	}
    if ($mi eq "q") {
    	 return $play;
    	}
    	
    print "$play->{name} has $play->{hp} \n";
    return $play;
    }
    sub dmap { #draws the map 3x3 grid.
    	my ($char, $grd) = @_;
    my $l;
    my $l1;
    for $l (($char->{y})-1 .. ($char->{y})+1) {
    for $l1 (($char->{x})-1 .. ($char->{x})+1) {
    if (($l == $char->{y}) and
    ($l1 == $char->{x})) 
    {print "@"; }
    				else 
    { print "$grd->{level}->[$l][$l1]"; }
    			} print "\n";
    		}
    }
    print "Welcom to the game. \n";
    print "Written by $author version $version. \n";
    print "h for help *hint check inventory* \n \n";
    $map = Map::new();
    $player = Player::generate();
    dmap ($player, $map);
    while ( $m ne "q" ) {
    print "Which way:";
    $m=<STDIN>; chomp $m;
    if ( $m ne "q" ) {
    if ($healcnt == 1) {
    	$player->heal();
    	}
    $player = move ($m, $player, $map);
    dmap ($player, $map);
    $count++;
    $healcnt = $count % 3; 
    print "$player->{name} $player->{hp}\n";
    if ($player->{hp} < 1 ) {death ($player);}
    } 
    } 
    print "You quit"; 