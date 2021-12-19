#!/bin/perl
# Monitor de portas
# use strict;
use LWP::UserAgent;
use HTTP::Request;
use HTTP::Response;

print "Iniciando Monitor de Portas ...\n";
# ---variaveis---
# ---variaveis---
$ftp = 21; $smtp = 25; $web = 80;
$i = 0;
print "ID do Processo: ";
print $$;
print "\nCarregado.";
print "\n";
while (1) {
$k = 0;
while(<access2>) { $k++; }
if ($k > $i) { 
# verificando portas
$request = new HTTP::Request( 'GET', $web);
$response = $def->request($request);
	if ($response->is_success) {
  	print $response->content;
	open(OUT, ">> scanlog_monitor.txt");
	print OUT "\n$host[$a] : $response->content"; 
	-close OUT; 
  	 } else {
  	print $response->error_as_HTML;
	}
# monitoramento sendo executado!
@date = `date`;
chomp($date[0]);
print OUT "--- Scan Detectou de $webip on $date[0]---\n"; 
$web = 0; $ftp = 0; $smtp = 0; 
}
-close OUT;

close(access2);
$currentip = $ip;
$i = $k;
}

