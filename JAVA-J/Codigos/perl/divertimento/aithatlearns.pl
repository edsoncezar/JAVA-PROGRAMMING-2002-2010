### Memory.pm

package Memory;

# A class that implements a simple memory. Memory is stored by keying
# "states" to "priorities"; a higher priority indicates that the
# corresponding state is a better state than those of lower priority.

use strict;
use integer;

# Constructor
sub new { 
  my $class = shift;
  my $self = {};
  bless $self, $class;
  return $self;
}

# Loads the memory from the given filename.
sub load {
  my ($self, $filename) = @_;
  open(MEMORY_FILE, $filename) || warn "could not load memory from $filename: $!";
  my @memory = <MEMORY_FILE>;
  close(MEMORY_FILE);
  for (my $i = 0; $i < @memory; $i++) {
    chomp $memory[$i];
  }
  my %memory = @memory;
  foreach my $key (keys %memory) {
    $self->set($key, $memory{$key});
  }
}

# Dumps the current state of memory to a file.
sub save {
  my ($self, $filename) = @_;
  open(MEMORY_FILE, ">$filename") || warn "could not save memory to $filename: $!";
  foreach my $key (sort keys %$self) {
    my $value = $self->get($key);
    print MEMORY_FILE "$key\n$value\n";
  }
}

# Takes in a string representing a state, and returns
# the priority of that state.
sub get {
  return $_[0]{$_[1]};
}

# Takes in a string representing a state, and a numerical priority for
# the state. Sets the priority of that state accordingly.
sub set {
  $_[0]{$_[1]} = $_[2];
}

# Takes in a string representing a state, and an amount to modify the
# priority of that state by. Modifies the priority by the given amount.
sub modify {
  my ($self, $state, $priority) = @_;
  $self->{$state} = $self->get($state) + $priority;
}

# Takes in a reference to a list of all the valid states that can
# currently occur, and returns the state with the highest priority. If
# more than one state shares the highest priority, it randomly picks
# one of the best states.
sub get_best_state {
  my ($self, $states) = @_;

  # Find the highest priority of any of the states.
  my $highest_priority = -2**30;
  foreach (@$states) {
    if ($self->get($_) > $highest_priority) {
      $highest_priority = $self->get($_);
    }
  }

  # Find all of the states at the highest priority.
  my @best_states;
  foreach my $state (@$states) {
    my $priority = $self->get($state);
    if ($priority == $highest_priority) {
      push @best_states, $state;
    }
  }

  # Randomly choose a state out of our list of best states, and return it.
  return $best_states[int(rand(@best_states))];
}

1;

### Random.pm

package Random;

# A game-player AI implementing a player who merely makes a
# random move out of a list of available moves.
#
# Nothing very exciting is going on here.

use strict;
use integer;

sub new {
  my $class = shift;
  my $self = {};
  bless $self, $class;
  return $self;
}

sub make_move {
  my ($self, $valid_states) = @_;
  my @valid_states = keys %$valid_states;
  my $move = $valid_states[int(rand(@valid_states))];
  return $$valid_states{$move};
}

sub win {}
sub lose {}
sub tie {}

1;

### Defensive.pm

package Defensive;

# A class implementing a "defensive" AI game player. The defensive
# player considers all states that have led to losses as "bad" and
# makes no preference between a win and a tie. The defensive player is
# the only type of player that can evolve to become an unbeatable
# tic-tac-toe player.
#
# Although I've only tested this player in the game of tic-tac-toe,
# it knows nothing about the rules of tic-tac-toe.  At any
# given time when it has to make a move, it just gets a list of the
# valid states it can put the board into, and chooses the "best" state
# out of memory. At the end of a game, it receives the result of the
# game (win, loss, or tie) and modifies its memory to adjust for the
# result.
#
# Adapting this player to a different game should be incredibly easy;
# the game just has to send in a list of valid states and call the
# appropriate win(), lose(), or tie() method at the end of the game.

use Memory;

@Defensive::ISA = ("Memory");

# Constructor
sub new {
  my $class = shift;
  my $self = Memory::new($class);
  return $self;
}

# Do nothing if the result of a game was a win or a tie, except clear
# the "states" entry.
sub win { delete $_[0]->{"states"}; }
sub tie { delete $_[0]->{"states"}; }

# If we lost, decrease the priorities of all states that we put the
# game into. States which occurred toward the end of the game are
# weighted as "more bad" than states which occurred at the
# beginning.
sub lose {
  my $self = shift;
  my @states = @{$self->{"states"}};
  my $score = -32;
  while (@states) {
    my $state = pop(@states);
    $self->modify($state, $score);
    $score /= 2;
  }
  delete $self->{"states"};
}

# Uses Memory.pm's get_best_state() method to find and return the best
# move out of those provided in the @$valid_states array.
#
# Keeps track of the moves it's made during this game, such that it
# can modify their values accordingly at the end of the game.
sub make_move {
  my ($self, $valid_states) = @_;
  my @valid_states = (keys %$valid_states);
  my $best_state = $self->get_best_state(\@valid_states);
  push @{$self->{"states"}}, $best_state;
  return $valid_states->{$best_state};
}

1;

### TicTacToe.pm

package TicTacToe;

use integer;
use strict;

# A class that implements a game of tic-tac-toe.
#
# The board is indexed as follows:
# 0 1 2
# 3 4 5
# 6 7 8
#
# Each tic-tac-toe state is represented as a nine-character string
# where each character in the string corresponds to the given location
# on the board. The character is a "0" if that square is empty, "1" is
# that square has a mark by the current player, and "2" is that square
# has a mark y the current player's opponent.

