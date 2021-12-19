#!/usr/bin/perl

### Some of this code came from CHOAD (especially the CDDB handler)

### Modules
use CDDB;

### Stuff
my $HOME = $ENV{HOME};
my $USER = $ENV{USER};
my $HOST = `hostname`; $HOST =~ s/\n//;

$currcd=0;
while(!$done) {
  
  ### Check the CDDB info on this person.
  my(@toc,$ntracks,$i);

  $i=0;
  open(IN,"cdparanoia -Q 2>&1 |") || die $1;
  while(<IN>) {
    chomp;
    if(/^\s*(\d+)\.\s+\d+\s+\[\d+:\d+\.\d+\]\s+\d+\s+\[(\d+):(\d+)\.(\d+)\]/) {
      $toc[$i++] = "$1 $2 $3 $4";
      $ntracks=$1;
    } elsif (/^TOTAL\s+[0-9]+\s+\[([0-9]+):([0-9]+)\.([0-9]+)\]/) {
      $toc[$i++] = "999 $1 $2 $3";  # lead-out track
    }
  }
  close IN;

  ### Now get CDDB...
  my $cddb = new CDDB( Host  => "freedb.freedb.org",
		       Port  => 888,
		       Login => "$USER\@$HOST",
		     ) or die $!;

  my ($cddb_id, $track_numbers, $track_lengths, $track_offsets, 
      $total_seconds) = $cddb->calculate_id(@toc);
  
  my @discs = $cddb->get_discs($cddb_id, $track_offsets, $total_seconds);
  
  if (scalar @discs > 0) {
    ($genre, my $cddb_id, my $title) = @{$discs[0]};
    $title=~/([^\/]*) \/ (.*)/; 
    $artist = $1; $album = $2;
    
    my $disc_info     = $cddb->get_disc_details($genre, $cddb_id);
    @tracks  = @{$disc_info->{'ttitles'}};
  }

  ### Work out the prefixes...
  $prefix = "$artist/$album";
  $prefix =~ tr/ ,.\'\"&//d;
  system "mkdir -p $prefix";
 
  for($i=0; $i<$ntracks; $i++) {
    $f=$tracks[$i];
    $f =~ s/\/[^\/]+$//;
    $f =~ tr/ ,.\'\"&//d;
    $f =~ s/\([^\)]+\)//g;
    $fname="$prefix/$f";
    
    $t=$i+1;

    print "$t>$fname\n";
    system "cdparanoia $t $fname.wav";
    symlink "$fname.wav", "$currcd-$i.trk";
  }

  system "eject -r /dev/hdc";
  $currcd++;
  print "Pausing...";
  <STDIN>;
}

##</code><code>##

#!/usr/bin/perl

print "Scanning for MP3's...\n";
while(1)
{
    sleep 10;
    opendir(DIR,".");
    @d=grep {/\.trk$/} ( readdir(DIR) );
    closedir(DIR);
    
    if(@d) {
	$f=readlink $d[0];
	print "Reencoding $f...\n";
	system "lame -b 128 $f";
	unlink $f;
	unlink $d[0];
    }
}


