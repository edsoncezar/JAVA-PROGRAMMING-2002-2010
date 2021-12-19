#!/usr/bin/perl
# make a Solaris software package by reading filenames on standard input
# see man pages for pkginfo/pkgmk/pkgtrans
# usage:
#    mkpkg -n package_name -v version -d description  
#            
#
use POSIX qw (strftime);
use Getopt::Long;
use Cwd;
chomp($ARCH=`uname -p`);
$result=GetOptions("n=s",\$pkgname,'v=s',\$version,'d=s',\$description);
if (!$result || !defined($pkgname) || !defined($version) || !defined($description)) {
    &usage;
}
#
# gather files
#
while (<>) {
    chomp;
    if (! -e ) {
        die("No such file: [$_]\n");
    }
    if (! m#^/#) {
        die("Not an absolute path: [$_]\n");
    } 
    push(@files,$_);
}
if (@files == 0) {
   die("No file list!");
}
$TEMPDIR="/tmp/mkpkg.$$";
mkdir($TEMPDIR,0700) or 
  die("Can't make temp dir [$TEMPDIR]: $!\n");
# 
$cwd=getcwd;
$DESTFILE="${cwd}/${pkgname}.${version}.pkg";
chdir($TEMPDIR) or die("Can't change dir to temp dir [$TEMPDIR]\n");
# make "Prototype" file
#
my($time)=strftime("%m/%d/%Y %H:%M",localtime);
open(PKGINFO,">pkginfo") or die;
print PKGINFO <<EOF;
PKG=$pkgname
NAME="$pkgname $version"
VERSION="$version"
ARCH="$ARCH"
CLASSES="none"
CATEGORY="utility"
PSTAMP="$time"
VENDOR="Locally Created"
EMAIL="root\@localhost"
ISTATES="S s 1 2 3"
RSTATES="S s 1 2 3"
BASEDIR="/"
DESC="$description"
EOF

close PKGINFO;
#
# make package prototype
#
open(PROTO,">Prototype") or die;
print PROTO "i pkginfo\n";
close PROTO;
open(MKPROTO,"|pkgproto >> Prototype");
foreach $file (@files) {
    print MKPROTO "$file\n";
}
close MKPROTO or die;
#
#
# call pkgmk to create the package, then
# pkgtrans to convert it to a stream type package
#
system("pkgmk -o -r / -d $TEMPDIR -f Prototype"); 
system("pkgtrans -s $TEMPDIR $DESTFILE $pkgname"); 
chdir("/") or die ("Can't chdir to /:$!\n");
system("rm -rf $TEMPDIR");
#
#
sub usage {
    print STDERR "Usage: $0 -n <package name> -v <version number> -d <description>\n";
    print STDERR "\tYou must supply a list of filenames on stdin\n";
    exit 1;
}

