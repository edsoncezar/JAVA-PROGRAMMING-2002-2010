#!perl -p0

s/<!-(-.*?-)->/ (my $x = $1) =~ tr,-,,s; $x = '--' if $x eq '-'; "<!-$x->" /gse;