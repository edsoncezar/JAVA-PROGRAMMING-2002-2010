package Sports::Baseball::Teams;
use vars qw($VERSION);
use Carp;
use strict;
use overload '""' 		=> \&stringify,
			 '==' 		=> \&compare,
			 'eq'		=> \&compare,
		 	 'fallback' => "SOL";

$VERSION = 0.33;

###Initialization code

my $stringsare = "place";

my %MLTeams  =  (
			sea =>	  {
						fullname 	=> "Seattle Mariners",
						place	 	=> "Seattle",
						teamname 	=> "Mariners",
						league	 	=> "American",
						division	=> "West",
						abbrevs	 	=> [ "Sea" ],
						nick	 	=> "M's",
					  },
			tex => 	  {
						fullname 	=> "Texas Rangers",
						place	 	=> "Texas",
						city		=> "Arlington",
						teamname	=> "Rangers",
						division	=> "West",
						league		=> "American",
						abbrevs		=> ["Tex"],
					  },
			ana => 	  {
						fullname	=> "Anaheim Angels",
						place		=> "Anaheim",
						teamname	=> "Angels",
						league		=> "American",
						division	=> "West",
						abbrevs		=> ["Ana"],
					  },
			oak => 	  {
						fullname	=> "Oakland Athletics",
						place		=> "Oakland",
						teamname	=> "Athletics",
						league		=> "American",
						division	=> "West",
						abbrevs		=> ["Oak"],
						nick		=> "A's",
					  },
			cle => 	  {
						fullname	=> "Cleveland Indians",
						place		=> "Cleveland",
						teamname	=> "Indians",
						league		=> "American",
						division 	=> "Central",
						abbrevs		=> ["Cle"],
					  },
			kan => 	  {
						fullname	=> "Kansas City Royals",
						place		=> "Kansas City",
						teamname	=> "Royals",
						league		=> "American",
						division 	=> "Central",
						abbrevs		=> ["KC"],
					  },
			min => 	  {
						fullname 	=> "Minnesota Twins",
						place		=> "Minnesota",
						city		=> "Minneapolis",
						teamname	=> "Twins",
						league		=> "American",
						division 	=> "Central",
						abbrevs		=> ["Min"],
					  },
			det => 	  {
						fullname	=> "Detroit Tigers",
						place		=> "Detroit",
						teamname	=> "Tigers",
						league		=> "American",
						division 	=> "Central",
						abbrevs		=> ["Det"],
					  },
			chw => 	  {
						fullname 	=> "Chicago White Sox",
						place		=> "Chicago",
						teamname	=> "White Sox",
						league		=> "American",
						division 	=> "Central",
						abbrevs		=> ["CWS", "CHA"],
					  },
			tor => 	  {
						fullname 	=> "Toronto Blue Jays",
						place		=> "Toronto",
						teamname	=> "Blue Jays",
						league		=> "American",
						division 	=> "East",
						abbrevs		=> ["Tor"],
					  },
			tam => 	  {
						fullname 	=> "Tampa Bay Devil Rays",
						place		=> "Tampa Bay",
						city		=> "St. Petersburg",
						teamname	=> "Devil Rays",
						league		=> "American",
						division 	=> "East",
						abbrevs		=> ["TB"],
					  },
			bos => 	  {
						fullname	=> "Boston Red Sox",
						place		=> "Boston",
						teamname	=> "Red Sox",
						league		=> "American",
						division 	=> "East",
						abbrevs		=> ["Bos"],
					  },
			nyy => 	  {
						fullname	=> "New York Yankees",
						place		=> "New York",
						teamname	=> "Yankees",
						league		=> "American",
						division 	=> "East",
						abbrevs		=> ["NYY", "NYA"],
					  },			  
			bal => 	  {
						fullname	=> "Baltimore Orioles",
						place		=> "Baltimore",
						teamname	=> "Orioles",
						league		=> "American",
						division 	=> "East",
						abbrevs		=> ["Bal"],
					  },
			los => 	  {
						fullname	=> "Los Angeles Dodgers",
						place		=> "Los Angeles",
						teamname	=> "Dodgers",
						league		=> "National",
						division	=> "West",
						abbrevs		=> ["LA"],
					  },
			sfo => 	  {
						fullname	=> "San Francisco Giants",
						place		=> "San Francisco",
						teamname	=> "Giants",
						league		=> "National",
						division	=> "West",
						abbrevs		=> ["SF"],
					  },
			sdg => 	  {
						fullname	=> "San Diego Padres",
						place		=> "San Diego",
						teamname	=> "Padres",
						league		=> "National",
						division	=> "West",
						abbrevs		=> ["SD"],
					  },
			ari => 	  {
						fullname	=> "Arizona Diamondbacks",
						place		=> "Arizona",
						city		=> "Phoenix",
						teamname	=> "Diamondbacks",
						league		=> "National",
						division	=> "West",
						abbrevs		=> ["Ari"],
					  },
			col => 	  {
						fullname	=> "Colorado Rockies",
						place		=> "Colorado",
						city		=> "Denver",
						teamname	=> "Rockies",
						league		=> "National",
						division	=> "West",
						abbrevs		=> ["Col"],
					  },
			stl => 	  {
						fullname	=> "St. Louis Cardinals",
						place		=> "St. Louis",
						teamname	=> "Cardinals",
						league		=> "National",
						division	=> "Central",
						abbrevs		=> ["StL"],
					  },
			chc => 	  {
						fullname	=> "Chicago Cubs",
						place		=> "Chicago",
						teamname	=> "Cubs",
						league		=> "National",
						division	=> "Central",
						abbrevs		=> ["ChC", "CHN"],
					  	nick		=> "Cubbies",
					  },
			cin => 	  {
						fullname	=> "Cincinnati Reds",
						place		=> "Cincinnati",
						teamname	=> "Reds",
						league		=> "National",
						division	=> "Central",
						abbrevs		=> ["Cin"],
					  },
			mil => 	  {
						fullname	=> "Milwaukee Brewers",
						place		=> "Milwaukee",
						teamname	=> "Brewers",
						league		=> "National",
						division	=> "Central",
						abbrevs		=> ["Mil"],
					  	nick		=> "The Brew Crew",
					  },
			hou => 	  {
						fullname	=> "Houston Astros",
						place		=> "Houston",
						teamname	=> "Astros",
						league		=> "National",
						division	=> "Central",
						abbrevs		=> ["Hou"],
					  	nick		=> "'Stros",
					  },
			pit => 	  {
						fullname	=> "Pittsburgh Pirates",
						place		=> "Pittsburgh",
						teamname	=> "Pirates",
						league		=> "National",
						division	=> "Central",
						abbrevs		=> ["Pit"],
					  	nick		=> "Bucs",
					  },
			atl => 	  {
						fullname	=> "Atlanta Braves",
						place		=> "Atlanta",
						teamname	=> "Braves",
						league		=> "National",
						division	=> "East",
						abbrevs		=> ["Atl"],
					  },
			nym => 	  {
						fullname	=> "New York Mets",
						place		=> "New York",
						teamname	=> "Mets",
						league		=> "National",
						division	=> "East",
						abbrevs		=> ["NYM", "NYN"],
					  },
			phi => 	  {
						fullname	=> "Philadelphia Phillies",
						place		=> "Philadelphia",
						teamname	=> "Phillies",
						league		=> "National",
						division	=> "East",
						abbrevs		=> ["Phi"],
					  },
			fla => 	  {
						fullname	=> "Florida Marlins",
						city		=> "Miami",
						place		=> "Florida",
						teamname	=> "Marlins",
						league		=> "National",
						division	=> "East",
						abbrevs		=> ["Fla"],
					  	nick		=> "Fish",
					  },
			mon => 	  {
						fullname	=> "Montreal Expos",
						place		=> "Montreal",
						teamname	=> "Expos",
						league		=> "National",
						division	=> "East",
						abbrevs		=> ["Mon"],
					  },
			 );
			  

