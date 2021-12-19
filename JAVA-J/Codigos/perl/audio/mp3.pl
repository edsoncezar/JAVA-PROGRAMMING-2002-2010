#!/usr/bin/perl -w

 ####################################################################
 #
 #   burncd.pl
 #
 #   Nate Oostendorp Jan 2000
 #
 #   cheezy script to turn a bunch of mp3s into a audio (or data) cd

 use strict;

 if ($ENV{USER} ne 'root') {
     die "you must be root to burn cds!\n";
 }


 #default settings -- -1 means auto
 my %settings = (
     type => "audio",
     playlist => 0,
     dev => "3,0",
     pad => 1,
     speed => 2
 );
 my $usage = "Usage: burncd.pl [OPTIONS] FILES
     options include:
         -dev=SCSIID,TARGET  (default $settings{dev})
         -playlist           (FILES points to playlist file(s) -- default off)
         -pad                (pad the tracks -- default on)
         -speed=SPEED        (set the record speed -- default auto)
         -type=[audio|data]  (audio or data cd -- default audio)
         \n";


 use DirHandle;
 #thank you, mr christensen
 sub plainfiles {
     my $dir = shift;
     my $dh = DirHandle->new($dir)   or die "can't opendir $dir: $!";
     return sort                     # sort pathnames
         grep {    -f     }       # choose only "plain" files
         map  { "$dir/$_" }       # create full paths
         grep {  !/^\./   }       # filter out dot files
         $dh->read();             # read all entries
 }

 ################################################################
 #   sub readPlaylist
 #
 #   confirms existence of all files referenced by a playlist
 #   and returns a list of them
 #
 sub readPlaylist {
     my ($playlist) = @_;
     my @files;

     open (PL, $playlist) or die "can't read playlist $playlist";
     while (my $file = <PL>) {
         chomp $file;
         die "$file is referenced by $playlist, but doesn't exist!"
             unless -e $file;
         die "$file is not a data file"
             unless -f $file;
         push @files, $file;
     }
     close PL;

     @files;
 }

 ######################################################################
 #
 #   burnFiles
 #
 #   given a list of files, will put them on the cd
 #
 sub burnFiles {
     my @files = @_;

     my $str = "cdrecord -eject ";

     #assign the general options
     $str .= "speed=$settings{speed} " unless $settings{speed} == -1;
     $str .= "-audio " if $settings{type} eq "audio";
     $str .= "-data " if $settings{type} eq "data";
     $str .= "-pad " if $settings{pad};

     $str.= "dev=$settings{dev} ";

     #assign the track options
     #er...  I'm not sure this is necessary yet.

     foreach (@files) { $str.='"'.$_."\" "; }
     print "$str";
     system($str);
 }

 ###############################################################
 #
 #   decodeMp3s
 #
 #   in order to burn audio, we must have the files in a WAV format
 #
 sub decodeMp3s {
     my $tempdir = "/tmp/burncd";
     `mkdir $tempdir` unless -e $tempdir;

     my $counter = 1;
     foreach my $file (@_) {
         next unless ($file =~ /\.mp3$/);
         my $wavname = $tempdir."/track".$counter.".wav";
         print "converting $file to WAV format\n";
         system ("mpg123 -q -w $wavname \"$file\"");
         $file = $wavname;
         #now use the wav as the filename
         $counter++
     }

     $tempdir;
 }
 ######################################################
 #   main
 #

 while ($ARGV[0] =~ /^-/) {
     my $option = shift @ARGV;
     $option =~ s/^-+//;
     my ($setting, $val) = split /\=/, $option;
     $val ||= 1;
     unless (exists $settings{$setting}) {
         print $usage;
         die "$setting isn't a valid parameter";
     }
     $settings{$setting} = $val;
 }
 #create the settings hash from command line params


 if (not @ARGV) {
     print $usage;
     die "no files specified";
 }

 my @files;

 while ($_ = shift @ARGV) {
     if (-d and $settings{type} = 'audio') {
         push @ARGV, plainfiles($_);
         #files within a directory get pushed on the queue
     } else {
         if ($settings{playlist}) {
             push @files, readPlaylist($_);
         } else {
             push @files, $_ unless not -f and $settings{type} eq 'audio';
         }
     }
 }

 my $tempdir = decodeMp3s(@files) if $settings{type} eq 'audio';

 burnFiles (@files);

 #clean up my tempdir, if we have one
 if ($tempdir) {
     print "cleaning up temporary files...\n";
     `rm -r $tempdir`;
 }


