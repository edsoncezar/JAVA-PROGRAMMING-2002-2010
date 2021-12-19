#!/usr/bin/perl -w

print "Welcome to blob maker V1.1\n";

print "all you need to do is input the size of your 

blob\n";

print "and the computers built in gene sequencer ;-) will 

make your blob\n\n\n"; 

print "Input your blob's height\n";

$height=<STDIN>;

print "Input your blob's width\n";

$width=<STDIN>;

print "input blob message\n";

$msg=<STDIN>;

do{

print "$msg" x $width . "\n";

$height-=1;

}until($height==0);

