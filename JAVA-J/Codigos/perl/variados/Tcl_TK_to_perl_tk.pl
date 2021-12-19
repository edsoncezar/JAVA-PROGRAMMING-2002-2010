#!/usr/bin/perl

print "#!/usr/bin/perl\n\nuse Tk;\n\n\$main=MainWindow->new;\n\n";

while(<>)
{
    chop;
    if(/^\s*(frame|entry|button|text|scrollbar|list)\s+(\S+)\s*(.*)$/)
    {
	
	$a=$1; $b=$2; $c=$3;
	$b=~ s/\./_/g; $b=~/^(.+)_[^_]+$/;
	print "\$main$b = \$main$1->\u$a";
	print "($c)" if($c);
	print ";\n";
    } elsif($_) {
	print "# $_\n";
    } else {
	print "\n";
    }
}



