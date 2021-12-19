# A little something I cooked up to check if the DHCP 
#and the DNS servers matched reasonably, regarding name 
# and number.
# Done in ActivePerl on NT 4.0
# Needs NT Resource Kit installed, for using the 
# command 'dhcpcmd' to query the DHCP server.
# Macs using DHCP sometimes give (null) as computer name.

use Socket;
use Net::hostent;
use Time::localtime;

$DHCPserver = "111.222.333.444"; 
# your DHCP server's IP here
$scope = "111.222.333.0"; # the DHCP scope

sub makedhcphash(){
    concat(DHCPCMD, "dhcpcmd $DHCPserver enumclients $scope|");
    while ($line = <DHCPCMD>) {
	if ($line =~ /^\d/) {
	    @fields = split(/\s+/,$line);
	    $dhcphash{$fields[1]} = $fields[2];
	} # if ends

    } # while ends
    close DHCPCMD;
} # makedhcphash ends

sub makednshash(){
# With the wild assumption that the computer you 
# run the script on knows which DNS server, if any, 
# it should talk to.
    while (($ipnumber, $computername) = each(%dhcphash)) {
	unless ($h = gethost($ipnumber)) {
	    $dnshash{$ipnumber} = "not";
	    next;
	}
	$dnshash{$ipnumber} = gethost("$ipnumber")->name;
    } # while ends
} # makednshash ends

sub twodigit {
# return string (mday, mon, hour and so on) as
# two digits. January is thus 01, not 1
    if ($_[0] =~ /\d/) {
	return "0" . $_[0];
    }
    else {
	return $_[0];
    }
}

sub comparehashes(){
# . operator concatenates string values
    $timestamp = twodigit(localtime->mday) . localtime->mon . localtime->hour . localtime->min;
    concat(OUT,">result$timestamp.txt");
    while(($ip, $name) = each(%dhcphash)) {
	$_ = $dnshash{$ip};
	/^\w+\./;
	print $1;
	unless ($dhcphash{$ip} =~ /$1/i) {
	    print OUT ($ip, " (", $dhcphash{$ip}, ") is ", $dnshash{$ip}, " in DNS\n");
	} # unless ends
    } # while ends
    close(OUT);
} # comparehashes ends

&makedhcphash();
&makednshash();
&comparehashes();

