#!/usr/bin/perl -w

use strict;
sub file_create;

if (! -e "hi.dat") { file_create };


open (FILE, "hi.dat") || die "cannot open file hi.dat\n";

my $time = <FILE>;
chomp $time;
print "\n******** A simple maths game *******\n******** Highest Score is $time";
$time = <FILE>;
chomp $time;
print "$time ********\n\n";
close (FILE) || die "cannot close file hi.dat\n";

sleep(1);
srand;

my $num_c = 0;
my $score_r = 0;
my $score_w = 0;
my @symbols = qw(+ - *);

$time = time();

while (time() - $time < 30) {
     my	$num_a = int rand (9)+1;
     my	$num_b = int rand (9)+1;
     my $x = int rand (3);

my $equat = "$num_a $symbols[$x] $num_b";# 
print "$equat = ";                       # thanks jeffa 
$num_c = eval $equat;                    #


     my $answer = <>;

if ($answer == $num_c) {
	print "correct\n";
	$score_r +=1;
}
else 
{
	print "wrong heh!\n";
        $score_w +=1;
}
}

$time = time() - $time;

open (FILE, "hi.dat") || die "cannot open hi.dat\n";
$time = <FILE>;
chomp $time;
close (FILE) || die "cannot close file hi.dat\n";

print "Time is up!! you scored $score_r correct and $score_w incorrect \n ";

if ($time < $score_r) {
	open (FILE, ">hi.dat") || die "cannot open hi.dat\n";
	print "You beat the hi score!!.. please enter your name so you can be credited for this feat!\n";
	$time = <>;
	print FILE "$score_r \nby $time";
	close (FILE) || die "cannot close file hi.dat\n";
}


sub file_create {
print "cannot open file hi.dat shall I create it? y or n\n";
my $time = <>;
chomp $time;

if ($time eq "y")
{
	open (FILE, ">hi.dat") || die "Sorry I was unable to create hi.dat\n";
	print FILE "1\n by Gordy";
	close (FILE) || die "cannot close file hi.dat\n";
}
else {
	die "I need hi.dat sorry I am unable to proceed\n";
}
}
