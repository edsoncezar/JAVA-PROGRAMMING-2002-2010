package File::Find::Duplicates;

=head1 NAME

File::Find::Duplicates - Find duplicate files

=head1 SYNOPSIS

  use File::Find::Duplicates;

  my %dupes = find_duplicate_files('/basedir');

  local $" = "\n  ";
  foreach my $filesize (keys %dupes) {
    print "Duplicate files of size $filesize:\n  @{$dupes{$filesize}}\n";
  }

=head1 DESCRIPTION

This module provides a way of finding duplicate files on your system.

When passed a base directory (or list of such directories) it returns
a hash, keyed on filesize, of lists of the identical files of that size.

=head1 TODO

Provide some much more useful interfaces to this.

=head1 AUTHOR

Tony Bowden, tony@blackstar.co.uk

=head1 SEE ALSO

File::Find

=cut

use vars qw($VERSION @ISA @EXPORT %files);

require Exporter;

@ISA     = qw/Exporter/;
@EXPORT  = qw/find_duplicate_files/;
$VERSION = '0.02';

use strict;
use File::Find;
use Digest::MD5;

sub check_file {
  -f && push @{$files{(stat(_))[7]}}, $File::Find::name;
}

sub find_duplicate_files {
  my %dupes;
  find(\&check_file, shift || ".");
  foreach my $size (sort {$b <=> $a} keys %files) {
    next unless @{$files{$size}} > 1;
    my %md5;
    foreach my $file (@{$files{$size}}) {
      open(FILE, $file) or next;
      binmode(FILE);
      push @{$md5{Digest::MD5->new->addfile(*FILE)->hexdigest}},$file;
    }
    foreach my $hash (keys %md5) {
      push @{$dupes{$size}}, @{$md5{$hash}} 
        if (@{$md5{$hash}} > 1);
    }
  }
  return %dupes;
}

"dissolving ... removing ... there is water at the bottom of the ocean";