my %abbrs;

while (my ($key, $val) = each %MLTeams) {
	$abbrs{$key} = $key;
	foreach (@{$val->{abbrevs}}) { $abbrs{lc $_} = $key; }
}			  

###Constructors

sub new {
	my $probe = lc $_[1];
	if (not exists $abbrs{$probe} ) {
		$@ = "No team matches probe $_[1]";
		return;
	}
	return bless \$probe, $_[0];	
}

sub new_fromscore {
	my $probe = lc $_[1];
	my ($ans);
	reset %MLTeams;
	while ( my ($key, $hash) = each %MLTeams) {
		if (   lc $hash->{place} eq $probe ) {
			$ans = $key;
		}
	}

	# special cases
	if    ($probe =~ /WS/i)      { $ans = "chw" }
	elsif ($probe =~ /Cubs/i)    { $ans = "chc" }
	elsif ($probe =~ /Mets/i)    { $ans = "nym" }
	elsif ($probe =~ /Yankees/i) { $ans = "nyy" }
	
	if ($ans) {return bless \$ans, $_[0] }
	else      {$@= "No team matches probe \"$_[1]\""; return}
}

###Accessor methods

sub getfullname {
	my $self = $MLTeams{$abbrs{${$_[0]}}};
	return $self->{fullname};
}