# Constructor.
sub new {
  my $class = shift;
  my $self = { board             => [0, 0, 0, 0, 0, 0, 0, 0, 0],
	       moves_made        => 0,
	       player            => int(rand(2) + 1),
	       winning_positions => [[0, 1, 2], [3, 4, 5], [6, 7, 8], [0, 3, 6], 
				     [1, 4, 7], [2, 5, 8], [0, 4, 8], [2, 4, 6]] };
  bless $self, $class;
  return $self;
}

# Plays an entire game of tic-tac-toe. Takes in references to two
# "player" objects; it alternates between these objects, asking each
# for a move, until the game is over. At the end of the game, it
# notifies each player of the result of the game, and returns the
# result. 0 is a tie, 1 and 2 are wins by player 1 and player 2,
# respectively.

sub play {
  my ($self, $p1, $p2) = @_;
  my $result = undef;

  until (defined $result) {
    if (current_player($self) == 1) {
      $result = $self->request_move($p1);
    } else {
      $result = $self->request_move($p2);
    }
    $self->switch_player();
  }
  if ($result == 1) {
    $p1->win();
    $p2->lose();
  } elsif ($result == 2) {
    $p2->win();
    $p1->lose();
  } else {
    $p1->tie();
    $p2->tie();
  }
  return $result;
}

# Takes in a reference to a player object, and requests (through the
# player's make_make() method) that the player make a move.
#
# BUG: We send the player a list of the valid moves (actually, a list
# of the valid states that the player can currently put the board
# into), but never check to see whether the player actually made a
# valid move. It's assumed that we have honest (and
# correctly-programmed) players, which is probably a bad assumption to
# make. 
sub request_move {
  my ($self, $ai) = @_;
  my $move = $ai->make_move($self->valid_states());
  $$self{"board"}->[$move] = $self->current_player();
  $self->{"moves_made"}++;
  return $self->check_for_win();
}

# Checks to see if the game has been won.
# Returns 1 or 2 if player 1 or 2 has won, undef otherwise.
#
# This sub is horribly non-optimized, but it works.
sub check_for_win {
  my $self = shift;
  return undef if ($self->{moves_made} < 3);
  my $player = $self->current_player();
  my $win = 0;
  my @winning_positions = @{$self->{"winning_positions"}};
  for (my $i = 0; $win == 0 && $i < scalar(@winning_positions); $i++) {
    $win = 1;
    my @needed_moves = @{$winning_positions[$i]};
    foreach my $move (@needed_moves) {
      unless ($self->{"board"}->[$move] == $player) {
	$win = 0;
      }
    }
  }

  if ($win) {
    return $player;
  } elsif ($self->{"moves_made"} == 9) { 
    return 0; 
  } else {
    return undef;
  }
}

# Returns a list of the currently-valid moves.
sub valid_moves {
  my $self = shift;
  my @valid_moves;
  for (my $i = 0; $i < 9; $i++) {
    if ($self->{"board"}->[$i] == 0) {
      push(@valid_moves, $i);
    }
  }
  return @valid_moves;
}

# Returns the current player (1 or 2).
sub current_player {
  my $self = shift;
  return $$self{"player"};
}

# Switches the current player.
sub switch_player {
  my $self = shift;
  if ($$self{"player"} == 1) {
    $$self{"player"} = 2;
  } else {
    $$self{"player"} = 1;
  }
}

# Returns the board.
sub board { 
  my $self = shift;
  return @{$self->{"board"}};
}

# Returns a string representation of the current state of the board.
# The nth character in the string corresponds to square n of the
# board, where n is an integer from 0 to 8.The current player is
# always denoted as "1", its opponent "2", and an empty space "0".
sub current_state {
  my $self = shift;
  my $current_state = join("", board($self));

  # If the current player is 2, we need to swap the 1's and 2's in the board state.
  if (current_player($self) == 2) {
    $current_state =~ tr/12/21/;
  }
  return $current_state;
}

# Returns a reference to a hash of the states that the current player
# can legally put the board into. The keys of the hash are the states
# themselves; the values are the moves required to put the board into
# each state.
sub valid_states {
  my $self = shift;
  my $current_state = current_state($self);
  my %valid_states;

  my @valid_moves = $self->valid_moves();

  foreach my $move (@valid_moves) {
    my $valid_state = $current_state;
    substr($valid_state, $move, 1) = 1;
    $valid_states{$valid_state} = $move;
  }
  return \%valid_states;
}

1;

### main.pl

#!/usr/bin/perl

# Driver program for tic-tac-toe games. Plays a (basically) infinite
# number of games, dying gracefully when interrupted. Prints out the
# results so far, at every 100 games. Also handles loading and saving
# of player memories.

use strict;
use integer;

use TicTacToe;
use Defensive;
use Random;

$SIG{INT} = \&sig_handler;


my @record = (0, 0, 0);
my $p1 = Defensive->new();
my $p2 = Random->new();
$p1->load("player-1-memory.txt");

my $dead = 0;
my $num_games = 0;

until ($dead) {
  $num_games++;
  my $ttt = TicTacToe->new();
  my $result = $ttt->play($p1, $p2);
  $record[$result]++;
  if ($num_games % 100 == 0) {
    print "($num_games) $record[1]/$record[2]/$record[0]\n";
  }
}

$p1->save("player-1-memory.txt");
print "Player 1 memory saved OK.\n";

sub sig_handler {
  print "\nCaught INT signal... shutting down.\n";
  $dead = 1;
}

