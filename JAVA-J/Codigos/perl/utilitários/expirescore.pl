#!/usr/bin/perl -w

# File: expirescore.pl
#
# Version: 0.01
#
# Purpose: modify the score file generated from the slrn newsreader 
#          and expire scores based on date.


use Time::ParseDate;
use strict;

# Put your scorefile here
my $scorefile = "/where/ever/.slrnscore";

# This is the hash containing the data structure.
my %scores;

# Read in the score file and generate the data structure.
&read_score_struct();

# Endless loop.
while (1) {
	system("clear");

	# Print out the list of newsgroups.
	&show_newsgroups();

	print "[E]xpire scores, [D]elete whole group score, [W]rite scorefile " .
			"or [Q]uit? ";
	chomp(my $input = <STDIN>);

	# Quit the program.
	if (lc $input eq "q") {
		print "Allright, I'll quit.\n";
		last;

	# Expire scores in a specific newsgroup.
	} elsif (lc $input eq "e") {
		print "Which newsgroup? ";
		chomp(my $newsgroup = <STDIN>);
		print "Expire messages older than (e.g. \"2 months\", \"1 year\"): ";
		chomp(my $date = <STDIN>);

		my $epochtime = parsedate("-$date");
		die "Error parsing date: $!" unless $epochtime;

		&expire($newsgroup, $epochtime);

	# Delete the scores for a whole newsgroup.
	} elsif (lc $input eq "d") {
		print "Which newsgroup? ";
		chomp(my $newsgroup = <STDIN>);

		delete $scores{$newsgroup};

	# Write out the scorefile.
	} elsif (lc $input eq "w") {
		&write_score_struct();

		print "Score file $scorefile written.\n";

		last;
	}

}
	

####
# This sub reads in the scorefile and generates our data 
# structure, based on hash of hashes.
sub read_score_struct {
	my ($current_group, $epochtime);

	open (SCORES, $scorefile) or die $!;

	while (my $line = <SCORES>) {
		# Skip commented lines that we don't need.
		next if $line =~ /^%(?!Score created)/;

		# Extract the creation date.
		if ($line =~ /^%Score created by slrn on (.*)$/) {
			$epochtime = parsedate($1);
			die "Parsing date: $!\n" unless defined $epochtime;

		# Extract the newsgroup that the score is responsible for and setup
		# a temporary variable we can work with.
		} elsif ($line =~ /^\[(.*)\]/) {
			if (exists $scores{$1}) {
				$current_group = $scores{$1};

			} else {
				$scores{$1} = $current_group = {};
			}

		# Extract the score value.
		} elsif ($line =~ /^Score: (.*)/) {
			$current_group->{$epochtime}->{SCORE} = $1;

		# Extract the header that was scored on.
		} elsif ($line =~ /^\s+(\w+): (.*)$/) {
			$current_group->{$epochtime}->{HEADER} = $1;
			$current_group->{$epochtime}->{MATCH} = $2;
		}
	}

	close SCORES;
}

####
# This sub will write out the score file.
sub write_score_struct {
	open (SCORES, "> $scorefile") or die $!;

	# Traverse over the data structure.
	foreach my $newsgroup (keys %scores) {
		foreach my $epochtime (sort keys %{$scores{$newsgroup}}) {
			print SCORES "%Score created by slrn on ";
			print SCORES scalar localtime($epochtime);
			print SCORES "\n\n";
			print SCORES "[$newsgroup]\n";
			print SCORES "Score: $scores{$newsgroup}->{$epochtime}->{SCORE}\n";
			print SCORES "\t$scores{$newsgroup}->{$epochtime}->{HEADER}: " .
							"$scores{$newsgroup}->{$epochtime}->{MATCH}\n\n";
		}
	}

	close SCORES;
}

####
# Print out the list of newsgroups and the scores count.
sub show_newsgroups {
	print "Scores exist on these newsgroups:\n";
	print "---------------------------------\n\n";

	foreach (sort keys %scores) {
		print "$_: ";
		printf "%d score%s\n", scalar keys %{$scores{$_}},
				scalar keys %{$scores{$_}} == 1 ? "" : "s";
	}

	print "\n";
}

####
# Expire scores in a newsgroup on a given date.
sub expire {
	my ($newsgroup, $epochtime) = @_;

	foreach (keys %{$scores{$newsgroup}}) {
		delete $scores{$newsgroup}->{$_} if $_ < $epochtime;
	}

	# If there are no more scores for a newsgroup, we can delete it, too.
	delete $scores{$newsgroup} unless scalar keys %{$scores{$newsgroup}};

}

