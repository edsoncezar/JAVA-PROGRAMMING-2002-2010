#!/usr/bin/perl -w
#What the script does:
#Checks the status of the tape drive
#Then places that information into an html file.
# only without having to worry about the fact that
# the webserver can't run the command as it's owner. (CERN)
#   May not be a problem with Apache.
#   Comments and script written April 14, 2000

use strict;
#Sucking the output from amcheck and placing it in
#the varialble $status.
open (AMCHECK, "/your_path_to_amcheck/amcheck -s SETNAME |");
my $status = join "",<AMCHECK>;
close (AMCHECK);

#This displays the time
# placement is important here because
# it gets the time right after the status was retrieved
# more precise output than stating when the script was run as in $^T
my $time = localtime(time);

#The $color changes depending on TRUE : FALSE
my $color = $status =~ /error/i ? "#FFBBBB" : "#BBFFBB";
chomp ($status);

#Opening the tape-status.html file for writing
open (STATUS,">/your_path_to_status_page/tape-status.html");

#Print html to a file
print STATUS <<EOF;
<HTML>
<TITLE>Tape Drive Status</TITLE>
<HEAD>
</HEAD>
<BODY BGCOLOR="#FFFFFF">
<CENTER>
<FONT SIZE="+3" FACE="impact">Tape Drive Status</FONT>
<BR>
<FONT COLOR ="red"><STRONG>Tape Check Last Run</STRONG>
<BR>
$time
</FONT>
<BR>
<HR>
<P>
<TABLE><TR>
<TD BGCOLOR="$color">
<FONT SIZE="+1">
<B>
<PRE>$status</PRE>
</B>
</FONT>
</TD></TR></TABLE>
</CENTER>
</BODY>
</HTML>
EOF
close (STATUS);

#system("system_command") forks another process and is costly.
#It's nice of perl to have SOOOO many alternatives.
chmod 0666, "/path_to_this_page_again/tape-status.html";

