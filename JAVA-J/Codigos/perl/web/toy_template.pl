#!/usr/bin/perl -wT

# index.pl
# pod at tail

use strict;
use HTML::Template;
use CGI qw(:all);
use CGI::Carp;                 # qw(fatalsToBrowser);
use Time::localtime;
use File::stat;
use vars qw($query $content $text @url_array @common_array);

my %dir = (                    # no trailing slash
    web  => '/var/www',
    conf => '/etc/HTML-Template',
    );
my %file = (
    perl => 'index.pl',
    css  => 'site.css',
    tmpl => 'Site.template',
    );
my $commonlist = 'Common';
my $template   = HTML::Template->new(filename=>"$dir{conf}/$file{tmpl}");
my $tm         = localtime;
my $month      = $tm -> mon+1;
my $day        = $tm -> mday;
my $year       = $tm -> year+1900;
my $hour       = $tm -> hour;
my $minute     = $tm -> min;
my $second     = $tm -> sec;


# Tighten security a bit
# from perldoc CGI and CGI Programming w/Perl p210
$CGI::DISABLE_UPLOADS = 1;  # probably not needed as no forms upload anyway
$ENV{PATH}            = "";
delete @ENV{'PATH', 'IFS', 'CDPATH', 'ENV', 'BASH_ENV',};


# (must precede untaint)
# Set query param to site home if url is:
#    /    /index.pl    index.pl?    /index.pl?page
param('page','home') unless defined param('page');


# Untaint query param
if ($query = param('page') =~ /^(\w+)$/) {$content = $1;}
else {$content = 'Error';}


# Build array that lists content files
opendir DIR, "$dir{conf}/";
my @files = grep {
    $_ ne '.' &&
    $_ ne '..' &&
    $_ ne 'Common' &&
    $_ ne 'Site.template'
    }
readdir  DIR;
closedir DIR;
unless (grep{$_ eq $content} @files) {$content = 'Error';}


# define template varables
$template-> param(
    TITLE     => "$ENV{'SERVER_NAME'} - $content",
    DAY       => $day,
    MONTH     => $month,
    YEAR      => $year,
    HOUR      => $hour,
    MINUTE    => $minute,
    SECOND    => $second,
    CSSFILE   => $file{css},
    CODEMOD   => ctime(stat("$dir{web}/$file{perl}")  -> mtime),
    CSSMOD    => ctime(stat("$dir{web}/$file{css}")   -> mtime),
    URLMOD    => ctime(stat("$dir{conf}/$content")    -> mtime),
    COMMONMOD => ctime(stat("$dir{conf}/$commonlist") -> mtime),
    TMPLMOD   => ctime(stat("$dir{conf}/$file{tmpl}") -> mtime),
    PERLVER   => $],
    CGIVER    => $CGI::VERSION,
    HTMPLVER  => $HTML::Template::VERSION,
    REMHOST   => $ENV{'REMOTE_HOST'},
    REMADDR   => $ENV{'REMOTE_ADDR'},
    USERAGNT  => $ENV{'HTTP_USER_AGENT'},
    SERVADDR  => $ENV{'SERVER_ADDR'},
    SERVNAME  => $ENV{'SERVER_NAME'},
    WEBSERV   => $ENV{'SERVER_SOFTWARE'},
    REFERER   => $ENV{'HTTP_REFERER'},
    #REMUSER  => $ENV{'REMOTE_USER'},
    );


# Code to gen navbar links common to all pages
unless (my $return = do "$dir{conf}/$commonlist") {
    die "Cannot parse $commonlist: $@" if $@;
    die "Cannot do $commonlist: $!"    unless defined $return;
    die "Cannot run $commonlist"       unless $return;
    }
for (my $i = 0; $i < $#common_array; $i+=2) {
    my($loop, $aref) = @common_array[$i, $i+1];
    my @vars;
    for (my $j = 0; $j < $#{$aref}; $j+=2) {
        my($name, $url) = @{$aref}[$j, $j+1];
        push @vars, { name => $name, url => $url };
        }
    $template->param($loop, [ @vars ]);
    }


# Code to gen content and links unique to each page
unless (my $return = do "$dir{conf}/$content") {
    die "Cannot parse $content: $@" if $@;
    die "Cannot do $content: $!"    unless defined $return;
    die "Cannot run $content"       unless $return;
    }
$template->param(CONTENT => $text);
for (my $i = 0; $i < $#url_array; $i+=2) {
    my($loop, $aref) = @url_array[$i, $i+1];
    my @vars;
    for (my $j = 0; $j < $#{$aref}; $j+=2) {
        my($name, $url) = @{$aref}[$j, $j+1];

        push @vars, { name => $name, url => $url };
        }
    $template->param($loop, [ @vars ]);

    }



