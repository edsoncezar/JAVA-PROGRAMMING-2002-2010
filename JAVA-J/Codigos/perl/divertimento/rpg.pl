package RPG::Dice;

require 5.005;
use strict;
#use warnings;
use Carp;

use Exporter;

use vars qw(@ISA %EXPORT_TAGS @EXPORT_OK @EXPORT $VERSION);

@ISA = qw(Exporter);

# Items to export into callers namespace by default. Note: do not export
# names by default without a very good reason. Use EXPORT_OK instead.
# Do not simply export all your public functions/methods/constants.

# This allows declaration	use RPG::Dice ':all';
# If you do not need this, moving things directly into @EXPORT or @EXPORT_OK
# will save memory.
%EXPORT_TAGS = ( 'all' => [ qw( 
                                   &computeDice
                                  ) ] );

@EXPORT_OK = ( @{ $EXPORT_TAGS{'all'} } );

@EXPORT = qw(
	   &computeDice
);
$VERSION = '0.85';

my($no_ltz_results) = 1;

BEGIN {
       srand(); # intitalize Randomizer
      }


#---------------------------------------------------------------------
#---------------------------------------------------------------------
sub computeDice {
my($die_expr) = shift;
my($top_rolls, $no_rolls, $die_face, $adj, $die_modifier, $list_rolls)  = 0;
my($roll);
my($result) = 0;
my(@dice_rolls) = ();

if ($die_expr =~ /tt(\d+)/i) # Top rolls
   {
    $top_rolls = $1 * -1;
    $die_expr =~ s/tt(\d+)//i;
   }
   
if ($die_expr =~ /l/i) # lowercase L
   {
    $list_rolls = 1;
    $die_expr =~ s/ *l *//i;
   }
   
if ($die_expr =~ /(\d+)d(\d+)(([-+])(\d+))*/)
   {
    $no_rolls = $1;
    $die_face = $2;
    $adj = $4;
    $die_modifier = $5;

    while ($no_rolls-- > 0)
       {
        push(@dice_rolls, int(rand($die_face) + 1));
       }
   }

if (@dice_rolls) #@articles = sort {$a <=> $b} @files;
   {
    if ($top_rolls)
       {
        @dice_rolls = sort {$a <=> $b} @dice_rolls;
        @dice_rolls = splice(@dice_rolls, $top_rolls);
       }

    foreach $roll (@dice_rolls) 
       {
        $result +=  $roll;
       }

    if ($adj =~ /-/)
       { $result -= $die_modifier; }
      else
       { $result += $die_modifier; }
    
    $result = 1 if (($no_ltz_results) && ($result < 1));
   }
  else
   {
    $result = -1;
   }


if ($list_rolls)
   {
    return($result, @dice_rolls);
   }
  else
   {
    return($result);
   }
}



1;
__END__
# Below is stub documentation for your module. You better edit it!

=head1 NAME

RPG::Dice - Perl extension for Dice rolling expressions.


=head1 SYNOPSIS

  use RPG::Dice;
  $dice_roll = computeDice("1d10+1");
  $dice_roll = computeDice("tt3 4d6"); # Stat-Roll (roll 4d6 take top 3)
     -or-
  $dice_roll = RPG::Dice::computeDice("1d10+1");

=head1 DESCRIPTION

   Dice is a set of subroutines for parsing dice expressions. RPG is
   an ongoing library for Role Playing Game systems written in Perl.

=item computeDice("1d2+3");

   Function evaluates the expression. for example the expression
   "1d2+3" means roll 1 '2 sided die' and add 3 to the result.
   
   Dice Expression Syntax:
   
       [tt#1] #2d#3[+/-#4] [l]
       
       Where:
             tt#1 = Take Top Number of rolls for result
             #2 = Number of times to roll die face
             #3 = Die Face
             #4 = Result Modifier
             l = List roll results (lower case L) 
                 Returns list with result as first item
                 and rolls following. If tt is used it
                 returns all the rolls.

       Example Syntax:
           
           "tt3 4d6"  Roll 4 d6 and take top 3 rolls
           "5d10-3"   Roll 4 d10 and subtract 3 from result


=head2 NOTE:
    An internal variable '$no_ltz_results', if set to false(0) will allow
    for zero or negative results from expression. This is by default
    set to true.

=head2 EXPORT

computeDice().


=head1 AUTHOR

Syrkres syrkres@miniworld.com

=head1 SEE ALSO

perl(1).

=cut
