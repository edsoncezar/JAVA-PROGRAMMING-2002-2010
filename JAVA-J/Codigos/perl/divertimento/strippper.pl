
#!/usr/bin/perl -w

@files=@ARGV;
foreach $file (@files) {
print "Starting On $file\n";
open (INPUT,$file);
open (OUTPUT,">$file.out");
while (<INPUT>) {
chop ($line=$_);
if ($line=~/^\s*(\S.*)/) {
$line=$1;
}unless ($line=~/^\#!\//) {
if ($line=~/^\#/) {
next;
}}if ($line=~/(.*)=(.*)/) {
$line="$1=$2";
}if ($line =~ /(.*)=~(.*)/) {
$line="$1=~$2";
}if ($line=~/(.*;)\s*\# .*/) {
$line=$1;
}if ($line=~/(.*\{)\s*\# .*/) {
$line=$1;
}if ($line eq "") {
next;
}if ($line eq "}") {
print OUTPUT "$line";
}else {
print OUTPUT "$line\n";
}}close OUTPUT;
close INPUT;
}exit;

# yes SLAYER is evil johnny
