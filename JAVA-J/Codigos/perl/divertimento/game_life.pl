#!/usr/bin/perl

package Game::Life;

######################
#
# Game::Life
# v0.01
# Michael K. Neylon
# mneylon-pm@masemware.com
# May 28, 2001
#
# Runs Conway's Game of Life
#
# Suggestions/Comments/Ideas are highly desired and can be 
# sent to the eamil address above.
#
# Change History:
#
# v0.01 - May 28, 2001
# - Initial Release
#
######################


use strict;
use Exporter;
use Clone qw( clone );

use vars qw /$VERSION @ISA @EXPORT @EXPORT_OK/;

@ISA       = qw /Exporter/;
@EXPORT    = qw //;
@EXPORT_OK = qw //;

$VERSION   = 0.01;

my $default_size = 100;

sub new {
    my $class = shift;
    my $self = {} ;

	# No args, set up a blank one
	$self->{ size } = shift || $default_size;
	$self->{ grid } = [ map 
		              { [ map { 0 } (1..$self->{ size } ) ] } 
				      (1..$self->{ size } ) ];

	bless $self, $class;
	return $self;
}

sub toggle_point {
	my ( $self, $x, $y ) = @_;
	return ( $self->{ grid }->[$x]->[$y] = !$self->{ grid }->[$x]->[$y] );
}

sub set_point {
	my ( $self, $x, $y ) = @_;
	$self->{ grid }->[$x]->[$y] = 1;
}

sub unset_point {
	my ( $self, $x, $y ) = @_;
	$self->{ grid }->[$x]->[$y] = 0;
}

sub place_points {
	my ( $self, $x, $y, $array ) = @_;
	return if ( $x < 0 || $x >= $self->{ size } || 
		        $y < 0 || $y >= $self->{ size } );
	my ($i, $j);
	my $array_x = @$array;
	my $array_y = @{$$array[0]};
	for ( $i = 0 ; $i < $array_x && $i+$x < $self->{ size }; $i++ ) {
		for ( $j = 0 ; $j < $array_y && $j+$y < $self->{ size }; $j++ ) {
			$self->{ grid }->[ $x + $i ]->[ $y + $j ] = 
				($array->[ $i ]->[ $j ] > 0) ? 1 : 0;
		}
	}
}

sub get_grid { 
	my ( $self ) = @_;
	return clone( $self->{ grid } );
}

sub process {
	my $self = shift;
	my $times = shift || 1;

	for (1..$times) {
		my $new_grid = clone( $self->{ grid } );
		
		for	my $i ( 0..$self->{ size }-1 ) {
			for	my $j ( 0..$self->{ size }-1 ) {
				$new_grid->[$i]->[$j] = 
					$self->_determine_life_status( $i, $j );
			}
		}
		$self->{ grid } = $new_grid;
	}
}

sub _determine_life_status {
	my ( $self, $x , $y ) = @_;
	my $n = 0;
	for my $i ( $x-1, $x, $x+1 ) {
		for my $j ( $y-1, $y, $y+1 ) {
			$n++ if ( $i >= 0 && $i < $self->{ size } &&
				      $j >= 0 && $j < $self->{ size } ) && 
				    ( $self->{ grid }->[ $i ]->[ $j ] );
		}
	}
	return ( $self->{ grid }->[ $x ]->[ $y ] ) ? 
			( $n == 3 || $n == 4 ) : ( $n == 3 );
}

=head1 NAME

Game::Life - Plays Conway's Game of Life

=head1 SYNOPSIS

	use Game::Life;
	my $game = new Game::Life( 20 );
	my $starting = [
					[ 1, 1, 1 ],
					[ 1, 0, 0 ],
					[ 0, 1, 0 ]
				   ];

	$game->place_points( 10, 10, $starting );
	for (1..20) {
		my $grid = $game->get_grid();
		foreach ( @$grid ) {
			print map { $_ ? 'X' : '.' } @$_;
			print "\n";
		}
		print "\n\n";
		$game->process();
	}

=head1 DESCRIPTION

Conway's Game of Life is a basic example of finding 'living' patterns
in rather basic rulesets.  The Game of Life takes place on a 2-D 
rectangular grid, with each grid point being either alive or dead.
If a living grid point has 2 or 3 neighbors within the surrounding 8
points, the point will remain alive in the next generation; any fewer
or more will kill it.  A dead grid point will become alive if there are
exactly 3 living neighbors to it.  With these simple rules, fascinating
structures such as gliders that move across the grid, glider guns that
generate these gliders, XOR gates, and others have been found.

This module simply provides a way to simulate the Game of Life in Perl.

C<new> - Creates a new Life game board; if passed a scalar, the game
board will be a square of that size, otherwise, it will be a default 
100x100 units.

C<place_points> - Takes two scalars (indicating the position on the
grid) and a reference to an array of arrays; this array is placed into 
the Life grid at the specified position, overwriting any data already
there.  Within the array of arrays, any non-zero values will be 
considered as a living square.

C<toggle_point>, C<set_point>, C<unset_point> - Take two scalars
that indiciate a specific grid position.  These functions toggle, 
sets, or unsets the life status of the grid point passed,
respectively.

C<process> - If passed a number, runs the Life simulation that many
times, else runs the simulation once.

C<get_grid> - Returns a B<copy> of the Life grid as a reference to 
an array of arrays.

=head1 HISTORY

	Revision 0.01  2001/05/28   Michael K. Neylon
	Initial revision

=head1 AUTHOR

This package was written by Michael K. Neylon

=head1 COPYRIGHT

Copyright 2001 by Michael K. Neylon

=head1 LICENSE

Permission is hereby granted, free of charge, to any person obtaining a
copy of this software and associated documentation files (the "Software"),
to deal in the Software without restriction, including without limitation
the rights to use, copy, modify, merge, publish, distribute, sublicense,
and/or sell copies of the Software, and to permit persons to whom the
Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included
in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
THE AUTHOR BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT
OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.

=cut

1;
