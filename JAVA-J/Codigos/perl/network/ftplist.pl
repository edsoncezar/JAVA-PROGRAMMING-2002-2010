#!/usr/bin/perl -w
use Net::FTP;

$hostname = 'user defined';
$username = 'user defined';
$password = 'user defined';

$home = 'user defined';

# Open the FilesToGet.txt file which contains a list of files to be searched.
open(FilesToGet, '<FilesToGet.txt') or die "Could not open the 'FilesToGet.txt'.";

#Start the main loop, reading through the FilesToGet.txt file.
while(<FilesToGet>) { 
  $TheFile = $_;                         
  chomp($TheFile);                 
  $INFILE = $TheFile;

  $ftp = Net::FTP->new($hostname);
  $ftp->login($username, $password);

  $ftp ->cwd($home),"\n";

  #get the file
  $ftp->get($INFILE);

}

$ftp->quit;

