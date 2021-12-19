#!perl -wT
use strict;

use CGI;
use CGI::Carp qw(fatalsToBrowser);
my $q = new CGI;
print $q->header;
my $username = $q->param("username");
my $password = $q->param("password");

if ($username)
{
	open (DATA, "data.txt");
	my @data = <DATA>;
	close (DATA);
	open (OUT, ">>data.txt");
	print OUT scalar(@data)+1, "*$username*$password\n";
	close (OUT);

	print "<p>Thank you for signing up!<br>";
	print "Your username is $username, your password is $password.";
	print "</p>";

}
else
{
	print "<form method=\"post\" action=\"/cgi-bin/signup.pl\">\n";
	print "Username:<input type=\"text\" name=\"username\"><br>\n";
	print "Password:<input type=\"password\" name=\"password\"><br>\n";
	print "<input type=\"submit\">";
	print "</form>";
}
print << "END_OF_MENU";
<br>
<table border="0">
<tr>
<td><a href="/cgi-bin/signup.pl">Sign Up</a></td>
<td><a href="/cgi-bin/standings.pl">Standings</a></td>
<td><a href="/cgi-bin/loss.pl">Report Loss</a></td>
</tr>
</table>
END_OF_MENU


##</code><code>##

#!perl -wT
use strict;

# report a loss, and recalculate rankings.

use CGI;
use CGI::Carp qw(fatalsToBrowser);

my $q = new CGI;
print $q->header;
my $winner = $q->param("winner");
my $description = $q->param("description");
my $username = $q->param("username");
my $password = $q->param("password");

