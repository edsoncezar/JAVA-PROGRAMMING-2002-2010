#!/usr/bin/perl -w
#	eXpanium converter tool V0.0.2
#
#   credits to a, 
tye, mkmcconn and all the other people at perlmonks.org who helped me with regexps!
#
# This program should convert MP3 file names into the dos format (8.3)
# and store  the original names into a file. Once the names are convertet the
# procedure can be reversed with said file.
#
# This program is used to make mp3 files burnable with mkisofs since this program
# does not preserve the .mp3 extension on files that would have the same filename when
# shortened to 8.3 format. 
#
# The program takes directories or filenames as parameters. See source code for details :-)
#
# This program is free software; you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation; either version 2 of the License, or
# (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU Library General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program; if not, write to the Free Software
# Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.

use strict;
use File::Copy;		# If somebody knows a more comfortable way to copy a file with perl please let me know

my (@newfiles);
my (@directories);
my (@mp3files);

my $logfile;
my $recover = 0;
my $alttarget = 0;		# if 1: another directory as the current is used to store the renamed / recovered files
my $TargetDir; 		# Place were all the files will go to
my $version = "V0.0.2";


sub checkargs {

my $Dateiname;
my @arguments = @ARGV;
my @dirs;


	while ($Dateiname = shift @arguments){
		if ($Dateiname eq "-r"){
			print "Recovering activated...";
		 	$recover = 1;
			$Dateiname = shift @arguments or fail("source directories missing.\n");
		}
		if ($Dateiname eq "-t"){
			$TargetDir = shift @arguments or fail("target directory missing.\n");
			print "using alternative dir $TargetDir to store files...";
			$alttarget = 1;
			if ($TargetDir =~/\/|\\$/) {chop $TargetDir;}		#if directory name ends with a slash or a backslash throw it away
			if (!-e ($TargetDir)){
				print("$TargetDir does not exist. Creating $TargetDir..\n");
				system ("mkdir","$TargetDir") or die "unable to create $TargetDir: $!\n";
			}elsif (!-d $TargetDir){ fail("$TargetDir is not a directory.\n");}
			$Dateiname = shift @arguments or fail("source directory is missing.\n");
		}
		if ($Dateiname eq "--help") {morehelp();}
		(-d $Dateiname) or fail("$Dateiname is not a directory. Please check your arguments.\n");
		if ($Dateiname =~m/\/|\\$/) {chop $Dateiname;}		#if directory name ends with a slash or a backslash throw it away
		push (@dirs, $Dateiname);
	}
	return @dirs;
}

sub createfilenames {

my $dir = $_[0];
my @files;
my $file;


	opendir(VERZEICHNIS,$dir) || die "Cannot open directory $dir: $!\n";
	while ($file = readdir(VERZEICHNIS)){
		
		@files = (@files, $file);
	}
	closedir(VERZEICHNIS);
	return @files;
}

sub shortenfilenames  {

my $equalfilenumber = 0;
my $file = "";
my $oldfile = "";
my $shortenedname = "";
my @FileArray = @_;
my @ShortFiles;



	@FileArray = sort (@FileArray);		# sorts the Array before we can shorten longer filenames

	foreach $file (@FileArray){
	
		if (length($file)>13){
	
			$shortenedname = substr($file, 0, 8);
			$shortenedname = $shortenedname.".mp3";
		} else {$shortenedname = $file;}
		
		

		if ($shortenedname eq $oldfile){
			if (length($equalfilenumber) > 7){
				print "WARNING: TOO MANY SIMILAR FILES! CANNOT SHORTEN ANY MORE FILES THAT HAVE THE SAME BEGINNING AS ".$shortenedname."\n";
				exit(1);
			}
			if (length($file) > 13){
				$shortenedname = substr($shortenedname, 0, 8-length($equalfilenumber));
			}else {	
				$shortenedname = substr($shortenedname, 0, (length($file)-length($equalfilenumber)-4));
			}
			$shortenedname = $shortenedname.$equalfilenumber.".mp3";
			$equalfilenumber++;
		} else {
			$equalfilenumber = 0;
			$oldfile = $shortenedname;
		}
		push (@ShortFiles,$shortenedname);
	}
	return @ShortFiles;
}

