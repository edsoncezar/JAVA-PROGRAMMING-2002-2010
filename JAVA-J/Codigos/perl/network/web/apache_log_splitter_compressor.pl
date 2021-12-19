#!/usr/bin/perl -w

use strict;

use Time::ParseDate;
use Time::CTime;
use Compress::Zlib;

my $out_dir=$ENV{LOG_DIR};

my $cur_str='';
my $outlog;

my $line;

$SIG{TERM}= sub {
  #handle apache closing by closing the .gz file properly;
  if(defined $outlog){
    $outlog->gzclose();
    undef $outlog;
  }
};

  while (defined($line=<STDIN>)){
    chomp $line;
    if($line=~/\[(\d+)\/(\w+)\/(\d+)\:/){
      #lines must contain an extractable date
      my ($dd,$mm,$yy)=($1,$2,$3);
      $dd=~s/^0//g;
      my $str="$dd $mm $yy";
      if($str ne $cur_str){
	# new day, rotate to next file
        $cur_str=$str;
        my $dt=parsedate($str);
        my $ts=strftime("%Y_%m_%d",localtime($dt));
        $ts=~s/\s/0/g;
        # close current log (If necessary)
        $outlog->gzclose() if defined $outlog;
	#find a unique name for the new log	
        my $ct=1;
        my $fn="$out_dir$ts.0.gz";
        while(-f $fn){
          $fn="$out_dir$ts.$ct.gz";
          $ct++;
        }
        $outlog=gzopen($fn,"a");
      }
      $outlog->gzwrite($line."\n");
    }else{
      # unrecognized line.... there should probably be some error checking here
    }
  }

  if(defined $outlog){
    $outlog->gzclose();
  }
