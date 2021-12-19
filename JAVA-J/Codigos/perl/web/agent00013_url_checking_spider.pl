#!/usr/local/bin/perl -w
############################################################
use strict; 
use HTML::LinkExtor;		#used to extract links
use HTTP::Request::Common;
use LWP::Simple;   		#used to check links
use LWP::UserAgent;		#helps with authorization	

### Global Variables ###
my (%visitedLinks, %linksToVisit, %linkResults);
#declare hashes

### Restricting Variables ###

my $restriction = "www.perlmonks.org";
#restricts links to addresses including $restriction

my $stripquery = "true";
#true of false, yanks the ?node=whatever,blah=whatever

my $stripanchor = "true";
#true of false, yanks the #anchor13

### some server settings variables ###

my $webserver = "http://www.somesite.com/mydir/";
#home web directory

my $onserver = "/theserver/mydir/";
#this is where the web address is located on your server...

my $localserver = "false";
#if this is true, it will perform some checks
# on the server side
# using the $webserver and $onserver variables

my $dirIndexExt = "index.pl";
#the default page loaded when a directory is requested from the server
# i.e. "http://www.perlmonks.org/"
# loads "http://www.niwoths.org/index.pl"

### Start Page ###
my $startpage = "http://www.perlmonks.org/";
#the page to start checking from -- must be full address
# if it's the site name or a directory name, please be sure to include /

### Site Login Info (if any) ###
my %logins = (
		
	     );
#for directions on setting up this variable,
#please refer to the documentation
# "sitename.com"=>"user:pass"

### Log File ###
my $linkInfoFile = "linkInfoFile.txt";
#which pages link to what

my $updateFreq = 100;
#frequency at which link results file is updated

### Control Variables
my $sleep = 1;
#seconds to pause after displaying output results
my $timeout = 30;
#timeout in seconds

### Run the Script! ###

&main;				#run main function

&outLinkResults;
#when execution finishes, update the results

sub main {
############################################################
my($link);
$| =1; #unbuffer output

$linksToVisit{$startpage} = &linkFormatter($startpage,"_Beginning_");
#starting from the $startpage,
# marked as being linked to from "_Beginning_"

while ((scalar keys %linksToVisit) > 0) {
#while some left to check
  foreach (sort keys %linksToVisit) {	#foreach link
    $link = $_;
    &checkLink($link);			#check the link

    if ($linkResults{$link} eq "OK") {	#if the page is OK
      if ($link =~ /${restriction}/) {		
        if ($link =~ /(\.s?html?|\.php|\.(a|j)sp|\/$)/) { 
#it's a web page
          &grabLinks($link);		#grab the links
        }#end if
      }#end if
    }#end if

    if ((scalar keys %visitedLinks) % $updateFreq == 0) { #update time
      &outLinkResults; 		#output link information 
      sleep($sleep);		#sleep
    }#end if
  }
}
############################################################
}

sub grabLinks{
############################################################
my(@links,@values);	#declare local variables
my($parser,$base_url,$elt_type);
my($attr_name,$attr_value,$value);
my $ua = LWP::UserAgent->new;
$ua->timeout($timeout);		#set timeout for UserAgent
my $req;

$base_url = $_; #grab the page url

$req = HTTP::Request::Common::GET($base_url);	#the request
LINE: foreach (keys %logins) {
  if ($base_url =~ /$_/) {
    $req->authorization_basic(split(":",$logins{$_}));
    last LINE;
  }#end if
}#end GOTO style loop

$parser = HTML::LinkExtor->new(undef, $base_url);
#initialize parser
$parser->parse($ua->request($req)->as_string)->eof;
#grab the page

@links = $parser->links; 	#parse it

foreach (@links) {
 $elt_type = shift @$_;		#get tag type
 if ($elt_type =~ /\b(a|img)\b/) {
  while (@$_) {	
   ($attr_name, $attr_value) = splice (@$_, 0, 2);
   if ($attr_value =~ /(https?|ftp|file)/) {
    $attr_value = &linkFormatter($attr_value,$base_url);
     if(exists $visitedLinks{$attr_value}) {
      $value = $visitedLinks{$attr_value};
      @values = split("::",$value);
      unless (grep {$base_url =~ /$_/} @values) {
     $visitedLinks{$attr_value} = join("::",$value,$base_url);
      }
     }#end if
     else {
      if(exists $linksToVisit{$attr_value}) {
       $value = $linksToVisit{$attr_value};
       @values = split("::",$value);
       unless (grep {$base_url =~ /$_/} @values) {
        $linksToVisit{$attr_value} = join("::",$value,$base_url);
        return;
       }#end unless
      }
      else {	#not marked to visit
       $linksToVisit{$attr_value} = $base_url;
      }
     }

   }	#end if
  }	#end while
 }	#end if
}	#end foreach
############################################################
}

