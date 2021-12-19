#!/usr/bin/perl -w
#
# pod2xml.pl

#
# main routine
#

my $VERSION  = '0.1b';
my $mail     = "j.giebels\@instant-karma.de";
my $web      = "http://www.instant-karma.de";
my $reldate  = "03/03/2000";
my $infile   = shift;
my $outfile  = shift;

if ($infile eq "-h" or $infile eq "--help" or $infile eq "-?" or $infile eq "/?") {
	&usage;
}
elsif ($infile) {
	&Input;
}
else {
	print "\nusage:\n\n$0 --infile=<name> --outfile=<name>\n\n$0 --option=<[-h][--help][/?][-?]> for help\n";
}

#
# usagepage at option -h --help -? /?
#

sub usage {
	$usage =<<END_OF_USAGE;

	usage:  $0 --infile=<name> --outfile=<name>
	        $0 --otion=<[-h][--help][/?][-?]>

	  --help       - prints this message.
	  -h
	  /?
	  -?

	  --infile     - filename for the pod to convert from

	  --outfile    - filename for the resulting xml file

	$0, version $VERSION, released $reldate

	please send bugreports, suggestions and related stuff to
	$mail
	
	the dokumentation is provided within this package as pod
	or visit $web for further details and help.
	thank you for using $0

END_OF_USAGE
	print $usage;
}

#
# sub input - read infile from given name $infile
#

sub Input {
	open (INPUT, "$infile") or die "error: can't open $infile\ncause: $!\n";
	while (<INPUT>) {
		if (/(^=\w*)/ .. /(^=cut)/) {
			push @rawdoku, $_;
		}
	}
	&XMLout;
}

#
# sub XMLout - XML output to given filename $outfile
#
# parameter:
#
# $outfile - filename for xml-output
# $line    - string for xml-conversion

sub XMLout {
	# create xml-file and a valid header
	open (OUTPUT, "> $outfile") or die "error: can't open $outfile\cause: $!\n";
	print OUTPUT "<?xml version=\"1.0\"?><pod2xml_$infile>";
	$ih1 = 0;$ih2 = 0;$it  = 0;$ib = 0;$io = 0;
	# check for pod-tag
	foreach $line(@rawdoku) {
		if ($line =~ /(^=\w*\b)/) {

			# split pod-tags
			$line =~ s/(^=\w*\b)//;
			$value = $line;
			$line = $1;
			$line =~ s/^=//;
			chomp $line;
			chomp $value;

			# kill other tags
			$value =~ s/E<\w*>//g; # remove escapes
			$value =~ s/[A-Z]{1}?<//g; # remove .<
			$value =~ s/>//g; # remove >
			$value =~ s/<//g; # remove <
			$value =~ s/&//g; # remove &

			if (not $line) {
				$line = $value;
			}
			if ($line eq "head1") {
				if ($ih1 == 2) {
					print OUTPUT "</headline1>";
					$ih1 = 1;
				}
				else {
					$ih1 = 1;
				}
			}
			elsif ($line eq "head2") {
				if ($ih2 == 2) {
					print OUTPUT "</headline2>";
					$ih2 = 1;
				}
				else {
					$ih2 = 1;
				}
			}
			elsif ($line eq "over") {
				if ($ih1 == 2) {
					print OUTPUT "</headline1>";
					$ih1 = 0;
				}
				elsif ($ih2 == 2) {
					print OUTPUT "</headline2>";
					$ih2 = 0;
				}
				$io = 1;
				print OUTPUT "<list>$value";
			}
			elsif ($line eq "back") {
				if ($it == 2) {
					print OUTPUT "</item>";
					$it = 0;
				}
				$ib = 1;
				print OUTPUT "</list>";
			}
			elsif ($line eq "item") {
				if ($it == 2) {
					print OUTPUT "</item>";
					$it = 1;
				}
				else {
					$it = 1;
				}
			}
			elsif ($line eq "cut") {
				$line = "";
			}
			elsif ($line eq "pod") {
				$line = "";
			}
		}
		if ($ih1 == 1) {
			if ($ih2 == 2) {
				print OUTPUT "</headline2>";
				$ih2 = 0;
			}
			elsif ($it == 2) {
				print OUTPUT "</item>";
				$it = 0;
			}
			print OUTPUT "<headline1>$value";
			$ih1 = 2;
		}
		elsif ($ih2 == 1) {
			if ($ih1 == 2) {
				print OUTPUT "</headline1>";
				$ih1 = 0;
			}
			elsif ($it == 2) {
				print OUTPUT "</item>";
				$it = 0;
			}
			print OUTPUT "<headline2>$value";
			$ih2 = 2;
		}
		elsif ($it == 1) {
			if ($ih1 == 2) {
				print OUTPUT "</headline1>";
				$ih1 = 0;
			}
			elsif ($ih2 == 2) {
				print OUTPUT "</headline2>";
				$ih2 = 0;
			}
			print OUTPUT "<item>$value";
			$it = 2;
		}
		elsif ($ib == 1) {
			$ib = 0;
		}
		elsif ($io == 1) {
			$io = 0;
		}
		else {
			# kill other tags
			$line =~ s/E<\w*>//g; # remove escapes
			$line =~ s/[A-Z]{1}?<//g; # remove .<
			$line =~ s/>//g; # remove >
			$line =~ s/<//g; # remove <
			$line =~ s/&//g; # remove &
			print OUTPUT "<content>$line</content>";
		}
	}
	print OUTPUT "</headline1>" if ($ih1 == 2);
	print OUTPUT "</headline2>" if ($ih2 == 2);
	print OUTPUT "</item>" if ($it == 2);
	print OUTPUT "<content>this XML-stream was created with pod2xml v.$VERSION</content></pod2xml_$infile>";
	print "\neverything is pretty fine.\nyour xml-file: $outfile was created successfully.\nthank you for using $0, $VERSION.\n";
}

