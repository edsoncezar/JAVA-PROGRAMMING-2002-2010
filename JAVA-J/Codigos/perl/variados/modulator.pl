#!perl -w
# MODULATOR by epoptai (with some crucial code from japhy's modlist.pl)
# This tool lists installed perl modules, views module pod and source code, runs code examples*, and more.
# *WARNING: THIS PROGRAM CAN EXECUTE USER SUPPLIED PERL CODE.
# DO NOT ALLOW PUBLIC ACCESS TO THIS CGI SCRIPT!
# http://www.perlmonks.org/index.pl?node=MODULATOR

$|++;

use strict;
use CGI qw(Vars :standard);
use CGI::Carp qw(warningsToBrowser fatalsToBrowser);
use Pod::Html;
use HTML::Entities;
use FindBin qw($Bin);
use File::Find;
use File::Spec::Functions 'rel2abs';
use Data::Dumper;

BEGIN{
	$_ = $0; 
	$0 = " A module tried to modify this script. "
	} # perlmonks.org/index.pl?node=177129
my $this = $_;

use vars qw($base $found %found %path);

# CONFIGURATION
my $bodytag = qq~<body bgcolor="white" text="black" link="#0000aa" vlink="#0000aa">~;
my $showlocal = 0;              # 0 excludes script dir from @INC, 1 includes it
my $sitemods = 'site';          # bold modules from this directory, for example: 'site', leave blank for none
my $synopsis_code_form = 'y';   # any value here puts the synopsis code in a form, leave blank to disable

(my $cache = url(-relative=>1)) =~ s|(\.).*$|$1cache|; # set scriptname.cache as the cache filename
eval "require '$cache'"; # use cache file if it exists
%found = %{$found} if !$@ && %{$found}; 

my $now = time;
my $url = url();
my %i = Vars;

my 
$p  = header;
$p .= '<html><head><title>MODULATOR</title></head>' unless $i{perlpod};
$p .= $bodytag if %i && !$i{perlpod};

cache('make') if $i{make}; # create cache
cache('dele') if $i{dele}; # delete cache

listmods() if $i{listmods}; # display left frame module list
splash()   if $i{splash};   # display right frame splash screen
alpha()    if $i{alpha};    # display alpha index
env()      if $i{env};      # display env vartiables
selfurls() if $i{urls};     # display paths to this script
synopsis() if $i{synopsis}; # eval code from synopsis form


if($i{pm} && $i{title}){ # handle actions from module list
	perlpod($i{pm},$i{title}) if $i{perlpod}; # render pod as html
	perlmod($i{pm},$i{title}) if $i{perlmod}; # display module source code
	}

$p .= qq~
<frameset cols="30%,*">
    <frameset rows="90%,40">
    <frame name="mod" src="$url?listmods=1" scrolling="auto" frameborder="1">
    <frame name="dex" src="$url?alpha=1"    scrolling="no"   frameborder="1"></frameset>
    <frame name="pod" src="$url?splash=1"   scrolling="auto" frameborder="1">
</frameset></html>~ unless %i;

if($i{showhash}){
	$_ = findmodules();
	$p .= '<pre>'.Data::Dumper->Dump([\%found],[qw(found)]).'</pre>';
	}

$p .= end_html if %i && !$i{perlpod};

print $p;  # there can be only one

	
sub listmods
{ # display the module list

my $total = findmodules(); # populate %found

$p .= qq~<a name="top">
<b>Perl</b> : $]<br></a>
<b>Path</b> : $^X<br>
<b>INC</b> : ~;

for(@INC){
	next if $showlocal eq '0' && $_ eq '.';

	if($sitemods && /$sitemods/){ 
		$p .= qq~<b>$_</b> ~ 
		}
	else{
		$p .= qq~$_ ~
		}		
	}

$p .= qq~<br><p><font size="-1">
<b><a href="$url?env=1" target="pod">Show environment variables</a></b><br>
<a href="$url?urls=1" target="pod">Paths to this script</a><br>
<a href="$url?pm=$this&perlpod=1&title=MODULATOR" target="pod">About</a> - 
<a href="$url?splash=1" target="pod">top</a><br></font><p><font size=+2>
$total Installed Modules</font> <sup><a href="$url" target="_top">re</a></sup><p><ol>~;

