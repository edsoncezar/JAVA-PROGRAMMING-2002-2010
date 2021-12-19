#!/usr/bin/perl -w

use strict;
use vars qw(%opt %DB $VERSION $NAME $DEFRC $DEFDB);

$VERSION = '0.9';
$NAME = 'syscheck';
$DEFDB = '/var/lib/syscheck/sum.db';
$DEFRC = '/etc/syscheck.rc';
use File::Find;
use Digest::MD5;
use Getopt::Std;
use DB_File;
getopts('hvif:c:', \%opt);

if($opt{h}) {
  print(<<EOF);

$NAME $VERSION

USAGE: $NAME [-hvi] [-f checksum_file] [-c config_file]

DESCRIPTION:

this program checks files and directories in your system 
reporting if they were modified (by checking an md5 sum) 
created or deleted since last time you initialized it.

I use it to check my /etc /sbin /lib /bin /usr/bin and /usr/sbin
for changes i didn't made (backdoors?)

you can run it from a cron job or manually 
(it is safe to store a copy of the checksum database outside the system)
it takes about 2 minutes to scan all rilevant files on my systems.

although it works for me it is all but perfect: 
i would appreciate some hints to make it better.

Options:
-h this (poor) help

-i create (initialize) a new checksum database 

-f <checksum_file> use another checksum file instead of 
   $DEFDB 

-c <config_file> use another config file instead of 
   $DEFRC ( - for STDIN)

-v verbose output (not yet implemented :)

EOF

  exit 0;
}

my $rcfile = $opt{c} || $DEFRC;
my $dbfile = $opt{f} || $DEFDB;



open(FIND, "<$rcfile")|| die "cannot open $rcfile: $!\n";
if($opt{i}) {
   unlink($dbfile) || die "cannot unlink $dbfile: $!\n";
}
tie %DB, 'DB_File', $dbfile || die "cannot tie $dbfile\n";
my @list;
while(<FIND>) {
  chomp;
  next if( $_ =~ /^\s*#/);
  next if( $_ =~ /^\s*$/);
  push @list, $_;
}

close FIND;

chdir('/');
print "$NAME V. $VERSION\n";
print "begin date: ", scalar(localtime(time())), "\n";
print "\n---- BEGIN REPORT ---\n";
print "init db $dbfile" if($opt{i});

find(\&checkfile, @list);
unless($opt{i}) {
  print "\nDeleted files:\n";
  foreach my $key (keys %DB) {
	unless($DB{$key} =~ s/:CHECKED$//) {
	  print "$key\n";
	  delete $DB{$key};
	}
  }
}

print "\n----  END REPORT  ---\n";
print "end date: ", scalar(localtime(time())), "\n";
untie %DB;

sub checkfile {
  if(-f $_) {
     my $fname = $File::Find::name;
     if(open(FILE, $_)){

	my $md5 = new Digest::MD5;
	my($dev,$ino,$mode,$nlink,$uid,$gid,$rdev,$size,
	   $atime,$mtime,$ctime,$blksize,$blocks) = stat(FILE);
	$md5->addfile(*FILE);
	my $digest = $md5->hexdigest;
	if($opt{i}) {
	  #add it
	  $DB{$fname} = "$digest:$mode:$uid:$gid:$mtime";
	}
	else {
	  #verify
	  my $finfo = $DB{$fname} || 0;
	  $DB{$fname} = $finfo .  ':CHECKED';
	  if($finfo) {
	    my ($odigest,$omode,$ouid,$ogid,$omtime) =
		split(':', $finfo);
	    unless($digest eq $odigest) {
		print "FILE $fname - digest changed: \n\t old: $odigest \n\t new: $digest\n"; 
	    }
	    unless($mode eq $omode) { 
		print "FILE $fname - mode changed: \n\t old: $omode \n\t new: $mode)\n";
	    }
	    unless($uid eq $ouid) { 
		print "FILE $fname - uid changed: \n\t old: $ouid \n\t new: $uid\n";
	    }
	    unless($gid eq $ogid) { 
		print "FILE $fname - gid changed: \n\t old: $ogid \n\t new: $gid\n";
	    }
	    unless($mtime eq $omtime) { 
		$mtime = scalar(localtime($mtime));
		$omtime =  scalar(localtime($omtime));
		print "FILE $fname - mtime changed: \n\t old: $omtime \n\t new: $mtime\n";
		
	    }
	  }
	  else {
	    print "- new file: $fname\n";
	    delete $DB{$fname};
	  }
	  close FILE;	
	}
     }
     else {
	  warn "cannot open file $fname: $!";
     }	
  }	
}