sub getplacename {
	my $self = $MLTeams{$abbrs{${$_[0]}}};
	return $self->{place};
}

sub getlocation {
	my $self = $MLTeams{$abbrs{${$_[0]}}};
	return exists $self->{city} ?  $self->{city} : $self->{place};
}

sub getteamname {
	my $self = $MLTeams{$abbrs{${$_[0]}}};
	return $self->{teamname};
}

sub getleague {
	my $self = $MLTeams{$abbrs{${$_[0]}}};
	return $self->{league};
}

sub getdivision {
	my $self = $MLTeams{$abbrs{${$_[0]}}};
	return $self->{division};
}

sub getnick {  # we do not guarantee that this is defined
	my $self = $MLTeams{$abbrs{${$_[0]}}};
	return $self->{nick}; 
}

sub getkey { #this is indicative of a design flaw, but an easy patch
	$abbrs{${$_[0]}}
}

###Class functions

sub stringify {
	if ($stringsare eq "place")   { $_[0]->getplacename() } 
	elsif ($stringsare eq "team") { $_[0]->getteamname()  }
	elsif ($stringsare eq "full") { $_[0]->getfullname()  }
}

sub compare { $_[0]->getkey() eq $_[1]->getkey() }

sub import {
	return unless $_[1];
	if    ($_[1] eq "-teamname") { $stringsare = "team"; }
	elsif ($_[1] eq "-fullname") { $stringsare = "full"; }
	else { warn "Sports::Baseball::Teams : unrecognized tag '$_[1]'\n"}
}

"Just another Perl hacker";

=pod

=head1 Synopsis

	use Sports::Baseball::Teams '-fullname';
	
	my $hometeam = Sports::Baseball::Teams->new("sea")
	my $villians = Sports::Baseball::Teams->new_fromscore("NY Yankees");
	
	print "The $hometeam and $villians play in the ",
		   $hometeam->getleague, " league.";

=head1 Constructors

=over 4

=item C<new>

Takes a string containing a two or three letter abbreviation 
for a major league team. The abbreviations must be reasonably 
standard, but some allowance is made for teams with multiple valid 
abbreviations (e.g. KC and Kan).  If no matching team is found, $@ is
set and undef returned.

=item C<new_fromscore>

Takes a string containing the team name found in the Boxscores on 
ESPN.com (e.g. "Boston", "Seattle", "NY Mets", "Chicago WS").  If no
matching team  is found, $@ is set and undef returned.

=back

=head1 Methods

=over 4

=item C<getfullname> 

Returns the full name of the team (e.g. "Seattle Mariners", "Tampa
Bay Devil Rays").

=item C<getplacename> 

Returns the place name of the team (e.g. "Seattle", "Tampa Bay").

=item C<getlocation> 

Returns the home city of the team (e.g. "Seattle", "St. Petersburg").

=item C<getteamname> 

Returns the team name (e.g. "Mariners", "Devil Rays").

=item C<getleague> 

Returns the league (National or American) of the team.

=item C<getdivision>

Returns the division (East, West or Central) of the team.

=item C<getnick>

Returns the nickname of the team if available (e.g. "M's", "Cubbies"),
if one is defined (otherwise undef).

=item C<getkey>

Returns the hash key for this team (principally for constructing 
URLs on ESPN.com).  This could legitimately be considered a nasty 
little hack.

=back

=head1 Overloaded operations

By default, objects in this class stringify to their place name 
(e.g. "Seattle", "Chicago").  This behavior can be changed to use 
either the full name or the team name by supplying an additional
argument of '-teamname' or '-fullname' to the C<use> statement, as in

	use Sports::Baseball::Teams '-teamname';

The equality operators (== and eq) are overloaded to operate as one
would expect (using the C<getkey> method above). 


=cut