# feed results through template, and voila!
print header, $template->output;


###############################################################

=pod

=head1 Name

index.pl

=head1 Description

 Simple scripted website generator.
 Uses CGI.pm, HTML::Template, param('page') to eliminate dulication of code and markup.
 Also uses CSS to separate style from structure (as much as possible, anyway).

=head1 Todos

 Hashamafy remaining scalar variables
 Research additional CGI security measures
 Parse "do content" for *short*list* of allowed Perl

=head1 Associated files

 Site.template
 site.css
 bunch of content (text/link) files

=head1 Updates

 2001-05-10   21:35
   Hashamafied some scalar variables.
 2001-01-12
 2000-12-20

=head1 Credits

 vroom for www.perlmonks.org (duh ;^)
 Ovid's tutorial for HTML::Template
 chipmunk provided code snippets for arrays of links+titles
 Petruchio, davorg, Ovid and chromatic for tips on untainting
 repson provided snippet for hash of links+titles used in initial code

=head1 Author

ybiC

=cut

#########################################################################


##</code><code>##

@common_array = (
  commonloop1 => [
    'home'      => 'index.pl?page=home',
    'Christian' => 'index.pl?page=Christian',
    'computing' => 'index.pl?page=computing',
    'family'    => 'index.pl?page=family',
    'humor'     => 'index.pl?page=humor',
    'music'     => 'index.pl?page=music',
    'science'   => 'index.pl?page=science',
    'search'    => 'index.pl?page=search',
    'sports'    => 'index.pl?page=sports',
    'sundry'    => 'index.pl?page=sundry',
    'weather'   => 'index.pl?page=weather',
    ],
  commonloop2 => [
    'O\'Reilly & Associates Inc' => 'http://www.oreilly.com',
    ],
  commonloop3 => [
    'permission' => 'http://perl.oreilly.com/usage/',
    ],
  );
#########################################################################

##</code><code>##

$text ='
Welcome to Toy Template - buckets o\' links and a little content.
<BR>blah, blah, yada, yada, hummina, hummina.
';

@url_array = (
  urlloopA => [
    'weather' => 'http://www.weather.com/weather/cities/us_ne_omaha.html',
    'pollen'  => 'http://www.aaaai.org/scripts/nab/cityDetail.asp?City=Omaha&State=Ne&Region=midwest',
    'Google'  => 'http://www.google.com/',
    ],
  urlloopB => [
    'Perl Monks'    => 'http://www.perlmonks.org/',
    'SlashDot'      => 'http://slashdot.org/',
    'FreshMeat'     => 'http://freshmeat.net/',
    'Technocrat'    => 'http://technocrat.net/',
    'Debian Weekly' => 'http://www.debian.org/News/weekly/current/issue/',
    'AlistApart'    => 'http://alistapart.com/',
    'Alertbox'      => 'http://www.useit.com/alertbox/',
    'Linux Gazette' => 'http://www.linuxgazette.com/',
    ],
  urlloopC => [
    'Doctor Fun'    => 'http://metalab.unc.edu/Dave/Dr-Fun/latest.jpg',
    'Doonesbury'    => 'http://www.doonesbury.com/strip/dailydose/',
    'Tank McNamara' => 'http://www.ucomics.com/tankmcnamara/',
    'Robotman'      => 'http://www.comics.com/comics/robotman/',
    'UserFriendly'  => 'http://www.userfriendly.org/static/',
    'Tom Tomorrow'  => 'http://www.thismodernworld.com/',
    ],
  );
#########################################################################

##</code><code>##

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<HTML>
<HEAD>
  <TITLE><TMPL_VAR NAME=TITLE></TITLE>
  <LINK REL="stylesheet" TYPE="text/css" HREF="<!-- tmpl_var name=cssfile -->">
</HEAD>
<BODY BGCOLOR="white"><!-- for CSS-unaware browsers -->

<TABLE CLASS="t-blend" ALIGN="center">
  <TR CLASS="tr-plain" VALIGN="top">
    <TD>
      <SPAN CLASS="small">
        <!-- tmpl_var name=hour --> : 
	<!-- tmpl_var name=minute --> : 
	<!-- tmpl_var name=second -->
      </SPAN>
      &nbsp;&nbsp;&nbsp;&nbsp;
      <IMG SRC="Images/burst.gif" STYLE="border:0;" ALT="*">
      <SPAN CLASS="big">
        <B><!-- tmpl_var name=title --></B>
      </SPAN>
      <IMG SRC="Images/burst.gif" STYLE="border:0;" ALT="*">
      &nbsp;&nbsp;&nbsp;&nbsp;
      <SPAN CLASS="small">
        <!-- tmpl_var name=month --> - 
	<!-- tmpl_var name=day --> - 
	<!-- tmpl_var name=year -->
      </SPAN>
    </TD>
  </TR>
