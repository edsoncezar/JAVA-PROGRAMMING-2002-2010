#!/usr/bin/perl -w

=head1 NAME

cut-file.pl - to cut a large file into smaller pieces so that they can be copied from one computer to another piece by piece.

=head1 DESCRIPTION

Run "%perl cut-file.pl -help" to find out command-line options.

=head1 README

cut-file.pl - to cut a large file into smaller pieces so that they can be copied from one computer to another piece by piece.

Run "%perl cut-file.pl -help" to find out command-line options.

To download, visit: 
  http://www.perl.com/CPAN 
or 
  http://www.freshmeat.net
or
  http://belmont-shores.ics.uci.edu

$Date: 2000/04/26 17:20:10 $ by Chang Liu (liu@ics.uci.edu | changliu@acm.org)

=head1 PREREQUISITES

Getopt::Long;;
Term::ANSIColor qw(:constants);

=head1 COREQUISITES

=pod OSNAMES

any

=pod SCRIPT CATEGORIES

CPAN/Administrative
CPAN

=head2 Example 1

 cut-file.pl -size 1000000 -order 3 redhat-6.2-i386.iso
 
 generate the 3rd piece from the hugh ISO CD image file.

=head1 ENVIRONMENT VARIABLES

 DEBUG : enable detailed debug info print out

=cut


my $VERSION = 1.0;

############# USAGE ##############################
#
#

sub usage
{
    print <<END_OF_USAGE;
Usage:
perl cut-file.pl OPTIONS InputFileName

OPTIONS:
    [-debug] default is false
    [-verbose] default is false
    [-size SIZE] default is 100 000 000
    [-block BLOCKSIZE] default is 1 000 000 (the bigger, the quicker, but it depends on the amount of memory you have)
    [-output OUTPUTFILENAME] default is InputFileName
    [-order ORDER] default is 0, which means all pieces are generated
EXAMPLE:
    %perl cut-file.pl -size 230000000 -output ./redhat /mnt/hdb1/redhat-6.2-i386.iso

NOTE:
To merge them together, just use:
%cat file.1 file.2 file.3 file.4 > file

END_OF_USAGE

    print 'Last modified: $Date: 2000/04/26 17:20:10 $ by Chang Liu (liu@ics.uci.edu | changliu@acm.org)';
    print "\n";
    exit;
}

#
#
############# END OF USAGE #########################

if ($ENV{DEBUG}) {
    print "DEBUG:cut-file.pl VERSION: $VERSION\n";
}

use Getopt::Long;;
use Term::ANSIColor qw(:constants);

############# Command Line options processing and default values ##################
#
# If you want to change the default behavior of this script, modify the values here
# Please notice that for some of them, environment variables are honored.
#


my $opt_verbose;
my $opt_debug;
my $opt_size = 100000000;
my $opt_block = 1000000;
my $opt_order = 0;
my $opt_output;

GetOptions("verbose" => \$opt_verbose,
           "debug" => \$opt_debug,
           "size=s" => \$opt_size,
           "block=s" => \$opt_block,
           "order=s" => \$opt_order,
           "output=s" => \$opt_output,
           "help" => \$opt_help,
	   );

if (defined $opt_help) {
    &usage;
}


my $input_filename = shift or usage;

if (! defined $opt_output) {
     $opt_output = $input_filename;
}

#
#
########### END OF Command Line Options ###############

my $order = 0;
my $counter = 0;

  print BLUE, "read input-file [$input_filename] and generate the [$opt_order]th piece with size [$opt_size].\n", RESET if $opt_verbose;
  open(INPUT,"$input_filename") or die "Can't open file [$input_filename]\n";
  binmode(INPUT);

      if ($opt_block > $opt_size) {
        $opt_block = $opt_size;
        print "block size adjusted to $opt_block.\n";
      }
       my $pass_num = $opt_size / $opt_block;
       my $new_opt_size = $pass_num * $opt_block;
      if ($new_opt_size != $opt_size )
      {
         print "size adjusted to $new_opt_size.\n";
         $opt_size = $new_opt_size;
      }

  my $i;

  if ($opt_order == 0) {
     # generate all pieces
     $order = 0;
     print "size: $opt_size block: $opt_block pass_number: $pass_num\n" if $opt_debug;
     for (;;) {
       $order ++;
       $output_filename = "$opt_output" . "." . "$order";
       open(OUTPUT, ">$output_filename");
       binmode(OUTPUT);
       for ($i=0; $i<$pass_num; $i++) {
         if(read(INPUT, $buff, $opt_block)) {
           print OUTPUT $buff;
         } else {
           close (OUTPUT);
           close (INPUT);
           print "$order files are generated.\n";
           exit;
         }
       }
       close (OUTPUT);
     }
  } else {
     # generating only one piece
  print RED, "Sorry, generating one file only is not implemented yet. My hard disk has been able to handle all files so far. It's trivial to do so, though.\n", RESET;
  }

  close (INPUT);














