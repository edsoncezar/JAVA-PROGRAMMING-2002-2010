#! /usr/bin/perl -w

# To Do: more error checking, get --delimiter to work for \t. 

# cols2lines.pl takes an input file with fields separated 
# by the chosen delimiter (default is the tab stop) and 
# switches the columns into rows (and vice versa, 
# obviously). The script can be called with the input file, 
# or both input and output files as its arguments. If one or 
# both are omitted from the command line the user is prompted 
# for their location. The delimiter can be defined using the 
# -d or --delimiter switch. The delimiter may be double-
# quoted if necessary. In non-interactive mode the output 
# file will not overwrite an existing file unless the -f or 
# --force switch is used. Use the -h or --help
# switch to get a usage summary.

use strict;
use Getopt::Long;

# DECLARE GLOBALS
my $delimiter = "\t"; # default delimiter is tab stop 
my $infile = "";
my $outfile = "";

# PARSE ARGUMENTS
sub parseargs{
    my $force;
    my $helptext;
    &GetOptions("d|delimiter=s" => \$delimiter, 
                "f|force"       => \$force, 
                "h|help"        => \$helptext);
    if ($helptext){
        die ("Usage: cols2lines [-h] [-f] [-d <delimiter>]".
                        " [input file] [output file]\n");
    }
    if (@ARGV){
        $infile=shift (@ARGV);
        unless (-e $infile){die ("File \'$infile\' not found.\n");}
        $outfile=shift (@ARGV);
        if ((-e $outfile) and !$force){
            die ("File \'$outfile\' aleady exists. Use -f to".
                        " force overwrite.\a\n");
        }
    } else {
        $infile="";
        $outfile="";
    }
    return ($infile, $outfile);
}

# ASSIGN INPUT FILE
sub queryinfile {
    $infile="";
    until (-e $infile){
        print ("Please enter the name of the input file:\n");
        $infile = <STDIN>;
        chomp ($infile);
        unless (-e $infile){
            print STDERR ("File \'$infile\' not found. ".
                                        "Ctrl-C to exit.\a\n");
        }
    }
    $infile;
}

# ASSIGN OUTPUT FILE
sub queryoutfile {
    print ("Please enter the name of the output file:\n");
    $outfile = <STDIN>;
    chomp ($outfile);
    if (-e $outfile){
        print STDERR ("File \'$outfile\' aleady exists. ".
                                        "Overwrite? [Y\/N]\a\n");
        my $answer = <STDIN>;
        chomp ($answer);
        if (lc($answer) ne 'y'){die ("Script aborted by user.\n");}
    }
    $outfile;
}

# PERFORM OPERATION
sub bigfile_colstolines {
    my $infile = shift;
    my $outfile = shift;
    my $infilehandle = "<$infile";          # read-only
    open (INFILE, $infilehandle) or die ("File error.\a\n");
    my $outfilehandle = ">$outfile";       # write only
    open (OUTFILE, $outfilehandle) or die ("Output failure.\a\n");
    my $line = <INFILE>;
    my @testarray = split (/$delimiter/, $line);
    seek(INFILE,0,0);
    for (my $counter=0; $counter <= $#testarray; $counter++){
        my @columnarray = undef();
        while (defined ($line = <INFILE>)){
            chomp ($line);
            my @linearray = split (/$delimiter/, $line);
            push (@columnarray, $linearray [$counter]);
        }
        shift (@columnarray);               # removes unwanted characters
        my $newline = join $delimiter, (@columnarray);
        print OUTFILE ($newline, "\n");
        seek(INFILE,0,0);
    }
    close(INFILE);
    close(OUTFILE);
}

# MAIN LOOP

($infile, $outfile)=&parseargs;
unless ($infile){$infile=&queryinfile;}
unless ($outfile){$outfile=&queryoutfile;}
&bigfile_colstolines ($infile, $outfile);
print ("Done.\n");
exit

