 #!/usr/bin/perl
    #version 1.0
    #case.pl - change all files and folders in a directory to lowercase 
    #	or UPPERCASE. In your directory type 
    #
    #USAGE: ls > new; for i in new;do ./case.pl $i;done
    # for upper case move # up one line:
    # by James Shepherd - james@netjames.com - www.netjames.com
    while (<>) {
    chomp;
    $n = $_;
    s/$_/\L$_/gi;# Lowercase line
    #s/$_/\U$_/gi;#UPPERCASE LINE
    rename $n, $_;
    print "Success renaming $n to $_ !\n";
    }

