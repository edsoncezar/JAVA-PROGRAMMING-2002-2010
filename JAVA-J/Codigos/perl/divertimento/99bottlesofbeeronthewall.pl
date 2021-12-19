#!/usr/bin/perl -w

print "Welcome to 99 bottles of beer on the wall\n";

print "How many bottles of beer do you want on the wall\n";

$beer=<STDIN>;

do{

$beer1=$beer-1;

if($beer1>=0){

print "$beer bottles of beer on the wall, $beer bottles of

 beer. Take one down pass it around $beer1 bottles of beer 

on the wall\n";

$beer=$beer-1;

}

}while($beer>0);
