#!/usr/bin/perl -w
# Removes files by using the tarball to get the files
# by Rob Hudson (02012000)

my $tarball = $ARGV[0] if ($ARGV[0] ne '') || die "Must specify a file.\n";
my @files;
my @dirs;

# If file ends in .gz or .tgz, assume is gzipped
# Else assume it is a plain tar file
if ($tarball =~ m/\.t?gz$/) {
        @files = `tar -tzf $tarball`;
}
else {
        @files = `tar -tf $tarball`;
}

# Removes files.  If it ends with '/', then its a directory
# Push those into the directory array and move on.
foreach $file (@files) {
        chomp $file;
        if ($file =~ m!.*/$!) {
                push @dirs, $file;
                next;
        }
        print "Removing file: $file\n";
        system ("rm -f $file");
}

# Using pop here to go backwards thru the array since directories
# can be nested.
while ($dir = pop(@dirs)) {
        print "Removing directory: $dir\n";
        system ("rmdir $dir");
}

