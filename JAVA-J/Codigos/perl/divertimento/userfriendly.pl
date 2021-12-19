#!/usr/bin/perl
@a=localtime;
$a[4]++;$a[4]=~s/(\d)/0$1/;
$y=$a[5]+1900;
($Y=$y)=~s#(\d\d)(\d\d)#$2#;
($a[2]<9)&&$a[3]--;
(length($a[3])<2)&&($a[3]="0$a[3]");
($b=lc(localtime(time)))=~s/([\w]{3}\s){2}//;
($b=$1)=~s/\s//;
print("Location: http://www.userfriendly.org/cartoons/archives/${Y}${b}/${y}$a[4]$a[3].html\n\n");
