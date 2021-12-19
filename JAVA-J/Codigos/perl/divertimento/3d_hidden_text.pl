#!/usr/bin/perl -w
use strict;
use GD;

# Create a new image object.
my ($width,$height) = (300,300);
my $im = new GD::Image($width,$height);

# Allocate a few custom colors.
my $black = $im->colorAllocate(0,0,0);
my $blue = $im->colorAllocate(0,0,255);
my $green = $im->colorAllocate(0,255,0);
my $lgrey = $im->colorAllocate(200,200,200);
my $lred = $im->colorAllocate(255,190,190);
my $red = $im->colorAllocate(255,0,0);
my $white = $im->colorAllocate(255,255,255);
my $yellow = $im->colorAllocate(255,255,0);

# Make image transparent and interlaced.
$im->transparent($white);
$im->interlaced('true');

# Pass these values and run subroutine Box.
# (Since this data does not need to be kept
# secure really, I went ahead and decided
# to pass variables the basic way.
&Box($red,$black,gdGiantFont,$lred);

sub Box {
# Sets up and prints the funky pattern that
# kinda masks the words you are supposed to
# try to see.
$im->setStyle($red,$red,$red,$red,$blue,$blue,$blue,$blue,gdTransparent,gdTransparent);
#$im->arc(50,50,25,25,0,360,gdStyled);
$im->fill(0,0,gdStyled);
$im->rectangle(0,0,299,299,$_[0]);
#$im->rectangle(0,0,299,299,$_[1]);

# Find's (not necessarily the perfect center,)
# center of whatever value width used to be,
# and changes it into the new value, since we
# don't need the old value of width anymore.
$width = $width/4;

# Prints out whatever string you want to put here,
# in a nifty old-skool C-style for-loop.
for (my $x=$width;$x<=$width;$x++) {
  for (my $y=0;$y<=300;$y+=15) {
    $im->string($_[2],$x,$y,"The Andy-man can!",$_[3]);
  }
}

# Finish up Box subroutine, and run the Lines sub.
&Lines;
}

sub Lines {
# This subroutine sets up and prints some funky
# lines to obscure your otherwise easily seen
# 'hidden' string.
for (my $x=0;$x<=300;$x++) {
  for (my $y=0;$y<=300;$y+=15) {
    $im->string(gdLargeFont,$x,$y,"-_=",$black);
  }
}

# This little bite-size piece of code simply
# prints out the little footprint for the title
# of the script to reside on.
for (my $x=0;$x<=300;$x++) {
  for (my $y=280;$y<=300;$y++) {
    $im->setPixel($x,$y,$black);
  }
}

$im->string(gdSmallFont,5,283,"Subliminal Messages with 3d Glasses Support!",$white);
$im->rectangle(0,0,299,299,$black);

}

# Makes sure that this script works on most any
# platform that it might be run on.
binmode STDOUT;

# Sets the type of picture for the web,
# to know that it is a .png
print "Content-type: image/png\n\n";

# We are all done, so now we just have to
# have it output to the web!
print $im->png;

