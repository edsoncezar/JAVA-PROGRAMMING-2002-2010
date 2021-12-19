use Socket qw( inet_aton inet_ntoa );

sub IP_radix_sort {
  for (my $i = 3; $i >= 0; $i--) {
    my @table;
    for (@_) {
      push @{ $table[unpack "\@$i C", $_] }, $_;
    }
    @_ = map @$_, @table;
  }
  return @_;
}

sub IPsort {
  map inet_ntoa,
  IP_radix_sort
  map inet_aton,
  @_;
}

