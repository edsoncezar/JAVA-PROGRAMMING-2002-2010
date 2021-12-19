#!/usr/bin/perl -w

use strict;

our($text, %config, %complements);

$config{colors}={
	#sigils=red
	'$'	=> '993333',
	'@'	=> 'CC6633',
	'%'	=> '660000',
	'&'	=> '990033',
	'*'	=> '990000',

	#quotes=blue
	"'"	=> '6699FF',
	'"' 	=> '3366CC',
	'`' 	=> '333399',
	'qw' 	=> '0000FF',

	#regexes
	'm'	=> '000088',
	's' 	=> '0000CC',
	'y' 	=> '0000FF',
	'tr'	=> '0000FF',

	#various
	'()'	=> '880000',
	'[]'	=> 'CC0000',
	'{}'	=> 'FF0000',
	'<<'	=> '00CC00',
	'#'	=> '339999',
};

%complements=(
	'{' => '}',
	'(' => ')',
	'[' => ']',
	'<' => '>'
);

for(32..127) {
	$complements{chr($_)}=chr($_) unless exists $complements{chr($_)};
}

$/=undef;
$text=<>;

$text=highlight_quotes($text);
$text=highlight_regexes($text);
$text=highlight_various($text);
$text=highlight_sigils($text);
$text=fix_it_up($text);

print <<"END";
<HTML><font face="Courier New">
	$text
</font></HTML>
END


sub fixit($) {
	local $_=shift;
	
	if(/qq/)    { '"' }
	elsif(/qx/) { '`' }
	elsif(/qw/) { 'qw'}
	elsif(/q/)  { "'" }
	else { die "$0: $_ is not a valid quoter\n" }
}

sub fix_it_up($) {
	local $_=shift;
	s/\n/\0<BR\0>\n/g;
	s/(?<!\0)</&lt;/g;
	s/(?<!\0)>/&gt;/g;
	s/  /&nbsp; /g;
	#since I'm using null as an escape character, I have to get rid of the ones that are left
	s/\0//g;
	return $_;
}

sub highlight_quotes($) {
	local $_=shift;

	#heredocs: MUST BE DONE before '' and "" highlighting
	s|<<(['"]?)(.*)\1([^\n]*?)\n(.*?)\n\2\n|<<$1$2$1$3\n\0<font color=\0"\0#$config{colors}{'<<'}\0"\0>$4\0</font\0>\n$2\n|gs;

	#normal quoted strings
	s|(?<!\0)(['"`])(.*?)(?<!\\)\1|$1\0<font color=\0"\0#$config{colors}{$1}\0"\0>$2\0</font\0>$1|gs;

	# qX
	s|(q[qxwr]?)([^\w\s\[\{\(\<])(.*?)(?<!\\)(??{quotemeta($complements{$2})})|qq($1$2\0<font color=\0"\0#).$config{colors}{fixit($1)}.qq(\0"\0>$3\0</font\0>$complements{$2})|ges;

	return $_;
}

sub highlight_various($) {
	local $_=shift;

	#highlight subscripting and function args
	s|(?<=\w)([\{\[\(])(.*?)(??{quotemeta($complements{$1})})|\0$1\0<font color=\0"\0#$config{colors}{$1.$complements{$1}}\0"\0>$2\0</font\0>\0$complements{$1}|sg;

	#highlight comments--unless the sharp appears to be the delimiter of a q, qq, qw, qx, tr, y, m, or s
	s|^([^#]+)(?<![qwxmsry\0])#(.*)$|$1\0<font color=\0"\0#$config{colors}{'#'}\0"\0>\0#$2\0</font\0>|gm;
	s|^#(.*)$|\0<font color=\0"\0#$config{colors}{'#'}\0"\0>\0#$1\0</font\0>|gm;

	return $_;
}

sub highlight_sigils($) {
	local $_=shift;

	#wrapped in {}
	1 while s|(?<!\0)([\$\@\%\&\*])(?<!\0)\{(.*?)(?<!\0)\}|\0<font color=\0"\0#$config{colors}{$1}\0"\0>\0$1\0{$2\0}\0</font\0>|sg;

	#unwrapped
	s[(?<!\0)([\$\@\%\&\*])((?:[\w:]|\0(?:\{|\}|\[|\]))*)][\0<font color=\0"\0#$config{colors}{$1}\0"\0>\0$1$2\0</font\0>]sg;

	return $_;
}

sub highlight_regexes($) {
	local $_=shift;

	#m//, m{}
	s{m([^\w\s\0])(.*?)(?<![\\\0])(??{quotemeta($complements{$1})})}
	 {m$1\0<font color=\0"\0#$config{colors}{m}\0"\0>$2\0</font\0>$complements{$1}}gs;

	#s///, tr///
	s{(s|tr|y)([^\w\s\0])(.*?)(?![\\\0])\2(.*?)(?![\\\0])\2}
	 {$1$2\0<font color=\0"\0#$config{colors}{$1}\0"\0>$3\0</font\0>$2\0<font color=\0"\0#$config{colors}{'"'}\0"\0>$4\0</font\0>$2}gs;

	#s{}{}, tr{}{}
		s{(s|tr|y)([\{\[\(\<])(.*?)(?<![\\\0])(??{quotemeta($complements{$2})})(\s*)([\{\[\(\<])(.*?)(?<![\\\0])(??{quotemeta($complements{$5})})}{$1$2\0<font color=\0"\0#$config{colors}{$1}\0"\0>$3\0</font\0>$complements{$2}$4$5\0<font color=\0"\0#$config{colors}{'"'}\0"\0>$6\0</font\0>$complements{$5}}gs;

	return $_;
}

