#!/usr/bin/perl -w
use strict;
$|++;

use File::Find;
use Cwd;

my $dir = cwd;

find sub {
  return unless -l;

  my @right = split /\//, $File::Find::name;

  my @left = do {
    @right && ($right[0] eq "") ?
      shift @right :            # quick way
        split /\//, $dir;
  };    # first element always null

  while (@right) {
    my $item = shift @right;
    next if $item eq "." or $item eq "";

    if ($item eq "..") {
      pop @left if @left > 1;
      next;
    }

    my $link = readlink (join "/", @left, $item);

    if (defined $link) {
      my @parts = split /\//, $link;
      if (@parts && ($parts[0] eq "")) { # absolute
        @left = shift @parts;   # quick way
      }
      unshift @right, @parts;
      next;
    } else {
      push @left, $item;
      next;
    }
  }
  print "$File::Find::name is ", join("/", @left), "\n";

}, @ARGV;

