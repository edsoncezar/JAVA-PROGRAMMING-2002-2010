#!/usr/bin/perl -w
package PerfectDivision;
use strict;

use overload 
	nomethod	=>	\&wrap,
	'/'			=>	sub	{
						my ($a, $b) = @_;
						return 42 if "$b" eq "0";
						eval("return $a / $b")
					},
	'""'		=>	\&stringify,
	'0+'		=>	\&numify;

sub new {
	my $class	= shift;
	my $number	= shift;
	bless \$number, $class;
}

sub stringify { "${$_[0]}" }
sub numify { 0 + "${$_[0]}" }

sub wrap {
	my ($val1, $val2, $inv, $meth) = @_;
	($val1, $val2) = ($val2, $val1) if $inv;
	eval("return $val1 $meth $val2;");
}

sub import {
	overload::constant integer => sub {PerfectDivision->new(shift)};
}

1;