my %abc = ();

for(sort { lc($a) cmp lc($b) } keys %found){

	my ($ltr,$tag) = ('') x 2;

	m|^(.)|;
	$ltr = lc($1) if $1;

	$abc{$ltr}++;
	
	$tag = "name='$ltr'" if $abc{$ltr} < 2; # name only the first link
	$tag = ''            if $abc{$ltr} > 1;
	
	my($i1,$i2) = ('') x 2;
	  ($i1,$i2) = ('<b>','</b>') if $sitemods && $found{$_}->{path} && $found{$_}->{path} =~ /$sitemods/;
	  
	$p .= qq(<nobr>$i1<li><a href="$url?perlpod=1&pm=$found{$_}->{path}&title=$_" target="pod">$_</a>
	<a $tag href="$url?perlmod=1&pm=$found{$_}->{path}&title=$_" target="pod">&deg;</a>
	<a      href="$url?perlmod=1&pm=$found{$_}->{path}&title=$_&num=1" target="pod">*</a>$i2</nobr>\n)
	if  $found{$_}->{pod} && $found{$_}->{pod} == 1; # has pod

	$p .= qq(<nobr>$i1<li>$_
	<a $tag href="$url?perlmod=1&pm=$found{$_}->{path}&title=$_" target="pod">&deg;</a>
	<a      href="$url?perlmod=1&pm=$found{$_}->{path}&title=$_&num=1" target="pod">*</a>$i2</nobr>\n)
	if !$found{$_}->{pod} || $found{$_}->{pod} == 2; # has no pod
	}
}


sub findmodules
{ # sub adapted from from modlist.pl (lines with #modlist)
  # http://www.crusoe.net/~jeffp/programs/modlist

if(%found){ # if a cache file is in use %found will exist
	$_ = keys %found;
	return $_
	}
	
@path{@INC} = (); #modlist

for $base (@INC) { #modlist
	next if $showlocal eq '0' && $base eq '.';
	find(\&modules, $base) #modlist
	}

my $t = keys %found;
return $t if $_[0]; # skip the pod search?

for my $f (keys %found){ # identify modules with pod
	my $it = load($found{$f}->{path});
	
	if($it =~ /\n=[^c]\w/){ # find a pod directive besides =cut
		$found{$f}->{pod} = 1 # has pod
		}
	else{
		$found{$f}->{pod} = 0 # no pod
		}
	}
return $t
}


