#!/usr/local/perl/bin/perl -w
#
# Resolve IP addresses in web logs.
# Diego Zamboni, Feb 7, 2000

use Socket;

# Local domain mame
$localdomain=".your.local.domain";

while (<>) {
  @f=split;
  if ($f[0] =~ /^[\d.]+$/) {
    if ($cache{$f[0]}) {
      $f[0]=$cache{$f[0]};
    }
    else {
      $addr=inet_aton($f[0]);
      if ($addr) {
        $name=gethostbyaddr($addr, AF_INET);
        if ($name) {
	  # NOTE: To ensure the veracity of $name, we really
	  # would need to do a gethostbyname on it and compare
	  # the result with the original $f[0], to prevent
	  # someone spoofing us with false DNS information.
	  # See the comments below. For this application,
	  # we don't care too much, so we don't do this.
          # Fix local names
          if ($name !~ /\./) {
            $name.=$localdomain;
          }
          $cache{$f[0]}=$name;
          $f[0]=$name;
        }
      }
    }
    print join(" ", @f)."\n";
  }
  else {
    print $_;
  }
}
