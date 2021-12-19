#!/usr/bin/perl -w
#
###########################################################
#
# img2txt
#  version 0.1.0 alpha
#
# Converts a greyscale image in various formats to ASCII
# characters giving the illusion of a picture. Works best
# with small fonts.
#
## About the Author
#
# Name: David Neil McKee
# Email: anti@anti.co.uk
# Online Aliases: 
#  Riel (furrymuck)
#  dmckee (perlmonks)
#  The Cow (h2g2, everything2)
# DOB: St. Paddy's, 1982 (17th March)
# Address: Chester, England
#
## Thanks
#
# everything2...........................for the inspiration
# perlmonks.................................for the answers
# everything development corporation......for the above two
#
## Version History
#
#
# 0.1.0 alpha - 25 Feb 2001
#  *ADDED* support for most known graphics formats through
#    Image::Magick. Thanks to Merlyn and everyone else at
#    Perlmonks for their help.
#
# 0.0.3 alpha - 24 Feb 2001
#  Marginal improvements
#  *ADDED* command line interface
#  Neatened code (esp: file handling locations)
#  Chomped filenames to produce prettier errors
#
# 0.0.2 alpha - 23 Feb 2001
#  Marginal improvements 
#  *FIXED* i/o handling problems
#  *ADDED* comments
#
# 0.0.1 alpha - 23 Feb 2001
#  First release
#
## Known Bugs
#
# Shoddy reverse handling
# No documentation
# No i/o handling *FIXED 0.0.2a*
#
## Wishlist
#
# Support for GIF or other image formats *ADDED 0.1.0a*
# Multiple palettes
# Command line control *ADDED 0.0.3a*
# Copy of the Camel book 
# World peace and harmony
# A ticket on the first manned flight to Mars
#
###########################################################

use strict;
use Image::Magick;                  #Allows access to multiple file formats
my $IMimage = Image::Magick->new;   #Creates the new instance
my ($palette,$filename,$width,$output,$reverse);  

unless (@ARGV) {            # If no arguments, ask the user.
  print "Filename: ";
  chomp($filename=<STDIN>); # the image to load in
  print "Output to: ";
  chomp($output=<STDIN>);   # where to spew the text to
  print "Reverse?: ";
  $reverse=<STDIN>;  # allows for light-on-dark and dark-on-light matching
  $reverse=($reverse ne "\n");
}
else
{
  $filename=$ARGV[0];   # format: img2txt [inputfile [outputfile [reverse]]]
  if ($#ARGV>0)
    {$output=$ARGV[1]}
  else                                 # use second param, or just use inputfile with a .txt extension.
    {$output=$filename.".txt"};        # Not recommended in MSDOS environments.
  if ($#ARGV<2) {$reverse=0} else      # if there's no last param, do it normally.
    {if ($ARGV[2]=~m/rev/i) {$reverse=1} else {$reverse=0}}; #accepts anything containing 'rev'.
}

if ($reverse) {$palette=" .'-;/?71ICDOQ\@M"} else {$palette="M\@QODCI17?/;-'. ";};
$IMimage->Read($filename); 
my ($r,$g,$b,$a,$colour,$xcount,$ycount, $substring, $readval);  # make use strict happy
my $spew="";                        # make sure it's initialised.
for ($ycount = 0; $ycount < $IMimage->Get('Height'); $ycount++) {      # for each row of the file
  for ($xcount = 0; $xcount < $IMimage->Get('Width'); $xcount++) {     # and each pixel in the row
    $readval=$IMimage->Get('pixel['.$xcount.','.$ycount.']');           # read the colours of the pixel
    ($r,$g,$b,$a)=split(/,/,$readval);                                  # split into mixed colours
    $colour=int((($r+$g+$b)/3)/16);                                     # convert it to 0-15
    $substring=substr($palette,$colour,1);                              # get the character to write as ASCII
    $spew.=$substring;                                                  # add it to the list
  }
  $spew.="\n";     # at the end of the row, put in a linebreak
} #image done

open TXT, ">$output" or die("FATAL ERROR:Could not open output file '$output' for output.\n\n$!");
print TXT $spew;  # chuck it to the file
close TXT;        # close it again

