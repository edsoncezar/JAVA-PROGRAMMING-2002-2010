#!c:\perl\bin\perl

use LWP::UserAgent;
use XML::RSS;

#We're running this off of a Windows machine, connecting to a M$SQL server
# although any old SQL server would do (e.g. MySQL) 

use Win32::ODBC;

$DSN = "TESTSERVER";


#Create a new UserAgent to pull the XML data down
$ua = new LWP::UserAgent;
$ua->agent("HeadlineAgent/0.1 ".$ua->agent);

#connect via ODBC to the SQL server
if(!($db = new Win32::ODBC($DSN))){
    print "Error connecting to $DSN\n";
    print "Error: " . Win32::ODBC::Error() . "\n";
   exit;
}

# We'll be pulling in RSS files from various sources, 
# their URL's are stored in the SQL database

my %sources;

if($db->Sql("SELECT * FROM ExternalNewsSources"))
{
    print "SQL failed.\n";
    print "Error: " . $db->Error() . "\n";
    $db->Close();
    exit;
}

while($db->FetchRow()){
    my(%data) = $db->DataHash();
#    ...process the data...
#    Add to hash of hashes
    $sources{$data{'ExternalNewsSourceID'}} =  $data{'Source'};
}

#Create the RSS object to parse the RSS files retrieved...
my $rss = new XML::RSS;

($sec,$min,$hour,$mday,$mon,$year) = localtime(time);
# preformatted string compatible with SQLServer's timestamp field
$nowstring = sprintf("%02i/%02i/%i %02i:%02i:%02i",($mon+1),$mday,($year+1900),$hour,$min,$sec);

#Walk through each of the XML sources
foreach $sourceid(keys %sources)
{
# fetch RSS file from the source's URL
    my $request = new HTTP::Request GET => $sources{$sourceid};
    my $result = $ua->request($request);

    if($result->is_success)
    {
#   grok the RSS file retrieved
        $rss->parse($result->content);

#   Step through all the links in the RSS
        for my $i (@{$rss->{items}})
	{
#       Check to see if we've already seen this link from this source before...
            $db->Sql("SELECT * FROM ExternalNews WHERE SourceID=".$sourceid." AND Link = '".$i->{'link'}."'");
            if($db->FetchRow())
            {
            #skip it - it's here already...
            }
            #Sometimes the RSS mis-parses and give us an empty item
            elsif(length($i->{'title'}) <= 0)
            {
            #skip it - it's empty...
            }
            else
            {
            #Plunk it into the database
                $db->Sql("INSERT INTO ExternalNews (SourceID,PostDate,Title,Link,Description) VALUES ($sourceid,'$nowstring','".$i->{'title'}."','".$i->{'link'}."','".$i->{'description'}."')");
            }
# Nuke the current values in the object, it appears that the XML lib recycles the variables without clearing them...
            $i->{'title'} = '';
            $i->{'link'} = '';
            $i->{'description'} = '';
        }
    }
    else
    {
        print "Doh! couldnt get ".$sources{$sourceid}.": $!\n";
    }
}

#clean up
$db->Close();

