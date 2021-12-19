#!/usr/bin/perl

use warnings;
use strict;

if (!$ARGV[0]) {
	print "Usage: pinetoc inputfile outputfile\n";
	die;
}
open (INFILE, "<$ARGV[0]") or die "Could not open input file!\n";
open (OUTFILE, ">$ARGV[1]") or die "Could not open input file!\n";

##### Variables #####
my $From = "";		# Used to store the from address
my $Subject = "";		# Used to store the subject
my $Date = "";		# Used to store the message date
my $LetterNum = 0;		# Counts the number of emails
my $HeaderFlag = -1;		# Flag is < 0 when we're searching for a new email
			# Flag is > 0 and < 7 when we're getting header info
			# Flag is > 6 when we've found all the header info

##### Main Loop #####
while (<INFILE>){

	# Look for a new message (all messages have a header line beginning "X-UIDL: ")
	if (/^X-UIDL: \w{32}/) {

		if ($HeaderFlag > 0) {
			# We haven't got all the header info yet... but we'll write anyway
			&WriteTOCline ($LetterNum, $From, $Subject, $Date, $HeaderFlag);
		}

		$LetterNum++;

		# Clear the message data variables
		$HeaderFlag = 0;
		$From = "";
		$Subject = "";
		$Date = "";
	}
	if ($HeaderFlag < 0) {
		# Do nothing -- already found the header info, so we're searching for a new letter
	}
	elsif ($HeaderFlag < 7) {
		if ($_ =~ "^From:") {
			s/(From: |"|(\[|<)[^\]>](\]|>)|\n)//g;	# remove a buncha stuff to isolate the name
			s/^\s*|\s*$//g;				# remove leading or trailing whitespace
			$From = $_;
			$HeaderFlag += 1;
		}
		elsif ($_ =~ "^Subject:") {
			s/Subject:|\n//g;			# remove stuff to isolate the subject
			s/^\s*|\s*$//g;				# remove leading or trailing whitespace
			$Subject = $_;
			if ($Subject eq "") {
				$Subject = "(Blank subject)";
			}
			$HeaderFlag += 2;
		}
		elsif ($_ =~ "^Date:") {
			($Date) = ($_ =~ /Date: (\w+, \w+ \w+ \w+)/);
			$HeaderFlag += 4;
		}
	}
	else {
		# We've got all the header info
		&WriteTOCline ($LetterNum, $From, $Subject, $Date, $HeaderFlag);
		$HeaderFlag = -1;
	}
}

close INFILE;
close OUTFILE;
exit 0;

##### Subroutine for writing the TOC #####
sub WriteTOCline {
	my($LetterNum, $From, $Subject, $Date, $HeaderFlag) = @_;
	my @Error = ("","From", "Subject", "", "Date");

	my $ErrorNum = $HeaderFlag ^ 7;

	if ($ErrorNum > 7) {
		print "Error: Too much header info in letter $LetterNum titled '$Subject'\n";
	}
	elsif ($ErrorNum >0) {
		print "Error: Missing '$Error[$ErrorNum]' field in message $LetterNum\n";
	}
	
	# Write to output file (all cases)
	printf OUTFILE "%-4d  %-30.30s  %-20.20s  %-16.16s\n", $LetterNum, $Subject, $From, $Date or die "Could not write to output file!\n";
}    