</TABLE>

<TABLE CLASS="t-blend" ALIGN="center" WIDTH="630">
  <TR CLASS="tr-plain" VALIGN="top">
    <TD>
      <!-- tmpl_loop name="commonloop1" -->
        <TD>
          <SPAN CLASS="small">
          <A HREF="<!-- tmpl_var name="url" -->">
            <B><!-- tmpl_var name="name" --></B>
          </A>
          </SPAN>
        </TD>
      <!-- /tmpl_loop -->
    </TD>
  </TR>
</TABLE>

<TABLE CLASS="t-contrast" ALIGN="center" WIDTH="630">
  <TR CLASS="tr-plain" VALIGN="top">
    <TD CLASS="td-l">
      <!-- tmpl_var name="content" -->
    </TD>
  </TR>
</TABLE>

<TABLE CLASS="t-contrast" ALIGN="center" WIDTH="630">
  <TR CLASS="tr-plain" VALIGN="top">
    <TD CLASS="td-33">
      <!-- tmpl_loop name="urlloopA" -->
        <A HREF="<!-- tmpl_var name="url" -->">
          <!-- tmpl_var name="name" -->
        </A>
        <BR>
      <!-- /tmpl_loop -->
    </TD>
    <TD CLASS="td-33">
      <!-- tmpl_loop name="urlloopB" -->
        <A HREF="<!-- tmpl_var name="url" -->">
          <!-- tmpl_var name="name" -->
        </A>
        <BR>
      <!-- /tmpl_loop -->
    </TD>
    <TD CLASS="td-33">
      <!-- tmpl_loop name="urlloopC" -->
        <A HREF="<!-- tmpl_var name="url" -->">
          <!-- tmpl_var name="name" -->
        </A>
        <BR>
      <!-- /tmpl_loop -->
    </TD>
  </TR>
  <TR>
    <TD>
      &nbsp;
    </TD>
  </TR>
</TABLE>

<TABLE CLASS="t-contrast" ALIGN="center" WIDTH="630">
  <TR CLASS="tr-plain" VALIGN="top">
    <TD CLASS="td-33">
      <!-- tmpl_loop name="urlloopD" -->
        <A HREF="<!-- tmpl_var name="url" -->">
          <!-- tmpl_var name="name" -->
        </A>
        <BR>
      <!-- /tmpl_loop -->
    </TD>
    <TD CLASS="td-33">
      <!-- tmpl_loop name="urlloopE" -->
        <A HREF="<!-- tmpl_var name="url" -->">
          <!-- tmpl_var name="name" -->
        </A>
        <BR>
      <!-- /tmpl_loop -->
    </TD>
    <TD CLASS="td-33">
      <!-- tmpl_loop name="urlloopF" -->
        <A HREF="<!-- tmpl_var name="url" -->">
          <!-- tmpl_var name="name" -->
        </A>
        <BR>
      <!-- /tmpl_loop -->
    </TD>
  </TR>
  <TR>
    <TD>
      &nbsp;
    </TD>
  </TR>
</TABLE>

<TABLE CLASS="t-contrast" ALIGN="center" WIDTH="630">
  <TR CLASS="tr-plain" VALIGN="top">
    <TD CLASS="td-33">
      <!-- tmpl_loop name="urlloopG" -->
        <A HREF="<!-- tmpl_var name="url" -->">
          <!-- tmpl_var name="name" -->
        </A>
        <BR>
      <!-- /tmpl_loop -->
    </TD>
    <TD CLASS="td-33">
      <!-- tmpl_loop name="urlloopH" -->
        <A HREF="<!-- tmpl_var name="url" -->">
          <!-- tmpl_var name="name" -->
        </A>
        <BR>
      <!-- /tmpl_loop -->
    </TD>
    <TD CLASS="td-33">
      <!-- tmpl_loop name="urlloopI" -->
        <A HREF="<!-- tmpl_var name="url" -->">
          <!-- tmpl_var name="name" -->
        </A>
        <BR>
      <!-- /tmpl_loop -->
    </TD>
  </TR>
</TABLE>

<TABLE CLASS="t-blend" ALIGN="center">
  <TR CLASS="tr-plain" VALIGN="top">
    <TD>
      <SPAN CLASS="small">
        <I>you</I><BR>
        <!-- TMPL_VAR NAME= REMHOST --> &nbsp;&nbsp; 
	<!-- TMPL_VAR NAME= REMADDR --><BR>
        <!-- TMPL_VAR NAME= USERAGNT --><BR>
        Referred by <!-- TMPL_VAR NAME= REFERER -->
      </SPAN>
    </TD>
  </TR>
</TABLE>