sub checkLink {
############################################################
my $link = $_;			#store link value locally
my $req;			#declare local variable
print "Checking ${link}:";

my $ua = LWP::UserAgent->new;	#create user agent
my $results;

$req = HTTP::Request::Common::HEAD($link);
#grab head by default

if($link =~ /(\/cgi-bin|\.cgi|\.pl)/) {	#if cgi, do a post
  $req = HTTP::Request::Common::POST($link);
}

LINE: foreach (keys %logins) {
  if ($link =~ /$_/) {		#if it needs a login
    $req->authorization_basic(split(":",$logins{$_}));
#send in login info
    last LINE;
  }#end if
}#end foreach (and LINE)

$ua->timeout($timeout);

$results = $ua->request($req)->as_string;
#grab it (head or full page)

if ($results =~ /\b200\b/) {	#request successfull
  print "OK\n";
  $linkResults{$link} = "OK";
}
elsif ($results =~ /\b401\b|\b403\b/) {		#unauthorized or forbidden
  print "UNAUTH\n";
  $linkResults{$link} = "UNAUTH";
}
elsif ($results =~ /\b500\b/) {	#internal server error
  print "SERVERROR\n";
  $linkResults{$link} = "SERVERROR";
}
else {
  print "BAD\n"; 		#404 (file not found) or other
  $linkResults{$link} = "BAD";	#add it to results
}

$visitedLinks{$link} = $linksToVisit{$link};
#add it to list
delete $linksToVisit{$link}; #do not need to visit again
############################################################
}

sub outLinkResults{
############################################################
my ($key,$link,$value,$status);		#local variables
my %stats;

$stats{"goodpage"}   = 0; #init. values (none unassigned)
$stats{"badpage"}    = 0;
$stats{"unauthpage"} = 0;
$stats{"serverror"}  = 0;
$stats{"goodlink"}   = 0;
$stats{"badlink"}    = 0;
$stats{"unauthlink"} = 0;

my (@links,@keys);

print "-------------------------------\n";	
print "Updating Link Information file...\n";	
#let them know...

open(FILE,">$linkInfoFile");	#open link info file

print FILE "##########################################\n";
print FILE "# Link File: Tells ya what links where   #\n";
print FILE "##########################################\n";
print FILE "# Note: Totals are listed at the bottom. #\n";
print FILE "##########################################\n";

@keys = sort keys %visitedLinks; #grab the keys
foreach $key (@keys) {		 #go through all the keys
  $value  = $visitedLinks{$key}; #grab the value
  $status =  $linkResults{$key};
  chomp($status);
  push(@links,split(/::/,$value));
  print FILE "Information for ${key} --\n";
  print FILE "Status -- ${status}\n";
  if ($status eq "OK") {
    $stats{"goodpage"}++;
    $stats{"goodlink"} += scalar(@links);
  } 
  elsif ($status eq "UNAUTH") {
    $stats{"unauthpage"}++;
    $stats{"unauthlink"} += scalar(@links);
  }
  elsif ($status eq "SERVERROR") {
    $stats{"serverror"}++;
    $stats{"goodlink"} += scalar(@links);
  }
  else {
    $stats{"badpage"}++;
    $stats{"badlink"} += scalar(@links);
  }
  print FILE "Linked to from " . scalar(@links) . " pages: \n";
  foreach $link (@links) {
    print FILE "$link\n";
  }
  print FILE "-----------------------\n";
  @links = qw();
}
print FILE "       TOTALS\n";		#outputing totals
print FILE "-----------------------\n";
print FILE "   Good pages: " . $stats{"goodpage"}     . "\n";	#good pages
print FILE "    Bad pages: " . $stats{"badpage"}      . "\n";	#bad pages
print FILE " Unauth pages: " . $stats{"unauthpage"}   . "\n";	#unauthorized
print FILE "Server errors: " . $stats{"serverror"}    . "\n";   #serv. error
print FILE "  Total pages: " . ($stats{"goodpage"} + $stats{"badpage"} + $stats{"serverror"} +
$stats{"unauthpage"}) . "\n";
print FILE "-----------------------\n";
print FILE "   Good links: " . $stats{"goodlink"} . "\n";
print FILE "    Bad links: " . $stats{"badlink"} . "\n";
print FILE " Unauth links: " . $stats{"unauthlink"} . "\n";
print FILE "  Total links: " . ($stats{"goodlink"}    +
$stats{"badlink"} + $stats{"unauthlink"}) . "\n"; 
print FILE "-----------------------\n";

print " ...completed.\n";	#let 'em know it's done
print "Pages checked: " . (scalar keys % visitedLinks) . "\n";
print  "Pages remaining: " . (scalar keys %linksToVisit) . "\n";
print "-------------------------------\n";

close(FILE);
############################################################
}


sub linkFormatter {
############################################################
my($address,$from,$path);         #set local vars
$address = shift;                 #grab address
$from = shift;                    #grab addr. linked from

if ($stripanchor eq "true" && ($address =~ /\#/)) {
  $address =~ s!\#.*$!!;          #discard anchor
}
if ($stripquery  eq "true" && ($address =~ /\?/)) {
  $address =~ s!\?.*$!!;          #discard query
}
  
 if ($address =~ /\.\.\//) {
  #if the link includes a relative directory (../something/)
  #fix it up to the absolute address
  $from =~ s!/[^/]*?/?$!!;	#removes subdirectory
  $address =~ s!.*/\.\./(.*)!${from}/${1}!; #does it
 }
 
if ($localserver eq "true" && $address =~ /$webserver/) {
 #if the link is to a directory, add $dirIndexExt
 #not done to off server links cause we can't touch 'em
 $path = $address;
 $path =~ s|$webserver/?(.*)$|${1}|;
 $path = $onserver . $path;
 if (-d $path) {                #if it's a directory
  $address =~ s!(.*?)/?$!${1}/$dirIndexExt!;
#add on extension   
 }
} 
  
return $address;		#return the address
############################################################
}
###T#H#E##E#N#D###
