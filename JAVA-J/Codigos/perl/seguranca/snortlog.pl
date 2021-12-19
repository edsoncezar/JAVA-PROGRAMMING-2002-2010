#!/usr/bin/perl
# Script de Analise do Syslog SNORT
#
if($ARGV[1] eq undef)
{
   print "USAR: snortlog <logname> <machinename>\n";
   print "EXEMPLO: snortlog /var/log/messages sentinel\n";
   print "NotA: O nome da máquina é hostname, not the FQDN!\n";
   exit;
}

$machine = $ARGV[1];

$targetlen=25;
$sourcelen=35;
$protolen=12;

use Socket;


open(LOG,"< $ARGV[0]") || die "No can do";

printf("%15s %-35s %-25s %-25s\n","DATA","CUIDADO", "DE", "PARA");
print "=" x 100;
print "\n";
while(<LOG>) {
        chomp();
        if ( 
                ( !  /.*snort*/gi )
           ) { next ; }

        $_ =~ s/ $machine snort//gi ;
        $date=substr($_,0,15);
        $rest=substr($_,16,500);



        @fields=split(": ", $rest);
        $text=$fields[0];


        $fields[1] =~ s/ \-\> /-/gi;
        ($source,$dest)=split('-', $fields[1]);


        ($host,$port)=split(':',$source);


        $iaddr = inet_aton($host); 
        $name  = gethostbyaddr($iaddr, AF_INET);            
        if ( $name =~ /^$/ ) {
                $name=$host;
        }
        $name = $name . ":" .  $port;

        $skipit=0;

        ($shost,$sport)=split(':',$dest);
        $sport =~ s/ //gi;
        $siaddr = inet_aton($shost); 
        $sname  = gethostbyaddr($siaddr, AF_INET) ;            
        if ( $sname =~ /^$/ ) {
                $sname=$shost;
        }
        $sname = $sname . ":" .  $sport;
        printf("%15s %-32s %-30s   %s\n",
                $date, $text,
                $name,$sname);

}
close(LOG);