<TABLE CLASS="t-blend" ALIGN="center">
  <TR CLASS="tr-plain" VALIGN="top">
    <TD>
      <A HREF="http://www.apache.org/">
        <IMG SRC="Images/apache_feather.gif" STYLE="border:0;">
      </A>
      <A HREF="http://www.debian.org/">
        <IMG SRC="Images/debian_button-k1.png" STYLE="border:0;" HEIGHT="31" WIDTH="88">
      </A>
      <A HREF="http://www.perl.com/">
        <IMG SRC="Images/rectangle_power_perl.gif" STYLE="border:0;" HEIGHT="31" WIDTH="88">
      </A>
      <A HREF="http://validator.w3.org/">
        <IMG SRC="Images/vh40.gif" STYLE="border:0;" HEIGHT="31" WIDTH="88">
      </A>
      <A HREF="http://jigsaw.w3.org/css-validator/">
        <IMG SRC="Images/vcss.gif" STYLE="border:0;" HEIGHT="31" WIDTH="88">
      </A>
    </TD>
  </TR>
</TABLE>


<TABLE CLASS="t-blend" ALIGN="center">
  <TR CLASS="tr-plain" VALIGN="top">
    <TD CLASS="td-r">
    </TD>
    <TD CLASS="td-c">
      <SPAN CLASS="small">
        <I>me</I>
      </SPAN>
    </TD>
    <TD CLASS="td-l">
    </TD>
  </TR>
  <TR CLASS="tr-plain" VALIGN="top">
    <TD CLASS="td-r">
      <SPAN CLASS="small">
        <!-- TMPL_VAR NAME=SERVNAME --> &nbsp;&nbsp; 
	<!-- TMPL_VAR NAME=SERVADDR --><BR>
        <!-- TMPL_VAR NAME=WEBSERV --><BR>
        Perl <!-- TMPL_VAR NAME=PERLVER --><BR>
	CGI.pm <!-- TMPL_VAR NAME=CGIVER --><BR>
	HTML::Template <!-- TMPL_VAR NAME=HTMPLVER -->
      </SPAN>
    </TD>
    <TD>
    </TD>
    <TD CLASS="td-l">
      <SPAN CLASS="small">
        Content &nbsp;<!-- TMPL_VAR NAME=URLMOD --><BR>
        Navbar &nbsp;&nbsp;<!-- TMPL_VAR NAME=COMMONMOD --><BR>
        Markup &nbsp;&nbsp;<!-- TMPL_VAR NAME=TMPLMOD --><BR>
        Style &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<!-- TMPL_VAR NAME=CSSMOD --><BR>
        Code &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<!-- TMPL_VAR NAME=CODEMOD -->
      </SPAN>
    </TD>
  </TR>
</TABLE>

<P>

<TABLE CLASS="t-blend" ALIGN="center">
  <TR CLASS="tr-plain" VALIGN="top">
    <TD>
      <SPAN CLASS="small">
        Perl camel trademark 
        <!-- tmpl_loop name="commonloop2" -->
          <A HREF="<!-- tmpl_var name="url" -->">
            <!-- tmpl_var name="name" -->
          </A>
        <!-- /tmpl_loop -->
        <BR>
	Used with 
        <!-- tmpl_loop name="commonloop3" -->
          <A HREF="<!-- tmpl_var name="url" -->">
            <!-- tmpl_var name="name" -->
          </A>
        <!-- /tmpl_loop -->
      </SPAN>
    </TD>
  </TR>
</TABLE>

</BODY>
</HTML>
#########################################################################

##</code><code>##

BODY  {
    font-family : arial, sans-serif, geneva, helvetica, verdana; 
    /* background-color : lime; */
    background-color : beige;
    text-align : center;
    margin : 0;
    }

A:visited  {
    font-weight : normal;
    line-height : 140%;
    color : blue;
    }

A:link  {
    font-weight : normal;
    line-height : 140%;
    color : blue;
    }

A:hover  {
    font-weight : normal;
    line-height : 140%;
    color : red;
    background-color : yellow;
    }

A:active  {
    font-weight : bold;
    line-height : 140%;
    color : red;
    }

.tr-plain  {
    text-align : center;
    }

.tiny  {
    font-size : 55%;
    line-height : 135%;
    }

.td-r  {
    text-align : right;
    }

.td-l  {
    text-align : left;
    }

.td-c  {
    text-align : center;
    }

.td-33  {
    width : 33%;
    }

.t-contrast  {
    background-color : white;
    text-align : center;
    border-width : 0;
    border-style : none;
    border-color : inherit;
    }

.t-blend  {
    text-align : center;
    border-width : 0;
    border-style : none;
    border-color : inherit;
    }

.small  {
    font-size : 65%;
    line-height : 135%;
    }

.big  {
    font-weight : bold;
    font-size : 130%;
    line-height : 200%;
    }
