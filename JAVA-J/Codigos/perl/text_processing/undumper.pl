#####
# Package: Undumper
# Author: Ton Sistos
#
# Usage:
# 
# my $undumper = Undumper->new();
# my $string = my <<'_EOSTRING_';
# {
#  '1' => {string=>"hello"}, 
#  '2' => [2,4,6,[0,3],[1,2]], 
#  bar => [1, 2, { this => 'that', 5, "world"}, baz],
#  5, 4.023421, 
#  'foo', "hello world"
# }
# _EOSTRING_
# my $struct = $undumper->Undump($string) or die "Bad string";
#####
package Undumper;

use Parse::RecDescent;
use strict;
use vars qw($grammar);

# Enable warnings within the Parse::RecDescent module.

$::RD_ERRORS = 1; # Make sure the parser dies when it encounters an error
$::RD_WARN   = 1; # Enable warnings. This will warn on unused rules &c.
$::RD_HINT   = 1; # Give out hints to help fix problems.


$grammar = <<'_GRAMMAR_';
    { my $u = '^%$&undef&$*!'; }

    # Terminals first
    INTEGER : /[-+]?\d+/
            { $return = int($item[1]); }
    FLOAT : /[-+]?\d*\.\d+[eE][-+]?\d+/
          | /[-+]?\d+\.\d*[eE][-+]?\d+/
          | /[-+]?\d*\.\d+/
    STRING : /"((.*?(\\\\)*(\\")*)*?)"/s
           { $return = $1; $return =~ s/\\"/"/g; $return =~ s/\\\\/\\/g; }
           | /'((.*?(\\\\)*(\\')*)*?)'/s
           { $return = $1; $return =~ s/\\'/'/g; $return =~ s/\\\\/\\/g; }
    SIMPLESTRING : /[a-zA-Z]\w*/

    term : FLOAT
         | INTEGER
         | STRING
         | SIMPLESTRING

    goodterm : FLOAT
             | INTEGER
             | STRING

    anystring : STRING
              | SIMPLESTRING
    
    hashpair : goodterm ',' expression
             { $return = [$item[1], $item[3] eq $u ? undef : $item[3]]; }
             | term '=>' expression
             { $return = [$item[1], $item[3] eq $u ? undef : $item[3]]; }
    
    arraylist : expression ',' arraylist
              { $return = [$item[1] eq $u ? undef : $item[1], @{$item[3]}]; }
              | expression ','
              { $return = [$item[1] eq $u ? undef : $item[1]]; }
              | expression
              { $return = [$item[1] eq $u ? undef : $item[1]]; }
    
    hashlist : hashpair ',' hashlist
             { $return = [@{$item[1]}, @{$item[3]}]; }
             | hashpair ','
             { $return = $item[1]; }
             | hashpair
             { $return = $item[1]; }
    
    array : '[' arraylist ']'
          { $return = $item[2]; }
          | '[' ']'
          { $return = []; }
    
    hash : '{' hashlist '}'
         { $return = { @{$item[2]} };  }
         | '{' '}'
         { $return = {}; }

    object : 'bless' '(' primitive ',' anystring ')'
           { $return = bless($item[3], $item[5]); }

    primitive : hash
              | array
              | term

    expression : object
               | 'undef'
               { $return = $u; }
               | primitive
        
    startrule : expression
              { $return = (($text =~ m/^[\s;]*$/) ? ($item[1] eq $u ? undef : $item[1]) : undef); }

_GRAMMAR_

sub new($$)
{
    my $invocant = shift;
    my $paramHRef = shift;
    my $class = ref($invocant) || $invocant;   # object or class name
    my $self = { };
    bless($self, $class);
    $self->_Initialize();
    return $self;
}

sub Undump($$)
{
    my $self = shift;
    my $string = shift;
    return $self->{'parser'}->startrule($string);
}

sub _Initialize($)
{
    my $self = shift;
    my $parser = Parse::RecDescent->new($grammar);
    $self->{'parser'} = $parser;
}

