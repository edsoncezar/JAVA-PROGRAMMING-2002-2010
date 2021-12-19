#needs these to get the html file from live365.com
use LWP::Simple;

#type in your username(aka: broadcaster's name) here
#print "Username: ";
#$username = <STDIN>;
#chomp($username);
$username="thealienz";

#Creates URL
$url = 'http://www.live365.com/cgi-bin/directory.cgi?genre=search&searchdesc=' . $username . '&searchfields=H';

print "Getting Stats - ";
unless (defined ($page = get($url))) {
	die "ERROR - There was an error getting URL: $URL\n";
}
print "Done\n";

print "Profile: $usrename\n";

#Checks the description
if($page =~ m|<TD ID="desc".*?><a class="desc".*?>(.*?)</a></TD>|i) {
	print "Description:\n$1\n";
}

#Checks to see what your connection speed is
if($page =~ m|<TD ID="connection".*?>(.*?)</TD>|i) {
	print "Speed: $1\n";
}

#Checks how many people are listening
if($page =~ m|<TD ID="listen".*?>(.*?)</TD>|i) {
	if($1 =~ m|DrawListenerStars\("/scp/\.\./images/", (\d+), (\d+)\)|i) {
		($listen,$outof) = ($1,$2);
	} else{
		($listen,$outof) = split(/\//,$1);
	}
	print "Listeners: $listen / $outof\n";
}
