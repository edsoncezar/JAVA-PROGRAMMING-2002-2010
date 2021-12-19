# Generates a Random playlist of the length you specify...

use strict;
use File::Find;
my ($dir1, $input, @array);
&prep;
find(\&wanted, $dir1);
&outout;

#----------Sub Routines below this line---------------

sub prep {
	print "Enter the number of songs:";
	chomp ($input=<STDIN>);
	$dir1="//server/mp3share/";
}

sub wanted {
	if (/mp3$/){
		push @array, "$File::Find::name\n";
	}
	print'.';
}

sub outout{
	my $out="$dir1/upload/randlist.m3u";
	open OUT, ">$out" or die "Cannot open $out for write :$!";
	for (1..$input){
	    
		my $number=int(rand $#array);
		my $result=splice @array, $number,1;
		$result=~s/\//\\/g;	
		print OUT "$result";
	}
	close $out;
}

