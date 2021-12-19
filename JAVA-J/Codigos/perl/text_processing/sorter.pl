#!/usr/bin/perl -wT
use CGI qw(:all);
use strict;

print header, start_html("Alphabetical sorter"), center(h2("Alphabetical sorter"));


my $query = new CGI;
if ($query->param(1)) {
	foreach my $key (sort {lc(param($a)) cmp lc(param($b))} ($query->param)) {
		print "<STRONG>$key</STRONG> = ";
		my @values = $query->param($key);
		print join("",@values),"<BR>\n";
	}

}else{
	print start_form;
	print textfield({name=>$_,size=>'20'}),br for 1..5;
	print submit, end_form;
}
