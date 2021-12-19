#!/usr/bin/perl
# >=8.2.3-REL -> not vulnerable
######################################
$file_in = @ARGV[0];
$file_out = @ARGV[1];
$display = @ARGV[2];
$not_vuln = "8.2.3-REL";
unless ($file_in) {
 print "[DkL]         traduzido por Douglas 	        [DkL]\n";
 print "Usar : ./bind_scan.pl <file in> [file out] [display]\n";
 }
else {
 if ($file_out eq "" ) {
  $file_out = "bs_output.txt";
 }
 if ($display eq "" ) {
  $display = 1;
 }
open(IP,"<$file_in");
while (!eof(IP)) {
      $host = <IP>;
@bind_ver = `dig @$host version.bind chaos txt | grep \\\"8`;
foreach $tmp(@bind_ver)
  {
    @bind_tmp =split(" ",$tmp);
    if ($bind_tmp[4] =~ /$not_vuln/i ) {
     if ($display == 1) {
     print "\n:. ".$host." Bind: Versão --> ".$bind_tmp[4]." Não Vulnerável .:\n";
     }
    }

    else {
     $vuln = ":. ".$host." Bind: Versão --> ".$bind_tmp[4]." Vulnerável .:";
     print $vuln."\n";
     open (NEW,">$file_out");
     foreach ($vuln) {print NEW}
     close NEW;
    }
  }
 }
print "\n\n EOF! -dkl- Bind Scan -dkl-  \n    Douglas Vigliazzi \n\n";
}

