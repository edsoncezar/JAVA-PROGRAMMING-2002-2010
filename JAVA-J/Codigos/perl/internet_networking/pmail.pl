#!/usr/bin/perl -w

use Time::Local;
use strict;

my $mbox = shift || "/home/lukas/mbox";
my $outputdir = shift || "/home/lukas/tmp/test";

my %monthmap = (
	Jan => 1,
	Feb => 2,
	Mar => 3,
	Apr => 4,
	May => 5,	
	Jun => 6,
	Jul => 7,
	Aug => 8,
	Sep => 9,
	Oct => 10,
	Nov => 11,
	Dec => 12
);

my %mails;

&read_mbox();
&print_html();
&generate_index();

####
# Generate a HTML file for each email.
sub print_html {
	my $count = 1;

	for (sort { $mails{$a}->{date} <=> $mails{$b}->{date} } keys %mails) {
#		warn "WARNING: overwriting $outputdir/mail$count.html\n" 
									#if (-f "$outputdir/mail$count.html");

		open HTML, "> $outputdir/mail$count.html" 
					or die "Couldn't open $outputdir/mail$count.html: $!\n";

		my $html = qq(<html>
	<head>
		<title>$mails{$_}->{subject}</title>
	</head>
	<body bgcolor="#ffffff">

		<center>
		<h1>$mails{$_}->{subject}</h1>
		</center>
		<p align="center"><small>[ <a href="index.html#$count">thread</a> | <a href="date.html#$count">date</a> | <a href="subject.html#$count">subject</a> | <a href="author.html#$count">author</a> ]</small></p>
		<hr>
		);

		my $date = scalar localtime($mails{$_}->{date});
		my $from = &html_escape($mails{$_}->{from});
		my $body = &html_escape($mails{$_}->{body});

		$html .= qq(
		<b>From:</b> $from<br>
		<b>Subject:</b> $mails{$_}->{subject}<br>
		<b>Date:</b> $date<br>
		<hr>
		$body
		<hr>
		<p align="center"><small>[ <a href="index.html#$count">thread</a> | <a href="date.html#$count">date</a> | <a href="subject.html#$count">subject</a> | <a href="author.html#$count">author</a> ]</small></p>
	</body>
</html>
		);

		print HTML $html;
		close HTML;

		$mails{$_}->{html} = "mail$count.html";

		$count++;

	}

}

