#!/usr/bin/perl
# Anything, an Everything clone
# By strredwolf

# Config...
$root='/home/tygris/web/doc/ws';

# Code
use CGI::Lite;
$cgi=new CGI::Lite;

%form=$cgi->parse_form_data;

chdir $root;

if(! -e 'ids/-1')
{
    mkdir 'names';
    mkdir 'ids';
    open(OUT,">ids/-1");
    print OUT "Item not found\n-1\nNot found!  Try again.\n";
    close(OUT);
    open(OUT,">ids/-2");
    print OUT "New node\n-2\n\n";
    close(OUT);
}
$|=1;

if($ENV{'REQUEST_METHOD'} eq "GET")
{
    if($ENV{'QUERY_STRING'})
    {
	$node_id=$form{"node_id"}; $node=$form{"node"};
	$op=$form{"op"};
    }

    $node_id=0, $op='' unless($node_id ne ''||$node);
    chomp $node; chomp $node_id;
    if($node) {
	$node =~ y/A-Z/a-z/;
	$node =~ y/a-z0-9/_/cs;
	opendir(DIR,"names/.") || print "Error: $!\n";
	@d=readdir(DIR);
	closedir(DIR);
	@z=grep(/$node/,@d);
	$node_id=-1;
	if(@z) { 
	    open(IN,"names/$z[$1]");
	    <IN>; $node_id=<IN>; chomp $node_id; close(IN);
	}
    }
    foreach($node_id,-1) {
	$node_id=-1 unless(/^-?\d+$/);
	$path="ids/$_";
	last if(-e $path);
    }
    if(open(IN, "<$path")) {
	$name=<IN>; chomp $name;
	$nid=<IN>; chomp $nid;
	@mess=<IN>; @m2=@mess;
	
	foreach $_ (@m2)
	{
	    s#\[id://(\d+)\|([^\]]+)\]#<a href="/cgi-bin/anything.pl?node_id=$1">$2</a>#g;
	    s#\[(http[^|]+)\|([^\]]+)\]#<a href="$1">$2</a>#g;
	    s#\[([^|]+)\|([^\]]+)\]#<a href="/cgi-bin/anything.pl?node=$1">$2</a>#g;
	    s#\[([^\]]+)\]#<a href="/cgi-bin/anything.pl?node=$1">$1</a>#g;
	}
	
	print "Content-type: text/html\n\n";
	print "<html><head><title>$name -- Anything</title></head>\n";
	print '<body><TABLE WIDTH="99%" BORDER="0"><tr><td>';
	print "\n<b>$name</b><BR>$node_id\n</td>\n";
	print '<TD ALIGN="RIGHT"><B><I><FONT SIZE="+2">Anything</FONT></I></B><BR><A HREF="/cgi-bin/anything.pl?node_id=-2&op=edit">New node</a></TD></TR></TABLE>';

	print "<P>";
	print @m2;
	print "</P>";
	if($op ne "edit")
	{
	    print "<P><a href=\"/cgi-bin/anything.pl?node_id=$nid&op=edit\">Edit this node</a></P>" if($nid>-1);
	} else {
	    if($nid==-2)
	    {
		if(open(IN,"<node.d8a"))
		{
		    flock(IN,2);
		    $nid = <IN>; chomp $node_id;
		    flock(IN,8);
		    close(IN);
		    $nid++;
		} else { $nid=0 };
		open(OUT,">node.d8a");
		flock(OUT,2);
		print OUT "$nid\n";
		flock(OUT,8);
		close(OUT);
	    }
	    
	    print '<hr><form method="post" action="/cgi-bin/anything.pl"><P>';
	    print '<INPUT TYPE="text" NAME="name" SIZE="60" MAXLENGTH="256" VALUE="'.$name.'";><BR>';
	    print '<TEXTAREA NAME="mess" COLS="60" WRAP="VIRTUAL" ROWS="20">';
	    print "\n";
	    print @mess;
	    print "\n</textarea>";
	    print '<INPUT TYPE="submit" NAME="op" VALUE="post">';
	    print '<INPUT TYPE="hidden" NAME="node_id" VALUE="'.$nid.'">';
	    print '</p></form>';
	}
	print "\n<hr></body></html>\n";
    } else {
	print "Died because of this: $!<BR>\n";
    }
} elsif($ENV{'REQUEST_METHOD'} eq "POST") {
    
    $node_id=$form{"node_id"}; $name=$form{"name"};
    $op=$form{"op"}; $mess=$form{"mess"};
    
    $node=$name;
    $node =~ y/A-Z/a-z/;
    $node =~ s/[^a-z0-9]/_/g;
    $path="names/$node";
    $path2="ids/$node_id";
    
    open(OUT,">$path2");
    print OUT "$name\n$node_id\n$mess";
    close(OUT);

    symlink("../$path2",$path);

    print "\n\n";
    print "<html><head><META HTTP-EQUIV=\"Refresh\" content=\"2;URL=http:/cgi-bin/anything.pl?node_id=$node_id\"></head>\n<body>";
    print "<a href=\"/cgi-bin/anything.pl?node_id=$node_id\">This is where to go...</a>\n";
    print "</body></head>\n";
}