sub removenonmp3 {

my @args = @_;
my $file;
my @mp3files;

	while ($file = shift @args){
		if ($file =~ /\.mp3$/i){
			push(@mp3files, $file);
		}
	}
	return @mp3files;
}

sub renamefiles{

my $file;
my $newfile;
my $sourcedir = $_[0];

	open(LOGFILE, $logfile) or die "Unableto open  $logfile!: $!\n";
	
	while ($file = shift(@newfiles)){
		$newfile = shift @mp3files;
		if ($alttarget) {
			# necessary to keep out oold files in the old directory (rename removes them)
			copy("$sourcedir/$file","$TargetDir/$newfile") or die "Cannot copy files: $!";			
		}
		else {rename("$sourcedir/$file", "$sourcedir/$newfile") or die "Cannot rename $file in $newfile: $!.\n";}
		print LOGFILE "$file:$newfile\n" or die "Cannot write to $logfile: $! !\n";
	}
	close(LOGFILE);
}

sub convert {

	my $directory;	

	foreach $directory (@directories){
		print "Getting mp3 files...";
		@newfiles = createfilenames($directory);
		@newfiles = removenonmp3(@newfiles);
		print "done.\n";
		
		print "Shortening names...";
		@mp3files = shortenfilenames(@newfiles);
		print "done.\n";
		
		if ($alttarget == 1) {
			if (-e "$TargetDir/eXpanium.log"){
				die "Logfile in alternative $TargetDir already exists.\nAborting now to avoid loss of older names.\n";
			} else {$logfile = ">$TargetDir/eXpanium.log";}
		}
		elsif (-e "$directory/eXpanium.log"){die "Logfile in $directory already exists. \nAborting now to avoid loss of older names.\n";}
		else {$logfile = ">$directory/eXpanium.log";}
		print "\nChange log: $logfile.\n";
		
		@newfiles = sort(@newfiles);
		
		print "Renaming files ...";
		renamefiles($directory);
		print "done.\n";
	}
}

sub recoverfiles{

my $dir = $directories[0];
my $line;
my ($oldname, $newname);
my @names;
	
	
	foreach $dir (@directories){
		$logfile = "$dir/eXpanium.log";
		# first look for the log file
		print "Logfile is: $logfile\n";
		(-e ("$logfile")) or die "There seems to be no log file $logfile in $dir. Recovering is not possible sorry.\n";}
		print "$dir\n";
		open(LOGFILE, "$logfile") or die "Cannot open /$logfile: $!\n";
		print "Recovering...";
		while ($line = <LOGFILE>){
			@names = split(/:/, $line);
			$oldname = shift @names;
			$newname = shift @names;
			chop($newname);	# split leaves a newline character behind
			if ($alttarget){ copy ("$dir/$newname","$TargetDir/$oldname") or die "Cannot copy filename: $!";}
			else {rename ("$dir/$newname", "$dir/$oldname") or die "Cannot change filename $dir/$newname to $dir/$oldname: $!\n";}
		}
		print "done.\n";
		if ($alttarget == 0){
			print "Deleting logfile...";
			if (system ("rm","$logfile")){die "Unable to delete log file: $!\n";}
			print "done.\n";
		}
		close(LOGFILE);
}

sub shorthelp {
	print "eXpanium converter $version\n==========================================\n";
	print "Usage: eXpanium [-r] [-t targetdir] source directories\n";
	print "For more help try eXpanium --help\n"
}

sub morehelp { #:-)
	shorthelp();
	print"\n";
	print "More help is not available at the moment. Please try again later :-)\n";	
	exit(0);
}

sub fail {	# little function for the arg sorting at the beginning. should save a lot of unnecessary lines
my $Message = $_[0];
	shorthelp();
	print "$Message";
	exit(1);
}
		

sub main {
	@directories = checkargs;

	if ($recover) {
		print "Recovering files in following directories: @directories.\n";
		recoverfiles();
	} else {
		print "Changing filenames in: @directories.\n";
		convert();
		}
	print "Have a nice day!\n";
}

main();