####
# Generate the index files.
sub generate_index {

	my $countmsg = scalar keys %mails;

	# Sorted by thread.
	open INDEX, "> $outputdir/index.html" or die "Couldn't open $outputdir/index.html: $!\n";
	my $html = qq(<html>
	<head>
		<title>Mailbox</title>
	</head>

	<body bgcolor="#ffffff">
		<center><h1>Mailbox</h1></center>
		<p align="center">
		<b>$countmsg messages.</b><br>
		Ordered by thread.<br>
		<small>Order by [ <a href="date.html">date</a> | <a href="subject.html">subject</a> | <a href="author.html">author</a> ].</small>
		</p>
		<hr>
		<ul>
	);

	for (sort { $mails{$a}->{date} <=> $mails{$b}->{date} } keys %mails) {
		next if $mails{$_}->{seen_thread};
		my $date = scalar localtime($mails{$_}->{date});
		my $from = &strip_email($mails{$_}->{from});
		my $anchor = $mails{$_}->{'html'};
		$anchor =~ s/mail(\d+)\.html/$1/;
		$html .= qq|
			<li><b><a href="$mails{$_}->{'html'}">$mails{$_}->{'subject'}</a></b> <i><small><a name="$anchor">$from ($date)</a></small></i></li>
		|;

		$mails{$_}->{seen_thread}++;

		$html .= &check_replies($_);

	}

	$html .= qq(
		</ul>
		<hr>

	</body>
</html>
	);

	print INDEX $html;

	close INDEX;


	# Sorted by date.
	open DATE, "> $outputdir/date.html" 
						or die "Couldn't open $outputdir/date.html: $!\n";
	$html = qq(<html>
	<head>
		<title>Mailbox</title>
	</head>

	<body bgcolor="#ffffff">
		<center><h1>Mailbox</h1></center>
		<p align="center">
		<b>$countmsg messages.</b><br>
		Ordered by date.<br>
		<small>Order by [ <a href="index.html">thread</a> | <a href="subject.html">subject</a> | <a href="author.html">author</a> ].</small>
		</p>
		<hr>
		<ul>
	);

	for (sort { $mails{$a}->{date} <=> $mails{$b}->{date} } keys %mails) {
		my $date = scalar localtime($mails{$_}->{date});
		my $from = &strip_email($mails{$_}->{from});
		my $anchor = $mails{$_}->{'html'};
		$anchor =~ s/mail(\d+)\.html/$1/;
		$html .= qq|
			<li><b><a href="$mails{$_}->{'html'}">$mails{$_}->{'subject'}</a></b> <i><small><a name="$anchor">$from ($date)</a></small></i></li>
		|;
	}

	$html .= qq(
		</ul>
		<hr>

	</body>
</html>
	);

	print DATE $html;

	close DATE;


	# Sorted by subject.
	open SUBJECT, "> $outputdir/subject.html" 
					or die "Couldn't open $outputdir/subject.html: $!\n";
	$html = qq(<html>
	<head>
		<title>Mailbox</title>
	</head>

	<body bgcolor="#ffffff">
		<center><h1>Mailbox</h1></center>
		<p align="center">
		<b>$countmsg messages.</b><br>
		Ordered by subject.<br>
		<small>Order by [ <a href="index.html">thread</a> | <a href="date.html">date</a> | <a href="author.html">author</a> ].</small>
		</p>
		<hr>
		<ul>
	);

	foreach my $mail (sort { lc $mails{$a}->{clean_subject} cmp lc $mails{$b}->{clean_subject} } keys %mails) {
		next if $mails{$mail}->{seen_subject};
		$html .= qq(
			<li><b>$mails{$mail}->{clean_subject}</b></li>
			<ul>
		);
		foreach (keys %mails) {
			if ($mails{$_}->{clean_subject} eq $mails{$mail}->{clean_subject}) {
				my $date = scalar localtime($mails{$_}->{date});
				my $from = &strip_email($mails{$_}->{from});
				my $anchor = $mails{$_}->{'html'};
				$anchor =~ s/mail(\d+)\.html/$1/;
				$html .= qq|
				<li><a href="$mails{$_}->{html}">$from</a> <small><i><a name="$anchor">($date)</a></i></small></a></li>
				|;
				$mails{$_}->{seen_subject}++;
			}
		}
		$html .= "</ul>\n";
	}
			

	$html .= qq(
		</ul>
		<hr>

	</body>
</html>
	);

	print SUBJECT $html;
	
	close SUBJECT;


	# Sorted by author
	open AUTHOR, "> $outputdir/author.html" 
					or die "Couldn't open $outputdir/author.html: $!\n";

		$html = qq(<html>
	<head>
		<title>Mailbox</title>
	</head>

	<body bgcolor="#ffffff">
		<center><h1>Mailbox</h1></center>
		<p align="center">
		<b>$countmsg messages.</b><br>
		Ordered by author.<br>
		<small>Order by [ <a href="index.html">thread</a> | <a href="date.html">date</a> | <a href="subject.html">subject</a> ].</small>
		</p>
		<hr>
		<ul>
	);

	foreach my $mail (sort { lc $mails{$a}->{from} cmp lc $mails{$b}->{from} } keys %mails) {
		next if $mails{$mail}->{seen_author};
		my $from = &html_escape($mails{$mail}->{from});
		$html .= qq(
			<li><b>$from</b></li>
			<ul>
		);
		foreach (keys %mails) {
			if ($mails{$mail}->{from} eq $mails{$_}->{from}) {
				my $date = scalar localtime($mails{$_}->{date});
				my $anchor = $mails{$_}->{'html'};
				$anchor =~ s/mail(\d+)\.html/$1/;
				$html .= qq|
				<li><a href="$mails{$_}->{html}">$mails{$_}->{subject}</a> <small><i><a name="$anchor">($date)</a></i></small></a></li>
				|;
				$mails{$_}->{seen_author}++;
			}
		}
		$html .= "</ul>\n";
	}

	print AUTHOR $html;

	close AUTHOR;

	$html .= qq(
		</ul>
		<hr>

	</body>
</html>
	);

}

