#!/usr/bin/perl -w
use strict;
use CGI qw(:standard);
use CGI::Carp qw(fatalsToBrowser);
use LWP::UserAgent;
use HTTP::Request;
use HTTP::Response;
use URI::Heuristic;
use vars qw( $url );
$|++;

# Global vars...
my $plugin_dir = "/usr/lib/cgi-bin/comix-plugins";

# Set up the useragent, to be used by all plugs.
my $agent = LWP::UserAgent->new;
$agent->agent("Daily Comix/0.1");

if ( param() ) {
        &do_this;
} else {
        &print_main;
}

sub do_this {
        print header;
        print start_html(-Title=>"Comix",-BGCOLOR=>'white');
        foreach (param) {
                if ( open(PLUGIN, "$plugin_dir/$_") ) {
                        while (<PLUGIN>){
                                if (/^#/) { next }
                                my ($key, $val) = split(/=/,$_,2);
                                if (lc($key) eq 'title') { print "<B>$val</B><BR>\n"; }
                                if (lc($key) eq 'url') { $url = $val; print "<I>$url</I><BR>\n"; }
                                if (lc($key) eq 'code') {
                                        while () {
                                                (my $line = <PLUGIN>);
                                                $val .= $line;
                                                if ($line =~ /^}/) { last; }
                                        }
                                        &get_comic( $val );
                                }
                        }
                        close(PLUGIN);
                } else {
                        print "<BR><B>There was an error processing $plugin_dir/$_</B><BR>\n";
                }
                print "<BR><BR>";
        }
        print end_html;
}
sub print_main {
        print header;
        print start_html(-Title=>"Comix",-BGCOLOR=>'white');
        &list_plugins;
        print "<BR><BR>";
        print end_html;
}

sub list_plugins {
        print qq(<CENTER><TABLE><TR><TD>);
        print qq(<FORM ACTION="http://dream.chello.nl/cgi-bin/comix.pl"><BR>\n);
        if ( opendir(PLUGINS, $plugin_dir) ) {
                while ( defined( my $file = readdir(PLUGINS) ) ) {
                        if ( $file =~ /^\.{1,2}$/) { next; }
                        open (CONFIG, "$plugin_dir/$file") or die "Couldn't open: $!\n";
                        while (<CONFIG>) {
                                if (/^#/) { next }
                                my ($key, $val) = split(/=/,$_,2);
                                chomp($val);
                                if (lc($key) eq 'title') {  
                                        print qq(<INPUT TYPE=CHECKBOX NAME=$file VALUE=$file> );
                                        print $val, "<BR>\n";
                                }
                        }
                }
                closedir(PLUGINS);
                print qq(<INPUT TYPE=SUBMIT VALUE="Show comix">);
        } else {
                print "Error opening directory: $!\n";
        }
        print qq(</TD></TR></TABLE></FORM>)
}

sub get_comic {
        $url = URI::Heuristic::uf_urlstr($url);
        my $req = HTTP::Request->new(GET => $url);
        $req->referer("http://wish.you.were.here");
        my $response = $agent->request($req);
        if ($response->is_error) {
                print "Oops... there was an error processing the request<BR>";
                print "<DD>", $response->status_line;
        } else {
                my $content = $response->content;
                eval $_[0];
                print $@ if $@;
        }
}

Configuration file:

Title=User Friendly Daily Strip
URL=http://www.userfriendly.org/static
Code={
        $content =~ m/(<IMG ALT="Latest Strip" .+?>)/sig;
        print $1, "<BR>\n";                                     
}
