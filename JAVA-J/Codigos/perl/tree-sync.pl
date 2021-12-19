#!/usr/bin/perl -w

=head1 NAME

tree-sync.pl - sync two directories recursively. The goal is to bring two trees exactly the same. However, tree-sync.pl does not perform any copy operation. It generates a command file instead. The user has the opportunity to exam the command file before real copy operations happen.

=head1 DESCRIPTION

This script compares two directories recursively and print out a report.
A batch file that can sync two trees is also generated. 

Run "tree-sync.pl -help" to find out command-line options.

=head1 README

tree-sync.pl - sync two directories recursively. The goal is to bring two trees exactly the same. However, tree-sync.pl does not perform any copy operation. It generates a command file instead. The user has the opportunity to exam the command file before real copy operations happen.

Run "tree-sync.pl -help" to find out command-line options.

To download, visit: 
  http://www.perl.com/CPAN 
or 
  http://www.freshmeat.net
or
  http://belmont-shores.ics.uci.edu

Copyright (c) 2000 Chang  Liu. All rights reserved.  
License: GPL: GNU General Public License (http://www.gnu.org/copyleft/gpl.html). 

$Date: 2000/04/26 17:35:37 $ by Chang Liu (liu@ics.uci.edu | changliu@acm.org)

=head1 PREREQUISITES

Getopt::Long;;
Term::ANSIColor qw(:constants);
FileHandle;

=head1 COREQUISITES

=pod OSNAMES

any

=pod SCRIPT CATEGORIES

CPAN/Administrative
CPAN

=head2 Examples

 %perl tree-sync.pl /home/chang/work/prj /mnt/f/backup/prj

or (relative path)

 %perl tree-sync.pl /home/chang/work/prj ../backup/prj

or (in a shell)
 
  C:/work>perl tree-sync.pl c:/work/prj d:/backup/prj

or (in DOS)

  C:\work>perl tree-sync.pl c:\work\prj d:\backup\prj


=head1 ENVIRONMENT VARIABLES

 DEBUG : enable detailed debug info print out

=head1 SYMBOLS

A <- B : B overwrite A
A -> B : A overwrite B
A ->   : copy A
  <- B : copy B
A -- B : please sync A and B manually

name/ : it's a directory

=cut


$VERSION = 1.0;

############# USAGE ##############################
#
#

sub usage
{
    print <<END_OF_USAGE;
PURPOSE:
Synchronize two directory trees.
USAGE:
perl tree-sync.pl OPTIONS DIR1 DIR2

OPTIONS:
    [-debug] default is false
    [-verbose] default is false
    [-cmd SYNC-CMD-FILENAME] default is sync-now.pl
    [-width SCREEN-WIDTH] default is 80
    [-os DOS|UNIX] default is DOS
    [-help]
EXAMPLE:
    c:/>perl tree-sync.pl -cmd mysync.pl /home/chang/work/prj /mnt/f/backup/prj
or
    %perl tree-sync.pl -width 200 -verbose /home/chang/work/prj /mnt/f/backup/prj

After that:
    %cat sync-now.pl
    %perl sync-now.pl
NOTE:
It's always safe to run tree-sync.pl as it doesn't perform any file copy operation.
Be careful with the generated command file, though, especially if you have different
clock settings on different machines.
END_OF_USAGE

    print 'Last modified: $Date: 2000/04/26 17:35:37 $';
    print "\n";
    exit;
}

#
#
############# END OF USAGE #########################

if ($ENV{DEBUG}) {
    print "DEBUG:tree-sync.pl VERSION: $VERSION\n";
}

use Getopt::Long;;
use Term::ANSIColor qw(:constants);
use FileHandle;

############# Command Line options processing and default values ##################
#
# If you want to change the default behavior of this script, modify the values here
# Please notice that for some of them, environment variables are honored.
#

my $opt_verbose;
my $opt_debug;
my $opt_cmd = "sync-now.pl";
my $opt_width = 80;
my $opt_os = "DOS";
my $opt_help;

GetOptions("verbose" => \$opt_verbose,
           "debug" => \$opt_debug,
           "cmd=s" => \$opt_cmd,
           "width=s" => \$opt_width,
           "os=s" => \$opt_os,
           "help" => \$opt_help,
	   );

if (defined $opt_help) {
    &usage;
}

my $count_files = 0;
my $count_dirs = 0;
my $count_diffs = 0;

my $sourceDirectory = shift or usage;
my $targetDirectory = shift or usage;

my $flag_first_report = 1;


#
#
########### END OF Command Line Options ###############

my $date = scalar localtime;

print "Generating commands to the command file [$opt_cmd]...\n" if $opt_verbose;
open(CMD,">$opt_cmd") or die "Can't open command file [$opt_cmd] to write.\n";
print CMD "#!/usr/bin/perl -w\n";
print CMD "\n";
print CMD "# This file is generated on $date by tree-sync.pl to sync directories [$sourceDirectory] and [$targetDirectory].\n";
print CMD "# Please do not edit.\n";
print CMD "\n";
print CMD "use File::Copy;\n";
print CMD "\n";

    my $format = "format STDOUT_TOP =\n";
    my $i ;

    $format = $format . "DIR 1:\@";
    for ($i=0; $i< $opt_width/2 -7; $i++)
    {
	$format = $format . "<";
    }
    $format = $format . " DIR 2:\@";
    for ($i=0; $i< $opt_width/2 -7; $i++)
    {
	$format = $format . "<";
    }
    $format = $format . "\n";
    $format = $format . "\$sourceDirectory, \$targetDirectory\n";
    for ($i=0; $i< $opt_width -1 ; $i++)
    {
	$format = $format . "-";
    }
    $format = $format . "\n.\n";

    $format = $format . "format STDOUT=\n";
    $format = $format . "\@";
    for ($i=0; $i< $opt_width/2 -2; $i++)
    {
	$format = $format . "<";
    }
    $format = $format . " \@< \@";
    for ($i=0; $i< $opt_width/2 -2; $i++)
    {
	$format = $format . "<";
    }
    $format = $format . "\n";
    $format = $format . "\$left, \$op, \$right\n";
    $format = $format . ".\n";

    print $format if $opt_debug;

# Here's what these two format will look like if width is 80:
#
#format STDOUT_TOP=
##        1         2         3         4         5         6         7         8
##2345678901234567890123456789012345678901234567890123456789012345678901234567890
#DIR 1:@<<<<<<<<<<<                      DIR 2:@<<<<<<<<<<<<<<<
#      $sourceDirectory,                       $targetDirectory
#-------------------------------------------------------------------------------
#.
#
#format =
##        1         2         3         4         5         6         7         8
##2345678901234567890123456789012345678901234567890123456789012345678901234567890
#@<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< @< @<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
#$left,                                $op, $right
#.

    my $left;
    my $right;
    my $op;

	eval $format;
	die $@ if $@;

sub report
{
    $left = shift;
    $op = shift;
    $right = shift;

    $count_diffs ++;
    write;
}

sub numberOfFiles
{
    my $dir = shift;
    opendir(DIR, $dir) or die "Can't open directory [$dir].\n";
    my @files = readdir(DIR);
    return scalar @files;
}

sub tree_sync
{
    my $sourceDir = shift;
    my $targetDir = shift;

    opendir(SOURCEDIR, $sourceDir) or die "Can't open source directory [$sourceDir].\n";
    my @sourceFiles = sort readdir(SOURCEDIR);

    opendir(TARGETDIR, $targetDir) or die "Can't open target directory [$targetDir].\n";
    my @targetFiles = sort readdir(TARGETDIR);

    foreach $sourceFileName (@sourceFiles) {
	print "SOURCE: [$sourceDir] [$sourceFileName]\n" if $opt_debug;
    }
    
    foreach $targetFileName (@targetFiles) {
	print "TARGET: [$targetDir] [$targetFileName]\n" if $opt_debug;
    }

    $sourceFileName = shift (@sourceFiles);
    $targetFileName = shift (@targetFiles);

    for (;;) {

	if (!defined $sourceFileName) {
	    if (!defined $targetFileName) { # end of sync
		last;
	    } else {
		print CMD "copy(\"$targetDir/$targetFileName\", \"$sourceDir\");\n";
		report ("", "<-", "$targetDir/$targetFileName");
		foreach $targetFileName (@targetFiles) {
		    print CMD "copy(\"$targetDir/$targetFileName\", \"$sourceDir\");\n";
		    report ("", "<-", "$targetDir/$targetFileName");
		}
		last;
	    }
	} elsif (!defined $targetFileName){
	    print CMD "copy(\"$sourceDir/$sourceFileName\", \"$targetDir\");\n";
	    report ("$sourceDir/$sourceFileName", "->", "");
	    foreach $sourceFileName (@sourceFiles) {
		print CMD "copy(\"$sourceDir/$sourceFileName\", \"$targetDir\");\n";
		report ("$sourceDir/$sourceFileName", "->", "");
	    }
	    last;
	}

	my ($devS, $inoS, $modeS, $nlinkS, $uidS, $gidS, $rdevS, $sizeS, $atimeS, $mtimeS, $ctimeS, $blksizeS, $blocksS) = stat "$sourceDir/$sourceFileName";
	my ($devT, $inoT, $modeT, $nlinkT, $uidT, $gidT, $rdevT, $sizeT, $atimeT, $mtimeT, $ctimeT, $blksizeT, $blocksT) = stat "$targetDir/$targetFileName";
	$mtimeSs = scalar(localtime($mtimeS));
	$mtimeTs = scalar(localtime($mtimeT));

	if ($sourceFileName lt $targetFileName) {
	    if (-d "$sourceDir/$sourceFileName" ) {
		my $n = numberOfFiles("$sourceDir/$sourceFileName");
		if ($opt_os eq "DOS") {
		    print CMD "system(\"copy $sourceDir/$sourceFileName $targetDir\");\n";
		} else {
		    print CMD "system(\"cp -r $sourceDir/$sourceFileName $targetDir\");\n";
		}
		report ("$sourceDir/$sourceFileName ($n files $mtimeSs)", "->", "");
	    } else {
		print CMD "copy(\"$sourceDir/$sourceFileName\", \"$targetDir\");\n";
		report ("$sourceDir/$sourceFileName ($sizeS $mtimeSs)", "->", "");
	    }
	    $sourceFileName = shift (@sourceFiles);
	} elsif ($sourceFileName gt $targetFileName) {
	    if (-d "$targetDir/$targetFileName" ) {
		my $n = numberOfFiles("$targetDir/$targetFileName");
		if ($opt_os eq "DOS") {
		    print CMD "system(\"copy $targetDir/$targetFileName $sourceDir\");\n";
		} else {
		    print CMD "system(\"cp -r $targetDir/$targetFileName $sourceDir\");\n";
		}
		report ("". "<-", "$targetDir/$targetFileName ($n files $mtimeTs)");
	    } else {
		print CMD "copy(\"$targetDir/$targetFileName\", \"$sourceDir\");\n";
		report ("", "<-", "$targetDir/$targetFileName ($sizeT $mtimeTs)");
	    }
	    $targetFileName = shift (@targetFiles);
	} elsif ( -d "$sourceDir/$sourceFileName" ) { # sync recursively
	    if (! -d "$targetDir/$targetFileName" ) {
		my $n = numberOfFiles("$sourceDir/$sourceFileName");
		report ("$sourceDir/$sourceFileName/ ($n files $mtimeSs)", "??", "$targetDir/$targetFileName ($sizeT $mtimeTs)");
	    } else {
		if ( $sourceFileName eq "." || $sourceFileName eq ".." ) {
		    # skip . and ..
		} else {
		    print "syncing [$sourceDir/$sourceFileName] and [$targetDir/$sourceFileName] ...\n" if $opt_verbose;
		    $count_dirs ++;
		    tree_sync( "$sourceDir/$sourceFileName", "$targetDir/$sourceFileName");  
		}
	    }
	    $sourceFileName = shift (@sourceFiles);
	    $targetFileName = shift (@targetFiles);
	} elsif (-d "$targetDir/$targetFileName") {
	    my $n = numberOfFiles("$sourceDir/$sourceFileName");
	    report ("$sourceDir/sourceFileName ($sizeS $mtimeSs)", "??", "$targetDir/$targetFileName/ ($n files $mtimeTs)");
	    $sourceFileName = shift (@sourceFiles);
	    $targetFileName = shift (@targetFiles);
        } else {
	    if ($sizeS != $sizeT) {
		if ($mtimeS > $mtimeT) {
		    print CMD "copy(\"$sourceDir/$sourceFileName\", \"$targetDir\");\n";
		    report("$sourceDir/$sourceFileName ($sizeS $mtimeSs)", "->", "$targetDir/$targetFileName ($sizeT $mtimeTs)");
		} elsif ($mtimeS < $mtimeT) {
		    print CMD "copy(\"$targetDir/$targetFileName\", \"$sourceDir\");\n";
		    report("$sourceDir/$sourceFileName ($sizeS $mtimeSs)", "<-", "$targetDir/$targetFileName ($sizeT $mtimeTs)");
		} else {
		    report("$sourceDir/$sourceFileName ($sizeS $mtimeSs)", "??", "$targetDir/$targetFileName ($sizeT $mtimeTs)");
		}
	    } else {
		print "[$sourceFileName] has the same size: $sizeS in [$sourceDir] $sizeT in [$targetDir]\n" if $opt_debug;
		$count_files ++;
		# put more comparison here ... 
	    }
				 
	    $sourceFileName = shift (@sourceFiles);
	    $targetFileName = shift (@targetFiles);
        }
    }
}

tree_sync($sourceDirectory, $targetDirectory);
    
print "\nIn addition to these $count_diffs differences, there are $count_files identical files in $count_dirs identical directories in [$sourceDirectory] and [$targetDirectory].\n";
print "The generated command file $opt_cmd can bring these two directories in sync.\n";
print "You must run it under current directory.\n";
print "You can run it using command \"%perl $opt_cmd\" or \"C:\\>perl $opt_cmd\".\n";

close (CMD);








