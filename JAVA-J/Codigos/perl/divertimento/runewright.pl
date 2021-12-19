#!perl -w
#####################################################################
#
# RuneWright v0.00a
# Copyleft Dave McKee, March 2001.
#
#####################################################################
#
# RuneWright (name subject to change: there are so many good
# (bad?) puns I could use) is a program which writes English
# Runes (as used in JRR Tolkien's 'The Hobbit' and 'Lord of the
# Rings') to a PNG image, based on a font-set in another.
#
#####################################################################

use strict;            # bondage?
use GD;                # GD.pm: available at all outlets of CPAN
open (FONTIMG,"runes.png") or die "Font not found: $!\n";
my $font=newFromPng GD::Image(\*FONTIMG) or die "Unloadable Font.\n";
close FONTIMG;
(my $fontwidth,my $fontheight)=$font->getBounds();

#### POPULATE ARRAY ####
my (@char1,@char2);
my $charcounter=(ord 'a'); # start with a.
for (0..$fontwidth-1)
{
  if ($font->getPixel($_,$fontheight-1)) 
  {
    $char2[$charcounter]=$_-1;   # set right barrier of current
    if ($charcounter==ord 'z')   # once you've done Z... 
    {
      $charcounter=ord ('0')-1;  # go to just before 0...
    }
    $char1[$charcounter+1]=$_+1; # set left barrier of next
    $charcounter++;              # move on
  };
};
$char1[ord('a')]=0;              
$char2[ord('9')]=$fontwidth-1;


my $text="if you can fill the unforgiving minute\nwith sixty seconds worth of distance run\nyours is the earth and everything thats in it\nand:which is more:youll be a man my son";

#### CONVERT TEXT ####

for my $pos (reverse(0..length($text)-1))
{

  my $two="XXX";
  if ($pos<length($text)-1) {$two=substr($text, $pos, 2)};
  my $one=substr($text, $pos, 1);
  my $counter=0;
  foreach ('ea','ee','eo','ng','st','th') 
  {
    if ($two eq $_) {substr($text, $pos, 2, "$counter")};
    $counter++;
  }
  if ($one eq " ") {substr($text, $pos, 1, "7")};
  if ($one eq ":") {substr($text, $pos, 1, "6")};
}

#### CALCULATE ROOM ####
my $newheight=$fontheight;
my $newwidth=0;
my $currwidth=0;
for my $pos (0..length($text)-1)
{
  if (substr($text,$pos,1) eq "\n")
  {
    $newheight+=$fontheight;
    if ($currwidth>$newwidth) {$newwidth=$currwidth};
    $currwidth=0;
  }
  else
  {
    my $num=ord (substr($text,$pos,1));
    $currwidth+=$char2[$num]-$char1[$num]+2;
  } 
}
if ($currwidth>$newwidth) {$newwidth=$currwidth};

#### PRINT TO PNG ####

my $output=new GD::Image($newwidth,$newheight);
my $black=$output->colorAllocate(0,0,0);
$output->rectangle(0,0,$newwidth,$newheight,$black);
$output->fill(50,50,$black);   # make things neater...
my $xpos=0;
my $ypos=0;
for my $textpos (0..length($text)-1)
{
  if (substr($text,$textpos,1) eq "\n")
  {
    $xpos=0;
    $ypos+=$fontheight;
  }
  else
  {
    my $num=ord (substr($text,$textpos,1));
    $output->copy($font,$xpos,$ypos,$char1[$num],0,$char2[$num]-$char1[$num]+2,$fontheight-1);
    $xpos+=$char2[$num]-$char1[$num]+2;
  } 
}
open PNGOUT, ">runeout.png";
binmode PNGOUT;
print PNGOUT $output->png;
close PNGOUT;
print "all done?";

__END__
