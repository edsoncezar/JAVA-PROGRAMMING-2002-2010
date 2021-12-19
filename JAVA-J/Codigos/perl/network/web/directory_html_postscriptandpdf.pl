#!/usr/bin/perl -w

# h2p2p.pl
# pod at tail

use strict;

my $baseurl = 'http://mysite.dom/Christian';
my %bin = (
   html2ps => '/usr/bin/html2ps',
   ps2pdf  => '/usr/bin/ps2pdf',
   );
my %dir = (
   html => '/var/www/Christian/',
   pdf  => '/var/www/Christian/PrinterFriendly/PDF/',
   ps   => '/var/www/Christian/PrinterFriendly/PostScript/',
   );
my %ext =(
   page => 'html',
   ps   => 'ps',
   pdf  => 'pdf',
   );
my @excludes = (
   'christian.html',
   'christianlinks.html',
   'graceqs.html',
   'index.html',
   'underconstructionchristian.html',
   );


print"\n  Starting run of $0\n\n";
PrintLocs();


# Read $dir{html} for entries
opendir DIR, $dir{html}   or die "Error opening $dir{html}: $!";
my @files = (readdir DIR) or die "Error reading $dir{html}: $!";
closedir DIR              or die "Error closing $dir{html}: $!";


# Parse directory entries for HTML files
my @pages = grep(/\.html/, @files);


# process only desired HTML files, excluding unwanted
my %count;
for(@pages, @excludes) { $count{$_}++ }
my @includes = grep {$count{$_}!=2} keys %count;


# Get down to business
for(@includes) {

   # Strip .html extension
   $_ =~ s/\.html//g;

   # Convert HTML to PostScript
   # /usr/bin/html2ps -o /psdir/outfile.ps http://mysite.dom/page.html
   system ("$bin{html2ps} -o $dir{ps}/$_.$ext{ps} $baseurl/$_.$ext{page}")
      and die "  Error converting $_.$ext{page} to PostScript: $?";

   # Convert PostScript to PDF
   # /usr/bin/ps2pdf /psdir/infile.ps /pdfdir/outfile.pdf
   system ("$bin{ps2pdf} $dir{ps}/$_.$ext{ps} $dir{pdf}/$_.$ext{pdf}")
      and die "  Error converting $_.$ext{ps} to PDF: $?";
   }


print"\n  Completed run of $0\n\n";
PrintLocs();


##########################################################################
sub PrintLocs {
   print(
      "  Original HTML files:  $dir{html}\n",
      "  PostScript versions:  $dir{ps}\n",
      "  PDF versions:         $dir{pdf}\n\n",
      );
   }
##########################################################################


=head1 Name

 h2p2p.pl

=head1 Description

 Create PostScript and PDF versions of all HTML files in given directory.
 Ignore files listed in @excludes.
 Fetches HTML files by URL instead of file so html2ps will process images.
 Add <!--NewPage--> to HTML as needed, for html2ps to handle.
 Links *not* converted from HTML to PDF  8^(
 Requires external libraries html2ps and gs-aladdin, but no perl modules.


=head1 Tested

 html2ps    1.0b1-8
 gs-aladdin 5.50-8
 Perl       5.00503
 Debian     2.2r3

=head1 Updated

 2001-05-30   22:25
   Add explanatory html2ps and ps2pdf syntax comments
   Eliminate redundant code with PrintLocs()
   Replace this:
     for(keys %count) { if ($count{$_} != 2) { push @includes, $_; } }
       with:
     @includes = grep {$count{$_}!=2} keys %count;
   Replace $element with $_ like so:
     for(@pages, @excludes) { $count{$_}++ }
   Fix weird indenting.
   Post to PerlMonks.
   More informative runtime messeages.
   Additional details to pod.
 2001-05-28   19:25
   Remove test 'print' lines.
   Catch potential 'system' errors properly with '$?'.
   Simplify exclude handling.
   Exclude syntax derived from Ram p106.
   Initial working code,

=head1 Todos

 Print to logfile as well as console.
 Getopt::Long to specify HTML, PS, and PDF directories.
 Perlish alternative to system call to html2ps.
   HTML::FormatPs doesn't handle HTML tables  8^(
 Perlish alternative to system call to ps2pdf.

=head1 Author


 ybiC

=head1 Credits

 Thanks to
   stephen for '$?' tip with system,
   jeroenes for cleaner exclusions syntax and for spotting funky indents
 Oh yeah, and to some guy named vroom.

=cut

