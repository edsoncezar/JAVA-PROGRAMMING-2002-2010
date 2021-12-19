 use strict;
    use Win32::AdminMisc;
    use Win32::NetResource;
    my $drive;
    my $free;
    my $total;
    my $unc;						
    my $low = "1000";
    my @netdrv = Win32::AdminMisc::GetDrives( DRIVE_REMOTE );
    my $VERSION = 1.0;
    print "The drives bellow have less than 1 GB free:\n\n"; 
    foreach $drive (@netdrv) {
    ($total, $free) = Win32::AdminMisc::GetDriveSpace($drive);
    Win32::NetResource::GetUNCName( $unc, $drive );
    $free = $free / 1048576;
    	if ($free < $low) {
    	print "$unc is mapped to $drive drive has $free MB free\n\n";
    	}
    	
    }
    =head1 NAME
    netspace - This script grabs a list of all the network drives your workstation is connected to and prints out how much space is left on the drive if it is less than 1 GB.
    =head1 DESCRIPTION
    This program is useful for quickly getting a list of drives low on space.
    =head1 README
    This program is useful for quickly getting a list of drives low on space.
    =head1 PREREQUISITES
    This script has a few requirements. You will need the Win32::AdminMisc and Win32::NetResource modules.
    =head1 COREQUISITES
    None
    =pod OSNAMES
    MSWin32
    =pod SCRIPT CATEGORIES
    Win32/Utilities 