############################################################

=head1 name

pod2xml - perl script for converting pod-dokumentation into a valid xml-file

=head1 synopsis

    pod2xml --infile=E<lt>[name]E<gt> --outfile=E<lt>[name]E<gt>
    
    pod2xml --option=E<lt>[-h][--help][-?][/?]E<gt>

=head1 history

v. 0.1b		03/03/2000	release of first beta version

v. 0.01a	02/20/2000	initial version

=head1 description

pod2xml is a perl based script, that helps you, converting pod (embedded dokumentation) to
a valid XML file version 1.0. this is used, e.g. if you want to build a xml-dokumentation 
server.
the output is hard to edit, but feel free do edit the code for your use. only thing to do,
is to leave this dokumentation untouched. it would be nice, if you send me a modified version.

=head1 README

pod2xml converts embedded perl dokumentation,
known as pod, to a valid xml-stream.
the inputfile can be any ascii formatted file
with pdo-tags included.
this is a very early beta-version, so use it
carefully.

=head1 arguments

the following arguments can be used with pod2xml

=over 4

=item --infile=E<lt>[name]E<gt>

the inputfile wich contains the pod-dokumentation. it can be a *.pl, *.pm or *.pod file
but also any other inputfile in ascii format with pod-tags.

=item --outfile=E<lt>[name]E<gt>


the outputfile wich contains the xml-stream. we suggest to use *.xml extension but also any

other outputfilename can be given.

=item --option=E<lt>[-h][--help][-?][/?]E<gt>

display the helppage of pod2xml

=back

=head1 future

-easier configuration of output via configfile

-support of EE<lt>escapeE<gt> sequences

=head1 limitations

pod-tags not implemented yet:

cut - planned in further version
pod - planned in further version
FMT - these tags are only for the FMT formater


format codes not implemented yet:

EE<lt>escapeE<gt> - will be removed completely
FE<lt>fileE<gt>   - filename, path to file will be shown
XE<lt>indexE<gt>  - indexentry, entry will be shown, none indexing

BE<lt>textE<gt> - bold, ignored
CE<lt>codeE<gt> - nonepropotional for code, ignored
IE<lt>textE<gt> - cursiv, ignored
LE<lt>nameE<gt> - link, ignored
SE<lt>textE<gt> - spacebreak, ignored
ZE<lt>E<gt>     - zero character, ignored

not supported keys:

& - et
< - smaller
> - greater

=pod OSNAMES

any

=pod SCRIPT CATEGORIES

CPAN/Administrative

=head1 author

jan giebels, E<lt>j.giebels@instant-karma.deE<gt>.

=head1 bugs

please send bugreports, suggestions and related stuff to j.giebels@instant-karma.de
or visit http://www.instant-karma.de for further versions, details and help.

=head1 see also

perl(1)

=head1 copyright

this program is distributed under the artistic license.

=cut
