  =**************************************
    = Name: HI HO Cherry-O!
    = Description:Children's game of Hi-Ho C
    =     herry-0!
    = By: wv-perlguy
    =
    = Inputs:Colors. Type in the colors you 
    =     want to use
    =
    = Returns:Plays out the game (since ther
    =     e is no strategy), it just goes...
    =
    = Assumes:Enter the colors on the comman
    =     d line with the program. Example: HIHO.p
    =     l RED GREEN BLUE
    =
    =This code is copyrighted and has    = limited warranties.Please see http://w
    =     ww.Planet-Source-Code.com/vb/scripts/Sho
    =     wCode.asp?txtCodeId=277&lngWId=6    =for details.    =**************************************
    
    #!/usr/local/bin/perl
    use strict;
    # give the command line args a slightly nicer name
    my @players = @ARGV;
    if (@players <= 1) { # array used in scalar context
    print "Not enough players: round up some friends and try again.\n";
    exit(0);
    }
    # This holds the number of cherries, per player. The key of the hash
    # is the player's color (contained in the @players array.)
    my %tree;
    # Initialize each player's tree with 10 cherries.
    map { $tree{$_} = 10; } @players;
    # Keep track of the current player, starting with a random one
    my $current_player = int(rand @players);
    # The spinner: an array of anonymous arrays!
    # Format is: (number of cherries to remove, spinner space name, clever quip)
    my @spinner = ([1, "one cherry", "Slow and steady wins the race."],
    [2, "two cherries", "One for you and one for me!"],
    [3, "three cherries", "Just one short of a grand slam."],
    [4, "four cherries", "That was certainly lucky!"],
    [-2, "bird", "Dratted pigeons!"],
    [-2, "dog", "Isn't there a leash law in this town?"],
    [-10, "spilled bucket", "Bad luck, perhaps next time."]);
    # set to a nonzero value when the game is done
    my $game_over = 0;
    # must be declared, thanks to 'use strict'
    my ($cherries, $spinner_name, $clever_quip);
    while (! $game_over) {
    print $players[$current_player] ," spins ";
    ($cherries, $spinner_name, $clever_quip) = @{$spinner[int(rand @spinner)]};
    print $spinner_name, ". ", $clever_quip, " ";
    $tree{$players[$current_player]} -= $cherries;
    # boundary checking: can't have more than 10 cherries in the tree
    if ($tree{$players[$current_player]} > 10) {
    $tree{$players[$current_player]} = 10;
    }
    if ($game_over = ($tree{$players[$current_player]} <= 0)) {
    print $players[$current_player]," has won!\n";
    } else {
    print "Cherries left on the tree: ", $tree{$players[$current_player]},"\n";
    $current_player = ($current_player + 1) % @players;
    }
    }

