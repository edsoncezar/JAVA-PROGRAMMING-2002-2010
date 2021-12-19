#!perl -wi.bak
# fixPPMXMLfile

$ARGV[0] = 'D:/ActivePERL/site/lib/ppm.xml';

while ($line=<>)  {
	$line =~ s#(</PACKAGE>)(<PACKAGE NAME=)#$1\n\n$2#;
	print $line;
}

#########################
#  run `pl2bat' on this and: either place in your
#  PATH, or supply a full path specification when
#  you CALL it.
#########################

