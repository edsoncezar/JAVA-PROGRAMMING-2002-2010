 =**************************************
    = Name: GAN- Guess A Number game
    = Description:Computer generates a semi-
    =     random number upto a max number you spec
    =     ify. User gets X guesses to guess the co
    =     rrect number (default set at 5 tries), t
    =     he system will tell you whether your gue
    =     ss was too high or too low. Stats and pe
    =     rcents are saved in DBM.
    = By: Aaron L. Anderson
    =
    = Assumes:If you don't have SDBM install
    =     ed change the affiliated lines to the da
    =     tabase of your choice.
    =
    =This code is copyrighted and has    = limited warranties.Please see http://w
    =     ww.Planet-Source-Code.com/vb/scripts/Sho
    =     wCode.asp?txtCodeId=433&lngWId=6    =for details.    =**************************************
    
    #!/usr/bin/perl -W
    use strict;
    use warnings;
    use POSIX;
    use Fcntl;
    use SDBM_File;
    my %dbm;
    my $test = "game.dbm";
    my $win = 0;
    my ( $total, $wonpercent, $lostpercent );
    tie( %dbm, 'SDBM_File', $test, O_CREAT | O_RDWR, 0644 )
    || die "Died tying database\nReason: $!";
    unless ( exists $dbm{win} ) { $dbm{win} = 0 }
    unless ( exists $dbm{lost} ) { $dbm{lost} = 0 }
    # max = maximum number to randomize
    # tries = # of tries not to exceed $allowed
    # allowed = max number of tries allowed
    my $tries = 0;
    my $guess;
    my $allowed = 5;
    print "What's the highest number you want to try?\n";
    chomp( my $max = <STDIN> );
    my $answer = int( rand($max) ) + 1;
    while ( $tries < $allowed ) {
    print " Your guess: ";
    chomp( $guess = <STDIN> );
    $tries++;
    if ( $guess eq $answer ) {
    last;
    }
    elsif ( $guess > $answer ) {
    print " $guess is too high!\n";
    }
    elsif ( $guess < $answer ) {
    print " $guess is too low!\n";
    }
    }
    if ( $guess eq $answer ) {
    print "\nYou got it right!\n";
    print "It only took you $tries tries!\n";
    $dbm{won}++;
    $dbm{total}++;# for stats
    }
    else {
    print "\n You lose! You're only allowed $allowed guesses :(\n";
    print "Answer was: $answer\n";
    $dbm{lost}++;
    $dbm{total}++;# for stats
    }
    print "-----------------------------------------\n";
    print "Total games played: $dbm{total}\n";
    $wonpercent = ( $dbm{won} / $dbm{total} ) * 100;
    $lostpercent = ( $dbm{lost} / $dbm{total} ) * 100;
    print "Games won: $dbm{won}\n";
    print "Games lost: $dbm{lost}\n";
    printf( "Percent won: %.0f\n", $wonpercent );
    printf( "Percent lost: %.0f\n", $lostpercent );
    print "-----------------------------------------\n";

 