####
# Recursive subroutine the check for replies.
sub check_replies {
	my $id = shift;
	my $html;

	$html = "<ul>\n";
	foreach (sort { $mails{$a}->{date} <=> $mails{$b}->{date} } keys %mails) {
		next if $_ eq $id;
		next unless $mails{$_}->{refs};
		next if $mails{$_}->{seen_thread};
		if ($mails{$_}->{refs} eq $id) {
			my $date = scalar localtime($mails{$_}->{date});
			my $from = &strip_email($mails{$_}->{from});
			my $anchor = $mails{$_}->{'html'};
			$anchor =~ s/mail(\d+)\.html/$1/;
			$html .= qq|
				<a name="$anchor"></a>
				<li><b><a href="$mails{$_}->{html}">$mails{$_}->{subject}</a></b> <i><small><a name="anchor">$from ($date)</a></small></i></li>
			|;
			$mails{$_}->{seen_thread}++;
			$html .= &check_replies($_);
		}
	}
	$html .= "</ul>\n";

	return $html eq "<ul>\n</ul>\n" ? '' : $html;
}

####
# Beautify the output, create links of appropriate tags.
sub html_escape {
	my $thing = shift;

	$thing =~ s/</&lt;/g;
	$thing =~ s/>/&gt;/g;
	$thing =~ s/"/&quot;/g;
	$thing =~ s/\n/<br>/g;
	$thing =~ s!\b([-\w+.]+\@[-\w+.]+)\b!<a href="mailto:$1">$1</a>!g;
	$thing =~ s!\b(https?://[-\w?&/.+]+)\b!<a href="$1"></a>!g;

	return $thing;
}

####
# Strip the email address
sub strip_email {
	my $original = shift;

	my ($email) = $original =~ m/\b<[-\w.]+\@[-\w.]+>\b/;
	$original =~ s/<.*>//;

	return $original ? $original : $email;
}

####
# Read in the mailbox file and generate the data structure.
sub read_mbox {

	# This will be our message container.
	my $current;

	# This indicates, that the last line was blank, initially set to true,
	# so we can parse the first mail correctly.
	my $blank = 1;

	open MBOX, $mbox or die "Couldn't open mailbox $mbox: $!\n";

	while (<MBOX>) {

		# There was a blank line before, and this line looks like the beginning
		# of a new mail, so we need to take some action.
		if ($blank && /^From .*\d{4}$/) {

			# Save the message that we've parsed before (if there was one).
			$mails{$current->{message_id}} = $current 
										if scalar keys %{$current};

			# Create a new container for this message.
			$current = {};

			# Set the blank line to zero.
			$blank = 0;

		# We're still in the header part, so we save some.
		} elsif (!$blank && /^From: (.*)/i) {
			my $from = $1;
			$from =~ s/"//g;
			$current->{from} = $from;

		} elsif (!$blank && /^Subject: (.*)/i) {
			$current->{subject} = $1;
			my $clean_subject = $1;
			$clean_subject =~ s/Re: (.*)/$1/i;
			$current->{clean_subject} = $clean_subject;

		} elsif (!$blank && /^Message-Id: (.*)/i) {
			$current->{message_id} = $1;

		} elsif (!$blank && /^Date: (.*)/) {
			$current->{date} = parsedate($1);
			warn "Could parse date: $!\n" unless $current->{date};

		} elsif (!$blank && /^(?:References|In-Reply-To): (<.+>)/i) {
			$current->{refs} = @{ [ split(/ /, $1) ] }[-1];

		# There was a blank line before, but it wasn't catched by the if-
		# statement above, so it must be the message body.
		} elsif ($blank) {
			$current->{body} .= $_;
		}

		# Aha, we have a blank line. This could've been the end of the header.
		$blank = 1 if /^$/;
	}

	close MBOX;
}

sub parsedate {
	my $date = shift;
#	print $date, "\n";
	my ($wday, $mday, $mon, $year, $time, $hrs, $min, $sec);

	if ($date =~ /^\d\d?\s/) {
		($mday, $mon, $year, $time) = split(/ /, $date);
	} elsif ($date =~ /^\w{3},\s/) {
		($wday, $mday, $mon, $year, $time) = split(/,?\s+/, $date);
	}

	($hrs, $min, $sec) = split(/:/, $time);
	$mon = $monthmap{$mon};
	$mon--;
	$year -= 1900;
		
	return timelocal($sec, $min, $hrs, $mday, $mon, $year);

}

