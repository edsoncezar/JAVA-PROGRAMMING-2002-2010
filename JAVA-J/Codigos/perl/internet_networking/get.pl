  use strict;
    use Win32;
    my $subnet = "10.10.10.";
    my $i = "0";
    my $dir = "C:/"; #use forward slash
    my $filename = "arp.txt";
    my $output;
    while ($i < 255) {
    $i += 1;
    print `ping -n 1 -l 1 -w 2 $subnet$i`;
    }
    $output = `arp -a`;
    open (FILE, ">$dir$filename");
    print FILE $output;
    print $output;
    close (FILE);