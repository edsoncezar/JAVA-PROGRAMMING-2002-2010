#!/usr/bin/perl
 
$numargs = scalar @ARGV;
 
if ($numargs == 0) {
        print "Clare's Random Password Generator\n";
        print "Usage: pwdgen template [number]\n";
        print "where template is a string composed of the following characters\n";
        print "\tl\tlower case letter\n\tL\tupper case letter\n\tc\tlower case consonant\n\tC\tupper case consonant\n";
        print "\tl\tlower case vowel\n\tL\tupper case vowel\n\tn\tnumber\n\tp\tpunctuation mark\n\ta\tany character\n";
        print "Any character in the template which is not one of those above will be placed in the password at the same position\n";
        print "number refers to the number of passwords to generate - this defaults to 10.\n";
}
 
$template = $ARGV[0];
 
if ($ARGV[1] =~ /^\d+$/) {
        $num = $ARGV[1];
}
else {
        $num = 10;
}
 
my @l = split '', 'qwertyuiopasdfghjklzxcvbnm';
my @n = split '', '0123456789';
my @p = split '', ',.?<>;:@/!"%^&*()-+=_';
my @L = split '', 'QWERTYUIOPASDFGHJKLZXCVBNM';
my @c = split '', 'qwrtypsdfghjklzxcvbnm';
my @C = split '', 'QWRTYPSDFGHJKLZXCVBNM';
my @v = split '', 'aeiou';
my @V = split '', 'AEIOU';
my @a = split '', 'qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM,.?<>;:@/!"%^&*()-+=_0123456789';
 
my @t = split '', $template;
$len = scalar @t;
 
my $newpass = "";
 
for ($c = 0; $c < $num; $c++) {
        for ($x = 0; $x < $len ; $x ++) {
                if ($t[$x] eq "l") { $newpass .= $l[rand @l]; next;}
                if ($t[$x] eq "L") { $newpass .= $L[rand @L]; next;}
                if ($t[$x] eq "c") { $newpass .= $c[rand @c]; next;}
                if ($t[$x] eq "C") { $newpass .= $C[rand @C]; next;}
                if ($t[$x] eq "v") { $newpass .= $v[rand @v]; next;}
                if ($t[$x] eq "V") { $newpass .= $V[rand @V]; next;}
                if ($t[$x] eq "n") { $newpass .= $n[rand @n]; next;}
                if ($t[$x] eq "p") { $newpass .= $p[rand @p]; next;}
                if ($t[$x] eq "a") { $newpass .= $a[rand @a]; next;}
                $newpass .= $t[$x];
        }
        print "$newpass\n";
        $newpass = "";
}

