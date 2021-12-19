#!/usr/local/bin/perl -w

#Password file checker written by: Benjamin A. McFarland
#
#Purpose: This program is  meant to munge through N passwd files and
#         check for logins with multiple UIDs, UIDs with multiple
#         logins, and logins with a UID of zero (0) that are not root.
#         Logins with multiple UIDs are listed in the output file:
#         "multi_uids.dat" as are users with an UID of zero (0). UIDs
#         owned by more than one login are listed in "multi_logins.dat". 
#         All other users who pass these criteria are listed in the
#         file: "goodusr.dat"

$length = scalar(@ARGV);
$SYSLIMIT = 100;

#We set this constant to allow for system logins. The constant allows for
#SAs to alter it here if they decide to allow system logins to have UIDs
#higher or lower.

if($length == 0)
{
   print STDOUT "passchk requires a source password file! USAGE:\n\tuser_name>passchk /path/to/your/passwd\n";
   exit 0;
}

#The above conditional confirms that the script is run with at least one
#source file for data, else it exits with failure.

    open(LOGPUT, "> "."multi_uids.dat");
    open(UIDPUT, "> "."multi_logins.dat");

#Then the log files for the script are opened for writing. Old files are
#clobbered since we're looking for the most recent data, and I didn't feel
#that the old data was pertinent.

#So, now we cycle through the  number of source files given in the ARGV
#array.

for($i = 0; $i <= $length; $i++)
{
    open(CURFILE, "$ARGV[$i]");
    while($input = <CURFILE>)
    {

#As long as we're getting data from the Current datafile, read it into
#the variable $input.

    @dataf = split(/:/,$input);
#we break the data at the colons and place it into an array.

    if(($dataf[2] == 0)&&($dataf[0] ne "root"))
    {
       print LOGPUT "**\n*WARNING* User: $dataf[0] has a UID of $dataf[2]!\n**\n";
    }

#The above loop checks right away for any multiple logins with a UID of
#zero.

    if((!defined $loghash{$dataf[0]})&&($dataf[2] > $SYSLIMIT))
    {
      $loghash{$dataf[0]} = $dataf[2];
      $badhash{$dataf[0]} = 0; 
      if(!defined $srcfile{$dataf[0]})
      {
         $srcfile{$dataf[0]} = $ARGV[$i];
      }
    }

#Now we check for values that haven't been entered into our logging
#arrays and that have a UID of larger than the constant set at the
#beginning of the script. If so, we log them, and set the source array.
#This is done in the conditionals above and below.

     if((!defined $uidhash{$dataf[2]})&&($dataf[2] > $SYSLIMIT))
     {
        $uidhash{$dataf[2]} = $dataf[0];
        $badhash{$dataf[2]} = 0;
        if(!defined $srcfile{$dataf[2]})
        {
           $srcfile{$dataf[2]} = $ARGV[$i];
        }
      }

#Now we check to see if the UID doesn't match the one previously set
#with this login and that the UID is above our system constant. If not,
#we log it into the appropriate file and set the bad flag.

      if(($loghash{$dataf[0]} ne $dataf[2])&&($dataf[2] > $SYSLIMIT))
      {
         print LOGPUT "$dataf[0] had multiple UIDs $loghash{$dataf[0]} from file: $srcfile{$dataf[0]}\n\tand $dataf[2] from file:$ARGV[$i]\n";
         $badlist[$dataf[0]} = 1;
      }

#Same conditional as above, but this time we're looking to see that the
#login is the same one set to this UID, the opposite condition of the
#previous conditional. If not, then we set the bad flag.

       if(($uidhash{$dataf[2]} ne $dataf[0])&&($dataf[2] > $SYSLIMIT))
       {
          print  UIDPUT "UID $dataf[2] is being used by both:\n$uidhash{$dataf[2]} from file: $srcfile{$dataf[2]} and\n$dataf[0] from file: $ARGV[$i]\n";
          $badlist{dataf[2]} = 1;
       }
     }
     close(CURFILE);

#We're done with the current file, close it before we open a new one.
}

#At this point,  we've completed all of our source file munging, and
#should have compiled all the valid and invalid logins and UIDs. So,
#we open up our good data file, pull up the keys, sort them alphabetically,
#check that they haven't ended up in our bad list, and print them out.

    open(GOODFILE, "> "."goodusr.dat");
    foreach $key (sort keys(%loghash))
    {
       if(($badlist{$key} != 1) && ($badlist{$loghash{$key}} != 1))
       {
          print GOODFILE "$key is using only valid UID $loghash{$key}, from:\n$srcfile{$key}\n";
       }
    }
    close(GOODFILE);
    close(LOGPUT);
    close(UIDPUT);

#We're done with this bad boy, so we shut all our files like a good
#monkey.


