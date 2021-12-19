#!perl
#p-in-p.p

open LOG, ">LOG.~" or die $!;
@ARGV = split /\n/,`TYPE .\\TMP.~`;
print LOG "Patterns: ",qq{\n} x 2, join qq{\n}, @ARGV[$#ARGV,$#ARGV-1],qq{\n};
$P=pop @ARGV;
$S=pop @ARGV;
@ARGV = sort {uc($a) cmp uc($b)} @ARGV;
print LOG "\n" . @ARGV . " Files: ",qq{\n} x 2, join qq{\n},@ARGV, qq{\n};
@ARGV = &Dequote(@ARGV);
my $countfiles = $#ARGV + 1;
$^I=q{.PBU};
$S =~ s#\\@#@#g;
while (<>){
    my $former = $_; chomp $former;
	if (s/($P)/$S/g){
		print LOG qq{\n},q{  Now in file: },
		    $ARGV , qq{:\n};
		print LOG qq{Replacing: *${1}* in: } .'"' . $former . '"' .qq{\n}.
		qq{with: } .'"'. $_ .'"'. qq{\n};
		print;
	}
 print;
};
close (LOG);
system "notepad LOG.~";
sleep 3;
unlink qw{LOG.~ TMP.~};
return $countfiles;

sub Dequote  {

 for (@_)  {
   $_ = substr ($_,1,(length $_) - 2);
 }
 return @_;
}


