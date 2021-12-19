#!/usr/bin/perl -w

$usage = "$0 c-source-file\n".
  "Converts a C source into printf's for another C File.\n";

die $usage unless $ARGV[0];

$cfile = $ARGV[0];

@rules = ('\x5C' => "\x5C"."\x5C",
                  '"' => '\"',
                  "'" => "\'",
                 '%' => '%%');
open(C,"<$cfile") or die "Couldn't open source file.\n$!";

while (<C>) {
  chomp;
  $line = $_;
  for ($i=0;$i<$#rules;$i+=2) {
        $line =~ s($rules[$i])($rules[$i+1])g;
  }
  print "printf(\"$line\\n\");\n";
  
}