sub modules
{ # sub adapted from from modlist.pl (lines with #modlist)
  # http://www.crusoe.net/~jeffp/programs/modlist

$File::Find::prune = 1, 
return if exists $path{$File::Find::dir} and $File::Find::dir ne $base; #modlist

my $file = $File::Find::name;

my $module = substr $File::Find::name, length $base; #modlist
return unless $module =~ s|\.pm$||; #modlist

$module =~ m|([\W^'])\w+$|;   # discover directory delimiter returned by File::Find
my $sep = $1;

$module =~ s|^\Q$sep\E+||;
$module =~ s|\Q$sep\E|::|g;

$found{$module}->{path} = $file;
}


sub load
{ # load a file
$_ = pop;

open  IT,"< $_\0" or die "Could not open $_ : $!";
@_ = <IT>;
close IT;

return wantarray ? @_ : join '', @_;
}


sub perlpod
{ # show pod as html
my $pod = $^T;

if($i{title} eq $url){ # fix inter-module links

	$i{pm} =~ m|([\W^'])\w+\.html$|; # discover directory delimiter
	my $sep = $1;

	$i{pm} =~ s|\.html$||;
	$i{pm} =~ s|^\Q$sep\E||;
	$i{pm} =~ s|\Q$sep\E|::|g;
	
	$i{title} = $i{pm};
	
	findmodules(); # populates %found
	$_[0] = $found{$i{pm}}->{path} if $found{$i{pm}}->{pod} == 1;	
	}

pod2html( "--htmlroot=$url?perlpod=1&title=$url&pm=",
          "--infile=$_[0]",
          "--outfile=$pod.html",
          "--title=$_[1]",
		  "--backlink=Top",
		  "--header",
);

my $it = load("$pod.html");

unlink "$pod.html" or die "Could not delete $pod.html : $!";

$_ = 0;
$_ = 1 if $it =~ m|<hr>|i;

$it =~ s|<body>|$bodytag|i;

$it =~ s|(<h1><a NAME="synopsis">SYNOPSIS<\/a><\/h1>)(.*?)(<a HREF="#__index__"><small>Top</small></a>)|codeform($1,$2,$3)|eism 
if $synopsis_code_form;

$p .= $it                       if $_ > 0;
$p .= qq~No pod found in $_[0]~ if $_ < 1;
}


sub perlmod
{ # show module code
$p .= '<pre>';

if($i{num}){
	my @it = load($i{pm}); # TAINTED
	my $c = 1;

	for(@it){
		$_ = encode_entities($_);
		$p .= qq~$c. $_~;
		$c++
		}
	}
else{
	my $it = load($i{pm}); # TAINTED
	$it = encode_entities($it);
	$p .= $it
	}
$p .= '</pre>'
}


sub synopsis
{ # eval code from a synopsis form
return if $i{strip_html};

unless($i{noheader}){
	$i{htmlhead} ? print header : print header('text/plain');
	}

# turn strict off by default for the eval form
no strict; 

eval $i{synopsis} if $synopsis_code_form; # TAINTED, ETC
print $@ if $@;
exit
}


sub codeform
{ # display synopsis code in a form
my($front,$coded,$rear) = @_;

my @coded = split /\n/, $coded;
my (%len,$c,$ex);

for(@coded){ # determine width of textarea
	my $l = length($_);
	$len{$l} = $l
	}
for(sort { $b <=> $a } keys %len){
	$c = $len{$_};
	last
	}
my $r = @coded; # determine height of textarea

$coded =~ s|</?pr?e?>||ig;
$coded =~ s|<[^>]+>||g if $i{strip_html};

if($coded =~ m|<[^>]+>|){
	$ex = qq~
	<input type="Submit" name="strip_html" value="strip html">
	<input type="Hidden" name="pm" value="$i{pm}">
	<input type="Hidden" name="title" value="$i{title}">~;

	$ex .= qq~<input type="Hidden" name="perlpod" value="$i{perlpod}">~ if $i{perlpod};
	$ex .= qq~<input type="Hidden" name="perlmod" value="$i{perlmod}">~ if $i{perlmod};
	}
$ex = '' if ! $ex;

$coded = qq~$front <form><textarea name="synopsis" cols=$c rows=$r>$coded</textarea><p>
<input type="Submit" value="eval"> $ex 
<input type="checkbox" name="htmlhead" value="1"> HTML
<input type="checkbox" name="noheader" value="1"> No header</form><p> $rear~;

return $coded
}


sub view
{ # view file, any arg toggles text mode
if(-e $i{pm}){

	my $it = load($i{pm}); # TAINTED

	$it = encode_entities($it) and $p .= '<pre>'.$it.'</pre>' if $_[0]; # text
	$p .= $it if !$_[0];
	}
else{ $p .= '<p>File does not exist!' }
}


sub env
{ # show environment variables
my $v = keys %ENV;

$p .= qq~
<p align="right"><font size="+2">$v Environment Variables</font></p>
<p><table border=1 align=center cellpadding=3 cellspacing=0 width=100%>~;

for(sort { $a cmp $b } keys %ENV){
	if(/DOCUMENT_ROOT|PWD|WINDIR|SCRIPT_FILENAME/){
		$p .= qq~<TR><TD>$_ &nbsp;</TD><TD> <a href="file://$ENV{$_}">$ENV{$_}</a></TD></TR>~;
		}
	elsif(/PATH/){
		$ENV{$_} =~ s|;|;<br> |g;
		$p .= qq~<TR><TD>$_ &nbsp;</TD><TD> $ENV{$_}</TD></TR>~;
		}
	elsif(/HTTP_ACCEPT/){
		$ENV{$_} =~ s|,|,<br> |g;
		$p .= qq~<TR><TD>$_ &nbsp;</TD><TD> $ENV{$_}</TD></TR>~;
		}
	elsif(/HTTP_COOKIE/){
		$ENV{$_} =~ s|;|;<br> |g;
		$p .= qq~<TR><TD>$_ &nbsp;</TD><TD> $ENV{$_}</TD></TR>~;
		}
	elsif(/REMOTE_ADDR|SERVER_ADDR|HTTP_HOST/){
		$p .= qq~<TR><TD>$_ &nbsp;</TD><TD> <a href="http://$ENV{$_}">$ENV{$_}</a></TD></TR>~;
		}
	elsif(/SERVER_ADMIN/){
		$p .= qq~<TR><TD>$_ &nbsp;</TD><TD> <a href="mailto:$ENV{$_}">$ENV{$_}</a></TD></TR>~;
		}
	elsif(/SERVER_SIGNATURE/){
		$ENV{$_} =~ s|(</?)address>|$1i>|igm;
		$p .= qq~<TR><TD>$_ &nbsp;</TD><TD> $ENV{$_}</TD></TR>~;
		}
	else{
		$p .= qq~<TR><TD>$_ &nbsp;</TD><TD> $ENV{$_}</TD></TR>~;
		}
	}
$p .= qq~</table>~;
}

sub divide
{
$_ = $_[0] / $_[1];
$_ = sprintf "%.1F", $_;
return $_
}


sub splash
{ # display splash screen (top/cache)
$p .= qq~
<table border="0" align="center" width="100%" height="100%">
<tr><td align="center"><table><tr><td><h1>MODULATOR</h1><p>~;

if(-e $cache){
	
	my @stats = stat(_);
	
	my $size = $stats[7] / 1024;
	
	$size = sprintf "%.0F", $size;
	$size .= 'k';
	
	my $dif = $now - $stats[9];
	my $tmp = divide($dif,'86400');                            # days

	if($tmp < 1){ $tmp = divide($dif,'3600');                  # hours
		
		if($tmp < 1){ $tmp = divide($dif,'60');                # minutes
			
			if($tmp < 1){ $tmp = $dif; $tmp = $tmp.' seconds'} # seconds
				
			else{ $tmp = $tmp.' minutes' }}                    # minutes
			
		else{ $tmp = $tmp.' hours' }}                          # hours
		
	else{ $tmp = $tmp.' days' }                                # days
		
	$dif = $tmp;
	
	$stats[9] = localtime($stats[9]);
	$now      = localtime($now);
	
	$p .= qq~<table border="1" cellpadding="3" cellspacing="0">
	<tr><td colspan="2">	
	<a href="$url?make=1">refresh</a> or 
	<a href="$url?dele=1">delete</a> the cache file ($size)</td></tr>
	<tr><td align="right">created &nbsp;</td><td>$stats[9]</td></tr>
	<tr><td align="right">now &nbsp;</td><td>$now</td></tr>
	<tr><td colspan="2" align="center">
	<font size="-1"><b>cache file created $dif ago</b></font></td></tr>
	</table>~;
	}
else{
	$p .= qq~<a href="$url?make=1">create</a> a cache file~
	}
$p .= qq~<!-- cpan search form from www.perlmonks.org -->
  <form method="get" action="http://search.cpan.org/search">
    <font size="-1">
      <b>CPAN Search:</b>
      <select name="mode">
        <option value="module">Module</option>
        <option value="dist">Distribution</option>
        <option value="author">Author</option>
        <option value="doc">Documentation</option>
      </select><br>
    </font>
    <input type="text" name="query" size="32" />
    <input type="submit" value="Search" />
  </form>
<a href="http://www.perl.com/CPAN-local/modules/00modlist.long.html" target="_blank">
The Perl 5 Module List</a><p align="center"><font size="-1">
<a href="http://www.perlmonks.org/index.pl?node=MODULATOR" target="_blank">
visit the homepage</a></font></td></tr></table></td></tr></table>~;
}


sub alpha
{ # display alphabet index
$p .= qq~<p align="center"><b>~;

findmodules(); # returns %found

my %abc = ();

for(keys %found){
	
	my $ltr = '';
	
	m|^(.)|;
	$ltr = lc($1) if $1;

	$abc{$ltr}++; # only show letters that exist
	}
	
$p .= qq~<a href="$url?listmods=1#top" target="mod">^</a> &nbsp;&nbsp;~;

for(sort {$a cmp $b} keys %abc){
	$p .= qq~<a href="$url?listmods=1#$_" target="mod">$_</a> ~
	}
$p .= qq~</b><br>~;	
}


sub selfurls
{ # show paths
my $rurl = url(-relative=>'1');
my $url3 = url(-absolute=>1);
my $url4 = url(-path_info=>1);
my $url5 = url(-path_info=>1,-query=>1);

$p .= '<p><br>';
$p .= table({-border=>"1",-cellspacing=>'0',-cellpadding=>'6',-align=>'center'},
Tr([td({-colspan=>'2',},font({-size=>'+2'},b(tt('Path to this script by various methods'))))]),
Tr({-align=>'left'},[th('method').th('result')]),	

Tr([td({-colspan=>'2'},small(b('System')))]),
Tr([td(tt('$0')).td($this)]),	
Tr([td(tt('rel2abs($0)')).td(rel2abs($this))]),
Tr([td(tt('FindBin($Bin)')).td($Bin)]),

Tr([td({-colspan=>'2'},small(b('Environment Variables')))]),
Tr([td("<tt>\$ENV{'SCRIPT_NAME'}").td($ENV{'SCRIPT_NAME'})]),
Tr([td("<tt>\$ENV{'REQUEST_URI'}").td($ENV{'REQUEST_URI'})]),
Tr([td("<tt>\$ENV{'SCRIPT_FILENAME'}").td($ENV{'SCRIPT_FILENAME'})]),
Tr([td("<tt>\$ENV{'PWD'}").td($ENV{'PWD'})]),

Tr([td({-colspan=>'2'},small(b('CGI Module')))]),
Tr([td(tt('url()')).td($url)]),
Tr([td(tt('url(-relative=>1)')).td($rurl)]),
Tr([td(tt('url(-absolute=>1)')).td($url3)]),
Tr([td(tt('url(-path_info=>1)')).td($url4)]),
Tr([td(tt('url(-path_info=>1,-query=>1)')).td($url5)]));
}

sub cache
{ # create or delete cache file
if($i{make}){

	%found = ();
	
	my $total = findmodules(); # repopulate %found
	$total = 1 if -e $cache;

	open  FILE, "> $cache" or die "Could not create cache file $cache: $!";
	print FILE Data::Dumper->new([\%found],['$found'])->Indent(0)->Quotekeys(0)->Dump;
	close FILE;
	
	$_ = 'Created';
	$_ = 'Refreshed' if $total == 1;
	
	$p .= qq~$_ cache file $cache~;
	}

if($i{dele}){

	unlink $cache;
	
	$p .= qq~Could not delete cache file $cache: $!~ if $!;
	$p .= qq~Deleted cache file $cache~              if !$!
	}
$p .= qq~<p><a href="$url" target="_top">ok</a>~
}

__END__

=head1 NAME

MODULATOR

=head1 DESCRIPTION

Browse pod and code of installed perl modules.

=head1 FUNCTIONS

Lists each installed perl module linked to an HTML rendering of its pod if any.

The degree sign links to the source code of each module.

The asterisk links to line numbered source code of each module.

Option to automatically put synopsis code into a form for easy testing via eval.

Lists environment variables and result of various path and url finding methods.

Can create a cache file to improve performance.

=head1 COPYRIGHT?

This program is free software; you can redistribute it and/or modify
it under the same terms as Perl itself.

=head1 AUTHOR

http://perlmonks.org/index.pl?node=epoptai

=cut

