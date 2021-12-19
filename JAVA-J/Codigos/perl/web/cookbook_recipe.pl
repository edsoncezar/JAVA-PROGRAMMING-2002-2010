#!/usr/bin/perl
# cookbook.pl - get and store daily cookbook recipes from perl.com
# files are saved as title_of_the_recipe.html

use strict;
use LWP::Simple 'get';

my $path = "/full/path/to/cookbook"; # CHANGE
my $cook = "http://www.perl.com/pub/a/universal/pcb/solution.html";
my $cooked = get $cook or die;
$cooked =~ s|<link href="/perl_a\.css" type="text/css" rel="stylesheet">||oi;
$cooked =~ s|language="JavaScript"|language="foo"|gi;
$cooked =~ s| src="(.*?)"| src="blank.gif"|gi;
$cooked =~ s| href="/| href="http://www\.perl\.com/|gi;
$cooked =~ m/<h2><center>.*[^<]/oi;
my $title = $&;
chomp title;
$title =~ s|<h2><center>||oi;
$title =~ s|\W|_|g;
$title =~ s|^_||o;
my $save = "$title.html";
open(FILE,">$path/$save") or die;
print FILE $cooked; 
close (FILE) or die;
exit;


#!/usr/bin/perl
# cookbooks.pl - list saved recipes

use CGI qw(:standard);

opendir DIR, "." or die "error reading dir: $!";
@files = readdir DIR;
closedir DIR;
foreach $file(@files){ if($file=~/.*\.html/){ if($file=~/^[A-Z]/){ push @recs,$file}}}
@recs=sort(@recs);
$uhh=scalar(@recs);
print header;
print start_html(-title=>'Recipes of the Day',-bgcolor=>'ffffff',-text=>'000000',-link=>'000000',-vlink=>'000000');
print<<HTML;
<table border="0" cellpadding="3" cellspacing="0" width="100%" align="center"><tr><td bgcolor="#0098C0">
<font color="#FFFFFF" size="+3" face="Times New Roman">&nbsp;$uhh Perl Cookbook Recipes<br></font></td>
<td bgcolor="#0098C0" align="right"><a href="http://www.perl.com/pub/universal/pcb/solution.html">
<img src="cookbook.gif" border=0 alt="Perl Cookbook cover image"></a>
<tr><td><ol><tt>
HTML
foreach $pe(@recs){
		($fp=$pe)=~s/_|\.html$/ /g;
		print qq~<li><a href="$pe">$fp</a><br>~;
	}
if($uhh==0){ print qq~Your recipes will be listed here, sorted alphabetically.~;}
print<<HTML;
</ol>
<tr bgcolor="#0098C0"><td><font size="-1" color="#ffffff">
Solutions and Examples for Perl Programmers</td>
<td align="right"><font size="-2" color="#ffffff">
by Tom Christiansen and Nathan Torkington</font></td></tr></table>
HTML
exit;


# sample crontab
1 11 * * * /full/path/to/cookbook/cookbook.pl