if ($username)
{
	open (DATA, "data.txt");
	my @data = <DATA>;
	close (DATA);

	my (@rank, @player, @player_password, $the_winner, $the_loser, $winner_rank, $loser_rank);

	for (my $i=0; $i<@data; $i++)
	{
		my @entry = split /\*/, $data[$i];
		$rank[@rank] = $entry[0];
		$player[@player] = $entry[1];
		chomp($entry[2]);
		$player_password[@player_password] = $entry[2];

		if ($winner eq $entry[1])
		{
			$the_winner=$i;
			$winner_rank=$entry[0];
		}
		if ($username eq $entry[1])
		{
			$the_loser=$i;
			$loser_rank=$entry[0];
		}
	}

	$rank[$the_winner] += (int(($loser_rank - $winner_rank)/2)-1) if ($winner_rank>$loser_rank);
	$loser_rank = $rank[$the_winner];
	if ($username eq $player[$the_loser] && $password eq $player_password[$the_loser])
	{
		for (my $i=0; $i<@player; $i++)
		{
			$rank[$i]++ if ($rank[$i] >= $loser_rank && $rank[$i] < $winner_rank && $i != $the_winner);
		}

		my @indices = (0 .. $#rank);
		my @sorted_indices = sort {$rank[$a] <=> $rank[$b]} @indices;
		@rank = @rank[@sorted_indices];
		@player= @player[@sorted_indices];
		@player_password = @player_password[@sorted_indices];

		open (OUT, ">data.txt");
		for (my $i=0; $i < @rank; $i++)
		{
			print OUT "$rank[$i]*$player[$i]*$player_password[$i]\n";
		}
		close (OUT);

		my $thetime = localtime;
		my @entry = split(' ', $thetime);
		$thetime = "$entry[1]/$entry[2]/$entry[4]";

		open (OUT, ">>matches.txt");
		print OUT "$winner*$username*$description*$thetime\n";
		close (OUT);

		print "Thank you.  You submitted:<br>\n";
		print "Winner: $winner<br>\n";
		print "Loser: $username<br>\n";
		print "Description: $description<br>\n";
		print "Time: $thetime\n";
	}
	else
	{
		print "I hope you die, cheating scum.";
	}
}
else
{
	print << "END_OF_FORM";
	<form method="post" action="/cgi-bin/loss.pl">
		Username: <input type="text" name="username"><br>
		Password: <input type="password" name="password"><br>
		Winner: <input type="text" name="winner"><br>
		Description: <input type="text" name="description"><br>
		<input type="submit">
	</form>
END_OF_FORM
}
print << "END_OF_MENU";
<br>
<table border="0">
<tr>
<td><a href="/cgi-bin/signup.pl">Sign Up</a></td>
<td><a href="/cgi-bin/standings.pl">Standings</a></td>
<td><a href="/cgi-bin/loss.pl">Report Loss</a></td>
</tr>
</table>
END_OF_MENU


##</code><code>##

#!perl -wT
use strict;

use CGI::Carp qw(fatalsToBrowser);

print "Content-type: text/html\n\n";
open (DATA, "data.txt");
my @data = <DATA>;
close (DATA);

my (@rank, @player, @player_password);

for (my $i=0; $i<@data; $i++)
{
	my @entry = split /\*/, $data[$i];
	$rank[@rank] = $entry[0];
	$player[@player] = $entry[1];
	chomp($entry[2]);
	$player_password[@player_password] = $entry[2];
}

open (MATCHES, "matches.txt");
my @matches = <MATCHES>;
close (MATCHES);

my %wins;
my %losses;

foreach my $player (@player)
{
	$wins{$player} = 0;
	$losses{$player} = 0;
	foreach my $match (@matches)
	{
		my @entry = split /\*/, $match;
		if ($player eq $entry[0])
		{
			$wins{$player}++;
		}
		if ($player eq $entry[1])
		{
			$losses{$player}++;
		}
	}
	
}

print "<table border=\"1\">\n";
print "<tr><td>Rank</td><td>Player</td><td>Wins</td><td>Losses</td><td>Win Percentage</td></tr>\n";
for (my $i=0; $i < @rank; $i++)
{
	my $win_percentage = substr(($wins{$player[$i]}/($wins{$player[$i]}+$losses{$player[$i]})),0,6) unless (($wins{$player[$i]}+$losses{$player[$i]}) < 1);
	$win_percentage = 0 if (($wins{$player[$i]}+$losses{$player[$i]}) < 1);
	print "<tr><td>$rank[$i]</td><td><a href=\"/cgi-bin/users.pl?user=$player[$i]\">$player[$i]</a></td><td>$wins{$player[$i]}</td><td>$losses{$player[$i]}</td><td>$win_percentage</td></tr>\n";
}
print "</table>\n";
print << "END_OF_MENU";
<br>
<table border="0">
<tr>
<td><a href="/cgi-bin/signup.pl">Sign Up</a></td>
<td><a href="/cgi-bin/standings.pl">Standings</a></td>
<td><a href="/cgi-bin/loss.pl">Report Loss</a></td>
</tr>
</table>
END_OF_MENU


##</code><code>##

#!perl -wT
use strict;

use CGI;
use CGI::Carp qw(fatalsToBrowser);

my $q = new CGI;
print $q->header;
my $username = $q->param("username");
my $password = $q->param("password");
my $delete   = $q->param("delete");
my $deluser  = $q->param("deluser");
if ($username)
{	

	if ($username eq "admin" && $password eq "monksofperl")
	{

		if ($delete)
		{
			open (MATCHES, "matches.txt");
			my @matches = <MATCHES>;
			close (MATCHES);
			open (OUT, ">matches.txt");
			for (my $i=0; $i<@matches; $i++)
			{
				print OUT "$matches[$i]" if ($i != $delete);
			}
			close (OUT);
		}
		if ($deluser)
		{
			open (DATA, "data.txt");
			my @data = <DATA>;
			close (DATA);
			open (OUT, ">data.txt");
			for (my $i=0; $i<@data; $i++)
			{
				print OUT "$data[$i]" if ($i != $deluser);
			}
			close (OUT);
		}
		open (MATCHES, "matches.txt");
		my @matches = <MATCHES>;
		close (MATCHES);
		open (DATA, "data.txt");
		my @data = <DATA>;
		close (DATA);

		print "<h1>Matches</h1>\n";
		print "<table border=\"1\">";
		print "<tr><td>&nbsp;</td><td>Winner</td><td>Loser</td><td>Description</td><td>Date</td></tr>\n";
		for (my $i=0; $i<@matches; $i++)
		{
			my @entry = split /\*/, $matches[$i];
			print "<tr><td><a href=\"/cgi-bin/admin.pl?delete=$i&username=$username&password=$password\">Reap</a></td><td>$entry[0]</td><td>$entry[1]</td><td>$entry[2]</td><td>$entry[3]</td></tr>\n";
		}
		print "</table>\n";
		print "<p><b>Report a loss</b><br>\n";
		print "<form method=\"post\" action=\"/cgi-bin/loss.pl\">\n";
		print "<table border=\"0\">\n";
		print "<tr><td>Winner:</td><td><input type=\"text\" name=\"winner\"></td></tr>\n";
		print "<tr><td>Loser:</td><td><input type=\"text\" name=\"username\"></td></tr>\n";
		print "<tr><td>Description:</td><td><input type=\"text\" name=\"description\"></td></tr>\n";
		print "<tr><td>Loser's pw:</td><td><input type=\"text\" name=\"password\"></td></tr>\n";		
		print "</table>\n";
		print "<input type=\"submit\">";
		print "</form>";
		print "<h1>Users</h1>\n";
		print "<table border=\"1\">";
		print "<tr><td>&nbsp;</td><td>Rank</td><td>Username</td><td>Password</td></tr>\n";
		for (my $i=0; $i<@data; $i++)
		{
			my @entry = split /\*/, $data[$i];
			print "<tr><td><a href=\"/cgi-bin/admin.pl?deluser=$i&username=$username&password=$password\">Reap</a></td><td>$entry[0]</td><td>$entry[1]</td><td>$entry[2]</td></tr>\n";
		}
		print "</table>\n";
	}
	else
	{
		print "<h1><font color=\"red\">UNAUTHORIZED</font></h1>";
	}
}
else
{
	print "<form method=\"post\" action=\"/cgi-bin/admin.pl\">\n";
	print "Username:<input type=\"text\" name=\"username\"><br>\n";
	print "Password:<input type=\"password\" name=\"password\"><br>\n";
	print "<input type=\"submit\">";
	print "</form>";
}
print << "END_OF_MENU";
<br>
<table border="0">
<tr>
<td><a href="/cgi-bin/signup.pl">Sign Up</a></td>
<td><a href="/cgi-bin/standings.pl">Standings</a></td>
<td><a href="/cgi-bin/loss.pl">Report Loss</a></td>
</tr>
</table>
END_OF_MENU


##</code><code>##

#!perl -wT
use strict;

use CGI;
use CGI::Carp qw(fatalsToBrowser);

my $q = new CGI;
print $q->header;
my $user = $q->param("user");

open (MATCHES, "matches.txt");
my @matches = <MATCHES>;
close (MATCHES);

open (DATA, "data.txt");
my @data = <DATA>;
close (DATA);

my %rankings;
foreach my $line (@data)
{
	my @entry = split /\*/, $line;
	$rankings{$entry[1]} = $entry[0];
}
my ($x, $wins, $losses, @winners, @losers, @descriptions, @dates);
$wins = $losses = 0;
if (exists($rankings{$user}))
{
	foreach my $match (@matches)
	{
		my @entry = split /\*/, $match;
		$wins++ if ($entry[0] eq $user);
		$losses++ if ($entry[1] eq $user);
		if ($entry[0] eq $user || $entry[1] eq $user && $x<10)
		{
			$winners[@winners] = $entry[0];
			$losers[@losers] = $entry[1];
			$descriptions[@descriptions] = $entry[2];
			$dates[@dates] = $entry[3];
			$x++;
		}
	}
}

print "<center><h1>$user</h1>\n";
print "<p><b>Wins:</b> $wins &nbsp;&nbsp; <b>Losses:</b> $losses</p><p>Last 10 Matches<br>";
print "<table border=\"1\">";
print "<tr><td>Date</td><td>Winner</td><td>Loser</td><td>Description</td></tr>";
for (my $i=0; $i<@winners; $i++)
{
	print "<tr><td>$dates[$i]</td><td>$winners[$i]</td><td>$losers[$i]</td><td>$descriptions[$i]</td></tr>\n";
}
print "</table>\n";
print << "END_OF_MENU";
<br>
<table border="0">
<tr>
<td><a href="/cgi-bin/signup.pl">Sign Up</a></td>
<td><a href="/cgi-bin/standings.pl">Standings</a></td>
<td><a href="/cgi-bin/loss.pl">Report Loss</a></td>
</tr>
</table>
END_OF_MENU

